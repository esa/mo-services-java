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
import java.util.Set;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.impl.broker.BrokerMessage;
import org.ccsds.moims.mo.mal.impl.NotifyMessage;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionKey;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * A CachingSubscriptionDetails is keyed on consumer Id
 *  It maintains a map of subscriptions for this consumer.
 */
class CachingConsumerDetails
{
  private final String consumerId;
  private final MALBrokerBindingImpl binding;
  private final Map<String, CachingSubscriptionDetails> details = new TreeMap<String, CachingSubscriptionDetails>();
  private BrokerMessage brokerMessage = null;
  private NotifyMessage notifyMessage = null;
  private Identifier transactionId;
  private QoSLevel qos;
  private Integer priority;

  public CachingConsumerDetails(String consumerId, MALBrokerBindingImpl binding)
  {
    super();
    this.consumerId = consumerId;
    this.binding = binding;
  }

  public String getConsumerId()
  {
    return consumerId;
  }

  public void report()
  {
    Logging.logMessage("    START Consumer ( " + consumerId + " )");
    Logging.logMessage("    Required: ");
    Set<Map.Entry<String, CachingSubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, CachingSubscriptionDetails> entry : values)
    {
      entry.getValue().report();
    }
    Logging.logMessage("    END Consumer ( " + consumerId + " )");
  }

  public boolean notActive()
  {
    return details.isEmpty();
  }

  public void addSubscription(MALMessageHeader srcHdr,
          Map<SubscriptionKey,
          PublishedEntry> published,
          Subscription subscription)
  {
    String subId = subscription.getSubscriptionId().getValue();
    CachingSubscriptionDetails sub = details.get(subId);

    if (null == sub)
    {
      if (details.isEmpty())
      {
//        this.transactionId = srcHdr.getTransactionId();
//        this.qos = srcHdr.getQoSlevel();
//        this.priority = srcHdr.getPriority();
      }
      sub = new CachingSubscriptionDetails(this, subId);
      details.put(subId, sub);
    }

    sub.removeSubscription(published);
    sub.setIds(published, subscription.getEntities());
  }

  public void populateNotifyList()//SubscriptionUpdate subUpdate)
  {
    Logging.logMessage("INFO: Checking CacheConDetails");

    if (null == brokerMessage)
    {
      brokerMessage = new BrokerMessage(binding);
      notifyMessage = new NotifyMessage();
      brokerMessage.msgs.add(notifyMessage);
    }

//    notifyMessage.updates.add(subUpdate);
  }

  public void getNotifyMessage(MALMessageHeader srcHdr, List<BrokerMessage> lst)
  {
    if (null != brokerMessage)
    {
      // update the details in the header
//      notifyMessage.header.setURIto(new URI(consumerId));
//      notifyMessage.header.setURIfrom(binding.getURI());
//      notifyMessage.header.setAuthenticationId(binding.getAuthenticationId());
//      notifyMessage.header.setTimestamp(srcHdr.getTimestamp());
//      notifyMessage.header.setQoSlevel(qos);
//      notifyMessage.header.setPriority(priority);
//      notifyMessage.header.setDomain(srcHdr.getDomain());
//      notifyMessage.header.setNetworkZone(srcHdr.getNetworkZone());
//      notifyMessage.header.setSession(srcHdr.getSession());
//      notifyMessage.header.setSessionName(srcHdr.getSessionName());
//      notifyMessage.header.setInteractionType(InteractionType.PUBSUB);
//      notifyMessage.header.setInteractionStage(MALPubSubOperation.NOTIFY_STAGE);
//      notifyMessage.header.setTransactionId(transactionId);
//      notifyMessage.header.setArea(srcHdr.getArea());
//      notifyMessage.header.setService(srcHdr.getService());
//      notifyMessage.header.setOperation(srcHdr.getOperation());
//      notifyMessage.header.setVersion(srcHdr.getVersion());
//      notifyMessage.header.setError(srcHdr.isError());

      lst.add(brokerMessage);

      brokerMessage = null;
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
  }

  public void removeAllSubscriptions(Map<SubscriptionKey, PublishedEntry> published)
  {
    for (Iterator<CachingSubscriptionDetails> it = details.values().iterator(); it.hasNext(); )
    {
      it.next().removeSubscription(published);
    }

    details.clear();
  }

  public void appendSubscriptions(PublishedEntry publishedEntry, SubscriptionKey key)
  {
    Set<Map.Entry<String, CachingSubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, CachingSubscriptionDetails> entry : values)
    {
      entry.getValue().appendSubscription(publishedEntry, key);
    }
  }
}
