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
package org.ccsds.moims.mo.mal.test.transport;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.test.util.Helper;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdateList;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.transport.TestEndPoint;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class ReceiveMultipleTestProcedure {

    public static final SessionType SESSION = SessionType.LIVE;
    public static final Identifier SESSION_NAME = new Identifier("LIVE");
    public static final UInteger PRIORITY = new UInteger(1);
    public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
    public static final Identifier value = new Identifier("A");
    private Subscription subscription;

    private TestEndPoint ep;
    private LocalMALInstance.IPTestConsumer ipTestConsumer;
    private IPTestStub ipTest;
    private final MonitorListener listener = new MonitorListener();

    public boolean createConsumer() throws Exception {
        LoggingBase.logMessage("ReceiveMultipleTestProcedure.createConsumer()");
        Thread.sleep(2000);

        ipTestConsumer = LocalMALInstance.instance().ipTestStub(
                HeaderTestProcedure.AUTHENTICATION_ID,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, false);
        ipTest = ipTestConsumer.getStub();

        SubscriptionFilterList filters = new SubscriptionFilterList();
        filters.add(new SubscriptionFilter(Helper.key1, new AttributeList(value)));

        subscription = new Subscription(HeaderTestProcedure.SUBSCRIPTION_ID, HeaderTestProcedure.DOMAIN, filters);

        ipTest.monitorRegister(subscription, listener);

        return true;
    }

    public boolean publishInitialMessage() throws Exception {
        LoggingBase.logMessage("ReceiveMultipleTestProcedure.publishInitialMessage()");

        IdentifierList keyNames = new IdentifierList();
        keyNames.add(Helper.key1);

        UInteger expectedErrorCode = new UInteger(999);
        TestPublishRegister testPublishRegister
                = new TestPublishRegister(QOS_LEVEL, PRIORITY,
                        HeaderTestProcedure.DOMAIN,
                        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false,
                        keyNames, expectedErrorCode);
        ipTest.publishRegister(testPublishRegister);

        UpdateHeaderList updateHeaderList = new UpdateHeaderList();
        updateHeaderList.add(new UpdateHeader(new Identifier("source"), HeaderTestProcedure.DOMAIN, new AttributeList(value)));

        TestUpdateList testUpdateList = new TestUpdateList();
        testUpdateList.add(new TestUpdate(0));

        TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
                QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, false, updateHeaderList, testUpdateList, null, expectedErrorCode, false, null);
        ipTest.publishUpdates(testPublishUpdate);

        return true;
    }

    public boolean receiveInitialNotifyMessage() throws Exception {
        LoggingBase.logMessage("ReceiveMultipleTestProcedure.receiveInitialNotifyMessage()");

        ep = TransportInterceptor.instance().getEndPoint(ipTestConsumer.getConsumer().getURI());

        if (0 == listener.getNotifyHeaders().size()) {
            return false;
        }

        return true;
    }

    public boolean receiveMultipleNotifyMessages() throws Exception {
        LoggingBase.logMessage("ReceiveMultipleTestProcedure.receiveMultipleNotifyMessages()");

        // Create a Notify message
        MALMessage notifyMessage = ep.createTestMessage(
                listener.getLastNotifyHeader(),
                HeaderTestProcedure.SUBSCRIPTION_ID,
                new UpdateHeader(),
                new Object[]{new TestUpdate()}, new Hashtable());
        LoggingBase.logMessage("notifyMessage = " + notifyMessage);

        // Reinject it twice (cloning is useless)
        MALMessage[] messages = new MALMessage[2];
        messages[0] = notifyMessage;
        messages[1] = notifyMessage;

        listener.resetState();
        listener.setNotifyCount(2);
        synchronized (listener.cond) {
            LoggingBase.logMessage("Trigger Receive Multiple");
            ep.receiveMultiple(messages);
            listener.cond.waitFor(Configuration.WAIT_TIME_OUT);
            listener.cond.reset();
        }

        List<MALMessageHeader> headers = listener.getNotifyHeaders();
        return (headers.size() == 2);
    }

    static class MonitorListener extends IPTestAdapter {

        private final BooleanCondition cond = new BooleanCondition();

        private List<MALMessageHeader> notifyHeaders;

        private int notifyCount;

        MonitorListener() {
            notifyHeaders = new ArrayList<>();
            notifyCount = 1;
        }

        public void setNotifyCount(int notifyCount) {
            this.notifyCount = notifyCount;
        }

        @Override
        public void monitorNotifyReceived(MALMessageHeader msgHeader,
                Identifier subscriptionId, UpdateHeader updateHeader,
                TestUpdate update, Map qosProperties) {
            LoggingBase.logMessage("monitorNotifyReceived(" + msgHeader + ',' + updateHeader + ')');
            notifyHeaders.add(msgHeader);
            if (notifyHeaders.size() == notifyCount) {
                cond.set();
            }
        }

        public void resetState() {
            cond.reset();
            notifyHeaders.clear();
        }

        public List<MALMessageHeader> getNotifyHeaders() {
            return notifyHeaders;
        }

        public MALMessageHeader getLastNotifyHeader() {
            if (notifyHeaders.size() > 0) {
                return notifyHeaders.get(notifyHeaders.size() - 1);
            } else {
                return null;
            }
        }
    }
}
