/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageListener;
import org.ccsds.moims.mo.mal.impl.transport.MALTransportSingleton;

/**
 *
 * @author cooper_sf
 */
public class MALProviderImpl extends MALClose implements MALProvider, MALMessageListener
{
  private final MALServiceSend sendHandler;
  private final MALServiceReceive receiveHandler;
  private final String localName;
  private final String protocol;
  private final MALService service;
  private Blob authenticationId;
  private final MALInteractionHandler handler;
  private final QoSLevel[] expectedQos;
  private final Integer priorityLevelNumber;
  private final Hashtable defaultQoSProperties;
  private final boolean isPublisher;
  private final boolean isLocalBroker;
  private final URI sharedBrokerUri;
  private final URI localUri;
  private final URI localBrokerUri;
  private final MALEndPoint endpoint;
  private final MALEndPoint localBrokerEndpoint;

  public MALProviderImpl(MALClose parent, MALServiceSend sendHandler, MALServiceReceive receiveHandler, MALInteractionMap maps, String localName, String protocol, MALService service, Blob authenticationId, MALInteractionHandler handler, QoSLevel[] expectedQos, int priorityLevelNumber, Hashtable defaultQoSProperties, Boolean isPublisher, URI sharedBrokerUri) throws MALException
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
    this.isPublisher = isPublisher;
    this.sharedBrokerUri = sharedBrokerUri;

    if (null != service)
    {
      this.endpoint = MALTransportSingleton.instance(protocol, defaultQoSProperties).createEndPoint(localName, null, defaultQoSProperties);
      this.localUri = this.endpoint.getURI();
      this.endpoint.setMessageListener(this);
    }
    else
    {
      this.endpoint = null;
      this.localUri = null;
    }

    if (isPublisher())
    {
      this.handler.malInitialize(this);

      if (null == this.sharedBrokerUri)
      {
        this.localBrokerEndpoint = MALTransportSingleton.instance(protocol, defaultQoSProperties).createEndPoint(localName, service, defaultQoSProperties);
        this.localBrokerUri = this.localBrokerEndpoint.getURI();
        this.localBrokerEndpoint.setMessageListener(this);
        this.isLocalBroker = true;
      }
      else
      {
        this.localBrokerEndpoint = null;
        this.localBrokerUri = null;
        this.isLocalBroker = false;
      }
    }
    else
    {
      this.localBrokerEndpoint = null;
      this.localBrokerUri = null;
      this.isLocalBroker = false;
    }
  }

  @Override
  public boolean isPublisher()
  {
    return isPublisher;
  }

  @Override
  public MALPublisher getPublisher(MALPubSubOperation op)
  {
    return new MALBrokerPublisher(this, sendHandler, op);
  }

  @Override
  public URI getURI()
  {
    return this.localUri;
  }

  @Override
  public URI getBrokerURI()
  {
    if (isPublisher())
    {
      if (null != sharedBrokerUri)
      {
        return this.sharedBrokerUri;
      }
      else
      {
        return this.localBrokerUri;
      }
    }

    return null;
  }

  @Override
  public void onInternalError(StandardError err)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void onMessage(MALMessage msg)
  {
    receiveHandler.onMessage(msg, defaultQoSProperties, handler);
  }

  @Override
  public void onMessages(MALMessage[] msgList)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void close() throws MALException
  {
    super.close();

    this.handler.malFinalize(this);
  }

  public boolean isLocalBroker()
  {
    return isLocalBroker;
  }

  public MALEndPoint getPublishEndpoint()
  {
    if (null != localBrokerEndpoint)
    {
      return localBrokerEndpoint;
    }

    return endpoint;
  }
}
