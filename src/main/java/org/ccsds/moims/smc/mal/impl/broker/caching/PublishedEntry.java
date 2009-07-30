package org.ccsds.moims.smc.mal.impl.broker.caching;

import java.util.Vector;

final class PublishedEntry
{
  public final Vector<SubscriptionDetails> onAll = new Vector<SubscriptionDetails>();
  public final Vector<SubscriptionDetails> onChange = new Vector<SubscriptionDetails>();
}
