/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.impl.util.MALClose;
import java.util.Hashtable;
import org.ccsds.moims.smc.mal.api.MALService;
import org.ccsds.moims.smc.mal.api.provider.MALInteractionHandler;
import org.ccsds.moims.smc.mal.api.provider.MALProvider;
import org.ccsds.moims.smc.mal.api.provider.MALProviderManager;
import org.ccsds.moims.smc.mal.api.structures.MALBlob;
import org.ccsds.moims.smc.mal.api.structures.MALBoolean;
import org.ccsds.moims.smc.mal.api.structures.MALException;
import org.ccsds.moims.smc.mal.api.structures.MALInteger;
import org.ccsds.moims.smc.mal.api.structures.MALQoSLevel;
import org.ccsds.moims.smc.mal.api.structures.MALString;
import org.ccsds.moims.smc.mal.api.structures.MALURI;

/**
 *
 * @author cooper_sf
 */
public class MALProviderManagerImpl extends MALClose implements MALProviderManager
{
  private final MALImpl impl;

  public MALProviderManagerImpl(MALImpl impl)
  {
    super(impl);
    this.impl = impl;
  }

  public MALProvider createProvider(MALString localName, MALString protocol, MALService service, MALBlob authenticationId, MALInteractionHandler handler, MALQoSLevel[] expectedQos, MALInteger priorityLevelNumber, Hashtable defaultQoSProperties, MALBoolean isPublisher, MALURI sharedBrokerUri) throws MALException
  {
    return (MALProvider)addChild(new MALProviderImpl(this, impl.getSendingInterface(), impl.getReceivingInterface(), impl.getMaps(), localName, protocol, service, authenticationId, handler, expectedQos, priorityLevelNumber, defaultQoSProperties, isPublisher, sharedBrokerUri));
  }

  public void deleteProvider(MALString localName) throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
