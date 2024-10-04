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
import java.util.List;

/**
 * Holds summary information about the operations of a service.
 */
public final class ServiceSummary {

    private final ServiceType service;
    private final List<OperationSummary> operations;

    /**
     * Constructor.
     *
     * @param service The XML service.
     * @param operations The list of operations.
     */
    public ServiceSummary(ServiceType service, List<OperationSummary> operations) {
        this.service = service;
        this.operations = operations;
    }

    /**
     * Returns the original service definition.
     *
     * @return the service.
     */
    public ServiceType getService() {
        return service;
    }

    /**
     * Returns the operations of the service.
     *
     * @return the operations.
     */
    public List<OperationSummary> getOperations() {
        return operations;
    }
}
