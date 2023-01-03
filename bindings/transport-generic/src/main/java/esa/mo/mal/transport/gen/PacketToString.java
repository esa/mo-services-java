/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esa.mo.mal.transport.gen;

import static esa.mo.mal.transport.gen.GENTransport.UTF8_CHARSET;
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
