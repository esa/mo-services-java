/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Simple base class that represents one of the many update keys of a broker.
 */
public abstract class ElementKey implements Comparable
{
  /**
   * Match all string constant.
   */
  public static final String ALL_ID = "*";
  /**
   * Match all numeric constant.
   */
  public static final Integer ALL_NUMBER = 0;
  /**
   * Match all ushort constant.
   */
  public static final UShort ALL_SHORT = new UShort(ALL_NUMBER);
  /**
   * Hash function magic number.
   */
  protected static final int HASH_MAGIC_NUMBER = 47;
  /**
   * First sub key.
   */
  protected final String key1;
  /**
   * Second sub key.
   */
  protected final Integer key2;
  /**
   * Third sub key.
   */
  protected final Integer key3;
  /**
   * Fourth sub key.
   */
  protected final Integer key4;

  /**
   * Constructor.
   * 
   * @param key1 First sub key.
   * @param key2 Second sub key.
   * @param key3 Third sub key.
   * @param key4 Fourth sub key.
   */
  public ElementKey(final String key1, final Integer key2, final Integer key3, final Integer key4)
  {
    this.key1 = key1;
    this.key2 = key2;
    this.key3 = key3;
    this.key4 = key4;
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
    final ElementKey other = (ElementKey) obj;
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
    final ElementKey rhs = (ElementKey) o;
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
   * Compares an Integer based sub-key.
   * @param myKeyPart The first key part.
   * @param theirKeyPart The second key part.
   * @return -1, 0, or 1 based on how the two values compare using normal comparable rules.
   */
  protected static int compareSubkey(final Integer myKeyPart, final Integer theirKeyPart)
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
  protected static boolean matchedSubkey(final String myKeyPart, final String theirKeyPart)
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
   * Compares two Integer sub-keys taking into account wildcard values.
   * @param myKeyPart The first key part.
   * @param theirKeyPart The second key part.
   * @return True if they match or one is the wildcard.
   */
  protected static boolean matchedSubkey(final Integer myKeyPart, final Integer theirKeyPart)
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
  protected static boolean matchedSubkey(final UShort myKeyPart, final UShort theirKeyPart)
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
