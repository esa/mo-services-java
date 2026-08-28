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
import esa.mo.mal.transport.http.util.HttpHeaderSink;
import esa.mo.mal.transport.http.util.MALHttpHeaderEncoder;
import java.io.IOException;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The message sender for the binding mode that maps the MAL message header onto
 * HTTP headers, but does not take part in the HTTP request/response paradigm.
 */
public class HTTPMessageSenderNoResponse extends HTTPMessageSender {

    /**
     * Constructor.
     *
     * @param transport The parent HTTP transport.
     * @param abstractPostClientImpl AbstractPostClient interface implementation
     */
    public HTTPMessageSenderNoResponse(HTTPTransport transport, String abstractPostClientImpl) {
        super(transport, abstractPostClientImpl);
    }

    @Override
    public synchronized void sendEncodedMessage(OutgoingMessageHolder<byte[]> packetData) throws IOException {
        MALMessageHeader malMessageHeader = packetData.getOriginalMessage().getHeader();

        try {
            IPostClient client = connectPostClient(getRemoteUrl(malMessageHeader));

            client.setRequestReferer(malMessageHeader.getFrom().getValue());
            client.setRequestHeader("X-MAL-Version-Number", "2"); // according to 3.4.2 in recommended standard.

            if (malMessageHeader.getIsErrorMessage()) {
                RLOGGER.severe("sendEncodedMessage: This is an untreated error message!");
            }

            // set content type according to 3.4.3 in recommended standard.
            setContentTypeHeader(client);
            setRequestHeaders(malMessageHeader, client);
            client.writeFullRequestBody(packetData.getEncodedMessage());
            client.sendRequest();

            transport.runAsynchronousTask(new HTTPClientShutDown(client));
            threadSleep(10);
        } catch (HttpApiImplException ex) {
            throw new IOException("HTTPMessageSender: HttpApiImplException at sendEncodedMessageViaHttpClient()", ex);
        }
    }

    /**
     * Maps the MAL header fields from the message to HTTP custom headers of the
     * client
     *
     * @param malMessageHeader the MALMessageHeader
     * @param client the AbstractPostClient
     * @throws IOException in case an internal error occurs
     */
    @Override
    public void setRequestHeaders(MALMessageHeader malMessageHeader, IPostClient client) throws IOException {
        MALHttpHeaderEncoder.encodeRequestHeaders(malMessageHeader, HttpHeaderSink.of(client));
    }

    /**
     * If the default xml encoding mechanism is used, set the contenttype to
     * application/mal-xml.Otherwise, set the content-type to application/mal
     * and specify the encoder used in the X-MAL-ENCODING header.
     *
     * @param client The client object.
     */
    protected void setContentTypeHeader(IPostClient client) {
        MALHttpHeaderEncoder.encodeContentType(
                transport.getStreamFactory().getClass().getCanonicalName(),
                HttpHeaderSink.of(client));
    }
}
