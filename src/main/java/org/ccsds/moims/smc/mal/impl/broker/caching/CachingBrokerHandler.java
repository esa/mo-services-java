/*
 * CachingBrokerHandler.java
 *
 * Created on 21 August 2006, 15:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.broker.caching;

import org.ccsds.moims.smc.mal.impl.broker.*;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALMessageHeader;
import org.ccsds.moims.smc.mal.api.structures.MALSubscription;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateList;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.util.StructureHelper;

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
      MALSubscription lst = (MALSubscription) msg.getBody();

      if (lst != null)
      {
        getEntry(msg.getHeader(), true).addSubscription(msg.getHeader().getUriFrom().getURIValue(), lst);
      }
    }
  }

  public synchronized java.util.List<MALBrokerMessage> createNotify(MALMessageHeader hdr, MALUpdateList updateList)
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
      MALIdentifierList lst = (MALIdentifierList) msg.getBody();

      if ((lst != null) && (0 < lst.size()))
      {
        SubscriptionSource ent = getEntry(msg.getHeader(), false);

        if (null != ent)
        {
          ent.removeSubscriptions(msg.getHeader().getUriFrom().getURIValue(), lst);
        }
      }
    }
  }

  public synchronized void removeLostConsumer(MALMessageHeader hdr)
  {
    if (null != hdr)
    {
      SubscriptionSource ent = getEntry(hdr, false);

      if (null != ent)
      {
        ent.removeAllSubscriptions(hdr.getUriTo().getURIValue());
      }
    }
  }

  private SubscriptionSource getEntry(MALMessageHeader hdr, boolean create)
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

  static String makeSig(MALMessageHeader hdr)
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
    buf.append(hdr.getVersion().getOctetValue());

    return buf.toString();
  }
}
