package org.ccsds.moims.mo.mal.impl.broker.simple;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.broker.BrokerMessage;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionKey;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * A SimpleSubscriptionDetails is keyed on subscription Id
 */
class SimpleConsumerDetails
{
  private final String consumerId;
  private final MALBrokerBindingImpl binding;
  private Set<SubscriptionKey> required = new TreeSet<SubscriptionKey>();
  private final Map<String, SimpleSubscriptionDetails> details = new TreeMap<String, SimpleSubscriptionDetails>();

  public SimpleConsumerDetails(String consumerId, MALBrokerBindingImpl binding)
  {
    this.consumerId = consumerId;
    this.binding = binding;
  }

  public void report()
  {
    Logging.logMessage("    START Consumer ( " + consumerId + " )");
    Logging.logMessage("    Required: " + String.valueOf(required.size()));
    Set<Map.Entry<String, SimpleSubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, SimpleSubscriptionDetails> entry : values)
    {
      entry.getValue().report();
    }
    Logging.logMessage("    END Consumer ( " + consumerId + " )");
  }

  public boolean notActive()
  {
    return required.isEmpty();
  }

  public Set<SubscriptionKey> addSubscription(MessageHeader srcHdr, Subscription subscription)
  {
    String subId = subscription.getSubscriptionId().getValue();
    SimpleSubscriptionDetails sub = details.get(subId);
    if (null == sub)
    {
      sub = new SimpleSubscriptionDetails(srcHdr, subId);
      details.put(subId, sub);
    }
    sub.setIds(subscription.getEntities());
    updateIds();
    return required;
  }

  public void populateNotifyList(MessageHeader srcHdr, Identifier transId, List<BrokerMessage> lst, UpdateList updateList)
  {
    Logging.logMessage("INFO: Checking SimComDetails");
    Set<Map.Entry<String, SimpleSubscriptionDetails>> values = details.entrySet();
    Iterator<Map.Entry<String, SimpleSubscriptionDetails>> it = values.iterator();
    BrokerMessage bmsg = new BrokerMessage(binding);
    while (it.hasNext())
    {
      BrokerMessage.NotifyMessage subUpdate = it.next().getValue().populateNotifyList(updateList);
      if (null != subUpdate)
      {
        bmsg.msgs.add(subUpdate);
      }
    }
    if (!bmsg.msgs.isEmpty())
    {
      for (Iterator<BrokerMessage.NotifyMessage> it1 = bmsg.msgs.iterator(); it1.hasNext();)
      {
        BrokerMessage.NotifyMessage msg = it1.next();

        // update the details in the header
        msg.header.setURIto(new URI(consumerId));
        msg.header.setURIfrom(binding.getURI());
        msg.header.setAuthenticationId(binding.getAuthenticationId());
        msg.header.setTimestamp(srcHdr.getTimestamp());
        msg.header.setDomain(srcHdr.getDomain());
        msg.header.setNetworkZone(srcHdr.getNetworkZone());
        msg.header.setSession(srcHdr.getSession());
        msg.header.setSessionName(srcHdr.getSessionName());
        msg.header.setInteractionType(InteractionType.PUBSUB);
        msg.header.setInteractionStage(MALPubSubOperation.NOTIFY_STAGE);
        msg.header.setArea(srcHdr.getArea());
        msg.header.setService(srcHdr.getService());
        msg.header.setOperation(srcHdr.getOperation());
        msg.header.setVersion(srcHdr.getVersion());
        msg.header.setError(srcHdr.isError());

      }
      
      lst.add(bmsg);
    }
  }

  public void removeSubscriptions(IdentifierList subscriptions)
  {
    for (int i = 0; i < subscriptions.size(); i++)
    {
      Identifier sub = (Identifier) subscriptions.get(i);
      details.remove(sub.getValue());
    }
    updateIds();
  }

  public void removeAllSubscriptions()
  {
    details.clear();
    required.clear();
  }

  public void appendIds(Set<SubscriptionKey> new_set)
  {
    new_set.addAll(required);
  }

  protected void updateIds()
  {
    required.clear();
    Set<Map.Entry<String, SimpleSubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, SimpleSubscriptionDetails> entry : values)
    {
      entry.getValue().appendIds(required);
    }
  }
}
