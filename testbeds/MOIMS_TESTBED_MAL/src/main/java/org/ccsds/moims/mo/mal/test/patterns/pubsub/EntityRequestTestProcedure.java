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
import java.util.StringTokenizer;
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
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class EntityRequestTestProcedure extends LoggingBase
{
  public static final SessionType SESSION = SessionType.LIVE;
  public static final Identifier SESSION_NAME = new Identifier("LIVE");
  public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
  public static final UInteger PRIORITY = new UInteger(1);
  
  public static final Identifier SUBSCRIPTION_ID = new Identifier("EntityRequestSubscription");
 
  private UpdateHeaderList updateHeaderList;
  
  private TestUpdateList updateList;
  
  private IPTestStub ipTest;
  
  public boolean initiatePublisherWithEntitiesAndSharedBroker(String entities, String sharedBroker) throws Exception
  {
    logMessage("EntityRequestTestProcedure.initiatePublisherWithEntitiesAndSharedBroker({" + 
        entities + "}," + sharedBroker + ")");
    
    boolean shared = Boolean.parseBoolean(sharedBroker);
    
    EntityKeyList entityKeyList = parseEntityKeyList(entities);
    
    ipTest = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, 
        HeaderTestProcedure.DOMAIN, 
        HeaderTestProcedure.NETWORK_ZONE, 
        SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, shared).getStub();
    
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishRegister testPublishRegister = 
      new TestPublishRegister(QOS_LEVEL, PRIORITY, 
          HeaderTestProcedure.DOMAIN, 
          HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, entityKeyList, expectedErrorCode);
    ipTest.publishRegister(testPublishRegister);
    
    updateHeaderList = new UpdateHeaderList();
    updateList = new TestUpdateList();
    for (int i = 0; i < entityKeyList.size(); i++) {
      updateHeaderList.add(new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.CREATION,
          entityKeyList.get(i)));
      updateList.add(new TestUpdate(new Integer(i)));
    }
    
    return true;
  }
  
  public static EntityKeyList parseEntityKeyList(String s) {
    EntityKeyList entityKeyList = new EntityKeyList();
    StringTokenizer st = new StringTokenizer(s, " ,");
    while (st.hasMoreTokens()) {
      entityKeyList.add(parseEntityKey(st.nextToken()));
    }
    return entityKeyList;
  }
  
  public static EntityKey parseEntityKey(String s) {
    StringTokenizer st = new StringTokenizer(s, ".");
    EntityKey k = new EntityKey();
    k.setFirstSubKey(parseEntitySubKey(st.nextToken()));
    k.setSecondSubKey(parseSubKey(st.nextToken()));
    k.setThirdSubKey(parseSubKey(st.nextToken()));
    k.setFourthSubKey(parseSubKey(st.nextToken()));
    return k;
  }
  
  public static Identifier parseEntitySubKey(String s) {
    if (s.equals("[null]")) {
      return null;
    } else {
      return new Identifier(s);
    }
  }
  
  public static Long parseSubKey(String s) {
    if (s.equals("[null]")) {
      return null;
    } else if (s.equals("*")) {
      return new Long(0);
    } else {
      return new Long(Long.parseLong(s));
    }
  }
  
  public boolean subscribeToPatternAndExpectedEntities(String pattern, String expectedEntities)
  throws Exception
  {
    logMessage("EntityRequestTestProcedure.subscribeToPatternAndCheckExpectedEntities({" + 
        pattern + "},{" + expectedEntities + "})");

    EntityKeyList expectedKeys = parseEntityKeyList(expectedEntities);
    
    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(parseEntityKey(pattern));
    Boolean onlyOnChange = false;
    EntityRequest entityRequest = new EntityRequest(
        null, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, 
        onlyOnChange, entityKeys);
    EntityRequestList entityRequests = new EntityRequestList();
    entityRequests.add(entityRequest);
    Subscription subscription = new Subscription(SUBSCRIPTION_ID, entityRequests);
    
    MonitorListener listener = new MonitorListener();
    
    ipTest.monitorRegister(subscription, listener);

    UInteger expectedErrorCode = new UInteger(999);
    TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE, 
        SESSION, SESSION_NAME, false, updateHeaderList, updateList, expectedErrorCode,
        Boolean.FALSE, null);
    
    ipTest.publishUpdates(testPublishUpdate);
    
    // Check the Notify arrival
    // The QoS level is Assured (FIFO) so the Notify messages arrived
    // before the 'publishUpdates' ack.
    
    EntityKeyList notifiedKeys = listener.getNotifiedKeys();
    
    IdentifierList idList = new IdentifierList();
    idList.add(SUBSCRIPTION_ID);
    
    ipTest.monitorDeregister(idList);
    
    AssertionList assertions = new AssertionList();
    
    String procedureName = "PubSub.checkEntityRequest";
    checkIsContainedInto(expectedKeys, notifiedKeys, assertions, 
        procedureName, "Expected key has been found: ");
    checkIsContainedInto(notifiedKeys, expectedKeys, assertions, 
        procedureName, "Received key is expected: ");
    
    return AssertionHelper.checkAssertions(assertions);
  }
  
  public boolean publishDeregister() throws Exception
  {
    logMessage("EntityRequestTestProcedure.publishDeregister()");
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
        QOS_LEVEL, PRIORITY, 
        HeaderTestProcedure.DOMAIN, 
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, null, expectedErrorCode);
    ipTest.publishDeregister(testPublishDeregister);
    return true;
  }

  public void checkIsContainedInto(EntityKeyList containedList, EntityKeyList containerList,
      AssertionList assertions, String procedureName, String info) {
    for (int i = 0; i < containedList.size(); i++) {
      EntityKey key = containedList.get(i);
      boolean res;
        res = containerList.indexOf(key) >= 0;
      assertions.add(new Assertion(procedureName, info + 
          key.getFirstSubKey() + '.' + key.getSecondSubKey() + '.' +
          key.getThirdSubKey() + '.' + key.getFourthSubKey(), res));
    }
  }
  
  static class MonitorListener extends IPTestAdapter
  {
    
    private EntityKeyList notifiedKeys;
    
    MonitorListener() {
      notifiedKeys = new EntityKeyList();
    }

		@Override
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
        Identifier subscriptionId, UpdateHeaderList updateHeaderList,
        TestUpdateList updateList, Map qosProperties)
    {
      for (UpdateHeader updateHeader : updateHeaderList) {
        notifiedKeys.add(updateHeader.getKey());
      }
    }
    
    EntityKeyList getNotifiedKeys() {
      return notifiedKeys;
    }
  }
}
