package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALInteractionMap
{
  private final java.util.Map<String, InternalOperationHandler> transMap = new java.util.TreeMap<String, InternalOperationHandler>();
  private final java.util.Map<String, Pair> resolveMap = new java.util.TreeMap<String, Pair>();

  public MALInteractionMap()
  {
  }

  public Identifier createTransaction(MALOperation operation, boolean syncOperation, byte syncStage, MALInteractionListener listener)
  {
    final Identifier oTransId = MALTransactionStore.getTransactionId();

    synchronized (transMap)
    {
      transMap.put(oTransId.getValue(), new InternalOperationHandler(operation, syncOperation, syncStage, listener));
    }

    return oTransId;
  }

  public Identifier createTransaction(MALOperation operation, boolean syncOperation, byte syncStage, MALPublishInteractionListener listener)
  {
    final Identifier oTransId = MALTransactionStore.getTransactionId();

    synchronized (transMap)
    {
      transMap.put(oTransId.getValue(), new InternalOperationHandler(operation, syncOperation, syncStage, new MALInteractionListenerPublishAdapter(listener)));
    }

    return oTransId;
  }

  public MALMessage waitForResponse(Identifier _transId)
  {
    InternalOperationHandler handler = null;
    final String id = _transId.getValue();

    synchronized (transMap)
    {
      if (transMap.containsKey(id))
      {
        handler = transMap.get(id);
      }
      else
      {
        System.out.println("ERROR: **** No key found in service maps to wait for response! " + id);
      }
    }

    MALMessage retVal = null;

    // do the wait
    if (null != handler)
    {
      synchronized (handler.lock)
      {
        while (false == handler.lock.getLock())
        {
          try
          {
            handler.lock.wait();
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
        }
      }

      // must have value now
      retVal = handler.getResult();

      // delete entry from trans map
      synchronized (transMap)
      {
        if (handler.finished())
        {
          System.out.println("INFO: A Removing handler from service maps: " + id);
          transMap.remove(id);
        }
      }
    }

    return retVal;
  }

  public void handleStage(MALMessage msg)
  {
    final String id = msg.getHeader().getTransactionId().getValue();
    InternalOperationHandler handler = null;

    synchronized (transMap)
    {
      if (transMap.containsKey(id))
      {
        handler = transMap.get(id);
      }
      else
      {
        System.out.println("ERROR: **** No key found in service maps to get listener! " + id);
      }
    }

    if (null != handler)
    {
      handler.handleStage(msg);

      // delete entry from trans map
      if (handler.finished())
      {
        synchronized (transMap)
        {
          System.out.println("INFO: B Removing handler from service maps: " + id);
          transMap.remove(id);
        }
      }
    }
  }

  public Identifier addTransactionSource(URI urlFrom, Identifier transactionId)
  {
    final Identifier oTransId = MALTransactionStore.getTransactionId();

    synchronized (resolveMap)
    {
      resolveMap.put(oTransId.getValue(), new Pair(urlFrom, transactionId));
    }

    return oTransId;
  }

  public Pair resolveTransactionSource(Identifier transactionId)
  {
    synchronized (resolveMap)
    {
      if (resolveMap.containsKey(transactionId.getValue()))
      {
        return resolveMap.get(transactionId.getValue());
      }
    }

    return null;
  }

  private static final class InternalOperationHandler
  {
    private static final class BooleanLock
    {
      private boolean lock = false;

      public synchronized boolean getLock()
      {
        return lock;
      }

      public synchronized void setLock()
      {
        lock = true;
      }
    }
    public final MALOperation operation;
    public final boolean syncOperation;
    public final MALInteractionListener listener;
    public final BooleanLock lock = new BooleanLock();
    private MALMessage result = null;
    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    public InternalOperationHandler(MALOperation operation, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      this.operation = operation;
      this.syncOperation = syncOperation;
      this.listener = listener;

      switch (operation.getInteractionType().getOrdinal())
      {
        case InteractionType._SUBMIT_INDEX:
        {
          receivedResponse = true;
          break;
        }
        case InteractionType._REQUEST_INDEX:
        {
          receivedAck = true;
          break;
        }
        case InteractionType._PUBSUB_INDEX:
        {
          switch (stage)
          {
            case MALPubSubOperation._REGISTER_STAGE:
            case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
            case MALPubSubOperation._DEREGISTER_STAGE:
            case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
            {
              receivedResponse = true;
            }
          }
          break;
        }
      }
    }

    private void signalResponse(MALMessage msg)
    {
      result = msg;

      // do the wait
      synchronized (lock)
      {
        lock.setLock();
        lock.notifyAll();
      }
    }

    public void handleStage(MALMessage msg)
    {
      final int interactionType = msg.getHeader().getInteractionType().getOrdinal();
      final int interactionStage = msg.getHeader().getInteractionStage().intValue();

      try
      {
        switch (interactionType)
        {
          case InteractionType._SUBMIT_INDEX:
          {
            if (interactionStage == MALSubmitOperation._SUBMIT_ACK_STAGE)
            {
              if (syncOperation)
              {
                signalResponse(msg);
              }
              else
              {
                receivedAck = true;
                listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
              }
            }
            break;
          }
          case InteractionType._REQUEST_INDEX:
          {
            if (interactionStage == MALRequestOperation._REQUEST_RESPONSE_STAGE)
            {
              if (syncOperation)
              {
                signalResponse(msg);
              }
              else
              {
                receivedResponse = true;
                listener.responseReceived(operation, msg.getHeader(), msg.getBody());
              }
            }
            break;
          }
          case InteractionType._INVOKE_INDEX:
          {
            switch (interactionStage)
            {
              case MALInvokeOperation._INVOKE_ACK_STAGE:
              {
                if (syncOperation)
                {
                  signalResponse(msg);
                }
                else
                {
                  receivedAck = true;
                  listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
                }
                break;
              }
              case MALInvokeOperation._INVOKE_RESPONSE_STAGE:
              {
                receivedResponse = true;
                listener.responseReceived(operation, msg.getHeader(), msg.getBody());
                break;
              }
            }
            break;
          }
          case InteractionType._PROGRESS_INDEX:
          {
            switch (interactionStage)
            {
              case MALProgressOperation._PROGRESS_ACK_STAGE:
              {
                if (syncOperation)
                {
                  signalResponse(msg);
                }
                else
                {
                  receivedAck = true;
                  listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
                }
                break;
              }
              case MALProgressOperation._PROGRESS_UPDATE_STAGE:
              {
                listener.updateReceived(operation, msg.getHeader(), msg.getBody());
                break;
              }
              case MALProgressOperation._PROGRESS_RESPONSE_STAGE:
              {
                receivedResponse = true;
                listener.responseReceived(operation, msg.getHeader(), msg.getBody());
                break;
              }
            }
            break;
          }
          case InteractionType._PUBSUB_INDEX:
          {
            switch (interactionStage)
            {
              case MALPubSubOperation._REGISTER_ACK_STAGE:
              case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
              case MALPubSubOperation._DEREGISTER_ACK_STAGE:
              case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
              {
                if (syncOperation)
                {
                  signalResponse(msg);
                }
                else
                {
                  receivedAck = true;
                  if(msg.getHeader().isError())
                  {
                    listener.errorReceived(operation, msg.getHeader(), (StandardError)msg.getBody());
                  }
                  else
                  {
                    listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
                  }
                }
                break;
              }
            }
            break;
          }
        }
      }
      catch (MALException ex)
      {
        // nothing we can do with this
        ex.printStackTrace();
      }

    }

    public MALMessage getResult()
    {
      switch (operation.getInteractionType().getOrdinal())
      {
        case InteractionType._SUBMIT_INDEX:
        case InteractionType._INVOKE_INDEX:
        case InteractionType._PROGRESS_INDEX:
        case InteractionType._PUBSUB_INDEX:
        {
          receivedAck = true;
          break;
        }
        case InteractionType._REQUEST_INDEX:
        {
          receivedResponse = true;
          break;
        }
      }

      return result;
    }

    public boolean finished()
    {
      return receivedResponse && receivedAck;
    }
  }

  private final static class MALInteractionListenerPublishAdapter implements MALInteractionListener
  {
    private final MALPublishInteractionListener delegate;

    public MALInteractionListenerPublishAdapter(MALPublishInteractionListener delegate)
    {
      this.delegate = delegate;
    }

    @Override
    public void acknowledgementReceived(MALOperation op, MessageHeader msgHeader, Element result) throws MALException
    {
      delegate.acknowledgementReceived(msgHeader);
    }

    @Override
    public void errorReceived(MALOperation op, MessageHeader msgHeader, StandardError error) throws MALException
    {
      delegate.errorReceived(msgHeader, error);
    }

    @Override
    public void notifyReceived(MALOperation op, MessageHeader msgHeader, SubscriptionUpdate subscriptionUpdate) throws MALException
    {
    }

    @Override
    public void responseReceived(MALOperation op, MessageHeader msgHeader, Element result) throws MALException
    {
    }

    @Override
    public void updateReceived(MALOperation op, MessageHeader msgHeader, Element update) throws MALException
    {
    }
  }
}
