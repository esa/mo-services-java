package org.ccsds.moims.mo.malprototype.iptest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Provider INVOKE interaction class for IPTest::invokeMulti operation.
 */
public class InvokeMultiInteraction {

    /**
     * The interaction field.
     */
    private MALInvoke interaction;

    /**
     * Wraps the provided MAL interaction object with methods for sending responses
     * to an INVOKE interaction from a provider.
     * 
     * @param interaction The MAL interaction action object to use.
     */
    public InvokeMultiInteraction(MALInvoke interaction) {
        this.interaction = interaction;
    }

    /**
     * Returns the MAL interaction object used for returning messages from the
     * provider.
     * 
     * @return The MAL interaction object provided in the constructor
     */
    public MALInvoke getInteraction() {
        return interaction;
    }

    /**
     * Sends a INVOKE acknowledge to the consumer.
     * 
     * @param ack1 The ack1 field.
     * @param ack2 The ack2 field.
     * @return Returns the MAL message created by the acknowledge
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendAcknowledgement(String ack1,
            Element ack2) throws MALException {
        try {
            return interaction.sendAcknowledgement((ack1 == null) ? null : new Union(ack1), ack2);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends a INVOKE response to the consumer.
     * 
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @return Returns the MAL message created by the response
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendResponse(String output1,
            Element output2) throws MALException {
        try {
            return interaction.sendResponse((output1 == null) ? null : new Union(output1), output2);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends an error to the consumer.
     * 
     * @param error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendError(MOErrorException error) throws MALException {
        try {
            return interaction.sendError(error);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

}
