/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Test bed
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
package org.ccsds.moims.mo.com.test.activity;

import org.ccsds.moims.mo.com.test.util.ComStructureHelper;
import java.util.*;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingServiceInfo;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer;
import org.ccsds.moims.mo.com.event.consumer.EventStub;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.com.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.com.test.util.COMTestHelper;
import org.ccsds.moims.mo.comprototype.activitytest.consumer.ActivityTestAdapter;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import static org.ccsds.moims.mo.testbed.util.LoggingBase.logMessage;

/**
 * Support for consumer side of MonitorActivity test. Supports a test in which
 * the Consumer communicates with the Provider optionally via a number of
 * relays.
 */
public class MonitorActivityScenario extends BaseActivityScenario {

    final IdentifierList domain = new IdentifierList();
    private final Map monitorMap = new TreeMap();
    private int nextMonitorKey = 0;
    private MonitorEventAdapter monitorEventAdapter = null;
    private int instanceIdentifier = 0;
    // Store names of relays that have been created - assumed 10 = reasonable max 
    String relayList[] = new String[10];
    int noRelays = 0;
    // Strings which identify the 4 MAL patterns used in the test
    protected final String SEND = "SEND";
    protected final String SUBMIT = "SUBMIT";
    protected final String REQUEST = "REQUEST";
    protected final String INVOKE = "INVOKE";
    protected final String PROGRESS = "PROGRESS";

    /**
     *
     * @param type text which identifies the type of the test.
     */
    public MonitorActivityScenario(String type) {
        super(type + "MonitorActivityScenario");
        monitorEventAdapter = new MonitorEventAdapter();
        logMessage(loggingClassName + ":constructor");
        InstIdLists.inst().reset();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));
    }

    /**
     * Checks if Relay Management client has been created - attempts to create
     * it.
     *
     * @return true if client exists or is successfully created, false otherwise
     * @throws Exception generated in case of comms failures
     */
    public boolean testActivityRelayManagementServiceClientHasBeenCreated() throws Exception {
        logMessage(loggingClassName + ":testActivityRelayManagementServiceClientHasBeenCreated");
        return (null != LocalMALInstance.instance().activityRelayManagementStub());
    }

    /**
     * Calls operation ResetTest on Relay Management service provider
     *
     * @return success indication returned by provider.
     * @throws Exception generated in case of comms failures
     */
    public boolean callResetTestOnRelayManagementServiceProvider() throws Exception {
        logMessage(loggingClassName + ":callResetTestOnRelayManagementServiceProvider");
        LocalMALInstance.instance().activityRelayManagementStub().resetTest();
        noRelays = 0;  // Assume all relays have been removed
        return true;
    }

    /**
     * Calls operation ResetTest on the Relay Service provider
     *
     * @param relay identifies relay to be reset
     * @return success indication returned by provider.
     * @throws Exception generated in case of comms failures
     */
    public boolean callResetTestOnRelayServiceProvider(String relay) throws Exception {
        logMessage(loggingClassName + ":callResetTestOnRelayServiceProvider " + relay);
        LocalMALInstance.instance().activityTestStub(relay).resetTest();
        return true;
    }

    /**
     * Calls operation Close on the Relay Service provider
     *
     * @param relay identifies relay to be reset
     * @return success indication returned by provider.
     * @throws Exception generated in case of comms failures
     */
    public boolean closeRelayServiceProvider(String relay) throws Exception {
        logMessage(loggingClassName + ":closeRelayServiceProvider " + relay);
        LocalMALInstance.instance().activityTestStub(relay).close();
        return true;
    }

    /**
     * Subscribes for activity events from a relay.
     *
     * @param relay to subscribe with
     * @return success indication returned by provider.
     * @throws Exception generated in case of comms failures
     */
    public boolean subscribeForActivityEventsFrom(String relay) throws Exception {
        logMessage(loggingClassName + ":registerForEvents START");
        EventStub evStub = LocalMALInstance.instance().activityEventStub(relay, domain);
        SubscriptionFilterList filters = new SubscriptionFilterList();
        Subscription sub = new Subscription(new Identifier("SubA"), domain, null, filters);
        evStub.monitorEventRegister(sub, monitorEventAdapter);
        logMessage(loggingClassName + ":registerForEvents Complete");
        return true;
    }

    /**
     * Unsubscribes for activity events from a relay.
     *
     * @param relay to unsubscribe from
     * @return success indication returned by provider.
     * @throws Exception generated in case of comms failures
     */
    public boolean unsubscribeForActivityEventsFrom(String relay) throws Exception {
        logMessage(loggingClassName + ":deregisterForEvents START");
        final IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("esa"));
        domain.add(new Identifier("mission"));
        EventStub evStub = LocalMALInstance.instance().activityEventStub(relay, domain);
        IdentifierList sublst = new IdentifierList();
        sublst.add(new Identifier("SubA"));
        evStub.monitorEventDeregister(sublst);
        logMessage(loggingClassName + ":deregisterForEvents Complete");
        return true;
    }

    /**
     * Creates a relay.
     *
     * @param relayName name of relay
     * @param relayTo next relay in chain
     * @return success indication returned by provider.
     * @throws Exception generated in case of comms failures
     */
    public boolean createActivityServiceRelayCalledToRelayTo(String relayName, String relayTo) throws Exception {
        logMessage(loggingClassName + ":createActivityServiceRelayCalled " + relayName + " which relays to " + relayTo);
        LocalMALInstance.instance().activityRelayManagementStub().createRelay(relayName, relayTo);
        // Wait here
        try {
            Thread.sleep((long) Configuration.COM_PERIOD_LONG);
        } catch (Exception ex) {
        }

        relayList[noRelays++] = relayName;
        return true;
    }

    /**
     * Clears any existing activity reports.
     *
     * @return success indication - currently always true.
     */
    public boolean clearReceivedActivityReportsList() {
        logMessage(loggingClassName + ":clearReceivedActivityReportsList");
        InstIdLists.inst().reset();

        return true;
    }

    /**
     * Initiates a test in which a MAL pattern is invoked with a list of
     * transport & execution phases, for which the corresponding activity phases
     * shall be generated.
     *
     * @param pattern The MAL pattern
     * @param relay The relay on which the pattern should be invoked
     * @param transactivity the transport phases
     * @param exeactivity the execution phases
     * @return monitorkey to be used to check result of the pattern.
     * @throws org.ccsds.moims.mo.mal.MALException
     * @throws org.ccsds.moims.mo.mal.MALInteractionException
     */
    public String patternInitiationForViaWithTransportActivityAndExecutionActivity(String pattern,
            String relay, String[] transactivity, String[] exeactivity) throws MALException, MALInteractionException {
        logMessage(loggingClassName + ":patternInitiationForViaWithTransportActivityAndExecutionActivity["
                + pattern + "," + relay + "," + toString(transactivity) + "," + toString(exeactivity) + "]");

        MonitorActivityTestAdapter monitor = new MonitorActivityTestAdapter();
        MALMessage sentMsg = null;
        MALMessageHeader hdr = null;

        String monitorKey = String.valueOf(++nextMonitorKey);
        monitorMap.put(monitorKey, monitor);

        StringList lst = convertStringList(convertStringList(null, transactivity), exeactivity);

        // Reset monitor event list
        monitorEventAdapter.getMonitorEventList().clear();

        try {
            {
                if (SEND.equalsIgnoreCase(pattern)) {
                    sentMsg = LocalMALInstance.instance().activityTestStub(relay).send(lst);
                    // We don't get a response on a send
                    monitor.expectResponse = false;
                } else if (SUBMIT.equalsIgnoreCase(pattern)) {
                    monitor.expectResponse = true;
                    sentMsg = LocalMALInstance.instance().activityTestStub(relay).asyncTestSubmit(lst, monitor);
                } else if (REQUEST.equalsIgnoreCase(pattern)) {
                    sentMsg = LocalMALInstance.instance().activityTestStub(relay).asyncRequest(lst, monitor);
                } else if (INVOKE.equalsIgnoreCase(pattern)) {
                    monitor.expectResponse = true;
                    monitor.isError = true;
                    sentMsg = LocalMALInstance.instance().activityTestStub(relay).asyncInvoke(lst, monitor);
                } else if (PROGRESS.equalsIgnoreCase(pattern)) {
                    sentMsg = LocalMALInstance.instance().activityTestStub(relay).asyncProgress(lst, monitor);
                }
                if (sentMsg != null) {
                    hdr = sentMsg.getHeader();
                    publishReleaseEvent(true, relay, hdr);
                }
            }
        } catch (MALTransmitErrorException ex) {
            // Exception expected in case of RELEASE_ERROR 
            if (lst.contains("RELEASE_ERROR")) {
                // release error is expected failure
                logMessage(loggingClassName + ":patternInitiationForViaWithTransportActivityAndExecutionActivity "
                        + " RELEASE_ERROR " + ex.getHeader().getTransactionId());
                hdr = ex.getHeader();
                publishReleaseEvent(false, relay, hdr);
                monitor.expectResponse = false;
            }
        }

        monitorEventAdapter.setPattern(pattern);
        monitorEventAdapter.setSentMsg(hdr);
        return monitorKey;
    }

    /**
     * Checks the pattern completes as expected - only checks completion
     * indication
     *
     * @param monitorKey returned by the pattern initiation method identifies
     * pattern
     * @param transactivity - transport activities for which events should have
     * been generated
     * @param exeactivity - transport activities for which events should have
     * been generated
     * @return success
     */
    public boolean patternCompletesAsExpected(String monitorKey, String[] transactivity, String[] exeactivity) {
        logMessage(loggingClassName + ":patternCompletesAsExpected");
        boolean retVal = false;

        MonitorActivityTestAdapter monitor = ((MonitorActivityTestAdapter) monitorMap.get(monitorKey));
        try {
            retVal = monitor.cond.waitFor(2 * Configuration.COM_PERIOD_LONG);
        } catch (InterruptedException ex) {
            // do nothing, we are expecting this
        }

        if (retVal && monitor.expectResponse) {
            if (containsErrorStage(transactivity) || containsErrorStage(exeactivity)) {
                retVal = monitor.isError;
            } else {
                retVal = !monitor.isError;
            }
        }
        // Wait for a short time to ensure all events received
        try {
            waitForReasonableAmountOfTime();
        } catch (Exception ex) {
            logMessage(loggingClassName + ":patternCompletesAsExpectedWithTransportActivityAndExecutionActivity Wait Err " + ex);
        }
        return (retVal || (!monitor.expectResponse));
    }

    /**
     * For an executed pattern checks expected Activity Events have been
     * received for transport activities
     *
     * @param monitorKey
     * @param transactivity
     * @return success indication
     */
    public boolean receivedExpectedTransportActivity(String monitorKey, String[] transactivity) {
        logMessage(loggingClassName + ":receivedExpectedTransportActivity");
        boolean transEventsMatch = true;

        for (int transCnt = 0; transCnt < transactivity.length && transEventsMatch; transCnt++) {
            if (!transactivity[transCnt].isEmpty()) {
                transEventsMatch = monitorEventAdapter.getMonitorEventList().matches(transactivity[transCnt]);
                if (!transEventsMatch) {
                    logMessage(loggingClassName + ":patternCompletesAsExpectedWithTransportActivityAndExecutionActivity NO MATCH for "
                            + transactivity[transCnt]);
                }
            }
        }

        logMessage(loggingClassName + ":receivedExpectedTransportActivity RET = " + transEventsMatch);
        return transEventsMatch;
    }

    /**
     * For an executed pattern checks expected Activity Events have been
     * received for execution activities
     *
     * @param monitorKey Identifies the pattern execution
     * @param expEvents Identifies expected execution activities
     * @return success indication
     */
    public boolean receivedExpectedExecutionActivity(String monitorKey, String[] expEvents) {
        logMessage(loggingClassName + ":receivedExpectedExecutionActivity");
        try {
            Thread.sleep((long) Configuration.COM_PERIOD_LONG);
        } catch (Exception ex) {
        }
        MonitorEventDetailsList eventList = monitorEventAdapter.getMonitorEventList();
        int noExecutionEvents = eventList.noEvents("EXECUTION");
        int noExecutionErrorEvents = eventList.noEvents("EXECUTION_ERROR");
        int expNoExecutionErrorEvents = 0;
        int expNoExecutionEvents = 0;
        int noExpEvents = 0;

        if (expEvents.length != 0) {
            for (int i = 0; i < expEvents.length; i++) {
                if (!expEvents[i].isEmpty()) {
                    if (expEvents[i].contains("ERR")) {
                        ++expNoExecutionErrorEvents;
                    } else {
                        ++expNoExecutionEvents;
                    }
                    ++noExpEvents;
                }
            }
        }
        logMessage(loggingClassName + ":checkExecutionActivity EXE = " + expNoExecutionEvents + " " + noExecutionEvents
                + " EXE ERR " + expNoExecutionErrorEvents + " " + noExecutionErrorEvents);
        if (noExpEvents != 0) {
            return (noExecutionEvents == expNoExecutionEvents && noExecutionErrorEvents == expNoExecutionErrorEvents);
        } else {
            return true;
        }
    }

    /**
     * Checks that all the fields in the received activity events are correct
     *
     * @param monitorKey identifies the pattern
     * @param transactivity Identifies expected transport activities
     * @param exeactivity Identifies expected execution activities
     * @return success indication
     */
    public boolean receivedEventDetailsValid(String monitorKey, String[] transactivity, String[] exeactivity) {
        logMessage(loggingClassName + ":receivedEventDetailsValid");
        return (monitorEventAdapter.eventDetailsValid(exeactivity, relayList));
    }

    /**
     * Checks if any entries in an array correspond to an error stage - contain
     * text ERROR
     *
     * @param arr array to be checked
     * @return true if array contains ERROR false otherwise
     */
    protected boolean containsErrorStage(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].contains("ERROR")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if any entries in an array contain text corresponding to a
     * transport or execution stage
     *
     * @param stage stage we are searching for
     * @param arr array we are searching in
     * @return true if stage found false otherwise
     */
    protected boolean containsStage(String stage, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equalsIgnoreCase(stage)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Converts an array to a string list
     *
     * @param lst If not null entries added to this
     * @param arr the array
     * @return StringList containing entries
     */
    protected StringList convertStringList(StringList lst, String[] arr) {
        if (null != arr) {
            if (lst == null) {
                lst = new StringList();
            }

            lst.addAll(Arrays.asList(arr));
        }

        return lst;
    }

    /**
     * Converts an array to a comma separated string
     *
     * @param arr to be converted
     * @return comma separated string
     */
    public String toString(String[] arr) {
        StringBuilder buf = new StringBuilder();

        if (null != arr) {
            buf.append('[');
            for (int i = 0; i < arr.length; i++) {
                if (0 < i) {
                    buf.append(", ");
                }
                buf.append(arr[i]);
            }
            buf.append(']');
        }

        return buf.toString();
    }

    /**
     * Publishes an activity release event
     *
     * @param withSuccess success indication to be included in release event
     * @param relay the first relay in chain - we can use broker created by this
     * relay
     * @param hdr the MAL message header for which event to be created
     * @throws MALInteractionException
     * @throws MALException
     */
    protected void publishReleaseEvent(boolean withSuccess, String relay,
            MALMessageHeader hdr) throws MALInteractionException, MALException {
        LoggingBase.logMessage(loggingClassName + ":publishReleaseEvent " + withSuccess);

        /*
    final EntityKey ekey = new EntityKey(
            new Identifier(COMTestHelper.OBJ_NO_ASE_RELEASE_STR),
            COMTestHelper.getActivityObjectTypeAsKey(0),
            new Long(instanceIdentifier++),
            COMTestHelper.getActivityObjectTypeAsKey(COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY));
    final Time timestamp = new Time(System.currentTimeMillis());
         */
        AttributeList keys = new AttributeList();
        keys.add(new Identifier(COMTestHelper.OBJ_NO_ASE_RELEASE_STR));
        keys.add(new Union(ComStructureHelper.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                0)));
        keys.add(new Union((long) instanceIdentifier++));
        keys.add(new Union(ComStructureHelper.generateSubKey(
                COMHelper._COM_AREA_NUMBER,
                ActivityTrackingServiceInfo._ACTIVITYTRACKING_SERVICE_NUMBER,
                COMHelper._COM_AREA_VERSION,
                COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY)));

        // Produce header
        UpdateHeader uh = new UpdateHeader(new Identifier(LocalMALInstance.ACTIVITY_EVENT_NAME + "CONSUMER"),
                domain, keys.getAsNullableAttributeList());

        // Produce ActivityTransfer
        ActivityTransfer activityTransferInstance = new ActivityTransfer(withSuccess);

        ObjectKey key = new ObjectKey(domain, hdr.getTransactionId());
        ObjectId source = new ObjectId(COMTestHelper.getOperationActivityType(), key);
        ObjectDetails objDetails = new ObjectDetails(null, source);

        // We can now publish the event
        LocalMALInstance.instance().getMonitorEventPublisher(relay).publish(uh, objDetails, activityTransferInstance);
        LoggingBase.logMessage(loggingClassName + ":publishReleaseEvent END");
    }

    /**
     * Used to receive responses to operations on the MonitorActivityTest
     * service. Contains a BooleanCondition that can be used to wait for
     * completion of an operation
     */
    private class MonitorActivityTestAdapter extends ActivityTestAdapter {

        final BooleanCondition cond = new BooleanCondition();
        boolean isError = false;
        boolean expectResponse = true;

        @Override
        public void testSubmitAckReceived(MALMessageHeader msgHeader, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:testSubmitAckReceived");
            cond.set();
        }

        @Override
        public void testSubmitErrorReceived(MALMessageHeader msgHeader, MOErrorException error, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:testSubmitErrorReceived");
            synchronized (cond) {
                isError = true;
                cond.set();
            }
        }

        @Override
        public void requestResponseReceived(MALMessageHeader msgHeader, StringList _StringList0, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:requestResponseReceived " + _StringList0);

            // Error details returned in StringList
            for (int i = 0; i < _StringList0.size() && isError == false; i++) {
                if (_StringList0.get(i).contains("RESPONSE_ERROR")) {
                    isError = true;
                }
            }

            cond.set();
        }

        @Override
        public void requestErrorReceived(MALMessageHeader msgHeader, MOErrorException error, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:requestErrorReceived");
            synchronized (cond) {
                isError = true;
                cond.set();
            }
        }

        @Override
        public void invokeAckErrorReceived(MALMessageHeader msgHeader, MOErrorException error, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:invokeAckErrorReceived");
            synchronized (cond) {
                isError = true;
                cond.set();
            }
        }

        @Override
        public void invokeResponseReceived(MALMessageHeader msgHeader, StringList _StringList0, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:invokeResponseReceived");
            synchronized (cond) {
                isError = false;
                cond.set();
            }
        }

        @Override
        public void invokeResponseErrorReceived(MALMessageHeader msgHeader, MOErrorException error, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:invokeResponseErrorReceived");
            synchronized (cond) {
                isError = true;
                cond.set();
            }
        }

        @Override
        public void progressAckErrorReceived(MALMessageHeader msgHeader, MOErrorException error, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:progressAckErrorReceived");
            synchronized (cond) {
                isError = true;
                cond.set();
            }
        }

        @Override
        public void progressUpdateReceived(MALMessageHeader msgHeader, StringList _StringList0, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:progressUpdateReceived"); // <editor-fold defaultstate="collapsed" desc="Compiled Code">
            /* 0: return
       *  */
            // </editor-fold>
        }

        @Override
        public void progressUpdateErrorReceived(MALMessageHeader msgHeader, MOErrorException error, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:progressUpdateErrorReceived");
            synchronized (cond) {
                isError = true;
                cond.set();
            }
        }

        @Override
        public void progressResponseErrorReceived(MALMessageHeader msgHeader, MOErrorException error, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:progressResponseErrorReceived");
            synchronized (cond) {
                isError = true;
                cond.set();
            }
        }

        @Override
        public void progressResponseReceived(MALMessageHeader msgHeader, StringList _StringList0, Map qosProperties) {
            LoggingBase.logMessage("ActivityTestRelayHandlerImpl:MATA:progressResponseReceived");
            cond.set();
        }
    }
}
