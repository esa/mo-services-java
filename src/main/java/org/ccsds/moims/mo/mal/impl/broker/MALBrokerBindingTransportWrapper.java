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
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.DummyHandler;
import org.ccsds.moims.mo.mal.impl.EndPointAdapter;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Wrapper class for Transport level broker bindings.
 */
public class MALBrokerBindingTransportWrapper extends MALClose implements MALInternalBrokerBinding
{
  private final MALBrokerBinding transportDelegate;
  private final MALEndPoint endpoint;
  private final Address address;

  MALBrokerBindingTransportWrapper(MALClose parent,
          MALImpl impl,
          MALTransport transport,
          MALService service,
          MALBrokerBinding transportDelegate) throws MALException
  {
    super(parent);

    this.transportDelegate = transportDelegate;
    this.endpoint = transport.createEndPoint(null, service, null);
    this.address = new Address(endpoint, getURI(), getAuthenticationId(), new DummyHandler());
    this.endpoint.setMessageListener(new EndPointAdapter(impl.getReceivingInterface(), this.address));
  }

  @Override
  public void activate() throws MALException
  {
    transportDelegate.activate();
  }

  @Override
  public void close() throws MALException
  {
    transportDelegate.close();
  }

  @Override
  public Blob getAuthenticationId()
  {
    return transportDelegate.getAuthenticationId();
  }

  @Override
  public URI getURI()
  {
    return transportDelegate.getURI();
  }

  @Override
  public boolean isMALLevelBroker()
  {
    return transportDelegate.isMALLevelBroker();
  }

  @Override
  public MALEndPoint getEndpoint()
  {
    return endpoint;
  }
}
