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
package esa.mo.mal.encoders;

import esa.mo.mal.encoder.EncoderDecoderTest;
import esa.mo.mal.encoder.string.StringStreamFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Element;
import org.junit.After;
import org.junit.Before;

/**
 * Tests for Encoding and Decoding with XML
 */
public class StringEncoderDecoderTest extends EncoderDecoderTest {

    public StringEncoderDecoderTest() {
        // Needs to load the full MAL Area
        MALContextFactory.getElementsRegistry().loadFullArea(MALHelper.MAL_AREA);
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
        StringStreamFactory factory = new StringStreamFactory();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MALElementOutputStream malWriter = factory.createOutputStream(baos);
        malWriter.writeElement(element, field);
        malWriter.flush();
        byte[] encodedData = baos.toByteArray();
        //Useful for debugging:
        System.out.println("Encoded data as ASCII: " + new String(encodedData, StandardCharsets.US_ASCII));
        System.out.println("Encoded data as Array:\n" + Arrays.toString(encodedData));

        // Decode
        ByteArrayInputStream bais = new ByteArrayInputStream(encodedData);
        MALElementInputStream malReader = factory.createInputStream(bais);
        return malReader.readElement(element.createElement(), field);
    }

}
