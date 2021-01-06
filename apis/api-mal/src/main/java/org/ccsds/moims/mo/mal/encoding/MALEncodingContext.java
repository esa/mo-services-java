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

    private MALMessageHeader header;
    private MALOperation operation;
    private int bodyElementIndex;
    private Map endpointQosProperties;
    private Map messageQosProperties;

    /**
     * Creates an instance.
     *
     * @param header The MAL message header.
     * @param operation The MAL operation
     * @param bodyElementIndex the index of the body element
     * @param endpointQosProperties the QoS properties owned by the MALEndpoint
     * that sends or receives the Element
     * @param messageQosProperties the QoS properties owned by the MALMessage
     * that contains the Element to encode or decode
     */
    public MALEncodingContext(final MALMessageHeader header,
            final MALOperation operation,
            final int bodyElementIndex,
            final Map endpointQosProperties,
            final Map messageQosProperties) {
        this.header = header;
        this.operation = operation;
        this.bodyElementIndex = bodyElementIndex;
        this.endpointQosProperties = endpointQosProperties;
        this.messageQosProperties = messageQosProperties;
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
     * Sets the header.
     *
     * @param header the header to set
     */
    public void setHeader(final MALMessageHeader header) {
        this.header = header;
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

    /**
     * Returns the endpoint QoS properties.
     *
     * @return the endpointQosProperties
     */
    public java.util.Map getEndpointQosProperties() {
        return endpointQosProperties;
    }

    /**
     * Sets the endpoint QoS properties.
     *
     * @param endpointQosProperties the endpointQosProperties to set
     */
    public void setEndpointQosProperties(final java.util.Map endpointQosProperties) {
        this.endpointQosProperties = endpointQosProperties;
    }

    /**
     * Returns the message QoS properties.
     *
     * @return the messageQosProperties
     */
    public java.util.Map getMessageQosProperties() {
        return messageQosProperties;
    }

    /**
     * Sets the message QoS properties.
     *
     * @param messageQosProperties the messageQosProperties to set
     */
    public void setMessageQosProperties(final java.util.Map messageQosProperties) {
        this.messageQosProperties = messageQosProperties;
    }
}
