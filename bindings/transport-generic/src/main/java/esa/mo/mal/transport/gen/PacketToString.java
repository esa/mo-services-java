/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen;

import esa.mo.mal.transport.gen.util.GENHelper;
import java.util.Properties;

/**
 * Converts the packet to a string form for logging.
 *
 */
public class PacketToString {

    /**
     * True if want to log the packet data
     */
    private static Boolean logFullDebug = null;

    /**
     * True if string based stream, can be logged as a string rather than hex.
     */
    private final byte[] data;
    private String str;

    /**
     * Constructor.
     *
     * @param data the packet.
     */
    public PacketToString(byte[] data) {
        this.data = data;

        // very crude and faulty test but it will do for testing
        // Should be removed in the future...
        if (logFullDebug == null) {
            Properties properties = System.getProperties();
            String prop = properties.getProperty(GENTransport.DEBUG_PROPERTY, "false");
            logFullDebug = Boolean.valueOf(prop);
        }
    }

    @Override
    public synchronized String toString() {
        if (str != null) {
            return str;
        }

        str = "";

        if (logFullDebug && data != null) {
            str = GENHelper.byteArrayToHexString(data);
        }

        return str;
    }
}
