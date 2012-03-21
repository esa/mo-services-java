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

/**
 * A CachingSubscriptionSource is currently broken.
 */
class CachingSubscriptionSource// extends SubscriptionSource
{
//  private final Map<SubscriptionKey, PublishedEntry> published = new TreeMap<SubscriptionKey, PublishedEntry>();
//  private final Map<String, CachingConsumerDetails> details = new TreeMap<String, CachingConsumerDetails>();
//
//  public CachingSubscriptionSource(MessageHeader hdr, MALBrokerBindingImpl binding)
//  {
//    super(hdr);
//  }
//
//  @Override
//  public boolean active()
//  {
//    return true;
//  }
//
//  @Override
//  public void report()
//  {
//    /**
//    Logging.logMessage("  START Source ( " + signature + " )");
//    Logging.logMessage("  Expecting: " + String.valueOf(published.size()));
//    Iterator pit = published.entrySet().iterator();
//    while (pit.hasNext())
//    {
//      Entry e = ((Entry) pit.next());
//      Logging.logMessage("           : " + ((SubscriptionKey) e.getKey()));
//      ((PublishedEntry) e.getValue()).report();
//    }
//    Iterator it = details.entrySet().iterator();
//    while (it.hasNext())
//    {
//      ((CachingConsumerDetails) ((Entry) it.next()).getValue()).report();
//    }
//    Logging.logMessage("  END Source ( " + signature + " )");
//     **/
//  }
//
//  @Override
//  public String getSignature()
//  {
//    return "";
//  }
//
//  @Override
//  public void addSubscription(MessageHeader srcHdr, Subscription subscription)
//  {
//    /**
//    CachingConsumerDetails det = getDetails(consumer, binding);
//    det.addSubscription(srcHdr, published, subscription);
//     **/
//  }
//
//  @Override
//  public void populateNotifyList(MessageHeader srcHdr, List<BrokerMessage> lst, UpdateList updateList)
//  {
//    Logging.logMessage("INFO: Checking CacheSubSource");
//    /**
//    int length = updateList.size();
//    for (int i = 0; i < length; ++i)
//    {
//      Update update = (Update) updateList.get(i);
//      SubscriptionKey key = new SubscriptionKey(update.getKey());
//  
//      PublishedEntry publishedEntry = published.get(key);
//      if (null == publishedEntry)
//      {
//        publishedEntry = populatePublishedMap(key);
//      }
//
//      Collection<CachingSubscriptionDetails> subsList
//              = publishedEntry.getDetailSet(!update.getUpdateType().equals(UpdateType.UPDATE));
//
//      for (CachingSubscriptionDetails subscriptionDetails : subsList)
//      {
//        subscriptionDetails.populateNotify(update);
//      }
//    }
//
//    for (CachingConsumerDetails entry : details.values())
//    {
//      entry.getNotifyMessage(srcHdr, lst);
//    }
//     **/
//  }
//
//  @Override
//  public void removeSubscriptions(IdentifierList subscriptions)
//  {
//    /**
//    CachingConsumerDetails det = getDetails(consumer, null);
//    // when a consumer deregisters we need to remove the deregistered Subscriptions from the published map
//    det.removeSubscriptions(subscriptions, published);
//    if (det.notActive())
//    {
//      details.remove(consumer);
//    }
//     **/
//  }
//
//  @Override
//  public void removeAllSubscriptions()
//  {
//    /**
//    CachingConsumerDetails det = getDetails(consumer, null);
//    // when a copnsumer deregisters we need to remove the deregistered Subscriptions from the published map
//    det.removeAllSubscriptions(published);
//    if (det.notActive())
//    {
//      details.remove(consumer);
//    }
//     **/
//  }
//
//  private PublishedEntry populatePublishedMap(SubscriptionKey key)
//  {
//    PublishedEntry publishedEntry = new PublishedEntry();
//
//    Set<Map.Entry<String, CachingConsumerDetails>> values = details.entrySet();
//
//    for (Map.Entry<String, CachingConsumerDetails> entry : values)
//    {
//      entry.getValue().appendSubscriptions(publishedEntry, key);
//    }
//
//    published.put(key, publishedEntry);
//
//    return publishedEntry;
//  }
//
//  private CachingConsumerDetails getDetails(String consumer, MALBrokerBindingImpl binding)
//  {
//    CachingConsumerDetails retVal = (CachingConsumerDetails) details.get(consumer);
//    if (null == retVal)
//    {
//      retVal = new CachingConsumerDetails(consumer, binding);
//      details.put(consumer, retVal);
//    }
//    return retVal;
//  }
}
