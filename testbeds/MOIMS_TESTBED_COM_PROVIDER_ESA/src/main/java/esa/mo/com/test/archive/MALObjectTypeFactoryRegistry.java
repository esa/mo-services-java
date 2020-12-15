/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Testbed ESA provider
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
package esa.mo.com.test.archive;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALElementFactory;

/**
 *
 * Holds a map of MALElementFactorys for the element and the associated elementList indexed on ObjectType.
 */
public class MALObjectTypeFactoryRegistry
{
  private final Map<String, ElementFactories> factoryMap = new TreeMap();

  private static MALObjectTypeFactoryRegistry instance = null;

  public static MALObjectTypeFactoryRegistry inst()
  {
    if (instance == null)
    {
      instance = new MALObjectTypeFactoryRegistry();
    }
    return instance;

  }

  /**
   * Registers a element factory in the map using the supplied short form object as the key.
   *
   * @param objectType The object type used for lookup.
   * @param elementFactory The element factory.
   * @throws IllegalArgumentException If either supplied argument is null.
   */
  public void registerElementFactories(final ObjectType objectType,
          final MALElementFactory elementFactory,
          final MALElementFactory elementListFactory)
          throws IllegalArgumentException
  {
    if ((null == elementListFactory))
    {
      throw new IllegalArgumentException("NULL argument");
    }

    factoryMap.put(objectType.toString(), new ElementFactories(elementFactory, elementListFactory));
  }

  /**
   * Returns a MALElementFactory for the supplied object Type, or null if not found.
   *
   * @param objectType The short form to search for.
   * @return The MALELementFactory or null if not found.
   * @throws IllegalArgumentException If supplied argument is null.
   */
  public MALElementFactory lookupElementFactory(final ObjectType objectType)
          throws IllegalArgumentException
  {
    if (null == objectType)
    {
      throw new IllegalArgumentException("NULL argument");
    }

    return factoryMap.get(objectType.toString()).getElementFactory();
  }

  /**
   * Returns a MALElementFactory, for the list, for the supplied object Type, or null if not found.
   *
   * @param objectType The short form to search for.
   * @return The MALELementFactory or null if not found.
   * @throws IllegalArgumentException If supplied argument is null.
   */
  public MALElementFactory lookupElementlistFactory(final ObjectType objectType)
          throws IllegalArgumentException
  {
    if (null == objectType)
    {
      throw new IllegalArgumentException("NULL argument");
    }
    if (factoryMap.get(objectType.toString()) != null)
    {
      return factoryMap.get(objectType.toString()).getElementListFactory();
    }
    else
    {
      return null;
    }
  }

  private class ElementFactories
  {
    private final MALElementFactory elementFactory;
    private final MALElementFactory elementListFactory;

    public ElementFactories(MALElementFactory elementFactory, MALElementFactory elementListFactory)
    {
      this.elementFactory = elementFactory;
      this.elementListFactory = elementListFactory;
    }

    public MALElementFactory getElementFactory()
    {
      return elementFactory;
    }

    public MALElementFactory getElementListFactory()
    {
      return elementListFactory;
    }

  }

}
