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
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Implementation of the MALProviderManager interface.
 */
public class MALProviderManagerImpl extends MALClose implements MALProviderManager
{
  private final MALImpl impl;

  /**
   * Creates a provider manager.
   * @param impl The MAL implementation.
   */
  public MALProviderManagerImpl(MALImpl impl)
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
          int priorityLevelNumber,
          Hashtable defaultQoSProperties,
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
   * Deletes an existing provider.
   * @param protocol Protocol of the provider.
   * @param localName Local name used during creation.
   * @throws MALException on error.
   */
  @Override
  public void deleteProvider(String protocol, String localName) throws MALException
  {
  }
}
