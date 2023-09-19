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
package org.ccsds.moims.mo.mal.encoding;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The class MALEncodingContext gives access to: the header of the MALMessage
 * that contains the Element to encode or decode; the description of the
 * operation that has been called; the index of the body element to encode or
 * decode; the list of the QoS properties owned by the MALEndpoint that sends or
 * receives the Element; the list of the QoS properties owned by the MALMessage
 * that contains the Element to encode or decode.
 *
 */
public class MALEncodingContext {

    private static final OperationField[] ERROR_OPERATION_FIELD = new OperationField[]{
        new OperationField("errorCode", false, UInteger.UINTEGER_SHORT_FORM),
        new OperationField("extraInfo", true, null)
    };

    private final MALMessageHeader header;
    private MALOperation operation;

    /**
     * Creates an instance.
     *
     * @param header The MAL message header.
     * @param operation The MAL operation
     */
    public MALEncodingContext(final MALMessageHeader header, final MALOperation operation) {
        this.header = header;
        this.operation = operation;
    }

    /**
     * Returns the header.
     *
     * @return the header
     */
    public MALMessageHeader getHeader() {
        return header;
    }

    /**
     * Returns the operation.
     *
     * @return the operation
     */
    private MALOperation getOperation() {
        if (operation != null) {
            return operation;
        }

        MALArea area = MALContextFactory.lookupArea(header.getServiceArea(), header.getServiceVersion());

        if (area == null) {
            Logger.getLogger(MALEncodingContext.class.getName()).log(Level.SEVERE,
                    "Operation for unknown area/version received ({0}, {1})",
                    new Object[]{header.getServiceArea(), header.getServiceVersion()});
            return null;
        }

        MALService service = area.getServiceByNumber(header.getService());

        if (service == null) {
            Logger.getLogger(MALEncodingContext.class.getName()).log(Level.SEVERE,
                    "Service for unknown area/version/service received ({0}, {1}, {2})",
                    new Object[]{
                        header.getServiceArea(), header.getServiceVersion(), header.getService()
                    });
            return null;
        }

        MALOperation op = service.getOperationByNumber(header.getOperation());

        if (op == null) {
            Logger.getLogger(MALEncodingContext.class.getName()).log(Level.SEVERE,
                    "Operation for unknown area/version/service/op received ({0}, {1}, {2}, {3})",
                    new Object[]{
                        header.getServiceArea(), header.getServiceVersion(),
                        header.getService(), header.getOperation()
                    });
            return null;
        }

        operation = op;
        return operation;
    }

    public OperationField[] getOperationFields() {
        if (header.getIsErrorMessage()) {
            return ERROR_OPERATION_FIELD;
        }

        UOctet stage = header.getInteractionStage();
        return this.getOperation().getFieldsOnStage(stage);
    }
}
