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

import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

final class ProviderDetails
{
  private final String uri;
  private final QoSLevel qosLevel;
  private final Set<PublisherKey> keySet = new TreeSet<PublisherKey>();
  private IdentifierList domain = null;

  ProviderDetails(String uri, QoSLevel qosLevel)
  {
    super();
    this.uri = uri;
    this.qosLevel = qosLevel;
  }

  QoSLevel getQosLevel()
  {
    return qosLevel;
  }

  void report()
  {
    Logging.logMessage("  START Provider ( " + uri + " )");
    Logging.logMessage("    Domain : " + StructureHelper.domainToString(domain));
    for (PublisherKey key : keySet)
    {
      Logging.logMessage("    Allowed: " + key);
    }
    Logging.logMessage("  END Provider ( " + uri + " )");
  }

  void setKeyList(MALMessageHeader hdr, EntityKeyList l)
  {
    domain = hdr.getDomain();
    keySet.clear();
    for (int i = 0; i < l.size(); i++)
    {
      keySet.add(new PublisherKey(l.get(i)));
    }
  }

  void checkPublish(MALMessageHeader hdr, UpdateHeaderList updateList) throws MALInteractionException
  {
    if (StructureHelper.isSubDomainOf(domain, hdr.getDomain()))
    {
      EntityKeyList lst = new EntityKeyList();
      for (int i = 0; i < updateList.size(); i++)
      {
        UpdateHeader update = (UpdateHeader) updateList.get(i);
        EntityKey updateKey = update.getKey();
        boolean matched = false;
        for (PublisherKey key : keySet)
        {
          if (key.matches(updateKey))
          {
            matched = true;
            break;
          }
        }
        if (!matched)
        {
          lst.add(updateKey);
        }
      }
      if (0 < lst.size())
      {
        Logging.logMessage("ERR : Provider not allowed to publish some keys");
        throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, lst));
      }
    }
    else
    {
      Logging.logMessage("ERR : Provider not allowed to publish to the domain");
      throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, null));
    }
  }
}
