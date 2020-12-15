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

import esa.mo.xsd.ServiceType;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds summary information about the operations of a service.
 */
public final class ServiceSummary
{
  private final ServiceType service;
  private final boolean comService;
  private final List<OperationSummary> operations = new LinkedList<OperationSummary>();

  /**
   * Constructor.
   *
   * @param service The XML service.
   * @param isComService True if the COM service.
   */
  public ServiceSummary(ServiceType service, boolean isComService)
  {
    this.service = service;
    this.comService = isComService;
  }

  /**
   * Returns true if is the COM service.
   *
   * @return TYrue if the COM service.
   */
  public boolean isComService()
  {
    return comService;
  }

  /**
   * Returns the original service definition.
   *
   * @return the service.
   */
  public ServiceType getService()
  {
    return service;
  }

  /**
   * Returns the operations of the service.
   *
   * @return the operations.
   */
  public List<OperationSummary> getOperations()
  {
    return operations;
  }
}
