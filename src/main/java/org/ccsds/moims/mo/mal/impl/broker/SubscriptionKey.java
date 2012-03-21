/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL subscription.
 */
public final class SubscriptionKey implements Comparable
{
  /**
   * Match all constant.
   */
  public static final String ALL_ID = "*";
  public static final Integer ALL_NUMBER = 0;
  public static final UShort ALL_SHORT = new UShort(ALL_NUMBER);
  private static final int HASH_MAGIC_NUMBER = 47;
  private final String domain;
  private final boolean andSubDomains;
  private final UShort area;
  private final UShort service;
  private final UShort operation;
  private final String key1;
  private final Integer key2;
  private final Integer key3;
  private final Integer key4;

  /**
   * Constructor.
   * @param lst Entity key.
   */
  public SubscriptionKey(MALMessageHeader hdr, EntityRequest rqst, EntityKey lst)
  {
    super();

    String tmpDomain = "";
    boolean tmpAndSubDomains = false;

    IdentifierList mdomain = hdr.getDomain();
    IdentifierList sdomain = rqst.getSubDomain();
    if ((null != mdomain) || (null != sdomain))
    {
      StringBuilder buf = new StringBuilder();
      if ((null != mdomain) && (0 < mdomain.size()))
      {
        buf.append(StructureHelper.domainToString(mdomain));
      }

      if ((null != sdomain) && (0 < sdomain.size()))
      {
        int i = 0;
        int e = sdomain.size();
        while (i < e)
        {
          String id = String.valueOf((Identifier) sdomain.get(i));
          if (!ALL_ID.equals(id))
          {
            if (0 < buf.length())
            {
              buf.append('.');
            }

            buf.append(id);
          }
          else
          {
            tmpAndSubDomains = true;
          }

          ++i;
        }
      }

      tmpDomain = buf.toString();
    }

    this.domain = tmpDomain;
    this.andSubDomains = tmpAndSubDomains;
    this.area = getIdValueOrWildcard(hdr.getServiceArea(), rqst.getAllAreas());
    this.service = getIdValueOrWildcard(hdr.getService(), rqst.getAllServices());
    this.operation = getIdValueOrWildcard(hdr.getOperation(), rqst.getAllOperations());
    this.key1 = getIdValue(lst.getFirstSubKey());
    this.key2 = lst.getSecondSubKey();
    this.key3 = lst.getThirdSubKey();
    this.key4 = lst.getFourthSubKey();
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final SubscriptionKey other = (SubscriptionKey) obj;
    if ((this.key1 == null) ? (other.key1 != null) : !this.key1.equals(other.key1))
    {
      return false;
    }
    if ((this.key2 == null) ? (other.key2 != null) : !this.key2.equals(other.key2))
    {
      return false;
    }
    if ((this.key3 == null) ? (other.key3 != null) : !this.key3.equals(other.key3))
    {
      return false;
    }
    if ((this.key4 == null) ? (other.key4 != null) : !this.key4.equals(other.key4))
    {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    int hash = 7;
    hash = HASH_MAGIC_NUMBER * hash + (this.key1 != null ? this.key1.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.key2 != null ? this.key2.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.key3 != null ? this.key3.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.key4 != null ? this.key4.hashCode() : 0);
    return hash;
  }

  @Override
  public int compareTo(Object o)
  {
    SubscriptionKey rhs = (SubscriptionKey) o;
    int rv = compareSubkey(this.key1, rhs.key1);
    if (0 == rv)
    {
      rv = compareSubkey(this.key2, rhs.key2);
      if (0 == rv)
      {
        rv = compareSubkey(this.key3, rhs.key3);
        if (0 == rv)
        {
          rv = compareSubkey(this.key4, rhs.key4);
        }
      }
    }

    return rv;
  }

  /**
   * Returns true if this key matches supplied argument taking into account wildcards.
   * @param rhs Key to match against.
   * @return True if matches.
   */
  public boolean matches(UpdateKey rhs)
  {
    boolean matched = rhs.domain.startsWith(this.domain);

    if (matched)
    {
      if ((this.domain.length() < rhs.domain.length()))
      {
        matched = this.andSubDomains;
      }

      if (matched)
      {
        matched = matchedSubkey(area, rhs.area);
        if (matched)
        {
          matched = matchedSubkey(service, rhs.service);
          if (matched)
          {
            matched = matchedSubkey(operation, rhs.operation);
            if (matched)
            {
              matched = matchedSubkey(key1, rhs.key1);
              if (matched)
              {
                matched = matchedSubkey(key2, rhs.key2);
                if (matched)
                {
                  matched = matchedSubkey(key3, rhs.key3);
                  if (matched)
                  {
                    matched = matchedSubkey(key4, rhs.key4);
                  }
                }
              }
            }
          }
        }
      }
    }

    return matched;
  }

  protected static int compareSubkey(String myKeyPart, String theirKeyPart)
  {
    if ((null == myKeyPart) || (null == theirKeyPart))
    {
      if ((null != myKeyPart) || (null != theirKeyPart))
      {
        if (null == myKeyPart)
        {
          return -1;
        }

        return 1;
      }
    }
    else
    {
      if (!myKeyPart.equals(theirKeyPart))
      {
        return myKeyPart.compareTo(theirKeyPart);
      }
    }

    return 0;
  }

  protected static int compareSubkey(Integer myKeyPart, Integer theirKeyPart)
  {
    if ((null == myKeyPart) || (null == theirKeyPart))
    {
      if ((null != myKeyPart) || (null != theirKeyPart))
      {
        if (null == myKeyPart)
        {
          return -1;
        }

        return 1;
      }
    }
    else
    {
      if (!myKeyPart.equals(theirKeyPart))
      {
        return myKeyPart.compareTo(theirKeyPart);
      }
    }

    return 0;
  }

  protected static boolean matchedSubkey(String myKeyPart, String theirKeyPart)
  {
    if (ALL_ID.equals(myKeyPart) || ALL_ID.equals(theirKeyPart))
    {
      return true;
    }

    if ((null == myKeyPart) || (null == theirKeyPart))
    {
      if ((null == myKeyPart) && (null == theirKeyPart))
      {
        return true;
      }

      return false;
    }

    return myKeyPart.equals(theirKeyPart);
  }

  protected static boolean matchedSubkey(Integer myKeyPart, Integer theirKeyPart)
  {
    if (ALL_NUMBER.equals(myKeyPart) || ALL_NUMBER.equals(theirKeyPart))
    {
      return true;
    }

    if ((null == myKeyPart) || (null == theirKeyPart))
    {
      if ((null == myKeyPart) && (null == theirKeyPart))
      {
        return true;
      }

      return false;
    }

    return myKeyPart.equals(theirKeyPart);
  }

  protected static boolean matchedSubkey(UShort myKeyPart, UShort theirKeyPart)
  {
    if (ALL_SHORT.equals(myKeyPart) || ALL_SHORT.equals(theirKeyPart))
    {
      return true;
    }

    if ((null == myKeyPart) || (null == theirKeyPart))
    {
      if ((null == myKeyPart) && (null == theirKeyPart))
      {
        return true;
      }

      return false;
    }

    return myKeyPart.equals(theirKeyPart);
  }

  protected static UShort getIdValueOrWildcard(UShort id, boolean isWildcard)
  {
    if (isWildcard)
    {
      return ALL_SHORT;
    }

    return id;
  }

  protected static String getIdValue(Identifier id)
  {
    if ((null != id) && (null != id.getValue()))
    {
      return id.getValue();
    }

    return null;
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append('[');
    buf.append(this.domain);
    if(this.andSubDomains)
    {
      buf.append(".*");
    }
    buf.append(':');
    buf.append(this.area);
    buf.append(':');
    buf.append(this.service);
    buf.append(':');
    buf.append(this.operation);
    buf.append(':');
    buf.append(this.key1);
    buf.append('.');
    buf.append(this.key2);
    buf.append('.');
    buf.append(this.key3);
    buf.append('.');
    buf.append(this.key4);
    buf.append(']');
    return buf.toString();
  }
}
