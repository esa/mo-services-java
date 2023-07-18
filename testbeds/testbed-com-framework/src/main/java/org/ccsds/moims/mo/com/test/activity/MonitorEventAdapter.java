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
import org.ccsds.moims.mo.com.event.consumer.EventAdapter;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_ACCEPTANCE_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_EXECUTION_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_FORWARD_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_RECEPTION_STR;
import static org.ccsds.moims.mo.com.test.util.COMTestHelper.OBJ_NO_ASE_RELEASE_STR;
import static org.ccsds.moims.mo.com.test.activity.BaseActivityScenario.objToEventName;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

class MonitorEventAdapter extends EventAdapter {

    private MonitorEventDetailsList monitorEventList = new MonitorEventDetailsList();
    // private MALMessage sentMsg = null;
    private MALMessageHeader hdr = null;
    private String pattern = null;

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param _Identifier0 Argument number 0 as defined by the service
     * operation.
     * @param header Argument number 1 as defined by the service operation.
     * @param objectDetails Argument number 2 as defined by the service
     * operation.
     * @param element Argument number 3 as defined by the service operation.
     * @param qosProperties The QoS properties associated with the message.
     */
    @Override
    public void monitorEventNotifyReceived(MALMessageHeader msgHeader, Identifier _Identifier0,
            UpdateHeader header, ObjectDetails objectDetails,
            Element element, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY");
        boolean success = false;

        // LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + msgHeader);
        // LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + _Identifier0);
        // LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + _UpdateHeaderList1);
        // LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + _ObjectDetailsList2);
        // LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + _ElementList3);
        Identifier objectNumber = (Identifier) header.getKeyValues().get(0);
        Identifier source = header.getSource();
        String strObjectNumber = objectNumber.toString();
        String strEventName = objToEventName(objectNumber.toString());
        strEventName.trim();
        if (strObjectNumber.equals(OBJ_NO_ASE_RELEASE_STR) || strObjectNumber.equals(OBJ_NO_ASE_RECEPTION_STR)
                || strObjectNumber.equals(OBJ_NO_ASE_FORWARD_STR)) {
            ActivityTransfer activityTransferInstance = (ActivityTransfer) element;
            success = activityTransferInstance.getSuccess();
            if (!success) {
                LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY RELEASE ERR " + strEventName);
            }
        } else if (strObjectNumber.equals(OBJ_NO_ASE_ACCEPTANCE_STR)) {
            ActivityAcceptance activityTransferAcceptance = (ActivityAcceptance) element;
            success = activityTransferAcceptance.getSuccess();
        } else if (strObjectNumber.equals(OBJ_NO_ASE_EXECUTION_STR)) {
            ActivityExecution activityTransferExecution = (ActivityExecution) element;
            success = activityTransferExecution.getSuccess();
        }
        // TBC do we need to support multiple updates
        monitorEventList.add(new MonitorEventDetails(strEventName, source.toString(), success,
                header, objectDetails, (Element) element));
        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived " + strEventName + " " + success);
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param qosProperties The QoS properties associated with the message.
     */
    public void monitorEventRegisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorEventRegisterAckReceived - ERROR");
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement error is
     * received from a broker for the operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param error The received error message.
     * @param qosProperties The QoS properties associated with the message.
     */
    public void monitorEventRegisterErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorEventRegisterErrorReceived - ERROR");
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param qosProperties The QoS properties associated with the message.
     */
    public void monitorEventDeregisterAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorEventDeregisterAckReceived - ERROR");
    }

    /**
     * Called by the MAL when a PubSub update error is received from a broker
     * for the operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param error The received error message.
     * @param qosProperties The QoS properties associated with the message.
     */
    public void monitorEventNotifyErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorEventDeregisterAckReceived - ERROR");
    }

    public MonitorEventDetailsList getMonitorEventList() {
        return monitorEventList;
    }

    public void setSentMsg(MALMessageHeader hdr) {
        this.hdr = hdr;
    }

    public void setPattern(String aPattern) {
        pattern = aPattern;
    }

    public boolean eventDetailsValid(String[] exeactivity, String relays[]) {
        // Calculate total number of execution stages
        int totalPhases = 0;

        if ("INVOKE".equalsIgnoreCase(pattern)) {
            totalPhases = 2;
        } else if ("PROGRESS".equalsIgnoreCase(pattern)) {
            // Total phases 2 for ACK & RESPONSE plus one for each PROGRESS
            totalPhases = 2;
            for (int i = 0; i < exeactivity.length; i++) {
                if (exeactivity[i].contains("UPDATE_ERROR") || exeactivity[i].contains("UPDATE")) {
                    ++totalPhases;
                }
            }
        } else if ("SEND".equalsIgnoreCase(pattern)) {
            totalPhases = 2;
        } else {
            totalPhases = 1;
        }
        return monitorEventList.eventDetailsValid(pattern, hdr, totalPhases, relays);
    }
}
