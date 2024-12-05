/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
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
package esa.mo.tools.stubgen;

import esa.mo.tools.stubgen.specification.OperationSummary;

/**
 * Small class holding required publisher details.
 */
public class RequiredPublisher {

    private final String area;
    private final String service;
    private final OperationSummary operation;

    public RequiredPublisher(String area, String service, OperationSummary operation) {
        this.area = area;
        this.service = service;
        this.operation = operation;
    }

    public String getArea() {
        return area;
    }

    public String getService() {
        return service;
    }

    public OperationSummary getOperation() {
        return operation;
    }
}
