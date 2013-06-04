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
package org.ccsds.moims.mo.mal.transport;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The MALNotifyBody interface gives access to the body of the NOTIFY message defined by the PUBLISH-SUBSCRIBE
 * interaction.
 */
public interface MALNotifyBody extends MALPublishBody
{
  /**
   * The method returns the identifier of the subscription from this NOTIFY message.
   *
   * @return The subscription id.
   * @throws MALException If an error occurs
   */
  Identifier getSubscriptionId() throws MALException;
}
