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

import java.util.Map;
import java.util.Vector;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALNotifyBody;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest.structures.*;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class SubscriptionAreaTestProcedure extends LoggingBase
{
  public static final SessionType SESSION = SessionType.LIVE;
  public static final Identifier SESSION_NAME = new Identifier("LIVE");
  public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
  public static final UInteger PRIORITY = new UInteger(1);

  public static final Identifier SUBSCRIPTION_ID = new Identifier(
      "EntityRequestSubscription");
  public static final Identifier ENTITY_A = new Identifier("A");
  public static final Long ALL_IDS = new Long(0);

  private IPTestStub ipTest;
  
  private org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestStub ipTestFromArea2;
  
  private MonitorListener listener;

  public boolean initiatePublishers() throws Exception
  {
    logMessage("SubscriptionAreaTestProcedure.initiatePublishers()");

    ipTest = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, QOS_LEVEL,
        PRIORITY, true).getStub();
    
    ipTestFromArea2 = LocalMALInstance.instance().newIPTestFromArea2Stub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, QOS_LEVEL,
        PRIORITY, true);

    EntityKeyList entityKeyList = new EntityKeyList();
    entityKeyList.add(new EntityKey(ENTITY_A, ALL_IDS, ALL_IDS, ALL_IDS));

    UInteger expectedErrorCode = new UInteger(999);
    TestPublishRegister testPublishRegister = new TestPublishRegister(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, entityKeyList,
        expectedErrorCode);
    ipTest.publishRegister(testPublishRegister);
    ipTestFromArea2.publishRegister(testPublishRegister);
    return true;
  }

  public int subscribeToAllAreasAndExpectedNotifyFromOtherServicesIs(
      boolean allAreas) throws Exception
  {
    logMessage("SubscriptionAreaTestProcedure.subscribeToAllAreasAndExpectedNotifyFromOtherServices("
        + allAreas + ")");
    
    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(new EntityKey(ENTITY_A, ALL_IDS, ALL_IDS, ALL_IDS));
    Boolean onlyOnChange = false;
    EntityRequest entityRequest = new EntityRequest(null,
        allAreas,
        Boolean.FALSE, 
        Boolean.FALSE, 
        onlyOnChange,
        entityKeys);
    EntityRequestList entityRequests = new EntityRequestList();
    entityRequests.add(entityRequest);
    Subscription subscription = new Subscription(SUBSCRIPTION_ID,
        entityRequests);
    
    listener = new MonitorListener();
    
    ipTest.monitorRegister(subscription, listener);
    
    UpdateHeaderList updateHeaderList = new UpdateHeaderList();
    updateHeaderList.add(new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.CREATION,
        new EntityKey(ENTITY_A, null, null, null)));
    
    TestUpdateList updateList = new TestUpdateList();
    updateList.add(new TestUpdate(new Integer(0)));
    
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishUpdate testPublishUpdate = new TestPublishUpdate(QOS_LEVEL,
        PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE,
        SESSION, SESSION_NAME, false, updateHeaderList, updateList, expectedErrorCode, 
        Boolean.FALSE, null);
    
    ipTest.publishUpdates(testPublishUpdate);
    ipTestFromArea2.publishUpdates(testPublishUpdate);
    
    synchronized (listener.monitorCond)
    {
      listener.monitorCond.waitFor(Configuration.WAIT_TIME_OUT);
      listener.monitorCond.reset();
    }

    IdentifierList idList = new IdentifierList();
    idList.add(SUBSCRIPTION_ID);
    ipTest.monitorDeregister(idList);
    
    return listener.countNotifyFromOtherServices();
  }
  
  public boolean checkNotifyHeader() {
    return listener.checkHeaderAssertions();
  }

  public boolean publishDeregister() throws Exception
  {
    logMessage("SubscriptionAreaTestProcedure.publishDeregister()");
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, null,
        expectedErrorCode);
    ipTest.publishDeregister(testPublishDeregister);
    ipTestFromArea2.publishDeregister(testPublishDeregister);
    return true;
  }
  
  static class MonitorListener extends IPTestAdapter
  {
    private final BooleanCondition monitorCond = new BooleanCondition();

    private Vector receivedNotifyFromOtherServices;

    MonitorListener()
    {
      receivedNotifyFromOtherServices = new Vector();
    }
    
    @Override
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
        Identifier subscriptionId, UpdateHeaderList updateHeaderList,
        TestUpdateList updateList, Map qosProperties)
    {
      logMessage("MonitorListener.monitorNotifyReceived(" + msgHeader + ','
          + updateHeaderList + ')');
    }

    @Override
    public void notifyReceivedFromOtherService(MALMessageHeader msgHeader,
        MALNotifyBody body, Map qosProperties) throws MALException
    {
      logMessage("MonitorListener.notifyReceivedFromOtherService(" + msgHeader + ','
          + body.getSubscriptionId() + ')');
      receivedNotifyFromOtherServices.addElement(msgHeader);
      monitorCond.set();
    }

    int countNotifyFromOtherServices()
    {
      return receivedNotifyFromOtherServices.size();
    }
    
    public boolean checkHeaderAssertions() {
      AssertionList assertions = new AssertionList();
      String procedureName = "PubSub.checkSubscriptionArea";
      for (int i = 0; i < receivedNotifyFromOtherServices.size(); i++) {
        MALMessageHeader msgHeader = (MALMessageHeader) receivedNotifyFromOtherServices.elementAt(i);
        assertions.add(new Assertion(procedureName, 
          "The area of the other service is: " + 
          org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.IPTEST_SERVICE.getArea().getName(),
          msgHeader.getServiceArea().equals(org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.IPTEST_SERVICE.getArea().getNumber())));
        assertions.add(new Assertion(procedureName, 
          "The name of the other service is: " + IPTestHelper.IPTEST_SERVICE_NAME,
          msgHeader.getService().equals(IPTestHelper.IPTEST_SERVICE_NUMBER)));
        assertions.add(new Assertion(procedureName, 
          "The operation of the other service is: " + IPTestHelper.MONITOR_OP.getName(),
          msgHeader.getOperation().equals(IPTestHelper.MONITOR_OP.getNumber())));
      }
      return AssertionHelper.checkAssertions(assertions);
    }
  }
}
