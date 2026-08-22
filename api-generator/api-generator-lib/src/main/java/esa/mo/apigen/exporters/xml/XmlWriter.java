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
package esa.mo.apigen.exporters.xml;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A small indenting XML writer.
 * <p>
 * Hand-written rather than StAX because the output has to be laid out predictably: the
 * round-trip test compares documents, and a writer that decides its own line breaks makes
 * every comparison a negotiation.
 */
public final class XmlWriter {

    private final Writer out;
    private final Deque<String> open = new ArrayDeque<String>();
    private boolean elementOpen;
    private boolean hasChildren;

    public XmlWriter(Writer out) throws IOException {
        this.out = out;
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
    }

    public void start(String name) throws IOException {
        closeStartTag(true);
        indent(open.size());
        out.write("<" + name);
        open.push(name);
        elementOpen = true;
        hasChildren = false;
    }

    public void attr(String name, String value) throws IOException {
        if (value != null) {
            out.write(" " + name + "=\"" + escapeAttr(value) + "\"");
        }
    }

    public void attr(String name, int value) throws IOException {
        out.write(" " + name + "=\"" + value + "\"");
    }

    public void attr(String name, long value) throws IOException {
        out.write(" " + name + "=\"" + value + "\"");
    }

    public void attr(String name, boolean value) throws IOException {
        out.write(" " + name + "=\"" + value + "\"");
    }

    /**
     * Writes the element's text content. Must be called before any child element.
     *
     * @param text The content, written escaped.
     * @throws IOException if writing fails.
     */
    public void text(String text) throws IOException {
        if (text == null) {
            return;
        }
        closeStartTag(false);
        out.write(escapeText(text));
        hasChildren = true;
    }

    /**
     * Writes pre-serialised markup as-is - used for diagrams, whose SVG the model carries
     * as text.
     *
     * @param markup The markup to write verbatim.
     * @throws IOException if writing fails.
     */
    public void raw(String markup) throws IOException {
        if (markup == null) {
            return;
        }
        closeStartTag(false);
        out.write(markup);
        hasChildren = true;
    }

    public void end() throws IOException {
        String name = open.pop();
        if (elementOpen) {
            out.write("/>\n");
            elementOpen = false;
        } else {
            if (!hasChildren) {
                indent(open.size());
            }
            out.write("</" + name + ">\n");
        }
        hasChildren = false;
    }

    private void closeStartTag(boolean newline) throws IOException {
        if (elementOpen) {
            out.write(">");
            if (newline) {
                out.write("\n");
            }
            elementOpen = false;
        }
    }

    private void indent(int depth) throws IOException {
        for (int i = 0; i < depth; i++) {
            out.write("  ");
        }
    }

    public void flush() throws IOException {
        out.flush();
    }

    private static String escapeText(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String escapeAttr(String text) {
        return escapeText(text)
                .replace("\"", "&quot;")
                .replace("\n", "&#10;")
                .replace("\r", "&#13;")
                .replace("\t", "&#9;");
    }
}
