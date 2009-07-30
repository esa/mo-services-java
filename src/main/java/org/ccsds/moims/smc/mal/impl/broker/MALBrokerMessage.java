package org.ccsds.moims.smc.mal.impl.broker;

import org.ccsds.moims.smc.mal.api.structures.MALMessageHeader;
import org.ccsds.moims.smc.mal.api.structures.MALSubscriptionUpdateList;

public final class MALBrokerMessage
{
  public final MALMessageHeader header = new MALMessageHeader();
  public final MALSubscriptionUpdateList updates = new MALSubscriptionUpdateList();
}
