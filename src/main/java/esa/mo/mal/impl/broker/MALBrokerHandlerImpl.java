/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl.broker;

import esa.mo.mal.impl.StringPair;
import esa.mo.mal.impl.broker.NotifyMessageSet.NotifyMessage;
import esa.mo.mal.impl.broker.key.BrokerKey;
import esa.mo.mal.impl.util.MALClose;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALDeregisterBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;
import org.ccsds.moims.mo.mal.transport.MALPublishRegisterBody;
import org.ccsds.moims.mo.mal.transport.MALRegisterBody;

/**
 * Base implementation of the MALBrokerHandler class that should be extended by real broker implementations.
 */
public abstract class MALBrokerHandlerImpl extends MALClose implements MALBrokerHandler
{
  private final List<MALBrokerBindingImpl> bindings = new LinkedList<MALBrokerBindingImpl>();
  private final Map<BrokerKey, Map<StringPair, PublisherSource>> providerMap = new HashMap();
  private final Map<BrokerKey, Map<String, SubscriptionSource>> consumerMap = new HashMap();

  /**
   * Constructor.
   *
   * @param parent Parent closing class.
   */
  protected MALBrokerHandlerImpl(MALClose parent)
  {
    super(parent);
  }

  @Override
  public void malInitialize(MALBrokerBinding brokerBinding)
  {
    bindings.add((MALBrokerBindingImpl) brokerBinding);
  }

  @Override
  public void malFinalize(MALBrokerBinding brokerBinding)
  {
    bindings.remove((MALBrokerBindingImpl) brokerBinding);
  }

  @Override
  public void handleRegister(final MALInteraction interaction, final MALRegisterBody body)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    final Subscription lst = body.getSubscription();
    final BrokerKey key = new BrokerKey(hdr);

    report(key);
    if ((null != hdr) && (null != lst))
    {
      getConsumerEntry(key, hdr, true).addSubscription(hdr, lst);
    }
    report(key);
  }

  @Override
  public void handlePublishRegister(final MALInteraction interaction, final MALPublishRegisterBody body)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    final EntityKeyList providerKeyList = body.getEntityKeyList();
    final BrokerKey key = new BrokerKey(hdr);

    report(key);
    if ((null != hdr) && (null != providerKeyList))
    {
      getProviderEntry(key, hdr, true).setKeyList(hdr, providerKeyList);
    }
    report(key);
  }

  @Override
  public void handlePublish(final MALInteraction interaction, final MALPublishBody body)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    final BrokerKey key = new BrokerKey(hdr);
    final java.util.List<NotifyMessageSet> notifyList = createNotify(key, hdr, body);

    if (!notifyList.isEmpty())
    {
      for (NotifyMessageSet notifyMessageSet : notifyList)
      {
        MALBrokerBinding binding = getBinding(notifyMessageSet.details.uriTo.getValue());

        if (null != binding)
        {
          for (NotifyMessage notifyMessage : notifyMessageSet.messages)
          {
            binding.sendNotify(notifyMessage.area,
                    notifyMessage.service,
                    notifyMessage.operation,
                    notifyMessage.version,
                    notifyMessageSet.details.uriTo,
                    notifyMessageSet.details.transactionId,
                    notifyMessage.domain,
                    notifyMessage.networkZone,
                    notifyMessageSet.details.sessionType,
                    notifyMessageSet.details.sessionName,
                    notifyMessageSet.details.qosLevel,
                    notifyMessageSet.details.qosProps,
                    notifyMessageSet.details.priority,
                    notifyMessage.subscriptionId,
                    notifyMessage.updateHeaderList,
                    notifyMessage.updateList);
          }
        }
        else
        {
          MALBrokerImpl.LOGGER.log(Level.WARNING, "Unable to find consumer for NOTIFY : {0}", notifyMessageSet.details.uriTo.getValue());
        }
      }
    }
  }

  @Override
  public void handleDeregister(final MALInteraction interaction, final MALDeregisterBody body)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    final IdentifierList lst = body.getIdentifierList();
    final BrokerKey key = new BrokerKey(hdr);

    report(key);
    if ((null != hdr) && (null != lst) && !lst.isEmpty())
    {
      final SubscriptionSource ent = getConsumerEntry(key, hdr, false);
      if (null != ent)
      {
        ent.removeSubscriptions(lst);
        if (!ent.active())
        {
          final Map<String, SubscriptionSource> rv = getConsumerMap(key);
          rv.remove(ent.getSignature());

          if (rv.isEmpty())
          {
            consumerMap.remove(key);
          }
        }
      }
    }
    report(key);
  }

  @Override
  public void handlePublishDeregister(final MALInteraction interaction)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    final BrokerKey key = new BrokerKey(hdr);

    report(key);
    final Map<StringPair, PublisherSource> rv = getProviderMap(key);
    if (null != rv.remove(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr))))
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "Removing publisher details: {0}", hdr);
    }

    if (rv.isEmpty())
    {
      providerMap.remove(key);
    }
    report(key);
  }

  /**
   * Returns the QoS used when contacting the provider.
   *
   * @param hdr The supplied header message.
   * @return The required QoS level.
   */
  public QoSLevel getProviderQoSLevel(final MALMessageHeader hdr)
  {
    final BrokerKey key = new BrokerKey(hdr);
    final PublisherSource details = getProviderEntry(key, hdr, false);

    if (null != details)
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "Getting publisher QoS details: {0}", hdr);
      return details.getQosLevel();
    }

    return QoSLevel.BESTEFFORT;
  }

  private MALBrokerBinding getBinding(String uriTo)
  {
    for (MALBrokerBindingImpl binding : bindings)
    {
      if (binding.hasSubscriber(uriTo))
      {
        return binding;
      }
    }

    return null;
  }

  private synchronized java.util.List<NotifyMessageSet> createNotify(final BrokerKey key, final MALMessageHeader hdr,
          final MALPublishBody publishBody) throws MALInteractionException, MALException
  {
    MALBrokerImpl.LOGGER.fine("Checking provider");
    final PublisherSource details = getProviderEntry(key, hdr, false);

    if (null == details)
    {
      MALBrokerImpl.LOGGER.warning("Provider not known");
      throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    }

    final UpdateHeaderList hl = publishBody.getUpdateHeaderList();
    details.checkPublish(hdr, hl);

    final List<NotifyMessageSet> lst = new LinkedList<NotifyMessageSet>();

    if (hl != null)
    {
      final Map<String, SubscriptionSource> rv = getConsumerMap(key);
      for (Map.Entry<String, SubscriptionSource> entry : rv.entrySet())
      {
        entry.getValue().populateNotifyList(hdr, lst, hl, publishBody);
      }
    }

    return lst;
  }

  private synchronized void report(final BrokerKey key)
  {
    MALBrokerImpl.LOGGER.fine("START REPORT");

    for (PublisherSource subscriptionSource : getProviderMap(key).values())
    {
      subscriptionSource.report();
    }

    for (SubscriptionSource subscriptionSource : getConsumerMap(key).values())
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

  private Map<String, SubscriptionSource> getConsumerMap(final BrokerKey key)
  {
    Map<String, SubscriptionSource> rv = consumerMap.get(key);

    if (null == rv)
    {
      rv = new HashMap();
      consumerMap.put(key, rv);
    }

    return rv;
  }

  private SubscriptionSource getConsumerEntry(final BrokerKey key, final MALMessageHeader hdr, final boolean create)
  {
    final Map<String, SubscriptionSource> rv = getConsumerMap(key);
    final String sig = hdr.getURIFrom().getValue();
    SubscriptionSource ent = rv.get(sig);

    if ((null == ent) && (create))
    {
      ent = createEntry(hdr);
      rv.put(sig, ent);
    }

    return ent;
  }

  private Map<StringPair, PublisherSource> getProviderMap(final BrokerKey key)
  {
    Map<StringPair, PublisherSource> rv = providerMap.get(key);

    if (null == rv)
    {
      rv = new HashMap();
      providerMap.put(key, rv);
    }

    return rv;
  }

  private PublisherSource getProviderEntry(final BrokerKey key, final MALMessageHeader hdr, final boolean create)
  {
    final Map<StringPair, PublisherSource> rv = getProviderMap(key);
    PublisherSource details = rv.get(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr)));

    if ((null == details) && create)
    {
      details = new PublisherSource(hdr.getURIFrom().getValue(), hdr.getQoSlevel());
      rv.put(new StringPair(hdr.getURIFrom().getValue(), createProviderKey(hdr)), details);
      MALBrokerImpl.LOGGER.log(Level.FINE, "New publisher registering: {0}", hdr);
    }

    return details;
  }

  /**
   * Creates a broker implementation specific subscription source.
   *
   * @param hdr Source message header.
   * @return The new subscription source object.
   */
  protected abstract SubscriptionSource createEntry(final MALMessageHeader hdr);
}
