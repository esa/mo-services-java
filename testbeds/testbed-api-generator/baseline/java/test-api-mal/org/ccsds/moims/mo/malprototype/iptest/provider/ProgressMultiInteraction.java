package org.ccsds.moims.mo.malprototype.iptest.provider;

/**
 * Provider PROGRESS interaction class for IPTest::progressMulti operation.
 */
public class ProgressMultiInteraction {

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
    public ProgressMultiInteraction(org.ccsds.moims.mo.mal.provider.MALProgress interaction) {
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
     * @param ack1 The ack1 field.
     * @param ack2 The ack2 field.
     * @return Returns the MAL message created by the acknowledge
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendAcknowledgement(String ack1,
            org.ccsds.moims.mo.mal.structures.Element ack2) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendAcknowledgement((ack1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(ack1), ack2);
    }

    /**
     * Sends a PROGRESS update to the consumer.
     * 
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @return Returns the MAL message created by the update
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdate(Integer output1,
            org.ccsds.moims.mo.mal.structures.Element output2) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendUpdate((output1 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(output1), output2);
    }

    /**
     * Sends a PROGRESS response to the consumer.
     * 
     * @param output3 The output3 field.
     * @param output4 The output4 field.
     * @return Returns the MAL message created by the response
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendResponse(String output3,
            org.ccsds.moims.mo.mal.structures.Element output4) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendResponse((output3 == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(output3), output4);
    }

    /**
     * Sends an error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendError(org.ccsds.moims.mo.mal.MOErrorException error) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendError(error);
    }

    /**
     * Sends an update error to the consumer.
     * 
     * @param error error The MAL error to send to the consumer.
     * @return Returns the MAL message created by the error
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage sendUpdateError(org.ccsds.moims.mo.mal.MOErrorException error) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException {
        return interaction.sendUpdateError(error);
    }

}
