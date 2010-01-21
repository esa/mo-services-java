/*
 * MALBrokerHandler.java
 *
 * Created on 21 August 2006, 15:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker.simple;

import org.ccsds.moims.mo.mal.impl.broker.BaseBrokerHandler;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionSource;
import org.ccsds.moims.mo.mal.structures.MessageHeader;

/**
 *
 * @author cooper_sf
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
