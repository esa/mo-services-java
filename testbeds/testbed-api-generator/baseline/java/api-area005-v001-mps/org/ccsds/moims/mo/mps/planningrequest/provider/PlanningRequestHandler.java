package org.ccsds.moims.mo.mps.planningrequest.provider;

/**
 * Interface that providers of the PlanningRequest service must implement
 * to handle the operations of that service.
 */
public interface PlanningRequestHandler {

    /**
     * Implements the operation submitRequest.
     * 
     * @param requestDetails The requestDetails field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.PlanningRequestResponse submitRequest(org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getRequestSummaries.
     * 
     * @param requestFilter The requestFilter field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList getRequestSummaries(org.ccsds.moims.mo.mps.structures.RequestFilter requestFilter,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getRequestStatus.
     * 
     * @param requestRefs The requestRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void getRequestStatus(org.ccsds.moims.mo.mal.structures.ObjectRefList requestRefs,
            org.ccsds.moims.mo.mps.planningrequest.provider.GetRequestStatusInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation cancelRequest.
     * 
     * @param requestRef The requestRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.CancelFailedException The cancelRequest operation failed to cancel the referenced RequestInstance.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void cancelRequest(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> requestRef,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.CancelFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateRequest.
     * 
     * @param requestRef The requestRef field.
     * @param requestDetails The requestDetails field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mps.structures.PlanningRequestResponse updateRequest(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> requestRef,
            org.ccsds.moims.mo.mps.structures.PlanningRequestDetails requestDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation getRequest.
     * 
     * @param requestRefs The requestRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void getRequest(org.ccsds.moims.mo.mal.structures.ObjectRefList requestRefs,
            org.ccsds.moims.mo.mps.planningrequest.provider.GetRequestInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mps.planningrequest.provider.PlanningRequestSkeleton skeleton);
}
