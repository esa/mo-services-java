/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen.specification;

import esa.mo.xsd.AreaType;
import esa.mo.xsd.ServiceType;
import java.util.List;

/**
 * Holds details about when a type should be created to represent the return from an operation that returns multiple
 * types.
 */
public class MultiReturnType
{
  private final String returnType;
  private final AreaType area;
  private final ServiceType service;
  private final String shortName;
  private final List<TypeInfo> returnTypes;

  /**
   * Constructor.
   *
   * @param returnType The fully qualified return type name.
   * @param area The service area of the operation.
   * @param service The service of the operation.
   * @param shortName The generated return type name.
   * @param returnTypes The types contained in the return type.
   */
  public MultiReturnType(final String returnType,
          final AreaType area,
          final ServiceType service,
          final String shortName,
          final List<TypeInfo> returnTypes)
  {
    this.returnType = returnType;
    this.area = area;
    this.service = service;
    this.shortName = shortName;
    this.returnTypes = returnTypes;
  }

  public String getReturnType()
  {
    return returnType;
  }

  public AreaType getArea()
  {
    return area;
  }

  public ServiceType getService()
  {
    return service;
  }

  public String getShortName()
  {
    return shortName;
  }

  public List<TypeInfo> getReturnTypes()
  {
    return returnTypes;
  }
}
