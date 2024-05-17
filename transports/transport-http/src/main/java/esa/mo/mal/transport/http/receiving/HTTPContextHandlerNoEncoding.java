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

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.PacketToString;
import esa.mo.mal.transport.gen.receivers.IncomingMessageHolder;
import esa.mo.mal.transport.http.HTTPTransport;
import esa.mo.mal.transport.http.api.IContextHandler;
import esa.mo.mal.transport.http.api.IHttpRequest;
import esa.mo.mal.transport.http.api.IHttpResponse;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The HttpHandler implementation for the MAL HTTP Transport Server. Reads the
 * encoded MAL message from the HTTP request and forwards it to the
 * HTTPTransport.
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
        try {
            GENMessage malMsg = new GENMessage(false, true, new MALMessageHeader(),
                    new HashMap(), data, transport.getStreamFactory());
            IncomingMessageHolder msgHolder = new IncomingMessageHolder(malMsg, new PacketToString(data));
            transport.receive(null, msgHolder);
        } catch (MALException ex) {
            Logger.getLogger(HTTPContextHandlerNoEncoding.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
