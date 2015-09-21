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

import esa.mo.mal.transport.gen.GENEndpoint;
import esa.mo.mal.transport.gen.GENMessageHeader;
import esa.mo.mal.transport.gen.GENTransport;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 */
public class SPPEndpoint extends GENEndpoint
{
  private final SPPConfiguration configuration;
  private final int apidQualifier;
  private final SPPURIRepresentation uriRep;
  private final SPPSourceSequenceCounter ssCounter;

  public SPPEndpoint(GENTransport transport,
          SPPConfiguration configuration,
          int apidQualifier,
          SPPURIRepresentation uriRep,
          SPPSourceSequenceCounter ssCounter,
          String localName, String routingName, String uri, boolean wrapBodyParts,
          final Map properties)
  {
    super(transport, localName, routingName, uri, wrapBodyParts);

    this.configuration = configuration;
    this.apidQualifier = apidQualifier;
    this.uriRep = uriRep;
    this.ssCounter = ssCounter;
  }

  @Override
  public MALMessage createMessage(final Blob authenticationId,
          final URI uriTo,
          final Time timestamp,
          final QoSLevel qosLevel,
          final UInteger priority,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType session,
          final Identifier sessionName,
          final InteractionType interactionType,
          final UOctet interactionStage,
          final Long transactionId,
          final UShort serviceArea,
          final UShort service,
          final UShort operation,
          final UOctet serviceVersion,
          final Boolean isErrorMessage,
          final Map qosProperties,
          final Object... body) throws MALException
  {
    try
    {
      return new SPPMessage(false, createMessageHeader(getURI(),
              authenticationId,
              uriTo,
              timestamp,
              qosLevel,
              priority,
              domain,
              networkZone,
              session,
              sessionName,
              interactionType,
              interactionStage,
              transactionId,
              serviceArea,
              service,
              operation,
              serviceVersion,
              isErrorMessage),
              qosProperties, null, body);
    }
    catch (MALInteractionException ex)
    {
      throw new MALException("Error creating message", ex);
    }
  }

  @Override
  public MALMessage createMessage(final Blob authenticationId,
          final URI uriTo,
          final Time timestamp,
          final QoSLevel qosLevel,
          final UInteger priority,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType session,
          final Identifier sessionName,
          final InteractionType interactionType,
          final UOctet interactionStage,
          final Long transactionId,
          final UShort serviceArea,
          final UShort service,
          final UShort operation,
          final UOctet serviceVersion,
          final Boolean isErrorMessage,
          final Map qosProperties,
          final MALEncodedBody body) throws MALException
  {
    try
    {
      return new SPPMessage(false, createMessageHeader(getURI(),
              authenticationId,
              uriTo,
              timestamp,
              qosLevel,
              priority,
              domain,
              networkZone,
              session,
              sessionName,
              interactionType,
              interactionStage,
              transactionId,
              serviceArea,
              service,
              operation,
              serviceVersion,
              isErrorMessage),
              qosProperties, null, body);
    }
    catch (MALInteractionException ex)
    {
      throw new MALException("Error creating message", ex);
    }
  }

  @Override
  public MALMessage createMessage(final Blob authenticationId,
          final URI uriTo,
          final Time timestamp,
          final QoSLevel qosLevel,
          final UInteger priority,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType session,
          final Identifier sessionName,
          final Long transactionId,
          final Boolean isErrorMessage,
          final MALOperation op,
          final UOctet interactionStage,
          final Map qosProperties,
          final MALEncodedBody body) throws MALException
  {
    try
    {
      return new SPPMessage(false, createMessageHeader(getURI(),
              authenticationId,
              uriTo,
              timestamp,
              qosLevel,
              priority,
              domain,
              networkZone,
              session,
              sessionName,
              op.getInteractionType(),
              interactionStage,
              transactionId,
              op.getService().getArea().getNumber(),
              op.getService().getNumber(),
              op.getNumber(),
              op.getService().getArea().getVersion(),
              isErrorMessage),
              qosProperties,
              op,
              body);
    }
    catch (MALInteractionException ex)
    {
      throw new MALException("Error creating message", ex);
    }
  }

  @Override
  public MALMessage createMessage(final Blob authenticationId,
          final URI uriTo,
          final Time timestamp,
          final QoSLevel qosLevel,
          final UInteger priority,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType session,
          final Identifier sessionName,
          final Long transactionId,
          final Boolean isErrorMessage,
          final MALOperation op,
          final UOctet interactionStage,
          final Map qosProperties,
          final Object... body) throws MALException
  {
    try
    {
      return new SPPMessage(false, createMessageHeader(getURI(),
              authenticationId,
              uriTo,
              timestamp,
              qosLevel,
              priority,
              domain,
              networkZone,
              session,
              sessionName,
              op.getInteractionType(),
              interactionStage,
              transactionId,
              op.getService().getArea().getNumber(),
              op.getService().getNumber(),
              op.getNumber(),
              op.getService().getArea().getVersion(),
              isErrorMessage),
              qosProperties,
              op,
              body);
    }
    catch (MALInteractionException ex)
    {
      throw new MALException("Error creating message", ex);
    }
  }

  @Override
  public GENMessageHeader createMessageHeader(URI uriFrom,
          Blob authenticationId,
          URI uriTo,
          Time timestamp,
          QoSLevel qosLevel,
          UInteger priority,
          IdentifierList domain,
          Identifier networkZone,
          SessionType session,
          Identifier sessionName,
          InteractionType interactionType,
          UOctet interactionStage,
          Long transactionId,
          UShort serviceArea,
          UShort service,
          UShort operation,
          UOctet serviceVersion,
          Boolean isErrorMessage)
  {
    return new SPPMessageHeader(configuration, apidQualifier, uriRep, ssCounter, getURI(),
            authenticationId,
            uriTo,
            timestamp,
            qosLevel,
            priority,
            domain,
            networkZone,
            session,
            sessionName,
            interactionType,
            interactionStage,
            transactionId,
            serviceArea,
            service,
            operation,
            serviceVersion,
            isErrorMessage);
  }
}
