package org.ccsds.moims.mo.mc.check.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mc.check.structures.CheckResultSummaryList;

/**
 * Provider PROGRESS interaction class for Check::getSummaryReport operation.
 */
public class GetSummaryReportInteraction {

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
    public GetSummaryReportInteraction(MALProgress interaction) {
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
     * @param updateObjInstIds The returned updates and final response shall contain an entry for each requested CheckIdentity.
The first part of the update shall be the CheckIdentity object instance identifier.
The second part shall be the list of all CheckLink object instance identifiers and CheckResults associated with that CheckIdentity.
     * @param updateSummaries updateSummaries Argument number 1 as defined by the service operation
     * @return Returns the MAL message created by the update
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendUpdate(Long updateObjInstIds,
            CheckResultSummaryList updateSummaries) throws MALException {
        try {
            return interaction.sendUpdate((updateObjInstIds == null) ? null : new Union(updateObjInstIds), updateSummaries);
        } catch (MALInteractionException ex) {
            throw new MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends a PROGRESS response to the consumer.
     * 
     * @param responseObjInstIds responseObjInstIds Argument number 0 as defined by the service operation
     * @param responseSummaries responseSummaries Argument number 1 as defined by the service operation
     * @return Returns the MAL message created by the response
     * @throws MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public MALMessage sendResponse(Long responseObjInstIds,
            CheckResultSummaryList responseSummaries) throws MALException {
        try {
            return interaction.sendResponse((responseObjInstIds == null) ? null : new Union(responseObjInstIds), responseSummaries);
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
