/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MALService;
import org.ccsds.moims.smc.mal.api.consumer.MALConsumer;
import org.ccsds.moims.smc.mal.api.consumer.MALConsumerManager;
import org.ccsds.moims.smc.mal.api.structures.MALBlob;
import org.ccsds.moims.smc.mal.api.structures.MALDomainIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALInteger;
import org.ccsds.moims.smc.mal.api.structures.MALQoSLevel;
import org.ccsds.moims.smc.mal.api.structures.MALSessionType;
import org.ccsds.moims.smc.mal.api.structures.MALURI;

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

  public MALConsumer createConsumer(MALURI uriTo, MALURI brokerUri, MALService service, MALBlob authenticationId, MALDomainIdentifier domain, MALIdentifier networkZone, MALSessionType sessionType, MALIdentifier sessionName, MALQoSLevel qosLevel, Hashtable qosProps, MALInteger priority) throws MALException
  {
    return (MALConsumer)addChild(new MALConsumerImpl(impl, this, uriTo, brokerUri, service, authenticationId, domain, networkZone, sessionType, sessionName, qosLevel, qosProps, priority));
  }
}
