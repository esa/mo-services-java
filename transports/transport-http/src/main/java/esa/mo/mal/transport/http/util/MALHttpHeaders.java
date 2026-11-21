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
package esa.mo.mal.transport.http.util;

import esa.mo.mal.transport.gen.BodyEncoding;

/**
 * Helpers for reading the MAL header fields carried over HTTP headers.
 */
public abstract class MALHttpHeaders {

    private MALHttpHeaders() {
    }

    /**
     * Returns the value of a header that the MAL HTTP binding requires, and
     * fails with the name of the header when it carries no value.
     *
     * Without this the absence of a header surfaces further down as whatever
     * the parser of that particular field happens to throw, such as an unknown
     * enumeration or an unparsable number, naming neither the header nor the
     * message it came with.
     *
     * @param headerName The name of the header.
     * @param headerValue The value read for it, possibly null or empty.
     * @return The value of the header.
     * @throws HttpApiImplException if the header carries no value.
     */
    public static String required(final String headerName, final String headerValue)
            throws HttpApiImplException {
        if (headerValue == null || headerValue.isEmpty()) {
            throw new HttpApiImplException("The MAL message cannot be decoded because the "
                    + headerName + " header carries no value. Either it was not sent, or the "
                    + "message is not a MAL message.", null);
        }

        return headerValue;
    }

    /**
     * Returns the body encoding identifier named by the Content-Type and
     * X-MAL-Encoding headers (CCSDS 524.3-B-1, 3.6.3 and 3.6.5). A message
     * without a MAL content type is taken to be in the receiver's own
     * encoding, and an X-MAL-Encoding holding a factory class name, as sent
     * before version 15.1, is still understood.
     *
     * @param contentType The Content-Type header, may be null.
     * @param malEncoding The X-MAL-Encoding header, may be null.
     * @param ownFactoryClassName The class of the receiver's stream factory.
     * @return the identifier, or -1 if the headers name no known encoding.
     */
    public static int bodyEncodingIdOf(final String contentType, final String malEncoding,
            final String ownFactoryClassName) {
        BodyEncoding own = BodyEncoding.ofFactoryClass(ownFactoryClassName);
        int ownId = own == null ? -1 : own.getId();
        String type = contentType == null ? "" : contentType.split(";")[0].trim();

        if ("application/mal-xml".equalsIgnoreCase(type)) {
            return BodyEncoding.XML.getId();
        }
        if (!"application/mal".equalsIgnoreCase(type) || malEncoding == null) {
            return ownId;
        }
        String value = malEncoding.trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            if (value.equals(ownFactoryClassName)) {
                return ownId;
            }
            BodyEncoding named = BodyEncoding.ofFactoryClass(value);
            return named == null ? -1 : named.getId();
        }
    }
}
