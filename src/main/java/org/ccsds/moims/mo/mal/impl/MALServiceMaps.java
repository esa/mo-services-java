package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceMaps
{
  private volatile int transId = 0;
  private final java.util.Map<String, InternalOperationHandler> transMap = new java.util.TreeMap<String, InternalOperationHandler>();
  private final java.util.Map<String, Pair> resolveMap = new java.util.TreeMap<String, Pair>();
//  private final java.util.Map<String, MALMessage> resultMap = new java.util.TreeMap<String, MALMessage>();
//  private final java.util.Map<String, OperationHandler> listenerMap = new java.util.TreeMap<String, OperationHandler>();
  private final java.util.Map<String, MALInteractionListener> notifyMap = new java.util.TreeMap<String, MALInteractionListener>();

  public MALServiceMaps()
  {
  }

  public void registerNotifyListener(MALMessageDetails details, MALPubSubOperation op, Subscription subscription, MALInteractionListener list)
  {
    //TODO: Not correct currently as register can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getValue();

    synchronized (notifyMap)
    {
      if (false == notifyMap.containsKey(id))
      {
        notifyMap.put(id, list);
      }
    }
  }

  public MALInteractionListener getNotifyListener(URI uri)
  {
    final String id = uri.getValue();

    synchronized (notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        return notifyMap.get(id);
      }
    }

    return null;
  }

  public void deregisterNotifyListener(MALMessageDetails details, MALPubSubOperation op, IdentifierList unsubscription)
  {
    //TODO: Not correct currently as deregister can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getValue();

    synchronized (notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        notifyMap.remove(id);
      }
    }
  }

  public Identifier createTransaction(MALOperation operation, boolean syncOperation, byte syncStage, MALInteractionListener listener)
  {
    final Identifier oTransId = new Identifier(Integer.toString(transId++));

    synchronized (transMap)
    {
      transMap.put(oTransId.getValue(), new InternalOperationHandler(operation, syncOperation, syncStage, listener));
    }

    return oTransId;
  }

//  public void registerInteractionListener(Identifier _transId, MALOperation op, MALInteractionListener listener)
//  {
//    final String id = _transId.getValue();
//
//    synchronized (transMap)
//    {
//      if (transMap.containsKey(id))
//      {
//        listenerMap.put(id, new OperationHandler(op, listener));
//      }
//      else
//      {
//        System.out.println("**** No key found in service maps to register interaction listener ! " + id);
//      }
//    }
//  }
//
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
        System.out.println("**** No key found in service maps to wait for response! " + id);
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
          System.out.println("**** A Removing handler from service maps: " + id);
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
        System.out.println("**** No key found in service maps to get listener! " + id);
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
          System.out.println("**** B Removing handler from service maps: " + id);
          transMap.remove(id);
        }
      }
    }
  }

  public Identifier addTransactionSource(URI urlFrom, Identifier transactionId)
  {
    final Identifier oTransId = new Identifier(Integer.toString(transId++));

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
          if ((stage == MALPubSubOperation._REGISTER_STAGE) || (stage == MALPubSubOperation._DEREGISTER_STAGE))
          {
            receivedResponse = true;
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
                listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
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
              case MALPubSubOperation._DEREGISTER_ACK_STAGE:
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
}
