package org.ccsds.moims.mo.comprototype.activityrelaymanagement.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;

/**
 * Interface that providers of the ActivityRelayManagement service must implement
 * to handle the operations of that service.
 */
public interface ActivityRelayManagementHandler {

    /**
     * Implements the operation resetTest.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void resetTest(MALInteraction interaction) throws MALException;
    /**
     * Implements the operation createRelay.
     * 
     * @param in1 The in1 field.
     * @param in2 The in2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void createRelay(String in1,
            String in2,
            MALInteraction interaction) throws MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(ActivityRelayManagementSkeleton skeleton);
}
