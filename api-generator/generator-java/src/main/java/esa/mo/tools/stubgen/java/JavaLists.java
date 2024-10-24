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

import esa.mo.tools.stubgen.GeneratorLangs;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class JavaLists {

    private final GeneratorLangs generator;

    public JavaLists(GeneratorLangs generator) {
        this.generator = generator;
    }

    /**
     * Creates a list for an abstract type.
     *
     * @param folder The base folder to create the list in.
     * @param area The Area of the list.
     * @param service The service of the list.
     * @param srcTypeName The name of the element in the list.
     * @throws IOException if there is a problem writing the file.
     */
    public void createHeterogeneousListClass(File folder, String area,
            String service, String srcTypeName) throws IOException {
        TypeReference srcType = new TypeReference();
        srcType.setArea(area);
        if (service != null) {
            srcType.setService(service);
        }

        srcType.setName(srcTypeName);
        String listName = srcTypeName + "List";

        JavaClassWriter file = (JavaClassWriter) generator.createClassFile(folder, listName);
        file.addPackageStatement(area, service, generator.getConfig().getStructureFolder());

        file.addClassOpenStatement(listName, true, false,
                "org.ccsds.moims.mo.mal.structures.HeterogeneousList",
                null, "List class for " + srcTypeName + ".");

        file.addConstructorDefault(listName); // create blank constructor

        TypeReference argElement = new TypeReference();
        argElement.setArea(StdStrings.MAL);
        argElement.setName(StdStrings.ELEMENT);

        List<CompositeField> argList = new LinkedList<>();
        argList.add(generator.createCompositeElementsDetails(file, true, "element",
                argElement, true, true, "The element to be added."));
        TypeReference type = new TypeReference();
        type.setName("boolean");
        CompositeField rtype = generator.createCompositeElementsDetails(file, false, "element",
                type, false, true, "List element.");

        MethodWriter method = file.addMethodOpenStatementOverride(rtype, "add", argList, null);
        method.addLine("if (element != null && !(element instanceof " + srcTypeName + ")) {", false);
        method.addLine("    throw new java.lang.ClassCastException(\"The added element does not extend the type: " + srcTypeName + "\")");
        method.addLine("}", false);
        method.addLine("return super.add(element)");
        method.addMethodCloseStatement();

        file.addClassCloseStatement();
        file.flush();
    }

    /**
     * Creates a list for an abstract type.
     *
     * @param folder The base folder to create the list in.
     * @param area The Area of the list.
     * @param service The service of the list.
     * @param srcTypeName The name of the element in the list.
     * @param shortFormPart The short form part of the contained element.
     * @throws IOException if there is a problem writing the file.
     */
    public void createHomogeneousListClass(File folder, AreaType area, ServiceType service,
            String srcTypeName, Long shortFormPart) throws IOException {
        String listName = srcTypeName + "List";

        TypeReference srcType = new TypeReference();
        srcType.setName(srcTypeName);
        srcType.setArea(area.getName());
        if (service != null) {
            srcType.setService(service.getName());
        }

        ClassWriter file = generator.createClassFile(folder, listName);
        file.addPackageStatement(area.getName(), service == null ? null : service.getName(), generator.getConfig().getStructureFolder());

        CompositeField elementType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false),
                true, true, null);
        String fqSrcTypeName = generator.createElementType(area, service, srcTypeName);

        String sElement = "org.ccsds.moims.mo.mal.structures.Homogeneous";
        file.addClassOpenStatement(listName, true, false, "java.util.ArrayList<" + fqSrcTypeName + ">",
                sElement + "List<" + fqSrcTypeName + ">", "List class for " + srcTypeName + ".");

        CompositeField listElement = generator.createCompositeElementsDetails(file, true, null,
                srcType, true, true, "List element.");

        generator.addTypeShortFormDetails(file, area, service, -shortFormPart);

        // create blank constructor
        file.addConstructorDefault(listName);

        // create initial size contructor
        MethodWriter method = file.addConstructor(StdStrings.PUBLIC, listName,
                generator.createCompositeElementsDetails(file, false, "initialCapacity",
                        TypeUtils.createTypeReference(null, null, "int", false),
                        false, false, "The required initial capacity."),
                true, null, "Constructor that initialises the capacity of the list.", null);
        method.addMethodCloseStatement();

        // create contructor with ArrayList 
        method = file.addConstructor(StdStrings.PUBLIC, listName,
                generator.createCompositeElementsDetails(file, false, "elementList",
                        TypeUtils.createTypeReference(null, null, "java.util.ArrayList<" + fqSrcTypeName + ">", false),
                        false, false, "The ArrayList that is used for initialization."),
                false, null, "Constructor that uses an ArrayList for initialization.", null);
        method.addLine("for(" + fqSrcTypeName + " element : elementList) {", false);
        method.addLine("    this.add(element)");
        method.addLine("}", false);
        method.addMethodCloseStatement();

        List<CompositeField> argList = new LinkedList<>();
        argList.add(generator.createCompositeElementsDetails(file, true, "element",
                srcType, true, true, "List element."));
        TypeReference type = new TypeReference();
        type.setName("boolean");
        CompositeField rtype = generator.createCompositeElementsDetails(file, false, "element",
                type, false, true, "List element.");

        method = file.addMethodOpenStatementOverride(rtype, "add", argList, null);
        method.addLine("if (element == null) {", false);
        method.addLine("    throw new IllegalArgumentException(\"The added argument cannot be null!\")");
        method.addLine("}", false);

        method.addLine("return super.add(element)");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatementOverride(elementType, "createElement", null, null);
        method.addLine("return new " + listName + "()");
        method.addMethodCloseStatement();

        method = file.addMethodOpenStatementOverride(elementType, "createTypedElement", null, null);

        // Wrap in Union if needed:
        if (listElement.getNewCall().contains("structures")) {
            method.addLine("return " + listElement.getNewCall());
        } else {
            method.addLine("org.ccsds.moims.mo.mal.TypeId typeId = this.getTypeId()");
            String dummyUnion = "new Union(typeId.generateTypeIdPositive())";
            method.addLine("return " + dummyUnion);
        }
        method.addMethodCloseStatement();

        // create encode method
        method = generator.encodeMethodOpen(file);
        method.addLine("encoder.encodeHomogeneousList(this)");
        method.addMethodCloseStatement();

        // create decode method
        method = generator.decodeMethodOpen(file, elementType);
        method.addLine("decoder.decodeHomogeneousList(this)");
        method.addLine("return this");
        method.addMethodCloseStatement();

        generator.addTypeIdGetterMethod(file, area, service);

        file.addClassCloseStatement();
        file.flush();
    }
}
