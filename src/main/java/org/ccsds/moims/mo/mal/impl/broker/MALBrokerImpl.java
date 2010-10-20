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

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.impl.broker.simple.SimpleBrokerHandler;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Implementation of the MALBroker interface.
 */
public class MALBrokerImpl extends MALClose implements MALBroker
{
  private final MALBrokerBindingImpl[] bindings = new MALBrokerBindingImpl[0];
  private final Map<BrokerKey, BaseBrokerHandler> brokerMap = new TreeMap<BrokerKey, BaseBrokerHandler>();

  MALBrokerImpl(MALClose parent) throws MALException
  {
    super(parent);
  }

  @Override
  public MALBrokerBinding[] getBindings()
  {
    return bindings;
  }

  @Override
  public synchronized void activate() throws MALException
  {
    for (int i = 0; i < bindings.length; i++)
    {
      bindings[i].activate();
    }
  }

  /**
   * Adds a consumer to this broker.
   * @param hdr Source message.
   * @param body Consumer subscription.
   * @param binding Broker binding.
   */
  public void addConsumer(MessageHeader hdr, Subscription body, MALBrokerBindingImpl binding)
  {
    getHandler(hdr).addConsumer(hdr, body, binding);
  }

  /**
   * Adds a provider to this broker.
   * @param hdr Source message.
   * @param body Provider entity key list.
   */
  public void addProvider(MessageHeader hdr, EntityKeyList body)
  {
    getHandler(hdr).addProvider(hdr, body);
  }

  /**
   * Returns the QoS level used for a provider.
   * @param hdr Source message.
   * @return The QoSLevel.
   */
  public QoSLevel getProviderQoSLevel(MessageHeader hdr)
  {
    return getHandler(hdr).getProviderQoSLevel(hdr);
  }

  /**
   * Removes a consumer from this broker for a set of subscriptions.
   * @param hdr Source Message.
   * @param ids Subscription ids to remove.
   */
  public void removeConsumer(MessageHeader hdr, IdentifierList ids)
  {
    getHandler(hdr).removeConsumer(hdr, ids);
  }

  /**
   * Removes a consumer that we have lost contact with.
   * @param hdr Source message.
   */
  public void removeLostConsumer(MessageHeader hdr)
  {
    getHandler(hdr).removeLostConsumer(hdr);
  }

  /**
   * Removes a provider from this Broker.
   * @param hdr Source Message.
   */
  public void removeProvider(MessageHeader hdr)
  {
    getHandler(hdr).removeProvider(hdr);
  }

  /**
   * Publishes a set of updates.
   * @param hdr Source Message.
   * @param updateList The update list.
   * @throws MALException On error.
   */
  public void handlePublish(MessageHeader hdr, UpdateList updateList) throws MALException
  {
    java.util.List<BrokerMessage> msgList = getHandler(hdr).createNotify(hdr, updateList);

    if (!msgList.isEmpty())
    {
      for (BrokerMessage brokerMessage : msgList)
      {
        MALEndPoint endpoint = brokerMessage.binding.getEndpoint();

        for (BrokerMessage.NotifyMessage notifyMessage : brokerMessage.msgs)
        {
          try
          {
            MALMessage msg = endpoint.createMessage(notifyMessage.header, notifyMessage.updates, new Hashtable());

            // send it out
            endpoint.sendMessage(msg);
          }
          catch (MALException ex)
          {
            // with the exception being thrown we assume that there is a problem with this consumer so remove
            //  them from the observe manager
            Logging.logMessage("ERROR: Error with notify consumer, removing from list : "
                    + notifyMessage.header.getURIto());
            removeLostConsumer(notifyMessage.header);

            // TODO: notify local provider
          }
        }
      }
    }
  }

  private BaseBrokerHandler getHandler(MessageHeader hdr)
  {
    BrokerKey key = new BrokerKey(hdr);

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
