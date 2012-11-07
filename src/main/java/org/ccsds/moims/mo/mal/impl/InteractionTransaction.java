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
package org.ccsds.moims.mo.mal.impl;

/**
 * Singleton class that holds the transaction id counter used by the interaction maps.
 */
abstract class InteractionTransaction
{
  private static volatile long transId = 0;

  static synchronized Long getTransactionId()
  {
    return transId++;
  }
}
