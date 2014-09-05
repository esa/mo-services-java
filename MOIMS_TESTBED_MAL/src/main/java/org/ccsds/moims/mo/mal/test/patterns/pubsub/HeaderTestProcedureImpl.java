/*******************************************************************************
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
 *******************************************************************************/
package org.ccsds.moims.mo.mal.test.patterns.pubsub;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.EntityRequest;
import org.ccsds.moims.mo.mal.structures.EntityRequestList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.structures.UpdateType;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestUpdateList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.transport.TestEndPoint;
import org.ccsds.moims.mo.testbed.transport.TestMessageHeader;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;

public class HeaderTestProcedureImpl extends LoggingBase
{
  private LocalMALInstance.IPTestConsumer ipTestConsumer;
  
  private IPTestStub ipTest;
  
  private IPTestResult result;
  
  private AssertionList assertions;
  
  private Hashtable consumerContexts;
  
  private IdentifierList[] domains;
  
  public HeaderTestProcedureImpl() {
    consumerContexts = new Hashtable();
    resetAssertions();
    domains = new IdentifierList[5];
    domains[0] = HeaderTestProcedure.getDomain(0);
    domains[1] = HeaderTestProcedure.getDomain(1);
    domains[2] = HeaderTestProcedure.getDomain(2);
    domains[3] = HeaderTestProcedure.getDomain(3);
    domains[4] = HeaderTestProcedure.getDomain(4);
  }
  
  private void resetAssertions() {
    assertions = new AssertionList();
  }

  public boolean logTime(String str)
  {
    logMessage("TIMESTAMP (" + str + ") " + new java.util.Date().getTime());
    return true;
  }

  public boolean initiatePublishRegisterWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel, 
      String sessionType, String sharedBroker, int domain)
      throws Exception
  {
    logMessage("initiatePublishRegisterWithQosAndSessionAndSharedBrokerAndDomain(" + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();
    
    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(HeaderTestProcedure.RIGHT_ENTITY_KEY);
    UInteger errorCode = new UInteger(999);
    TestPublishRegister testPublishRegister = 
      new TestPublishRegister(qos, HeaderTestProcedure.PRIORITY, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, false, entityKeys, errorCode);
    ipTest.publishRegister(testPublishRegister);
    return true;
  }
  
  public boolean CallTheOperationGetResult() throws MALInteractionException, MALException
  {
    LoggingBase.logMessage("HeaderTestProcedure.CallTheOperationGetResult()");
    result = ipTest.getResult(null);
    return true;
  }
  
  public boolean theProviderAssertions()
  {
    LoggingBase.logMessage("HeaderTestProcedure.theProviderAssertions()");
    return AssertionHelper.checkAssertions(result.getAssertions());
  }
  
  public static FileBasedDirectory.URIpair getProviderURIs(boolean shared) {
    FileBasedDirectory.URIpair uris;
    if (shared) {
      uris = FileBasedDirectory.loadURIs(TestServiceProvider.IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
    } else {
      uris = FileBasedDirectory.loadURIs(IPTestHelper.IPTEST_SERVICE_NAME.getValue());
    }
    return uris;
  }
  
  public boolean initiateRegisterWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel, String sessionType, String sharedBroker, int domain)
      throws Exception
  {
    logMessage("initiateRegisterWithQosAndSessionAndSharedBrokerAndDomain(" + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();
    
    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(HeaderTestProcedure.RIGHT_ENTITY_KEY);
    Boolean onlyOnChange =  false;
    EntityRequest entityRequest = new EntityRequest(
        null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, 
        onlyOnChange, entityKeys);
    EntityRequestList entityRequests = new EntityRequestList();
    entityRequests.add(entityRequest);
    Subscription subscription = new Subscription(HeaderTestProcedure.SUBSCRIPTION_ID, entityRequests);
    MonitorListener listener = new MonitorListener();
    
    ConsumerContext consumerContext = new ConsumerContext(listener);
    
    logMessage("new consumer context");
    consumerContexts.put(
        new ConsumerKey(qosLevel, sessionType, sharedBroker),
        consumerContext);
    
    FileBasedDirectory.URIpair uris = getProviderURIs(shared);
    
    Blob brokerAuthId = HeaderTestProcedure.getBrokerAuthId(shared);
    
    long timeBeforeRegister = System.currentTimeMillis();
    
    // Reset listener
    listener.setMonitorRegisterAckHeader(null);
    
    synchronized (listener.monitorRegisterCond)
    {
      logMessage("register");
      ipTest.asyncMonitorRegister(subscription, listener);
      listener.monitorRegisterCond.waitFor(Configuration.WAIT_TIME_OUT);
      listener.monitorRegisterCond.reset();
    }
    
    TestMessageHeader expectedMonitorRegisterHeader = new TestMessageHeader(
        ipTestConsumer.getConsumer().getURI(),
        HeaderTestProcedure.AUTHENTICATION_ID,
        uris.broker,
        new Time(timeBeforeRegister), 
        qos, 
        HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, 
        session, 
        sessionName, 
        InteractionType.PUBSUB,
        new UOctet(MALPubSubOperation._REGISTER_STAGE),
        null, // transaction id not checked here (see below)
        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
        IPTestHelper.IPTEST_SERVICE_NUMBER,
        IPTestHelper.MONITOR_OP.getNumber(),
        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
        Boolean.FALSE);
    
    MALMessage registerMsg = TransportInterceptor.instance().getLastSentMessage(ipTestConsumer.getConsumer().getURI());
    MALMessageHeader monitorRegisterHeader = registerMsg.getHeader();
    
    logMessage("HeaderTest Register header: " + monitorRegisterHeader);
    
    Long transactionId = monitorRegisterHeader.getTransactionId();
    consumerContext.setRegisterTransactionId(transactionId);
    
    AssertionHelper.checkHeader("PubSub.checkRegisterHeader", assertions, 
        monitorRegisterHeader,
        expectedMonitorRegisterHeader);
    
    String procedureName = "PubSub.checkRegisterAckHeader";
    
    consumerContext.checkTransactionIdUniqueness(procedureName, transactionId);
    
    MALMessageHeader monitorRegisterAckHeader =
      listener.getMonitorRegisterAckHeader();
    
    assertions.add(new Assertion(procedureName, "Register Error received", (monitorRegisterAckHeader != null)));
    
    if (monitorRegisterAckHeader == null) {
      return false;
    }
    
    TestMessageHeader expectedMonitorRegisterAckHeader = new TestMessageHeader(
        uris.broker,
        brokerAuthId,
        ipTestConsumer.getConsumer().getURI(),
        new Time(timeBeforeRegister), 
        qos, 
        HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, 
        session, 
        sessionName, 
        InteractionType.PUBSUB,
        new UOctet(MALPubSubOperation._REGISTER_ACK_STAGE),
        transactionId,
        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
        IPTestHelper.IPTEST_SERVICE_NUMBER,
        IPTestHelper.MONITOR_OP.getNumber(),
        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
        Boolean.FALSE);
    
    AssertionHelper.checkHeader(procedureName, assertions, 
        monitorRegisterAckHeader,
        expectedMonitorRegisterAckHeader);

    return true;
  }
  
  public boolean theConsumerAssertions()
  {
    return AssertionHelper.checkAssertions(assertions);
  }
  
  public boolean initiatePublishWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
      String sessionType, String sharedBroker, int domain) throws Exception
  {
    logMessage("initiatePublishWithQosAndSessionAndSharedBrokerAndDomain(" + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();
    
    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    
    UpdateHeader updateHeader1 = new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.CREATION, HeaderTestProcedure.RIGHT_ENTITY_KEY);
    TestUpdate update1 = new TestUpdate(new Integer(1));
    UpdateHeader updateHeader2 = new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.DELETION, HeaderTestProcedure.RIGHT_ENTITY_KEY);
    TestUpdate update2 = new TestUpdate(new Integer(2));
    UpdateHeader updateHeader3 = new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.MODIFICATION, HeaderTestProcedure.RIGHT_ENTITY_KEY);
    TestUpdate update3 = new TestUpdate(new Integer(3));
    UpdateHeader updateHeader4 = new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.UPDATE, HeaderTestProcedure.RIGHT_ENTITY_KEY);
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
    
    ConsumerContext cc = 
      (ConsumerContext) consumerContexts.get(new ConsumerKey(qosLevel, sessionType, sharedBroker));
    
    if (cc == null) {
      logMessage("The consumer context has not been found.");
      return false;
    }
    
    cc.setPublishTimeStamp(new Time(System.currentTimeMillis()));
    
    UInteger errorCode = new UInteger(999);
    TestPublishUpdate testPublishUpdate = new TestPublishUpdate(qos, HeaderTestProcedure.PRIORITY, HeaderTestProcedure.getDomain(domain), 
        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, false, updateHeaders, updates, errorCode, Boolean.FALSE, null);
    ipTest.publishUpdates(testPublishUpdate);

    return true;
  }
  
  public boolean getNotifyWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
      String sessionType, String sharedBroker, int domain) throws Exception {
    logMessage("getNotifyWithQosAndSessionAndSharedBrokerAndDomain(" + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();
    
    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    
    ConsumerContext cc = 
      (ConsumerContext) consumerContexts.get(new ConsumerKey(qosLevel, sessionType, sharedBroker));
    
    if (cc == null) {
      logMessage("The consumer context has not been found.");
      return false;
    }
    
    FileBasedDirectory.URIpair uris = getProviderURIs(shared);
    
    Blob brokerAuthId = HeaderTestProcedure.getBrokerAuthId(shared);
    
    TestMessageHeader expectedMonitorNotifyHeader = new TestMessageHeader(
        uris.broker,
        brokerAuthId, 
        ipTestConsumer.getConsumer().getURI(),
        cc.getPublishTimeStamp(), 
        qos, 
        HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, 
        session, 
        sessionName, 
        InteractionType.PUBSUB,
        new UOctet(MALPubSubOperation._NOTIFY_STAGE),
        cc.getTransactionId(),
        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
        IPTestHelper.IPTEST_SERVICE_NUMBER,
        IPTestHelper.MONITOR_OP.getNumber(),
        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
        Boolean.FALSE);
    
    MALMessageHeader monitorNotifyHeader =
      cc.getListener().getMonitorNotifyHeader();
    
    if (monitorNotifyHeader == null) {
      logMessage("Wait timeout");
      return false;
    }
    
    AssertionHelper.checkHeader("PubSub.checkNotifyHeader", assertions, 
        monitorNotifyHeader,
        expectedMonitorNotifyHeader);
    
    return true;
  }
  
  public boolean initiateNotifyErrorWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
      String sessionType, String sharedBroker, int domain) throws Exception {
    logMessage("initiateNotifyErrorWithQosAndSessionAndSharedBrokerAndDomain(" + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();
    
    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    
    ConsumerContext cc = 
      (ConsumerContext) consumerContexts.get(new ConsumerKey(qosLevel, sessionType, sharedBroker));
    
    FileBasedDirectory.URIpair uris = getProviderURIs(shared);
    
    Blob brokerAuthId = HeaderTestProcedure.getBrokerAuthId(shared);
    
    TestMessageHeader expectedMonitorNotifyErrorHeader = new TestMessageHeader(
        uris.broker,
        brokerAuthId, 
        ipTestConsumer.getConsumer().getURI(),
        cc.getPublishTimeStamp(), 
        qos, 
        HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, 
        session, 
        sessionName, 
        InteractionType.PUBSUB,
        new UOctet(MALPubSubOperation._NOTIFY_STAGE),
        cc.getTransactionId(),
        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
        IPTestHelper.IPTEST_SERVICE_NUMBER,
        IPTestHelper.MONITOR_OP.getNumber(),
        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
        Boolean.TRUE);
    
    TestEndPoint ep = TransportInterceptor.instance().getEndPoint(ipTestConsumer.getConsumer().getURI());
    
    MALMessage notifyMessage = ep.createTestMessage(
        expectedMonitorNotifyErrorHeader,
        new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, null), new Hashtable());
    
    // Inject the Notify error message
    ep.receive(notifyMessage);
    
    MALMessageHeader monitorNotifyErrorHeader =
      cc.getListener().getMonitorNotifyErrorHeader();
    
    if (monitorNotifyErrorHeader == null) {
      logMessage("Wait timeout");
      return false;
    }
    
    AssertionHelper.checkHeader("PubSub.checkNotifyHeader", assertions, 
        monitorNotifyErrorHeader,
        expectedMonitorNotifyErrorHeader);
    
    return true;
  }
  
  public boolean initiatePublishErrorWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
      String sessionType, String sharedBroker, int domain) throws Exception
  {
    logMessage("initiatePublishErrorWithQosAndSession(" + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();
    
    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    
    UpdateHeader updateHeader = new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.CREATION, HeaderTestProcedure.WRONG_ENTITY_KEY);
    TestUpdate update = new TestUpdate(new Integer(1));
    
    UpdateHeaderList updateHeaders = new UpdateHeaderList();
    updateHeaders.add(updateHeader);
    
    TestUpdateList updates = new TestUpdateList();
    updates.add(update);
    
    ConsumerContext cc = 
      (ConsumerContext) consumerContexts.get(new ConsumerKey(qosLevel, sessionType, sharedBroker));
    
    if (cc == null) {
      logMessage("The consumer context has not been found.");
      return false;
    }
    
    cc.setPublishTimeStamp(new Time(System.currentTimeMillis()));
    
    UInteger errorCode = MALHelper.UNKNOWN_ERROR_NUMBER;
    EntityKeyList failedEntityKeys = new EntityKeyList();
    failedEntityKeys.add(HeaderTestProcedure.WRONG_ENTITY_KEY);
    TestPublishUpdate testPublishUpdate = new TestPublishUpdate(qos, HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, false, updateHeaders, updates, errorCode,
        Boolean.FALSE, failedEntityKeys);
    ipTest.publishUpdates(testPublishUpdate);

    return true;
  }
  
  public boolean initiateDeregisterWithQosAndSessionAndSharedBrokerAndDomain(
      String qosLevel, String sessionType, String sharedBroker, int domain)
      throws Exception
  {
    logMessage("initiateDeregisterWithQosAndSessionAndSharedBrokerAndDomain(" + qosLevel
        + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();

    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    
    ConsumerContext consumerContext = 
      (ConsumerContext) consumerContexts.get(new ConsumerKey(qosLevel, sessionType, sharedBroker));
    
    if (consumerContext == null) {
      logMessage("The consumer context has not been found.");
      return false;
    }
    
    FileBasedDirectory.URIpair uris = getProviderURIs(shared);
    
    Blob brokerAuthId = HeaderTestProcedure.getBrokerAuthId(shared);
    
    long timeBeforeDeregister = System.currentTimeMillis();
    
    IdentifierList subIdList = new IdentifierList();
    subIdList.add(HeaderTestProcedure.SUBSCRIPTION_ID);
    synchronized (consumerContext.listener.monitorDeregisterCond)
    {
      logMessage("deregister");
      consumerContext.listener.monitorDeregisterCond.reset();
      ipTest.asyncMonitorDeregister(subIdList, consumerContext.listener);
      consumerContext.listener.monitorDeregisterCond.waitFor(Configuration.WAIT_TIME_OUT);
      consumerContext.listener.monitorDeregisterCond.reset();
    }
    
    TestMessageHeader expectedDemonitorRegisterHeader = new TestMessageHeader(
        ipTestConsumer.getConsumer().getURI(),
        HeaderTestProcedure.AUTHENTICATION_ID,
        uris.broker,
        new Time(timeBeforeDeregister), 
        qos, 
        HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, 
        session, 
        sessionName, 
        InteractionType.PUBSUB,
        new UOctet(MALPubSubOperation._DEREGISTER_STAGE),
        null, // transaction id not checked here (see below)
        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
        IPTestHelper.IPTEST_SERVICE_NUMBER,
        IPTestHelper.MONITOR_OP.getNumber(),
        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
        Boolean.FALSE);
    
    MALMessage deregisterMsg = TransportInterceptor.instance().getLastSentMessage(ipTestConsumer.getConsumer().getURI());
    MALMessageHeader monitorDeregisterHeader = deregisterMsg.getHeader();
    
    AssertionHelper.checkHeader("PubSub.checkRegisterHeader", assertions, 
        monitorDeregisterHeader,
        expectedDemonitorRegisterHeader);
    
    String procedureName = "PubSub.checkDeregisterAckHeader";
    
    Long transactionId = monitorDeregisterHeader.getTransactionId();
    consumerContext.checkTransactionIdUniqueness(procedureName, transactionId);
    
    MALMessageHeader monitorDeregisterAckHeader =
      consumerContext.getListener().getMonitorDeregisterAckHeader();
    
    if (monitorDeregisterAckHeader == null) {
      logMessage("Wait timeout");
      return false;
    }
    
    TestMessageHeader expectedMonitorDeregisterAckHeader = new TestMessageHeader(
        uris.broker,
        brokerAuthId,
        ipTestConsumer.getConsumer().getURI(),
        new Time(timeBeforeDeregister), 
        qos, 
        HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, 
        session, 
        sessionName, 
        InteractionType.PUBSUB,
        new UOctet(MALPubSubOperation._DEREGISTER_ACK_STAGE),
        transactionId,
        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
        IPTestHelper.IPTEST_SERVICE_NUMBER,
        IPTestHelper.MONITOR_OP.getNumber(),
        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
        Boolean.FALSE);
    
    AssertionHelper.checkHeader(procedureName, assertions, 
        monitorDeregisterAckHeader,
        expectedMonitorDeregisterAckHeader);
    
    return true;
  }
  
  public boolean initiatePublishDeregisterWithQosAndSessionAndSharedBrokerAndDomain(
      String qosLevel, String sessionType, String sharedBroker, int domain)
      throws Exception
  {
    logMessage("initiatePublishDeregisterWithQosAndSessionAndSharedBrokerAndDomain("
        + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();

    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    UInteger errorCode = new UInteger(999);
    TestPublishDeregister testPublishDeregister = new TestPublishDeregister(qos,
        HeaderTestProcedure.PRIORITY, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, false, null,
        errorCode);
    ipTest.publishDeregister(testPublishDeregister);
    return true;
  }
  
  public boolean initiatePublishRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain(
      String qosLevel, String sessionType, String sharedBroker, int domain)
      throws Exception
  {
    logMessage("initiatePublishRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain("
        + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();

    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(HeaderTestProcedure.PUBLISH_REGISTER_ERROR_ENTITY_KEY);
    UInteger errorCode = MALHelper.INTERNAL_ERROR_NUMBER;
    TestPublishRegister testPublishRegister = new TestPublishRegister(qos,
        HeaderTestProcedure.PRIORITY, HeaderTestProcedure.getDomain(domain), HeaderTestProcedure.NETWORK_ZONE, session, sessionName, false, entityKeys,
        errorCode);
    ipTest.publishRegister(testPublishRegister);
    return true;
  }
  
  
  
  public boolean initiateRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain(
      String qosLevel, String sessionType, String sharedBroker, int domain)
      throws Exception
  {
    logMessage("initiateRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain("
        + qosLevel + ',' + sessionType + ',' + sharedBroker + ',' + domain+ ')');
    resetAssertions();

    QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    boolean shared = Boolean.parseBoolean(sharedBroker);
    ipTestConsumer = LocalMALInstance.instance().ipTestStub(HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.getDomain(domain),
        HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos, HeaderTestProcedure.PRIORITY, shared);
    ipTest = ipTestConsumer.getStub();
    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(HeaderTestProcedure.RIGHT_ENTITY_KEY);
    Boolean onlyOnChange = Boolean.FALSE;
    EntityRequest entityRequest = new EntityRequest(
        null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, 
        onlyOnChange, entityKeys);
    EntityRequestList entityRequests = new EntityRequestList();
    entityRequests.add(entityRequest);
    Subscription subscription = new Subscription(
        HeaderTestProcedure.REGISTER_ERROR_SUBSCRIPTION_ID, entityRequests);
    MonitorListener listener = new MonitorListener();

    ConsumerContext consumerContext = new ConsumerContext(listener);

    logMessage("new consumer context");
    consumerContexts.put(new ConsumerKey(qosLevel, sessionType, sharedBroker),
        consumerContext);

    FileBasedDirectory.URIpair uris = getProviderURIs(shared);

    Blob brokerAuthId = HeaderTestProcedure.getBrokerAuthId(shared);

    long timeBeforeRegister = System.currentTimeMillis();
    
    // Reset listener
    listener.setMonitorRegisterErrorHeader(null);
    listener.setMonitorRegisterError(null);

    synchronized (listener.monitorRegisterCond)
    {
      logMessage("register");
      ipTest.asyncMonitorRegister(subscription, listener);
      listener.monitorRegisterCond.waitFor(Configuration.WAIT_TIME_OUT);
      listener.monitorRegisterCond.reset();
    }

    MALMessage registerMsg = TransportInterceptor.instance()
        .getLastSentMessage(ipTestConsumer.getConsumer().getURI());
    MALMessageHeader monitorRegisterHeader = registerMsg.getHeader();

    String procedureName = "PubSub.checkRegisterErrorHeader";

    MALMessageHeader monitorRegisterErrorHeader = listener
        .getMonitorRegisterErrorHeader();
    
    assertions.add(new Assertion(procedureName, "Register Error received", (monitorRegisterErrorHeader != null)));

    if (monitorRegisterErrorHeader == null)
    {
      return false;
    }

    TestMessageHeader expectedMonitorRegisterErrorHeader = new TestMessageHeader(
        uris.broker, 
        brokerAuthId, 
        ipTestConsumer.getConsumer().getURI(),
        new Time(timeBeforeRegister), 
        qos,
        HeaderTestProcedure.PRIORITY, 
        HeaderTestProcedure.getDomain(domain), 
        HeaderTestProcedure.NETWORK_ZONE,
        session, 
        sessionName, 
        InteractionType.PUBSUB, 
        MALPubSubOperation.REGISTER_ACK_STAGE,
        monitorRegisterHeader.getTransactionId(),
        MALPrototypeHelper.MALPROTOTYPE_AREA_NUMBER,
        IPTestHelper.IPTEST_SERVICE_NUMBER,
        IPTestHelper.MONITOR_OP.getNumber(),
        MALPrototypeHelper.MALPROTOTYPE_AREA.getVersion(),
        Boolean.TRUE);

    AssertionHelper.checkHeader(procedureName, assertions,
        monitorRegisterErrorHeader, expectedMonitorRegisterErrorHeader);
    
    MALStandardError monitorRegisterError = listener.getMonitorRegisterError();

    assertions.add(new Assertion(procedureName,
        "Error received", (monitorRegisterError != null)));
    if (monitorRegisterError != null) {
      AssertionHelper.checkEquality(procedureName,
        assertions, "errorNumber", monitorRegisterError.getErrorNumber(),
        MALHelper.INTERNAL_ERROR_NUMBER);
    }
    
    return true;
  }
  
  static class MonitorListener extends IPTestAdapter
  {
    private final BooleanCondition monitorRegisterCond = new BooleanCondition();
    private MALMessageHeader monitorRegisterAckHeader;
    
    private final BooleanCondition monitorNotifyCond = new BooleanCondition();
    private MALMessageHeader monitorNotifyHeader;
    
    private final BooleanCondition monitorNotifyErrorCond = new BooleanCondition();
    private MALMessageHeader monitorNotifyErrorHeader;
    
    private final BooleanCondition monitorDeregisterCond = new BooleanCondition();
    private MALMessageHeader monitorDeregisterAckHeader;
    
    private MALMessageHeader monitorRegisterErrorHeader;

    private MALStandardError monitorRegisterError;

		@Override
    public synchronized void monitorRegisterAckReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      monitorRegisterAckHeader = msgHeader;
      monitorRegisterCond.set();
    }

    @Override
    public synchronized void monitorRegisterErrorReceived(MALMessageHeader msgHeader,
        MALStandardError error, Map qosProperties)
    {
      monitorRegisterErrorHeader = msgHeader;
      monitorRegisterError = error;
      monitorRegisterCond.set();
    }
    
    @Override
    public synchronized void monitorNotifyReceived(MALMessageHeader msgHeader,
        Identifier subscriptionId, UpdateHeaderList updateHeaderList,
        TestUpdateList updateList, Map qosProperties)
    {
      logMessage("monitorNotifyReceived: " + msgHeader);
      monitorNotifyHeader = msgHeader;
      monitorNotifyCond.set();
    }
    
    @Override
    public synchronized void monitorNotifyErrorReceived(MALMessageHeader msgHeader,
        MALStandardError error, Map qosProperties)
    {
      monitorNotifyErrorHeader = msgHeader;
      monitorNotifyErrorCond.set();
    }

    @Override
    public synchronized void monitorDeregisterAckReceived(MALMessageHeader msgHeader, Map qosProperties)
    {
      monitorDeregisterAckHeader = msgHeader;
      monitorDeregisterCond.set();
    }

    public MALMessageHeader getMonitorRegisterAckHeader()
    {
      return monitorRegisterAckHeader;
    }
    
    public void setMonitorRegisterAckHeader(
        MALMessageHeader monitorRegisterAckHeader)
    {
      this.monitorRegisterAckHeader = monitorRegisterAckHeader;
    }

    public MALMessageHeader getMonitorRegisterErrorHeader()
    {
      return monitorRegisterErrorHeader;
    }

    public void setMonitorRegisterErrorHeader(
        MALMessageHeader monitorRegisterErrorHeader)
    {
      this.monitorRegisterErrorHeader = monitorRegisterErrorHeader;
    }

    public MALStandardError getMonitorRegisterError()
    {
      return monitorRegisterError;
    }

    public void setMonitorRegisterError(MALStandardError monitorRegisterError)
    {
      this.monitorRegisterError = monitorRegisterError;
    }

    public MALMessageHeader getMonitorNotifyHeader()
    {
      if (null == monitorNotifyHeader) {
        try
        {
          monitorNotifyCond.waitFor(Configuration.WAIT_TIME_OUT);
        }
        catch (InterruptedException e)
        {
        }
        
        monitorNotifyCond.reset();
      }
      return monitorNotifyHeader;
    }
    
    public MALMessageHeader getMonitorNotifyErrorHeader()
    {
      if (null == monitorNotifyErrorHeader) {
        try
        {
          monitorNotifyErrorCond.waitFor(Configuration.WAIT_TIME_OUT);
        }
        catch (InterruptedException e)
        {
        }
        
        monitorNotifyErrorCond.reset();
      }
      return monitorNotifyErrorHeader;
    }
    
    public MALMessageHeader getMonitorDeregisterAckHeader()
    {
      return monitorDeregisterAckHeader;
    }
  }
  
  static class ConsumerKey {
    private String qosLevel;
    private String sessionType;
    private String sharedBroker;
    
    public ConsumerKey(String qosLevel, String sessionType, String sharedBroker)
    {
      super();
      this.qosLevel = qosLevel;
      this.sessionType = sessionType;
      this.sharedBroker = sharedBroker;
    }
    
    @Override
    public int hashCode() {
      return qosLevel.hashCode() + 
        sessionType.hashCode() + 
        sharedBroker.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof ConsumerKey) {
        ConsumerKey sk = (ConsumerKey) obj;
        if (! sk.qosLevel.equals(qosLevel))
        {
          return false;
        }
        if (! sk.sessionType.equals(sessionType))
        {
          return false;
        }
        if (! sk.sharedBroker.equals(sharedBroker))
        {
          return false;
        }
        return true;
      } else {
        return false;
      }
    } 
  }
  
  class ConsumerContext {
    final private MonitorListener listener;
    private Time publishTimeStamp;
    private Long transactionId;
    private Vector transactionIds;
    
    public ConsumerContext(MonitorListener listener)
    {
      super();
      this.listener = listener;
      transactionIds = new Vector();
    }

    public MonitorListener getListener()
    {
      return listener;
    }

    public Time getPublishTimeStamp()
    {
      return publishTimeStamp;
    }

    public void setPublishTimeStamp(Time publishTimeStamp)
    {
      this.publishTimeStamp = publishTimeStamp;
    }
    
    public Long getTransactionId() 
    {
      return transactionId;
    }

    public void setRegisterTransactionId(Long transactionId)
    {
      this.transactionId = transactionId;
    }
    
    public void checkTransactionIdUniqueness(
        String procedureName,
        Long transactionId) {
      Assertion tidAssertion = new Assertion(procedureName,
          "Transaction identifier uniqueness: " + transactionId, (transactionIds.indexOf(transactionId) == -1));
      transactionIds.addElement(transactionId);
      assertions.add(tidAssertion);
    }
  }
}
