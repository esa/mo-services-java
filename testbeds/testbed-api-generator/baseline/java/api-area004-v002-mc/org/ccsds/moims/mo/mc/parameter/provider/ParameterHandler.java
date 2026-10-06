package org.ccsds.moims.mo.mc.parameter.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.ReadOnlyException;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

/**
 * Interface that providers of the Parameter service must implement to handle
 * the operations of that service.
 */
public interface ParameterHandler {

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
    ParameterValueList getValue(IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws UnknownException, AmbiguousException, MALException;
    /**
     * Implements the operation setValue.
     * 
     * @param domain The domain field.
     * @param keys The keys field.
     * @param newRawValues The newRawValues field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Operation specific.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws ReadOnlyException The operation attempted to modify read-only data, which cannot be changed.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    void setValue(IdentifierList domain,
            IdentifierList keys,
            NullableAttributeList newRawValues,
            MALInteraction interaction) throws UnknownException, InvalidException, ReadOnlyException, AmbiguousException, MALException;
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
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws AmbiguousException The data or operation is ambiguous, requiring clarification to proceed.
     * @throws MALException if there is an implementation exception
     */
    void setReportingPeriod(IdentifierList domain,
            IdentifierList keys,
            Duration reportInterval,
            MALInteraction interaction) throws UnknownException, InvalidException, AmbiguousException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(ParameterSkeleton skeleton);
}
