package org.ccsds.moims.mo.mps.planinformationmanagement.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mps.InvalidException;
import org.ccsds.moims.mo.mps.structures.ActivityDefinitionList;
import org.ccsds.moims.mo.mps.structures.EventDefinitionList;
import org.ccsds.moims.mo.mps.structures.RequestDefinitionList;
import org.ccsds.moims.mo.mps.structures.ResourceList;

/**
 * Interface that providers of the PlanInformationManagement service must
 * implement to handle the operations of that service.
 */
public interface PlanInformationManagementHandler {

    /**
     * Implements the operation listRequestDefs.
     * 
     * @param domain The domain field.
     * @param requestDefs The requestDefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void listRequestDefs(IdentifierList domain,
            ObjectRefList requestDefs,
            ListRequestDefsInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getRequestDefs.
     * 
     * @param requestDefs The requestDefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    RequestDefinitionList getRequestDefs(ObjectRefList requestDefs,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation listEventDefs.
     * 
     * @param domain The domain field.
     * @param eventDefs The eventDefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void listEventDefs(IdentifierList domain,
            ObjectRefList eventDefs,
            ListEventDefsInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getEventDefs.
     * 
     * @param eventDefs The eventDefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    EventDefinitionList getEventDefs(ObjectRefList eventDefs,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation listActivityDefs.
     * 
     * @param domain The domain field.
     * @param activityDefs The activityDefs field.
     * @param defaultTags The defaultTags field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void listActivityDefs(IdentifierList domain,
            ObjectRefList activityDefs,
            StringList defaultTags,
            ListActivityDefsInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getActivityDefs.
     * 
     * @param activityDefs The activityDefs field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    ActivityDefinitionList getActivityDefs(ObjectRefList activityDefs,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation listResourceDefs.
     * 
     * @param domain The domain field.
     * @param dataType The dataType field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    void listResourceDefs(IdentifierList domain,
            AttributeTypeList dataType,
            ListResourceDefsInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation getResourceDefs.
     * 
     * @param resources The resources field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException One or more fields in the message contain invalid values.
     * @throws MALException if there is an implementation exception
     */
    ResourceList getResourceDefs(ObjectRefList resources,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(PlanInformationManagementSkeleton skeleton);
}
