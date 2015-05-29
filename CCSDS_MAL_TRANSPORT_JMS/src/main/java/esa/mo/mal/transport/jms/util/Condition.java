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
 * A condition object provides synchronization between two threads when one thread needs to wait for another thread to
 * execute some operation and signal the result.
 *
 * The condition object must first be reset, after which it can be set or waited for in either order. [This guarantee is
 * necessary in order to avoid the signal being missed.]
 *
 */
public class Condition
{
  /**
   * Flag to specify whether the value of the condition object has been set.
   */
  private BooleanHolder isSet = new BooleanHolder(false);

  /**
   * Resets the condition object.
   *
   */
  public synchronized void reset()
  {
    // don't reset if not set.
    if (isSet.value)
    {
      isSet = new BooleanHolder(false);
    }
  }

  /**
   * Sets the value of the condition object.
   *
   */
  public synchronized void set()
  {
    isSet.value = true;

    notifyAll();
  }

  /**
   * Waits for the value of the condition object to change.
   *
   * @throws InterruptedException Thrown if wait is interrupted.
   */
  public synchronized void waitFor() throws java.lang.InterruptedException
  {
    final BooleanHolder hld = isSet;
    while (!hld.value)
    {
      wait();
    }
  }

  /**
   * Waits for the value of the condition object to change.
   *
   * @param timeout Time in milliseconds to wait for condition.
   * @throws InterruptedException Thrown if wait is interrupted.
   * @return True if condition signalled, false if timed out.
   */
  public synchronized boolean waitFor(final long timeout) throws java.lang.InterruptedException
  {
    final BooleanHolder hld = isSet;
    if (!hld.value)
    {
      // do not wait in loop as we expect to fall out of this after the timeout.
      this.wait(timeout);
    }

    // held value will be true if set, false if timed out
    return hld.value;
  }

  private static final class BooleanHolder
  {
    private boolean value;

    protected BooleanHolder(final boolean value)
    {
      this.value = value;
    }
  }
}
