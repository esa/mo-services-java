package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.impl.broker.MALBroker;
import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MALFactory;
import org.ccsds.moims.smc.mal.api.MALInvokeOperation;
import org.ccsds.moims.smc.mal.api.MALOperation;
import org.ccsds.moims.smc.mal.api.MALProgressOperation;
import org.ccsds.moims.smc.mal.api.MALPubSubOperation;
import org.ccsds.moims.smc.mal.api.MALRequestOperation;
import org.ccsds.moims.smc.mal.api.MALSubmitOperation;
import org.ccsds.moims.smc.mal.api.consumer.MALInteractionListener;
import org.ccsds.moims.smc.mal.api.provider.MALInteraction;
import org.ccsds.moims.smc.mal.api.provider.MALInteractionHandler;
import org.ccsds.moims.smc.mal.api.provider.MALInvoke;
import org.ccsds.moims.smc.mal.api.provider.MALProgress;
import org.ccsds.moims.smc.mal.api.provider.MALRequest;
import org.ccsds.moims.smc.mal.api.provider.MALSubmit;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALInteractionType;
import org.ccsds.moims.smc.mal.api.structures.MALString;
import org.ccsds.moims.smc.mal.api.structures.MALSubscription;
import org.ccsds.moims.smc.mal.api.structures.MALSubscriptionUpdateList;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateList;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;
import org.ccsds.moims.smc.mal.api.transport.MALMessageListener;
import org.ccsds.moims.smc.mal.impl.patterns.InvokeInteractionImpl;
import org.ccsds.moims.smc.mal.impl.patterns.ProgressInteractionImpl;
import org.ccsds.moims.smc.mal.impl.patterns.RequestInteractionImpl;
import org.ccsds.moims.smc.mal.impl.patterns.SendInteractionImpl;
import org.ccsds.moims.smc.mal.impl.patterns.SubmitInteractionImpl;
import org.ccsds.moims.smc.mal.impl.profile.MALProfiler;

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

  public void onMessage(MALMessage msg, Hashtable qosProperties)
  {
    MALProfiler.instance.rcvMarkMALMessageReception(msg);
    onMessage(msg, qosProperties, null);
  }

  public void onException(MALException exc)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void onMessage(MALMessage msg, Hashtable qosProperties, MALInteractionHandler handler)
  {
    switch (msg.getHeader().getInteractionType().getOrdinal())
    {
      case MALInteractionType._SEND:
      {
        internalHandleSend(msg, handler);
        break;
      }
      case MALInteractionType._SUBMIT:
      {
        switch (msg.getHeader().getInteractionStage().getOctetValue())
        {
          case MALSubmitOperation._SUBMIT_STAGE:
          {
            internalHandleSubmit(msg, handler);
            break;
          }
          case MALSubmitOperation._SUBMIT_ACK_STAGE:
          {
            maps.signalResponse(msg);
            break;
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case MALInteractionType._REQUEST:
      {
        switch (msg.getHeader().getInteractionStage().getOctetValue())
        {
          case MALRequestOperation._REQUEST_STAGE:
          {
            internalHandleRequest(msg, handler);
            break;
          }
          case MALRequestOperation._REQUEST_RESPONSE_STAGE:
          {
            maps.signalResponse(msg);
            break;
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case MALInteractionType._INVOKE:
      {
        switch (msg.getHeader().getInteractionStage().getOctetValue())
        {
          case MALInvokeOperation._INVOKE_STAGE:
          {
            internalHandleInvoke(msg, handler);
            break;
          }
          case MALInvokeOperation._INVOKE_ACK_STAGE:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
          case MALInvokeOperation._INVOKE_RESPONSE_STAGE:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case MALInteractionType._PROGRESS:
      {
        switch (msg.getHeader().getInteractionStage().getOctetValue())
        {
          case MALProgressOperation._PROGRESS_STAGE:
          {
            internalHandleProgress(msg, handler);
            break;
          }
          case MALProgressOperation._PROGRESS_ACK_STAGE:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
          case MALProgressOperation._PROGRESS_UPDATE_STAGE:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
          case MALProgressOperation._PROGRESS_RESPONSE_STAGE:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
          default:
          {
            throw new UnsupportedOperationException("Not supported yet.");
          }
        }
        break;
      }
      case MALInteractionType._PUBSUB:
      {
        switch (msg.getHeader().getInteractionStage().getOctetValue())
        {
          case MALPubSubOperation._REGISTER_STAGE:
          {
            internalHandleRegister(msg);
            break;
          }
          case MALPubSubOperation._REGISTER_ACK_STAGE:
          {
            maps.signalResponse(msg);
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
            maps.signalResponse(msg);
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
    MALIdentifier transId = maps.addTransactionSource(msg.getHeader().getUriFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALSubmit interaction = new SubmitInteractionImpl(impl, transId, msg);
      handler.handleSubmit(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, ex);
    }
  }

  void internalHandleRequest(MALMessage msg, MALInteractionHandler handler)
  {
    MALIdentifier transId = maps.addTransactionSource(msg.getHeader().getUriFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALRequest interaction = new RequestInteractionImpl(impl, transId, msg);
      handler.handleRequest(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, ex);
    }
  }

  void internalHandleInvoke(MALMessage msg, MALInteractionHandler handler)
  {
    MALIdentifier transId = maps.addTransactionSource(msg.getHeader().getUriFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALInvoke interaction = new InvokeInteractionImpl(impl, transId, msg);
      handler.handleInvoke(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, ex);
    }
  }

  void internalHandleProgress(MALMessage msg, MALInteractionHandler handler)
  {
    MALIdentifier transId = maps.addTransactionSource(msg.getHeader().getUriFrom(), msg.getHeader().getTransactionId());

    try
    {
      MALProgress interaction = new ProgressInteractionImpl(impl, transId, msg);
      handler.handleProgress(interaction, msg.getBody());
    }
    catch (MALException ex)
    {
      impl.getSendingInterface().returnError(transId, msg, ex);
    }
  }

  void internalHandleRegister(MALMessage msg)
  {
    MALIdentifier transId = maps.addTransactionSource(msg.getHeader().getUriFrom(), msg.getHeader().getTransactionId());

    if (msg.getBody() instanceof MALSubscription)
    {
      // update register list
      brokerHandler.report();
      brokerHandler.addConsumer(msg);
      brokerHandler.report();

      // because we don't pass this upwards, we have to generate the ack
      impl.getSendingInterface().returnResponse(transId, msg, null);

    // inform subscribed listeners

    }
    else
    {
      impl.getSendingInterface().returnError(transId, msg, new MALException(MALException.BAD_ENCODING, new MALString("Body of register message must be of type Subscription")));
    }
  }

  void internalHandlePublish(MALMessage msg)
  {
    if (msg.getBody() instanceof MALUpdateList)
    {
      try
      {
        impl.getSendingInterface().returnNotify(null, msg.getHeader(), (MALUpdateList) msg.getBody());
      }
      catch (MALException ex)
      {
        System.out.println("Error generated during reception of PUBLISH message, dropping: " + ex);
      }
    }
    else
    {
      System.out.println("Error generated during reception of PUBLISH message, dropping");
    }
  }

  void internalHandleNotify(MALMessage msg)
  {
    MALInteractionListener rcv = maps.getNotifyListener(msg.getHeader().getUriTo());

    if (null != rcv)
    {
      MALOperation operation = MALFactory.lookupOperation(msg.getHeader().getArea(), msg.getHeader().getService(), msg.getHeader().getOperation());

      try
      {
        MALProfiler.instance.rcvMarkServiceMessageReception(msg);
        rcv.notifyReceived(operation, msg.getHeader(), (MALSubscriptionUpdateList) msg.getBody());
      }
      catch (MALException ex)
      {
        System.out.println("Error generated during handling of NOTIFY message, dropping: " + ex);
      }
    }
    else
    {
      System.out.println("Unknown notify consumer requested: " + msg.getHeader().getUriTo());
    }
  }

  void internalHandleDeregister(MALMessage msg)
  {
    MALIdentifier transId = maps.addTransactionSource(msg.getHeader().getUriFrom(), msg.getHeader().getTransactionId());

    if (msg.getBody() instanceof MALIdentifierList)
    {
      // update register list
      brokerHandler.report();
      brokerHandler.removeConsumer(msg);
      brokerHandler.report();

      // because we don't pass this upwards, we have to generate the ack
      impl.getSendingInterface().returnResponse(transId, msg, null);

    // inform subscribed listeners

    }
    else
    {
      impl.getSendingInterface().returnError(transId, msg, new MALException(MALException.BAD_ENCODING, new MALString("Body of deregister message must be of type IdentifierList")));
    }
  }
}