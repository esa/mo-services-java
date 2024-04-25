package esa.mo.mal.encoder.http.test;

import java.io.ByteArrayOutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.junit.Test;
import esa.mo.mal.encoder.xml.XMLStreamWriter;

public class HTTPXMLSchemaTest {

  XMLTestHelper helper = new XMLTestHelper();
  ByteArrayOutputStream baos;
  MALListEncoder writer;

  @Test
  public void testSimpleBodySchema() throws MALException {

    baos = new ByteArrayOutputStream();
    writer = new XMLStreamWriter(baos);

    writer.encodeBoolean(true);
    writer.encodeString("Simple Body");
    writer.close();

    helper.assertAgainstSchema(baos.toString());
  }

  @Test
  public void testSubscriptionSchema() throws MALException {

    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<malxml:Message xmlns:malxml=\"http://www.ccsds.org/schema/malxml/MAL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
        + "<malxml:Body>"
          + "<Subscription>"
            + "<Identifier>"
              + "<Identifier>Demo</Identifier>"
            + "</Identifier>"
            + "<IdentifierList>"
              + "<Identifier>Test</Identifier>"
              + "<Identifier>Domain0</Identifier>"
            + "</IdentifierList>"
            + "<SubscriptionFilterList>"
              + "<SubscriptionFilter>"
                + "<Identifier>"
                  + "<Identifier>TestFilter</Identifier>"
                + "</Identifier>"
                + "<AttributeList>"
                  + "<Attribute malxml:type=\"281475027042305\"><Blob>5465737456616c7565</Blob></Attribute>"
                  + "<Attribute malxml:type=\"281475027042306\"><Boolean>true</Boolean></Attribute>"
                + "</AttributeList>"
              + "</SubscriptionFilter>"
            + "</SubscriptionFilterList>"
          + "</Subscription>"
        + "</malxml:Body>"
      + "</malxml:Message>";

    helper.assertAgainstSchema(xml);
  }

}
