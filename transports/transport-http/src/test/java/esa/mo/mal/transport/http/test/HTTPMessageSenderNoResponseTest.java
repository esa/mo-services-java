/**
 * 
 */
package esa.mo.mal.transport.http.test;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.junit.After;
import org.junit.Before;

import esa.mo.mal.transport.http.sending.HTTPMessageSenderNoResponse;

/**
 * @author rvangijlswijk
 *
 */
public class HTTPMessageSenderNoResponseTest extends HTTPMessageSenderBaseTest {

  /**
   * @throws MALException 
   * @throws MALInteractionException 
   * @throws IllegalArgumentException 
   */
  @Override
  @Before
  public void setUp() throws Exception {

    super.setUp();
    sender = new HTTPMessageSenderNoResponse(transport, "esa.mo.mal.transport.http.connection.JdkTestClient");
    messageBuilder = createMessageBuilder();
    message = messageBuilder.build();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }
}
