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
package esa.mo.apigen.generators.java;

/**
 * Accumulates the text of a Java source file.
 */
public final class JavaSource {

    private final StringBuilder buf = new StringBuilder();

    public void line(String text) {
        buf.append(text).append('\n');
    }

    public void blank() {
        buf.append('\n');
    }

    /**
     * Writes a single-line javadoc block at the given indentation.
     *
     * @param indent Number of four-space levels.
     * @param text The comment, already normalised.
     */
    public void javadoc(int indent, String text) {
        String pad = pad(indent);
        line(pad + "/**");
        line(pad + " * " + JavaComment.escape(text));
        line(pad + " */");
    }

    private static String pad(int indent) {
        StringBuilder p = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            p.append("    ");
        }
        return p.toString();
    }

    /**
     * @return the last line written, without its line separator.
     */
    public String lastLine() {
        int end = buf.length() - 1;
        int start = buf.lastIndexOf("\n", end - 1) + 1;
        return buf.substring(start, end);
    }

    /**
     * Replaces the last line written, for the rare case where a line can only be finished
     * once the next is known - a list that closes on the line of its last entry.
     *
     * @param text The replacement line.
     */
    public void replaceLast(String text) {
        int end = buf.length() - 1;
        int start = buf.lastIndexOf("\n", end - 1) + 1;
        buf.setLength(start);
        line(text);
    }

    @Override
    public String toString() {
        return buf.toString();
    }
}
