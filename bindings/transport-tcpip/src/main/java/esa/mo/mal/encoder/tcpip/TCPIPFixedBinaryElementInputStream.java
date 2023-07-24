/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO TCP/IP Transport Framework
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
package esa.mo.mal.encoder.tcpip;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;

import esa.mo.mal.encoder.binary.fixed.FixedBinaryElementInputStream;
import esa.mo.mal.transport.tcpip.TCPIPMessageHeader;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Manage the decoding of an incoming TCPIP Message. Separate decoders are used
 * for the message header and body. The header uses a custom implementation
 * according to MAL TCPIP Transport Binding specifications, and the body is
 * split binary decoded.
 *
 * @author Rian van Gijlswijk
 *
 */
public class TCPIPFixedBinaryElementInputStream extends FixedBinaryElementInputStream {

    public TCPIPFixedBinaryElementInputStream(final java.io.InputStream is,
            final BinaryTimeHandler timeHandler) {
        super(new TCPIPFixedBinaryDecoder(is, timeHandler));
    }

    protected TCPIPFixedBinaryElementInputStream(final byte[] src, final int offset,
            final BinaryTimeHandler timeHandler) {
        super(new TCPIPFixedBinaryDecoder(src, offset, timeHandler));
    }

    @Override
    public MALMessageHeader readHeader(final MALMessageHeader header) throws MALException {
        // header is decoded using custom tcpip decoder
        return decodeHeader(header);
    }

    @Override
    public Element readElement(final Element element, final MALEncodingContext ctx)
            throws IllegalArgumentException, MALException {
        if (element == ctx.getHeader()) {
            throw new MALException("The header is no longer read here! Use: readHeader()");
        } else {
            // body is not decoded
            return null;
        }
    }

    /**
     * Decode the header
     *
     * @param element The header to decode
     * @return The decoded header
     * @throws MALException
     */
    private TCPIPMessageHeader decodeHeader(final Object element) throws MALException {
        if (!(element instanceof TCPIPMessageHeader)) {
            throw new MALException("Wrong header element supplied. "
                    + "Must be instance of TCPIPMessageHeader");
        }

        TCPIPMessageHeader headersrc = (TCPIPMessageHeader) element;

        short versionAndSDU = dec.decodeUOctet().getValue();
        short sduType = (short) (versionAndSDU & 0x1f);

        UShort serviceArea = new UShort(dec.decodeShort());
        UShort service = new UShort(dec.decodeShort());
        UShort operation = new UShort(dec.decodeShort());
        UOctet serviceVersion = dec.decodeUOctet();

        short parts = dec.decodeUOctet().getValue();
        Boolean isErrorMessage = (((parts & 0x80) >> 7) == 0x1);
        //QoSLevel qosLevel = QoSLevel.fromOrdinal(((parts & 0x70) >> 4));
        //SessionType session = SessionType.fromOrdinal(parts & 0xF);
        Long transactionId = ((TCPIPFixedBinaryDecoder) dec).decodeMALLong();

        short flags = dec.decodeUOctet().getValue(); // flags
        boolean sourceIdFlag = (((flags & 0x80) >> 7) == 0x1);
        boolean destinationIdFlag = (((flags & 0x40) >> 6) == 0x1);
        //boolean priorityFlag = (((flags & 0x20) >> 5) == 0x1);
        boolean timestampFlag = (((flags & 0x10) >> 4) == 0x1);
        //boolean networkZoneFlag = (((flags & 0x8) >> 3) == 0x1);
        //boolean sessionNameFlag = (((flags & 0x4) >> 2) == 0x1);
        //boolean domainFlag = (((flags & 0x2) >> 1) == 0x1);
        boolean authenticationIdFlag = ((flags & 0x1) == 0x1);

        short encodingId = dec.decodeUOctet().getValue();
        int bodyLength = (int) dec.decodeInteger();
        Identifier uriFrom = headersrc.getFrom();
        Identifier uriTo = headersrc.getTo();

        if (sourceIdFlag) {
            String sourceId = dec.decodeString();
            if (isURI(sourceId)) {
                uriFrom = new Identifier(sourceId);
            } else {
                String from = headersrc.getFrom() + sourceId;
                uriFrom = new Identifier(from);
            }
        }
        if (destinationIdFlag) {
            String destinationId = dec.decodeString();
            if (isURI(destinationId)) {
                uriTo = new Identifier(destinationId);
            } else {
                String to = headersrc.getTo() + destinationId;
                uriTo = new Identifier(to);
            }
        }

        //UInteger priority = (priorityFlag) ? dec.decodeUInteger() : null;
        Time timestamp = (timestampFlag) ? dec.decodeTime() : headersrc.getTimestamp();
        //Identifier networkZone = (networkZoneFlag) ? dec.decodeIdentifier() : null;
        //Identifier sessionName = (sessionNameFlag) ? dec.decodeIdentifier() : null;
        //IdentifierList domain = (domainFlag) ? (IdentifierList) new IdentifierList().decode(dec) : null;
        Blob authenticationId = (authenticationIdFlag) ? dec.decodeBlob() : new Blob();
        //NamedValueList supplements = (NamedValueList) dec.decodeNullableElement(new NamedValueList());

        TCPIPMessageHeader header = new TCPIPMessageHeader(uriFrom, headersrc.getServiceFrom(),
                authenticationId, uriTo, headersrc.getServiceTo(), timestamp,
                null, null, transactionId, serviceArea,
                service, operation, serviceVersion, isErrorMessage, new NamedValueList());

        header.setInteractionType(sduType);
        header.setInteractionStage(sduType);
        header.setEncodingId(encodingId);
        header.setBodyLength(bodyLength);

        header.decodedHeaderBytes = ((TCPIPFixedBinaryDecoder) dec).getBufferOffset();
        header.versionNumber = (versionAndSDU >> 0x5);

        // debug information
        /*
		RLOGGER.log(Level.FINEST, "Decoded header:");
		RLOGGER.log(Level.FINEST, "----------------------------------");
		RLOGGER.log(Level.FINEST, element.toString());
		RLOGGER.log(Level.FINEST, "Decoded header bytes:");
		RLOGGER.log(Level.FINEST, header.decodedHeaderBytes + "");
		RLOGGER.log(Level.FINEST, "----------------------------------");
         */
        return header;
    }

    /**
     * Is @param a URI?
     *
     * @param uri
     * @return
     */
    private boolean isURI(String uri) {
        return uri.startsWith("maltcp://");
    }
}
