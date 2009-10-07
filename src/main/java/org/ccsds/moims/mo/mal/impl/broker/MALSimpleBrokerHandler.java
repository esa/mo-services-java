/*
 * MALBrokerHandler.java
 *
 * Created on 21 August 2006, 15:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.LinkedList;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.EntityRequest;
import org.ccsds.moims.mo.mal.structures.EntityRequestList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.structures.UpdateType;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.EntityKey;

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

    public void setIds(EntityRequestList lst)
    {
      required.clear();
      onAll.clear();
      onChange.clear();

      for (int idx = 0; idx < lst.size(); idx++)
      {
        EntityRequest rqst = (EntityRequest) lst.get(idx);
        EntityKeyList keyList = rqst.getEntityKeys();
        boolean bOnChange = rqst.isOnlyOnChange();

        for (int i = 0; i < keyList.size(); i++)
        {
          EntityKey id = (EntityKey)keyList.get(i);
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

    public SubscriptionUpdate populateNotifyList(UpdateList updateList)
    {
      UpdateList sendList = new UpdateList();

      for (int i = 0; i < updateList.size(); ++i)
      {
        Update update = (Update) updateList.get(i);

        populateNotifyList(sendList, update);
      }

      SubscriptionUpdate retVal = null;

      if (false == sendList.isEmpty())
      {
        retVal = new SubscriptionUpdate();
        retVal.setSubscriptionId(new Identifier(subscriptionId));
        retVal.setUpdateList(sendList);
      }

      return retVal;
    }

    private void populateNotifyList(UpdateList lst, Update update)
    {
      MALSubscriptionKey key = new MALSubscriptionKey(update.getKey());

      boolean updateRequired = matchedUpdate(key, onAll);

      if (!updateRequired && update.getUpdateType().equals(UpdateType.UPDATE))
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

    public java.util.Set<MALSubscriptionKey> addSubscription(Subscription subscription)
    {
      String subId = subscription.getSubscriptionId().getValue();
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

    public void populateNotifyList(MessageHeader srcHdr, Identifier transId, java.util.List<MALBrokerMessage> lst, UpdateList updateList)
    {
      java.util.Set<Map.Entry<String, SubscriptionDetails>> values = details.entrySet();
      java.util.Iterator<Map.Entry<String, SubscriptionDetails>> it = values.iterator();

      MALBrokerMessage msg = new MALBrokerMessage();

      while (it.hasNext())
      {
        SubscriptionUpdate subUpdate = it.next().getValue().populateNotifyList(updateList);

        if (null != subUpdate)
        {
          msg.updates.add(subUpdate);
        }
      }

      if (!msg.updates.isEmpty())
      {
        // update the details in the header
        msg.header.setURIto(new URI(consumerId));
        msg.header.setAuthenticationId(srcHdr.getAuthenticationId());
        msg.header.setTimestamp(srcHdr.getTimestamp());
        msg.header.setQoSlevel(srcHdr.getQoSlevel());
        msg.header.setPriority(srcHdr.getPriority());
        msg.header.setDomain(srcHdr.getDomain());
        msg.header.setNetworkZone(srcHdr.getNetworkZone());
        msg.header.setSession(srcHdr.getSession());
        msg.header.setSessionName(srcHdr.getSessionName());
        msg.header.setInteractionType(InteractionType.PUBSUB);
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
    private final Identifier transactionId;
    private final DomainIdentifier domain;
    private final Identifier networkZone;
    private final SessionType session;
    private final Identifier area;
    private final Identifier service;
    private final Identifier operation;
    private final Byte version;
    private final String sig;
    private final java.util.Set<MALSubscriptionKey> required = new java.util.TreeSet<MALSubscriptionKey>();
    private final java.util.Map<String, ConsumerDetails> details = new java.util.TreeMap<String, ConsumerDetails>();

    public SubscriptionSource(MessageHeader hdr)
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

    public void addSubscription(String consumer, Subscription subscription)
    {
      ConsumerDetails det = getDetails(consumer);

      java.util.Set<MALSubscriptionKey> retVal = det.addSubscription(subscription);

      required.addAll(retVal);
    }

    public void populateNotifyList(MessageHeader srcHdr, java.util.List<MALBrokerMessage> lst, UpdateList updateList)
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

    public void removeSubscriptions(String consumer, IdentifierList subscriptions)
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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

          if (ent.notActive())
          {
            entryMap.remove(ent.sig);
          }
        }
      }
    }
  }

  @Override
  public synchronized void removeLostConsumer(MessageHeader hdr)
  {
    if (null != hdr)
    {
      SubscriptionSource ent = getEntry(hdr, false);

      if (null != ent)
      {
        ent.removeAllSubscriptions(hdr.getURIto().getValue());

        if (ent.notActive())
        {
          entryMap.remove(ent.sig);
        }

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
  /**
   * returns a list of new ids
  private static IdentifierList addIdsReturnNew(java.util.Set required, S_SubscriptionList lst)
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
  protected static IdentifierList addIdsReturnNew(java.util.Set required, IdentifierList lst)
  {
  return addIdsReturnNew(required, new java.util.TreeSet(lst.getlist()));
  }
   */
  /**
   * returns a list of new ids
  protected static IdentifierList addIdsReturnNew(java.util.Set required, java.util.Set new_set)
  {
  java.util.TreeSet original_set = new java.util.TreeSet(required);
  
  required.addAll(new_set);
  
  IdentifierList retVal = new IdentifierList();
  retVal.getlist().addAll(required);
  retVal.getlist().removeAll(original_set);
  
  return retVal;
  }
   */
}
