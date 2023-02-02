/** *****************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 ****************************************************************************** */
package org.ccsds.moims.mo.testbed.transport;

import java.util.Hashtable;
import java.util.Vector;

import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.mal.transport.MALMessage;

public class TransportInterceptor {

    private static TransportInterceptor instance;

    public static TransportInterceptor instance() {
        if (instance == null) {
            instance = new TransportInterceptor();
        }
        return instance;
    }

    private Hashtable lastSentMessageTable;
    private Vector lastReceivedMessage;
    private Vector lastSentMessage;
    private MALMessage[] lastSentMessages;
    private MALMessage[] lastReceivedMessages;

    private Hashtable isSupportedQoSRequestCounts;
    private Hashtable isSupportedQoSResponseCounts;

    private Hashtable isSupportedIpRequestCounts;
    private Hashtable isSupportedIpResponseCounts;

    private Hashtable transmitRequestCounts;
    private Hashtable transmitResponseCounts;

    private int transmitMultipleRequestCount;
    private int transmitMultipleResponseCount;

    private Hashtable receiveCounts;

    private Hashtable endpoints;

    private TestEndPointSendInterceptor endpointSendInterceptor;

    private TransportInterceptor() {
        isSupportedQoSRequestCounts = new Hashtable();
        isSupportedQoSResponseCounts = new Hashtable();
        isSupportedIpRequestCounts = new Hashtable();
        isSupportedIpResponseCounts = new Hashtable();
        transmitRequestCounts = new Hashtable();
        transmitResponseCounts = new Hashtable();
        transmitMultipleRequestCount = 0;
        transmitMultipleResponseCount = 0;
        receiveCounts = new Hashtable();
        endpoints = new Hashtable();
        lastSentMessageTable = new Hashtable();
        lastReceivedMessage = new Vector();
        lastSentMessage = new Vector();
    }

    public void messageSent(MALMessage msg) {
        lastSentMessageTable.put(msg.getHeader().getURIFrom(), msg);
        lastSentMessage.add(msg);
    }

    public void messagesSent(MALMessage[] messages) {
        lastSentMessages = messages;
    }

    public void messageReceived(MALMessage msg) {
        lastReceivedMessage.add(msg);
    }

    public void messagesReceived(MALMessage[] messages) {
        lastReceivedMessages = messages;
    }

    public MALMessage getLastSentMessage(URI from) {
        return (MALMessage) lastSentMessageTable.get(from);
    }

    public MALMessage getLastSentMessage(int index) {
        if (index < lastSentMessage.size()) {
            return (MALMessage) lastSentMessage.get(index);
        }

        return null;
    }

    public MALMessage getLastReceivedMessage() {
        return (MALMessage) lastReceivedMessage.lastElement();
    }

    public MALMessage getLastReceivedMessage(int index) {
        if (index < lastReceivedMessage.size()) {
            return (MALMessage) lastReceivedMessage.elementAt(index);
        }

        return null;
    }

    public MALMessage[] getLastSentMessages() {
        return lastSentMessages;
    }

    public MALMessage[] getLastReceivedMessages() {
        return lastReceivedMessages;
    }

    // is supported QoS check
    public void resetSupportedQoSCount(QoSLevel qos) {
        LoggingBase.logMessage("resetSupportedQoSCount: " + qos);
        isSupportedQoSRequestCounts.put(qos, new Integer(0));
        isSupportedQoSResponseCounts.put(qos, new Integer(0));
    }

    public void incrementSupportedQoSRequestCount(QoSLevel qos) {
        LoggingBase.logMessage("incrementSupportedQoSRequestCount: " + qos);
        Integer count = (Integer) isSupportedQoSRequestCounts.get(qos);
        if (count == null) {
            count = new Integer(0);
        }
        isSupportedQoSRequestCounts.put(qos, new Integer(count.intValue() + 1));
    }

    public int getSupportedQoSRequestCount(QoSLevel qos) {
        Integer count = (Integer) isSupportedQoSRequestCounts.get(qos);
        if (count == null) {
            return 0;
        }
        return count.intValue();
    }

    public void incrementSupportedQoSResponseCount(QoSLevel qos) {
        Integer count = (Integer) isSupportedQoSResponseCounts.get(qos);
        if (count == null) {
            count = new Integer(0);
        }
        isSupportedQoSResponseCounts.put(qos, new Integer(count.intValue() + 1));
    }

    public int getSupportedQoSResponseCount(QoSLevel qos) {
        Integer count = (Integer) isSupportedQoSResponseCounts.get(qos);
        if (count == null) {
            return 0;
        }
        return count.intValue();
    }

    // is supported IP check
    public void resetSupportedIpCount(InteractionType ip) {
        isSupportedIpRequestCounts.put(ip, new Integer(0));
        isSupportedIpResponseCounts.put(ip, new Integer(0));
    }

    public void incrementSupportedIpRequestCount(InteractionType ip) {
        Integer count = (Integer) isSupportedIpRequestCounts.get(ip);
        if (count == null) {
            count = new Integer(0);
        }
        isSupportedIpRequestCounts.put(ip, new Integer(count.intValue() + 1));
    }

    public int getSupportedIpRequestCount(InteractionType ip) {
        Integer count = (Integer) isSupportedIpRequestCounts.get(ip);
        if (count == null) {
            return 0;
        }
        return count.intValue();
    }

    public void incrementSupportedIpResponseCount(InteractionType ip) {
        Integer count = (Integer) isSupportedIpResponseCounts.get(ip);
        if (count == null) {
            count = new Integer(0);
        }
        isSupportedIpResponseCounts.put(ip, new Integer(count.intValue() + 1));
    }

    public int getSupportedIpResponseCount(InteractionType ip) {
        Integer count = (Integer) isSupportedIpResponseCounts.get(ip);
        if (count == null) {
            return 0;
        }
        return count.intValue();
    }

    // transmit check
    public void resetTransmitCount(InteractionType ip) {
        transmitRequestCounts.put(ip, new Integer(0));
        transmitResponseCounts.put(ip, new Integer(0));
    }

    public void incrementTransmitRequestCount(InteractionType ip) {

        Integer count = (Integer) transmitRequestCounts.get(ip);
        if (count == null) {
            count = new Integer(0);
        }
        transmitRequestCounts.put(ip, new Integer(count.intValue() + 1));
    }

    public int getTransmitRequestCount(InteractionType ip) {
        Integer count = (Integer) transmitRequestCounts.get(ip);
        if (count == null) {
            return 0;
        }
        return count.intValue();
    }

    public void incrementTransmitResponseCount(InteractionType ip) {
        Integer count = (Integer) transmitResponseCounts.get(ip);
        if (count == null) {
            count = new Integer(0);
        }
        transmitResponseCounts.put(ip, new Integer(count.intValue() + 1));
    }

    public int getTransmitResponseCount(InteractionType ip) {
        Integer count = (Integer) transmitResponseCounts.get(ip);
        if (count == null) {
            return 0;
        }
        return count.intValue();
    }

    // transmit multiple check
    public void resetTransmitMultipleCount() {
        transmitMultipleRequestCount = 0;
        transmitMultipleResponseCount = 0;
        lastReceivedMessages = null;
    }

    public void incrementTransmitMultipleRequestCount() {
        transmitMultipleRequestCount++;
    }

    public int getTransmitMultipleRequestCount() {
        return transmitMultipleRequestCount;
    }

    public void incrementTransmitMultipleResponseCount() {
        transmitMultipleResponseCount++;
    }

    public int getTransmitMultipleResponseCount() {
        return transmitMultipleResponseCount;
    }

    // receive check
    public void resetReceiveCount(InteractionType ip) {
        receiveCounts.put(ip, new Integer(0));
        lastReceivedMessage.clear();
    }

    public void resetLastReceivedMessage() {
        lastReceivedMessage.clear();
    }

    public void resetLastSentMessage() {
        lastSentMessage.clear();
    }

    public void incrementReceiveCount(InteractionType ip) {
        Integer count = (Integer) receiveCounts.get(ip);
        if (count == null) {
            count = new Integer(0);
        }
        receiveCounts.put(ip, new Integer(count.intValue() + 1));
    }

    public int getReceiveCount(InteractionType ip) {
        Integer count = (Integer) receiveCounts.get(ip);
        return (count == null) ?  0 : count.intValue();
    }

    public void addEndPoint(TestEndPoint ep) {
        endpoints.put(ep.getURI(), ep);
    }

    public TestEndPoint getEndPoint(URI uri) {
        return (TestEndPoint) endpoints.get(uri);
    }

    public TestEndPointSendInterceptor getEndpointSendInterceptor() {
        return endpointSendInterceptor;
    }

    public void setEndpointSendInterceptor(TestEndPointSendInterceptor endpointSendInterceptor) {
        this.endpointSendInterceptor = endpointSendInterceptor;
    }
}
