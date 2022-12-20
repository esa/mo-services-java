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
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
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
     * The session type of the subscription.
     */
    public final SessionType sessionType;
    /**
     * The session name of the subscription.
     */
    public final Identifier sessionName;
    /**
     * The QoS level of the subscription.
     */
    public final QoSLevel qosLevel;
    /**
     * The QoS properties of the subscription.
     */
    public final Map qosProps;
    /**
     * The priority of the subscription.
     */
    public final UInteger priority;

    /**
     * Constructor.
     *
     * @param uriTo The URI of the subscriber.
     * @param transactionId The transaction id of the subscription.
     * @param sessionType The session type of the subscription.
     * @param sessionName The session name of the subscription.
     * @param qosLevel The QoS level of the subscription.
     * @param qosProps The QoS properties of the subscription.
     * @param priority The priority of the subscription.
     */
    public NotifyMessageHeader(URI uriTo, Long transactionId,
            SessionType sessionType, Identifier sessionName,
            QoSLevel qosLevel, Map qosProps, UInteger priority) {
        this.uriTo = uriTo;
        this.transactionId = transactionId;
        this.sessionType = sessionType;
        this.sessionName = sessionName;
        this.qosLevel = qosLevel;
        this.qosProps = qosProps;
        this.priority = priority;
    }

}
