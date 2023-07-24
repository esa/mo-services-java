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

import esa.mo.mal.encoder.zmtp.header.ZMTPHeaderDecoder;
import esa.mo.mal.encoder.zmtp.header.ZMTPHeaderEncoder;
import esa.mo.mal.transport.gen.GENMessageHeader;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 *
 * @author Dominik Marszk
 */
public class ZMTPMessageHeader extends GENMessageHeader {

    /**
     * Logger.
     */
    public static final java.util.logging.Logger LOGGER = Logger.getLogger(
            "org.ccsds.moims.mo.mal.transport.zmtp");

    /**
     * Mapping and QoS configuration to be used by this header.
     */
    protected ZMTPConfiguration configuration;

    /**
     * Used Encoding.
     * <ul>
     * <li>0 - fixed binary</li>
     * <li>1 - variable binary</li>
     * <li>2 - split binary</li>
     * <li>3 - other, in additional field</li>
     * </ul>
     */
    protected byte encodingId;

    /**
     * Extended encoding ID field
     */
    protected short encodingExtendedId;

    /**
     * Reference to ZMTP transport instance that holds the Mapping Directory.
     */
    protected ZMTPTransport transport;

    public static int ZMTP_BINDING_VERSION_NUMBER = 1;

    /**
     * Constructor.
     *
     * @param configuration The ZMTP configuration to use for this message
     * header.
     * @param transport Respective ZMTP transport that this message header is
     * being handled by.
     *
     */
    public ZMTPMessageHeader(ZMTPConfiguration configuration, ZMTPTransport transport) {
        this.configuration = configuration;
        this.transport = transport;
    }

    /**
     * Constructor.
     *
     * @param configuration The ZMTP configuration to use for this message
     * header
     * @param transport Respective ZMTP transport that this message header is
     * being handled by
     * @param uriFrom URI of the message source
     * @param authenticationId Authentication identifier of the message
     * @param uriTo URI of the message destination
     * @param timestamp Timestamp of the message
     * @param interactionType Interaction type of the operation
     * @param interactionStage Interaction stage of the interaction
     * @param transactionId Transaction identifier of the interaction, may be
     * null.
     * @param serviceArea Area number of the service
     * @param service Service number
     * @param operation Operation number
     * @param serviceVersion Service version number
     * @param isErrorMessage Flag indicating if the message conveys an error
     * @param supplements The supplements
     */
    public ZMTPMessageHeader(ZMTPConfiguration configuration, ZMTPTransport transport,
            Identifier uriFrom, Blob authenticationId, Identifier uriTo, Time timestamp,
            InteractionType interactionType, UOctet interactionStage, Long transactionId,
            UShort serviceArea, UShort service, UShort operation, UOctet serviceVersion,
            Boolean isErrorMessage, NamedValueList supplements) {
        super(uriFrom, authenticationId, uriTo, timestamp, interactionType,
                interactionStage, transactionId, serviceArea, service,
                operation, serviceVersion, isErrorMessage, supplements);

        this.configuration = configuration;
        this.transport = transport;
    }

    @Override
    public void encode(final MALEncoder hdrEncoder) throws MALException {
        if (!(hdrEncoder instanceof ZMTPHeaderEncoder)) {
            throw new MALException("Expected ZMTPHeaderEncoder as an encoder");
        }
        ZMTPHeaderEncoder encoder = (ZMTPHeaderEncoder) hdrEncoder;
        encoder.encodeUOctet(new UOctet((short) (getVersionNumberBits() | getSDUType(interactionType,
                interactionStage))));
        encoder.encodeUShort(serviceArea);
        encoder.encodeUShort(service);
        encoder.encodeUShort(operation);
        encoder.encodeUOctet(serviceVersion);
        encoder.encodeUOctet(new UOctet((short) (getErrorBit() | getQoSLevelBits() | getSessionBits())));
        encoder.encodeLong(transactionId);
        encoder.encodeUOctet(new UOctet((short) (getEncodingIdBits() | configuration.getFlags())));
        encoder.encodeIdentifier(from);
        encoder.encodeIdentifier(to);
        if (getEncodingId() == 3) {
            encoder.encodeUOctet(new UOctet(getEncodingExtendedId()));
        }
        /*
        if (configuration.isPriorityFlag()) {
            encoder.encodeVariableUInteger(priority);
        }
         */
        if (configuration.isTimestampFlag()) {
            encoder.encodeTime(timestamp);
        }
        /*
        if (configuration.isNetworkFlag()) {
            encoder.encodeIdentifier(networkZone);
        }
        if (configuration.isSessionNameFlag()) {
            encoder.encodeIdentifier(sessionName);
        }
        if (configuration.isDomainFlag()) {
            encoder.encodeElement(domain);
        }
         */
        if (configuration.isAuthFlag()) {
            encoder.encodeBlob(authenticationId);
        }
    }

    @Override
    public ZMTPMessageHeader decode(final MALDecoder hdrDecoder) throws MALException {
        if (!(hdrDecoder instanceof ZMTPHeaderDecoder)) {
            throw new MALException("Expected ZMTPHeaderDecoder as a decoder");
        }
        ZMTPHeaderDecoder decoder = (ZMTPHeaderDecoder) hdrDecoder;
        short versionAndSduType = decoder.decodeUOctet().getValue();
        int version = extractVersionNumber(versionAndSduType);
        if (version != ZMTP_BINDING_VERSION_NUMBER) {
            throw new MALException(MessageFormat.format(
                    "Mismatching ZMTP version - supported: {0}, received: {1}",
                    ZMTP_BINDING_VERSION_NUMBER,
                    version));
        }
        short sduType = (short) (versionAndSduType & 0x1F);
        interactionType = getInteractionType(sduType);
        interactionStage = getInteractionStage(sduType);
        serviceArea = decoder.decodeUShort();
        service = decoder.decodeUShort();
        operation = decoder.decodeUShort();
        serviceVersion = decoder.decodeUOctet();

        final short moHdrPt1 = decoder.decodeUOctet().getValue();
        extractError(moHdrPt1);
        //extractQoSLevel(moHdrPt1);
        //extractSession(moHdrPt1);

        transactionId = decoder.decodeLong();
        short flags = decoder.decodeUOctet().getValue();
        extractEncodingId(flags);
        from = decoder.decodeIdentifier();
        to = decoder.decodeIdentifier();

        if (getEncodingId() == 3) {
            setEncodingExtendedId(decoder.decodeUOctet().getValue());
        }
        /*
        if (0 != (flags & 0x20)) {
            priority = decoder.decodeVariableUInteger();
        } else {
            priority = new UInteger(0);
        }
         */
        if (0 != (flags & 0x10)) {
            timestamp = decoder.decodeTime();
        } else {
            timestamp = new Time(new Date().getTime());
        }
        /*
        if (0 != (flags & 0x08)) {
            networkZone = decoder.decodeIdentifier();
        } else {
            networkZone = new Identifier(configuration.getDefaultNetwork());
        }
        if (0 != (flags & 0x04)) {
            sessionName = decoder.decodeIdentifier();
        } else {
            sessionName = new Identifier(configuration.getDefaultSessionName());
        }
        if (0 != (flags & 0x02)) {
            domain = (IdentifierList) decoder.decodeElement(new IdentifierList());
        } else {
            domain = new IdentifierList();
            StringTokenizer defaultDomainTokenizer = new StringTokenizer(configuration.getDefaultDomain(),
                    ".");
            while (defaultDomainTokenizer.hasMoreTokens()) {
                domain.add(new Identifier(defaultDomainTokenizer.nextToken()));
            }
        }
         */
        if (0 != (flags & 0x01)) {
            authenticationId = decoder.decodeBlob();
        } else if (configuration.getDefaultAuth().length() > 0) {
            authenticationId = new Blob(
                    DatatypeConverter.parseBase64Binary(configuration.getDefaultAuth()));
        } else {
            authenticationId = new Blob(new byte[0]);
        }
        return this;
    }

    public ZMTPConfiguration getConfiguration() {
        return configuration;
    }

    protected static short getSDUType(InteractionType interactionType, UOctet interactionStage) {
        final short stage = (InteractionType._SEND_INDEX == interactionType.getOrdinal()) ? 0
                : interactionStage.getValue();

        switch (interactionType.getOrdinal()) {
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

    protected short getVersionNumberBits() {
        return (short) (ZMTP_BINDING_VERSION_NUMBER << 5);
    }

    protected int extractVersionNumber(final short versionAndSduType) {
        return ((versionAndSduType & 0xE0) >> 5);
    }

    protected void extractError(final short moHdrErrQosSess) {
        isErrorMessage = (0 != (moHdrErrQosSess & 0x80));
    }

    /*
    protected void extractQoSLevel(final short moHdrErrQosSess) {
        QoSlevel = QoSLevel.fromOrdinal((moHdrErrQosSess >> 4) & 0x7);
    }

    protected void extractSession(final short moHdrErrQosSess) {
        session = SessionType.fromOrdinal(moHdrErrQosSess & 0x0F);
    }
     */
    protected short getErrorBit() {
        if (isErrorMessage) {
            return 0x80;
        }
        return 0;
    }

    protected short getQoSLevelBits() {
        // return (short) ((QoSlevel.getOrdinal() & 0x7) << 4);
        return (short) ((1 & 0x7) << 4);
    }

    protected short getSessionBits() {
        // return (short) ((session.getOrdinal() & 0xF));
        return (short) ((1 & 0xF));
    }

    public short getEncodingExtendedId() {
        return encodingExtendedId;
    }

    protected short getEncodingIdBits() {
        return (short) ((getEncodingId() & 0x3) << 6);
    }

    protected void extractEncodingId(short moHdrFlags) {
        setEncodingId((byte) ((moHdrFlags >> 6) & 0x3));
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder("ZMTPMessageHeader{");
        str.append("URIFrom=");
        str.append(from);
        str.append(", authenticationId=");
        str.append(authenticationId);
        str.append(", URITo=");
        str.append(to);
        str.append(", timestamp=");
        str.append(timestamp);
        str.append(", interactionType=");
        str.append(interactionType);
        str.append(", interactionStage=");
        str.append(interactionStage);
        str.append(", transactionId=");
        str.append(transactionId);
        str.append(", serviceArea=");
        str.append(serviceArea);
        str.append(", service=");
        str.append(service);
        str.append(", operation=");
        str.append(operation);
        str.append(", serviceVersion=");
        str.append(serviceVersion);
        str.append(", isErrorMessage=");
        str.append(isErrorMessage);
        str.append(", supplements=");
        str.append(supplements);
        str.append('}');

        return str.toString();
    }

    protected static InteractionType getInteractionType(short sduType) {
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

    protected static UOctet getInteractionStage(short sduType) throws MALException {
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
        throw new MALException(MessageFormat.format(
                "ZMTPMessageHeader: Unknown SDU value ({0}) received during decoding", sduType));
    }

    /**
     * Returns the encoding ID.
     *
     * @return the encodingId.
     */
    public byte getEncodingId() {
        return encodingId;
    }

    /**
     * Sets the encoding ID.
     *
     * @param encodingId the encodingId to set.
     */
    public void setEncodingId(byte encodingId) {
        this.encodingId = encodingId;
    }

    /**
     * Sets the encoding extended ID.
     *
     * @param encodingExtendedId the encodingExtendedId to set.
     */
    public void setEncodingExtendedId(short encodingExtendedId) {
        this.encodingExtendedId = encodingExtendedId;
    }
}
