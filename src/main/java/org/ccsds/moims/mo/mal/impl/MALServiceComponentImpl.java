/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.impl.transport.MALTransportSingleton;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 *
 * @author cooper_sf
 */
public abstract class MALServiceComponentImpl extends MALClose
{
  protected final MALServiceSend sendHandler;
  protected final MALServiceReceive receiveHandler;
  protected final MALInteractionHandler handler;
  protected final String localName;
  protected final String protocol;
  protected final MALService service;
  protected final Blob authenticationId;
  protected final QoSLevel[] expectedQos;
  protected final Integer priorityLevelNumber;
  protected final Hashtable defaultQoSProperties;
  protected final URI localUri;
  protected final MALTransport transport;
  protected final MALEndPoint endpoint;

  public MALServiceComponentImpl(MALClose parent, MALServiceSend sendHandler, MALServiceReceive receiveHandler, MALInteractionMap maps, String localName, String protocol, MALService service, Blob authenticationId, QoSLevel[] expectedQos, int priorityLevelNumber, Hashtable defaultQoSProperties, MALInteractionHandler handler) throws MALException
  {
    super(parent);

    this.sendHandler = sendHandler;
    this.receiveHandler = receiveHandler;
    this.handler = handler;
    this.localName = localName;
    this.protocol = protocol;
    this.service = service;
    this.authenticationId = authenticationId;
    if(null != expectedQos)
    {
      this.expectedQos = java.util.Arrays.copyOf(expectedQos, expectedQos.length);
    }
    else
    {
      this.expectedQos = null;
    }
    this.priorityLevelNumber = priorityLevelNumber;
    if(null != defaultQoSProperties)
    {
      this.defaultQoSProperties = (Hashtable)defaultQoSProperties.clone();
    }
    else
    {
      this.defaultQoSProperties = null;
    }

    if (null != service)
    {
      this.transport = MALTransportSingleton.instance(protocol, defaultQoSProperties);
      this.endpoint = transport.createEndPoint(localName, service, defaultQoSProperties);
      this.localUri = this.endpoint.getURI();
      receiveHandler.addMessageHandler(this.endpoint, this);
      this.endpoint.setMessageListener(receiveHandler);
    }
    else
    {
      this.transport = null;
      this.endpoint = null;
      this.localUri = null;
    }
  }

  public URI getURI()
  {
    return this.localUri;
  }

  public MALInteractionHandler getHandler()
  {
    return handler;
  }

  public MALEndPoint getEndpoint()
  {
    return endpoint;
  }

  @Override
  public void close() throws MALException
  {
    super.close();
  }
}
