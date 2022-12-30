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
package esa.mo.performance.encoder;

import esa.mo.performance.util.TestStructureBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

public class TestEncoder {

    public static void main(String[] args) throws Exception {
        List<Results> results = new LinkedList<>();

        int runCount = 100;
        int pktsPerReport = 1;
        int paramsPerPkt = 1000;
        boolean testDecode = true;
        boolean dumpBinary = false;

        Date now = new Date();
        Time timestamp = new Time(now.getTime());

        Object testXMLComposite = TestStructureBuilder.createTestXMLComposite(now, pktsPerReport, paramsPerPkt);

        results.add(new Results("esa.mo.performance.encoder.TestXMLStreamFactory", false, false, testXMLComposite, testXMLComposite));
        //results.add(new Results("esa.mo.mal.encoder.line.LineStreamFactory", false, true, size));
        results.add(new Results("esa.mo.mal.encoder.string.StringStreamFactory", false, false, pktsPerReport, paramsPerPkt, timestamp));
        //results.add(new Results("fr.cnes.maljoram.malencoding.JORAMElementStreamFactory", false, false, pktsPerReport, paramsPerPkt, timestamp));
        results.add(new Results("esa.mo.mal.encoder.binary.variable.VariableBinaryStreamFactory", false, false, pktsPerReport, paramsPerPkt, timestamp));
        results.add(new Results("esa.mo.mal.encoder.binary.split.SplitBinaryStreamFactory", false, false, pktsPerReport, paramsPerPkt, timestamp));
        results.add(new Results("esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory", false, false, pktsPerReport, paramsPerPkt, timestamp));

        results.add(new Results("esa.mo.performance.encoder.TestXMLStreamFactory", true, false, testXMLComposite, testXMLComposite));
        //results.add(new Results("esa.mo.mal.encoder.line.LineStreamFactory", true, false, size));
        results.add(new Results("esa.mo.mal.encoder.string.StringStreamFactory", true, false, pktsPerReport, paramsPerPkt, timestamp));
        //results.add(new Results("fr.cnes.maljoram.malencoding.JORAMElementStreamFactory", true, false, pktsPerReport, paramsPerPkt, timestamp));
        results.add(new Results("esa.mo.mal.encoder.binary.variable.VariableBinaryStreamFactory", true, false, pktsPerReport, paramsPerPkt, timestamp));
        results.add(new Results("esa.mo.mal.encoder.binary.split.SplitBinaryStreamFactory", true, false, pktsPerReport, paramsPerPkt, timestamp));
        results.add(new Results("esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory", true, false, pktsPerReport, paramsPerPkt, timestamp));

        runtests(results, runCount, testDecode, dumpBinary);

        System.out.println("Times are in microseconds per encode/decode, packet size is in bytes, differences are in %.");
        System.out.println("                                      Encode(us)  Decode(us)  Encode(PPS)   Decode(PPS)    Size(bytes)   Valid");
        for (Results res : results) {
            long eTime = (long) (((double) res.encodeTime) / ((double) (runCount * 1000)));
            long dTime = (long) (((double) res.decodeTime) / ((double) (runCount * 1000)));
            System.out.println(" " + String.format("%35s", res.encoderName)
                    + "   " + String.format("%6d", eTime)
                    + "      " + String.format("%6d", dTime)
                    + "      " + String.format("%8d", (long) ((1000000.0 / ((float) eTime)) * pktsPerReport * paramsPerPkt))
                    + "      " + String.format("%8d", (long) ((1000000.0 / ((float) dTime)) * pktsPerReport * paramsPerPkt))
                    + "      " + String.format("%8d", res.packetSize)
                    + "      " + res.decodedCorrectly);
        }
    }

    protected static void runtests(List<Results> results, int count, boolean testDecode, boolean dumpBuf) throws Exception {
        System.out.println("Creating stream factories");
        for (Results result : results) {
            System.setProperty(MALElementStreamFactory.FACTORY_PROP_NAME_PREFIX + ".testProtocol", result.factoryClassName);
            result.factory = MALElementStreamFactory.newFactory("testProtocol", new java.util.Properties());
            result.encoderName = result.factory.getClass().getSimpleName();
            if (result.compress) {
                result.encoderName += " with GZip";
            }
        }

        System.out.println("Creating objects");
        org.ccsds.moims.mo.perftest.PerfTestHelper.deepInit(MALContextFactory.getElementsRegistry());

        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("ccsds"));
        domain.add(new Identifier("mission"));
        domain.add(null);
        Identifier nz = new Identifier("network");
        MALEncodingContext ctx = new MyMALContext(new DUMMYMessageHeader(
                new URI("from"),
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
        for (Results result : results) {
            if (result.compress) {
                check(result, new TestGZipStreamFactory(result.factory), count,
                        testDecode, dumpBuf, result.objectToEncode, result.blankToEncode, ctx);
            } else {
                check(result, result.factory, count, testDecode, dumpBuf,
                        result.objectToEncode, result.blankToEncode, ctx);
            }
        }

        System.out.println("Checking tests");
        Results presult = null;
        for (Results result : results) {
            if (result.compareable) {
                if (null != presult) {
                    System.out.println("Equal: "
                            + compareObjects(presult.objectToEncode, result.objectToEncode));
                }

                presult = result;
            }
        }
    }

    protected static void check(Results result, MALElementStreamFactory streamFactory,
            int count, boolean testDecode, boolean dumpBuf, Object testComposite,
            Object blankComposite, MALEncodingContext ctx) throws Exception {
        System.out.println("Testing  : " + result.encoderName);

        ByteArrayOutputStream baos = testEncoder(result, streamFactory, count, dumpBuf, testComposite, ctx);

        if (result.dump) {
            java.io.File outputFile = new java.io.File(result.encoderName + ".txt");
            java.io.FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(baos.toByteArray());
            fos.close();
        }

        if (testDecode) {
            try {
                result.decodedCorrectly = testDecoder(result, streamFactory,
                        count, baos, testComposite, blankComposite, ctx);
            } catch (Throwable ex) {
                result.decodedCorrectly = false;
                ex.printStackTrace();
                System.out.println("FAILED decoding");
            }
        }
        System.out.println("Finished : " + result.encoderName);
    }

    protected static ByteArrayOutputStream testEncoder(Results result,
            MALElementStreamFactory streamFactory, int count, boolean dumpBuf,
            Object testComposite, MALEncodingContext ctx) throws Exception {
        ByteArrayOutputStream baos = null;

        System.out.println("Starting encoding...");
        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            baos = new ByteArrayOutputStream();
            MALElementOutputStream encoder = streamFactory.createOutputStream(baos);

            encoder.writeElement(testComposite, ctx);
            encoder.flush();
            encoder.close();
        }
        long stopTime = System.nanoTime();

        result.encodeTime = stopTime - startTime;

        System.out.println("Finished encoding");
        if (null != baos) {
            result.packetSize = baos.size();

            byte[] data = baos.toByteArray();
            System.out.println("  DUMP : Size " + data.length);
            if (dumpBuf) {
                System.out.println("  DUMP : " + byteArrayToHexString(data));
            }
        }

        return baos;
    }

    protected static boolean testDecoder(Results result, MALElementStreamFactory streamFactory,
            int count, ByteArrayOutputStream encodedValue, Object testComposite,
            Object blankComposite, MALEncodingContext ctx) throws Exception {
        Object rv = null;

        System.out.println("Starting decoding...");
        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
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

    protected static boolean compareObjects(Object original, Object newVersion) {
        if (original instanceof Element) {
            return original.equals(newVersion);
        } else if (original instanceof org.ccsds.moims.mo.xml.test.Report) {
            org.ccsds.moims.mo.xml.test.Report car_o = (org.ccsds.moims.mo.xml.test.Report) original;
            org.ccsds.moims.mo.xml.test.Report car_n = (org.ccsds.moims.mo.xml.test.Report) newVersion;

            boolean theSame = true;

            theSame &= TestStructureBuilder.compareUpdateHeader(car_o.getUpdateHeader(), car_n.getUpdateHeader());
            theSame &= TestStructureBuilder.compareObjectId(car_o.getObjectId(), car_n.getObjectId());
            theSame &= TestStructureBuilder.compareAggregationValue(car_o.getValue(), car_n.getValue());

            return theSame;
        }

        return false;
    }

    public static String byteArrayToHexString(byte[] data) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xFF & data[i]);
            if (hex.length() == 1) {
                // could use a for loop, but we're only dealing with a single byte
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    protected static class Results {

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

        public Results(String factoryClassName, boolean compress, boolean dump,
                Object objectToEncode, Object blankToEncode) {
            this.factoryClassName = factoryClassName;
            this.compress = compress;
            this.dump = dump;
            this.objectToEncode = objectToEncode;
            this.blankToEncode = blankToEncode;
            this.compareable = false;
        }

        public Results(String factoryClassName, boolean compress, boolean dump,
                int pktsPerReport, int paramsPerPkt, Time timestamp) {
            this.factoryClassName = factoryClassName;
            this.compress = compress;
            this.dump = dump;
            this.objectToEncode = TestStructureBuilder.createTestMALComposite(timestamp, pktsPerReport, paramsPerPkt);
            this.blankToEncode = new Report();
            this.compareable = true;
        }
    }

    protected static class MyMALContext extends MALEncodingContext {

        public MyMALContext(MALMessageHeader header, MALOperation operation,
                int bodyElementIndex, Map endpointQosProperties, Map messageQosProperties) {
            super(header, operation, bodyElementIndex, endpointQosProperties, messageQosProperties);
        }

        @Override
        public int getBodyElementIndex() {
            return super.getBodyElementIndex();
        }

        @Override
        public Map getEndpointQosProperties() {
            return super.getEndpointQosProperties();
        }

        @Override
        public MALMessageHeader getHeader() {
            return super.getHeader();
        }

        @Override
        public Map getMessageQosProperties() {
            return super.getMessageQosProperties();
        }

        @Override
        public MALOperation getOperation() {
            return super.getOperation();
        }

        @Override
        public void setBodyElementIndex(int bodyElementIndex) {
            super.setBodyElementIndex(bodyElementIndex);
        }

        @Override
        public void setEndpointQosProperties(Map endpointQosProperties) {
            super.setEndpointQosProperties(endpointQosProperties);
        }

        @Override
        public void setHeader(MALMessageHeader header) {
            super.setHeader(header);
        }

        @Override
        public void setMessageQosProperties(Map messageQosProperties) {
            super.setMessageQosProperties(messageQosProperties);
        }

        @Override
        public void setOperation(MALOperation operation) {
            super.setOperation(operation);
        }
    }
}
