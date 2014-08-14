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

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.*;

/**
 * The interaction map is responsible for maintaining the state of consumer initiated interactions for a MAL instance.
 * New transactions are added using the creatTransaction methods. Synchronous consumer interactions are handled by
 * calling waitForResponse, and any message received is 'handled' using the handleStage method.
 *
 * When a new interaction is created, an internal (to this class) interaction handler class is created which is
 * responsible for ensuring the correct stages are received in the correct order.
 */
class InteractionConsumerMap
{
  private final Map<Long, InternalOperationHandler> transMap
          = new TreeMap<Long, InternalOperationHandler>();

  Long createTransaction(final int interactionType,
          final boolean syncOperation,
          final MALInteractionListener listener) throws MALInteractionException
  {
    synchronized (transMap)
    {
      final Long oTransId = InteractionTransaction.getTransactionId(transMap.keySet());

      InternalOperationHandler handler = null;

      switch (interactionType)
      {
        case InteractionType._SEND_INDEX:
          // do nothing as no handler is required for SEND interaction
          break;
        case InteractionType._SUBMIT_INDEX:
          handler = new SubmitOperationHandler(syncOperation, listener);
          break;
        case InteractionType._REQUEST_INDEX:
          handler = new RequestOperationHandler(syncOperation, listener);
          break;
        case InteractionType._INVOKE_INDEX:
          handler = new InvokeOperationHandler(syncOperation, listener);
          break;
        case InteractionType._PROGRESS_INDEX:
          handler = new ProgressOperationHandler(syncOperation, listener);
          break;
        case InteractionType._PUBSUB_INDEX:
          handler = new PubSubOperationHandler(syncOperation, listener);
          break;
        default:
          throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
                  new Union("Pattern not supported")));
      }

      if (null != handler)
      {
        transMap.put(oTransId, handler);
      }

      return oTransId;
    }
  }

  void continueTransaction(final int interactionType,
          final UOctet lastInteractionStage,
          final Long oTransId,
          final MALInteractionListener listener) throws MALException, MALInteractionException
  {
    synchronized (transMap)
    {
      if (transMap.containsKey(oTransId))
      {
        throw new MALException("Transaction Id already in use and cannot be continued");
      }

      InternalOperationHandler handler = null;

      switch (interactionType)
      {
        case InteractionType._SUBMIT_INDEX:
          handler = new SubmitOperationHandler(listener);
          break;
        case InteractionType._REQUEST_INDEX:
          handler = new RequestOperationHandler(listener);
          break;
        case InteractionType._INVOKE_INDEX:
          handler = new InvokeOperationHandler(lastInteractionStage, listener);
          break;
        case InteractionType._PROGRESS_INDEX:
          handler = new ProgressOperationHandler(lastInteractionStage, listener);
          break;
        case InteractionType._PUBSUB_INDEX:
          handler = new PubSubOperationHandler(listener);
          break;
        default:
          throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER,
                  new Union("Pattern not supported")));
      }

      transMap.put(oTransId, handler);
    }
  }

  Long createTransaction(final boolean syncOperation, final MALPublishInteractionListener listener)
  {
    synchronized (transMap)
    {
      final Long oTransId = InteractionTransaction.getTransactionId(transMap.keySet());

      transMap.put(oTransId,
              new PubSubOperationHandler(syncOperation, new InteractionListenerPublishAdapter(listener)));

      return oTransId;
    }
  }

  MALMessage waitForResponse(final Long id) throws MALInteractionException
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
        MALContextFactoryImpl.LOGGER.log(Level.WARNING, "No key found in service maps to wait for response! {0}", id);
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
          catch (InterruptedException ex)
          {
            MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Interrupted waiting for handler lock ", ex);
          }
        }
      }

      // delete entry from trans map
      synchronized (transMap)
      {
        if (handler.finished())
        {
          MALContextFactoryImpl.LOGGER.log(Level.INFO, "Removing handler from service maps: {0}", id);
          transMap.remove(id);
        }
      }

      if (handler.isInError())
      {
        handler.throwException();
      }

      // must have value now
      retVal = handler.getResult();
    }

    return retVal;
  }

  void handleStage(final MALMessage msg) throws MALInteractionException
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
        MALContextFactoryImpl.LOGGER.log(Level.WARNING, "No key found in service maps to get listener! {0}", id);
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
          MALContextFactoryImpl.LOGGER.log(Level.INFO, "Removing handler from service maps: {0}", id);
          transMap.remove(id);
        }
      }
    }
  }

  void handleError(final MALMessageHeader hdr,
          final MALStandardError err,
          final Map qosMap)
  {
    final Long id = hdr.getTransactionId();
    InternalOperationHandler handler = null;

    synchronized (transMap)
    {
      if (transMap.containsKey(id))
      {
        handler = transMap.get(id);
      }
      else
      {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING, "No key found in service maps to get listener! {0}", id);
      }
    }

    if (null != handler)
    {
      handler.handleError(hdr, err, qosMap);

      // delete entry from trans map
      synchronized (transMap)
      {
        MALContextFactoryImpl.LOGGER.log(Level.INFO, "Removing handler from service maps: {0}", id);
        transMap.remove(id);
      }
    }
  }

  private abstract static class InternalOperationHandler
  {
    private static final class BooleanLock
    {
      private boolean lock = false;

      protected synchronized boolean getLock()
      {
        return lock;
      }

      protected synchronized void setLock()
      {
        lock = true;
      }
    }
    protected final boolean syncOperation;
    protected final MALInteractionListener listener;
    protected final BooleanLock lock = new BooleanLock();
    private boolean inError = false;
    private MALMessage result = null;
    private MALStandardError error = null;

    protected InternalOperationHandler(final boolean syncOperation, final MALInteractionListener listener)
    {
      this.syncOperation = syncOperation;
      this.listener = listener;
    }

    protected void signalResponse(final MALMessage msg)
    {
      result = msg;

      // do the wait
      synchronized (lock)
      {
        lock.setLock();
        lock.notifyAll();
      }
    }

    protected void signalError(final MALStandardError err)
    {
      inError = true;
      result = null;
      error = err;

      // do the wait
      synchronized (lock)
      {
        lock.setLock();
        lock.notifyAll();
      }
    }

    protected abstract void handleStage(final MALMessage msg) throws MALInteractionException;

    protected abstract void handleError(final MALMessageHeader hdr,
            final MALStandardError err,
            final Map qosMap);

    public boolean isInError()
    {
      return inError;
    }

    protected void throwException() throws MALInteractionException
    {
      throw new MALInteractionException(error);
    }

    protected MALMessage getResult()
    {
      return result;
    }

    protected abstract boolean finished();

    protected static void logUnexpectedTransitionError(final int interactionType, final int interactionStage)
    {
      MALContextFactoryImpl.LOGGER.log(Level.WARNING,
              "Unexpected transition IP({0}) Stage({1})", new Object[]
              {
                InteractionType.fromOrdinal(interactionType), interactionStage
              });
    }
  }

  private static class SubmitOperationHandler extends InternalOperationHandler
  {
    protected boolean receivedInitialStage = false;
    protected boolean takenFinalStage = false;
    protected final int interactionType;
    protected final int interactionStage;

    protected SubmitOperationHandler(final boolean syncOperation, final MALInteractionListener listener)
    {
      super(syncOperation, listener);

      this.interactionType = InteractionType._SUBMIT_INDEX;
      this.interactionStage = MALSubmitOperation._SUBMIT_ACK_STAGE;
    }

    protected SubmitOperationHandler(final MALInteractionListener listener)
    {
      super(false, listener);

      this.interactionType = InteractionType._SUBMIT_INDEX;
      this.interactionStage = MALSubmitOperation._SUBMIT_ACK_STAGE;
    }

    protected SubmitOperationHandler(final int interactionType,
            final int interactionStage,
            final boolean syncOperation,
            final MALInteractionListener listener)
    {
      super(syncOperation, listener);

      this.interactionType = interactionType;
      this.interactionStage = interactionStage;
    }

    @Override
    protected synchronized void handleStage(final MALMessage msg) throws MALInteractionException
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
            logUnexpectedTransitionError(msg.getHeader().getInteractionType().getOrdinal(),
                    msg.getHeader().getInteractionStage().getValue());
            throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
          }
        }
        catch (MALException ex)
        {
          // nothing we can do with this
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Exception thrown handling stage {0}", ex);
        }
      }
      else
      {
        logUnexpectedTransitionError(interactionType, interactionStage);
        throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
      }
    }

    @Override
    protected synchronized void handleError(final MALMessageHeader hdr,
            final MALStandardError err,
            final Map qosMap)
    {
      if (syncOperation)
      {
        signalError(err);
      }
      else
      {
        try
        {
          listener.submitErrorReceived(hdr, new DummyErrorBody(err), qosMap);
        }
        catch (MALException ex)
        {
          // not a lot we can do with this at this stage apart from log it
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Error received from consumer error handler in response to a provider error! {0}", ex);
        }
      }
    }

    @Override
    protected synchronized MALMessage getResult()
    {
      takenFinalStage = true;

      return super.getResult();
    }

    @Override
    protected synchronized boolean finished()
    {
      return takenFinalStage;
    }

    protected boolean checkStage(final int stage)
    {
      return (interactionStage == stage);
    }

    protected void informListener(final MALMessage msg) throws MALException
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
    protected RequestOperationHandler(final boolean syncOperation, final MALInteractionListener listener)
    {
      super(InteractionType._REQUEST_INDEX,
              MALRequestOperation._REQUEST_RESPONSE_STAGE,
              syncOperation,
              listener);
    }

    protected RequestOperationHandler(final MALInteractionListener listener)
    {
      super(InteractionType._REQUEST_INDEX,
              MALRequestOperation._REQUEST_RESPONSE_STAGE,
              false,
              listener);
    }

    @Override
    protected void informListener(final MALMessage msg) throws MALException
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

    protected InvokeOperationHandler(final boolean syncOperation, final MALInteractionListener listener)
    {
      super(syncOperation, listener);
    }

    protected InvokeOperationHandler(final UOctet lastInteractionStage, final MALInteractionListener listener)
    {
      super(false, listener);

      final int interactionStage = lastInteractionStage.getValue();

      if (interactionStage == MALInvokeOperation._INVOKE_ACK_STAGE)
      {
        receivedAck = true;
      }
    }

    @Override
    protected void handleStage(final MALMessage msg) throws MALInteractionException
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
            logUnexpectedTransitionError(interactionType, interactionStage);
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
          logUnexpectedTransitionError(interactionType, interactionStage);
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
            MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Exception thrown handling stage {0}", ex);
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
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Exception thrown handling stage {0}", ex);
        }
      }
    }

    @Override
    protected synchronized void handleError(final MALMessageHeader hdr,
            final MALStandardError err,
            final Map qosMap)
    {
      if (syncOperation)
      {
        signalError(err);
      }
      else
      {
        try
        {
          if (!receivedAck)
          {
            listener.invokeAckErrorReceived(hdr, new DummyErrorBody(err), qosMap);
          }
          else
          {
            listener.invokeResponseErrorReceived(hdr, new DummyErrorBody(err), qosMap);
          }
        }
        catch (MALException ex)
        {
          // not a lot we can do with this at this stage apart from log it
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Error received from consumer error handler in response to a provider error! {0}", ex);
        }
      }
    }

    @Override
    protected synchronized boolean finished()
    {
      return receivedResponse;
    }
  }

  private static final class ProgressOperationHandler extends InternalOperationHandler
  {
    private boolean receivedAck = false;
    private boolean receivedResponse = false;

    protected ProgressOperationHandler(final boolean syncOperation, final MALInteractionListener listener)
    {
      super(syncOperation, listener);
    }

    protected ProgressOperationHandler(final UOctet lastInteractionStage, final MALInteractionListener listener)
    {
      super(false, listener);

      final int interactionStage = lastInteractionStage.getValue();

      if ((interactionStage == MALProgressOperation._PROGRESS_ACK_STAGE)
              || (interactionStage == MALProgressOperation._PROGRESS_UPDATE_STAGE))
      {
        receivedAck = true;
      }
    }

    @Override
    protected void handleStage(final MALMessage msg) throws MALInteractionException
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
            logUnexpectedTransitionError(interactionType, interactionStage);
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
          logUnexpectedTransitionError(interactionType, interactionStage);
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
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Exception thrown handling stage {0}", ex);
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
              listener.progressUpdateErrorReceived(msg.getHeader(),
                      (MALErrorBody) msg.getBody(), msg.getQoSProperties());
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
              listener.progressResponseErrorReceived(msg.getHeader(),
                      (MALErrorBody) msg.getBody(), msg.getQoSProperties());
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
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Exception thrown handling stage {0}", ex);
        }
      }
    }

    @Override
    protected synchronized void handleError(final MALMessageHeader hdr,
            final MALStandardError err,
            final Map qosMap)
    {
      if (syncOperation)
      {
        signalError(err);
      }
      else
      {
        try
        {
          if (!receivedAck)
          {
            listener.progressAckErrorReceived(hdr, new DummyErrorBody(err), qosMap);
          }
          else
          {
            listener.progressResponseErrorReceived(hdr, new DummyErrorBody(err), qosMap);
          }
        }
        catch (MALException ex)
        {
          // not a lot we can do with this at this stage apart from log it
          MALContextFactoryImpl.LOGGER.log(Level.WARNING, "Error received from consumer error handler in response to a provider error! {0}", ex);
        }
      }
    }

    @Override
    protected synchronized boolean finished()
    {
      return receivedResponse;
    }
  }

  private static final class PubSubOperationHandler extends SubmitOperationHandler
  {
    protected PubSubOperationHandler(final boolean syncOperation, final MALInteractionListener listener)
    {
      super(InteractionType._PUBSUB_INDEX, 0, syncOperation, listener);
    }

    protected PubSubOperationHandler(final MALInteractionListener listener)
    {
      super(InteractionType._PUBSUB_INDEX, 0, false, listener);
    }

    @Override
    protected boolean checkStage(final int stage)
    {
      switch (stage)
      {
        case MALPubSubOperation._REGISTER_ACK_STAGE:
        case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
        case MALPubSubOperation._DEREGISTER_ACK_STAGE:
        case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
          return true;
        default:
          return true;
      }
    }

    @Override
    protected void informListener(final MALMessage msg) throws MALException
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

    protected InteractionListenerPublishAdapter(final MALPublishInteractionListener delegate)
    {
      this.delegate = delegate;
    }

    @Override
    public void registerAckReceived(final MALMessageHeader header, final Map qosProperties)
            throws MALException
    {
      delegate.publishRegisterAckReceived(header, qosProperties);
    }

    @Override
    public void registerErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties)
            throws MALException
    {
      delegate.publishRegisterErrorReceived(header, body, qosProperties);
    }

    @Override
    public void deregisterAckReceived(final MALMessageHeader header, final Map qosProperties)
            throws MALException
    {
      delegate.publishDeregisterAckReceived(header, qosProperties);
    }

    @Override
    public void invokeAckErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void invokeAckReceived(final MALMessageHeader header, final MALMessageBody body, final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void invokeResponseErrorReceived(final MALMessageHeader header,
            final MALErrorBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void invokeResponseReceived(final MALMessageHeader header,
            final MALMessageBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void notifyErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void notifyReceived(final MALMessageHeader header, final MALNotifyBody body, final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void progressAckErrorReceived(final MALMessageHeader header,
            final MALErrorBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void progressAckReceived(final MALMessageHeader header, final MALMessageBody body, final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void progressResponseErrorReceived(final MALMessageHeader header,
            final MALErrorBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void progressResponseReceived(final MALMessageHeader header,
            final MALMessageBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void progressUpdateErrorReceived(final MALMessageHeader header,
            final MALErrorBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void progressUpdateReceived(final MALMessageHeader header,
            final MALMessageBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void requestErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void requestResponseReceived(final MALMessageHeader header,
            final MALMessageBody body,
            final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void submitAckReceived(final MALMessageHeader header, final Map qosProperties)
            throws MALException
    {
    }

    @Override
    public void submitErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties)
            throws MALException
    {
    }
  }

  private static final class DummyErrorBody implements MALErrorBody
  {
    private final MALStandardError error;

    public DummyErrorBody(MALStandardError error)
    {
      this.error = error;
    }

    public MALStandardError getError() throws MALException
    {
      return error;
    }

    public int getElementCount()
    {
      return 1;
    }

    public Object getBodyElement(int index, Object element) throws MALException
    {
      return error;
    }

    public MALEncodedElement getEncodedBodyElement(int index) throws MALException
    {
      return null;
    }

    public MALEncodedBody getEncodedBody() throws MALException
    {
      return null;
    }
  }
}
