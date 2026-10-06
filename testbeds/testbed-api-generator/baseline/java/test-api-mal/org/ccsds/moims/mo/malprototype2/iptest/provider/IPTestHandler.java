package org.ccsds.moims.mo.malprototype2.iptest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;

/**
 * Interface that providers of the IPTest service must implement to handle
 * the operations of that service.
 */
public interface IPTestHandler {

    /**
     * Implements the operation publishUpdates.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void publishUpdates(TestPublishUpdate input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation publishRegister.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void publishRegister(TestPublishRegister input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation publishDeregister.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void publishDeregister(TestPublishDeregister input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testMultipleNotify.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void testMultipleNotify(TestPublishUpdate input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testObjectRefSubmit.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void testObjectRefSubmit(ObjectRef<Auto> input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testObjectRefSend.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void testObjectRefSend(ObjectRef<Auto> input,
            MALInteraction interaction) throws MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(IPTestSkeleton skeleton);
}
