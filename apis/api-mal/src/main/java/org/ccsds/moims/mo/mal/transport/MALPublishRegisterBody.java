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
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;

/**
 * The MALPublishRegisterBody interface gives access to the body of the PUBLISH
 * REGISTER message defined by the IP PUBLISH-SUBSCRIBE.
 */
public interface MALPublishRegisterBody extends MALMessageBody {

    /**
     * The method returns the Subscription Key Names from the PUBLISH_REGISTER
     * message.
     *
     * @return The decoded key names.
     * @throws MALException If an error occurs
     */
    IdentifierList getSubscriptionKeyNames() throws MALException;

    /**
     * The method returns the Subscription Key Types from the PUBLISH_REGISTER
     * message.
     *
     * @return The decoded key types.
     * @throws MALException If an error occurs
     */
    AttributeTypeList getSubscriptionKeyTypes() throws MALException;
}
