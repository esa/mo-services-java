/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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
package esa.mo.mal.transport.http.util;

import esa.mo.mal.transport.http.api.IHttpResponse;
import esa.mo.mal.transport.http.api.IPostClient;

/**
 * Somewhere HTTP headers can be written to.
 *
 * The MAL header fields are mapped onto HTTP headers identically whether they
 * travel on an HTTP request or on an HTTP response, but the two are set through
 * unrelated interfaces. This hides that difference so the mapping only has to
 * be written once.
 */
public interface HttpHeaderSink {

    /**
     * Sets a header.
     *
     * @param headerName The name of the header.
     * @param headerValue The value of the header.
     */
    void setHeader(String headerName, String headerValue);

    /**
     * Sets the referer.
     *
     * @param referer The referer.
     */
    void setReferer(String referer);

    /**
     * Returns a sink writing to the request of an HTTP client.
     *
     * @param client The client to write to.
     * @return The sink.
     */
    static HttpHeaderSink of(final IPostClient client) {
        return new HttpHeaderSink() {
            @Override
            public void setHeader(final String headerName, final String headerValue) {
                client.setRequestHeader(headerName, headerValue);
            }

            @Override
            public void setReferer(final String referer) {
                client.setRequestReferer(referer);
            }
        };
    }

    /**
     * Returns a sink writing to an HTTP response.
     *
     * @param response The response to write to.
     * @return The sink.
     */
    static HttpHeaderSink of(final IHttpResponse response) {
        return new HttpHeaderSink() {
            @Override
            public void setHeader(final String headerName, final String headerValue) {
                response.setResponseHeader(headerName, headerValue);
            }

            @Override
            public void setReferer(final String referer) {
                response.setReferer(referer);
            }
        };
    }
}
