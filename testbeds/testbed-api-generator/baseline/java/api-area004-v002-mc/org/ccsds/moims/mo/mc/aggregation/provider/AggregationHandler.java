package org.ccsds.moims.mo.mc.aggregation.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.DuplicateException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
 * Interface that providers of the Aggregation service must implement to handle
 * the operations of that service.
 */
public interface AggregationHandler {

    /**
     * Implements the operation getValue.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    AggregationValueList getValue(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation getReportingConfiguration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    ReportConfigurationList getReportingConfiguration(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation enableReporting.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    void enableReporting(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation disableReporting.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    void disableReporting(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation setReportingPeriod.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param reportInterval The reportInterval field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws MALException if there is an implementation exception
     */
    void setReportingPeriod(IdentifierList domain,
            IdentifierList keys,
            Duration reportInterval,
            MALInteraction interaction) throws UnknownException, AmbiguousException, InvalidException, MALException;
    /**
     * Implements the operation listDefinition.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    AggregationDefinitionList listDefinition(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation addAggregation.
     * 
     * @param newObjects The newObjects field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws DuplicateException The entry or operation is a duplicate of an existing record, violating uniqueness.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws MALException if there is an implementation exception
     */
    void addAggregation(AggregationDefinitionList newObjects,
            MALInteraction interaction) throws DuplicateException, InvalidException, MALException;
    /**
     * Implements the operation removeAggregation.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Operation specific.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    void removeAggregation(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(AggregationSkeleton skeleton);
}
