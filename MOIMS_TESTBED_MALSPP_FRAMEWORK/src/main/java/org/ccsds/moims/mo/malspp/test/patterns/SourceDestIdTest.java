/*******************************************************************************
 * Copyright or � or Copr. CNES
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
package org.ccsds.moims.mo.malspp.test.patterns;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.PubSubTestCaseHelper;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestTransitionList;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;

public class SourceDestIdTest extends MalSppPatternTest {
  
  protected void initConsumer(SessionType session, Identifier sessionName,
      QoSLevel qos) throws Exception {
    ipTestConsumer = LocalMALInstance.instance().getTcTcIpTestStub(
            HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
            HeaderTestProcedure.PRIORITY, false, false);
  }
  
  public boolean initiateRequest(String procedureName) throws Exception {
    QoSLevel qos = QoSLevel.BESTEFFORT;
    SessionType session = SessionType.LIVE;
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    initConsumer(session, sessionName, qos);
    ipTest = ipTestConsumer.getStub();

    IPTestDefinition testDef = new IPTestDefinition(procedureName,
        ipTestConsumer.getConsumer().getURI(),
        HeaderTestProcedure.AUTHENTICATION_ID,
        qos,
        HeaderTestProcedure.PRIORITY,
        HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE,
        session, sessionName,
        new IPTestTransitionList(),
        new Time(System.currentTimeMillis()));
        
    ipTest.request(testDef);
    return true;
  }

}
