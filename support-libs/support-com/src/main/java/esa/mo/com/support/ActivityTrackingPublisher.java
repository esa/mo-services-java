/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Support library
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.com.support;

import esa.mo.mal.support.BaseMalServer;
import esa.mo.mal.support.StructureHelper;
import java.util.logging.Level;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingHelper;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateType;

/**
 * This class provides a set of helper methods for simplifying the generation of
 * Activity Tracking events.
 */
public class ActivityTrackingPublisher {

    /**
     * The COM Object number for ACCEPTANCE events.
     */
    public static final int OBJ_NO_ASE_ACCEPTANCE = 4;
    /**
     * The COM Object number for EXECUTION events.
     */
    public static final int OBJ_NO_ASE_EXECUTION = 5;
    /**
     * The COM Object number for MAL operation objects.
     */
    public static final int OBJ_NO_ASE_OPERATION_ACTIVITY = 6;
    /**
     * The COM Object name for ACCEPTANCE events.
     */
    public static final String OBJ_NO_ASE_ACCEPTANCE_STR = Integer.toString(OBJ_NO_ASE_ACCEPTANCE);
    /**
     * The COM Object name for EXECUTION events.
     */
    public static final String OBJ_NO_ASE_EXECUTION_STR = Integer.toString(OBJ_NO_ASE_EXECUTION);
    /**
     * The COM ObjectType for MAL Operation objects.
     */
    public static final ObjectType OPERATION_ACTIVITY_OBJECT_TYPE = new ObjectType(
            COMHelper.COM_AREA_NUMBER,
            ActivityTrackingHelper.ACTIVITYTRACKING_SERVICE_NUMBER,
            COMHelper.COM_AREA_VERSION,
            new UShort(OBJ_NO_ASE_OPERATION_ACTIVITY));

    private final EventServiceProvider eventProvider;
    private int instanceIdentifier = 0;

    /**
     * Constructor.
     *
     * @param eventProvider The event service provider used to publish activity
     * tracking events.
     */
    public ActivityTrackingPublisher(final EventServiceProvider eventProvider) {
        this.eventProvider = eventProvider;
    }

    /**
     * Constructor.
     *
     * @param eventProvider The event service provider used to publish activity
     * tracking events.
     * @param nextInstanceIdentifier The next object instance identifier to use.
     */
    public ActivityTrackingPublisher(final EventServiceProvider eventProvider,
            final int nextInstanceIdentifier) {
        this.eventProvider = eventProvider;
        this.instanceIdentifier = nextInstanceIdentifier;
    }

    /**
     * Publishes an Acceptance event for a MAL interaction and constructs the
     * source object from the interaction details.
     *
     * @param interaction The MAL interaction to publish the event for.
     * @param success Success value for the activity tracking event.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishAcceptanceEventOperation(final MALInteraction interaction,
            final boolean success) throws MALInteractionException, MALException {
        // create source
        ObjectId source = new ObjectId();
        source.setType(OPERATION_ACTIVITY_OBJECT_TYPE);
        BaseMalServer.LOGGER.log(Level.FINE,
                "ActivityTracking:publishAcceptance source = {0}", source);

        ObjectKey key = new ObjectKey();
        key.setDomain(interaction.getMessageHeader().getDomain());
        key.setInstId(interaction.getMessageHeader().getTransactionId());
        if (interaction.getMessageHeader().getTransactionId() == null) {
            BaseMalServer.LOGGER.fine("ActivityTracking:getTransactionId = NULL");
        }
        BaseMalServer.LOGGER.log(Level.FINE, "ActivityTracking:key = {0}", key);
        source.setKey(key);

        publishAcceptanceEvent(interaction, success, source);
    }

    /**
     * Publishes an Acceptance event for a MAL interaction using the supplied
     * source object.
     *
     * @param interaction The MAL interaction to publish the event for.
     * @param success Success value for the activity tracking event.
     * @param source The COM object to use as the source for the event.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishAcceptanceEvent(final MALInteraction interaction,
            final boolean success, final ObjectId source) throws MALInteractionException, MALException {
        BaseMalServer.LOGGER.log(Level.FINE,
                "ActivityTracking:publishAcceptance malInter = {0} source {1}",
                new Object[]{interaction, source});

        // Produce ActivityTransferList
        ActivityAcceptance aa = new ActivityAcceptance();
        aa.setSuccess(success);

        // Produce ObjectDetails
        ObjectDetails objDetails = new ObjectDetails();
        objDetails.setRelated(null);
        objDetails.setSource(source);

        // Produce header
        final EntityKey ekey = new EntityKey(
                new Identifier(OBJ_NO_ASE_ACCEPTANCE_STR),
                ComStructureHelper.generateSubKey(
                        COMHelper._COM_AREA_NUMBER,
                        ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER,
                        COMHelper._COM_AREA_VERSION,
                        0),
                (long) instanceIdentifier++,
                ComStructureHelper.generateSubKey(source.getType()));
        BaseMalServer.LOGGER.log(Level.FINE, "ActivityTracking:eKey = {0}", ekey);

        UpdateHeader uh = new UpdateHeader(StructureHelper.getTimestampMillis(),
                interaction.getMessageHeader().getURITo(), UpdateType.DELETION, ekey);

        // We can now publish the event
        eventProvider.publishSingleEvent(uh, objDetails, aa);
    }

    /**
     * Publishes an Execution event for a Submit interaction.
     *
     * @param interaction The MAL interaction to publish the event for.
     * @param success Success value for the activity tracking event.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishExecutionEventSubmitAck(final MALInteraction interaction,
            final boolean success) throws MALInteractionException, MALException {
        // log warning if not correct interaction type
        if (InteractionType._SUBMIT_INDEX != interaction.getMessageHeader().getInteractionType().getOrdinal()) {
            BaseMalServer.LOGGER.log(Level.WARNING,
                    "ActivityTracking:raising SUBMIT execution event for interaction of type {0}",
                    interaction.getMessageHeader().getInteractionType().toString());
        }

        publishExecutionEventOperation(interaction, success, 1, 1);
    }

    /**
     * Publishes an Execution event for a Request interaction.
     *
     * @param interaction The MAL interaction to publish the event for.
     * @param success Success value for the activity tracking event.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishExecutionEventRequestResponse(final MALInteraction interaction,
            final boolean success) throws MALInteractionException, MALException {
        // log warning if not correct interaction type
        if (InteractionType._REQUEST_INDEX != interaction.getMessageHeader().getInteractionType().getOrdinal()) {
            BaseMalServer.LOGGER.log(Level.WARNING,
                    "ActivityTracking:raising REQUEST execution event for interaction of type {0}",
                    interaction.getMessageHeader().getInteractionType().toString());
        }

        publishExecutionEventOperation(interaction, success, 1, 1);
    }

    /**
     * Publishes an Execution event for an Invoke Ack interaction.
     *
     * @param interaction The MAL interaction to publish the event for.
     * @param success Success value for the activity tracking event.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishExecutionEventInvokeAck(final MALInteraction interaction,
            final boolean success) throws MALInteractionException, MALException {
        // log warning if not correct interaction type
        if (InteractionType._INVOKE_INDEX != interaction.getMessageHeader().getInteractionType().getOrdinal()) {
            BaseMalServer.LOGGER.log(Level.WARNING,
                    "ActivityTracking:raising INVOKE execution event for interaction of type {0}",
                    interaction.getMessageHeader().getInteractionType().toString());
        }

        publishExecutionEventOperation(interaction, success, 1, 2);
    }

    /**
     * Publishes an Execution event for an Invoke response interaction.
     *
     * @param interaction The MAL interaction to publish the event for.
     * @param success Success value for the activity tracking event.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishExecutionEventInvokeResponse(final MALInteraction interaction,
            final boolean success) throws MALInteractionException, MALException {
        // log warning if not correct interaction type
        if (InteractionType._INVOKE_INDEX != interaction.getMessageHeader().getInteractionType().getOrdinal()) {
            BaseMalServer.LOGGER.log(Level.WARNING,
                    "ActivityTracking:raising INVOKE execution event for interaction of type {0}",
                    interaction.getMessageHeader().getInteractionType().toString());
        }

        publishExecutionEventOperation(interaction, success, 2, 2);
    }

    /**
     * Publishes an Execution event for a MAL interaction using the supplied
     * stage count and index values.
     *
     * @param interaction The MAL interaction to publish the event for.
     * @param success Success value for the activity tracking event.
     * @param currentStageCount The current stage for this event
     * @param totalStageCount The total number of stage to be raised
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishExecutionEventOperation(final MALInteraction interaction,
            final boolean success, final int currentStageCount,
            final int totalStageCount) throws MALInteractionException, MALException {
        BaseMalServer.LOGGER.log(Level.FINE,
                "ActivityTracking:publishexecution malInter = {0}", interaction);
        if (interaction.getMessageHeader().getTransactionId() == null) {
            BaseMalServer.LOGGER.fine("ActivityTracking:getTransactionId = NULL");
        }

        ObjectId source = new ObjectId(OPERATION_ACTIVITY_OBJECT_TYPE,
                new ObjectKey(interaction.getMessageHeader().getDomain(),
                        interaction.getMessageHeader().getTransactionId()));

        publishExecutionEvent(interaction.getMessageHeader().getURITo(),
                success,
                currentStageCount,
                totalStageCount,
                source);
    }

    /**
     * Publishes an Execution event using the supplied values.
     *
     * @param eventSourceURI The URI to use as the source of the event.
     * @param success Success value for the activity tracking event.
     * @param currentStageCount The current stage for this event
     * @param totalStageCount The total number of stage to be raised
     * @param source The COM object to use as the source for the event.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishExecutionEvent(final URI eventSourceURI, final boolean success,
            final int currentStageCount, final int totalStageCount,
            final ObjectId source) throws MALInteractionException, MALException {
        BaseMalServer.LOGGER.log(Level.FINE,
                "ActivityTracking:publishexecution to ({0}), source ({1})",
                new Object[]{eventSourceURI, source});

        // Produce header
        final EntityKey ekey = new EntityKey(
                new Identifier(OBJ_NO_ASE_EXECUTION_STR),
                ComStructureHelper.generateSubKey(
                        COMHelper._COM_AREA_NUMBER,
                        ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER,
                        COMHelper._COM_AREA_VERSION,
                        0),
                (long) instanceIdentifier++,
                ComStructureHelper.generateSubKey(source.getType()));

        BaseMalServer.LOGGER.log(Level.FINE,
                "ActivityTracking:publishexecution ekey = {0}", ekey);

        UpdateHeader uh = new UpdateHeader(StructureHelper.getTimestampMillis(),
                eventSourceURI, UpdateType.DELETION, ekey);

        // Produce ActivityTransferList
        ActivityExecution activityExecutionInstance = new ActivityExecution();
        activityExecutionInstance.setExecutionStage(new UInteger(currentStageCount)); // TBD
        activityExecutionInstance.setStageCount(new UInteger(totalStageCount));
        activityExecutionInstance.setSuccess(success);

        // Produce ObjectDetails
        ObjectDetails objDetails = new ObjectDetails();
        objDetails.setRelated(null);
        objDetails.setSource(source);

        // We can now publish the event
        eventProvider.publishSingleEvent(uh, objDetails, activityExecutionInstance);
    }
}
