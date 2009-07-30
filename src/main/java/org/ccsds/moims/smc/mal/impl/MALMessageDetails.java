/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl;

import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MALService;
import org.ccsds.moims.smc.mal.api.structures.MALBlob;
import org.ccsds.moims.smc.mal.api.structures.MALDomainIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALInteger;
import org.ccsds.moims.smc.mal.api.structures.MALQoSLevel;
import org.ccsds.moims.smc.mal.api.structures.MALSessionType;
import org.ccsds.moims.smc.mal.api.structures.MALURI;
import org.ccsds.moims.smc.mal.api.transport.MALEndPoint;

/**
 *
 * @author cooper_sf
 */
public final class MALMessageDetails
{
  final MALEndPoint endpoint;
  final MALURI uriTo;
  final MALURI brokerUri;
  final MALService service;
  MALBlob authenticationId;
  final MALDomainIdentifier domain;
  final MALIdentifier networkZone;
  final MALSessionType sessionType;
  final MALIdentifier sessionName;
  final MALQoSLevel qosLevel;
  final Hashtable qosProps;
  final MALInteger priority;

  public MALMessageDetails(MALEndPoint endpoint, MALURI uriTo, MALURI brokerUri, MALService service, MALBlob authenticationId, MALDomainIdentifier domain, MALIdentifier networkZone, MALSessionType sessionType, MALIdentifier sessionName, MALQoSLevel qosLevel, Hashtable qosProps, MALInteger priority)
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
