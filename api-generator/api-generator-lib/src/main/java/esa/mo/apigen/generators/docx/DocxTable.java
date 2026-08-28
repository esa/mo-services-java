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
 * One table: the widths of its columns, and the rows written into it.
 * <p>
 * The borders and the sizing are the same for every table in these documents, so they are
 * not something a caller says anything about.
 */
public final class DocxTable {

    /**
     * The borders every table in these documents has. Written through the same indent
     * helper as everything else rather than as a block of pre-indented text, so that one
     * place decides what a level of indentation is.
     */
    private static final String[] BORDERS = {"top", "left", "bottom", "right", "insideH", "insideV"};

    private final DocxBody body;

    private final int[] widths;

    DocxTable(DocxBody body, int[] widths) {
        this.body = body;
        this.widths = widths;
        body.append(2, "<w:tbl>");
        body.append(3, "<w:tblPr>");
        body.append(4, "<w:tblW w:w=\"00\" w:type=\"auto\"/>");
        body.append(4, "<w:tblBorders>");
        for (String edge : BORDERS) {
            body.append(5, "<w:" + edge + " w:val=\"single\" w:sz=\"4\" w:space=\"0\""
                    + " w:color=\"000000\"/>");
        }
        body.append(4, "</w:tblBorders>");
        body.append(3, "</w:tblPr>");
        if (widths != null) {
            body.append(3, "<w:tblGrid>");
            for (int width : widths) {
                body.append(4, "<w:gridCol w:w=\"" + width + "\"/>");
            }
            body.append(3, "</w:tblGrid>");
        }
    }

    /**
     * Starts a row.
     *
     * @return the row, to add cells to.
     */
    public DocxRow row() {
        return new DocxRow(this);
    }

    /**
     * Ends the table.
     */
    public void end() {
        body.append(2, "</w:tbl>");
    }

    /**
     * @param column The column the cell starts in.
     * @param span How many columns it covers.
     * @return how wide the cell is, which is the columns it covers taken together.
     */
    int widthOf(int column, int span) {
        if (widths == null) {
            return 0;
        }
        int width = 0;
        for (int i = column; i < column + span && i < widths.length; i++) {
            width += widths[i];
        }
        return width;
    }

    void append(int indent, String text) {
        body.append(indent, text);
    }
}
