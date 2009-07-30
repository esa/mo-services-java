/*
 * MALBrokerHandler.java
 *
 * Created on 21 August 2006, 15:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl.broker;

import java.util.LinkedList;
import java.util.Map;
import org.ccsds.moims.smc.mal.api.MALPubSubOperation;
import org.ccsds.moims.smc.mal.api.structures.MALDomainIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALEntityKeyList;
import org.ccsds.moims.smc.mal.api.structures.MALEntityRequest;
import org.ccsds.moims.smc.mal.api.structures.MALEntityRequestList;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALInteractionType;
import org.ccsds.moims.smc.mal.api.structures.MALMessageHeader;
import org.ccsds.moims.smc.mal.api.structures.MALOctet;
import org.ccsds.moims.smc.mal.api.structures.MALSessionType;
import org.ccsds.moims.smc.mal.api.structures.MALSubscription;
import org.ccsds.moims.smc.mal.api.structures.MALSubscriptionUpdate;
import org.ccsds.moims.smc.mal.api.structures.MALURI;
import org.ccsds.moims.smc.mal.api.structures.MALUpdate;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateList;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateType;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.impl.util.StructureHelper;

/**
 *
 * @author cooper_sf
 */
public class MALSimpleBrokerHandler implements MALBroker
{
  /**
   * A SubscriptionDetails is keyed on subscription Id
   */
  private static class SubscriptionDetails
  {
    private final String subscriptionId;
    private java.util.Set<MALSubscriptionKey> required = new java.util.TreeSet<MALSubscriptionKey>();
    private java.util.Set<MALSubscriptionKey> onAll = new java.util.TreeSet<MALSubscriptionKey>();
    private java.util.Set<MALSubscriptionKey> onChange = new java.util.TreeSet<MALSubscriptionKey>();

    public SubscriptionDetails(String subscriptionId)
    {
      this.subscriptionId = subscriptionId;
    }

    public void report()
    {
      System.out.println("      START Subscription ( " + subscriptionId + " )");
      System.out.println("      Required: " + String.valueOf(required.size()));
      System.out.println("      END Subscription ( " + subscriptionId + " )");
    }

    public boolean notActive()
    {
      return required.isEmpty();
    }

    public void setIds(MALEntityRequestList lst)
    {
      required.clear();
      onAll.clear();
      onChange.clear();

      for (int idx = 0; idx < lst.size(); idx++)
      {
        MALEntityRequest rqst = (MALEntityRequest) lst.get(idx);
        MALEntityKeyList keyList = rqst.getEntityKeys();
        boolean bOnChange = rqst.getOnlyOnChange().getBooleanValue();

        for (int i = 0; i < keyList.size(); i++)
        {
          MALIdentifierList id = (MALIdentifierList) keyList.get(i);
          MALSubscriptionKey key = new MALSubscriptionKey(id);

          required.add(key);
          if (bOnChange)
          {
            onChange.add(key);
          }
          else
          {
            onAll.add(key);
          }
        }
      }
    }

    public MALSubscriptionUpdate populateNotifyList(MALUpdateList updateList)
    {
      MALUpdateList sendList = new MALUpdateList();

      for (int i = 0; i < updateList.size(); ++i)
      {
        MALUpdate update = (MALUpdate) updateList.get(i);

        populateNotifyList(sendList, update);
      }

      MALSubscriptionUpdate retVal = null;

      if (false == sendList.isEmpty())
      {
        retVal = new MALSubscriptionUpdate();
        retVal.setSubscriptionId(new MALIdentifier(subscriptionId));
        retVal.setUpdateList(sendList);
      }

      return retVal;
    }

    private void populateNotifyList(MALUpdateList lst, MALUpdate update)
    {
      MALSubscriptionKey key = new MALSubscriptionKey(update.getKey());

      boolean updateRequired = matchedUpdate(key, onAll);

      if (!updateRequired && update.getUpdateType().equals(MALUpdateType.UPDATE))
      {
        updateRequired = matchedUpdate(key, onChange);
      }

      if (updateRequired)
      {
        // add update for this consumer/subscription
        lst.add(update);
      }
    }

    private static boolean matchedUpdate(MALSubscriptionKey key, java.util.Set<MALSubscriptionKey> searchSet)
    {
      boolean matched = false;

      for (MALSubscriptionKey subscriptionKey : searchSet)
      {
        if (subscriptionKey.matches(key))
        {
          matched = true;
          break;
        }
      }

      return matched;
    }

    private void appendIds(java.util.Set<MALSubscriptionKey> new_set)
    {
      new_set.addAll(required);
    }
  };

  /**
   * A SubscriptionDetails is keyed on subscription Id
   */
  private static class ConsumerDetails
  {
    private final String consumerId;
    private java.util.Set<MALSubscriptionKey> required = new java.util.TreeSet<MALSubscriptionKey>();
    private final java.util.Map<String, SubscriptionDetails> details = new java.util.TreeMap<String, SubscriptionDetails>();

    public ConsumerDetails(String consumerId)
    {
      this.consumerId = consumerId;
    }

    public void report()
    {
      System.out.println("    START Consumer ( " + consumerId + " )");
      System.out.println("    Required: " + String.valueOf(required.size()));
      System.out.println("    END Consumer ( " + consumerId + " )");
    }

    public boolean notActive()
    {
      return required.isEmpty();
    }

    public java.util.Set<MALSubscriptionKey> addSubscription(MALSubscription subscription)
    {
      String subId = subscription.getSubscriptionId().getIdentifierValue();
      SubscriptionDetails sub = details.get(subId);
      if (null == sub)
      {
        sub = new SubscriptionDetails(subId);
        details.put(subId, sub);
      }

      sub.setIds(subscription.getEntities());

      updateIds();

      return required;
    }

    public void populateNotifyList(MALMessageHeader srcHdr, MALIdentifier transId, java.util.List<MALBrokerMessage> lst, MALUpdateList updateList)
    {
      java.util.Set<Map.Entry<String, SubscriptionDetails>> values = details.entrySet();
      java.util.Iterator<Map.Entry<String, SubscriptionDetails>> it = values.iterator();

      MALBrokerMessage msg = new MALBrokerMessage();

      while (it.hasNext())
      {
        MALSubscriptionUpdate subUpdate = it.next().getValue().populateNotifyList(updateList);

        if (null != subUpdate)
        {
          msg.updates.add(subUpdate);
        }
      }

      if (!msg.updates.isEmpty())
      {
        // update the details in the header
        msg.header.setUriTo(new MALURI(consumerId));
        msg.header.setAuthenticationId(srcHdr.getAuthenticationId());
        msg.header.setTimeStamp(srcHdr.getTimeStamp());
        msg.header.setQoSLevel(srcHdr.getQoSLevel());
        msg.header.setPriority(srcHdr.getPriority());
        msg.header.setDomain(srcHdr.getDomain());
        msg.header.setNetworkZone(srcHdr.getNetworkZone());
        msg.header.setSession(srcHdr.getSession());
        msg.header.setSessionName(srcHdr.getSessionName());
        msg.header.setInteractionType(MALInteractionType.PUBSUB);
        msg.header.setInteractionStage(MALPubSubOperation.NOTIFY_STAGE);
        msg.header.setTransactionId(transId);
        msg.header.setArea(srcHdr.getArea());
        msg.header.setService(srcHdr.getService());
        msg.header.setOperation(srcHdr.getOperation());
        msg.header.setVersion(srcHdr.getVersion());
        msg.header.setIsError(srcHdr.isError());

        lst.add(msg);
      }
    }

    public void removeSubscriptions(MALIdentifierList subscriptions)
    {
      for (int i = 0; i < subscriptions.size(); i++)
      {
        MALIdentifier sub = (MALIdentifier) subscriptions.get(i);

        details.remove(sub.getIdentifierValue());
      }

      updateIds();
    }

    public void removeAllSubscriptions()
    {
      details.clear();
      required.clear();
    }

    public void appendIds(java.util.Set<MALSubscriptionKey> new_set)
    {
      new_set.addAll(required);
    }

    protected void updateIds()
    {
      required.clear();

      java.util.Set<Map.Entry<String, SubscriptionDetails>> values = details.entrySet();
      for (Map.Entry<String, SubscriptionDetails> entry : values)
      {
        entry.getValue().appendIds(required);
      }
    }
  }

  /**
   * A SubscriptionSource is keyed on Area, Service and Operation,
   *  it contains one to many ConsumerDetails.
   */
  private static class SubscriptionSource
  {
    private final MALIdentifier transactionId;
    private final MALDomainIdentifier domain;
    private final MALIdentifier networkZone;
    private final MALSessionType session;
    private final MALIdentifier area;
    private final MALIdentifier service;
    private final MALIdentifier operation;
    private final MALOctet version;
    private final String sig;
    private final java.util.Set<MALSubscriptionKey> required = new java.util.TreeSet<MALSubscriptionKey>();
    private final java.util.Map<String, ConsumerDetails> details = new java.util.TreeMap<String, ConsumerDetails>();

    public SubscriptionSource(MALMessageHeader hdr)
    {
      this.transactionId = hdr.getTransactionId();
      this.domain = hdr.getDomain();
      this.networkZone = hdr.getNetworkZone();
      this.session = hdr.getSession();
      this.area = hdr.getArea();
      this.service = hdr.getService();
      this.operation = hdr.getOperation();
      this.version = hdr.getVersion();
      this.sig = makeSig(hdr);
    }

    public boolean notActive()
    {
      return required.isEmpty();
    }

    public void report()
    {
      java.util.Set values = details.entrySet();
      java.util.Iterator it = values.iterator();

      System.out.println("  START Source ( " + sig + " )");
      System.out.println("  Required: " + String.valueOf(required.size()));
      while (it.hasNext())
      {
        ((ConsumerDetails) ((java.util.Map.Entry) it.next()).getValue()).report();
      }
      System.out.println("  END Source ( " + sig + " )");
    }

    public void addSubscription(String consumer, MALSubscription subscription)
    {
      ConsumerDetails det = getDetails(consumer);

      java.util.Set<MALSubscriptionKey> retVal = det.addSubscription(subscription);

      required.addAll(retVal);
    }

    public void populateNotifyList(MALMessageHeader srcHdr, java.util.List<MALBrokerMessage> lst, MALUpdateList updateList)
    {
      java.util.Set<Map.Entry<String, ConsumerDetails>> values = details.entrySet();
      java.util.Iterator<Map.Entry<String, ConsumerDetails>> it = values.iterator();

      java.util.List<MALBrokerMessage> localLst = new LinkedList<MALBrokerMessage>();
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

    public void removeSubscriptions(String consumer, MALIdentifierList subscriptions)
    {
      ConsumerDetails det = getDetails(consumer);

      det.removeSubscriptions(subscriptions);

      if (det.notActive())
      {
        details.remove(consumer);
      }

      updateIds();
    }

    public void removeAllSubscriptions(String consumer)
    {
      ConsumerDetails det = getDetails(consumer);

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

      java.util.Set<Map.Entry<String, ConsumerDetails>> values = details.entrySet();
      for (Map.Entry<String, ConsumerDetails> entry : values)
      {
        entry.getValue().appendIds(required);
      }
    }

    protected ConsumerDetails getDetails(String consumer)
    {
      ConsumerDetails retVal = (ConsumerDetails) details.get(consumer);

      if (null == retVal)
      {
        retVal = new ConsumerDetails(consumer);
        details.put(consumer, retVal);
      }

      return retVal;
    }
  };
  private final java.util.Map<String, SubscriptionSource> entryMap = new java.util.TreeMap<String, SubscriptionSource>();

  /** Creates a new instance of MALBrokerHandler */
  public MALSimpleBrokerHandler()
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

          if (ent.notActive())
          {
            entryMap.remove(ent.sig);
          }
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

        if (ent.notActive())
        {
          entryMap.remove(ent.sig);
        }

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
  /**
   * returns a list of new ids
  private static MALIdentifierList addIdsReturnNew(java.util.Set required, S_SubscriptionList lst)
  {
  java.util.TreeSet new_set = new java.util.TreeSet();
  
  java.util.Iterator it = lst.getlist().iterator();
  while(it.hasNext())
  {
  S_Subscription sub = (S_Subscription)it.next();
  
  new_set.addAll(sub.getids().getlist());
  }
  
  return addIdsReturnNew(required, new_set);
  }
   */
  /**
   * returns a list of new ids
  protected static MALIdentifierList addIdsReturnNew(java.util.Set required, MALIdentifierList lst)
  {
  return addIdsReturnNew(required, new java.util.TreeSet(lst.getlist()));
  }
   */
  /**
   * returns a list of new ids
  protected static MALIdentifierList addIdsReturnNew(java.util.Set required, java.util.Set new_set)
  {
  java.util.TreeSet original_set = new java.util.TreeSet(required);
  
  required.addAll(new_set);
  
  MALIdentifierList retVal = new MALIdentifierList();
  retVal.getlist().addAll(required);
  retVal.getlist().removeAll(original_set);
  
  return retVal;
  }
   */
}
