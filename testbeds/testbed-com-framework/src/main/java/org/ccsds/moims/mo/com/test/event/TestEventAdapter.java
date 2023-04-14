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
package org.ccsds.moims.mo.com.test.event;

import org.ccsds.moims.mo.com.event.consumer.EventAdapter;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

class TestEventAdapter extends EventAdapter {

    private final EventDetailsList eventDetailsList;

    public TestEventAdapter(EventDetailsList eventDetailsList) {
        this.eventDetailsList = eventDetailsList;
    }

    public EventDetailsList getEventDetailsList() {
        return eventDetailsList;
    }

    /**
     * Called by the MAL when a PubSub update is received from a broker for the
     * operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param _Identifier0 Argument number 0 as defined by the service
     * operation.
     * @param _UpdateHeaderList1 Argument number 1 as defined by the service
     * operation.
     * @param _ObjectDetailsList2 Argument number 2 as defined by the service
     * operation.
     * @param _ElementList3 Argument number 3 as defined by the service
     * operation.
     * @param qosProperties The QoS properties associated with the message.
     */
    @Override
    public void monitorEventNotifyReceived(MALMessageHeader msgHeader, Identifier _Identifier0,
            UpdateHeader updateHeader, ObjectDetails objectDetails,
            Element element, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY");
        boolean success = false;

        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + msgHeader);
        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + _Identifier0);
        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + updateHeader);
        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + objectDetails);
        LoggingBase.logMessage("MonitorEventAdapter:monitorStatusNotifyReceived - NOTIFY " + element);

        Identifier objectNumber = (Identifier) updateHeader.getKeyValues().get(0);
        Identifier uri = updateHeader.getSource();
        String strObjectNumber = objectNumber.toString();
        eventDetailsList.add(new EventDetails(updateHeader, objectDetails, (Element) element));
    }

    /**
     * Called by the MAL when a PubSub register acknowledgement is received from
     * a broker for the operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param qosProperties The QoS properties associated with the message.
     */
    public void monitorEventRegisterAckReceived(MALMessageHeader msgHeader, java.util.Map qosProperties) {
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
    public void monitorEventRegisterErrorReceived(MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MALStandardError error, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorEventRegisterErrorReceived - ERROR");
    }

    /**
     * Called by the MAL when a PubSub deregister acknowledgement is received
     * from a broker for the operation monitorEvent.
     *
     * @param msgHeader The header of the received message.
     * @param qosProperties The QoS properties associated with the message.
     */
    public void monitorEventDeregisterAckReceived(MALMessageHeader msgHeader, java.util.Map qosProperties) {
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
    public void monitorEventNotifyErrorReceived(MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MALStandardError error, java.util.Map qosProperties) {
        LoggingBase.logMessage("MonitorEventAdapter:monitorEventDeregisterAckReceived - ERROR");
    }
}
