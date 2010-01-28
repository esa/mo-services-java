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
package org.ccsds.moims.mo.mal.impl.broker.caching;

import org.ccsds.moims.mo.mal.impl.broker.BaseBrokerHandler;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionSource;
import org.ccsds.moims.mo.mal.structures.MessageHeader;

/**
 * The caching broker maintains lists of who desired what published updates, so when an update arrives the costly
 *  searching and key matching is already performed.
 */
public class CachingBrokerHandler extends BaseBrokerHandler
{
  /** Creates a new instance of CachingBrokerHandler */
  public CachingBrokerHandler()
  {
  }

  @Override
  protected SubscriptionSource createEntry(MessageHeader hdr)
  {
    return new CachingSubscriptionSource(hdr);
  }
}
