package esa.mo.mal.encoder.http.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;
import esa.mo.mal.encoder.http.HTTPXMLStreamWriter;

public class HTTPXMLStreamWriterTest {

  ByteArrayOutputStream baos;
  MALListEncoder writer;
  XMLTestHelper helper = new XMLTestHelper();

  @Before
  public void setUp() throws Exception {

    baos = new ByteArrayOutputStream();
    writer = new HTTPXMLStreamWriter(baos);

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testEncodeEmpty() {

    try {
      writer.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "</malxml:Body>";

    assertEquals(expected, baos.toString());
  }

  @Test
  public void testEncodeBlob() throws IllegalArgumentException, MALException {

    Blob b1 = new Blob("".getBytes());
    Blob b2 = new Blob("4".getBytes());
    Blob b3 = new Blob("test".getBytes());
    Blob b4 = new Blob("http://test.com".getBytes());

    writer.encodeBlob(b1);
    writer.encodeBlob(b2);
    writer.encodeBlob(b3);
    writer.encodeBlob(b4);
    writer.close();

    String result = baos.toString();

    helper.assertAgainstSchema(result);

    assertXmlSyntax(baos.toString(), "Blob", "");
    assertXmlSyntax(baos.toString(), "Blob", "34");
    assertXmlSyntax(baos.toString(), "Blob", "74657374");
    assertXmlSyntax(baos.toString(), "Blob", "687474703a2f2f746573742e636f6d");
  }

  @Test
  public void testEncodeBufferBasedBlob() throws MALException {

    Blob b1 = new Blob(new byte[] { 0 });
    Blob b2 = new Blob(new byte[] { 0, 1 });
    Blob b3 = new Blob(new byte[] { 0, 1, 2 });

    writer.encodeBlob(b1);
    writer.encodeBlob(b2);
    writer.encodeBlob(b3);
    writer.close();

    String encodedXml = baos.toString();

    helper.assertAgainstSchema(encodedXml);
  }

  @Test
  public void testEncodeBoolean() throws IllegalArgumentException, MALException {

    Boolean b1 = true, b2 = false;

    writer.encodeBoolean(b1);
    writer.encodeBoolean(b2);
    writer.close();

    String encodedXml = baos.toString();

    helper.assertAgainstSchema(encodedXml);

    assertXmlSyntax(encodedXml, "Boolean", "true");
    assertXmlSyntax(encodedXml, "Boolean", "false");
  }

  @Test
  public void testEncodeDuration() {

    Duration d1 = new Duration();
    Duration d2 = new Duration(100.5);
    Duration d3 = new Duration(0);
    Duration d4 = new Duration(-10.5);
    Duration d5 = new Duration(86300);

    try {

      writer.encodeDuration(d1);
      writer.encodeDuration(d2);
      writer.encodeDuration(d3);
      writer.encodeDuration(d4);
      writer.encodeDuration(d5);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Duration", "PT0S", 2);
    assertXmlSyntax(result, "Duration", "PT1M40.5S");
    assertXmlSyntax(result, "Duration", "PT-10.5S");
    assertXmlSyntax(result, "Duration", "PT23H58M20S");
  }

  @Test
  public void testEncodeFloat() {

    Float f1 = new Float(100.5);
    Float f2 = new Float(0);
    Float f3 = new Float(-10.5);

    try {

      writer.encodeFloat(f1);
      writer.encodeFloat(f2);
      writer.encodeFloat(f3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Float", "0.0");
    assertXmlSyntax(result, "Float", "100.5");
    assertXmlSyntax(result, "Float", "-10.5");
  }

  @Test
  public void testEncodeDouble() {

    Double f1 = new Double(100.5);
    Double f2 = new Double(0);
    Double f3 = new Double(-10.5);

    try {

      writer.encodeDouble(f1);
      writer.encodeDouble(f2);
      writer.encodeDouble(f3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Double", "0.0");
    assertXmlSyntax(result, "Double", "100.5");
    assertXmlSyntax(result, "Double", "-10.5");
  }

  @Test
  public void testEncodeIdentifier() {

    Identifier i1 = new Identifier();
    Identifier i2 = new Identifier("Identifier");
    Identifier i3 = new Identifier("SomeValue");

    try {

      writer.encodeIdentifier(i1);
      writer.encodeIdentifier(i2);
      writer.encodeIdentifier(i3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    helper.assertAgainstSchema(result);

    assertXmlSyntax(result, "Identifier", "");
    assertXmlSyntax(result, "Identifier", "Identifier");
    assertXmlSyntax(result, "Identifier", "SomeValue");
  }

  @Test
  public void testEncodeNullableIdentifier() {

    Identifier i1 = new Identifier();
    Identifier i2 = null;

    try {

      writer.encodeNullableIdentifier(i1);
      writer.encodeNullableIdentifier(i2);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Identifier", "");
    assertXmlNullableSyntax(result, "Identifier");
  }

  @Test
  public void testEncodeOctet() {

    byte i1 = 0;
    byte i2 = 42;
    byte i3 = (byte) 255;
    byte i4 = (byte) 257;

    try {

      writer.encodeOctet(i1);
      writer.encodeOctet(i2);
      writer.encodeOctet(i3);
      writer.encodeOctet(i4);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Octet", "0");
    assertXmlSyntax(result, "Octet", "42");
    assertXmlSyntax(result, "Octet", "-1");
    assertXmlSyntax(result, "Octet", "1");
  }

  @Test
  public void testEncodeNullableOctet() {

    byte i2 = 42;

    try {

      writer.encodeNullableOctet(null);
      writer.encodeNullableOctet(i2);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Octet", "42");
    assertXmlNullableSyntax(result, "Octet");
  }

  @Test
  public void testEncodeUOctet() {

    UOctet i1 = new UOctet();
    UOctet i2 = new UOctet((short) 42);
    UOctet i3 = new UOctet((short) 255);

    try {

      writer.encodeUOctet(i1);
      writer.encodeUOctet(i2);
      writer.encodeUOctet(i3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "UOctet", "0");
    assertXmlSyntax(result, "UOctet", "42");
    assertXmlSyntax(result, "UOctet", "255");
  }

  @Test
  public void testEncodeShort() {

    short i1 = 0;
    short i2 = 42;
    short i3 = 256;

    try {

      writer.encodeShort(i1);
      writer.encodeShort(i2);
      writer.encodeShort(i3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Short", "0");
    assertXmlSyntax(result, "Short", "42");
    assertXmlSyntax(result, "Short", "256");
  }

  @Test
  public void testEncodeUShort() {

    UShort i1 = new UShort();
    UShort i2 = new UShort(0);
    UShort i3 = new UShort(1);
    UShort i4 = new UShort(42);

    try {

      writer.encodeUShort(i1);
      writer.encodeUShort(i2);
      writer.encodeUShort(i3);
      writer.encodeUShort(i4);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "UShort", "0", 2);
    assertXmlSyntax(result, "UShort", "1");
    assertXmlSyntax(result, "UShort", "42");
  }

  @Test
  public void testEncodeInteger() {

    int i1 = 0;
    int i2 = 1;
    int i3 = Integer.MAX_VALUE;

    try {

      writer.encodeInteger(i1);
      writer.encodeInteger(i2);
      writer.encodeInteger(i3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Integer", "0");
    assertXmlSyntax(result, "Integer", "1");
    assertXmlSyntax(result, "Integer", "2147483647");
  }

  @Test
  public void testEncodeUInteger() {

    UInteger i1 = new UInteger();
    UInteger i2 = new UInteger(0);
    UInteger i3 = new UInteger(1);
    UInteger i4 = new UInteger((long) Integer.MAX_VALUE * 2);

    try {

      writer.encodeUInteger(i1);
      writer.encodeUInteger(i2);
      writer.encodeUInteger(i3);
      writer.encodeUInteger(i4);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "UInteger", "0", 2);
    assertXmlSyntax(result, "UInteger", "1");
    assertXmlSyntax(result, "UInteger", "4294967294");
  }

  @Test
  public void testEncodeNullableUInteger() {

    UInteger i1 = new UInteger(3);
    UInteger i2 = null;

    try {

      writer.encodeNullableUInteger(i1);
      writer.encodeNullableUInteger(i2);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "UInteger", "3");
    assertXmlNullableSyntax(result, "UInteger");
  }

  @Test
  public void testEncodeNullableString() {

    String s1 = new String("a");
    String s2 = null;

    try {

      writer.encodeNullableString(s1);
      writer.encodeNullableString(s2);
      writer.close();
    } catch (MALException me) {
      me.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "String", "a");
    assertXmlNullableSyntax(result, "String");
  }

  @Test
  public void testEncodeLong() {

    long i1 = 0;
    long i2 = 1;
    long i3 = Long.MAX_VALUE;

    try {

      writer.encodeLong(i1);
      writer.encodeLong(i2);
      writer.encodeLong(i3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "Long", "0");
    assertXmlSyntax(result, "Long", "1");
    assertXmlSyntax(result, "Long", "9223372036854775807");
  }

  @Test
  public void testEncodeULong() {

    ULong i1 = new ULong();
    ULong i2 = new ULong(new BigInteger("0"));
    ULong i3 = new ULong(new BigInteger("1"));
    ULong i4 = new ULong(new BigInteger("9223372036854775807"));

    try {

      writer.encodeULong(i1);
      writer.encodeULong(i2);
      writer.encodeULong(i3);
      writer.encodeULong(i4);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "ULong", "0", 2);
    assertXmlSyntax(result, "ULong", "1");
    assertXmlSyntax(result, "ULong", "9223372036854775807");
  }

  @Test
  public void testEncodeString() {

    String i1 = new String();
    String i2 = new String("String");
    String i3 = new String("SomeValue");

    try {

      writer.encodeString(i1);
      writer.encodeString(i2);
      writer.encodeString(i3);
      writer.close();

    } catch (MALException e) {
      e.printStackTrace();
    }

    String result = baos.toString();

    assertXmlSyntax(result, "String", "");
    assertXmlSyntax(result, "String", "String");
    assertXmlSyntax(result, "String", "SomeValue");
  }

  /**
   * Encode time. The standard Unix epoch is assumed.
   * @throws Exception
   */
  @Test
  public void testEncodeTime() throws Exception {

    Time t1 = new Time();
    Time t2 = new Time(100000);
    Time t3 = new Time(946684900500L);
    Time t4 = new Time(1480345255605L);

    writer.encodeTime(t1);
    writer.encodeTime(t2);
    writer.encodeTime(t3);
    writer.encodeTime(t4);
    writer.close();

    String result = baos.toString();

    helper.assertAgainstSchema(result);
    NodeList nodes = helper.queryXPath(result, "/Body/Time");

    assertNotNull(nodes);
    assertEquals(4, nodes.getLength());
    assertEquals("1970-01-01T00:00:00.000", nodes.item(0).getTextContent());
    assertEquals("1970-01-01T00:01:40.000", nodes.item(1).getTextContent());
    assertEquals("2000-01-01T00:01:40.500", nodes.item(2).getTextContent());
    assertEquals("2016-11-28T15:00:55.605", nodes.item(3).getTextContent());
  }

  @Test
  public void testEncodeFinetime() throws Exception {

    FineTime t1 = new FineTime();
    FineTime t2 = new FineTime(100);
    FineTime t3 = new FineTime(1480345255605000100L);

    writer.encodeFineTime(t1);
    writer.encodeFineTime(t2);
    writer.encodeFineTime(t3);
    writer.close();

    String result = baos.toString();

    helper.assertAgainstSchema(result);
    NodeList nodes = helper.queryXPath(result, "/Body/FineTime");

    assertNotNull(nodes);
    assertEquals(3, nodes.getLength());
    assertEquals("1970-01-01T00:00:00.000000000", nodes.item(0).getTextContent());
    assertEquals("1970-01-01T00:00:00.000000100", nodes.item(1).getTextContent());
    assertEquals("2016-11-28T15:00:55.605000100", nodes.item(2).getTextContent());
  }

  @Test
  public void testEncodeURI() throws Exception {

    URI i1 = new URI();
    URI i2 = new URI("http://URI");
    URI i3 = new URI("malhttp://SomeValue");

    writer.encodeURI(i1);
    writer.encodeURI(i2);
    writer.encodeURI(i3);
    writer.close();

    String result = baos.toString();

    assertNotNull(helper.queryXPath(result, "/Body/URI"));
    assertEquals(3, helper.queryXPath(result, "/Body/*").getLength());
    NodeList uriResult = helper.queryXPath(result, "/Body/*");
    assertEquals("", uriResult.item(0).getTextContent().replaceAll("\\n|\\t", ""));
    assertEquals("http://URI", uriResult.item(1).getTextContent().replaceAll("\\n|\\t", ""));
    assertEquals("malhttp://SomeValue", uriResult.item(2).getTextContent().replaceAll("\\n|\\t", ""));
  }

  @Test
  public void testEncodeObjectRef() throws Exception {

    ObjectRef objectRef1 = new ObjectRef<>();
    ObjectRef objectRef2 = new ObjectRef<>(null, null, null, null);
    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("domain_"));
    ObjectRef objectRef3 = new ObjectRef<>(domain, 123456789L, new Identifier("_key_"), new UInteger(1));

    writer.encodeObjectRef(objectRef1);
    writer.encodeObjectRef(objectRef2);
    writer.encodeObjectRef(objectRef3);
    writer.close();

    String result = baos.toString();

    helper.assertAgainstSchema(result);

    assertNotNull(helper.queryXPath(result, "/Body/URI"));
    assertEquals(3, helper.queryXPath(result, "/Body/*").getLength());
    NodeList uriResult = helper.queryXPath(result, "/Body/*");
    assertEquals("00", uriResult.item(0).getTextContent().replaceAll("\\n|\\t", ""));
    assertEquals("", uriResult.item(1).getTextContent().replaceAll("\\n|\\t", ""));
    assertEquals("domain_123456789_key_1", uriResult.item(2).getTextContent().replaceAll("\\n|\\t", ""));
  }

  @Test
  public void testEncodeNullableObjectRef() throws Exception {
    ObjectRef objectRef1 = null;
    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("domain_"));
    ObjectRef objectRef2 = new ObjectRef<>(domain, 123456789L, new Identifier("_key_"), new UInteger(1));

    writer.encodeNullableObjectRef(objectRef1);
    writer.encodeNullableObjectRef(objectRef2);
    writer.close();

    String result = baos.toString();

    helper.assertAgainstSchema(result);

    assertNotNull(helper.queryXPath(result, "/Body/URI"));
    assertEquals(2, helper.queryXPath(result, "/Body/*").getLength());
    NodeList uriResult = helper.queryXPath(result, "/Body/*");
    assertEquals("", uriResult.item(0).getTextContent().replaceAll("\\n|\\t", ""));
    assertEquals("domain_123456789_key_1", uriResult.item(1).getTextContent().replaceAll("\\n|\\t", ""));
  }

  @Test
  public void testEncodeElement() throws Exception {

    Subscription test = new Subscription();
    writer.encodeElement(test);
    writer.close();

    String xmlResult = baos.toString();

    assertNotNull(helper.queryXPath(xmlResult, "/Body/Subscription"));
    assertNotNull(helper.queryXPath(xmlResult, "/Body/Subscription/*"));

    assertEquals(4, helper.queryXPath(xmlResult, "/Body/Subscription/*").getLength());
  }

  @Test
  public void testEncodePairWithTime() throws Exception {

    Time time = new Time(1511254501L);

    Pair pair = new Pair(time, null);

    writer.encodeElement(pair);
    writer.close();

    String xmlResult = baos.toString();

    assertNotNull(helper.queryXPath(xmlResult, "/Body/Pair"));
    assertNotNull(helper.queryXPath(xmlResult, "/Body/Pair/*"));
    assertEquals(2, helper.queryXPath(xmlResult, "/Body/Pair/*").getLength());

    assertNotNull(helper.queryXPath(xmlResult, "/Body/Pair/first"));
    assertNotNull(helper.queryXPath(xmlResult, "/Body/Pair/second"));
  }

  @Test
  public void testEncodeNullableElement() throws Exception {

    IdentifierList subDomain = new IdentifierList();
    subDomain.add(new Identifier("Id1"));
    subDomain.add(new Identifier("Id2"));
    //subDomain.add(new Identifier(null));

    writer.encodeNullableElement(subDomain);
    writer.close();

    String result = baos.toString();

    NodeList identifierList = helper.queryXPath(result, "/Body/IdentifierList");
    assertNotNull(identifierList);
    assertEquals(1, identifierList.getLength());

    NodeList identifiers = helper.queryXPath(result, "/Body/IdentifierList/Identifier");
    assertNotNull(identifiers);
    assertEquals(2, identifiers.getLength());
    assertNotNull(identifiers.item(0));
    assertEquals("Identifier", identifiers.item(0).getNodeName());
    assertEquals("Id1", identifiers.item(0).getTextContent().replaceAll("\\n|\\t", ""));
    assertNotNull(identifiers.item(1));
    assertEquals("Identifier", identifiers.item(1).getNodeName());
    assertEquals("Id2", identifiers.item(1).getTextContent().replaceAll("\\n|\\t", ""));

    /*
    assertNotNull(identifiers.item(2));
    assertEquals("Identifier", identifiers.item(2).getNodeName());
    assertEquals("", identifiers.item(2).getTextContent().replaceAll("\\n|\\t", ""));
    */
  }

  @Test
  public void testEncodeEnumeration() throws Exception {

    SessionType st = SessionType.LIVE;

    writer.encodeElement(st);
    writer.close();

    String result = baos.toString();

    NodeList sessionTypes = helper.queryXPath(result, "/Body/SessionType");
    assertNotNull(sessionTypes);
    assertEquals(1, sessionTypes.getLength());
    assertNotNull(sessionTypes.item(0));
    assertEquals("SessionType", sessionTypes.item(0).getNodeName());
    assertNotNull(sessionTypes.item(0).getFirstChild());
    assertEquals("SessionType", sessionTypes.item(0).getFirstChild().getNodeName());
    assertEquals("LIVE", sessionTypes.item(0).getTextContent().replaceAll("\\n|\\t", ""));
  }

  @Test
  public void testEncodeEnumerationQosLevel() throws Exception {

    QoSLevel ql = QoSLevel.QUEUED;

    writer.encodeElement(ql);
    writer.close();

    String result = baos.toString();

    NodeList qosLevels = helper.queryXPath(result, "/Body/QoSLevel");
    assertNotNull(qosLevels);
    assertEquals(1, qosLevels.getLength());
    assertNotNull(qosLevels.item(0));
    assertEquals("QUEUED", qosLevels.item(0).getTextContent());
  }

  private void assertXmlSyntax(String xml, String structureName, String value, int expectedOccurences) {

    Pattern p = Pattern.compile("<" + structureName + ">" + value + "</" + structureName + ">");
    Matcher m = p.matcher(xml);
    int occs = 0;
    while (m.find()) {
      occs++;
    }

    assertEquals(expectedOccurences, occs);
  }

  private void assertXmlNullableSyntax(String xml, String structureName, int expectedOccurences) {

    Pattern p = Pattern.compile("<" + structureName + " xsi:nil=\"true\"/>");
    Matcher m = p.matcher(xml);
    int occs = 0;
    while (m.find()) {
      occs++;
    }

    assertEquals(expectedOccurences, occs);
  }

  private void assertXmlSyntax(String xml, String structureName, String value) {

    assertXmlSyntax(xml, structureName, value, 1);
  }

  private void assertXmlNullableSyntax(String xml, String structureName) {

    assertXmlNullableSyntax(xml, structureName, 1);
  }

}
