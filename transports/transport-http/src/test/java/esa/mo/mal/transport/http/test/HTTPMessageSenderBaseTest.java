package esa.mo.mal.transport.http.test;

import esa.mo.mal.transport.http.HTTPTransport;
import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.connection.JdkTestClient;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import esa.mo.mal.transport.http.sending.HTTPMessageSenderNoEncoding;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.util.Map;
import javax.mail.internet.MimeUtility;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class HTTPMessageSenderBaseTest {

  protected HTTPMessageSenderNoEncoding sender;
  protected HTTPTransport transport;
  protected MALMessage message;
  protected GENMessageBuilder messageBuilder;

  @BeforeClass
  public static void setUpBeforeClass() throws MALException {

    System.setProperty("org.ccsds.moims.mo.mal.encoding.protocol.malhttp",
        "esa.mo.mal.encoder.xml.XMLStreamFactory");
    //MALElementStreamFactory.registerFactoryClass(HTTPXMLStreamFactory.class);
  }

  @Before
  public void setUp() throws Exception {
    transport = new HTTPTransport("malhttp", ':', false, null);
  }

  @Test
  public void testFrom() throws Exception {

    message = messageBuilder.from(new Identifier("malhttp://127.0.0.1")).build();
    HttpURLConnection connection = createClient(message);

    assertEquals("malhttp://127.0.0.1", message.getHeader().getFrom().getValue());
    assertNotNull(connection);
    assertEquals("malhttp%3A%2F%2F127.0.0.1", connection.getRequestProperty("X-MAL-From"));
  }

  @Test
  public void testAuthenticationHeader() throws Exception {

    message = messageBuilder.authenticationId(new Blob("http://www.esa.int".getBytes())).build();
    HttpURLConnection connection = createClient(message);

    assertNotNull(connection);
    String authenticationId = new String(
        HTTPTransport.hexStringToByteArray(connection.getRequestProperty("X-MAL-Authentication-Id")));
    assertEquals("http://www.esa.int", authenticationId);
  }

  @Test
  public void testTo() throws Exception {

    message = messageBuilder.to(new Identifier("http://www.esa.int")).build();
    HttpURLConnection connection = createClient(message);

    assertEquals("http://www.esa.int", message.getHeader().getTo().getValue());
    assertNotNull(connection);
    assertEquals("http%3A%2F%2Fwww.esa.int", connection.getRequestProperty("X-MAL-To"));
  }

  @Test
  public void testTimestampHeader() throws Exception {

    message = messageBuilder.timestamp(new Time(0)).build();

    HttpURLConnection connection = createClient(message);

    assertNotNull(connection);
    assertEquals("1970-001T00:00:00.000", connection.getRequestProperty("X-MAL-Timestamp"));

    message = messageBuilder.timestamp(new Time(1451606400000L)).build();
    HttpURLConnection connection2 = createClient(message);

    assertNotNull(connection2);
    assertEquals("2016-001T00:00:00.000", connection2.getRequestProperty("X-MAL-Timestamp"));

    message = messageBuilder.timestamp(new Time(-1565136000000L)).build();
    HttpURLConnection connection3 = createClient(message);

    assertNotNull(connection3);
    assertEquals("1920-149T00:00:00.000", connection3.getRequestProperty("X-MAL-Timestamp"));
  }

  @Test
  public void testInteractionTypeHeader() throws Exception {

    message = messageBuilder.interactionType(InteractionType.INVOKE).build();
    HttpURLConnection connection = createClient(message);

    assertNotNull(connection);
    assertEquals("INVOKE", connection.getRequestProperty("X-MAL-Interaction-Type"));

    message = messageBuilder.interactionType(InteractionType.PROGRESS).build();
    HttpURLConnection connection2 = createClient(message);

    assertNotNull(connection2);
    assertEquals("PROGRESS", connection2.getRequestProperty("X-MAL-Interaction-Type"));

    message = messageBuilder.interactionType(new InteractionType(1)).build();
    HttpURLConnection connection3 = createClient(message);

    assertNotNull(connection3);
    assertEquals("SUBMIT", connection3.getRequestProperty("X-MAL-Interaction-Type"));
  }

  @Test
  public void testInteractionStageHeader() throws Exception {

    message = messageBuilder
        .interactionType(new InteractionType(0))
        .interactionStage(new UOctet((short) 3))
        .build();
    HttpURLConnection connection = createClient(message);

    assertNotNull(connection);
    assertEquals("3", connection.getRequestProperty("X-MAL-Interaction-Stage"));

    message = messageBuilder
        .interactionType(InteractionType.INVOKE)
        .interactionStage(new UOctet((short) 3))
        .build();
    HttpURLConnection connection2 = createClient(message);

    assertNotNull(connection2);
    assertEquals("3", connection2.getRequestProperty("X-MAL-Interaction-Stage"));
  }

  @Test
  public void testTransactionId() throws Exception {

    message = messageBuilder.transactionId(0L).build();
    HttpURLConnection connection = createClient(message);

    assertNotNull(connection);
    assertEquals("0", connection.getRequestProperty("X-MAL-Transaction-Id"));

    message = messageBuilder.transactionId((long) Integer.MAX_VALUE).build();
    HttpURLConnection connection2 = createClient(message);
    assertEquals("2147483647", connection2.getRequestProperty("X-MAL-Transaction-Id"));
  }

  @Test
  public void testIsErrorMessage() throws Exception {
    message = messageBuilder.isErrorMessage(false).build();
    HttpURLConnection connection = createClient(message);

    assertNotNull(connection);
    assertEquals("False", connection.getRequestProperty("X-MAL-Is-Error-Message"));

    message = messageBuilder.isErrorMessage(true).build();
    HttpURLConnection connection2 = createClient(message);
    assertEquals("True", connection2.getRequestProperty("X-MAL-Is-Error-Message"));
  }

  @Test
  public void testSupplementsHeader() throws Exception {

    HttpURLConnection connection = createClient(message);

    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("domain1"));
    domain.add(new Identifier("domain2"));
    ObjectRef objectRef = new ObjectRef(
        domain,
        123456789L,
        new Identifier("key"),
        new UInteger(0L));

    Object[][] testValues = new Object[][] {
        { "name", null, "null" },
        { "blob", new Blob("test".getBytes()), "1_test" },
        { "boolean", Attribute.javaType2Attribute(new Boolean(true)), "2_true" },
        { "duration", new Duration(123.456), "3_123.456" },
        { "float", Attribute.javaType2Attribute(new Float(123.456)), "4_123.456" },
        { "double", Attribute.javaType2Attribute(new Double(123.456)), "5_123.456" },
        { "identifier", new Identifier("SchöneGrüße"), "6_Sch%C3%B6neGr%C3%BC%C3%9Fe" },
        { "octet", Attribute.javaType2Attribute(new Byte("127")), "7_127" },
        { "uoctet", new UOctet(new Short("255")), "8_255" },
        { "short", Attribute.javaType2Attribute(new Short("32767")), "9_32767" },
        { "ushort", new UShort(65535), "10_65535" },
        { "integer", Attribute.javaType2Attribute(Integer.MAX_VALUE), "11_2147483647" },
        { "uinteger", new UInteger(4294967295L), "12_4294967295" },
        { "long", Attribute.javaType2Attribute(4294967296L), "13_4294967296" },
        { "ulong", new ULong(new BigInteger("4294967296")), "14_4294967296" },
        { "string", Attribute.javaType2Attribute(new String("SchöneGrüße")), "15_Sch%C3%B6neGr%C3%BC%C3%9Fe" },
        { "time", new Time(1234567890), "16_1234567890" },
        { "finetime", new FineTime(1234567890), "17_1234567890" },
        { "uri", new URI("uri/to"), "18_uri%2Fto" },
        { "objectref", objectRef, "19_domain1%2Cdomain2%3A123456789%3Akey%3A0" },
    };

    for (int valueIndex = 0; valueIndex < testValues.length; valueIndex++) {
      Object[] testValue = testValues[valueIndex];
      String name = (String) testValue[0];
      Attribute value = (Attribute) testValue[1];
      String encodedValue = (String) testValue[2];

      message = messageBuilder
          .supplements(createSupplement(name, value))
          .build();

      connection = createClient(message);

      String expected = String.format("%s=%s", name, encodedValue);
      assertEquals(expected, decodeMimeText(connection.getRequestProperty("X-MAL-Supplements")));
    }
  }

  protected NamedValueList createSupplement(String name, Attribute value) {
    return createSupplement(new Identifier(name), value);
  }

  protected NamedValueList createSupplement(Identifier name, Attribute value) {
    NamedValueList supplements = new NamedValueList();
    supplements.add(new NamedValue(name, value));
    return supplements;
  }

  protected GENMessageBuilder createMessageBuilder()
      throws IllegalArgumentException, MALException, MALInteractionException {

    Object[] body = new Object[0];
    Map qosProperties = null;

    return new GENMessageBuilder()
        .from(new Identifier("http://127.0.0.1"))
        .authenticationId(new Blob("http://www.esa.int".getBytes()))
        .to(new Identifier("http://127.0.0.1"))
        .timestamp(new Time(0))
        .interactionType(InteractionType.SEND)
        .interactionStage(new UOctet((short) 0))
        .transactionId(new Long(0))
        .serviceArea(new UShort(0))
        .service(new UShort(0))
        .operation(new UShort(0))
        .serviceVersion(new UOctet((short) 0))
        .isErrorMessage(false)
        .supplements(new NamedValueList())
        .qosProperties(qosProperties)
        .body(body);
  }

  protected HttpURLConnection createClient(MALMessage msg) throws Exception {

    IPostClient client = sender.createPostClient();

    client.initAndConnectClient("http://127.0.0.1", transport.useHttps(), transport.getKeystoreFilename(),
        transport.getKeystorePassword());

    sender.setRequestHeaders(msg.getHeader(), client);
    return ((JdkTestClient) client).getConnection();
  }

  /**
   * 
   * @param input
   * @return
   */
  protected String decodeMimeText(String input) {

    try {
      return MimeUtility.decodeText(input);
    } catch (UnsupportedEncodingException e) {
      RLOGGER.severe(e.getMessage());
      e.printStackTrace();
    }
    return "";
  }

}
