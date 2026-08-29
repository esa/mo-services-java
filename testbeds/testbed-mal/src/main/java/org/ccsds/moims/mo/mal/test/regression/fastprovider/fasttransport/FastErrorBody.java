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
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;

/**
 *
 */
public class FastErrorBody extends FastBody implements MALErrorBody {

    /**
     * The name given to an error whose number is not declared by a loaded
     * service, so that the name cannot be resolved.
     */
    private static final String UNRESOLVED_ERROR_NAME = "UNRESOLVED";

    public FastErrorBody(Object[] body) {
        super(body);
    }

    @Override
    public MOErrorException getError() throws MALException {
        if (body.length > 1) {
            return new MOErrorException(UNRESOLVED_ERROR_NAME, (UInteger) body[0], body[1]);
        } else {
            return new MOErrorException(UNRESOLVED_ERROR_NAME, (UInteger) body[0], null);
        }
    }
}
