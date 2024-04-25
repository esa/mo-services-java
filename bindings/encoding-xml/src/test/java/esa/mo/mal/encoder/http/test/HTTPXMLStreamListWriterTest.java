package esa.mo.mal.encoder.http.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import esa.mo.mal.encoder.http.HTTPXMLStreamWriter;

import org.w3c.dom.NodeList;

public class HTTPXMLStreamListWriterTest {

  XMLTestHelper helper = new XMLTestHelper();
  ByteArrayOutputStream baos;
  MALListEncoder writer;

  @Before
  public void setUp() throws Exception {

    baos = new ByteArrayOutputStream();
    writer = new HTTPXMLStreamWriter(baos);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testIdentifierList() throws Exception {

    IdentifierList list = new IdentifierList();
    list.add(new Identifier("ESA"));
    list.add(new Identifier("Test"));
    list.add(new Identifier("Domain0"));

    list.encode(writer);
    writer.close();

    String xmlResult = baos.toString();
    System.out.println(xmlResult);

    assertNotNull(helper.queryXPath(xmlResult, "//IdentifierList"));
    assertEquals(1, helper.queryXPath(xmlResult, "//IdentifierList").getLength());

    NodeList identifiers = helper.queryXPath(xmlResult, "//Identifier");
    assertNotNull(identifiers);
    assertEquals(3, identifiers.getLength());
    assertNotNull(identifiers.item(0));
    assertEquals("Identifier", identifiers.item(0).getNodeName());
    assertEquals("ESA", identifiers.item(0).getTextContent());
    assertNotNull(identifiers.item(1));
    assertEquals("Identifier", identifiers.item(1).getNodeName());
    assertEquals("Test", identifiers.item(1).getTextContent());
    assertNotNull(identifiers.item(2));
    assertEquals("Identifier", identifiers.item(2).getNodeName());
    assertEquals("Domain0", identifiers.item(2).getTextContent());
  }

  @Test
  public void testAttributeList() throws Exception {

    AttributeList list = new AttributeList();
    list.add(new Union(new Boolean(true)));
    list.add(new Identifier("Test"));
    list.add(new Union(Integer.MAX_VALUE));

    list.encode(writer);
    writer.close();

    String xmlResult = baos.toString();

    assertNotNull(helper.queryXPath(xmlResult, "//AttributeList"));
    assertEquals(1, helper.queryXPath(xmlResult, "//AttributeList").getLength());

    NodeList attributes = helper.queryXPath(xmlResult, "//AttributeList/*");
    assertNotNull(attributes);
    assertEquals(3, attributes.getLength());
    assertNotNull(attributes.item(0));
    assertEquals("Union", attributes.item(0).getNodeName());
    assertEquals("true", attributes.item(0).getTextContent());
    assertNotNull(attributes.item(1));
    assertEquals("Identifier", attributes.item(1).getNodeName());
    assertEquals("Test", attributes.item(1).getTextContent());
    assertNotNull(attributes.item(2));
    assertEquals("Union", attributes.item(2).getNodeName());
    assertEquals("2147483647", attributes.item(2).getTextContent());
  }

}
