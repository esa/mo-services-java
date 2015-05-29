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

import java.util.Deque;
import java.util.LinkedList;

/**
 * A container adaptor class that provides a restricted subset of the Container functionality. It is a FIFO data
 * structure.
 *
 * Since it is implemented on top of some underlying container type, the current choice of java.util.LinkedList can
 * easily be changed without affecting any of the classes which use Queue.
 *
 * @param <T> The type of object held in the queue.
 *
 */
public class Queue<T>
{
  /**
   * A linked list which actually stores the objects in the queue.
   */
  private final Deque<T> container = new LinkedList<T>();

  /**
   * Adds an entry to the back of the queue.
   *
   * @param obj Object to be added.
   */
  public void push(final T obj)
  {
    container.addLast(obj);
  }

  /**
   * Removes the element at the front of the queue, and returns it to the caller.
   *
   * @return Object removed from front of queue.
   */
  public T pop()
  {
    return container.removeFirst();
  }

  /**
   * Removes all of the elements contained in the queue.
   */
  public void clear()
  {
    container.clear();
  }

  /**
   * Returns the number of elements contained in the queue.
   *
   * @return Size of queue.
   */
  public int size()
  {
    return container.size();
  }

  /**
   * Removes a single instance of the specified object from the list.
   *
   * @param obj Object to find and remove from queue.
   * @return True if object found and removed from queue, false if not found.
   */
  public boolean remove(final T obj)
  {
    return container.remove(obj);
  }

  /**
   * Returns the element at the front of the queue to the caller without removing it.
   *
   * @return Object at front of queue.
   */
  public T head()
  {
    return container.getFirst();
  }

  /**
   * Returns a string representation of the object.
   *
   * @return String representation of queue.
   */
  @Override
  public String toString()
  {
    final StringBuilder str = new StringBuilder();

    str.append("[ ");

    final java.util.Iterator<T> it = container.iterator();

    while (it.hasNext())
    {
      str.append(" (");
      str.append(it.next().toString());
      str.append(") ");
    }

    str.append(" ]");

    return str.toString();
  }
}
