package esa.mo.mal.transport.http.receiving;

import esa.mo.mal.transport.http.HTTPTransport;
import esa.mo.mal.transport.http.api.IHttpRequest;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HTTPMessageHandlerNoResponse {

  private HTTPContextHandlerNoResponse handler;
  private HTTPTransport transport;
  private TestRequest request;

  @BeforeClass
  public static void setUpBeforeClass() throws MALException {

    System.setProperty("org.ccsds.moims.mo.mal.encoding.protocol.malhttp",
        "esa.mo.mal.encoder.xml.XMLStreamFactory");
    //MALElementStreamFactory.registerFactoryClass(HTTPXMLStreamFactory.class);
  }

  @Before
  public void setUp() throws Exception {
    transport = new HTTPTransport("malhttp", ':', false, null, null);
    handler = new HTTPContextHandlerNoResponse(transport);
    request = createTestRequest();
  }

  @Test
  public void testSupplementsHeader() {

    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("domain"));
    ObjectRef objectRef = new ObjectRef(
        domain,
        123456789L,
        new Identifier("key"),
        new UInteger(0L));

    Object[][] testValues = new Object[][] {
        { "test", null, "null" },
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
        { "uri", new URI("uri/to"), "18_uri/to" },
        { "objectref", objectRef, "19_domain%3A123456789%3Akey%3A0" },
    };

    for (int valueIndex = 0; valueIndex < testValues.length; valueIndex++) {
      Object[] testValue = testValues[valueIndex];
      String name = (String) testValue[0];
      Attribute value = (Attribute) testValue[1];
      String encodedValue = (String) testValue[2];

      request.setRequestHeader("X-MAL-Supplements", String.format("%s=%s", name, encodedValue));
      MALMessageHeader header = handler.createMALHeaderFromHttp(request, new Identifier("http://127.0.0.1"), new Identifier("http://127.0.0.1"));

      assertEquals(value, header.getSupplements().get(0).getValue());
    }
  }

  private TestRequest createTestRequest() {
    TestRequest testRequest = new TestRequest();
    testRequest.setRequestHeader("X-MAL-Authentication-Id", "http://www.esa.int");
    testRequest.setRequestHeader("X-MAL-Interaction-Type", "SEND");
    testRequest.setRequestHeader("X-MAL-Transaction-Id", "0");
    testRequest.setRequestHeader("X-MAL-Service-Area", "0");
    testRequest.setRequestHeader("X-MAL-Service", "0");
    testRequest.setRequestHeader("X-MAL-Operation", "0");
    testRequest.setRequestHeader("X-MAL-Service-Version", "0");
    testRequest.setRequestHeader("X-MAL-Is-Error-Message", "false");
    testRequest.setRequestHeader("X-MAL-Supplements", "");
    return testRequest;
  }

  class TestRequest implements IHttpRequest {

    Map<String, String> headerMap = new HashMap<String, String>();

    @Override
    public String getRequestUrl() {
      return null;
    }

    @Override
    public String getReferer() {
      return null;
    }

    public void setRequestHeader(String headerName, String headerValue) {
      headerMap.put(headerName, headerValue);
    }

    @Override
    public String getRequestHeader(String headerName) {
      return headerMap.get(headerName);
    }

    @Override
    public byte[] readFullBody() throws HttpApiImplException {
      return null;
    }
  }
}
