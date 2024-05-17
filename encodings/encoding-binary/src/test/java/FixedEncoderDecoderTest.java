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

import esa.mo.mal.encoder.binary.fixed.FixedBinaryStreamFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for Encoding and Decoding with FixedBinary
 */
public class FixedEncoderDecoderTest {

    public FixedEncoderDecoderTest() {
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
     * Simple test for encoding decoding a list of NullableAttributeList.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEncodeDecodeNullableAttributeList() throws Exception {
        NullableAttributeList list = new NullableAttributeList();
        list.add(new NullableAttribute(new Identifier("String A")));
        list.add(new NullableAttribute(new Identifier("String B")));
        list.add(new NullableAttribute(new Identifier("String C")));

        FixedBinaryStreamFactory factory = new FixedBinaryStreamFactory();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MALElementOutputStream malWriter = factory.createOutputStream(baos);

        malWriter.writeElement(list, null);

        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        MALElementInputStream malReader = factory.createInputStream(bais);

        NullableAttributeList readList = new NullableAttributeList();
        readList = (NullableAttributeList) malReader.readElement(readList, null);

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
        StringList list = new StringList();
        list.add("String A");
        list.add("String B");
        list.add("String C");

        FixedBinaryStreamFactory factory = new FixedBinaryStreamFactory();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MALElementOutputStream malWriter = factory.createOutputStream(baos);

        malWriter.writeElement(list, null);

        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        MALElementInputStream malReader = factory.createInputStream(bais);

        StringList readList = new StringList();
        readList = (StringList) malReader.readElement(readList, null);

        // Assertions:
        assertNotNull(readList);
        assertEquals(3, readList.size());
        assertEquals("StringList.get(0)", list.get(0), readList.get(0));
        assertEquals("StringList.get(1)", list.get(1), readList.get(1));
        assertEquals("StringList.get(2)", list.get(2), readList.get(2));
    }
}
