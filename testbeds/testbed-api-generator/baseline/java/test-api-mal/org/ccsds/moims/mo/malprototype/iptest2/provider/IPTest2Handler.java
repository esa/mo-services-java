package org.ccsds.moims.mo.malprototype.iptest2.provider;

/**
 * Interface that providers of the IPTest2 service must implement to handle
 * the operations of that service.
 */
public interface IPTest2Handler {

    /**
     * Implements the operation publishUpdates.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void publishUpdates(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation publishRegister.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void publishRegister(org.ccsds.moims.mo.malprototype.structures.TestPublishRegister input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation publishDeregister.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void publishDeregister(org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testMultipleNotify.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void testMultipleNotify(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.malprototype.iptest2.provider.IPTest2Skeleton skeleton);
}
