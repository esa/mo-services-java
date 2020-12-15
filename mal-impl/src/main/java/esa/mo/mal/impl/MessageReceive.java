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
package esa.mo.mal.impl;

import esa.mo.mal.impl.broker.MALBrokerBindingImpl;
import esa.mo.mal.impl.patterns.InvokeInteractionImpl;
import esa.mo.mal.impl.patterns.ProgressInteractionImpl;
import esa.mo.mal.impl.patterns.PubSubInteractionImpl;
import esa.mo.mal.impl.patterns.RequestInteractionImpl;
import esa.mo.mal.impl.patterns.SendInteractionImpl;
import esa.mo.mal.impl.patterns.SubmitInteractionImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.*;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * This class is the main class for handling received messages.
 */
public class MessageReceive implements MALMessageListener
{
  private final MessageSend sender;
  private final MALAccessControl securityManager;
  private final InteractionConsumerMap icmap;
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap;
  private final Map<EndPointPair, Address> providerEndpointMap = new HashMap();
  private final InteractionPubSubMap ipsmap;

  MessageReceive(final MessageSend sender,
          final MALAccessControl securityManager,
          final InteractionConsumerMap imap,
          final InteractionPubSubMap psmap,
          final Map<String, MALBrokerBindingImpl> brokerBindingMap)
  {
    this.sender = sender;
    this.securityManager = securityManager;
    this.icmap = imap;
    this.ipsmap = psmap;
    this.brokerBindingMap = brokerBindingMap;
  }

  @Override
  public void onInternalError(final MALEndpoint callingEndpoint, final Throwable err)
  {
    MALContextFactoryImpl.LOGGER.severe("MAL Receiving ERROR!");
  }

  @Override
  public void onTransmitError(final MALEndpoint callingEndpoint,
          final MALMessageHeader srcMessageHeader,
          final MALStandardError err,
          final Map qosMap)
  {
    MALContextFactoryImpl.LOGGER.severe("MAL Receiving Transmission ERROR!");

    icmap.handleError(srcMessageHeader, err, qosMap);
  }

  @Override
  public void onMessages(final MALEndpoint callingEndpoint, final MALMessage[] msgList)
  {
    for (MALMessage msgList1 : msgList)
    {
      onMessage(callingEndpoint, msgList1);
    }
  }

  /**
   * Entry point for this class, determines what to do with the received message.
   *
   * @param callingEndpoint The endpoint that received this message.
   * @param msg The message.
   */
  @Override
  public void onMessage(final MALEndpoint callingEndpoint, MALMessage msg)
  {
    Address address = null;

    try
    {
      msg = securityManager.check(msg);

      short stage = -1;
      UOctet oStage = msg.getHeader().getInteractionStage();
      if (null != oStage)
      {
        stage = oStage.getValue();
      }

      MALContextFactoryImpl.LOGGER.fine("MAL Receiving message");

      switch (msg.getHeader().getInteractionType().getOrdinal())
      {
        case InteractionType._SEND_INDEX:
          address = lookupAddress(callingEndpoint, msg);
          internalHandleSend(msg, address);
          break;
        case InteractionType._SUBMIT_INDEX:
          switch (stage)
          {
            case MALSubmitOperation._SUBMIT_STAGE:
              address = lookupAddress(callingEndpoint, msg);
              internalHandleSubmit(msg, address);
              break;
            case MALSubmitOperation._SUBMIT_ACK_STAGE:
              icmap.handleStage(msg);
              break;
            default:
              throw new MALException("Received unexpected stage of " + stage);
          }
          break;
        case InteractionType._REQUEST_INDEX:
          switch (stage)
          {
            case MALRequestOperation._REQUEST_STAGE:
              address = lookupAddress(callingEndpoint, msg);
              internalHandleRequest(msg, address);
              break;
            case MALRequestOperation._REQUEST_RESPONSE_STAGE:
              icmap.handleStage(msg);
              break;
            default:
              throw new MALException("Received unexpected stage of " + stage);
          }
          break;
        case InteractionType._INVOKE_INDEX:
          switch (stage)
          {
            case MALInvokeOperation._INVOKE_STAGE:
              address = lookupAddress(callingEndpoint, msg);
              internalHandleInvoke(msg, address);
              break;
            case MALInvokeOperation._INVOKE_ACK_STAGE:
            case MALInvokeOperation._INVOKE_RESPONSE_STAGE:
              icmap.handleStage(msg);
              break;
            default:
              throw new MALException("Received unexpected stage of " + stage);
          }
          break;
        case InteractionType._PROGRESS_INDEX:
          switch (stage)
          {
            case MALProgressOperation._PROGRESS_STAGE:
              address = lookupAddress(callingEndpoint, msg);
              internalHandleProgress(msg, address);
              break;
            case MALProgressOperation._PROGRESS_ACK_STAGE:
            case MALProgressOperation._PROGRESS_UPDATE_STAGE:
            case MALProgressOperation._PROGRESS_RESPONSE_STAGE:
              icmap.handleStage(msg);
              break;
            default:
              throw new MALException("Received unexpected stage of " + stage);
          }
          break;
        case InteractionType._PUBSUB_INDEX:
          switch (stage)
          {
            case MALPubSubOperation._REGISTER_ACK_STAGE:
            case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
            case MALPubSubOperation._DEREGISTER_ACK_STAGE:
            case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
              icmap.handleStage(msg);
              break;
            case MALPubSubOperation._REGISTER_STAGE:
              address = lookupAddress(callingEndpoint, null);
              internalHandleRegister(msg, address);
              break;
            case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
              address = lookupAddress(callingEndpoint, null);
              internalHandlePublishRegister(msg, address);
              break;
            case MALPubSubOperation._PUBLISH_STAGE:
              address = lookupAddress(callingEndpoint, null);
              internalHandlePublish(msg, address);
              break;
            case MALPubSubOperation._NOTIFY_STAGE:
              internalHandleNotify(msg);
              break;
            case MALPubSubOperation._DEREGISTER_STAGE:
              address = lookupAddress(callingEndpoint, null);
              internalHandleDeregister(msg, address);
              break;
            case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
              address = lookupAddress(callingEndpoint, null);
              internalHandlePublishDeregister(msg, address);
              break;
            default:
              throw new MALException("Received unexpected stage of " + stage);
          }
          break;
        default:
          throw new MALException("Received unexpected interaction of "
                  + msg.getHeader().getInteractionType().getOrdinal());
      }
    }
    catch (MALInteractionException ex)
    {
      // try to determine address info if null
      if (null == address)
      {
        address = lookupAddress(callingEndpoint, msg);
      }

      final UOctet rspnInteractionStage = calculateReturnStage(msg.getHeader());

      if (null == rspnInteractionStage)
      {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Unable to return error, already a return message ({0})", ex);
      }
      else
      {
        sender.returnError(address,
                msg.getHeader(),
                rspnInteractionStage,
                ex.getStandardError());
      }
    }
    catch (MALException ex)
    {
      // try to determine address info if null
      if (null == address)
      {
        address = lookupAddress(callingEndpoint, msg);
      }

      final UOctet rspnInteractionStage = calculateReturnStage(msg.getHeader());

      if (null == rspnInteractionStage)
      {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Unable to return error, already a return message ({0})", ex);
      }
      else
      {
        sender.returnError(address, msg.getHeader(), rspnInteractionStage, ex);
      }
    }
  }

  void registerProviderEndpoint(final String localURI, final MALService service, final Address address)
  {
    final EndPointPair key = new EndPointPair(localURI, service);

    if (!providerEndpointMap.containsKey(key))
    {
      MALContextFactoryImpl.LOGGER.log(Level.FINE, "registerProviderEndpoint for {0}", key);
      providerEndpointMap.put(key, address);
    }
  }

  void deregisterProviderEndpoint(final String localURI, final MALService service)
  {
    final EndPointPair key = new EndPointPair(localURI, service);

    if (providerEndpointMap.containsKey(key))
    {
      MALContextFactoryImpl.LOGGER.log(Level.FINE, "deregisterProviderEndpoint for {0}", key);
      providerEndpointMap.remove(key);
    }
  }

  private void internalHandleSend(final MALMessage msg, final Address address) throws MALInteractionException
  {
    try
    {
      MALContextFactoryImpl.LOGGER.log(Level.FINE, "internalHandleSend for type {0}", address.handler);
      address.handler.handleSend(new SendInteractionImpl(sender, msg), msg.getBody());
    }
    catch (MALException ex)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Error generated during reception of SEND pattern, dropping: {0}", ex);
    }
  }

  private void internalHandleSubmit(final MALMessage msg, final Address address) throws MALInteractionException
  {
    SubmitInteractionImpl interaction = new SubmitInteractionImpl(sender, address, msg);

    try
    {
      try
      {
        MALContextFactoryImpl.LOGGER.log(Level.FINE, "internalHandleSubmit for {0} type {1}", new Object[]
        {
          msg.getHeader().getTransactionId(), address.handler
        });
        address.handler.handleSubmit(interaction, msg.getBody());
      }
      catch (MALInteractionException ex)
      {
        interaction.sendError(ex.getStandardError());
      }
    }
    catch (MALException ex)
    {
      sender.returnError(address,
              msg.getHeader(),
              MALSubmitOperation.SUBMIT_ACK_STAGE,
              ex);
    }
  }

  private void internalHandleRequest(final MALMessage msg, final Address address) throws MALInteractionException
  {
    RequestInteractionImpl interaction = new RequestInteractionImpl(sender, address, msg);

    try
    {
      try
      {
        MALContextFactoryImpl.LOGGER.log(Level.FINE, "internalHandleRequest for {0} type {1}", new Object[]
        {
          msg.getHeader().getTransactionId(), address.handler
        });
        address.handler.handleRequest(interaction, msg.getBody());
      }
      catch (MALInteractionException ex)
      {
        interaction.sendError(ex.getStandardError());
      }
    }
    catch (MALException ex)
    {
      sender.returnError(address,
              msg.getHeader(),
              MALRequestOperation.REQUEST_RESPONSE_STAGE,
              ex);
    }
  }

  private void internalHandleInvoke(final MALMessage msg, final Address address) throws MALInteractionException
  {
    InvokeInteractionImpl interaction = new InvokeInteractionImpl(sender, address, msg);

    try
    {
      try
      {
        MALContextFactoryImpl.LOGGER.log(Level.FINE, "internalHandleInvoke for {0} type {1}", new Object[]
        {
          msg.getHeader().getTransactionId(), address.handler
        });
        address.handler.handleInvoke(interaction, msg.getBody());
      }
      catch (MALInteractionException ex)
      {
        interaction.sendError(ex.getStandardError());
      }
    }
    catch (MALException ex)
    {
      try
      {
        interaction.sendError(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union(ex.getLocalizedMessage())));
      }
      catch (MALException noex)
      {
        // this exception cannot actually be thrown in this implmentation, therefore we can safely ignore it
      }
    }
  }

  private void internalHandleProgress(final MALMessage msg, final Address address) throws MALInteractionException
  {
    ProgressInteractionImpl interaction = new ProgressInteractionImpl(sender, address, msg);

    try
    {
      try
      {
        MALContextFactoryImpl.LOGGER.log(Level.FINE, "internalHandleProgresss for {0} type {1}", new Object[]
        {
          msg.getHeader().getTransactionId(), address.handler
        });
        address.handler.handleProgress(interaction, msg.getBody());
      }
      catch (MALInteractionException ex)
      {
        interaction.sendError(ex.getStandardError());
      }
    }
    catch (MALException ex)
    {
      try
      {
        interaction.sendError(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union(ex.getLocalizedMessage())));
      }
      catch (MALException noex)
      {
        // this exception cannot actually be thrown in this implmentation, therefore we can safely ignore it
      }
    }
  }

  private void internalHandleRegister(final MALMessage msg, final Address address)
          throws MALInteractionException, MALException
  {
    // find relevant broker
    final MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    if (null != brokerHandler)
    {
      if (msg.getBody() instanceof MALRegisterBody)
      {
        // update register list
        final MALInteraction interaction = new PubSubInteractionImpl(sender, address, msg);
        brokerHandler.addSubscriber(msg.getHeader().getURIFrom().getValue());
        brokerHandler.getBrokerImpl().getHandler().handleRegister(interaction, (MALRegisterBody) msg.getBody());

        // because we don't pass this upwards, we have to generate the ack
        sender.returnResponse(address,
                msg.getHeader(),
                msg.getHeader().getQoSlevel(),
                MALPubSubOperation.REGISTER_ACK_STAGE,
                interaction.getOperation(),
                interaction.getQoSProperties(),
                (Object[]) null);
      }
      else
      {
        sender.returnError(address,
                msg.getHeader(),
                MALPubSubOperation.REGISTER_ACK_STAGE,
                new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
                        new Union("Body of register message must be of type Subscription")));
      }
    }
    else
    {
      sender.returnError(address,
              msg.getHeader(),
              MALPubSubOperation.REGISTER_ACK_STAGE,
              new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                      new Union("Broker unknown at this address")));
    }
  }

  private void internalHandlePublishRegister(final MALMessage msg, final Address address)
          throws MALInteractionException, MALException
  {
    // find relevant broker
    final MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    if (null != brokerHandler)
    {
      if (msg.getBody() instanceof MALPublishRegisterBody)
      {
        // update register list
        final MALInteraction interaction = new PubSubInteractionImpl(sender, address, msg);
        brokerHandler.getBrokerImpl().getHandler().handlePublishRegister(interaction, (MALPublishRegisterBody) msg.getBody());

        // need to use QOSlevel and priority from original publish register
        final QoSLevel lvl = brokerHandler.getBrokerImpl().getProviderQoSLevel(msg.getHeader());

        // because we don't pass this upwards, we have to generate the ack
        sender.returnResponse(address,
                msg.getHeader(),
                lvl,
                MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE,
                interaction.getOperation(), interaction.getQoSProperties(), (Object[]) null);
      }
      else
      {
        sender.returnError(address,
                msg.getHeader(),
                MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE,
                new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
                        new Union("Body of publish register message must be of type EntityKeyList")));
      }
    }
    else
    {
      sender.returnError(address,
              msg.getHeader(),
              MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE,
              new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                      new Union("Broker unknown at this address")));
    }
  }

  private void internalHandlePublish(final MALMessage msg, final Address address) throws MALInteractionException
  {
    if (msg.getHeader().getIsErrorMessage())
    {
      if (msg.getBody() instanceof MALErrorBody)
      {
        try
        {
          final MALPublishInteractionListener list = ipsmap.getPublishListener(msg.getHeader().getURITo(),
                  msg.getHeader());

          if (null != list)
          {
            list.publishErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
          }
          else
          {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                    "Unknown publisher for PUBLISH error: {0}", msg.getHeader().getURITo());
            ipsmap.listPublishListeners();
          }
        }
        catch (MALException ex)
        {
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Exception thrown processing publish error: {0}", ex);
        }
      }
    }
    else
    {
      // find relevant broker
      final MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

      if (null != brokerHandler)
      {
        if (msg.getBody() instanceof MALPublishBody)
        {
          try
          {
            final MALInteraction interaction
                    = new PubSubInteractionImpl(sender, address, msg);
            brokerHandler.getBrokerImpl().getHandler().handlePublish(interaction, (MALPublishBody) msg.getBody());
          }
          catch (MALInteractionException ex)
          {
            sender.returnError(address,
                    msg.getHeader(),
                    MALPubSubOperation.PUBLISH_STAGE,
                    ex.getStandardError());
          }
          catch (MALException ex)
          {
            sender.returnError(address,
                    msg.getHeader(),
                    MALPubSubOperation.PUBLISH_STAGE,
                    ex);
          }
        }
        else
        {
          MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                  "Unexpected body type for PUBLISH: {0}", msg.getHeader().getURITo());
          sender.returnError(address,
                  msg.getHeader(),
                  MALPubSubOperation.PUBLISH_STAGE,
                  new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
                          new Union("Body of publish message must be of type UpdateList")));
        }
      }
      else
      {
        sender.returnError(address,
                msg.getHeader(),
                MALPubSubOperation.PUBLISH_STAGE,
                new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                        new Union("Broker unknown at this address")));
      }
    }
  }

  private void internalHandleNotify(final MALMessage msg) throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = msg.getHeader();

    if (hdr.getIsErrorMessage())
    {
      final Map<String, MALInteractionListener> lists = ipsmap.getNotifyListenersAndRemove(hdr.getURITo());

      if (null != lists)
      {
        final MALErrorBody err = (MALErrorBody) msg.getBody();
        for (Map.Entry<String, MALInteractionListener> e : lists.entrySet())
        {
          try
          {
            e.getValue().notifyErrorReceived(hdr, err, msg.getQoSProperties());
          }
          catch (MALException ex)
          {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Exception thrown processing notify error: {0}", ex);
          }
        }
      }
      else
      {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Unknown notify consumer requested: {0}", hdr.getURITo());
      }
    }
    else
    {
      final MALNotifyBody notifyBody = (MALNotifyBody) msg.getBody();
      final MALInteractionListener rcv = ipsmap.getNotifyListener(hdr.getURITo(), notifyBody.getSubscriptionId());

      if (null != rcv)
      {
        try
        {
          rcv.notifyReceived(hdr, notifyBody, msg.getQoSProperties());
        }
        catch (MALException ex)
        {
          MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                  "Error generated during handling of NOTIFY message, dropping: {0}", ex);
        }
      }
      else
      {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Unknown notify consumer requested: {0}", hdr.getURITo());
      }
    }
  }

  private void internalHandleDeregister(final MALMessage msg, final Address address) throws MALInteractionException
  {
    // find relevant broker
    final MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    if (null != brokerHandler)
    {
      try
      {
        // update register list
        final MALInteraction interaction = new PubSubInteractionImpl(sender, address, msg);
        brokerHandler.getBrokerImpl().getHandler().handleDeregister(interaction, (MALDeregisterBody) msg.getBody());
        brokerHandler.removeSubscriber(msg.getHeader().getURIFrom().getValue());

        // because we don't pass this upwards, we have to generate the ack
        sender.returnResponse(address,
                msg.getHeader(),
                msg.getHeader().getQoSlevel(),
                MALPubSubOperation.DEREGISTER_ACK_STAGE,
                interaction.getOperation(),
                interaction.getQoSProperties(),
                (Object[]) null);
      }
      catch (MALException ex)
      {
        sender.returnError(address,
                msg.getHeader(),
                MALPubSubOperation.DEREGISTER_ACK_STAGE, ex);
      }
    }
    else
    {
      sender.returnError(address,
              msg.getHeader(),
              MALPubSubOperation.DEREGISTER_ACK_STAGE,
              new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                      new Union("Broker unknown at this address")));
    }
  }

  private void internalHandlePublishDeregister(final MALMessage msg, final Address address)
          throws MALInteractionException, MALException
  {
    // find relevant broker
    final MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    if (null != brokerHandler)
    {
      // get the correct qos for the dergister
      QoSLevel lvl = brokerHandler.getBrokerImpl().getProviderQoSLevel(msg.getHeader());
      if (null == lvl)
      {
        lvl = msg.getHeader().getQoSlevel();
      }

      // update register list
      final MALInteraction interaction = new PubSubInteractionImpl(sender, address, msg);
      brokerHandler.getBrokerImpl().getHandler().handlePublishDeregister(interaction);

      // because we don't pass this upwards, we have to generate the ack
      sender.returnResponse(address,
              msg.getHeader(),
              lvl,
              MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE,
              interaction.getOperation(), interaction.getQoSProperties(), (Object[]) null);
    }
    else
    {
      sender.returnError(address,
              msg.getHeader(),
              MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE,
              new MALStandardError(MALHelper.DESTINATION_UNKNOWN_ERROR_NUMBER,
                      new Union("Broker unknown at this address")));
    }
  }

  private Address lookupAddress(final MALEndpoint callingEndpoint, final MALMessage msg)
  {
    final EndPointPair key = new EndPointPair(callingEndpoint.getURI().getValue(), msg);
    Address addr = providerEndpointMap.get(key);

    if (null == addr)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING, "lookupAddress failed to find local endpoint for {0}", new Object[]
      {
        key
      });
    }

    return addr;
  }

  private UOctet calculateReturnStage(final MALMessageHeader srcHdr)
  {
    UOctet rspnInteractionStage = null;
    final short srcInteractionStage = srcHdr.getInteractionStage().getValue();

    switch (srcHdr.getInteractionType().getOrdinal())
    {
      case InteractionType._SUBMIT_INDEX:
        if (MALSubmitOperation._SUBMIT_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALSubmitOperation.SUBMIT_ACK_STAGE;
        }
        break;
      case InteractionType._REQUEST_INDEX:
        if (MALRequestOperation._REQUEST_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALRequestOperation.REQUEST_RESPONSE_STAGE;
        }
        break;
      case InteractionType._INVOKE_INDEX:
        if (MALInvokeOperation._INVOKE_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALInvokeOperation.INVOKE_ACK_STAGE;
        }
        break;
      case InteractionType._PROGRESS_INDEX:
        if (MALProgressOperation._PROGRESS_STAGE == srcInteractionStage)
        {
          rspnInteractionStage = MALProgressOperation.PROGRESS_ACK_STAGE;
        }
        break;
      case InteractionType._PUBSUB_INDEX:
        switch (srcInteractionStage)
        {
          case MALPubSubOperation._REGISTER_STAGE:
            rspnInteractionStage = MALPubSubOperation.REGISTER_ACK_STAGE;
            break;
          case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
            rspnInteractionStage = MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE;
            break;
          case MALPubSubOperation._PUBLISH_STAGE:
            rspnInteractionStage = MALPubSubOperation.PUBLISH_STAGE;
            break;
          case MALPubSubOperation._DEREGISTER_STAGE:
            rspnInteractionStage = MALPubSubOperation.DEREGISTER_ACK_STAGE;
            break;
          case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
            rspnInteractionStage = MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE;
            break;
          default:
          // no op
        }
        break;
      default:
      // no op
    }

    return rspnInteractionStage;
  }

  private static class EndPointPair implements Comparable
  {
    private static final int HASH_VALUE = 71;
    private final String first;
    private final Long second;

    protected EndPointPair(final String localURI, final MALService service)
    {
      first = localURI;
      if (null != service)
      {
        second = (((long) service.getArea().getNumber().getValue()) << 32) + ((long) service.getNumber().getValue());
      }
      else
      {
        second = null;
      }
    }

    protected EndPointPair(final String localURI, final MALMessage msg)
    {
      first = localURI;

      if (null != msg)
      {
        second = (((long) msg.getHeader().getServiceArea().getValue()) << 32) + ((long) msg.getHeader().getService().getValue());
      }
      else
      {
        second = null;
      }
    }

    @Override
    public int compareTo(final Object other)
    {
      final EndPointPair otherPair = (EndPointPair) other;

      final int irv = this.first.compareTo(otherPair.first);

      if (0 == irv)
      {
        if (null != this.second)
        {
          if (null == otherPair.second)
          {
            return -1;
          }
          else
          {
            return this.second.compareTo(otherPair.second);
          }
        }
        else
        {
          if (null == otherPair.second)
          {
            return 0;
          }

          return -1;
        }
      }

      return irv;
    }

    @Override
    public boolean equals(final Object obj)
    {
      if (obj == null)
      {
        return false;
      }
      if (getClass() != obj.getClass())
      {
        return false;
      }
      final EndPointPair other = (EndPointPair) obj;
      if (this.first == null ? other.first != null : !this.first.equals(other.first))
      {
        return false;
      }
      return !(this.second == null ? other.second != null : !this.second.equals(other.second));
    }

    @Override
    public int hashCode()
    {
      int hash = 5;
      hash = HASH_VALUE * hash + (this.first != null ? this.first.hashCode() : 0);
      hash = HASH_VALUE * hash + (this.second != null ? this.second.hashCode() : 0);
      return hash;
    }

    @Override
    public String toString()
    {
      return "EndPointPair{" + "first=" + first + ", second=" + second + '}';
    }
  }
}
