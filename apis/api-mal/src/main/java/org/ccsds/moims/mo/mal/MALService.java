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

import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The MALService class represents the specification of a service.
 */
public class MALService {

    /**
     * Number representing a non-existent service.
     */
    public static final UShort NULL_SERVICE_NUMBER = new UShort(0);

    private final Map<Integer, MALOperation> operationsByNumber = new HashMap<>();
    private final ServiceKey serviceKey;
    private final Identifier serviceName;

    /**
     * Constructs a MALService object.
     *
     * @param serviceKey The key of the service.
     * @param serviceName The name of the service.
     * @param operations The operations of the service.
     * @throws java.lang.IllegalArgumentException If any arguments are null.
     */
    public MALService(final ServiceKey serviceKey, final Identifier serviceName,
            final MALOperation[] operations) throws java.lang.IllegalArgumentException {
        if (serviceKey == null) {
            throw new IllegalArgumentException("Number argument must not be NULL");
        }
        if (operations == null) {
            throw new IllegalArgumentException("Name argument must not be NULL");
        }

        this.serviceKey = serviceKey;
        this.serviceName = serviceName;

        for (MALOperation operation : operations) {
            this.addOperation(operation);
        }
    }

    /**
     * Adds an operation to this service specification.
     *
     * @param operation The operation to add.
     * @throws java.lang.IllegalArgumentException If the argument is null.
     */
    private void addOperation(final MALOperation operation) throws java.lang.IllegalArgumentException {
        if (operation == null) {
            throw new IllegalArgumentException("Operation argument must not be NULL");
        }

        operation.setService(this);
        operationsByNumber.put(operation.getNumber().getValue(), operation);
    }

    public Identifier getName() {
        return serviceName;
    }

    /**
     * Returns the number of the service.
     *
     * @return The service number.
     */
    public UShort getServiceNumber() {
        return serviceKey.getServiceNumber();
    }

    /**
     * Returns the area number of the service.
     *
     * @return The area number.
     */
    public UShort getAreaNumber() {
        return serviceKey.getAreaNumber();
    }

    /**
     * Returns the version number of the service.
     *
     * @return The version number.
     */
    public UOctet getServiceVersion() {
        return serviceKey.getServiceVersion();
    }

    /**
     * Returns the service key of the service.
     *
     * @return The service key.
     */
    public ServiceKey getserviceKey() {
        return serviceKey;
    }

    /**
     * Return an operation identified by its number.
     *
     * @param opNumber The number of the operation.
     * @return The found operation or null.
     */
    public MALOperation getOperationByNumber(final UShort opNumber) {
        return operationsByNumber.get(opNumber.getValue());
    }
}
