package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class InteractionMap
{
  private static volatile int transId = 0;
  private final java.util.Map<String, InternalOperationHandler> transMap = new java.util.TreeMap<String, InternalOperationHandler>();
  private final java.util.Map<String, Pair> resolveMap = new java.util.TreeMap<String, Pair>();

  public InteractionMap()
  {
  }

  public Identifier createTransaction(MALOperation operation, boolean syncOperation, byte syncStage, MALInteractionListener listener) throws MALException
  {
    final Identifier oTransId = getTransactionId();

    InternalOperationHandler handler = null;

    if (InteractionType._SUBMIT_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new SubmitOperationHandler(operation, syncOperation, syncStage, listener);
    }
    else if (InteractionType._REQUEST_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new RequestOperationHandler(operation, syncOperation, syncStage, listener);
    }
    else if (InteractionType._INVOKE_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new InvokeOperationHandler(operation, syncOperation, syncStage, listener);
    }
    else if (InteractionType._PROGRESS_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new ProgressOperationHandler(operation, syncOperation, syncStage, listener);
    }
    else if (InteractionType._PUBSUB_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new PubSubOperationHandler(operation, syncOperation, syncStage, listener);
    }
    else
    {
      throw new MALException(new StandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union("Pattern not supported")));
    }

    synchronized (transMap)
    {
      transMap.put(oTransId.getValue(), handler);
    }

    return oTransId;
  }

  public Identifier createTransaction(MALOperation operation, boolean syncOperation, byte syncStage, MALPublishInteractionListener listener)
  {
    final Identifier oTransId = getTransactionId();

    synchronized (transMap)
    {
      transMap.put(oTransId.getValue(), new PubSubOperationHandler(operation, syncOperation, syncStage, new InteractionListenerPublishAdapter(listener)));
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
        Logging.logMessage("ERROR: **** No key found in service maps to wait for response! " + id);
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
          Logging.logMessage("INFO: A Removing handler from service maps: " + id);
          transMap.remove(id);
        }
      }
    }

    return retVal;
  }

  public void handleStage(MALMessage msg) throws MALException
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
        Logging.logMessage("ERROR: **** No key found in service maps to get listener! " + id);
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
          Logging.logMessage("INFO: B Removing handler from service maps: " + id);
          transMap.remove(id);
        }
      }
    }
  }

  public Identifier addTransactionSource(URI urlFrom, Identifier transactionId)
  {
    final Identifier oTransId = getTransactionId();

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

  private static synchronized Identifier getTransactionId()
  {
    return new Identifier(Integer.toString(transId++));
  }

  private static abstract class InternalOperationHandler
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

    public InternalOperationHandler(MALOperation operation, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      this.operation = operation;
      this.syncOperation = syncOperation;
      this.listener = listener;
    }

    protected void signalResponse(MALMessage msg)
    {
      result = msg;

      // do the wait
      synchronized (lock)
      {
        lock.setLock();
        lock.notifyAll();
      }
    }

    public abstract void handleStage(MALMessage msg) throws MALException;

    public MALMessage getResult()
    {
      return result;
    }

    public abstract boolean finished();
  }

  private static class SubmitOperationHandler extends InternalOperationHandler
  {
    protected boolean receivedInitialStage = false;
    protected boolean takenFinalStage = false;
    protected final int interactionType;
    protected final int interactionStage;

    public SubmitOperationHandler(MALOperation operation, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      super(operation, syncOperation, stage, listener);

      this.interactionType = InteractionType._SUBMIT_INDEX;
      this.interactionStage = MALSubmitOperation._SUBMIT_ACK_STAGE;
    }

    public SubmitOperationHandler(MALOperation operation, int interactionType, int interactionStage, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      super(operation, syncOperation, stage, listener);

      this.interactionType = interactionType;
      this.interactionStage = interactionStage;
    }

    @Override
    public synchronized void handleStage(MALMessage msg) throws MALException
    {
      if (!receivedInitialStage)
      {
        try
        {
          if ((interactionType == msg.getHeader().getInteractionType().getOrdinal()) && checkStage(msg.getHeader().getInteractionStage().intValue()))
          {
            receivedInitialStage = true;
            if (syncOperation)
            {
              signalResponse(msg);
            }
            else
            {
              takenFinalStage = true;
              if (msg.getHeader().isError())
              {
                listener.errorReceived(operation, msg.getHeader(), (StandardError) msg.getBody());
              }
              else
              {
                informListener(msg);
              }
            }
          }
          else
          {
            Logging.logMessage("ERROR: Unexpected transition IP(" + InteractionType.fromInt(msg.getHeader().getInteractionType().getOrdinal()) + ") Stage(" + msg.getHeader().getInteractionStage().intValue() + ")");
            throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
          }
        }
        catch (MALException ex)
        {
          // nothing we can do with this
          ex.printStackTrace();
        }
      }
      else
      {
        Logging.logMessage("ERROR: Unexpected transition IP(" + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
        throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
      }
    }

    @Override
    public synchronized MALMessage getResult()
    {
      takenFinalStage = true;

      return super.getResult();
    }

    @Override
    public boolean finished()
    {
      return takenFinalStage;
    }

    protected boolean checkStage(int stage)
    {
      return (interactionStage == stage);
    }

    protected void informListener(MALMessage msg) throws MALException
    {
      listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
    }
  }

  private static final class RequestOperationHandler extends SubmitOperationHandler
  {
    public RequestOperationHandler(MALOperation operation, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      super(operation, InteractionType._REQUEST_INDEX, MALRequestOperation._REQUEST_RESPONSE_STAGE, syncOperation, stage, listener);
    }

    @Override
    protected void informListener(MALMessage msg) throws MALException
    {
      listener.responseReceived(operation, msg.getHeader(), msg.getBody());
    }
  }

  private static final class InvokeOperationHandler extends InternalOperationHandler
  {
    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    public InvokeOperationHandler(MALOperation operation, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      super(operation, syncOperation, stage, listener);
    }

    @Override
    public synchronized void handleStage(MALMessage msg) throws MALException
    {
      final int interactionType = msg.getHeader().getInteractionType().getOrdinal();
      final int interactionStage = msg.getHeader().getInteractionStage().intValue();

      if (!receivedAck)
      {
        if ((interactionType == InteractionType._INVOKE_INDEX) && (interactionStage == MALInvokeOperation._INVOKE_ACK_STAGE))
        {
          try
          {
            receivedAck = true;
            if (syncOperation)
            {
              signalResponse(msg);
            }
            else
            {
              if (msg.getHeader().isError())
              {
                receivedResponse = true;
                listener.errorReceived(operation, msg.getHeader(), (StandardError) msg.getBody());
              }
              else
              {
                listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
              }
            }
          }
          catch (MALException ex)
          {
            // nothing we can do with this
            ex.printStackTrace();
          }
        }
        else
        {
          Logging.logMessage("ERROR: Unexpected transition IP(" + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
          throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
        }
      }
      else if ((!receivedResponse) && (interactionType == InteractionType._INVOKE_INDEX) && (interactionStage == MALInvokeOperation._INVOKE_RESPONSE_STAGE))
      {
        try
        {
          receivedResponse = true;
          if (msg.getHeader().isError())
          {
            listener.errorReceived(operation, msg.getHeader(), (StandardError) msg.getBody());
          }
          else
          {
            listener.responseReceived(operation, msg.getHeader(), msg.getBody());
          }
        }
        catch (MALException ex)
        {
          // nothing we can do with this
          ex.printStackTrace();
        }
      }
      else
      {
        Logging.logMessage("ERROR: Unexpected transition IP(" + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
        throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
      }
    }

    @Override
    public boolean finished()
    {
      return receivedResponse;
    }
  }

  private static final class ProgressOperationHandler extends InternalOperationHandler
  {
    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    public ProgressOperationHandler(MALOperation operation, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      super(operation, syncOperation, stage, listener);
    }

    @Override
    public synchronized void handleStage(MALMessage msg) throws MALException
    {
      final int interactionType = msg.getHeader().getInteractionType().getOrdinal();
      final int interactionStage = msg.getHeader().getInteractionStage().intValue();

      if (!receivedAck)
      {
        if ((interactionType == InteractionType._PROGRESS_INDEX) && (interactionStage == MALProgressOperation._PROGRESS_ACK_STAGE))
        {
          try
          {
            receivedAck = true;
            if (syncOperation)
            {
              signalResponse(msg);
            }
            else
            {
              if (msg.getHeader().isError())
              {
                receivedResponse = true;
                listener.errorReceived(operation, msg.getHeader(), (StandardError) msg.getBody());
              }
              else
              {
                listener.acknowledgementReceived(operation, msg.getHeader(), msg.getBody());
              }
            }
          }
          catch (MALException ex)
          {
            // nothing we can do with this
            ex.printStackTrace();
          }
        }
        else
        {
          Logging.logMessage("ERROR: Unexpected transition IP(" + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
          throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
        }
      }
      else if ((!receivedResponse) && (interactionType == InteractionType._PROGRESS_INDEX) && ((interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE) || (interactionStage == MALProgressOperation._PROGRESS_RESPONSE_STAGE)))
      {
        try
        {
          if (interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE)
          {
            if (msg.getHeader().isError())
            {
              receivedResponse = true;
              listener.errorReceived(operation, msg.getHeader(), (StandardError) msg.getBody());
            }
            else
            {
              listener.updateReceived(operation, msg.getHeader(), msg.getBody());
            }
          }
          else
          {
            receivedResponse = true;
            if (msg.getHeader().isError())
            {
              listener.errorReceived(operation, msg.getHeader(), (StandardError) msg.getBody());
            }
            else
            {
              listener.responseReceived(operation, msg.getHeader(), msg.getBody());
            }
          }
        }
        catch (MALException ex)
        {
          // nothing we can do with this
          ex.printStackTrace();
        }
      }
      else
      {
        Logging.logMessage("ERROR: Unexpected transition IP(" + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
        throw new MALException(new StandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
      }
    }

    @Override
    public boolean finished()
    {
      return receivedResponse;
    }
  }

  private static final class PubSubOperationHandler extends SubmitOperationHandler
  {
    public PubSubOperationHandler(MALOperation operation, boolean syncOperation, byte stage, MALInteractionListener listener)
    {
      super(operation, InteractionType._PUBSUB_INDEX, 0, syncOperation, stage, listener);
    }

    @Override
    protected boolean checkStage(int stage)
    {
      switch (stage)
      {
        case MALPubSubOperation._REGISTER_ACK_STAGE:
        case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
        case MALPubSubOperation._DEREGISTER_ACK_STAGE:
        case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
        {
          return true;
        }
      }

      return false;
    }
  }

  private final static class InteractionListenerPublishAdapter implements MALInteractionListener
  {
    private final MALPublishInteractionListener delegate;

    public InteractionListenerPublishAdapter(MALPublishInteractionListener delegate)
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
