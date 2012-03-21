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
  public BrokerKey(MALMessageHeader hdr)
  {
    this.uri = hdr.getURITo().getValue();
    this.session = hdr.getSession().getOrdinal();
    this.sessionName = hdr.getSessionName().getValue();
  }

  /**
   * Constructor.
   * @param hdr Source message.
   */
  public BrokerKey(MessageDetails details)
  {
    this.uri = details.uriTo.getValue();
    this.session = details.sessionType.getOrdinal();
    this.sessionName = details.sessionName.getValue();
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof BrokerKey)
    {
      BrokerKey other = (BrokerKey) obj;
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
    return HASH_MAGIC_NUMBER; // any arbitrary constant will do
  }

  @Override
  public int compareTo(Object o)
  {
    BrokerKey other = (BrokerKey) o;

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
