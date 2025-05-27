/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen.writers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Basic implementation of the language writer that adds a few extra support
 * methods.
 */
public abstract class AbstractLanguageWriter extends AbstractWriter implements LanguageWriter {

    /**
     * Constructor.
     *
     * @param lineSeparator The line separator to use.
     */
    public AbstractLanguageWriter(String lineSeparator) {
        super(lineSeparator);
    }

    /**
     * Constructor.
     */
    public AbstractLanguageWriter() {
        super();
    }

    @Override
    public void addMultilineComment(String comment) throws IOException {
        addMultilineComment(1, false, comment, false);
    }

    @Override
    public void addMultilineComment(int tabCount, boolean preBlankLine, String comment, boolean postBlankLine) throws IOException {
        addMultilineComment(tabCount, preBlankLine, normaliseComment(new LinkedList<>(), comment), postBlankLine);
    }

    /**
     * Processes a list of comments making sure they contain a full stop at the
     * end.
     *
     * @param output The list to return.
     * @param comments List of comments to check.
     * @return the processed list.
     */
    public static List<String> normaliseComments(List<String> output, List<String> comments) {
        if (comments != null) {
            for (String comment : comments) {
                normaliseComment(output, comment);
            }
        }

        return output;
    }

    /**
     * Processes a comment making sure it contain a full stop at the end. It
     * will also break it into multiple lines if it is longer than 100
     * characters.
     *
     * @param output The list to return.
     * @param comment The comment to check.
     * @return the supplied list.
     */
    public static List<String> normaliseComment(List<String> output, String comment) {
        if ((output != null) && (comment != null) && (comment.length() > 0)) {
            if (!comment.endsWith(".")) {
                comment += ".";
            }

            comment = comment.replace("&", "&amp;");
            comment = comment.replace("'", "&quot;");
            List<String> text = normaliseText(comment);
            output.addAll(text);
        }

        return output;
    }

    public static List<String> normaliseText(String comment) {
        List<String> output = new LinkedList<>();
        // If the size is less than 70 chars, then put
        // it directly, otherwise break it up
        int LENGTH_THRESHOLD = 70;

        if (comment.length() < LENGTH_THRESHOLD) {
            output.add(comment);
        } else {
            for (String paragraph : comment.split("\n")) {
                String[] parts = paragraph.split(" ");
                String str = "";
                int counter = 0;

                for (String part : parts) {
                    if (counter > LENGTH_THRESHOLD) {
                        // .trim() removes the last ' ' (space) character
                        output.add(str.trim());
                        // Reset both the string and the counter
                        str = "";
                        counter = 0;
                    }
                    str += part + " ";
                    counter += 1 + part.length();
                }

                // .trim() removes the last ' ' (space) character
                output.add(str.trim());
            }
        }
        return output;
    }

}
