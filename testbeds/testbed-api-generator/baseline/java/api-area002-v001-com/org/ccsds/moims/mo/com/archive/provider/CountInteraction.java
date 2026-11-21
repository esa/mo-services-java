package org.ccsds.moims.mo.com.archive.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Provider INVOKE interaction class for Archive::count operation.
 */
public class CountInteraction {

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
    public CountInteraction(MALInvoke interaction) {
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
     * @return Returns the MAL message created by the acknowledge
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendAcknowledgement() throws MALException {
        try {
            return interaction.sendAcknowledgement((Object[]) null);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends a INVOKE response to the consumer.
     * 
     * @param counts The response shall contain the count of matched objects.
There shall be an entry in each returned list for each entry in the request list.
The returned lists shall be ordered the same as the request query lists so that the response can be matched to the corresponding request.
     * @return Returns the MAL message created by the response
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendResponse(LongList counts) throws MALException {
        try {
            return interaction.sendResponse(counts);
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
