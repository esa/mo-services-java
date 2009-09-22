/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.mo.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MAL;
import org.ccsds.moims.mo.mal.MALFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.transport.MALTransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;

/**
 *
 * @author cooper_sf
 */
public class MALFactoryImpl extends MALFactory
{
  public MALFactoryImpl()
  {
    init();
  }

  public void init()
  {
    String configFile = System.getProperty("ccsds.properties", "org/ccsds/moims/smc/ccsds.properties");
    java.util.Properties props = StructureHelper.loadProperties(configFile, "ccsds.properties");

    java.util.Properties sysProps = System.getProperties();
    sysProps.putAll(props);
    System.setProperties(sysProps);

    MALTransportSingleton.init();
  }

  @Override
  public MAL createMAL(Hashtable properties) throws MALException
  {
    return new MALImpl(properties);
  }
}
