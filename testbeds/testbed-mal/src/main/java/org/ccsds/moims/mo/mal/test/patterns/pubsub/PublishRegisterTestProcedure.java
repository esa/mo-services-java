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
import org.ccsds.moims.mo.mal.MALHelper;
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

public class PublishRegisterTestProcedure extends LoggingBase
{
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
  
  private AttributeList myKeys = new AttributeList();
  
  public boolean useSharedBroker(String sharedBroker) throws Exception {
    LoggingBase.logMessage("PublishRegisterTestProcedure.useSharedBroker(" +
        sharedBroker + ')');
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
    LoggingBase.logMessage("PublishRegisterTestProcedure.publishRegisterWithTheEntities(" +
        entities + ')');
    publishRegistered = true;
    
    // Add the Key Names
    ArrayList<AttributeList> entityKeyList = EntityRequestTestProcedure.parseEntityKeyList(entities);
    myKeys = entityKeyList.get(0);
    IdentifierList keyNames = new IdentifierList();
    if(myKeys.get(0) != null) {
        keyNames.add(Helper.key1);
    }
    if(myKeys.get(1) != null) {
        keyNames.add(Helper.key2);
    }
    if(myKeys.get(2) != null) {
        keyNames.add(Helper.key3);
    }
    if(myKeys.get(3) != null) {
        keyNames.add(Helper.key4);
    }
    
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishRegister testPublishRegister = 
      new TestPublishRegister(QOS_LEVEL, PRIORITY, 
          HeaderTestProcedure.DOMAIN, 
          HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, 
          keyNames, expectedErrorCode);
    ipTest.publishRegister(testPublishRegister);
    
    return true;
  }
  
  public boolean publishWithEntityAndExpectError(String entityKeyValue, String error) throws Exception {
    LoggingBase.logMessage("PublishRegisterTestProcedure.publishWithEntityAndExpectError(" +
        entityKeyValue + ',' + error + ')');
    listener.clear();
   
    AttributeList parsed = EntityRequestTestProcedure.parseEntityKey(entityKeyValue);

    SubscriptionFilterList filters = new SubscriptionFilterList();
    filters.add(new SubscriptionFilter(Helper.key1, new AttributeList((Attribute) Attribute.javaType2Attribute(parsed.get(0)))));
    filters.add(new SubscriptionFilter(Helper.key2, new AttributeList((Attribute) Attribute.javaType2Attribute(parsed.get(1)))));
    filters.add(new SubscriptionFilter(Helper.key3, new AttributeList((Attribute) Attribute.javaType2Attribute(parsed.get(2)))));
    filters.add(new SubscriptionFilter(Helper.key4, new AttributeList((Attribute) Attribute.javaType2Attribute(parsed.get(3)))));
    
    Subscription subscription = new Subscription(SUBSCRIPTION_ID, HeaderTestProcedure.DOMAIN, filters);
    ipTest.monitorRegister(subscription, listener);
    
    boolean expectError = Boolean.parseBoolean(error);

    AttributeList values = new AttributeList();

    if(myKeys.size() == 4) {
        if(myKeys.get(0) != null || parsed.get(0) != null) {
            values.add(parsed.get(0));
        }
        if(myKeys.get(1) != null || parsed.get(1) != null) {
            values.add(parsed.get(1));
        }
        if(myKeys.get(2) != null || parsed.get(2) != null) {
            values.add(parsed.get(2));
        }
        if(myKeys.get(3) != null || parsed.get(3) != null) {
            values.add(parsed.get(3));
        }
    }
    
    UpdateHeaderList updateHeaders = new UpdateHeaderList();
    updateHeaders.add(new UpdateHeader(new Identifier("source"), HeaderTestProcedure.DOMAIN, values));
    
    TestUpdateList updateList = new TestUpdateList();
    updateList.add(new TestUpdate(new Integer(0)));
    
    UInteger expectedErrorCode;
    SubscriptionFilterList failedEntityKeys;
    if (expectError) {
      expectedErrorCode = MALHelper.UNKNOWN_ERROR_NUMBER;
      failedEntityKeys = filters;
    } else {
      expectedErrorCode = new UInteger(999);
      failedEntityKeys = null;
    }
    TestPublishUpdate testPublishUpdate = new TestPublishUpdate(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN, HeaderTestProcedure.NETWORK_ZONE, 
        SESSION, SESSION_NAME, false, updateHeaders, updateList, failedEntityKeys, expectedErrorCode, (! publishRegistered));
    
    ipTest.publishUpdates(testPublishUpdate);
    
    AssertionList providerAssertions = ipTest.getResult(null).getAssertions();
    AssertionList localAssertions = new AssertionList();
    
    // The notify message has to be received
    UpdateHeaderList notifiedUpdateHeaders = listener.getNotifiedUpdateHeaders();
    TestUpdateList notifiedUpdates = listener.getNotifiedUpdates();
      
    if (! expectError) {
      localAssertions.add(new Assertion(procedureName, 
          "Notified of updates", notifiedUpdates != null));
      if(notifiedUpdates != null)
      {
        localAssertions.add(new Assertion(procedureName, 
          "One notified update : " + notifiedUpdates.size() + " - " + notifiedUpdates.toString(), (notifiedUpdates.size() == 1)));
      }
      if (notifiedUpdates != null && notifiedUpdates.size() == 1) {
        localAssertions.add(new Assertion(procedureName, 
          "Expected key is: " + entityKeyValue, values.equals(notifiedUpdateHeaders.get(0).getKeyValues())));
      }
    }
    
    return AssertionHelper.checkAssertions(localAssertions) &&
      AssertionHelper.checkAssertions(providerAssertions);
  }
  
  public boolean publishDeregister() throws Exception
  {
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
  
  static class MonitorListener extends IPTestAdapter
  {
    private UpdateHeaderList notifiedUpdateHeaders;
    
    private TestUpdateList notifiedUpdates;
    
    MonitorListener() {}
    
    @Override
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
        Identifier subscriptionId, UpdateHeaderList updateHeaderList,
        TestUpdateList updateList, Map qosProperties)
    {
      LoggingBase.logMessage("PublishRegisterTestProcedure.MonitorListener.monitorNotifyReceived: "
      );
      notifiedUpdateHeaders = updateHeaderList;
      notifiedUpdates = updateList;
    }

    public TestUpdateList getNotifiedUpdates() 
    {
      return notifiedUpdates;
    }
    
    public UpdateHeaderList getNotifiedUpdateHeaders()
    {
      return notifiedUpdateHeaders;
    }

    public void clear() {
      notifiedUpdates = null;
      notifiedUpdateHeaders = null;
    }
  }
}
