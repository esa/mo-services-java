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
package org.ccsds.moims.mo.mal.impl.consumer;

import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Implements the MALConsumerManager interface.
 */
public class MALConsumerManagerImpl extends MALClose implements MALConsumerManager
{
  private final MALImpl impl;

  /**
   * Constructor.
   * @param impl MAL implemenation.
   */
  public MALConsumerManagerImpl(MALImpl impl)
  {
    super(impl);

    this.impl = impl;
  }

  @Override
  /**
   *
   * @param uriTo
   * @param brokerUri
   * @param service
   * @param authenticationId
   * @param domain
   * @param networkZone
   * @param sessionType
   * @param sessionName
   * @param qosLevel
   * @param qosProps
   * @param priority
   * @return
   * @throws MALException
   */
  public MALConsumer createConsumer(URI uriTo,
          URI brokerUri,
          MALService service,
          Blob authenticationId,
          DomainIdentifier domain,
          Identifier networkZone,
          SessionType sessionType,
          Identifier sessionName,
          QoSLevel qosLevel,
          Hashtable qosProps,
          Integer priority) throws MALException
  {
    return (MALConsumer) addChild(new MALConsumerImpl(impl,
            this,
            uriTo,
            brokerUri,
            service,
            authenticationId,
            domain,
            networkZone,
            sessionType,
            sessionName,
            qosLevel,
            qosProps,
            priority));
  }
}
