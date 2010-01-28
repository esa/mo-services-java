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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import org.ccsds.moims.mo.mal.impl.broker.BrokerMessage;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionKey;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionSource;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.structures.UpdateType;

/**
 * A CachingSubscriptionSource is keyed on Area, Service and Operation,
 * it contains one to many CachingConsumerDetails.
 */
class CachingSubscriptionSource extends SubscriptionSource
{
  private final Map<SubscriptionKey, PublishedEntry> published = new TreeMap<SubscriptionKey, PublishedEntry>();
  private final Map<String, CachingConsumerDetails> details = new TreeMap<String, CachingConsumerDetails>();

  public CachingSubscriptionSource(MessageHeader hdr)
  {
    super(hdr);
  }

  @Override
  public boolean active()
  {
    return true;
  }

  @Override
  public void report()
  {
    Set values = details.entrySet();
    Iterator it = values.iterator();
    Logging.logMessage("  START Source ( " + signature + " )");
    Logging.logMessage("  Required: " + String.valueOf(published.size()));
    while (it.hasNext())
    {
      ((CachingConsumerDetails) ((Entry) it.next()).getValue()).report();
    }
    Logging.logMessage("  END Source ( " + signature + " )");
  }

  @Override
  public void addSubscription(MessageHeader srcHdr,
          String consumer,
          Subscription subscription,
          MALBrokerBindingImpl binding)
  {
    CachingConsumerDetails det = getDetails(consumer, binding);
    det.addSubscription(published, subscription);
  }

  @Override
  public void populateNotifyList(MessageHeader srcHdr, List<BrokerMessage> lst, UpdateList updateList)
  {
    int length = updateList.size();
    for (int i = 0; i < length; ++i)
    {
      Update update = (Update) updateList.get(i);
      SubscriptionKey key = new SubscriptionKey(update.getKey());
      boolean onlyForAll = update.getUpdateType().equals(UpdateType.UPDATE);

      PublishedEntry publishedEntry = published.get(key);
      if (null == publishedEntry)
      {
        publishedEntry = populatePublishedMap(key);
      }

      Vector<CachingSubscriptionDetails> subsList = null;
      if (onlyForAll)
      {
        subsList = publishedEntry.onAll;
      }
      else
      {
        subsList = publishedEntry.onChange;
      }

      for (CachingSubscriptionDetails subscriptionDetails : subsList)
      {
        subscriptionDetails.populateNotify(update);
      }
    }

    for (CachingConsumerDetails entry : details.values())
    {
      entry.getNotifyMessage(srcHdr, transactionId, lst);
    }
  }

  @Override
  public void removeSubscriptions(String consumer, IdentifierList subscriptions)
  {
    CachingConsumerDetails det = getDetails(consumer, null);
    // when a consumer deregisters we need to remove the deregistered Subscriptions from the published map
    det.removeSubscriptions(subscriptions, published);
    if (det.notActive())
    {
      details.remove(consumer);
    }
  }

  @Override
  public void removeAllSubscriptions(String consumer)
  {
    CachingConsumerDetails det = getDetails(consumer, null);
    // when a copnsumer deregisters we need to remove the deregistered Subscriptions from the published map
    det.removeAllSubscriptions(published);
    if (det.notActive())
    {
      details.remove(consumer);
    }
  }

  private PublishedEntry populatePublishedMap(SubscriptionKey key)
  {
    PublishedEntry publishedEntry = new PublishedEntry();

    Set<Map.Entry<String, CachingConsumerDetails>> values = details.entrySet();

    for (Map.Entry<String, CachingConsumerDetails> entry : values)
    {
      entry.getValue().appendSubscriptions(publishedEntry, key);
    }

    published.put(key, publishedEntry);

    return publishedEntry;
  }

  private CachingConsumerDetails getDetails(String consumer, MALBrokerBindingImpl binding)
  {
    CachingConsumerDetails retVal = (CachingConsumerDetails) details.get(consumer);
    if (null == retVal)
    {
      retVal = new CachingConsumerDetails(consumer, binding);
      details.put(consumer, retVal);
    }
    return retVal;
  }
}
