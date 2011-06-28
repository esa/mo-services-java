/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl.provider;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.ServiceComponentImpl;
import org.ccsds.moims.mo.mal.impl.broker.MALInternalBrokerBinding;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;

/**
 * MALProvider implementation.
 */
class MALProviderImpl extends ServiceComponentImpl implements MALProvider
{
  private final boolean isPublisher;
  private final Map<Integer, MALPublisher> publishers = new TreeMap<Integer, MALPublisher>();
  private final URI sharedBrokerUri;
  private final MALBrokerBinding localBrokerBinding;
  private final URI localBrokerUri;
  private final MALEndPoint brokerEndpoint;

  MALProviderImpl(MALProviderManagerImpl parent,
          MALContextImpl impl,
          String localName,
          String protocol,
          MALService service,
          Blob authenticationId,
          MALInteractionHandler handler,
          QoSLevel[] expectedQos,
          int priorityLevelNumber,
          Hashtable defaultQoSProperties,
          Boolean isPublisher,
          URI sharedBrokerUri) throws MALException
  {
    super(parent,
            impl,
            localName,
            protocol,
            service,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            defaultQoSProperties,
            handler);

    this.isPublisher = isPublisher;
    this.sharedBrokerUri = sharedBrokerUri;

    if (this.isPublisher)
    {
      this.handler.malInitialize(this);

      if (null == this.sharedBrokerUri)
      {
        this.localBrokerBinding = impl.createBrokerManager().createBrokerBinding(null,
                localName + "InternalBroker",
                protocol,
                authenticationId,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties);
        this.localBrokerUri = this.localBrokerBinding.getURI();
        this.brokerEndpoint = ((MALInternalBrokerBinding) localBrokerBinding).getEndpoint();
      }
      else
      {
        this.localBrokerBinding = null;
        this.localBrokerUri = null;
        
        if (TransportSingleton.isSameTransport(sharedBrokerUri, transport))
        {
          this.brokerEndpoint = endpoint;
        }
        else
        {
          this.brokerEndpoint = TransportSingleton.instance(sharedBrokerUri, impl.getInitialProperties()).createEndPoint(localName, defaultQoSProperties);
          this.brokerEndpoint.setMessageListener(this.receiveHandler);
        }
      }
    }
    else
    {
      this.localBrokerBinding = null;
      this.localBrokerUri = null;
      this.brokerEndpoint = null;
    }
  }

  MALProviderImpl(MALProviderManagerImpl parent,
          MALContextImpl impl,
          MALEndPoint endPoint,
          MALService service,
          Blob authenticationId,
          MALInteractionHandler handler,
          QoSLevel[] expectedQos,
          int priorityLevelNumber,
          Hashtable defaultQoSProperties,
          Boolean isPublisher,
          URI sharedBrokerUri) throws MALException
  {
    super(parent,
            impl,
            endPoint,
            service,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            defaultQoSProperties,
            handler);

    this.isPublisher = isPublisher;
    this.sharedBrokerUri = sharedBrokerUri;

    if (this.isPublisher)
    {
      this.handler.malInitialize(this);

      if (null == this.sharedBrokerUri)
      {
        this.localBrokerBinding = impl.createBrokerManager().createBrokerBinding(impl.createBrokerManager().createBroker(),
                endPoint,
                authenticationId,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties);
        this.localBrokerUri = this.localBrokerBinding.getURI();
        this.brokerEndpoint = ((MALInternalBrokerBinding) localBrokerBinding).getEndpoint();
      }
      else
      {
        this.localBrokerBinding = null;
        this.localBrokerUri = null;

        if (TransportSingleton.isSameTransport(sharedBrokerUri, transport))
        {
          this.brokerEndpoint = endpoint;
        }
        else
        {
          this.brokerEndpoint = TransportSingleton.instance(sharedBrokerUri, impl.getInitialProperties()).createEndPoint(localName, defaultQoSProperties);
          this.brokerEndpoint.setMessageListener(this.receiveHandler);
        }
      }
    }
    else
    {
      this.localBrokerBinding = null;
      this.localBrokerUri = null;
      this.brokerEndpoint = null;
    }
  }

  /**
   * Check to see if this Provider is also a publisher.
   * @return True if a publisher.
   */
  @Override
  public boolean isPublisher()
  {
    return isPublisher;
  }

  /**
   * Access the internal MALPublisher interface.
   * @param op The operation that is to be published.
   * @return The internal MALPublisher
   */
  @Override
  public synchronized MALPublisher getPublisher(MALPubSubOperation op)
  {
    MALPublisher pub = publishers.get(op.getNumber());

    if (null == pub)
    {
      pub = new MALPublisherImpl(this, sendHandler, op);
      publishers.put(op.getNumber(), pub);
    }

    return pub;
  }

  /**
   * Returns the URI of the Broker being used.
   * @return The URI of the Broker or null if not a publisher.
   */
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

  /**
   * Returns the authentication identifier of the Broker if its a private broker.
   * @return The authentication identifier of the private broker or null.
   */
  @Override
  public Blob getBrokerAuthenticationId()
  {
    if (isPublisher() && (null == sharedBrokerUri))
    {
      return this.localBrokerBinding.getAuthenticationId();
    }

    return null;
  }

  /**
   * Closes this Provider and the private broker if it has been created.
   * @throws MALException On error.
   */
  @Override
  public void close() throws MALException
  {
    super.close();

    this.handler.malFinalize(this);
  }

  MALEndPoint getPublishEndpoint()
  {
    return brokerEndpoint;
  }
}
