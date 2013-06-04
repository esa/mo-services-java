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
package org.ccsds.moims.mo.mal;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * A factory of MAL context objects.
 */
public abstract class MALContextFactory
{
  /**
   * The Java property that is used to determine the correct implementation of the factory to use.
   */
  public static final String MAL_FACTORY_PROPERTY = "org.ccsds.moims.mo.mal.factory.class";
  /**
   * The default implementation to use.
   */
  public static final String MAL_DEFAULT_FACTORY = "esa.mo.mal.impl.MALContextFactoryImpl";
  private static final Map _FACTORY_MAP = new TreeMap();
  private static final Map<String, MALArea> _AREA_MAP = new TreeMap();
  private static final Map<Integer, MALArea> _AREA_NUMBER_MAP = new TreeMap();
  private static final Map<Long, Identifier> _ERROR_MAP = new TreeMap();
  private static final MALElementFactoryRegistry _FACTORY_REGISTRY = new MALElementFactoryRegistry();

  /**
   * Registers a MALArea in the list of areas held by this context factory.
   *
   * @param area The new MALArea to register.
   * @throws IllegalArgumentException If area is null.
   * @throws MALException If area number already registered to a different MALArea instance.
   */
  public static void registerArea(final MALArea area) throws IllegalArgumentException, MALException
  {
    if (null == area)
    {
      throw new IllegalArgumentException("NULL area argument");
    }

    final Integer num = area.getNumber().getValue();
    final MALArea currentMapping = _AREA_NUMBER_MAP.get(num);

    if ((null != currentMapping) && (currentMapping != area))
    {
      throw new MALException("MALArea already registered with a different instance");
    }

    _AREA_MAP.put(area.getName().getValue(), area);
    _AREA_NUMBER_MAP.put(num, area);
  }

  /**
   * Registers an Error number to an Error name.
   *
   * @param errorNumber The number to use.
   * @param errorName The matching name to the number.
   * @throws java.lang.IllegalArgumentException if either is null.
   * @throws MALException If already registered the number.
   */
  public static void registerError(final UInteger errorNumber, final Identifier errorName)
          throws java.lang.IllegalArgumentException, MALException
  {
    if ((null == errorNumber) || (null == errorName))
    {
      throw new IllegalArgumentException("NULL argument");
    }

    synchronized (_ERROR_MAP)
    {
      final Long num = errorNumber.getValue();
      final Identifier currentMapping = _ERROR_MAP.get(num);

      if ((null != currentMapping) && (currentMapping.equals(errorName)))
      {
        throw new MALException("Error already registered with a different name");
      }

      _ERROR_MAP.put(errorNumber.getValue(), errorName);
    }
  }

  /**
   * Look up a MALArea from an area name.
   *
   * @param areaName The area name to search for.
   * @param version The version of the area to find.
   * @return The matched MALArea or null if not found.
   * @throws IllegalArgumentException If an argument is null.
   */
  public static MALArea lookupArea(final Identifier areaName, final UOctet version) throws IllegalArgumentException
  {
    if (null == areaName)
    {
      throw new IllegalArgumentException("NULL area argument");
    }
    if (null == version)
    {
      throw new IllegalArgumentException("NULL version argument");
    }

    return (MALArea) _AREA_MAP.get(areaName.getValue());
  }

  /**
   * Look up a MALArea from an area number.
   *
   * @param areaNumber The area number to search for.
   * @param version The version of the area to find.
   * @return The matched MALArea or null if not found.
   * @throws IllegalArgumentException If an argument is null.
   */
  public static MALArea lookupArea(final UShort areaNumber, final UOctet version) throws IllegalArgumentException
  {
    if (null == areaNumber)
    {
      throw new IllegalArgumentException("NULL area argument");
    }
    if (null == version)
    {
      throw new IllegalArgumentException("NULL version argument");
    }

    return (MALArea) _AREA_NUMBER_MAP.get(areaNumber.getValue());
  }

  /**
   * Look up an error name from its number.
   *
   * @param errorNumber The error name.
   * @return The error number or null if not found.
   */
  public static Identifier lookupError(final UInteger errorNumber)
  {
    return _ERROR_MAP.get(errorNumber.getValue());
  }

  /**
   * Returns the element factory registry.
   *
   * @return The registry.
   */
  public static MALElementFactoryRegistry getElementFactoryRegistry()
  {
    return _FACTORY_REGISTRY;
  }

  /**
   * Allows a specific implementation of this class to be registered where multiple class loaders can cause
   * instantiation problems.
   *
   * @param factoryClass The factory class to register.
   * @throws IllegalArgumentException if the parameter does not extend MALContextFactory or is null.
   */
  public static void registerFactoryClass(final Class factoryClass) throws IllegalArgumentException
  {
    if (null != factoryClass)
    {
      if (!MALContextFactory.class.isAssignableFrom(factoryClass))
      {
        throw new IllegalArgumentException("Supplied factory class does not extend MALContextFactory: "
                + factoryClass.getName());
      }

      _FACTORY_MAP.put(factoryClass.getName(), factoryClass);
    }
    else
    {
      throw new IllegalArgumentException("NULL argument");
    }
  }

  /**
   * Deregisters a previously registered factory class implementation. Does nothing if passed null.
   *
   * @param factoryClass The class to deregister
   * @throws IllegalArgumentException if the parameter does not extend MALContextFactory.
   */
  public static void deregisterFactoryClass(final java.lang.Class factoryClass) throws IllegalArgumentException
  {
    if (null != factoryClass)
    {
      if (!MALContextFactory.class.isAssignableFrom(factoryClass))
      {
        throw new IllegalArgumentException("Supplied factory class does not extend MALContextFactory: "
                + factoryClass.getName());
      }

      _FACTORY_MAP.remove(factoryClass.getName());
    }
    else
    {
      throw new IllegalArgumentException("NULL argument");
    }
  }

  /**
   * Creates and returns a new instance of the factory class identified by the property.
   *
   * @return The new instance.
   * @throws MALException If there is a problem instantiating the new instance.
   */
  public static MALContextFactory newFactory() throws MALException
  {
    try
    {
      final String malfactoryClassName = System.getProperty(MAL_FACTORY_PROPERTY, MAL_DEFAULT_FACTORY);

      Class malFactoryClass;

      if (_FACTORY_MAP.containsKey(malfactoryClassName))
      {
        malFactoryClass = (Class) _FACTORY_MAP.get(malfactoryClassName);
      }
      else
      {
        malFactoryClass = Class.forName(malfactoryClassName);
        registerFactoryClass(malFactoryClass);
      }

      return (MALContextFactory) malFactoryClass.newInstance();
    }
    catch (ClassNotFoundException e)
    {
      throw new MALException(e.getLocalizedMessage(), e);
    }
    catch (InstantiationException e)
    {
      throw new MALException(e.getLocalizedMessage(), e);
    }
    catch (IllegalAccessException e)
    {
      throw new MALException(e.getLocalizedMessage(), e);
    }
  }

  /**
   * Creates a MAL Context instance.
   *
   * @param props Properties required by the specific MALContext implementation
   * @return The new MALContext instance.
   * @throws MALException If there is a problem instantiating the new instance.
   */
  public abstract MALContext createMALContext(java.util.Map props) throws MALException;
}
