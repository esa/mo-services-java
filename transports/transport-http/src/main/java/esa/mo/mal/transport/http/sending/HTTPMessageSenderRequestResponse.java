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

import esa.mo.mal.transport.gen.body.ErrorBody;
import esa.mo.mal.transport.gen.sending.OutgoingMessageHolder;
import esa.mo.mal.transport.http.HTTPTransport;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import esa.mo.mal.transport.http.api.IHttpResponse;
import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.receiving.HTTPClientProcessResponse;
import esa.mo.mal.transport.http.receiving.HTTPClientShutDown;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.HttpHeaderSink;
import esa.mo.mal.transport.http.util.MALHttpHeaderEncoder;
import esa.mo.mal.transport.http.util.StatusCodeHelper;
import java.io.IOException;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * The message sender for the binding mode that takes part in the HTTP
 * request/response paradigm. A message is delivered either over an HTTP
 * response that was left open for it, or over a fresh HTTP request whose
 * response may itself carry a message back.
 */
public class HTTPMessageSenderRequestResponse extends HTTPMessageSender {

    /**
     * Constructor.
     *
     * @param transport The parent HTTP transport.
     * @param abstractPostClientImpl AbstractPostClient interface implementation
     */
    public HTTPMessageSenderRequestResponse(HTTPTransport transport, String abstractPostClientImpl) {
        super(transport, abstractPostClientImpl);
    }

    @Override
    public synchronized void sendEncodedMessage(OutgoingMessageHolder<byte[]> packetData) throws IOException {
        MALMessageHeader header = packetData.getOriginalMessage().getHeader();

        if (HTTPTransport.messageIsEncodedHttpResponse(header)) {
            sendEncodedMessageViaHttpResponse(packetData);
        } else {
            sendEncodedMessageViaHttpClient(packetData);
        }
    }

    /**
     * Sends the encoded message via an open HTTP response.
     *
     * @param packetData the MALMessage
     * @throws IOException in case the message cannot be sent to the client
     */
    private void sendEncodedMessageViaHttpResponse(OutgoingMessageHolder<byte[]> packetData) throws IOException {
        MALMessageHeader header = packetData.getOriginalMessage().getHeader();
        IHttpResponse httpResponse = transport.retrieveOpenHttpResponse(header.getFrom().getValue(),
                header.getTransactionId());
        if (httpResponse == null) {
            throw new IOException("HTTPMessageSender: httpResponse is NULL at sendEncodedMessageViaHttpResponse()");
        }

        try {
            int statusCode = StatusCodeHelper.getHttpResponseCode(header.getInteractionType(),
                    header.getInteractionStage());

            MALMessageBody body = packetData.getOriginalMessage().getBody();

            if (header.getIsErrorMessage() && !(body instanceof ErrorBody)) {
                // assume that the MAL Message only has one element, containing the MAL error code
                RLOGGER.severe("Message is an error message but body is not an error body!");
                throw new IOException("Message is an error message but body is not an error body!");
            }

            RLOGGER.log(Level.FINE, "sendEncodedMessageViaHttpResponse statusCode={0}", statusCode);
            httpResponse.setStatusCode(statusCode);
            setContentTypeHeader(httpResponse); // according to 3.4.3 in recommended standard.
            setResponseHeaders(header, httpResponse);
            byte[] data = packetData.getEncodedMessage();

            if (data.length > 0) {
                httpResponse.writeFullResponseBody(data);
            }
            httpResponse.send();
        } catch (HttpApiImplException haie) {
            throw new IOException("HTTPMessageSender: HttpApiImplException at sendEncodedMessageViaHttpResponse()",
                    haie);
        }
    }

    /**
     * Sends an encoded message via the HTTP request of a HTTP Client.
     *
     * @param packetData the MALMessage
     * @throws IOException in case the message cannot be sent to the client
     */
    private void sendEncodedMessageViaHttpClient(OutgoingMessageHolder<byte[]> packetData) throws IOException {
        MALMessageHeader malMessageHeader = packetData.getOriginalMessage().getHeader();

        try {
            IPostClient client = connectPostClient(getRemoteUrl(malMessageHeader));

            setContentTypeHeader(client); // according to 3.4.3 in recommended standard.
            setRequestHeaders(malMessageHeader, client);
            byte[] data = packetData.getEncodedMessage();
            client.writeFullRequestBody(data);
            client.sendRequest();

            if (HTTPTransport.messageHasEmtpyHttpResponse(malMessageHeader)) {
                transport.runAsynchronousTask(new HTTPClientShutDown(client));
            }
            if (HTTPTransport.messageExpectsHttpResponse(malMessageHeader)) {
                transport.runAsynchronousTask(new HTTPClientProcessResponse(client, transport));
            }
            threadSleep(10);
        } catch (HttpApiImplException haie) {
            throw new IOException("HTTPMessageSender: HttpApiImplException at sendEncodedMessageViaHttpClient()", haie);
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
     * Maps the MAL header fields from the message to HTTP custom headers of the
     * httpResponse
     *
     * @param malMessageHeader the MALMessageHeader
     * @param httpResponse the AbstractHttpResponse
     * @throws IOException in case an internal error occurs
     */
    private void setResponseHeaders(MALMessageHeader malMessageHeader,
            IHttpResponse httpResponse) throws IOException {
        MALHttpHeaderEncoder.encodeResponseHeaders(malMessageHeader, HttpHeaderSink.of(httpResponse));
    }

    /**
     * If the default xml encoding mechanism is used, set the contenttype to
     * application/mal-xml. Otherwise, set the content-type to application/mal
     * and specify the encoder used in the X-MAL-ENCODING header.
     *
     * @param client The client object to be set.
     */
    private void setContentTypeHeader(IHttpResponse client) {
        MALHttpHeaderEncoder.encodeContentType(
                transport.getStreamFactory().getClass().getCanonicalName(),
                HttpHeaderSink.of(client));
    }

    /**
     * If the default xml encoding mechanism is used, set the contenttype to
     * application/mal-xml. Otherwise, set the content-type to application/mal
     * and specify the encoder used in the X-MAL-ENCODING header.
     *
     * @param client The client object to be set.
     */
    private void setContentTypeHeader(IPostClient client) {
        MALHttpHeaderEncoder.encodeContentType(
                transport.getStreamFactory().getClass().getCanonicalName(),
                HttpHeaderSink.of(client));
    }
}
