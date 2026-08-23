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
package esa.mo.apigen.exporters.mospec;

import java.util.ArrayList;
import java.util.List;

/**
 * How a MOSpec file is laid out, apart from what it says.
 * <p>
 * The exporter decides what to write; this decides how it reads - where the indentation
 * goes, how a doc comment is written, where the blank lines fall. Keeping them apart means
 * the layout can be changed without touching the traversal, and the round-trip test says
 * whether the change was safe.
 */
public final class SourceFormatter {

    private final StringBuilder buffer = new StringBuilder();

    private int indent = 0;

    /**
     * True where documentation is left out entirely. The one mode that does not preserve
     * the model, and the reason the round-trip test holds it to a weaker standard.
     */
    private boolean suppressed = false;

    /**
     * Stops anything being written as documentation.
     */
    public void suppressDocumentation() {
        this.suppressed = true;
    }

    /**
     * Steps in. Everything written until the matching {@link #out} is indented one further.
     */
    public void in() {
        indent++;
    }

    public void out() {
        if (indent > 0) {
            indent--;
        }
    }

    /**
     * Writes one line at the current indentation.
     *
     * @param text The line, which may be empty for a blank one.
     */
    public void line(String text) {
        if (text.isEmpty()) {
            buffer.append('\n');
            return;
        }
        for (int i = 0; i < indent; i++) {
            buffer.append('\t');
        }
        buffer.append(text).append('\n');
    }

    /**
     * Writes a blank line, unless the last thing written was already one. A traversal that
     * separates everything from everything else would otherwise leave gaps of two and three
     * lines wherever two rules meet.
     */
    public void blank() {
        int length = buffer.length();
        if (length == 0 || (length >= 2 && buffer.charAt(length - 1) == '\n'
                && buffer.charAt(length - 2) == '\n')) {
            return;
        }
        buffer.append('\n');
    }

    /**
     * Writes a documentation comment, as one line where it fits and as a block where it
     * does not.
     *
     * @param text The documentation, may be null or empty for none.
     */
    public void doc(String text) {
        doc(text, new ArrayList<String>());
    }

    /**
     * Writes a documentation comment followed by tagged lines, which is how an operation
     * documents its fields and errors in bulk mode.
     *
     * @param text The documentation of the thing itself, may be null.
     * @param tags The tagged lines, each already beginning with its tag.
     */
    public void doc(String text, List<String> tags) {
        if (suppressed) {
            return;
        }
        // Nothing is re-wrapped. A comment is written with the line breaks it has and no
        // others: wrapping a long paragraph would turn one paragraph into several, and the
        // text that came back would say something the model no longer does. Long lines are
        // the price of a round trip that returns what it was given.
        List<String> body = new ArrayList<String>(paragraphs(text));
        if (!tags.isEmpty() && !body.isEmpty()) {
            body.add("");
        }
        for (String tag : tags) {
            body.addAll(paragraphs(tag));
        }
        if (body.isEmpty()) {
            // An empty comment is written as an empty one. The specifications are full of
            // comment="", which says nothing but is not the same as saying nothing, and a
            // format that could not tell them apart would not round-trip.
            if (text != null) {
                line("///");
            }
            return;
        }
        // One line is written as one line; anything longer becomes a block, so that a short
        // comment does not cost three lines to say one thing.
        if (body.size() == 1) {
            line("/// " + body.get(0));
            return;
        }
        line("/**");
        for (String written : body) {
            line(written.isEmpty() ? " *" : " * " + written);
        }
        line(" **/");
    }

    /**
     * Writes text that has to survive exactly as it is - a requirement, or anything else
     * whose own line breaks carry meaning.
     *
     * @param text The text.
     */
    public void verbatim(String text) {
        line("\"\"\"");
        in();
        for (String written : text.split("\n", -1)) {
            line(written);
        }
        out();
        line("\"\"\"");
    }

    /**
     * @return what has been written.
     */
    public String toText() {
        return buffer.toString();
    }

    /**
     * Splits documentation where the writer put a line break, so that a comment written as
     * several paragraphs stays several paragraphs.
     */
    private static List<String> paragraphs(String text) {
        List<String> found = new ArrayList<String>();
        if (text == null || text.isEmpty()) {
            return found;
        }
        for (String paragraph : text.split("\n", -1)) {
            found.add(paragraph);
        }
        return found;
    }

}
