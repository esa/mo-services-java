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

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.errors.OrekitException;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;

public class BufferReader {
  
  public static void init() {
    String path = System.getProperty("org.ccsds.moims.mo.malspp.test.util.orekit.data.path", "./target");
    try {
      DataProvidersManager.getInstance().addProvider(new DirectoryCrawler(new File(path)));
    } catch (OrekitException e) {
      throw new RuntimeException(e);
    } 
  }
	
	public static class TimeFormat {
    public final static int NO_TIME = 0;
    public final static int CUC = 1;
    public final static int CDS = 2;
  }

	public static final double PICO = Math.pow(10, 12);
	
  private byte[] bytes;
  
  private boolean varintSupported;
  
  private int index;

  public BufferReader(byte[] bytes, int offset, boolean varintSupported) {
    this.bytes = bytes;
    this.varintSupported = varintSupported;
    index = offset;
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
    return ((((int) read()) &0xFF) << 16) |
        ((((int) read()) &0xFF) << 8) | (((int) read()) &0xFF);
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
  
  public String readString() {
    byte[] bytes = readBytes();
    String res = new String(bytes);
    return res;
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
 
  private AbsoluteDate readCUCAbsoluteTime() {
  	byte coarseTimeLength = 4;
    byte fineTimeLength = 3;
    byte[] timeField = readBytes(coarseTimeLength + fineTimeLength);
    // TAI epoch of 1958 January 1
    AbsoluteDate agencyDefinedEpoch = new AbsoluteDate(1958, 1, 1, TimeScalesFactory.getTAI());
    double seconds = 0;
    for (int i = 0; i < coarseTimeLength; ++i) {
      seconds = seconds * 256 + (timeField[i] & 0xFF);
    }
    double subseconds = 0;
    for (int i = timeField.length - 1; i >= coarseTimeLength; --i) {
      subseconds = (subseconds + (timeField[i] & 0xFF)) / 256;
    }
    AbsoluteDate absoluteDate = new AbsoluteDate(agencyDefinedEpoch, seconds).shiftedBy(subseconds);
    return absoluteDate;
  }
  
  public Time readTime(int timeFormat) throws Exception {
  	return new Time(readTimestamp(timeFormat));
  }
  
  public long readTimestamp(int timeFormat) throws Exception {
  	long timestamp;
		switch (timeFormat) {
		case BufferReader.TimeFormat.NO_TIME:
			timestamp = 0;
			break;
		case BufferReader.TimeFormat.CUC:
			timestamp = readCUCTime();
			break;
		case BufferReader.TimeFormat.CDS:
			throw new Exception("Not yet available");
		default:
			throw new Exception("Unknown time format: " + timeFormat);
		}
		return timestamp;
  }
  
  public long readCUCTime() throws Exception {
	// Time is not well-defined in MAL Java API.
	// First line (CNES): Time value represents apparent elapsed milliseconds since Java epoch.
	// Second line (DLR): Time value represents real elapsed milliseconds since Java
	// epoch.
  	return readCUCAbsoluteTime().toDate(TimeScalesFactory.getUTC()).getTime();
	//return Math.round(readCUCAbsoluteTime().durationFrom(AbsoluteDate.JAVA_EPOCH) * 1000);
  }
  
  public Duration readDuration() throws Exception {
    return new Duration(readCUCDuration());
  }
  
  public int readCUCDuration() throws Exception {
    // 4 bytes
    int duration = read32();
    // 3 bytes
    // There is currently nothing decoded because of the 'int' format of
    // MAL::Duration in the MAL Java API. This bug should be fixed by changing
    // the type to 'float'.
    return duration;
  }
  
  public FineTime readFineTime(int timeFormat) throws Exception {
  	return new FineTime(readCUCFineTime(timeFormat));
  }
  
  public long readCUCFineTime(int timeFormat) throws Exception {
    AbsoluteDate absoluteDate = readCUCAbsoluteTime();
    String epochAsString = System.getProperty("org.ccsds.moims.mo.mal.finetime.epoch", "2013-01-01T00:00:00.000");
    AbsoluteDate epoch = new AbsoluteDate(epochAsString, TimeScalesFactory.getUTC());
    double offset = absoluteDate.offsetFrom(epoch, TimeScalesFactory.getUTC());
    // convert to picoseconds
    return (long) (offset * PICO);
  }

  public byte[] readBytes() {
    int length = readUnsignedVarInt();
    return readBytes(length);
  }
    
  public byte[] readBytes(int length) {
    byte[] res = new byte[length];
    System.arraycopy(bytes, index, res, 0, length);
    index += res.length;
    return res;
  }
  
  public IdentifierList readIdentifierList() {
    int size = readUnsignedVarInt();
    IdentifierList res = new IdentifierList(size);
    for (int i = 0; i < size; i++) {
      res.add(readIdentifier());
    }
    return res;
  }

  public int getIndex() {
    return index;
  }
  
}
