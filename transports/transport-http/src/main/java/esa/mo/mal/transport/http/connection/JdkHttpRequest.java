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

import esa.mo.mal.transport.http.HTTPTransport;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import esa.mo.mal.transport.http.api.IHttpRequest;
import static esa.mo.mal.transport.http.api.IPostClient.EMPTY_STRING_PLACEHOLDER;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.UriHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsExchange;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * An implementation of the AbstractHttpRequest interface based on
 * com.sun.net.httpserver.HttpExchange.
 */
public class JdkHttpRequest implements IHttpRequest {

    protected final HttpExchange httpExchange;

    private String[] asciiHeaders = new String[]{"X-MAL-From", "X-MAL-To", "Host", "request-target"};

    /**
     * Constructor.
     *
     * @param httpExchange the HttpExchange object for initialisation of the
     * final field
     */
    public JdkHttpRequest(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public String getRequestUrl() {
        String host = getRequestHeader("Host");
        String path = httpExchange.getRequestURI().getPath();

        StringBuilder sb = new StringBuilder();
        if (!host.startsWith("http")) {
            if (httpExchange instanceof HttpsExchange) {
                sb.append("https://");
            } else {
                sb.append("http://");
            }
        }
        sb.append(host);
        sb.append(path);
        return sb.toString();
    }

    @Override
    public String getReferer() {
        return getRequestHeader("X-MAL-From");
    }

    @SuppressWarnings("restriction")
    @Override
    public String getRequestHeader(String headerName) {
        String headerValue = httpExchange.getRequestHeaders().getFirst(headerName);
        if (headerValue == null || headerValue.equals(EMPTY_STRING_PLACEHOLDER)) {
            HTTPTransport.RLOGGER.log(Level.WARNING, "Header not found: {0}", headerName);
        }
        if (headerValue.equals(EMPTY_STRING_PLACEHOLDER)) {
            headerValue = "";
        }

        if (Arrays.asList(asciiHeaders).contains(headerName)) {
            headerValue = UriHelper.uriToUtf8(headerValue);
        }
        return headerValue;
    }

    @Override
    public byte[] readFullBody() throws HttpApiImplException {
        try {
            int packetSize = 0;
            String contentLength = httpExchange.getRequestHeaders().getFirst("Content-Length");
            if (contentLength != null) {
                packetSize = Integer.parseInt(contentLength);
            }
            DataInputStream is = new DataInputStream(httpExchange.getRequestBody());
            byte[] data = new byte[packetSize];
            is.readFully(data);
            return data;
        } catch (IOException ex) {
            RLOGGER.severe(ex.getMessage());
            throw new HttpApiImplException("JdkHttpRequest: IOException at readFullBody()", ex);
        }
    }
}
