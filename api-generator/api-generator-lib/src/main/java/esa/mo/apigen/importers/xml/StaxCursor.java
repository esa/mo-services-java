/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
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
package esa.mo.apigen.importers.xml;

import esa.mo.apigen.model.SourceLocation;
import esa.mo.apigen.model.SourceRef;
import java.io.StringWriter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Thin conveniences over a StAX cursor: attribute access, child iteration, subtree
 * capture and source locations.
 * <p>
 * StAX rather than a DOM because the reader gives line and column for free, and every
 * element in the model records where it came from.
 */
public final class StaxCursor {

    private final XMLStreamReader in;
    private final SourceRef source;

    public StaxCursor(XMLStreamReader in, SourceRef source) {
        this.in = in;
        this.source = source;
    }

    public XMLStreamReader reader() {
        return in;
    }

    public String localName() {
        return in.getLocalName();
    }

    public String namespace() {
        return in.getNamespaceURI() == null ? "" : in.getNamespaceURI();
    }

    /**
     * Returns an attribute of the current element, ignoring its namespace. Attributes in
     * these documents are unqualified, and being lenient about it costs nothing.
     *
     * @param name The attribute name.
     * @return the value, or null if absent.
     */
    public String attr(String name) {
        for (int i = 0; i < in.getAttributeCount(); i++) {
            if (name.equals(in.getAttributeLocalName(i))) {
                return in.getAttributeValue(i);
            }
        }
        return null;
    }

    public String attr(String name, String fallback) {
        String value = attr(name);
        return value == null ? fallback : value;
    }

    public int intAttr(String name, int fallback) {
        String value = attr(name);
        if (value == null) {
            return fallback;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    public long longAttr(String name, long fallback) {
        String value = attr(name);
        if (value == null) {
            return fallback;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    public Integer integerAttr(String name) {
        String value = attr(name);
        if (value == null) {
            return null;
        }
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public boolean boolAttr(String name, boolean fallback) {
        String value = attr(name);
        return value == null ? fallback : Boolean.parseBoolean(value.trim());
    }

    /**
     * Returns the comment attribute, treating the empty string as absent. The
     * specifications are full of {@code comment=""}, which carries no information and
     * would otherwise become an empty documentation comment in every output.
     *
     * @return the comment, null if there is none, empty if it was written empty.
     */
    public String comment() {
        // An empty comment is kept apart from an absent one. The two read the same
        // everywhere a comment is shown, but a message body records which of them a field
        // carried, so the difference has to survive the import.
        return attr("comment");
    }

    public SourceLocation location() {
        javax.xml.stream.Location loc = in.getLocation();
        return new SourceLocation(source, loc.getLineNumber(), loc.getColumnNumber());
    }

    /**
     * Advances to the next child element of the current element, or returns false when
     * the element ends. The cursor must be on a START_ELEMENT.
     *
     * @return true if positioned on a child START_ELEMENT.
     * @throws XMLStreamException if the document is malformed.
     */
    public boolean nextChild() throws XMLStreamException {
        int depth = 0;
        while (in.hasNext()) {
            int event = in.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                if (depth == 0) {
                    return true;
                }
                depth++;
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                if (depth == 0) {
                    return false;
                }
                depth--;
            }
        }
        return false;
    }

    /**
     * Consumes the rest of the current element, leaving the cursor on its END_ELEMENT.
     *
     * @throws XMLStreamException if the document is malformed.
     */
    public void skip() throws XMLStreamException {
        int depth = 1;
        while (in.hasNext() && depth > 0) {
            int event = in.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                depth++;
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                depth--;
            }
        }
    }

    /**
     * Returns the mixed text content of the current element, consuming it.
     *
     * @return the text, with no trimming, or null if there was none.
     * @throws XMLStreamException if the document is malformed.
     */
    public String text() throws XMLStreamException {
        StringBuilder buf = new StringBuilder();
        int depth = 1;
        while (in.hasNext() && depth > 0) {
            int event = in.next();
            if (event == XMLStreamConstants.CHARACTERS || event == XMLStreamConstants.CDATA) {
                if (depth == 1) {
                    buf.append(in.getText());
                }
            } else if (event == XMLStreamConstants.START_ELEMENT) {
                depth++;
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                depth--;
            }
        }
        return buf.length() == 0 ? null : buf.toString();
    }

    /**
     * Serialises the child elements of the current element, consuming them. Used for
     * diagrams, whose SVG is carried through the model as text rather than parsed.
     *
     * @return the serialised children, or null if there were none.
     * @throws XMLStreamException if the document is malformed.
     */
    public String captureChildren() throws XMLStreamException {
        StringWriter out = new StringWriter();
        int depth = 1;
        boolean wrote = false;
        while (in.hasNext() && depth > 0) {
            int event = in.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    depth++;
                    writeStart(out);
                    wrote = true;
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    depth--;
                    if (depth > 0) {
                        out.write("</" + in.getLocalName() + ">");
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                case XMLStreamConstants.CDATA:
                    if (depth > 1) {
                        out.write(escapeText(in.getText()));
                    }
                    break;
                default:
                    break;
            }
        }
        return wrote ? out.toString() : null;
    }

    private void writeStart(StringWriter out) {
        out.write("<" + in.getLocalName());
        for (int i = 0; i < in.getAttributeCount(); i++) {
            out.write(" " + in.getAttributeLocalName(i) + "=\""
                    + escapeAttr(in.getAttributeValue(i)) + "\"");
        }
        out.write(">");
    }

    private static String escapeText(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String escapeAttr(String text) {
        return escapeText(text).replace("\"", "&quot;");
    }
}
