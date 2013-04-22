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
package esa.mo.mal.impl;

/**
 * Small comparable string pair class.
 */
public class StringPair implements Comparable
{
  /**
   * First string component, MSB.
   */
  public final String first;
  /**
   * Second string component, LSB.
   */
  public final String second;
  private static final int HASH_MAGIC_NUMBER = 79;

  /**
   * Constructor.
   * @param first First string part.
   * @param second Second string part.
   */
  public StringPair(final String first, final String second)
  {
    this.first = first;
    this.second = second;
  }

  @Override
  public int compareTo(final Object o)
  {
    final StringPair p = (StringPair) o;

    final int i = first.compareTo(p.first);
    if (0 == i)
    {
      return second.compareTo(p.second);
    }

    return i;
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof StringPair)
    {
      final StringPair p = (StringPair) obj;
      return first.equals(p.first) && second.equals(p.second);
    }

    return false;
  }

  @Override
  public int hashCode()
  {
    int hash = 3;
    hash = HASH_MAGIC_NUMBER * hash + (this.first != null ? this.first.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.second != null ? this.second.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString()
  {
    return "(" + this.first + " : " + this.second + ")";
  }
}
