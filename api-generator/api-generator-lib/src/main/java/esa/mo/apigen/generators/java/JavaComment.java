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

import java.util.ArrayList;
import java.util.List;

/**
 * Turns a specification comment into javadoc lines.
 * <p>
 * This reproduces the existing generator's behaviour exactly, including where that
 * behaviour is odd, because the generated API is compiled against by every api module and
 * its javadoc is part of what people read. Two quirks are deliberate:
 * <ul>
 * <li>an apostrophe becomes {@code &quot;} rather than {@code &apos;};</li>
 * <li>an angle bracket becomes an underscore, so a comment mentioning {@code >=} reads
 * {@code _=};</li>
 * <li>the line-length check happens before a word is added rather than after, so lines
 * routinely run past the threshold - up to 92 characters in the current output.</li>
 * </ul>
 * Changing either would be an improvement, and would show up in the golden tree as a
 * deliberate difference to be agreed rather than something to slip in here.
 */
public final class JavaComment {

    private static final int LENGTH_THRESHOLD = 70;

    private JavaComment() {
    }

    /**
     * Normalises a comment into the lines of a javadoc block.
     *
     * @param comment The comment, may be null.
     * @return the lines, empty if there is no comment.
     */
    public static List<String> normalise(String comment) {
        List<String> output = new ArrayList<String>();
        if (comment == null || comment.isEmpty()) {
            return output;
        }
        String text = comment;
        if (!text.endsWith(".")) {
            text += ".";
        }
        text = text.replace("&", "&amp;");
        text = text.replace("'", "&quot;");
        output.addAll(wrap(text));
        return output;
    }

    /**
     * Normalises a comment for a javadoc block inside a class, where angle brackets would
     * be read as markup. Package documentation goes through {@link #normalise} instead and
     * keeps its {@code <ul>} and {@code <li>} tags.
     *
     * @param comment The comment, may be null.
     * @return the lines, empty if there is no comment.
     */
    public static List<String> normaliseInClass(String comment) {
        List<String> output = new ArrayList<String>();
        for (String line : normalise(comment)) {
            output.add(escape(line));
        }
        return output;
    }

    /**
     * Removes the angle brackets that would otherwise be read as javadoc markup.
     *
     * @param text The comment text.
     * @return the text with angle brackets replaced by underscores.
     */
    public static String escape(String text) {
        return text == null ? null : text.replace("<", "_").replace(">", "_");
    }

    /**
     * Breaks text into lines at roughly the threshold, paragraph by paragraph.
     *
     * @param text The text to wrap.
     * @return the lines.
     */
    public static List<String> wrap(String text) {
        List<String> output = new ArrayList<String>();
        if (text.length() < LENGTH_THRESHOLD) {
            output.add(text);
            return output;
        }
        for (String paragraph : text.split("\n")) {
            StringBuilder line = new StringBuilder();
            int counter = 0;
            for (String word : paragraph.split(" ")) {
                if (counter > LENGTH_THRESHOLD) {
                    output.add(line.toString().trim());
                    line.setLength(0);
                    counter = 0;
                }
                line.append(word).append(' ');
                counter += 1 + word.length();
            }
            output.add(line.toString().trim());
        }
        return output;
    }
}
