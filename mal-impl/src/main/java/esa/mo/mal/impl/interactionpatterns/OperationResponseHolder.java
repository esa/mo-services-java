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
package esa.mo.mal.impl.interactionpatterns;

import esa.mo.mal.impl.MALContextFactoryImpl;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;

/**
 * This small class is used to hold the response to interactions for a consumer.
 */
public class OperationResponseHolder {

    private final AtomicBoolean signalReceived = new AtomicBoolean(false);
    private final MALInteractionListener listener;
    private boolean isError = false;
    private MALMessage result = null;
    private MOErrorException errorBody;

    /**
     * Constructor.
     *
     * @param listener The MAL interaction listener
     */
    public OperationResponseHolder(MALInteractionListener listener) {
        this.listener = listener;
    }

    /**
     * Constructor.
     *
     * @param listener The MAL publish interaction listener
     */
    public OperationResponseHolder(MALPublishInteractionListener listener) {
        this.listener = new InteractionListenerPublishAdapter(listener);
    }

    public MALInteractionListener getListener() {
        return listener;
    }

    /**
     * Waits until a response signal is available.
     */
    public void waitForResponseSignal() {
        // wait for the response signal
        synchronized (signalReceived) {
            while (!signalReceived.get()) {
                try {
                    signalReceived.wait();
                } catch (InterruptedException ex) {
                    MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                            "Interrupted waiting for handler lock ", ex);
                }
            }
        }
    }

    /**
     * Responds to a consumer.
     *
     * @param isError boolean value for error status.
     * @param msg   MAL message
     */
    public void signalResponse(final boolean isError, final MALMessage msg) {
        if (isError) {
            try {
                signalError(((MALErrorBody) msg.getBody()).getError());
            } catch (MALException ex) {
                Logger.getLogger(OperationResponseHolder.class.getName()).log(
                        Level.SEVERE, "Something went wrong!", ex);
            }
            return;
        }
        this.isError = isError;
        this.result = msg;

        synchronized (signalReceived) {
            signalReceived.set(true);
            signalReceived.notifyAll();
        }
    }

    /**
     * Responds an error to a consumer.
     *
     * @param errorBody MAL error message body.
     */
    public void signalError(final MOErrorException errorBody) {
        this.isError = true;
        this.errorBody = errorBody;

        synchronized (signalReceived) {
            signalReceived.set(true);
            signalReceived.notifyAll();
        }
    }

    public MALMessage getResult() throws MALInteractionException, MALException {
        if (isError) {
            throw new MALInteractionException(errorBody);
        }

        return result;
    }

    /**
     * Wrapper class to allow an PubSub interaction to be processed by common
     * code.
     */
    private static final class InteractionListenerPublishAdapter implements MALInteractionListener {

        private final MALPublishInteractionListener delegate;

        protected InteractionListenerPublishAdapter(final MALPublishInteractionListener delegate) {
            this.delegate = delegate;
        }

        @Override
        public void registerAckReceived(final MALMessageHeader header,
                final Map qosProperties) throws MALException {
            delegate.publishRegisterAckReceived(header, qosProperties);
        }

        @Override
        public void registerErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            delegate.publishRegisterErrorReceived(header, body, qosProperties);
        }

        @Override
        public void deregisterAckReceived(final MALMessageHeader header,
                final Map qosProperties) throws MALException {
            delegate.publishDeregisterAckReceived(header, qosProperties);
        }

        @Override
        public void invokeAckErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void invokeAckReceived(final MALMessageHeader header,
                final MALMessageBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void invokeResponseErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void invokeResponseReceived(final MALMessageHeader header,
                final MALMessageBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void notifyErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void notifyReceived(final MALMessageHeader header,
                final MALNotifyBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void progressAckErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void progressAckReceived(final MALMessageHeader header,
                final MALMessageBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void progressResponseErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void progressResponseReceived(final MALMessageHeader header,
                final MALMessageBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void progressUpdateErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void progressUpdateReceived(final MALMessageHeader header,
                final MALMessageBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void requestErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void requestResponseReceived(final MALMessageHeader header,
                final MALMessageBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void submitAckReceived(final MALMessageHeader header,
                final Map qosProperties) throws MALException {
            // nothing to do here
        }

        @Override
        public void submitErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // nothing to do here
        }
    }
}
