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

import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.impl.broker.key.PublisherKey;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Represents a publisher (provider) in a broker, so contains the list of entities it is allowed to publish.
 */
public final class PublisherSource
{
  private final String uri;
  private final QoSLevel qosLevel;
  private final Set<PublisherKey> keySet = new TreeSet<PublisherKey>();
  private IdentifierList domain = null;

  PublisherSource(final String uri, final QoSLevel qosLevel)
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
    MALBrokerImpl.LOGGER.log(Level.FINE, "  START Provider ( {0} )", uri);
    MALBrokerImpl.LOGGER.log(Level.FINE, "    Domain : {0}", StructureHelper.domainToString(domain));
    for (PublisherKey key : keySet)
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "    Allowed: {0}", key);
    }
    MALBrokerImpl.LOGGER.log(Level.FINE, "  END Provider ( {0} )", uri);
  }

  void setKeyList(final MALMessageHeader hdr, final EntityKeyList l)
  {
    domain = hdr.getDomain();
    keySet.clear();
    for (EntityKey entityKey : l)
    {
      keySet.add(new PublisherKey(entityKey));
    }
  }

  void checkPublish(final MALMessageHeader hdr, final UpdateHeaderList updateList) throws MALInteractionException
  {
    if (StructureHelper.isSubDomainOf(domain, hdr.getDomain()))
    {
      final EntityKeyList lst = new EntityKeyList();
      for (final UpdateHeader update : updateList)
      {
        final EntityKey updateKey = update.getKey();
        boolean matched = false;
        for (PublisherKey key : keySet)
        {
          if (key.matchesWithWildcard(updateKey))
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
        MALBrokerImpl.LOGGER.warning("Provider not allowed to publish some keys");
        throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, lst));
      }
    }
    else
    {
      MALBrokerImpl.LOGGER.warning("Provider not allowed to publish to the domain");
      throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, null));
    }
  }
}
