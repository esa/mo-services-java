package org.ccsds.moims.mo.mal.impl.broker.caching;

import org.ccsds.moims.mo.mal.impl.broker.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
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
  private final Map<MALSubscriptionKey, PublishedEntry> published = new TreeMap<MALSubscriptionKey, PublishedEntry>();
  private final Map<String, CachingConsumerDetails> details = new TreeMap<String, CachingConsumerDetails>();

  public CachingSubscriptionSource(MessageHeader hdr)
  {
    super(hdr);
  }

  @Override
  public boolean notActive()
  {
    return false;
  }

  @Override
  public void report()
  {
    Set values = details.entrySet();
    Iterator it = values.iterator();
    System.out.println("  START Source ( " + signature + " )");
    System.out.println("  Required: " + String.valueOf(published.size()));
    while (it.hasNext())
    {
      ((CachingConsumerDetails) ((Entry) it.next()).getValue()).report();
    }
    System.out.println("  END Source ( " + signature + " )");
  }

  @Override
  public void addSubscription(MessageHeader srcHdr, String consumer, Subscription subscription, MALBrokerBindingImpl binding)
  {
    CachingConsumerDetails det = getDetails(consumer, binding);
    det.addSubscription(published, subscription);
  }

  @Override
  public void populateNotifyList(MessageHeader srcHdr, List<MALBrokerMessage> lst, UpdateList updateList)
  {
    int length = updateList.size();
    for (int i = 0; i < length; ++i)
    {
      Update update = (Update) updateList.get(i);
      MALSubscriptionKey key = new MALSubscriptionKey(update.getKey());
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

//  public void populateNotifyListx(MALMessageHeader srcHdr, List<MALBrokerMessage> lst, MALUpdateList updateList)
//  {
//    Map<Integer, Map.Entry<CachingSubscriptionDetails, Vector<MALUpdate>>> subsList = new TreeMap<Integer, Map.Entry<CachingSubscriptionDetails, Vector<MALUpdate>>>();
//
//    int length = updateList.size();
//    for (int i = 0; i < length; ++i)
//    {
//      MALUpdate update = (MALUpdate) updateList.get(i);
//      MALSubscriptionKey key = new MALSubscriptionKey(update.getKey());
//      Vector<CachingSubscriptionDetails> clients = published.get(key);
//
//      if (null == clients)
//      {
//        clients = populatePublishedMap(key);
//      }
//
//      for (CachingSubscriptionDetails subscriptionDetails : clients)
//      {
//        if (subscriptionDetails.requiresUpdate(key, update))
//        {
//          Map.Entry<CachingSubscriptionDetails, Vector<MALUpdate>> subsUpdates = subsList.get(subscriptionDetails.hashCode());
//          if (null == subsUpdates)
//          {
//            subsUpdates = new TreeMap.SimpleEntry<CachingSubscriptionDetails, Vector<MALUpdate>>(subscriptionDetails, new Vector<MALUpdate>());
//            subsList.put(subscriptionDetails.hashCode(), subsUpdates);
//          }
//          subsUpdates.getValue().add(update);
//        }
//      }
//    }
//
//    Map<Integer, MALBrokerMessage> localLst = new TreeMap<Integer, MALBrokerMessage>();
//    for (Map.Entry<CachingSubscriptionDetails, Vector<MALUpdate>> entry : subsList.values())
//    {
//      entry.getKey().populateNotify(srcHdr, transactionId, localLst, entry.getValue());
//    }
//
//    // zip through list and insert our details
//    if (!localLst.isEmpty())
//    {
//      lst.addAll(localLst.values());
//    }
//  }
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

//    protected void updateIds()
//    {
//      required.clear();
//
//      java.util.Set<Map.Entry<String, CachingConsumerDetails>> values = details.entrySet();
//      for (Map.Entry<String, CachingConsumerDetails> entry : values)
//      {
//        entry.getValue().appendIds(required);
//      }
//    }
  private PublishedEntry populatePublishedMap(MALSubscriptionKey key)
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
