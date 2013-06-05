/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl;

import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Simple struct style class for holding details of a message.
 */
public final class MessageDetails
{
  /**
   * The MAL endpoint used for this message.
   */
  public final MALEndpoint endpoint;
  /**
   * The URI from field.
   */
  public final URI uriFrom;
  /**
   * The URI to field.
   */
  public final URI uriTo;
  /**
   * The broker URI to use.
   */
  public final URI brokerUri;
  /**
   * The service being used.
   */
  public final MALService service;
  /**
   * The authentication id being used.
   */
  public Blob authenticationId;
  /**
   * The domain of the message.
   */
  public final IdentifierList domain;
  /**
   * The network zone of the message.
   */
  public final Identifier networkZone;
  /**
   * The session type.
   */
  public final SessionType sessionType;
  /**
   * The session name.
   */
  public final Identifier sessionName;
  /**
   * The QoS level.
   */
  public final QoSLevel qosLevel;
  /**
   * The QoS properties.
   */
  public final Map qosProps;
  /**
   * The priority of the message.
   */
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
  public MessageDetails(final MALEndpoint endpoint,
          final URI uriFrom,
          final URI uriTo,
          final URI brokerUri,
          final MALService service,
          final Blob authenticationId,
          final IdentifierList domain,
          final Identifier networkZone,
          final SessionType sessionType,
          final Identifier sessionName,
          final QoSLevel qosLevel,
          final Map qosProps,
          final UInteger priority)
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
