/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
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
package esa.mo.mal.transport.tcp;

import static esa.mo.mal.transport.gen.GENTransport.LOGGER;
import esa.mo.mal.transport.inproc.InProcTransport;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 *
 */
public class TCPTransport extends InProcTransport implements InProcTransport.ExternalMessageSink
{
  private boolean bContinue = true;
  private int portNumber = 0;
  private ServerSocket serverSocket;
  private Socket socket;

  public TCPTransport(String protocol, MALTransportFactory factory, java.util.Map properties) throws MALException
  {
    super(protocol, '-', false, false, factory, properties);
  }

  @Override
  public void init() throws MALException
  {
    this.setExternalSink(this);
    
    
    try
    {
      String serverHost = System.getProperty("org.ccsds.moims.mo.mal.transport.protocol.tcp.host");
      int port = Integer.getInteger("org.ccsds.moims.mo.mal.transport.protocol.tcp.port", 0);

      if (null == serverHost)
      {
        LOGGER.info("TCP Transport in server mode");
        // we are the server socket
        serverSocket = new ServerSocket(port);
        portNumber = serverSocket.getLocalPort();

        startConnectionHandler();
      }
      else
      {
        LOGGER.info("TCP Transport in client mode");
        serverSocket = null;
        socket = new Socket(serverHost, port);
        portNumber = socket.getLocalPort();

        startConnectionPump();
      }

      super.init();
    }
    catch (Exception ex)
    {
      throw new MALException("TCP transport unable to initialise", ex);
    }
  }

  @Override
  protected String createTransportAddress() throws MALException
  {
    try
    {
      // Build url string
      final StringBuilder hostAddress = new StringBuilder();

      final InetAddress addr = Inet4Address.getLocalHost();
      if (addr instanceof Inet6Address)
      {
        LOGGER.fine("TCP Address class is IPv6");
        hostAddress.append('[');
        hostAddress.append(addr.getHostAddress());
        hostAddress.append(']');
      }
      else
      {
        hostAddress.append(addr.getHostAddress());
      }

      hostAddress.append(':');
      hostAddress.append(portNumber);

      return hostAddress.toString();
    }
    catch (UnknownHostException ex)
    {
      throw new MALException("Could not determine local host address", ex);
    }
  }

  @Override
  public void close() throws MALException
  {
    try
    {
      bContinue = false;

      if (null != socket)
      {
        socket.close();
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  @Override
  public void receiveMessage(byte[] buf)
  {
//      LOGGER.log(Level.INFO, "TCP Sending data to {0} : {1}", new Object[]
//      {
//        tmsg.addr, tmsg.msg
//      });

    try
    {
      socket.getOutputStream().write(buf);
    }
    catch (IOException ex)
    {
      LOGGER.log(Level.WARNING, "TCP Exceptoin thrown when sending TCP/IP message", ex);
    }
  }

  private void startConnectionHandler()
  {
    final Thread connectionThread = new Thread()
    {
      @Override
      public void run()
      {
        try
        {
          serverSocket.setReuseAddress(true);
          serverSocket.setSoTimeout(1000);
        }
        catch (SocketException ex)
        {
        }

        while (bContinue)
        {
          try
          {
            if (null == socket)
            {
              socket = serverSocket.accept();

              startConnectionPump();
            }
            else
            {
              Thread.sleep(1000);
            }
          }
          catch (Exception ex)
          {
          }
        }
      }
    };

    connectionThread.start();
  }

  private void startConnectionPump()
  {
    final Thread connectionThread = new Thread()
    {
      @Override
      public void run()
      {
        boolean localContinue = true;

        while (bContinue && localContinue)
        {
          try
          {
            if (null != socket)
            {
              int size = (socket.getInputStream().available() < 1024 ? 1024 : socket.getInputStream().available());

//              byte[] buf = new byte[size];
//              int len = socket.getInputStream().read(buf);
//
//              if (0 < len)
//              {
//                receive(buf);
//              }
//              else if (-1 == len)
//              {
//                // readed eof
//                localContinue = false;
//              }
              if (0 < size)
              {
                receive(socket.getInputStream());
              }
            }
            else
            {
              Thread.sleep(1000);
            }
          }
          catch (Exception ex)
          {
            localContinue = false;
          }
        }

        try
        {
          socket.close();
        }
        catch (IOException ex)
        {
        }
        socket = null;
      }
    };

    connectionThread.start();
  }
}
