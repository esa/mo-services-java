/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen.util;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.gen.receivers.MessageReceiver;
import esa.mo.mal.transport.gen.ReceptionHandler;
import esa.mo.mal.transport.gen.sending.MessageSender;
import esa.mo.mal.transport.gen.Transport;
import static esa.mo.mal.transport.gen.Transport.LOGGER;
import java.io.IOException;
import java.io.EOFException;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;

/**
 * This utility class creates a thread to pull encoded messages from a
 * transceiver. It receives messages from it and then forwards the incoming
 * message to an asynchronous processor in order to return immediately and not
 * hold the calling thread while the message is processed.
 *
 * In case of a communication problem it informs the transport and/or closes the
 * resource
 *
 * Only transport adapter that pull messages from their transport layer will
 * need to use this class.
 *
 * @param <I> The type of the encoded messages.
 * @param <O> The type of the outgoing messages.
 */
public class MessagePoller<I, O> extends Thread implements ReceptionHandler {

    /**
     * Reference to the transport
     */
    protected final Transport transport;
    /**
     * the low level message sender
     */
    protected final MessageSender messageSender;
    protected final MessageReceiver<I> messageReceiver;
    /**
     * the remote URI (client) this connection is associated to. This is
     * volatile as it is potentially set by a different thread after its
     * creation
     */
    private volatile String remoteURI = null;

    /**
     * Constructor.
     *
     * @param transport Message transport being used.
     * @param messageSender The message sending interface associated to this
     * connection.
     * @param messageReceiver The message reception interface, used for pulling
     * messaging into this transport.
     */
    public MessagePoller(Transport<I, O> transport,
            MessageSender messageSender,
            MessageReceiver<I> messageReceiver) {
        this.transport = transport;
        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
        setName("Transport_Receive");
    }

    @Override
    public void run() {
        boolean bContinue = true;

        // handles message reads from this client
        while (bContinue && !interrupted()) {
            try {
                I msg = messageReceiver.readEncodedMessage();

                if (msg != null) {
                    try {
                        //PacketToString smsg = new PacketToString(msg);
                        GENMessage malMsg = transport.createMessage(msg);
                        IncomingMessageHolder holder = new IncomingMessageHolder(malMsg, null);
                        transport.receive(this, holder);
                    } catch (MALException e) {
                        Transport.LOGGER.log(Level.WARNING,
                                "Error occurred when decoding data : {0}", e);

                        transport.communicationError(null, this);
                    }
                }
            } catch (InterruptedException ex) {
                LOGGER.log(Level.INFO, "(1) Client closing connection: {0}", remoteURI);

                transport.closeConnection(remoteURI, this);
                close();
                bContinue = false; // and terminate
            } catch (EOFException ex) {
                LOGGER.log(Level.INFO, "(2) Client closing connection: {0}", remoteURI);

                transport.closeConnection(remoteURI, this);
                close();
                bContinue = false; // and terminate
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Cannot read message from client", e);

                transport.communicationError(remoteURI, this);
                close();
                bContinue = false; // and terminate
            }
        }
    }

    @Override
    public String getRemoteURI() {
        return remoteURI;
    }

    @Override
    public void setRemoteURI(String remoteURI) {
        this.remoteURI = remoteURI;
        setName("Transport_Receive" + " URI:" + remoteURI);
    }

    @Override
    public MessageSender getMessageSender() {
        return messageSender;
    }

    @Override
    public void close() {
        messageSender.close();
        messageReceiver.close();
    }

}
