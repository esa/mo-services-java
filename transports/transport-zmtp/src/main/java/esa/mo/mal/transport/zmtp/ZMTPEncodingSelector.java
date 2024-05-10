/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;

/**
 * Selector of encoding for MAL message body transmitted over ZMTP.
 *
 * Produces proper decoder for received messages and produces encoding bits for
 * transmitted messages.
 *
 * Override this class for usage of custom encoders.
 */
public class ZMTPEncodingSelector {

    public final static byte ENCODING_FIXED_BINARY_ID = 0;
    public final static byte ENCODING_VARIABLE_BINARY_ID = 1;
    public final static byte ENCODING_SPLIT_BINARY_ID = 2;
    public final static byte ENCODING_OTHER_ID = 3;

    public final static String MALZMTP_ENCODING_PROPERTY
            = MALElementStreamFactory.FACTORY_PROP_NAME_PREFIX + '.' + "malzmtp";
    public final static String INTERNAL_PROTOCOL_NAME = "zmtpencodingselector";
    public final static String INTERNAL_ENCODING_PROPERTY
            = MALElementStreamFactory.FACTORY_PROP_NAME_PREFIX + '.' + INTERNAL_PROTOCOL_NAME;

    public final static String ENCODING_FIXED_BINARY_FACTORY
            = "esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory";
    public final static String ENCODING_VARIABLE_BINARY_FACTORY
            = "esa.mo.mal.encoder.binary.variable.VariableBinaryStreamFactory";
    public final static String ENCODING_SPLIT_BINARY_FACTORY
            = "esa.mo.mal.encoder.binary.split.SplitBinaryStreamFactory";

    private Map<Integer, MALElementStreamFactory> encodingFactories = new HashMap<>();

    /**
     * Encoding ID used for outgoing messages.
     */
    private byte outboundEncodingId;
    /**
     * Encoding Extended ID used for outgoing messages.
     */
    private short outboundEncodingExtendedId;

    public void init(Map qosProperties) throws MALException {
        if (!qosProperties.containsKey(MALZMTP_ENCODING_PROPERTY)) {
            throw new MALException("Missing ZMTP outbound encoder stream factory property.");
        }

        String outboundEncoder = qosProperties.get(MALZMTP_ENCODING_PROPERTY).toString();

        if (outboundEncoder.equals(ENCODING_FIXED_BINARY_FACTORY)) {
            outboundEncodingId = ENCODING_FIXED_BINARY_ID;
        } else if (outboundEncoder.equals(ENCODING_VARIABLE_BINARY_FACTORY)) {
            outboundEncodingId = ENCODING_VARIABLE_BINARY_ID;
        } else if (outboundEncoder.equals(ENCODING_SPLIT_BINARY_FACTORY)) {
            outboundEncodingId = ENCODING_SPLIT_BINARY_ID;
        } else {
            throw new MALException(MessageFormat.format(
                    "Outbound encoder stream factory selected not "
                    + "recognized by ZMTPEncodingSelector: {0}",
                    outboundEncoder));
        }

        encodingFactories.put((int) ENCODING_FIXED_BINARY_ID, createEncodingFactory(
                ENCODING_FIXED_BINARY_FACTORY, qosProperties));
        encodingFactories.put((int) ENCODING_VARIABLE_BINARY_ID, createEncodingFactory(
                ENCODING_VARIABLE_BINARY_FACTORY, qosProperties));
        encodingFactories.put((int) ENCODING_SPLIT_BINARY_ID, createEncodingFactory(
                ENCODING_SPLIT_BINARY_FACTORY, qosProperties));
    }

    protected MALElementStreamFactory createEncodingFactory(String className,
            Map qosProperties) throws MALException {
        // Hack to force MALElementStreamFactory to produce desired factory
        System.setProperty(INTERNAL_ENCODING_PROPERTY, className);
        MALElementStreamFactory ret = MALElementStreamFactory.newFactory(
                INTERNAL_PROTOCOL_NAME, qosProperties);
        System.clearProperty(INTERNAL_ENCODING_PROPERTY);
        return ret;
    }

    /**
     * Sets the encoding ID to the header.
     *
     * @param header Outbound message header to apply the encoding id on.
     */
    public void applyEncodingIdToHeader(ZMTPMessageHeader header) {
        header.setEncodingId(outboundEncodingId);
        header.setEncodingExtendedId(outboundEncodingExtendedId);
    }

    /**
     * Selects a body decoder basing on encoding id and encoding extended id
     * from given message header.
     *
     * @param header Inbound message header to use for selection
     * @return Selected encoding stream factory
     * @throws MALException The MAL Element Stream Factory.
     */
    public MALElementStreamFactory getDecoderStreamFactory(ZMTPMessageHeader header) throws MALException {
        // Currently selected encoding (2 bit field from header)
        byte encodingId = header.getEncodingId();
        // Extended encoding ID (used when encoding id = 3)
        long encodingExtId = header.getEncodingExtendedId();
        if (encodingId < ENCODING_OTHER_ID) {
            return encodingFactories.get((int) encodingId);
        } else {
            throw new MALException(MessageFormat.format(
                    "Unsupported encoding selected - "
                    + "encodingId {0}, encodingExtId {1}",
                    encodingId,
                    encodingExtId));
        }
    }

}
