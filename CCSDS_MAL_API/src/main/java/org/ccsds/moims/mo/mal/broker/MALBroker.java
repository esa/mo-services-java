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
package org.ccsds.moims.mo.mal.broker;

import org.ccsds.moims.mo.mal.MALException;

/**
 * The MALBroker interface represents a shared MAL level broker, i.e., a shared broker implemented at the MAL level.
 * NOTE – A single MALBroker instance can be bound to one or several transport layers. In this way, the MALBroker can
 * act as a bridge between several transport layers. For instance, a MALBroker can bridge two transport layers by
 * receiving updates from publishers through the first transport layer and notifying those updates to subscribers
 * through the second transport layer.
 *
 */
public interface MALBroker
{
  /**
   * Return the MALBrokerBindings owned by this MALBroker.
   * @return the MALBrokerBindings;
   */
  MALBrokerBinding[] getBindings();

  /**
   * The method terminates all pending interactions.
   * @throws MALException If an internal error occurs.
   */
  void close() throws MALException;
}
