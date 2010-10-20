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
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;

final class ProviderDetails
{
  private final String uri;
  private final QoSLevel qosLevel;
  private final Set<PublisherKey> keySet = new TreeSet<PublisherKey>();
  private DomainIdentifier domain = null;

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
    Logging.logMessage("    Domain: " + StructureHelper.domainToString(domain));
    for (PublisherKey key : keySet)
    {
      Logging.logMessage("   Allowed: " + key);
    }
    Logging.logMessage("  END Provider ( " + uri + " )");
  }

  void setKeyList(MessageHeader hdr, EntityKeyList l)
  {
    domain = hdr.getDomain();
    keySet.clear();
    for (int i = 0; i < l.size(); i++)
    {
      keySet.add(new PublisherKey(l.get(i)));
    }
  }

  void checkPublish(MessageHeader hdr, UpdateList updateList) throws MALException
  {
    if (StructureHelper.isSubDomainOf(domain, hdr.getDomain()))
    {
      EntityKeyList lst = new EntityKeyList();
      for (int i = 0; i < updateList.size(); i++)
      {
        Update update = (Update) updateList.get(i);
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
        throw new MALException(new StandardError(MALHelper.UNKNOWN_ERROR_NUMBER, lst));
      }
    }
    else
    {
      Logging.logMessage("ERR : Provider not allowed to publish to the domain");
      throw new MALException(new StandardError(MALHelper.UNKNOWN_ERROR_NUMBER, null));
    }
  }
}
