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

import esa.mo.mal.transport.http.HTTPTransport;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Maps the fields of a MAL message header onto the custom HTTP headers defined
 * by the MAL-HTTP binding.
 *
 * The same mapping is used whether the message travels on an HTTP request or on
 * an HTTP response, so the methods here write through a {@link HttpHeaderSink}
 * rather than to either interface directly. The few fields whose treatment
 * differs between the two directions are deliberately left to the caller.
 */
public abstract class MALHttpHeaderEncoder {

    private MALHttpHeaderEncoder() {
    }

    /**
     * Writes the referer and the addressing headers, taken from the From and To
     * fields of the MAL header.
     *
     * @param malMessageHeader The MAL message header.
     * @param sink Where to write the HTTP headers.
     * @throws URISyntaxException if the From or To field is not a valid URI.
     */
    public static void encodeUriHeaders(final MALMessageHeader malMessageHeader,
            final HttpHeaderSink sink) throws URISyntaxException {
        URI uriFrom = new URI(malMessageHeader.getFrom().getValue());
        URI uriTo = new URI(malMessageHeader.getTo().getValue());
        sink.setReferer(uriFrom.toASCIIString());
        sink.setHeader("X-MAL-Version-Number", "2"); // according to 3.4.2 in recommended standard.
        sink.setHeader("X-MAL-To", uriTo.toASCIIString());
        sink.setHeader("Host", uriTo.getHost());
        sink.setHeader("request-target", uriTo.getPath());
    }

    /**
     * Writes the header fields that are mapped identically in both directions.
     *
     * The interaction stage and the error flag are not written here, because
     * the request and the response paths treat them differently.
     *
     * @param malMessageHeader The MAL message header.
     * @param sink Where to write the HTTP headers.
     */
    public static void encodeCommonHeaders(final MALMessageHeader malMessageHeader,
            final HttpHeaderSink sink) {
        sink.setHeader("X-MAL-Authentication-Id",
                HTTPTransport.byteArrayToHexString(malMessageHeader.getAuthenticationId().getValue()));

        Date timestampAsDate = new Date(malMessageHeader.getTimestamp().getValue());
        SimpleDateFormat timestampFormat = new SimpleDateFormat(HTTPTransport.TIMESTAMP_STRING_FORMAT);
        timestampFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        sink.setHeader("X-MAL-Timestamp", timestampFormat.format(timestampAsDate));

        sink.setHeader("X-MAL-Interaction-Type", malMessageHeader.getInteractionType().toString());
        sink.setHeader("X-MAL-Transaction-Id", String.valueOf(malMessageHeader.getTransactionId()));
        sink.setHeader("X-MAL-Service-Area", String.valueOf(malMessageHeader.getServiceArea().getValue()));
        sink.setHeader("X-MAL-Service", String.valueOf(malMessageHeader.getService().getValue()));
        sink.setHeader("X-MAL-Operation", String.valueOf(malMessageHeader.getOperation().getValue()));
        sink.setHeader("X-MAL-Area-Version", String.valueOf(malMessageHeader.getAreaVersion().getValue()));
    }

    /**
     * Writes the interaction stage header.
     *
     * @param malMessageHeader The MAL message header.
     * @param sink Where to write the HTTP header.
     */
    public static void encodeInteractionStage(final MALMessageHeader malMessageHeader,
            final HttpHeaderSink sink) {
        sink.setHeader("X-MAL-Interaction-Stage",
                String.valueOf(malMessageHeader.getInteractionStage().getValue()));
    }

    /**
     * Writes the supplements header, if the message carries any supplements.
     *
     * @param malMessageHeader The MAL message header.
     * @param sink Where to write the HTTP header.
     * @throws IOException if the supplements could not be encoded.
     */
    public static void encodeSupplements(final MALMessageHeader malMessageHeader,
            final HttpHeaderSink sink) throws IOException {
        String supplements = SupplementsEncoder.encode(malMessageHeader.getSupplements());

        if (supplements != null) {
            sink.setHeader("X-MAL-Supplements", supplements);
        }
    }

    /**
     * Writes the full set of MAL headers onto an outgoing HTTP request.
     *
     * A From or To field that is not a valid URI is logged and the addressing
     * headers are skipped, the rest of the mapping still being useful.
     *
     * @param malMessageHeader The MAL message header.
     * @param sink Where to write the HTTP headers.
     * @throws IOException if the supplements could not be encoded.
     */
    public static void encodeRequestHeaders(final MALMessageHeader malMessageHeader,
            final HttpHeaderSink sink) throws IOException {
        encodeCommonHeaders(malMessageHeader, sink);

        try {
            encodeUriHeaders(malMessageHeader, sink);
        } catch (URISyntaxException e) {
            RLOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        if (malMessageHeader.getInteractionStage() != null) {
            encodeInteractionStage(malMessageHeader, sink);
        }

        sink.setHeader("X-MAL-Is-Error-Message", malMessageHeader.getIsErrorMessage() ? "True" : "False");
        encodeSupplements(malMessageHeader, sink);
    }

    /**
     * Writes the full set of MAL headers onto an outgoing HTTP response.
     *
     * Note that this differs from the request mapping in three ways, all of
     * them preserved from the two implementations this was factored out of: an
     * invalid From or To field is fatal rather than logged, the interaction
     * stage is written unconditionally, and the error flag is written in lower
     * case.
     *
     * @param malMessageHeader The MAL message header.
     * @param sink Where to write the HTTP headers.
     * @throws IOException if the From or To field is not a valid URI.
     */
    public static void encodeResponseHeaders(final MALMessageHeader malMessageHeader,
            final HttpHeaderSink sink) throws IOException {
        encodeCommonHeaders(malMessageHeader, sink);

        try {
            encodeUriHeaders(malMessageHeader, sink);
        } catch (URISyntaxException use) {
            throw new IOException(
                    "HTTPMessageSender: HttpApiImplException at sendEncodedMessageViaHttpResponse()", use);
        }

        encodeInteractionStage(malMessageHeader, sink);
        sink.setHeader("X-MAL-Is-Error-Message", String.valueOf(malMessageHeader.getIsErrorMessage()));
        encodeSupplements(malMessageHeader, sink);
    }

    /**
     * Writes the content type header. If the default xml encoding mechanism is
     * used the content type is application/mal-xml, otherwise it is
     * application/mal and the encoder in use is named in the X-MAL-Encoding
     * header.
     *
     * @param encoderInUse The canonical name of the element stream factory in
     * use.
     * @param sink Where to write the HTTP headers.
     */
    public static void encodeContentType(final String encoderInUse, final HttpHeaderSink sink) {
        String contentType = "application/mal-xml";
        RLOGGER.log(Level.FINEST, "Using encoder {0}", encoderInUse);
        boolean isUsingDefaultEncoder = HTTPTransport.HTTP_DEFAULT_XML_ENCODER.equals(encoderInUse);

        if (!isUsingDefaultEncoder) {
            contentType = "application/mal";
            sink.setHeader("X-MAL-Encoding", encoderInUse);
        }

        sink.setHeader("Content-Type", contentType);
    }
}
