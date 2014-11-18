/*
 Copyright (C) 2014, Deutsches Zentrum für Luft- und Raumfahrt e.V.,
 Author: Stefan Gärtner

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.ccsds.moims.mo.malspp.test.segmentation;

import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.transport.MALTransmitErrorException;
import org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestStub;
import org.ccsds.moims.mo.malspp.test.datatype.MalSppDataTypeTest;
import org.ccsds.moims.mo.malspp.test.sppinterceptor.SPPInterceptor;
import org.ccsds.moims.mo.malspp.test.sppinterceptor.SPPInterceptorSocket;
import org.ccsds.moims.mo.malspp.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malspp.test.util.SecondaryHeader;
import org.ccsds.moims.mo.testbed.util.spp.SpacePacketHeader;

public class MalSppSegmentationTest extends MalSppDataTypeTest {

  private static final int PACKET_DATA_FIELD_SIZE_LIMIT = 65536; // max
  protected static final int LARGE_BLOB_SIZE = 3 * PACKET_DATA_FIELD_SIZE_LIMIT; // this leads to 4 packets due to the extra data in the secondary header
  public static final Blob testLargeBlob = createTestLargeBlob();
  public static final Blob testSmallBlob = new Blob(new byte[]{1, 2, 3});

  public static final String testLongString = "ABCD";
  public static final String testEmptyString = "";
  private String testString;
  private DataTestStub segErrorStub;

  private SpacePacketHeader firstPrimaryHeader;
  private SecondaryHeader firstSecondaryHeader;

  protected static Blob createTestLargeBlob() {
    byte[] b = new byte[LARGE_BLOB_SIZE];
    for (int i = 0; i < b.length; i++) {
      b[i] = (byte) i;
    }
    return new Blob(b);
  }

  public String smallBlobWorks() throws MALInteractionException, MALException {
    String rv;
    logMessage("Starting small blob data test...");
    try {
      rv = subSingleTest(testSmallBlob, LocalMALInstance.instance().segmentationStub().testData(testSmallBlob), "small blob");
    } catch (MALInteractionException ex) {
      rv = subSingleTestExceptionHandler(ex, "small blob");
    }
    logMessage("Finished explicit small blob data test");
    return rv;
  }

  public String largeBlobWorks() throws MALInteractionException, MALException {
    String rv;
    logMessage("Starting large blob data test...");
    try {
      rv = subSingleTest(testLargeBlob, LocalMALInstance.instance().segmentationStub().testDataBlob(testLargeBlob), "large blob");
    } catch (MALInteractionException ex) {
      rv = subSingleTestExceptionHandler(ex, "large blob");
    }
    logMessage("Finished explicit large blob data test");
    return rv;
  }

  public int numberOfSentPacketsIs() {
    return SPPInterceptor.instance().getSentPacketCount();
  }

  public int primaryHeaderSequenceFlagsAre() {
    return primaryHeader.getSequenceFlags();
  }

  public int primaryHeaderSequenceCounterIsInitialCounterPlus() {
    return primaryHeader.getSequenceCount() - firstPrimaryHeader.getSequenceCount();
  }

  public long secondaryHeaderSegmentCounterIs() {
    return secondaryHeader.getSegmentCounter();
  }

  public boolean malMessageHeaderMappingComparesPacketAtToFirstPacket(int index) {
    if (index == 0) {
      firstPrimaryHeader = primaryHeader;
      firstSecondaryHeader = secondaryHeader;
    }
    if (null == firstPrimaryHeader || null == firstSecondaryHeader) {
      return false;
    }
    return true;
  }

  public boolean packetDataLengthIsConsistentWith(int sequence) {
    switch (sequence) {
      case 0: // continuation
        return packetBody.length == PACKET_DATA_FIELD_SIZE_LIMIT;
      case 1: // first segment
        return packetBody.length == PACKET_DATA_FIELD_SIZE_LIMIT;
      case 2: // last segment
        return packetBody.length <= PACKET_DATA_FIELD_SIZE_LIMIT;
      case 3: // unsegmented
        return packetBody.length <= PACKET_DATA_FIELD_SIZE_LIMIT;
    }
    return false;
  }

  public boolean samePacketVersionNumber() {
    return firstPrimaryHeader.getPacketVersionNumber() == primaryHeader.getPacketVersionNumber();
  }

  public boolean samePacketType() {
    return firstPrimaryHeader.getPacketType() == primaryHeader.getPacketType();
  }

  public boolean sameSecondaryHeaderFlag() {
    return firstPrimaryHeader.getSecondaryHeaderFlag() == primaryHeader.getSecondaryHeaderFlag();
  }

  public boolean sameApid() {
    return firstPrimaryHeader.getApid() == primaryHeader.getApid();
  }

  public boolean sameVersionNumber() {
    return firstSecondaryHeader.getMalsppVersion() == secondaryHeader.getMalsppVersion();
  }

  public boolean sameSduType() {
    return firstSecondaryHeader.getSduType() == secondaryHeader.getSduType();
  }

  public boolean sameServiceArea() {
    return firstSecondaryHeader.getArea() == secondaryHeader.getArea();
  }

  public boolean sameService() {
    return firstSecondaryHeader.getService() == secondaryHeader.getService();
  }

  public boolean sameOperation() {
    return firstSecondaryHeader.getOperation() == secondaryHeader.getOperation();
  }

  public boolean sameAreaVersion() {
    return firstSecondaryHeader.getAreaVersion() == secondaryHeader.getAreaVersion();
  }

  public boolean sameIsErrorMessage() {
    return firstSecondaryHeader.getIsError() == secondaryHeader.getIsError();
  }

  public boolean sameQosLevel() {
    return firstSecondaryHeader.getQos() == secondaryHeader.getQos();
  }

  public boolean sameSession() {
    return firstSecondaryHeader.getSession() == secondaryHeader.getSession();
  }

  public boolean sameSecondaryApid() {
    return firstSecondaryHeader.getSecondaryApid() == secondaryHeader.getSecondaryApid();
  }

  public boolean sameSecondaryApidQualifier() {
    return firstSecondaryHeader.getSecondaryApidQualifier() == secondaryHeader.getSecondaryApidQualifier();
  }

  public boolean sameTransactionId() {
    return firstSecondaryHeader.getTransactionId() == secondaryHeader.getTransactionId();
  }

  public boolean sameSourceIdFlag() {
    return firstSecondaryHeader.getSourceIdFlag() == secondaryHeader.getSourceIdFlag();
  }

  public boolean sameDestinationIdFlag() {
    return firstSecondaryHeader.getDestinationIdFlag() == secondaryHeader.getDestinationIdFlag();
  }

  public boolean samePriorityFlag() {
    return firstSecondaryHeader.getPriorityFlag() == secondaryHeader.getPriorityFlag();
  }

  public boolean sameTimestampFlag() {
    return firstSecondaryHeader.getTimestampFlag() == secondaryHeader.getTimestampFlag();
  }

  public boolean sameNetworkZoneFlag() {
    return firstSecondaryHeader.getNetworkZoneFlag() == secondaryHeader.getNetworkZoneFlag();
  }

  public boolean sameSessionNameFlag() {
    return firstSecondaryHeader.getSessionNameFlag() == secondaryHeader.getSessionNameFlag();
  }

  public boolean sameDomainFlag() {
    return firstSecondaryHeader.getDomainFlag() == secondaryHeader.getDomainFlag();
  }

  public boolean sameAuthenticationIdFlag() {
    return firstSecondaryHeader.getAuthenticationIdFlag() == secondaryHeader.getAuthenticationIdFlag();
  }

  public boolean sameSourceId() {
    if (secondaryHeader.getSourceIdFlag() == 1) {
      return firstSecondaryHeader.getSourceId() == secondaryHeader.getSourceId();
    }
    return true;
  }

  public boolean sameDestinationId() {
    if (secondaryHeader.getDestinationIdFlag() == 1) {
      return firstSecondaryHeader.getDestinationId() == secondaryHeader.getDestinationId();
    }
    return true;
  }

  public boolean samePriority() {
    if (secondaryHeader.getPriorityFlag() == 1) {
      return firstSecondaryHeader.getPriority() == secondaryHeader.getPriority();
    }
    return true;
  }

  public boolean sameTimestamp() {
    if (secondaryHeader.getTimestampFlag() == 1) {
      return firstSecondaryHeader.getTimestamp() == secondaryHeader.getTimestamp();
    }
    return true;
  }

  public boolean sameNetworkZone() {
    if (secondaryHeader.getNetworkZoneFlag() == 1) {
      return firstSecondaryHeader.getNetworkZone().equals(secondaryHeader.getNetworkZone());
    }
    return true;
  }

  public boolean sameSessionName() {
    if (secondaryHeader.getSessionNameFlag() == 1) {
      return firstSecondaryHeader.getSessionName().equals(secondaryHeader.getSessionName());
    }
    return true;
  }

  public boolean sameDomain() {
    if (secondaryHeader.getDomainFlag() == 1) {
      return firstSecondaryHeader.getDomain().equals(secondaryHeader.getDomain());
    }
    return true;
  }

  public boolean sameAuthenticationId() {
    if (secondaryHeader.getAuthenticationIdFlag() == 1) {
      return Arrays.equals(firstSecondaryHeader.getAuthenticationId(), secondaryHeader.getAuthenticationId());
    }
    return true;
  }

  public boolean perMessageQosPropertiesForSecondaryHeader(String comparison) throws MALException {
    if (comparison.equals("smaller")) {
      segErrorStub = LocalMALInstance.instance().segmentationErrorSmallHeaderStub();
      return true;
    } else if (comparison.equals("equal")) {
      segErrorStub = LocalMALInstance.instance().segmentationErrorSmallHeaderStub();
      return true;
    } else if (comparison.equals("larger")) {
      segErrorStub = LocalMALInstance.instance().segmentationErrorLargeHeaderStub();
      return true;
    }
    return false;
  }

  public boolean stringBodyForSecondaryHeader(String comparison) {
    if (comparison.equals("smaller")) {
      testString = testEmptyString;
      return true;
    } else if (comparison.equals("equal")) {
      testString = testLongString;
      return true;
    } else if (comparison.equals("larger")) {
      testString = testLongString;
      return true;
    }
    return false;
  }

  public String stringTest() throws MALInteractionException, MALException {
    String rv;
    logMessage("Starting packet data field size string data test...");
    try {
      rv = subSingleTest(testString, segErrorStub.testDataString(testString), "string segmentation");
    } catch (MALTransmitErrorException ex) {
      boolean expected = ex.getStandardError().getErrorNumber().getValue() == MALHelper._INTERNAL_ERROR_NUMBER;
      if (expected) {
        rv = "generates internal error";
      } else {
        rv = ex.toString();
      }
    }
    logMessage("Finished packet data field size string data test");
    return rv;
  }

  public boolean scramblingPattern(String[] pattern) throws Exception {
    //Thread.sleep(2000);
    logMessage("Set scrambling pattern to " + Arrays.toString(pattern));
    int[] p = new int[pattern.length];
    for (int i = 0; i < pattern.length; i++) {
      p[i] = Integer.parseInt(pattern[i]);
    }
    if (p.length == 0) {
      p = null;
    }
    SPPInterceptorSocket.setScramblePattern(p);
    return true;
  }

}
