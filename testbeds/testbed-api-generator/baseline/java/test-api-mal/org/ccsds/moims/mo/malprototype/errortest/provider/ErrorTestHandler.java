package org.ccsds.moims.mo.malprototype.errortest.provider;

/**
 * Interface that providers of the ErrorTest service must implement to handle
 * the operations of that service.
 */
public interface ErrorTestHandler {

    /**
     * Implements the operation testDeliveryFailed.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testDeliveryFailed(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDeliveryTimedout.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testDeliveryTimedout(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDeliveryDelayed.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testDeliveryDelayed(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDestinationUnknown.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testDestinationUnknown(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDestinationTransient.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testDestinationTransient(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testDestinationLost.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testDestinationLost(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testEncryptionFail.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testEncryptionFail(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testUnsupportedArea.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testUnsupportedArea(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testUnsupportedOperation.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testUnsupportedOperation(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testUnsupportedAreaVersion.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testUnsupportedAreaVersion(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testBadEncoding.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testBadEncoding(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testUnknown.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testUnknown(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testAuthenticationFailure.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testAuthenticationFailure(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testAuthorizationFailure.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testAuthorizationFailure(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation testUnsupportedService.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.Element testUnsupportedService(org.ccsds.moims.mo.mal.structures.Element input,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.malprototype.errortest.provider.ErrorTestSkeleton skeleton);
}
