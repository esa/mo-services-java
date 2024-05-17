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
package esa.mo.mal.transport.tcpip;

import esa.mo.mal.encoder.tcpip.TCPIPFixedBinaryDecoder;
import esa.mo.mal.encoder.tcpip.TCPIPFixedBinaryEncoder;
import static esa.mo.mal.transport.tcpip.TCPIPTransport.RLOGGER;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

public class TCPIPMessageHeader extends MALMessageHeader {

    private static final long serialVersionUID = 1L;

    public int versionNumber = 1;

    private short encodingId = 0;

    private int totalLength = 0;

    private byte[] remainingEncodedData;

    private String serviceFrom;

    private String serviceTo;

    private int decodedHeaderOffset = 0;

    public TCPIPMessageHeader() {
    }

    public TCPIPMessageHeader(Identifier uriFrom, Identifier uriTo) {
        this.from = uriFrom;
        this.to = uriTo;
    }

    public TCPIPMessageHeader(Identifier uriFrom, String serviceFrom,
            Blob authenticationId, Identifier uriTo, String serviceTo, Time timestamp,
            InteractionType interactionType, UOctet interactionStage,
            Long transactionId, UShort serviceArea, UShort service,
            UShort operation, UOctet serviceVersion,
            Boolean isErrorMessage, NamedValueList supplements) {
        super(uriFrom, authenticationId, uriTo, timestamp, interactionType,
                interactionStage, transactionId, serviceArea, service,
                operation, serviceVersion, isErrorMessage, supplements);
        this.serviceFrom = serviceFrom;
        this.serviceTo = serviceTo;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public int getDecodedHeaderOffset() {
        return decodedHeaderOffset;
    }

    @Deprecated
    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public String getServiceFrom() {
        return serviceFrom;
    }

    public String getServiceTo() {
        return serviceTo;
    }

    public byte[] getRemainingEncodedData() {
        return remainingEncodedData;
    }

    public short getEncodingId() {
        return encodingId;
    }

    @Deprecated
    private void setEncodingId(short encodingId) {
        this.encodingId = encodingId;
    }

    @Override
    public TCPIPMessageHeader decode(final MALDecoder decoder) throws MALException {
        TCPIPFixedBinaryDecoder dec = (TCPIPFixedBinaryDecoder) decoder;
        this.totalLength = (int) dec.decodeInteger();
        short versionAndSDU = dec.decodeUOctet().getValue();
        short sduType = (short) (versionAndSDU & 0x1f);

        this.serviceArea = new UShort(dec.decodeShort());
        this.service = new UShort(dec.decodeShort());
        this.operation = new UShort(dec.decodeShort());
        this.areaVersion = dec.decodeUOctet();

        short parts = dec.decodeUOctet().getValue();
        this.isErrorMessage = (((parts & 0x80) >> 7) == 0x1);
        //QoSLevel qosLevel = QoSLevel.fromOrdinal(((parts & 0x70) >> 4));
        //SessionType session = SessionType.fromOrdinal(parts & 0xF);
        this.transactionId = dec.decodeMALLong();

        short flags = dec.decodeUOctet().getValue(); // flags
        boolean sourceIdFlag = (((flags & 0x80) >> 7) == 0x1);
        boolean destinationIdFlag = (((flags & 0x40) >> 6) == 0x1);
        //boolean priorityFlag = (((flags & 0x20) >> 5) == 0x1);
        boolean timestampFlag = (((flags & 0x10) >> 4) == 0x1);
        //boolean networkZoneFlag = (((flags & 0x8) >> 3) == 0x1);
        //boolean sessionNameFlag = (((flags & 0x4) >> 2) == 0x1);
        //boolean domainFlag = (((flags & 0x2) >> 1) == 0x1);
        boolean authenticationIdFlag = ((flags & 0x1) == 0x1);

        this.encodingId = dec.decodeUOctet().getValue();
        this.supplements = new NamedValueList();
        this.supplements = (NamedValueList) supplements.decode(dec);

        Identifier uriFrom = this.getFrom();
        Identifier uriTo = this.getTo();

        if (sourceIdFlag) {
            String sourceId = dec.decodeString();
            if (isURI(sourceId)) {
                uriFrom = new Identifier(sourceId);
            } else {
                uriFrom = new Identifier(this.getFrom() + sourceId);
            }
        }
        if (destinationIdFlag) {
            String destinationId = dec.decodeString();
            if (isURI(destinationId)) {
                uriTo = new Identifier(destinationId);
            } else {
                uriTo = new Identifier(this.getTo() + destinationId);
            }
        }

        //UInteger priority = (priorityFlag) ? dec.decodeUInteger() : null;
        this.timestamp = (timestampFlag) ? dec.decodeTime() : this.getTimestamp();
        //Identifier networkZone = (networkZoneFlag) ? dec.decodeIdentifier() : null;
        //Identifier sessionName = (sessionNameFlag) ? dec.decodeIdentifier() : null;
        //IdentifierList domain = (domainFlag) ? (IdentifierList) new IdentifierList().decode(dec) : null;
        this.authenticationId = (authenticationIdFlag) ? dec.decodeBlob() : new Blob();
        this.interactionType = getInteractionType(sduType);

        TCPIPMessageHeader header = new TCPIPMessageHeader(uriFrom, this.getServiceFrom(),
                authenticationId, uriTo, this.getServiceTo(), timestamp,
                interactionType, getInteractionStage(sduType),
                transactionId, serviceArea,
                service, operation, areaVersion, isErrorMessage, supplements);

        header.setEncodingId(encodingId);
        header.setTotalLength(totalLength);

        header.decodedHeaderOffset = dec.getBufferOffset();
        header.versionNumber = (versionAndSDU >> 0x5);

        // debug information
        /*
		RLOGGER.log(Level.FINEST, "Decoded header:");
		RLOGGER.log(Level.FINEST, "----------------------------------");
		RLOGGER.log(Level.FINEST, header.toString());
		RLOGGER.log(Level.FINEST, "Decoded header bytes:");
		RLOGGER.log(Level.FINEST, header.decodedHeaderBytes + "");
		RLOGGER.log(Level.FINEST, "----------------------------------");
         */
        return header;
        // return this;
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

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        TCPIPFixedBinaryEncoder enc = (TCPIPFixedBinaryEncoder) encoder;
        enc.encodeInteger(totalLength);
        byte versionAndSDU = (byte) (this.versionNumber << 5 | this.getSDUType());
        enc.encodeUOctet(new UOctet(versionAndSDU));

        enc.encodeShort((short) this.getServiceArea().getValue());
        enc.encodeShort((short) this.getService().getValue());
        enc.encodeShort((short) this.getOperation().getValue());
        enc.encodeUOctet(this.getServiceVersion());

        /*
        short parts = (short) (((header.getIsErrorMessage() ? 0x1 : 0x0) << 7)
                | (header.getQoSlevel().getOrdinal() << 4)
                | header.getSession().getOrdinal());
         */
        short parts = (short) (((this.getIsErrorMessage() ? 0x1 : 0x0) << 7)
                | (1 << 4)
                | 1);

        enc.encodeUOctet(new UOctet(parts));
        enc.encodeMALLong(this.getTransactionId());

        enc.encodeUOctet(getFlags()); // set flags

        enc.encodeUOctet(new UOctet(this.getEncodingId())); // set encoding id
        supplements.encode(enc);

        // encode rest of header
        if (!this.getServiceFrom().isEmpty()) {
            enc.encodeString(this.getFrom().toString());
        }
        if (!this.getServiceTo().isEmpty()) {
            enc.encodeString(this.getTo().toString());
        }
        /*
        if (header.getPriority() != null) {
            enc.encodeUInteger(header.getPriority());
        }
         */
        if (this.getTimestamp() != null) {
            enc.encodeTime(this.getTimestamp());
        }
        /*
        if (header.getNetworkZone() != null) {
            enc.encodeIdentifier(header.getNetworkZone());
        }
        if (header.getSessionName() != null) {
            enc.encodeIdentifier(header.getSessionName());
        }
        if (header.getDomain() != null && header.getDomain().size() > 0) {
            header.getDomain().encode(enc);
        }
         */
        if (this.getAuthenticationId() != null && this.getAuthenticationId().getLength() > 0) {
            enc.encodeBlob(this.getAuthenticationId());
        }
    }

    /**
     * Set a byte which flags the optional fields that are set in the header.
     *
     * @param header
     * @return
     */
    private UOctet getFlags() {
        short result = 0;
        if (!this.getServiceFrom().isEmpty()) {
            result |= (0x1 << 7);
        }
        if (!this.getServiceTo().isEmpty()) {
            result |= (0x1 << 6);
        }
        /*
        if (this.getPriority() != null) {
            result |= (0x1 << 5);
        }
         */
        if (this.getTimestamp() != null) {
            result |= (0x1 << 4);
        }
        /*
        if (this.getNetworkZone() != null) {
            result |= (0x1 << 3);
        }
        if (this.getSessionName() != null) {
            result |= (0x1 << 2);
        }
        if (this.getDomain() != null && header.getDomain().size() > 0) {
            result |= (0x1 << 1);
        }
         */

        if (this.getAuthenticationId() != null && this.getAuthenticationId().getLength() > 0) {
            result |= 0x1;
        }

        return new UOctet(result);
    }

    public short getSDUType() {
        int type = interactionType.getOrdinal();
        final short stage = (InteractionType._SEND_INDEX == type) ? 0 : interactionStage.getValue();

        switch (type) {
            case InteractionType._SEND_INDEX:
                return 0;
            case InteractionType._SUBMIT_INDEX:
                if (MALSubmitOperation._SUBMIT_STAGE == stage) {
                    return 1;
                }
                return 2;
            case InteractionType._REQUEST_INDEX:
                if (MALRequestOperation._REQUEST_STAGE == stage) {
                    return 3;
                }
                return 4;
            case InteractionType._INVOKE_INDEX:
                if (MALInvokeOperation._INVOKE_STAGE == stage) {
                    return 5;
                } else if (MALInvokeOperation._INVOKE_ACK_STAGE == stage) {
                    return 6;
                }
                return 7;
            case InteractionType._PROGRESS_INDEX: {
                if (MALProgressOperation._PROGRESS_STAGE == stage) {
                    return 8;
                }
                if (MALProgressOperation._PROGRESS_ACK_STAGE == stage) {
                    return 9;
                } else if (MALProgressOperation._PROGRESS_UPDATE_STAGE == stage) {
                    return 10;
                }
                return 11;
            }
            case InteractionType._PUBSUB_INDEX: {
                switch (stage) {
                    case MALPubSubOperation._REGISTER_STAGE:
                        return 12;
                    case MALPubSubOperation._REGISTER_ACK_STAGE:
                        return 13;
                    case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
                        return 14;
                    case MALPubSubOperation._PUBLISH_REGISTER_ACK_STAGE:
                        return 15;
                    case MALPubSubOperation._PUBLISH_STAGE:
                        return 16;
                    case MALPubSubOperation._NOTIFY_STAGE:
                        return 17;
                    case MALPubSubOperation._DEREGISTER_STAGE:
                        return 18;
                    case MALPubSubOperation._DEREGISTER_ACK_STAGE:
                        return 19;
                    case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
                        return 20;
                    case MALPubSubOperation._PUBLISH_DEREGISTER_ACK_STAGE:
                        return 21;
                }
            }
        }

        return 0;
    }

    protected InteractionType getInteractionType(short sduType) {
        switch (sduType) {
            case 0:
                return InteractionType.SEND;
            case 1:
            case 2:
                return InteractionType.SUBMIT;
            case 3:
            case 4:
                return InteractionType.REQUEST;
            case 5:
            case 6:
            case 7:
                return InteractionType.INVOKE;
            case 8:
            case 9:
            case 10:
            case 11:
                return InteractionType.PROGRESS;
        }

        return InteractionType.PUBSUB;
    }

    protected static UOctet getInteractionStage(short sduType) {
        switch (sduType) {
            case 0:
                return new UOctet((short) 0);
            case 1:
                return MALSubmitOperation.SUBMIT_STAGE;
            case 2:
                return MALSubmitOperation.SUBMIT_ACK_STAGE;
            case 3:
                return MALRequestOperation.REQUEST_STAGE;
            case 4:
                return MALRequestOperation.REQUEST_RESPONSE_STAGE;
            case 5:
                return MALInvokeOperation.INVOKE_STAGE;
            case 6:
                return MALInvokeOperation.INVOKE_ACK_STAGE;
            case 7:
                return MALInvokeOperation.INVOKE_RESPONSE_STAGE;
            case 8:
                return MALProgressOperation.PROGRESS_STAGE;
            case 9:
                return MALProgressOperation.PROGRESS_ACK_STAGE;
            case 10:
                return MALProgressOperation.PROGRESS_UPDATE_STAGE;
            case 11:
                return MALProgressOperation.PROGRESS_RESPONSE_STAGE;
            case 12:
                return MALPubSubOperation.REGISTER_STAGE;
            case 13:
                return MALPubSubOperation.REGISTER_ACK_STAGE;
            case 14:
                return MALPubSubOperation.PUBLISH_REGISTER_STAGE;
            case 15:
                return MALPubSubOperation.PUBLISH_REGISTER_ACK_STAGE;
            case 16:
                return MALPubSubOperation.PUBLISH_STAGE;
            case 17:
                return MALPubSubOperation.NOTIFY_STAGE;
            case 18:
                return MALPubSubOperation.DEREGISTER_STAGE;
            case 19:
                return MALPubSubOperation.DEREGISTER_ACK_STAGE;
            case 20:
                return MALPubSubOperation.PUBLISH_DEREGISTER_STAGE;
            case 21:
                return MALPubSubOperation.PUBLISH_DEREGISTER_ACK_STAGE;
        }

        RLOGGER.log(Level.WARNING, "SPPMessageHeader: Unknown sdu value "
                + "received during decoding of {0}", sduType);

        return null;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("TCPIPMessageHeader{");
        str.append("totalLength=").append(totalLength);
        str.append(", versionNumber=").append(versionNumber);
        str.append(", serviceArea=").append(serviceArea);
        str.append(", service=").append(service);
        str.append(", operation=").append(operation);
        str.append(", serviceVersion=").append(areaVersion);
        str.append(", isErrorMessage=").append(isErrorMessage);
        str.append(", transactionId=").append(transactionId);
        str.append(", timestamp=").append(timestamp);
        str.append(", authenticationId=").append(authenticationId);
        str.append(", from=").append(from);
        str.append(", to=").append(to);
        str.append(", interactionType=").append(interactionType);
        str.append(", interactionStage=").append(interactionStage);
        str.append(", supplements=").append(supplements);
        str.append('}');
        return str.toString();
    }
}
