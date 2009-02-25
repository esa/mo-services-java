/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MALInvokeOperation;
import org.ccsds.moims.smc.mal.api.MALProgressOperation;
import org.ccsds.moims.smc.mal.api.MALPubSubOperation;
import org.ccsds.moims.smc.mal.api.MALRequestOperation;
import org.ccsds.moims.smc.mal.api.MALSendOperation;
import org.ccsds.moims.smc.mal.api.MALService;
import org.ccsds.moims.smc.mal.api.MALSubmitOperation;
import org.ccsds.moims.smc.mal.api.consumer.MALConsumer;
import org.ccsds.moims.smc.mal.api.consumer.MALInteractionListener;
import org.ccsds.moims.smc.mal.api.structures.MALBlob;
import org.ccsds.moims.smc.mal.api.structures.MALDomainIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALElement;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALInteger;
import org.ccsds.moims.smc.mal.api.structures.MALQoSLevel;
import org.ccsds.moims.smc.mal.api.structures.MALSessionType;
import org.ccsds.moims.smc.mal.api.structures.MALSubscription;
import org.ccsds.moims.smc.mal.api.structures.MALURI;
import org.ccsds.moims.smc.mal.api.transport.MALEndPoint;
import org.ccsds.moims.smc.mal.impl.transport.MALTransportSingleton;

/**
 *
 * @author cooper_sf
 */
public class MALConsumerImpl extends MALClose implements MALConsumer
{
  private final MALImpl impl;
  private final MALMessageDetails details;


  public MALConsumerImpl(MALImpl impl, MALConsumerManagerImpl parent, MALURI uriTo, MALURI brokerUri, MALService service, MALBlob authenticationId, MALDomainIdentifier domain, MALIdentifier networkZone, MALSessionType sessionType, MALIdentifier sessionName, MALQoSLevel qosLevel, Hashtable qosProps, MALInteger priority) throws MALException
  {
    super(parent);
    this.impl = impl;
    this.details = new MALMessageDetails(MALTransportSingleton.instance(uriTo, null).createEndPoint(null, null), uriTo, brokerUri, service, authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, qosProps, priority);
  }

  public void send(MALSendOperation op, MALElement requestBody) throws MALException
  {
    impl.getSendingInterface().send(details, op, requestBody);
  }

  public void submit(MALSubmitOperation op, MALElement requestBody) throws MALException
  {
    impl.getSendingInterface().submit(details, op, requestBody);
  }

  public MALElement request(MALRequestOperation op, MALElement requestBody) throws MALException
  {
    return impl.getSendingInterface().request(details, op, requestBody);
  }

  public MALElement invoke(MALInvokeOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
  {
    return impl.getSendingInterface().invoke(details, op, requestBody, listener);
  }

  public MALElement progress(MALProgressOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
  {
    return impl.getSendingInterface().progress(details, op, requestBody, listener);
  }

  public void register(MALPubSubOperation op, MALSubscription subscription, MALInteractionListener listener) throws MALException
  {
    impl.getSendingInterface().register(details, op, subscription, listener);
  }

  public void deregister(MALPubSubOperation op, MALIdentifierList unsubscription) throws MALException
  {
    impl.getSendingInterface().deregister(details, op, unsubscription);
  }

  public void asyncSubmit(MALSubmitOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
  {
    impl.getSendingInterface().submitAsync(details, op, requestBody, listener);
  }

  public void asyncRequest(MALRequestOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void asyncInvoke(MALInvokeOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void asyncProgress(MALProgressOperation op, MALElement requestBody, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void asyncRegister(MALPubSubOperation op, MALSubscription subscription, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void asyncDeregister(MALPubSubOperation op, MALIdentifierList unsubscription, MALInteractionListener listener) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
