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
package org.ccsds.moims.mo.malspp.test.patterns;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malspp.test.sppinterceptor.SPPInterceptor;
import org.ccsds.moims.mo.malspp.test.util.BufferReader;
import org.ccsds.moims.mo.malspp.test.util.TestHelper;
import org.ccsds.moims.mo.malspp.test.util.SecondaryHeaderReader;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacket;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacketHeader;

public class SpacePacketCheck {
  
  private SpacePacket spacePacket;
	
	private SpacePacketHeader primaryHeader;
	
	private MALMessageHeader malHeader;
	
	private BufferReader bufferReader;
	
	private SecondaryHeaderReader secondaryHeaderReader;
	
	private int sourceId;
	
	private int destinationId;
	
	public boolean selectReceivedPacketAt(int index) {
		MALMessage message = TransportInterceptor.instance().getLastReceivedMessage(index);
		malHeader = message.getHeader();
		spacePacket = SPPInterceptor.instance().getReceivedPacket(index);
		return selectPacket(spacePacket);
	}
	
	public boolean selectSentPacketAt(int index) {
		MALMessage message = TransportInterceptor.instance().getLastSentMessage(index);
		malHeader = message.getHeader();
		spacePacket = SPPInterceptor.instance().getSentPacket(index);
		return selectPacket(spacePacket);
	}
	
  public boolean selectPacket(SpacePacket packet) {
    byte[] packetBody = packet.getBody();
    primaryHeader = packet.getHeader();
    bufferReader = new BufferReader(packetBody, 0, true);
    secondaryHeaderReader = new SecondaryHeaderReader(bufferReader,
        BufferReader.TimeFormat.CUC);
    return true;
  }
	
	public boolean checkTimestamp() throws Exception {
		long timestamp = bufferReader.readTimestamp(BufferReader.TimeFormat.CUC);
		boolean res = (malHeader.getTimestamp().getValue() == timestamp);
		if (! res) {
			LoggingBase.logMessage(timestamp + " != " + malHeader.getTimestamp().getValue());
			LoggingBase.logMessage("MAL header:  " + malHeader);
		}
		return res;
	}
	
	public int spacePacketTypeIs() {
    return primaryHeader.getPacketType();
  }
	
	public int versionIs() {
		return secondaryHeaderReader.readVersion();
	}
	
	public int sduTypeIs() {
		return secondaryHeaderReader.readSduType();
  }
	
	public int areaIs() {
		return secondaryHeaderReader.readArea();
  }

	public int serviceIs() {
		return secondaryHeaderReader.readService();
  }
	
	public int operationIs() {
		return secondaryHeaderReader.readOperation();
  }
	
	public int areaVersionIs() {
		return secondaryHeaderReader.readAreaVersion();
  }
	
	public int errorFlagIs() {
		return secondaryHeaderReader.readIsError();
  }
	
  public boolean checkUriFrom() {
    URI expectedURI = TestHelper.getUriFrom(spacePacket,
        secondaryHeaderReader.getSecondaryHeader());
    boolean res = expectedURI.equals(malHeader.getURIFrom());
    if (!res) {
      LoggingBase.logMessage(expectedURI + " != " + malHeader.getURIFrom());
    }
    return res;
  }

  public boolean checkUriTo() {
    URI expectedURI = TestHelper.getUriTo(spacePacket,
        secondaryHeaderReader.getSecondaryHeader());
    boolean res = expectedURI.equals(malHeader.getURITo());
    if (!res) {
      LoggingBase.logMessage(expectedURI + " != " + malHeader.getURITo());
    }
    return res;
  }
	
	public boolean checkTransactionId() {
		Long tid = secondaryHeaderReader.readTransactionId();
		boolean res = tid.equals(malHeader.getTransactionId());
		if (! res) {
			LoggingBase.logMessage("TransactionId: " + tid + " != " + malHeader.getTransactionId());
			LoggingBase.logMessage("MAL header:  " + malHeader);
		}
		return res;
	}
	
	public boolean resetSppInterceptor() {
		SPPInterceptor.instance().reset();
		TransportInterceptor.instance().resetLastReceivedMessage();
		TransportInterceptor.instance().resetLastSentMessage();
		return true;
	}
	
	public boolean checkQos(String qosLevelAsString) throws Exception {
		QoSLevel qosLevel = ParseHelper.parseQoSLevel(qosLevelAsString);
	  int qos = secondaryHeaderReader.readQos();
	  return (qos == qosLevel.getOrdinal());
	}
	
	public boolean checkSession(String sessionTypeAsString) throws Exception {
		SessionType sessionType =  ParseHelper.parseSessionType(sessionTypeAsString);
		int session = secondaryHeaderReader.readSession();
		return (session == sessionType.getOrdinal());
  }
	
	public int secondaryApidIs() {
	  return secondaryHeaderReader.readSecondaryApid();
	}
	
	public int secondaryApidQualifierIs() {
    return secondaryHeaderReader.readSecondaryApidQualifier();
  }
	
	public byte sourceIdFlagIs() throws Exception {
    return secondaryHeaderReader.readSourceIdFlag();
  }
	
	public byte destinationIdFlagIs() throws Exception {
    return secondaryHeaderReader.readDestinationIdFlag();
  }
	
	public byte priorityFlagIs() throws Exception {
    return secondaryHeaderReader.readPriorityFlag();
  }
	
	public byte timestampFlagIs() throws Exception {
    return secondaryHeaderReader.readTimestampFlag();
  }
	
	public byte networkZoneFlagIs() throws Exception {
    return secondaryHeaderReader.readNetworkZoneFlag();
  }
	
	public byte sessionNameFlagIs() throws Exception {
    return secondaryHeaderReader.readSessionNameFlag();
  }
	
	public byte domainFlagIs() throws Exception {
    return secondaryHeaderReader.readDomainFlag();
  }
	
	public byte authenticationIdFlagIs() throws Exception {
    return secondaryHeaderReader.readAuthenticationIdFlag();
  }
	
	public boolean readSourceId() {
	  secondaryHeaderReader.readSourceId();
	  return true;
	}
	
	public boolean readDestinationId() {
	  secondaryHeaderReader.readDestinationId();
    return true;
  }
	
	public int segmentCounterIs() {
    return secondaryHeaderReader.readSegmentCounter();
  }
	
	public long priorityIs() throws Exception {
		return secondaryHeaderReader.readPriority();
	}
	
	public String networkZoneIs() throws Exception {
		return secondaryHeaderReader.readNetworkZone();
	}
	
	public String sessionNameIs() throws Exception {
		return secondaryHeaderReader.readSessionName();
	}
	
	public String domainIdentifierIs() throws Exception {
		IdentifierList domainId = secondaryHeaderReader.readDomain();
		StringBuffer buf = new StringBuffer();
		if (domainId.size() > 0) {
   		buf.append(domainId.get(0));
		  for (int i = 1; i < domainId.size(); i++) {
		  	buf.append('.');
		  	buf.append(domainId.get(i));
		  }
		}
		return buf.toString();
	}
	
	public boolean checkAuthenticationId() throws Exception {
		Blob readAuthId = new Blob(secondaryHeaderReader.readAuthenticationId());
		Blob authId = malHeader.getAuthenticationId();
		boolean res = readAuthId.equals(authId);
		if (! res) {
			LoggingBase.logMessage(readAuthId + " != " + authId);
			LoggingBase.logMessage("MAL header:  " + malHeader);
		}
		return res;
	}
	
	public boolean checkDomainId() throws Exception {
		IdentifierList expectedDomainId = secondaryHeaderReader.readDomain();
		IdentifierList domainId = malHeader.getDomain();
		boolean res = expectedDomainId.equals(domainId);
		if (! res) {
			LoggingBase.logMessage(expectedDomainId + " != " + domainId);
			LoggingBase.logMessage("MAL header:  " + malHeader);
		}
		return res;
	}
	
}
