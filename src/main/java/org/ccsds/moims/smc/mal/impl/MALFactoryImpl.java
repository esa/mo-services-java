/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.smc.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MAL;
import org.ccsds.moims.smc.mal.api.MALFactory;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.impl.transport.MALTransportSingleton;
import org.ccsds.moims.smc.mal.impl.util.StructureHelper;

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
