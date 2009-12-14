/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 *
 * @author cooper_sf
 */
public abstract class MALBaseBrokerHandler implements MALBrokerHandler
{
  private final Map<String, ProviderDetails> providerMap = new TreeMap<String, ProviderDetails>();
  private final Map<String, SubscriptionSource> consumerMap = new TreeMap<String, SubscriptionSource>();

  public MALBaseBrokerHandler()
  {
  }

  public synchronized void report()
  {
    System.out.println("START REPORT");

    java.util.Collection<ProviderDetails> pvalues = providerMap.values();
    for (ProviderDetails subscriptionSource : pvalues)
    {
      subscriptionSource.report();
    }

    java.util.Collection<SubscriptionSource> cvalues = consumerMap.values();
    for (SubscriptionSource subscriptionSource : cvalues)
    {
      subscriptionSource.report();
    }

    System.out.println("END REPORT");
  }

  @Override
  public synchronized void addConsumer(MessageHeader hdr, Subscription lst, MALBrokerBindingImpl binding)
  {
    report();
    if (null != hdr)
    {
      if (lst != null)
      {
        getEntry(hdr, true).addSubscription(hdr, hdr.getURIfrom().getValue(), lst, binding);
      }
    }
    report();
  }

  @Override
  public synchronized void addProvider(MessageHeader hdr, EntityKeyList l)
  {
    report();
    ProviderDetails details = providerMap.get(hdr.getURIfrom().getValue());

    if (null == details);
    {
      details = new ProviderDetails(hdr.getURIfrom().getValue());
      providerMap.put(hdr.getURIfrom().getValue(), details);
    }

    details.setKeyList(l);

    report();
  }

  @Override
  public synchronized java.util.List<MALBrokerMessage> createNotify(MessageHeader hdr, UpdateList updateList) throws MALException
  {
    ProviderDetails details = providerMap.get(hdr.getURIfrom().getValue());

    if (null == details)
    {
      throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    }

    details.checkPublish(updateList);

    java.util.List<MALBrokerMessage> lst = new java.util.LinkedList<MALBrokerMessage>();

    if ((null != hdr) && (updateList != null))
    {
      System.out.println("INFO: Checking BaseBrokerHandler");
      SubscriptionSource ent = getEntry(hdr, true);

      if (null != ent)
      {
        ent.populateNotifyList(hdr, lst, updateList);
      }
    }

    return lst;
  }

  @Override
  public synchronized void removeProvider(MessageHeader hdr)
  {
    report();
    providerMap.remove(hdr.getURIfrom().getValue());
    report();
  }

  @Override
  public void removeConsumer(MessageHeader hdr, IdentifierList lst)
  {
    report();
    if (null != hdr)
    {
      if ((lst != null) && (0 < lst.size()))
      {
        SubscriptionSource ent = getEntry(hdr, false);
        if (null != ent)
        {
          ent.removeSubscriptions(hdr.getURIfrom().getValue(), lst);
          if (ent.notActive())
          {
            consumerMap.remove(ent.getSignature());
          }
        }
      }
    }
    report();
  }

  @Override
  public synchronized void removeLostConsumer(MessageHeader hdr)
  {
    report();
    if (null != hdr)
    {
      SubscriptionSource ent = getEntry(hdr, false);
      if (null != ent)
      {
        ent.removeAllSubscriptions(hdr.getURIto().getValue());
        if (ent.notActive())
        {
          consumerMap.remove(ent.getSignature());
        }
      }
    }
    report();
  }

  protected SubscriptionSource getEntry(MessageHeader hdr, boolean create)
  {
    String sig = makeSig(hdr);
    SubscriptionSource ent = consumerMap.get(sig);

    if ((null == ent) && (create))
    {
      ent = createEntry(hdr);
      consumerMap.put(sig, ent);
    }

    return ent;
  }

  protected abstract SubscriptionSource createEntry(MessageHeader hdr);

  public static String makeSig(MessageHeader hdr)
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

  protected static final class ProviderDetails
  {
    private final String uri;
    private final Set<MALSubscriptionKey> keySet = new TreeSet<MALSubscriptionKey>();

    public ProviderDetails(String uri)
    {
      this.uri = uri;
    }

    public void report()
    {
      System.out.println("  START Provider ( " + uri + " )");
      for (MALSubscriptionKey key : keySet)
      {
        System.out.println("  Allowed: " + key);
      }
      System.out.println("  END Provider ( " + uri + " )");
    }

    public void setKeyList(EntityKeyList l)
    {
      keySet.clear();

      for (int i = 0; i < l.size(); i++)
      {
        keySet.add(new MALSubscriptionKey(l.get(i)));
      }
    }

    public void checkPublish(UpdateList updateList) throws MALException
    {
      EntityKeyList lst = new EntityKeyList();

      for (int i = 0; i < updateList.size(); i++)
      {
        Update update = (Update) updateList.get(i);

        MALSubscriptionKey publishKey = new MALSubscriptionKey(update.getKey());

        boolean matched = false;
        for (MALSubscriptionKey key : keySet)
        {
          if (key.matches(publishKey))
          {
            matched = true;
            break;
          }
        }
        if (!matched)
        {
          lst.add(update.getKey());
        }
      }

      if (0 < lst.size())
      {
        throw new MALException(new StandardError(MALHelper.UNKNOWN_ERROR_NUMBER, lst));
      }
    }
  }
}
