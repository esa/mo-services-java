/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Encoder - XML
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
package esa.mo.mal.encoder.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.MALException;

/**
 *
 */
public class XMLStreamFactory extends MALElementStreamFactory {

    /**
     * Logger
     */
    public static final Logger RLOGGER = Logger.getLogger("org.ccsds.moims.mo.mal.encoding.xml");

    @Override
    protected void init(Map properties) throws IllegalArgumentException, MALException {
        // TODO Auto-generated method stub
    }

    @Override
    public MALElementInputStream createInputStream(InputStream is) throws IllegalArgumentException, MALException {
        return new XMLElementInputStream(is);
    }

    @Override
    public MALElementOutputStream createOutputStream(OutputStream os) throws IllegalArgumentException, MALException {
        return new XMLElementOutputStream(os);
    }

}
