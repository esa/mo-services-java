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
package org.ccsds.moims.mo.mal.impl.broker.simple;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.broker.BrokerMessage;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionKey;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionSource;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * A SimpleSubscriptionSource represents a single consumer indexed by URI.
 */
class SimpleSubscriptionSource extends SubscriptionSource
{
  private final String signature;
  private final Set<SubscriptionKey> required = new TreeSet<SubscriptionKey>();
  private final MALBrokerBindingImpl binding;
  private final Map<String, SimpleSubscriptionDetails> details = new TreeMap<String, SimpleSubscriptionDetails>();

  public SimpleSubscriptionSource(MessageHeader hdr, MALBrokerBindingImpl binding)
  {
    super(hdr);
    this.signature = hdr.getURIfrom().getValue();
    this.binding = binding;
  }

  @Override
  public boolean active()
  {
    return !required.isEmpty();
  }

  @Override
  public void report()
  {
    Logging.logMessage("    START Consumer ( " + signature + " )");
    Logging.logMessage("    Required: " + String.valueOf(required.size()));
    Set<Map.Entry<String, SimpleSubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, SimpleSubscriptionDetails> entry : values)
    {
      entry.getValue().report();
    }
    Logging.logMessage("    END Consumer ( " + signature + " )");
  }

  @Override
  public String getSignature()
  {
    return signature;
  }

  @Override
  public void addSubscription(MessageHeader srcHdr, Subscription subscription)
  {
    //SimpleConsumerDetails det = getDetails(consumer, binding);
    //Set<SubscriptionKey> retVal = det.addSubscription(srcHdr, subscription);
    //required.addAll(retVal);

    String subId = subscription.getSubscriptionId().getValue();
    SimpleSubscriptionDetails sub = details.get(subId);
    if (null == sub)
    {
      sub = new SimpleSubscriptionDetails(srcHdr, subId);
      details.put(subId, sub);
    }
    sub.setIds(srcHdr, subscription.getEntities());
    updateIds();
  }

  @Override
  public void populateNotifyList(MessageHeader srcHdr, List<BrokerMessage> lst, UpdateList updateList)
  {
    Logging.logMessage("INFO: Checking SimComSource : " + signature);

    String srcDomainId = StructureHelper.domainToString(srcHdr.getDomain());

    List<BrokerMessage> localLst = new LinkedList<BrokerMessage>();

    Set<Map.Entry<String, SimpleSubscriptionDetails>> values = details.entrySet();
    Iterator<Map.Entry<String, SimpleSubscriptionDetails>> it = values.iterator();
    BrokerMessage bmsg = new BrokerMessage(binding);
    while (it.hasNext())
    {
      BrokerMessage.NotifyMessage subUpdate = it.next().getValue().populateNotifyList(srcHdr, srcDomainId, updateList);
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
        msg.header.setURIto(new URI(signature));
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

      localLst.add(bmsg);
    }

    // zip through list and insert our details
    if (!localLst.isEmpty())
    {
      lst.addAll(localLst);
    }
  }

  @Override
  public void removeSubscriptions(IdentifierList subscriptions)
  {
    for (int i = 0; i < subscriptions.size(); i++)
    {
      Identifier sub = (Identifier) subscriptions.get(i);
      details.remove(sub.getValue());
    }
    updateIds();
  }

  @Override
  public void removeAllSubscriptions()
  {
    details.clear();
    required.clear();
  }

  private void updateIds()
  {
    required.clear();
    Set<Map.Entry<String, SimpleSubscriptionDetails>> values = details.entrySet();
    for (Map.Entry<String, SimpleSubscriptionDetails> entry : values)
    {
      entry.getValue().appendIds(required);
    }
  }
}
