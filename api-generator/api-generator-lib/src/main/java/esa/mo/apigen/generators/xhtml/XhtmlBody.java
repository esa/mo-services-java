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
package esa.mo.apigen.generators.xhtml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A run of page content, built up and then appended to another.
 * <p>
 * A page is not written in the order it is read: the index of a service has to be written
 * above the operations it indexes, and both are only known once the operations have been
 * walked. So sections are written into bodies of their own and assembled at the end.
 */
public final class XhtmlBody {

    /**
     * How far in the content of a page sits: inside html and body.
     */
    static final int INDENT = 2;

    private final StringBuilder buf = new StringBuilder();

    /**
     * Escapes what XHTML reserves.
     * <p>
     * The generator this replaces escaped the text of a paragraph and not the text of a
     * field description, so a description containing a {@code <} or an {@code &} - and the
     * specifications contain both - produced a page no parser would accept.
     *
     * @param text The text, may be null.
     * @return the text, safe to put between tags.
     */
    public static String escape(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /**
     * Writes a heading.
     *
     * @param level The heading level, 1 to 6.
     * @param prefix What the heading is, such as "Composite: ".
     * @param anchor The anchor to give it, or null for none.
     * @param text The name, which is what the prefix introduces.
     * @param italic Whether the name is set in italics, which is how an abstract type is
     * shown.
     */
    public void title(int level, String prefix, String anchor, String text, boolean italic) {
        String name = escape(text);
        if (italic) {
            name = "<i>" + name + "</i>";
        }
        String id = anchor == null ? "" : " id=\"" + escape(anchor) + "\"";
        line("<h" + level + id + ">" + escape(prefix) + name + "</h" + level + ">");
    }

    /**
     * Writes a comment as one paragraph per paragraph of the comment.
     *
     * @param text The comment, may be null or empty.
     */
    public void comment(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        for (String block : XhtmlText.blocksOf(text)) {
            line(block);
        }
    }

    /**
     * Writes several comments in turn.
     *
     * @param texts The comments, any of which may be null or empty.
     */
    public void comments(List<String> texts) {
        for (String text : texts) {
            comment(text);
        }
    }

    /**
     * Describes one field of a type or a message, under its name.
     *
     * @param name The field name.
     * @param text What it is, may be null or empty.
     */
    public void fieldComment(String name, String text) {
        line("<h5>" + escape(name) + "</h5>");
        if (text == null || text.isEmpty()) {
            return;
        }
        for (String block : XhtmlText.blocksOf(text)) {
            line(block);
        }
    }

    /**
     * Writes a list of links to elsewhere in the page.
     *
     * @param title What the index is of.
     * @param level The heading level of that title.
     * @param entries The entries, name to href, in the order they are to be shown.
     */
    public void index(String title, int level, List<Map.Entry<String, String>> entries) {
        if (entries.isEmpty()) {
            return;
        }
        line("<div class=\"index\">");
        title(level, title, null, "", false);
        line("<ul>");
        for (Map.Entry<String, String> entry : entries) {
            line("<li><a href=\"" + escape(entry.getValue()) + "\">"
                    + escape(entry.getKey()) + "</a></li>");
        }
        line("</ul>");
        line("</div>");
    }

    /**
     * Writes a diagram.
     *
     * @param diagram The diagram to write, already drawn.
     */
    public void diagram(SvgDiagram diagram) {
        line("<p class=\"diagram\">");
        buf.append(diagram.render(INDENT + 1));
        line("</p>");
    }

    /**
     * Appends another body to this one.
     *
     * @param other The body to append.
     */
    public void append(XhtmlBody other) {
        buf.append(other.buf);
    }

    /**
     * @return true if nothing has been written.
     */
    public boolean isEmpty() {
        return buf.length() == 0;
    }

    @Override
    public String toString() {
        return buf.toString();
    }

    private void line(String text) {
        buf.append(XhtmlText.line(INDENT, text));
    }

    /**
     * @return an empty list of index entries, ready to add to.
     */
    static List<Map.Entry<String, String>> newIndex() {
        return new ArrayList<Map.Entry<String, String>>();
    }
}
