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

public class SubscriptionDomainTestProcedure extends LoggingBase
{
  public static final SessionType SESSION = SessionType.LIVE;
  public static final Identifier SESSION_NAME = new Identifier("LIVE");
  public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
  public static final UInteger PRIORITY = new UInteger(1);

  public static final Identifier SUBSCRIPTION_ID = new Identifier(
      "EntityRequestSubscription");
  public static final Identifier ENTITY_A = new Identifier("A");
  public static final Long ALL_IDS = new Long(0);

  private UpdateHeaderList updateHeaderList;
  
  private TestUpdateList updateList;

  private boolean shared;

  private IPTestStub ipTestForPublish;

  private IdentifierList[] publishDomainIds;

  public boolean initiatePublisherWithDomainsAndSharedBroker(String domains,
      String sharedBroker) throws Exception
  {
    logMessage("SubscriptionDomainTestProcedure.initiatePublisherWithDomainsAndSharedBroker({"
        + domains + "}," + sharedBroker + ")");

    shared = Boolean.parseBoolean(sharedBroker);

    ipTestForPublish = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, QOS_LEVEL,
        PRIORITY, shared).getStub();

    EntityKeyList entityKeyList = new EntityKeyList();
    entityKeyList.add(new EntityKey(ENTITY_A, ALL_IDS, ALL_IDS, ALL_IDS));

    updateHeaderList = new UpdateHeaderList();
    updateHeaderList.add(new UpdateHeader(new Time(System.currentTimeMillis()), new URI(""), UpdateType.CREATION,
        new EntityKey(ENTITY_A, null, null, null)));
    
    updateList = new TestUpdateList();
    updateList.add(new TestUpdate(new Integer(0)));

    publishDomainIds = parseDomains(domains);
    for (int i = 0; i < publishDomainIds.length; i++)
    {
      UInteger expectedErrorCode = new UInteger(999);
      TestPublishRegister testPublishRegister = new TestPublishRegister(
          QOS_LEVEL, PRIORITY, publishDomainIds[i],
          HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false,
          entityKeyList, expectedErrorCode);
      ipTestForPublish.publishRegister(testPublishRegister);
    }
    return true;
  }

  public static IdentifierList[] parseDomains(String s)
  {
    Vector domainList = new Vector();
    StringTokenizer st = new StringTokenizer(s, " ,");
    while (st.hasMoreTokens())
    {
      domainList.add(parseDomain(st.nextToken()));
    }
    IdentifierList[] res = new IdentifierList[domainList.size()];
    domainList.copyInto(res);
    return res;
  }

  public static IdentifierList parseDomain(String s)
  {
    if (s.equals("NULL"))
    {
      return null;
    }
    StringTokenizer st = new StringTokenizer(s, ".");
    IdentifierList id = new IdentifierList();
    while (st.hasMoreElements())
    {
      id.add(new Identifier(st.nextToken()));
    }
    return id;
  }

  public boolean subscribeToDomainAndSubdomainAndExpectedDomains(String domain,
      String subdomain, String expectedDomains) throws Exception
  {
    logMessage("SubscriptionDomainTestProcedure.subscribeToDomainAndSubdomainAndExpectedDomains("
        + domain + "," + subdomain + ",{" + expectedDomains + "})");

    IdentifierList domainId = parseDomain(domain);
    IdentifierList subdomainId = parseDomain(subdomain);
    IdentifierList[] expectedDomainIds = parseDomains(expectedDomains);
    
    logMessage("subdomainId=" + subdomainId);

    EntityKeyList entityKeys = new EntityKeyList();
    entityKeys.add(new EntityKey(ENTITY_A, ALL_IDS, ALL_IDS, ALL_IDS));
    Boolean onlyOnChange = false;
    EntityRequest entityRequest = new EntityRequest(subdomainId, Boolean.FALSE,
        Boolean.FALSE, Boolean.FALSE, onlyOnChange, entityKeys);
    EntityRequestList entityRequests = new EntityRequestList();
    entityRequests.add(entityRequest);
    Subscription subscription = new Subscription(SUBSCRIPTION_ID,
        entityRequests);

    MonitorListener listener = new MonitorListener();

    IPTestStub ipTest = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID, domainId,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, QOS_LEVEL,
        PRIORITY, shared).getStub();

    ipTest.monitorRegister(subscription, listener);

    for (int i = 0; i < publishDomainIds.length; i++)
    {
      UInteger expectedErrorCode = new UInteger(999);
      TestPublishUpdate testPublishUpdate = new TestPublishUpdate(QOS_LEVEL,
          PRIORITY, publishDomainIds[i], HeaderTestProcedure.NETWORK_ZONE,
          SESSION, SESSION_NAME, false, updateHeaderList, updateList, 
          expectedErrorCode, Boolean.FALSE, null);
      ipTestForPublish.publishUpdates(testPublishUpdate);
    }

    synchronized (listener.monitorCond)
    {
      listener.monitorCond.waitFor(Configuration.WAIT_TIME_OUT);
      listener.monitorCond.reset();
    }

    IdentifierList[] notifiedDomains = listener.getNotifiedDomains();

    IdentifierList idList = new IdentifierList();
    idList.add(SUBSCRIPTION_ID);

    ipTest.monitorDeregister(idList);

    AssertionList assertions = new AssertionList();

    String procedureName = "PubSub.checkSubscriptionDomain";
    checkIsContainedInto(expectedDomainIds, notifiedDomains, assertions,
        procedureName, "The expected domain has been received: ");
    checkIsContainedInto(notifiedDomains, expectedDomainIds, assertions,
        procedureName, "The received domain was expected: ");

    return AssertionHelper.checkAssertions(assertions);
  }

  public boolean publishDeregister() throws Exception
  {
    logMessage("SubscriptionDomainTestProcedure.publishDeregister()");
    UInteger expectedErrorCode = new UInteger(999);
    TestPublishDeregister testPublishDeregister = new TestPublishDeregister(
        QOS_LEVEL, PRIORITY, HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, false, null,
        expectedErrorCode);
    for (int i = 0; i < publishDomainIds.length; i++)
    {
      IPTestStub loopIpTestForPublish = LocalMALInstance.instance().ipTestStub(
          HeaderTestProcedure.AUTHENTICATION_ID, publishDomainIds[i],
          HeaderTestProcedure.NETWORK_ZONE, SESSION, SESSION_NAME, QOS_LEVEL,
          PRIORITY, shared).getStub();
      loopIpTestForPublish.publishDeregister(testPublishDeregister);
    }
    return true;
  }

  public void checkIsContainedInto(IdentifierList[] containedList,
      IdentifierList[] containerList, AssertionList assertions,
      String procedureName, String info)
  {
    for (int i = 0; i < containedList.length; i++)
    {
      boolean res = false;
      for (int j = 0; j < containerList.length; j++)
      {
        if (containedList[i].equals(containerList[j]))
        {
          res = true;
          break;
        }
      }
      assertions.add(new Assertion(procedureName, info + containedList[i], res));
    }
  }

  static class MonitorListener extends IPTestAdapter
  {
    private final BooleanCondition monitorCond = new BooleanCondition();

    private Vector notifiedDomains;

    MonitorListener()
    {
      notifiedDomains = new Vector();
    }

    @Override
    public void monitorNotifyReceived(MALMessageHeader msgHeader,
        Identifier subscriptionId, UpdateHeaderList updateHeaderList,
        TestUpdateList updateList, Map qosProperties)
    {
      logMessage("MonitorListener.monitorNotifyReceived(" + msgHeader + ','
          + updateHeaderList + ')');
      notifiedDomains.add(msgHeader.getDomain());
      monitorCond.set();
    }

    IdentifierList[] getNotifiedDomains()
    {
      IdentifierList[] res = new IdentifierList[notifiedDomains.size()];
      notifiedDomains.copyInto(res);
      return res;
    }
  }
}
