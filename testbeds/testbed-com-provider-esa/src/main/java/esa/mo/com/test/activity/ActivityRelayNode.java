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

import java.util.Hashtable;
import java.util.Map;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingServiceInfo;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.com.event.consumer.EventAdapter;
import org.ccsds.moims.mo.com.event.consumer.EventStub;
import org.ccsds.moims.mo.com.event.provider.MonitorEventPublisher;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.com.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.com.test.util.COMTestHelper;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.*;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class ActivityRelayNode {

    private static final Identifier ALL_ID = new Identifier("*");
    private static final Integer ALL_INT = 0;
    private final TestServiceProvider testService;
    private final ActivityRelayManagementHandlerImpl relayManager;
    private final String relayName;
    private final String relayTo;
    private MonitorEventPublisherSkeleton monitorEventPublisherSkeleton = null;
    private MonitorEventPublisher monitorEventPublisher = null;
    private ActivityTestPublisher activityTestPublisher = new ActivityTestPublisher();
    private EventStub evstub;
    private int instanceIdentifier = 0;
    private long instIdBaseOffset = 0;

    public ActivityRelayNode(TestServiceProvider testService, ActivityRelayManagementHandlerImpl relayManager,
            String protocol, String relayName, String relayTo) throws MALException {
        LoggingBase.logMessage("Starting relay " + relayName);

        this.testService = testService;
        this.relayManager = relayManager;
        this.relayName = relayName;
        this.relayTo = relayTo;

        setInstIdBaseOffset(relayName);
    }

    public void init() throws MALException {
    }

    public void relayMessage(StringList _String, MALInteraction interaction) throws MALInteractionException, MALException {
        if (containsStage("RECEPTION_ERROR", relayName, _String)) {
            publishReceptionOrForward(false, COMTestHelper.OBJ_NO_ASE_RECEPTION_STR, interaction);
            throw new MALInteractionException(new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER, null));
        } else if (containsStage("FORWARD_ERROR", relayName, _String)) {
            publishReceptionOrForward(true, COMTestHelper.OBJ_NO_ASE_RECEPTION_STR, interaction);
            publishReceptionOrForward(false, COMTestHelper.OBJ_NO_ASE_FORWARD_STR, interaction);
            throw new MALInteractionException(new MALStandardError(MALHelper.DESTINATION_LOST_ERROR_NUMBER, null));
        } else {
            publishReceptionOrForward(true, COMTestHelper.OBJ_NO_ASE_RECEPTION_STR, interaction);
            publishReceptionOrForward(true, COMTestHelper.OBJ_NO_ASE_FORWARD_STR, interaction);
            LoggingBase.logMessage("ActivityRelayNode:send send from " + relayName + " to " + relayTo);

            relayManager.passToRelay(relayTo, _String, interaction);
        }
    }

    protected void resetTest() throws MALException {
        LoggingBase.logMessage("ActivityRelayNode:resetTest");

        if (null == monitorEventPublisher) {
            createMonitorEventPublisher();
        }

        createMonitorEventListener();

        LoggingBase.logMessage("ActivityRelayNode:resetTest - Complete");
    }

    // Removes listeners such that relay no longer partcipates in test
    public void close() throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityRelayNode:close " + relayName);
        removeMonitorEventListener();
    }

    private static boolean containsStage(String stageName, String relayName, StringList arr) {
        String stage = stageName + "(" + relayName + ")";

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equalsIgnoreCase(stage)) {
                return true;
            }
        }

        return false;
    }

    private void createMonitorEventPublisher() throws MALException {
        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventPublisher");

        monitorEventPublisherSkeleton = new MonitorEventPublisherSkeleton();
        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventPublisher - publisher created\n");

        MALProviderManager malProviderMgr = testService.getDefaultProviderMgr();

        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventPublisher Create Provider\n");
        MALProvider malProvider = malProviderMgr.createProvider("MonitorEventPublisher - Activity Test - " + relayName,
                null,
                EventHelper.EVENT_SERVICE,
                new Blob("".getBytes()),
                monitorEventPublisherSkeleton,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1),
                null,
                true,
                null);
        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventPublisher - calling store UI\n");
        FileBasedDirectory.storeURI(LocalMALInstance.ACTIVITY_EVENT_NAME + relayName, malProvider.getURI(), malProvider.getBrokerURI());

        monitorEventPublisherSkeleton.malInitialize(malProvider);

        final IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));

        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventPublisher Create ME publisher");

        monitorEventPublisher = monitorEventPublisherSkeleton.createMonitorEventPublisher(domain,
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

        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventPublisher Reset X calling register");

        try {
            monitorEventPublisher.register(keys, activityTestPublisher);
        } catch (MALInteractionException ex) {
            // todo
            ex.printStackTrace();
        }
    }

    private void createMonitorEventListener() throws MALException {
        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventListener " + relayName);
        EventStub evStub = getEventStub();
        /*
    EntityKeyList ekl = new EntityKeyList();
    EntityRequestList erl = new EntityRequestList();
    ekl.add(new EntityKey(ALL_ID, new Long(ALL_INT), new Long(ALL_INT), new Long(ALL_INT)));
    erl.add(new EntityRequest(null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, ekl));
         */

        SubscriptionFilterList filters = new SubscriptionFilterList();
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));
        Subscription sub = new Subscription(new Identifier("Sub-" + relayName), domain, filters);
        try {
            evStub.monitorEventRegister(sub, new MonitorEventAdapter());
        } catch (MALInteractionException ex) {
            // todo
            ex.printStackTrace();
        }
        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventListener complete " + relayName);
    }

    private void removeMonitorEventListener() throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityRelayNode:removeMonitorEventListener " + relayName);
        IdentifierList idl = new IdentifierList();
        idl.add(new Identifier("Sub-" + relayName));
        getEventStub().monitorEventDeregister(idl);
        LoggingBase.logMessage("ActivityRelayNode:createMonitorEventListener complete " + relayName);
    }

    private EventStub getEventStub() throws MALException {
        LoggingBase.logMessage("ActivityRelayNode:getEventStub " + relayName);
        if (null == evstub) {
            String extraName = relayTo;

            if ("Provider".equalsIgnoreCase(extraName)) {
                extraName = "";
            }
            LoggingBase.logMessage("ActivityRelayNode:createEventStub "
                    + LocalMALInstance.ACTIVITY_EVENT_NAME + extraName);
            FileBasedDirectory.URIpair uris
                    = FileBasedDirectory.loadURIs(LocalMALInstance.ACTIVITY_EVENT_NAME + extraName); // Need relayFrom

            final IdentifierList domain = new IdentifierList();
            domain.add(new Identifier("esa"));
            domain.add(new Identifier("mission"));
            MALConsumer consumer = testService.getDefaultMal().createConsumerManager().createConsumer(
                    "ActivityTest" + relayName + " " + extraName,
                    uris.uri,
                    uris.broker,
                    EventHelper.EVENT_SERVICE,
                    new Blob("".getBytes()),
                    domain,
                    new Identifier("networkZone"),
                    SessionType.LIVE,
                    new Identifier("LIVE"),
                    QoSLevel.BESTEFFORT,
                    new Hashtable(), new UInteger(0));

            evstub = new EventStub(consumer);
        }

        return evstub;
    }

    private void publishReceptionOrForward(boolean withSuccess, String phase, MALInteraction interaction)
            throws MALInteractionException, MALException {
        MALMessageHeader srcMessage = interaction.getMessageHeader();

        LoggingBase.logMessage("publishReceptionOrForward " + relayName + " " + withSuccess);

        AttributeList keyValues = new AttributeList();
        keyValues.add(new Identifier(phase));
        keyValues.add(new Union(ActivityTestHandlerImpl.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                0)
        ));
        keyValues.add(new Long(instIdBaseOffset + (instanceIdentifier++)));
        keyValues.add(new Union(ActivityTestHandlerImpl.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY)));

        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));

        // Produce header
        UpdateHeader uh = new UpdateHeader(new Identifier(LocalMALInstance.ACTIVITY_EVENT_NAME + relayName), domain, keyValues);

        // Produce ActivityTransfer
        ActivityTransfer activityTransferInstance = new ActivityTransfer(withSuccess);

        ObjectKey key;

        if (srcMessage.getTransactionId() == null) {
            key = new ObjectKey(domain, null);
            LoggingBase.logMessage("ActivityRelayNode:getTransactionId = NULL");
        } else {
            key = new ObjectKey(domain, srcMessage.getTransactionId());
        }
        LoggingBase.logMessage("ActivityRelayNode:key = " + key);

        ObjectId source = new ObjectId(COMTestHelper.getOperationActivityType(), key);
        ObjectDetails objDetails = new ObjectDetails(null, source);

        // We can now publish the event
        monitorEventPublisher.publish(uh, objDetails, activityTransferInstance);
        LoggingBase.logMessage("ActivityRelayNode:publishReceptionStatus - END " + relayName);
    }

    private void setInstIdBaseOffset(String relayName) {
        if (relayName.toUpperCase().contains("RELAYA")) {
            instIdBaseOffset = 100000;
        } else if (relayName.toUpperCase().contains("RELAYB")) {
            instIdBaseOffset = 200000;
        } else if (relayName.toUpperCase().contains("RELAYC")) {
            instIdBaseOffset = 300000;
        } else if (relayName.toUpperCase().contains("RELAYD")) {
            instIdBaseOffset = 400000;
        } else if (relayName.toUpperCase().contains("RELAYE")) {
            instIdBaseOffset = 500000;
        } else {
            instIdBaseOffset = 900000;
        }
    }

    private class MonitorEventAdapter extends EventAdapter {

        /**
         * Called by the MAL when a PubSub update is received from a broker for
         * the operation monitorEvent.
         *
         * @param msgHeader The header of the received message.
         * @param _Identifier0 Argument number 0 as defined by the service
         * operation.
         * @param _UpdateHeaderList1 Argument number 1 as defined by the service
         * operation.
         * @param _ObjectDetailsList2 Argument number 2 as defined by the
         * service operation.
         * @param _ElementList3 Argument number 3 as defined by the service
         * operation.
         * @param qosProperties The QoS properties associated with the message.
         */
        @Override
        public void monitorEventNotifyReceived(MALMessageHeader msgHeader, Identifier _Identifier0,
                UpdateHeader updateHeader, ObjectDetails objectDetails,
                Element element, java.util.Map qosProperties) {
            LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived - NOTIFY");

            LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived - NOTIFY HDR" + msgHeader);
            LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived - NOTIFY ID0" + _Identifier0);
            LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived - NOTIFY HDR1" + updateHeader);
            LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived - NOTIFY ODL2" + objectDetails);
            LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived - NOTIFY EL3" + element);

            try {
                monitorEventPublisher.publish(updateHeader, objectDetails, element);
            } catch (MALInteractionException ex1) {
                LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived FAILURE " + ex1);
            } catch (MALException ex2) {
                LoggingBase.logMessage("ActivityRelayNode:monitorStatusNotifyReceived FAILURE " + ex2);
            }
        }

        /**
         * Called by the MAL when a PubSub register acknowledgement is received
         * from a broker for the operation monitorEvent.
         *
         * @param msgHeader The header of the received message.
         * @param qosProperties The QoS properties associated with the message.
         */
        @Override
        public void monitorEventRegisterAckReceived(MALMessageHeader msgHeader, java.util.Map qosProperties) {
            LoggingBase.logMessage("ActivityRelayNode:monitorEventRegisterAckReceived - ERROR");
        }

        /**
         * Called by the MAL when a PubSub register acknowledgement error is
         * received from a broker for the operation monitorEvent.
         *
         * @param msgHeader The header of the received message.
         * @param error The received error message.
         * @param qosProperties The QoS properties associated with the message.
         */
        @Override
        public void monitorEventRegisterErrorReceived(MALMessageHeader msgHeader, MALStandardError error,
                java.util.Map qosProperties) {
            LoggingBase.logMessage("ActivityRelayNode:monitorEventRegisterErrorReceived - ERROR");
        }

        /**
         * Called by the MAL when a PubSub deregister acknowledgement is
         * received from a broker for the operation monitorEvent.
         *
         * @param msgHeader The header of the received message.
         * @param qosProperties The QoS properties associated with the message.
         */
        @Override
        public void monitorEventDeregisterAckReceived(MALMessageHeader msgHeader, Map qosProperties) {
            LoggingBase.logMessage("ActivityRelayNode:monitorEventDeregisterAckReceived - ERROR");
        }

        /**
         * Called by the MAL when a PubSub update error is received from a
         * broker for the operation monitorEvent.
         *
         * @param msgHeader The header of the received message.
         * @param error The received error message.
         * @param qosProperties The QoS properties associated with the message.
         */
        @Override
        public void monitorEventNotifyErrorReceived(MALMessageHeader msgHeader, MALStandardError error, java.util.Map qosProperties) {
            LoggingBase.logMessage("ActivityRelayNode:monitorEventDeregisterAckReceived - ERROR");
        }
    }
}
