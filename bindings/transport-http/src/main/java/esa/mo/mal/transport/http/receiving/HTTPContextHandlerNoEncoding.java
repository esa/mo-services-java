/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.transport.http.receiving;

import esa.mo.mal.transport.gen.receivers.ByteMessageDecoderFactory;
import esa.mo.mal.transport.http.HTTPTransport;
import esa.mo.mal.transport.http.api.IContextHandler;
import esa.mo.mal.transport.http.api.IHttpRequest;
import esa.mo.mal.transport.http.api.IHttpResponse;
import esa.mo.mal.transport.http.util.HttpApiImplException;

/**
 * The HttpHandler implementation for the MAL HTTP Transport Server.
 * Reads the encoded MAL message from the HTTP request and forwards it to the HTTPTransport.
 */
public class HTTPContextHandlerNoEncoding implements IContextHandler {
  protected final HTTPTransport transport;
  protected byte[] data;

  /**
   * Constructor.
   *
   * @param transport The parent HTTP transport.
   */
  public HTTPContextHandlerNoEncoding(HTTPTransport transport) {
    this.transport = transport;
  }

  @Override
  public void processRequest(IHttpRequest request) throws HttpApiImplException {
    data = request.readFullBody();
  }

  @Override
  public void processResponse(IHttpResponse response) throws HttpApiImplException {
    response.setStatusCode(204);
    response.send();
  }

  @Override
  public void finishHandling() {
    transport.receive(null, new ByteMessageDecoderFactory.GENIncomingByteMessageDecoder(transport, data));
  }
}
