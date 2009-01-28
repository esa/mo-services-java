/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.provider.MALSubscriberEventListener;
import org.ccsds.moims.smc.mal.api.structures.MALInteger;
import org.ccsds.moims.smc.mal.api.structures.MALQoSLevel;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateList;
import org.ccsds.moims.smc.mal.api.MALPubSubOperation;
import org.ccsds.moims.smc.mal.api.provider.MALPublisher;
import org.ccsds.moims.smc.mal.api.structures.MALDomainIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALSessionType;
import org.ccsds.moims.smc.mal.impl.profile.MALProfiler;

/**
 *
 * @author cooper_sf
 */
public class MALBrokerPublisher implements MALPublisher
{
  private final MALProviderImpl parent;
  private final MALServiceSend handler;
  private final MALPubSubOperation operation;

  public MALBrokerPublisher(MALProviderImpl parent, MALServiceSend handler, MALPubSubOperation operation)
  {
    this.parent = parent;
    this.handler = handler;
    this.operation = operation;
  }

  public void publish(MALUpdateList updateList, MALDomainIdentifier domain, MALIdentifier networkZone, MALSessionType sessionType, MALIdentifier sessionName, MALQoSLevel publishQos, Hashtable publishQosProps, MALInteger publishPriority) throws MALException
  {
    MALProfiler.instance.sendMarkMALReception(domain);
    MALMessageDetails details = new MALMessageDetails(parent.getEndpoint(), null, null, operation.getService(), null, domain, networkZone, sessionType, sessionName, publishQos, publishQosProps, publishPriority);

    MALProfiler.instance.sendMALTransferObject(domain, details);
    handler.returnNotify(details, operation, updateList);
  }

  public void publish(MALException exception, MALQoSLevel publishQos, Hashtable publishQosProps, MALInteger publishPriority) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void addPublishedEntityKeys(MALIdentifierList[] keys) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public boolean isListened(MALIdentifierList entityKey) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void removePublishedEntityKeys(MALIdentifierList[] keys) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void setSubscriberEventListener(MALSubscriberEventListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
