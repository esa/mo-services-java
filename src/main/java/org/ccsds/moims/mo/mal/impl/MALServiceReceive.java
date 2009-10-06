package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.broker.MALBroker;
import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MALFactory;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageListener;
import org.ccsds.moims.mo.mal.impl.patterns.InvokeInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.ProgressInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.RequestInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.SendInteractionImpl;
import org.ccsds.moims.mo.mal.impl.patterns.SubmitInteractionImpl;
import org.ccsds.moims.mo.mal.impl.profile.MALProfiler;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceReceive implements MALMessageListener
{
  private final MALImpl impl;
  private final MALServiceMaps maps;
  private final MALBroker brokerHandler;

  public MALServiceReceive(MALImpl impl, MALServiceMaps maps, MALBroker brokerHandler)
  {
    this.impl = impl;
    this.maps = maps;
    this.brokerHandler = brokerHandler;
  }

  @Override
  public void onMessages(MALMessage[] msgList)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void onMessage(MALMessage msg)
  {
    MALProfiler.instance.rcvMarkMALMessageReception(msg);

    try
    {
      msg = impl.getSecurityManager().check(msg);
    }
    catch (MALException ex)
    {
      //todo
      ex.printStackTrace();
    }

    onMessage(msg, null, null);
  }

  @Override
  public void onInternalError(StandardError err)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void onMessage(MALMessage msg, Hashtable qosProperties, MALInteractionHandler handler)
  {
    switch (msg.getHeader().getInteractionType().getOrdinal())
    {
      case InteractionType._SEND_INDEX:
      {
        internalHandleSend(msg, handler);
        break;
      }
      case InteractionType._SUBMIT_INDEX:
      {
        switch (msg.getHeader().getInteractionStage().intValue())
        {
          case MALSubmitOperation._SUBMIT_STAGE:
          {
            internalHandleSubmit(msg, handler);
            break;
          }
          case MALSubmitOperation._SUBMIT_ACK_STAGE:
          {
            maps.handleStage(msg);
            break;
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case InteractionType._REQUEST_INDEX:
      {
        switch (msg.getHeader().getInteractionStage().intValue())
        {
          case MALRequestOperation._REQUEST_STAGE:
          {
            internalHandleRequest(msg, handler);
            break;
          }
          case MALRequestOperation._REQUEST_RESPONSE_STAGE:
          {
            maps.handleStage(msg);
            break;
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case InteractionType._INVOKE_INDEX:
      {
        switch (msg.getHeader().getInteractionStage().intValue())
        {
          case MALInvokeOperation._INVOKE_STAGE:
          {
            internalHandleInvoke(msg, handler);
            break;
          }
          case MALInvokeOperation._INVOKE_ACK_STAGE:
          case MALInvokeOperation._INVOKE_RESPONSE_STAGE:
          {
            maps.handleStage(msg);
            break;
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case InteractionType._PROGRESS_INDEX:
      {
        switch (msg.getHeader().getInteractionStage().intValue())
        {
          case MALProgressOperation._PROGRESS_STAGE:
          {
            internalHandleProgress(msg, handler);
            break;
          }
          case MALProgressOperation._PROGRESS_ACK_STAGE:
          case MALProgressOperation._PROGRESS_UPDATE_STAGE:
          case MALProgressOperation._PROGRESS_RESPONSE_STAGE:
          {
            maps.handleStage(msg);
            break;
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case InteractionType._PUBSUB_INDEX:
      {
        switch (msg.getHeader().getInteractionStage().intValue())
        {
          case MALPubSubOperation._REGISTER_STAGE:
          {
            internalHandleRegister(msg);
            break;
          }
          case MALPubSubOperation._REGISTER_ACK_STAGE:
          {
            maps.handleStage(msg);
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
          case MALPubSubOperation._DEREGISTER_ACK_STAGE:
          {
            maps.handleStage(msg);
            break;
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      default:
      {
        throw new UnsupportedOperationException("Not supported yet.");
      }
    }
  }

  void internalHandleSend(MALMessage msg, MALInteractionHandler handler)
  {
    try
    {
      MALInteraction interaction = new SendInteractionImpl(impl, msg);
      handler.handleSend(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      System.out.println("Error generated during reception of SEND pattern, dropping: " + ex);
    }
  }

  void internalHandleSubmit(MALMessage msg, MALInteractionHandler handler)
  {
    Identifier transId = maps.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALSubmit interaction = new SubmitInteractionImpl(impl, transId, msg);
      handler.handleSubmit(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, MALSubmitOperation.SUBMIT_ACK_STAGE, ex.getStandardError());
    }
  }

  void internalHandleRequest(MALMessage msg, MALInteractionHandler handler)
  {
    Identifier transId = maps.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALRequest interaction = new RequestInteractionImpl(impl, transId, msg);
      handler.handleRequest(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, MALRequestOperation.REQUEST_RESPONSE_STAGE, ex.getStandardError());
    }
  }

  void internalHandleInvoke(MALMessage msg, MALInteractionHandler handler)
  {
    Identifier transId = maps.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALInvoke interaction = new InvokeInteractionImpl(impl, transId, msg);
      handler.handleInvoke(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, MALInvokeOperation.INVOKE_ACK_STAGE, ex.getStandardError());
    }
  }

  void internalHandleProgress(MALMessage msg, MALInteractionHandler handler)
  {
    Identifier transId = maps.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    try
    {
      MALProgress interaction = new ProgressInteractionImpl(impl, transId, msg);
      handler.handleProgress(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, MALProgressOperation.PROGRESS_ACK_STAGE, ex.getStandardError());
    }
  }

  void internalHandleRegister(MALMessage msg)
  {
    Identifier transId = maps.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    if (msg.getBody() instanceof Subscription)
    {
      // update register list
      brokerHandler.report();
      brokerHandler.addConsumer(msg);
      brokerHandler.report();

      // because we don't pass this upwards, we have to generate the ack
      impl.getSendingInterface().returnResponse(transId, msg, MALPubSubOperation.REGISTER_ACK_STAGE, null);

    // inform subscribed listeners

    }
    else
    {
      impl.getSendingInterface().returnError(transId, msg, MALPubSubOperation.REGISTER_ACK_STAGE, new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Body of register message must be of type Subscription")));
    }
  }

  void internalHandlePublish(MALMessage msg)
  {
    if (msg.getBody() instanceof UpdateList)
    {
      impl.getSendingInterface().returnNotify(null, msg.getHeader(), (UpdateList) msg.getBody());
    }
    else
    {
      System.out.println("Error generated during reception of PUBLISH message, dropping");
    }
  }

  void internalHandleNotify(MALMessage msg)
  {
    MALInteractionListener rcv = maps.getNotifyListener(msg.getHeader().getURIto());

    if (null != rcv)
    {
      MALOperation operation = MALFactory.lookupOperation(msg.getHeader().getArea(), msg.getHeader().getService(), msg.getHeader().getOperation());

      try
      {
        MALProfiler.instance.rcvMarkServiceMessageReception(msg);
        rcv.notifyReceived(operation, msg.getHeader(), (SubscriptionUpdate) msg.getBody());
      }
      catch (MALException ex)
      {
        System.out.println("Error generated during handling of NOTIFY message, dropping: " + ex);
      }
    }
    else
    {
      System.out.println("Unknown notify consumer requested: " + msg.getHeader().getURIto());
    }
  }

  void internalHandleDeregister(MALMessage msg)
  {
    Identifier transId = maps.addTransactionSource(msg.getHeader().getURIfrom(), msg.getHeader().getTransactionId());

    if (msg.getBody() instanceof IdentifierList)
    {
      // update register list
      brokerHandler.report();
      brokerHandler.removeConsumer(msg);
      brokerHandler.report();

      // because we don't pass this upwards, we have to generate the ack
      impl.getSendingInterface().returnResponse(transId, msg, MALPubSubOperation.DEREGISTER_ACK_STAGE, null);

    // inform subscribed listeners

    }
    else
    {
      impl.getSendingInterface().returnError(transId, msg, MALPubSubOperation.DEREGISTER_ACK_STAGE, new StandardError(MALHelper.BAD_ENCODING_ERROR_NUMBER, new Union("Body of deregister message must be of type IdentifierList")));
    }
  }
}