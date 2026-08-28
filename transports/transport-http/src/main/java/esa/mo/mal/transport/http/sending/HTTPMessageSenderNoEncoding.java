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
package esa.mo.mal.transport.http.sending;

import esa.mo.mal.transport.gen.sending.OutgoingMessageHolder;
import esa.mo.mal.transport.http.HTTPTransport;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.receiving.HTTPClientShutDown;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import java.io.IOException;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The message sender for the binding mode that maps nothing of the MAL message
 * onto HTTP. The whole encoded message travels in the request body.
 */
public class HTTPMessageSenderNoEncoding extends HTTPMessageSender {

    /**
     * Constructor.
     *
     * @param transport The parent HTTP transport.
     * @param abstractPostClientImpl AbstractPostClient interface implementation
     */
    public HTTPMessageSenderNoEncoding(HTTPTransport transport, String abstractPostClientImpl) {
        super(transport, abstractPostClientImpl);
    }

    @Override
    public void sendEncodedMessage(OutgoingMessageHolder<byte[]> packetData) throws IOException {
        MALMessageHeader malMessageHeader = packetData.getOriginalMessage().getHeader();

        try {
            IPostClient client = connectPostClient(getRemoteUrl(malMessageHeader));

            if (malMessageHeader.getIsErrorMessage()) {
                RLOGGER.severe("sendEncodedMessage: This is an untreated error message!");
            }

            client.writeFullRequestBody(packetData.getEncodedMessage());
            client.sendRequest();
            transport.runAsynchronousTask(new HTTPClientShutDown(client));
            threadSleep(10);
        } catch (HttpApiImplException ex) {
            throw new IOException("HTTPMessageSender: HttpApiImplException at sendEncodedMessageViaHttpClient()", ex);
        }
    }

    /**
     * This binding mode does not map the MAL header onto HTTP headers.
     *
     * @param malMessageHeader the MALMessageHeader
     * @param client the AbstractPostClient
     */
    @Override
    public void setRequestHeaders(MALMessageHeader malMessageHeader, IPostClient client) {
        // nothing is mapped onto the HTTP headers in this binding mode
    }
}
