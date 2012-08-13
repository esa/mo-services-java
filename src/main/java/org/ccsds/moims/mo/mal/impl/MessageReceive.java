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
package org.ccsds.moims.mo.mal.impl;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.impl.patterns.*;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.*;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * This class is the main class for handling received messages.
 */
public class MessageReceive implements MALMessageListener
{
  private final MessageSend sender;
  private final MALAccessControl securityManager;
  private final InteractionMap imap;
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap;
  private final Map<EndPointPair, Address> providerEndpointMap = new TreeMap();
  private final PubSubMap pmap;

  MessageReceive(MessageSend sender,
          MALAccessControl securityManager,
          InteractionMap imap,
          PubSubMap pmap,
          Map<String, MALBrokerBindingImpl> brokerBindingMap)
  {
    this.sender = sender;
    this.securityManager = securityManager;
    this.imap = imap;
    this.pmap = pmap;
    this.brokerBindingMap = brokerBindingMap;
  }

  public void registerProviderEndpoint(String localName, MALService service, Address address)
  {
    EndPointPair key = new EndPointPair(localName, service);

    if (!providerEndpointMap.containsKey(key))
    {
      providerEndpointMap.put(key, address);
    }
  }

  @Override
  public void onInternalError(MALEndpoint callingEndpoint, Throwable err)
  {
    Logging.logMessage("INFO: MAL Receiving ERROR!");
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void onMessages(MALEndpoint callingEndpoint, MALMessage[] msgList)
  {
    for (int i = 0; i < msgList.length; i++)
    {
      onMessage(callingEndpoint, msgList[i]);
    }
  }

  /**
   * Entry point for this class, determines what to do with the received message.
   *
   * @param msg The message.
   */
  @Override
  public void onMessage(MALEndpoint callingEndpoint, MALMessage msg)
  {
    Address address = null;

    try
    {
      msg = securityManager.check(msg);
      final short stage = msg.getHeader().getInteractionStage().getValue();

      Logging.logMessage("INFO: MAL Receiving message");

      switch (msg.getHeader().getInteractionType().getOrdinal())
      {
        case InteractionType._SEND_INDEX:
        {
          address = lookupAddress(callingEndpoint, msg);
          internalHandleSend(msg, address);
          break;
        }
        case InteractionType._SUBMIT_INDEX:
        {
          switch (stage)
          {
            case MALSubmitOperation._SUBMIT_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandleSubmit(msg, address);
              break;
            }
            case MALSubmitOperation._SUBMIT_ACK_STAGE:
            {
              imap.handleStage(msg);
              break;
            }
            default:
            {
              throw new MALException("Received unexpected stage of " + stage);
            }
          }
          break;
        }
        case InteractionType._REQUEST_INDEX:
        {
          switch (stage)
          {
            case MALRequestOperation._REQUEST_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandleRequest(msg, address);
              break;
            }
            case MALRequestOperation._REQUEST_RESPONSE_STAGE:
            {
              imap.handleStage(msg);
              break;
            }
            default:
            {
              throw new MALException("Received unexpected stage of " + stage);
            }
          }
          break;
        }
        case InteractionType._INVOKE_INDEX:
        {
          switch (stage)
          {
            case MALInvokeOperation._INVOKE_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandleInvoke(msg, address);
              break;
            }
            case MALInvokeOperation._INVOKE_ACK_STAGE:
            case MALInvokeOperation._INVOKE_RESPONSE_STAGE:
            {
              imap.handleStage(msg);
              break;
            }
            default:
            {
              throw new MALException("Received unexpected stage of " + stage);
            }
          }
          break;
        }
        case InteractionType._PROGRESS_INDEX:
        {
          switch (stage)
          {
            case MALProgressOperation._PROGRESS_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandleProgress(msg, address);
              break;
            }
            case MALProgressOperation._PROGRESS_ACK_STAGE:
            case MALProgressOperation._PROGRESS_UPDATE_STAGE:
            case MALProgressOperation._PROGRESS_RESPONSE_STAGE:
            {
              imap.handleStage(msg);
              break;
            }
            default:
            {
              throw new MALException("Received unexpected stage of " + stage);
            }
          }
          break;
        }
        case InteractionType._PUBSUB_INDEX:
        {
          switch (stage)
          {
            case MALPubSubOperation._REGISTER_ACK_STAGE:
            case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
            case MALPubSubOperation._DEREGISTER_ACK_STAGE:
            case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
            {
              imap.handleStage(msg);
              break;
            }
            case MALPubSubOperation._REGISTER_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandleRegister(msg, address);
              break;
            }
            case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandlePublishRegister(msg, address);
              break;
            }
            case MALPubSubOperation._PUBLISH_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandlePublish(msg, address);
              break;
            }
            case MALPubSubOperation._NOTIFY_STAGE:
            {
              internalHandleNotify(msg);
              break;
            }
            case MALPubSubOperation._DEREGISTER_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandleDeregister(msg, address);
              break;
            }
            case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
            {
              address = lookupAddress(callingEndpoint, msg);
              internalHandlePublishDeregister(msg, address);
              break;
            }
            default:
            {
              throw new MALException("Received unexpected stage of " + stage);
            }
          }
          break;
        }
      }
    }
    catch (MALInteractionException ex)
    {
      // try to determine address info if null
      if (null == address)
      {
        address = lookupAddress(callingEndpoint, msg);
      }

      sender.returnErrorAndCalculateStage(address,
              msg.getHeader().getTransactionId(),
              msg.getHeader(),
              ex.getStandardError());
    }
    catch (MALException ex)
    {
      // try to determine address info if null
      if (null == address)
      {
        address = lookupAddress(callingEndpoint, msg);
      }

      sender.returnErrorAndCalculateStage(address,
              msg.getHeader().getTransactionId(),
              msg.getHeader(),
              ex);
    }
  }

  void internalHandleSend(MALMessage msg, Address address) throws MALInteractionException
  {
    try
    {
      MALInteraction interaction = new SendInteractionImpl(sender, msg);
      address.handler.handleSend(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error generated during reception of SEND pattern, dropping: " + ex);
    }
  }

  void internalHandleSubmit(MALMessage msg, Address address) throws MALInteractionException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALSubmit interaction = new SubmitInteractionImpl(sender, address, transId, msg);
      address.handler.handleSubmit(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      sender.returnError(address,
              transId,
              msg.getHeader(),
              MALSubmitOperation.SUBMIT_ACK_STAGE,
              ex);
    }
  }

  void internalHandleRequest(MALMessage msg, Address address) throws MALInteractionException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALRequest interaction = new RequestInteractionImpl(sender, address, transId, msg);
      address.handler.handleRequest(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      sender.returnError(address,
              transId,
              msg.getHeader(),
              MALRequestOperation.REQUEST_RESPONSE_STAGE,
              ex);
    }
  }

  void internalHandleInvoke(MALMessage msg, Address address) throws MALInteractionException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALInvoke interaction = new InvokeInteractionImpl(sender, address, transId, msg);
      address.handler.handleInvoke(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      sender.returnError(address,
              transId,
              msg.getHeader(),
              MALInvokeOperation.INVOKE_ACK_STAGE,
              ex);
    }
  }

  void internalHandleProgress(MALMessage msg, Address address) throws MALInteractionException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALProgress interaction = new ProgressInteractionImpl(sender, address, transId, msg);
      address.handler.handleProgress(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      sender.returnError(address,
              transId,
              msg.getHeader(),
              MALProgressOperation.PROGRESS_ACK_STAGE,
              ex);
    }
  }

  private void internalHandleRegister(MALMessage msg, Address address) throws MALInteractionException, MALException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    if (msg.getBody() instanceof MALRegisterBody)
    {
      // update register list
      MALInteraction interaction = new PubSubInteractionImpl(sender, address, transId, msg);
      brokerHandler.getBrokerImpl().internalHandleRegister(interaction, (MALRegisterBody) msg.getBody(), brokerHandler);

      // because we don't pass this upwards, we have to generate the ack
      sender.returnResponse(brokerHandler.getMsgAddress(),
              transId,
              msg.getHeader(),
              MALPubSubOperation.REGISTER_ACK_STAGE, (Object[]) null);

      // inform subscribed listeners
      // ToDo
    }
    else
    {
      sender.returnError(brokerHandler.getMsgAddress(),
              transId,
              msg.getHeader(),
              MALPubSubOperation.REGISTER_ACK_STAGE,
              new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
              new Union("Body of register message must be of type Subscription")));
    }
  }

  private void internalHandlePublishRegister(MALMessage msg, Address address) throws MALInteractionException, MALException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    if (msg.getBody() instanceof MALPublishRegisterBody)
    {
      // update register list
      MALInteraction interaction = new PubSubInteractionImpl(sender, address, transId, msg);
      brokerHandler.getBrokerImpl().handlePublishRegister(interaction, (MALPublishRegisterBody) msg.getBody());

      // need to use QOSlevel and priority from original publish register
      QoSLevel lvl = brokerHandler.getBrokerImpl().getProviderQoSLevel(msg.getHeader());
      //UInteger pri = brokerHandler.getBrokerImpl().getPriority();
      
      // because we don't pass this upwards, we have to generate the ack
      sender.returnResponse(brokerHandler.getMsgAddress(),
              transId,
              msg.getHeader(),
              lvl,
              MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE, (Object[]) null);
    }
    else
    {
      sender.returnError(brokerHandler.getMsgAddress(),
              transId,
              msg.getHeader(),
              MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE,
              new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
              new Union("Body of publish register message must be of type EntityKeyList")));
    }
  }

  private void internalHandlePublish(MALMessage msg, Address address) throws MALInteractionException
  {
    if (msg.getHeader().getIsErrorMessage())
    {
      if (msg.getBody() instanceof MALErrorBody)
      {
        try
        {
          MALPublishInteractionListener list = pmap.getPublishListener(msg.getHeader().getURITo(), msg.getHeader());

          if (null != list)
          {
            list.publishErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
          }
          else
          {
            Logging.logMessage("ERROR: Unknown publisher for PUBLISH error: " + msg.getHeader().getURITo());
            pmap.listPublishListeners();
          }
        }
        catch (MALException ex)
        {
          ex.printStackTrace();
        }
      }
    }
    else
    {
      // find relevant broker
      MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

      if (msg.getBody() instanceof MALPublishBody)
      {
        try
        {
          Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());
          MALInteraction interaction = new PubSubInteractionImpl(sender, address, transId, msg);
          brokerHandler.getBrokerImpl().handlePublish(interaction, (MALPublishBody) msg.getBody());
        }
        catch (MALInteractionException ex)
        {
          sender.returnError(brokerHandler.getMsgAddress(),
                  msg.getHeader().getTransactionId(),
                  msg.getHeader(),
                  MALPubSubOperation.PUBLISH_STAGE,
                  ex.getStandardError());
        }
        catch (MALException ex)
        {
          sender.returnError(brokerHandler.getMsgAddress(),
                  msg.getHeader().getTransactionId(),
                  msg.getHeader(),
                  MALPubSubOperation.PUBLISH_STAGE,
                  ex);
        }
      }
      else
      {
        Logging.logMessage("ERROR: Unexpected body type for PUBLISH: " + msg.getHeader().getURITo());
        sender.returnError(brokerHandler.getMsgAddress(),
                msg.getHeader().getTransactionId(),
                msg.getHeader(),
                msg.getHeader().getQoSlevel(),
                MALPubSubOperation.PUBLISH_STAGE,
                new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
                new Union("Body of publish message must be of type UpdateList")));
      }
    }
  }

  private void internalHandleNotify(MALMessage msg) throws MALInteractionException, MALException
  {
    final MALMessageHeader hdr = msg.getHeader();

    if (hdr.getIsErrorMessage())
    {
      Map<String, MALInteractionListener> lists = pmap.getNotifyListenersAndRemove(hdr.getURITo());

      if (null != lists)
      {
        MALErrorBody err = (MALErrorBody) msg.getBody();
        for (Map.Entry<String, MALInteractionListener> e : lists.entrySet())
        {
          try
          {
            e.getValue().notifyErrorReceived(hdr, err, msg.getQoSProperties());
          }
          catch (MALException ex)
          {
            ex.printStackTrace();
          }
        }
      }
      else
      {
        Logging.logMessage("ERROR: Unknown notify consumer requested: " + hdr.getURITo());
      }
    }
    else
    {
      MALNotifyBody notifyBody = (MALNotifyBody) msg.getBody();
      MALInteractionListener rcv = pmap.getNotifyListener(hdr.getURITo(), notifyBody.getSubscriptionId());

      if (null != rcv)
      {
        try
        {
          rcv.notifyReceived(hdr, notifyBody, msg.getQoSProperties());
        }
        catch (MALException ex)
        {
          Logging.logMessage("ERROR: Error generated during handling of NOTIFY message, dropping: " + ex);
        }
      }
      else
      {
        Logging.logMessage("ERROR: Unknown notify consumer requested: " + hdr.getURITo());
      }
    }
  }

  private void internalHandleDeregister(MALMessage msg, Address address) throws MALInteractionException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    try
    {
      // update register list
      MALInteraction interaction = new PubSubInteractionImpl(sender, address, transId, msg);
      brokerHandler.getBrokerImpl().handleDeregister(interaction, (MALDeregisterBody) msg.getBody());

      // because we don't pass this upwards, we have to generate the ack
      sender.returnResponse(brokerHandler.getMsgAddress(),
              transId,
              msg.getHeader(),
              MALPubSubOperation.DEREGISTER_ACK_STAGE, (Object) null);

      // inform subscribed listeners
      // ToDo
    }
    catch (MALException ex)
    {
      sender.returnError(brokerHandler.getMsgAddress(),
              transId,
              msg.getHeader(),
              MALPubSubOperation.DEREGISTER_ACK_STAGE,
              new MALStandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER,
              new Union("Body of deregister message must be of type IdentifierList")));
    }
  }

  private void internalHandlePublishDeregister(MALMessage msg, Address address) throws MALInteractionException, MALException
  {
    Long transId = imap.addTransactionSource(msg.getHeader().getURIFrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURITo().getValue());

    // get the correct qos for the dergister
    QoSLevel lvl = brokerHandler.getBrokerImpl().getProviderQoSLevel(msg.getHeader());
    if(null == lvl)
    {
      lvl = msg.getHeader().getQoSlevel();
    }
    
    // update register list
    MALInteraction interaction = new PubSubInteractionImpl(sender, address, transId, msg);
    brokerHandler.getBrokerImpl().handlePublishDeregister(interaction);
    
    // because we don't pass this upwards, we have to generate the ack
    sender.returnResponse(brokerHandler.getMsgAddress(),
            transId,
            msg.getHeader(),
            lvl,
            MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE, (Object) null);
  }

  private Address lookupAddress(MALEndpoint callingEndpoint, MALMessage msg)
  {
    EndPointPair key = new EndPointPair(callingEndpoint.getLocalName(), msg.getHeader().getService());
    Address rv = providerEndpointMap.get(key);

    return rv;
  }

  private static class EndPointPair implements Comparable
  {
    private final String first;
    private final Integer second;

    public EndPointPair(String localName, MALService service)
    {
      first = localName;
      if (null != service)
      {
        second = service.getNumber().getValue();
      }
      else
      {
        second = null;
      }
    }

    public EndPointPair(String localName, UShort service)
    {
      first = localName;
      second = service.getValue();
    }

    public int compareTo(Object other)
    {
      EndPointPair otherPair = (EndPointPair) other;

      int irv = this.first.compareTo(otherPair.first);

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
    public boolean equals(Object obj)
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
      if ((this.first == null) ? (other.first != null) : !this.first.equals(other.first))
      {
        return false;
      }
      if ((this.second == null) ? (other.second != null) : !this.second.equals(other.second))
      {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode()
    {
      int hash = 5;
      hash = 71 * hash + (this.first != null ? this.first.hashCode() : 0);
      hash = 71 * hash + (this.second != null ? this.second.hashCode() : 0);
      return hash;
    }
  }
}
