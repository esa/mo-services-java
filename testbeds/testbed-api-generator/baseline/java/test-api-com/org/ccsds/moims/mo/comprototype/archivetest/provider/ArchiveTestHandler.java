package org.ccsds.moims.mo.comprototype.archivetest.provider;

/**
 * Interface that providers of the ArchiveTest service must implement to handle
 * the operations of that service.
 */
public interface ArchiveTestHandler {

    /**
     * Implements the operation reset.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void reset(org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.comprototype.archivetest.provider.ArchiveTestSkeleton skeleton);
}
