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
 * The body of a Word document, built up as it is written.
 * <p>
 * Everything a document is made of goes through here - titles, paragraphs, tables,
 * captions - and comes out as the WordprocessingML that says it. Nothing in this class
 * knows anything about MO: it is told what to write, not what it means.
 */
public final class DocxBody {

    /**
     * The numbering of the document this body belongs to, so that a numbered list can ask
     * for one of its own. Null for a body that is built up on the side and appended later,
     * which is how the data type sections are assembled.
     */
    private final DocxNumbering numbering;

    /**
     * A caption is the same construction whether it names a table or a figure: the word,
     * the chapter number, a sequence number, and an entry for the table of contents. Only
     * the word and the two sequence names differ, so the block is written once.
     */
    private static final String CAPTION
            = "<w:pPr><w:pStyle w:val=\"TableTitle\"/></w:pPr><w:r>"
            + "<w:t xml:space=\"preserve\">${kind} </w:t></w:r>";

    private final StringBuilder buffer = new StringBuilder();

    /**
     * @param numbering The numbering of the document, which numbered lists draw from.
     */
    public DocxBody(DocxNumbering numbering) {
        this.numbering = numbering;
    }

    /**
     * A body that cannot number a list, for text assembled on the side.
     */
    public DocxBody() {
        this(null);
    }

    /**
     * Writes a heading.
     *
     * @param level How deep the heading is, 1 being a chapter.
     * @param name What the heading says.
     */
    public void title(int level, String name) {
        title(level, "", name, null);
    }

    /**
     * Writes a heading that can be linked to from elsewhere in the document.
     *
     * @param level How deep the heading is, 1 being a chapter.
     * @param section The number in front of the name, empty for none.
     * @param name What the heading says.
     * @param bookmark What to call the anchor, or null not to place one.
     */
    public void title(int level, String section, String name, String bookmark) {
        append(2, "<w:p><w:pPr><w:pStyle w:val=\"Heading" + level + "\"/></w:pPr>");
        if (bookmark != null) {
            append(3, "<w:bookmarkStart w:id=\"1\" w:name=\"" + bookmark + "_" + name
                    + "\"/><w:bookmarkEnd w:id=\"1\"/>");
        }
        append(3, "<w:r><w:t>" + section + name + "</w:t></w:r>");
        append(2, "</w:p>");
    }

    /**
     * Writes a comment out of a specification as running text.
     * <p>
     * The comments carry a little markup of their own: a line inside {@code _li_} becomes a
     * bullet, and the {@code _ul_} around a group of them says nothing a Word document
     * needs, so it is dropped.
     *
     * @param text The comment, may be null.
     */
    public void comment(String text) {
        for (String line : DocxText.split(text)) {
            if ("<ul>".equals(line) || "</ul>".equals(line)) {
                continue;
            }
            if (line.contains("<li>")) {
                String item = line.substring(line.indexOf("<li>") + 4, line.indexOf("</li>"));
                append(2, "<w:p><w:pPr><w:pStyle w:val=\"ListParagraph\"/><w:numPr>"
                        + "<w:ilvl w:val=\"0\"/><w:numId w:val=\"1\"/></w:numPr></w:pPr><w:r><w:t>"
                        + DocxText.escape(item) + "</w:t></w:r></w:p>");
            } else {
                append(2, "<w:p><w:pPr><w:keepNext/></w:pPr><w:r><w:t>"
                        + DocxText.escape(line) + "</w:t></w:r></w:p>");
            }
        }
    }

    /**
     * Writes a numbered list, which gets a numbering of its own so that it starts from the
     * top rather than carrying on from the last list.
     *
     * @param lines The items. An {@code _ol_} among them opens a nested list and the
     * matching close ends it.
     * @throws java.io.IOException if the numbering template is missing from the build.
     */
    public void numberedComment(java.util.List<String> lines) throws java.io.IOException {
        if (lines == null || lines.isEmpty()) {
            return;
        }
        int instance = numbering == null ? 0 : numbering.nextInstance();
        numbered(instance, 0, lines.iterator());
    }

    /**
     * Writes the items of a list, descending into a nested one where it starts.
     */
    private void numbered(int instance, int level, java.util.Iterator<String> lines) {
        while (lines.hasNext()) {
            String line = lines.next();
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            if ("<ol>".equalsIgnoreCase(line)) {
                numbered(instance, level + 1, lines);
            } else if ("</ol>".equalsIgnoreCase(line)) {
                return;
            } else {
                numberedItem(instance, level, line);
            }
        }
    }

    /**
     * Writes one item, which may itself be several lines, and which is written as a note
     * instead where it announces itself as one.
     */
    private void numberedItem(int instance, int level, String text) {
        java.util.List<String> parts = DocxText.split(text);
        if (parts.size() > 1) {
            for (String part : parts) {
                numberedItem(instance, level, part);
            }
            return;
        }
        if (text.toLowerCase().contains("note:")) {
            note(text);
            return;
        }
        for (String part : parts) {
            if (!part.isEmpty()) {
                append(2, "<w:p><w:pPr><w:numPr><w:ilvl w:val=\"" + level + "\"/><w:numId w:val=\""
                        + instance + "\"/></w:numPr></w:pPr><w:r><w:t>" + DocxText.escape(part)
                        + "</w:t></w:r></w:p>");
            }
        }
    }

    /**
     * Writes a note, which the documents set apart from the text around it.
     *
     * @param text The note, with or without the word that announces it.
     */
    public void note(String text) {
        String body = text.replace("Note: ", "").replace("NOTE: ", "");
        append(2, "<w:p w14:paraId=\"7F00DCB7\" w14:textId=\"77777777\" w:rsidR=\"00B06274\""
                + " w:rsidRPr=\"00B06274\" w:rsidRDefault=\"00B06274\" w:rsidP=\"00B06274\">");
        append(3, "<w:pPr><w:keepLines/><w:tabs><w:tab w:val=\"left\" w:pos=\"806\"/></w:tabs>"
                + "<w:spacing w:before=\"240\" w:after=\"0\" w:line=\"280\" w:lineRule=\"atLeast\"/>"
                + "<w:ind w:left=\"1138\" w:hanging=\"1138\"/><w:jc w:val=\"both\"/><w:rPr>"
                + "<w:kern w:val=\"0\"/><w:sz w:val=\"24\"/><w:szCs w:val=\"20\"/>"
                + "<w14:ligatures w14:val=\"none\"/></w:rPr></w:pPr>");
        append(3, "<w:r w:rsidRPr=\"00B06274\"><w:t>NOTE</w:t></w:r>");
        append(3, "<w:r w:rsidRPr=\"00B06274\"><w:tab/><w:t>\u2013</w:t></w:r>");
        append(3, "<w:r w:rsidRPr=\"00B06274\"><w:tab/><w:t>" + body + "</w:t></w:r>");
        append(2, "</w:p>");
    }

    /**
     * Writes the one field a message carries, named and described.
     *
     * @param fieldName The name of the field.
     * @param fieldComment What the field holds.
     */
    public void singleTypeSignature(String fieldName, String fieldComment) {
        append(2, "<w:p><w:pPr><w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"1\"/></w:numPr>"
                + "</w:pPr><w:r><w:rPr><w:b/><w:bCs/></w:rPr><w:t>" + DocxText.escape(fieldName)
                + "</w:t></w:r><w:r><w:t  xml:space=\"preserve\"> - "
                + DocxText.escape(fieldComment) + "</w:t></w:r></w:p>");
    }

    /**
     * Starts a table.
     *
     * @param widths The width of each column, in twentieths of a point.
     * @return the table, to add rows to.
     */
    public DocxTable table(int[] widths) {
        return new DocxTable(this, widths);
    }

    /**
     * Starts a table under a caption that names it.
     *
     * @param widths The width of each column, in twentieths of a point.
     * @param caption What the table is called.
     * @return the table, to add rows to.
     */
    public DocxTable table(int[] widths, String caption) {
        if (caption != null) {
            caption("Table", caption);
        }
        return new DocxTable(this, widths);
    }

    /**
     * Writes the caption of a figure, which follows the figure rather than preceding it.
     *
     * @param caption What the figure is called.
     */
    public void figureCaption(String caption) {
        if (caption != null) {
            caption("Figure", caption);
        }
    }

    /**
     * Writes a caption: the word, the chapter it is in, its number within the chapter, and
     * the field that puts it in the table of contents.
     *
     * @param kind "Table" or "Figure", which is both the word shown and the sequence the
     * number comes from.
     * @param caption What is being named.
     */
    private void caption(String kind, String caption) {
        String bookmark = "Table".equals(kind) ? "T_" : "F_";
        append(2, "<w:p>");
        append(3, CAPTION.replace("${kind}", kind));
        append(3, "<w:bookmarkStart w:id=\"0\" w:name=\"" + bookmark + caption + "\"/>");
        append(3, "<w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText"
                + " xml:space=\"preserve\"> STYLEREF \"Heading 1\"\\l \\n \\t  \\* MERGEFORMAT"
                + " </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r>"
                + "<w:t>1</w:t></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r><w:r>"
                + "<w:noBreakHyphen/></w:r><w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r>"
                + "<w:instrText xml:space=\"preserve\"> SEQ " + kind + " \\s 1 </w:instrText>"
                + "</w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r><w:t>1</w:t>"
                + "</w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r><w:bookmarkEnd w:id=\"0\"/>"
                + "<w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText>tc  \\f T \""
                + "</w:instrText></w:r><w:fldSimple w:instr=\" STYLEREF &quot;Heading 1&quot;"
                + "\\l \\n \\t  \\* MERGEFORMAT \">");
        append(3, "<w:bookmarkStart w:id=\"1\" w:name=\"_" + caption + "\"/><w:r><w:instrText>1"
                + "</w:instrText></w:r></w:fldSimple>");
        append(3, "<w:r><w:instrText>-</w:instrText></w:r><w:r><w:fldChar"
                + " w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> SEQ "
                + kind + "_TOC \\s 1 </w:instrText></w:r><w:r><w:fldChar"
                + " w:fldCharType=\"separate\"/></w:r><w:r><w:instrText>1</w:instrText></w:r><w:r>"
                + "<w:fldChar w:fldCharType=\"end\"/></w:r>");
        append(3, "<w:r><w:instrText>" + caption + "</w:instrText></w:r>");
        append(3, "<w:bookmarkEnd w:id=\"1\"/><w:r><w:instrText>\"</w:instrText></w:r><w:r>"
                + "<w:fldChar w:fldCharType=\"end\"/></w:r>");
        append(3, "<w:r><w:t>:  " + caption + "</w:t></w:r>");
        append(2, "</w:p>");
    }

    /**
     * Starts the next page.
     */
    public void pageBreak() {
        buffer.append("<w:p><w:r><w:br w:type=\"page\"/></w:r></w:p>");
    }

    /**
     * Ends a section without starting a new page, which is what separates one data type
     * from the next.
     */
    public void sectionBreak() {
        buffer.append("<w:p><w:pPr><w:sectPr><w:type w:val=\"continuous\"/></w:sectPr></w:pPr></w:p>");
    }

    /**
     * @return everything written so far.
     */
    public String toXml() {
        return buffer.toString();
    }

    /**
     * Appends a body built up on the side, as it stands. The data types are gathered while
     * the services are walked and land at the end of the document.
     *
     * @param other The body to append.
     */
    public void append(DocxBody other) {
        buffer.append(other.buffer);
    }

    public void append(int indent, String text) {
        buffer.append(DocxText.line(indent, text, DocxText.BODY_SEPARATOR));
    }

}
