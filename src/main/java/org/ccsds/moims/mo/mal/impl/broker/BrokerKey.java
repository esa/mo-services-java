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

import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Comparable URI based address.
 */
public class BrokerKey implements Comparable
{
  private static final int HASH_MAGIC_NUMBER = 42;
  private final String uri;
  private final int session;
  private final String sessionName;

  /**
   * Constructor.
   * @param hdr Source message.
   */
  public BrokerKey(final MALMessageHeader hdr)
  {
    this.uri = hdr.getURITo().getValue();
    this.session = hdr.getSession().getOrdinal();
    this.sessionName = hdr.getSessionName().getValue();
  }

  /**
   * Constructor.
   * @param details Message details to base on.
   */
  public BrokerKey(final MessageDetails details)
  {
    this.uri = details.uriTo.getValue();
    this.session = details.sessionType.getOrdinal();
    this.sessionName = details.sessionName.getValue();
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof BrokerKey)
    {
      final BrokerKey other = (BrokerKey) obj;
      if (uri == null)
      {
        if (other.uri != null)
        {
          return false;
        }
      }
      else
      {
        if (!uri.equals(other.uri))
        {
          return false;
        }
      }
      if (session != other.session)
      {
        return false;
      }
      if (sessionName == null)
      {
        if (other.sessionName != null)
        {
          return false;
        }
      }
      else
      {
        if (!sessionName.equals(other.sessionName))
        {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    assert false : "hashCode not designed";
    return HASH_MAGIC_NUMBER;
  }

  @Override
  public int compareTo(final Object o)
  {
    final BrokerKey other = (BrokerKey) o;

    if (uri.equals(other.uri))
    {
      if (session == other.session)
      {
        if (sessionName.equals(other.sessionName))
        {
          return 0;
        }
        else
        {
          return sessionName.compareTo(other.sessionName);
        }
      }
      else
      {
        return session - other.session;
      }
    }
    else
    {
      return uri.compareTo(other.uri);
    }
  }
}
