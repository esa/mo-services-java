/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
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
package esa.mo.mal.encoder.binary.variable;

import esa.mo.mal.encoder.binary.EncoderDecoderTest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Tests for Encoding and Decoding with FixedBinary
 */
public class VariableEncoderDecoderTest extends EncoderDecoderTest {

    public VariableEncoderDecoderTest() {
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

    @Override
    public Element encodeThenDecode(Element element, OperationField field) throws MALException {
        // Encode
        VariableBinaryStreamFactory factory = new VariableBinaryStreamFactory();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MALElementOutputStream malWriter = factory.createOutputStream(baos);
        malWriter.writeElement(element, field);
        byte[] encodedData = baos.toByteArray();

        // Decode
        ByteArrayInputStream bais = new ByteArrayInputStream(encodedData);
        MALElementInputStream malReader = factory.createInputStream(bais);
        return malReader.readElement(element.createElement(), field);
    }

}
