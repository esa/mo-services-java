/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO File Transport Framework
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
package esa.mo.mal.transport.file;

import esa.mo.mal.transport.gen.GENMessage;
import java.io.InputStream;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extension of the GEN message class for incoming file messages. Closes the
 * input stream after reading.
 */
public class FileBasedMessage extends GENMessage {

    private final InputStream is;

    /**
     * Constructor.
     *
     * @param qosProperties The QoS properties for this message.
     * @param ios The message in encoded form.
     * @param encFactory The stream factory to use for decoding.
     * @throws MALException On decoding error.
     */
    public FileBasedMessage(Map qosProperties, InputStream ios,
            MALElementStreamFactory encFactory) throws MALException {
        super(false, true, new MALMessageHeader(), qosProperties, ios, encFactory);
        is = ios;
    }
}
