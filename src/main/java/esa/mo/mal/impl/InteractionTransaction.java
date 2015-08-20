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
  /**
   * The following constant defines a time epoch for the MAL transaction id. The date is 1st September 2010 which is when
   * the MAL was first published. It is used to subtract from the UNIX time used as the seed for the transaction id. It
   * gives us an effective time range for transaction values from the MAL epoch until approximately year 2045. Hopefully
   * we will have come up with a new algorithm before then...
   */
  private static final long MAL_EPOCH = 1283299200000L;
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
    transMag = (System.currentTimeMillis() - MAL_EPOCH) << 24;
    transOffset = 0;
  }
}
