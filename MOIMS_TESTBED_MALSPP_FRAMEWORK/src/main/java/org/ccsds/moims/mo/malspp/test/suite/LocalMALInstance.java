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

import java.util.HashMap;
import java.util.Hashtable;

import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malspp.test.util.BufferReader;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;

public class LocalMALInstance extends org.ccsds.moims.mo.mal.test.suite.LocalMALInstance {

	public static LocalMALInstance instance() throws MALException {
		return (LocalMALInstance) binstance();
	}

	private Hashtable<StubKey, IPTestConsumer> ipstubs = 
	  new Hashtable<StubKey, IPTestConsumer>();
	
	public LocalMALInstance() throws MALException {
		super();
		BufferReader.init();
	}

	public synchronized IPTestConsumer getPubsubErrorIPTestStub(
	    Blob authenticationId, IdentifierList domain,
	    Identifier networkZone, SessionType sessionType, Identifier sessionName,
	    QoSLevel qosLevel, UInteger priority) throws MALException {
		StubKey key = new StubKey(authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, priority, true);
    IPTestConsumer ipconsumer = (IPTestConsumer) ipstubs.get(key);
    if (ipconsumer == null) {
   		FileBasedDirectory.URIpair uris = FileBasedDirectory
		    .loadURIs(TestServiceProvider.PUBSUB_ERROR_IP_TEST_PROVIDER_NAME);
		  MALConsumer consumer = defaultConsumerMgr.createConsumer((String) null,
		    uris.uri, uris.broker, IPTestHelper.IPTEST_SERVICE, authenticationId,
		    domain, networkZone, sessionType, sessionName, qosLevel, new HashMap(),
		    priority);
  		IPTestStub stub = new IPTestStub(consumer);
  		ipconsumer = new IPTestConsumer(consumer, stub);
  		ipstubs.put(key, ipconsumer);
		  return ipconsumer;
	  } else {
	  	return ipconsumer;
	  }
	}
	
	public String getProtocol() {
		return super.getProtocol();
	}
	
	public MALContext getMalContext() {
		return defaultMal;
	}
}
