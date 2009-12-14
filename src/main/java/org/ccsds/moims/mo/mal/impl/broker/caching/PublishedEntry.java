package org.ccsds.moims.mo.mal.impl.broker.caching;

import java.util.Vector;

final class PublishedEntry
{
  public final Vector<CachingSubscriptionDetails> onAll = new Vector<CachingSubscriptionDetails>();
  public final Vector<CachingSubscriptionDetails> onChange = new Vector<CachingSubscriptionDetails>();
}
