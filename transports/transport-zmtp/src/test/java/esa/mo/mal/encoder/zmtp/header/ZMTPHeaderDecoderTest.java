/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
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

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;
import esa.mo.mal.transport.zmtp.ZMTPConfiguration;
import esa.mo.mal.transport.zmtp.ZMTPMessageHeader;
import esa.mo.mal.transport.zmtp.ZMTPStringMappingDirectory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import org.ccsds.moims.mo.mal.encoding.Encoder;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperTime;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
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

    @Test
    public void testHeaderEncodeDecode() throws Exception {
        ZMTPConfiguration configuration = new ZMTPConfiguration(
                false,
                true,
                false,
                false,
                false,
                true);

        Time crazyTime = new Time(5283532800000L);
        FineTime crazyFineTime = new FineTime(5283532800000000000L);
        String strTime = HelperTime.time2readableString(crazyTime);
        String strFineTime = HelperTime.time2readableString(crazyFineTime);
        System.out.print("\n  -  The Time is: " + strTime);
        System.out.print("\n  -  The FineTime is: " + strFineTime);

        NamedValueList supplementsFull = new NamedValueList();
        supplementsFull.add(new NamedValue(new Identifier("Blob suppl"), new Blob("Blob suppl".getBytes())));
        supplementsFull.add(new NamedValue(new Identifier("Boolean suppl"), new Union(true)));
        supplementsFull.add(new NamedValue(new Identifier("Duration suppl"), new Duration(Double.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("Float suppl"), new Union(Float.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("Double suppl"), new Union(Double.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("Identifier suppl"), new Identifier("Identifier suppl")));
        supplementsFull.add(new NamedValue(new Identifier("Octet suppl"), new Union(Byte.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("UOctet suppl"), new UOctet(Byte.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("Short suppl"), new Union(Short.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("UShort suppl"), new UShort(Short.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("Integer suppl"), new Union(Integer.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("UInteger suppl"), new UInteger(Integer.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("Long suppl"), new Union(Long.MAX_VALUE)));
        supplementsFull.add(new NamedValue(new Identifier("ULong suppl"), new ULong(BigInteger.TEN)));
        supplementsFull.add(new NamedValue(new Identifier("String suppl"), new Union("String suppl")));
        supplementsFull.add(new NamedValue(new Identifier("Time suppl"), crazyTime));
        supplementsFull.add(new NamedValue(new Identifier("FineTime suppl"), crazyFineTime));
        supplementsFull.add(new NamedValue(new Identifier("URI suppl"), new URI("URI suppl")));
        supplementsFull.add(new NamedValue(new Identifier("ObjectRef suppl"), new ObjectRef(new IdentifierList(),
                Subscription.SHORT_FORM, new Identifier("ObjectRef suppl"), new UInteger(1))));

        ZMTPMessageHeader header = new ZMTPMessageHeader(
                new ZMTPConfiguration(configuration, null),
                new Identifier("URI From"),
                new Blob(new byte[2]),
                new Identifier("URI To"),
                Time.now(),
                InteractionType.SUBMIT,
                new UOctet(1),
                (long) 2,
                new UShort(3),
                new UShort(4),
                new UShort(5),
                new UOctet(6),
                false,
                supplementsFull);

        // Encode the header!
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Encoder hdrEncoder = new ZMTPHeaderEncoder(baos, new ZMTPStringMappingDirectory(), new BinaryTimeHandler());
        header.encode(hdrEncoder);

        // Now decode!
        final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ZMTPHeaderDecoder decoder = new ZMTPHeaderDecoder(bais, new ZMTPStringMappingDirectory(), new BinaryTimeHandler());
        ZMTPMessageHeader decodedHeader = new ZMTPMessageHeader(configuration);
        decodedHeader = decodedHeader.decode(decoder);

        // Checks:
        assertEquals("URI From", header.getFrom(), decodedHeader.getFrom());
        assertEquals("URI To", header.getTo(), decodedHeader.getTo());
        assertEquals("Timestamp", header.getTimestamp(), decodedHeader.getTimestamp());
        assertEquals("Interaction Type", header.getInteractionType(), decodedHeader.getInteractionType());
        assertEquals("Interaction Stage", header.getInteractionStage(), decodedHeader.getInteractionStage());
        assertEquals("TransactionId", header.getTransactionId(), decodedHeader.getTransactionId());
        assertEquals("Area", header.getServiceArea(), decodedHeader.getServiceArea());
        assertEquals("Area Version", header.getServiceVersion(), decodedHeader.getServiceVersion());
        assertEquals("Service", header.getService(), decodedHeader.getService());
        assertEquals("Operation", header.getOperation(), decodedHeader.getOperation());
        assertEquals("EncodingExtendedId", header.getEncodingExtendedId(), decodedHeader.getEncodingExtendedId());
        assertEquals("EncodingId", header.getEncodingId(), decodedHeader.getEncodingId());
        assertEquals("AuthenticationId", header.getAuthenticationId(), decodedHeader.getAuthenticationId());
        assertEquals("IsErrorMessage", header.getIsErrorMessage(), decodedHeader.getIsErrorMessage());
        assertEquals("Supplements", header.getSupplements(), decodedHeader.getSupplements());
    }
}
