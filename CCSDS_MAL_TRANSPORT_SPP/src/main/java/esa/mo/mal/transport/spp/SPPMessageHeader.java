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
package esa.mo.mal.transport.spp;

import esa.mo.mal.transport.gen.GENMessageHeader;
import java.util.Date;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInvokeOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * An implementation of the message header interface for SPP.
 */
public class SPPMessageHeader extends GENMessageHeader
{
  private final int primaryApidQualifier;
  private final SPPConfiguration configuration;
  private final SPPURIRepresentation uriRepresentation;
  private final SPPSourceSequenceCounter ssCounter;
  private short ssc = -1;

  /**
   * Constructor.
   *
   * @param primaryApidQualifier The APID qualifier to use for the one that will be missing from the encoded packet
   * @param uriRep Interface used to convert from URI to SPP APID etc
   * @param ssCounter Interface used to get the SPP source sequence count
   *
   */
  public SPPMessageHeader(SPPConfiguration configuration, int primaryApidQualifier, SPPURIRepresentation uriRep, SPPSourceSequenceCounter ssCounter)
  {
    this.configuration = configuration;
    this.primaryApidQualifier = primaryApidQualifier;
    this.uriRepresentation = uriRep;
    this.ssCounter = ssCounter;
  }

  /**
   * Constructor.
   *
   * @param primaryApidQualifier The APID qualifier to use for the one that will be missing from the encoded packet
   * @param uriRep Interface used to convert from URI to SPP APID etc
   * @param ssCounter Interface used to get the SPP source sequence count
   * @param uriFrom URI of the message source
   * @param authenticationId Authentication identifier of the message
   * @param uriTo URI of the message destination
   * @param timestamp Timestamp of the message
   * @param qosLevel QoS level of the message
   * @param priority Priority of the message
   * @param domain Domain of the service provider
   * @param networkZone Network zone of the service provider
   * @param session Session of the service provider
   * @param sessionName Session name of the service provider
   * @param interactionType Interaction type of the operation
   * @param interactionStage Interaction stage of the interaction
   * @param transactionId Transaction identifier of the interaction, may be null.
   * @param serviceArea Area number of the service
   * @param service Service number
   * @param operation Operation number
   * @param serviceVersion Service version number
   * @param isErrorMessage Flag indicating if the message conveys an error
   */
  public SPPMessageHeader(SPPConfiguration configuration, int primaryApidQualifier, SPPURIRepresentation uriRep, SPPSourceSequenceCounter ssCounter, URI uriFrom, Blob authenticationId, URI uriTo, Time timestamp, QoSLevel qosLevel, UInteger priority, IdentifierList domain, Identifier networkZone, SessionType session, Identifier sessionName, InteractionType interactionType, UOctet interactionStage, Long transactionId, UShort serviceArea, UShort service, UShort operation, UOctet serviceVersion, Boolean isErrorMessage)
  {
    super(uriFrom, authenticationId, uriTo, timestamp, qosLevel, priority, domain, networkZone, session, sessionName, interactionType, interactionStage, transactionId, serviceArea, service, operation, serviceVersion, isErrorMessage);

    this.configuration = configuration;
    this.primaryApidQualifier = primaryApidQualifier;
    this.uriRepresentation = uriRep;
    this.ssCounter = ssCounter;
  }

  @Override
  public Element createElement()
  {
    return new SPPMessageHeader(configuration, primaryApidQualifier, uriRepresentation, ssCounter);
  }

  @Override
  public void encode(final MALEncoder encoder) throws MALException
  {
    short pktType = getPacketType();
    short primaryApid;
    int secondaryApidQualifier;
    short secondaryApid;

    if (0 == pktType)
    {
      //TM
      primaryApid = uriRepresentation.getApid(URIFrom);
      secondaryApidQualifier = uriRepresentation.getQualifier(URITo);
      secondaryApid = uriRepresentation.getApid(URITo);
    }
    else
    {
      // TC
      primaryApid = uriRepresentation.getApid(URITo);
      secondaryApidQualifier = uriRepresentation.getQualifier(URIFrom);
      secondaryApid = uriRepresentation.getApid(URIFrom);
    }

    // CCSDS packet header
    encoder.encodeUShort(new UShort(pktType | 0x00000800 | primaryApid));
    int lssc = 0;
    if (null != ssCounter)
    {
      lssc = ssCounter.getNextSourceSequenceCount();
      ssc = (short) lssc;
    }
    encoder.encodeUShort(new UShort(0x0000C000 | lssc));
    encoder.encodeUShort(new UShort(0));

    // MAL SPP Header
    encoder.encodeUOctet(new UOctet(getSDUType(interactionType, interactionStage)));
    encoder.encodeUShort(serviceArea);
    encoder.encodeUShort(service);
    encoder.encodeUShort(operation);
    encoder.encodeUOctet(areaVersion);
    encoder.encodeUShort(new UShort(getErrorFlag(isErrorMessage) | getQoSLevelBits() | getSessionBits() | secondaryApid));
    encoder.encodeUShort(new UShort(secondaryApidQualifier));
    encoder.encodeLong(transactionId);

    encoder.encodeUOctet(new UOctet((short) configuration.getFlags()));

    if (configuration.isSrcSubId())
    {
      encoder.encodeUOctet(new UOctet(uriRepresentation.getSubId(URIFrom)));
    }

    if (configuration.isDstSubId())
    {
      encoder.encodeUOctet(new UOctet(uriRepresentation.getSubId(URITo)));
    }

    if (configuration.isPriority())
    {
      encoder.encodeUInteger(priority);
    }

    if (configuration.isTimestamp())
    {
      encoder.encodeTime(timestamp);
    }

    if (configuration.isNetwork())
    {
      encoder.encodeIdentifier(networkZone);
    }

    if (configuration.isSession())
    {
      encoder.encodeIdentifier(sessionName);
    }

    if (configuration.isDomain())
    {
      encoder.encodeElement(domain);
    }

    if (configuration.isAuth())
    {
      encoder.encodeBlob(authenticationId);
    }
  }

  @Override
  public Element decode(final MALDecoder decoder) throws MALException
  {
    // CCSDS packet header
    final int ccsdsHdrPt1 = decoder.decodeUShort().getValue();
    final int ccsdsHdrPt2 = decoder.decodeUShort().getValue();
    decoder.decodeUShort();
    ssc = (short) (ccsdsHdrPt2 & 0x3FFF);

    // MAL SPP Header
    short sduType = decoder.decodeUOctet().getValue();
    serviceArea = decoder.decodeUShort();
    service = decoder.decodeUShort();
    operation = decoder.decodeUShort();
    areaVersion = decoder.decodeUOctet();
    final int moHdrPt1 = decoder.decodeUShort().getValue();
    int apidQualifier = decoder.decodeUShort().getValue();
    transactionId = decoder.decodeLong();
    if (0 == transactionId)
    {
      transactionId = (long) ssc;
    }
    short flags = decoder.decodeUOctet().getValue();
    Short sourceSubId = null;
    Short destSubId = null;

    if (0 != (flags & 0x80))
    {
      sourceSubId = decoder.decodeUOctet().getValue();
    }
    if (0 != (flags & 0x40))
    {
      destSubId = decoder.decodeUOctet().getValue();
    }
    if (0 != (flags & 0x20))
    {
      priority = decoder.decodeUInteger();
    }
    else
    {
      priority = new UInteger(0);
    }
    if (0 != (flags & 0x10))
    {
      timestamp = decoder.decodeTime();
    }
    else
    {
      timestamp = new Time(new Date().getTime());
    }
    if (0 != (flags & 0x08))
    {
      networkZone = decoder.decodeIdentifier();
    }
    else
    {
      networkZone = null;
    }
    if (0 != (flags & 0x04))
    {
      sessionName = decoder.decodeIdentifier();
    }
    else
    {
      sessionName = new Identifier("LIVE");
    }
    if (0 != (flags & 0x02))
    {
      domain = (IdentifierList) decoder.decodeElement(new IdentifierList());
    }
    else
    {
      domain = new IdentifierList();
    }
    if (0 != (flags & 0x01))
    {
      authenticationId = decoder.decodeBlob();
    }
    else
    {
      authenticationId = null;
    }

    boolean isTC = 0 != (0x00001000 & ccsdsHdrPt1);
    Integer sourceQualifier;
    short sourceApid;
    Integer destQualifier;
    short destApid;

    if (isTC)
    {
      //TC
      sourceQualifier = apidQualifier;
      sourceApid = (short) (moHdrPt1 & 0x7FF);
      destQualifier = primaryApidQualifier;
      destApid = (short) (ccsdsHdrPt1 & 0x7FF);
    }
    else
    {
      // TM
      sourceQualifier = primaryApidQualifier;
      sourceApid = (short) (ccsdsHdrPt1 & 0x7FF);
      destQualifier = apidQualifier;
      destApid = (short) (moHdrPt1 & 0x7FF);
    }

    URIFrom = uriRepresentation.getURI(sourceQualifier, sourceApid, sourceSubId);
    URITo = uriRepresentation.getURI(destQualifier, destApid, destSubId);

    QoSlevel = QoSLevel.fromOrdinal((moHdrPt1 & 0x6000) >> 13);
    session = SessionType.fromOrdinal((moHdrPt1 & 0x1800) >> 11);
    interactionType = getInteractionType(sduType);
    interactionStage = getInteractionStage(sduType);
    isErrorMessage = 0 != (moHdrPt1 & 0x8000);

    return this;
  }

  @Override
  public String toString()
  {
    final StringBuilder str = new StringBuilder("SPPMessageHeader{");
    str.append("URIFrom=");
    str.append(URIFrom);
    str.append(", authenticationId=");
    str.append(authenticationId);
    str.append(", URITo=");
    str.append(URITo);
    str.append(", timestamp=");
    str.append(timestamp);
    str.append(", QoSlevel=");
    str.append(QoSlevel);
    str.append(", priority=");
    str.append(priority);
    str.append(", domain=");
    str.append(domain);
    str.append(", networkZone=");
    str.append(networkZone);
    str.append(", session=");
    str.append(session);
    str.append(", sessionName=");
    str.append(sessionName);
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
    str.append(areaVersion);
    str.append(", isErrorMessage=");
    str.append(isErrorMessage);
    str.append('}');

    return str.toString();
  }

  protected static int getErrorFlag(boolean isError)
  {
    if (isError)
    {
      return 0x00008000;
    }

    return 0;
  }

  protected int getQoSLevelBits()
  {
    return QoSlevel.getOrdinal() << 13;
  }

  protected int getSessionBits()
  {
    return session.getOrdinal() << 11;
  }

  public int getApidQualifier()
  {
    if (0 == getPacketType())
    {
      //TM
      return uriRepresentation.getQualifier(URIFrom);
    }
    else
    {
      // TC
      return uriRepresentation.getQualifier(URITo);
    }
  }

  public short getApid()
  {
    if (0 == getPacketType())
    {
      //TM
      return uriRepresentation.getApid(URIFrom);
    }
    else
    {
      // TC
      return uriRepresentation.getApid(URITo);
    }
  }

  public short getSSC()
  {
    if (-1 == ssc)
    {
      return transactionId.shortValue();
    }

    return ssc;
  }

  public short getPacketType()
  {
    switch (interactionType.getOrdinal())
    {
      case InteractionType._SEND_INDEX:
        return 0x00001000;
      case InteractionType._SUBMIT_INDEX:
        if (MALSubmitOperation._SUBMIT_STAGE == interactionStage.getValue())
        {
          return 0x00001000;
        }
        return 0;
      case InteractionType._REQUEST_INDEX:
        if (MALRequestOperation._REQUEST_STAGE == interactionStage.getValue())
        {
          return 0x00001000;
        }
        return 0;
      case InteractionType._INVOKE_INDEX:
        if (MALInvokeOperation._INVOKE_STAGE == interactionStage.getValue())
        {
          return 0x00001000;
        }
        return 0;
      case InteractionType._PROGRESS_INDEX:
      {
        if (MALProgressOperation._PROGRESS_STAGE == interactionStage.getValue())
        {
          return 0x00001000;
        }
        return 0;
      }
      case InteractionType._PUBSUB_INDEX:
      {
        switch (interactionStage.getValue())
        {
          case MALPubSubOperation._REGISTER_STAGE:
          case MALPubSubOperation._DEREGISTER_STAGE:
          case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
          case MALPubSubOperation._PUBLISH_DEREGISTER_STAGE:
            return 0x00001000;
        }
        return 0;
      }
    }

    return 0;
  }

  protected static short getSDUType(InteractionType interactionType, UOctet interactionStage)
  {
    final short stage = (InteractionType._SEND_INDEX == interactionType.getOrdinal()) ? 0 : interactionStage.getValue();

    switch (interactionType.getOrdinal())
    {
      case InteractionType._SEND_INDEX:
        return 0;
      case InteractionType._SUBMIT_INDEX:
        if (MALSubmitOperation._SUBMIT_STAGE == stage)
        {
          return 1;
        }
        return 2;
      case InteractionType._REQUEST_INDEX:
        if (MALRequestOperation._REQUEST_STAGE == stage)
        {
          return 3;
        }
        return 4;
      case InteractionType._INVOKE_INDEX:
        if (MALInvokeOperation._INVOKE_STAGE == stage)
        {
          return 5;
        }
        else if (MALInvokeOperation._INVOKE_ACK_STAGE == stage)
        {
          return 6;
        }
        return 7;
      case InteractionType._PROGRESS_INDEX:
      {
        if (MALProgressOperation._PROGRESS_STAGE == stage)
        {
          return 8;
        }
        if (MALProgressOperation._PROGRESS_ACK_STAGE == stage)
        {
          return 9;
        }
        else if (MALProgressOperation._PROGRESS_UPDATE_STAGE == stage)
        {
          return 10;
        }
        return 11;
      }
      case InteractionType._PUBSUB_INDEX:
      {
        switch (stage)
        {
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

  protected static InteractionType getInteractionType(short sduType)
  {
    switch (sduType)
    {
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

  protected static UOctet getInteractionStage(short sduType)
  {
    switch (sduType)
    {
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

    return null;
  }
}
