package org.ccsds.moims.mo.mc.statistic.provider;

/**
 * Interface that providers of the Statistic service must implement to handle
 * the operations of that service.
 */
public interface StatisticHandler {

    /**
     * Implements the operation getStatistics.
     * 
     * @param funcObjInstIds The funcObjInstIds field shall include a list of StatisticFunction object instance identifiers to match.
The funcObjInstIds field shall support the wildcard value of '0' and will match all functions of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
     * @param isGroup isGroup Argument number 1 as defined by the service operation
     * @param paramObjInstIds If the isGroup field is TRUE then the paramObjInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain ParameterIdentity object instance identifiers.
If the isGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain ParameterIdentity objects otherwise an INVALID error shall be returned.
The ParameterIdentity objects referenced, either directly or indirectly via groups, by the paramObjInstIds field shall be the parameters to match.
The paramObjInstIds field shall support the wildcard value of '0' and matches all parameters of the provider matched to the functions given in the funcObjInstIds field.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested function, group or parameters is unknown then an UNKNOWN error shall be returned.
The sets of matched StatisticFunction objects and ParameterIdentity objects shall be matched to the set of existing StatisticLink objects to determine which StatisticLink objects to report on.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested groups or parameters do not exist in the provider or statistic functions is not supported by the provider.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied groups is not a group of groups or ParameterIdentity objects.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList getStatistics(org.ccsds.moims.mo.mal.structures.LongList funcObjInstIds,
            Boolean isGroup,
            org.ccsds.moims.mo.com.structures.ObjectKeyList paramObjInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation resetEvaluation.
     * 
     * @param isStatLinkGroup If the isStatLinkGroup field is TRUE then the objInstIds field shall contain GroupIdentity object instance identifiers, otherwise the field shall contain StatisticFunction object instance identifiers.
If the isStatLinkGroup field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.
The StatisticLink objects referenced, either indirectly via statistic functions or indirectly via groups, by the objInstIds field shall be the StatisticLink objects to match.
     * @param objInstIds The objInstIds field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a requested function or group is unknown then an UNKNOWN error shall be returned.
     * @param returnLatestEval If the returnLatestEval Boolean field is TRUE then the latest evaluation result for each of the matched links shall be returned before resetting, otherwise a NULL is returned.
If an error is raised then no resetting of evaluations shall be made as a result of this operation call.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied groups is not a group of groups or StatisticLink objects.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested groups or functions is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.statistic.structures.StatisticEvaluationReportList resetEvaluation(Boolean isStatLinkGroup,
            org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            Boolean returnLatestEval,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableService.
     * 
     * @param enableService If enableService is set to TRUE the service shall be enabled and evaluation and reporting of statistics will be reset and commence.
If enableService is set to FALSE then all evaluation of statistics shall be suspended and no statistics will be reported.
If the enableService value matches the current enabled state of the service then no change shall be made and no error reported. Enabling an already enabled service has no effect.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void enableService(Boolean enableService,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getServiceStatus.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    Boolean getServiceStatus(org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation enableReporting.
     * 
     * @param isGroupIds If the isGroupIds field is TRUE then the enableInstances field shall contain GroupIdentity object instance identifiers, otherwise the field contains StatisticFunction object instance identifiers.
If the isGroupIds field is TRUE, the requested Group, or the Group objects referenced by that Group, must contain StatisticLink objects otherwise an INVALID error shall be returned.
The StatisticLink objects referenced, either indirectly via StatisticFunction objects or indirectly via groups, by the enableInstances field shall be the StatisticLink objects to match.
The id of the enableInstances field shall support the wildcard value of '0' and matches all StatisticLink objects of the provider.
The service provider shall check for the wildcard value in the list of object instance identifiers in the enableInstances field first and if found no other checks of supplied object instance identifiers shall be made.
If the enableInstances field contains a value of TRUE then reports after the reporting and collection intervals for matching StatisticLink objects shall be generated, a value of FALSE requests that reports will not be generated.
No error shall be raised if the enableInstances Boolean value supplied is the same as the current reportingEnabled field for a StatisticLink object i.e. enabling an already enabled link will not result in an error.
If a requested StatisticFunction or GroupIdentity object is unknown then an UNKNOWN error shall be returned.
If an error is raised then no modifications shall be made as a result of this operation call.
The provider should create and store a new StatisticLinkDefinition object in the COM archive if the reportingEnabled field is changed.
     * @param enableInstances If the generation of reports is being enabled, then the provider shall generate a report immediately and start the report interval from that report.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the requested StatisticFunction or Group objects is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException One of the supplied groups is not a group of groups or StatisticLink objects.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void enableReporting(Boolean isGroupIds,
            org.ccsds.moims.mo.com.structures.InstanceBooleanPairList enableInstances,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listParameterEvaluations.
     * 
     * @param statObjInstIds The statObjInstIds field shall hold a list of StatisticFunction object instance identifiers to retrieve the StatisticLink object instance identifiers for.
The request may contain the wildcard value of '0' to return all supported statistic links.
The wildcard value should be checked for first, if found no other checks of supplied identifiers shall be made.
If a provided identifier does not include a wildcard and does not match an existing StatisticFunction object then this operation shall fail with an UNKNOWN error.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the supplied identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkSummaryList listParameterEvaluations(org.ccsds.moims.mo.mal.structures.LongList statObjInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation addParameterEvaluation.
     * 
     * @param newDetails The newDetails field shall hold a StatisticCreationRequest for each new parameter to be sampled.
The statFuncInstId field of the StatisticCreationRequest shall reference the object instance identifier of the StatisticFunction to be used.
If the statFuncInstId field does not match an existing StatisticFunction then an UNKNOWN error shall be raised.
The parameterId shall reference the ParameterIdentity that the function is being applied to.
If the parameterId field does not match an existing ParameterIdentity then an UNKNOWN error shall be raised.
If the type of the matched parameter is not supported by the matched statistical function, for example Mean average of a String parameter, then an INVALID error shall be returned.
The samplingInterval field shall contain the sampling duration interval for the parameter.
If the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned.
If an error is raised then no new StatisticLink object shall be created and stored as a result of this operation call.
If no error is to be raised then StatisticLink and StatisticLinkDefinition objects shall be created for each function/parameter link and stored in the COM archive.
The referenced parameter shall be sampled immediately and the sampling, reporting and collection intervals started.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.InvalidException One or more of the supplied StatisticLink is either requesting an invalid sampling interval or invalid function for the request parameter.
     * @throws org.ccsds.moims.mo.mal.UnknownException One of the requested StatisticLink objects references either an unknown StatisticFunction object or an unknown ParameterIdentity object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mc.structures.ObjectInstancePairList addParameterEvaluation(org.ccsds.moims.mo.mc.statistic.structures.StatisticCreationRequestList newDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateParameterEvaluation.
     * 
     * @param linkIds The linkIds field shall contain the object instance identifiers of the StatisticLink objects to be updated.
If the linkIds list contains either NULL or '0' an INVALID error shall be raised.
The supplied object instance identifiers shall match existing link objects, an UNKNOWN error shall be raised if this is not the case.
If the supplied samplingInterval is not supported for the requested parameter then an INVALID error shall be returned.
     * @param newDetails The newDetails field shall contain the replacement StatisticLinkDetails.
The two lists shall be ordered the same.
The number of entries in the two lists shall be the same size otherwise an INVALID error shall be returned.
If an error is raised then no links shall be updated as a result of this operation call.
The provider shall create a new StatisticLinkDefinition object and store it in the COM archive.
If any of the intervals are updated then the service shall reset the relevant timer and use the new intervals immediately.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the supplied StatisticLink object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.com.InvalidException One or more of the supplied object instance identifiers list contains either a NULL or '0' or is requesting an invalid sampling interval for the request parameter or the two supplied lists are not the same length.
If the two lists are not the same length then the extra information field shall contain the first index of the element in the largest list which does not have corresponding element in the other list.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList updateParameterEvaluation(org.ccsds.moims.mo.mal.structures.LongList linkIds,
            org.ccsds.moims.mo.mc.statistic.structures.StatisticLinkDetailsList newDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation removeParameterEvaluation.
     * 
     * @param objInstIds The objInstIds field holds the object instance identifiers of the StatisticLink objects to be removed from the provider.
The list may contain the wildcard value of '0'.
The wildcard value should be checked for first, if found no other checks of supplied object instance identifiers shall be made.
If a provided StatisticLink object instance identifier does not include a wildcard and does not match an existing StatisticLink object then this operation shall fail with an UNKNOWN error.
Matched StatisticLink objects shall not be removed from the COM archive only the list of evaluated StatisticLink objects in the provider.
If an error is raised then no StatisticLink objects shall be removed as a result of this operation call.
If the operation succeeds then the provider shall not evaluate those parameter/function definition combinations for the deleted StatisticLink objects anymore.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.UnknownException One or more of the supplied StatisticLink object instance identifiers is unknown.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void removeParameterEvaluation(org.ccsds.moims.mo.mal.structures.LongList objInstIds,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.statistic.provider.StatisticSkeleton skeleton);
}
