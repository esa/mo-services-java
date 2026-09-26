package org.ccsds.moims.mo.malprototype.iptest.provider;

/**
 * Provider INVOKE interaction class for IPTest::testInvokeEmptyBody operation.
 */
public class TestInvokeEmptyBodyInteraction {

    /**
     * The interaction field.
     */
    private org.ccsds.moims.mo.mal.provider.MALInvoke interaction;

    /**
     * Wraps the provided MAL interaction object with methods for sending responses
     * to an INVOKE interaction from a provider.
     * 
     * @param interaction The MAL interaction action object to use.
     */
    public TestInvokeEmptyBodyInteraction(org.ccsds.moims.mo.mal.provider.MALInvoke interaction) {
        this.interaction = interaction;
    }

    /**
     * Returns the MAL interaction object used for returning messages from the
     * provider.
     * 
     * @return The MAL interaction object provided in the constructor
     */
    public org.ccsds.moims.mo.mal.provider.MALInvoke getInteraction() {
        return interaction;
    }

    /**
     * Sends a INVOKE acknowledge to the consumer.
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
     * Sends a INVOKE response to the consumer.
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
     * @param error The MAL error to send to the consumer.
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

}
