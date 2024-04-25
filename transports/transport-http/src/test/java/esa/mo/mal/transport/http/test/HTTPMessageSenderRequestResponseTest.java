/**
 * 
 */
package esa.mo.mal.transport.http.test;

import org.junit.Before;

import esa.mo.mal.transport.http.sending.HTTPMessageSenderRequestResponse;

/**
 * @author rvangijlswijk
 *
 */
public class HTTPMessageSenderRequestResponseTest extends HTTPMessageSenderBaseTest {

  /* (non-Javadoc)
   * @see esa.mo.mal.transport.http.test.HTTPMessageSenderBaseTest#setUp()
   */
  @Override
  @Before
  public void setUp() throws Exception {

    super.setUp();
    sender = new HTTPMessageSenderRequestResponse(transport, "esa.mo.mal.transport.http.connection.JdkTestClient");
    messageBuilder = createMessageBuilder();
    message = messageBuilder.build();
  }

}
