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

import esa.mo.mal.impl.MessageTarget;
import esa.mo.mal.impl.MessageSend;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Implementation of the MALPublisher interface.
 */
public class MALPublisherImpl implements MALPublisher {

    /**
     * Logger
     */
    public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.impl.provider");
    private final Map<String, Long> transIdMap = new HashMap<>();
    private final MALProviderImpl provider;
    private final MessageSend handler;
    private final MALPubSubOperation operation;
    private final Map remotePublisherQosProps;

    public MALPublisherImpl(final MALProviderImpl provider, final MessageSend handler,
            final MALPubSubOperation operation, final Map remotePublisherQosProps) {
        this.provider = provider;
        this.handler = handler;
        this.operation = operation;
        this.remotePublisherQosProps = remotePublisherQosProps;
    }

    @Override
    public void close() throws MALException {
        // nothing to do here
    }

    @Override
    public MALProvider getProvider() {
        return provider;
    }

    @Override
    public void register(final IdentifierList keyNames, final AttributeTypeList keyTypes,
            final MALPublishInteractionListener listener)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageTarget messageTarget = new MessageTarget(
                provider.getEndpoint(),
                null,
                provider.getBrokerURI(),
                provider.getAuthenticationId(),
                remotePublisherQosProps);

        Long transactionId = handler.publishRegister(provider.getURI().getValue(), messageTarget, operation, keyNames, keyTypes, listener);
        this.putTransId(provider.getBrokerURI(), transactionId);
    }

    @Override
    public MALMessage asyncRegister(final IdentifierList keyNames,
            final AttributeTypeList keyTypes, final MALPublishInteractionListener listener)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageTarget messageTarget = new MessageTarget(
                provider.getEndpoint(),
                null,
                provider.getBrokerURI(),
                provider.getAuthenticationId(),
                remotePublisherQosProps);

        MALMessage msg = handler.publishRegisterAsync(provider.getURI().getValue(), messageTarget, operation, keyNames, keyTypes, listener);
        this.putTransId(provider.getBrokerURI(), msg.getHeader().getTransactionId());
        return msg;
    }

    @Override
    public MALMessage publish(final UpdateHeader updateHeader, final Object... updateValues)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageTarget messageTarget = new MessageTarget(
                provider.getEndpoint(),
                null,
                provider.getBrokerURI(),
                provider.getAuthenticationId(),
                remotePublisherQosProps);

        final Long tid = this.getTransId(provider.getBrokerURI());

        if (tid == null) {
            // this means that we haven't successfully registered, need to throw an exception
            throw new MALInteractionException(new MOErrorException(
                    MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));

        }

        LOGGER.log(Level.FINE, "Publisher using transaction Id of: {0}", tid);

        final Object[] body = new Object[updateValues.length + 1];
        body[0] = updateHeader;
        System.arraycopy(updateValues, 0, body, 1, updateValues.length);

        return handler.onewayInteraction(messageTarget, tid, operation,
                MALPubSubOperation.PUBLISH_STAGE, body);
    }

    @Override
    public void deregister() throws MALInteractionException, MALException {
        final MessageTarget messageTarget = new MessageTarget(
                provider.getEndpoint(),
                null,
                provider.getBrokerURI(),
                provider.getAuthenticationId(),
                remotePublisherQosProps);

        handler.publishDeregister(messageTarget, operation);
        this.removeTransId(provider.getBrokerURI());
    }

    @Override
    public MALMessage asyncDeregister(final MALPublishInteractionListener listener)
            throws IllegalArgumentException, MALInteractionException, MALException {
        final MessageTarget messageTarget = new MessageTarget(
                provider.getEndpoint(),
                null,
                provider.getBrokerURI(),
                provider.getAuthenticationId(),
                remotePublisherQosProps);

        final MALMessage msg = handler.publishDeregisterAsync(messageTarget, operation, listener);
        this.removeTransId(provider.getBrokerURI());
        return msg;
    }

    private synchronized Long getTransId(final URI lbrokerUri) {
        return transIdMap.get(lbrokerUri.getValue());
    }

    private synchronized void putTransId(final URI lbrokerUri, final Long transactionId) {
        if (!transIdMap.containsKey(lbrokerUri.getValue())) {
            LOGGER.log(Level.FINE, "Publisher setting transaction Id to: {0}", transactionId);
            transIdMap.put(lbrokerUri.getValue(), transactionId);
        }
    }

    private synchronized void removeTransId(final URI lbrokerUri) {
        final Long id = transIdMap.get(lbrokerUri.getValue());

        if (id != null) {
            LOGGER.log(Level.FINE, "Publisher removing transaction Id of: {0}", id);
            transIdMap.remove(lbrokerUri.getValue());
        }
    }
}
