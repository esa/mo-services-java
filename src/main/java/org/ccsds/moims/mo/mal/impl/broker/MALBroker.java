/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.mo.mal.impl.broker;

import java.util.List;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public interface MALBroker {

  void addConsumer(MALMessage msg);

  List<MALBrokerMessage> createNotify(MessageHeader hdr, UpdateList updateList);

  void removeConsumer(MALMessage msg);

  void removeLostConsumer(MessageHeader hdr);

  void report();

}
