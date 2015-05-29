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

/**
 * A FIFO container with built-in synchronization. Provides the standard queue methods, each of which is synchronous,
 * plus an event-driven pop. This queue can store any type of object, but not null.
 *
 * @param <T> The type of object held in the queue.
 *
 */
public class SyncQueue<T>
{
  /**
   * The underlying (non-synchronous) queue object which actually queues the objects.
   */
  private final Queue<T> theQueue = new Queue<T>();

  /**
   * Adds an entry to the back of the queue (synchronous). One of the clients waiting on an empty queue will be
   * immediately notified.
   *
   * @param obj The object to add to the queue (not <code>null</code>).
   */
  public synchronized void push(final T obj)
  {
    assert (obj != null);

    // Notify one of the clients waiting for the queue to become non-empty
    this.theQueue.push(obj);
    notify();
  }

  /**
   * Returns the element at the front of the queue to the caller without removing it (synchronous).
   *
   * @return The object at the front of the queue, or <code>null</code> if the queue is empty.
   */
  public synchronized T head()
  {
    if (this.theQueue.size() == 0)
    {
      return null;
    }
    return this.theQueue.head();
  }

  /**
   * Removes the element at the front of the queue, and returns it to the caller (synchronous).
   *
   * @return The object removed from the front of the queue, or <code>null</code> if the queue is empty.
   */
  public synchronized T pop()
  {
    if (this.theQueue.size() == 0)
    {
      return null;
    }
    return this.theQueue.pop();
  }

  /**
   * Removes the element at the front of the queue, and returns it to the caller (synchronous). If the queue is empty,
   * waits until an object is added and pops it.
   *
   * @return The object removed from the front of the queue.
   * @throws InterruptedException If wait is interrupted.
   */
  public synchronized T syncPop() throws InterruptedException
  {
    while (this.theQueue.size() == 0)
    {
      // Note: immediately releases the monitor, then reacquires it before
      //       returning.
      wait();
    }
    return this.theQueue.pop();
  }

  /**
   * Removes all of the elements contained in the queue (synchronous).
   */
  public synchronized void clear()
  {
    this.theQueue.clear();
  }

  /**
   * Returns the number of elements contained in the queue (synchronous).
   *
   * @return The size of the queue.
   */
  public synchronized int size()
  {
    return this.theQueue.size();
  }

  @Override
  public synchronized String toString()
  {
    return this.theQueue.toString();
  }
}
