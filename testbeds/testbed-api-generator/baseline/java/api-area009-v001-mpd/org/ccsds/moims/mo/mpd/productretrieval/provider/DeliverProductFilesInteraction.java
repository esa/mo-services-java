package org.ccsds.moims.mo.mpd.productretrieval.provider;

/**
 * Provider PROGRESS interaction class for ProductRetrieval::deliverProductFiles
 * operation.
 */
public class DeliverProductFilesInteraction {

    /**
     * The interaction field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProgress interaction;

    /**
     * Wraps the provided MAL interaction object with methods for sending responses
     * to an PROGRESS interaction from a provider.
     * 
     * @param interaction The MAL interaction action object to use.
     */
    public DeliverProductFilesInteraction(org.ccsds.moims.mo.mal.provider.MALProgress interaction) {
        this.interaction = interaction;
    }

    /**
     * Returns the MAL interaction object used for returning messages from the
     * provider.
     * 
     * @return The MAL interaction object provided in the constructor
     */
    public org.ccsds.moims.mo.mal.provider.MALProgress getInteraction() {
        return interaction;
    }

    /**
     * Sends a PROGRESS acknowledge to the consumer.
     * 
     * @return Returns the MAL message created by the acknowledge
     * @throws org.ccsds.moims.mo.mal.MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendAcknowledgement() throws org.ccsds.moims.mo.mal.MALException {
        try {
            return interaction.sendAcknowledgement((Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw new org.ccsds.moims.mo.mal.MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends a PROGRESS update to the consumer.
     * 
     * @param metadata The metadata of the transferred mission data product(s).
     * @param filename The filename of the transferred mission data product(s).
     * @param success The completion status of the remote file transfer.
     * @return Returns the MAL message created by the update
     * @throws org.ccsds.moims.mo.mal.MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdate(org.ccsds.moims.mo.mpd.structures.ProductMetadata metadata,
            String filename,
            Boolean success) throws org.ccsds.moims.mo.mal.MALException {
        try {
            return interaction.sendUpdate(metadata, (filename == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(filename), (success == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(success));
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw new org.ccsds.moims.mo.mal.MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends a PROGRESS response to the consumer.
     * 
     * @return Returns the MAL message created by the response
     * @throws org.ccsds.moims.mo.mal.MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse() throws org.ccsds.moims.mo.mal.MALException {
        try {
            return interaction.sendResponse((Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw new org.ccsds.moims.mo.mal.MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends an error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws org.ccsds.moims.mo.mal.MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendError(org.ccsds.moims.mo.mal.MOErrorException error) throws org.ccsds.moims.mo.mal.MALException {
        try {
            return interaction.sendError(error);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw new org.ccsds.moims.mo.mal.MALException(ex.getMessage(), ex);
        }
    }

    /**
     * Sends an update error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws org.ccsds.moims.mo.mal.MALException if the message could not be sent, including a MAL standard error raised by the MAL
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdateError(org.ccsds.moims.mo.mal.MOErrorException error) throws org.ccsds.moims.mo.mal.MALException {
        try {
            return interaction.sendUpdateError(error);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw new org.ccsds.moims.mo.mal.MALException(ex.getMessage(), ex);
        }
    }

}
