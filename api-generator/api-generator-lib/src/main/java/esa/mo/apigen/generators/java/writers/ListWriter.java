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
package esa.mo.apigen.generators.java.writers;

import esa.mo.apigen.generators.java.JavaClassBuilder;
import esa.mo.apigen.generators.java.JavaMethodBuilder;
import esa.mo.apigen.generators.java.JavaNaming;
import esa.mo.apigen.generators.java.JavaSource;
import esa.mo.apigen.generators.java.JavaTypes;
import esa.mo.apigen.generators.java.ShortForm;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.AttributeType;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.TypeDefinition;

/**
 * Writes the list class that accompanies every concrete type.
 * <p>
 * A list's short form part is the negation of its element's, in three bytes - so a type
 * with part 12 has a list with part {@code 0xFFFFFF - 11}.
 */
public final class ListWriter {


    private ListWriter() {
    }

    /**
     * @return the source of the list class, or null if the type has no list.
     */
    public static String write(Area area, Service service, TypeDefinition type) {
        Integer shortFormPart = shortFormPartOf(type);
        if (shortFormPart == null) {
            return type instanceof CompositeType
                    ? writeHeterogeneous(area, service, type) : null;
        }
        String name = type.getName();
        String listName = name + "List";
        String pkg = service == null
                ? JavaNaming.packageOf(area, JavaNaming.STRUCTURES)
                : JavaNaming.packageOf(service, JavaNaming.STRUCTURES);
        boolean isNative = JavaTypes.isNative(area.getName(), name);
        // A native attribute type is a plain Java type: the list holds Boolean, not a
        // generated class.
        String element = isNative ? JavaTypes.nativeName(name) : pkg + "." + name;
        String fq = pkg + "." + name;
        long shortForm = ShortForm.of(area, service, 0xFFFFFFL & -shortFormPart.longValue());

        JavaClassBuilder clazz = JavaClassBuilder.named(listName).inPackage(pkg).asFinal()
                .extending("java.util.ArrayList<" + element + ">")
                .implementing(JavaNaming.MAL_STRUCTURES + "HomogeneousList<" + element + ">")
                .comment("List class for " + name + ".");
        JavaSource out = clazz.open();
        ShortForm.writeIdentity(out, shortForm);

        JavaMethodBuilder.constructor(listName)
                .comment("Default constructor for " + listName + ".")
                .write(out);

        JavaMethodBuilder.constructor(listName)
                .comment("Constructor that initialises the capacity of the list.")
                .argument("int", "initialCapacity", "The required initial capacity.")
                .line("super(initialCapacity);")
                .write(out);

        JavaMethodBuilder.constructor(listName)
                .comment("Constructor that uses an ArrayList for initialization.")
                .argument("java.util.ArrayList<" + element + ">", "elementList",
                        "The ArrayList that is used for initialization.")
                .line("for(" + element + " element : elementList) {")
                .line("    this.add(element);")
                .line("}")
                .write(out);

        JavaMethodBuilder.named("add").asOverride().returns("boolean", null)
                .argument(element, "element", null)
                .line("if (element == null) {")
                .line("    throw new IllegalArgumentException(\"The added argument cannot "
                        + "be null!\");")
                .line("}")
                .line("return super.add(element);")
                .write(out);

        JavaMethodBuilder.named("createElement").asOverride()
                .returns(JavaNaming.MAL_STRUCTURES + "Element", null)
                .line("return new " + listName + "();")
                .write(out);

        JavaMethodBuilder createTyped = JavaMethodBuilder.named("createTypedElement")
                .asOverride()
                .returns(JavaNaming.MAL_STRUCTURES + "Element", null);
        if (isNative) {
            // Nothing can be instantiated for a Java built-in, so the list hands back a
            // Union carrying its own type id instead.
            createTyped.line(JavaNaming.MAL + "TypeId typeId = this.getTypeId();");
            createTyped.line("return new Union(typeId.generateTypeIdPositive());");
        } else {
            createTyped.line("return " + typedElement(type, fq) + ";");
        }
        createTyped.write(out);

        JavaMethodBuilder.named("encode").asOverride()
                .argument(JavaNaming.MAL + "MALEncoder", "encoder", null)
                .throwing(JavaNaming.MAL + "MALException", null)
                .line("encoder.encodeHomogeneousList(this);")
                .write(out);

        JavaMethodBuilder.named("decode").asOverride()
                .returns(JavaNaming.MAL_STRUCTURES + "Element", null)
                .argument(JavaNaming.MAL + "MALDecoder", "decoder", null)
                .throwing(JavaNaming.MAL + "MALException", null)
                .line("decoder.decodeHomogeneousList(this);")
                .line("return this;")
                .write(out);

        JavaMethodBuilder.named("getTypeId").asOverride()
                .returns(JavaNaming.MAL + "TypeId", null)
                .line("return TYPE_ID;")
                .write(out);

        return clazz.close();
    }

    /**
     * An abstract composite has no short form and cannot be instantiated, so its list
     * holds whatever extends it and checks the type on the way in.
     */
    private static String writeHeterogeneous(Area area, Service service, TypeDefinition type) {
        String name = type.getName();
        String listName = name + "List";
        String pkg = service == null
                ? JavaNaming.packageOf(area, JavaNaming.STRUCTURES)
                : JavaNaming.packageOf(service, JavaNaming.STRUCTURES);

        JavaClassBuilder clazz = JavaClassBuilder.named(listName).inPackage(pkg).asFinal()
                .extending(JavaNaming.MAL + "structures.HeterogeneousList")
                .comment("List class for " + name + ".");
        JavaSource out = clazz.open();
        JavaMethodBuilder.constructor(listName)
                .comment("Default constructor for " + listName + ".")
                .write(out);

        JavaMethodBuilder.named("add").asOverride().returns("boolean", null)
                .argument(JavaNaming.MAL_STRUCTURES + "Element", "element", null)
                .line("if (element != null && !(element instanceof " + name + ")) {")
                .line("    throw new java.lang.ClassCastException(\"The added element does "
                        + "not extend the type: " + name + "\");")
                .line("}")
                .line("return super.add(element);")
                .write(out);
        return clazz.close();
    }

    /**
     * An enumeration has no no-argument value to hand back, so the list offers its first
     * singleton instead.
     */
    private static String typedElement(TypeDefinition type, String fq) {
        if (type instanceof EnumerationType) {
            EnumerationType enumeration = (EnumerationType) type;
            if (!enumeration.getItems().isEmpty()) {
                return fq + "." + enumeration.getItems().get(0).getValue();
            }
        }
        return "new " + fq + "()";
    }

    private static Integer shortFormPartOf(TypeDefinition type) {
        if (type instanceof EnumerationType) {
            return ((EnumerationType) type).getShortFormPart();
        }
        if (type instanceof AttributeType) {
            return ((AttributeType) type).getShortFormPart();
        }
        if (type instanceof CompositeType) {
            return ((CompositeType) type).getShortFormPart();
        }
        return null;
    }
}
