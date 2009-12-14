/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 *
 * @author cooper_sf
 */
public interface MALBrokerHandler
{
  void addConsumer(MessageHeader hdr, Subscription body, MALBrokerBindingImpl binding);

  void addProvider(MessageHeader hdr, EntityKeyList body);

  java.util.List<MALBrokerMessage> createNotify(MessageHeader hdr, UpdateList updateList) throws MALException;

  void removeConsumer(MessageHeader hdr, IdentifierList ids);

  void removeProvider(MessageHeader hdr);

  void removeLostConsumer(MessageHeader hdr);
}
