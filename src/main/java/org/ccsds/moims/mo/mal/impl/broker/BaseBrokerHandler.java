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

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
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
  private final Map<String, ProviderDetails> providerMap = new TreeMap<String, ProviderDetails>();
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
        getEntry(hdr, true).addSubscription(hdr, hdr.getURIfrom().getValue(), lst, binding);
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
    ProviderDetails details = providerMap.get(hdr.getURIfrom().getValue());

    if (null == details)
    {
      details = new ProviderDetails(hdr.getURIfrom().getValue(), hdr.getQoSlevel());
      providerMap.put(hdr.getURIfrom().getValue(), details);
      Logging.logMessage("New publisher registering: " + hdr);
    }

    details.setKeyList(providerKeyList);

    report();
  }

  /**
   * Returns the QoS level used by the provider.
   * @param hdr Message source.
   * @return QoSLevel used.
   */
  public QoSLevel getProviderQoSLevel(MessageHeader hdr)
  {
    ProviderDetails details = providerMap.get(hdr.getURIfrom().getValue());

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
    ProviderDetails details = providerMap.get(hdr.getURIfrom().getValue());

    if (null == details)
    {
      throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    }

    details.checkPublish(updateList);

    java.util.List<BrokerMessage> lst = new java.util.LinkedList<BrokerMessage>();

    if (updateList != null)
    {
      Logging.logMessage("INFO: Checking BaseBrokerHandler");
      SubscriptionSource ent = getEntry(hdr, true);

      if (null != ent)
      {
        ent.populateNotifyList(hdr, lst, updateList);
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
    providerMap.remove(hdr.getURIfrom().getValue());
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
        SubscriptionSource ent = getEntry(hdr, false);
        if (null != ent)
        {
          ent.removeSubscriptions(hdr.getURIfrom().getValue(), lst);
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
      SubscriptionSource ent = getEntry(hdr, false);
      if (null != ent)
      {
        ent.removeAllSubscriptions(hdr.getURIto().getValue());
        if (!ent.active())
        {
          consumerMap.remove(ent.getSignature());
        }
      }
    }
    report();
  }

  private SubscriptionSource getEntry(MessageHeader hdr, boolean create)
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

  /**
   * Creates a broker implementation specific subscription source.
   * @param hdr Source message header.
   * @return The new subscription source object.
   */
  protected abstract SubscriptionSource createEntry(MessageHeader hdr);

  /**
   * Creates a unique signature for a broker based on the message header.
   * @param hdr Source message.
   * @return Broker subscription signature.
   */
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
}
