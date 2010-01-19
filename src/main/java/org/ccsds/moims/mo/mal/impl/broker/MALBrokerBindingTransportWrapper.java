/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 *
 * @author cooper_sf
 */
public class MALBrokerBindingTransportWrapper extends MALClose implements MALInternalBrokerBinding
{
  private final MALBrokerBinding transportDelegate;
  private final MALEndPoint endpoint;

  public MALBrokerBindingTransportWrapper(MALClose parent, MALTransport transport, String localName, MALService service, MALBrokerBinding transportDelegate) throws MALException
  {
    super(parent);

    this.transportDelegate = transportDelegate;
    this.endpoint = transport.createEndPoint(null, service, null);
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
