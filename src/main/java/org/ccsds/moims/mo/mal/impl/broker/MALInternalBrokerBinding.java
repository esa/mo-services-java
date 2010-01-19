/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;

/**
 *
 * @author cooper_sf
 */
public interface MALInternalBrokerBinding extends MALBrokerBinding
{
  MALEndPoint getEndpoint();
}
