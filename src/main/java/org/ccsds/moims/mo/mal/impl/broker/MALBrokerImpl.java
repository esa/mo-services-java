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

import org.ccsds.moims.mo.mal.impl.NotifyMessage;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.impl.broker.simple.SimpleBrokerHandler;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * Implementation of the MALBroker interface.
 */
public class MALBrokerImpl extends MALBrokerBaseImpl
{
  private final MessageSend sender;
  private final Map<BrokerKey, BaseBrokerHandler> brokerMap = new TreeMap<BrokerKey, BaseBrokerHandler>();

  MALBrokerImpl(MALClose parent, MessageSend sender) throws MALException
  {
    super(parent);
    
    this.sender = sender;
  }

  /**
   * Adds a consumer to this broker.
   * @param hdr Source message.
   * @param body Consumer subscription.
   * @param binding Broker binding.
   */
  public void internalHandleRegister(MALInteraction interaction, MALRegisterBody body, MALBrokerBindingImpl binding) throws MALInteractionException, MALException
  {
    MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).addConsumer(hdr, body.getSubscription(), binding);
  }

  public void handleRegister(MALInteraction interaction, MALRegisterBody body) throws MALInteractionException, MALException
  {
    throw new UnsupportedOperationException("This should never be called!!!!");
  }

  /**
   * Adds a provider to this broker.
   * @param hdr Source message.
   * @param body Provider entity key list.
   */
  public void handlePublishRegister(MALInteraction interaction, MALPublishRegisterBody body) throws MALInteractionException, MALException
  {
    MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).addProvider(hdr, body.getEntityKeyList());
  }

  /**
   * Returns the QoS level used for a provider.
   * @param hdr Source message.
   * @return The QoSLevel.
   */
  public QoSLevel getProviderQoSLevel(MALMessageHeader hdr)
  {
    return getHandler(new BrokerKey(hdr)).getProviderQoSLevel(hdr);
  }

  /**
   * Removes a consumer from this broker for a set of subscriptions.
   * @param hdr Source Message.
   * @param ids Subscription ids to remove.
   */
  public void handleDeregister(MALInteraction interaction, MALDeregisterBody body) throws MALInteractionException, MALException
  {
    MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).removeConsumer(hdr, body.getIdentifierList());
  }

  /**
   * Removes a consumer that we have lost contact with.
   * @param hdr Source message.
   */
  public void removeLostConsumer(MessageDetails details)
  {
    getHandler(new BrokerKey(details)).removeLostConsumer(details);
  }

  /**
   * Removes a provider from this Broker.
   * @param hdr Source Message.
   */

  public void handlePublishDeregister(MALInteraction interaction) throws MALInteractionException, MALException
  {
    MALMessageHeader hdr = interaction.getMessageHeader();
    getHandler(new BrokerKey(hdr)).removeProvider(hdr);
  }

  /**
   * Publishes a set of updates.
   * @param hdr Source Message.
   * @param updateList The update list.
   * @throws MALException On error.
   */
  public void handlePublish(MALInteraction interaction, MALPublishBody body) throws MALInteractionException, MALException
  {
    MALMessageHeader hdr = interaction.getMessageHeader();
    java.util.List<BrokerMessage> msgList = getHandler(new BrokerKey(hdr)).createNotify(hdr, body);

    if (!msgList.isEmpty())
    {
      for (BrokerMessage brokerMessage : msgList)
      {
        for (NotifyMessage notifyMessage : brokerMessage.msgs)
        {
          try
          {
            // send it out
            sender.onewayPublish(notifyMessage);
          }
          catch (MALException ex)
          {
            // with the exception being thrown we assume that there is a problem with this consumer so remove
            //  them from the observe manager
            Logging.logMessage("ERROR: Error with notify consumer, removing from list : "
                    + notifyMessage.details.uriTo);
            removeLostConsumer(notifyMessage.details);

            // TODO: notify local provider
          }
        }
      }
    }
  }

  private BaseBrokerHandler getHandler(BrokerKey key)
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
    String clsName = System.getProperty("org.ccsds.moims.mo.mal.broker.class", SimpleBrokerHandler.class.getName());

    BaseBrokerHandler broker = null;
    try
    {
      Class cls = Thread.currentThread().getContextClassLoader().loadClass(clsName);

      broker = (BaseBrokerHandler) cls.newInstance();
      Logging.logMessage("INFO: Creating internal MAL Broker handler: " + cls.getSimpleName());
    }
    catch (ClassNotFoundException ex)
    {
      Logging.logMessage("WARN: Unable to find MAL Broker handler class: " + clsName);
    }
    catch (InstantiationException ex)
    {
      Logging.logMessage("WARN: Unable to instantiate MAL Broker handler: " + clsName);
    }
    catch (IllegalAccessException ex)
    {
      Logging.logMessage("WARN: IllegalAccessException when instantiating MAL Broker handler class: " + clsName);
    }

    if (null == broker)
    {
      broker = new SimpleBrokerHandler();
      Logging.logMessage("INFO: Creating internal MAL Broker handler: SimpleBrokerHandler");
    }

    return broker;
  }
}
