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

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.impl.broker.simple.SimpleBrokerHandler;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * Implementation of the MALBroker interface.
 */
public class MALBrokerImpl extends MALBrokerBaseImpl
{
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
  public void removeLostConsumer(final MessageDetails details)
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
    final java.util.List<BrokerMessage> msgList = getHandler(new BrokerKey(hdr)).createNotify(hdr, body);

    if (!msgList.isEmpty())
    {
      for (BrokerMessage brokerMessage : msgList)
      {
        for (BrokerMessage.NotifyMessage notifyMessage : brokerMessage.msgs)
        {
          try
          {
            // send it out
            sender.onewayPublish(notifyMessage.details,
                    notifyMessage.transId,
                    notifyMessage.domain,
                    notifyMessage.networkZone,
                    notifyMessage.area,
                    notifyMessage.service,
                    notifyMessage.operation,
                    notifyMessage.version,
                    notifyMessage.updates);
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
