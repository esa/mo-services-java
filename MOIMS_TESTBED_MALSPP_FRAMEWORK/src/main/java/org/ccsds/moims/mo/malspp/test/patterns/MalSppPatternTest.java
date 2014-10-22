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
import org.ccsds.moims.mo.mal.test.patterns.PatternTest;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.HeaderTestProcedure;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.PubSubTestCaseHelper;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.iptest.structures.IPTestTransitionList;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.objectweb.util.monolog.api.Logger;

public class MalSppPatternTest extends PatternTest {
	
	public final static Logger logger = fr.dyade.aaa.common.Debug
		  .getLogger(MalSppPatternTest.class.getName());
	
	private SpacePacketCheck spacePacketCheck;

	public MalSppPatternTest() {
    super();
    spacePacketCheck = new SpacePacketCheck();
  }

  public boolean consumerPacketIsTc(boolean isTc) {
    return spacePacketCheck.consumerPacketIsTc(isTc);
  }
	
	public boolean providerPacketIsTc(boolean isTc) {
	  return spacePacketCheck.providerPacketIsTc(isTc);
  }
	
  protected void initConsumer(SessionType session, Identifier sessionName,
      QoSLevel qos) throws Exception {
    int consumerPacketType = spacePacketCheck.getConsumerPacketType();
    int providerPacketType = spacePacketCheck.getProviderPacketType();
    if (consumerPacketType == 1) {
      if (providerPacketType == 1) {
        ipTestConsumer = LocalMALInstance.instance().ipTestStub(
            HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
            HeaderTestProcedure.PRIORITY, false);
      } else {
        ipTestConsumer = LocalMALInstance.instance().getTcTmIpTestStub(
            HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
            HeaderTestProcedure.PRIORITY, false);
      }
    } else {
      if (providerPacketType == 1) {
        ipTestConsumer = LocalMALInstance.instance().getTmTcIpTestStub(
            HeaderTestProcedure.AUTHENTICATION_ID,
            HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
            HeaderTestProcedure.PRIORITY, false);
      } else {
        ipTestConsumer = LocalMALInstance.instance().getTmTmIpTestStub(
            HeaderTestProcedure.AUTHENTICATION_ID, HeaderTestProcedure.DOMAIN,
            HeaderTestProcedure.NETWORK_ZONE, session, sessionName, qos,
            HeaderTestProcedure.PRIORITY, false);
      }
    }
  }
	
	public boolean selectReceivedPacketAt(int index) {
		return spacePacketCheck.selectReceivedPacketAt(index);
	}
	
	public boolean selectSentPacketAt(int index) {
		return spacePacketCheck.selectSentPacketAt(index);
	}
	
	public boolean checkTimestamp() throws Exception {
		return spacePacketCheck.checkTimestamp();
	}
	
	public boolean checkSpacePacketType() {
		return spacePacketCheck.checkSpacePacketType();
  }
	
	public int versionIs() {
		return spacePacketCheck.versionIs();
	}
	
	public int sduTypeIs() {
		return spacePacketCheck.sduTypeIs();
  }
	
	public int areaIs() {
		return spacePacketCheck.areaIs();
  }
	
	public int serviceIs() {
		return spacePacketCheck.serviceIs();
  }
	
	public int operationIs() {
		return spacePacketCheck.operationIs();
  }
	
	public int areaVersionIs() {
		return spacePacketCheck.areaVersionIs();
  }
	
	public int errorFlagIs() {
		return spacePacketCheck.errorFlagIs();
  }
	
	public boolean checkUriFrom() {
		return spacePacketCheck.checkUriFrom();
	}
	
	public boolean checkUriTo() {
		return spacePacketCheck.checkUriTo();
	}
	
	public boolean checkTransactionId() {
		return spacePacketCheck.checkTransactionId();
	}
	
	public boolean resetSppInterceptor() {
		return spacePacketCheck.resetSppInterceptor();
	}
	
	public boolean initiateSendPatternWithQosAndSession(String qosLevel, String sessionType) throws Exception {
		QoSLevel qos = ParseHelper.parseQoSLevel(qosLevel);
    SessionType session = ParseHelper.parseSessionType(sessionType);
    Identifier sessionName = PubSubTestCaseHelper.getSessionName(session);
    initConsumer(session, sessionName, qos);
    /*
		ipTestConsumer = LocalMALInstance.instance().ipTestStub(HeaderTestProcedure.AUTHENTICATION_ID,
        HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE,
        session,
        sessionName,
        qos,
        HeaderTestProcedure.PRIORITY,
        false);*/
    ipTest = ipTestConsumer.getStub();
    IPTestDefinition testDef = new IPTestDefinition("TestSendPattern",
        ipTestConsumer.getConsumer().getURI(),
        HeaderTestProcedure.AUTHENTICATION_ID,
        qos,
        HeaderTestProcedure.PRIORITY,
        HeaderTestProcedure.DOMAIN,
        HeaderTestProcedure.NETWORK_ZONE,
        session, sessionName,
        new IPTestTransitionList(),
        new Time(System.currentTimeMillis()));
        
    ipTest.send(testDef);
    return true;
	}
	
	public boolean checkQos(String qosLevelAsString) throws Exception {
		return spacePacketCheck.checkQos(qosLevelAsString);
	}
	
	public boolean checkSession(String sessionTypeAsString) throws Exception {
		return spacePacketCheck.checkSession(sessionTypeAsString);
  }
	
	public boolean checkSecondaryApid() {
    return spacePacketCheck.checkSecondaryApid();
  }
  
  public boolean checkSecondaryApidQualifier() {
    return spacePacketCheck.checkSecondaryApidQualifier();
  }
  
  public byte sourceIdFlagIs() throws Exception {
    return spacePacketCheck.sourceIdFlagIs();
  }
  
  public byte destinationIdFlagIs() throws Exception {
    return spacePacketCheck.destinationIdFlagIs();
  }
  
  public byte priorityFlagIs() throws Exception {
    return spacePacketCheck.priorityFlagIs();
  }
  
  public byte timestampFlagIs() throws Exception {
    return spacePacketCheck.timestampFlagIs();
  }
  
  public byte networkZoneFlagIs() throws Exception {
    return spacePacketCheck.networkZoneFlagIs();
  }
  
  public byte sessionNameFlagIs() throws Exception {
    return spacePacketCheck.sessionNameFlagIs();
  }
  
  public byte domainFlagIs() throws Exception {
    return spacePacketCheck.domainFlagIs();
  }
  
  public byte authenticationIdFlagIs() throws Exception {
    return spacePacketCheck.authenticationIdFlagIs();
  }
  
  public boolean readSourceId() {
    return spacePacketCheck.readSourceId();
  }
  
  public boolean readDestinationId() {
    return spacePacketCheck.readDestinationId();
  }
  
  public int segmentCounterIs() {
    return spacePacketCheck.segmentCounterIs();
  }
	
	public long priorityIs() throws Exception {
		return spacePacketCheck.priorityIs();
	}
	
	public String networkZoneIs() throws Exception {
		return spacePacketCheck.networkZoneIs();
	}
	
	public String sessionNameIs() throws Exception {
		return spacePacketCheck.sessionNameIs();
	}
	
	public boolean checkDomainId() throws Exception {
    return spacePacketCheck.checkDomainId();
  }
	
	public boolean checkAuthenticationId() throws Exception {
		return spacePacketCheck.checkAuthenticationId();
	}
	
}
