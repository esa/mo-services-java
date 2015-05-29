/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO JMS Transport Framework
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
package esa.mo.mal.transport.jms.util;

import java.util.logging.Logger;

public class ActiveQueue<T> extends StoppableThread
{
  private final SyncQueue<T> theSyncQueue = new SyncQueue<T>();
  private final ActiveQueueAdapter<T> theAdapter;
  /**
   * Maximum number of elements to have in queue before triggering an auto flush.
   */
  private volatile int maxQueueLength;
  /**
   * Used to inform the internal thread to perform a flush.
   */
  private final Condition signalFlush = new Condition();

  /**
   * Constructs a manual active queue which will use
   * <code>adapter</code> to consume the objects. The
   * <code>flush</code> method must be called explicitly to consume objects in the queue.
   *
   * @param name Name of the new thread.
   * @param adapter The consumer of the popped objects.
   * @see #flush
   */
  public ActiveQueue(final String name, final ActiveQueueAdapter<T> adapter)
  {
    this(name, adapter, -1);
  }

  /**
   * Constructs an automatic active queue which will use
   * <code>adapter</code> to consume the objects when the maximum queue length is exceeded.
   *
   * @param name Name of the new thread.
   * @param adapter The consumer of the popped objects.
   * @param maxLength Maximum length of queue before flushing.
   */
  public ActiveQueue(final String name, final ActiveQueueAdapter<T> adapter, final int maxLength)
  {
    super(name);

    // check preconditions
    assert (adapter != null);

    theAdapter = adapter;
    maxQueueLength = maxLength;

    signalFlush.reset();
  }

  /**
   * Sets the maximum length of the queue before an automatic flush is triggered. <p> To put the queue in manual mode,
   * set the length to a negative value. Setting the queue to zero means the objects are consumed as soon as they are
   * pushed. <p> <I>Note: The new length will only come into effect after the next push or flush.</I>
   *
   * @param maxLength New maximum queue length.
   */
  public void setQueueLength(final int maxLength)
  {
    maxQueueLength = maxLength;
  }

  /**
   * Adds an entry to the back of the queue. Triggers a flush if in automatic mode and the maximum queue length is
   * exceeded.
   *
   * @param obj The object to add to the queue (not <code>null</code>).
   */
  public void push(final T obj)
  {
    // add the new object to the queue (checks for null objects)
    theSyncQueue.push(obj);

    // if in auto mode, check whether the queue needs to be flushed.
    //  (take a snapshot of maxQueueLength to ensure checks are consistent)
    final int maxLengthSnapshot = maxQueueLength;
    final boolean autoMode = (maxLengthSnapshot >= 0);

    if (autoMode && theSyncQueue.size() > maxLengthSnapshot)
    {
      // have too many objects, trigger a flush.
      //  (may trigger a phantom flush if a flush is currently in progress)
      signalFlush.set();
    }
  }

  /**
   * Pops and consumes all objects in this active queue. Objects are removed from the queue and processed until either
   * the queue is empty, or an object is not consumed by the adapter (in which case it remains at the head of the
   * queue). <p> Two modes of flush are possible, synchronous and asynchronous. In the synchronous mode the caller is
   * blocked until all objects have been popped and consumed, whereas in asynchronous mode the caller returns
   * immediately.
   *
   * @param sync True is caller should block, else false if caller should return immediately.
   */
  public void flush(final boolean sync)
  {
    if (sync)
    {
      // have to flush queue by hand is sync mode
      internalFlush();
    }
    else
    {
      // get built in thread to flush for us
      signalFlush.set();
    }
  }

  /**
   * Removes all of the elements contained in this active queue without consuming them.
   */
  public void clear()
  {
    theSyncQueue.clear();
  }

  /**
   * Returns the number of objects contained in this active queue.
   *
   * @return Number of objects in the queue.
   */
  public int size()
  {
    return theSyncQueue.size();
  }

  /**
   * Entry point for the internal thread. <p> Sleeps until
   * <code>signalFlush</code> is signalled to indicate that an asynchronous flush needs to be performed. Performs a
   * final flush when asked to terminate by the base class.
   */
  @Override
  protected void stoppableRun()
  {
    while (shouldContinue())
    {
      // wait for next notification
      try
      {
        signalFlush.waitFor();
        signalFlush.reset();

        // recheck autoflush condition (try to avoid phantom flushes)
        if (theSyncQueue.size() > maxQueueLength)
        {
          flush(true);
        }
      }
      catch (java.lang.InterruptedException e)
      {
        // do nothing, probably being shutdown
      }
    }

    // if we are here we have been asked to stop, perform final flush
    flush(true);
  }

  /**
   * Performs the actual consuming of objects from this queue. <p> Continues processing objects from the queue until
   * either the queue is empty, or an object is not consumed by the adapter (in which case it remains at the head of the
   * queue). If an exception is thrown by the adapter, the queued object is removed and disgarded. <p> <I>Note: this
   * method is 'synchronized', so only one flush can be ongoing at any one time.</I>
   */
  protected synchronized void internalFlush()
  {
    boolean continueFlushing = true;

    while (continueFlushing)
    {
      final T obj = theSyncQueue.head();

      if (obj == null)
      {
        // queue is empty, stop flushing
        continueFlushing = false;
      }
      else
      {
        // queue contains at least one object, attempt to consume it
        boolean shouldPopObject = true;
        try
        {
          shouldPopObject = theAdapter.consume(obj);
        }
        catch (Throwable ex)
        {
          // dodgy object detected, drop it
          Logger.getLogger("org.ccsds.moims.mo.mal.impl.util")
                  .warning("Exception thrown during consume, dropping object");
        }
        if (shouldPopObject)
        {
          // object consumed, so pop it (if not already removed by a clear)
          theSyncQueue.pop();
        }
        else
        {
          // oh dear, adapter currently 'full' so end this flush
          //  (unconsumed object is still at the head of the queue)
          continueFlushing = false;
        }
      }
    }
  }
}
