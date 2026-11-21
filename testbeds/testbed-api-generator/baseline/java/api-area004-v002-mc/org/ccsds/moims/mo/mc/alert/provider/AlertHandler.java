package org.ccsds.moims.mo.mc.alert.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;

/**
 * Interface that providers of the Alert service must implement to handle
 * the operations of that service.
 */
public interface AlertHandler {

    /**
     * Implements the operation getAlertConfiguration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    AlertConfigurationList getAlertConfiguration(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation enableGeneration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    void enableGeneration(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation disableGeneration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    void disableGeneration(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(AlertSkeleton skeleton);
}
