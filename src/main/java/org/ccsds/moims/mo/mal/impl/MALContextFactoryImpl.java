/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MALContext Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControlFactory;
import org.ccsds.moims.mo.mal.impl.transport.TransportSingleton;
import org.ccsds.moims.mo.mal.impl.util.StructureHelper;

/**
 * Implementation of the MALContextFactory abstract class.
 */
public class MALContextFactoryImpl extends MALContextFactory
{
  private final MALAccessControlFactory securityFactory;
  
  /**
   * Constructor.
   * @throws MALException On error.
   */
  public MALContextFactoryImpl() throws MALException
  {
    init();
    
    securityFactory = MALAccessControlFactory.newFactory();
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
  public MALContext createMALContext(Map properties) throws MALException
  {
    return new MALContextImpl(securityFactory, properties);
  }
}
