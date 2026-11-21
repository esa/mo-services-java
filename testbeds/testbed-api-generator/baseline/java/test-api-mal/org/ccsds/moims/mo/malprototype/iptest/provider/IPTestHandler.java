package org.ccsds.moims.mo.malprototype.iptest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.malprototype.TestErrorException;
import org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse;
import org.ccsds.moims.mo.malprototype.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;

/**
 * Interface that providers of the IPTest service must implement to handle
 * the operations of that service.
 */
public interface IPTestHandler {

    /**
     * Implements the operation send.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void send(IPTestDefinition input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testSubmit.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void testSubmit(IPTestDefinition input,
            MALInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation request.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    String request(IPTestDefinition input,
            MALInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation invoke.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void invoke(IPTestDefinition input,
            InvokeInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation progress.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void progress(IPTestDefinition input,
            ProgressInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation getResult.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    IPTestResult getResult(Element input,
            MALInteraction interaction) throws MALException;
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
     * Implements the operation sendMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void sendMulti(IPTestDefinition input1,
            Element input2,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation submitMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void submitMulti(IPTestDefinition input1,
            Element input2,
            MALInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation requestMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    RequestMultiResponse requestMulti(IPTestDefinition input1,
            Element input2,
            MALInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation invokeMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void invokeMulti(IPTestDefinition input1,
            Element input2,
            InvokeMultiInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation progressMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws TestErrorException Fake error for testing.
     * @throws MALException if there is an implementation exception
     */
    void progressMulti(IPTestDefinition input1,
            Element input2,
            ProgressMultiInteraction interaction) throws TestErrorException, MALException;
    /**
     * Implements the operation testRequestEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void testRequestEmptyBody(IPTestDefinition input1,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testInvokeEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void testInvokeEmptyBody(IPTestDefinition input1,
            TestInvokeEmptyBodyInteraction interaction) throws MALException;
    /**
     * Implements the operation testProgressEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws MALException if there is an implementation exception
     */
    void testProgressEmptyBody(IPTestDefinition input1,
            TestProgressEmptyBodyInteraction interaction) throws MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(IPTestSkeleton skeleton);
}
