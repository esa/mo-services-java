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

import java.util.Set;

/**
 * Singleton class that holds the transaction id counter used by the interaction maps.
 */
abstract class InteractionTransaction
{
  private static final long FIRST_FIRST_TWENTY_FIFTEEN_IN_MS = 1420070400L;
  private static final long MAX_OFFSET = 16777215L;
  private static volatile long transMag;
  private static volatile long transOffset;

  private InteractionTransaction()
  {
  }

  static
  {
    recalculateTransactionIdMagnitude();
  }

  static synchronized Long getTransactionId(Set<Long> keySet)
  {
    long lt;

    do
    {
      ++transOffset;

      if (transOffset > MAX_OFFSET)
      {
        recalculateTransactionIdMagnitude();
      }
      
      lt = transMag + transOffset;
    } while (keySet.contains(lt));

    return lt;
  }

  static private void recalculateTransactionIdMagnitude()
  {
    long ct = System.currentTimeMillis() - FIRST_FIRST_TWENTY_FIFTEEN_IN_MS;
    ct = ct << 24;

    transMag = ct;
    transOffset = 0;
  }
}
