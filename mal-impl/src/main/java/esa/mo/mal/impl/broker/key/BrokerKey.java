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

import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL level broker key. Brokers are separated on the URI of the broker and the session.
 * This allows a broker to host several contexts separated by session.
 */
public class BrokerKey implements Comparable
{
  private static final int HASH_MAGIC_NUMBER = 79;
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
   * @param uri Broker URI
   * @param session Broker session enumeration ordinal
   * @param sessionName Broker session name
   */
  public BrokerKey(String uri, int session, String sessionName)
  {
    this.uri = uri;
    this.session = session;
    this.sessionName = sessionName;
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
    int hash = 3;
    hash = HASH_MAGIC_NUMBER * hash + (this.uri != null ? this.uri.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + this.session;
    hash = HASH_MAGIC_NUMBER * hash + (this.sessionName != null ? this.sessionName.hashCode() : 0);
    return hash;
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
