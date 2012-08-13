/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl;

import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 *
 */
public final class MessageDetails
{
  public final MALEndpoint endpoint;
  public final URI uriFrom;
  public final URI uriTo;
  public final URI brokerUri;
  public final MALService service;
  public Blob authenticationId;
  public final IdentifierList domain;
  public final Identifier networkZone;
  public final SessionType sessionType;
  public final Identifier sessionName;
  public final QoSLevel qosLevel;
  public final Map qosProps;
  public final UInteger priority;

  /**
   * Constructor.
   * @param endpoint Endpoint.
   * @param uriFrom URIFrom.
   * @param uriTo URITo.
   * @param brokerUri BrokerURI.
   * @param service Service.
   * @param authenticationId Authentication Identifier.
   * @param domain Domain.
   * @param networkZone Network Zone.
   * @param sessionType Session type.
   * @param sessionName Session name.
   * @param qosLevel QOS Level.
   * @param qosProps QOS properties.
   * @param priority Priority.
   */
  public MessageDetails(MALEndpoint endpoint,
          URI uriFrom,
          URI uriTo,
          URI brokerUri,
          MALService service,
          Blob authenticationId,
          IdentifierList domain,
          Identifier networkZone,
          SessionType sessionType,
          Identifier sessionName,
          QoSLevel qosLevel,
          Map qosProps,
          UInteger priority)
  {
    this.endpoint = endpoint;
    this.uriFrom = uriFrom;
    this.uriTo = uriTo;
    this.brokerUri = brokerUri;
    this.service = service;
    this.authenticationId = authenticationId;
    this.domain = domain;
    this.networkZone = networkZone;
    this.sessionType = sessionType;
    this.sessionName = sessionName;
    this.qosLevel = qosLevel;
    this.qosProps = (null == qosProps) ? new HashMap() : qosProps;
    this.priority = priority;
  }
}
