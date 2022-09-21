/*******************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a 
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 *******************************************************************************/
package org.ccsds.moims.mo.mal.test.transport;

import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.malprototype.iptest.consumer.IPTestStub;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestTransitionList;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;

public class ReceiveTestProcedure
{
  public static final SessionType SESSION = SessionType.LIVE;
  public static final Identifier SESSION_NAME = new Identifier("LIVE");
  public static final UInteger PRIORITY = new UInteger(1);
  public static final QoSLevel QOS_LEVEL = QoSLevel.ASSURED;
  
  public static final String PROCEDURE_NAME = "ReceiveTestProcedure";
  
  private IPTestStub ipTest;
  
  public boolean createConsumer() throws Exception {
    LoggingBase.logMessage("ReceiveTestProcedure.createConsumer()");
    Thread.sleep(2000);
    
    ipTest = LocalMALInstance.instance().ipTestStub(
        HeaderTestProcedure.AUTHENTICATION_ID,
        HeaderTestProcedure.DOMAIN, 
        HeaderTestProcedure.NETWORK_ZONE, 
        SESSION, SESSION_NAME, QOS_LEVEL, PRIORITY, false).getStub();
    return true;
  }
  
  public boolean initiateInteraction() throws Exception {
    LoggingBase.logMessage("ReceiveTestProcedure.initiateInteraction()");
    
    TransportInterceptor.instance().resetReceiveCount(InteractionType.REQUEST);
    
    IPTestTransitionList transitions = new IPTestTransitionList();
    ipTest.request(null);
    
    return true;
  }
  
  public int receiveCount() throws Exception {
    return TransportInterceptor.instance().getReceiveCount(InteractionType.REQUEST);
  }
  
  public boolean isRequestResponse() throws Exception {
    MALMessage msg = TransportInterceptor.instance().getLastReceivedMessage();
    return (msg != null && 
        msg.getHeader().getInteractionType().equals(InteractionType.REQUEST) &&
        msg.getHeader().getInteractionStage().equals(MALRequestOperation.REQUEST_RESPONSE_STAGE));
  }
}
