/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

/**
 *
 * @author cooper_sf
 */
public class StringPair implements Comparable
{
  public final String first;
  public final String second;

  public StringPair(String first, String second)
  {
    this.first = first;
    this.second = second;
  }

  @Override
  public int compareTo(Object o)
  {
    StringPair p = (StringPair) o;

    int i = first.compareTo(p.first);
    if (0 == i)
    {
      return second.compareTo(p.second);
    }

    return i;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof StringPair)
    {
      StringPair p = (StringPair) obj;
      return first.equals(p.first) && second.equals(p.second);
    }

    return false;
  }

  @Override
  public int hashCode()
  {
    int hash = 3;
    hash = 79 * hash + (this.first != null ? this.first.hashCode() : 0);
    hash = 79 * hash + (this.second != null ? this.second.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString()
  {
    return "(" + this.first + " : " + this.second + ")";
  }
}
