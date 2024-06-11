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
import esa.mo.mal.transport.http.util.StatusCodeHelper;
import esa.mo.mal.transport.http.util.SupplementsEncoder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.TimeZone;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extension of HTTPMessageSenderNoResponse. Additionally adds support for the
 * HTTP request/response paradigm. I.e. potentially encodes and delivers the MAL
 * message via a HTTP response, or possibly processes the HTTP response as
 * received message for the parent transport.
 */
public class HTTPMessageSenderRequestResponse extends HTTPMessageSenderNoResponse {

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
    public void sendEncodedMessage(OutgoingMessageHolder<byte[]> packetData) throws IOException {
        if (HTTPTransport.messageIsEncodedHttpResponse(packetData.getOriginalMessage().getHeader())) {
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
    public void sendEncodedMessageViaHttpResponse(OutgoingMessageHolder<byte[]> packetData) throws IOException {
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
    protected void sendEncodedMessageViaHttpClient(OutgoingMessageHolder<byte[]> packetData) throws IOException {
        MALMessageHeader malMessageHeader = packetData.getOriginalMessage().getHeader();
        String remoteUrl = malMessageHeader.getTo().getValue();

        if (transport.useHttps()) {
            remoteUrl = remoteUrl.replaceAll("malhttp://", "https://");
        } else {
            remoteUrl = remoteUrl.replaceAll("malhttp://", "http://");
        }

        try {
            IPostClient client = createPostClient();
            client.initAndConnectClient(remoteUrl, transport.useHttps(), transport.getKeystoreFilename(),
                    transport.getKeystorePassword());

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
     * httpResponse
     *
     * @param malMessageHeader the MALMessageHeader
     * @param httpResponse the AbstractHttpResponse
     * @throws IOException in case an internal error occurs
     */
    protected void setResponseHeaders(MALMessageHeader malMessageHeader,
            IHttpResponse httpResponse) throws IOException {
        httpResponse.setResponseHeader("X-MAL-Authentication-Id",
                HTTPTransport.byteArrayToHexString(malMessageHeader.getAuthenticationId().getValue()));

        try {
            URI from = new URI(malMessageHeader.getFrom().getValue());
            URI to = new URI(malMessageHeader.getTo().getValue());
            httpResponse.setReferer(from.toASCIIString());
            httpResponse.setResponseHeader("X-MAL-Version-Number", "1"); // according to 3.4.2 in recommended standard.
            httpResponse.setResponseHeader("X-MAL-To", to.toASCIIString());
            httpResponse.setResponseHeader("Host", to.getHost());
            httpResponse.setResponseHeader("request-target", to.getPath());
        } catch (URISyntaxException use) {
            throw new IOException("HTTPMessageSender: HttpApiImplException at sendEncodedMessageViaHttpResponse()",
                    use);
        }

        Date timestampAsDate = new Date(malMessageHeader.getTimestamp().getValue());
        SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(HTTPTransport.TIMESTAMP_STRING_FORMAT);
        TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        httpResponse.setResponseHeader("X-MAL-Timestamp", TIMESTAMP_FORMAT.format(timestampAsDate));
        httpResponse.setResponseHeader("X-MAL-Interaction-Type", malMessageHeader.getInteractionType().toString());
        httpResponse.setResponseHeader("X-MAL-Interaction-Stage",
                encodeAscii(String.valueOf(malMessageHeader.getInteractionStage().getValue())));
        httpResponse.setResponseHeader("X-MAL-Transaction-Id",
                encodeAscii(String.valueOf(malMessageHeader.getTransactionId())));
        httpResponse.setResponseHeader("X-MAL-Service-Area",
                encodeAscii(String.valueOf(malMessageHeader.getServiceArea().getValue())));
        httpResponse.setResponseHeader("X-MAL-Service",
                encodeAscii(String.valueOf(malMessageHeader.getService().getValue())));
        httpResponse.setResponseHeader("X-MAL-Operation",
                encodeAscii(String.valueOf(malMessageHeader.getOperation().getValue())));
        httpResponse.setResponseHeader("X-MAL-Service-Version",
                encodeAscii(String.valueOf(malMessageHeader.getServiceVersion().getValue())));
        httpResponse.setResponseHeader("X-MAL-Is-Error-Message",
                encodeAscii(String.valueOf(malMessageHeader.getIsErrorMessage())));

        String supplements = SupplementsEncoder.encode(malMessageHeader.getSupplements());
        if (supplements != null) {
            httpResponse.setResponseHeader("X-MAL-Supplements", encodeAscii(supplements));
        }
    }

    /**
     * If the default xml encoding mechanism is used, set the contenttype to
     * application/mal-xml. Otherwise, set the content-type to application/mal
     * and specify the encoder used in the X-MAL-ENCODING header.
     *
     * @param client The client object to be set.
     */
    protected void setContentTypeHeader(IHttpResponse client) {
        String contentType = "application/mal-xml";
        String encoderInUse = transport.getStreamFactory().getClass().getCanonicalName();
        RLOGGER.log(Level.FINEST, "Using encoder {0}", encoderInUse);
        boolean isUsingDefaultEncoder = HTTPTransport.HTTP_DEFAULT_XML_ENCODER.equals(encoderInUse);

        if (!isUsingDefaultEncoder) {
            contentType = "application/mal";
            client.setResponseHeader("X-MAL-Encoding", encodeAscii(encoderInUse));
        }

        client.setResponseHeader("Content-Type", contentType);
    }
}
