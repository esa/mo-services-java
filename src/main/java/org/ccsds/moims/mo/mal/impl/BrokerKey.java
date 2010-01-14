package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.URI;

public class BrokerKey implements Comparable
{
  private final String brokerUri;
  private final String domain;
  private final String networkZone;
  private final int session;
  private final String sessionName;

  public BrokerKey(URI brokerUri, DomainIdentifier domain, String networkZone, SessionType session, String sessionName)
  {
    this.brokerUri = brokerUri.getValue();
    this.domain = StructureHelper.domainToString(domain);
    this.networkZone = networkZone;
    this.session = session.getOrdinal();
    this.sessionName = sessionName;
  }

  public BrokerKey(MessageHeader hdr)
  {
    this.brokerUri = hdr.getURIto().getValue();
    this.domain = StructureHelper.domainToString(hdr.getDomain());
    this.networkZone = hdr.getNetworkZone().getValue();
    this.session = hdr.getSession().getOrdinal();
    this.sessionName = hdr.getSessionName().getValue();
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof BrokerKey)
    {
      BrokerKey other = (BrokerKey) obj;
      if (brokerUri == null)
      {
        if (other.brokerUri != null)
        {
          return false;
        }
      }
      else
      {
        if (!brokerUri.equals(other.brokerUri))
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
    return 42; // any arbitrary constant will do
  }

  @Override
  public int compareTo(Object o)
  {
    BrokerKey other = (BrokerKey) o;

    if (brokerUri.equals(other.brokerUri))
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
      return brokerUri.compareTo(other.brokerUri);
    }
  }
}
