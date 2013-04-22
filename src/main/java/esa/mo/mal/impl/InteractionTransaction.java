/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl;

import java.util.Set;

/**
 * Singleton class that holds the transaction id counter used by the interaction maps.
 */
abstract class InteractionTransaction
{
  private static volatile long transId = 0;

  static synchronized Long getTransactionId(Set<Long> keySet)
  {
    long lt = transId++;
    
    while(keySet.contains(lt))
    {
      lt = transId++;
    }
    
    return lt;
  }
}
