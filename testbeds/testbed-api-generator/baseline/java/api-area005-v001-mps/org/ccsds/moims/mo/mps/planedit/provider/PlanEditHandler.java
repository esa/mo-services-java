package org.ccsds.moims.mo.mps.planedit.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mps.DeleteFailedException;
import org.ccsds.moims.mo.mps.InsertFailedException;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.UnsupportedException;
import org.ccsds.moims.mo.mps.UpdateFailedException;
import org.ccsds.moims.mo.mps.structures.ActivityInstance;
import org.ccsds.moims.mo.mps.structures.ActivityUpdate;
import org.ccsds.moims.mo.mps.structures.EventInstance;
import org.ccsds.moims.mo.mps.structures.EventUpdate;
import org.ccsds.moims.mo.mps.structures.InsertedActivityDetails;
import org.ccsds.moims.mo.mps.structures.InsertedEventDetails;
import org.ccsds.moims.mo.mps.structures.Plan;
import org.ccsds.moims.mo.mps.structures.PlanStatusEnum;
import org.ccsds.moims.mo.mps.structures.ResourceProfile;
import org.ccsds.moims.mo.mps.structures.ResourceUpdate;
import org.ccsds.moims.mo.mps.structures.TimeWindow;

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
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALException if there is an implementation exception
     */
    void updatePlanStatus(ObjectRef<Plan> planRef,
            PlanStatusEnum status,
            Boolean isAlternate,
            MALInteraction interaction) throws InvalidException, UpdateFailedException, MALException;
    /**
     * Implements the operation insertActivity.
     * 
     * @param activityDetails The activityDetails field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws InsertFailedException The insertActivity or insertEvent operation failed to insert the requested object.
     * @throws MALException if there is an implementation exception
     */
    ObjectRef<ActivityInstance> insertActivity(InsertedActivityDetails activityDetails,
            MALInteraction interaction) throws InvalidException, UnsupportedException, InsertFailedException, MALException;
    /**
     * Implements the operation insertEvent.
     * 
     * @param eventDetails The eventDetails field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws InsertFailedException The insertActivity or insertEvent operation failed to insert the requested object.
     * @throws MALException if there is an implementation exception
     */
    ObjectRef<EventInstance> insertEvent(InsertedEventDetails eventDetails,
            MALInteraction interaction) throws InvalidException, InsertFailedException, MALException;
    /**
     * Implements the operation deleteActivity.
     * 
     * @param planRef The planRef field.
     * @param activityRef The activityRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeleteFailedException The deleteActivity or deleteEvent operation failed to delete the requested object.
     * @throws MALException if there is an implementation exception
     */
    void deleteActivity(ObjectRef<Plan> planRef,
            ObjectRef<ActivityInstance> activityRef,
            MALInteraction interaction) throws InvalidException, DeleteFailedException, MALException;
    /**
     * Implements the operation deleteEvent.
     * 
     * @param planRef The planRef field.
     * @param eventRef The eventRef field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws DeleteFailedException The deleteActivity or deleteEvent operation failed to delete the requested object.
     * @throws MALException if there is an implementation exception
     */
    void deleteEvent(ObjectRef<Plan> planRef,
            ObjectRef<EventInstance> eventRef,
            MALInteraction interaction) throws InvalidException, DeleteFailedException, MALException;
    /**
     * Implements the operation updateActivity.
     * 
     * @param planRef The planRef field.
     * @param activityUpdate The activityUpdate field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALException if there is an implementation exception
     */
    void updateActivity(ObjectRef<Plan> planRef,
            ActivityUpdate activityUpdate,
            MALInteraction interaction) throws InvalidException, UpdateFailedException, MALException;
    /**
     * Implements the operation updateEvent.
     * 
     * @param planRef The planRef field.
     * @param eventUpdate The eventUpdate field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALException if there is an implementation exception
     */
    void updateEvent(ObjectRef<Plan> planRef,
            EventUpdate eventUpdate,
            MALInteraction interaction) throws InvalidException, UpdateFailedException, MALException;
    /**
     * Implements the operation updateResourceValue.
     * 
     * @param planRef The planRef field.
     * @param resourceUpdate The resourceUpdate field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALException if there is an implementation exception
     */
    void updateResourceValue(ObjectRef<Plan> planRef,
            ResourceUpdate resourceUpdate,
            MALInteraction interaction) throws InvalidException, UnsupportedException, UpdateFailedException, MALException;
    /**
     * Implements the operation updateResourceProfile.
     * 
     * @param planRef The planRef field.
     * @param resourceProfile The resourceProfile field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UnsupportedException An optional data structure used in the message is not supported by the service provider.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALException if there is an implementation exception
     */
    void updateResourceProfile(ObjectRef<Plan> planRef,
            ResourceProfile resourceProfile,
            MALInteraction interaction) throws InvalidException, UnsupportedException, UpdateFailedException, MALException;
    /**
     * Implements the operation applyTimeShift.
     * 
     * @param planRef The planRef field.
     * @param subPlans The subPlans field.
     * @param timePeriod The timePeriod field.
     * @param offset The offset field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws UpdateFailedException The update operation (to Request, PlanStatus, Activity, Event or Resource) failed to update the referenced object.
     * @throws MALException if there is an implementation exception
     */
    void applyTimeShift(ObjectRef<Plan> planRef,
            IdentifierList subPlans,
            TimeWindow timePeriod,
            Duration offset,
            MALInteraction interaction) throws InvalidException, UpdateFailedException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(PlanEditSkeleton skeleton);
}
