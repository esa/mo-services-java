/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Transport Framework
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
package esa.mo.mal.encoder.binary;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that Encode and then Decode data.
 */
public abstract class EncoderDecoderTest {

    public abstract Element encodeThenDecode(Element element, OperationField field) throws MALException;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("--------------------------------------------");
        System.out.println("Running testbed for Encoder: ");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Completed the tests!");
        System.out.println("--------------------------------------------");
    }

    /**
     * Simple test for encoding decoding a list of NullableAttributeList.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEncodeDecodeNullableAttributeList() throws Exception {
        System.out.println("Running: testEncodeDecodeNullableAttributeList()");
        NullableAttributeList list = new NullableAttributeList();
        list.add(new NullableAttribute(new Identifier("String A")));
        list.add(new NullableAttribute(new Identifier("String B")));
        list.add(new NullableAttribute(new Identifier("String C")));

        NullableAttributeList readList = (NullableAttributeList) encodeThenDecode(list, null);

        // Assertions:
        assertNotNull(readList);
        assertEquals(3, readList.size());
        assertEquals("NullableAttributeList.get(0)", list.get(0), readList.get(0));
        assertEquals("NullableAttributeList.get(1)", list.get(1), readList.get(1));
        assertEquals("NullableAttributeList.get(2)", list.get(2), readList.get(2));
    }

    /**
     * Simple test for encoding decoding a list of StringList. Note that the
     * String MAL type is mapped to the String Java type, therefore the type
     * might need to be wrapped in a Union type for correct encoding/decoding.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEncodeDecodeStringList() throws Exception {
        System.out.println("Running: testEncodeDecodeStringList()");
        StringList list = new StringList();
        list.add("String A");
        list.add("String B");
        list.add("String C");

        StringList readList = (StringList) encodeThenDecode(list, null);

        // Assertions:
        assertNotNull(readList);
        assertEquals(3, readList.size());
        assertEquals("StringList.get(0)", list.get(0), readList.get(0));
        assertEquals("StringList.get(1)", list.get(1), readList.get(1));
        assertEquals("StringList.get(2)", list.get(2), readList.get(2));
    }

    @Test
    public void testEncodeDecodeTime2010() throws Exception {
        System.out.println("Running: testEncodeDecodeTime2010()");
        Time date = new Time("2010-01-01T09:13:51.352Z");

        Time readTime = (Time) encodeThenDecode(date, null);

        // Assertions:
        assertNotNull(readTime);
        assertEquals("Time", date.getValue(), readTime.getValue());
    }

    @Test
    public void testEncodeDecodeTime2099() throws Exception {
        System.out.println("Running: testEncodeDecodeTime2099()");
        Time date = new Time("2099-12-31T09:13:51.352Z");

        Time readTime = (Time) encodeThenDecode(date, null);

        // Assertions:
        assertNotNull(readTime);
        assertEquals("Time", date.getValue(), readTime.getValue());
    }

    @Test
    public void testEncodeDecodeFineTime2010() throws Exception {
        System.out.println("Running: testEncodeDecodeFineTime2010()");
        FineTime date = new FineTime("2010-01-01T09:13:51.352Z");

        FineTime readTime = (FineTime) encodeThenDecode(date, null);

        // Assertions:
        assertNotNull(readTime);
        assertEquals("FineTime", date.getValue(), readTime.getValue());
    }

    @Test
    public void testEncodeDecodeFineTime2099() throws Exception {
        System.out.println("Running: testEncodeDecodeFineTime2099()");
        FineTime date = new FineTime("2099-12-31T09:13:51.352Z");

        FineTime readTime = (FineTime) encodeThenDecode(date, null);

        // Assertions:
        assertNotNull(readTime);
        assertEquals("FineTime", date.getValue(), readTime.getValue());
    }
}
