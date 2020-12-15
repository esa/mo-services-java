/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.provider;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * The MALProviderManager interface encapsulates the resources used to enable MAL providers to handle interactions.
 */
public interface MALProviderManager
{
  /**
   * Creates a MAL provider.
   *
   * @param localName Name of the private MALEndpoint to be created and used by the provider, may be null.
   * @param protocol Name of the protocol used to bind the provider
   * @param service service Description of the provided service
   * @param authenticationId Authentication identifier to be used by the provider
   * @param handler Interaction handler
   * @param expectedQos QoS levels the provider can rely on
   * @param priorityLevelNumber Number of priorities the provider uses
   * @param defaultQoSProperties Default QoS properties used by the provider to send messages back to the consumer and
   * to publish updates to a shared broker, may be null.
   * @param isPublisher Specifies whether the provider is a PUBLISH-SUBSCRIBE publisher or not
   * @param sharedBrokerUri URI of the shared broker to be used
   * @return The new provider.
   * @throws java.lang.IllegalArgumentException If the parameters ‘protocol’ or or ‘service’ or ‘authenticationId’ or
   * ‘handler’ or ‘expectedQoS’ or ‘priorityLevelNumber’ or ‘isPublisher’ are NULL.
   * @throws MALException If the MALProviderManager is closed or if an internal error occurs.
   */
  MALProvider createProvider(
          String localName,
          String protocol,
          MALService service,
          Blob authenticationId,
          MALInteractionHandler handler,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map defaultQoSProperties,
          Boolean isPublisher,
          URI sharedBrokerUri)
          throws java.lang.IllegalArgumentException, MALException;

  /**
   * Creates a MAL provider.
   *
   * @param endpoint Shared MALEndpoint to be used by the provider
   * @param service service Description of the provided service
   * @param authenticationId Authentication identifier to be used by the provider
   * @param handler Interaction handler
   * @param expectedQos QoS levels the provider can rely on
   * @param priorityLevelNumber Number of priorities the provider uses
   * @param defaultQoSProperties Default QoS properties used by the provider to send messages back to the consumer and
   * to publish updates to a shared broker, may be null.
   * @param isPublisher Specifies whether the provider is a PUBLISH-SUBSCRIBE publisher or not
   * @param sharedBrokerUri URI of the shared broker to be used
   * @return The new provider.
   * @throws java.lang.IllegalArgumentException If the parameters ‘endpoint’ or or ‘service’ or ‘authenticationId’ or
   * ‘handler’ or ‘expectedQoS’ or ‘priorityLevelNumber’ or ‘isPublisher’ are NULL.
   * @throws MALException If the MALProviderManager is closed or if an internal error occurs.
   */
  MALProvider createProvider(
          MALEndpoint endpoint,
          MALService service,
          Blob authenticationId,
          MALInteractionHandler handler,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map defaultQoSProperties,
          Boolean isPublisher,
          URI sharedBrokerUri)
          throws java.lang.IllegalArgumentException, MALException;

  /**
   * The method releases the resources owned by a MALProviderManager.
   *
   * @throws MALException If an internal error occurs
   */
  void close() throws MALException;
}
