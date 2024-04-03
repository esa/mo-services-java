package esa.mo.mal.encoder.http.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLTestHelper {

  public NodeList queryXPath(String xml, String xpath) throws Exception {

    NodeList nodes = null;

    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    InputSource is = new InputSource();
    is.setCharacterStream(new StringReader(xml));

    Document doc = db.parse(is);

    XpathEngine engine = XMLUnit.newXpathEngine();
    nodes = engine.getMatchingNodes(xpath, doc);

    return nodes;

  }

  public void assertAgainstSchema(String xml) {

    try {
      SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = schemaFactory.newSchema(new File("MALXML_Schema.xsd"));
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(new StringReader(xml)));
    } catch (SAXException e) {
      fail("Failed with SAXException:\n" + e.getMessage());
    } catch (IOException e) {
      fail("Failed with IOException:\n" + e.getMessage());
    }
  }

}
