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

import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The class MALEncodingContext gives access to: the header of the MALMessage
 * that contains the Element to encode or decode; the description of the
 * operation that has been called; the index of the body element to encode or
 * decode;
 *
 */
public class MALEncodingContext {

    private static final OperationField[] ERROR_OPERATION_FIELD = new OperationField[]{
        new OperationField("errorCode", false, UInteger.UINTEGER_SHORT_FORM),
        new OperationField("extraInfo", true, null)
    };

    private final MALMessageHeader header;

    /**
     * Creates an instance.
     *
     * @param header The MAL message header.
     */
    public MALEncodingContext(final MALMessageHeader header) {
        this.header = header;
    }

    /**
     * Returns the header.
     *
     * @return the header
     */
    public MALMessageHeader getHeader() {
        return header;
    }

    public OperationField[] getOperationFields() throws NotFoundException {
        if (header.getIsErrorMessage()) {
            return ERROR_OPERATION_FIELD;
        }

        UOctet stage = header.getInteractionStage();
        return header.getMALOperation().getFieldsOnStage(stage);
    }
}
