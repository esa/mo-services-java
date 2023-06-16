/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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
package esa.mo.tools.stubgen.docx;

import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.AbstractWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.TypeReference;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Cesar.Coelho
 */
public class DocxBaseWriter extends AbstractWriter {

    private final DocxNumberingWriter numberWriter;
    private final StringBuffer buffer = new StringBuffer();

    public DocxBaseWriter() throws IOException {
        super("\r\n");
        numberWriter = null;
    }

    public DocxBaseWriter(DocxNumberingWriter numberWriter) throws IOException {
        super("\r\n");
        this.numberWriter = numberWriter;
    }

    public DocxNumberingWriter getNumberWriter() {
        return numberWriter;
    }

    public void addFigureCaption(String caption) throws IOException {
        if (null != caption) {
            buffer.append(makeLine(2, "<w:p>", false));
            buffer.append(makeLine(3, "<w:pPr><w:pStyle w:val=\"TableTitle\"/></w:pPr><w:r><w:t xml:space=\"preserve\">Figure </w:t></w:r>", false));
            buffer.append(makeLine(3, "<w:bookmarkStart w:id=\"0\" w:name=\"F_" + caption + "\"/>", false));
            buffer.append(makeLine(3, "<w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> STYLEREF \"Heading 1\"\\l \\n \\t  \\* MERGEFORMAT </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r><w:t>1</w:t></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r><w:r><w:noBreakHyphen/></w:r><w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> SEQ Figure \\s 1 </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r><w:t>1</w:t></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r><w:bookmarkEnd w:id=\"0\"/><w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText>tc  \\f T \"</w:instrText></w:r><w:fldSimple w:instr=\" STYLEREF &quot;Heading 1&quot;\\l \\n \\t  \\* MERGEFORMAT \">", false));
            buffer.append(makeLine(3, "<w:bookmarkStart w:id=\"1\" w:name=\"_" + caption + "\"/><w:r><w:instrText>1</w:instrText></w:r></w:fldSimple>", false));
            buffer.append(makeLine(3, "<w:r><w:instrText>-</w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> SEQ Figure_TOC \\s 1 </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r><w:instrText>1</w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r>", false));
            buffer.append(makeLine(3, "<w:r><w:instrText>" + caption + "</w:instrText></w:r>", false));
            buffer.append(makeLine(3, "<w:bookmarkEnd w:id=\"1\"/><w:r><w:instrText>\"</w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r>", false));
            buffer.append(makeLine(3, "<w:r><w:t>:  " + caption + "</w:t></w:r>", false));
            buffer.append(makeLine(2, "</w:p>", false));
        }
    }

    public void startTable(int[] widths) throws IOException {
        startTable(widths, null);
    }

    public void startTable(int[] widths, String caption) throws IOException {
        if (null != caption) {
            buffer.append(makeLine(2, "<w:p>", false));
            buffer.append(makeLine(3, "<w:pPr><w:pStyle w:val=\"TableTitle\"/></w:pPr><w:r><w:t xml:space=\"preserve\">Table </w:t></w:r>", false));
            buffer.append(makeLine(3, "<w:bookmarkStart w:id=\"0\" w:name=\"T_" + caption + "\"/>", false));
            buffer.append(makeLine(3, "<w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> STYLEREF \"Heading 1\"\\l \\n \\t  \\* MERGEFORMAT </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r><w:t>1</w:t></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r><w:r><w:noBreakHyphen/></w:r><w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> SEQ Table \\s 1 </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r><w:t>1</w:t></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r><w:bookmarkEnd w:id=\"0\"/><w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText>tc  \\f T \"</w:instrText></w:r><w:fldSimple w:instr=\" STYLEREF &quot;Heading 1&quot;\\l \\n \\t  \\* MERGEFORMAT \">", false));
            buffer.append(makeLine(3, "<w:bookmarkStart w:id=\"1\" w:name=\"_" + caption + "\"/><w:r><w:instrText>1</w:instrText></w:r></w:fldSimple>", false));
            buffer.append(makeLine(3, "<w:r><w:instrText>-</w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> SEQ Table_TOC \\s 1 </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r><w:r><w:instrText>1</w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r>", false));
            buffer.append(makeLine(3, "<w:r><w:instrText>" + caption + "</w:instrText></w:r>", false));
            buffer.append(makeLine(3, "<w:bookmarkEnd w:id=\"1\"/><w:r><w:instrText>\"</w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"end\"/></w:r>", false));
            buffer.append(makeLine(3, "<w:r><w:t>:  " + caption + "</w:t></w:r>", false));
            buffer.append(makeLine(2, "</w:p>", false));

        }
        buffer.append(makeLine(2, "<w:tbl>", false));
        buffer.append(makeLine(3, "<w:tblPr>", false));
        buffer.append(makeLine(4, "<w:tblW w:w=\"00\" w:type=\"auto\"/>", false));
        buffer.append(makeLine(4, "<w:tblBorders>", false));
        buffer.append(makeLine(5, "<w:top w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/>", false));
        buffer.append(makeLine(5, "<w:left w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/>", false));
        buffer.append(makeLine(5, "<w:bottom w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/>", false));
        buffer.append(makeLine(5, "<w:right w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/>", false));
        buffer.append(makeLine(5, "<w:insideH w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/>", false));
        buffer.append(makeLine(5, "<w:insideV w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"000000\"/>", false));
        buffer.append(makeLine(4, "</w:tblBorders>", false));
        buffer.append(makeLine(3, "</w:tblPr>", false));

        if (null != widths) {
            buffer.append(makeLine(3, "<w:tblGrid>", false));
            for (int i : widths) {
                buffer.append(makeLine(4, "<w:gridCol w:w=\"" + i + "\"/>", false));
            }
            buffer.append(makeLine(3, "</w:tblGrid>", false));
        }
    }

    public void startRow() throws IOException {
        buffer.append(makeLine(3, "<w:tr>", false));
    }

    public void addCell(int index, int[] widths, String text) throws IOException {
        addCell(index, widths, text, null, true, 0, false, false);
    }

    public void addCell(int index, int[] widths, String text, boolean vMerge, boolean vRestart) throws IOException {
        addCell(index, widths, text, null, true, 0, vMerge, vRestart);
    }

    public void addCell(int index, int[] widths, String text, String shade, boolean vMerge, boolean vRestart) throws IOException {
        addCell(index, widths, text, shade, true, 0, vMerge, vRestart);
    }

    public void addCell(int index, int[] widths, String text, boolean centered) throws IOException {
        addCell(index, widths, text, null, centered, 0, false, false);
    }

    public void addCell(int index, int[] widths, String text, String shade) throws IOException {
        addCell(index, widths, text, shade, true, 0, false, false);
    }

    public void addCell(int index, int[] widths, String text, String shade, String linkTo) throws IOException {
        String str = createHyperLink("", text, "", linkTo, true);
        actualAddCell(index, widths, str, shade, true, 0, false, false);
    }

    public void addCell(int index, int[] widths, String text, String shade, String linkTo, int span) throws IOException {
        String str = createHyperLink("", text, "", linkTo, true);
        actualAddCell(index, widths, str, shade, true, span, false, false);
    }

    public void addCell(int index, int[] widths, String text, String shade, boolean centered) throws IOException {
        addCell(index, widths, text, shade, centered, 0, false, false);
    }

    public void addCell(int index, int[] widths, String text, String shade, int span) throws IOException {
        addCell(index, widths, text, shade, true, span, false, false);
    }

    public void addCell(int index, int[] widths, boolean includeMessageFieldNames,
            boolean oldStyle, AreaType area, ServiceType service, TypeUtils.TypeRef type, String shade, int span) throws IOException {
        String str = createTypeHyperLink(includeMessageFieldNames, oldStyle, area, service, type);
        actualAddCell(index, widths, str, shade, true, span, false, false);
    }

    public void addCell(int index, int[] widths, boolean includeMessageFieldNames,
            boolean oldStyle, AreaType area, ServiceType service, List<TypeUtils.TypeRef> types, String shade) throws IOException {
        addCell(index, widths, includeMessageFieldNames, oldStyle, area, service, types, shade, 1);
    }

    public void addCell(int index, int[] widths, boolean includeMessageFieldNames,
            boolean oldStyle, AreaType area, ServiceType service, List<TypeUtils.TypeRef> types, String shade, int span) throws IOException {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < types.size(); i++) {
            String hyperlink = createTypeHyperLink(includeMessageFieldNames, oldStyle, area, service, types.get(i));
            buf.append(hyperlink);

            if (i != types.size() - 1) {
                buf.append("</w:p>");
                buf.append("<w:p>");
                buf.append("<w:pPr><w:jc w:val=\"center\"/></w:pPr>");
            }
        }

        actualAddCell(index, widths, buf.toString(), shade, true, span, false, false);
    }

    public void addCellNullability(int index, int[] widths,
            List<TypeUtils.TypeRef> types, String shade, int span) throws IOException {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < types.size(); i++) {
            boolean nullable = types.get(i).getFieldRef().isCanBeNull();
            buf.append("<w:r><w:t>");
            buf.append(nullable ? "Yes" : "No");
            buf.append("</w:t></w:r>");

            if (i != types.size() - 1) {
                buf.append("</w:p>");
                buf.append("<w:p>");
                buf.append("<w:pPr><w:jc w:val=\"center\"/></w:pPr>");
            }
        }

        actualAddCell(index, widths, buf.toString(), shade, true, span, false, false);
    }

    public void addCell(int index, int[] widths, String text, String shade,
            boolean centered, int span, boolean vMerge, boolean vRestart) throws IOException {
        actualAddCell(index, widths, "<w:r><w:t>" + escape(text) + "</w:t></w:r>", shade, centered, span, vMerge, vRestart);
    }

    protected void actualAddCell(int index, int[] widths, String text, String shade,
            boolean centered, int span, boolean vMerge, boolean vRestart) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append("<w:tc><w:tcPr>");
        if (widths != null) {
            int width = 0;
            if (span > 1) {
                for (int i = index; i < (index + span); i++) {
                    width += widths[i];
                }
            } else {
                width = widths[index];
            }

            buf.append("<w:tcW w:w=\"").append(width).append("\" w:type=\"dxa\"/>");
        }
        if (vMerge) {
            if (vRestart) {
                buf.append("<w:vMerge w:val=\"restart\"/><w:vAlign w:val=\"center\"/>");
            } else {
                buf.append("<w:vMerge/>");
            }
        }
        if (span > 1) {
            buf.append("<w:gridSpan w:val=\"").append(span).append("\"/>");
        }
        if (shade != null) {
            buf.append("<w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"");
            buf.append(shade);
            buf.append("\"/></w:tcPr>");
        } else {
            buf.append("</w:tcPr>");
        }
        if ((!vMerge) || (vRestart)) {
            buf.append("<w:p>");
            if (centered) {
                buf.append("<w:pPr><w:jc w:val=\"center\"/></w:pPr>");
            }
            buf.append(text);
            buf.append("</w:p>");
        } else {
            buf.append("<w:p/>");
        }
        buf.append("</w:tc>");

        buffer.append(makeLine(4, buf.toString(), false));
    }

    public void endRow() throws IOException {
        buffer.append(makeLine(3, "</w:tr>", false));
    }

    public void endTable() throws IOException {
        buffer.append(makeLine(2, "</w:tbl>", false));
    }

    public void addTitle(int level, String name) throws IOException {
        addTitle(level, "", name, "", false);
    }

    public void addTitle(int level, String section, String name, String bookmarkSection, boolean bookmark) throws IOException {
        buffer.append(makeLine(2, "<w:p><w:pPr><w:pStyle w:val=\"Heading" + level + "\"/></w:pPr>", false));
        if (bookmark) {
            buffer.append(makeLine(3, "<w:bookmarkStart w:id=\"1\" w:name=\"_" + bookmarkSection + "_" + name + "\"/><w:bookmarkEnd w:id=\"1\"/>", false));
        }
        buffer.append(makeLine(3, "<w:r><w:t>" + section + name + "</w:t></w:r>", false));
        buffer.append(makeLine(2, "</w:p>", false));
    }

    public void addNumberedComment(List<String> strings) throws IOException {
        if ((null != strings) && (!strings.isEmpty())) {
            if (1 == strings.size()) {
                addComment(strings.get(0));
            } else {
                int instance = 0;
                if (null != this.numberWriter) {
                    instance = this.numberWriter.getNextNumberingInstance();
                }

                addNumberedComment(instance, 0, strings.iterator());
            }
        }
    }

    public void addNumberedComment(int instance, int level, Iterator<String> iterator) throws IOException {
        while (iterator.hasNext()) {
            String text = iterator.next();

            if (text != null) {
                if ("<ol>".equalsIgnoreCase(text)) {
                    addNumberedComment(instance, level + 1, iterator);
                } else if ("</ol>".equalsIgnoreCase(text)) {
                    return;
                } else {
                    addNumberedComment(instance, level, text);
                }
            }
        }
    }

    public void addComment(String text) throws IOException {
        List<String> strings = GeneratorUtils.splitString(null, text);

        if (!strings.isEmpty()) {
            for (int i = 0; i < strings.size(); i++) {
                String string = strings.get(i);

                if (string != null) {
                    if (string.contentEquals("<ul>") || string.contentEquals("</ul>")) {
                        string = null;
                    } else {
                        if (string.contains("<li>")) {
                            string = string.substring(string.indexOf("<li>") + 4, string.indexOf("</li>"));
                            string = "<w:p><w:pPr><w:pStyle w:val=\"ListParagraph\"/><w:numPr><w:ilvl w:val=\"0\"/><w:numId w:val=\"1\"/></w:numPr></w:pPr><w:r><w:t>" + escape(string) + "</w:t></w:r></w:p>";
                        } else {
                            string = "<w:p><w:r><w:t>" + escape(string) + "</w:t></w:r></w:p>";
                        }
                    }

                    strings.set(i, string);
                }
            }

            for (String str : strings) {
                if (null != str) {
                    buffer.append(makeLine(2, str, false));
                }
            }
        }
    }

    @Override
    public void flush() throws IOException {
    }

    public StringBuffer getBuffer() {
        return buffer;
    }

    public void appendBuffer(StringBuffer buf) {
        buffer.append(buf);
    }

    private void addNumberedComment(int instance, int level, String text) throws IOException {
        List<String> strings = GeneratorUtils.splitString(null, text);
        if (!strings.isEmpty()) {
            if (strings.size() > 1) {
                for (String str : strings) {
                    addNumberedComment(instance, level, str);
                }
            } else {
                String str = strings.get(0);
                if (str != null && str.length() > 0) {
                    buffer.append(makeLine(2, "<w:p><w:pPr><w:numPr><w:ilvl w:val=\""
                            + level + "\"/><w:numId w:val=\"" + instance + "\"/></w:numPr></w:pPr><w:r><w:t>"
                            + escape(str) + "</w:t></w:r></w:p>", false));
                }
            }
        }
    }

    private String createTypeHyperLink(boolean includeMessageFieldNames, boolean oldStyle,
            AreaType area, ServiceType service, TypeUtils.TypeRef ref) throws IOException {
        String prefix = "";
        String typeName;
        String postfix = "";
        boolean hyperlink;
        TypeReference type;

        if (includeMessageFieldNames && ref.isField()) {
            NamedElementReferenceWithCommentType field = ref.getFieldRef();
            type = field.getType();
            hyperlink = area.getName().equalsIgnoreCase(type.getArea());

            if (oldStyle) {
                if (field.getName() != null && field.getName().length() > 0) {
                    prefix = field.getName() + " : (";
                } else {
                    prefix = "(";
                }

                postfix = ")";

                if (type.isList()) {
                    prefix += "List<";
                    postfix = ">" + postfix;
                }
            } else {
                prefix = (type.isList()) ? "List<" : "";
                postfix = (type.isList()) ? "> " : " "; // Space html character
                postfix += field.getName();
            }
        } else {
            type = ref.getTypeRef();
            hyperlink = area.getName().equalsIgnoreCase(type.getArea());

            if (type.isList()) {
                prefix = "List<";
                postfix = ">";
            }
        }

        typeName = GeneratorUtils.createFQTypeName(area, service, type, false);
        // No need to append the MAL prefix as those types are very well known
        if (typeName.startsWith("MAL::")) {
            typeName = typeName.replace("MAL::", "");
        }

        return createHyperLink(prefix, typeName, postfix, "DATATYPE_" + typeName, hyperlink);
    }

    private String createHyperLink(String prefix, String typeName, String postfix,
            String linkTo, boolean withHyperlink) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append("<w:r><w:t>");
        buf.append(escape(prefix));
        buf.append("</w:t></w:r>");

        if (withHyperlink) {
            buf.append("<w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText xml:space=\"preserve\"> HYPERLINK  \\l \"_");
            buf.append(linkTo);
            buf.append("\" </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r>");
        }

        buf.append("<w:r>");
        if (withHyperlink) {
            buf.append("<w:rPr><w:rStyle w:val=\"Hyperlink\"/></w:rPr>");
        }
        buf.append("<w:t>").append(escape(typeName)).append("</w:t>");
        buf.append("</w:r>");

        if (withHyperlink) {
            buf.append("<w:r><w:fldChar w:fldCharType=\"end\"/></w:r>");
        }

        buf.append("<w:r><w:t xml:space=\"preserve\">");
        buf.append(escape(postfix));
        buf.append("</w:t></w:r>");
        return buf.toString();
    }

    private String escape(String t) {
        if (t == null) {
            return "";
        }

        t = t.replaceAll("<li>", "");
        t = t.replaceAll("</li>", "");
        t = t.replaceAll("&", "&amp;");
        t = t.replaceAll("<", "&lt;");
        t = t.replaceAll(">", "&gt;");
        return t;
    }

}
