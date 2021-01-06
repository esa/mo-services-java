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
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The MALInteraction interface represents a generic IP handling context.
 */
public interface MALInteraction {

    /**
     * The method returns the header of the message that initiated the
     * interaction.
     *
     * @return The message header.
     */
    MALMessageHeader getMessageHeader();

    /**
     * The method returns the operation called through this interaction.
     *
     * @return The operation.
     */
    MALOperation getOperation();

    /**
     * Sets a QoS property.
     *
     * @param name The property name.
     * @param value The property value.
     * @throws java.lang.IllegalArgumentException If the name argument is NULL
     */
    void setQoSProperty(String name, Object value) throws java.lang.IllegalArgumentException;

    /**
     * Gets a QoS property.
     *
     * @param name The property name.
     * @return The property value.
     * @throws java.lang.IllegalArgumentException If the name argument is NULL
     */
    Object getQoSProperty(String name) throws java.lang.IllegalArgumentException;

    /**
     * Gets the complete map of QoS properties.
     *
     * @return The property value map.
     */
    Map<String, Object> getQoSProperties();
}
