/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.smc.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MAL;
import org.ccsds.moims.smc.mal.api.MALFactory;
import org.ccsds.moims.smc.mal.api.structures.MALException;

/**
 *
 * @author cooper_sf
 */
public class MALFactoryImpl extends MALFactory
{
  private static final java.util.Set m_loadedProperties = new java.util.TreeSet();

  public MALFactoryImpl()
  {
    init();
  }

  public void init()
  {
    String configFile = System.getProperty("ccsds.properties", "org/ccsds/moims/smc/ccsds.properties");
    java.util.Properties props = loadProperties(configFile);

    java.util.Properties sysProps = System.getProperties();
    sysProps.putAll(props);
    System.setProperties(sysProps);

    //org.ccsds.moims.protocol.implementation.holders.HolderHelper.init();
  }

  @Override
  public MAL createMAL(Hashtable properties) throws MALException
  {
    return new MALImpl(properties);
  }

  protected static java.util.Properties loadProperties(String configFile)
  {
    java.util.Properties topProps = new java.util.Properties();

    if (null != configFile)
    {
      java.net.URL url = ClassLoader.getSystemClassLoader().getResource(configFile);

      if ((null != url) && (false == m_loadedProperties.contains(url.toString())))
      {
        m_loadedProperties.add(url.toString());

        try
        {
          java.util.Properties myProps = new java.util.Properties();
          myProps.load(url.openStream());

          java.util.Properties subProps = loadProperties(myProps.getProperty("ccsds.properties"));

          System.out.println("Loading properties from " + url.toString());
          topProps.putAll(subProps);
          topProps.putAll(myProps);
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }
    }

    return topProps;
  }
}
