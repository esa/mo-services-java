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
package org.ccsds.moims.mo.mal.encoding;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The class MALEncodingContext gives access to: the header of the MALMessage
 * that contains the Element to encode or decode; the description of the
 * operation that has been called; the index of the body element to encode or
 * decode; the list of the QoS properties owned by the MALEndpoint that sends or
 * receives the Element; the list of the QoS properties owned by the MALMessage
 * that contains the Element to encode or decode.
 *
 */
public class MALEncodingContext {

    private final MALMessageHeader header;
    private MALOperation operation;
    private int bodyElementIndex;

    /**
     * Creates an instance.
     *
     * @param header The MAL message header.
     * @param operation The MAL operation
     * @param bodyElementIndex the index of the body element
     */
    public MALEncodingContext(final MALMessageHeader header,
            final MALOperation operation,
            final int bodyElementIndex) {
        this.header = header;
        this.operation = operation;
        this.bodyElementIndex = bodyElementIndex;
    }

    /**
     * Returns the header.
     *
     * @return the header
     */
    public MALMessageHeader getHeader() {
        return header;
    }

    /**
     * Returns the operation.
     *
     * @return the operation
     */
    public MALOperation getOperation() {
        return operation;
    }

    /**
     * Sets the operation.
     *
     * @param operation the service to set
     */
    public void setOperation(final MALOperation operation) {
        this.operation = operation;
    }

    /**
     * Returns the index.
     *
     * @return The index.
     */
    public int getBodyElementIndex() {
        return bodyElementIndex;
    }

    /**
     * Sets the index.
     *
     * @param bodyElementIndex The index to set.
     */
    public void setBodyElementIndex(final int bodyElementIndex) {
        this.bodyElementIndex = bodyElementIndex;
    }

}
