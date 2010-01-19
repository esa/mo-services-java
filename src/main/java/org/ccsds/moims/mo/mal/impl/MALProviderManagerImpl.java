/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.impl.broker.MALBrokerImpl;
import org.ccsds.moims.mo.mal.impl.util.MALClose;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.URI;

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

  @Override
  public MALProvider createProvider(
          String localName,
          String protocol,
          MALService service,
          Blob authenticationId,
          MALInteractionHandler handler,
          QoSLevel[] expectedQos,
          int priorityLevelNumber,
          Hashtable defaultQoSProperties,
          Boolean isPublisher,
          URI sharedBrokerUri) throws MALException
  {
    return (MALProvider) addChild(new MALProviderImpl(this, impl, localName, protocol, service, authenticationId, handler, expectedQos, priorityLevelNumber, defaultQoSProperties, isPublisher, sharedBrokerUri));
  }

  @Override
  public void deleteProvider(String protocol, String localName) throws MALException
  {
  }
}
