package org.ccsds.moims.mo.comprototype.activitytest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.StringList;

/**
 * Interface that providers of the ActivityTest service must implement to
 * handle the operations of that service.
 */
public interface ActivityTestHandler {

    /**
     * Implements the operation resetTest.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void resetTest(MALInteraction interaction) throws MALException;
    /**
     * Implements the operation close.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void close(MALInteraction interaction) throws MALException;
    /**
     * Implements the operation send.
     * 
     * @param in1 The in1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void send(StringList in1,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testSubmit.
     * 
     * @param in1 The in1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void testSubmit(StringList in1,
            MALInteraction interaction) throws UnknownException, MALException;
    /**
     * Implements the operation request.
     * 
     * @param in1 The in1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws UnknownException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    StringList request(StringList in1,
            MALInteraction interaction) throws UnknownException, MALException;
    /**
     * Implements the operation invoke.
     * 
     * @param in1 The in1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void invoke(StringList in1,
            InvokeInteraction interaction) throws UnknownException, MALException;
    /**
     * Implements the operation progress.
     * 
     * @param in1 The in1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void progress(StringList in1,
            ProgressInteraction interaction) throws UnknownException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(ActivityTestSkeleton skeleton);
}
