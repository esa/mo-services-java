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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Represents a MAL Area and holds information relating to the area.
 */
public class MALArea
{
  private final UShort number;
  private final Identifier name;
  private final UOctet version;
  private MALService[] services = new MALService[0];
  private final Map<String, MALService> serviceNames = new HashMap<String, MALService>();
  private final Map<Integer, MALService> serviceNumbers = new HashMap<Integer, MALService>();

  /**
   * MALArea constructor.
   *
   * @param number The number of the area.
   * @param name The name of the area.
   * @param version The area version.
   * @throws IllegalArgumentException If either argument is null.
   */
  public MALArea(final UShort number, final Identifier name, final UOctet version)
          throws java.lang.IllegalArgumentException
  {
    if (number == null)
    {
      throw new IllegalArgumentException("Number argument must not be NULL");
    }
    if (name == null)
    {
      throw new IllegalArgumentException("Name argument must not be NULL");
    }
    if (version == null)
    {
      throw new IllegalArgumentException("Version argument must not be NULL");
    }

    this.number = number;
    this.name = name;
    this.version = version;
  }

  /**
   * Returns the number of this Area.
   * @return The MAL Area number.
   */
  public final UShort getNumber()
  {
    return number;
  }

  /**
   * Returns the name of this Area.
   * @return The MAL Area name.
   */
  public final Identifier getName()
  {
    return name;
  }

  /**
   * Returns the version of the area.
   *
   * @return The service version.
   */
  public UOctet getVersion()
  {
    return version;
  }

  /**
   * Returns an array of MAL Services contained in this area.
   * @return The array of MAL services in this area, or an empty array if none contained.
   */
  public final MALService[] getServices()
  {
    MALService[] rv;

    synchronized (this)
    {
      rv = Arrays.copyOf(services, services.length);
    }

    return rv;
  }

  /**
   * Returns a contained service identified by its name.
   * @param serviceName The name of the service to find.
   * @return The found service or null if not found.
   */
  public MALService getServiceByName(final Identifier serviceName)
  {
    synchronized (this)
    {
      return (MALService) serviceNames.get(serviceName.getValue());
    }
  }

  /**
   * Returns a contained service identified by its number.
   * @param serviceNumber The number of the service to find.
   * @return The found service or null if not found.
   */
  public MALService getServiceByNumber(final UShort serviceNumber)
  {
    synchronized (this)
    {
      return (MALService) serviceNumbers.get(serviceNumber.getValue());
    }
  }

  /**
   * Adds a service to this Area.
   * @param service The MALService object to add.
   * @throws IllegalArgumentException Thrown if argument is NULL.
   * @throws MALException Thrown if service is already contained.
   */
  public void addService(final MALService service) throws IllegalArgumentException, MALException
  {
    synchronized (this)
    {
      if (!serviceNumbers.containsKey(service.getNumber().getValue())
              && !(serviceNames.containsKey(service.getName().getValue())))
      {
        service.setArea(this);

        final MALService[] t = new MALService[services.length + 1];
        System.arraycopy(services, 0, t, 0, services.length);
        t[services.length] = service;
        services = t;

        serviceNumbers.put(service.getNumber().getValue(), service);
        serviceNames.put(service.getName().getValue(), service);
      }
      else
      {
        throw new MALException("Service already included in area");
      }
    }
  }
}
