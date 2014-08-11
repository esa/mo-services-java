/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.accesscontrol;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;

/**
 * The MALAccessControlFactory class enables the MAL layer to create and configure MALAccessControl instances. The class
 * provides a static factory class repository that maps the MALAccessControlFactory implementation classes to their
 * class names.
 */
public abstract class MALAccessControlFactory
{
  /**
   * The system property that resolves the specific MALAccessControlFactory class name.
   */
  public static final String FACTORY_PROP_NAME = "org.ccsds.moims.mo.mal.accesscontrol.factory.class";
  private static final Map<String, Class> _FACTORY_MAP = new TreeMap<String, Class>();

  /**
   * The method allows an implementation to register the class of a specific MALAccessControlFactory. NOTE – This method
   * may be useful in environments where several class loaders are involved (e.g., OSGi platforms).
   *
   * @param factoryClass Class extending MALAccessControlFactory
   * @throws IllegalArgumentException if the argument does not extend MALAccessControlFactory
   */
  public static void registerFactoryClass(final java.lang.Class factoryClass) throws IllegalArgumentException
  {
    if (!MALAccessControlFactory.class.isAssignableFrom(factoryClass))
    {
      throw new IllegalArgumentException("Not compliant: " + factoryClass.getName());
    }

    _FACTORY_MAP.put(factoryClass.getName(), factoryClass);
  }

  /**
   * The method allows an implementation to deregister the class of a specific MALAccessControlFactory.
   *
   * @param factoryClass The class to deregister
   * @throws IllegalArgumentException if the argument does not extend MALAccessControlFactory
   */
  public static void deregisterFactoryClass(final java.lang.Class factoryClass) throws IllegalArgumentException
  {
    if (!MALAccessControlFactory.class.isAssignableFrom(factoryClass))
    {
      throw new IllegalArgumentException("Not compliant: " + factoryClass.getName());
    }

    if (null != factoryClass)
    {
      _FACTORY_MAP.remove(factoryClass.getName());
    }
  }

  /**
   * The method returns a MALAccessControlFactory instance.
   *
   * @return The new factory.
   * @throws MALException If an error detected during instantiation.
   */
  public static MALAccessControlFactory newFactory() throws MALException
  {
    final String className = System.getProperty(FACTORY_PROP_NAME);
    try
    {
      if (null != className)
      {
        Class factoryClass;

        if (_FACTORY_MAP.containsKey(className))
        {
          factoryClass = _FACTORY_MAP.get(className);
        }
        else
        {
          factoryClass = Class.forName(className);
          registerFactoryClass(factoryClass);
        }

        return (MALAccessControlFactory) factoryClass.newInstance();
      }
    }
    catch (ClassNotFoundException exc)
    {
      throw new MALException(exc.getLocalizedMessage(), exc);
    }
    catch (InstantiationException exc)
    {
      throw new MALException(exc.getLocalizedMessage(), exc);
    }
    catch (IllegalAccessException exc)
    {
      throw new MALException(exc.getLocalizedMessage(), exc);
    }

    return null;
  }

  /**
   * Creates an instance of an access control object.
   *
   * @param properties The QoS properties to use.
   * @return The new access control instance.
   * @throws MALException If an error detected during instantiation.
   */
  public abstract MALAccessControl createAccessControl(java.util.Map properties) throws MALException;
}
