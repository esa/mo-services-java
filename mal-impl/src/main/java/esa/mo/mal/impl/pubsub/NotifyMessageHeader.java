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
import org.ccsds.moims.mo.mal.structures.URI;

/**
 *
 * @author Cesar.Coelho
 */
public class NotifyMessageHeader {

    /**
     * The URI of the subscriber.
     */
    public final URI uriTo;
    /**
     * The transaction id of the subscription.
     */
    public final Long transactionId;
    /**
     * The QoS properties of the subscription.
     */
    public final Map qosProps;

    /**
     * Constructor.
     *
     * @param uriTo The URI of the subscriber.
     * @param transactionId The transaction id of the subscription.
     * @param qosProps The QoS properties of the subscription.
     */
    public NotifyMessageHeader(URI uriTo, Long transactionId, Map qosProps) {
        this.uriTo = uriTo;
        this.transactionId = transactionId;
        this.qosProps = qosProps;
    }
}
