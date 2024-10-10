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

import java.util.List;

/**
 * Holds summary information about the operations of a service.
 */
public final class ServiceSummary {

    private final int serviceNumber;
    private final List<OperationSummary> operations;

    /**
     * Constructor.
     *
     * @param serviceNumber The XML service.
     * @param operations The list of operations.
     */
    public ServiceSummary(int serviceNumber, List<OperationSummary> operations) {
        this.serviceNumber = serviceNumber;
        this.operations = operations;
    }

    /**
     * Returns the service number.
     *
     * @return the service number.
     */
    public int getServiceNumber() {
        return serviceNumber;
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
