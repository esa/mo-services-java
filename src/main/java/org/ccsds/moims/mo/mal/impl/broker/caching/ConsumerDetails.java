package org.ccsds.moims.mo.mal.impl.broker.caching;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerMessage;
import org.ccsds.moims.mo.mal.impl.broker.MALSubscriptionKey;

/**
 * A SubscriptionDetails is keyed on subscription Id
 */
class ConsumerDetails
{
  private final String consumerId;
  //private Set<MALSubscriptionKey> required = new TreeSet<MALSubscriptionKey>();
  private final Map<String, SubscriptionDetails> details = new TreeMap<String, SubscriptionDetails>();
  private MALBrokerMessage notifyMessage = null;

  public ConsumerDetails(String consumerId)
  {
    super();
    this.consumerId = consumerId;
  }

  public void report()
  {
    System.out.println("    START Consumer ( " + consumerId + " )");
    System.out.println("    Subscription count: " + String.valueOf(details.size()));
    System.out.println("    END Consumer ( " + consumerId + " )");
  }

  public boolean notActive()
  {
    return details.isEmpty();
  }

  public void addSubscription(Map<MALSubscriptionKey, PublishedEntry> published, Subscription subscription)
  {
    String subId = subscription.getSubscriptionId().getValue();
    SubscriptionDetails sub = details.get(subId);

    if (null == sub)
    {
      sub = new SubscriptionDetails(this, subId);
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
      notifyMessage = new MALBrokerMessage();
    }

    notifyMessage.updates.add(subUpdate);
  }

  public void getNotifyMessage(MessageHeader srcHdr, Identifier transId, List<MALBrokerMessage> lst)
  {
    if (null != notifyMessage)
    {
      // update the details in the header
      notifyMessage.header.setURIto(new URI(consumerId));
      notifyMessage.header.setAuthenticationId(srcHdr.getAuthenticationId());
      notifyMessage.header.setTimestamp(srcHdr.getTimestamp());
      notifyMessage.header.setQoSlevel(srcHdr.getQoSlevel());
      notifyMessage.header.setPriority(srcHdr.getPriority());
      notifyMessage.header.setDomain(srcHdr.getDomain());
      notifyMessage.header.setNetworkZone(srcHdr.getNetworkZone());
      notifyMessage.header.setSession(srcHdr.getSession());
      notifyMessage.header.setSessionName(srcHdr.getSessionName());
      notifyMessage.header.setInteractionType(InteractionType.PUBSUB);
      notifyMessage.header.setInteractionStage(MALPubSubOperation.NOTIFY_STAGE);
      notifyMessage.header.setTransactionId(transId);
      notifyMessage.header.setArea(srcHdr.getArea());
      notifyMessage.header.setService(srcHdr.getService());
      notifyMessage.header.setOperation(srcHdr.getOperation());
      notifyMessage.header.setVersion(srcHdr.getVersion());
      notifyMessage.header.setIsError(srcHdr.getIsError());

      lst.add(notifyMessage);

      notifyMessage = null;
      for (SubscriptionDetails sub : details.values())
      {
        sub.clearNotify();
      }
    }
  }

  public void removeSubscriptions(IdentifierList subscriptions, Map<MALSubscriptionKey, PublishedEntry> published)
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

  public void removeAllSubscriptions(Map<MALSubscriptionKey, PublishedEntry> published)
  {
    for (Iterator<SubscriptionDetails> it = details.values().iterator(); it.hasNext();)
    {
      it.next().removeSubscription(published);
    }

    details.clear();
  //required.clear();
  }

  public void appendSubscriptions(PublishedEntry publishedEntry, MALSubscriptionKey key)
  {
    Set<Map.Entry<String, SubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, SubscriptionDetails> entry : values)
    {
      entry.getValue().appendSubscription(publishedEntry, key);
    }
  }

//  public void appendIds(Set<MALSubscriptionKey> new_set)
//  {
//    new_set.addAll(required);
//  }
//
//  private void updateIds()
//  {
//    required.clear();
//    Set<Map.Entry<String, SubscriptionDetails>> values = details.entrySet();
//    for (Map.Entry<String, SubscriptionDetails> entry : values)
//    {
//      entry.getValue().appendIds(required);
//    }
//  }
}
