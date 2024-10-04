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
package esa.mo.tools.stubgen.specification;

import esa.mo.tools.stubgen.GeneratorBase;
import esa.mo.xsd.TypeReference;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.w3c.dom.Element;

/**
 * Utility methods for types.
 */
public class TypeUtils {

    /**
     * Converts a type reference from the XML format to the internal format.
     *
     * @param tiSource Source of generator specific type information.
     * @param tr The XML type reference.
     * @return the converted type information.
     */
    public static FieldInfo convertTypeReference(TypeInformation tiSource, TypeReference tr) {
        return convertTypeReference(tiSource, tr, null, null, false);
    }

    /**
     * Converts a type reference from the XML format to the internal format.
     *
     * @param tiSource Source of generator specific type information.
     * @param tr The XML type reference
     * @param fieldName Optional field name.
     * @param fieldComment Optional field comment.
     * @param fieldNullability The nullability of the field.
     * @return the converted type information.
     */
    public static FieldInfo convertTypeReference(TypeInformation tiSource,
            TypeReference tr, String fieldName, String fieldComment, boolean fieldNullability) {
        if (tr == null) {
            return null;
        }

        String argTypeStr = tiSource.createElementType(tr, true);
        String argVersionStr = tiSource.getAreaPackage(tr.getArea())
                + tr.getArea().toLowerCase() + "." + tr.getArea()
                + "Helper." + tr.getArea().toUpperCase() + "_AREA_VERSION";

        if (!tr.isList()) {
            return new FieldInfo(tr, fieldName, fieldComment, tr.getName(),
                    argTypeStr, tiSource.isAttributeNativeType(tr),
                    getTypeShortForm(tiSource, tr, argTypeStr),
                    argVersionStr, fieldNullability);
        }

        if (StdStrings.XML.equals(tr.getArea())) {
            // ToDo proper support for lists of XML types
            return new FieldInfo(tr, fieldName, fieldComment, tr.getName(),
                    argTypeStr, tiSource.isAttributeNativeType(tr),
                    getTypeShortForm(tiSource, tr, argTypeStr),
                    argVersionStr, fieldNullability);
        }

        if (tiSource.isAttributeNativeType(tr)) {
            String fqName = tiSource.getAreaPackage(StdStrings.MAL)
                    + "mal.structures." + tr.getName() + "List";
            return new FieldInfo(tr, fieldName, fieldComment,
                    tr.getName() + "List", fqName, false,
                    fqName + ".SHORT_FORM", argVersionStr, fieldNullability);
        }

        if (GeneratorBase.isObjectRef(argTypeStr)) {
            return new FieldInfo(tr, fieldName, fieldComment, tr.getName() + "List",
                    "org.ccsds.moims.mo.mal.structures.ObjectRefList", false,
                    getTypeShortForm(tiSource, tr, "ObjectRefList"),
                    argVersionStr, fieldNullability);
        }

        return new FieldInfo(tr, fieldName, fieldComment,
                tr.getName() + "List", argTypeStr + "List", false,
                getTypeShortForm(tiSource, tr, argTypeStr + "List"),
                argVersionStr, fieldNullability);
    }

    /**
     * Converts a type reference from the XML format to the internal format.
     *
     * @param tiSource Source of generator specific type information.
     * @param ttr The XML type reference.
     * @return the converted type information.
     */
    public static FieldInfo convertTypeReference(TypeInformation tiSource, TypeRef ttr) {
        if (ttr == null) {
            return null;
        }

        if (!ttr.isField()) {
            return convertTypeReference(tiSource, ttr.getTypeRef(), null, null, true);
        }

        String fieldName = ttr.getFieldRef().getName();
        String fieldComment = ttr.getFieldRef().getComment();
        boolean fieldNullability = ttr.getFieldRef().isCanBeNull();
        TypeReference tr = ttr.getFieldRef().getType();
        return convertTypeReference(tiSource, tr, fieldName, fieldComment, fieldNullability);
    }

    /**
     * Converts a list of type references from the XML format to the internal
     * format.
     *
     * @param tiSource Source of generator specific type information.
     * @param trList The XML type reference list.
     * @return the converted type information.
     */
    public static List<FieldInfo> convertTypeReferences(TypeInformation tiSource, List<TypeRef> trList) {
        if (trList == null) {
            return null;
        }

        List<FieldInfo> tiList = new ArrayList<>(trList.size());
        for (TypeRef tr : trList) {
            tiList.add(convertTypeReference(tiSource, tr));
        }
        return tiList;
    }

    /**
     * Creates an XML format type reference.
     *
     * @param area Type area.
     * @param service Type service, may be null.
     * @param name Type name.
     * @param isList True if type is a list.
     * @return the new XML type information.
     */
    public static TypeReference createTypeReference(String area,
            String service, String name, boolean isList) {
        TypeReference tr = new TypeReference();
        tr.setArea(area);
        tr.setService(service);
        tr.setName(name);
        tr.setList(isList);
        return tr;
    }

    /**
     * Creates a short name for a type based on the long name.
     *
     * @param nameSeparator the separator for the long name.
     * @param longName The long name of the type.
     * @return the short name.
     */
    public static String shortTypeName(String nameSeparator, String longName) {
        if (longName == null) {
            return null;
        }

        if (longName.contains(nameSeparator)) {
            longName = longName.substring(longName.lastIndexOf(nameSeparator) + nameSeparator.length());
        }
        if (longName.contains("*")) {
            longName = longName.substring(0, longName.length() - 1);
        }
        return longName;
    }

    /**
     * Returns the short form field for a type.
     *
     * @param tiSource Generator type information source.
     * @param type The type reference.
     * @param targetType The type.
     * @return The short form value.
     */
    public static String getTypeShortForm(TypeInformation tiSource,
            TypeReference type, String targetType) {
        if (type == null) {
            return null;
        }

        if (StdStrings.XML.equals(type.getArea())) {
            return "\"" + type.getService() + ":" + type.getName() + "\"";
        }

        if (!type.isList() && tiSource.isAttributeType(type)) {
            return tiSource.convertToNamespace(
                    tiSource.getAreaPackage(StdStrings.MAL)
                    + tiSource.convertToNamespace("mal.structures.Attribute.")
                    + type.getName().toUpperCase() + "_SHORT_FORM");
        }

        if (!type.isList() && type.getName().contains("ObjectRef")) {
            return tiSource.convertToNamespace(tiSource.getAreaPackage(StdStrings.MAL)
                    + "mal.structures.ObjectRef.OBJECTREF_SHORT_FORM");
        }

        if (type.isList() && type.getName().contains("ObjectRef")) {
            return tiSource.convertToNamespace(tiSource.getAreaPackage(StdStrings.MAL)
                    + "mal.structures.ObjectRefList.SHORT_FORM");
        }

        if (tiSource.convertToNamespace("org.ccsds.moims.mo.mal.structures.Element").equals(targetType)) {
            return null;
        }

        return tiSource.convertToNamespace(targetType + ".SHORT_FORM");
    }

    /**
     * Converts an XML any field into a list of types.
     *
     * @param any the XML any field.
     * @return the convert type list.
     */
    public static List<TypeRef> getTypeListViaXSDAny(Object any) {
        if (any == null) {
            return null;
        }

        if (any instanceof List) {
            List li = (List) any;
            ArrayList<TypeRef> list = new ArrayList<>(li.size());
            for (Object e : li) {
                list.add(getTypeViaXSDAny(e));
            }
            return list;
        } else {
            throw new IllegalArgumentException(
                    "Unexpected type in message body of : " + any.getClass().getSimpleName());
        }
    }

    /**
     * Converts an XML any field into a type reference.
     *
     * @param any the XML any field.
     * @return the converted type.
     */
    public static TypeRef getTypeViaXSDAny(Object any) {
        if (any == null) {
            return null;
        }

        if (any instanceof JAXBElement) {
            JAXBElement re = (JAXBElement) any;
            if (re.getValue() instanceof TypeReference) {
                return new TypeRef((TypeReference) re.getValue());
            } else if (re.getValue() instanceof NamedElementReferenceWithCommentType) {
                return new TypeRef((NamedElementReferenceWithCommentType) re.getValue());
            } else {
                throw new IllegalArgumentException(
                        "Unexpected type in message body of : " + re.getValue().getClass().getSimpleName());
            }
        } else if (any instanceof Element) {
            Element re = (Element) any;
            String stype = re.getAttribute("type");

            if (!"".equals(stype)) {
                String uri = stype.substring(0, stype.indexOf(':'));
                String type = stype.substring(uri.length() + 1);
                boolean isList = false;
                String smaxOccurrs = re.getAttribute("maxOccurs");
                if (!"".equals(smaxOccurrs)) {
                    int maxOccurrs = Integer.parseInt(smaxOccurrs);
                    if (maxOccurrs > 1) {
                        isList = true;
                    }
                }
                TypeReference newTr = new TypeReference();
                newTr.setArea(StdStrings.XML);
                newTr.setService(re.lookupNamespaceURI(uri));
                newTr.setName(type);
                newTr.setList(isList);

                NamedElementReferenceWithCommentType newField = new NamedElementReferenceWithCommentType();
                newField.setName(re.getAttribute("name"));
                newField.setType(newTr);
                newField.setCanBeNull(true);

                return new TypeRef(newField);
            } else {
                throw new IllegalArgumentException(
                        "Unexpected XML type in message body of : " + re);
            }
        } else {
            throw new IllegalArgumentException(
                    "Unexpected type in message body of : " + any.getClass().getSimpleName());
        }
    }
}
