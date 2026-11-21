package org.ccsds.moims.mo.malprototype.errortest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;

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
     * @throws MALException if there is an implementation exception
     */
    Element testDeliveryFailed(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testDeliveryTimedout.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testDeliveryTimedout(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testDeliveryDelayed.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testDeliveryDelayed(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testDestinationUnknown.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testDestinationUnknown(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testDestinationTransient.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testDestinationTransient(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testDestinationLost.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testDestinationLost(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testEncryptionFail.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testEncryptionFail(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testUnsupportedArea.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testUnsupportedArea(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testUnsupportedOperation.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testUnsupportedOperation(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testUnsupportedAreaVersion.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testUnsupportedAreaVersion(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testBadEncoding.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testBadEncoding(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testUnknown.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testUnknown(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testAuthenticationFailure.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testAuthenticationFailure(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testAuthorizationFailure.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testAuthorizationFailure(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Implements the operation testUnsupportedService.
     * 
     * @param input The input field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws MALException if there is an implementation exception
     */
    Element testUnsupportedService(Element input,
            MALInteraction interaction) throws MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(ErrorTestSkeleton skeleton);
}
