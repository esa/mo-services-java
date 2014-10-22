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

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.malspp.test.sppinterceptor.SPPInterceptor;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malspp.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.malspp.test.util.BufferReader;
import org.ccsds.moims.mo.malspp.test.util.CUCTimeCode;
import org.ccsds.moims.mo.malspp.test.util.TestHelper;
import org.ccsds.moims.mo.malspp.test.util.SecondaryHeaderReader;
import org.ccsds.moims.mo.malspp.test.util.TimeCode;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.ParseHelper;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacket;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacketHeader;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;

public class SpacePacketCheck {
  
  private SpacePacket spacePacket;
	
	private SpacePacketHeader primaryHeader;
	
	private MALMessageHeader malHeader;
	
	private BufferReader bufferReader;
	
	private SecondaryHeaderReader secondaryHeaderReader;
	
	private TimeCode timeCode;
  
	private TimeCode fineTimeCode;
  
	private TimeCode durationCode;
	
  private int consumerPacketType;
	  
	private int providerPacketType;
	
	private boolean isSent;

	public SpacePacketCheck() {
    super();  
	  timeCode = new CUCTimeCode(TimeCode.EPOCH_TAI, TimeCode.UNIT_SECOND, 4, 3);
    fineTimeCode = new CUCTimeCode(new AbsoluteDate("2013-01-01T00:00:00.000",
        TimeScalesFactory.getTAI()), TimeCode.UNIT_SECOND, 4, 5);
    durationCode = new CUCTimeCode(null, TimeCode.UNIT_SECOND, 4, 0);
  }
	
	public int getConsumerPacketType() {
    return consumerPacketType;
  }

  public int getProviderPacketType() {
    return providerPacketType;
  }

  public boolean selectReceivedPacketAt(int index) {
	  isSent = false;
		MALMessage message = TransportInterceptor.instance().getLastReceivedMessage(index);
		malHeader = message.getHeader();
		spacePacket = SPPInterceptor.instance().getReceivedPacket(index);
		return selectPacket(spacePacket);
	}
	
	public boolean selectSentPacketAt(int index) {
	  isSent = true;
		MALMessage message = TransportInterceptor.instance().getLastSentMessage(index);
		malHeader = message.getHeader();
		spacePacket = SPPInterceptor.instance().getSentPacket(index);
		return selectPacket(spacePacket);
	}
	
  public boolean selectPacket(SpacePacket packet) {
    byte[] packetBody = packet.getBody();
    primaryHeader = packet.getHeader();
    bufferReader = new BufferReader(packetBody, 0, true, timeCode, fineTimeCode, durationCode);
    secondaryHeaderReader = new SecondaryHeaderReader(bufferReader);
    return true;
  }
  
  public boolean consumerPacketIsTc(boolean isTc) {
    if (isTc) {
      consumerPacketType = 1;
    } else {
      consumerPacketType = 0;
    }
    return true;
  }
  
  public boolean providerPacketIsTc(boolean isTc) {
    if (isTc) {
      providerPacketType = 1;
    } else {
      providerPacketType = 0;
    }
    return true;
  }
	
	public boolean checkTimestamp() throws Exception {
		long timestamp = bufferReader.readTimestamp();
		boolean res = (malHeader.getTimestamp().getValue() == timestamp);
		if (! res) {
			LoggingBase.logMessage(timestamp + " != " + malHeader.getTimestamp().getValue());
			LoggingBase.logMessage("MAL header:  " + malHeader);
		}
		return res;
	}
	
  public boolean checkSpacePacketType() {
    if (isSent) {
      return primaryHeader.getPacketType() == consumerPacketType;
    } else {
      return primaryHeader.getPacketType() == providerPacketType;
    }
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
	
	private int getExpectedSecondaryApid() {
	  int expectedSecondaryApid;
	  if (consumerPacketType == 1) {
      if (providerPacketType == 1) {
        if (isSent) {
          // TC: consumer APID (from)
          expectedSecondaryApid = LocalMALInstance.TC_TC_LOCAL_APID;
        } else {
          // TC: provider APID (from)
          expectedSecondaryApid = TestServiceProvider.TC_REMOTE_APID;
        }
      } else {
        if (isSent) {
          // TC: consumer APID (from)
          expectedSecondaryApid = LocalMALInstance.TC_TM_LOCAL_APID;
        } else {
          // TM: consumer APID (to)
          expectedSecondaryApid = LocalMALInstance.TC_TM_LOCAL_APID;
        }
      }
    } else {
      if (providerPacketType == 1) {
        if (isSent) {
          // TM: provider APID (to)
          expectedSecondaryApid = TestServiceProvider.TC_REMOTE_APID;
        } else {
          // TC: provider APID (from)
          expectedSecondaryApid = TestServiceProvider.TC_REMOTE_APID;
        }
      } else {
        if (isSent) {
          // TM: provider APID (to)
          expectedSecondaryApid = TestServiceProvider.TM_REMOTE_APID;
        } else {
          // TM: consumer APID (to)
          expectedSecondaryApid = LocalMALInstance.TM_TM_LOCAL_APID;
        }
      }
    }
	  return expectedSecondaryApid;
	}
	
	private int getExpectedSecondaryApidQualifier() {
    int expectedSecondaryApidQualifier;
    if (consumerPacketType == 1) {
      if (providerPacketType == 1) {
        if (isSent) {
          // TC: consumer APID (from)
          expectedSecondaryApidQualifier = LocalMALInstance.TC_TC_LOCAL_APID_QUALIFIER;
        } else {
          // TC: provider APID (from)
          expectedSecondaryApidQualifier = TestServiceProvider.TC_REMOTE_APID_QUALIFIER;
        }
      } else {
        if (isSent) {
          // TC: consumer APID (from)
          expectedSecondaryApidQualifier = LocalMALInstance.TC_TM_LOCAL_APID_QUALIFIER;
        } else {
          // TM: consumer APID (to)
          expectedSecondaryApidQualifier = LocalMALInstance.TC_TM_LOCAL_APID_QUALIFIER;
        }
      }
    } else {
      if (providerPacketType == 1) {
        if (isSent) {
          // TM: provider APID (to)
          expectedSecondaryApidQualifier = TestServiceProvider.TC_REMOTE_APID_QUALIFIER;
        } else {
          // TC: provider APID (from)
          expectedSecondaryApidQualifier = TestServiceProvider.TC_REMOTE_APID_QUALIFIER;
        }
      } else {
        if (isSent) {
          // TM: provider APID (to)
          expectedSecondaryApidQualifier = TestServiceProvider.TM_REMOTE_APID_QUALIFIER;
        } else {
          // TM: consumer APID (to)
          expectedSecondaryApidQualifier = LocalMALInstance.TM_TM_LOCAL_APID_QUALIFIER;
        }
      }
    }
    return expectedSecondaryApidQualifier;
  }
	
  public boolean checkSecondaryApid() {
    int secondaryApid = secondaryHeaderReader.readSecondaryApid();
    boolean res = secondaryApid == getExpectedSecondaryApid();
    if (!res) {
      LoggingBase.logMessage("Secondary APID: "
          + secondaryApid + " != "
          + getExpectedSecondaryApid());
    }
    return res;
  }

  public boolean checkSecondaryApidQualifier() {
    int secondaryApidQualifier = secondaryHeaderReader.readSecondaryApidQualifier();
    boolean res = secondaryApidQualifier == getExpectedSecondaryApidQualifier();
    if (!res) {
      LoggingBase.logMessage("Secondary APID qualifier: "
          + secondaryApidQualifier + " != "
          + getExpectedSecondaryApidQualifier());
    }
    return res;
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
