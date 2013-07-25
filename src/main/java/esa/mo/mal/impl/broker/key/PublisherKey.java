/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl.broker.key;

import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Simple class that identifies a publisher in a MAL broker.
 */
public class PublisherKey implements Comparable
{
  /**
   * Match all string constant.
   */
  public static final String ALL_ID = "*";
  /**
   * Match all numeric constant.
   */
  public static final Long ALL_NUMBER = 0L;
  /**
   * Match all ushort constant.
   */
  public static final UShort ALL_SHORT = new UShort(0);
  /**
   * Hash function magic number.
   */
  protected static final int HASH_MAGIC_NUMBER = 47;
  /**
   * First sub key.
   */
  private final String key1;
  /**
   * Second sub key.
   */
  private final Long key2;
  /**
   * Third sub key.
   */
  private final Long key3;
  /**
   * Fourth sub key.
   */
  private final Long key4;

  /**
   * Constructor.
   * @param key Entity key.
   */
  public PublisherKey(final EntityKey key)
  {
    this.key1 = getIdValue(key.getFirstSubKey());
    this.key2 = key.getSecondSubKey();
    this.key3 = key.getThirdSubKey();
    this.key4 = key.getFourthSubKey();
  }

  @Override
  public String toString()
  {
    final StringBuilder buf = new StringBuilder();
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
  
  @Override
  public boolean equals(final Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final PublisherKey other = (PublisherKey) obj;
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
  public int compareTo(final Object o)
  {
    final PublisherKey rhs = (PublisherKey) o;
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
  public boolean matchesWithWildcard(final EntityKey rhs)
  {
    if (null != rhs)
    {
      boolean matched = matchedSubkeyWithWildcard(key1, getIdValue(rhs.getFirstSubKey()));

      if (matched)
      {
        matched = matchedSubkeyWithWildcard(key2, rhs.getSecondSubKey());
        if (matched)
        {
          matched = matchedSubkeyWithWildcard(key3, rhs.getThirdSubKey());
          if (matched)
          {
            matched = matchedSubkeyWithWildcard(key4, rhs.getFourthSubKey());
          }
        }
      }

      return matched;
    }

    return false;
  }

  /**
   * Returns true if this key matches supplied argument taking into account wildcards.
   * @param rhs Key to match against.
   * @return True if matches.
   */
  public boolean matchesWithWildcard(final PublisherKey rhs)
  {
    if (null != rhs)
    {
      boolean matched = matchedSubkeyWithWildcard(key1, rhs.key1);

      if (matched)
      {
        matched = matchedSubkeyWithWildcard(key2, rhs.key2);
        if (matched)
        {
          matched = matchedSubkeyWithWildcard(key3, rhs.key3);
          if (matched)
          {
            matched = matchedSubkeyWithWildcard(key4, rhs.key4);
          }
        }
      }

      return matched;
    }

    return false;
  }

  /**
   * Helper method to return the string value from an Identifier.
   * @param id The identifier.
   * @return The value or null.
   */
  protected static String getIdValue(final Identifier id)
  {
    if ((null != id) && (null != id.getValue()))
    {
      return id.getValue();
    }
    return null;
  }

  /**
   * Compares a String based sub-key.
   * @param myKeyPart The first key part.
   * @param theirKeyPart The second key part.
   * @return -1, 0, or 1 based on how the two values compare using normal comparable rules.
   */
  protected static int compareSubkey(final String myKeyPart, final String theirKeyPart)
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

  /**
   * Compares an Long based sub-key.
   * @param myKeyPart The first key part.
   * @param theirKeyPart The second key part.
   * @return -1, 0, or 1 based on how the two values compare using normal comparable rules.
   */
  protected static int compareSubkey(final Long myKeyPart, final Long theirKeyPart)
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

  /**
   * Compares two String sub-keys taking into account wildcard values.
   * @param myKeyPart The first key part.
   * @param theirKeyPart The second key part.
   * @return True if they match or one is the wildcard.
   */
  protected static boolean matchedSubkeyWithWildcard(final String myKeyPart, final String theirKeyPart)
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

  /**
   * Compares two Long sub-keys taking into account wildcard values.
   * @param myKeyPart The first key part.
   * @param theirKeyPart The second key part.
   * @return True if they match or one is the wildcard.
   */
  protected static boolean matchedSubkeyWithWildcard(final Long myKeyPart, final Long theirKeyPart)
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

  /**
   * Compares two UShort sub-keys taking into account wildcard values.
   * @param myKeyPart The first key part.
   * @param theirKeyPart The second key part.
   * @return True if they match or one is the wildcard.
   */
  protected static boolean matchedSubkeyWithWildcard(final UShort myKeyPart, final UShort theirKeyPart)
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
}
