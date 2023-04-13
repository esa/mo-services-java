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

import java.util.Map;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
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
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class PublishRegisterTestProcedure extends LoggingBase {

    public static final SessionType SESSION = SessionType.LIVE;
    public static final Identifier SESSION_NAME = new Identifier("LIVE");
    public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
    public static final UInteger PRIORITY = new UInteger(1);

    public static final Identifier SUBSCRIPTION_ID = new Identifier("PublishRegisterSubscription");

    private boolean shared;

    private IPTestStub ipTest;

    private MonitorListener listener;

    private String procedureName = "PubSub.PublishRegister";

    private boolean publishRegistered = false;

    public boolean useSharedBroker(String sharedBroker) throws Exception {
        LoggingBase.logMessage("PublishRegisterTestProcedure.useSharedBroker("
                + sharedBroker + ')');
        shared = Boolean.parseBoolean(sharedBroker);

        ipTest = LocalMALInstance.instance().ipTestStub(
                HeaderTestProcedure.AUTHENTICATION_ID,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, shared).getStub();

        listener = new MonitorListener();
        return true;
    }

    public boolean publishRegisterWithTheEntities(String entities) throws Exception {
        LoggingBase.logMessage("PublishRegisterTestProcedure.publishRegisterWithTheEntities("
                + entities + ')');
        publishRegistered = true;

        // Add the Key Names
        IdentifierList myKeys = EntityRequestTestProcedure.parseKeyNames(entities);

        UInteger expectedErrorCode = new UInteger(999);
        TestPublishRegister testPublishRegister
                = new TestPublishRegister(QOS_LEVEL, PRIORITY,
                        HeaderTestProcedure.DOMAIN,
                        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false,
                        myKeys, expectedErrorCode);
        ipTest.publishRegister(testPublishRegister);

        return true;
    }

    public boolean publishWithEntityAndExpectError(String entityKeyValue, String error) throws Exception {
        LoggingBase.logMessage("PublishRegisterTestProcedure.publishWithEntityAndExpectError("
                + entityKeyValue + ',' + error + ')');
        listener.clear();

        AttributeList values = EntityRequestTestProcedure.parseKeyValues(entityKeyValue);

        // Empty filters list because we don't want any filter
        SubscriptionFilterList filters = new SubscriptionFilterList();
        Subscription subscription = new Subscription(SUBSCRIPTION_ID, HeaderTestProcedure.DOMAIN, filters);
        ipTest.monitorRegister(subscription, listener);

        boolean expectError = Boolean.parseBoolean(error);

        UpdateHeaderList updateHeaders = new UpdateHeaderList();
        updateHeaders.add(new UpdateHeader(new Identifier("source"), HeaderTestProcedure.DOMAIN, values));

        TestUpdateList updateList = new TestUpdateList();
        updateList.add(new TestUpdate(new Integer(0)));

        UInteger expectedErrorCode;
        AttributeList failedEntityKeys;
        if (expectError) {
            expectedErrorCode = MALHelper.UNKNOWN_ERROR_NUMBER;
            // failedEntityKeys = values;
            failedEntityKeys = null; // Needs to be set to null, because the failed keys are not passed
        } else {
            expectedErrorCode = new UInteger(999);
            failedEntityKeys = null;
        }
        TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
                QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE,
                SESSION, SESSION_NAME, false, updateHeaders, updateList, values, expectedErrorCode, (!publishRegistered),
                failedEntityKeys);

        ipTest.publishUpdates(testPublishUpdate);

        AssertionList providerAssertions = ipTest.getResult(null).getAssertions();
        AssertionList localAssertions = new AssertionList();

        // The notify message has to be received
        UpdateHeaderList notifiedUpdateHeaders = listener.getNotifiedUpdateHeaders();
        TestUpdateList notifiedUpdates = listener.getNotifiedUpdates();

        if (!expectError) {
            localAssertions.add(new Assertion(procedureName,
                    "Notified of updates", notifiedUpdates != null));
            if (notifiedUpdates != null) {
                localAssertions.add(new Assertion(procedureName,
                        "One notified update : " + notifiedUpdates.size() + " - " + notifiedUpdates.toString(), (notifiedUpdates.size() == 1)));
            }
            if (notifiedUpdates != null && notifiedUpdates.size() == 1) {
                localAssertions.add(new Assertion(procedureName,
                        "Expected key is: " + entityKeyValue, values.equals(notifiedUpdateHeaders.get(0).getKeyValues())));
            }
        }

        return AssertionHelper.checkAssertions(localAssertions)
                && AssertionHelper.checkAssertions(providerAssertions);
    }

    public boolean publishDeregister() throws Exception {
        logMessage("PublishRegisterTestProcedure.publishDeregister()");
        UInteger expectedErrorCode = new UInteger(999);
        TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
                QOS_LEVEL, PRIORITY,
                HeaderTestProcedure.DOMAIN,
                HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, expectedErrorCode);
        ipTest.publishDeregister(testPublishDeregister);
        publishRegistered = false;

        IdentifierList idList = new IdentifierList();
        idList.add(SUBSCRIPTION_ID);
        ipTest.monitorDeregister(idList);

        return true;
    }

    static class MonitorListener extends IPTestAdapter {

        private UpdateHeaderList notifiedUpdateHeaders;

        private TestUpdateList notifiedUpdates;

        MonitorListener() {
        }

        @Override
        public void monitorNotifyReceived(MALMessageHeader msgHeader,
                Identifier subscriptionId, UpdateHeader updateHeader,
                TestUpdate updateList, Map qosProperties) {
            LoggingBase.logMessage("PublishRegisterTestProcedure.MonitorListener.monitorNotifyReceived: "
            );
            notifiedUpdateHeaders = new UpdateHeaderList();
            notifiedUpdateHeaders.add(updateHeader);
            notifiedUpdates = new TestUpdateList();
            notifiedUpdates.add(updateList);
        }

        public TestUpdateList getNotifiedUpdates() {
            return notifiedUpdates;
        }

        public UpdateHeaderList getNotifiedUpdateHeaders() {
            return notifiedUpdateHeaders;
        }

        public void clear() {
            notifiedUpdates = null;
            notifiedUpdateHeaders = null;
        }
    }
}
