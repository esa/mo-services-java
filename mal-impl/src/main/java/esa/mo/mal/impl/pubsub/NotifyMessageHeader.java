/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
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
package esa.mo.mal.impl.pubsub;

import java.util.Map;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * Simple structure style class that holds a single notify message header.
 *
 * @author Cesar.Coelho
 */
public class NotifyMessageHeader {

    /**
     * The URI of the subscriber.
     */
    private final Identifier uriTo;
    /**
     * The transaction id of the subscription.
     */
    private final Long transactionId;
    /**
     * The QoS properties of the subscription.
     */
    private final Map qosProps;

    /**
     * Constructor.
     *
     * @param uriTo The URI of the subscriber.
     * @param transactionId The transaction id of the subscription.
     * @param qosProps The QoS properties of the subscription.
     */
    public NotifyMessageHeader(Identifier uriTo, Long transactionId, Map qosProps) {
        this.uriTo = uriTo;
        this.transactionId = transactionId;
        this.qosProps = qosProps;
    }

    /**
     * Returns the URI To.
     *
     * @return The URI To.
     */
    public Identifier getUriTo() {
        return uriTo;
    }

    /**
     * Returns the transactionId.
     *
     * @return The transactionId.
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the QoS properties.
     *
     * @return The QoS properties.
     */
    public Map getQosProps() {
        return qosProps;
    }
}
