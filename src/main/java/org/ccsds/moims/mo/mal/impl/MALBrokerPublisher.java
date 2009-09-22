/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALSubscriberEventListener;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.impl.profile.MALProfiler;

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

  @Override
  public void publish(UpdateList updateList, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel publishQos, Hashtable publishQosProps, Integer publishPriority) throws MALException
  {
    MALProfiler.instance.sendMarkMALReception(domain);

    if (parent.isLocalBroker())
    {
      MALMessageDetails details = new MALMessageDetails(parent.getPublishEndpoint(), null, null, operation.getService(), null, domain, networkZone, sessionType, sessionName, publishQos, publishQosProps, publishPriority);

      MALProfiler.instance.sendMALTransferObject(domain, details);
      handler.returnNotify(details, operation, updateList);
    }
    else
    {
      MALMessageDetails details = new MALMessageDetails(parent.getPublishEndpoint(), null, parent.getBrokerURI(), operation.getService(), null, domain, networkZone, sessionType, sessionName, publishQos, publishQosProps, publishPriority);

      MALProfiler.instance.sendMALTransferObject(domain, details);
      handler.publish(details, operation, updateList);
    }
  }

  @Override
  public void addPublishedEntityKeys(EntityKey[] keys) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void asyncDeregister(MALPublishInteractionListener listener, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel remotePublisherQos, Hashtable remotePublisherQosProps, Integer remotePublisherPriority) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void asyncRegister(EntityKeyList entityKeys, MALPublishInteractionListener listener, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel remotePublisherQos, Hashtable remotePublisherQosProps, Integer remotePublisherPriority) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void deregister(DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel remotePublisherQos, Hashtable remotePublisherQosProps, Integer remotePublisherPriority) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isListened(EntityKey entityKey) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void publish(StandardError error, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel remotePublisherQos, Hashtable remotePublisherQosProps, Integer remotePublisherPriority) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void register(EntityKeyList entityKeys, MALPublishInteractionListener listener, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel remotePublisherQos, Hashtable remotePublisherQosProps, Integer remotePublisherPriority) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void removePublishedEntityKeys(EntityKey[] keys) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setSubscriberEventListener(MALSubscriberEventListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
