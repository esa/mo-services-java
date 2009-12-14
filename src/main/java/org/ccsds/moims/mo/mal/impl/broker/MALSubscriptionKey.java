package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.Identifier;

public final class MALSubscriptionKey implements Comparable
{
  public static final String ALL_ID = "*";
  private final String key1;
  private final String key2;
  private final String key3;
  private final String key4;

  public MALSubscriptionKey(EntityKey lst)
  {
    super();

    key1 = getIdValue(lst.getFirstSubKey());
    key2 = getIdValue(lst.getSecondSubKey());
    key3 = getIdValue(lst.getThirdSubKey());
    key4 = getIdValue(lst.getFourthSubKey());
  }

  @Override
  public int compareTo(Object o)
  {
    MALSubscriptionKey rhs = (MALSubscriptionKey) o;
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

  public boolean matches(MALSubscriptionKey rhs)
  {
    boolean matched = matchedSubkey(key1, rhs.key1);

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

    return matched;
  }

  private int compareSubkey(String myKeyPart, String theirKeyPart)
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

  private boolean matchedSubkey(String myKeyPart, String theirKeyPart)
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

  private static String getIdValue(Identifier id)
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
    StringBuffer buf = new StringBuffer();
    buf.append('[');
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
