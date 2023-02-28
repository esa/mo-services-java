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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
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
    private final Map<String, MALOperation> operationsByName = new HashMap<>();
    private final Map<Integer, ArrayList<MALOperation>> operationsBySet = new HashMap<>();
    private final ArrayList<MALSendOperation> sendOperations = new ArrayList();
    private final ArrayList<MALSubmitOperation> submitOperations = new ArrayList();
    private final ArrayList<MALRequestOperation> requestOperations = new ArrayList();
    private final ArrayList<MALInvokeOperation> invokeOperations = new ArrayList();
    private final ArrayList<MALProgressOperation> progressOperations = new ArrayList();
    private final ArrayList<MALPubSubOperation> pubSubOperations = new ArrayList();

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
            final ArrayList<MALOperation> operations) throws java.lang.IllegalArgumentException {
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

    public Identifier getName() {
        return serviceName;
    }

    /**
     * Adds an operation to this service specification.
     *
     * @param operation The operation to add.
     * @throws java.lang.IllegalArgumentException If the argument is null.
     */
    private void addOperation(final MALOperation operation) throws java.lang.IllegalArgumentException {
        if (null != operation) {
            switch (operation.getInteractionType().getOrdinal()) {
                case InteractionType._SEND_INDEX:
                    sendOperations.add((MALSendOperation) operation);
                    break;
                case InteractionType._SUBMIT_INDEX:
                    submitOperations.add((MALSubmitOperation) operation);
                    break;
                case InteractionType._REQUEST_INDEX:
                    requestOperations.add((MALRequestOperation) operation);
                    break;
                case InteractionType._INVOKE_INDEX:
                    invokeOperations.add((MALInvokeOperation) operation);
                    break;
                case InteractionType._PROGRESS_INDEX:
                    progressOperations.add((MALProgressOperation) operation);
                    break;
                case InteractionType._PUBSUB_INDEX:
                    pubSubOperations.add((MALPubSubOperation) operation);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown MAL interaction type of "
                            + operation.getInteractionType().getOrdinal());
            }

            initOperation(operation);
        } else {
            throw new IllegalArgumentException("Operation argument must not be NULL");
        }
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
        return (MALOperation) operationsByNumber.get(opNumber.getValue());
    }

    /**
     * Return an operation identified by its name.
     *
     * @param opName The name of the operation.
     * @return The found operation or null.
     */
    public MALOperation getOperationByName(final Identifier opName) {
        return (MALOperation) operationsByName.get(opName.getValue());
    }

    /**
     * Returns a set of operations by their capability set.
     *
     * @param capabilitySet The capability set.
     * @return The set of operations or an empty array if not found.
     */
    public ArrayList<MALOperation> getOperationsByCapabilitySet(final int capabilitySet) {
        final ArrayList<MALOperation> operations = operationsBySet.get(capabilitySet);
        return (operations == null) ? new ArrayList<>() : operations;
    }

    /**
     * Returns the set of SEND operations.
     *
     * @return The set of operations or an empty array if not found.
     */
    public MALSendOperation[] getSendOperations() {
        return (MALSendOperation[]) sendOperations.toArray();
    }

    /**
     * Returns the set of SUBMIT operations.
     *
     * @return The set of operations or an empty array if not found.
     */
    public MALSubmitOperation[] getSubmitOperations() {
        return (MALSubmitOperation[]) submitOperations.toArray();
    }

    /**
     * Returns the set of REQUEST operations.
     *
     * @return The set of operations or an empty array if not found.
     */
    public MALRequestOperation[] getRequestOperations() {
        return (MALRequestOperation[]) requestOperations.toArray();
    }

    /**
     * Returns the set of INVOKE operations.
     *
     * @return The set of operations or an empty array if not found.
     */
    public MALInvokeOperation[] getInvokeOperations() {
        return (MALInvokeOperation[]) invokeOperations.toArray();
    }

    /**
     * Returns the set of PROGRESS operations.
     *
     * @return The set of operations or an empty array if not found.
     */
    public MALProgressOperation[] getProgressOperations() {
        return (MALProgressOperation[]) progressOperations.toArray();
    }

    /**
     * Returns the set of PUBSUB operations.
     *
     * @return The set of operations or an empty array if not found.
     */
    public MALPubSubOperation[] getPubSubOperations() {
        return (MALPubSubOperation[]) pubSubOperations.toArray();
    }

    private void initOperation(final MALOperation op) {
        op.setService(this);

        operationsByName.put(op.getName().getValue(), op);
        operationsByNumber.put(op.getNumber().getValue(), op);

        ArrayList<MALOperation> operations = operationsBySet.get(op.getCapabilitySet().getValue());

        if (operations == null) {
            operations = new ArrayList<>();
        }

        operations.add(op);
        operationsBySet.put(op.getCapabilitySet().getValue(), operations);
    }
}
