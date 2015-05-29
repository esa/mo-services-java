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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * StoppableThread extends the native Java thread class by providing a means of telling the thread to stop in a safe
 * manner.
 *
 */
public abstract class StoppableThread implements Runnable
{
  /**
   * The java Thread object used to control starting/stopping the thread.
   */
  private volatile Thread theThread = null;
  /**
   * Name of the new thread.
   */
  private final String threadName;
  /**
   * Condition variable used for synchronous starting. Used to signal that a stoppable thread has started.
   *
   */
  private final Condition started;
  /**
   * Flag used to signal that a thread has been asked to stop.
   */
  private volatile boolean bContinue = true;

  /**
   * Allocates a new named StoppableThread object.
   *
   * @param name name of the new thread.
   */
  public StoppableThread(final String name)
  {
    // Check preconditions
    assert (name != null);

    threadName = name;
    started = new Condition();
    started.reset();
  }

  /**
   * Returns this thread's name.
   *
   * @return this thread's name (not <code>null</code>).
   */
  public String getName()
  {
    // Don't call theThread.getName() since 'theThread' could be null!
    return threadName;
  }

  /**
   * Interrupts this thread.
   */
  public void interrupt()
  {
    if (null != theThread)
    {
      theThread.interrupt();
    }
  }

  /**
   * Causes this thread to begin execution; the Java Virtual Machine calls the run() method of this thread.
   */
  public synchronized void pleaseStart()
  {
    if (null == theThread)
    {
      // set/reset continue flag
      this.bContinue = true;

      if (null == threadName)
      {
        theThread = new Thread(this);
      }
      else
      {
        theThread = new Thread(this, threadName);
      }
      theThread.start();
    }
  }

  /**
   * Causes this thread to begin execution; the Java Virtual Machine calls the run() method of this thread. Blocks the
   * caller until the thread has started
   *
   * @throws InterruptedException Caller was interupped whilst waiting for thread to start.
   */
  public void pleaseStartSync() throws java.lang.InterruptedException
  {
    boolean bWait = false;

    synchronized (this)
    {
      if (null == theThread)
      {
        pleaseStart();
        bWait = true;
      }
    }

    if (bWait)
    {
      started.waitFor();
    }
  }

  /**
   * Signals the thread to stop.
   *
   * This is implemented by interrupting the thread. If the thread is sleeping/waiting, it will be woken up; otherwise,
   * it will be woken up immediately when it next attempts to sleep/wait.
   */
  public synchronized void pleaseStop()
  {
    bContinue = false;
    notifyAll();

    if (null != theThread)
    {
      theThread.interrupt();
    }
  }

  /**
   * Waits for this thread to die.
   *
   * Returns true if the called thread terminated without the calling thread being interrupted.
   *
   * @return Returns <code>true</code> if the called thread terminated.
   */
  public boolean pleaseJoin()
  {
    return pleaseJoin(0);
  }

  /**
   * Waits at most
   * <code>millis</code> milliseconds for this thread to die. A timeout of 0 means to wait forever.
   *
   * Returns true if the called thread terminated without the calling thread being interrupted.
   *
   * @param millis the time to wait in milliseconds.
   * @return Returns <code>true</code> if the called thread terminated.
   */
  public boolean pleaseJoin(final long millis)
  {
    boolean isJoined = true;

    try
    {
      java.lang.Thread tmpTrd;

      synchronized (this)
      {
        tmpTrd = theThread;
      }

      if (null != tmpTrd)
      {
        tmpTrd.join(millis);
        isJoined = (theThread == null);
      }
    }
    catch (java.lang.InterruptedException ex)
    {
      isJoined = false;
    }

    return isJoined;
  }

  /**
   * Signals that the thread has started and invokes derived class stoppableRun method. Catches any exceptions that are
   * thrown and traces them. Do not override this method.
   */
  @Override
  public final void run()
  {
    try
    {
      started.set();

      stoppableRun();
    }
    catch (java.lang.Throwable ex)
    {
      // Uncaught exceptions should not reach here - trace and terminate thread
      Logger.getLogger("org.ccsds.moims.mo.mal.impl.util")
              .log(Level.WARNING, "Uncaught exception in thread, ''{0}'' terminating {1}", new Object[]
              {
                theThread.getName(), ex
              });
    }
    theThread = null;
  }

  /**
   * Returns true if execution of this thread should continue.
   *
   * @return true if should continue execution.
   */
  protected boolean shouldContinue()
  {
    return bContinue;
  }

  /**
   * Main method provided by derived class, provides implementation.
   */
  protected abstract void stoppableRun();
}
