/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.test.suite;

import java.util.Hashtable;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.transport.MALTestEndPointSendInterceptor;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestStub;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper;
import org.ccsds.moims.mo.malprototype.errortest.consumer.ErrorTestStub;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest2.IPTest2Helper;
import org.ccsds.moims.mo.malprototype.iptest2.consumer.IPTest2Stub;
import org.ccsds.moims.mo.malprototype2.MALPrototype2Helper;
import org.ccsds.moims.mo.testbed.suite.BaseLocalMALInstance;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;

/**
 *
 */
public class LocalMALInstance extends BaseLocalMALInstance
{
  private DataTestStub dtstub = null;
  private ErrorTestStub erstub = null;
  private Hashtable ipstubs = new Hashtable();

  public static LocalMALInstance instance() throws MALException
  {
    return (LocalMALInstance) binstance();
  }

  public LocalMALInstance() throws MALException
  {
    super();
  }

  @Override
  protected String getProtocol()
  {
    return System.getProperty(Configuration.TEST_PROTOCOL);
  }

  protected void initHelpers() throws MALException
  {
    MALPrototypeHelper.init(MALContextFactory.getElementFactoryRegistry());
    IPTestHelper.init(MALContextFactory.getElementFactoryRegistry());
    DataTestHelper.init(MALContextFactory.getElementFactoryRegistry());
    ErrorTestHelper.init(MALContextFactory.getElementFactoryRegistry());
    IPTest2Helper.init(MALContextFactory.getElementFactoryRegistry());
    MALPrototype2Helper.init(MALContextFactory.getElementFactoryRegistry());
    org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.init(MALContextFactory.getElementFactoryRegistry());

    TransportInterceptor.instance().setEndpointSendInterceptor(new MALTestEndPointSendInterceptor());
  }

  protected void createBrokers() throws MALException
  {
    String protocol = getProtocol();
    logMessage("A: " + protocol);

    String transportLevelSharedBroker = System.getProperty(Configuration.TRANSPORT_LEVEL_SHARED_BROKER);
    boolean isTransportLevel = Boolean.parseBoolean(transportLevelSharedBroker);

    MALBroker sharedBroker = null;
    if (!isTransportLevel)
    {
      sharedBroker = brokerManager.createBroker();
    }

    logMessage("B: " + protocol);

    try
    {
      MALBrokerBinding sharedBrokerBinding = brokerManager.createBrokerBinding(
              sharedBroker,
              Configuration.SHARED_BROKER_NAME,
              protocol,
              Configuration.DEFAULT_SHARED_BROKER_AUTHENTICATION_ID,
              new QoSLevel[]
              {
                QoSLevel.ASSURED
              },
              new UInteger(1), defaultProps);

      logMessage("C: " + protocol);
      // DF: now started as soon as created
      //sharedBrokerBinding.startMessageDelivery();
      logMessage("D: " + protocol);

      FileBasedDirectory.storeURI(Configuration.SHARED_BROKER_NAME,
              sharedBrokerBinding.getURI(), sharedBrokerBinding.getURI());
      FileBasedDirectory.storeSharedBrokerAuthenticationId(
              sharedBrokerBinding.getAuthenticationId());

    }
    catch (Throwable error)
    {
      error.printStackTrace();
    }
  }

  public synchronized DataTestStub dataTestStub() throws MALException
  {
    if (null == dtstub)
    {
      FileBasedDirectory.URIpair uris = FileBasedDirectory.loadURIs(DataTestHelper.DATATEST_SERVICE_NAME.getValue());

      MALConsumer consumer = defaultConsumerMgr.createConsumer(
              "dataTestConsumer",
              uris.uri,
              uris.broker,
              DataTestHelper.DATATEST_SERVICE,
              new Blob("".getBytes()),
              new IdentifierList(),
              new Identifier("networkZone"),
              SessionType.LIVE,
              new Identifier("LIVE"),
              QoSLevel.BESTEFFORT,
              new Hashtable(), new UInteger(0));

      dtstub = new DataTestStub(consumer);
    }

    return dtstub;
  }

  public synchronized ErrorTestStub errorTestStub() throws MALException
  {
    if (null == erstub)
    {
      FileBasedDirectory.URIpair uris = FileBasedDirectory.loadURIs(ErrorTestHelper.ERRORTEST_SERVICE_NAME.getValue());

      MALConsumer consumer = defaultConsumerMgr.createConsumer(
              "errorTestConsumer",
              uris.uri,
              uris.broker,
              ErrorTestHelper.ERRORTEST_SERVICE,
              new Blob("".getBytes()),
              new IdentifierList(),
              new Identifier("networkZone"),
              SessionType.LIVE,
              new Identifier("LIVE"),
              QoSLevel.BESTEFFORT,
              new Hashtable(), new UInteger(0));

      erstub = new ErrorTestStub(consumer);
    }

    return erstub;
  }

  public synchronized IPTestConsumer ipTestStub(Blob authenticationId,
          IdentifierList domain, Identifier networkZone, SessionType sessionType,
          Identifier sessionName, QoSLevel qosLevel, UInteger priority, boolean shared) throws MALException
  {
    StubKey key = new StubKey(authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, priority, shared);
    IPTestConsumer ipconsumer = (IPTestConsumer) ipstubs.get(key);
    if (ipconsumer == null)
    {
      ipconsumer = newIPTestStub(null, authenticationId, domain, networkZone,
              sessionType, sessionName, qosLevel, priority, shared);
      ipstubs.put(key, ipconsumer);
    }
    return ipconsumer;
  }

  public synchronized IPTestConsumer newIPTestStub(String consumerName, Blob authenticationId,
          IdentifierList domain, Identifier networkZone, SessionType sessionType,
          Identifier sessionName, QoSLevel qosLevel, UInteger priority, boolean shared) throws MALException
  {
    FileBasedDirectory.URIpair uris;
    if (shared)
    {
      uris = FileBasedDirectory.loadURIs(TestServiceProvider.IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
    }
    else
    {
      uris = FileBasedDirectory.loadURIs(IPTestHelper.IPTEST_SERVICE_NAME.getValue());
    }

    return newIPTestStub(consumerName, uris, authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, priority, shared);
  }

  public synchronized IPTestConsumer newIPTestStub(String consumerName, FileBasedDirectory.URIpair uris, Blob authenticationId,
          IdentifierList domain, Identifier networkZone, SessionType sessionType,
          Identifier sessionName, QoSLevel qosLevel, UInteger priority, boolean shared) throws MALException
  {
    Hashtable qosProperties = new Hashtable();

    if (uris.uri.getValue().startsWith("malamqp"))
    {
      // The protocol 'malamqp' is used
      if (qosLevel.equals(QoSLevel.QUEUED))
      {
        // The specific QoS property 'consumerId' has to be assigned
        qosProperties.put("consumerId", "consumer-" + sessionName + "-" + shared);
      }
    }

    MALConsumer consumer = defaultConsumerMgr.createConsumer(
            consumerName,
            uris.uri,
            uris.broker,
            IPTestHelper.IPTEST_SERVICE,
            authenticationId,
            domain,
            networkZone,
            sessionType,
            sessionName,
            qosLevel,
            qosProperties,
            priority);

    IPTestStub stub = new IPTestStub(consumer);
    return new IPTestConsumer(consumer, stub);
  }

  public synchronized org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestStub newIPTestFromArea2Stub(
          Blob authenticationId,
          IdentifierList domain, Identifier networkZone, SessionType sessionType,
          Identifier sessionName, QoSLevel qosLevel, UInteger priority, boolean shared)
          throws MALException
  {
    Hashtable qosProperties = new Hashtable();

    FileBasedDirectory.URIpair uris;
    if (shared)
    {
      uris = FileBasedDirectory.loadURIs(TestServiceProvider.IP_TEST_PROVIDER_FROM_AREA2_WITH_SHARED_BROKER_NAME);
    }
    else
    {
      uris = FileBasedDirectory.loadURIs(TestServiceProvider.IP_TEST_PROVIDER_FROM_AREA2_NAME);
    }

    if (uris.uri.getValue().startsWith("malamqp"))
    {
      // The protocol 'malamqp' is used
      if (qosLevel.equals(QoSLevel.QUEUED))
      {
        // The specific QoS property 'consumerId' has to be assigned
        qosProperties.put("consumerId", "iptestFromArea2-consumer-" + sessionName);
      }
    }

    MALConsumer consumer = defaultConsumerMgr.createConsumer(
            // DF: the name should be null in order to create a new consumer 
            // each time the method is called
            //"IPTestFromArea2Consumer-" + sessionName + "-" + shared,
            (String) null,
            uris.uri,
            uris.broker, org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.IPTEST_SERVICE,
            authenticationId, domain,
            networkZone, sessionType, sessionName, qosLevel, qosProperties,
            priority);

    org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestStub stub =
            new org.ccsds.moims.mo.malprototype2.iptest.consumer.IPTestStub(consumer);
    return stub;
  }

  public synchronized IPTest2Stub newIPTest2Stub(
          Blob authenticationId,
          IdentifierList domain, Identifier networkZone, SessionType sessionType,
          Identifier sessionName, QoSLevel qosLevel, UInteger priority)
          throws MALException
  {
    Hashtable qosProperties = new Hashtable();

    FileBasedDirectory.URIpair uris =
            FileBasedDirectory.loadURIs(IPTest2Helper.IPTEST2_SERVICE_NAME.getValue());

    if (uris.uri.getValue().startsWith("malamqp"))
    {
      // The protocol 'malamqp' is used
      if (qosLevel.equals(QoSLevel.QUEUED))
      {
        // The specific QoS property 'consumerId' has to be assigned
        qosProperties.put("consumerId", "iptest2-consumer-" + sessionName);
      }
    }

    MALConsumer consumer = defaultConsumerMgr.createConsumer(
            "IPTest2Consumer-" + sessionName,
            uris.uri,
            uris.broker, IPTest2Helper.IPTEST2_SERVICE,
            authenticationId, domain,
            networkZone, sessionType, sessionName, qosLevel, qosProperties,
            priority);

    IPTest2Stub stub = new IPTest2Stub(consumer);
    return stub;
  }

  public static class IPTestConsumer
  {
    private MALConsumer consumer;
    private IPTestStub stub;
    private Map qosProperties;

    public IPTestConsumer(MALConsumer consumer, IPTestStub stub)
    {
      super();
      this.consumer = consumer;
      this.stub = stub;
    }

    public Map getQosProperties() {
      return qosProperties;
    }

    public void setQosProperties(Map qosProperties) {
      this.qosProperties = qosProperties;
    }

    public MALConsumer getConsumer()
    {
      return consumer;
    }

    public IPTestStub getStub()
    {
      return stub;
    }
  }
}
