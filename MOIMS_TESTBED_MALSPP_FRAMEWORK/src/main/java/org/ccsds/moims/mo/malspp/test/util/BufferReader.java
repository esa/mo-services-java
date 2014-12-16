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

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import org.ccsds.moims.mo.mal.MALContextFactory;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malspp.test.datatype.LargeEnumeration;
import org.ccsds.moims.mo.malspp.test.datatype.LargeEnumerationFactory;
import org.ccsds.moims.mo.malspp.test.datatype.MediumEnumeration;
import org.ccsds.moims.mo.malspp.test.datatype.MediumEnumerationFactory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.errors.OrekitException;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.DateComponents;
import org.orekit.time.TimeComponents;
import org.orekit.time.TimeScalesFactory;

public class BufferReader {
  
  public static final double PICO = Math.pow(10, 12);
  
  public static final int CDS_MILLISECOND_FIELD_LENGTH = 4;
  
  public static final String MAL_JAVA_API_FINETIME_EPOCH = "org.ccsds.moims.mo.mal.finetime.epoch";
  
  public static final int signMaskFor24bitInteger = 0x00800000;
  
  public static void init() {
    String path = System.getProperty("org.ccsds.moims.mo.malspp.test.util.orekit.data.path", ".");
    try {
      DataProvidersManager.getInstance().addProvider(new DirectoryCrawler(new File(path)));
    } catch (OrekitException e) {
      throw new RuntimeException(e);
    }
    
    String epochAsString = System.getProperty(MAL_JAVA_API_FINETIME_EPOCH,
        "2013-01-01T00:00:00.000");
    fineTimeMalJavaApiEpoch = new AbsoluteDate(epochAsString,
        TimeScalesFactory.getTAI());
    
    // Initialize BigEnumeration data type
    MALContextFactory.getElementFactoryRegistry().registerElementFactory(
      LargeEnumeration.SHORT_FORM, new LargeEnumerationFactory());
    MALContextFactory.getElementFactoryRegistry().registerElementFactory(
      MediumEnumeration.SHORT_FORM, new MediumEnumerationFactory());
  }
  
  private static AbsoluteDate fineTimeMalJavaApiEpoch;
  
  private byte[] bytes;
  
  private boolean varintSupported;
  
  private int index;
  
  private TimeCode timeCode;
  
  private TimeCode fineTimeCode;
  
  private TimeCode durationCode;

  public BufferReader(byte[] bytes, int offset, boolean varintSupported,
      TimeCode timeCode, TimeCode fineTimeCode,
      TimeCode durationCode) {
    this.bytes = bytes;
    this.index = offset;
    this.varintSupported = varintSupported;
    this.timeCode = timeCode;
    this.fineTimeCode = fineTimeCode;
    this.durationCode = durationCode;
  }

  public byte read() {
    return bytes[index++];
  }
  
  public int readUnsignedByteAsInt() {
    return ((int) read()) & 0xFF;
  }
  
  public short read16() {
    return (short) (((((short) read()) &0xFF) << 8) | (((short) read()) &0xFF));
  }
  
  public int read24() {
    int decoded = ((((int) read()) &0xFF) << 16) |
        ((((int) read()) &0xFF) << 8) | (((int) read()) &0xFF);
    if ((decoded & signMaskFor24bitInteger) != 0) {
      // Negative value
      return decoded | 0xFF000000;
    } else {
      return decoded;
    }
  }
  
  public int read32() {
    return ((((int) read()) &0xFF) << 24) | ((((int) read()) &0xFF) << 16) |
        ((((int) read()) &0xFF) << 8) | (((int) read()) &0xFF);
  }

  public long read64() {
    return ((((long) read()) &0xFFL) << 56) | ((((long) read()) &0xFFL) << 48) |
        ((((long) read()) &0xFFL) << 40) | ((((long) read()) &0xFFL) << 32) |
        ((((long) read()) &0xFFL) << 24) | ((((long) read()) &0xFFL) << 16) |
        ((((long) read()) &0xFFL) << 8) | (((long) read()) &0xFFL);
  }
  
  public int readUnsignedVarInt() {
    int value = 0;
    int i;
    int b;
    for (i = 0; ((b = read()) & 0x80) != 0; i += 7) {
      value |= (b & 0x7f) << i;
    }
    return value | b << i;
  }

  public long readUnsignedVarLong() {
    long value = 0L;
    int i;
    long b;
    for (i = 0; ((b = read()) & 0x80L) != 0L; i += 7) {
      value |= (b & 0x7fL) << i;
    }
    return value | b << i;
  }
  
  public int readSignedVarInt() {
    int i = readUnsignedVarInt();
    return ((i >>> 1) ^ -(i & 1));
  }
  
  public long readSignedVarLong() {
    long l = readUnsignedVarLong();
    return ((l >>> 1) ^ -(l & 1));
  }

  public Identifier readIdentifier() {
  	return new Identifier(readString());
  }
  
  public URI readUri() {
   return new URI(readString());
  }
  
  public boolean isNull() throws Exception {
    byte b = read();
    return (b == 0x00);
  }
  
  public String readString() {
    byte[] bytes = readBytes();
    String res = new String(bytes);
    return res;
  }
  
  public Identifier readNullableIdentifier() throws Exception{
    if (isNull()) return null;
    else return new Identifier(readString());
  }
  
  public UOctet readUOctet() {
    return new UOctet((short) (read() & 0xFF));
  }
  
  public UShort readUShort() {
    if (varintSupported) {
      return new UShort(readUnsignedVarInt() & 0xFFFF);
    } else {
      return new UShort(read16() & 0xFFFF);
    }
  }
  
  public UInteger readUInteger() {
    if (varintSupported) {
      return new UInteger(readUnsignedVarInt() & 0xFFFFFFFFL);
    } else {
      return new UInteger(read32() & 0xFFFFFFFFL);
    }
  }
  
  public ULong readULong() {
    if (varintSupported) {
      long l = readUnsignedVarLong();
      byte[] bigIntegerBytes = new byte[9];
      bigIntegerBytes[0] = 0x00;
      bigIntegerBytes[1] = ((byte) (l >>>  56));
      bigIntegerBytes[2] = ((byte) (l >>> 48));
      bigIntegerBytes[3] = ((byte) (l >>> 40));
      bigIntegerBytes[4] = ((byte) (l >>> 32));
      bigIntegerBytes[5] = ((byte) (l >>> 24));
      bigIntegerBytes[6] = ((byte) (l >>> 16));
      bigIntegerBytes[7] = ((byte) (l >>> 8));
      bigIntegerBytes[8] = ((byte) (l >>> 0));
      return new ULong(new BigInteger(bigIntegerBytes));
    } else {
      byte[] bigIntegerBytes = new byte[9];
      bigIntegerBytes[0] = 0x00;
      bigIntegerBytes[1] = read();
      bigIntegerBytes[2] = read();
      bigIntegerBytes[3] = read();
      bigIntegerBytes[4] = read();
      bigIntegerBytes[5] = read();
      bigIntegerBytes[6] = read();
      bigIntegerBytes[7] = read();
      bigIntegerBytes[8] = read();
      return new ULong(new BigInteger(bigIntegerBytes));
    }
  }
 
  public Blob readBlob() {
    byte[] bytes = readBytes();
    return new Blob(bytes);
  }
  
  public Boolean readBoolean() throws Exception {
  	byte b = read();
  	if (b == 1) {
  		return Boolean.TRUE;
  	} else if (b == 0) {
  		return Boolean.FALSE;
  	} else {
  		throw new IOException("Not a Boolean");
  	}
  }
  
  public Byte readOctet() {
  	return new Byte(read());
  }
  
  public Double readDouble() {
  	return Double.longBitsToDouble(read64());
  }
  
  public Float readFloat() {
  	return Float.intBitsToFloat(read32());
  }
  
  public Short readShort() {
    if (varintSupported) {
      return new Short((short) readSignedVarInt());
    } else {
      return new Short(read16());
    }
  }
  
  public Integer readInteger() {
    if (varintSupported) {
      return new Integer(readSignedVarInt());
    } else {
      return new Integer(read32());
    }
  }
  
  public Long readLong() {
    if (varintSupported) {
      return new Long(readSignedVarLong());
    } else {
      return new Long(read64());
    }
  }
  
  private double readCUCDuration(int coarseTimeLength,
      int fineTimeLength) {
    byte[] timeField = readBytes(coarseTimeLength + fineTimeLength);
    double seconds = 0;
    for (int i = 0; i < coarseTimeLength; ++i) {
      seconds = seconds * 256 + (timeField[i] & 0xFF);
    }
    double subseconds = 0;
    for (int i = timeField.length - 1; i >= coarseTimeLength; --i) {
      subseconds = (subseconds + (timeField[i] & 0xFF)) / 256;
    }
    LoggingBase.logMessage("seconds=" + seconds);
    LoggingBase.logMessage("subseconds=" + subseconds);
    return seconds + subseconds;
  }
 
  private AbsoluteDate readCUCAbsoluteTime(int coarseTimeLength,
      int fineTimeLength, AbsoluteDate epoch) {
    double seconds = readCUCDuration(coarseTimeLength, fineTimeLength);
    AbsoluteDate absoluteDate = new AbsoluteDate(epoch, seconds);
    return absoluteDate;
  }
  
  public Time readTime() throws Exception {
  	return new Time(readTimestamp());
  }
  
  public long readTimestamp() throws Exception {
  	long timestamp;
		switch (timeCode.getType()) {
		case TimeCode.CUC:
		  LoggingBase.logMessage("Read CUC time");
			timestamp = readCUCTime((CUCTimeCode) timeCode);
			break;
		case TimeCode.CDS:
		  LoggingBase.logMessage("Read CDS time");
		  timestamp = readCDSTime((CDSTimeCode) timeCode);
      break;
		default:
			throw new Exception("Unknown time code: " + timeCode);
		}
		return timestamp;
  }
  
  private long readCUCTime(CUCTimeCode timeCode) throws Exception {
	// Time is not well-defined in MAL Java API.
	// First line (CNES): Time value represents apparent elapsed milliseconds since Java epoch.
	// Second line (DLR): Time value represents real elapsed milliseconds since Java
	// epoch.
  //return readCUCAbsoluteTime().toDate(TimeScalesFactory.getUTC()).getTime();
    // TAI epoch of 1958 January 1
    AbsoluteDate epoch = timeCode.getEpoch();
    return Math.round(readCUCAbsoluteTime(timeCode.getBasicTimeLength(),
        timeCode.getFractionalTimeLength(), epoch).durationFrom(
        AbsoluteDate.JAVA_EPOCH) * 1000);
  }
  
  private long readCDSTime(CDSTimeCode timeCode) throws Exception {
    DateComponents epochDate = timeCode.getEpoch().getDate()
        .getComponents(TimeScalesFactory.getUTC()).getDate();
    int daySegmentedLength = timeCode.getDaySegmentLength();
    int submillisecondsLength = timeCode.getSubMillisecondLength();
    byte[] timeField = readBytes(daySegmentedLength
        + CDS_MILLISECOND_FIELD_LENGTH + submillisecondsLength);
    double elapsedDuration = parseSegmentedTimeCode(daySegmentedLength,
        submillisecondsLength, timeField, AbsoluteDate.JAVA_EPOCH, epochDate);
    return Math.round(elapsedDuration);
  }
  
  public static double parseSegmentedTimeCode(int daySegmentLength,
      int submillisecondsLength, byte[] timeField, AbsoluteDate javaEpoch,
      DateComponents epochDate) throws Exception {
    LoggingBase.logMessage("epochDate=" + epochDate);
    
    int i = 0;
    int day = 0;
    while (i < daySegmentLength) {
      LoggingBase.logMessage("byte=" + (timeField[i] & 0xFF));
      day = day * 256 + (timeField[i++] & 0xFF);
    }
    
    LoggingBase.logMessage("day=" + day);
    
    long milliInDay = 0l;
    while (i < daySegmentLength + CDS_MILLISECOND_FIELD_LENGTH) {
      LoggingBase.logMessage("byte=" + (timeField[i] & 0xFF));
      milliInDay = milliInDay * 256 + (timeField[i++] & 0xFF);
    }
    
    LoggingBase.logMessage("milliInDay=" + milliInDay);
    
    final int milli   = (int) (milliInDay % 1000l);
    final int seconds = (int) ((milliInDay - milli) / 1000l);

    double subMilli = 0;
    double divisor  = 1;
    while (i < timeField.length) {
      LoggingBase.logMessage("byte=" + (timeField[i] & 0xFF));
      subMilli = subMilli * 256 + (timeField[i++] & 0xFF);
      divisor *= 1000;
    }
    
    LoggingBase.logMessage("subMilli=" + subMilli);

    final DateComponents date = new DateComponents(epochDate, day);
    final TimeComponents time = new TimeComponents(seconds);
    
    AbsoluteDate resultDate = new AbsoluteDate(date, time,
        TimeScalesFactory.getUTC()).shiftedBy(milli * 1.0e-3 + subMilli
        / divisor);
    LoggingBase.logMessage("resultDate=" + resultDate);
    double resultTime = resultDate.offsetFrom(javaEpoch, TimeScalesFactory.getTAI());
    LoggingBase.logMessage("resultTime=" + resultTime);
    return resultTime * 1000;
  }
  
  public Duration readDuration() throws Exception {
    int duration;
    switch (durationCode.getType()) {
    case TimeCode.CUC:
      duration = readCUCDuration((CUCTimeCode) durationCode);
      break;
    case TimeCode.CDS:
      throw new Exception("Not yet available");
    default:
      throw new Exception("Unknown time code: " + timeCode);
    }
    return new Duration(duration);
  }
  
  private int readCUCDuration(CUCTimeCode durationCode) throws Exception {
    return (int) readCUCDuration(durationCode.getBasicTimeLength(),
        durationCode.getFractionalTimeLength());
  }
  
  public FineTime readFineTime() throws Exception {
    long fineTime;
    switch (fineTimeCode.getType()) {
    case TimeCode.CUC:
      fineTime = readCUCFineTime((CUCTimeCode) fineTimeCode);
      break;
    case TimeCode.CDS:
      fineTime = readCDSFineTime((CDSTimeCode) fineTimeCode);
      break;
    default:
      throw new Exception("Unknown time code: " + timeCode);
    }
  	return new FineTime(fineTime);
  }
  
  public long readCUCFineTime(CUCTimeCode fineTimeCode) throws Exception {
    AbsoluteDate malsppEpoch = fineTimeCode.getEpoch();
    AbsoluteDate absoluteDate = readCUCAbsoluteTime(
        fineTimeCode.getBasicTimeLength(),
        fineTimeCode.getFractionalTimeLength(), malsppEpoch);
    LoggingBase.logMessage("absoluteDate=" + absoluteDate);
    String epochAsString = System.getProperty("org.ccsds.moims.mo.mal.finetime.epoch", "2013-01-01T00:00:00.000");
    AbsoluteDate malJavaEpoch = new AbsoluteDate(epochAsString, TimeScalesFactory.getTAI());
    double offset = absoluteDate.durationFrom(malJavaEpoch);
    LoggingBase.logMessage("duration since Java epoch (seconds) = " + offset);
    // convert to picoseconds
    return (long) (offset * PICO);
  }
  
  public long readCDSFineTime(CDSTimeCode fineTimeCode) throws Exception {
    LoggingBase.logMessage("epoch=" + fineTimeCode.getEpoch());
    DateComponents epochDate = fineTimeCode.getEpoch().getDate()
        .getComponents(TimeScalesFactory.getUTC()).getDate();
    LoggingBase.logMessage("epochDate=" + epochDate);
    int daySegmentedLength = fineTimeCode.getDaySegmentLength();
    int submillisecondsLength = fineTimeCode.getSubMillisecondLength();
    byte[] timeField = readBytes(daySegmentedLength
        + CDS_MILLISECOND_FIELD_LENGTH + submillisecondsLength);
    double elapsedDuration = parseSegmentedTimeCode(daySegmentedLength,
        submillisecondsLength, timeField, fineTimeMalJavaApiEpoch, epochDate);
    return Math.round(elapsedDuration * PICO / 1000);
  }

  public byte[] readBytes() {
    int length = (int) readUInteger().getValue();
    return readBytes(length);
  }
    
  public byte[] readBytes(int length) {
    byte[] res = new byte[length];
    System.arraycopy(bytes, index, res, 0, length);
    index += res.length;
    return res;
  }

  public int getIndex() {
    return index;
  }
  
}
