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

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.Attribute;
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
import org.ccsds.moims.mo.mal.test.util.AssertionHelper;
import org.ccsds.moims.mo.mal.test.util.Helper;
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
    
    ArrayList<AttributeList> entityKeyList = parseEntityKeyList(entities);

    ipTest = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, 
        HeaderTestProcedure.DOMAIN, 
        HeaderTestProcedure.NETWORK_ZONE, 
        SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, shared).getStub();
    
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishRegister testPublishRegister = 
      new TestPublishRegister(QOS_LEVEL, PRIORITY, 
          HeaderTestProcedure.DOMAIN, 
          HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, 
              false, Helper.get4TestKeys(), expectedErrorCode);
    ipTest.publishRegister(testPublishRegister);
    
    updateHeaderList = new UpdateHeaderList();
    updateList = new TestUpdateList();
    
    for (int i = 0; i < entityKeyList.size(); i++) {
      AttributeList attList = entityKeyList.get(i);
      updateHeaderList.add(new UpdateHeader(new Identifier(""), HeaderTestProcedure.DOMAIN, attList));
      updateList.add(new TestUpdate(i));
    }
    
    return true;
  }

  public static ArrayList<AttributeList> parseEntityKeyList(String s) {
    ArrayList<AttributeList> entityKeyList = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(s, " ,");
    while (st.hasMoreTokens()) {
      entityKeyList.add(parseEntityKey(st.nextToken()));
    }
    return entityKeyList;
  }
  
  public static AttributeList parseEntityKey(String s) {
    StringTokenizer st = new StringTokenizer(s, ".");
    AttributeList k = new AttributeList();
    k.add(parseEntitySubKey(st.nextToken()));
    k.add(parseEntitySubKey(st.nextToken()));
    k.add(parseEntitySubKey(st.nextToken()));
    k.add(parseEntitySubKey(st.nextToken()));
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

    ArrayList<AttributeList> expectedKeyValues = parseEntityKeyList(expectedEntities);
    //SubscriptionFilterList expectedKeyValues = parseKeyValueList(expectedEntities);   
    
    AttributeList values = parseEntityKey(pattern);
    
    SubscriptionFilterList filters = new SubscriptionFilterList();
    filters.add(new SubscriptionFilter(Helper.key1, new AttributeList((Attribute) Attribute.javaType2Attribute(values.get(0)))));
    filters.add(new SubscriptionFilter(Helper.key2, new AttributeList((Attribute) Attribute.javaType2Attribute(values.get(1)))));
    filters.add(new SubscriptionFilter(Helper.key3, new AttributeList((Attribute) Attribute.javaType2Attribute(values.get(2)))));
    filters.add(new SubscriptionFilter(Helper.key4, new AttributeList((Attribute) Attribute.javaType2Attribute(values.get(3)))));
    
    Subscription subscription = new Subscription(SUBSCRIPTION_ID, HeaderTestProcedure.DOMAIN, filters);
    
    MonitorListener listener = new MonitorListener();
    
    ipTest.monitorRegister(subscription, listener);

    UInteger expectedErrorCode = new UInteger(999);
    TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE, 
        SESSION, SESSION_NAME, false, updateHeaderList, updateList, null, expectedErrorCode,
        Boolean.FALSE);
    
    ipTest.publishUpdates(testPublishUpdate);
    
    // Check the Notify arrival
    // The QoS level is Assured (FIFO) so the Notify messages arrived
    // before the 'publishUpdates' ack.
    
    ArrayList<AttributeList> notifiedKeyValues = listener.getNotifiedKeys();
    
    IdentifierList idList = new IdentifierList();
    idList.add(SUBSCRIPTION_ID);
    
    ipTest.monitorDeregister(idList);
    
    AssertionList assertions = new AssertionList();
    
    String procedureName = "PubSub.checkEntityRequest";
    checkIsContainedInto(expectedKeyValues, notifiedKeyValues, assertions, 
        procedureName, "Expected key has been found: ");
    checkIsContainedInto(notifiedKeyValues, expectedKeyValues, assertions, 
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
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, expectedErrorCode);
    ipTest.publishDeregister(testPublishDeregister);
    return true;
  }

    public void checkIsContainedInto(ArrayList<AttributeList> containedList, ArrayList<AttributeList> containerList, 
            AssertionList assertions, String procedureName, String info) {
    for (int i = 0; i < containedList.size(); i++) {
      AttributeList keyValues = containedList.get(i);
      
      for(int j = 0; j < containerList.size(); j++) {
        for(int k = 0; k < keyValues.size(); k++) {
            Object keyValueA = keyValues.get(k);
            Object keyValueB = containerList.get(j).get(k);
            boolean res = (keyValueA != null) ? keyValueA.equals(keyValueB) : (keyValueA == keyValueB);

            assertions.add(new Assertion(procedureName, info + 
                keyValueA + " == " + keyValueB + " ? -> Index: " + j, res));
        }
      }
    }
  }
  
  static class MonitorListener extends IPTestAdapter
  {
    
    private ArrayList<AttributeList> notifiedKeyValues;
    
    MonitorListener() {
      notifiedKeyValues = new ArrayList<>();
    }

		@Override
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
        Identifier subscriptionId, UpdateHeaderList updateHeaderList,
        TestUpdateList updateList, Map qosProperties)
    {
      for (UpdateHeader updateHeader : updateHeaderList) {
        notifiedKeyValues.add(updateHeader.getKeyValues());
      }
    }
    
    ArrayList<AttributeList> getNotifiedKeys() {
      return notifiedKeyValues;
    }
  }
}
