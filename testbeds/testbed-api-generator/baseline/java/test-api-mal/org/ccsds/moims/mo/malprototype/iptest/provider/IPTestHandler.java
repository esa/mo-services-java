package org.ccsds.moims.mo.malprototype.iptest.provider;

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
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void send(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testSubmit.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void testSubmit(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation request.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    String request(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation invoke.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void invoke(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.provider.InvokeInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation progress.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void progress(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input,
            org.ccsds.moims.mo.malprototype.iptest.provider.ProgressInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getResult.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.structures.IPTestResult getResult(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation publishUpdates.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void publishUpdates(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation publishRegister.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void publishRegister(org.ccsds.moims.mo.malprototype.structures.TestPublishRegister input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation publishDeregister.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void publishDeregister(org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testMultipleNotify.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void testMultipleNotify(org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation sendMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void sendMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation submitMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void submitMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation requestMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse requestMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation invokeMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void invokeMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.provider.InvokeMultiInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation progressMulti.
     * 
     * @param input1 The input1 field.
     * @param input2 The input2 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.malprototype.TestErrorException Fake error for testing.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void progressMulti(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.structures.Element input2,
            org.ccsds.moims.mo.malprototype.iptest.provider.ProgressMultiInteraction interaction) throws org.ccsds.moims.mo.malprototype.TestErrorException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testRequestEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void testRequestEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testInvokeEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void testInvokeEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.malprototype.iptest.provider.TestInvokeEmptyBodyInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testProgressEmptyBody.
     * 
     * @param input1 The input1 field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void testProgressEmptyBody(org.ccsds.moims.mo.malprototype.structures.IPTestDefinition input1,
            org.ccsds.moims.mo.malprototype.iptest.provider.TestProgressEmptyBodyInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.malprototype.iptest.provider.IPTestSkeleton skeleton);
}
