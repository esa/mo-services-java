/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esa.mo.mal.transport.gen.receivers;

import esa.mo.mal.transport.gen.GENReceptionHandler;
import esa.mo.mal.transport.gen.GENTransport;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;

/**
 * This Runnable task is responsible for decoding newly arrived MAL Messages and
 * passing to the transport executor.
 */
public class GENIncomingMessageReceiver implements Runnable {

    protected final GENTransport transport;
    protected final GENReceptionHandler receptionHandler;
    protected final GENIncomingMessageDecoder decoder;

    /**
     * Constructor
     *
     * @param transport Containing transport.
     * @param receptionHandler The reception handler to pass them to.
     * @param decoder The class responsible for decoding the message from the
     * incoming connection
     */
    public GENIncomingMessageReceiver(final GENTransport transport,
            final GENReceptionHandler receptionHandler,
            final GENIncomingMessageDecoder decoder) {
        this.transport = transport;
        this.receptionHandler = receptionHandler;
        this.decoder = decoder;
    }

    /**
     * This method processes an incoming message and then forwards it for
     * routing to the appropriate message queue. The processing consists of
     * transforming the raw message to the appropriate format and then
     * registering if necessary the communication channel.
     */
    @Override
    public void run() {
        try {
            GENIncomingMessageHolder msg = decoder.decodeAndCreateMessage();

            // the decoder may return null for transports that support fragmentation
            if (null != msg) {
                GENTransport.LOGGER.log(Level.FINE,
                        "Receving message : {0} : {1}",
                        new Object[]{msg.malMsg.getHeader().getTransactionId(), msg.smsg});

                //register communication channel if needed
                transport.manageCommunicationChannel(msg.malMsg, true, receptionHandler);
                transport.receiveIncomingMessage(msg);
            }
        } catch (MALException e) {
            GENTransport.LOGGER.log(Level.WARNING,
                    "Error occurred when decoding data : {0}", e);

            transport.communicationError(null, receptionHandler);
        } catch (MALTransmitErrorException e) {
            GENTransport.LOGGER.log(Level.WARNING,
                    "Error occurred when decoding data : {0}", e);

            transport.communicationError(null, receptionHandler);
        }
    }
}
