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
import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.mal.test.patterns.IPTestHandlerImpl;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.IPTestHandlerWithSharedBroker;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malspp.test.util.BufferReader;
import org.ccsds.moims.mo.malspp.test.util.TestHelper;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory.URIpair;

public class TestServiceProvider extends org.ccsds.moims.mo.mal.test.suite.TestServiceProvider {
  
	public static final String ERROR_BROKER_NAME = "ErrorBroker";
	public static final String TM_ERROR_BROKER_NAME = "TMPacketErrorBroker";
	
	public static final String PUBSUB_ERROR_IP_TEST_PROVIDER_NAME = "PubsubErrorIPTestProvider";
	public static final String TM_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME = "TmPubsubErrorIPTestProvider";
	
	public static final String TC_IP_TEST_PROVIDER_NAME = "IPTest";
	public static final String TM_IP_TEST_PROVIDER_NAME = "TmIPTestProvider";
	
	public static final String QOS_TC_IP_TEST_PROVIDER_NAME = "QosTcIPTest";
	public static final String QOS_TM_IP_TEST_PROVIDER_NAME = "QosTmIPTest";
	
	public static final String TM_TC_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME = "TmTcIPTestProviderWithSharedBroker";
	public static final String TC_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME = "TcTmIPTestProviderWithSharedBroker";
	public static final String TM_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME = "TmTmIPTestProviderWithSharedBroker";

  public static final String TC_TC_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME = "TcTcPubsubErrorIPTestProvider";
  public static final String TC_TM_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME = "TcTmPubsubErrorIPTestProvider";
  public static final String TM_TC_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME = "TmTcPubsubErrorIPTestProvider";
  public static final String TM_TM_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME = "TmTmPubsubErrorIPTestProvider";
  
  public static final String DATA_TEST_NO_VARINT_PROVIDER_NAME = "DataTestNoVarint";
  
    public static final String SEGMENTATION_TEST_PROVIDER_NAME = "SegmentationTest";
    public static final String SEGMENTATION_ERROR_TEST_PROVIDER_NAME = "SegmentationErrorTest";
    public static final int SEGMENTATION_ERROR_REMOTE_APID_QUALIFIER = 648;
    public static final int SEGMENTATION_ERROR_REMOTE_APID = 2;
    public static final String SEGMENTATION_COUNTER_SELECT_TEST_PROVIDER_NAME = "SegmentationCounterSelectTest";
    public static final int SEGMENTATION_COUNTER_SELECT_REMOTE_APID_QUALIFIER = 648;
    public static final int SEGMENTATION_COUNTER_SELECT_REMOTE_APID = 4;
	
	/**
	 * APID qualifier for remote providers and shared brokers sending TC packets.
	 * Default value to be set at the transport level (see TestServiceProviderMAL.properties).
	 */
	public static final int TC_REMOTE_APID_QUALIFIER = 248;
	
	/**
	 * APID for remote providers and shared brokers sending TC packets.
   * Default value to be set at the transport level (see TestServiceProviderMAL.properties).
   */
  public static final int TC_REMOTE_APID = 2;
	
  /**
   * APID qualifier for remote providers and shared brokers sending TM packets.
   */
	public static final int TM_REMOTE_APID_QUALIFIER = 348;
	
	/**
   * APID for remote providers and shared brokers sending TM packets.
   */
	public static final int TM_REMOTE_APID = 4;
	
	private String protocol;
	
	private MALBrokerManager brokerManager;

  /**
   * Broker used to generate errors: Register Error, Publish Register Error,
   * Notify Error. These brokers need to be shared.
   * 
   * @param brokerName
   * @param properties
   * @return
   * @throws MALException
   */
  private MALBrokerBinding createSharedErrorBroker(String brokerName,
      Map<Object, Object> properties) throws MALException {
    ErrorBrokerHandler brokerHandler = new ErrorBrokerHandler();
    MALBroker broker = brokerManager.createBroker(brokerHandler);
    MALBrokerBinding sharedBrokerBinding = brokerManager.createBrokerBinding(
        broker, brokerName, protocol,
        Configuration.DEFAULT_SHARED_BROKER_AUTHENTICATION_ID,
        new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1), properties);
    FileBasedDirectory.storeURI(brokerName, sharedBrokerBinding.getURI(),
        sharedBrokerBinding.getURI());
    return sharedBrokerBinding;
  }
  
  /**
   * Provider used with a broker generating errors.
   * 
   * @param providerName
   * @param sharedBrokerUri the URI of the ErrorBroker
   * @param properties
   * @throws MALException
   */
  private void createPubSubErrorIpTestProvider(String providerName,
      URI sharedBrokerUri, Map<Object, Object> properties) throws MALException {
    PubsubErrorIPTestHandler handler = new PubsubErrorIPTestHandler();
    MALProvider provider = defaultProviderMgr.createProvider(
        providerName, protocol,
        IPTestHelper.IPTEST_SERVICE, IP_TEST_AUTHENTICATION_ID,
        handler, new QoSLevel[] { QoSLevel.ASSURED },
        new UInteger(1),
        properties, Boolean.TRUE,
        sharedBrokerUri);
    FileBasedDirectory.storeURI(providerName,
        provider.getURI(),
        provider.getBrokerURI());
  }
  
  /**
   * Provider used with a shared broker. 
   * 
   * @param providerName
   * @param sharedBrokerUri
   * @param properties
   * @throws MALException
   */
  private void createIpTestProviderWithSharedBroker(String providerName,
      URI sharedBrokerUri, Map<Object, Object> properties) throws MALException {
    IPTestHandlerWithSharedBroker handler = new IPTestHandlerWithSharedBroker();
    handler.setIpTestProviderWithSharedBrokerFileName(providerName);
    MALProvider provider = defaultProviderMgr
        .createProvider(providerName, protocol, IPTestHelper.IPTEST_SERVICE,
            IP_TEST_AUTHENTICATION_ID, handler,
            new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1), properties,
            Boolean.TRUE, sharedBrokerUri);
    FileBasedDirectory.storeURI(providerName,
        provider.getURI(),
        provider.getBrokerURI());
  }
  
  /**
   * Provider setting QoS properties.
   * 
   * @param providerName
   * @param sharedBrokerUri the URI of the ErrorBroker
   * @param properties
   * @throws MALException
   */
  private void createQosIpTestProvider(String providerName,
      URI sharedBrokerUri, Map<Object, Object> properties) throws MALException {
    QosPropertiesIPTestHandler handler = new QosPropertiesIPTestHandler();
    MALProvider provider = defaultProviderMgr.createProvider(
        providerName, protocol,
        IPTestHelper.IPTEST_SERVICE, IP_TEST_AUTHENTICATION_ID,
        handler, new QoSLevel[] { QoSLevel.ASSURED },
        new UInteger(1),
        properties, Boolean.TRUE,
        sharedBrokerUri);
    FileBasedDirectory.storeURI(providerName,
        provider.getURI(),
        provider.getBrokerURI());
  }

	protected void createProviders() throws MALException {
	  BufferReader.init();
	  
		super.createProviders();
		
		protocol = getProtocol();
    brokerManager = defaultMal.createBrokerManager();
    
    HashMap<Object, Object> tmProviderProps = new HashMap<Object, Object>();
    tmProviderProps.put(TestHelper.IS_TC_PACKET_PROPERTY, Boolean.FALSE);
    tmProviderProps.put(TestHelper.APID_QUALIFIER_PROPERTY,
        TM_REMOTE_APID_QUALIFIER);
    tmProviderProps.put(TestHelper.APID_PROPERTY, TM_REMOTE_APID);
		
    MALBrokerBinding sharedErrorBrokerBinding = createSharedErrorBroker(
        ERROR_BROKER_NAME, null);
    MALBrokerBinding tmSharedErrorBrokerBinding = createSharedErrorBroker(
        TM_ERROR_BROKER_NAME, tmProviderProps);
		
    createPubSubErrorIpTestProvider(PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
        sharedErrorBrokerBinding.getURI(), null);
    createPubSubErrorIpTestProvider(TM_PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
        tmSharedErrorBrokerBinding.getURI(), tmProviderProps);
    
    createQosIpTestProvider(QOS_TC_IP_TEST_PROVIDER_NAME, null, null);
    createQosIpTestProvider(QOS_TM_IP_TEST_PROVIDER_NAME, null, tmProviderProps);

    IPTestHandlerImpl iphandler = new IPTestHandlerImpl();
    iphandler.setIpTestProviderWithSharedBrokerFileName(TM_IP_TEST_PROVIDER_NAME);
    MALProvider tmPacketIpProvider = defaultProviderMgr.createProvider(
        TM_IP_TEST_PROVIDER_NAME, protocol, IPTestHelper.IPTEST_SERVICE,
        IP_TEST_AUTHENTICATION_ID, iphandler,
        new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1), tmProviderProps,
        Boolean.TRUE, null);
    FileBasedDirectory.storeURI(TM_IP_TEST_PROVIDER_NAME,
        tmPacketIpProvider.getURI(), tmPacketIpProvider.getBrokerURI());
    
    URIpair tcTmSharedBrokerUriPair = FileBasedDirectory
        .loadURIs(LocalMALInstance.TC_TM_SHARED_BROKER_NAME);
    createIpTestProviderWithSharedBroker(
        TC_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME,
        tcTmSharedBrokerUriPair.broker, tmProviderProps);
    
    URIpair tmTcSharedBrokerUriPair =
        FileBasedDirectory.loadURIs(LocalMALInstance.TM_TC_SHARED_BROKER_NAME);
    createIpTestProviderWithSharedBroker(
        TM_TC_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME,
        tmTcSharedBrokerUriPair.broker, null);
    
    URIpair tmTmSharedBrokerUriPair =
        FileBasedDirectory.loadURIs(LocalMALInstance.TM_TM_SHARED_BROKER_NAME);
    createIpTestProviderWithSharedBroker(
        TM_TM_IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME,
        tmTmSharedBrokerUriPair.broker, tmProviderProps);

    MALInteractionHandler dthandler = new DataTestHandlerImpl();
    MALProvider segmentationTestProvider = defaultProviderMgr.createProvider(
            SEGMENTATION_TEST_PROVIDER_NAME,
            protocol,
            DataTestHelper.DATATEST_SERVICE,
            DATA_TEST_AUTHENTICATION_ID,
            dthandler,
            new QoSLevel[]{
              QoSLevel.ASSURED
            },
            new UInteger(1), // number of priority levels
            null,
            Boolean.FALSE, // isPublisher
            null);

    Map<Object, Object> segmentationErrorProps = new HashMap<Object, Object>();
    segmentationErrorProps.put(TestHelper.APID_QUALIFIER_PROPERTY, SEGMENTATION_ERROR_REMOTE_APID_QUALIFIER);
    segmentationErrorProps.put(TestHelper.APID_PROPERTY, SEGMENTATION_ERROR_REMOTE_APID);
    segmentationErrorProps.put(TestHelper.AUTHENTICATION_ID_FLAG, Boolean.FALSE);
    segmentationErrorProps.put(TestHelper.DOMAIN_FLAG, Boolean.FALSE);
    segmentationErrorProps.put(TestHelper.NETWORK_ZONE_FLAG, Boolean.FALSE);
    segmentationErrorProps.put(TestHelper.PRIORITY_FLAG, Boolean.FALSE);
    segmentationErrorProps.put(TestHelper.SESSION_NAME_FLAG, Boolean.FALSE);
    segmentationErrorProps.put(TestHelper.TIMESTAMP_FLAG, Boolean.FALSE);

    MALInteractionHandler dtErrorHandler = new DataTestHandlerImpl();
    MALProvider segmentationErrorTestProvider = defaultProviderMgr.createProvider(
            SEGMENTATION_ERROR_TEST_PROVIDER_NAME,
            protocol,
            DataTestHelper.DATATEST_SERVICE,
            DATA_TEST_AUTHENTICATION_ID,
            dtErrorHandler,
            new QoSLevel[]{
              QoSLevel.ASSURED
            },
            new UInteger(1), // number of priority levels
            segmentationErrorProps,
            Boolean.FALSE, // isPublisher
            null);

    Map<Object, Object> segmentationCounterSelectProps = new HashMap<Object, Object>();
    segmentationCounterSelectProps.put(TestHelper.APID_QUALIFIER_PROPERTY, SEGMENTATION_COUNTER_SELECT_REMOTE_APID_QUALIFIER);
    segmentationCounterSelectProps.put(TestHelper.APID_PROPERTY, SEGMENTATION_COUNTER_SELECT_REMOTE_APID);
    
    MALInteractionHandler segmentationCounterSelectHandler = new IPSegmentationTestHandlerImpl();
    MALProvider segmentationCounterSelectTestProvider = defaultProviderMgr.createProvider(
            SEGMENTATION_COUNTER_SELECT_TEST_PROVIDER_NAME,
            protocol,
            IPTestHelper.IPTEST_SERVICE,
            IP_TEST_AUTHENTICATION_ID,
            segmentationCounterSelectHandler,
            new QoSLevel[]{
              QoSLevel.ASSURED
            },
            new UInteger(1), // number of priority levels
            segmentationCounterSelectProps,
            Boolean.FALSE, // isPublisher
            null);
    
    FileBasedDirectory.storeURI(SEGMENTATION_TEST_PROVIDER_NAME,
            segmentationTestProvider.getURI(), segmentationTestProvider.getBrokerURI());
    
    FileBasedDirectory.storeURI(SEGMENTATION_ERROR_TEST_PROVIDER_NAME,
            segmentationErrorTestProvider.getURI(), segmentationErrorTestProvider.getBrokerURI());
    
    FileBasedDirectory.storeURI(SEGMENTATION_COUNTER_SELECT_TEST_PROVIDER_NAME,
            segmentationCounterSelectTestProvider.getURI(), segmentationCounterSelectTestProvider.getBrokerURI());
    
    MALProvider dataTestProviderNoVarint = defaultProviderMgr.createProvider(
        DATA_TEST_NO_VARINT_PROVIDER_NAME,
        protocol,
        DataTestHelper.DATATEST_SERVICE,
        DATA_TEST_AUTHENTICATION_ID,
        new org.ccsds.moims.mo.mal.test.datatype.DataTestHandlerImpl(),
        new QoSLevel[]
        {
          QoSLevel.ASSURED
        },
        new UInteger(1),
        tmProviderProps,
        Boolean.FALSE,
        null);
    
    FileBasedDirectory.storeURI(DATA_TEST_NO_VARINT_PROVIDER_NAME,
        dataTestProviderNoVarint.getURI(),
        dataTestProviderNoVarint.getBrokerURI());
	}
	
}
