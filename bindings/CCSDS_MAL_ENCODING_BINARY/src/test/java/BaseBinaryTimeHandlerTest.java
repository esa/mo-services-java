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

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryEncoder;
import esa.mo.mal.encoder.binary.fixed.FixedBinaryDecoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of BinaryTimeHandler
 *
 * FixedBinaryStreamHolder and FixedBinaryBufferHolder are used to hold data because
 * BaseBinaryStreamHolder and BaseBinaryBufferHolder are abstract
 */
public class BaseBinaryTimeHandlerTest
{

  public BaseBinaryTimeHandlerTest()
  {
  }

  @BeforeClass
  public static void setUpClass()
  {
  }

  @AfterClass
  public static void tearDownClass()
  {
  }

  @Before
  public void setUp()
  {
  }

  @After
  public void tearDown()
  {
  }

  /**
   * Test of encodeTime method, of class BinaryTimeHandler.
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testEncodeTime() throws Exception
  {
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

  }

  /**
   * Test of encodeFineTime method, of class BinaryTimeHandler.
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testEncodeFineTime() throws Exception
  {
    BinaryTimeHandler timeHandler = new BinaryTimeHandler();
    System.out.println(
        "BaseBinaryTimeHandler.encodeFineTime & BaseBinaryTimeHandler.decodeFineTime test");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    FixedBinaryEncoder.FixedBinaryStreamHolder streamHolder
        = new FixedBinaryEncoder.FixedBinaryStreamHolder(baos, false);
    FineTime value = new FineTime((long) (Math.random() * Long.MAX_VALUE / 10));
    timeHandler.encodeFineTime(streamHolder, value);
    FixedBinaryDecoder.FixedBinaryBufferHolder inputBufferHolder;
    inputBufferHolder = new FixedBinaryDecoder.FixedBinaryBufferHolder(new ByteArrayInputStream(
        baos.toByteArray()), baos.toByteArray(), 0, 0, false);
    FineTime decodedValue = timeHandler.decodeFineTime(inputBufferHolder);
    assertEquals("FineTime encoded value == decoded value", value.getValue(),
        decodedValue.getValue());
  }

}
