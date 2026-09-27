/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.regression.fastprovider.fasttransport;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.UndefinedError;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 *
 */
public class FastErrorBody extends FastBody implements MALErrorBody {


    private final MALMessageHeader header;

    public FastErrorBody(MALMessageHeader header, Object[] body) {
        super(body);
        this.header = header;
    }

    @Override
    public MOErrorException getError() throws MALException {
        try {
            return header.getServiceInfo().errorOf(header.getOperation().getValue(),
                    getErrorNumber(), getExtraInformation());
        } catch (NotFoundException ex) {
            return new UndefinedError(getErrorNumber(), getExtraInformation());
        }
    }

    @Override
    public UInteger getErrorNumber() throws MALException {
        return (UInteger) body[0];
    }

    @Override
    public Object getExtraInformation() throws MALException {
        return (body.length > 1) ? body[1] : null;
    }
}
