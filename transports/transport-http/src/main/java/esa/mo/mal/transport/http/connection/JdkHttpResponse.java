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
package esa.mo.mal.transport.http.connection;

import esa.mo.mal.transport.http.api.IHttpResponse;
import static esa.mo.mal.transport.http.api.IPostClient.EMPTY_STRING_PLACEHOLDER;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.UriHelper;
import com.sun.net.httpserver.HttpExchange;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * An implementation of the AbstractHttpResponse interface based on
 * com.sun.net.httpserver.HttpExchange.
 */
public class JdkHttpResponse implements IHttpResponse {

    protected final HttpExchange httpExchange;

    private String[] asciiHeaders = new String[]{"X-MAL-From", "X-MAL-To", "Host", "request-target"};

    protected int statusCode;
    protected byte[] data;

    /**
     * Constructor.
     *
     * @param httpExchange the HttpExchange object for initialisation of the
     * final field
     */
    public JdkHttpResponse(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setReferer(String referer) {
        setResponseHeader("X-MAL-From", referer);
    }

    @Override
    public void setResponseHeader(String headerName, String headerValue) {
        if (headerValue.equals("")) {
            headerValue = EMPTY_STRING_PLACEHOLDER;
        }

        if (Arrays.asList(asciiHeaders).contains(headerName)) {
            headerValue = UriHelper.uriToAscii(headerValue);
        }
        httpExchange.getResponseHeaders().set(headerName, headerValue);
    }

    @Override
    public void writeFullResponseBody(byte[] data) throws HttpApiImplException {
        this.data = data;
    }

    @Override
    public void send() throws HttpApiImplException {

        try {
            if (data == null || data.length <= 0) {
                httpExchange.sendResponseHeaders(statusCode, -1);
            } else {
                httpExchange.sendResponseHeaders(statusCode, data.length);
                DataOutputStream os = new DataOutputStream(httpExchange.getResponseBody());
                os.write(data);
                os.flush();
            }
        } catch (IOException ex) {
            throw new HttpApiImplException("JdkHttpResponse: IOException at send()", ex);
        }
        httpExchange.close();
    }
}
