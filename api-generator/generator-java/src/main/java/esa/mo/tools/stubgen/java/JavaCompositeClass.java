/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
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
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class JavaCompositeClass {

    private final GeneratorLangs generator;

    public JavaCompositeClass(GeneratorLangs generator) {
        this.generator = generator;
    }

    public void createCompositeClass(File folder, AreaType area, ServiceType service, CompositeType composite) throws IOException {
        String className = composite.getName();
        ClassWriter file = generator.createClassFile(folder, className);
        String parentClass = null;
        TypeReference parentType = null;
        String parentInterface = generator.createElementType(StdStrings.MAL, null, StdStrings.COMPOSITE);

        // Check if it is an extended Composite Type:
        if (composite.getExtends() != null) {
            parentType = composite.getExtends().getType();

            if (!StdStrings.MAL.equals(composite.getExtends().getType().getArea())
                    && !StdStrings.COMPOSITE.equals(composite.getExtends().getType().getName())) {
                parentClass = generator.createElementType(parentType, true);
                parentInterface = null;
            }

            // Check if it is an MO Object:
            if (StdStrings.MAL.equals(composite.getExtends().getType().getArea())
                    && StdStrings.MOOBJECT.equals(composite.getExtends().getType().getName())) {
                parentClass = generator.createElementType(parentType, true);
                parentInterface = null;
            }
        }

        file.addPackageStatement(area.getName(), service == null ? null : service.getName(), generator.getConfig().getStructureFolder());

        CompositeField elementType = generator.createCompositeElementsDetails(file, false, "return",
                TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.ELEMENT, false),
                true, true, null);

        List<CompositeField> compElements = generator.createCompositeElementsList(file, composite);
        List<CompositeField> superCompElements = generator.createCompositeSuperElementsList(file, parentType);

        boolean abstractComposite = (composite.getShortFormPart() == null);
        file.addClassOpenStatement(className, !abstractComposite, abstractComposite,
                parentClass, parentInterface, composite.getComment());
        String fqName = generator.createElementType(area.getName(), service == null ? null : service.getName(), className);

        if (!abstractComposite) {
            generator.addTypeShortFormDetails(file, area, service, composite.getShortFormPart());
        }

        // Create the composite fields
        if (!compElements.isEmpty()) {
            for (CompositeField element : compElements) {
                file.addClassVariable(false, false, StdStrings.PRIVATE, element, false, (String) null);
            }
        }

        // create blank constructor
        file.addConstructorDefault(className);

        // if we or our parents have attributes then we need a typed constructor
        if (!compElements.isEmpty() || !superCompElements.isEmpty()) {
            List<CompositeField> superArgs = new LinkedList<>();
            List<CompositeField> args = new LinkedList<>();
            List<CompositeField> superArgsNonNullable = new LinkedList<>();
            List<CompositeField> argsNonNullable = new LinkedList<>();

            for (CompositeField element : superCompElements) {
                superArgs.add(element);
                args.add(element);

                if (!element.isCanBeNull()) {
                    superArgsNonNullable.add(element);
                    argsNonNullable.add(element);
                }
            }

            args.addAll(compElements);

            for (CompositeField element : compElements) {
                if (!element.isCanBeNull()) {
                    argsNonNullable.add(element);
                }
            }

            // Creates constructor with all arguments
            MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, args,
                    superArgs, null, "Constructor that initialises the values of the structure.", null);

            for (CompositeField element : compElements) {
                String call = generator.createMethodCall("this." + element.getFieldName() + " = " + element.getFieldName());
                method.addLine(call);
            }

            method.addMethodCloseStatement();

            // Add a contructor that has only the non-nullable fields. Sets the nullable fields to null.
            boolean hasNonNullable = !argsNonNullable.isEmpty() || !superArgsNonNullable.isEmpty();
            boolean allArgsNonNullable = argsNonNullable.size() == args.size(); // All args are non-nullable?
            // Note: If all method arguments are non-nullable, then the 
            // contructor will look the same as the one with all arguments. So, 
            // same type signature and therefore that needs to be avoided.
            if (hasNonNullable && !allArgsNonNullable) {
                MethodWriter method2 = file.addConstructor(StdStrings.PUBLIC, className,
                        argsNonNullable, superArgsNonNullable, null,
                        "Constructor that initialises the non-nullable values of the structure.", null);

                for (CompositeField element : compElements) {
                    String ending = (!element.isCanBeNull()) ? element.getFieldName() : "null";
                    String call = generator.createMethodCall("this." + element.getFieldName() + " = " + ending);
                    method.addLine(call);
                }

                method2.addMethodCloseStatement();
            }

            // create copy constructor
            if (generator.supportsToValue && !abstractComposite) {
                file.addConstructorCopy(fqName, compElements);
            }
        }

        if (!abstractComposite) {
            MethodWriter method = file.addMethodOpenStatementOverride(elementType, "createElement", null, null);
            method.addLine("return new " + fqName + "()");
            method.addMethodCloseStatement();
        }

        // add getters and setters
        for (CompositeField element : compElements) {
            GeneratorLangs.addGetter(file, element, null);
            GeneratorLangs.addSetter(file, element, null);
        }

        // create equals method
        if (generator.supportsEquals) {
            CompositeField boolType = generator.createCompositeElementsDetails(file, false, "return",
                    TypeUtils.createTypeReference(null, null, "boolean", false),
                    false, true, "return value");
            CompositeField intType = generator.createCompositeElementsDetails(file, false, "return",
                    TypeUtils.createTypeReference(null, null, "int", false),
                    false, true, "return value");
            CompositeField objType = generator.createCompositeElementsDetails(file, false, "obj",
                    TypeUtils.createTypeReference(null, null, "Object", false),
                    false, true, "The object to compare with.");

            MethodWriter method = file.addMethodOpenStatementOverride(boolType, "equals", Arrays.asList(objType), null);
            method.addLine("if (obj instanceof " + className + ") {", false);

            if (null != parentClass) {
                method.addLine("    if (! super.equals(obj)) {", false);
                method.addLine("        return false");
                method.addLine("    }", false);
            }
            if (!compElements.isEmpty()) {
                method.addLine("    " + className + " other = (" + className + ") obj");
                for (CompositeField element : compElements) {
                    method.addLine("    if (" + element.getFieldName() + " == null) {", false);
                    method.addLine("        if (other." + element.getFieldName() + " != null) {", false);
                    method.addLine("            return false");
                    method.addLine("        }", false);
                    method.addLine("    } else {", false);
                    method.addLine("        if (! " + element.getFieldName() + ".equals(other." + element.getFieldName() + ")) {", false);
                    method.addLine("            return false");
                    method.addLine("        }", false);
                    method.addLine("    }", false);
                }
            }
            method.addLine("    return true");
            method.addLine("}", false);
            method.addLine("return false");
            method.addMethodCloseStatement();

            method = file.addMethodOpenStatementOverride(intType, "hashCode", null, null);
            String line = (parentClass != null) ? "int hash = super.hashCode()" : "int hash = 7";
            method.addLine(line);

            for (CompositeField element : compElements) {
                method.addLine("hash = 83 * hash + (" + element.getFieldName() + " != null ? " + element.getFieldName() + ".hashCode() : 0)");
            }
            method.addLine("return hash");
            method.addMethodCloseStatement();
        }

        // create toString method
        if (generator.supportsToString) {
            CompositeField strType = generator.createCompositeElementsDetails(file, false, "return",
                    TypeUtils.createTypeReference(null, null, "_String", false),
                    false, true, "return value");

            MethodWriter method = file.addMethodOpenStatementOverride(strType, "toString", null, null);
            method.addLine("StringBuilder buf = new StringBuilder()");
            method.addLine("buf.append(\"(" + className + ": \")");

            String prefixSeparator = "";

            if (parentClass != null) {
                method.addLine("buf.append(super.toString())");
                prefixSeparator = ", ";
            }
            for (CompositeField element : compElements) {
                StringBuilder str = new StringBuilder();
                str.append("buf.append(\"").append(prefixSeparator).append(element.getFieldName());
                str.append("=\")").append(".append(").append(element.getFieldName()).append(")");
                method.addLine(str.toString());
                prefixSeparator = ", ";
            }
            method.addLine("buf.append(')')");
            method.addLine("return buf.toString()");
            method.addMethodCloseStatement();
        }

        // create getMALValue method
        /*
        if (generator.supportsToValue && !abstractComposite) {
            generator.addCompositeCloneMethod(file, fqName);
        }
         */
        // create encode method
        MethodWriter method = generator.encodeMethodOpen(file);
        if (parentClass != null) {
            method.addSuperMethodStatement("encode", "encoder");
        }

        // Add the if condition to check if there are null fields for non-nullable fields!
        for (int i = 0; i < compElements.size(); i++) {
            CompositeField element = compElements.get(i);
            String fieldName = element.getFieldName();

            if (!element.isCanBeNull()) {
                method.addLine("if (" + fieldName + " == null) {", false);
                method.addLine("    throw new org.ccsds.moims.mo.mal.MALException(\"The field '" + fieldName + "' cannot be null!\")");
                method.addLine("}", false);
            }
        }

        for (CompositeField element : compElements) {
            boolean isAbstract = generator.isAbstract(element.getTypeReference()) && !element.getTypeReference().getName().contentEquals(StdStrings.ATTRIBUTE);
            String canBeNullStr = element.isCanBeNull() ? "Nullable" : "";
            String fieldName = element.getFieldName();

            if (isAbstract) {
                if (element.isList()) { // Abstract List?
                    // The Abstract Lists do not not need an SPF because we know what we will get!
                    method.addLine(generator.createMethodCall("encoder.encode" + canBeNullStr + "Element(" + fieldName + ")"));
                } else {
                    method.addLine(generator.createMethodCall("encoder.encode" + canBeNullStr + "AbstractElement(" + fieldName + ")"));
                }
            } else {
                if (element.getEncodeCall() != null) {
                    method.addLine(generator.createMethodCall("encoder.encode" + canBeNullStr + element.getEncodeCall() + "(" + fieldName + ")"));
                } else {
                    // This is when the Element is set as the abstract Attribute type
                    method.addLine(generator.createMethodCall("encoder.encode" + canBeNullStr + "Element(" + fieldName + ")"));
                }
            }
        }
        method.addMethodCloseStatement();

        // create decode method
        method = generator.decodeMethodOpen(file, elementType);
        if (parentClass != null) {
            method.addSuperMethodStatement("decode", "decoder");
        }
        for (CompositeField element : compElements) {
            boolean isAbstract = generator.isAbstract(element.getTypeReference())
                    && !element.getTypeReference().getName().contentEquals(StdStrings.ATTRIBUTE);
            String canBeNullStr = element.isCanBeNull() ? "Nullable" : "";
            String castString = element.getDecodeCast();

            if (isAbstract) {
                if (element.isList()) { // Abstract List?
                    castString = castString.replaceAll("\\.ElementList", ".HeterogeneousList");
                    // Strip the parenthesis around the cast: "(abc) " -> "abc"
                    // Note: Yes, the string has a space at the end... that's why we have: length() - 2
                    String classPath = castString.substring(1, castString.length() - 2);
                    // Change the type to HeterogeneousList if it is ElementList
                    method.addLine(element.getFieldName() + " = " + castString
                            + generator.createMethodCall("decoder.decode" + canBeNullStr
                                    + "Element(new " + classPath + "())"));
                } else {
                    method.addLine(element.getFieldName() + " = " + castString
                            + generator.createMethodCall("decoder.decode" + canBeNullStr + "AbstractElement()"));
                }
            } else {
                // Needs the "." before the AttributeList because of the NullableAttributeList
                if (castString.contains(".AttributeList")) {
                    // This is when the Element is set as the abstract AttributeList type
                    String attNew = "new org.ccsds.moims.mo.mal.structures.AttributeList()";
                    method.addLine(element.getFieldName() + " = " + castString
                            + generator.createMethodCall("decoder.decode" + canBeNullStr + element.getDecodeCall() + "(" + attNew + ")"));
                } else {
                    method.addLine(element.getFieldName() + " = " + castString
                            + generator.createMethodCall("decoder.decode" + canBeNullStr + element.getDecodeCall()
                                    + "(" + (element.isDecodeNeedsNewCall() ? element.getNewCall() : "") + ")"));
                }
            }
        }
        method.addLine("return this");
        method.addMethodCloseStatement();

        if (!abstractComposite) {
            generator.addTypeIdGetterMethod(file, area, service);
        }

        file.addClassCloseStatement();
        file.flush();

        generator.createListClass(folder, area, service, className, abstractComposite, composite.getShortFormPart());
    }
}
