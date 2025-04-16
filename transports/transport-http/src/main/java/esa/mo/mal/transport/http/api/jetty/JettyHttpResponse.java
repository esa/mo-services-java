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
package esa.mo.mal.transport.http.api.jetty;

import esa.mo.mal.transport.http.api.IHttpResponse;
import static esa.mo.mal.transport.http.api.IPostClient.EMPTY_STRING_PLACEHOLDER;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.Continuation;

/**
 * An implementation of the AbstractHttpResponse interface based on
 * javax.servlet.http.HttpServletResponse.
 */
public class JettyHttpResponse implements IHttpResponse {

    protected final HttpServletResponse response;
    protected final Continuation continuation;

    /**
     * Constructor.
     *
     * @param response the HttpServletResponse object for initialisation of the
     * final field
     * @param continuation the Continuation object for initialisation of the
     * final field
     */
    public JettyHttpResponse(HttpServletResponse response, Continuation continuation) {
        this.response = response;
        this.continuation = continuation;
    }

    @Override
    public void setStatusCode(int statusCode) {
        response.setStatus(statusCode);
    }

    @Override
    public void setReferer(String referer) {
        setResponseHeader("Referer", referer);
    }

    @Override
    public void setResponseHeader(String headerName, String headerValue) {
        if (headerValue.equals("")) {
            headerValue = EMPTY_STRING_PLACEHOLDER;
        }
        response.setHeader(headerName, headerValue);
    }

    @Override
    public void writeFullResponseBody(byte[] data) throws HttpApiImplException {
        try {
            response.setContentLength(data.length);
            DataOutputStream os = new DataOutputStream(response.getOutputStream());
            os.write(data);
            os.flush();
        } catch (IOException ex) {
            throw new HttpApiImplException("JettyHttpResponse: IOException at writeFullBody()", ex);
        }
    }

    @Override
    public void send() throws HttpApiImplException {
        try {
            response.flushBuffer();
            continuation.complete();
        } catch (IOException ex) {
            throw new HttpApiImplException("JettyHttpResponse: IOException at send()", ex);
        }
    }
}
