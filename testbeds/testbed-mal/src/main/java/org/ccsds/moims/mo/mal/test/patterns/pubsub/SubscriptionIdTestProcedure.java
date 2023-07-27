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
package org.ccsds.moims.mo.mal.test.patterns.pubsub;

import org.ccsds.moims.mo.mal.test.util.Helper;
import java.util.Map;
import java.util.Vector;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdateList;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class SubscriptionIdTestProcedure extends LoggingBase {

    public static final SessionType SESSION = SessionType.LIVE;
    public static final Identifier SESSION_NAME = new Identifier("LIVE");
    public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
    public static final UInteger PRIORITY = new UInteger(1);
    private boolean shared;
    private IPTestStub ipTest;
    private Vector consumers;
    private Vector listeners;

    public SubscriptionIdTestProcedure() {
        consumers = new Vector();
        listeners = new Vector();
    }

    public boolean useSharedBroker(String sharedBroker) throws Exception {
        logMessage("SubscriptionIdTestProcedure.useSharedBroker(" + sharedBroker + ')');
        consumers.clear();
        listeners.clear();

        shared = Boolean.parseBoolean(sharedBroker);

        // Get the common stub to trigger the publish
        ipTest = LocalMALInstance.instance().ipTestStub(
                HeaderTestProcedure.AUTHENTICATION_ID,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, shared).getStub();

        IdentifierList keyNames = new IdentifierList();
        keyNames.add(Helper.key1);

        UInteger expectedErrorCode = new UInteger(999);
        TestPublishRegister testPublishRegister
                = new TestPublishRegister(QOS_LEVEL, PRIORITY,
                        HeaderTestProcedure.DOMAIN,
                        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, keyNames,
                        expectedErrorCode);
        ipTest.publishRegister(testPublishRegister);

        return true;
    }

    public boolean createSubscriber() throws Exception {
        logMessage("SubscriptionIdTestProcedure.createSubscriber()");
        // Create a new stub
        IPTestStub newIPTest = LocalMALInstance.instance().newIPTestStub(null,
                HeaderTestProcedure.AUTHENTICATION_ID,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, shared).getStub();
        consumers.addElement(newIPTest);

        SubscriptionFilterList filters = new SubscriptionFilterList();
        filters.add(new SubscriptionFilter(Helper.key1, new AttributeList("*")));
        Subscription subscription = new Subscription(HeaderTestProcedure.SUBSCRIPTION_ID,
                HeaderTestProcedure.DOMAIN, null, filters);

        MonitorListener listener = new MonitorListener();
        listeners.addElement(listener);

        newIPTest.monitorRegister(subscription, listener);
        return true;
    }

    public boolean publish() throws Exception {
        logMessage("SubscriptionIdTestProcedure.publish()");

        AttributeList att = new AttributeList(new Union("value"));
        UpdateHeaderList updateHeaderList = new UpdateHeaderList();
        updateHeaderList.add(new UpdateHeader(new Identifier("source"),
                HeaderTestProcedure.DOMAIN, att.getAsNullableAttributeList()));

        TestUpdateList updateList = new TestUpdateList();
        updateList.add(new TestUpdate(0));

        UInteger expectedErrorCode = new UInteger(999);
        TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
                QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, false, updateHeaderList,
                updateList, null, expectedErrorCode, false, null);

        ipTest.publishUpdates(testPublishUpdate);
        return true;
    }

    public boolean unregisterSubscribers() throws Exception {
        logMessage("SubscriptionIdTestProcedure.unregisterSubscribers()");
        IdentifierList subIds = new IdentifierList();
        subIds.add(HeaderTestProcedure.SUBSCRIPTION_ID);
        for (int i = 0; i < consumers.size(); i++) {
            IPTestStub consumer = (IPTestStub) consumers.elementAt(i);
            consumer.monitorDeregister(subIds);
        }
        return true;
    }

    public boolean publishDeregister() throws Exception {
        logMessage("SubscriptionIdTestProcedure.publishDeregister()");
        UInteger expectedErrorCode = new UInteger(999);
        TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
                QOS_LEVEL, PRIORITY,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, expectedErrorCode);
        ipTest.publishDeregister(testPublishDeregister);
        return true;
    }

    public boolean updateIsReceivedOnceByEverySubscriber() throws Exception {
        for (int i = 0; i < listeners.size(); i++) {
            MonitorListener listener = (MonitorListener) listeners.elementAt(i);

            synchronized (listener.monitorCond) {
                listener.monitorCond.waitFor(Configuration.WAIT_TIME_OUT);
                listener.monitorCond.reset();
            }

            if (listener.getNotifiedUpdateHeader() == null) {
                return false;
            }
        }
        return true;
    }

    static class MonitorListener extends IPTestAdapter {

        private final BooleanCondition monitorCond = new BooleanCondition();

        private UpdateHeader notifiedUpdateHeader;

        private TestUpdate notifiedUpdate;

        @Override
        public void monitorNotifyReceived(MALMessageHeader msgHeader,
                Identifier subscriptionId, UpdateHeader updateHeader,
                TestUpdate update, Map qosProperties) {
            notifiedUpdateHeader = updateHeader;
            notifiedUpdate = update;
            monitorCond.set();
        }

        public void reset() {
            notifiedUpdateHeader = null;
            notifiedUpdate = null;
        }

        public UpdateHeader getNotifiedUpdateHeader() {
            return notifiedUpdateHeader;
        }

        public TestUpdate getNotifiedUpdate() {
            return notifiedUpdate;
        }
    }
}
