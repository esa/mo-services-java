/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO SPP Transport Framework
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
package esa.mo.mal.encoder.spp;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Implements the MALElementStreamFactory interface for a SPP binary encoding.
 */
public class SPPVarBinaryStreamFactory extends esa.mo.mal.encoder.binary.variable.VariableBinaryStreamFactory {

    @Override
    protected void init(final Map properties) throws IllegalArgumentException, MALException {
        super.init(properties);

        // Override default binary time encoding handler
        timeHandler = new SPPTimeHandler(properties);
    }
}
