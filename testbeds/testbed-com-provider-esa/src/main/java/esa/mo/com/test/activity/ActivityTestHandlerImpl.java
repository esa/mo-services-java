/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Testbed ESA provider
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
package esa.mo.com.test.activity;

import esa.mo.com.support.ComStructureHelper;
import java.util.Map;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution;
import org.ccsds.moims.mo.com.event.provider.MonitorEventPublisher;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.comprototype.activitytest.provider.InvokeInteraction;
import org.ccsds.moims.mo.comprototype.activitytest.provider.ProgressInteraction;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.com.test.util.COMTestHelper;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingServiceInfo;
import org.ccsds.moims.mo.comprototype.activitytest.provider.ActivityTestInheritanceSkeleton;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

public class ActivityTestHandlerImpl extends ActivityTestInheritanceSkeleton {
    // Define constants used in incoming string list - ther correspnd to execution stages

    private final String ACCEPTANCE_ERROR = "ACCEPTANCE_ERROR";
    private final String ACK_ERROR = "ACK_ERROR";
    private final String RESPONSE_ERROR = "RESPONSE_ERROR";
    private final String UPDATE = "UPDATE";
    private final String UPDATE_ERROR = "UPDATE_ERROR";
    private final TestServiceProvider testService;
    private MonitorEventPublisher monitorEventPublisher = null;
    private int instanceIdentifier = 0;

    public ActivityTestHandlerImpl(TestServiceProvider testService) {
        this.testService = testService;
    }

    public void resetTest(MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityTestHandlerImpl:resetTest");

        if (null == monitorEventPublisher) {
            LoggingBase.logMessage("ActivityTestHandlerImpl:creating event publisher");

            final IdentifierList domain = new IdentifierList();
            domain.add(new Identifier("esa"));
            domain.add(new Identifier("mission"));

            monitorEventPublisher = testService.getActivityEventPublisher().createMonitorEventPublisher(domain,
                    new Identifier("GROUND"),
                    SessionType.LIVE,
                    new Identifier("LIVE"),
                    QoSLevel.BESTEFFORT,
                    null,
                    new UInteger(0));

            /*
      final EntityKeyList lst = new EntityKeyList();
      lst.add(new EntityKey(new Identifier("*"), new Long(0), new Long(0), new Long(0)));
             */
            IdentifierList keys = new IdentifierList();
            keys.add(new Identifier("K1"));
            keys.add(new Identifier("K2"));
            keys.add(new Identifier("K3"));
            keys.add(new Identifier("K4"));

            monitorEventPublisher.register(keys, new AttributeTypeList(), new ActivityTestPublisher());
        }
    }

    public void send(StringList _String, MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityTestHandlerImpl:send " + _String);

        publishAcceptance(!_String.contains(ACCEPTANCE_ERROR), interaction);
    }

    public StringList request(StringList _String, MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityTestHandlerImpl:request " + _String);
        if (!_String.contains(ACCEPTANCE_ERROR)) {
            publishAcceptance(true, interaction);
            if ((_String.contains(ACK_ERROR))) {
                publishExecution(false, interaction, 1, 1);
                throw new MALInteractionException(new MOErrorException(new UInteger(0), null));
            } else {
                publishExecution(!_String.contains(RESPONSE_ERROR), interaction, 1, 1);
            }
        } else {
            publishAcceptance(false, interaction);
            throw new MALInteractionException(new MOErrorException(new UInteger(0), null));
        }
        return _String;
    }

    public void testSubmit(StringList _String, MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityTestHandlerImpl:testSubmit " + _String);
        if (!_String.contains(ACCEPTANCE_ERROR)) {
            publishAcceptance(true, interaction);
            if ((_String.contains(ACK_ERROR))) {
                publishExecution(false, interaction, 1, 1);
                throw new MALInteractionException(new MOErrorException(new UInteger(0), null));
            } else {
                publishExecution(true, interaction, 1, 1);
            }
        } else {
            publishAcceptance(false, interaction);
            throw new MALInteractionException(new MOErrorException(new UInteger(0), null));
        }
    }

    public void invoke(StringList _String, InvokeInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityTestHandlerImpl:invoke " + _String);
        if (_String.contains(ACCEPTANCE_ERROR)) {
            publishAcceptance(false, interaction.getInteraction());
            if (_String.contains(ACK_ERROR)) {
                publishExecution(false, interaction.getInteraction(), 1, 2);
            }
            // TBD error number to be specified
            interaction.sendError(new MOErrorException(new UInteger(0), null));
        } else if ((_String.contains(ACK_ERROR))) {
            publishAcceptance(true, interaction.getInteraction());
            publishExecution(false, interaction.getInteraction(), 1, 2);
            // TBD error number to be specified
            interaction.sendError(new MOErrorException(new UInteger(0), null));
        } else {
            publishAcceptance(true, interaction.getInteraction());
            publishExecution(true, interaction.getInteraction(), 1, 2);
            interaction.sendAcknowledgement(_String);
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
            }
            if (!_String.contains(RESPONSE_ERROR)) {
                publishExecution(true, interaction.getInteraction(), 2, 2);
                interaction.sendResponse(_String);
            } else {
                // TBD error number to be specified
                publishExecution(false, interaction.getInteraction(), 2, 2);
                interaction.sendError(new MOErrorException(new UInteger(0), null));
            }
        }
    }

    public void progress(StringList _String, ProgressInteraction interaction) throws MALInteractionException, MALException {
        boolean bUpdateErr = false;
        LoggingBase.logMessage("ActivityTestHandlerImpl:progress " + _String);
        int totalStageCount = noUpdates(_String) + 2; // 2 = 1 for ACK, 1 for RSP
        int currentStage = 1;
        if (_String.contains(ACCEPTANCE_ERROR)) {
            publishAcceptance(false, interaction.getInteraction());
            // TBD error number to be specified
            if (_String.contains(ACK_ERROR)) {
                publishExecution(false, interaction.getInteraction(), currentStage++, totalStageCount);
            }
            interaction.sendError(new MOErrorException(new UInteger(0), null));
        } else if ((_String.contains(ACK_ERROR))) {
            publishAcceptance(true, interaction.getInteraction());
            publishExecution(false, interaction.getInteraction(), currentStage++, totalStageCount);
            // TBD error number to be specified
            interaction.sendError(new MOErrorException(new UInteger(0), null));
        } else {
            publishExecution(true, interaction.getInteraction(), currentStage++, totalStageCount);
            publishAcceptance(true, interaction.getInteraction());
            interaction.sendAcknowledgement(_String);
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
            }
            // Send updates
            for (int i = 0; i < _String.size() && !bUpdateErr; i++) {
                if (_String.get(i).contains(UPDATE_ERROR)) {
                    LoggingBase.logMessage("ActivityTestHandlerImpl:progress - send update ERR");
                    publishExecution(false, interaction.getInteraction(), currentStage++, totalStageCount);
                    interaction.sendUpdateError(new MOErrorException(new UInteger(0), null));
                    bUpdateErr = true;
                } else if (_String.get(i).contains(UPDATE)) {
                    LoggingBase.logMessage("ActivityTestHandlerImpl:progress - send UPDATE");
                    publishExecution(true, interaction.getInteraction(), currentStage++, totalStageCount);
                    interaction.sendUpdate(_String);
                    try {
                        Thread.sleep(100);
                    } catch (Exception ex) {
                    }
                }
            }
            if (!bUpdateErr) {
                if (!_String.contains(RESPONSE_ERROR)) {
                    LoggingBase.logMessage("ActivityTestHandlerImpl:progress - send response");
                    publishExecution(true, interaction.getInteraction(), currentStage++, totalStageCount);
                    interaction.sendResponse(_String);
                } else {
                    LoggingBase.logMessage("ActivityTestHandlerImpl:progress - send response ERR");
                    publishExecution(false, interaction.getInteraction(), currentStage++, totalStageCount);
                    // TBD error number to be specified
                    interaction.sendError(new MOErrorException(new UInteger(0), null));
                }
            }
        }
    }

    public void close(MALInteraction interaction) throws MALInteractionException, MALException {
        // No actions required at the moment
    }

    // Generate a EntityKey subkey using fields as specified in COM STD 3.2.4.2b
    static protected Long generateSubKey(int area, int service, int version, int objectNumber) {
        long subkey = objectNumber;
        subkey = subkey | (((long) version) << 24);
        subkey = subkey | ((long) service << 32);
        subkey = subkey | ((long) area << 48);

        return new Long(subkey);
    }

    // Calculates number of update phases in an update list
    private int noUpdates(StringList _String) {
        int noUpdates = 0;

        for (int i = 0; i < _String.size(); i++) {
            if (_String.get(i).contains(UPDATE_ERROR) || _String.get(i).contains(UPDATE)) {
                ++noUpdates;
            }
        }
        return noUpdates;
    }

    private void publishAcceptance(boolean success, MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityTestHandlerImpl:publishAcceptance malInter = " + interaction);

        // Produce ActivityTransfer
        ActivityAcceptance aa = new ActivityAcceptance(success);

        ObjectKey key = new ObjectKey(new IdentifierList(),
                new Long(interaction.getMessageHeader().getTransactionId()));

        if (interaction.getMessageHeader().getTransactionId() == null) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:getTransactionId = NULL");
        }
        LoggingBase.logMessage("ActivityTestHandler:key = " + key);

        // Produce ObjectDetails
        ObjectId source = new ObjectId(COMTestHelper.getOperationActivityType(), key);
        ObjectDetails objDetails = new ObjectDetails(null, source);
        LoggingBase.logMessage("ActivityTestHandlerImpl:publishAcceptance source = " + source);

        /*
        final EntityKey ekey = new EntityKey(
            new Identifier(COMTestHelper.OBJ_NO_ASE_ACCEPTANCE_STR),
            generateSubKey(COMHelper._COM_AREA_NUMBER, 
                    ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, 
                    COMHelper._COM_AREA_VERSION, 0),
            new Long(instanceIdentifier++),
            generateSubKey(COMHelper._COM_AREA_NUMBER, 
                    ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, 
                    COMHelper._COM_AREA_VERSION, COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY));
         */
        AttributeList keyValues = new AttributeList();
        keyValues.add(new Identifier(COMTestHelper.OBJ_NO_ASE_ACCEPTANCE_STR));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                0)));
        keyValues.add(new Union((long) instanceIdentifier++));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY)));

        LoggingBase.logMessage("ActivityTestHandler: keyValues = " + keyValues);
        URI uri = interaction.getMessageHeader().getToURI();
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));

        // Produce header
        UpdateHeader uh = new UpdateHeader(new Identifier(uri.getValue()),
                domain, keyValues.getAsNullableAttributeList());

        // We can now publish the event
        monitorEventPublisher.publish(uh, objDetails, aa);

    }

    private void publishExecution(boolean success, MALInteraction interaction,
            int currentStageCount, int totalStageCount) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityTestHandlerImpl:publishexecution malInter = " + interaction);
        /*
    final EntityKey ekey = new EntityKey(
            new Identifier(COMTestHelper.OBJ_NO_ASE_EXECUTION_STR),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, 0),
            new Long(instanceIdentifier++),
            generateSubKey(COMHelper._COM_AREA_NUMBER, ActivityTrackingHelper._ACTIVITYTRACKING_SERVICE_NUMBER, COMHelper._COM_AREA_VERSION, COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY));
         */

        AttributeList keyValues = new AttributeList();
        keyValues.add(new Identifier(COMTestHelper.OBJ_NO_ASE_EXECUTION_STR));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                0)));
        keyValues.add(new Union((long) instanceIdentifier++));
        keyValues.add(new Union(ComStructureHelper.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY)));

        LoggingBase.logMessage("ActivityTestHandlerImpl:publishexecution keyValues = " + keyValues);
        URI uri = interaction.getMessageHeader().getToURI();
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));

        // Produce header
        UpdateHeader uh = new UpdateHeader(new Identifier(uri.getValue()),
                domain, keyValues.getAsNullableAttributeList());

        // Produce ActivityTransferList
        ActivityExecution activityExecutionInstance = new ActivityExecution(
                success, new UInteger(currentStageCount), new UInteger(totalStageCount));

        ObjectKey key = new ObjectKey(domain, interaction.getMessageHeader().getTransactionId());

        if (interaction.getMessageHeader().getTransactionId() == null) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:getTransactionId = NULL");
        }

        // Produce ObjectDetails
        ObjectId source = new ObjectId(COMTestHelper.getOperationActivityType(), key);
        ObjectDetails objDetails = new ObjectDetails(null, source);

        // We can now publish the event
        monitorEventPublisher.publish(uh, objDetails, activityExecutionInstance);
    }

    public static class ActivityTestPublisher implements MALPublishInteractionListener {

        @Override
        public void publishRegisterAckReceived(MALMessageHeader header, Map qosProperties) throws MALException {
        }

        @Override
        public void publishRegisterErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException {
        }

        @Override
        public void publishErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException {
            LoggingBase.logMessage("ActivityTestPublisher:publishErrorReceived - " + body.toString());
        }

        @Override
        public void publishDeregisterAckReceived(MALMessageHeader header, Map qosProperties) throws MALException {
        }
    }
}
