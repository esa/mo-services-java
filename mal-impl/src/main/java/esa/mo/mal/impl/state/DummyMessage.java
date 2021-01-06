/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.state;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Used when returning an internally generated error.
 */
public final class DummyMessage implements MALMessage {

    private final MALMessageHeader header;
    private final MALMessageBody body;
    private final Map qoSProperties;

    /**
     * Constructor.
     *
     * @param header Message header.
     * @param body Message body.
     * @param qoSProperties Message QoS properties.
     */
    protected DummyMessage(MALMessageHeader header, MALMessageBody body, Map qoSProperties) {
        this.header = header;
        this.body = body;
        this.qoSProperties = qoSProperties;
    }

    @Override
    public MALMessageHeader getHeader() {
        return header;
    }

    @Override
    public MALMessageBody getBody() {
        return body;
    }

    @Override
    public Map getQoSProperties() {
        return qoSProperties;
    }

    @Override
    public void free() throws MALException {
        // nothing to do
    }
}
