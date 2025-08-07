package esa.mo.mal.encoder.http.test;

import esa.mo.mal.encoder.xml.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HTTPXMLStreamReaderTest {

    XMLTestHelper helper = new XMLTestHelper();
    InputStream xsd;
    ExpectedException exception = ExpectedException.none();

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        MALContextFactory.getElementsRegistry().loadFullArea(MALHelper.MAL_AREA);
    }

    @Before
    public void setUp() throws Exception {

        File schema = new File("MALXML_Schema.xsd");
        xsd = new FileInputStream(schema);
    }

    @After
    public void tearDown() throws Exception {

        xsd.close();
    }

    @Test
    public void testDecodeBoolean() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Boolean>true</Boolean>"
                + "<Boolean>true</Boolean>"
                + "<Boolean>false</Boolean>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertTrue(reader.decodeBoolean());
        assertTrue(reader.decodeBoolean());
        assertFalse(reader.decodeBoolean());
    }

    @Test
    public void testDecodeFloat() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Float>0.0</Float>"
                + "<Float>1.0</Float>"
                + "<Float>100.5</Float>"
                + "\n</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals((Float) 0f, reader.decodeFloat());
        assertEquals((Float) 1f, reader.decodeFloat());
        assertEquals(((Float) 100.5f), reader.decodeFloat());
    }

    @Test
    public void testDecodeDouble() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Double>0.0</Double>"
                + "<Double>1.0</Double>"
                + "<Double>100.5</Double>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals((Double) 0d, reader.decodeDouble());
        assertEquals((Double) 1d, reader.decodeDouble());
        assertEquals((Double) 100.5, reader.decodeDouble());
    }

    @Test
    public void testDecodeOctet() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Octet>0</Octet>"
                + "<Octet>42</Octet>"
                + "<Octet>127</Octet>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new Byte((byte) 0), reader.decodeOctet());
        assertEquals(new Byte((byte) 42), reader.decodeOctet());
        assertEquals(new Byte((byte) 127), reader.decodeOctet());
    }

    @Test
    public void testDecodeUOctet() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<UOctet>0</UOctet>"
                + "<UOctet>1</UOctet>"
                + "<UOctet>127</UOctet>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new UOctet((short) 0), reader.decodeUOctet());
        assertEquals(new UOctet((short) 1), reader.decodeUOctet());
        assertEquals(new UOctet((short) 127), reader.decodeUOctet());
    }

    @Test
    public void testDecodeShort() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Short>0</Short>"
                + "<Short>1</Short>"
                + "<Short>127</Short>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new Short((short) 0), reader.decodeShort());
        assertEquals(new Short((short) 1), reader.decodeShort());
        assertEquals(new Short((short) 127), reader.decodeShort());
    }

    @Test
    public void testDecodeUShort() throws MALException {
        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<UShort>0</UShort>"
                + "<UShort>1</UShort>"
                + "<UShort>127</UShort>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new UShort((short) 0), reader.decodeUShort());
        assertEquals(new UShort((short) 1), reader.decodeUShort());
        assertEquals(new UShort((short) 127), reader.decodeUShort());
    }

    @Test
    public void testDecodeInteger() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Integer>0</Integer>"
                + "<Integer>1</Integer>"
                + "<Integer>-100</Integer>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new Integer(0), reader.decodeInteger());
        assertEquals(new Integer(1), reader.decodeInteger());
        assertEquals(new Integer(-100), reader.decodeInteger());
    }

    @Test
    public void testDecodeUInteger() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<UInteger>0</UInteger>"
                + "<UInteger>1</UInteger>"
                + "<UInteger>4294967295</UInteger>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new UInteger(0l), reader.decodeUInteger());
        assertEquals(new UInteger(1l), reader.decodeUInteger());
        assertEquals(new UInteger(4294967295l), reader.decodeUInteger());
    }

    @Test
    public void testDecodeLong() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Long>0</Long>"
                + "<Long>1</Long>"
                + "<Long>-100</Long>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new Long(0), reader.decodeLong());
        assertEquals(new Long(1), reader.decodeLong());
        assertEquals(new Long(-100), reader.decodeLong());
    }

    @Test
    public void testDecodeULong() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<ULong>0</ULong>"
                + "<ULong>1</ULong>"
                + "<ULong>9223372036854775807</ULong>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new ULong(new BigInteger("0")), reader.decodeULong());
        assertEquals(new ULong(new BigInteger("1")), reader.decodeULong());
        assertEquals(new ULong(new BigInteger("9223372036854775807")), reader.decodeULong());
    }

    @Test
    public void testDecodeString() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<String></String>"
                + "<String>Foo</String>"
                + "<String>Lorem ipsum dolor sit amet !@#</String>"
                + "<String>3</String>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals("", reader.decodeString());
        assertEquals("Foo", reader.decodeString());
        assertEquals("Lorem ipsum dolor sit amet !@#", reader.decodeString());
        assertEquals("3", reader.decodeString());
    }

    @Test
    public void testDecodeBlob() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Blob></Blob><Blob>34</Blob><Blob>74657374</Blob>"
                + "<Blob>687474703a2f2f7777772e666f6f2e636f6d</Blob>"
                + "<Blob>6d616c687474703a2f2f536f6d657468696e67</Blob>"
                + "<Blob>6d616c7463703a2f2f3130302e30302e30302e303a31323334</Blob>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals("", new String(reader.decodeBlob().getValue()));
        assertEquals("4", new String(reader.decodeBlob().getValue()));
        assertEquals("test", new String(reader.decodeBlob().getValue()));
        assertEquals("http://www.foo.com", new String(reader.decodeBlob().getValue()));
        assertEquals("malhttp://Something", new String(reader.decodeBlob().getValue()));
        assertEquals("maltcp://100.00.00.0:1234", new String(reader.decodeBlob().getValue()));
    }

    @Test
    public void testDecodeBufferBasedBlob() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Blob>00</Blob>"
                + "<Blob>0001</Blob>"
                + "<Blob>000102</Blob>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new Blob(new byte[]{0}), reader.decodeBlob());
    }

    @Test
    public void testDecodeDuration() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Duration>PT0S</Duration>"
                + "<Duration>PT1M40.5S</Duration>"
                + "<Duration>PT0S</Duration>"
                + "<Duration>PT-10.5S</Duration>"
                + "<Duration>PT23H58M20S</Duration>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new Duration(0.0), reader.decodeDuration());
        assertEquals(new Duration(100.5), reader.decodeDuration());
        assertEquals(new Duration(0.0), reader.decodeDuration());
        assertEquals(new Duration(-10.5), reader.decodeDuration());
    }

    @Test
    public void testDecodeNullableDuration() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Duration>PT0S</Duration>"
                + "<Duration xsi:nil=\"true\" />"
                + "<Duration xsi:nil=\"true\" />"
                + "<Duration>PT-10.5S</Duration>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertEquals(new Duration(0.0), reader.decodeNullableDuration());
        assertEquals(null, reader.decodeNullableDuration());
        assertEquals(null, reader.decodeNullableDuration());
        assertEquals(new Duration(-10.5), reader.decodeNullableDuration());
    }

    @Test
    public void testDecodeTime() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Time>1970-01-01T00:00:00</Time>"
                + "<Time>1970-01-01T00:01:40</Time>"
                + "<Time>1968-12-31T23:59:40</Time>"
                + "<Time>2000-01-01T00:01:40.500</Time>"
                + "<Time>2016-11-28T15:00:55.605</Time>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        Time time1 = reader.decodeTime();
        Time time2 = reader.decodeTime();
        Time time3 = reader.decodeTime();
        Time time4 = reader.decodeTime();
        Time time5 = reader.decodeTime();

        assertNotNull(time1);
        assertEquals(0L, time1.getValue());
        assertNotNull(time2);
        assertEquals(100000L, time2.getValue());
        assertNotNull(time3);
        assertEquals(-31536020000L, time3.getValue());
        assertNotNull(time4);
        assertEquals(946684900500L, time4.getValue());
        assertNotNull(time5);
        assertEquals(1480345255605L, time5.getValue());

    }

    @Test
    public void testDecodeFineTime() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<FineTime>1970-01-01T00:00:00</FineTime>"
                + "<FineTime>1970-01-01T00:00:00.000000100</FineTime>"
                + "<FineTime>2016-11-28T15:00:55.605000100</FineTime>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        FineTime f1 = reader.decodeFineTime();
        FineTime f2 = reader.decodeFineTime();
        FineTime f3 = reader.decodeFineTime();

        assertNotNull(f1);
        assertEquals(0, f1.getValue());
        assertNotNull(f2);
        assertEquals(100, f2.getValue());
        assertNotNull(f3);
        assertEquals(1480345255605000100L, f3.getValue());
    }

    @Test
    public void testDecodeAsciiUrl() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<URI>http%3A%2F%2Fwww.esa.int%2F</URI>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        URI uri = reader.decodeURI();

        assertEquals("http://www.esa.int/", uri.getValue());
    }

    @Test
    public void testDecodeObjectRef() throws MALException {
        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<ObjectRef>"
                + "<IdentifierList>"
                + "<Identifier>domain</Identifier>"
                + "</IdentifierList>"
                + "<Long>0</Long>"
                + "<Identifier>key</Identifier>"
                + "<UInteger>1</UInteger>"
                + "</ObjectRef>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        ObjectRef objectRef = reader.decodeObjectRef();

        assertEquals(1, objectRef.getDomain().size());
        assertEquals("domain", objectRef.getDomain().get(0).getValue());
        assertEquals(0L, objectRef.getabsoluteSFP().longValue());
        assertEquals("key", objectRef.getKey().getValue());
        assertEquals(1L, objectRef.getObjectVersion().getValue());
    }

    @Test
    public void testDecodeElement() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Subscription>"
                + "<subscriptionId><Identifier>Id1</Identifier></subscriptionId>"
                + "<domain xsi:nil=\"true\" />"
                + "<selectedKeys xsi:nil=\"true\" />"
                + "<filters xsi:nil=\"true\" />"
                + "</Subscription>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        Subscription test = new Subscription();
        reader.decodeElement(test);

        assertNotNull(test);
        assertNotNull(test.getSubscriptionId());
        assertEquals("Id1", test.getSubscriptionId().getValue());
        assertNull(test.getDomain());
        assertNull(test.getSelectedKeys());
        assertNull(test.getFilters());
    }

    @Test
    public void testDecodeNullableElement() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Subscription xsi:nil=\"true\"/>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        Subscription test = new Subscription();
        Element result = reader.decodeNullableElement(test);

        assertNull(result);

        testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Subscription>"
                + "<subscriptionId><Identifier>Id1</Identifier></subscriptionId>"
                + "<domain xsi:nil=\"true\" />"
                + "<selectedKeys xsi:nil=\"true\" />"
                + "<filters xsi:nil=\"true\" />"
                + "</Subscription>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        bais = new ByteArrayInputStream(testXml.getBytes());
        reader = new XMLStreamReader(bais);

        Subscription test2 = new Subscription();
        Subscription result2 = (Subscription) reader.decodeNullableElement(test2);
        assertNotNull(result2);
        assertNotNull(result2.getSubscriptionId());
        assertEquals("Id1", result2.getSubscriptionId().getValue());
        assertNull(result2.getDomain());
        assertNull(result2.getSelectedKeys());
        assertNull(result2.getFilters());
    }

    @Test
    public void testDecodeNullableDouble() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Double xsi:nil=\"true\"/>"
                + "<Double></Double>"
                + "<Double>1.0</Double>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertNull(reader.decodeNullableDouble());
        assertEquals(new Double(0), reader.decodeNullableDouble());
        assertEquals(new Double(1.0), reader.decodeNullableDouble());

    }

    @Test
    public void testDecodeNullableString() throws MALException {
        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<String xsi:nil=\"true\"/>"
                + "<String></String>"
                + "<String>a</String>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertNull(reader.decodeNullableString());
        assertEquals("", reader.decodeNullableString());
        assertEquals("a", reader.decodeNullableString());
    }

    @Test
    public void testDecodeNullableObjectRef() throws MALException {
        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<ObjectRef xsi:nil=\"true\" />"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        assertNull(reader.decodeNullableObjectRef());
    }

    @Test
    public void testDecodeList() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<IdentifierList>"
                + "<Identifier><Identifier>Id1</Identifier></Identifier>"
                + "<Identifier><Identifier>Id2</Identifier></Identifier>"
                + "<Identifier><Identifier></Identifier></Identifier>"
                + "</IdentifierList>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        IdentifierList test = new IdentifierList();
        IdentifierList result = (IdentifierList) reader.decodeElement(test);

        assertEquals(3, result.size());
        assertNotNull(result.get(0));
        assertEquals(new Identifier("Id1"), result.get(0));
        assertNotNull(result.get(1));
        assertEquals(new Identifier("Id2"), result.get(1));
        assertNotNull(result.get(2));
        assertEquals(new Identifier(""), result.get(2));
    }

    @Test
    public void testDecodeCompositeList() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<subDomain>"
                + "<Identifier><Identifier>Id1</Identifier></Identifier>"
                + "<Identifier><Identifier>Id2</Identifier></Identifier>"
                + "<Identifier xsi:nil=\"true\"/>\n"
                + "</subDomain>\n"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        IdentifierList test = new IdentifierList();
        IdentifierList result = (IdentifierList) reader.decodeNullableElement(test);

        assertEquals(3, result.size());
        assertNotNull(result.get(0));
        assertEquals(new Identifier("Id1"), result.get(0));
        assertNotNull(result.get(1));
        assertEquals(new Identifier("Id2"), result.get(1));
        assertNotNull(result.get(2));
        assertEquals(new Identifier(""), result.get(2));
    }

    @Test
    public void testDecodeTimeAsNull() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Time><Time><Time>1970-01-01T00:01:40.500</Time></Time></Time>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        Time t = (Time) reader.decodeElement(null);

        assertNotNull(t);
        assertEquals(100500, t.getValue());
    }

    @Test
    public void testDecodePairWithTime() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Pair malxml:type=\"281474993487900\">"
                + "<first malxml:type=\"281475027042320\">"
                + "<Time><Time>2000-01-01T00:01:40.500</Time></Time>"
                + "</first>"
                + "<second xsi:nil=\"true\"/>"
                + "</Pair>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        Pair p = new Pair();
        p = (Pair) reader.decodeElement(p);

        assertNotNull(p);
        assertNotNull(p.getFirst());
        assertNull(p.getSecond());
    }

    @Test
    public void testDecodeEnumeration() throws MALException {

        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<SessionType><SessionType>LIVE</SessionType></SessionType>"
                + "</malxml:Body>";

        helper.assertAgainstSchema(testXml);

        InputStream bais = new ByteArrayInputStream(testXml.getBytes());
        MALDecoder reader = new XMLStreamReader(bais);

        SessionType st = SessionType.fromString("SIMULATION");
        st = (SessionType) reader.decodeElement(st);

        assertEquals("LIVE", st.toString());
    }
}
