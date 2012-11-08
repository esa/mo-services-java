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
package org.ccsds.moims.mo.mal.impl.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Implements the MALConsumerManager interface.
 */
public class MALConsumerManagerImpl extends MALClose implements MALConsumerManager
{
  private final MALContextImpl impl;

  /**
   * Constructor.
   *
   * @param impl MAL implementation.
   */
  public MALConsumerManagerImpl(final MALContextImpl impl)
  {
    super(impl);

    this.impl = impl;
  }

  @Override
  public MALConsumer createConsumer(final String localName,
          final URI uriTo,
          final URI brokerUri,
          final MALService service,
          final Blob authenticationId,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel qosLevel,
          final Map qosProps,
          final UInteger priority) throws MALException
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

  @Override
  public MALConsumer createConsumer(final MALEndpoint endPoint,
          final URI uriTo,
          final URI brokerUri,
          final MALService service,
          final Blob authenticationId,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel qosLevel,
          final Map qosProps,
          final UInteger priority) throws IllegalArgumentException, MALException
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
}
