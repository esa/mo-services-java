package org.ccsds.moims.mo.mal.impl;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALFactory;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerBindingImpl;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.patterns.InvokeInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.ProgressInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.RequestInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.SendInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.SubmitInteractionImpl;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdateList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceReceive
{
  private final MALImpl impl;
  private final MALInteractionMap imap;
  private final Map<String, MALBrokerBindingImpl> brokerBindingMap;
  private final MALPubSubMap pmap;

  public MALServiceReceive(MALImpl impl, MALInteractionMap imap, MALPubSubMap pmap, Map<String, MALBrokerBindingImpl> brokerBindingMap)
  {
    this.impl = impl;
    this.imap = imap;
    this.pmap = pmap;
    this.brokerBindingMap = brokerBindingMap;
  }

  public void internalHandleMessage(MALMessage msg, Address address)
  {
    try
    {
      msg = impl.getSecurityManager().check(msg);
      final int stage = msg.getHeader().getInteractionStage().intValue();

      Logging.logMessage("INFO: MAL Receiving message");

      switch (msg.getHeader().getInteractionType().getOrdinal())
      {
        case InteractionType._SEND_INDEX:
        {
          internalHandleSend(msg, address);
          break;
        }
        case InteractionType._SUBMIT_INDEX:
        {
          switch (stage)
          {
            case MALSubmitOperation._SUBMIT_STAGE:
            {
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
              throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, new Union("Received unexpected stage of " + stage)));
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
              throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, new Union("Received unexpected stage of " + stage)));
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
              throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, new Union("Received unexpected stage of " + stage)));
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
              throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, new Union("Received unexpected stage of " + stage)));
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
              internalHandleRegister(msg);
              break;
            }
            case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
            {
              internalHandlePublishRegister(msg);
              break;
            }
            case MALPubSubOperation._PUBLISH_STAGE:
            {
              internalHandlePublish(msg);
              break;
            }
            case MALPubSubOperation._NOTIFY_STAGE:
            {
              internalHandleNotify(msg);
              break;
            }
            case MALPubSubOperation._DEREGISTER_STAGE:
            {
              internalHandleDeregister(msg);
              break;
            }
            case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
            {
              internalHandlePublishDeregister(msg);
              break;
            }
            default:
            {
              throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, new Union("Received unexpected stage of " + stage)));
            }
          }
          break;
        }
        default:
        {
          throw new MALException(new StandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union("Received unexpected interaction of " + msg.getHeader().getInteractionType().getOrdinal())));
        }
      }
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnErrorAndCalculateStage(address, msg.getHeader().getTransactionId(), msg.getHeader(), ex.getStandardError());
    }
  }

  void internalHandleSend(MALMessage msg, Address address)
  {
    try
    {
      MALInteraction interaction = new SendInteractionImpl(impl, msg);
      address.handler.handleSend(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      Logging.logMessage("ERROR: Error generated during reception of SEND pattern, dropping: " + ex);
    }
  }

  void internalHandleSubmit(MALMessage msg, Address address)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALSubmit interaction = new SubmitInteractionImpl(impl, address, transId, msg);
      address.handler.handleSubmit(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(address, transId, msg.getHeader(), MALSubmitOperation.SUBMIT_ACK_STAGE, ex.getStandardError());
    }
  }

  void internalHandleRequest(MALMessage msg, Address address)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALRequest interaction = new RequestInteractionImpl(impl, address, transId, msg);
      address.handler.handleRequest(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(address, transId, msg.getHeader(), MALRequestOperation.REQUEST_RESPONSE_STAGE, ex.getStandardError());
    }
  }

  void internalHandleInvoke(MALMessage msg, Address address)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALInvoke interaction = new InvokeInteractionImpl(impl, address, transId, msg);
      address.handler.handleInvoke(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(address, transId, msg.getHeader(), MALInvokeOperation.INVOKE_ACK_STAGE, ex.getStandardError());
    }
  }

  void internalHandleProgress(MALMessage msg, Address address)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALProgress interaction = new ProgressInteractionImpl(impl, address, transId, msg);
      address.handler.handleProgress(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(address, transId, msg.getHeader(), MALProgressOperation.PROGRESS_ACK_STAGE, ex.getStandardError());
    }
  }

  private void internalHandleRegister(MALMessage msg)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURIto().getValue());

    if (msg.getBody() instanceof Subscription)
    {
      // update register list
      brokerHandler.getParent().addConsumer(msg.getHeader(), (Subscription) msg.getBody(), brokerHandler);

      // because we don't pass this upwards, we have to generate the ack
      impl.getSendingInterface().returnResponse(brokerHandler.getMsgAddress(), transId, msg.getHeader(), MALPubSubOperation.REGISTER_ACK_STAGE, null);

      // inform subscribed listeners

    }
    else
    {
      impl.getSendingInterface().returnError(brokerHandler.getMsgAddress(), transId, msg.getHeader(), MALPubSubOperation.REGISTER_ACK_STAGE, new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Body of register message must be of type Subscription")));
    }
  }

  private void internalHandlePublishRegister(MALMessage msg)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURIto().getValue());

    if (msg.getBody() instanceof EntityKeyList)
    {
      // update register list
      brokerHandler.getParent().addProvider(msg.getHeader(), (EntityKeyList) msg.getBody());

      // because we don't pass this upwards, we have to generate the ack
      impl.getSendingInterface().returnResponse(brokerHandler.getMsgAddress(), transId, msg.getHeader(), MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE, null);

      // inform subscribed listeners

    }
    else
    {
      impl.getSendingInterface().returnError(brokerHandler.getMsgAddress(), transId, msg.getHeader(), MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE, new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Body of publish register message must be of type EntityKeyList")));
    }
  }

  private void internalHandlePublish(MALMessage msg)
  {
    if (msg.getHeader().isError())
    {
      if (msg.getBody() instanceof StandardError)
      {
        try
        {
          MALPublishInteractionListener list = pmap.getPublishListener(msg.getHeader().getURIto(), msg.getHeader().getSessionName());

          if (null != list)
          {
            list.errorReceived(msg.getHeader(), (StandardError) msg.getBody());
          }
          else
          {
            Logging.logMessage("ERROR: Unknown publisher for PUBLISH error: " + msg.getHeader().getURIto());
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
      if (msg.getBody() instanceof UpdateList)
      {
        // find relevant broker
        MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURIto().getValue());

        try
        {
          brokerHandler.getParent().handlePublish(msg.getHeader(), (UpdateList) msg.getBody());
        }
        catch (MALException ex)
        {
          impl.getSendingInterface().returnError(brokerHandler.getMsgAddress(), msg.getHeader().getTransactionId(), msg.getHeader(), brokerHandler.getParent().getProviderQoSLevel(msg.getHeader()), MALPubSubOperation.PUBLISH_STAGE, ex.getStandardError());
        }
      }
      else
      {
        Logging.logMessage("ERROR: Unexpected body type for PUBLISH: " + msg.getHeader().getURIto());
        //impl.getSendingInterface().returnError(transId, msg.getHeader(), MALPubSubOperation.PUBLISH_STAGE, new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Body of publish message must be of type UpdateList")));
      }
    }
  }

  private void internalHandleNotify(MALMessage msg)
  {
    MALOperation operation = MALFactory.lookupOperation(msg.getHeader().getArea(), msg.getHeader().getService(), msg.getHeader().getOperation());

    if (msg.getHeader().isError())
    {
      if (msg.getBody() instanceof StandardError)
      {
        Map<String, MALInteractionListener> lists = pmap.getNotifyListenersAndRemove(msg.getHeader().getURIto());

        if (null != lists)
        {
          StandardError err = (StandardError) msg.getBody();
          for (Map.Entry<String, MALInteractionListener> e : lists.entrySet())
          {
            try
            {
              e.getValue().errorReceived(operation, msg.getHeader(), err);
            }
            catch (MALException ex)
            {
              ex.printStackTrace();
            }
          }
        }
        else
        {
          Logging.logMessage("ERROR: Unknown notify consumer requested: " + msg.getHeader().getURIto());
        }
      }
    }
    else
    {
      SubscriptionUpdateList subs = (SubscriptionUpdateList) msg.getBody();
      for (int i = 0; i < subs.size(); i++)
      {
        SubscriptionUpdate update = subs.get(i);
        MALInteractionListener rcv = pmap.getNotifyListener(msg.getHeader().getURIto(), update.getSubscriptionId());

        if (null != rcv)
        {
          try
          {
            rcv.notifyReceived(operation, msg.getHeader(), update);
          }
          catch (MALException ex)
          {
            Logging.logMessage("ERROR: Error generated during handling of NOTIFY message, dropping: " + ex);
          }
        }
        else
        {
          Logging.logMessage("ERROR: Unknown notify consumer requested: " + msg.getHeader().getURIto());
        }
      }
    }
  }

  private void internalHandleDeregister(MALMessage msg)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURIto().getValue());

    if (msg.getBody() instanceof IdentifierList)
    {
      // update register list
      brokerHandler.getParent().removeConsumer(msg.getHeader(), (IdentifierList) msg.getBody());

      // because we don't pass this upwards, we have to generate the ack
      impl.getSendingInterface().returnResponse(brokerHandler.getMsgAddress(), transId, msg.getHeader(), MALPubSubOperation.DEREGISTER_ACK_STAGE, null);

      // inform subscribed listeners

    }
    else
    {
      impl.getSendingInterface().returnError(brokerHandler.getMsgAddress(), transId, msg.getHeader(), MALPubSubOperation.DEREGISTER_ACK_STAGE, new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Body of deregister message must be of type IdentifierList")));
    }
  }

  private void internalHandlePublishDeregister(MALMessage msg)
  {
    Identifier transId = imap.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    // find relevant broker
    MALBrokerBindingImpl brokerHandler = brokerBindingMap.get(msg.getHeader().getURIto().getValue());

    // update register list
    brokerHandler.getParent().removeProvider(msg.getHeader());

    // because we don't pass this upwards, we have to generate the ack
    impl.getSendingInterface().returnResponse(brokerHandler.getMsgAddress(), transId, msg.getHeader(), MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE, null);

    // inform subscribed listeners

  }
}
