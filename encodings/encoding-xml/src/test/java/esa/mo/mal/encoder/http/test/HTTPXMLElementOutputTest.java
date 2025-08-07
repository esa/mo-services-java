/**
 *
 */
package esa.mo.mal.encoder.http.test;

import esa.mo.mal.encoder.xml.XMLElementInputStream;
import esa.mo.mal.encoder.xml.XMLElementOutputStream;
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
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        MALContextFactory.getElementsRegistry().loadFullArea(MALHelper.MAL_AREA);
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
        httpElementOutputStream = new XMLElementOutputStream(baos);
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

        httpElementOutputStream.writeElement(subDomain, null);
        httpElementOutputStream.close();

        String xmlResult = baos.toString();

        helper.assertAgainstSchema(xmlResult);

        // SubDomain
        assertNotNull(helper.queryXPath(xmlResult, "/Body/IdentifierList"));
        assertEquals(1, helper.queryXPath(xmlResult, "/Body/IdentifierList").getLength());
        NodeList identifiers = helper.queryXPath(xmlResult, "/Body/IdentifierList/Identifier");
        assertNotNull(identifiers);
        assertEquals(2, identifiers.getLength());
        assertNotNull(identifiers.item(0));
        assertEquals("Identifier", identifiers.item(0).getNodeName());
        assertEquals("Id1", identifiers.item(0).getTextContent().replaceAll("\\n|\\t", ""));
        assertNotNull(identifiers.item(1));
        assertEquals("Identifier", identifiers.item(1).getNodeName());
        assertEquals("Id2", identifiers.item(1).getTextContent().replaceAll("\\n|\\t", ""));
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
    public void testEncodeEnumeration() throws Exception {
        InteractionType it = InteractionType.SEND;
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
        assertEquals(String.valueOf(InteractionType.TYPE_ID.getTypeId()), att.getNodeValue());
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
        System.out.println(xmlResult);

        NodeList nodes = helper.queryXPath(xmlResult, "/Body/Subscription");
        assertNotNull(nodes);
        assertEquals(1, nodes.getLength());
        assertNotNull(nodes.item(0));

        // Decode
        InputStream inputStream = new ByteArrayInputStream(xmlResult.getBytes());
        XMLElementInputStream xmlInputStream = new XMLElementInputStream(inputStream);
        Subscription decodedSubscription = (Subscription) xmlInputStream.readElement(null, null);

        assertNotNull(decodedSubscription);
        assertEquals(subscription, decodedSubscription);
    }
}
