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
package esa.mo.apigen.generators.docx;

/**
 * The text side of a document: what has to be escaped, and how a line is indented.
 */
public final class DocxText {

    private DocxText() {
    }

    /**
     * Prepares text written by a specification for a document.
     * <p>
     * The list item markup a few comments carry is dropped rather than escaped: the
     * documents show those comments as running text, so a literal {@code _li_} in the middle
     * of a sentence would be worse than nothing. Everything else XML reserves is escaped,
     * and the order matters - the ampersands have to go first, or the escapes escape
     * themselves.
     *
     * @param text The text to put in the document, may be null.
     * @return the text, ready to sit inside an element.
     */
    public static String escape(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("<li>", "").replace("</li>", "")
                .replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /**
     * The body of a document ends its lines the way Word does, and the numbering part ends
     * them the way everything else here does. That is not a decision anyone took - the two
     * writers of the existing generator were built with different separators, one passing
     * "\r\n" and the other taking the default - but the documents are compared byte for
     * byte, so both are reproduced as they are.
     */
    public static final String BODY_SEPARATOR = "\r\n";

    public static final String SEPARATOR = "\n";

    /**
     * Splits a comment where a document starts a new paragraph: at a line break, or at the
     * double space the specifications use to mean one.
     *
     * @param text The comment, may be null.
     * @return the paragraphs, empty if there is no text.
     */
    public static java.util.List<String> split(String text) {
        java.util.List<String> parts = new java.util.ArrayList<String>();
        if (text != null) {
            parts.addAll(java.util.Arrays.asList(text.split("(  |\n)")));
        }
        return parts;
    }

    /**
     * @param indent How many steps in, at four spaces a step.
     * @param text The line.
     * @param separator How the line ends.
     * @return the line, indented and ended.
     */
    public static String line(int indent, String text, String separator) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            buf.append("    ");
        }
        return buf.append(text).append(separator).toString();
    }
}
