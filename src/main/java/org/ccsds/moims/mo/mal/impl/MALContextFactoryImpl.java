/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MALContext Implementation
 * Author       : Sam Cooper
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
  /**
   * The property that contains the file to load MAL properties from.
   */
  public static final String MAL_CONFIG_FILE_PROPERTY = "org.ccsds.moims.mo.mal.properties";
  /**
   * The default property file location.
   */
  public static final String MAL_CONFIG_FILE_DEFAULT = "org/ccsds/moims/mo/mal.properties";
  private final MALAccessControlFactory securityFactory;

  /**
   * Constructor.
   *
   * @throws MALException On error.
   */
  public MALContextFactoryImpl() throws MALException
  {
    init();

    securityFactory = MALAccessControlFactory.newFactory();
  }

  private void init()
  {
    final String configFile = System.getProperty(MAL_CONFIG_FILE_PROPERTY, MAL_CONFIG_FILE_DEFAULT);
    final java.util.Properties props = StructureHelper.loadProperties(configFile, MAL_CONFIG_FILE_PROPERTY);

    final java.util.Properties sysProps = System.getProperties();
    sysProps.putAll(props);
    System.setProperties(sysProps);

    TransportSingleton.init();
  }

  @Override
  public MALContext createMALContext(final Map properties) throws MALException
  {
    return new MALContextImpl(securityFactory, properties);
  }
}
