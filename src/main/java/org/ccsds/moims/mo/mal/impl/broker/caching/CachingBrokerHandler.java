/*
 * CachingBrokerHandler.java
 *
 * Created on 21 August 2006, 15:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker.caching;

import org.ccsds.moims.mo.mal.impl.broker.*;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;

/**
 *
 * @author cooper_sf
 */
public class CachingBrokerHandler implements MALBroker
{
  private final java.util.Map<String, SubscriptionSource> entryMap = new java.util.TreeMap<String, SubscriptionSource>();

  /** Creates a new instance of CachingBrokerHandler */
  public CachingBrokerHandler()
  {
  }

  public synchronized void report()
  {
    java.util.Collection<SubscriptionSource> values = entryMap.values();
    java.util.Iterator<SubscriptionSource> it = values.iterator();

    System.out.println("START REPORT");
    while (it.hasNext())
    {
      it.next().report();
    }

    System.out.println("END REPORT");
  }

  public synchronized void addConsumer(MALMessage msg)
  {
    if (null != msg)
    {
      Subscription lst = (Subscription) msg.getBody();

      if (lst != null)
      {
        getEntry(msg.getHeader(), true).addSubscription(msg.getHeader().getURIfrom().getValue(), lst);
      }
    }
  }

  public synchronized java.util.List<MALBrokerMessage> createNotify(MessageHeader hdr, UpdateList updateList)
  {
    java.util.List<MALBrokerMessage> lst = new java.util.LinkedList<MALBrokerMessage>();

    if ((null != hdr) && (updateList != null))
    {
      SubscriptionSource ent = getEntry(hdr, true);

      if (null != ent)
      {
        ent.populateNotifyList(hdr, lst, updateList);
      }
    }

    return lst;
  }

  public synchronized void removeConsumer(MALMessage msg)
  {
    if (null != msg)
    {
      IdentifierList lst = (IdentifierList) msg.getBody();

      if ((lst != null) && (0 < lst.size()))
      {
        SubscriptionSource ent = getEntry(msg.getHeader(), false);

        if (null != ent)
        {
          ent.removeSubscriptions(msg.getHeader().getURIfrom().getValue(), lst);
        }
      }
    }
  }

  public synchronized void removeLostConsumer(MessageHeader hdr)
  {
    if (null != hdr)
    {
      SubscriptionSource ent = getEntry(hdr, false);

      if (null != ent)
      {
        ent.removeAllSubscriptions(hdr.getURIfrom().getValue());
      }
    }
  }

  private SubscriptionSource getEntry(MessageHeader hdr, boolean create)
  {
    String sig = makeSig(hdr);
    SubscriptionSource ent = entryMap.get(sig);

    if ((null == ent) && (create))
    {
      ent = new SubscriptionSource(hdr);
      entryMap.put(sig, ent);
    }

    return ent;
  }

  static String makeSig(MessageHeader hdr)
  {
    StringBuffer buf = new StringBuffer();

    buf.append(StructureHelper.domainToString(hdr.getDomain()));
    buf.append("::");
    buf.append(hdr.getNetworkZone());
    buf.append("::");
    buf.append(hdr.getSession());
    buf.append("::");
    buf.append(hdr.getArea());
    buf.append("::");
    buf.append(hdr.getService());
    buf.append("::");
    buf.append(hdr.getOperation());
    buf.append("::");
    buf.append(hdr.getVersion());

    return buf.toString();
  }
}
