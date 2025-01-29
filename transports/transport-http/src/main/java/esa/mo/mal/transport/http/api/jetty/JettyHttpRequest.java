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

import esa.mo.mal.transport.http.api.IHttpRequest;
import static esa.mo.mal.transport.http.api.IPostClient.EMPTY_STRING_PLACEHOLDER;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import java.io.DataInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

/**
 * An implementation of the AbstractHttpRequest interface based on
 * javax.servlet.http.HttpServletRequest.
 */
public class JettyHttpRequest implements IHttpRequest {

    protected final HttpServletRequest request;

    /**
     * Constructor.
     *
     * @param request the HttpServletRequest object for initialisation of the
     * final field
     */
    public JettyHttpRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getRequestUrl() {
        return request.getRequestURL().toString();
    }

    @Override
    public String getReferer() {
        return getRequestHeader("Referer");
    }

    @Override
    public String getRequestHeader(String headerName) {
        String headerValue = request.getHeader(headerName);
        if (headerValue.equals(EMPTY_STRING_PLACEHOLDER)) {
            headerValue = "";
        }
        return headerValue;
    }

    @Override
    public byte[] readFullBody() throws HttpApiImplException {
        try {
            int packetSize = request.getContentLength();
            if (packetSize < 0) {
                packetSize = 0;
            }
            DataInputStream is = new DataInputStream(request.getInputStream());
            byte[] data = new byte[packetSize];
            is.readFully(data);
            return data;
        } catch (IOException ex) {
            throw new HttpApiImplException("JettyHttpRequest: IOException at readFullBody()", ex);
        }
    }
}
