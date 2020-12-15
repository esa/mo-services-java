/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.regression.fastprovider.fasttransport;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALTransport;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 *
 */
public class FastTransportFactoryImpl extends MALTransportFactory
{
  private static final Object MUTEX = new Object();
  private FastTransport transport = null;

  /**
   * Constructor.
   *
   * @param protocol The protocol string.
   */
  public FastTransportFactoryImpl(final String protocol)
  {
    super(protocol);
  }

  @Override
  public MALTransport createTransport(final MALContext malContext, final Map properties) throws MALException
  {
    synchronized (MUTEX)
    {
      if (null == transport)
      {
        transport = new FastTransport();
      }

      return transport;
    }
  }
}