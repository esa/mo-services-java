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
package esa.mo.mal.transport.http.api;

import esa.mo.mal.transport.http.util.HttpApiImplException;

/**
 * This interface offers abstraction from the library-dependent used data-structures and allows technology-independent
 * implementations of the handling routines invoked for incoming requests.
 */
public interface IContextHandler {

  /**
   * Routine handling the AbstractHttpRequest object, e.g. reading header fields and body data.
   * 
   * @param request
   *            the AbstractHttpRequest to process
   * @throws HttpApiImplException
   *             in case an error occurs when processing the request
   */
  public void processRequest(IHttpRequest request) throws HttpApiImplException;

  /**
   * Handles the AbstractHttpResponse object, e.g. writing header fields and body data.
   * 
   * Important: the AbstractHttpResponse shall not be committed to the client before its send() method actually has
   * been called. Depending on the actual ContextHandler technology used a suspend/complete pattern may be needed, in
   * order to prevent the handler from writing the response itself upon completion (regardless whether send() was
   * called or not), but to delegate this task to another potential asynchronous handler.
   * 
   * @param response
   *            the AbstractHttpResponse to process
   * @throws HttpApiImplException
   *             in case an error occurs when processing the response
   */
  public void processResponse(IHttpResponse response) throws HttpApiImplException;

  /**
   * This method is called after the processing of both the HTTP request and HTTP response is terminated and allows to
   * invoke any additional required steps before finalising the ContextHandler.
   */
  public void finishHandling();
}
