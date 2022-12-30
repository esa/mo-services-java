/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.regression.fastprovider.fasttransport;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 *
 */
public class FastMessage implements MALMessage {

    private final MALMessageHeader header;
    private final Map qoSProperties;
    private final FastBody body;

    public FastMessage(final MALMessageHeader header,
            final Map qosProperties,
            final Object... body) {
        this.header = header;
        this.qoSProperties = qosProperties;

        if (header.getIsErrorMessage()) {
            this.body = new FastErrorBody(body);
        } else {
            this.body = new FastBody(body);
        }
    }

    public MALMessageHeader getHeader() {
        return header;
    }

    public MALMessageBody getBody() {
        return body;
    }

    public Map getQoSProperties() {
        return qoSProperties;
    }

    public void free() throws MALException {
    }
}
