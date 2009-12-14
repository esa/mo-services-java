package org.ccsds.moims.mo.mal.impl.broker.simple;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerMessage;
import org.ccsds.moims.mo.mal.impl.broker.MALSubscriptionKey;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionSource;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * A SimpleSubscriptionSource is keyed on Area, Service and Operation,
 * it contains one to many SimpleConsumerDetails.
 */
class SimpleSubscriptionSource extends SubscriptionSource
{
  private final Set<MALSubscriptionKey> required = new TreeSet<MALSubscriptionKey>();
  private final Map<String, SimpleConsumerDetails> details = new TreeMap<String, SimpleConsumerDetails>();

  public SimpleSubscriptionSource(MessageHeader hdr)
  {
    super(hdr);
  }

  @Override
  public boolean notActive()
  {
    return required.isEmpty();
  }

  @Override
  public void report()
  {
    Set values = details.entrySet();
    Iterator it = values.iterator();
    System.out.println("  START Source ( " + signature + " )");
    System.out.println("  Required: " + String.valueOf(required.size()));
    while (it.hasNext())
    {
      ((SimpleConsumerDetails) ((Entry) it.next()).getValue()).report();
    }
    System.out.println("  END Source ( " + signature + " )");
  }

  @Override
  public void addSubscription(MessageHeader srcHdr, String consumer, Subscription subscription, MALBrokerBindingImpl binding)
  {
    SimpleConsumerDetails det = getDetails(consumer, binding);
    Set<MALSubscriptionKey> retVal = det.addSubscription(srcHdr, subscription);
    required.addAll(retVal);
  }

  @Override
  public void populateNotifyList(MessageHeader srcHdr, List<MALBrokerMessage> lst, UpdateList updateList)
  {
    System.out.println("INFO: Checking SimSubSource");
    Set<Map.Entry<String, SimpleConsumerDetails>> values = details.entrySet();
    Iterator<Map.Entry<String, SimpleConsumerDetails>> it = values.iterator();
    List<MALBrokerMessage> localLst = new LinkedList<MALBrokerMessage>();
    while (it.hasNext())
    {
      it.next().getValue().populateNotifyList(srcHdr, transactionId, localLst, updateList);
    }
    // zip through list and insert our details
    if (!localLst.isEmpty())
    {
      lst.addAll(localLst);
    }
  }

  @Override
  public void removeSubscriptions(String consumer, IdentifierList subscriptions)
  {
    SimpleConsumerDetails det = getDetails(consumer, null);
    det.removeSubscriptions(subscriptions);
    if (det.notActive())
    {
      details.remove(consumer);
    }
    updateIds();
  }

  @Override
  public void removeAllSubscriptions(String consumer)
  {
    SimpleConsumerDetails det = getDetails(consumer, null);
    if (null != det)
    {
      det.removeAllSubscriptions();
      if (det.notActive())
      {
        details.remove(consumer);
      }
    }
    updateIds();
  }

  protected void updateIds()
  {
    required.clear();
    Set<Map.Entry<String, SimpleConsumerDetails>> values = details.entrySet();
    for (Map.Entry<String, SimpleConsumerDetails> entry : values)
    {
      entry.getValue().appendIds(required);
    }
  }

  protected SimpleConsumerDetails getDetails(String consumer, MALBrokerBindingImpl binding)
  {
    SimpleConsumerDetails retVal = (SimpleConsumerDetails) details.get(consumer);
    if (null == retVal)
    {
      retVal = new SimpleConsumerDetails(this, consumer, binding);
      details.put(consumer, retVal);
    }
    return retVal;
  }
}
