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

import org.ccsds.moims.mo.com.activitytracking.structures.ActivityAcceptance;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityExecution;
import org.ccsds.moims.mo.com.activitytracking.structures.ActivityTransfer;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_ACCEPTANCE;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_EXECUTION;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_FORWARD;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_RECEPTION;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_RELEASE;

import org.ccsds.moims.mo.com.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.com.test.util.COMChecker;
import org.ccsds.moims.mo.com.test.util.COMTestHelper;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingHelper;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingServiceInfo;

/**
 *
 * @author dankiewicz_i
 */
public class MonitorEventDetailsList extends java.util.ArrayList<MonitorEventDetails> {

    boolean matches(String expEvent) {
        boolean found = false;
        for (int i = 0; i < this.size() && !found; i++) {
            found = get(i).toString().equals(expEvent);

        }
        return found;
    }

    // Returns number of events with a specified name
    int noEvents(String expEvent) {
        int noEvents = 0;
        for (int i = 0; i < this.size(); i++) {
            if (get(i).toString().equals(expEvent)) {
                ++noEvents;
            }
        }
        return noEvents;
    }

    public boolean eventDetailsValid(String pattern, MALMessageHeader hdr,
            int totalStages, String relays[]) {
        boolean bValid = true;
        ExecutionStage executionStage = new ExecutionStage();
        executionStage.totalStages = totalStages;
        executionStage.currentStage = 1;

        for (int i = 0; i < this.size(); i++) {
            boolean bLocalValid = get(i).valid(pattern, hdr, executionStage, relays);
            if (bValid) {
                bValid = bLocalValid;
            }
        }
        return bValid;
    }

    // Inner class which holds execution stage details
    class ExecutionStage {

        int currentStage = 1;
        int totalStages = 1;
    }
}

class MonitorEventDetails {

    String eventName = "";
    boolean success = true;
    String source = "";
    int objectNumber = 0;
    UpdateHeader updateHeader = null;
    ObjectDetails objectDetails = null;
    Element element = null;
    String strObject = "";

    MonitorEventDetails(String aEventName, String aSource, boolean aSuccess,
            UpdateHeader aUpdateHeader, ObjectDetails aObjectDetails, Element aElement) {
        eventName = aEventName;
        success = aSuccess;
        source = aSource;
        updateHeader = aUpdateHeader;
        objectDetails = aObjectDetails;
        element = aElement;
        objectNumber = new Integer((aUpdateHeader.getKeyValues().get(0)).toString()).intValue();

        // If source is a relay remove any prefix 
        if (source.indexOf("Relay") != -1) {
            source = source.substring(source.indexOf("Relay"));
        } else // ignore source if not relay
        {
            source = "";
        }
        strObject = "MonitorEventDetails:" + eventName;
    }

    public String toString() {
        String strRet = eventName;

        if (!success) {
            strRet = eventName + "_ERROR";
        }

        if (!source.equals("")) {
            strRet = strRet + "(" + source + ")";
        }
        return strRet;
    }

    private boolean uriValid(URI uri, String relays[]) {
        boolean bValid = false;

        if (objectNumber == OBJ_NO_ASE_RELEASE) {
            // Release comes from consumer
            bValid = uri.getValue().toUpperCase().contains(COMTestHelper.CONSUMER_STR);
        } else if (objectNumber == OBJ_NO_ASE_RECEPTION || objectNumber == OBJ_NO_ASE_FORWARD) {
            // Forward & reception from a relay - URI should contain relay names
            if (uri != null) {
                for (int i = 0; i < relays.length && !bValid; i++) {
                    bValid = uri.getValue().contains(relays[i]);
                }
            } else {
                bValid = false;
                COMChecker.recordError(strObject, "URI - value null ");
            }
        } else // other events are from provider
        {
            FileBasedDirectory.URIpair uris = FileBasedDirectory.loadURIs(LocalMALInstance.ACTIVITY_EVENT_NAME);
            bValid = uri.equals(uris.uri);
        }
        if (!bValid) {
            COMChecker.recordError(strObject,
                    "URI - " + uri + "invalid for object " + objectNumber);
        }
        return bValid;
    }

    private boolean headerValid(String pattern, String relays[]) {
        LoggingBase.logMessage("MonitorEventDetailsList:headerValid " + strObject + " " + updateHeader);
        boolean bValid = true;
        boolean bInstIdValid = true;
        // Check key
        // First Sub Key = event object number (Identifier) - TBD
        /* bValid = COMChecker.equalsCheck(strObject, "Header.Key.First", 
     (int) updateHeader.getKey().getFirstSubKey(),
     objectNumber, bValid); */
        // Second Sub Key = event object type (3 sub-fields)
        bValid = COMChecker.equalsCheck(strObject,
                "Header.Key.Second",
                (Long) updateHeader.getKeyValues().get(1),
                COMTestHelper.getActivityObjectTypeAsKey(0),
                bValid);
        // Third Sub Key = event object instance identifier
        updateHeader.getKeyValues().get(2);
        // Third sub key is event instance identifier check = TBC - just need to ensure it is unique
        bInstIdValid = InstIdLists.inst().add(objectNumber,
                new Long((Long) updateHeader.getKeyValues().get(2)));
        if (!bInstIdValid) {
            bValid = false;
            COMChecker.recordError(strObject,
                    "InstId - " + updateHeader.getKeyValues().get(2) + "not unique for object " + objectNumber);
        }
        // Fourth Sub Key = event source object type (4 sub-fields)
        bValid = COMChecker.equalsCheck(strObject,
                "Header.Key.Fourth",
                (Long) updateHeader.getKeyValues().get(3),
                COMTestHelper.getActivityObjectTypeAsKey(COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY), bValid);
        // Check source URI
        bValid = bValid && uriValid(new URI(updateHeader.getSource().getValue()), relays);

        return bValid;

    }

    private boolean objectDetailsValid(String pattern, MALMessageHeader hdr) {
        boolean bSourceValid = true;
        boolean bTypeValid = true;
        boolean bKeyValid = true;

        // objectDetails.getSource().getKey().
        // Check source 
        bSourceValid = COMChecker.nullCheck(strObject, "Source",
                objectDetails.getSource(), bSourceValid);
        if (bSourceValid) {
            bTypeValid = COMChecker.nullCheck(strObject, "Source Type",
                    objectDetails.getSource().getType(), bTypeValid);
            // If type valid check fields
            if (bTypeValid) {
                bTypeValid = COMChecker.equalsCheck(strObject, "Source.Type.Area",
                        objectDetails.getSource().getType().getArea(),
                        COMHelper.COM_AREA_NUMBER, bTypeValid);
                bTypeValid = COMChecker.equalsCheck(strObject, "Source.Type.Service",
                        objectDetails.getSource().getType().getService(),
                        ActivityTrackingServiceInfo.ACTIVITYTRACKING_SERVICE_NUMBER, bTypeValid);
                bTypeValid = COMChecker.equalsCheck(strObject, "Source.Type.Version",
                        objectDetails.getSource().getType().getAreaVersion(),
                        COMHelper.COM_AREA_VERSION, bTypeValid);
                bTypeValid = COMChecker.equalsCheck(strObject, "Source.Type.Number",
                        objectDetails.getSource().getType().getNumber(),
                        new UShort(COMTestHelper.OBJ_NO_ASE_OPERATION_ACTIVITY), bTypeValid);
            }
            bKeyValid = COMChecker.nullCheck(strObject, "Source key",
                    objectDetails.getSource().getKey(), bKeyValid);
            // If type valid check fields
            if (bKeyValid) {
                LoggingBase.logMessage("MonitorEventDetailsList:KEY = " + objectDetails.getSource().getKey());
                bTypeValid = COMChecker.equalsCheck(strObject, "Source.Key.Domain",
                        objectDetails.getSource().getKey().getDomain().toString(),
                        hdr.getDomain().toString(), bTypeValid);

                LoggingBase.logMessage("ActivityTestRelayHandlerImpl:Trans ID - " + strObject
                        + objectDetails.getSource().getKey().getInstId());

                LoggingBase.logMessage("ActivityTestRelayHandlerImpl:EXP Trans ID - " + strObject
                        + hdr.getTransactionId());

                bTypeValid = COMChecker.equalsCheck(strObject, "Source.Key.Inst Id",
                        (int) objectDetails.getSource().getKey().getInstId().longValue(),
                        (int) hdr.getTransactionId().longValue(), bTypeValid);
            }
        }

        return bTypeValid;
    }

    private boolean elementValid(String pattern,
            MonitorEventDetailsList.ExecutionStage executionStage) {
        boolean bRet = true;
        boolean bSuccess = true;

        LoggingBase.logMessage("MonitorEventDetailsList:elementValid");

        if (objectNumber == OBJ_NO_ASE_RELEASE || objectNumber == OBJ_NO_ASE_RECEPTION
                || objectNumber == OBJ_NO_ASE_FORWARD) {
            if (element instanceof ActivityTransfer) {
                ActivityTransfer at = (ActivityTransfer) element;
                success = at.getSuccess();
                if (!success) {
                    LoggingBase.logMessage("ActivityTestRelayHandlerImpl:monitorStatusNotifyReceived - NOTIFY RELEASE ERR ");
                }
            } else {
                bRet = false;
            }
        } else if (objectNumber == OBJ_NO_ASE_ACCEPTANCE) {
            if (element instanceof ActivityAcceptance) {
                ActivityAcceptance aa = (ActivityAcceptance) element;
                success = aa.getSuccess();
            } else {
                bRet = false;
            }
        } else if (objectNumber == OBJ_NO_ASE_EXECUTION) {
            if (element instanceof ActivityExecution) {
                ActivityExecution ae = (ActivityExecution) element;
                success = ae.getSuccess();
                bRet = COMChecker.equalsCheck(strObject, "Stage Count",
                        (int) ae.getStageCount().getValue(), executionStage.totalStages, bRet);
                bRet = COMChecker.equalsCheck(strObject, "Current Stage",
                        (int) ae.getExecutionStage().getValue(), executionStage.currentStage, bRet);

                ++executionStage.currentStage;

            } else {
                bRet = false;
            }
        }

        LoggingBase.logMessage("MonitorEventDetailsList:elementValid Ret = " + bRet);
        return bRet;
    }

    public boolean valid(String pattern, MALMessageHeader hdr,
            MonitorEventDetailsList.ExecutionStage executionStage, String relays[]) {
        LoggingBase.logMessage("MonitorEventDetailsList:eventDetailsValid " + strObject + " " + executionStage);
        boolean headerValid = headerValid(pattern, relays);
        boolean objectDetailsValid = objectDetailsValid(pattern, hdr);
        boolean elementValid = elementValid(pattern, executionStage);

        return headerValid && objectDetailsValid && elementValid;
    }
}
