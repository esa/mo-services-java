/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.BrokerKey;
import org.ccsds.moims.mo.mal.impl.MALImpl;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 *
 * @author cooper_sf
 */
public class MALBrokerMap implements MALBrokerHandler
{
  private final MALImpl impl;
  private final Map<BrokerKey, MALBrokerHandler> brokerMap = new TreeMap<BrokerKey, MALBrokerHandler>();

  public MALBrokerMap(MALImpl impl)
  {
    this.impl = impl;
  }

  @Override
  public void addConsumer(MessageHeader hdr, Subscription body, MALBrokerBindingImpl binding)
  {
    getHandler(hdr).addConsumer(hdr, body, binding);
  }

  @Override
  public void addProvider(MessageHeader hdr, EntityKeyList body)
  {
    getHandler(hdr).addProvider(hdr, body);
  }

  @Override
  public QoSLevel getProviderQoSLevel(MessageHeader hdr)
  {
    return getHandler(hdr).getProviderQoSLevel(hdr);
  }

  @Override
  public List<MALBrokerMessage> createNotify(MessageHeader hdr, UpdateList updateList) throws MALException
  {
    return getHandler(hdr).createNotify(hdr, updateList);
  }

  @Override
  public void removeConsumer(MessageHeader hdr, IdentifierList ids)
  {
    getHandler(hdr).removeConsumer(hdr, ids);
  }

  @Override
  public void removeLostConsumer(MessageHeader hdr)
  {
    getHandler(hdr).removeLostConsumer(hdr);
  }

  @Override
  public void removeProvider(MessageHeader hdr)
  {
    getHandler(hdr).removeProvider(hdr);
  }
  
  private MALBrokerHandler getHandler(MessageHeader hdr)
  {
    BrokerKey key = new BrokerKey(hdr);

    MALBrokerHandler rv = brokerMap.get(key);

    if (null == rv)
    {
      rv = impl.createBroker();
      brokerMap.put(key, rv);
    }

    return rv;
  }
}
