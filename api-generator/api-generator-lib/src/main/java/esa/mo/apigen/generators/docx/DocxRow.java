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
 * One row of a table, which knows where it has got to across the columns.
 * <p>
 * A cell is as wide as the column it sits in, and a cell that spans is as wide as the
 * columns it covers together. The row keeps the count, so that nothing has to be told its
 * own position.
 */
public final class DocxRow {

    private final DocxTable table;

    private int column = 0;

    DocxRow(DocxTable table) {
        this.table = table;
        table.append(3, "<w:tr>");
    }

    /**
     * Adds a cell holding text, in the table's own style.
     *
     * @param text The text, which is escaped.
     * @return the cell, for saying anything else about it.
     */
    public DocxCell cell(String text) {
        return new DocxCell(this, "<w:pPr><w:pStyle w:val=\"MOTable\"/></w:pPr><w:r><w:t>"
                + DocxText.escape(text) + "</w:t></w:r>");
    }

    /**
     * Adds a cell holding content that is already marked up - a hyperlink, or a signature
     * built out of several runs.
     *
     * @param xml The content of the cell, inside its paragraph.
     * @return the cell, for saying anything else about it.
     */
    public DocxCell markedUp(String xml) {
        return new DocxCell(this, xml);
    }

    /**
     * Ends the row.
     */
    public void end() {
        table.append(3, "</w:tr>");
    }

    void write(DocxCell cell) {
        table.append(4, cell.toXml(table.widthOf(column, cell.getSpan())));
        column += cell.getSpan();
    }
}
