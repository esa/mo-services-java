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

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.TypeRef;
import java.util.ArrayList;
import java.util.List;

/**
 * Names a type inside a table cell, linking to where it is defined when the document that
 * mentions it is also the document that defines it.
 * <p>
 * The generator this replaces took two flags through every call - whether to show the name
 * of the field and whether to use the older layout - and branched on them here. Neither has
 * ever been set to anything but its default, so neither is carried over: a field is always
 * shown as {@code List_Type_ name}, never as {@code name : (List_Type_)}.
 */
public final class DocxTypeLink {

    /**
     * Separates two type names sharing a cell: the cell holds a paragraph each.
     */
    private static final String NEXT_LINE
            = "</w:p><w:p><w:pPr><w:jc w:val=\"center\"/></w:pPr>";

    private DocxTypeLink() {
    }

    /**
     * Names the type of a field, with the field's own name after it.
     *
     * @param area The area of the document being written.
     * @param service The service being written, or null at area level.
     * @param field The field to name.
     * @return the content of a cell.
     */
    public static String forField(Area area, Service service, Field field) {
        TypeRef type = field.getType();
        String prefix = type.isList() ? "List<" : "";
        String postfix = (type.isList() ? "> " : " ") + field.getName();
        return link(area, service, type, prefix, postfix);
    }

    /**
     * Names a type on its own.
     *
     * @param area The area of the document being written.
     * @param service The service being written, or null at area level.
     * @param type The type to name.
     * @return the content of a cell.
     */
    public static String forType(Area area, Service service, TypeRef type) {
        String prefix = type.isList() ? "List<" : "";
        String postfix = type.isList() ? ">" : "";
        return link(area, service, type, prefix, postfix);
    }

    /**
     * Says of each field whether it may be left out.
     *
     * @param fields The fields of a message.
     * @return the content of a cell, one answer per line.
     */
    public static String nullability(List<Field> fields) {
        List<String> answers = new ArrayList<String>();
        for (Field field : fields) {
            answers.add("<w:pPr><w:pStyle w:val=\"MOTable\"/></w:pPr><w:r><w:t>"
                    + (field.isCanBeNull() ? "Yes" : "No") + "</w:t></w:r>");
        }
        return join(answers);
    }

    /**
     * @param fragments What each line of the cell holds.
     * @return the fragments as the lines of one cell.
     */
    public static String join(List<String> fragments) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < fragments.size(); i++) {
            buf.append(fragments.get(i));
            if (i != fragments.size() - 1) {
                buf.append(NEXT_LINE);
            }
        }
        return buf.toString();
    }

    /**
     * Writes the name, linked to its definition when that definition is in this document.
     */
    private static String link(Area area, Service service, TypeRef type,
            String prefix, String postfix) {
        String name = qualify(area, service, type);
        // The MAL types are mentioned everywhere and defined in a document of their own, so
        // naming their area every time would say nothing.
        if (name.startsWith("MAL::")) {
            name = name.substring("MAL::".length());
        }
        boolean sameArea = area.getName().equalsIgnoreCase(type.getArea());
        return hyperlink(prefix, name, postfix, "DATATYPE_" + name, sameArea);
    }

    /**
     * Names a type from where it is being mentioned: the area is left off when it is the
     * area being written, and so is the service.
     */
    private static String qualify(Area area, Service service, TypeRef reference) {
        // The older spelling of an object reference, ObjectRef(Product), means the same as
        // the newer one; unwrapping lets both be named the same way.
        TypeRef type = reference.unwrapped();
        StringBuilder buf = new StringBuilder();
        if (!area.getName().equalsIgnoreCase(type.getArea())) {
            buf.append(type.getArea()).append("::");
        }
        String owning = service == null ? "" : service.getName();
        if (type.getService() != null && !type.getService().isEmpty()
                && !type.getService().equalsIgnoreCase(owning)) {
            buf.append(type.getService()).append("::");
        }
        return buf.append(type.isObjectRef()
                ? "ObjectRef<" + type.getName() + ">" : type.getName()).toString();
    }

    /**
     * Writes a name, as a link to a bookmark where there is one to link to.
     *
     * @param prefix What comes before the name.
     * @param typeName The name shown.
     * @param postfix What comes after the name.
     * @param linkTo The bookmark to link to.
     * @param withHyperlink Whether to link at all.
     * @return the content of a cell.
     */
    public static String hyperlink(String prefix, String typeName, String postfix,
            String linkTo, boolean withHyperlink) {
        // An object reference is shown as ObjectRef_Product_, but what is linked to is
        // Product: the reference is not a type anyone documents.
        boolean isObjectRef = typeName.startsWith("ObjectRef");
        String target = linkTo.replace("ObjectRef<", "").replace(">", "")
                .replace("ObjectRef(", "").replace(")", "");
        String shown = isObjectRef ? insideObjectRef(typeName) : typeName;

        StringBuilder buf = new StringBuilder();
        buf.append("<w:pPr><w:pStyle w:val=\"MOTable\"/></w:pPr><w:r><w:t>");
        buf.append(DocxText.escape(prefix));
        if (isObjectRef) {
            buf.append(DocxText.escape("ObjectRef<"));
        }
        buf.append("</w:t></w:r>");

        if (withHyperlink) {
            buf.append("<w:r><w:fldChar w:fldCharType=\"begin\"/></w:r>");
            buf.append("<w:r><w:instrText xml:space=\"preserve\"> HYPERLINK  \\l \"");
            buf.append(DocxText.escape(isObjectRef ? target : linkTo));
            buf.append("\" </w:instrText></w:r>");
            buf.append("<w:r><w:fldChar w:fldCharType=\"separate\"/></w:r>");
        }

        buf.append("<w:r>");
        if (withHyperlink) {
            buf.append("<w:rPr><w:rStyle w:val=\"Hyperlink\"/></w:rPr>");
        }
        buf.append("<w:t>").append(DocxText.escape(shown)).append("</w:t></w:r>");

        if (withHyperlink) {
            buf.append("<w:r><w:fldChar w:fldCharType=\"end\"/></w:r>");
        }

        buf.append("<w:r><w:t xml:space=\"preserve\">");
        if (isObjectRef) {
            buf.append(DocxText.escape(">"));
        }
        return buf.append(DocxText.escape(postfix)).append("</w:t></w:r>").toString();
    }

    /**
     * @param typeName A name of the form ObjectRef_Something_.
     * @return what the reference points at.
     */
    private static String insideObjectRef(String typeName) {
        int open = typeName.indexOf('<');
        int close = typeName.lastIndexOf('>');
        return open >= 0 && close > open ? typeName.substring(open + 1, close) : typeName;
    }
}
