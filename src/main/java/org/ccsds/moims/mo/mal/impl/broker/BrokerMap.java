/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.AddressKey;
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
public class BrokerMap implements BrokerHandler
{
  private final MALImpl impl;
  private final Map<AddressKey, BrokerHandler> brokerMap = new TreeMap<AddressKey, BrokerHandler>();

  public BrokerMap(MALImpl impl)
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
  public List<BrokerMessage> createNotify(MessageHeader hdr, UpdateList updateList) throws MALException
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
  
  private BrokerHandler getHandler(MessageHeader hdr)
  {
    AddressKey key = new AddressKey(hdr);

    BrokerHandler rv = brokerMap.get(key);

    if (null == rv)
    {
      rv = impl.createBroker();
      brokerMap.put(key, rv);
    }

    return rv;
  }
}
