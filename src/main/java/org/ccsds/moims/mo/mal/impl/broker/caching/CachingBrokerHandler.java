/*
 * CachingBrokerHandler.java
 *
 * Created on 21 August 2006, 15:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker.caching;

import org.ccsds.moims.mo.mal.impl.broker.BaseBrokerHandler;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionSource;
import org.ccsds.moims.mo.mal.structures.MessageHeader;

/**
 *
 * @author cooper_sf
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
