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
import java.util.Arrays;
import java.util.List;

/**
 * Turns the text of a comment into the markup of a page.
 */
public final class XhtmlText {

    private static final String OPEN_LIST = "<ol>";

    private static final String CLOSE_LIST = "</ol>";

    private XhtmlText() {
    }

    /**
     * @param indent How many steps in, at four spaces a step.
     * @param text The line.
     * @return the line, indented and ended.
     */
    public static String line(int indent, String text) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            buf.append("    ");
        }
        return buf.append(text).append('\n').toString();
    }

    /**
     * Splits a comment into the blocks of markup that say it.
     * <p>
     * A comment is one string in the specification, and where it means a new paragraph it
     * says so with a line break or with the double space the specifications use for the
     * same thing. Requirement lists are written in the same string as markup - a line that
     * is exactly {@code <ol>} opens a level, one that is exactly {@code </ol>} closes it,
     * and text between {@code <li>} and {@code </li>} is an item of the level open at the
     * time. Here that markup can simply be honoured: the target format is the one it is
     * written in.
     *
     * @param text The comment, may be null.
     * @return the blocks, each a complete element, empty if there is no text.
     */
    public static List<String> blocksOf(String text) {
        List<String> blocks = new ArrayList<String>();
        if (text == null) {
            return blocks;
        }
        int open = 0;
        for (String part : Arrays.asList(text.split("(  |\n)"))) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            if (OPEN_LIST.equals(trimmed)) {
                blocks.add(OPEN_LIST);
                open++;
            } else if (CLOSE_LIST.equals(trimmed)) {
                // A close with nothing open is markup the specification got wrong; writing
                // it out would make the page unparseable, so it is dropped.
                if (open > 0) {
                    blocks.add(CLOSE_LIST);
                    open--;
                }
            } else if (trimmed.contains("<li>")) {
                // An item outside any list opens one, since a list is what it means and an
                // item on its own is not something the page can hold.
                if (open == 0) {
                    blocks.add(OPEN_LIST);
                    open++;
                }
                blocks.add("<li>" + XhtmlBody.escape(itemOf(trimmed)) + "</li>");
            } else if (open > 0) {
                // Text inside a list that is not an item still belongs to the item above it.
                blocks.add("<li>" + XhtmlBody.escape(trimmed) + "</li>");
            } else {
                blocks.add("<p>" + XhtmlBody.escape(trimmed) + "</p>");
            }
        }
        while (open > 0) {
            blocks.add(CLOSE_LIST);
            open--;
        }
        return blocks;
    }

    /**
     * @return what a line holding an item says, without the tags that mark it as one.
     */
    private static String itemOf(String line) {
        int start = line.indexOf("<li>") + "<li>".length();
        int end = line.indexOf("</li>", start);
        return (end < 0 ? line.substring(start) : line.substring(start, end)).trim();
    }
}
