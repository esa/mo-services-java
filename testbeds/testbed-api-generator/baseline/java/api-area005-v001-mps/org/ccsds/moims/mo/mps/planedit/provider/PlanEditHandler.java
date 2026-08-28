package org.ccsds.moims.mo.mps.planedit.provider;

/**
 * Interface that providers of the PlanEdit service must implement to handle
 * the operations of that service.
 */
public interface PlanEditHandler {

    /**
     * Implements the operation updatePlanStatus.
     * 
     * @param planRef The planRef field.
     * @param status The status field.
     * @param isAlternate The isAlternate field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void updatePlanStatus(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.PlanStatusEnum status,
            Boolean isAlternate,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation insertActivity.
     * 
     * @param activityDetails The activityDetails field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mps.InsertFailedException The insertActivity or insertEvent operation failed to insert the requested object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> insertActivity(org.ccsds.moims.mo.mps.structures.InsertedActivityDetails activityDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mps.InsertFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation insertEvent.
     * 
     * @param eventDetails The eventDetails field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.InsertFailedException The insertActivity or insertEvent operation failed to insert the requested object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> insertEvent(org.ccsds.moims.mo.mps.structures.InsertedEventDetails eventDetails,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.InsertFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation deleteActivity.
     * 
     * @param planRef The planRef field.
     * @param activityRef The activityRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.DeleteFailedException The deleteActivity or deleteEvent operation failed to delete the requested object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void deleteActivity(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.ActivityInstance> activityRef,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.DeleteFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation deleteEvent.
     * 
     * @param planRef The planRef field.
     * @param eventRef The eventRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.DeleteFailedException The deleteActivity or deleteEvent operation failed to delete the requested object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void deleteEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.EventInstance> eventRef,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.DeleteFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateActivity.
     * 
     * @param planRef The planRef field.
     * @param activityUpdate The activityUpdate field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void updateActivity(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ActivityUpdate activityUpdate,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateEvent.
     * 
     * @param planRef The planRef field.
     * @param eventUpdate The eventUpdate field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void updateEvent(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.EventUpdate eventUpdate,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateResourceValue.
     * 
     * @param planRef The planRef field.
     * @param resourceUpdate The resourceUpdate field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void updateResourceValue(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ResourceUpdate resourceUpdate,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation updateResourceProfile.
     * 
     * @param planRef The planRef field.
     * @param resourceProfile The resourceProfile field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void updateResourceProfile(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mps.structures.ResourceProfile resourceProfile,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UnsupportedException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation applyTimeShift.
     * 
     * @param planRef The planRef field.
     * @param subPlans The subPlans field.
     * @param timePeriod The timePeriod field.
     * @param offset The offset field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mps.InvalidException One or more fields in the message contain invalid values.
     * @throws org.ccsds.moims.mo.mps.UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void applyTimeShift(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.Plan> planRef,
            org.ccsds.moims.mo.mal.structures.IdentifierList subPlans,
            org.ccsds.moims.mo.mps.structures.TimeWindow timePeriod,
            org.ccsds.moims.mo.mal.structures.Duration offset,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mps.InvalidException, org.ccsds.moims.mo.mps.UpdateFailedException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mps.planedit.provider.PlanEditSkeleton skeleton);
}
