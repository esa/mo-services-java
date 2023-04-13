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
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdateList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;

public class SubscriptionSessionTypeTestProcedure extends LoggingBase {

    public static final SessionType SESSION = SessionType.LIVE;
    public static final Identifier SESSION_NAME = new Identifier("LIVE");
    public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
    public static final UInteger PRIORITY = new UInteger(1);

    public static final Identifier SUBSCRIPTION_ID = new Identifier("EntityRequestSubscription");

    private IPTestStub ipTestToPublish;

    private IPTestStub ipTestToSubscribe;

    private MonitorListener listener;

    private SessionType publisherSessionType;

    private SessionType subscriberSessionType;

    private boolean shared;

    public boolean initiatePublisherWithSessionTypeAndSharedBroker(String sessionType, boolean shared) throws Exception {
        logMessage("SubscriptionSessionTypeTestProcedure.initiatePublisherWithSessionTypeAndSharedBroker("
                + sessionType + ',' + shared + ")");

        this.publisherSessionType = ParseHelper.parseSessionType(sessionType);
        this.shared = shared;

        ipTestToPublish = LocalMALInstance.instance().ipTestStub(
                HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE, publisherSessionType, SESSION_NAME, QOS_LEVEL,
                PRIORITY, shared).getStub();

        IdentifierList keyNames = new IdentifierList();
        keyNames.add(Helper.key1);
        UInteger expectedErrorCode = new UInteger(999);
        TestPublishRegister testPublishRegister = new TestPublishRegister(
                QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE, publisherSessionType, SESSION_NAME,
                false, keyNames, expectedErrorCode);
        ipTestToPublish.publishRegister(testPublishRegister);
        return true;
    }

    public boolean subscribeWithSessionTypeAndExpectedNotify(String sessionType, int notifyNumber) throws Exception {
        logMessage("SubscriptionSessionTypeTestProcedure.subscribeWithSessionTypeAndExpectedNotify("
                + sessionType + ',' + notifyNumber + ")");

        this.subscriberSessionType = ParseHelper.parseSessionType(sessionType);

        SubscriptionFilterList filters = new SubscriptionFilterList();
        filters.add(new SubscriptionFilter(Helper.key1, new AttributeList("A")));
        Subscription subscription = new Subscription(SUBSCRIPTION_ID, HeaderTestProcedure.DOMAIN, filters);

        listener = new MonitorListener();

        ipTestToSubscribe = LocalMALInstance.instance().ipTestStub(
                HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE, subscriberSessionType, SESSION_NAME, QOS_LEVEL,
                PRIORITY, shared).getStub();

        ipTestToSubscribe.monitorRegister(subscription, listener);

        AttributeList keyValues = new AttributeList("A");
        UpdateHeaderList updateHeaderList = new UpdateHeaderList();
        updateHeaderList.add(new UpdateHeader(new Identifier("source"), HeaderTestProcedure.DOMAIN, keyValues));

        TestUpdateList updateList = new TestUpdateList();
        updateList.add(new TestUpdate(0));

        UInteger expectedErrorCode = new UInteger(999);
        TestPublishUpdate testPublishUpdate = new TestPublishUpdate(QOS_LEVEL,
                PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE,
                publisherSessionType, SESSION_NAME, false, updateHeaderList,
                updateList, keyValues, expectedErrorCode, false, null);

        ipTestToPublish.publishUpdates(testPublishUpdate);

        synchronized (listener.monitorCond) {
            listener.monitorCond.waitFor(Configuration.WAIT_TIME_OUT);
            listener.monitorCond.reset();
        }

        IdentifierList idList = new IdentifierList();
        idList.add(SUBSCRIPTION_ID);
        ipTestToSubscribe.monitorDeregister(idList);

        AssertionList assertions = new AssertionList();
        String procedureName = "PubSub.checkSubscriptionSessionType";
        assertions.add(new Assertion(procedureName,
                "The number of expected notify " + notifyNumber, (notifyNumber == listener.countNotify())));
        return AssertionHelper.checkAssertions(assertions);
    }

    public boolean checkNotifyHeader() {
        return listener.checkHeaderAssertions();
    }

    public boolean publishDeregister() throws Exception {
        logMessage("SubscriptionSessionTypeTestProcedure.publishDeregister()");
        UInteger expectedErrorCode = new UInteger(999);
        TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
                QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false,
                expectedErrorCode);
        ipTestToPublish.publishDeregister(testPublishDeregister);
        return true;
    }

    class MonitorListener extends IPTestAdapter {

        private final BooleanCondition monitorCond = new BooleanCondition();

        private java.util.Vector receivedNotify;

        MonitorListener() {
            receivedNotify = new java.util.Vector();
        }

        @Override
        public void monitorNotifyReceived(MALMessageHeader msgHeader,
                Identifier subscriptionId, UpdateHeader updateHeader,
                TestUpdate update, Map qosProperties) {
            logMessage("MonitorListener.monitorNotifyReceived(" + msgHeader + ','
                    + updateHeader + ')');
            receivedNotify.addElement(msgHeader);
            monitorCond.set();
        }

        int countNotify() {
            return receivedNotify.size();
        }

        public boolean checkHeaderAssertions() {
            AssertionList assertions = new AssertionList();
            String procedureName = "PubSub.checkSubscriptionSessionType";
            for (int i = 0; i < receivedNotify.size(); i++) {
                MALMessageHeader msgHeader = (MALMessageHeader) receivedNotify.elementAt(i);
                /*
                assertions.add(new Assertion(procedureName,
                        "The session type of the notify is : " + subscriberSessionType, 
                        msgHeader.getSession().equals(subscriberSessionType)));
                 */
            }
            return AssertionHelper.checkAssertions(assertions);
        }
    }
}
