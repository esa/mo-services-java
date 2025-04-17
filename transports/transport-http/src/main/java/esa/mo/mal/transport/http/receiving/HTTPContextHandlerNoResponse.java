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
import esa.mo.mal.transport.http.api.IHttpRequest;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.SupplementsEncoder;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.TimeZone;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extension of HTTPContextHandlerNoEncoding. Additionally decodes the MAL
 * message header separately from the HTTP headers.
 */
public class HTTPContextHandlerNoResponse extends HTTPContextHandlerNoEncoding {

    protected MALMessageHeader malMessageHeader;

    /**
     * Constructor.
     *
     * @param transport The parent HTTP transport.
     */
    public HTTPContextHandlerNoResponse(HTTPTransport transport) {
        super(transport);
    }

    @Override
    public void processRequest(IHttpRequest request) throws HttpApiImplException {
        String requestUrl = request.getRequestUrl();
        RLOGGER.log(Level.FINE, "HTTPContextHandlerNoResponse.processRequest requestUrl={0}", requestUrl);

        if (transport.useHttps()) {
            requestUrl = requestUrl.replaceAll("https://", "malhttp://");
        } else {
            requestUrl = requestUrl.replaceAll("http://", "malhttp://");
        }

        Identifier uriTo = new Identifier(requestUrl);
        Identifier uriFrom = new Identifier(request.getReferer());
        // should be "001" according to 3.4.2 in recommended standard.
        String versionNumber = request.getRequestHeader("X-MAL-Version-Number");
        // should be "text/xml; charset=utf-8" according to 3.4.3 in recommended standard.
        String contentType = request.getRequestHeader("Content-Type");

        malMessageHeader = createMALHeaderFromHttp(request, uriTo, uriFrom);

        data = request.readFullBody();
    }

    /**
     * Creates a MAL header from HTTP standard and custom requestHeader fields
     *
     * @param request the AbstractHttpRequest
     * @param uriTo the URITo field
     * @param uriFrom the URIfrom field
     * @return the GENMessageHeader corresponding to the HTTP headers
     */
    protected MALMessageHeader createMALHeaderFromHttp(IHttpRequest request, Identifier uriTo, Identifier uriFrom) {
        SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(HTTPTransport.TIMESTAMP_STRING_FORMAT);
        TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        Identifier from = new Identifier(uriFrom.getValue());
        Blob authenticationId = new Blob(
                HTTPTransport.hexStringToByteArray(request.getRequestHeader("X-MAL-Authentication-Id")));
        Identifier to = new Identifier(uriTo.getValue());
        Time timestamp = null;
        try {
            String xMalTimestamp = request.getRequestHeader("X-MAL-Timestamp");
            if (xMalTimestamp != null && !xMalTimestamp.isEmpty()) {
                timestamp = new Time(TIMESTAMP_FORMAT.parse(xMalTimestamp).getTime());
            }
        } catch (ParseException e) {
            RLOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
        InteractionType interactionType = InteractionType.fromString(request.getRequestHeader("X-MAL-Interaction-Type"));
        UOctet interactionStage = null;
        if (interactionType.getValue() != InteractionType.SEND_VALUE) {
            interactionStage = new UOctet(
                    Short.parseShort(decodeAscii(request.getRequestHeader("X-MAL-Interaction-Stage"))));
        }
        Long transactionId = Long.parseLong(decodeAscii(request.getRequestHeader("X-MAL-Transaction-Id")));
        UShort serviceArea = new UShort(Integer.parseInt(decodeAscii(request.getRequestHeader("X-MAL-Service-Area"))));
        UShort service = new UShort(Integer.parseInt(decodeAscii(request.getRequestHeader("X-MAL-Service"))));
        UShort operation = new UShort(Integer.parseInt(decodeAscii(request.getRequestHeader("X-MAL-Operation"))));
        UOctet serviceVersion = new UOctet(
                Short.parseShort(decodeAscii(request.getRequestHeader("X-MAL-Area-Version"))));
        Boolean isErrorMessage = request.getRequestHeader("X-MAL-Is-Error-Message")
                .equalsIgnoreCase("true") ? Boolean.TRUE : Boolean.FALSE;

        String supplementsString = decodeAscii(request.getRequestHeader("X-MAL-Supplements"));
        NamedValueList supplements = new NamedValueList();
        try {
            supplements = SupplementsEncoder.decode(supplementsString);
        } catch (IOException e) {
            RLOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        MALMessageHeader header = new MALMessageHeader(from, authenticationId, to, timestamp, interactionType,
                interactionStage, transactionId, serviceArea, service, operation, serviceVersion, isErrorMessage, supplements);

        RLOGGER.log(Level.FINEST, "Received the following header:\n" + header.toString());

        return header;
    }

    @Override
    public void finishHandling() {
        HTTPMessageReceiver receiver = new HTTPMessageReceiver(transport, malMessageHeader, 200);
        receiver.receive(data);
    }

    /**
     * Decodes an ASCII input.
     *
     * @param input The ASCII input.
     * @return The decoded text;
     */
    protected String decodeAscii(String input) {
        return input;
    }
}
