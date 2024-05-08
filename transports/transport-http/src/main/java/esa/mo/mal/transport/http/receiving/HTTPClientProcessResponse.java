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

import esa.mo.mal.transport.http.HTTPTransport;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.SupplementsEncoder;
import esa.mo.mal.transport.http.util.UriHelper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.TimeZone;
import javax.mail.internet.MimeUtility;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extension of HTTPClientShutDown. Additionally reads the encoded MAL message
 * from the HTTP response and forwards it to the HTTPMessageReceiver before
 * shutting down the HTTP client (again running in a separate thread).
 */
public class HTTPClientProcessResponse extends HTTPClientShutDown {

    protected final HTTPTransport transport;

    /**
     * Constructor.
     *
     * @param client the AbstractPostClient
     * @param transport The parent HTTP transport.
     */
    public HTTPClientProcessResponse(IPostClient client, HTTPTransport transport) {
        super(client);
        this.transport = transport;
    }

    @Override
    public void run() {
        try {
            processHttpResponse(client);
            client.shutDown();
        } catch (HttpApiImplException ex) {
            RLOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new RuntimeException("HTTPClientShutDown: HttpApiImplException at run()", ex);
        } catch (Throwable ex) {
            RLOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Processes the HTTP response after submitting the request
     *
     * @param client the AbstractPostClient
     * @throws HttpApiImplException in case an error occurs when trying to read
     * the response from the HTTP client
     */
    protected void processHttpResponse(IPostClient client) throws HttpApiImplException {
        int statusCode = client.getStatusCode(); // should always be "200 OK", except for INVOKE_ACK ("202 Accepted")

        Identifier uriTo = getUriTo(client);

        Identifier uriFrom = new Identifier(client.getResponseReferer());
        // should be "001" according to 3.4.2 in recommended standard:
        String versionNumber = client.getResponseHeader("X-MAL-Version-Number");
        // should be "text/xml; charset=utf-8" according to 3.4.3 in recommended standard:
        String contentType = client.getResponseHeader("Content-Type");

        MALMessageHeader malMessageHeader = createMALHeaderFromHttp(client, uriTo, uriFrom);

        byte[] responseData = client.readFullResponseBody();

        HTTPMessageReceiver receiver = new HTTPMessageReceiver(transport, malMessageHeader, statusCode);
        receiver.receive(responseData);
    }

    /**
     * Creates a MAL header from HTTP standard and custom responseHeader fields
     *
     * @param client the AbstractPostClient
     * @param uriTo the URITo field
     * @param uriFrom the URIfrom field
     * @return the GENMessageHeader corresponding to the HTTP headers
     */
    protected MALMessageHeader createMALHeaderFromHttp(IPostClient client, Identifier uriTo, Identifier uriFrom) {

        SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(HTTPTransport.TIMESTAMP_STRING_FORMAT);
        TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        Identifier from = new Identifier(uriFrom.getValue());
        Blob authenticationId = new Blob(
                HTTPTransport.hexStringToByteArray(client.getResponseHeader("X-MAL-Authentication-Id")));
        Identifier to = new Identifier(uriTo.getValue());
        Time timestamp = null;
        try {
            String timestampHeader = client.getResponseHeader("X-MAL-Timestamp");
            if (timestampHeader != null && !timestampHeader.isEmpty()) {
                timestamp = new Time(TIMESTAMP_FORMAT.parse(timestampHeader).getTime());
            }
        } catch (ParseException e) {
            RLOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
        InteractionType interactionType = InteractionType.fromString(client.getResponseHeader("X-MAL-Interaction-Type"));
        UOctet interactionStage = null;
        if (interactionType.getOrdinal() != InteractionType._SEND_INDEX) {
            interactionStage = new UOctet(
                    Short.parseShort(client.getResponseHeader("X-MAL-Interaction-Stage")));
        }
        Long transactionId = Long.parseLong(client.getResponseHeader("X-MAL-Transaction-Id"));
        UShort serviceArea = new UShort(Integer.parseInt(client.getResponseHeader("X-MAL-Service-Area")));
        UShort service = new UShort(Integer.parseInt(client.getResponseHeader("X-MAL-Service")));
        UShort operation = new UShort(Integer.parseInt(client.getResponseHeader("X-MAL-Operation")));
        UOctet serviceVersion = new UOctet(Short.parseShort(client.getResponseHeader("X-MAL-Service-Version")));
        Boolean isErrorMessage = client.getResponseHeader("X-MAL-Is-Error-Message")
                .equalsIgnoreCase("true") ? Boolean.TRUE : Boolean.FALSE;

        String supplementsString = decodeMimeText(client.getResponseHeader("X-MAL-Supplements"));
        NamedValueList supplements = new NamedValueList();
        try {
            supplements = SupplementsEncoder.decode(supplementsString);
        } catch (IOException e) {
            RLOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return new MALMessageHeader(from, authenticationId, to, timestamp, interactionType,
                interactionStage, transactionId, serviceArea, service, operation, serviceVersion, isErrorMessage, supplements);
    }

    /**
     *
     * @param input
     * @return
     */
    protected String decodeMimeText(String input) {

        try {
            return MimeUtility.decodeText(input);
        } catch (UnsupportedEncodingException e) {
            RLOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    protected Identifier getUriTo(IPostClient client) {
        String uriTo = null;
        String uriToHeader = client.getResponseHeader("X-MAL-To");

        if (uriToHeader != null && !uriToHeader.isEmpty()) {
            uriTo = uriToHeader;
        } else {
            String host = client.getResponseHeader("Host");
            String requestTarget = client.getResponseHeader("request-target");
            uriTo = host + requestTarget;
        }

        if (uriTo.startsWith("http")) {
            if (transport.useHttps()) {
                uriTo = uriTo.replaceAll("https://", "malhttp://");
            } else {
                uriTo = uriTo.replaceAll("http://", "malhttp://");
            }
        }

        RLOGGER.fine("HTTPClientProcessResponse.getUriTo uriTo=" + uriTo);
        return new Identifier(UriHelper.uriToUtf8(uriTo));
    }
}
