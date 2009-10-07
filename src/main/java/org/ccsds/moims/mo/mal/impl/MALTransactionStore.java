/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 *
 * @author cooper_sf
 */
public abstract class MALTransactionStore
{
  private static volatile int transId = 0;

  public static synchronized Identifier getTransactionId()
  {
    return new Identifier(Integer.toString(transId++));
  }
}
