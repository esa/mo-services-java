/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package esa.mo.mal.impl.broker;

import java.util.List;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

/**
 * Base class for subscription sources.
 */
public abstract class SubscriptionSource {

    private final NotifyMessageSet.MessageHeaderDetails msgHeaderDetails;
    private int commsErrorCount = 0;

    /**
     * Constructor.
     *
     * @param hdr Source message.
     * @param uriTo The URI to send updates too.
     */
    public SubscriptionSource(final MALMessageHeader hdr, final URI uriTo) {
        msgHeaderDetails = new NotifyMessageSet.MessageHeaderDetails(uriTo,
                hdr.getTransactionId(),
                hdr.getSession(),
                hdr.getSessionName(),
                hdr.getQoSlevel(),
                null,
                hdr.getPriority());
    }

    /**
     * Returns the message header details for this subscription source.
     *
     * @return the msgHeaderDetails
     */
    public NotifyMessageSet.MessageHeaderDetails getMsgHeaderDetails() {
        return msgHeaderDetails;
    }

    /**
     * Increments the count of communication errors.
     */
    public void incCommsErrorCount() {
        ++commsErrorCount;
    }

    /**
     * Returns the current communications error count.
     *
     * @return the error count.
     */
    public int getCommsErrorCount() {
        return commsErrorCount;
    }

    /**
     * Resets the count of communication errors.
     */
    public void resetCommsErrorCount() {
        commsErrorCount = 0;
    }

    /**
     * Returns the signature for this source.
     *
     * @return signature.
     */
    public abstract String getSignature();

    /**
     * Determines if this source is active.
     *
     * @return true if this source is active.
     */
    public abstract boolean active();

    /**
     * Debugging report.
     */
    public abstract void report();

    /**
     * Adds a subscription to this source.
     *
     * @param srcHdr Source message.
     * @param subscription New subscription.
     */
    public abstract void addSubscription(final MALMessageHeader srcHdr, 
            final Subscription subscription);

    /**
     * Adds messages to the list of notify messages to be sent out.
     *
     * @param srcHdr Source publish message.
     * @param updateHeaderList The update header list.
     * @param publishBody The publish message body.
     * @param keyNames The provider key list.
     * @return NotifyMessageSet The set of NotifyMessages.
     * @throws MALException On error.
     */
    public abstract NotifyMessageSet  populateNotifyList(final MALMessageHeader srcHdr,
            final UpdateHeaderList updateHeaderList,
            final MALPublishBody publishBody,
            final IdentifierList keyNames) throws MALException;

    /**
     * Removes a subscription.
     *
     * @param subscriptions List of subscription identifiers to remove.
     */
    public abstract void removeSubscriptions(final IdentifierList subscriptions);
}
