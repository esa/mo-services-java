/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MAL;
import org.ccsds.moims.mo.mal.MALFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;
import org.ccsds.moims.mo.mal.security.MALSecurityManagerFactory;

/**
 * Implementation of the MALFactory abstract class.
 */
public class MALFactoryImpl extends MALFactory
{
  private final MALSecurityManagerFactory securityFactory;
  
  /**
   * Constrcutor.
   * @throws MALException On error.
   */
  public MALFactoryImpl() throws MALException
  {
    init();
    
    securityFactory = MALSecurityManagerFactory.newInstance();
  }

  private void init()
  {
    String configFile = System.getProperty("org.ccsds.moims.mo.mal.properties", "org/ccsds/moims/mo/mal.properties");
    java.util.Properties props = StructureHelper.loadProperties(configFile, "org.ccsds.moims.mo.mal.properties");

    java.util.Properties sysProps = System.getProperties();
    sysProps.putAll(props);
    System.setProperties(sysProps);

    TransportSingleton.init();
  }

  @Override
  public MAL createMAL(Hashtable properties) throws MALException
  {
    return new MALImpl(securityFactory, properties);
  }
}
