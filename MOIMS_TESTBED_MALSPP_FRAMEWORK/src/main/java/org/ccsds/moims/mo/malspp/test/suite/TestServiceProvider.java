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
package org.ccsds.moims.mo.malspp.test.suite;

import java.util.Hashtable;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malspp.test.util.BufferReader;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;

public class TestServiceProvider extends org.ccsds.moims.mo.mal.test.suite.TestServiceProvider {

	public static final String ERROR_BROKER_NAME = "ErrorBroker";
	
	public static final String PUBSUB_ERROR_IP_TEST_PROVIDER_NAME = "PubsubErrorIPTestProvider";

	protected void createProviders() throws MALException {
	  BufferReader.init();
	  
		super.createProviders();
		
		String protocol = getProtocol();

		MALBrokerManager brokerManager = defaultMal.createBrokerManager();

		ErrorBrokerHandler errorBroker = new ErrorBrokerHandler();
		MALBroker sharedBroker = brokerManager.createBroker(errorBroker);

		MALBrokerBinding sharedBrokerBinding = brokerManager.createBrokerBinding(
		    sharedBroker, ERROR_BROKER_NAME, protocol,
		    Configuration.DEFAULT_SHARED_BROKER_AUTHENTICATION_ID,
		    new QoSLevel[] { QoSLevel.ASSURED }, new UInteger(1), new Hashtable());

		FileBasedDirectory.storeURI(ERROR_BROKER_NAME,
		    sharedBrokerBinding.getURI(), sharedBrokerBinding.getURI());
		
		PubsubErrorIPTestHandler pubsubErrorIPTestHandler = new PubsubErrorIPTestHandler();
		
		MALProvider pubsubErrorIPTestProvider = defaultProviderMgr.createProvider(
				PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
        protocol,
        IPTestHelper.IPTEST_SERVICE,
        IP_TEST_AUTHENTICATION_ID,
        pubsubErrorIPTestHandler,
        new QoSLevel[]
        {
          QoSLevel.ASSURED
        },
        new UInteger(1), // number of priority levels
        null,
        Boolean.TRUE, // isPublisher
        sharedBrokerBinding.getURI());
     FileBasedDirectory.storeURI(PUBSUB_ERROR_IP_TEST_PROVIDER_NAME,
    		 pubsubErrorIPTestProvider.getURI(), pubsubErrorIPTestProvider.getBrokerURI());
	}
}
