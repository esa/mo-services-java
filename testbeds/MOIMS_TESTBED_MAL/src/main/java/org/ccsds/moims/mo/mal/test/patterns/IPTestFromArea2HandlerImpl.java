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
package org.ccsds.moims.mo.mal.test.patterns;

import java.util.Hashtable;
import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.iptest.structures.TestUpdateList;
import org.ccsds.moims.mo.malprototype2.iptest.provider.IPTestInheritanceSkeleton;
import org.ccsds.moims.mo.malprototype2.iptest.provider.Monitor2Publisher;
import org.ccsds.moims.mo.malprototype2.iptest.provider.MonitorPublisher;

public class IPTestFromArea2HandlerImpl extends IPTestInheritanceSkeleton
{

  public void publishDeregister(TestPublishDeregister _TestPublishRegister, MALInteraction interaction)
      throws MALInteractionException, MALException
  {
    MonitorPublisher publisher = createMonitorPublisher(
        _TestPublishRegister.getDomain(),
        _TestPublishRegister.getNetworkZone(),
        _TestPublishRegister.getSession(),
        _TestPublishRegister.getSessionName(),
        _TestPublishRegister.getQos(),
        new Hashtable(),
        _TestPublishRegister.getPriority());
    publisher.deregister();
    
    Monitor2Publisher publisher2 = createMonitor2Publisher(
        _TestPublishRegister.getDomain(),
        _TestPublishRegister.getNetworkZone(),
        _TestPublishRegister.getSession(),
        _TestPublishRegister.getSessionName(),
        _TestPublishRegister.getQos(),
        new Hashtable(),
        _TestPublishRegister.getPriority());
    publisher2.deregister();
  }

  public void publishRegister(TestPublishRegister _TestPublishRegister, MALInteraction interaction)
      throws MALInteractionException, MALException
  {
    MonitorPublisher publisher = createMonitorPublisher(
        _TestPublishRegister.getDomain(),
        _TestPublishRegister.getNetworkZone(),
        _TestPublishRegister.getSession(),
        _TestPublishRegister.getSessionName(),
        _TestPublishRegister.getQos(),
        new Hashtable(),
        _TestPublishRegister.getPriority());
    publisher.register(new PublisherListener());
    
    Monitor2Publisher publisher2 = createMonitor2Publisher(
        _TestPublishRegister.getDomain(),
        _TestPublishRegister.getNetworkZone(),
        _TestPublishRegister.getSession(),
        _TestPublishRegister.getSessionName(),
        _TestPublishRegister.getQos(),
        new Hashtable(),
        _TestPublishRegister.getPriority());
    publisher2.register(new PublisherListener());
  }

  public void publishUpdates(TestPublishUpdate _TestPublishUpdate, MALInteraction interaction)
      throws MALInteractionException, MALException
  {
    MonitorPublisher publisher = createMonitorPublisher(
        _TestPublishUpdate.getDomain(),
        _TestPublishUpdate.getNetworkZone(),
        _TestPublishUpdate.getSession(),
        _TestPublishUpdate.getSessionName(),
        _TestPublishUpdate.getQos(),
        new Hashtable(),
        _TestPublishUpdate.getPriority());
    
    Monitor2Publisher publisher2 = createMonitor2Publisher(
        _TestPublishUpdate.getDomain(),
        _TestPublishUpdate.getNetworkZone(),
        _TestPublishUpdate.getSession(),
        _TestPublishUpdate.getSessionName(),
        _TestPublishUpdate.getQos(),
        new Hashtable(),
        _TestPublishUpdate.getPriority());
    
    UpdateHeaderList updateHeaderList = _TestPublishUpdate.getUpdateHeaders();
    TestUpdateList testUpdateList = _TestPublishUpdate.getUpdates();
    for (UpdateHeader updateHeader : updateHeaderList) {
      updateHeader.setTimestamp(new Time(System.currentTimeMillis()));
      updateHeader.setSourceURI(new URI(""));
    }
    publisher.publish(updateHeaderList, testUpdateList);
    publisher2.publish(updateHeaderList, testUpdateList);
  }

  public void testMultipleNotify(TestPublishUpdate _TestPublishRegister, MALInteraction interaction)
      throws MALException
  {
    
  }
  
  static class PublisherListener implements MALPublishInteractionListener {

    public void publishDeregisterAckReceived(MALMessageHeader arg0, Map arg1)
        throws MALException
    {
      // TODO Auto-generated method stub
      
    }

    public void publishErrorReceived(MALMessageHeader arg0, MALErrorBody arg1,
        Map arg2) throws MALException
    {
      // TODO Auto-generated method stub
      
    }

    public void publishRegisterAckReceived(MALMessageHeader arg0, Map arg1)
        throws MALException
    {
      // TODO Auto-generated method stub
      
    }

    public void publishRegisterErrorReceived(MALMessageHeader arg0,
        MALErrorBody arg1, Map arg2) throws MALException
    {
      // TODO Auto-generated method stub
      
    }
  }
}
