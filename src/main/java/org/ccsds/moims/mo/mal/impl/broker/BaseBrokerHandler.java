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
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.impl.StringPair;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * The base class for MAL level broker logic implementations.
 */
public abstract class BaseBrokerHandler
{
  private final Map<StringPair, ProviderDetails> providerMap = new TreeMap<StringPair, ProviderDetails>();
  private final Map<String, SubscriptionSource> consumerMap = new TreeMap<String, SubscriptionSource>();

  /**
   * Constructor.
   */
  public BaseBrokerHandler()
  {
  }

  private synchronized void report()
  {
    Logging.logMessage("START REPORT");

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

    Logging.logMessage("END REPORT");
  }

  /**
   * Adds a consumer to this Broker.
   * @param hdr Message header.
   * @param lst Subscription list.
   * @param binding Source binding.
   */
  public synchronized void addConsumer(MessageHeader hdr, Subscription lst, MALBrokerBindingImpl binding)
  {
    report();
    if (null != hdr)
    {
      if (lst != null)
      {
        getEntry(hdr, true, binding).addSubscription(hdr, lst);
      }
    }
    report();
  }

  /**
   * Add a provider to this broker.
   * @param hdr Source message.
   * @param providerKeyList List of keys from the provider.
   */
  public synchronized void addProvider(MessageHeader hdr, EntityKeyList providerKeyList)
  {
    report();
    ProviderDetails details = providerMap.get(new StringPair(hdr.getURIfrom().getValue(), StructureHelper.domainToString(hdr.getDomain())));

    if (null == details)
    {
      details = new ProviderDetails(hdr.getURIfrom().getValue(), hdr.getQoSlevel());
      providerMap.put(new StringPair(hdr.getURIfrom().getValue(), StructureHelper.domainToString(hdr.getDomain())), details);
      Logging.logMessage("New publisher registering: " + hdr);
    }

    details.setKeyList(hdr, providerKeyList);

    report();
  }

  /**
   * Returns the QoS level used by the provider.
   * @param hdr Message source.
   * @return QoSLevel used.
   */
  public QoSLevel getProviderQoSLevel(MessageHeader hdr)
  {
    ProviderDetails details = providerMap.get(new StringPair(hdr.getURIfrom().getValue(), StructureHelper.domainToString(hdr.getDomain())));

    if (null != details)
    {
      return details.getQosLevel();
    }

    return null;
  }

  /**
   * Creates a set of Notify messages based on an update list.
   * @param hdr Source update header.
   * @param updateList Update list.
   * @return List of notify messages.
   * @throws MALException on Error.
   */
  public synchronized java.util.List<BrokerMessage> createNotify(MessageHeader hdr,
          UpdateList updateList) throws MALException
  {
    Logging.logMessage("INFO: Checking BaseBrokerHandler");
    ProviderDetails details = providerMap.get(new StringPair(hdr.getURIfrom().getValue(), StructureHelper.domainToString(hdr.getDomain())));

    if (null == details)
    {
      Logging.logMessage("ERR : Provider not known");
      throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    }

    details.checkPublish(hdr, updateList);

    java.util.List<BrokerMessage> lst = new java.util.LinkedList<BrokerMessage>();

    if (updateList != null)
    {
      Set<Map.Entry<String, SubscriptionSource>> values = consumerMap.entrySet();
      Iterator<Map.Entry<String, SubscriptionSource>> it = values.iterator();
      while (it.hasNext())
      {
        it.next().getValue().populateNotifyList(hdr, lst, updateList);
      }
    }

    return lst;
  }

  /**
   * Removes a provider from this broker.
   * @param hdr Source message.
   */
  public synchronized void removeProvider(MessageHeader hdr)
  {
    report();
    providerMap.remove(new StringPair(hdr.getURIfrom().getValue(), StructureHelper.domainToString(hdr.getDomain())));
    report();
  }

  /**
   * Removes a consumer from this broker.
   * @param hdr Source message.
   * @param lst Subscription identifiers to remove.
   */
  public void removeConsumer(MessageHeader hdr, IdentifierList lst)
  {
    report();
    if (null != hdr)
    {
      if ((lst != null) && (0 < lst.size()))
      {
        SubscriptionSource ent = getEntry(hdr, false, null);
        if (null != ent)
        {
          ent.removeSubscriptions(lst);
          if (!ent.active())
          {
            consumerMap.remove(ent.getSignature());
          }
        }
      }
    }
    report();
  }

  /**
   * Removes a consumer that we have lost contact with.
   * @param hdr Source header.
   */
  public synchronized void removeLostConsumer(MessageHeader hdr)
  {
    report();
    if (null != hdr)
    {
      SubscriptionSource ent = getEntry(hdr, false, null);
      if (null != ent)
      {
        ent.removeAllSubscriptions();
        if (!ent.active())
        {
          consumerMap.remove(ent.getSignature());
        }
      }
    }
    report();
  }

  private SubscriptionSource getEntry(MessageHeader hdr, boolean create, MALBrokerBindingImpl binding)
  {
    String sig = hdr.getURIfrom().getValue();
    SubscriptionSource ent = consumerMap.get(sig);

    if ((null == ent) && (create))
    {
      ent = createEntry(hdr, binding);
      consumerMap.put(sig, ent);
    }

    return ent;
  }

  /**
   * Creates a broker implementation specific subscription source.
   * @param hdr Source message header.
   * @return The new subscription source object.
   */
  protected abstract SubscriptionSource createEntry(MessageHeader hdr, MALBrokerBindingImpl binding);
}
