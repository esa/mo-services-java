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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;

/**
 * Implements the MALConsumerManager interface.
 */
public class MALConsumerManagerImpl extends MALClose implements MALConsumerManager
{
  private final MALContextImpl impl;

  /**
   * Constructor.
   * @param impl MAL implemenation.
   */
  public MALConsumerManagerImpl(MALContextImpl impl)
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
  public MALConsumer createConsumer(
          String localName,
          URI uriTo,
          URI brokerUri,
          MALService service,
          Blob authenticationId,
          IdentifierList domain,
          Identifier networkZone,
          SessionType sessionType,
          Identifier sessionName,
          QoSLevel qosLevel,
          Map qosProps,
          UInteger priority) throws MALException
  {
    return (MALConsumer) addChild(new MALConsumerImpl(impl,
            this,
            localName,
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

  public MALConsumer createConsumer(MALEndPoint endPoint, URI uriTo, URI brokerUri, MALService service, Blob authenticationId, IdentifierList domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel qosLevel, Map qosProps, UInteger priority) throws IllegalArgumentException, MALException
  {
    return (MALConsumer) addChild(new MALConsumerImpl(impl,
            this,
            endPoint,
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

  public void deleteConsumer(String localName) throws MALException
  {
  }
}
