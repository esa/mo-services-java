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
package org.ccsds.moims.mo.mal.impl.provider;

import org.ccsds.moims.mo.mal.impl.util.MALClose;
import java.util.Hashtable;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;

/**
 * Implementation of the MALProviderManager interface.
 */
public class MALProviderManagerImpl extends MALClose implements MALProviderManager
{
  private final MALContextImpl impl;

  /**
   * Creates a provider manager.
   * @param impl The MAL implementation.
   */
  public MALProviderManagerImpl(MALContextImpl impl)
  {
    super(impl);
    this.impl = impl;
  }

  /**
   * Creates a provider.
   * @param localName Local name of the Provider.
   * @param protocol Protocol to use.
   * @param service The service it provides.
   * @param authenticationId Authentication identifier to use.
   * @param handler The handler to use.
   * @param expectedQos Supported QoS levels.
   * @param priorityLevelNumber number of priority levels supported.
   * @param defaultQoSProperties Default properties.
   * @param isPublisher True if it is a publisher.
   * @param sharedBrokerUri URI of shared broker, null if not shared.
   * @return The new Provider.
   * @throws MALException On error.
   */
  @Override
  public MALProvider createProvider(
          String localName,
          String protocol,
          MALService service,
          Blob authenticationId,
          MALInteractionHandler handler,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map defaultQoSProperties,
          Boolean isPublisher,
          URI sharedBrokerUri) throws MALException
  {
    return (MALProvider) addChild(new MALProviderImpl(this,
            impl,
            localName,
            protocol,
            service,
            authenticationId,
            handler,
            expectedQos,
            priorityLevelNumber,
            defaultQoSProperties,
            isPublisher,
            sharedBrokerUri));
  }

  /**
   *
   * @param endPoint
   * @param protocol
   * @param service
   * @param authenticationId
   * @param handler
   * @param expectedQos
   * @param priorityLevelNumber
   * @param defaultQoSProperties
   * @param isPublisher
   * @param sharedBrokerUri
   * @return
   * @throws MALException
   */
  @Override
  public MALProvider createProvider(
          MALEndPoint endPoint,
          MALService service,
          Blob authenticationId,
          MALInteractionHandler handler,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map defaultQoSProperties,
          Boolean isPublisher,
          URI sharedBrokerUri) throws MALException
  {
    return (MALProvider) addChild(new MALProviderImpl(this,
            impl,
            endPoint,
            service,
            authenticationId,
            handler,
            expectedQos,
            priorityLevelNumber,
            defaultQoSProperties,
            isPublisher,
            sharedBrokerUri));
  }
}
