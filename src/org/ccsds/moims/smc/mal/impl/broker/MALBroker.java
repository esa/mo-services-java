/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ccsds.moims.smc.mal.impl.broker;

import java.util.List;
import org.ccsds.moims.smc.mal.api.structures.MALMessageHeader;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateList;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;

/**
 *
 * @author cooper_sf
 */
public interface MALBroker {

  void addConsumer(MALMessage msg);

  List<MALBrokerMessage> createNotify(MALMessageHeader hdr, MALUpdateList updateList);

  void removeConsumer(MALMessage msg);

  void removeLostConsumer(MALMessageHeader hdr);

  void report();

}
