/* ----------------------------------------------------------------------------
 * (C) 2011      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO File Transport
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.transport.file;

import esa.mo.mal.transport.gen.GENTransport;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALTransport;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 * Instance of the transport factory for a file based protocol.
 */
public class FileTransportFactoryImpl extends MALTransportFactory
{
  private static final Object MUTEX = new Object();
  private GENTransport transport = null;

  /**
   * Constructor.
   *
   * @param protocol The protocol string.
   */
  public FileTransportFactoryImpl(final String protocol)
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
        transport = new FileTransport(getProtocol(), this, properties);
        transport.init();
      }

      return transport;
    }
  }
}
