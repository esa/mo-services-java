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

import esa.mo.mal.transport.gen.sending.OutgoingMessageHolder;
import esa.mo.mal.transport.http.HTTPTransport;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.receiving.HTTPClientShutDown;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.SupplementsEncoder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.TimeZone;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extension of HTTPMessageSenderNoEncoding. Additionally encodes the MAL
 * message header separately over the HTTP headers.
 */
public class HTTPMessageSenderNoResponse extends HTTPMessageSenderNoEncoding {

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
    public void sendEncodedMessage(OutgoingMessageHolder<byte[]> packetData) throws IOException {
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
        client.setRequestHeader("X-MAL-Authentication-Id",
                HTTPTransport.byteArrayToHexString(malMessageHeader.getAuthenticationId().getValue()));

        try {
            URI uriFrom = new URI(malMessageHeader.getFrom().getValue());
            URI uriTo = new URI(malMessageHeader.getTo().getValue());
            client.setRequestReferer(uriFrom.toASCIIString());
            client.setRequestHeader("X-MAL-Version-Number", "2"); // according to 3.4.2 in recommended standard.
            client.setRequestHeader("X-MAL-To", uriTo.toASCIIString());
            client.setRequestHeader("Host", uriTo.getHost());
            client.setRequestHeader("request-target", uriTo.getPath());
        } catch (URISyntaxException e) {
            RLOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        Date timestampAsDate = new Date(malMessageHeader.getTimestamp().getValue());
        SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(HTTPTransport.TIMESTAMP_STRING_FORMAT);
        TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        client.setRequestHeader("X-MAL-Timestamp", TIMESTAMP_FORMAT.format(timestampAsDate));
        client.setRequestHeader("X-MAL-Interaction-Type", malMessageHeader.getInteractionType().toString());

        if (malMessageHeader.getInteractionStage() != null) {
            client.setRequestHeader("X-MAL-Interaction-Stage",
                    encodeAscii(String.valueOf(malMessageHeader.getInteractionStage().getValue())));
        }

        client.setRequestHeader("X-MAL-Transaction-Id",
                encodeAscii(String.valueOf(malMessageHeader.getTransactionId())));
        client.setRequestHeader("X-MAL-Service-Area",
                encodeAscii(String.valueOf(malMessageHeader.getServiceArea().getValue())));
        client.setRequestHeader("X-MAL-Service", encodeAscii(String.valueOf(malMessageHeader.getService().getValue())));
        client.setRequestHeader("X-MAL-Operation",
                encodeAscii(String.valueOf(malMessageHeader.getOperation().getValue())));
        client.setRequestHeader("X-MAL-Area-Version",
                encodeAscii(String.valueOf(malMessageHeader.getServiceVersion().getValue())));
        client.setRequestHeader("X-MAL-Is-Error-Message", malMessageHeader.getIsErrorMessage() ? "True" : "False");

        String supplements = SupplementsEncoder.encode(malMessageHeader.getSupplements());
        if (supplements != null) {
            client.setRequestHeader("X-MAL-Supplements", encodeAscii(supplements));
        }
    }

    /**
     * If the default xml encoding mechanism is used, set the contenttype to
     * application/mal-xml.Otherwise, set the content-type to application/mal
     * and specify the encoder used in the X-MAL-ENCODING header.
     *
     * @param client The client object.
     */
    protected void setContentTypeHeader(IPostClient client) {
        String contentType = "application/mal-xml";
        String encoderInUse = transport.getStreamFactory().getClass().getCanonicalName();
        RLOGGER.log(Level.FINEST, "Using encoder {0}", encoderInUse);
        boolean isUsingDefaultEncoder = HTTPTransport.HTTP_DEFAULT_XML_ENCODER.equals(encoderInUse);

        if (!isUsingDefaultEncoder) {
            contentType = "application/mal";
            client.setRequestHeader("X-MAL-Encoding", encodeAscii(encoderInUse));
        }
        client.setRequestHeader("Content-Type", contentType);
    }

    protected String encodeAscii(String input) {
        return input;
    }
}
