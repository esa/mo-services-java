package org.ccsds.moims.mo.comprototype.archivetest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;

/**
 * Interface that providers of the ArchiveTest service must implement to handle
 * the operations of that service.
 */
public interface ArchiveTestHandler {

    /**
     * Implements the operation reset.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void reset(MALInteraction interaction) throws MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(ArchiveTestSkeleton skeleton);
}
