/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.state;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEncodedElement;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;

/**
 * Used when returning an internally generated error.
 */
public final class DummyErrorBody implements MALErrorBody {

    private final MALStandardError error;

    /**
     * Constructor.
     *
     * @param error Error.
     */
    protected DummyErrorBody(MALStandardError error) {
        this.error = error;
    }

    @Override
    public MALStandardError getError() throws MALException {
        return error;
    }

    @Override
    public int getElementCount() {
        return 1;
    }

    @Override
    public Object getBodyElement(int index, Object element) throws MALException {
        return error;
    }

    @Override
    public MALEncodedElement getEncodedBodyElement(int index) throws MALException {
        return null;
    }

    @Override
    public MALEncodedBody getEncodedBody() throws MALException {
        return null;
    }
}
