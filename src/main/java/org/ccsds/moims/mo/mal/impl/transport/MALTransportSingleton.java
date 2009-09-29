/*
 * MALTransportSingleton.java
 *
 * Created on 23 February 2006, 09:36
 */
package org.ccsds.moims.mo.mal.impl.transport;

import java.util.Hashtable;
import java.util.Map.Entry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALTransport;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 *
 */
public final class MALTransportSingleton
{
  /** Map of protocol handlers currently used by the application */
  private static final java.util.Map<String, MALTransportFactory> handlerMap = new java.util.TreeMap<String, MALTransportFactory>();
  private static final java.util.Map<String, MALTransport> transportMap = new java.util.TreeMap<String, MALTransport>();
  //private static final java.util.Map m_receiverMap = new java.util.TreeMap();
  /** The default protocol to be used by the provider */
  private static String s_strDefaultProtocol = "rmi://";

  public static void init()
  {
    String propName = MALTransportFactory.FACTORY_PROP_NAME_PREFIX + ".rmi";
    System.setProperty(propName, "org.ccsds.moims.mo.mal.test.transport.rmi.RMITransportFactoryImpl");

    if (null != System.getProperty("org.ccsds.moims.mo.mal.transport.default.protocol"))
    {
      defaultHandler(System.getProperty("org.ccsds.moims.mo.mal.transport.default.protocol"));
    }
  }

  /**
   *  Sets the default communication protocl to be used by this provider.
   *  @param strProtocol Protocol to be used by this provider.
   */
  public static void defaultHandler(final String strProtocol)
  {
    s_strDefaultProtocol = strProtocol;
  }

  /**
   *   Creates an instance of the proctocl handler.
   *   @param dstUri The Uri location of the provider.
   *   @return ProtocolHandler The Protocol.
   */
  public static MALTransport instance(final URI dstUri, Hashtable properties) throws MALException
  {
    init();

    if ((null != dstUri) && (null != dstUri.getValue()))
    {
      return _instance(dstUri.getValue(), properties);
    }

    return _instance(s_strDefaultProtocol, null);
  }

  public static MALTransport instance(final String dstUri, Hashtable properties) throws MALException
  {
    init();

    if (null != dstUri)
    {
      return _instance(dstUri, properties);
    }

    return _instance(s_strDefaultProtocol, null);
  }

  /**
   *   Creates an instance of the proctocl handler.
   *   @param dstUri The Uri location of the provider.
   *   @return ProtocolHandler The Protocol.
   */
  public static MALTransport _instance(final String dstUri, Hashtable properties) throws MALException
  {
    // get protocol from uri
    String strProtocol = dstUri;

    int iPro = dstUri.indexOf("://");
    if (-1 != iPro)
    {
      strProtocol = dstUri.substring(0, iPro);
    }

    // lookup for existing transport
    MALTransport transport = transportMap.get(strProtocol);

    if (null == transport)
    {
      // lookup for existing handler else create new one and add to map
      MALTransportFactory ohandler = handlerMap.get(strProtocol);
      if (null == ohandler)
      {
        ohandler = MALTransportFactory.newInstance(strProtocol);

        if (null != ohandler)
        {
          handlerMap.put(strProtocol, ohandler);
        }
      }

      transport = ohandler.createTransport(properties);

      if (null != transport)
      {
        transportMap.put(strProtocol, transport);
      }
    }

    return transport;
  }

  public static void close()
  {
    synchronized (transportMap)
    {
      for (Entry<String, MALTransport> obj : transportMap.entrySet())
      {
        try
        {
          obj.getValue().close();
        }
        catch (MALException ex)
        {
          // TODO
        }
      }

      transportMap.clear();
      handlerMap.clear();
    }
  }
}