package org.ccsds.moims.mo.comprototype.activitytest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Provider INVOKE interaction class for ActivityTest::invoke operation.
 */
public class InvokeInteraction {

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
    public InvokeInteraction(MALInvoke interaction) {
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
     * @return Returns the MAL message created by the acknowledge
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendAcknowledgement(StringList ack1) throws MALException {
        try {
            return interaction.sendAcknowledgement(ack1);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends a INVOKE response to the consumer.
     * 
     * @param out1 The out1 field.
     * @return Returns the MAL message created by the response
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendResponse(StringList out1) throws MALException {
        try {
            return interaction.sendResponse(out1);
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
