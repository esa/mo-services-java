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

import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL subscription.
 */
public final class SubscriptionKey extends ElementKey
{
  private final String domain;
  private final boolean andSubDomains;
  private final UShort area;
  private final UShort service;
  private final UShort operation;

  /**
   * Constructor.
   * @param lst Entity key.
   */
  public SubscriptionKey(MALMessageHeader hdr, EntityRequest rqst, EntityKey lst)
  {
    super(getIdValue(lst.getFirstSubKey()), lst.getSecondSubKey(), lst.getThirdSubKey(), lst.getFourthSubKey());

    String tmpDomain = "";
    boolean tmpAndSubDomains = false;

    IdentifierList mdomain = hdr.getDomain();
    IdentifierList sdomain = rqst.getSubDomain();
    if ((null != mdomain) || (null != sdomain))
    {
      StringBuilder buf = new StringBuilder();
      if ((null != mdomain) && (0 < mdomain.size()))
      {
        buf.append(StructureHelper.domainToString(mdomain));
      }

      if ((null != sdomain) && (0 < sdomain.size()))
      {
        int i = 0;
        int e = sdomain.size();
        while (i < e)
        {
          String id = String.valueOf((Identifier) sdomain.get(i));
          if (!ALL_ID.equals(id))
          {
            if (0 < buf.length())
            {
              buf.append('.');
            }

            buf.append(id);
          }
          else
          {
            tmpAndSubDomains = true;
          }

          ++i;
        }
      }

      tmpDomain = buf.toString();
    }

    this.domain = tmpDomain;
    this.andSubDomains = tmpAndSubDomains;
    this.area = getIdValueOrWildcard(hdr.getServiceArea(), rqst.getAllAreas());
    this.service = getIdValueOrWildcard(hdr.getService(), rqst.getAllServices());
    this.operation = getIdValueOrWildcard(hdr.getOperation(), rqst.getAllOperations());
  }

  /**
   * Returns true if this key matches supplied argument taking into account wildcards.
   * @param rhs Key to match against.
   * @return True if matches.
   */
  public boolean matches(UpdateKey rhs)
  {
    boolean matched = rhs.domain.startsWith(this.domain);

    if (matched)
    {
      if ((this.domain.length() < rhs.domain.length()))
      {
        matched = this.andSubDomains;
      }

      if (matched)
      {
        matched = matchedSubkey(area, rhs.area);
        if (matched)
        {
          matched = matchedSubkey(service, rhs.service);
          if (matched)
          {
            matched = matchedSubkey(operation, rhs.operation);
            if (matched)
            {
              matched = matchedSubkey(key1, rhs.key1);
              if (matched)
              {
                matched = matchedSubkey(key2, rhs.key2);
                if (matched)
                {
                  matched = matchedSubkey(key3, rhs.key3);
                  if (matched)
                  {
                    matched = matchedSubkey(key4, rhs.key4);
                  }
                }
              }
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
    StringBuilder buf = new StringBuilder();
    buf.append('[');
    buf.append(this.domain);
    if(this.andSubDomains)
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
