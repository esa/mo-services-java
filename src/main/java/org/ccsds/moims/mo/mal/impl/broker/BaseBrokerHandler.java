/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.impl.StringPair;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

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

  /**
   * Adds a consumer to this Broker.
   *
   * @param hdr Message header.
   * @param lst Subscription list.
   * @param binding Source binding.
   */
  public synchronized void addConsumer(final MALMessageHeader hdr,
          final Subscription lst,
          final MALBrokerBindingImpl binding)
  {
    report();
    if ((null != hdr) && (null != lst))
    {
      getEntry(hdr, true, binding).addSubscription(hdr, lst);
    }
    report();
  }

  /**
   * Add a provider to this broker.
   *
   * @param hdr Source message.
   * @param providerKeyList List of keys from the provider.
   */
  public synchronized void addProvider(final MALMessageHeader hdr, final EntityKeyList providerKeyList)
  {
    report();
    ProviderDetails details = providerMap.get(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr)));

    if (null == details)
    {
      details = new ProviderDetails(hdr.getURIFrom().getValue(), hdr.getQoSlevel());
      providerMap.put(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr)), details);
      MALBrokerImpl.LOGGER.log(Level.INFO, "New publisher registering: {0}", hdr);
    }

    details.setKeyList(hdr, providerKeyList);

    report();
  }

  /**
   * Returns the QoS level used by the provider.
   *
   * @param hdr Message source.
   * @return QoSLevel used.
   */
  public QoSLevel getProviderQoSLevel(final MALMessageHeader hdr)
  {
    final ProviderDetails details =
            providerMap.get(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr)));

    if (null != details)
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "Getting publisher QoS details: {0}", hdr);
      return details.getQosLevel();
    }

    return null;
  }

  /**
   * Creates a set of Notify messages based on an update list.
   *
   * @param hdr Source update header.
   * @param publishBody The publish message body.
   * @return List of notify messages.
   * @throws MALInteractionException On an interaction error.
   * @throws MALException on Error.
   */
  public synchronized java.util.List<BrokerMessage> createNotify(final MALMessageHeader hdr,
          final MALPublishBody publishBody) throws MALInteractionException, MALException
  {
    MALBrokerImpl.LOGGER.fine("Checking BaseBrokerHandler");
    final ProviderDetails details =
            providerMap.get(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr)));

    if (null == details)
    {
      MALBrokerImpl.LOGGER.warning("Provider not known");
      throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    }

    final UpdateHeaderList hl = publishBody.getUpdateHeaderList();
    details.checkPublish(hdr, hl);

    final List<BrokerMessage> lst = new LinkedList<BrokerMessage>();

    if (hl != null)
    {
      for (Map.Entry<String, SubscriptionSource> entry : consumerMap.entrySet())
      {
        entry.getValue().populateNotifyList(hdr, lst, hl, publishBody);
      }
    }

    return lst;
  }

  /**
   * Removes a provider from this broker.
   *
   * @param hdr Source message.
   */
  public synchronized void removeProvider(final MALMessageHeader hdr)
  {
    report();
    if (null != providerMap.remove(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr))))
    {
      MALBrokerImpl.LOGGER.log(Level.INFO, "Removing publisher details: {0}", hdr);
    }
    report();
  }

  /**
   * Removes a consumer from this broker.
   *
   * @param hdr Source message.
   * @param lst Subscription identifiers to remove.
   */
  public void removeConsumer(final MALMessageHeader hdr, final IdentifierList lst)
  {
    report();
    if ((null != hdr) && (null != lst) && (0 < lst.size()))
    {
      final SubscriptionSource ent = getEntry(hdr, false, null);
      if (null != ent)
      {
        ent.removeSubscriptions(lst);
        if (!ent.active())
        {
          consumerMap.remove(ent.getSignature());
        }
      }
    }
    report();
  }

  /**
   * Removes a consumer that we have lost contact with.
   *
   * @param details Details of the lost consumer.
   */
  public synchronized void removeLostConsumer(final MessageDetails details)
  {
    report();
    if (null != details)
    {
      final SubscriptionSource ent = getEntry(details);
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

  /**
   * Creates a broker implementation specific subscription source.
   *
   * @param hdr Source message header.
   * @param binding
   * @return The new subscription source object.
   */
  protected abstract SubscriptionSource createEntry(final MALMessageHeader hdr, final MALBrokerBindingImpl binding);

  private synchronized void report()
  {
    MALBrokerImpl.LOGGER.fine("START REPORT");

    for (ProviderDetails subscriptionSource : providerMap.values())
    {
      subscriptionSource.report();
    }

    for (SubscriptionSource subscriptionSource : consumerMap.values())
    {
      subscriptionSource.report();
    }

    MALBrokerImpl.LOGGER.fine("END REPORT");
  }

  private static String createProviderKey(final MALMessageHeader details)
  {
    final StringBuilder buf = new StringBuilder();

    buf.append(details.getSession());
    buf.append(':');
    buf.append(details.getSessionName());
    buf.append(':');
    buf.append(details.getNetworkZone());
    buf.append(':');
    buf.append(details.getDomain());

    return buf.toString();
  }

  private SubscriptionSource getEntry(final MessageDetails details)
  {
    return consumerMap.get(details.uriFrom.getValue());
  }

  private SubscriptionSource getEntry(final MALMessageHeader hdr,
          final boolean create,
          final MALBrokerBindingImpl binding)
  {
    final String sig = hdr.getURIFrom().getValue();
    SubscriptionSource ent = consumerMap.get(sig);

    if ((null == ent) && (create))
    {
      ent = createEntry(hdr, binding);
      consumerMap.put(sig, ent);
    }

    return ent;
  }
}
