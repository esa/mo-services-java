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
 * One cell of a table, described before it is written.
 * <p>
 * The generator this replaces reached a cell through nine overloads of one method, taking
 * a column index, the widths of every column, and four flags in a fixed order. Here a cell
 * says what it is: the row knows which column it is in and how wide that is, and anything
 * unusual about the cell is named at the point it is asked for.
 */
public final class DocxCell {

    /**
     * How a cell takes part in a run of cells merged down the page.
     */
    enum Merge {
        /**
         * Not merged with anything.
         */
        NONE,
        /**
         * The top of a merged run, which is where the content goes.
         */
        START,
        /**
         * Continues the run above it, and so is written empty.
         */
        CONTINUE
    }

    private final DocxRow row;

    private final String content;

    private String shade = null;

    private boolean centered = false;

    private int span = 1;

    private Merge merge = Merge.NONE;

    DocxCell(DocxRow row, String content) {
        this.row = row;
        this.content = content;
    }

    /**
     * Fills the cell with a background colour.
     *
     * @param colour The colour, as six hexadecimal digits.
     * @return this cell.
     */
    public DocxCell shaded(String colour) {
        this.shade = colour;
        return this;
    }

    /**
     * @return this cell, with its content centred.
     */
    public DocxCell centered() {
        this.centered = true;
        return this;
    }

    /**
     * Widens the cell over the columns that follow it.
     *
     * @param columns How many columns the cell covers, including its own.
     * @return this cell.
     */
    public DocxCell spanning(int columns) {
        this.span = columns;
        return this;
    }

    /**
     * @return this cell, as the top of a run merged down the page.
     */
    public DocxCell mergeStart() {
        this.merge = Merge.START;
        return this;
    }

    /**
     * @return this cell, continuing the merged run above it.
     */
    public DocxCell mergeContinue() {
        this.merge = Merge.CONTINUE;
        return this;
    }

    /**
     * Finishes the cell and returns to the row, so that cells read one after another.
     *
     * @return the row this cell belongs to.
     */
    public DocxRow next() {
        row.write(this);
        return row;
    }

    /**
     * Finishes the cell and the row.
     */
    public void endRow() {
        row.write(this);
        row.end();
    }

    String toXml(int width) {
        StringBuilder buf = new StringBuilder("<w:tc><w:tcPr>");
        buf.append("<w:tcW w:w=\"").append(width).append("\" w:type=\"dxa\"/>");

        if (merge == Merge.START) {
            buf.append("<w:vMerge w:val=\"restart\"/><w:vAlign w:val=\"center\"/>");
        } else if (merge == Merge.CONTINUE) {
            buf.append("<w:vMerge/>");
        }
        if (span > 1) {
            buf.append("<w:gridSpan w:val=\"").append(span).append("\"/>");
        }
        if (shade != null) {
            buf.append("<w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"").append(shade).append("\"/>");
        }
        buf.append("</w:tcPr>");

        // A cell that only continues the run above it holds nothing: the content sits in
        // the cell that started the run.
        if (merge == Merge.CONTINUE) {
            buf.append("<w:p/>");
        } else {
            buf.append("<w:p>");
            if (centered) {
                buf.append("<w:pPr><w:jc w:val=\"center\"/></w:pPr>");
            }
            buf.append(content).append("</w:p>");
        }
        return buf.append("</w:tc>").toString();
    }

    int getSpan() {
        return span;
    }
}
