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
package esa.mo.mal.impl.provider;

import esa.mo.mal.impl.MessageDetails;
import esa.mo.mal.impl.MessageSend;
import esa.mo.mal.impl.util.StructureHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Implementation of the MALPublisher interface.
 */
class MALPublisherImpl implements MALPublisher {

    /**
     * Logger
     */
    public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.impl.provider");
    private final Map<AddressKey, Long> transIdMap = new HashMap<>();
    private final MALProviderImpl parent;
    private final MessageSend handler;
    private final MALPubSubOperation operation;
    private final IdentifierList domain;
    private final Identifier networkZone;
    private final SessionType sessionType;
    private final Identifier sessionName;
    private final QoSLevel remotePublisherQos;
    private final Map remotePublisherQosProps;
    private final UInteger remotePublisherPriority;

    MALPublisherImpl(final MALProviderImpl parent,
            final MessageSend handler,
            final MALPubSubOperation operation,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel remotePublisherQos,
            final Map remotePublisherQosProps,
            final UInteger remotePublisherPriority) {
        this.parent = parent;
        this.handler = handler;
        this.operation = operation;
        this.domain = domain;
        this.networkZone = networkZone;
        this.sessionType = sessionType;
        this.sessionName = sessionName;
        this.remotePublisherQos = remotePublisherQos;
        this.remotePublisherQosProps = remotePublisherQosProps;
        this.remotePublisherPriority = remotePublisherPriority;
    }

    @Override
    public void close() throws MALException {
        // nothing to do here
    }

    @Override
    public MALProvider getProvider() {
        return parent;
    }

    @Override
    public void register(final IdentifierList keys, final MALPublishInteractionListener listener)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageDetails details = new MessageDetails(
                parent.getEndpoint(),
                parent.getURI(),
                null,
                parent.getBrokerURI(),
                parent.getAuthenticationId(),
                remotePublisherQosProps);

        setTransId(parent.getBrokerURI(),
                domain,
                handler.publishRegister(details, operation, keys, listener));
    }

    @Override
    public MALMessage asyncRegister(final IdentifierList keys,
            final MALPublishInteractionListener listener)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageDetails details = new MessageDetails(
                parent.getEndpoint(),
                parent.getURI(),
                null,
                parent.getBrokerURI(),
                parent.getAuthenticationId(),
                remotePublisherQosProps);

        final MALMessage msg = handler.publishRegisterAsync(details,
                operation, keys, listener);

        setTransId(parent.getBrokerURI(), domain, msg.getHeader().getTransactionId());

        return msg;
    }

    @Override
    public MALMessage publish(final UpdateHeader updateHeader, final Object... updateValues)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageDetails details = new MessageDetails(
                parent.getEndpoint(),
                parent.getURI(),
                null,
                parent.getBrokerURI(),
                parent.getAuthenticationId(),
                remotePublisherQosProps);

        final Long tid = getTransId(parent.getBrokerURI(), domain);

        if (tid != null) {
            LOGGER.log(Level.FINE, "Publisher using transaction Id of: {0}", tid);

            final Object[] body = new Object[updateValues.length + 1];
            body[0] = updateHeader;
            System.arraycopy(updateValues, 0, body, 1, updateValues.length);

            return handler.onewayInteraction(details, tid, operation,
                    MALPubSubOperation.PUBLISH_STAGE, body);
        } else {
            // this means that we haven't successfully registered, need to throw an exception
            throw new MALInteractionException(new MOErrorException(
                    MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
        }
    }

    @Override
    public void deregister() throws MALInteractionException, MALException {
        final MessageDetails details = new MessageDetails(
                parent.getEndpoint(),
                parent.getURI(),
                null,
                parent.getBrokerURI(),
                parent.getAuthenticationId(),
                remotePublisherQosProps);

        handler.publishDeregister(details, operation);
        clearTransId(parent.getBrokerURI(), domain);
    }

    @Override
    public MALMessage asyncDeregister(final MALPublishInteractionListener listener)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageDetails details = new MessageDetails(
                parent.getEndpoint(),
                parent.getURI(),
                null,
                parent.getBrokerURI(),
                parent.getAuthenticationId(),
                remotePublisherQosProps);

        final MALMessage msg = handler.publishDeregisterAsync(details, operation, listener);
        clearTransId(parent.getBrokerURI(), domain);
        return msg;
    }

    private synchronized void setTransId(final URI lbrokerUri, final IdentifierList ldomain, final Long lid) {
        final AddressKey key = new AddressKey(lbrokerUri, ldomain);

        if (!transIdMap.containsKey(key)) {
            LOGGER.log(Level.FINE, "Publisher setting transaction Id to: {0}", lid);
            transIdMap.put(key, lid);
        }
    }

    private synchronized void clearTransId(final URI lbrokerUri, final IdentifierList ldomain) {
        final AddressKey key = new AddressKey(lbrokerUri, ldomain);
        final Long id = transIdMap.get(key);

        if (id != null) {
            LOGGER.log(Level.FINE, "Publisher removing transaction Id of: {0}", id);
            transIdMap.remove(key);
        }
    }

    private synchronized Long getTransId(final URI lbrokerUri, final IdentifierList ldomain) {
        return transIdMap.get(new AddressKey(lbrokerUri, ldomain));
    }

    private static class AddressKey implements Comparable {

        private final String uri;
        private final IdentifierList domain;

        /**
         * Constructor.
         *
         * @param uri URI.
         * @param domain Domain.
         * @param networkZone Network zone.
         * @param session Session type.
         * @param sessionName Session name.
         */
        public AddressKey(final URI uri, final IdentifierList domain) {
            this.uri = uri.getValue();
            this.domain = domain;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof AddressKey) {
                final AddressKey other = (AddressKey) obj;
                if (uri == null) {
                    if (other.uri != null) {
                        return false;
                    }
                } else {
                    if (!uri.equals(other.uri)) {
                        return false;
                    }
                }
                if (domain == null) {
                    if (other.domain != null) {
                        return false;
                    }
                } else {
                    if (!domain.equals(other.domain)) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + (this.uri != null ? this.uri.hashCode() : 0);
            hash = 53 * hash + (this.domain != null ? this.domain.hashCode() : 0);
            return hash;
        }

        @Override
        public int compareTo(final Object o) {
            final AddressKey other = (AddressKey) o;

            if (uri.equals(other.uri)) {
                return (StructureHelper.domainToString(domain))
                        .compareTo(StructureHelper.domainToString(other.domain));
            } else {
                return uri.compareTo(other.uri);
            }
        }
    }
}
