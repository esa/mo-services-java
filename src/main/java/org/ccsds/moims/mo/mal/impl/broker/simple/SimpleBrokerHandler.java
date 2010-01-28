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
package org.ccsds.moims.mo.mal.impl.broker.simple;

import org.ccsds.moims.mo.mal.impl.broker.BaseBrokerHandler;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionSource;
import org.ccsds.moims.mo.mal.structures.MessageHeader;

/**
 * Extends the BaseBrokerHandler for the Simple broker implementation.
 */
public class SimpleBrokerHandler extends BaseBrokerHandler
{
  /** Creates a new instance of MALBrokerHandler */
  public SimpleBrokerHandler()
  {
  }

  @Override
  protected SubscriptionSource createEntry(MessageHeader hdr)
  {
    return new SimpleSubscriptionSource(hdr);
  }
}
