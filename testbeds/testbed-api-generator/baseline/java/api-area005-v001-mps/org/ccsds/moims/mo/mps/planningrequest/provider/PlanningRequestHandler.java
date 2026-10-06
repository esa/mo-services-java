package org.ccsds.moims.mo.mps.planningrequest.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mps.CancelFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.UpdateFailedException;
import org.ccsds.moims.mo.mps.structures.PlanningRequestDetails;
import org.ccsds.moims.mo.mps.structures.PlanningRequestResponse;
import org.ccsds.moims.mo.mps.structures.RequestFilter;
import org.ccsds.moims.mo.mps.structures.RequestInstance;
import org.ccsds.moims.mo.mps.structures.RequestSummaryStatusList;

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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws MALException if there is an implementation exception
     */
    PlanningRequestResponse submitRequest(PlanningRequestDetails requestDetails,
            MALInteraction interaction) throws InvalidException, UnsupportedException, MALException;
    /**
     * Implements the operation getRequestSummaries.
     * 
     * @param requestFilter The requestFilter field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    RequestSummaryStatusList getRequestSummaries(RequestFilter requestFilter,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getRequestStatus.
     * 
     * @param requestRefs The requestRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void getRequestStatus(ObjectRefList requestRefs,
            GetRequestStatusInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation cancelRequest.
     * 
     * @param requestRef The requestRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws CancelFailedException The cancelRequest operation failed to cancel the referenced RequestInstance.
     * @throws MALException if there is an implementation exception
     */
    void cancelRequest(ObjectRef<RequestInstance> requestRef,
            MALInteraction interaction) throws InvalidException, CancelFailedException, MALException;
    /**
     * Implements the operation updateRequest.
     * 
     * @param requestRef The requestRef field.
     * @param requestDetails The requestDetails field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALException if there is an implementation exception
     */
    PlanningRequestResponse updateRequest(ObjectRef<RequestInstance> requestRef,
            PlanningRequestDetails requestDetails,
            MALInteraction interaction) throws InvalidException, UnsupportedException, UpdateFailedException, MALException;
    /**
     * Implements the operation getRequest.
     * 
     * @param requestRefs The requestRefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void getRequest(ObjectRefList requestRefs,
            GetRequestInteraction interaction) throws InvalidException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(PlanningRequestSkeleton skeleton);
}
