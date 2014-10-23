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
package org.ccsds.moims.mo.malspp.test.util;

import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacket;

public class TestHelper {
  
  public static final String IS_TC_PACKET_PROPERTY = "org.ccsds.moims.mo.malspp.isTcPacket";
  public static final String APID_QUALIFIER_PROPERTY = "org.ccsds.moims.mo.malspp.apidQualifier";
  public static final String APID_PROPERTY = "org.ccsds.moims.mo.malspp.apid";
  
  public static final String AUTHENTICATION_ID_FLAG = "org.ccsds.moims.mo.malspp.authenticationIdFlag";
  public static final String DOMAIN_FLAG = "org.ccsds.moims.mo.malspp.domainFlag";
  public static final String NETWORK_ZONE_FLAG = "org.ccsds.moims.mo.malspp.networkZoneFlag";
  public static final String PRIORITY_FLAG = "org.ccsds.moims.mo.malspp.priorityFlag";
  public static final String SESSION_NAME_FLAG = "org.ccsds.moims.mo.malspp.sessionNameFlag";
  public static final String TIMESTAMP_FLAG = "org.ccsds.moims.mo.malspp.timestampFlag";
  
  public static final String PROTOCOL = "malspp:";
  
  public static final char SLASH = '/';
  
  public static URI getUriFrom(SpacePacket spacePacket,
      SecondaryHeader secondaryHeader) {
    if (spacePacket.getHeader().getPacketType() == 1) {
      return createUri(secondaryHeader.getSecondaryApidQualifier(),
          secondaryHeader.getSecondaryApid(),
          secondaryHeader.getSourceIdFlag(), secondaryHeader.getSourceId());
    } else {
      return createUri(spacePacket.getApidQualifier(),
          spacePacket.getHeader().getApid(),
          secondaryHeader.getSourceIdFlag(), secondaryHeader.getSourceId());
    }
  }
  
  public static URI getUriTo(SpacePacket spacePacket,
      SecondaryHeader secondaryHeader) {
    if (spacePacket.getHeader().getPacketType() == 1) {
      return createUri(spacePacket.getApidQualifier(),
          spacePacket.getHeader().getApid(),
          secondaryHeader.getDestinationIdFlag(), secondaryHeader.getDestinationId());
    } else {
      return createUri(secondaryHeader.getSecondaryApidQualifier(),
          secondaryHeader.getSecondaryApid(),
          secondaryHeader.getDestinationIdFlag(), secondaryHeader.getDestinationId());
    }
  }
  
  public static URI createUri(int qualifier, int apid, int flag, int instanceId) {
    StringBuffer buf = new StringBuffer();
    buf.append(PROTOCOL);
    buf.append(qualifier);
    buf.append(SLASH);
    buf.append(apid);
    if (flag > 0) {
      buf.append(SLASH);
      buf.append(instanceId);
    }
    return new URI(buf.toString());
  }
  
  public static int decodeSecondaryHeader(SecondaryHeader ssh,
      BufferReader bufferReader, int sequenceFlag) throws Exception {
    SecondaryHeaderReader secondaryHeaderReader = new SecondaryHeaderReader(bufferReader);
    secondaryHeaderReader.readAll(sequenceFlag);
    return bufferReader.getIndex();
  }

}
