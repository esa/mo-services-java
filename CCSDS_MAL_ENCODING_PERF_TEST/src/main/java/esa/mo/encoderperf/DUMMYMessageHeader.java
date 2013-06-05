/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Encoder performance test
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
package esa.mo.encoderperf;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;


public class DUMMYMessageHeader implements MALMessageHeader, Element
{
  private URI URIFrom;
  private Blob authenticationId;
  private URI URITo;
  private Time timestamp;
  private QoSLevel QoSlevel;
  private UInteger priority;
  private IdentifierList domain;
  private Identifier networkZone;
  private SessionType session;
  private Identifier sessionName;
  private InteractionType interactionType;
  private UOctet interactionStage;
  private Long transactionId;
  private UShort serviceArea;
  private UShort service;
  private UShort operation;
  private UOctet areaVersion;
  private Boolean isErrorMessage;

  public DUMMYMessageHeader()
  {
  }

  public DUMMYMessageHeader(URI uriFrom, Blob authenticationId, URI uriTo, Time timestamp, QoSLevel qosLevel, UInteger priority, IdentifierList domain, Identifier networkZone, SessionType session, Identifier sessionName, InteractionType interactionType, UOctet interactionStage, Long transactionId, UShort serviceArea, UShort service, UShort operation, UOctet areaVersion, Boolean isErrorMessage)
  {
    this.URIFrom = uriFrom;
    this.authenticationId = authenticationId;
    this.URITo = uriTo;
    this.timestamp = timestamp;
    this.QoSlevel = qosLevel;
    this.priority = priority;
    this.domain = domain;
    this.networkZone = networkZone;
    this.session = session;
    this.sessionName = sessionName;
    this.interactionType = interactionType;
    this.interactionStage = interactionStage;
    this.transactionId = transactionId;
    this.serviceArea = serviceArea;
    this.service = service;
    this.operation = operation;
    this.areaVersion = areaVersion;
    this.isErrorMessage = isErrorMessage;
  }

  @Override
  public URI getURIFrom()
  {
    return URIFrom;
  }

  @Override
  public void setURIFrom(URI URIFrom)
  {
    this.URIFrom = URIFrom;
  }

  @Override
  public Blob getAuthenticationId()
  {
    return authenticationId;
  }

  @Override
  public void setAuthenticationId(Blob authenticationId)
  {
    this.authenticationId = authenticationId;
  }

  @Override
  public IdentifierList getDomain()
  {
    return domain;
  }

  @Override
  public void setDomain(IdentifierList domain)
  {
    this.domain = domain;
  }

  @Override
  public UOctet getInteractionStage()
  {
    return interactionStage;
  }

  @Override
  public void setInteractionStage(UOctet interactionStage)
  {
    this.interactionStage = interactionStage;
  }

  @Override
  public InteractionType getInteractionType()
  {
    return interactionType;
  }

  @Override
  public void setInteractionType(InteractionType interactionType)
  {
    this.interactionType = interactionType;
  }

  @Override
  public Boolean getIsErrorMessage()
  {
    return isErrorMessage;
  }

  @Override
  public void setIsErrorMessage(Boolean isErrorMessage)
  {
    this.isErrorMessage = isErrorMessage;
  }

  @Override
  public Identifier getNetworkZone()
  {
    return networkZone;
  }

  @Override
  public void setNetworkZone(Identifier networkZone)
  {
    this.networkZone = networkZone;
  }

  @Override
  public UShort getOperation()
  {
    return operation;
  }

  @Override
  public void setOperation(UShort operation)
  {
    this.operation = operation;
  }

  @Override
  public UInteger getPriority()
  {
    return priority;
  }

  @Override
  public void setPriority(UInteger priority)
  {
    this.priority = priority;
  }

  @Override
  public QoSLevel getQoSlevel()
  {
    return QoSlevel;
  }

  @Override
  public void setQoSlevel(QoSLevel qosLevel)
  {
    this.QoSlevel = qosLevel;
  }

  @Override
  public UShort getService()
  {
    return service;
  }

  @Override
  public void setService(UShort service)
  {
    this.service = service;
  }

  @Override
  public UShort getServiceArea()
  {
    return serviceArea;
  }

  @Override
  public void setServiceArea(UShort serviceArea)
  {
    this.serviceArea = serviceArea;
  }

  @Override
  public UOctet getAreaVersion()
  {
    return areaVersion;
  }

  @Override
  public void setAreaVersion(UOctet areaVersion)
  {
    this.areaVersion = areaVersion;
  }

  @Override
  public SessionType getSession()
  {
    return session;
  }

  @Override
  public void setSession(SessionType session)
  {
    this.session = session;
  }

  @Override
  public Identifier getSessionName()
  {
    return sessionName;
  }

  @Override
  public void setSessionName(Identifier sessionName)
  {
    this.sessionName = sessionName;
  }

  @Override
  public Time getTimestamp()
  {
    return timestamp;
  }

  @Override
  public void setTimestamp(Time timestamp)
  {
    this.timestamp = timestamp;
  }

  @Override
  public Long getTransactionId()
  {
    return transactionId;
  }

  @Override
  public void setTransactionId(Long transactionId)
  {
    this.transactionId = transactionId;
  }

  @Override
  public URI getURITo()
  {
    return URITo;
  }

  @Override
  public void setURITo(URI uriTo)
  {
    this.URITo = uriTo;
  }

  @Override
  public Element createElement()
  {
    return new DUMMYMessageHeader();
  }

  @Override
  public void encode(MALEncoder encoder) throws MALException
  {
    encoder.encodeNullableURI(URIFrom);
    encoder.encodeNullableBlob(authenticationId);
    encoder.encodeNullableURI(URITo);
    encoder.encodeNullableTime(timestamp);
    encoder.encodeNullableElement(QoSlevel);
    encoder.encodeNullableUInteger(priority);
    encoder.encodeNullableElement(domain);
    encoder.encodeNullableIdentifier(networkZone);
    encoder.encodeNullableElement(session);
    encoder.encodeNullableIdentifier(sessionName);
    encoder.encodeNullableElement(interactionType);
    encoder.encodeNullableUOctet(interactionStage);
    encoder.encodeNullableLong(transactionId);
    encoder.encodeNullableUShort(serviceArea);
    encoder.encodeNullableUShort(service);
    encoder.encodeNullableUShort(operation);
    encoder.encodeNullableUOctet(areaVersion);
    encoder.encodeNullableBoolean(isErrorMessage);
  }

  @Override
  public Element decode(MALDecoder decoder) throws MALException
  {
    URIFrom = decoder.decodeNullableURI();
    authenticationId = decoder.decodeNullableBlob();
    URITo = decoder.decodeNullableURI();
    timestamp = decoder.decodeNullableTime();
    QoSlevel = (QoSLevel)decoder.decodeNullableElement(QoSLevel.BESTEFFORT);
    priority = decoder.decodeNullableUInteger();
    domain = (IdentifierList)decoder.decodeNullableElement(new IdentifierList());
    networkZone = decoder.decodeNullableIdentifier();
    session = (SessionType)decoder.decodeNullableElement(SessionType.LIVE);
    sessionName = decoder.decodeNullableIdentifier();
    interactionType = (InteractionType)decoder.decodeNullableElement(InteractionType.SEND);
    interactionStage = decoder.decodeNullableUOctet();
    transactionId = decoder.decodeNullableLong();
    serviceArea = decoder.decodeNullableUShort();
    service = decoder.decodeNullableUShort();
    operation = decoder.decodeNullableUShort();
    areaVersion = decoder.decodeNullableUOctet();
    isErrorMessage = decoder.decodeNullableBoolean();
    
    return this;
  }

  @Override
  public UShort getAreaNumber()
  {
    return new UShort(0);
  }

  @Override
  public UShort getServiceNumber()
  {
    return new UShort(0);
  }

  @Override
  public Long getShortForm()
  {
    return 0L;
  }

  @Override
  public Integer getTypeShortForm()
  {
    return 0;
  }

  @Override
  public String toString()
  {
    return "GENMessageHeader{" + "URIFrom=" + URIFrom + ", authenticationId=" + authenticationId + ", URITo=" + URITo + ", timestamp=" + timestamp + ", QoSlevel=" + QoSlevel + ", priority=" + priority + ", domain=" + domain + ", networkZone=" + networkZone + ", session=" + session + ", sessionName=" + sessionName + ", interactionType=" + interactionType + ", interactionStage=" + interactionStage + ", transactionId=" + transactionId + ", serviceArea=" + serviceArea + ", service=" + service + ", operation=" + operation + ", serviceVersion=" + areaVersion + ", isErrorMessage=" + isErrorMessage + '}';
  }
}
