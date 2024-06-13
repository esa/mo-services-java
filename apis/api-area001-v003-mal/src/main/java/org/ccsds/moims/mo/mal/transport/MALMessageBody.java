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

/**
 * The MALMessageBody interface gives a generic access to the MAL message body.
 */
public interface MALMessageBody {

    /**
     * The method returns the number of elements contained in the MAL message
     * body.
     *
     * @return The element count.
     */
    int getElementCount();

    /**
     * The method gets a body element.
     *
     * @param index Index of the element in the body, starts from 0.
     * @param element An instance of the Element to be decoded
     * @return The element, may not be the same as the parameter ‘element’.
     * @throws MALException If an error occurs
     */
    Object getBodyElement(int index, Object element) throws MALException;

    /**
     * The method gets an encoded body element.
     *
     * @param index Index of the element in the body, starts from 0, the index
     * of -1 returns the complete encoded body.
     * @return The encoded element.
     * @throws MALException If an error occurs
     */
    MALEncodedElement getEncodedBodyElement(int index) throws MALException;

}
