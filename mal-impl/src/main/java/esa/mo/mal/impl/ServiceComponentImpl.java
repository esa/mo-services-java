/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl;

import esa.mo.mal.impl.transport.TransportSingleton;
import esa.mo.mal.impl.util.MALClose;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Base class that is used by service providers, both providers and Brokers.
 */
public abstract class ServiceComponentImpl extends MALClose
{
  protected final MessageSend sendHandler;
  protected final MessageReceive receiveHandler;
  protected final MALInteractionHandler handler;
  protected final String localName;
  protected final MALService service;
  protected Blob authenticationId;
  protected final QoSLevel[] expectedQos;
  protected final UInteger priorityLevelNumber;
  protected final Map defaultQoSProperties;
  protected final URI localUri;
  protected final MALTransport transport;
  protected final MALEndpoint endpoint;
  protected final Address msgAddress;

  /**
   * Constructor.
   *
   * @param parent Parent object.
   * @param impl MAL impl.
   * @param localName Local name of this component.
   * @param protocol The protocol to use.
   * @param service The service.
   * @param authenticationId Authentication identifier.
   * @param expectedQosA Expected QoS.
   * @param priorityLevelNumber Number of priority levels.
   * @param defaultQoSProperties Default QOS properties.
   * @param handler Service interaction handler.
   * @throws MALException on error.
   */
  public ServiceComponentImpl(final MALClose parent,
          final MALContextImpl impl,
          final String localName,
          final String protocol,
          final MALService service,
          final Blob authenticationId,
          final QoSLevel[] expectedQosA,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties,
          final MALInteractionHandler handler) throws MALException
  {
    super(parent);

    this.sendHandler = impl.getSendingInterface();
    this.receiveHandler = impl.getReceivingInterface();
    this.handler = handler;
    this.localName = localName;
    this.service = service;
    this.authenticationId = authenticationId;
    if (null != expectedQosA)
    {
      this.expectedQos = java.util.Arrays.copyOf(expectedQosA, expectedQosA.length);
    }
    else
    {
      this.expectedQos = null;
    }
    this.priorityLevelNumber = priorityLevelNumber;
    if (null != defaultQoSProperties)
    {
      this.defaultQoSProperties = defaultQoSProperties;
    }
    else
    {
      this.defaultQoSProperties = null;
    }

    this.transport = TransportSingleton.instance(protocol, impl.getInitialProperties());
    this.endpoint = transport.createEndpoint(localName, defaultQoSProperties);
    this.localUri = this.endpoint.getURI();
    this.msgAddress = new Address(endpoint, endpoint.getURI(), authenticationId, handler);
    this.receiveHandler.registerProviderEndpoint(endpoint.getURI().getValue(), service, this.msgAddress);
    this.endpoint.setMessageListener(this.receiveHandler);
    this.endpoint.startMessageDelivery();
  }

  /**
   * Constructor.
   *
   * @param parent Parent object.
   * @param impl MAL impl.
   * @param endPoint The endpoint to use.
   * @param service The service.
   * @param authenticationId Authentication identifier.
   * @param expectedQosA Expected QoS.
   * @param priorityLevelNumber Number of priority levels.
   * @param defaultQoSProperties Default QOS properties.
   * @param handler Service interaction handler.
   * @throws MALException on error.
   */
  public ServiceComponentImpl(final MALClose parent,
          final MALContextImpl impl,
          final MALEndpoint endPoint,
          final MALService service,
          final Blob authenticationId,
          final QoSLevel[] expectedQosA,
          final UInteger priorityLevelNumber,
          final Map defaultQoSProperties,
          final MALInteractionHandler handler) throws MALException
  {
    super(parent);

    this.sendHandler = impl.getSendingInterface();
    this.receiveHandler = impl.getReceivingInterface();
    this.handler = handler;
    this.localName = endPoint.getLocalName();
    this.service = service;
    this.authenticationId = authenticationId;
    if (null != expectedQosA)
    {
      this.expectedQos = java.util.Arrays.copyOf(expectedQosA, expectedQosA.length);
    }
    else
    {
      this.expectedQos = null;
    }
    this.priorityLevelNumber = priorityLevelNumber;
    if (null != defaultQoSProperties)
    {
      this.defaultQoSProperties = defaultQoSProperties;
    }
    else
    {
      this.defaultQoSProperties = null;
    }

    this.endpoint = endPoint;
    this.transport = TransportSingleton.instance(endpoint.getURI(), impl.getInitialProperties());
    this.localUri = this.endpoint.getURI();
    this.msgAddress = new Address(endpoint, endpoint.getURI(), authenticationId, handler);
    this.receiveHandler.registerProviderEndpoint(endpoint.getURI().getValue(), service, this.msgAddress);
    this.endpoint.setMessageListener(this.receiveHandler);
  }

  /**
   * Returns the URI of this component.
   *
   * @return the URI.
   */
  public URI getURI()
  {
    return this.localUri;
  }

  /**
   * Returns the interaction handler for messages received by this component.
   *
   * @return the interaction handler.
   */
  public MALInteractionHandler getHandler()
  {
    return handler;
  }

  /**
   * Returns the Endpoint for sending messages from this component.
   *
   * @return the Endpoint.
   */
  public MALEndpoint getEndpoint()
  {
    return endpoint;
  }

  /**
   * Returns the authentication identifier used by this component.
   *
   * @return the Authentication Id.
   */
  public Blob getAuthenticationId()
  {
    return authenticationId;
  }

  /**
   * Sets the authentication identifier used by this component.
   *
   * @param newAuthenticationId the new authentication identifier to use.
   * @return the Authentication Id.
   */
  public Blob setAuthenticationId(Blob newAuthenticationId)
  {
    Blob rv = this.authenticationId;
    this.authenticationId = newAuthenticationId;
    
    return rv;
  }

  /**
   * Returns the Address structure used by this component.
   *
   * @return the Address structure.
   */
  public Address getMsgAddress()
  {
    return msgAddress;
  }

  @Override
  protected void thisObjectClose() throws MALException
  {
    super.thisObjectClose();

    this.receiveHandler.deregisterProviderEndpoint(endpoint.getURI().getValue(), service);
    endpoint.stopMessageDelivery();
    endpoint.close();
  }
}
