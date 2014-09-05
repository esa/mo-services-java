/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Test bed utilities
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
package org.ccsds.moims.mo.testbed.suite;

import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.omg.CORBA.BooleanHolder;

/**
 *
 */
public final class BooleanCondition
{
  private BooleanHolder isSet = new BooleanHolder(false);

  public synchronized final void set()
  {
    isSet.value = true;
    notifyAll();
  }

  public synchronized final void reset()
  {
    isSet.value = false;
    notifyAll();
  }

  public synchronized final boolean waitFor(long timeout) throws InterruptedException
  {
    BooleanHolder hld = isSet;
    if (false == hld.value)
    {
      this.wait(timeout);
    }
    // held value will be true if set, false if timed out

    if (false == hld.value)
    {
      LoggingBase.logMessage("Condition timed out");
    }

    return hld.value;
  }
}
