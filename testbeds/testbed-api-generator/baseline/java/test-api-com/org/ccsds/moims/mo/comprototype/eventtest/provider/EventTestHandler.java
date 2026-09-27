package org.ccsds.moims.mo.comprototype.eventtest.provider;

/**
 * Interface that providers of the EventTest service must implement to handle
 * the operations of that service.
 */
public interface EventTestHandler {

    /**
     * Implements the operation resetTest.
     * 
     * @param in1 The in1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void resetTest(String in1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation createinstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Long createinstance(Short in1,
            String in2,
            String in3,
            Long in4,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation deleteInstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void deleteInstance(Short in1,
            String in2,
            Long in3,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateInstance.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void updateInstance(Long in1,
            org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum in2,
            org.ccsds.moims.mo.mal.structures.Duration in3,
            org.ccsds.moims.mo.mal.structures.ShortList in4,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateInstanceComposite.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param in3 The in3 field.
     * @param in4 The in4 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void updateInstanceComposite(Long in1,
            org.ccsds.moims.mo.mal.structures.UOctet in2,
            Byte in3,
            Double in4,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.comprototype.eventtest.provider.EventTestSkeleton skeleton);
}
