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
package esa.mo.mal.impl.provider;

import esa.mo.mal.impl.MALContextImpl;
import esa.mo.mal.impl.util.MALClose;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Implementation of the MALProviderManager interface.
 */
public class MALProviderManagerImpl extends MALClose implements MALProviderManager
{
  private final MALContextImpl impl;

  /**
   * Creates a provider manager.
   *
   * @param impl The MAL implementation.
   */
  public MALProviderManagerImpl(final MALContextImpl impl)
  {
    super(impl);
    this.impl = impl;
  }

  @Override
  public MALProvider createProvider(
          final String localName,
          final String protocol,
          final MALService service,
          final Blob authenticationId,
          final MALInteractionHandler handler,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties,
          final Boolean isPublisher,
          final URI sharedBrokerUri) throws MALException
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

  @Override
  public MALProvider createProvider(
          final MALEndpoint endPoint,
          final MALService service,
          final Blob authenticationId,
          final MALInteractionHandler handler,
          final QoSLevel[] expectedQos,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties,
          final Boolean isPublisher,
          final URI sharedBrokerUri) throws MALException
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
