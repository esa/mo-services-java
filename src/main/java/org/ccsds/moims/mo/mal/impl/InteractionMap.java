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
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.*;

class InteractionMap
{
  private static volatile long transId = 0;
  private final java.util.Map<Long, InternalOperationHandler> transMap = new java.util.TreeMap<Long, InternalOperationHandler>();
  private final java.util.Map<Long, Map.Entry> resolveMap = new java.util.TreeMap<Long, Map.Entry>();

  InteractionMap()
  {
  }

  Long createTransaction(MALOperation operation,
          boolean syncOperation,
          MALInteractionListener listener) throws MALInteractionException
  {
    final Long oTransId = getTransactionId();

    InternalOperationHandler handler = null;

    if (InteractionType._SUBMIT_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new SubmitOperationHandler(operation, syncOperation, listener);
    }
    else if (InteractionType._REQUEST_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new RequestOperationHandler(operation, syncOperation, listener);
    }
    else if (InteractionType._INVOKE_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new InvokeOperationHandler(operation, syncOperation, listener);
    }
    else if (InteractionType._PROGRESS_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new ProgressOperationHandler(operation, syncOperation, listener);
    }
    else if (InteractionType._PUBSUB_INDEX == operation.getInteractionType().getOrdinal())
    {
      handler = new PubSubOperationHandler(operation, syncOperation, listener);
    }
    else
    {
      throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, new Union("Pattern not supported")));
    }

    synchronized (transMap)
    {
      transMap.put(oTransId, handler);
    }

    return oTransId;
  }

  Long createTransaction(MALOperation operation,
          boolean syncOperation,
          MALPublishInteractionListener listener)
  {
    final Long oTransId = getTransactionId();

    synchronized (transMap)
    {
      transMap.put(oTransId, new PubSubOperationHandler(operation,
              syncOperation, new InteractionListenerPublishAdapter(listener)));
    }

    return oTransId;
  }

  MALMessage waitForResponse(final Long id)
  {
    InternalOperationHandler handler = null;

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
        while (!handler.lock.getLock())
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

  void handleStage(MALMessage msg) throws MALInteractionException
  {
    final Long id = msg.getHeader().getTransactionId();
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

  Long addTransactionSource(URI urlFrom, Long transactionId)
  {
    final Long oTransId = getTransactionId();

    synchronized (resolveMap)
    {
      resolveMap.put(oTransId, new TreeMap.SimpleEntry(urlFrom, transactionId));
    }

    return oTransId;
  }

  Map.Entry resolveTransactionSource(Long transactionId)
  {
    synchronized (resolveMap)
    {
      return resolveMap.get(transactionId);
    }
  }

  static synchronized Long getTransactionId()
  {
    return transId++;
  }

  private abstract static class InternalOperationHandler
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
    protected final MALOperation operation;
    protected final boolean syncOperation;
    protected final MALInteractionListener listener;
    protected final BooleanLock lock = new BooleanLock();
    private MALMessage result = null;

    public InternalOperationHandler(MALOperation operation,
            boolean syncOperation,
            MALInteractionListener listener)
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

    public abstract void handleStage(MALMessage msg) throws MALInteractionException;

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

    public SubmitOperationHandler(MALOperation operation,
            boolean syncOperation,
            MALInteractionListener listener)
    {
      super(operation, syncOperation, listener);

      this.interactionType = InteractionType._SUBMIT_INDEX;
      this.interactionStage = MALSubmitOperation._SUBMIT_ACK_STAGE;
    }

    public SubmitOperationHandler(MALOperation operation,
            int interactionType,
            int interactionStage,
            boolean syncOperation,
            MALInteractionListener listener)
    {
      super(operation, syncOperation, listener);

      this.interactionType = interactionType;
      this.interactionStage = interactionStage;
    }

    @Override
    public synchronized void handleStage(MALMessage msg) throws MALInteractionException
    {
      if (!receivedInitialStage)
      {
        try
        {
          if ((interactionType == msg.getHeader().getInteractionType().getOrdinal())
                  && checkStage(msg.getHeader().getInteractionStage().getValue()))
          {
            receivedInitialStage = true;
            if (syncOperation)
            {
              signalResponse(msg);
            }
            else
            {
              takenFinalStage = true;
              informListener(msg);
            }
          }
          else
          {
            Logging.logMessage("ERROR: Unexpected transition IP("
                    + InteractionType.fromInt(msg.getHeader().getInteractionType().getOrdinal())
                    + ") Stage(" + msg.getHeader().getInteractionStage() + ")");
            throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
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
        Logging.logMessage("ERROR: Unexpected transition IP("
                + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
        throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
      }
    }

    @Override
    public synchronized MALMessage getResult()
    {
      takenFinalStage = true;

      return super.getResult();
    }

    @Override
    public synchronized boolean finished()
    {
      return takenFinalStage;
    }

    protected boolean checkStage(int stage)
    {
      return (interactionStage == stage);
    }

    protected void informListener(MALMessage msg) throws MALException
    {
      if (msg.getHeader().getIsErrorMessage())
      {
        listener.submitErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
      }
      else
      {
        listener.submitAckReceived(msg.getHeader(), msg.getQoSProperties());
      }
    }
  }

  private static final class RequestOperationHandler extends SubmitOperationHandler
  {
    public RequestOperationHandler(MALOperation operation,
            boolean syncOperation,
            MALInteractionListener listener)
    {
      super(operation,
              InteractionType._REQUEST_INDEX,
              MALRequestOperation._REQUEST_RESPONSE_STAGE,
              syncOperation,
              listener);
    }

    @Override
    protected void informListener(MALMessage msg) throws MALException
    {
      if (msg.getHeader().getIsErrorMessage())
      {
        listener.requestErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
      }
      else
      {
        listener.requestResponseReceived(msg.getHeader(), msg.getBody(), msg.getQoSProperties());
      }
    }
  }

  private static final class InvokeOperationHandler extends InternalOperationHandler
  {
    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    public InvokeOperationHandler(MALOperation operation,
            boolean syncOperation,
            MALInteractionListener listener)
    {
      super(operation, syncOperation, listener);
    }

    @Override
    public void handleStage(MALMessage msg) throws MALInteractionException
    {
      final int interactionType = msg.getHeader().getInteractionType().getOrdinal();
      final int interactionStage = msg.getHeader().getInteractionStage().getValue();

      boolean isAckStage = false;

      synchronized (this)
      {
        if (!receivedAck)
        {
          if ((interactionType == InteractionType._INVOKE_INDEX)
                  && (interactionStage == MALInvokeOperation._INVOKE_ACK_STAGE))
          {
            isAckStage = true;
            receivedAck = true;
            if (!syncOperation && msg.getHeader().getIsErrorMessage())
            {
              receivedResponse = true;
            }
          }
          else
          {
            Logging.logMessage("ERROR: Unexpected transition IP("
                    + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
            throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
          }
        }
        else if ((!receivedResponse) && (interactionType == InteractionType._INVOKE_INDEX)
                && (interactionStage == MALInvokeOperation._INVOKE_RESPONSE_STAGE))
        {
          receivedResponse = true;
        }
        else
        {
          Logging.logMessage("ERROR: Unexpected transition IP("
                  + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
          throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
        }
      }

      if (isAckStage)
      {
        if (syncOperation)
        {
          signalResponse(msg);
        }
        else
        {
          try
          {
            if (msg.getHeader().getIsErrorMessage())
            {
              listener.invokeAckErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
            }
            else
            {
              listener.invokeAckReceived(msg.getHeader(), msg.getBody(), msg.getQoSProperties());
            }
          }
          catch (MALException ex)
          {
            // nothing we can do with this
            ex.printStackTrace();
          }
        }
      }
      else
      {
        try
        {
          if (msg.getHeader().getIsErrorMessage())
          {
            listener.invokeResponseErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
          }
          else
          {
            listener.invokeResponseReceived(msg.getHeader(), msg.getBody(), msg.getQoSProperties());
          }
        }
        catch (MALException ex)
        {
          // nothing we can do with this
          ex.printStackTrace();
        }
      }
    }

    @Override
    public synchronized boolean finished()
    {
      return receivedResponse;
    }
  }

  private static final class ProgressOperationHandler extends InternalOperationHandler
  {
    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    public ProgressOperationHandler(MALOperation operation,
            boolean syncOperation,
            MALInteractionListener listener)
    {
      super(operation, syncOperation, listener);
    }

    @Override
    public void handleStage(MALMessage msg) throws MALInteractionException
    {
      final int interactionType = msg.getHeader().getInteractionType().getOrdinal();
      final int interactionStage = msg.getHeader().getInteractionStage().getValue();

      boolean isAckStage = false;

      synchronized (this)
      {
        if (!receivedAck)
        {
          if ((interactionType == InteractionType._PROGRESS_INDEX)
                  && (interactionStage == MALProgressOperation._PROGRESS_ACK_STAGE))
          {
            isAckStage = true;
            receivedAck = true;
            if (msg.getHeader().getIsErrorMessage())
            {
              receivedResponse = true;
            }
          }
          else
          {
            Logging.logMessage("ERROR: Unexpected transition IP("
                    + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
            throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
          }
        }
        else if ((!receivedResponse) && (interactionType == InteractionType._PROGRESS_INDEX)
                && ((interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE)
                || (interactionStage == MALProgressOperation._PROGRESS_RESPONSE_STAGE)))
        {
          if (interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE)
          {
            if (msg.getHeader().getIsErrorMessage())
            {
              receivedResponse = true;
            }
          }
          else
          {
            receivedResponse = true;
          }
        }
        else
        {
          Logging.logMessage("ERROR: Unexpected transition IP("
                  + InteractionType.fromInt(interactionType) + ") Stage(" + interactionStage + ")");
          throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
        }
      }

      if (isAckStage)
      {
        try
        {
          if (syncOperation)
          {
            signalResponse(msg);
          }
          else
          {
            if (msg.getHeader().getIsErrorMessage())
            {
              listener.progressAckErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
            }
            else
            {
              listener.progressAckReceived(msg.getHeader(), msg.getBody(), msg.getQoSProperties());
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
        try
        {
          if (interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE)
          {
            if (msg.getHeader().getIsErrorMessage())
            {
              listener.progressUpdateErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
            }
            else
            {
              listener.progressUpdateReceived(msg.getHeader(), msg.getBody(), msg.getQoSProperties());
            }
          }
          else
          {
            if (msg.getHeader().getIsErrorMessage())
            {
              listener.progressResponseErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
            }
            else
            {
              listener.progressResponseReceived(msg.getHeader(), msg.getBody(), msg.getQoSProperties());
            }
          }
        }
        catch (MALException ex)
        {
          // nothing we can do with this
          ex.printStackTrace();
        }
      }
    }

    @Override
    public synchronized boolean finished()
    {
      return receivedResponse;
    }
  }

  private static final class PubSubOperationHandler extends SubmitOperationHandler
  {
    public PubSubOperationHandler(MALOperation operation,
            boolean syncOperation,
            MALInteractionListener listener)
    {
      super(operation, InteractionType._PUBSUB_INDEX, 0, syncOperation, listener);
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

    @Override
    protected void informListener(MALMessage msg) throws MALException
    {
      if (msg.getHeader().getIsErrorMessage())
      {
        listener.registerErrorReceived(msg.getHeader(), (MALErrorBody) msg.getBody(), msg.getQoSProperties());
      }
      else
      {
        if ((MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE == msg.getHeader().getInteractionStage().getValue())
                || (MALPubSubOperation._REGISTER_ACK_STAGE == msg.getHeader().getInteractionStage().getValue()))
        {
          listener.registerAckReceived(msg.getHeader(), msg.getQoSProperties());
        }
        else
        {
          listener.deregisterAckReceived(msg.getHeader(), msg.getQoSProperties());
        }
      }
    }
  }

  private static final class InteractionListenerPublishAdapter implements MALInteractionListener
  {
    private final MALPublishInteractionListener delegate;

    public InteractionListenerPublishAdapter(MALPublishInteractionListener delegate)
    {
      this.delegate = delegate;
    }

    public void registerAckReceived(MALMessageHeader header, Map qosProperties) throws MALException
    {
      delegate.publishRegisterAckReceived(header, qosProperties);
    }

    public void registerErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
      delegate.publishRegisterErrorReceived(header, body, qosProperties);
    }

    public void deregisterAckReceived(MALMessageHeader header, Map qosProperties) throws MALException
    {
      delegate.publishDeregisterAckReceived(header, qosProperties);
    }

    public void invokeAckErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void invokeAckReceived(MALMessageHeader header, MALMessageBody body, Map qosProperties) throws MALException
    {
    }

    public void invokeResponseErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void invokeResponseReceived(MALMessageHeader header, MALMessageBody body, Map qosProperties) throws MALException
    {
    }

    public void notifyErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void notifyReceived(MALMessageHeader header, MALNotifyBody body, Map qosProperties) throws MALException
    {
    }

    public void progressAckErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void progressAckReceived(MALMessageHeader header, MALMessageBody body, Map qosProperties) throws MALException
    {
    }

    public void progressResponseErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void progressResponseReceived(MALMessageHeader header, MALMessageBody body, Map qosProperties) throws MALException
    {
    }

    public void progressUpdateErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void progressUpdateReceived(MALMessageHeader header, MALMessageBody body, Map qosProperties) throws MALException
    {
    }

    public void requestErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }

    public void requestResponseReceived(MALMessageHeader header, MALMessageBody body, Map qosProperties) throws MALException
    {
    }

    public void submitAckReceived(MALMessageHeader header, Map qosProperties) throws MALException
    {
    }

    public void submitErrorReceived(MALMessageHeader header, MALErrorBody body, Map qosProperties) throws MALException
    {
    }
  }
}
