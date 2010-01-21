/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;

/**
 *
 * @author cooper_sf
 */
public final class MessageDetails
{
  public final MALEndPoint endpoint;
  public final URI uriFrom;
  public final URI uriTo;
  public final URI brokerUri;
  public final MALService service;
  public Blob authenticationId;
  public final DomainIdentifier domain;
  public final Identifier networkZone;
  public final SessionType sessionType;
  public final Identifier sessionName;
  public final QoSLevel qosLevel;
  public final Hashtable qosProps;
  public final int priority;

  public MessageDetails(MALEndPoint endpoint, URI uriFrom, URI uriTo, URI brokerUri, MALService service, Blob authenticationId, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel qosLevel, Hashtable qosProps, int priority)
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
    this.qosProps = (null == qosProps) ? new Hashtable() : qosProps;
    this.priority = priority;
  }
}
