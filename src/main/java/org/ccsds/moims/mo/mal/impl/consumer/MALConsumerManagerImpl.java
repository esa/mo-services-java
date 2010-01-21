/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.consumer;

import org.ccsds.moims.mo.mal.impl.*;
import org.ccsds.moims.mo.mal.impl.consumer.MALConsumerImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 *
 * @author cooper_sf
 */
public class MALConsumerManagerImpl extends MALClose implements MALConsumerManager
{
  private final MALImpl impl;

  public MALConsumerManagerImpl(MALImpl impl)
  {
    super(impl);

    this.impl = impl;
  }

  @Override
  public MALConsumer createConsumer(URI uriTo, URI brokerUri, MALService service, Blob authenticationId, DomainIdentifier domain, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel qosLevel, Hashtable qosProps, Integer priority) throws MALException
  {
    return (MALConsumer)addChild(new MALConsumerImpl(impl, this, uriTo, brokerUri, service, authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, qosProps, priority));
  }
}
