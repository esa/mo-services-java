package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdateList;

public final class MALBrokerMessage
{
  public final MessageHeader header = new MessageHeader();
  public final SubscriptionUpdateList updates = new SubscriptionUpdateList();
}
