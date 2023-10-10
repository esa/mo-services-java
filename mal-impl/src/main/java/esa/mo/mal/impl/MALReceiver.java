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
package esa.mo.mal.impl;

import esa.mo.mal.impl.broker.MALBrokerBindingImpl;
import esa.mo.mal.impl.ips.InvokeIPProviderHandler;
import esa.mo.mal.impl.ips.ProgressIPProviderHandler;
import esa.mo.mal.impl.ips.PubSubIPProviderHandler;
import esa.mo.mal.impl.ips.RequestIPProviderHandler;
import esa.mo.mal.impl.ips.SendIPProviderHandler;
import esa.mo.mal.impl.ips.SubmitIPProviderHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.*;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * This class is the main class for handling received messages.
 */
public class MALReceiver implements MALMessageListener {

    private final MALSender sender;
    private final MALAccessControl securityManager;
    private final InteractionConsumerMap consumersMap;
    private final Map<String, MALBrokerBindingImpl> brokers;
    private final Map<EndPointPair, Address> providerEndpointMap = new HashMap();
    private final InteractionPubSubMap pubSubMap;

    MALReceiver(final MALSender sender,
            final MALAccessControl securityManager,
            final InteractionConsumerMap imap,
            final InteractionPubSubMap psmap,
            final Map<String, MALBrokerBindingImpl> brokers) {
        this.sender = sender;
        this.securityManager = securityManager;
        this.consumersMap = imap;
        this.pubSubMap = psmap;
        this.brokers = brokers;
    }

    @Override
    public void onInternalError(final MALEndpoint callingEndpoint, final Throwable err) {
        MALContextFactoryImpl.LOGGER.severe("MAL Receiving ERROR!");
    }

    @Override
    public void onTransmitError(final MALEndpoint callingEndpoint,
            final MALMessageHeader srcMessageHeader,
            final MOErrorException err,
            final Map qosMap) {
        MALContextFactoryImpl.LOGGER.severe("MAL Receiving Transmission ERROR!");

        consumersMap.handleError(srcMessageHeader, err, qosMap);
    }

    @Override
    public void onMessages(final MALEndpoint callingEndpoint, final MALMessage[] msgList) {
        for (MALMessage msgList1 : msgList) {
            onMessage(callingEndpoint, msgList1);
        }
    }

    /**
     * Entry point for this class, determines what to do with the received
     * message.
     *
     * @param callingEndpoint The endpoint that received this message.
     * @param msg The message.
     */
    @Override
    public void onMessage(final MALEndpoint callingEndpoint, MALMessage msg) {
        Address address = null;

        try {
            msg = securityManager.check(msg);

            UOctet oStage = msg.getHeader().getInteractionStage();
            short stage = (oStage != null) ? oStage.getValue() : -1;

            MALContextFactoryImpl.LOGGER.fine("MAL Receiving message");

            switch (msg.getHeader().getInteractionType().getOrdinal()) {
                case InteractionType._SEND_INDEX:
                    address = lookupAddress(callingEndpoint, msg);
                    handleSend(msg, address);
                    break;
                case InteractionType._SUBMIT_INDEX:
                    switch (stage) {
                        case MALSubmitOperation._SUBMIT_STAGE:
                            address = lookupAddress(callingEndpoint, msg);
                            handleSubmit(msg, address);
                            break;
                        case MALSubmitOperation._SUBMIT_ACK_STAGE:
                            consumersMap.handleStage(msg);
                            break;
                        default:
                            throw new MALException("Received unexpected stage of " + stage);
                    }
                    break;
                case InteractionType._REQUEST_INDEX:
                    switch (stage) {
                        case MALRequestOperation._REQUEST_STAGE:
                            address = lookupAddress(callingEndpoint, msg);
                            handleRequest(msg, address);
                            break;
                        case MALRequestOperation._REQUEST_RESPONSE_STAGE:
                            consumersMap.handleStage(msg);
                            break;
                        default:
                            throw new MALException("Received unexpected stage of " + stage);
                    }
                    break;
                case InteractionType._INVOKE_INDEX:
                    switch (stage) {
                        case MALInvokeOperation._INVOKE_STAGE:
                            address = lookupAddress(callingEndpoint, msg);
                            handleInvoke(msg, address);
                            break;
                        case MALInvokeOperation._INVOKE_ACK_STAGE:
                        case MALInvokeOperation._INVOKE_RESPONSE_STAGE:
                            consumersMap.handleStage(msg);
                            break;
                        default:
                            throw new MALException("Received unexpected stage of " + stage);
                    }
                    break;
                case InteractionType._PROGRESS_INDEX:
                    switch (stage) {
                        case MALProgressOperation._PROGRESS_STAGE:
                            address = lookupAddress(callingEndpoint, msg);
                            handleProgress(msg, address);
                            break;
                        case MALProgressOperation._PROGRESS_ACK_STAGE:
                        case MALProgressOperation._PROGRESS_UPDATE_STAGE:
                        case MALProgressOperation._PROGRESS_RESPONSE_STAGE:
                            consumersMap.handleStage(msg);
                            break;
                        default:
                            throw new MALException("Received unexpected stage of " + stage);
                    }
                    break;
                case InteractionType._PUBSUB_INDEX:
                    switch (stage) {
                        case MALPubSubOperation._REGISTER_ACK_STAGE:
                        case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
                        case MALPubSubOperation._DEREGISTER_ACK_STAGE:
                        case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
                            consumersMap.handleStage(msg);
                            break;
                        case MALPubSubOperation._REGISTER_STAGE:
                            address = lookupAddress(callingEndpoint, null);
                            handleRegister(msg, address);
                            break;
                        case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                            address = lookupAddress(callingEndpoint, null);
                            handlePublishRegister(msg, address);
                            break;
                        case MALPubSubOperation._PUBLISH_STAGE:
                            address = lookupAddress(callingEndpoint, null);
                            handlePublish(msg, address);
                            break;
                        case MALPubSubOperation._NOTIFY_STAGE:
                            handleNotify(msg);
                            break;
                        case MALPubSubOperation._DEREGISTER_STAGE:
                            address = lookupAddress(callingEndpoint, null);
                            handleDeregister(msg, address);
                            break;
                        case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
                            address = lookupAddress(callingEndpoint, null);
                            handlePublishDeregister(msg, address);
                            break;
                        default:
                            throw new MALException("Received unexpected stage of " + stage);
                    }
                    break;
                default:
                    throw new MALException("Received unexpected interaction of "
                            + msg.getHeader().getInteractionType().getOrdinal());
            }
        } catch (MALInteractionException ex) {
            // try to determine address info if null
            if (null == address) {
                address = lookupAddress(callingEndpoint, msg);
            }

            final UOctet rspnInteractionStage = msg.getHeader().calculateReturnStage();

            if (rspnInteractionStage == null) {
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "(1) Unable to return error, already a return message!", ex);
            } else {
                sender.returnError(address,
                        msg.getHeader(),
                        rspnInteractionStage,
                        ex.getStandardError());
            }
        } catch (MALException ex) {
            // try to determine address info if null
            if (address == null) {
                address = lookupAddress(callingEndpoint, msg);
            }

            final UOctet rspnInteractionStage = msg.getHeader().calculateReturnStage();

            if (rspnInteractionStage == null) {
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "(2) Unable to return error, already a return message!", ex);
            } else {
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "(3) Attempting to return error!", ex);
                sender.returnError(address, msg.getHeader(), rspnInteractionStage, ex);
            }
        }
    }

    public void addProviderEndpoint(final String localURI,
            final MALService service, final Address address) {
        final EndPointPair key = new EndPointPair(localURI, service);

        if (!providerEndpointMap.containsKey(key)) {
            MALContextFactoryImpl.LOGGER.log(Level.FINE,
                    "registerProviderEndpoint for {0}", key);
            providerEndpointMap.put(key, address);
        }
    }

    public void removeProviderEndpoint(final String localURI, final MALService service) {
        final EndPointPair key = new EndPointPair(localURI, service);

        if (providerEndpointMap.containsKey(key)) {
            MALContextFactoryImpl.LOGGER.log(Level.FINE,
                    "deregisterProviderEndpoint for {0}", key);
            providerEndpointMap.remove(key);
        }
    }

    private void handleSend(final MALMessage msg, final Address address) throws MALInteractionException {
        try {
            MALInteractionHandler handler = address.getHandler();
            MALContextFactoryImpl.LOGGER.log(Level.FINE,
                    "handleSend for type {0}", handler);
            handler.handleSend(new SendIPProviderHandler(sender, msg), msg.getBody());
        } catch (MALException ex) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Error generated during reception of SEND pattern, dropping: {0}", ex);
        }
    }

    private void handleSubmit(final MALMessage msg,
            final Address address) throws MALInteractionException {
        SubmitIPProviderHandler interaction = new SubmitIPProviderHandler(sender, address, msg);

        try {
            try {
                MALInteractionHandler handler = address.getHandler();
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "handleSubmit for {0} type {1}",
                        new Object[]{msg.getHeader().getTransactionId(), handler}
                );
                handler.handleSubmit(interaction, msg.getBody());
            } catch (MALInteractionException ex) {
                interaction.sendError(ex.getStandardError());
            }
        } catch (MALException ex) {
            sender.returnError(address,
                    msg.getHeader(),
                    MALSubmitOperation.SUBMIT_ACK_STAGE,
                    ex);
        }
    }

    private void handleRequest(final MALMessage msg,
            final Address address) throws MALInteractionException {
        RequestIPProviderHandler interaction = new RequestIPProviderHandler(sender, address, msg);

        try {
            try {
                MALInteractionHandler handler = address.getHandler();
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "handleRequest for {0} type {1}",
                        new Object[]{msg.getHeader().getTransactionId(), handler}
                );
                handler.handleRequest(interaction, msg.getBody());
            } catch (MALInteractionException ex) {
                interaction.sendError(ex.getStandardError());
            }
        } catch (MALException ex) {
            sender.returnError(address,
                    msg.getHeader(),
                    MALRequestOperation.REQUEST_RESPONSE_STAGE,
                    ex);
        }
    }

    private void handleInvoke(final MALMessage msg,
            final Address address) throws MALInteractionException {
        InvokeIPProviderHandler interaction = new InvokeIPProviderHandler(sender, address, msg);

        try {
            try {
                MALInteractionHandler handler = address.getHandler();
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "handleInvoke for {0} type {1}",
                        new Object[]{msg.getHeader().getTransactionId(), handler}
                );
                handler.handleInvoke(interaction, msg.getBody());
            } catch (MALInteractionException ex) {
                interaction.sendError(ex.getStandardError());
            }
        } catch (MALException ex) {
            try {
                interaction.sendError(new MOErrorException(
                        MALHelper.INTERNAL_ERROR_NUMBER,
                        new Union(ex.getLocalizedMessage())
                ));
            } catch (MALException noex) {
                // this exception cannot actually be thrown in this 
                // implementation, therefore we can safely ignore it
            }
        }
    }

    private void handleProgress(final MALMessage msg,
            final Address address) throws MALInteractionException {
        ProgressIPProviderHandler interaction = new ProgressIPProviderHandler(sender, address, msg);

        try {
            try {
                MALInteractionHandler handler = address.getHandler();
                MALContextFactoryImpl.LOGGER.log(Level.FINE,
                        "handleProgresss for {0} type {1}",
                        new Object[]{msg.getHeader().getTransactionId(), handler}
                );
                handler.handleProgress(interaction, msg.getBody());
            } catch (MALInteractionException ex) {
                interaction.sendError(ex.getStandardError());
            }
        } catch (MALException ex) {
            try {
                interaction.sendError(new MOErrorException(
                        MALHelper.INTERNAL_ERROR_NUMBER,
                        new Union(ex.getLocalizedMessage())
                ));
            } catch (MALException noex) {
                // this exception cannot actually be thrown in this 
                // implementation, therefore we can safely ignore it
            }
        }
    }

    private void handleRegister(final MALMessage msg, final Address address)
            throws MALInteractionException, MALException {
        // find relevant broker
        final MALBrokerBindingImpl brokerHandler = brokers.get(msg.getHeader().getTo().getValue());

        if (null != brokerHandler) {
            if (msg.getBody() instanceof MALRegisterBody) {
                // update register list
                final MALInteraction interaction = new PubSubIPProviderHandler(sender, address, msg);
                brokerHandler.addSubscriber(msg.getHeader().getFrom().getValue());
                brokerHandler.getBrokerImpl().getHandler().handleRegister(
                        interaction, (MALRegisterBody) msg.getBody());

                // because we don't pass this upwards, we have to generate the ack
                sender.returnResponse(address,
                        msg.getHeader(),
                        MALPubSubOperation.REGISTER_ACK_STAGE,
                        interaction.getOperation(),
                        interaction.getQoSProperties(),
                        (Object[]) null);
            } else {
                sender.returnError(address,
                        msg.getHeader(),
                        MALPubSubOperation.REGISTER_ACK_STAGE,
                        new MOErrorException(
                                MALHelper.BAD_ENCODING_ERROR_NUMBER,
                                new Union("Body of register message must be of type Subscription")
                        ));
            }
        } else {
            sender.returnError(address,
                    msg.getHeader(),
                    MALPubSubOperation.REGISTER_ACK_STAGE,
                    new MOErrorException(
                            MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                            new Union("Broker unknown at this address")
                    ));
        }
    }

    private void handlePublishRegister(final MALMessage msg,
            final Address address) throws MALInteractionException, MALException {
        // find relevant broker
        final MALBrokerBindingImpl brokerHandler = brokers.get(msg.getHeader().getTo().getValue());

        if (null != brokerHandler) {
            if (msg.getBody() instanceof MALPublishRegisterBody) {
                // update register list
                final MALInteraction interaction = new PubSubIPProviderHandler(sender, address, msg);
                brokerHandler.getBrokerImpl().getHandler().handlePublishRegister(interaction, (MALPublishRegisterBody) msg.getBody());

                // because we don't pass this upwards, we have to generate the ack
                sender.returnResponse(address,
                        msg.getHeader(),
                        MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE,
                        interaction.getOperation(),
                        interaction.getQoSProperties(),
                        (Object[]) null
                );
            } else {
                sender.returnError(address,
                        msg.getHeader(),
                        MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE,
                        new MOErrorException(
                                MALHelper.BAD_ENCODING_ERROR_NUMBER,
                                new Union("Body of publish register message must be of type EntityKeyList")
                        ));
            }
        } else {
            sender.returnError(address,
                    msg.getHeader(),
                    MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE,
                    new MOErrorException(
                            MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                            new Union("Broker unknown at this address")
                    ));
        }
    }

    private void handlePublish(final MALMessage msg,
            final Address address) throws MALInteractionException {
        if (msg.getHeader().getIsErrorMessage()) {
            if (msg.getBody() instanceof MALErrorBody) {
                try {
                    MALPublishInteractionListener listener
                            = pubSubMap.getPublishListener(msg.getHeader().getTo(), msg.getHeader());

                    if (listener != null) {
                        listener.publishErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
                    } else {
                        MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                                "Unknown publisher for PUBLISH error: {0}",
                                msg.getHeader().getTo());
                        pubSubMap.listPublishListeners();
                    }
                } catch (MALException ex) {
                    MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                            "Exception thrown processing publish error: {0}", ex);
                }
            }
        } else {
            // find relevant broker
            final MALBrokerBindingImpl brokerHandler = brokers.get(msg.getHeader().getTo().getValue());

            if (null != brokerHandler) {
                if (msg.getBody() instanceof MALPublishBody) {
                    try {
                        final MALInteraction interaction = new PubSubIPProviderHandler(sender, address, msg);
                        brokerHandler.getBrokerImpl().getHandler().handlePublish(interaction, (MALPublishBody) msg.getBody());
                    } catch (MALInteractionException ex) {
                        sender.returnError(address,
                                msg.getHeader(),
                                MALPubSubOperation.PUBLISH_STAGE,
                                ex.getStandardError());
                    } catch (MALException ex) {
                        sender.returnError(address,
                                msg.getHeader(),
                                MALPubSubOperation.PUBLISH_STAGE,
                                ex);
                    }
                } else {
                    MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                            "Unexpected body type for PUBLISH: {0}",
                            msg.getHeader().getTo());
                    sender.returnError(address,
                            msg.getHeader(),
                            MALPubSubOperation.PUBLISH_STAGE,
                            new MOErrorException(
                                    MALHelper.BAD_ENCODING_ERROR_NUMBER,
                                    new Union("Body of publish message must be of type UpdateList")
                            ));
                }
            } else {
                sender.returnError(address,
                        msg.getHeader(),
                        MALPubSubOperation.PUBLISH_STAGE,
                        new MOErrorException(
                                MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                                new Union("Broker unknown at this address")
                        ));
            }
        }
    }

    private void handleNotify(final MALMessage msg) throws MALInteractionException, MALException {
        final MALMessageHeader hdr = msg.getHeader();

        if (hdr.getIsErrorMessage()) {
            final Map<String, MALInteractionListener> listeners = pubSubMap.getNotifyListenersAndRemove(hdr.getTo().getValue());

            if (listeners != null) {
                final MALErrorBody err = (MALErrorBody) msg.getBody();
                for (Map.Entry<String, MALInteractionListener> e : listeners.entrySet()) {
                    try {
                        e.getValue().notifyErrorReceived(hdr, err, msg.getQoSProperties());
                    } catch (MALException ex) {
                        MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                                "Exception thrown processing notify error: {0}", ex);
                    }
                }
            } else {
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "Unknown notify consumer requested: {0}", hdr.getTo());
            }
        } else {
            final MALNotifyBody notifyBody = (MALNotifyBody) msg.getBody();
            final MALInteractionListener rcv = pubSubMap.getNotifyListener(hdr.getTo(), notifyBody.getSubscriptionId());

            if (rcv != null) {
                try {
                    rcv.notifyReceived(hdr, notifyBody, msg.getQoSProperties());
                } catch (MALException ex) {
                    MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                            "Error generated during handling of NOTIFY message, dropping: {0}", ex);
                }
            } else {
                MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                        "Unknown notify consumer requested:\n >> uri: {0}\n >> subscriptionId: {1}",
                        new Object[]{hdr.getTo(), notifyBody.getSubscriptionId()});
                pubSubMap.listPublishListeners();
            }
        }
    }

    private void handleDeregister(final MALMessage msg,
            final Address address) throws MALInteractionException {
        // find relevant broker
        String uri = msg.getHeader().getTo().getValue();
        final MALBrokerBindingImpl brokerHandler = brokers.get(uri);

        if (null != brokerHandler) {
            try {
                // update register list
                final MALInteraction interaction = new PubSubIPProviderHandler(sender, address, msg);
                brokerHandler.getBrokerImpl().getHandler().handleDeregister(interaction, (MALDeregisterBody) msg.getBody());
                brokerHandler.removeSubscriber(msg.getHeader().getFrom().getValue());

                // because we don't pass this upwards, we have to generate the ack
                sender.returnResponse(address,
                        msg.getHeader(),
                        MALPubSubOperation.DEREGISTER_ACK_STAGE,
                        interaction.getOperation(),
                        interaction.getQoSProperties(),
                        (Object[]) null);
            } catch (MALException ex) {
                sender.returnError(address,
                        msg.getHeader(),
                        MALPubSubOperation.DEREGISTER_ACK_STAGE, ex);
            }
        } else {
            sender.returnError(address,
                    msg.getHeader(),
                    MALPubSubOperation.DEREGISTER_ACK_STAGE,
                    new MOErrorException(
                            MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                            new Union("Broker unknown at this address")
                    ));
        }
    }

    private void handlePublishDeregister(final MALMessage msg,
            final Address address) throws MALInteractionException, MALException {
        // find relevant broker
        final MALBrokerBindingImpl brokerHandler = brokers.get(msg.getHeader().getTo().getValue());

        if (null != brokerHandler) {
            // update register list
            final MALInteraction interaction = new PubSubIPProviderHandler(sender, address, msg);
            brokerHandler.getBrokerImpl().getHandler().handlePublishDeregister(interaction);

            // because we don't pass this upwards, we have to generate the ack
            sender.returnResponse(address,
                    msg.getHeader(),
                    MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE,
                    interaction.getOperation(),
                    interaction.getQoSProperties(),
                    (Object[]) null
            );
        } else {
            sender.returnError(address,
                    msg.getHeader(),
                    MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE,
                    new MOErrorException(
                            MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                            new Union("Broker unknown at this address")
                    ));
        }
    }

    private Address lookupAddress(final MALEndpoint callingEndpoint, final MALMessage msg) {
        final EndPointPair key = new EndPointPair(callingEndpoint.getURI().getValue(), msg);
        Address addr = providerEndpointMap.get(key);

        if (addr == null) {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "lookupAddress failed to find local endpoint for:\n  >> {0}"
                    + "\nAvailable options: \n{1}\n",
                    new Object[]{key, providerEndpointMap.toString()}
            );
        }

        return addr;
    }

    private static class EndPointPair implements Comparable {

        private static final int HASH_VALUE = 71;
        private final String first;
        private final Long second;

        protected EndPointPair(final String localURI, final MALService service) {
            first = localURI;
            if (service != null) {
                second = (((long) service.getAreaNumber().getValue()) << 32) + ((long) service.getServiceNumber().getValue());
            } else {
                second = null;
            }
        }

        protected EndPointPair(final String localURI, final MALMessage msg) {
            first = localURI;

            if (msg != null) {
                second = (((long) msg.getHeader().getServiceArea().getValue()) << 32) + ((long) msg.getHeader().getService().getValue());
            } else {
                second = null;
            }
        }

        @Override
        public int compareTo(final Object other) {
            final EndPointPair otherPair = (EndPointPair) other;

            final int irv = this.first.compareTo(otherPair.first);

            if (0 == irv) {
                if (null != this.second) {
                    if (null == otherPair.second) {
                        return -1;
                    } else {
                        return this.second.compareTo(otherPair.second);
                    }
                } else {
                    return (null == otherPair.second) ? 0 : -1;
                }
            }

            return irv;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final EndPointPair other = (EndPointPair) obj;
            if (this.first == null ? other.first != null : !this.first.equals(other.first)) {
                return false;
            }
            return !(this.second == null ? other.second != null : !this.second.equals(other.second));
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = HASH_VALUE * hash + (this.first != null ? this.first.hashCode() : 0);
            hash = HASH_VALUE * hash + (this.second != null ? this.second.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "\nEndPointPair{" + "first=" + first + ", second=" + second + '}';
        }
    }
}
