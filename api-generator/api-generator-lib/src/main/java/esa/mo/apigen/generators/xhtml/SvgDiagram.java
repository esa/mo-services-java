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

/**
 * One drawing: boxes for fields, each naming the field over the type in it.
 * <p>
 * The surface is a grid rather than a canvas. A column is one field wide and a row is a
 * name over a type, so everything is placed in column and row units and multiplied up on
 * the way out. The drawing sizes itself: the extent of what has been placed is what the
 * {@code svg} element ends up declaring.
 * <p>
 * Two kinds of drawing share it. A message is drawn flat, one field per column, with what
 * applies to a field bracketed underneath it. A type is drawn expanded, each composite
 * opened out into the fields it holds on the row below, so a field spans as many columns
 * as its contents need and as many rows as it has left above the bottom.
 */
public final class SvgDiagram {

    /**
     * The width of one field.
     */
    static final int WIDTH = 160;

    /**
     * The height of the box naming the field.
     */
    static final int PRIMARY_HEIGHT = 30;

    /**
     * The height of the box naming its type.
     */
    static final int SECONDARY_HEIGHT = 20;

    static final int ROW_HEIGHT = PRIMARY_HEIGHT + SECONDARY_HEIGHT;

    /**
     * How far a bracket sits below one drawn over the same fields.
     */
    private static final int SPAN_STEP = 16;

    /**
     * The height of a bracket.
     */
    private static final int SPAN_HEIGHT = 10;

    private static final int HALF_TEXT_HEIGHT = 4;

    private static final int MARGIN = 10;

    private static final String PARENT_COLOUR = "grey";

    private static final String HEADER_COLOUR = "yellow";

    private static final String COMPOSITE_COLOUR = "lavender";

    private static final String FIELD_COLOUR = "lightsteelblue";

    private static final String STROKE = "navy";

    private final StringBuilder buf = new StringBuilder();

    private final int baseLine = MARGIN;

    private int column = 1;

    private int maxWidth = 2;

    private int maxHeight = MARGIN;

    /**
     * Draws the type a composite extends, ahead of the fields it adds.
     *
     * @param type The name of the parent type.
     * @param href Where it is described, or null if nothing loaded describes it.
     */
    public void parent(String type, String href) {
        box(column * WIDTH, baseLine, WIDTH, PRIMARY_HEIGHT, PARENT_COLOUR, "EXTENDS", null,
                false, false, true);
        box(column * WIDTH, baseLine + PRIMARY_HEIGHT, WIDTH, SECONDARY_HEIGHT, PARENT_COLOUR,
                type, href, true, false, true);
        column++;
        widen();
    }

    /**
     * Draws one field: its name over the name of its type.
     *
     * @param name The field name.
     * @param type The name of its type.
     * @param href Where that type is described, or null.
     * @param italic Whether the type is abstract, which is how an abstract type is shown.
     * @param bold Whether the type is an enumeration.
     */
    public void field(String name, String type, String href, boolean italic, boolean bold) {
        box(column * WIDTH, baseLine, WIDTH, PRIMARY_HEIGHT, FIELD_COLOUR, name, null,
                false, false, true);
        box(column * WIDTH, baseLine + PRIMARY_HEIGHT, WIDTH, SECONDARY_HEIGHT, FIELD_COLOUR,
                type, href, italic, bold, true);
        column++;
        widen();
    }

    /**
     * Brackets the fields about to be drawn, saying something that applies to all of them.
     *
     * @param index How many brackets already sit under them, so this one clears them.
     * @param count How many fields it covers.
     * @param text What it says.
     */
    public void span(int index, int count, String text) {
        int x1 = column * WIDTH;
        int x2 = (column + count) * WIDTH;
        int y1 = baseLine + ROW_HEIGHT + (index * SPAN_STEP);
        bracket(x1, y1, x2, y1 + SPAN_HEIGHT, text);
    }

    /**
     * Draws a field of an expanded type.
     *
     * @param name The field name.
     * @param type The name of its type.
     * @param href Where that type is described, or null.
     * @param x The column it starts at.
     * @param y The row it starts at.
     * @param columns How many columns it covers.
     * @param rows How many rows it covers.
     * @param italic Whether the type is abstract.
     * @param bold Whether the type is an enumeration.
     * @param composite Whether the type is a composite, which is shown in its own colour.
     */
    public void nestedField(String name, String type, String href, int x, int y,
            int columns, int rows, boolean italic, boolean bold, boolean composite) {
        int top = baseLine + (y * ROW_HEIGHT);
        box(x * WIDTH, top, WIDTH * columns, PRIMARY_HEIGHT, HEADER_COLOUR, name, null,
                false, false, false);
        box(x * WIDTH, top + PRIMARY_HEIGHT, WIDTH * columns,
                (rows * ROW_HEIGHT) - PRIMARY_HEIGHT,
                composite ? COMPOSITE_COLOUR : FIELD_COLOUR, type, href, italic, bold, false);
        if (maxWidth < x + columns) {
            maxWidth = x + columns;
        }
    }

    /**
     * Rules under a field of an expanded type, saying something that applies to it.
     * <p>
     * Wider than a bracket and drawn as a line with the text knocked out of it, because
     * what it spans can be a good many columns of an opened-out composite.
     *
     * @param x The column it starts at.
     * @param row The row it sits under.
     * @param columns How many columns it covers.
     * @param depth How many rules are drawn under the same field, this one being the last.
     * @param text What it says.
     */
    public void nestedSpan(int x, int row, int columns, int depth, String text) {
        int x1 = x * WIDTH;
        int x2 = (x + columns) * WIDTH;
        int y1 = baseLine + (row * ROW_HEIGHT) + (depth * SPAN_STEP);
        int y2 = y1 + SPAN_HEIGHT;
        int middle = y1 + (y2 - y1) / 2;

        line(x1, y1, x1, y2);
        line(x2, y1, x2, y2);
        line(x1, middle, x2, middle);

        // The label is set in a box of clear ground so the rule does not run through it.
        int width = text.length() * 8;
        knockOut(x1 + ((x2 - x1) / 2 - (width / 2)), y1, width, y2 - y1);
        text(x1, y1, x2 - x1, HALF_TEXT_HEIGHT, text, null, false, false);
        grow(y2);
    }

    /**
     * @param indent How many steps in the element sits.
     * @return the drawing as an svg element, sized to what it holds.
     */
    public String render(int indent) {
        StringBuilder out = new StringBuilder();
        out.append(XhtmlText.line(indent, "<svg:svg version=\"1.1\" width=\""
                + ((maxWidth * WIDTH) + MARGIN) + "px\" height=\""
                + (maxHeight + MARGIN) + "px\">"));
        out.append(buf);
        out.append(XhtmlText.line(indent, "</svg:svg>"));
        return out.toString();
    }

    /**
     * @return true if nothing has been drawn.
     */
    public boolean isEmpty() {
        return buf.length() == 0;
    }

    private void bracket(int x1, int y1, int x2, int y2, String text) {
        int middle = y1 + (y2 - y1) / 2;
        line(x1, y1, x1, y2);
        line(x2, y1, x2, y2);
        line(x1, middle, x1 + SECONDARY_HEIGHT, middle);
        line(x2, middle, x2 - SECONDARY_HEIGHT, middle);
        text(x1, y1, x2 - x1, y2 - y1, text, null, false, false);
        grow(y2);
    }

    private void widen() {
        if (maxWidth < column) {
            maxWidth = column;
        }
    }

    /**
     * Draws a box with its text in it.
     *
     * @param centred Whether the text sits in the middle of the box. A box of an expanded
     * type is as tall as the type is deep, and text floating in the middle of it would sit
     * nowhere near the name it belongs to, so it stays at the top.
     */
    private void box(int x, int y, int width, int height, String colour, String text,
            String href, boolean italic, boolean bold, boolean centred) {
        append("<svg:rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\""
                + height + "\" fill=\"" + colour + "\" stroke=\"" + STROKE
                + "\" stroke-width=\"2\"/>");
        text(x, y, width, centred ? height : SECONDARY_HEIGHT, text, href, italic, bold);
        grow(y + height);
    }

    private void knockOut(int x, int y, int width, int height) {
        append("<svg:rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\""
                + height + "\" fill=\"white\" stroke=\"" + STROKE + "\" stroke-width=\"0\"/>");
        grow(y + height);
    }

    private void line(int x1, int y1, int x2, int y2) {
        append("<svg:line x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 + "\" y2=\"" + y2
                + "\" stroke=\"" + STROKE + "\" stroke-width=\"1\"/>");
    }

    private void text(int x, int y, int width, int height, String text, String href,
            boolean italic, boolean bold) {
        String style = italic ? " font-style=\"italic\"" : "";
        if (bold) {
            style += " font-weight=\"bold\"";
        }
        String shown = XhtmlBody.escape(text);
        if (href != null && !href.isEmpty()) {
            shown = "<svg:a xlink:href=\"" + XhtmlBody.escape(href) + "\">" + shown + "</svg:a>";
        }
        append("<svg:text x=\"" + (x + width / 2) + "\" y=\""
                + (y + height / 2 + HALF_TEXT_HEIGHT) + "\" font-family=\"Verdana\""
                + " font-size=\"12\"" + style + " fill=\"" + STROKE
                + "\" text-anchor=\"middle\" alignment-baseline=\"middle\">");
        append(1, shown);
        append("</svg:text>");
    }

    private void grow(int height) {
        if (maxHeight < height) {
            maxHeight = height;
        }
    }

    private void append(String text) {
        append(0, text);
    }

    private void append(int extra, String text) {
        buf.append(XhtmlText.line(XhtmlBody.INDENT + 2 + extra, text));
    }
}
