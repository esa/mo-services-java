/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Transport - HTTP
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

import esa.mo.mal.transport.http.api.IHttpResponse;
import esa.mo.mal.transport.http.HTTPTransport;
import esa.mo.mal.transport.http.util.HttpApiImplException;

/**
 * Extension of HTTPContextHandlerNoResponse. Additionally adds support for the
 * HTTP request/response paradigm. I.e. optionally stores the HTTP response to
 * the parent transport for later processing.
 */
public class HTTPContextHandlerRequestResponse extends HTTPContextHandlerNoResponse {

    /**
     * Constructor.
     *
     * @param transport The parent HTTP transport.
     */
    public HTTPContextHandlerRequestResponse(HTTPTransport transport) {
        super(transport);
    }

    @Override
    public void processResponse(IHttpResponse response) throws HttpApiImplException {
        if (HTTPTransport.messageHasEmtpyHttpResponse(malMessageHeader)) {
            super.processResponse(response);
        } else {
            transport.storeOpenHttpResponse(malMessageHeader.getTo().getValue(),
                    malMessageHeader.getTransactionId(), response);
        }
    }
}
