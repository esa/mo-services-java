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
public final class MALMessageDetails
{
  final MALEndPoint endpoint;
  final URI uriTo;
  final URI brokerUri;
  final MALService service;
  Blob authenticationId;
  final DomainIdentifier domain;
  final Identifier networkZone;
  final SessionType sessionType;
  final Identifier sessionName;
  final QoSLevel qosLevel;
  final Hashtable qosProps;
  final int priority;

  public MALMessageDetails(MALEndPoint endpoint, URI uriTo, URI brokerUri, MALService service, Blob authenticationId, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel qosLevel, Hashtable qosProps, int priority)
  {
    this.endpoint = endpoint;
    this.uriTo = uriTo;
    this.brokerUri = brokerUri;
    this.service = service;
    this.authenticationId = authenticationId;
    this.domain = domain;
    this.networkZone = networkZone;
    this.sessionType = sessionType;
    this.sessionName = sessionName;
    this.qosLevel = qosLevel;
    this.qosProps = qosProps;
    this.priority = priority;
  }
}
