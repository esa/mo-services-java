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
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * The interaction map is responsible for maintaining the state of the provider side of interactions for a MAL instance.
 */
class InteractionProviderMap
{
  private final Map<Long, Map.Entry> resolveMap = new TreeMap<Long, Map.Entry>();

  Long addTransactionSource(final URI urlFrom, final Long transactionId)
  {
    synchronized (resolveMap)
    {
      final Long internalTransactionId = InteractionTransaction.getTransactionId(resolveMap.keySet());

      resolveMap.put(internalTransactionId, new TreeMap.SimpleEntry(urlFrom, transactionId));

      return internalTransactionId;
    }
  }

  Map.Entry resolveTransactionSource(final Long internalTransactionId)
  {
    synchronized (resolveMap)
    {
      return resolveMap.get(internalTransactionId);
    }
  }

  void removeTransactionSource(final Long internalTransactionId)
  {
    synchronized (resolveMap)
    {
      if (null == resolveMap.remove(internalTransactionId))
      {
        MALContextFactoryImpl.LOGGER.log(Level.WARNING,
                "No key found in service maps for received interaction of {0}", internalTransactionId);
      }
    }
  }
}
