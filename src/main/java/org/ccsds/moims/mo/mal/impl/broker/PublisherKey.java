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

import org.ccsds.moims.mo.mal.structures.EntityKey;

/**
 * Simple class that represents a MAL update key.
 */
public final class PublisherKey extends ElementKey
{
  /**
   * Constructor.
   * @param key Entity key.
   */
  public PublisherKey(final EntityKey key)
  {
    super(getIdValue(key.getFirstSubKey()), key.getSecondSubKey(), key.getThirdSubKey(), key.getFourthSubKey());
  }

  /**
   * Returns true if this key matches supplied argument taking into account wildcards.
   * @param rhs Key to match against.
   * @return True if matches.
   */
  public boolean matches(final EntityKey rhs)
  {
    if (null != rhs)
    {
      boolean matched = matchedSubkey(key1, getIdValue(rhs.getFirstSubKey()));

      if (matched)
      {
        matched = matchedSubkey(key2, rhs.getSecondSubKey());
        if (matched)
        {
          matched = matchedSubkey(key3, rhs.getThirdSubKey());
          if (matched)
          {
            matched = matchedSubkey(key4, rhs.getFourthSubKey());
          }
        }
      }

      return matched;
    }

    return false;
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
}
