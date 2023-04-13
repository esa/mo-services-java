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
import org.ccsds.moims.mo.mal.structures.UpdateHeader;

/**
 * The MALPublishBody interface gives access to the body of the PUBLISH message
 * defined by the PUBLISH-SUBSCRIBE interaction.
 */
public interface MALPublishBody extends MALMessageBody {

    /**
     * The method returns the UpdateHeader from the message.
     *
     * @return The decoded UpdateHeader.
     * @throws MALException If an error occurs
     */
    UpdateHeader getUpdateHeader() throws MALException;

    /**
     * The method returns the update objects from the message.
     *
     * @return The decoded lists.
     * @throws MALException If an error occurs
     */
    Object[] getUpdateObjects() throws MALException;

    /**
     * The method returns an Update from the message.
     *
     * @param updateIndex Index of the update, starting from 0.
     * @return The decoded update.
     * @throws MALException If an error occurs
     */
    Object getUpdateObject(final int updateIndex) throws MALException;

    /**
     * The method returns an encoded Update from the message.
     *
     * @param updateIndex Index of the update, starting from 0.
     * @return The encoded update.
     * @throws MALException If the transport encoding format does support
     * separately decoding the updates or if another error occurs.
     */
    MALEncodedElement getEncodedUpdate(int updateIndex) throws MALException;
}
