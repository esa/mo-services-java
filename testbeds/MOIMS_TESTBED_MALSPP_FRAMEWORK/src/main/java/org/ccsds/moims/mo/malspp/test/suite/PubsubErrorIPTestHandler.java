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
import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.iptest.body.RequestMultiResponse;
import org.ccsds.moims.mo.malprototype.iptest.provider.IPTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.InvokeMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.MonitorPublisher;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.ProgressMultiInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.TestInvokeEmptyBodyInteraction;
import org.ccsds.moims.mo.malprototype.iptest.provider.TestProgressEmptyBodyInteraction;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestUpdateList;

public class PubsubErrorIPTestHandler extends IPTestInheritanceSkeleton {
	
	private Hashtable<Publisherkey, MonitorPublisher> publishers;
	
	public PubsubErrorIPTestHandler() {
		publishers = new Hashtable<PubsubErrorIPTestHandler.Publisherkey, MonitorPublisher>();
	}

	public synchronized MonitorPublisher getMonitorPublisher(TestPublishUpdate testPublishUpdate) throws MALException, MALInteractionException {
		Publisherkey key = new Publisherkey(testPublishUpdate.getSession(), testPublishUpdate.getSessionName(), testPublishUpdate.getQos());
		MonitorPublisher publisher = publishers.get(key);
		if (publisher == null) {
			publisher = createMonitorPublisher(testPublishUpdate.getDomain(), 
					HeaderTestProcedure.NETWORK_ZONE, testPublishUpdate.getSession(),
					testPublishUpdate.getSessionName(), testPublishUpdate.getQos(), new Hashtable(), new UInteger(1));
			publisher.asyncRegister(new EntityKeyList(), new PublishListener());
			publishers.put(key, publisher);
		}
		return publisher;
	}

	public IPTestResult getResult(Element arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub
		return null;
	}

	public void invoke(IPTestDefinition arg0, InvokeInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void invokeMulti(IPTestDefinition arg0, Element arg1,
	    InvokeMultiInteraction arg2) throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void progress(IPTestDefinition arg0, ProgressInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void progressMulti(IPTestDefinition arg0, Element arg1,
	    ProgressMultiInteraction arg2) throws MALInteractionException,
	    MALException {
		// TODO Auto-generated method stub

	}

	public void publishDeregister(TestPublishDeregister arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void publishRegister(TestPublishRegister arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void publishUpdates(TestPublishUpdate testPublishUpdate,
	    MALInteraction interaction) throws MALInteractionException, MALException {
		getMonitorPublisher(testPublishUpdate).publish(new UpdateHeaderList(), new TestUpdateList());
	}

	public String request(IPTestDefinition arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub
		return null;
	}

	public RequestMultiResponse requestMulti(IPTestDefinition arg0, Element arg1,
	    MALInteraction arg2) throws MALInteractionException, MALException {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(IPTestDefinition arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void sendMulti(IPTestDefinition arg0, Element arg1, MALInteraction arg2)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void submitMulti(IPTestDefinition arg0, Element arg1,
	    MALInteraction arg2) throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void testMultipleNotify(TestPublishUpdate arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void testSubmit(IPTestDefinition arg0, MALInteraction arg1)
	    throws MALInteractionException, MALException {
		// TODO Auto-generated method stub

	}

	public void testRequestEmptyBody(IPTestDefinition _IPTestDefinition0, MALInteraction interaction) throws MALInteractionException, MALException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void testInvokeEmptyBody(IPTestDefinition _IPTestDefinition0, TestInvokeEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void testProgressEmptyBody(IPTestDefinition _IPTestDefinition0, TestProgressEmptyBodyInteraction interaction) throws MALInteractionException, MALException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	static class PublishListener implements MALPublishInteractionListener {

		public void publishDeregisterAckReceived(MALMessageHeader arg0, Map arg1)
        throws MALException {
	    // TODO Auto-generated method stub
	    
    }

		public void publishErrorReceived(MALMessageHeader arg0, MALErrorBody arg1,
        Map arg2) throws MALException {
	    // TODO Auto-generated method stub
	    
    }

		public void publishRegisterAckReceived(MALMessageHeader arg0, Map arg1)
        throws MALException {
	    // TODO Auto-generated method stub
	    
    }

		public void publishRegisterErrorReceived(MALMessageHeader arg0,
        MALErrorBody arg1, Map arg2) throws MALException {
	    // TODO Auto-generated method stub
	    
    }
		
	}
	
	static class Publisherkey {
		private SessionType session;
		private Identifier sessionName;
		private QoSLevel qos;
		
		public Publisherkey(SessionType session, Identifier sessionName,
        QoSLevel qos) {
	    super();
	    this.session = session;
	    this.sessionName = sessionName;
	    this.qos = qos;
    }

		@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((qos == null) ? 0 : qos.hashCode());
	    result = prime * result + ((session == null) ? 0 : session.hashCode());
	    result = prime * result
	        + ((sessionName == null) ? 0 : sessionName.hashCode());
	    return result;
    }

		@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    Publisherkey other = (Publisherkey) obj;
	    if (qos == null) {
		    if (other.qos != null)
			    return false;
	    } else if (!qos.equals(other.qos))
		    return false;
	    if (session == null) {
		    if (other.session != null)
			    return false;
	    } else if (!session.equals(other.session))
		    return false;
	    if (sessionName == null) {
		    if (other.sessionName != null)
			    return false;
	    } else if (!sessionName.equals(other.sessionName))
		    return false;
	    return true;
    }
	}
}
