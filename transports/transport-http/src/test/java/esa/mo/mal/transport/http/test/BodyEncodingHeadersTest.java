/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.test;

import esa.mo.mal.transport.gen.BodyEncoding;
import esa.mo.mal.transport.http.util.HttpHeaderSink;
import esa.mo.mal.transport.http.util.MALHttpHeaderEncoder;
import esa.mo.mal.transport.http.util.MALHttpHeaders;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Tests how the body encoding is written to and read from the Content-Type and
 * X-MAL-Encoding headers (CCSDS 524.3-B-1, 3.6.3 and 3.6.5).
 */
public class BodyEncodingHeadersTest {

    private static final String FIXED = BodyEncoding.FIXED_BINARY.getFactoryClassName();
    private static final String SPLIT = BodyEncoding.SPLIT_BINARY.getFactoryClassName();
    private static final String XML = BodyEncoding.XML.getFactoryClassName();
    private static final String STRING = "esa.mo.mal.encoder.string.StringStreamFactory";

    private static Map<String, String> written(String factoryClassName) {
        final Map<String, String> headers = new HashMap<>();
        MALHttpHeaderEncoder.encodeContentType(factoryClassName, new HttpHeaderSink() {
            @Override
            public void setHeader(String headerName, String headerValue) {
                headers.put(headerName, headerValue);
            }

            @Override
            public void setReferer(String referer) {
            }
        });
        return headers;
    }

    @Test
    public void xmlIsNamedByItsContentType() {
        Map<String, String> headers = written(XML);
        assertEquals("application/mal-xml", headers.get("Content-Type"));
        assertNull(headers.get("X-MAL-Encoding"));
    }

    @Test
    public void otherEncodingsCarryTheirIdentifier() {
        Map<String, String> headers = written(SPLIT);
        assertEquals("application/mal", headers.get("Content-Type"));
        assertEquals("2", headers.get("X-MAL-Encoding"));
    }

    @Test
    public void anEncodingWithoutIdentifierIsNamedByItsClass() {
        Map<String, String> headers = written(STRING);
        assertEquals("application/mal", headers.get("Content-Type"));
        assertEquals(STRING, headers.get("X-MAL-Encoding"));
    }

    @Test
    public void readsTheContentTypeAndIdentifier() {
        assertEquals(3, MALHttpHeaders.bodyEncodingIdOf("application/mal-xml", null, FIXED));
        assertEquals(3, MALHttpHeaders.bodyEncodingIdOf("application/mal-xml; charset=utf-8", null, FIXED));
        assertEquals(1, MALHttpHeaders.bodyEncodingIdOf("application/mal", "1", XML));
        assertEquals(7, MALHttpHeaders.bodyEncodingIdOf("application/mal", " 7 ", XML));
    }

    @Test
    public void understandsAClassNameAsSentBefore() {
        assertEquals(2, MALHttpHeaders.bodyEncodingIdOf("application/mal", SPLIT, XML));
        assertEquals(-1, MALHttpHeaders.bodyEncodingIdOf("application/mal", STRING, XML));
    }

    @Test
    public void fallsBackToTheOwnEncoding() {
        assertEquals(0, MALHttpHeaders.bodyEncodingIdOf(null, null, FIXED));
        assertEquals(0, MALHttpHeaders.bodyEncodingIdOf("text/xml", null, FIXED));
        assertEquals(0, MALHttpHeaders.bodyEncodingIdOf("application/mal", null, FIXED));
    }

    /**
     * A receiver whose own encoding has no identifier gets none back; the
     * transport then decodes with its own stream factory.
     */
    @Test
    public void anOwnEncodingWithoutIdentifierHasNone() {
        assertEquals(-1, MALHttpHeaders.bodyEncodingIdOf("application/mal", STRING, STRING));
        assertEquals(-1, MALHttpHeaders.bodyEncodingIdOf(null, null, STRING));
    }
}
