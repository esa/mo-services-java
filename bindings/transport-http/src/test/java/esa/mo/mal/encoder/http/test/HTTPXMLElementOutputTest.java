/**
 * 
 */
package esa.mo.mal.encoder.http.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.structures.AbstractComposite;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.BasicAbstractComposite;
import org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractField;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import esa.mo.mal.encoder.http.HTTPXMLElementInputStream;
import esa.mo.mal.encoder.http.HTTPXMLElementOutputStream;

/**
 * @author rvangijlswijk
 *
 */
public class HTTPXMLElementOutputTest {

  XMLTestHelper helper = new XMLTestHelper();
  ByteArrayOutputStream baos;
  MALElementOutputStream httpElementOutputStream;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    MALContextFactory.getElementsRegistry().registerElementsForArea(MALHelper.MAL_AREA);
    MALContextFactory.getElementsRegistry().registerElementsForArea(MALPrototypeHelper.MALPROTOTYPE_AREA);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {

    baos = new ByteArrayOutputStream();
    httpElementOutputStream = new HTTPXMLElementOutputStream(baos);

  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testSubDomain() throws Exception {

    IdentifierList subDomain = new IdentifierList();
    subDomain.add(new Identifier("Id1"));
    subDomain.add(new Identifier("Id2"));
    subDomain.add(new Identifier(null));

    httpElementOutputStream.writeElement(subDomain, null);
    httpElementOutputStream.close();

    String xmlResult = baos.toString();

    helper.assertAgainstSchema(xmlResult);

    // SubDomain
    assertNotNull(helper.queryXPath(xmlResult, "/Body/IdentifierList"));
    assertEquals(1, helper.queryXPath(xmlResult, "/Body/IdentifierList").getLength());
    NodeList identifiers = helper.queryXPath(xmlResult, "/Body/IdentifierList/Identifier");
    assertNotNull(identifiers);
    assertEquals(3, identifiers.getLength());
    assertNotNull(identifiers.item(0));
    assertEquals("Identifier", identifiers.item(0).getNodeName());
    assertEquals("Id1", identifiers.item(0).getTextContent().replaceAll("\\n|\\t", ""));
    assertNotNull(identifiers.item(1));
    assertEquals("Identifier", identifiers.item(1).getNodeName());
    assertEquals("Id2", identifiers.item(1).getTextContent().replaceAll("\\n|\\t", ""));
    assertNotNull(identifiers.item(2));
    assertEquals("Identifier", identifiers.item(2).getNodeName());
    assertEquals("", identifiers.item(2).getTextContent().replaceAll("\\n|\\t", ""));
    assertEquals(0, identifiers.item(2).getAttributes().getLength());
  }

  @Test
  public void testEmptyBody() throws Exception {

    httpElementOutputStream.writeElement(null, null);
    httpElementOutputStream.close();

    String xmlResult = baos.toString();
    helper.assertAgainstSchema(xmlResult);

    NodeList bodyNode = helper.queryXPath(xmlResult, "/Body");
    assertNotNull(bodyNode);
    NodeList nodes = helper.queryXPath(xmlResult, "/Body/*");
    assertNotNull(nodes);
    assertEquals(1, nodes.getLength());
    NodeList childNode = helper.queryXPath(xmlResult, "/Body/Element");
    assertNotNull(childNode);
  }

  @Test
  public void testEncodeComplexComposite() throws Exception {

    TestPublishUpdate encodable = new TestPublishUpdate();

    httpElementOutputStream.writeElement(encodable, null);
    httpElementOutputStream.close();

    String xmlResult = baos.toString();

    helper.assertAgainstSchema(xmlResult);
    NodeList childNodes = helper.queryXPath(xmlResult, "/Body/TestPublishUpdate/*");
    assertEquals(13, childNodes.getLength());
  }

  @Test
  public void testEncodeEnumeration() throws Exception {

    InteractionType it = InteractionType.fromOrdinal(0);

    httpElementOutputStream.writeElement(it, null);
    httpElementOutputStream.close();

    String xmlResult = baos.toString();

    helper.assertAgainstSchema(xmlResult);

    NodeList nodes = helper.queryXPath(xmlResult, "/Body/InteractionType");

    assertNotNull(nodes);
    assertEquals(1, nodes.getLength());
    Node itNode = nodes.item(0);
    assertNotNull(itNode);
    assertEquals("InteractionType", itNode.getNodeName());
    assertEquals(1, itNode.getAttributes().getLength());
    assertNotNull(itNode.getAttributes().item(0));
    Node att = itNode.getAttributes().item(0);
    assertEquals("malxml:type", att.getNodeName());
    assertEquals("281475027042405", att.getNodeValue());
  }

  @Test
  public void testEncodeAbtractComposite() throws Exception {

    String firstItem = "first";
    Integer secondItem = 2;
    AbstractComposite abstractItem = new BasicAbstractComposite("firstAbstract", 3);
    Boolean thirdItem = false;
    Integer fourthItem = 4;
    StructureWithAbstractField structure = new StructureWithAbstractField(firstItem, secondItem, abstractItem,
        thirdItem, fourthItem);

    // Encode
    httpElementOutputStream.writeElement(structure, null);
    httpElementOutputStream.close();

    String xmlResult = baos.toString();

    helper.assertAgainstSchema(xmlResult);

    NodeList nodes = helper.queryXPath(xmlResult, "/Body/StructureWithAbstractField");
    assertNotNull(nodes);
    assertEquals(1, nodes.getLength());
    assertNotNull(nodes.item(0));
    assertEquals(5, nodes.item(0).getChildNodes().getLength());

    Node abstractNode = nodes.item(0).getChildNodes().item(2);
    assertNotNull(abstractNode);
    Node firstType = abstractNode.getAttributes().item(0);
    assertEquals("malxml:type", firstType.getNodeName());
    assertEquals("28147497687843162", firstType.getNodeValue());

    // Decode
    InputStream inputStream = new ByteArrayInputStream(xmlResult.getBytes());
    HTTPXMLElementInputStream xmlInputStream = new HTTPXMLElementInputStream(inputStream);
    StructureWithAbstractField decodedStructure = (StructureWithAbstractField) xmlInputStream.readElement(null, null);

    assertNotNull(decodedStructure);
    assertEquals(firstItem, decodedStructure.getFirstItem());
    assertEquals(secondItem, decodedStructure.getSecondItem());
    assertEquals(abstractItem, decodedStructure.getAbstract_item());
    assertEquals(thirdItem, decodedStructure.getThird_item());
    assertEquals(fourthItem, decodedStructure.getFourth_item());
  }

  @Test
  public void testEncodeAbtractList() throws Exception {

    BasicAbstractComposite basicComposite = new BasicAbstractComposite("first", 2);
    StructureWithAbstractField complexComposite = new StructureWithAbstractField("first", 2, basicComposite, false, 4);
    AbstractCompositeList abstractList = new AbstractCompositeList();
    abstractList.add(basicComposite);
    abstractList.add(complexComposite);

    // Encode
    httpElementOutputStream.writeElement(abstractList, null);
    httpElementOutputStream.close();

    String xmlResult = baos.toString();

    helper.assertAgainstSchema(xmlResult);

    NodeList nodes = helper.queryXPath(xmlResult, "/Body/AbstractCompositeList");
    assertNotNull(nodes);
    assertEquals(1, nodes.getLength());

    Node listNode = nodes.item(0);
    assertNotNull(listNode);
    assertEquals("AbstractCompositeList", listNode.getNodeName());

    NodeList abstractNodes = listNode.getChildNodes();
    assertNotNull(abstractNodes);
    assertEquals(2, abstractNodes.getLength());

    Node firstNode = abstractNodes.item(0);
    assertNotNull(firstNode);
    Node firstType = firstNode.getAttributes().item(0);
    assertEquals("malxml:type", firstType.getNodeName());
    assertEquals("28147497687843162", firstType.getNodeValue());

    Node secondNode = abstractNodes.item(1);
    assertNotNull(secondNode);
    Node secondType = secondNode.getAttributes().item(0);
    assertEquals("malxml:type", secondType.getNodeName());
    assertEquals("28147497687843163", secondType.getNodeValue());

    // Decode
    InputStream inputStream = new ByteArrayInputStream(xmlResult.getBytes());
    HTTPXMLElementInputStream xmlInputStream = new HTTPXMLElementInputStream(inputStream);
    AbstractCompositeList decodedAbstractList = (AbstractCompositeList) xmlInputStream.readElement(new AbstractCompositeList(), null);

    assertNotNull(decodedAbstractList);
    assertEquals(2, decodedAbstractList.size());
    assertEquals(basicComposite, decodedAbstractList.get(0));
    assertEquals(complexComposite, decodedAbstractList.get(1));
  }

  @Test
  public void encodeSubscription() throws Exception {
    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("Test"));
    domain.add(new Identifier("Domain0"));

    IdentifierList selectedKeys = new IdentifierList();
    selectedKeys.add(new Identifier("key1"));

    SubscriptionFilterList filterList = new SubscriptionFilterList();
    AttributeList values = new AttributeList();
    values.add(new Blob("TestValue".getBytes()));
    values.add(new Union(new Boolean(true)));
    SubscriptionFilter filter = new SubscriptionFilter(new Identifier("TestFilter"), values);
    filterList.add(filter);
    Subscription subscription = new Subscription(new Identifier("Demo"), domain, selectedKeys, filterList);

    // Encode
    httpElementOutputStream.writeElement(subscription, null);
    httpElementOutputStream.close();

    String xmlResult = baos.toString();

    NodeList nodes = helper.queryXPath(xmlResult, "/Body/Subscription");
    assertNotNull(nodes);
    assertEquals(1, nodes.getLength());
    assertNotNull(nodes.item(0));

    // Decode
    InputStream inputStream = new ByteArrayInputStream(xmlResult.getBytes());
    HTTPXMLElementInputStream xmlInputStream = new HTTPXMLElementInputStream(inputStream);
    Subscription decodedSubscription = (Subscription) xmlInputStream.readElement(null, null);

    assertNotNull(decodedSubscription);
    assertEquals(subscription, decodedSubscription);
  }
}
