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
package esa.mo.tools.stubgen.java;

import esa.mo.tools.stubgen.GeneratorBase;
import esa.mo.tools.stubgen.GeneratorLangs;
import esa.mo.tools.stubgen.StubUtils;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.TypeReference;

/**
 *
 */
public class JavaCompositeFields {

    private final GeneratorLangs generator;

    public JavaCompositeFields(GeneratorLangs generator) {
        this.generator = generator;
    }

    public CompositeField createCompositeElementsDetails(LanguageWriter file, boolean checkType,
            String fieldName, TypeReference elementType, boolean isStructure, boolean canBeNull, String comment) {
        CompositeField ele;

        String typeName = elementType.getName();
        boolean isObjectRef = GeneratorBase.isObjectRef(typeName);

        if (elementType.isList()) {
            String fqTypeName;
            
            if (generator.isAttributeNativeType(elementType)) {
                fqTypeName = generator.createElementType(file, StdStrings.MAL, null, typeName + "List");
            } else {
                if (isObjectRef) {
                    //String temp = generator.createElementType(file, elementType, true);
                    // String lastCharRemoved = temp.substring(0, temp.length() - 1); // Strip the last '>'
                    // fqTypeName = lastCharRemoved + "List>";
                    fqTypeName = "org.ccsds.moims.mo.mal.structures.ObjectRefList";
                } else {
                    fqTypeName = generator.createElementType(file, elementType, true) + "List";
                }
            }

            String newCall = null;
            String encCall = null;
            if (!generator.isAbstract(elementType)) {
                newCall = "new " + fqTypeName + "()";
                encCall = StdStrings.ELEMENT;
            }

            ele = new CompositeField(fqTypeName, elementType, fieldName, elementType.isList(),
                    canBeNull, false, encCall, "(" + fqTypeName + ") ",
                    StdStrings.ELEMENT, true, newCall, comment);
        } else if (generator.isAttributeType(elementType)) {
            AttributeTypeDetails details = generator.getAttributeDetails(elementType);
            String fqTypeName = generator.createElementType(file, elementType, isStructure);
            ele = new CompositeField(details.getTargetType(), elementType, fieldName, elementType.isList(),
                    canBeNull, false, typeName, "", typeName, false, "new " + fqTypeName + "()", comment);
        } else {
            TypeReference elementTypeIndir = elementType;

            // have to work around the fact that JAXB does not replicate the XML type name into Java in all cases
            if (StdStrings.XML.equalsIgnoreCase(elementType.getArea())) {
                elementTypeIndir = TypeUtils.createTypeReference(elementType.getArea(),
                        elementType.getService(), StubUtils.preCap(elementType.getName()), elementType.isList());
            }

            String fqTypeName = generator.createElementType(file, elementTypeIndir, isStructure);

            if (generator.isEnum(elementType)) {
                EnumerationType typ = generator.getEnum(elementType);
                String firstEle = fqTypeName + "." + typ.getItem().get(0).getValue();
                ele = new CompositeField(fqTypeName, elementType, fieldName, elementType.isList(),
                        canBeNull, false, StdStrings.ELEMENT, "(" + fqTypeName + ") ",
                        StdStrings.ELEMENT, true, firstEle, comment);
            } else if (StdStrings.ATTRIBUTE.equals(typeName)) {
                ele = new CompositeField(fqTypeName, elementType, fieldName, elementType.isList(),
                        canBeNull, false, StdStrings.ATTRIBUTE, "(" + fqTypeName + ") ",
                        StdStrings.ATTRIBUTE, false, "", comment);
            } else if (StdStrings.ELEMENT.equals(typeName)) {
                ele = new CompositeField(fqTypeName, elementType, fieldName, elementType.isList(),
                        canBeNull, false, StdStrings.ELEMENT, "(" + fqTypeName + ") ",
                        StdStrings.ELEMENT, false, "", comment);
            } else {
                ele = new CompositeField(fqTypeName, elementType, fieldName, elementType.isList(),
                        canBeNull, false, StdStrings.ELEMENT, "(" + fqTypeName + ") ",
                        StdStrings.ELEMENT, true, "new " + fqTypeName + "()", comment);
            }
        }

        return ele;
    }
}
