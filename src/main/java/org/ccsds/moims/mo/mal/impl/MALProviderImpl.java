/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

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
import org.ccsds.moims.mo.mal.impl.broker.MALInternalBrokerBinding;
import org.ccsds.moims.mo.mal.impl.transport.MALTransportSingleton;

/**
 *
 * @author cooper_sf
 */
public class MALProviderImpl extends MALServiceComponentImpl implements MALProvider
{
  private final boolean isPublisher;
  private final Map<Integer, MALPublisher> publishers = new TreeMap<Integer, MALPublisher>();
  private final URI sharedBrokerUri;
  private final MALBrokerBinding localBrokerBinding;
  private final URI localBrokerUri;
  private final MALEndPoint brokerEndpoint;

  public MALProviderImpl(MALProviderManagerImpl parent, MALImpl impl, String localName, String protocol, MALService service, Blob authenticationId, MALInteractionHandler handler, QoSLevel[] expectedQos, int priorityLevelNumber, Hashtable defaultQoSProperties, Boolean isPublisher, URI sharedBrokerUri) throws MALException
  {
    super(parent, impl, localName, protocol, service, authenticationId, expectedQos, priorityLevelNumber, defaultQoSProperties, handler);

    this.isPublisher = isPublisher;
    this.sharedBrokerUri = sharedBrokerUri;

    if (isPublisher())
    {
      this.handler.malInitialize(this);

      if (null == this.sharedBrokerUri)
      {
        this.localBrokerBinding = impl.createBrokerManager().createBrokerBinding(null, localName + "InternalBroker", protocol, service, authenticationId, expectedQos, priorityLevelNumber, defaultQoSProperties);
        this.localBrokerBinding.activate();
        this.localBrokerUri = this.localBrokerBinding.getURI();
        this.brokerEndpoint = ((MALInternalBrokerBinding)localBrokerBinding).getEndpoint();
      }
      else
      {
        this.localBrokerBinding = null;
        this.localBrokerUri = null;
        
        if (MALTransportSingleton.isSameTransport(sharedBrokerUri, transport))
        {
          this.brokerEndpoint = endpoint;
        }
        else
        {
          this.brokerEndpoint = MALTransportSingleton.instance(sharedBrokerUri, impl.getInitialProperties()).createEndPoint(null, service, defaultQoSProperties);
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

  @Override
  public boolean isPublisher()
  {
    return isPublisher;
  }

  @Override
  public synchronized MALPublisher getPublisher(MALPubSubOperation op)
  {
    MALPublisher pub = publishers.get(op.getNumber());

    if (null == pub)
    {
      pub = new MALBrokerPublisher(this, sendHandler, op);
      publishers.put(op.getNumber(), pub);
    }

    return pub;
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
  public void close() throws MALException
  {
    super.close();

    this.handler.malFinalize(this);
  }

  public MALEndPoint getPublishEndpoint()
  {
    return brokerEndpoint;
  }
}
