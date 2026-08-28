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
package esa.mo.apigen.generators.java;

import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.types.AttributeType;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;

/**
 * Renders a type reference as the Java type that represents it.
 */
public final class JavaTypeName {

    private JavaTypeName() {
    }

    /**
     * @return the Java type for a field or parameter of this type.
     */
    public static String of(MOModel model, TypeRef reference) {
        if (reference == null) {
            return "java.lang.Object";
        }
        // The older spelling of an object reference, ObjectRef(Product), means the same as
        // objectRef="true"; unwrapping here lets everything below see just the one form.
        TypeRef ref = reference.unwrapped();
        if (ref.isList()) {
            // A list of object references is a list of references, not of the target type.
            return ref.isObjectRef()
                    ? JavaNaming.MAL_STRUCTURES + "ObjectRefList" : qualifiedList(ref);
        }
        if (ref.isObjectRef()) {
            return JavaNaming.MAL_STRUCTURES + "ObjectRef<" + qualified(ref) + ">";
        }
        if (isNativeAttribute(model, ref)) {
            return JavaTypes.nativeName(ref.getName());
        }
        return qualified(ref);
    }

    /**
     * @return the expression that creates an empty instance, for decoding.
     */
    public static String newInstance(MOModel model, TypeRef ref) {
        // An enumeration cannot be constructed empty, so its first singleton stands in.
        if (ref != null && !ref.isList() && !ref.isObjectRef()) {
            TypeDefinition definition = model.resolve(ref);
            if (definition instanceof EnumerationType) {
                EnumerationType enumeration = (EnumerationType) definition;
                if (!enumeration.getItems().isEmpty()) {
                    return qualified(ref) + "." + enumeration.getItems().get(0).getValue();
                }
            }
        }
        return "new " + of(model, ref) + "()";
    }

    /**
     * @return the fully qualified name of the type itself, ignoring list and reference
     * wrapping.
     */
    public static String qualified(TypeRef ref) {
        StringBuilder pkg = new StringBuilder("org.ccsds.moims.mo.");
        pkg.append(ref.getArea().toLowerCase());
        if (ref.getService() != null) {
            pkg.append('.').append(ref.getService().toLowerCase());
        }
        pkg.append('.').append(JavaNaming.STRUCTURES).append('.')
                .append(JavaTypes.className(ref.getArea(), ref.getName()));
        return pkg.toString();
    }

    /**
     * Returns the Java type of a list of this type. Usually the element's name with
     * "List" appended, but a list of MAL Elements is the hand-written HeterogeneousList
     * rather than a generated ElementList.
     */
    private static String qualifiedList(TypeRef ref) {
        String listName = JavaTypes.className(ref.getArea(), ref.getName() + "List");
        int lastDot = qualified(ref).lastIndexOf('.');
        return qualified(ref).substring(0, lastDot + 1) + listName;
    }

    /**
     * Returns true if the reference names a MAL attribute type that is represented by a
     * plain Java type.
     */
    public static boolean isNativeAttribute(MOModel model, TypeRef ref) {
        return !ref.isList() && !ref.isObjectRef()
                && JavaTypes.isNative(ref.getArea(), ref.getName())
                && isAttribute(model, ref);
    }

    /**
     * Returns the MAL attribute type this reference names, or null if it names something
     * else. Attributes have their own encoder methods; everything else is encoded as an
     * element.
     */
    public static String attributeName(MOModel model, TypeRef ref) {
        if (ref == null || ref.isList() || ref.isObjectRef()) {
            return null;
        }
        // The abstract Attribute type has its own encoder method, like the concrete ones.
        if ("MAL".equals(ref.getArea()) && "Attribute".equals(ref.getName())) {
            return "Attribute";
        }
        return isAttribute(model, ref) ? ref.getName() : null;
    }

    /**
     * Returns true if the reference names a type that cannot be instantiated, so the
     * encoder has to record which concrete type it is writing.
     */
    public static boolean isAbstractElement(MOModel model, TypeRef ref) {
        if (ref == null || ref.isList()) {
            return false;
        }
        // A reference to an abstract type is itself abstract: ObjectRef<Element> can hold
        // a reference to anything, so the encoder has to record what it wrote.
        TypeDefinition definition = model.resolve(ref);
        return definition != null && definition.isAbstract();
    }

    /**
     * Returns the expression that names the short form of a type, as a message body needs
     * it to say what it carries. An abstract type has none - what is on the wire is decided
     * per message - and neither has the base Element.
     *
     * @param model The model the reference is resolved against.
     * @param reference The type, may be null.
     * @return the short form expression, or the literal null.
     */
    public static String shortFormOf(MOModel model, TypeRef reference) {
        if (reference == null) {
            return "null";
        }
        TypeRef ref = reference.unwrapped();
        // What is on the wire is decided per message, whether the field holds one of them
        // or a list of them.
        TypeDefinition definition = model.resolve(ref);
        if (definition != null && definition.isAbstract()) {
            return "null";
        }
        // An attribute carries the short form of the attribute type, not of a class: the
        // concrete ones are constants on Attribute itself.
        if (!ref.isList() && isAttribute(model, ref)) {
            return JavaNaming.MAL_STRUCTURES + "Attribute."
                    + ref.getName().toUpperCase() + "_SHORT_FORM";
        }
        if (ref.isObjectRef()) {
            return ref.isList()
                    ? JavaNaming.MAL_STRUCTURES + "ObjectRefList.SHORT_FORM"
                    : JavaNaming.MAL_STRUCTURES + "ObjectRef.OBJECTREF_SHORT_FORM";
        }
        String java = of(model, ref);
        return (JavaNaming.MAL_STRUCTURES + "Element").equals(java) ? "null" : java + ".SHORT_FORM";
    }

    /**
     * Returns what a decoder is handed so it knows what to build. An abstract type carries
     * its own identity on the wire, so nothing is handed over for it - except a list of
     * abstract things, which is still a list and can be built empty. A list of the base
     * Element is the one exception to that exception: the reference output leaves it
     * undecided, because before the Java mapping renames it the type is called ElementList
     * and the rule matches on the name.
     *
     * @param model The model the reference is resolved against.
     * @param reference The type of the field being decoded.
     * @return the expression handed to the decoder, or the literal null.
     */
    public static String expectedTypeOf(MOModel model, TypeRef reference) {
        TypeRef ref = reference.unwrapped();
        String raw = qualified(ref) + (ref.isList() ? "List" : "");
        if (raw.contains(".MOObject")) {
            return "null";
        }
        TypeDefinition definition = model.resolve(ref);
        boolean isAbstract = definition != null && definition.isAbstract();
        if (isAbstract) {
            return raw.contains("List") && !raw.contains(".Element")
                    ? "new " + of(model, reference) + "()" : "null";
        }
        return newInstance(model, reference);
    }

    private static boolean isAttribute(MOModel model, TypeRef ref) {
        TypeDefinition definition = model.resolve(ref);
        return definition instanceof AttributeType;
    }
}
