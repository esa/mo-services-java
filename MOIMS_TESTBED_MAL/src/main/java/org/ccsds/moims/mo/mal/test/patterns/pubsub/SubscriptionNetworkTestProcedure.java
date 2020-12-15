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
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.EntityRequest;
import org.ccsds.moims.mo.mal.structures.EntityRequestList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.structures.UpdateType;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestAdapter;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestUpdateList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.testbed.suite.BooleanCondition;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class SubscriptionNetworkTestProcedure extends LoggingBase
{
  public static final SessionType SESSION = SessionType.LIVE;
  public static final Identifier SESSION_NAME = new Identifier("LIVE");
  public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
  public static final UInteger PRIORITY = new UInteger(1);

  public static final Identifier SUBSCRIPTION_ID = new Identifier(
      "EntityRequestSubscription");
  public static final Identifier ENTITY_A = new Identifier("A");
  public static final Long ALL_IDS = new Long (0);

  private IPTestStub ipTestToPublish;
  
  private IPTestStub ipTestToSubscribe;
  
  private MonitorListener listener;
  
  private Identifier publishNetworkId;
  
  private Identifier subscribeNetworkId;
  
  private boolean shared;

  public boolean initiatePublisherWithNetworkAndSharedBroker(String network, boolean shared) throws Exception
  {
    logMessage("SubscriptionNetworkAndSharedBrokerTestProcedure.initiatePublisherWithNetwork(" + 
        network + ',' + shared + ")");
    
    this.shared = shared;

    publishNetworkId = new Identifier(network);
    ipTestToPublish = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
        publishNetworkId, SESSION, SESSION_NAME, QOS_LEVEL,
        PRIORITY, shared).getStub();

    EntityKeyList entityKeyList = new EntityKeyList();
    entityKeyList.add(new EntityKey(ENTITY_A, ALL_IDS, ALL_IDS, ALL_IDS));

    UInteger expectedErrorCode = new UInteger(999);
    TestPublishRegister testPublishRegister = new TestPublishRegister(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN,
        new Identifier(network), SESSION, SESSION_NAME, false, entityKeyList,
        expectedErrorCode);
    ipTestToPublish.publishRegister(testPublishRegister);
    return true;
  }

  public boolean subscribeWithNetworkAndExpectedNotify(String network, int notifyNumber) throws Exception
  {
    logMessage("SubscriptionNetworkTestProcedure.subscribeWithNetworkAndExpectedNotify("
        + network + ',' + notifyNumber + ")");
    
    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(new EntityKey(ENTITY_A, ALL_IDS, ALL_IDS, ALL_IDS));
    Boolean onlyOnChange = false;
    EntityRequest entityRequest = new EntityRequest(null, 
        Boolean.FALSE, 
        Boolean.FALSE,
        Boolean.FALSE, 
        onlyOnChange, entityKeys);
    EntityRequestList entityRequests = new EntityRequestList();
    entityRequests.add(entityRequest);
    Subscription subscription = new Subscription(SUBSCRIPTION_ID,
        entityRequests);
    
    listener = new MonitorListener();
    
    subscribeNetworkId = new Identifier(network);
    ipTestToSubscribe = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
        subscribeNetworkId, SESSION, SESSION_NAME, QOS_LEVEL,
        PRIORITY, shared).getStub();
    
    ipTestToSubscribe.monitorRegister(subscription, listener);
    
    UpdateHeaderList updateHeaderList = new UpdateHeaderList();
    updateHeaderList.add(new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.MODIFICATION, 
        new EntityKey(ENTITY_A, null, null, null)));
    
    TestUpdateList updateList = new TestUpdateList();
    updateList.add(new TestUpdate(new Integer(0)));
    
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishUpdate testPublishUpdate = new TestPublishUpdate(QOS_LEVEL,
        PRIORITY, HeaderTestProcedure.DOMAIN, publishNetworkId,
        SESSION, SESSION_NAME, false, updateHeaderList, updateList, expectedErrorCode, false, null);
    
    ipTestToPublish.publishUpdates(testPublishUpdate);
    
    synchronized (listener.monitorCond)
    {
      listener.monitorCond.waitFor(Configuration.WAIT_TIME_OUT);
      listener.monitorCond.reset();
    }
    
    IdentifierList idList = new IdentifierList();
    idList.add(SUBSCRIPTION_ID);
    ipTestToSubscribe.monitorDeregister(idList);

    AssertionList assertions = new AssertionList();
    String procedureName = "PubSub.checkSubscriptionNetwork";
    assertions.add(new Assertion(procedureName, 
        "The number of expected notify " + notifyNumber,
            (notifyNumber == listener.countNotify())));
    return AssertionHelper.checkAssertions(assertions);
  }
  
  public boolean checkNotifyHeader() {
    return listener.checkHeaderAssertions();
  }

  public boolean publishDeregister() throws Exception
  {
    logMessage("SubscriptionNetworkTestProcedure.publishDeregister()");
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, null,
        expectedErrorCode);
    ipTestToPublish.publishDeregister(testPublishDeregister);
    return true;
  }
  
  class MonitorListener extends IPTestAdapter
  {
    private final BooleanCondition monitorCond = new BooleanCondition();

    private Vector receivedNotify;

    MonitorListener()
    {
      receivedNotify = new Vector();
    }

    @Override
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
        Identifier subscriptionId, UpdateHeaderList updateHeaderList,
        TestUpdateList updateList, Map qosProperties)
    {
      logMessage("MonitorListener.monitorNotifyReceived(" + msgHeader + ','
          + updateHeaderList + ')');
      receivedNotify.addElement(msgHeader);
      monitorCond.set();
    }

    int countNotify()
    {
      return receivedNotify.size();
    }
    
    public boolean checkHeaderAssertions() {
      AssertionList assertions = new AssertionList();
      String procedureName = "PubSub.checkSubscriptionNetwork";
      for (int i = 0; i < receivedNotify.size(); i++) {
        MALMessageHeader msgHeader = (MALMessageHeader) receivedNotify.elementAt(i);
        assertions.add(new Assertion(procedureName, 
            "The network zone of the notify is : " + publishNetworkId,
                msgHeader.getNetworkZone().equals(publishNetworkId)));
      }
      return AssertionHelper.checkAssertions(assertions);
    }
  }
}
