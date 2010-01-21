package org.ccsds.moims.mo.mal.impl.broker.caching;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.impl.broker.BrokerMessage;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionKey;
import org.ccsds.moims.mo.mal.impl.util.Logging;

/**
 * A CachingSubscriptionDetails is keyed on subscription Id
 */
class CachingConsumerDetails
{
  private final String consumerId;
  private final MALBrokerBindingImpl binding;
  //private Set<SubscriptionKey> required = new TreeSet<SubscriptionKey>();
  private final Map<String, CachingSubscriptionDetails> details = new TreeMap<String, CachingSubscriptionDetails>();
  private BrokerMessage notifyMessage = null;

  public CachingConsumerDetails(String consumerId, MALBrokerBindingImpl binding)
  {
    super();
    this.consumerId = consumerId;
    this.binding = binding;
  }

  public void report()
  {
    Logging.logMessage("    START Consumer ( " + consumerId + " )");
    Logging.logMessage("    Subscription count: " + String.valueOf(details.size()));
    Logging.logMessage("    END Consumer ( " + consumerId + " )");
  }

  public boolean notActive()
  {
    return details.isEmpty();
  }

  public void addSubscription(Map<SubscriptionKey, PublishedEntry> published, Subscription subscription)
  {
    String subId = subscription.getSubscriptionId().getValue();
    CachingSubscriptionDetails sub = details.get(subId);

    if (null == sub)
    {
      sub = new CachingSubscriptionDetails(this, subId);
      details.put(subId, sub);
    }

    sub.removeSubscription(published);
    sub.setIds(published, subscription.getEntities());

  //updateIds();

  //return required;
  }

  public void populateNotifyList(SubscriptionUpdate subUpdate)
  {
    if (null == notifyMessage)
    {
      notifyMessage = new BrokerMessage(binding);
    }

//    notifyMessage.updates.add(subUpdate);
  }

  public void getNotifyMessage(MessageHeader srcHdr, Identifier transId, List<BrokerMessage> lst)
  {
    if (null != notifyMessage)
    {
      // update the details in the header
//      notifyMessage.header.setURIto(new URI(consumerId));
//      notifyMessage.header.setURIfrom(binding.getURI());
//      notifyMessage.header.setAuthenticationId(srcHdr.getAuthenticationId());
//      notifyMessage.header.setTimestamp(srcHdr.getTimestamp());
//      notifyMessage.header.setQoSlevel(srcHdr.getQoSlevel());
//      notifyMessage.header.setPriority(srcHdr.getPriority());
//      notifyMessage.header.setDomain(srcHdr.getDomain());
//      notifyMessage.header.setNetworkZone(srcHdr.getNetworkZone());
//      notifyMessage.header.setSession(srcHdr.getSession());
//      notifyMessage.header.setSessionName(srcHdr.getSessionName());
//      notifyMessage.header.setInteractionType(InteractionType.PUBSUB);
//      notifyMessage.header.setInteractionStage(MALPubSubOperation.NOTIFY_STAGE);
//      notifyMessage.header.setTransactionId(transId);
//      notifyMessage.header.setArea(srcHdr.getArea());
//      notifyMessage.header.setService(srcHdr.getService());
//      notifyMessage.header.setOperation(srcHdr.getOperation());
//      notifyMessage.header.setVersion(srcHdr.getVersion());
//      notifyMessage.header.setIsError(srcHdr.isError());

      lst.add(notifyMessage);

      notifyMessage = null;
      for (CachingSubscriptionDetails sub : details.values())
      {
        sub.clearNotify();
      }
    }
  }

  public void removeSubscriptions(IdentifierList subscriptions, Map<SubscriptionKey, PublishedEntry> published)
  {
    for (int i = 0; i < subscriptions.size(); i++)
    {
      String subId = ((Identifier) subscriptions.get(i)).getValue();

      if ((null != subId) && details.containsKey(subId))
      {
        details.remove(subId).removeSubscription(published);
      }
    }

  //updateIds();
  }

  public void removeAllSubscriptions(Map<SubscriptionKey, PublishedEntry> published)
  {
    for (Iterator<CachingSubscriptionDetails> it = details.values().iterator(); it.hasNext();)
    {
      it.next().removeSubscription(published);
    }

    details.clear();
  //required.clear();
  }

  public void appendSubscriptions(PublishedEntry publishedEntry, SubscriptionKey key)
  {
    Set<Map.Entry<String, CachingSubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, CachingSubscriptionDetails> entry : values)
    {
      entry.getValue().appendSubscription(publishedEntry, key);
    }
  }

//  public void appendIds(Set<SubscriptionKey> new_set)
//  {
//    new_set.addAll(required);
//  }
//
//  private void updateIds()
//  {
//    required.clear();
//    Set<Map.Entry<String, CachingSubscriptionDetails>> values = details.entrySet();
//    for (Map.Entry<String, CachingSubscriptionDetails> entry : values)
//    {
//      entry.getValue().appendIds(required);
//    }
//  }
}
