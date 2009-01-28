/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MALPubSubOperation;
import org.ccsds.moims.smc.mal.api.MALService;
import org.ccsds.moims.smc.mal.api.provider.MALInteractionHandler;
import org.ccsds.moims.smc.mal.api.provider.MALProvider;
import org.ccsds.moims.smc.mal.api.provider.MALPublisher;
import org.ccsds.moims.smc.mal.api.structures.MALBlob;
import org.ccsds.moims.smc.mal.api.structures.MALBoolean;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALInteger;
import org.ccsds.moims.smc.mal.api.structures.MALQoSLevel;
import org.ccsds.moims.smc.mal.api.structures.MALString;
import org.ccsds.moims.smc.mal.api.structures.MALURI;
import org.ccsds.moims.smc.mal.api.transport.MALEndPoint;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.api.transport.MALMessageListener;
import org.ccsds.moims.smc.mal.impl.transport.MALTransportSingleton;

/**
 *
 * @author cooper_sf
 */
public class MALProviderImpl extends MALClose implements MALProvider, MALMessageListener
{
  private final MALServiceSend sendHandler;
  private final MALServiceReceive receiveHandler;
  private final MALString localName;
  private final MALString protocol;
  private final MALService service;
  private MALBlob authenticationId;
  private final MALInteractionHandler handler;
  private final MALQoSLevel[] expectedQos;
  private final MALInteger priorityLevelNumber;
  private final Hashtable defaultQoSProperties;
  private final boolean isPublisher;
  private final MALURI sharedBrokerUri;
  private final MALURI localUri;
  private final MALEndPoint endpoint;

  public MALProviderImpl(MALClose parent, MALServiceSend sendHandler, MALServiceReceive receiveHandler, MALServiceMaps maps, MALString localName, MALString protocol, MALService service, MALBlob authenticationId, MALInteractionHandler handler, MALQoSLevel[] expectedQos, MALInteger priorityLevelNumber, Hashtable defaultQoSProperties, MALBoolean isPublisher, MALURI sharedBrokerUri) throws MALException
  {
    super(parent);

    this.sendHandler = sendHandler;
    this.receiveHandler = receiveHandler;
    this.localName = localName;
    this.protocol = protocol;
    this.service = service;
    this.authenticationId = authenticationId;
    this.handler = handler;
    this.expectedQos = expectedQos;
    this.priorityLevelNumber = priorityLevelNumber;
    this.defaultQoSProperties = defaultQoSProperties;
    this.isPublisher = isPublisher.getBooleanValue();
    this.sharedBrokerUri = sharedBrokerUri;

    this.endpoint = MALTransportSingleton.instance(protocol, defaultQoSProperties).createEndPoint(localName, service);
    this.localUri = this.endpoint.getURI();

    this.endpoint.setMessageListener(this);

    if (isPublisher())
    {
      this.handler.malInitialize(this);
    }
  }

  public boolean isPublisher()
  {
    return isPublisher;
  }

  public MALPublisher getPublisher(MALPubSubOperation op)
  {
    return new MALBrokerPublisher(this, sendHandler, op);
  }

  public MALURI getURI()
  {
    return this.localUri;
  }

  public MALURI getBrokerURI()
  {
    if (isPublisher())
    {
      if (null != sharedBrokerUri)
      {
        return this.sharedBrokerUri;
      }
      else
      {
        return this.localUri;
      }
    }

    return null;
  }

  public void onMessage(MALMessage msg, Hashtable qosProperties)
  {
    receiveHandler.onMessage(msg, qosProperties, handler);
  }

  public void onException(MALException exc)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void close() throws MALException
  {
    super.close();

    this.handler.malFinalize(this);
  }

  public MALEndPoint getEndpoint()
  {
    return endpoint;
  }
}
