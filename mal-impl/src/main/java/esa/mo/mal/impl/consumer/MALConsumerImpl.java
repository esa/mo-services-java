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
package esa.mo.mal.impl.consumer;

import esa.mo.mal.impl.MALContextImpl;
import esa.mo.mal.impl.MessageTarget;
import esa.mo.mal.impl.MALSender;
import esa.mo.mal.impl.transport.TransportSingleton;
import esa.mo.mal.impl.util.MALCloseable;
import java.util.Map;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorListener;

/**
 * Implementation of the MALConsumer interface. Delegates most of the work to
 * the MessageSend class.
 */
public class MALConsumerImpl implements MALConsumer, MALCloseable {

    private final MALSender sender;
    private final MessageTarget messageTarget;
    private MALTransmitErrorListener transmissionListener;

    MALConsumerImpl(final MALContextImpl impl,
            final String localName,
            final URI uriTo,
            final URI brokerUri,
            final MALService service,
            final Blob authenticationId,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel qosLevel,
            final Map qosProps,
            final UInteger priority,
            NamedValueList supplements) throws MALException {
        if (domain == null) {
            throw new IllegalArgumentException("Domain argument must not be null");
        }

        this.sender = impl.getSendingInterface();

        MALEndpoint ep = TransportSingleton.instance(uriTo, impl.getInitialProperties())
                .createEndpoint(localName, qosProps, supplements);

        if (ep == null) {
            // transport was unable to create the end point for us, need to throw an exception
            throw new MALException("Unable to create transport endpoint for URI: " + uriTo);
        }

        ep.setMessageListener(impl.getReceivingInterface());

        this.messageTarget = new MessageTarget(ep,
                uriTo,
                brokerUri,
                authenticationId,
                qosProps);

        ep.startMessageDelivery();
    }

    MALConsumerImpl(final MALContextImpl impl,
            final MALEndpoint endPoint,
            final URI uriTo,
            final URI brokerUri,
            final MALService service,
            final Blob authenticationId,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel qosLevel,
            final Map qosProps,
            final UInteger priority) throws MALException {
        if (domain == null) {
            throw new IllegalArgumentException("Domain argument must not be null");
        }

        this.sender = impl.getSendingInterface();

        endPoint.setMessageListener(impl.getReceivingInterface());

        this.messageTarget = new MessageTarget(endPoint,
                uriTo,
                brokerUri,
                authenticationId,
                qosProps);
    }

    @Override
    public URI getURI() {
        return messageTarget.getEndpoint().getURI();
    }

    @Override
    public Blob getAuthenticationId() {
        return messageTarget.getAuthenticationId();
    }

    @Override
    public Blob setAuthenticationId(Blob newAuthenticationId) {
        Blob previous = messageTarget.getAuthenticationId();
        messageTarget.setAuthenticationId(newAuthenticationId);
        return previous;
    }

    @Override
    public MALMessage send(final MALSendOperation op, final Object... requestBody)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        return sender.onewayInteraction(messageTarget, null, op,
                MALSendOperation.SEND_STAGE, requestBody);
    }

    @Override
    public MALMessage send(final MALSendOperation op, final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.onewayInteraction(messageTarget, null, op,
                MALSendOperation.SEND_STAGE, body);
    }

    @Override
    public void submit(final MALSubmitOperation op, final Object... requestBody)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        sender.synchronousInteraction(messageTarget,
                op,
                MALSubmitOperation.SUBMIT_STAGE,
                (MALInteractionListener) null,
                requestBody);
    }

    @Override
    public void submit(final MALSubmitOperation op, final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        sender.synchronousInteraction(messageTarget,
                op,
                MALSubmitOperation.SUBMIT_STAGE,
                (MALInteractionListener) null,
                body);
    }

    @Override
    public MALMessageBody request(final MALRequestOperation op, final Object... requestBody)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.synchronousInteraction(messageTarget,
                op,
                MALRequestOperation.REQUEST_STAGE,
                (MALInteractionListener) null,
                requestBody);
    }

    @Override
    public MALMessageBody request(final MALRequestOperation op, final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.synchronousInteraction(messageTarget,
                op,
                MALRequestOperation.REQUEST_STAGE,
                (MALInteractionListener) null,
                body);
    }

    @Override
    public MALMessageBody invoke(final MALInvokeOperation op,
            final MALInteractionListener listener,
            final Object... requestBody)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.synchronousInteraction(messageTarget, op,
                MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
    }

    @Override
    public MALMessageBody invoke(final MALInvokeOperation op,
            final MALInteractionListener listener,
            final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.synchronousInteraction(messageTarget, op,
                MALInvokeOperation.INVOKE_STAGE, listener, body);
    }

    @Override
    public MALMessageBody progress(final MALProgressOperation op,
            final MALInteractionListener listener,
            final Object... requestBody)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.synchronousInteraction(messageTarget, op,
                MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
    }

    @Override
    public MALMessageBody progress(final MALProgressOperation op,
            final MALInteractionListener listener,
            final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.synchronousInteraction(messageTarget, op,
                MALProgressOperation.PROGRESS_STAGE, listener, body);
    }

    @Override
    public void register(final MALPubSubOperation op,
            final Subscription subscription,
            final MALInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        sender.register(messageTarget, op, subscription, listener);
    }

    @Override
    public void deregister(final MALPubSubOperation op,
            final IdentifierList unsubscription)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        sender.deregister(messageTarget, op, unsubscription);
    }

    @Override
    public MALMessage asyncSubmit(final MALSubmitOperation op,
            final MALInteractionListener listener,
            final Object... requestBody)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALSubmitOperation.SUBMIT_STAGE, listener, requestBody);
    }

    @Override
    public MALMessage asyncSubmit(final MALSubmitOperation op,
            final MALInteractionListener listener,
            final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALSubmitOperation.SUBMIT_STAGE, listener, body);
    }

    @Override
    public MALMessage asyncRequest(final MALRequestOperation op,
            final MALInteractionListener listener,
            final Object... requestBody)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALRequestOperation.REQUEST_STAGE, listener, requestBody);
    }

    @Override
    public MALMessage asyncRequest(final MALRequestOperation op,
            final MALInteractionListener listener,
            final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALRequestOperation.REQUEST_STAGE, listener, body);
    }

    @Override
    public MALMessage asyncInvoke(final MALInvokeOperation op,
            final MALInteractionListener listener,
            final Object... requestBody)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALInvokeOperation.INVOKE_STAGE, listener, requestBody);
    }

    @Override
    public MALMessage asyncInvoke(final MALInvokeOperation op,
            final MALInteractionListener listener,
            final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALInvokeOperation.INVOKE_STAGE, listener, body);
    }

    @Override
    public MALMessage asyncProgress(final MALProgressOperation op,
            final MALInteractionListener listener,
            final Object... requestBody)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALProgressOperation.PROGRESS_STAGE, listener, requestBody);
    }

    @Override
    public MALMessage asyncProgress(final MALProgressOperation op,
            final MALInteractionListener listener,
            final MALEncodedBody body)
            throws IllegalArgumentException, MALInteractionException, MALException {
        return sender.asynchronousInteraction(messageTarget, op,
                MALProgressOperation.PROGRESS_STAGE, listener, body);
    }

    @Override
    public MALMessage asyncRegister(final MALPubSubOperation op,
            final Subscription subscription,
            final MALInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        return sender.registerAsync(messageTarget, op, subscription, listener);
    }

    @Override
    public MALMessage asyncDeregister(final MALPubSubOperation op,
            final IdentifierList unsubscription,
            final MALInteractionListener listener)
            throws java.lang.IllegalArgumentException, MALInteractionException, MALException {
        return sender.deregisterAsync(messageTarget, op, unsubscription, listener);
    }

    @Override
    public void continueInteraction(final MALOperation op,
            final UOctet lastInteractionStage,
            final Time initiationTimestamp,
            final Long transactionId,
            final MALInteractionListener listener)
            throws IllegalArgumentException, MALInteractionException, MALException {
        sender.continueInteraction(op, lastInteractionStage, initiationTimestamp, transactionId, listener);
    }

    @Override
    public void setTransmitErrorListener(final MALTransmitErrorListener plistener) throws MALException {
        transmissionListener = plistener;
    }

    @Override
    public MALTransmitErrorListener getTransmitErrorListener() throws MALException {
        return transmissionListener;
    }

    @Override
    public void close() throws MALException {
        messageTarget.getEndpoint().stopMessageDelivery();
        messageTarget.getEndpoint().close();
    }
}
