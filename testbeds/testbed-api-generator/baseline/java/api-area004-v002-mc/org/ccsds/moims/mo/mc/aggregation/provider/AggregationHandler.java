package org.ccsds.moims.mo.mc.aggregation.provider;

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
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.AggregationValueList getValue(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getReportingConfiguration.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ReportConfigurationList getReportingConfiguration(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableReporting.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void enableReporting(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation disableReporting.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void disableReporting(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation setReportingPeriod.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param reportInterval The reportInterval field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mc.InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void setReportingPeriod(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mal.structures.Duration reportInterval,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mc.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listDefinition.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.AggregationDefinitionList listDefinition(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation addAggregation.
     * 
     * @param newObjects The newObjects field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mc.DuplicateException The entry or operation is a duplicate of an existing record, violating uniqueness.
     * @throws org.ccsds.moims.mo.mc.InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void addAggregation(org.ccsds.moims.mo.mc.structures.AggregationDefinitionList newObjects,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mc.DuplicateException, org.ccsds.moims.mo.mc.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation removeAggregation.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mc.AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void removeAggregation(org.ccsds.moims.mo.mal.structures.IdentifierList domain,
            org.ccsds.moims.mo.mal.structures.IdentifierList keys,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mc.AmbiguousException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.aggregation.provider.AggregationSkeleton skeleton);
}
