/*******************************************************************************
 * Copyright or � or Copr. CNES
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
package org.ccsds.moims.mo.malspp.test.suite;

import java.util.HashMap;
import java.util.Hashtable;

import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malspp.test.util.BufferReader;
import org.ccsds.moims.mo.malspp.test.util.TestHelper;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;

public class LocalMALInstance extends org.ccsds.moims.mo.mal.test.suite.LocalMALInstance {
  
  /**
   * APID qualifier for interactions sending TC packets and receiving TC packets.
   * Default value to be set at the transport level (see TestServiceProviderMAL.properties).
   */
  public static final int TC_TC_LOCAL_APID_QUALIFIER = 247;
  
  /**
   * APID for interactions sending TC packets and receiving TC packets.
   * Default value to be set at the transport level (see TestServiceProviderMAL.properties).
   */
  public static final int TC_TC_LOCAL_APID = 1;
  
  /**
   * APID qualifier for interactions sending TC packets and receiving TM packets.
   */
  public static final int TC_TM_LOCAL_APID_QUALIFIER = 347;
  
  /**
   * APID for interactions sending TC packets and receiving TM packets.
   */
  public static final int TC_TM_LOCAL_APID = 3;
  
  /**
   * APID qualifier for interactions sending TM packets and receiving TC packets.
   */
  public static final int TM_TC_LOCAL_APID_QUALIFIER = 447;
  
  /**
   * APID for interactions sending TM packets and receiving TC packets.
   */
  public static final int TM_TC_LOCAL_APID = 5;
  
  /**
   * APID qualifier for interactions sending TM packets and receiving TM packets.
   */
  public static final int TM_TM_LOCAL_APID_QUALIFIER = 547;
  
  /**
   * APID for interactions sending TM packets and receiving TM packets.
   */
  public static final int TM_TM_LOCAL_APID = 7;
  
  public static final String TC_TM_SHARED_BROKER_NAME = "TcTmSharedBroker";
  public static final String TM_TC_SHARED_BROKER_NAME = "TmTcSharedBroker";
  public static final String TM_TM_SHARED_BROKER_NAME = "TmTmSharedBroker";

	public static LocalMALInstance instance() throws MALException {
		return (LocalMALInstance) binstance();
	}
	
  /**
   * Handler used to create locally a publisher that initiate a Publish Register
   * as a TC with a broker returning a Publish Register Error as a TC.
   */
  private PubsubErrorIPTestHandler tcTcPublishErrorHandler;
	
  /**
   * Handler used to create locally a publisher that initiate a Publish Register
   * as a TC with a broker returning a Publish Register Error as a TM.
   */
	private PubsubErrorIPTestHandler tcTmPublishErrorHandler;
	
  /**
   * Handler used to create locally a publisher that initiate a Publish Register
   * as a TM with a broker returning a Publish Register Error as a TC.
   */
	private PubsubErrorIPTestHandler tmTcPublishErrorHandler;
	
  /**
   * Handler used to create locally a publisher that initiate a Publish Register
   * as a TM with a broker returning a Publish Register Error as a TM.
   */
	private PubsubErrorIPTestHandler tmTmPublishErrorHandler;

  /**
   * Consumer used to interact with a broker that generates errors: Register
   * Error, Notify Error. The consumer sends TC packets and the broker sends TC
   * packets.
   */
	private Hashtable<StubKey, IPTestConsumer> tcTcPubsubErrorStubs = 
	  new Hashtable<StubKey, IPTestConsumer>();
	
  /**
   * Consumer used to interact with a broker that generates errors: Register
   * Error, Notify Error. The consumer sends TC packets and the broker sends TM
   * packets.
   */
	private Hashtable<StubKey, IPTestConsumer> tcTmPubsubErrorStubs = 
      new Hashtable<StubKey, IPTestConsumer>();
	
  /**
   * Consumer used to interact with a broker that generates errors: Register
   * Error, Notify Error. The consumer sends TM packets and the broker sends TC
   * packets.
   */
	private Hashtable<StubKey, IPTestConsumer> tmTcPubsubErrorStubs = 
	    new Hashtable<StubKey, IPTestConsumer>();
	
  /**
   * Consumer used to interact with a broker that generates errors: Register
   * Error, Notify Error. The consumer sends TM packets and the broker sends TM
   * packets.
   */
	private Hashtable<StubKey, IPTestConsumer> tmTmPubsubErrorStubs = 
      new Hashtable<StubKey, IPTestConsumer>();
	
	/**
   * Consumer used to interact with an IPTest provider. The consumer sends TC
   * packets and the provider sends TC packets.
   */
  private Hashtable<StubKey, IPTestConsumer> tcTcIpStubs = 
      new Hashtable<StubKey, IPTestConsumer>();
	
	/**
   * Consumer used to interact with an IPTest provider. The consumer sends TC
   * packets and the provider sends TM packets.
   */
	private Hashtable<StubKey, IPTestConsumer> tcTmIpStubs = 
      new Hashtable<StubKey, IPTestConsumer>();
	
  /**
   * Consumer used to interact with an IPTest provider. The consumer sends TM
   * packets and the provider sends TC packets.
   */
	private Hashtable<StubKey, IPTestConsumer> tmTcIpStubs = 
	  new Hashtable<StubKey, IPTestConsumer>();
	
	/**
   * Consumer used to interact with an IPTest provider. The consumer sends TM
   * packets and the provider sends TM packets.
   */
	private Hashtable<StubKey, IPTestConsumer> tmTmIpStubs = 
      new Hashtable<StubKey, IPTestConsumer>();
	
	/**
   * Consumer used to interact with an IPTest provider setting QoS properties. 
   * The consumer sends TC packets and the provider sends TC packets.
   */
  private Hashtable<StubKey, IPTestConsumer> qosTcTcIpStubs = 
      new Hashtable<StubKey, IPTestConsumer>();
  
  /**
   * Consumer used to interact with an IPTest provider setting QoS properties. 
   * The consumer sends TC packets and the provider sends TM packets.
   */
  private Hashtable<StubKey, IPTestConsumer> qosTcTmIpStubs = 
      new Hashtable<StubKey, IPTestConsumer>();
	
	public LocalMALInstance() throws MALException {
		super();
		BufferReader.init();
	}
	
  protected void createBrokers() throws MALException {
    super.createBrokers();

    String protocol = getProtocol();

    MALBroker tcTmSharedBroker = brokerManager.createBroker();
    HashMap<Object, Object> tcTmProps = new HashMap<Object, Object>();
    tcTmProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.TRUE);
    tcTmProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
        TC_TM_LOCAL_APID_QUALIFIER);
    tcTmProps.put(TestHelper.APID_PROPERTY,
        TC_TM_LOCAL_APID);
    MALBrokerBinding tcTmSharedBrokerBinding = brokerManager.createBrokerBinding(
        tcTmSharedBroker, Configuration.SHARED_BROKER_NAME, protocol,
        Configuration.DEFAULT_SHARED_BROKER_AUTHENTICATION_ID,
        new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1), tcTmProps);
    
    MALBroker tmTcSharedBroker = brokerManager.createBroker();
    HashMap<Object, Object> tmTcProps = new HashMap<Object, Object>();
    tmTcProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
    tmTcProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
        TM_TC_LOCAL_APID_QUALIFIER);
    tmTcProps.put(TestHelper.APID_PROPERTY,
        TM_TC_LOCAL_APID);
    MALBrokerBinding tmTcSharedBrokerBinding = brokerManager.createBrokerBinding(
        tmTcSharedBroker, Configuration.SHARED_BROKER_NAME, protocol,
        Configuration.DEFAULT_SHARED_BROKER_AUTHENTICATION_ID,
        new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1), tmTcProps);
    
    MALBroker tmTmSharedBroker = brokerManager.createBroker();
    HashMap<Object, Object> tmTmProps = new HashMap<Object, Object>();
    tmTmProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
    tmTmProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
        TM_TM_LOCAL_APID_QUALIFIER);
    tmTmProps.put(TestHelper.APID_PROPERTY,
        TM_TM_LOCAL_APID);
    MALBrokerBinding tmTmSharedBrokerBinding = brokerManager.createBrokerBinding(
        tmTmSharedBroker, Configuration.SHARED_BROKER_NAME, protocol,
        Configuration.DEFAULT_SHARED_BROKER_AUTHENTICATION_ID,
        new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1), tmTmProps);
    
    FileBasedDirectory.storeURI(TC_TM_SHARED_BROKER_NAME,
        tcTmSharedBrokerBinding.getURI(), tcTmSharedBrokerBinding.getURI());
    FileBasedDirectory.storeURI(TM_TC_SHARED_BROKER_NAME,
        tmTcSharedBrokerBinding.getURI(), tmTcSharedBrokerBinding.getURI());
    FileBasedDirectory.storeURI(TM_TM_SHARED_BROKER_NAME,
        tmTmSharedBrokerBinding.getURI(), tmTmSharedBrokerBinding.getURI());
  }

	public synchronized IPTestConsumer getTcTcPubsubErrorIPTestStub(
	    Blob authenticationId, IdentifierList domain,
	    Identifier networkZone, SessionType sessionType, Identifier sessionName,
	    QoSLevel qosLevel, UInteger priority) throws MALException {
		StubKey key = new StubKey(authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, priority, true);
    IPTestConsumer ipconsumer = (IPTestConsumer) tcTcPubsubErrorStubs.get(key);
    if (ipconsumer == null) {
   		FileBasedDirectory.URIpair uris = FileBasedDirectory
		    .loadURIs(TestServiceProvider.PUBSUB_ERROR_IP_TEST_PROVIDER_NAME);
		  MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
		    uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
		    domain, networkZone, sessionType, sessionName, qosLevel, new HashMap(),
		    priority);
  		IPTestStub stub = new IPTestStub(consumer);
  		ipconsumer = new IPTestConsumer(consumer, stub);
  		tcTcPubsubErrorStubs.put(key, ipconsumer);
		  return ipconsumer;
	  } else {
	  	return ipconsumer;
	  }
	}
	
  public synchronized IPTestConsumer getTcTmPubsubErrorIPTestStub(
      Blob authenticationId, IdentifierList domain, Identifier networkZone,
      SessionType sessionType, Identifier sessionName, QoSLevel qosLevel,
      UInteger priority) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone,
        sessionType, sessionName, qosLevel, priority, true);
    IPTestConsumer ipconsumer = (IPTestConsumer) tcTmPubsubErrorStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris = FileBasedDirectory
          .loadURIs(TestServiceProvider.TM_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME);

      HashMap<Object, Object> tcTmIpConsumerProps = new HashMap<Object, Object>();
      tcTmIpConsumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.TRUE);
      tcTmIpConsumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TC_TM_LOCAL_APID_QUALIFIER);
      tcTmIpConsumerProps.put(TestHelper.APID_PROPERTY,
          TC_TM_LOCAL_APID);

      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          tcTmIpConsumerProps, priority);
      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      tcTmPubsubErrorStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
	
  public synchronized IPTestConsumer getTmTmPubsubErrorIPTestStub(
      Blob authenticationId, IdentifierList domain, Identifier networkZone,
      SessionType sessionType, Identifier sessionName, QoSLevel qosLevel,
      UInteger priority) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone,
        sessionType, sessionName, qosLevel, priority, true);
    IPTestConsumer ipconsumer = (IPTestConsumer) tmTmPubsubErrorStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris = FileBasedDirectory
          .loadURIs(TestServiceProvider.TM_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME);

      HashMap<Object, Object> tmTmIpConsumerProps = new HashMap<Object, Object>();
      tmTmIpConsumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
      tmTmIpConsumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TM_TM_LOCAL_APID_QUALIFIER);
      tmTmIpConsumerProps.put(TestHelper.APID_PROPERTY,
          TM_TM_LOCAL_APID);

      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          tmTmIpConsumerProps, priority);
      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      tmTmPubsubErrorStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
	
  public synchronized IPTestConsumer getTmTcPubsubErrorIPTestStub(
      Blob authenticationId, IdentifierList domain, Identifier networkZone,
      SessionType sessionType, Identifier sessionName, QoSLevel qosLevel,
      UInteger priority) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone,
        sessionType, sessionName, qosLevel, priority, true);
    IPTestConsumer ipconsumer = (IPTestConsumer) tmTcPubsubErrorStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris = FileBasedDirectory
          .loadURIs(TestServiceProvider.PUBSUB_ERROR_IP_TEST_PROVIDER_NAME);

      HashMap<Object, Object> tmTcIpConsumerProps = new HashMap<Object, Object>();
      tmTcIpConsumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
      tmTcIpConsumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TM_TC_LOCAL_APID_QUALIFIER);
      tmTcIpConsumerProps.put(TestHelper.APID_PROPERTY,
          TM_TC_LOCAL_APID);

      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          tmTcIpConsumerProps, priority);
      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      tmTcPubsubErrorStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
  
  public synchronized IPTestConsumer getTcTcIpTestStub(Blob authenticationId,
      IdentifierList domain, Identifier networkZone, SessionType sessionType,
      Identifier sessionName, QoSLevel qosLevel, UInteger priority,
      boolean shared) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, priority, shared);
    IPTestConsumer ipconsumer = (IPTestConsumer) tcTcIpStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris;
      if (shared) {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
      } else {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.TC_IP_TEST_PROVIDER_NAME);
      }

      HashMap<Object, Object> tcTcConsumerProps = new HashMap<Object, Object>();
      tcTcConsumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.TRUE);
      tcTcConsumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TC_TC_LOCAL_APID_QUALIFIER);
      tcTcConsumerProps.put(TestHelper.APID_PROPERTY,
          TC_TC_LOCAL_APID);
      
      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          tcTcConsumerProps, priority);

      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      tcTcIpStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
	
	public synchronized IPTestConsumer getTmTcIpTestStub(Blob authenticationId,
      IdentifierList domain, Identifier networkZone, SessionType sessionType,
      Identifier sessionName, QoSLevel qosLevel, UInteger priority,
      boolean shared) throws MALException {
	  StubKey key = new StubKey(authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, priority, shared);
    IPTestConsumer ipconsumer = (IPTestConsumer) tmTcIpStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris;
      if (shared) {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.TM_TC_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
      } else {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.TC_IP_TEST_PROVIDER_NAME);
      }

      HashMap<Object, Object> tmTcConsumerProps = new HashMap<Object, Object>();
      tmTcConsumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
      tmTcConsumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TM_TC_LOCAL_APID_QUALIFIER);
      tmTcConsumerProps.put(TestHelper.APID_PROPERTY,
          TM_TC_LOCAL_APID);
     
      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          tmTcConsumerProps, priority);

      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      tmTcIpStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
	
  public synchronized IPTestConsumer getTcTmIpTestStub(Blob authenticationId,
      IdentifierList domain, Identifier networkZone, SessionType sessionType,
      Identifier sessionName, QoSLevel qosLevel, UInteger priority,
      boolean shared) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone,
        sessionType, sessionName, qosLevel, priority, shared);
    IPTestConsumer ipconsumer = (IPTestConsumer) tcTmIpStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris;
      if (shared) {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.TC_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
      } else {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.TM_IP_TEST_PROVIDER_NAME);
      }

      HashMap<Object, Object> tcTmConsumerProps = new HashMap<Object, Object>();
      tcTmConsumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.TRUE);
      tcTmConsumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TC_TM_LOCAL_APID_QUALIFIER);
      tcTmConsumerProps.put(TestHelper.APID_PROPERTY,
          TC_TM_LOCAL_APID);
      
      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          tcTmConsumerProps, priority);

      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      tcTmIpStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
  
  public synchronized IPTestConsumer getTmTmIpTestStub(Blob authenticationId,
      IdentifierList domain, Identifier networkZone, SessionType sessionType,
      Identifier sessionName, QoSLevel qosLevel, UInteger priority,
      boolean shared) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone,
        sessionType, sessionName, qosLevel, priority, shared);
    IPTestConsumer ipconsumer = (IPTestConsumer) tmTmIpStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris;
      if (shared) {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.TM_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
      } else {
        uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.TM_IP_TEST_PROVIDER_NAME);
      }

      HashMap<Object, Object> tmTmConsumerProps = new HashMap<Object, Object>();
      tmTmConsumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
      tmTmConsumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TM_TM_LOCAL_APID_QUALIFIER);
      tmTmConsumerProps.put(TestHelper.APID_PROPERTY,
          TM_TM_LOCAL_APID);
      
      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          tmTmConsumerProps, priority);

      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      tmTmIpStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
  
  public synchronized IPTestConsumer getQosTcTcIpTestStub(Blob authenticationId,
      IdentifierList domain, Identifier networkZone, SessionType sessionType,
      Identifier sessionName, QoSLevel qosLevel, UInteger priority) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone,
        sessionType, sessionName, qosLevel, priority, false);
    IPTestConsumer ipconsumer = (IPTestConsumer) qosTcTcIpStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.QOS_TC_IP_TEST_PROVIDER_NAME);
      
      HashMap<Object, Object> consumerProps = new HashMap<Object, Object>();
      consumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.TRUE);
      consumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TC_TC_LOCAL_APID_QUALIFIER);
      consumerProps.put(TestHelper.APID_PROPERTY,
          TC_TC_LOCAL_APID);
      
      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          consumerProps, priority);

      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      qosTcTcIpStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
  
  public synchronized IPTestConsumer getQosTcTmIpTestStub(Blob authenticationId,
      IdentifierList domain, Identifier networkZone, SessionType sessionType,
      Identifier sessionName, QoSLevel qosLevel, UInteger priority) throws MALException {
    StubKey key = new StubKey(authenticationId, domain, networkZone,
        sessionType, sessionName, qosLevel, priority, false);
    IPTestConsumer ipconsumer = (IPTestConsumer) qosTcTmIpStubs.get(key);
    if (ipconsumer == null) {
      FileBasedDirectory.URIpair uris = FileBasedDirectory
            .loadURIs(TestServiceProvider.QOS_TM_IP_TEST_PROVIDER_NAME);
      
      HashMap<Object, Object> consumerProps = new HashMap<Object, Object>();
      consumerProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.TRUE);
      consumerProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TC_TM_LOCAL_APID_QUALIFIER);
      consumerProps.put(TestHelper.APID_PROPERTY,
          TC_TM_LOCAL_APID);
      
      MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
          uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
          domain, networkZone, sessionType, sessionName, qosLevel,
          consumerProps, priority);

      IPTestStub stub = new IPTestStub(consumer);
      ipconsumer = new IPTestConsumer(consumer, stub);
      qosTcTmIpStubs.put(key, ipconsumer);
      return ipconsumer;
    } else {
      return ipconsumer;
    }
  }
  
  public PubsubErrorIPTestHandler getTcTcHandlerForPublishRegister() throws Exception {
    if (null == tcTcPublishErrorHandler) {
      FileBasedDirectory.URIpair errorBrokerUris = FileBasedDirectory
          .loadURIs(TestServiceProvider.ERROR_BROKER_NAME);
      tcTcPublishErrorHandler = new PubsubErrorIPTestHandler();
      MALProviderManager providerMgr = LocalMALInstance.instance()
          .getMalContext().createProviderManager();
      MALProvider pubsubErrorIPTestProvider = providerMgr.createProvider(
          TestServiceProvider.PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
          LocalMALInstance.instance().getProtocol(),
          IPTestHelper.IPTEST_SERVICE,
          TestServiceProvider.IP_TEST_AUTHENTICATION_ID, tcTcPublishErrorHandler,
          new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1),
          null, Boolean.TRUE,
          errorBrokerUris.broker);
    }
    return tcTcPublishErrorHandler;
  }
  
  public PubsubErrorIPTestHandler getTcTmHandlerForPublishRegister() throws Exception {
    if (null == tcTmPublishErrorHandler) {
      FileBasedDirectory.URIpair errorBrokerUris = FileBasedDirectory
          .loadURIs(TestServiceProvider.TM_ERROR_BROKER_NAME);
      tcTmPublishErrorHandler = new PubsubErrorIPTestHandler();
      MALProviderManager providerMgr = LocalMALInstance.instance()
          .getMalContext().createProviderManager();
      
      HashMap<Object, Object> tcTmProps = new HashMap<Object, Object>();
      tcTmProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.TRUE);
      tcTmProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TC_TM_LOCAL_APID_QUALIFIER);
      tcTmProps.put(TestHelper.APID_PROPERTY,
          TC_TM_LOCAL_APID);
      
      MALProvider pubsubErrorIPTestProvider = providerMgr.createProvider(
          TestServiceProvider.PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
          LocalMALInstance.instance().getProtocol(),
          IPTestHelper.IPTEST_SERVICE,
          TestServiceProvider.IP_TEST_AUTHENTICATION_ID, tcTmPublishErrorHandler,
          new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1),
          tcTmProps, Boolean.TRUE,
          errorBrokerUris.broker);
    }
    return tcTmPublishErrorHandler;
  }
  
  public PubsubErrorIPTestHandler getTmTcHandlerForPublishRegister() throws Exception {
    if (null == tmTcPublishErrorHandler) {
      FileBasedDirectory.URIpair errorBrokerUris = FileBasedDirectory
          .loadURIs(TestServiceProvider.ERROR_BROKER_NAME);
      tmTcPublishErrorHandler = new PubsubErrorIPTestHandler();
      MALProviderManager providerMgr = LocalMALInstance.instance()
          .getMalContext().createProviderManager();
      
      HashMap<Object, Object> tmTcProps = new HashMap<Object, Object>();
      tmTcProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
      tmTcProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TM_TC_LOCAL_APID_QUALIFIER);
      tmTcProps.put(TestHelper.APID_PROPERTY,
          TM_TC_LOCAL_APID);
      
      MALProvider pubsubErrorIPTestProvider = providerMgr.createProvider(
          TestServiceProvider.PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
          LocalMALInstance.instance().getProtocol(),
          IPTestHelper.IPTEST_SERVICE,
          TestServiceProvider.IP_TEST_AUTHENTICATION_ID, tmTcPublishErrorHandler,
          new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1),
          tmTcProps, Boolean.TRUE,
          errorBrokerUris.broker);
    }
    return tmTcPublishErrorHandler;
  }
  
  public PubsubErrorIPTestHandler getTmTmHandlerForPublishRegister() throws Exception {
    if (null == tmTmPublishErrorHandler) {
      FileBasedDirectory.URIpair errorBrokerUris = FileBasedDirectory
          .loadURIs(TestServiceProvider.TM_ERROR_BROKER_NAME);
      tmTmPublishErrorHandler = new PubsubErrorIPTestHandler();
      MALProviderManager providerMgr = LocalMALInstance.instance()
          .getMalContext().createProviderManager();
      
      HashMap<Object, Object> tmTmProps = new HashMap<Object, Object>();
      tmTmProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
      tmTmProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
          TM_TM_LOCAL_APID_QUALIFIER);
      tmTmProps.put(TestHelper.APID_PROPERTY,
          TM_TM_LOCAL_APID);
      
      MALProvider pubsubErrorIPTestProvider = providerMgr.createProvider(
          TestServiceProvider.PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
          LocalMALInstance.instance().getProtocol(),
          IPTestHelper.IPTEST_SERVICE,
          TestServiceProvider.IP_TEST_AUTHENTICATION_ID, tmTmPublishErrorHandler,
          new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1),
          tmTmProps, Boolean.TRUE,
          errorBrokerUris.broker);
    }
    return tmTmPublishErrorHandler;
  }
	
	public String getProtocol() {
		return super.getProtocol();
	}
	
	public MALContext getMalContext() {
		return defaultMal;
	}
}
