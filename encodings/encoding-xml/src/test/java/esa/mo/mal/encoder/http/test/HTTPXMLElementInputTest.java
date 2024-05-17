/**
 * 
 */
package esa.mo.mal.encoder.http.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;

import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.junit.BeforeClass;
import org.junit.Test;

import esa.mo.mal.encoder.xml.XMLElementInputStream;

/**
 * @author rvangijlswijk
 *
 */
public class HTTPXMLElementInputTest {

  XMLTestHelper helper = new XMLTestHelper();

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    MALContextFactory.getElementsRegistry().registerElementsForArea(MALHelper.MAL_AREA);
  }

  @Test
  public void testDecodeRegisterMessage() throws MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<Subscription malxml:type=\"281475027043305\">"
          + "<Identifier malxml:type=\"281475027042310\">"
            + "<Identifier>Demo</Identifier>"
          + "</Identifier>"
          + "<IdentifierList malxml:type=\"281475010265082\">"
            + "<Identifier>Test</Identifier>"
            + "<Identifier>Domain0</Identifier>"
          + "</IdentifierList>"
          + "<IdentifierList malxml:type=\"281475010265082\">"
            + "<Identifier>SomeKey</Identifier>"
          + "</IdentifierList>"
          + "<SubscriptionFilterList malxml:type=\"281475010264086\">"
            + "<SubscriptionFilter malxml:type=\"281474993488874\">"
              + "<Identifier malxml:type=\"281475027042310\">"
                + "<Identifier>TestFilter</Identifier>"
              + "</Identifier>"
              + "<AttributeList>"
                + "<Blob malxml:type=\"281475027042305\"><Blob>5465737456616c7565</Blob></Blob>"
                + "<Union malxml:type=\"281475027042306\"><Boolean>true</Boolean></Union>"
              + "</AttributeList>"
            + "</SubscriptionFilter>"
          + "</SubscriptionFilterList>"
        + "</Subscription>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    Subscription subscription = (Subscription) eis.readElement(new Subscription(), null);

    assertNotNull(subscription);

    // Subscription Id
    assertEquals("Demo", subscription.getSubscriptionId().getValue());

    // Domain
    assertEquals(2, subscription.getDomain().size());
    assertEquals("Test", subscription.getDomain().get(0).getValue());
    assertEquals("Domain0", subscription.getDomain().get(1).getValue());

    // Subscription filter list
    assertEquals(1, subscription.getFilters().size());
    SubscriptionFilter subscriptionFilter = subscription.getFilters().get(0);
    assertEquals("TestFilter", subscriptionFilter.getName().getValue());
    assertEquals(2, subscriptionFilter.getValues().size());
    assertArrayEquals("TestValue".getBytes(), ((Blob) subscriptionFilter.getValues().get(0)).getValue());
    assertEquals(true, ((Union) subscriptionFilter.getValues().get(1)).getBooleanValue());
  }

  @Test
  public void testDecodeNotifyMessage() throws MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<Identifier><Identifier>Demo</Identifier></Identifier>"
        + "<UpdateHeaderList>"
          + "<UpdateHeader>"
            + "<source><Identifier>SomeURI</Identifier></source>"
            + "<domain xsi:nil=\"true\" />"
            + "<keyValues xsi:nil=\"true\" />"
          + "</UpdateHeader>"
        + "</UpdateHeaderList>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    Identifier id = (Identifier) eis.readElement(new Identifier(), null);
    UpdateHeaderList uhl = (UpdateHeaderList) eis.readElement(new UpdateHeaderList(), null);

    assertNotNull(id);
    assertEquals("Demo", id.getValue());

    assertNotNull(uhl);
    assertEquals(1, uhl.size());
    assertNotNull(uhl.get(0));
    UpdateHeader uh = uhl.get(0);
    assertEquals("SomeURI", uh.getSource().getValue());
    assertNull(uh.getDomain());
    assertNull(uh.getKeyValues());
  }

  @Test
  public void testDecodeEmptyBody() throws MALException {

    // decode with non-null input stream without body content
    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    Identifier el = (Identifier) eis.readElement(new Identifier(), null);

    assertNull(el);

    // decode with 0-byte input stream
    /*
    InputStream bais2 = new ByteArrayInputStream(new byte[0]);
    HTTPXMLElementInputStream eis2 = new HTTPXMLElementInputStream(bais2);

    Object el2 = eis2.readElement(null, null);
    assertNull(el2);
    */
  }

  @Test
  public void testDecodeMessageWithUnion() throws MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<UInteger><UInteger>65549</UInteger></UInteger>"
        + "<Union><String></String></Union>"
        + "<Union><String>Foobar</String></Union>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    UInteger int1 = (UInteger) eis.readElement(new UInteger(), null);
    Union el = (Union) eis.readElement(new Union(""), null);
    Union el2 = (Union) eis.readElement(new Union(""), null);

    assertNotNull(int1);
    assertEquals(65549, int1.getValue());
    assertNotNull(el);
    assertEquals("", el.getStringValue());
    assertNotNull(el2);
    assertEquals("Foobar", el2.getStringValue());

  }

  @Test
  public void testDecodeUnionMessage() throws IllegalArgumentException, MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<UInteger><UInteger>999</UInteger></UInteger>"
        + "<Union><String>No error</String></Union>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    UInteger uint1 = (UInteger) eis.readElement(null, null);
    Union un1 = (Union) eis.readElement(null, null);

    assertNotNull(uint1);
    assertEquals(999, uint1.getValue());
    assertNotNull(un1);
    assertEquals("No error", un1.getStringValue());
  }

  @Test
  public void testDecodeMultiPartMessage() throws MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<UOctet><UOctet>255</UOctet></UOctet>"
        + "<UShort><UShort>65535</UShort></UShort>"
        + "<UInteger><UInteger>4294967295</UInteger></UInteger>"
        + "<ULong><ULong>18446744073709551615</ULong></ULong>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    UOctet uoct = (UOctet) eis.readElement(new UOctet(), null);
    UShort ushort = (UShort) eis.readElement(new UShort(), null);
    UInteger uint = (UInteger) eis.readElement(new UInteger(), null);
    ULong ulong = (ULong) eis.readElement(null, null);

    assertNotNull(uoct);
    assertNotNull(ushort);
    assertNotNull(uint);
    assertNotNull(ulong);

    assertEquals(new BigInteger("18446744073709551615"), ulong.getValue());
  }

  @Test
  public void testDecodeErrorMessageWithUnion() throws IllegalArgumentException, MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<UInteger><UInteger>70001</UInteger></UInteger>"
        + "<Union><String>18446744073709551615</String></Union>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    UInteger uint1 = (UInteger) eis.readElement(new UInteger(), null);
    Union union = (Union) eis.readElement(new Union(""), null);

    assertNotNull(uint1);
    assertNotNull(union);
    assertEquals(70001L, uint1.getValue());
    assertEquals("18446744073709551615", union.getStringValue());
  }

  @Test
  public void testDecodeEnumeration() throws MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<InteractionType malxml:type=\"281475027042405\"><InteractionType>SEND</InteractionType></InteractionType>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    InteractionType it = (InteractionType) eis.readElement(null, null);

    assertNotNull(it);
    assertEquals(0, it.getOrdinal());
  }

  @Test
  public void testDecodeUnionString() throws MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<UInteger malxml:type=\"281475027042316\"><UInteger>65549</UInteger></UInteger>"
        + "<Union malxml:type=\"281475027042319\"><String>4294967295</String></Union>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    UInteger uint = (UInteger) eis.readElement(null, null);
    Union union = (Union) eis.readElement(null, null);

    assertNotNull(uint);
    assertEquals(65549, uint.getValue());
    assertNotNull(union);
    assertEquals("4294967295", union.getStringValue());
  }

  @Test
  public void testDecodeUnionBoolean() throws MALException {

    String testXml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<Union malxml:type=\"281475027042306\"><Boolean>true</Boolean></Union>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    Union union = (Union) eis.readElement(null, null);

    assertTrue(union.getBooleanValue());
  }

  @Test
  public void testDecodeQoS() throws MALException {

    String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Body xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<Qos malxml:type=\"281475027042407\"><Qos>QUEUED</Qos></Qos>"
      + "</malxml:Body>";

    helper.assertAgainstSchema(testXml);

    InputStream bais = new ByteArrayInputStream(testXml.getBytes());
    XMLElementInputStream eis = new XMLElementInputStream(bais);

    QoSLevel qos = (QoSLevel) eis.readElement(null, null);

    assertNotNull(qos);
    assertEquals(QoSLevel.QUEUED, qos);
  }

}
