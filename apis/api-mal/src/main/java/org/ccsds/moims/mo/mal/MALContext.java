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
package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Primary interface for the creation of managers. Created via the MALContextFactory class.
 */
public interface MALContext
{
  /**
   * Creates a new consumer manager.
   * @return The new consumer manager.
   * @throws MALException Throws exception if cannot create manager.
   */
  MALConsumerManager createConsumerManager() throws MALException;

  /**
   * Creates a new provider manager.
   * @return The new provider manager.
   * @throws MALException Throws exception if cannot create manager.
   */
  MALProviderManager createProviderManager() throws MALException;

  /**
   * Creates a new broker manager.
   * @return The new broker manager.
   * @throws MALException Throws exception if cannot create manager.
   */
  MALBrokerManager createBrokerManager() throws MALException;

  /**
   * Returns a transport for the requested protocol if supported.
   * @param uri The URI to obtain the transport for.
   * @return The protocol transport, null if not supported.
   * @throws IllegalArgumentException If argument is null.
   * @throws MALException Throws exception if error detected during initialisation of the transport.
   */
  MALTransport getTransport(URI uri) throws IllegalArgumentException, MALException;

  /**
   * Returns a transport for the requested protocol if supported.
   * @param protocol The protocol to obtain the transport for.
   * @return The protocol transport, null if not supported.
   * @throws IllegalArgumentException If argument is null.
   * @throws MALException Throws exception if error detected during initialisation of the transport.
   */
  MALTransport getTransport(String protocol) throws IllegalArgumentException, MALException;

  /**
   * Returns the access control component used by this MAL context.
   * @return The access control component.
   * @throws MALException Throws exception if error detected.
   */
  MALAccessControl getAccessControl() throws MALException;

  /**
   * Closes the MALContext instance and all children managers.
   * @throws MALException Throws exception if cannot close.
   */
  void close() throws MALException;
}
