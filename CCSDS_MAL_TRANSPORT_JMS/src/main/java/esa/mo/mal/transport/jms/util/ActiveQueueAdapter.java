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

/** Used by the ActiveQueue class to consume objects popped from the queue.
 *
 * @param <T> The type of object held in the queue.
 * 
 * @author Sam Cooper
 */
public interface ActiveQueueAdapter<T>
{
  /** Invoked on each object popped from the queue.
   * @param obj The object to consume.
   * @return True if object was consumed
   */  
  boolean consume(T obj);
}

