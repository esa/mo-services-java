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
package esa.mo.mal.impl.broker.key;

import esa.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL subscription.
 */
public final class SubscriptionKey extends PublisherKey
{
  private final String domain;
  private final boolean andSubDomains;
  private final UShort area;
  private final UShort service;
  private final UShort operation;

  /**
   * Constructor.
   *
   * @param hdr Subscription message header.
   * @param rqst The subscription request.
   * @param key The subscription entity key.
   */
  public SubscriptionKey(final MALMessageHeader hdr, final EntityRequest rqst, final EntityKey key)
  {
    super(key);

    // Converts the domain from list form to string form.
    String tmpDomain = "";
    boolean tmpAndSubDomains = false;

    final IdentifierList mdomain = hdr.getDomain();
    final IdentifierList sdomain = rqst.getSubDomain();
    if ((null != mdomain) || (null != sdomain))
    {
      final StringBuilder buf = new StringBuilder();
      if ((null != mdomain) && (0 < mdomain.size()))
      {
        buf.append(StructureHelper.domainToString(mdomain));
      }

      if ((null != sdomain) && (0 < sdomain.size()))
      {
        for (Identifier identifier : sdomain)
        {
          final String id = identifier.getValue();
          if (ALL_ID.equals(id))
          {
            tmpAndSubDomains = true;
          }
          else
          {
            if (0 < buf.length())
            {
              buf.append('.');
            }

            buf.append(id);
          }
        }
      }

      tmpDomain = buf.toString();
    }

    this.domain = tmpDomain;
    this.andSubDomains = tmpAndSubDomains;
    this.area = rqst.getAllAreas() ? ALL_SHORT : hdr.getServiceArea();
    this.service = rqst.getAllServices() ? ALL_SHORT : hdr.getService();
    this.operation = rqst.getAllOperations() ? ALL_SHORT : hdr.getOperation();
  }

  @Override
  public int hashCode()
  {
    int hash = super.hashCode();
    hash = HASH_MAGIC_NUMBER * hash + (this.domain != null ? this.domain.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.andSubDomains ? 1 : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.area != null ? this.area.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.service != null ? this.service.hashCode() : 0);
    hash = HASH_MAGIC_NUMBER * hash + (this.operation != null ? this.operation.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final SubscriptionKey other = (SubscriptionKey) obj;
    if (!super.equals(obj))
    {
      return false;
    }
    if ((this.domain == null) ? (other.domain != null) : !this.domain.equals(other.domain))
    {
      return false;
    }
    if (this.andSubDomains != other.andSubDomains)
    {
      return false;
    }
    if (this.area != other.area && (this.area == null || !this.area.equals(other.area)))
    {
      return false;
    }
    if (this.service != other.service && (this.service == null || !this.service.equals(other.service)))
    {
      return false;
    }
    if (this.operation != other.operation && (this.operation == null || !this.operation.equals(other.operation)))
    {
      return false;
    }
    return true;
  }

  /**
   * Returns true if this key matches supplied argument taking into account wildcards.
   *
   * @param rhs Key to match against.
   * @return True if matches.
   */
  public boolean matchesWithWildcard(final UpdateKey rhs)
  {
    boolean matched = super.matchesWithWildcard(rhs);
    if (matched)
    {
      matched = rhs.getDomain().startsWith(this.domain);

      if (matched)
      {
        if ((this.domain.length() < rhs.getDomain().length()))
        {
          matched = this.andSubDomains;
        }

        if (matched)
        {
          matched = matchedSubkeyWithWildcard(area, rhs.getArea());
          if (matched)
          {
            matched = matchedSubkeyWithWildcard(service, rhs.getService());
            if (matched)
            {
              matched = matchedSubkeyWithWildcard(operation, rhs.getOperation());
            }
          }
        }
      }
    }

    return matched;
  }

  @Override
  public String toString()
  {
    final StringBuilder buf = new StringBuilder();
    buf.append('[');
    buf.append(this.domain);
    if (this.andSubDomains)
    {
      buf.append(".*");
    }
    buf.append(':');
    buf.append(this.area);
    buf.append(':');
    buf.append(this.service);
    buf.append(':');
    buf.append(this.operation);
    buf.append(':');
    buf.append(super.toString());
    buf.append(']');
    return buf.toString();
  }
}
