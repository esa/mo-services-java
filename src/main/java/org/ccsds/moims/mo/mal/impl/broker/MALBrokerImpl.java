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

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.impl.broker.simple.SimpleBrokerHandler;
import org.ccsds.moims.mo.mal.impl.patterns.PubSubInteractionImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * Implementation of the MALBroker interface.
 */
public class MALBrokerImpl extends MALBrokerBaseImpl
{
  /**
   * Logger
   */
  public static final java.util.logging.Logger LOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.impl.broker");
  private final MessageSend sender;
  private final Map<BrokerKey, BaseBrokerHandler> brokerMap = new TreeMap<BrokerKey, BaseBrokerHandler>();

  MALBrokerImpl(final MALClose parent, final MessageSend sender) throws MALException
  {
    super(parent);

    this.sender = sender;
  }

  @Override
  public void internalHandleRegister(final MALInteraction interaction,
          final MALRegisterBody body,
          final MALBrokerBindingImpl binding)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).addConsumer(hdr, body.getSubscription(), binding);
  }

  @Override
  public void handleRegister(final MALInteraction interaction, final MALRegisterBody body)
          throws MALInteractionException, MALException
  {
    throw new UnsupportedOperationException("This should never be called!!!!");
  }

  @Override
  public void handlePublishRegister(final MALInteraction interaction, final MALPublishRegisterBody body)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).addProvider(hdr, body.getEntityKeyList());
  }

  @Override
  public QoSLevel getProviderQoSLevel(final MALMessageHeader hdr)
  {
    return getHandler(new BrokerKey(hdr)).getProviderQoSLevel(hdr);
  }

  @Override
  public void handleDeregister(final MALInteraction interaction, final MALDeregisterBody body)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).removeConsumer(hdr, body.getIdentifierList());
  }

  /**
   * Removes a consumer that we have lost contact with.
   *
   * @param details The details of the lost consumer.
   */
  public void removeLostConsumer(final MALMessageHeader details)
  {
    getHandler(new BrokerKey(details)).removeLostConsumer(details);
  }

  @Override
  public void handlePublishDeregister(final MALInteraction interaction)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).removeProvider(hdr);
  }

  @Override
  public void handlePublish(final MALInteraction interaction, final MALPublishBody body)
          throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = interaction.getMessageHeader();
    final java.util.List<BrokerMessage> notifyList = getHandler(new BrokerKey(hdr)).createNotify(hdr, body);

    if (!notifyList.isEmpty())
    {
      final java.util.List<MALMessage> msgList = new LinkedList<MALMessage>();

      for (BrokerMessage brokerMessage : notifyList)
      {
        for (BrokerMessage.NotifyMessage notifyMessage : brokerMessage.msgs)
        {
          msgList.add(notifyMessage.details.endpoint.createMessage(notifyMessage.details.authenticationId,
                  notifyMessage.details.brokerUri,
                  new Time(new Date().getTime()),
                  notifyMessage.details.qosLevel,
                  notifyMessage.details.priority,
                  notifyMessage.domain,
                  notifyMessage.networkZone,
                  notifyMessage.details.sessionType,
                  notifyMessage.details.sessionName,
                  InteractionType.PUBSUB,
                  MALPubSubOperation.NOTIFY_STAGE,
                  notifyMessage.transId,
                  notifyMessage.area,
                  notifyMessage.service,
                  notifyMessage.operation,
                  notifyMessage.version,
                  Boolean.FALSE,
                  notifyMessage.details.qosProps,
                  (Object[]) notifyMessage.updates));
        }
      }

      sender.onewayMultiPublish(((PubSubInteractionImpl) interaction).getAddress().endpoint, msgList);
    }
  }

  private BaseBrokerHandler getHandler(final BrokerKey key)
  {
    BaseBrokerHandler rv = brokerMap.get(key);

    if (null == rv)
    {
      rv = createBrokerHandler();
      brokerMap.put(key, rv);
    }

    return rv;
  }

  private BaseBrokerHandler createBrokerHandler()
  {
    final String clsName = System.getProperty("org.ccsds.moims.mo.mal.broker.class",
            SimpleBrokerHandler.class.getName());

    BaseBrokerHandler broker = null;
    try
    {
      final Class cls = Thread.currentThread().getContextClassLoader().loadClass(clsName);

      broker = (BaseBrokerHandler) cls.newInstance();
      LOGGER.log(Level.INFO, "Creating internal MAL Broker handler: {0}", cls.getSimpleName());
    }
    catch (ClassNotFoundException ex)
    {
      LOGGER.log(Level.WARNING, "Unable to find MAL Broker handler class: {0}", clsName);
    }
    catch (InstantiationException ex)
    {
      LOGGER.log(Level.WARNING, "Unable to instantiate MAL Broker handler: {0}", clsName);
    }
    catch (IllegalAccessException ex)
    {
      LOGGER.log(Level.WARNING, "IllegalAccessException when instantiating MAL Broker handler class: {0}", clsName);
    }

    if (null == broker)
    {
      broker = new SimpleBrokerHandler();
      LOGGER.info("Creating internal MAL Broker handler: SimpleBrokerHandler");
    }

    return broker;
  }
}
