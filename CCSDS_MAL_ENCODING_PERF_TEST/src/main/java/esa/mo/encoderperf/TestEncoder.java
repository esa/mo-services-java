/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Encoder performance test
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.encoderperf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeFactory;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.perftest.perftest.PerfTestHelper;
import org.ccsds.moims.mo.perftest.structures.*;
import org.ccsds.moims.mo.xml.test.CompactStatusUpdate;


public class TestEncoder
{
  public static void main(String[] args) throws Exception
  {
    List<Results> results = new LinkedList<Results>();

    int size = 100;
    int count = 100;

    Object testXMLComposite = createTestXMLComposite(size);

    results.add(new Results("esa.mo.encoderperf.TestXMLStreamFactory", false, false, testXMLComposite, testXMLComposite));
    //results.add(new Results("esa.mo.mal.encoder.line.LineStreamFactory", false, true, size));
    results.add(new Results("esa.mo.mal.encoder.string.StringStreamFactory", false, false, size));
    results.add(new Results("esa.mo.mal.encoder.binary.BinaryStreamFactory", false, false, size));
    results.add(new Results("esa.mo.mal.encoder.binary.split.SplitBinaryStreamFactory", false, false, size));
    results.add(new Results("esa.mo.encoderperf.TestXMLStreamFactory", true, false, testXMLComposite, testXMLComposite));
    //results.add(new Results("esa.mo.mal.encoder.line.LineStreamFactory", true, false, size));
    results.add(new Results("esa.mo.mal.encoder.string.StringStreamFactory", true, false, size));
    results.add(new Results("esa.mo.mal.encoder.binary.BinaryStreamFactory", true, false, size));
    results.add(new Results("esa.mo.mal.encoder.binary.split.SplitBinaryStreamFactory", true, false, size));

    runtests(results, count, true);

    System.out.println("Times are in microseconds per encode/decode, packet size is in bytes, differences are in %.");
    System.out.println("                                      Encode(us)  Decode(us)  Encode(PPS)  Decode(PPS)  Size(bytes)  Valid");
    for (Results res : results)
    {
      long eTime = (long) (((double) res.encodeTime) / ((double) (count * 1000)));
      long dTime = (long) (((double) res.decodeTime) / ((double) (count * 1000)));
      System.out.println(" " + String.format("%35s", res.encoderName)
              + "   " + String.format("%6d", eTime)
              + "      " + String.format("%6d", dTime)
              + "      " + String.format("%7d", (long) ((1000000.0 / ((float) eTime)) * size * size))
              + "      " + String.format("%7d", (long) ((1000000.0 / ((float) dTime)) * size * size))
              + "      " + String.format("%7d", res.packetSize)
              + "      " + res.decodedCorrectly);
    }
  }

  protected static void runtests(List<Results> results, int count, boolean testDecode) throws Exception
  {
    System.out.println("Creating stream factories");
    for (Results result : results)
    {
      result.factory = (MALElementStreamFactory) Class.forName(result.factoryClassName).newInstance();
      result.encoderName = result.factory.getClass().getSimpleName();
      if (result.compress)
      {
        result.encoderName += " with GZip";
      }
    }

    System.out.println("Creating objects");
    org.ccsds.moims.mo.perftest.PerfTestHelper.deepInit(MALContextFactory.getElementFactoryRegistry());

    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("ccsds"));
    domain.add(new Identifier("mission"));
    domain.add(null);
    Identifier nz = new Identifier("network");
    MALEncodingContext ctx = new MyMALContext(new DUMMYMessageHeader(new URI("from"),
            new Blob("".getBytes()),
            new URI("to"),
            new Time(12345678),
            QoSLevel.ASSURED,
            new UInteger(1),
            domain,
            nz,
            SessionType.LIVE,
            new Identifier("LIVE"),
            InteractionType.SEND,
            new UOctet((short) 0),
            Long.MIN_VALUE,
            PerfTestHelper.PERFTEST_SERVICE.getArea().getNumber(),
            PerfTestHelper.PERFTEST_SERVICE.getNumber(),
            PerfTestHelper.SEND_OP_NUMBER,
            PerfTestHelper.PERFTEST_SERVICE.getArea().getVersion(),
            Boolean.FALSE), PerfTestHelper.SEND_OP, 0, null, null);

    System.out.println("Running tests");
    for (Results result : results)
    {
      if (result.compress)
      {
        check(result, new TestGZipStreamFactory(result.factory), count, testDecode, result.objectToEncode, result.blankToEncode, ctx);
      }
      else
      {
        check(result, result.factory, count, testDecode, result.objectToEncode, result.blankToEncode, ctx);
      }
    }

    System.out.println("Checking tests");
    Results presult = null;
    for (int i = 0; i < results.size(); i++)
    {
      Results result = results.get(i);

      if (result.compareable)
      {
        if (null != presult)
        {
          System.out.println("Equal: " + compareObjects(presult.objectToEncode, result.objectToEncode));
        }

        presult = result;
      }
    }
  }

  protected static void check(Results result, MALElementStreamFactory streamFactory, int count, boolean testDecode, Object testComposite, Object blankComposite, MALEncodingContext ctx) throws Exception
  {
    System.out.println("Testing  : " + result.encoderName);

    ByteArrayOutputStream baos = testEncoder(result, streamFactory, count, testComposite, ctx);

    if (result.dump)
    {
      java.io.File outputFile = new java.io.File(result.encoderName + ".txt");
      java.io.FileOutputStream fos = new FileOutputStream(outputFile);
      fos.write(baos.toByteArray());
      fos.close();
    }

    if (testDecode)
    {
      try
      {
        result.decodedCorrectly = testDecoder(result, streamFactory, count, baos, testComposite, blankComposite, ctx);
      }
      catch (Throwable ex)
      {
        result.decodedCorrectly = false;
        ex.printStackTrace();
        System.out.println("FAILED decoding");
      }
    }
    System.out.println("Finished : " + result.encoderName);
  }

  protected static ByteArrayOutputStream testEncoder(Results result, MALElementStreamFactory streamFactory, int count, Object testComposite, MALEncodingContext ctx) throws Exception
  {
    ByteArrayOutputStream baos = null;

    System.out.println("Starting encoding...");
    long startTime = System.nanoTime();
    for (int i = 0; i < count; i++)
    {
      baos = new ByteArrayOutputStream();
      MALElementOutputStream encoder = streamFactory.createOutputStream(baos);

      encoder.writeElement(testComposite, ctx);
      encoder.flush();
      encoder.close();
    }
    long stopTime = System.nanoTime();

    result.encodeTime = stopTime - startTime;
    result.packetSize = baos.size();

    System.out.println("Finished encoding");
    byte[] data = baos.toByteArray();
    System.out.println("  DUMP : Size " + data.length);
    //System.out.println("  DUMP : " + byteArrayToHexString(data));
    return baos;
  }

  protected static boolean testDecoder(Results result, MALElementStreamFactory streamFactory, int count, ByteArrayOutputStream encodedValue, Object testComposite, Object blankComposite, MALEncodingContext ctx) throws Exception
  {
    Object rv = null;

    System.out.println("Starting decoding...");
    long startTime = System.nanoTime();
    for (int i = 0; i < count; i++)
    {
      byte[] bbuf = encodedValue.toByteArray();
      ByteArrayInputStream bais = new ByteArrayInputStream(bbuf);
      MALElementInputStream decoder = streamFactory.createInputStream(bais);
//      MALElementInputStream decoder = streamFactory.createInputStream(bbuf, 0);
      rv = decoder.readElement(blankComposite, ctx);
      decoder.close();
    }
    long stopTime = System.nanoTime();
    result.decodeTime = stopTime - startTime;
    System.out.println("Finished decoding");

    return compareObjects(testComposite, rv);
  }

  protected static boolean compareObjects(Object original, Object newVersion)
  {
    if (original instanceof Element)
    {
      return original.equals(newVersion);
    }
    else if (original instanceof org.ccsds.moims.mo.xml.test.CompactArchiveReport)
    {
      org.ccsds.moims.mo.xml.test.CompactArchiveReport car_o = (org.ccsds.moims.mo.xml.test.CompactArchiveReport) original;
      org.ccsds.moims.mo.xml.test.CompactArchiveReport car_n = (org.ccsds.moims.mo.xml.test.CompactArchiveReport) newVersion;

      boolean theSame = true;

      theSame &= Arrays.equals(car_o.getDomain().toArray(new String[0]), car_n.getDomain().toArray(new String[0]));
      theSame &= car_o.getNetworkZone().equals(car_n.getNetworkZone());
      theSame &= car_o.getArea() == car_n.getArea();
      theSame &= car_o.getService() == car_n.getService();
      theSame &= compareStatusUpdates(car_o.getStatusUpdates(), car_n.getStatusUpdates());
      theSame &= compareStatusUpdates(car_o.getEventUpdates(), car_n.getEventUpdates());

      return theSame;
    }

    return false;
  }

  protected static boolean compareStatusUpdates(List<org.ccsds.moims.mo.xml.test.CompactStatusUpdate> left, List<org.ccsds.moims.mo.xml.test.CompactStatusUpdate> right)
  {
    boolean theSame = left.size() == right.size();

    for (int i = 0; theSame && (i < right.size()); i++)
    {
      CompactStatusUpdate u_left = left.get(i);
      CompactStatusUpdate u_right = right.get(i);

      theSame = compareStatusUpdates(u_left, u_right);
    }

    return theSame;
  }

  protected static boolean compareStatusUpdates(org.ccsds.moims.mo.xml.test.CompactStatusUpdate left, org.ccsds.moims.mo.xml.test.CompactStatusUpdate right)
  {
    boolean theSame = true;

    theSame &= left.getKey().getEntityId().equals(right.getKey().getEntityId());
    theSame &= left.getKey().getDefinitionId() == right.getKey().getDefinitionId();
    theSame &= left.getKey().getOccurrenceId() == right.getKey().getOccurrenceId();
    theSame &= left.getKey().getStatusId() == right.getKey().getStatusId();
    theSame &= left.getSourceURI().equals(right.getSourceURI());
    theSame &= compareStatus(left.getUpdates(), right.getUpdates());

    return theSame;
  }

  protected static boolean compareStatus(List<org.ccsds.moims.mo.xml.test.CompactStatus> left, List<org.ccsds.moims.mo.xml.test.CompactStatus> right)
  {
    boolean theSame = left.size() == right.size();

    for (int i = 0; theSame && (i < right.size()); i++)
    {
      org.ccsds.moims.mo.xml.test.CompactStatus u_left = left.get(i);
      org.ccsds.moims.mo.xml.test.CompactStatus u_right = right.get(i);

      theSame = compareStatus(u_left, u_right);
    }

    return theSame;
  }

  protected static boolean compareStatus(org.ccsds.moims.mo.xml.test.CompactStatus left, org.ccsds.moims.mo.xml.test.CompactStatus right)
  {
    boolean theSame = true;

    theSame &= 0 == left.getTimestamp().compare(right.getTimestamp());
    theSame &= 0 == left.getUpdateType().compareTo(right.getUpdateType());
    theSame &= Arrays.equals(left.getUpdateSource().getDomain().toArray(new String[0]), right.getUpdateSource().getDomain().toArray(new String[0]));
    theSame &= left.getUpdateSource().getNetworkZone().equals(right.getUpdateSource().getNetworkZone());
    theSame &= left.getUpdateSource().getArea() == right.getUpdateSource().getArea();
    theSame &= left.getUpdateSource().getService() == right.getUpdateSource().getService();
    theSame &= left.getUpdateSource().getOperation() == right.getUpdateSource().getOperation();
    theSame &= left.getUpdateSource().getSourceKey().getEntityId().equals(right.getUpdateSource().getSourceKey().getEntityId());
    theSame &= left.getUpdateSource().getSourceKey().getDefinitionId() == right.getUpdateSource().getSourceKey().getDefinitionId();
    theSame &= left.getUpdateSource().getSourceKey().getOccurrenceId() == right.getUpdateSource().getSourceKey().getOccurrenceId();

    theSame &= left.getStatus().getRawValue().equals(right.getStatus().getRawValue());
    theSame &= 0 == left.getStatus().getValidityState().compareTo(right.getStatus().getValidityState());
    theSame &= left.getStatus().getConvertedValue().equals(right.getStatus().getConvertedValue());

    return theSame;
  }

  protected static Composite createTestMALComposite(int count)
  {
    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("ccsds"));
    domain.add(new Identifier("mission"));
    domain.add(null);
    Identifier nz = new Identifier("network");
    OccurrenceKey src = new OccurrenceKey(new Identifier("OccKey"), 1, 1);
    xxCompactStatusUpdateList statusList = new xxCompactStatusUpdateList();

    Time timestamp = new Time(count);

    for (int i = 0; i < count; i++)
    {
      statusList.add(createTestMALCompactStatusUpdate(timestamp, domain, nz, src, count));
    }

    return new xxCompactArchiveReport(domain,
            new Identifier("Network Zone"),
            Short.MIN_VALUE, Short.MIN_VALUE, statusList, new xxCompactStatusUpdateList());
  }

  protected static xxCompactStatusUpdate createTestMALCompactStatusUpdate(Time timestamp, IdentifierList domain, Identifier nz, OccurrenceKey src, int count)
  {
    ExternalReference exRef = new ExternalReference(domain, nz, Short.MIN_VALUE, Short.MIN_VALUE, Short.MIN_VALUE, src);
    xxComplexParameterValue statusValue = new xxComplexParameterValue(new Union(count), Validity.VALID, new Blob("1234".getBytes()));
    xxCompactStatus status = new xxCompactStatus(timestamp, UpdateType.MODIFICATION, exRef, statusValue);

    xxCompactStatusList compactStatusList = new xxCompactStatusList();

    for (int i = 0; i < count; i++)
    {
      compactStatusList.add(status);
    }

    StatusKey statusKey = new StatusKey(new Identifier("EntityId"), 1, 2, count);
    return new xxCompactStatusUpdate(statusKey, new URI("http://www.ccsds.org"), compactStatusList);
  }

  protected static org.ccsds.moims.mo.xml.test.CompactArchiveReport createTestXMLComposite(int count) throws Exception
  {
    List<String> domain = new ArrayList();
    domain.add("ccsds");
    domain.add("mission");
    String nz = "network";
    org.ccsds.moims.mo.xml.test.OccurrenceKey src = new org.ccsds.moims.mo.xml.test.OccurrenceKey();
    src.setEntityId("OccKey");
    src.setDefinitionId(1);
    src.setOccurrenceId(1);
    List<org.ccsds.moims.mo.xml.test.CompactStatusUpdate> statusList = new ArrayList();

    DatatypeFactory xmlDatatypeFactory = DatatypeFactory.newInstance();

    GregorianCalendar gcal = new GregorianCalendar();
    gcal.setTime(new Date());

    for (int i = 0; i < count; i++)
    {
      statusList.add(createTestXMLCompactStatusUpdate(gcal, xmlDatatypeFactory, domain, nz, src, count));
    }

    org.ccsds.moims.mo.xml.test.CompactArchiveReport rv = new org.ccsds.moims.mo.xml.test.CompactArchiveReport();
    rv.getDomain().addAll(domain);
    rv.setNetworkZone("Network Zone");
    rv.setArea(Short.MIN_VALUE);
    rv.setService(Short.MIN_VALUE);
    rv.getStatusUpdates().addAll(statusList);

    return rv;
  }

  protected static org.ccsds.moims.mo.xml.test.CompactStatusUpdate createTestXMLCompactStatusUpdate(GregorianCalendar gcal, DatatypeFactory xmlDatatypeFactory, List<String> domain, String nz, org.ccsds.moims.mo.xml.test.OccurrenceKey src, int count)
  {
    org.ccsds.moims.mo.xml.test.ExternalReference exRef = new org.ccsds.moims.mo.xml.test.ExternalReference();
    exRef.getDomain().addAll(domain);
    exRef.setNetworkZone(nz);
    exRef.setArea(Short.MIN_VALUE);
    exRef.setService(Short.MIN_VALUE);
    exRef.setSourceKey(src);

    org.ccsds.moims.mo.xml.test.ComplexParameterValue statusValue = new org.ccsds.moims.mo.xml.test.ComplexParameterValue();
    statusValue.setRawValue(count);
    statusValue.setValidityState(org.ccsds.moims.mo.xml.test.Validity.VALID);
    statusValue.setConvertedValue(Boolean.TRUE);

    org.ccsds.moims.mo.xml.test.CompactStatus status = new org.ccsds.moims.mo.xml.test.CompactStatus();

    status.setTimestamp(xmlDatatypeFactory.newXMLGregorianCalendar(gcal));
    status.setUpdateType(org.ccsds.moims.mo.xml.test.UpdateType.MODIFICATION);
    status.setUpdateSource(exRef);
    status.setStatus(statusValue);

    List<org.ccsds.moims.mo.xml.test.CompactStatus> compactStatusList = new ArrayList();

    for (int i = 0; i < count; i++)
    {
      compactStatusList.add(status);
    }

    org.ccsds.moims.mo.xml.test.StatusKey statusKey = new org.ccsds.moims.mo.xml.test.StatusKey();
    statusKey.setEntityId("EntityId");
    statusKey.setDefinitionId(1);
    statusKey.setOccurrenceId(2);
    statusKey.setStatusId(count);

    org.ccsds.moims.mo.xml.test.CompactStatusUpdate rv = new org.ccsds.moims.mo.xml.test.CompactStatusUpdate();
    rv.setKey(statusKey);
    rv.setSourceURI("http://www.ccsds.org");
    rv.getUpdates().addAll(compactStatusList);

    return rv;
  }

  public static String byteArrayToHexString(byte[] data)
  {
    StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < data.length; i++)
    {
      String hex = Integer.toHexString(0xFF & data[i]);
      if (hex.length() == 1)
      {
        // could use a for loop, but we're only dealing with a single byte
        hexString.append('0');
      }
      hexString.append(hex);
    }

    return hexString.toString();
  }

  protected static class Results
  {
    final String factoryClassName;
    final boolean compress;
    final boolean dump;
    final Object objectToEncode;
    final Object blankToEncode;
    final boolean compareable;
    MALElementStreamFactory factory;
    String encoderName;
    long encodeTime;
    long decodeTime;
    long packetSize;
    boolean decodedCorrectly = false;

    public Results(String factoryClassName, boolean compress, boolean dump, Object objectToEncode, Object blankToEncode)
    {
      this.factoryClassName = factoryClassName;
      this.compress = compress;
      this.dump = dump;
      this.objectToEncode = objectToEncode;
      this.blankToEncode = blankToEncode;
      this.compareable = false;
    }

    public Results(String factoryClassName, boolean compress, boolean dump, int size)
    {
      this.factoryClassName = factoryClassName;
      this.compress = compress;
      this.dump = dump;
      this.objectToEncode = createTestMALComposite(size);
      this.blankToEncode = new xxCompactArchiveReport();
      this.compareable = true;
    }
  }

  protected static class MyMALContext extends MALEncodingContext
  {
    public MyMALContext(MALMessageHeader header, MALOperation operation, int bodyElementIndex, Map endpointQosProperties, Map messageQosProperties)
    {
      super(header, operation, bodyElementIndex, endpointQosProperties, messageQosProperties);
    }

    @Override
    public int getBodyElementIndex()
    {
      return super.getBodyElementIndex();
    }

    @Override
    public Map getEndpointQosProperties()
    {
      return super.getEndpointQosProperties();
    }

    @Override
    public MALMessageHeader getHeader()
    {
      return super.getHeader();
    }

    @Override
    public Map getMessageQosProperties()
    {
      return super.getMessageQosProperties();
    }

    @Override
    public MALOperation getOperation()
    {
      return super.getOperation();
    }

    @Override
    public void setBodyElementIndex(int bodyElementIndex)
    {
      super.setBodyElementIndex(bodyElementIndex);
    }

    @Override
    public void setEndpointQosProperties(Map endpointQosProperties)
    {
      super.setEndpointQosProperties(endpointQosProperties);
    }

    @Override
    public void setHeader(MALMessageHeader header)
    {
      super.setHeader(header);
    }

    @Override
    public void setMessageQosProperties(Map messageQosProperties)
    {
      super.setMessageQosProperties(messageQosProperties);
    }

    @Override
    public void setOperation(MALOperation operation)
    {
      super.setOperation(operation);
    }
  }
}
