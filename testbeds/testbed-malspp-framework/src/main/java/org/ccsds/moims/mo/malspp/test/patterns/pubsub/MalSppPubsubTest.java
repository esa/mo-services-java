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
package org.ccsds.moims.mo.malspp.test.patterns.pubsub;

import java.util.Hashtable;
import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedureImpl;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.PubSubTestCaseHelper;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance.IPTestConsumer;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest.provider.MonitorPublisher;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdateList;
import org.ccsds.moims.mo.malspp.test.patterns.SpacePacketCheck;
import org.ccsds.moims.mo.malspp.test.suite.ErrorBrokerHandler;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malspp.test.suite.PubsubErrorIPTestHandler;
import org.ccsds.moims.mo.malspp.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.objectweb.util.monolog.api.Logger;

public class MalSppPubsubTest extends HeaderTestProcedureImpl {

    public final static Logger logger = fr.dyade.aaa.common.Debug
            .getLogger(MalSppPubsubTest.class.getName());

    //public static final EntityKey WILDCARD_ENTITY_KEY = new EntityKey(new Identifier("A"), (long) 0, null, null);
    //public static final EntityKey SPECIAL_ENTITY_KEY = new EntityKey(new Identifier("A"), (long) 1, null, null);
    public static final Long SPECIAL_KEY_VALUE = 1L;
    public static final Long OTHER_KEY_VALUE = 2L;

    private SpacePacketCheck spacePacketCheck = new SpacePacketCheck();
    private MALMessage rcvdMsg = null;

    public boolean consumerPacketIsTc(boolean isTc) {
        return spacePacketCheck.consumerPacketIsTc(isTc);
    }

    public boolean providerPacketIsTc(boolean isTc) {
        return spacePacketCheck.providerPacketIsTc(isTc);
    }

    public FileBasedDirectory.URIpair getProviderURIs(boolean shared) {
        FileBasedDirectory.URIpair uris;
        int consumerPacketType = spacePacketCheck.getConsumerPacketType();
        int providerPacketType = spacePacketCheck.getProviderPacketType();
        if (consumerPacketType == 1) {
            if (providerPacketType == 1) {
                if (shared) {
                    uris = FileBasedDirectory
                            .loadURIs(TestServiceProvider.IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
                } else {
                    uris = FileBasedDirectory.loadURIs(IPTestHelper.IPTEST_SERVICE.IPTEST_SERVICE_NAME.getValue());
                }
            } else {
                if (shared) {
                    uris = FileBasedDirectory
                            .loadURIs(TestServiceProvider.TC_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
                } else {
                    uris = FileBasedDirectory.loadURIs(TestServiceProvider.TM_IP_TEST_PROVIDER_NAME);
                }
            }
        } else {
            if (providerPacketType == 1) {
                if (shared) {
                    uris = FileBasedDirectory
                            .loadURIs(TestServiceProvider.TM_TC_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
                } else {
                    uris = FileBasedDirectory.loadURIs(IPTestHelper.IPTEST_SERVICE.IPTEST_SERVICE_NAME.getValue());
                }
            } else {
                if (shared) {
                    uris = FileBasedDirectory
                            .loadURIs(TestServiceProvider.TM_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
                } else {
                    uris = FileBasedDirectory.loadURIs(TestServiceProvider.TM_IP_TEST_PROVIDER_NAME);
                }
            }
        }
        return uris;
    }

    protected void initConsumer(int domain, SessionType session, Identifier sessionName,
            QoSLevel qos, boolean shared) throws Exception {
        int consumerPacketType = spacePacketCheck.getConsumerPacketType();
        int providerPacketType = spacePacketCheck.getProviderPacketType();
        if (consumerPacketType == 1) {
            if (providerPacketType == 1) {
                ipTestConsumer = LocalMALInstance.instance().getTcTcIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID,
                        HeaderTestProcedure.getDomain(domain),
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, shared);
            } else {
                ipTestConsumer = LocalMALInstance.instance().getTcTmIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain),
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, shared);
            }
        } else {
            if (providerPacketType == 1) {
                ipTestConsumer = LocalMALInstance.instance().getTmTcIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID,
                        HeaderTestProcedure.getDomain(domain),
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, shared);
            } else {
                ipTestConsumer = LocalMALInstance.instance().getTmTmIpTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain),
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY, shared);
            }
        }
    }

    public boolean selectReceivedPacketAt(int index) {
        return spacePacketCheck.selectReceivedPacketAt(index);
    }

    public boolean selectSentPacketAt(int index) {
        return spacePacketCheck.selectSentPacketAt(index);
    }

    public boolean checkTimestamp() throws Exception {
        return spacePacketCheck.checkTimestamp();
    }

    public boolean checkSpacePacketType() {
        return spacePacketCheck.checkSpacePacketType();
    }

    public int versionIs() {
        return spacePacketCheck.versionIs();
    }

    public int sduTypeIs() {
        return spacePacketCheck.sduTypeIs();
    }

    public int areaIs() {
        return spacePacketCheck.areaIs();
    }

    public int serviceIs() {
        return spacePacketCheck.serviceIs();
    }

    public int operationIs() {
        return spacePacketCheck.operationIs();
    }

    public int areaVersionIs() {
        return spacePacketCheck.areaVersionIs();
    }

    public int errorFlagIs() {
        return spacePacketCheck.errorFlagIs();
    }

    public boolean checkUriFrom() {
        return spacePacketCheck.checkUriFrom();
    }

    public boolean checkUriTo() {
        return spacePacketCheck.checkUriTo();
    }

    public boolean checkTransactionId() {
        return spacePacketCheck.checkTransactionId();
    }

    public boolean resetSppInterceptor() {
        return spacePacketCheck.resetSppInterceptor();
    }

    public boolean checkQos(String qosLevelAsString) throws Exception {
        return spacePacketCheck.checkQos(qosLevelAsString);
    }

    public boolean checkSession(String sessionTypeAsString) throws Exception {
        return spacePacketCheck.checkSession(sessionTypeAsString);
    }

    public long priorityIs() throws Exception {
        return spacePacketCheck.priorityIs();
    }

    public String networkZoneIs() throws Exception {
        return spacePacketCheck.networkZoneIs();
    }

    public String sessionNameIs() throws Exception {
        return spacePacketCheck.sessionNameIs();
    }

    public boolean checkAuthenticationId() throws Exception {
        return spacePacketCheck.checkAuthenticationId();
    }

    public boolean checkDomainId() throws Exception {
        return spacePacketCheck.checkDomainId();
    }

    public boolean checkSecondaryApid() {
        return spacePacketCheck.checkSecondaryApid();
    }

    public boolean checkSecondaryApidQualifier() {
        return spacePacketCheck.checkSecondaryApidQualifier();
    }

    public byte sourceIdFlagIs() throws Exception {
        return spacePacketCheck.sourceIdFlagIs();
    }

    public byte destinationIdFlagIs() throws Exception {
        return spacePacketCheck.destinationIdFlagIs();
    }

    public byte priorityFlagIs() throws Exception {
        return spacePacketCheck.priorityFlagIs();
    }

    public byte timestampFlagIs() throws Exception {
        return spacePacketCheck.timestampFlagIs();
    }

    public byte networkZoneFlagIs() throws Exception {
        return spacePacketCheck.networkZoneFlagIs();
    }

    public byte sessionNameFlagIs() throws Exception {
        return spacePacketCheck.sessionNameFlagIs();
    }

    public byte domainFlagIs() throws Exception {
        return spacePacketCheck.domainFlagIs();
    }

    public byte authenticationIdFlagIs() throws Exception {
        return spacePacketCheck.authenticationIdFlagIs();
    }

    public boolean readSourceId() {
        return spacePacketCheck.readSourceId();
    }

    public boolean readDestinationId() {
        return spacePacketCheck.readDestinationId();
    }

    public long segmentCounterIs() {
        return spacePacketCheck.segmentCounterIs();
    }

    public boolean readSegmentCounterIfSegmented() {
        return spacePacketCheck.readSegmentCounterIfSegmented();
    }

    public int packetVersionNumberIs() {
        return spacePacketCheck.packetVersionNumberIs();
    }

    public int secondaryHeaderFlagIs() {
        return spacePacketCheck.secondaryHeaderFlagIs();
    }

    public int packetDataLengthIs() {
        return spacePacketCheck.packetDataLengthIs();
    }

    public boolean packetDataLengthIsLengthOfPacketDataFieldMinusOne() {
        return spacePacketCheck.packetDataLengthIsLengthOfPacketDataFieldMinusOne();
    }

    private IPTestConsumer getPubsubErrorIPTestStub(int domain, SessionType session,
            Identifier sessionName, QoSLevel qos) throws Exception {
        int consumerPacketType = spacePacketCheck.getConsumerPacketType();
        int providerPacketType = spacePacketCheck.getProviderPacketType();
        if (consumerPacketType == 1) {
            if (providerPacketType == 1) {
                return LocalMALInstance.instance().getTcTcPubsubErrorIPTestStub(
                        HeaderTestProcedure.AUTHENTICATION_ID,
                        HeaderTestProcedure.getDomain(domain),
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                        HeaderTestProcedure.PRIORITY);
            } else {
                return LocalMALInstance.instance()
                        .getTcTmPubsubErrorIPTestStub(
                                HeaderTestProcedure.AUTHENTICATION_ID,
                                HeaderTestProcedure.getDomain(domain),
                                HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                                HeaderTestProcedure.PRIORITY);
            }
        } else {
            if (providerPacketType == 1) {
                return LocalMALInstance.instance()
                        .getTmTcPubsubErrorIPTestStub(
                                HeaderTestProcedure.AUTHENTICATION_ID,
                                HeaderTestProcedure.getDomain(domain),
                                HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                                HeaderTestProcedure.PRIORITY);
            } else {
                return LocalMALInstance.instance()
                        .getTmTmPubsubErrorIPTestStub(
                                HeaderTestProcedure.AUTHENTICATION_ID,
                                HeaderTestProcedure.getDomain(domain),
                                HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
                                HeaderTestProcedure.PRIORITY);
            }
        }
    }

    public boolean initiateRegisterErrorWithQosAndSessionAndDomain(String qosLevel,
            String sessionType, int domain) throws Exception {
        logMessage("initiateRegisterErrorWithQosAndSessionAndDomain(" + qosLevel + ',' + sessionType + ',' + domain + ')');

        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);

        IPTestConsumer ipTestConsumer = getPubsubErrorIPTestStub(domain, session, sessionName, qos);
        IPTestStub ipTest = ipTestConsumer.getStub();

        Subscription subscription = new Subscription(
                new Identifier(ErrorBrokerHandler.SUBSCRIPTION_RAISING_ERROR),
                null,
                null,
                new SubscriptionFilterList());

        try {
            ipTest.monitorRegister(subscription, new IPTestListener());
        } catch (MALInteractionException exc) {
            // Expected error
            return true;
        }

        Thread.sleep(2000);

        return false;
    }

    public boolean initiateNotifyErrorWithQosAndSessionAndDomain(String qosLevel,
            String sessionType, int domain) throws Exception {
        logMessage("initiateNotifyErrorWithQosAndSessionAndDomain(" + qosLevel + ',' + sessionType + ',' + domain + ')');

        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);

        // First, need to subscribe in order to synchronize with the Notify  
        IPTestConsumer ipTestConsumer = getPubsubErrorIPTestStub(domain, session, sessionName, qos);
        IPTestStub ipTest = ipTestConsumer.getStub();

        Subscription subscription = new Subscription(new Identifier("subscription"),
                null, null, new SubscriptionFilterList());

        IPTestListener listener = new IPTestListener();

        try {
            ipTest.monitorRegister(subscription, listener);
        } catch (MALInteractionException exc) {
            // Unexpected error
            logMessage("Unexpected error: " + exc);
            return false;
        }

        //IPTestConsumer errorIpTestConsumer = getPubsubErrorIPTestStub(domain, session, sessionName, qos);
        //IPTest errorIpTest = errorIpTestConsumer.getStub();
        AttributeList keyValues = new AttributeList();
        UInteger errorCode = MALHelper.INTERNAL_ERROR_NUMBER;
        TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
                qos, HeaderTestProcedure.PRIORITY,
                HeaderTestProcedure.getDomain(domain),
                HeaderTestProcedure.NETWORK_ZONE, session, sessionName, false,
                new UpdateHeaderList(), new TestUpdateList(),
                keyValues.getAsNullableAttributeList(), errorCode, Boolean.FALSE, null);
        ipTest.publishUpdates(testPublishUpdate);

        listener.waitNotifyError();

        return true;
    }

    private PubsubErrorIPTestHandler initHandlerForPublishRegister() throws Exception {
        int consumerPacketType = spacePacketCheck.getConsumerPacketType();
        int providerPacketType = spacePacketCheck.getProviderPacketType();
        if (consumerPacketType == 1) {
            if (providerPacketType == 1) {
                return LocalMALInstance.instance().getTcTcHandlerForPublishRegister();
            } else {
                return LocalMALInstance.instance().getTcTmHandlerForPublishRegister();
            }
        } else {
            if (providerPacketType == 1) {
                return LocalMALInstance.instance().getTmTcHandlerForPublishRegister();
            } else {
                return LocalMALInstance.instance().getTmTmHandlerForPublishRegister();
            }
        }
    }

    public boolean initiatePublishRegisterErrorWithQosAndSessionAndDomain(String qosLevel,
            String sessionType, int domain) throws Exception {
        logMessage("initiatePublishRegisterErrorWithQosAndSessionAndDomain(" + qosLevel + ',' + sessionType + ',' + domain + ')');

        PubsubErrorIPTestHandler ipTestHandler = initHandlerForPublishRegister();

        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
        MonitorPublisher publisher = ipTestHandler.createMonitorPublisher(
                HeaderTestProcedure.getDomain(domain),
                HeaderTestProcedure.NETWORK_ZONE, session,
                sessionName, qos, new Hashtable(), new UInteger(1));

        try {
            publisher.register(new IdentifierList(), new AttributeTypeList(), new PublishListener());
        } catch (MALInteractionException exc) {
            // Expected error
            return true;
        }

        return false;
    }

    public boolean publishRegisterAndRegisterWithAndSessionAndSharedBrokerAndDomain(
            String qosLevel, String sessionType, String sharedBroker, int domain) throws Exception {
        logMessage("publishRegisterAndRegisterWithAndSessionAndDomain(" + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain + ')');

        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
        boolean shared = Boolean.parseBoolean(sharedBroker);

        initConsumer(domain, session, sessionName, qos, shared);
        IPTestStub ipTest = ipTestConsumer.getStub();

        // Publish Register
        //EntityKeyList entityKeys = new EntityKeyList();
        //entityKeys.add(WILDCARD_ENTITY_KEY);
        IdentifierList keyNames = new IdentifierList();

        UInteger expectedErrorCode = new UInteger(999);
        TestPublishRegister testPublishRegister
                = new TestPublishRegister(qos, HeaderTestProcedure.PRIORITY,
                        HeaderTestProcedure.getDomain(domain),
                        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, true,
                        keyNames, new AttributeTypeList(), expectedErrorCode);
        ipTest.publishRegister(testPublishRegister);

        // Register
        Boolean onlyOnChange = false;
        /*
        EntityRequest entityRequest = new EntityRequest(
                null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
                onlyOnChange, entityKeys);
        EntityRequestList entityRequests = new EntityRequestList();
        entityRequests.add(entityRequest);
         */
        //Subscription subscription = new Subscription(HeaderTestProcedure.SUBSCRIPTION_ID, entityRequests);
        SubscriptionFilterList filters = new SubscriptionFilterList();
        Subscription subscription = new Subscription(HeaderTestProcedure.SUBSCRIPTION_ID, null, null, filters);
        ipTest.monitorMultiRegister(subscription, new IPTestListener());

        return true;
    }

    public boolean publishWithAndSessionAndDomain(String mode,
            String qosLevel, String sessionType, int domain) throws Exception {
        logMessage("publishWithAndSessionAndDomain(" + mode + ',' + qosLevel + ',' + sessionType + ',' + domain + ')');

        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);

        IPTestStub ipTest = ipTestConsumer.getStub();

        /*
        EntityKey entityKey = null;
        if (mode.equalsIgnoreCase("abstract service-defined")) {
            entityKey = HeaderTestProcedure.RIGHT_ENTITY_KEY;
        } else if (mode.equalsIgnoreCase("abstract non-service defined")) {
            entityKey = SPECIAL_ENTITY_KEY;
        } else {
            logMessage("Unexpected Publish mode: " + mode);
            return false;
        }
         */
        AttributeList keyValues = new AttributeList();
        if (mode.equalsIgnoreCase("abstract service-defined")) {
            keyValues.add(new Union(OTHER_KEY_VALUE));
        } else if (mode.equalsIgnoreCase("abstract non-service defined")) {
            keyValues.add(new Union(SPECIAL_KEY_VALUE));
        } else {
            logMessage("Unexpected Publish mode: " + mode);
            return false;
        }

        // Publish
        UpdateHeader updateHeader1 = new UpdateHeader(new Identifier(""),
                null, keyValues.getAsNullableAttributeList());
        TestUpdate update1 = new TestUpdate(new Integer(1));
        UpdateHeader updateHeader2 = new UpdateHeader(new Identifier(""),
                null, keyValues.getAsNullableAttributeList());
        TestUpdate update2 = new TestUpdate(new Integer(2));
        UpdateHeader updateHeader3 = new UpdateHeader(new Identifier(""),
                null, keyValues.getAsNullableAttributeList());
        TestUpdate update3 = new TestUpdate(new Integer(3));
        UpdateHeader updateHeader4 = new UpdateHeader(new Identifier(""),
                null, keyValues.getAsNullableAttributeList());
        TestUpdate update4 = new TestUpdate(new Integer(4));

        UpdateHeaderList updateHeaders = new UpdateHeaderList();
        updateHeaders.add(updateHeader1);
        updateHeaders.add(updateHeader2);
        updateHeaders.add(updateHeader3);
        updateHeaders.add(updateHeader4);

        TestUpdateList updates = new TestUpdateList();
        updates.add(update1);
        updates.add(update2);
        updates.add(update3);
        updates.add(update4);

        UInteger expectedErrorCode = new UInteger(999);
        TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
                qos, HeaderTestProcedure.PRIORITY,
                HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE,
                session, sessionName, true, updateHeaders, updates,
                keyValues.getAsNullableAttributeList(), expectedErrorCode, false, null);

        ipTest.publishUpdates(testPublishUpdate);

        return true;
    }

    public boolean deregisterAndPublishDeregisterWithAndSessionAndDomain(
            String qosLevel, String sessionType, int domain) throws Exception {
        logMessage("deregisterAndPublishDeregisterWithAndSessionAndDomain(" + qosLevel + ',' + sessionType + ',' + domain + ')');

        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);

        IPTestStub ipTest = ipTestConsumer.getStub();

        // Deregister
        IdentifierList subIds = new IdentifierList();
        subIds.add(HeaderTestProcedure.SUBSCRIPTION_ID);
        ipTest.monitorMultiDeregister(subIds);

        // Publish-Deregister
        UInteger expectedErrorCode = new UInteger(999);
        TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
                qos, HeaderTestProcedure.PRIORITY,
                HeaderTestProcedure.getDomain(domain),
                HeaderTestProcedure.NETWORK_ZONE, session, sessionName, true, expectedErrorCode);
        ipTest.publishDeregister(testPublishDeregister);

        return true;
    }

    private MALMessage createMessage(Object[] body, URI uriTo, MALEndpoint ep, UOctet stage,
            QoSLevel qos, SessionType session, Identifier sessionName, int domain) throws Exception {
        Long transId = 0L;
        try {
            transId = TransportInterceptor.instance().getLastSentMessage(0).getHeader().getTransactionId() + 1;
        } catch (Exception ex) {
            // do nothing
        }
        return ep.createMessage(
                TestServiceProvider.IP_TEST_AUTHENTICATION_ID,
                uriTo,
                new Time(System.currentTimeMillis()),
                InteractionType.PUBSUB,
                stage,
                transId,
                MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
                IPTestHelper.IPTEST_SERVICE.IPTEST_SERVICE_NUMBER,
                IPTestHelper.IPTEST_SERVICE.MONITOR_OP_NUMBER,
                MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
                Boolean.FALSE,
                new NamedValueList(),
                null, // qos proerties
                body
        );
    }

    public boolean stageInitiationForWithUnknownUriToAndQosAndSessionAndSessionNameAndDomain(
            String stage, String uri, String qosLevel, String sessionType, String sessionNameStr, int domain
    ) throws Exception {
        URI uriTo = new URI(uri);
        QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
        SessionType session = ParseHelper.parseSessionType(sessionType);
        Identifier sessionName = new Identifier(sessionNameStr);

        // Value of 'shared' does not matter because uriTo is set to unknown URI anyway.
        initConsumer(domain, session, sessionName, qos, false);
        IPTestStub ipTest = ipTestConsumer.getStub();

        MALEndpoint ep = TransportInterceptor.instance().getEndPoint(ipTestConsumer.getConsumer().getURI());
        MALMessage msg = null;

        IdentifierList keyValues = new IdentifierList();
        Identifier keyName = new Identifier("MyKey");
        keyValues.add(keyName);
        Boolean onlyOnChange = false;
        /*
        EntityRequest entityRequest = new EntityRequest(
                null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
                onlyOnChange, entityKeys);
        EntityRequestList entityRequests = new EntityRequestList();
        entityRequests.add(entityRequest);
         */
        SubscriptionFilterList filters = new SubscriptionFilterList();
        filters.add(new SubscriptionFilter(keyName, new AttributeList(OTHER_KEY_VALUE)));
        Subscription subscription = new Subscription(HeaderTestProcedure.SUBSCRIPTION_ID, null, null, filters);

        if ("REGISTER".equalsIgnoreCase(stage)) {
            Object[] body = new Object[]{subscription};
            msg = createMessage(body, uriTo, ep, MALPubSubOperation.REGISTER_STAGE, qos, session, sessionName, domain);
        } else if ("PUBLISH_REGISTER".equalsIgnoreCase(stage)) {
            Object[] body = new Object[]{keyValues};
            msg = createMessage(body, uriTo, ep, MALPubSubOperation.PUBLISH_REGISTER_STAGE, qos, session, sessionName, domain);
        } else if ("PUBLISH_DEREGISTER".equalsIgnoreCase(stage)) {
            Object[] body = new Object[]{};
            msg = createMessage(body, uriTo, ep, MALPubSubOperation.PUBLISH_DEREGISTER_STAGE, qos, session, sessionName, domain);
        } else if ("DEREGISTER".equalsIgnoreCase(stage)) {
            IdentifierList identifierList = new IdentifierList();
            identifierList.add(HeaderTestProcedure.SUBSCRIPTION_ID);
            Object[] body = new Object[]{identifierList};
            msg = createMessage(body, uriTo, ep, MALPubSubOperation.DEREGISTER_STAGE, qos, session, sessionName, domain);
        }
        ep.sendMessage(msg);
        Thread.sleep(2000);
        return true;
    }

    public boolean selectLastReceivedMessage() {
        rcvdMsg = TransportInterceptor.instance().getLastReceivedMessage();
        return true;
    }

    public boolean receivedMessageMalHeaderFieldIsErrorMessage() {
        return rcvdMsg.getHeader().getIsErrorMessage();
    }

    public String receivedMessageBodyContainsError() throws Exception {
        if (rcvdMsg.getBody() instanceof MALErrorBody) {
            MOErrorException error = ((MALErrorBody) rcvdMsg.getBody()).getError();
            if (error.getErrorNumber().equals(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER)) {
                return "destination unknown";
            } else {
                //return error.getErrorName().toString();
                return error.toString();
            }
        }
        return "Not an error.";
    }

    public String receivedMessageMalHeaderFieldUriFromIs() {
        return rcvdMsg.getHeader().getFromURI().toString();
    }

    public int presenceFlagIs() {
        return spacePacketCheck.presenceFlagIs();
    }

    public int bufferRemainingSizeIs() {
        return spacePacketCheck.bufferRemainingSizeIs();
    }

    public boolean readUInteger() {
        spacePacketCheck.readUInteger();
        return true;
    }

    public int readIntegerHasValue() {
        return spacePacketCheck.readInteger();
    }

    public long uintegerFieldIs() {
        long res = spacePacketCheck.readUInteger().getValue();
        return res;
    }

    public boolean uintegerFieldIsSizeOf(String type) {
        long res = spacePacketCheck.readUInteger().getValue();
        long exp;

        // integer size for small MAL::Integers is different for different varintSupported settings
        long integerSize = spacePacketCheck.isVarintSupported() ? 1 : 4;
        if (type.equalsIgnoreCase("integer")) {
            exp = integerSize; // MAL::Integer
        } else if (type.equalsIgnoreCase("TestUpdate")) {
            exp = integerSize + 1; // Presence field boolean + MAL::Integer
        } else {
            return false;
        }
        return res == exp;
    }

    public boolean readIdentifierList() throws Exception {
        UInteger length = spacePacketCheck.readUInteger();
        for (long i = 0; i < length.getValue(); i++) {
            if (spacePacketCheck.presenceFlagIs() == 1) {
                spacePacketCheck.readIdentifier();
            }
        }
        return true;
    }

    public boolean readIdentifier() throws Exception {
        spacePacketCheck.readIdentifier();
        return true;
    }

    private boolean readEntityKey() {
        if (spacePacketCheck.presenceFlagIs() == 1) {
            spacePacketCheck.readIdentifier();
        }
        if (spacePacketCheck.presenceFlagIs() == 1) {
            spacePacketCheck.readLong();
        }
        if (spacePacketCheck.presenceFlagIs() == 1) {
            spacePacketCheck.readLong();
        }
        if (spacePacketCheck.presenceFlagIs() == 1) {
            spacePacketCheck.readLong();
        }
        return true;
    }

    public boolean readEntityKeyList() {
        UInteger length = spacePacketCheck.readUInteger();
        for (long i = 0; i < length.getValue(); i++) {
            if (spacePacketCheck.presenceFlagIs() == 1) {
                readEntityKey();
            }
        }
        return true;
    }

    private boolean readUpdateHeader() throws Exception {
        spacePacketCheck.readTime();
        spacePacketCheck.readUri();
        spacePacketCheck.readUInt8Enum();
        readEntityKey();
        return true;
    }

    public boolean readUpdateHeaderList() throws Exception {
        UInteger length = spacePacketCheck.readUInteger();
        for (long i = 0; i < length.getValue(); i++) {
            if (spacePacketCheck.presenceFlagIs() == 1) {
                readUpdateHeader();
            }
        }
        return true;
    }

    private boolean readEntityRequest() throws Exception {
        if (spacePacketCheck.presenceFlagIs() == 1) {
            readIdentifierList();
        }
        spacePacketCheck.readBoolean();
        spacePacketCheck.readBoolean();
        spacePacketCheck.readBoolean();
        spacePacketCheck.readBoolean();
        readEntityKeyList();
        return true;
    }

    public boolean readEntityRequestList() throws Exception {
        UInteger length = spacePacketCheck.readUInteger();
        for (long i = 0; i < length.getValue(); i++) {
            if (spacePacketCheck.presenceFlagIs() == 1) {
                readEntityRequest();
            }
        }
        return true;
    }

    public Integer readTestUpdateHasValue() {
        if (spacePacketCheck.presenceFlagIs() == 1) {
            return spacePacketCheck.readInteger();
        }
        return null;
    }

    public boolean readTestUpdateList() throws Exception {
        UInteger length = spacePacketCheck.readUInteger();
        for (long i = 0; i < length.getValue(); i++) {
            if (spacePacketCheck.presenceFlagIs() == 1) {
                readTestUpdateHasValue();
            }
        }
        return true;
    }

    public int elementAreaNumberIs() {
        return spacePacketCheck.readUInt16();
    }

    public long elementServiceNumberIs() {
        return spacePacketCheck.readUInt16();
    }

    public int elementVersionIs() {
        return spacePacketCheck.readUInt8();
    }

    public int elementTypeNumberIs() {
        return spacePacketCheck.readInt24();
    }

    static class IPTestListener extends IPTestAdapter {

        private boolean notifyErrorReceived;
        private final BooleanCondition monitorMultiCond = new BooleanCondition();

        @Override
        public synchronized void monitorNotifyErrorReceived(MALMessageHeader msgHeader,
                MOErrorException error, Map qosProperties) {
            System.out.println("monitorNotifyErrorReceived: " + error);
            notifyErrorReceived = true;
            notify();
        }

        @Override
        public void monitorMultiNotifyReceived(MALMessageHeader msgHeader,
                Identifier _Identifier0, UpdateHeader _UpdateHeaderList1,
                TestUpdate _TestUpdateList2, Element _ElementList3, Map qosProperties) {
            monitorMultiCond.set();
        }

        synchronized void waitNotifyError() {
            while (!notifyErrorReceived) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }

    }

    static class PublishListener implements MALPublishInteractionListener {

        public void publishDeregisterAckReceived(MALMessageHeader arg0, Map arg1)
                throws MALException {
            // TODO Auto-generated method stub

        }

        public void publishErrorReceived(MALMessageHeader arg0, MALErrorBody arg1,
                Map arg2) throws MALException {
            // TODO Auto-generated method stub

        }

        public void publishRegisterAckReceived(MALMessageHeader arg0, Map arg1)
                throws MALException {
            // TODO Auto-generated method stub

        }

        public void publishRegisterErrorReceived(MALMessageHeader arg0,
                MALErrorBody arg1, Map arg2) throws MALException {
            // TODO Auto-generated method stub

        }

    }

}
