/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.encoder.zmtp.header;

import esa.mo.mal.encoder.zmtp.header.ZMTPHeaderStreamFactory;
import esa.mo.mal.transport.zmtp.ZMTPStringMappingDirectory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests of ZMTPHeaderDecoder
 *
 */
public class ZMTPHeaderDecoderTest {

    public ZMTPHeaderDecoderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of encodeTime method, of class BinaryTimeHandler.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testHeaderEncodeDecode() throws Exception {
        ZMTPHeaderStreamFactory factory = new ZMTPHeaderStreamFactory(new ZMTPStringMappingDirectory());
        //factory.createInputStream(is)
        /*
        BinaryTimeHandler timeHandler = new BinaryTimeHandler();
        System.out.println("BaseBinaryTimeHandler.encodeTime & BaseBinaryTimeHandler.decodeTime test");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FixedBinaryEncoder.FixedBinaryStreamHolder streamHolder
                = new FixedBinaryEncoder.FixedBinaryStreamHolder(baos, false);
        Time value = new Time((long) (Math.random() * Long.MAX_VALUE / 10000000));
        timeHandler.encodeTime(streamHolder, value);
        FixedBinaryDecoder.FixedBinaryBufferHolder inputBufferHolder;
        inputBufferHolder = new FixedBinaryDecoder.FixedBinaryBufferHolder(new ByteArrayInputStream(
                baos.toByteArray()), baos.toByteArray(), 0, 0, false);
        Time decodedValue = timeHandler.decodeTime(inputBufferHolder);
        assertEquals("Time encoded value == decoded value", value.getValue(), decodedValue.getValue());
         */
    }

}
