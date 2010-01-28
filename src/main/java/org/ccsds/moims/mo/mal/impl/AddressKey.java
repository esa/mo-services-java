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
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Comparable URI based address.
 */
public class AddressKey implements Comparable
{
  private static final int HASH_MAGIC_NUMBER = 42;
  private final String uri;
  private final String domain;
  private final String networkZone;
  private final int session;
  private final String sessionName;

  /**
   * Constructor.
   * @param uri URI.
   * @param domain Domain.
   * @param networkZone Network zone.
   * @param session Session type.
   * @param sessionName Session name.
   */
  public AddressKey(URI uri, DomainIdentifier domain, String networkZone, SessionType session, String sessionName)
  {
    this.uri = uri.getValue();
    this.domain = StructureHelper.domainToString(domain);
    this.networkZone = networkZone;
    this.session = session.getOrdinal();
    this.sessionName = sessionName;
  }

  /**
   * Constructor.
   * @param hdr Source message.
   */
  public AddressKey(MessageHeader hdr)
  {
    this.uri = hdr.getURIto().getValue();
    this.domain = StructureHelper.domainToString(hdr.getDomain());
    this.networkZone = hdr.getNetworkZone().getValue();
    this.session = hdr.getSession().getOrdinal();
    this.sessionName = hdr.getSessionName().getValue();
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof AddressKey)
    {
      AddressKey other = (AddressKey) obj;
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
      if (domain == null)
      {
        if (other.domain != null)
        {
          return false;
        }
      }
      else
      {
        if (!domain.equals(other.domain))
        {
          return false;
        }
      }
      if (networkZone == null)
      {
        if (other.networkZone != null)
        {
          return false;
        }
      }
      else
      {
        if (!networkZone.equals(other.networkZone))
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
    AddressKey other = (AddressKey) o;

    if (uri.equals(other.uri))
    {
      if (domain.equals(other.domain))
      {
        if (networkZone.equals(other.networkZone))
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
          return networkZone.compareTo(other.networkZone);
        }
      }
      else
      {
        return domain.compareTo(other.domain);
      }
    }
    else
    {
      return uri.compareTo(other.uri);
    }
  }
}
