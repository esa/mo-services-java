package org.ccsds.moims.mo.mps.plandistribution.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mps.structures.Plan;

/**
 * Provider PROGRESS interaction class for PlanDistribution::getPlan operation.
 */
public class GetPlanInteraction {

    /**
     * The interaction field.
     */
    private MALProgress interaction;

    /**
     * Wraps the provided MAL interaction object with methods for sending responses
     * to an PROGRESS interaction from a provider.
     * 
     * @param interaction The MAL interaction action object to use.
     */
    public GetPlanInteraction(MALProgress interaction) {
        this.interaction = interaction;
    }

    /**
     * Returns the MAL interaction object used for returning messages from the
     * provider.
     * 
     * @return The MAL interaction object provided in the constructor
     */
    public MALProgress getInteraction() {
        return interaction;
    }

    /**
     * Sends a PROGRESS acknowledge to the consumer.
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
     * Sends a PROGRESS update to the consumer.
     * 
     * @param retrievedPlan The retrievedPlan field.
     * @return Returns the MAL message created by the update
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendUpdate(Plan retrievedPlan) throws MALException {
        try {
            return interaction.sendUpdate(retrievedPlan);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends a PROGRESS response to the consumer.
     * 
     * @return Returns the MAL message created by the response
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendResponse() throws MALException {
        try {
            return interaction.sendResponse((Object[]) null);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends an error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
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

    /**
     * Sends an update error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendUpdateError(MOErrorException error) throws MALException {
        try {
            return interaction.sendUpdateError(error);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

}
