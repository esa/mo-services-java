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
import esa.mo.apigen.generators.java.JavaFieldBuilder;
import esa.mo.apigen.generators.java.JavaMethodBuilder;
import esa.mo.apigen.generators.java.JavaNaming;
import esa.mo.apigen.generators.java.JavaSource;
import esa.mo.apigen.generators.java.JavaTypeName;
import esa.mo.apigen.generators.java.ShortForm;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.TypeRef;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the Java class for a composite.
 */
public final class CompositeWriter {

    private static final String COMPOSITE = JavaNaming.MAL_STRUCTURES + "Composite";

    private CompositeWriter() {
    }

    public static String write(MOModel model, Area area, Service service, CompositeType type) {
        String name = type.getName();
        String pkg = service == null
                ? JavaNaming.packageOf(area, JavaNaming.STRUCTURES)
                : JavaNaming.packageOf(service, JavaNaming.STRUCTURES);
        String fq = pkg + "." + name;
        List<Field> own = type.getFields();
        List<Field> inherited = model.inheritedFields(type);
        List<Field> all = new ArrayList<Field>(inherited);
        all.addAll(own);

        String superFq = superClassOf(type);
        boolean isAbstract = type.isAbstract();

        JavaClassBuilder clazz = JavaClassBuilder.named(name).inPackage(pkg)
                .comment(isBlank(type.getComment())
                        ? "The " + name + " structure." : type.getComment());
        if (isAbstract) {
            clazz.asAbstract();
        } else {
            clazz.asFinal();
        }
        // A composite that extends the base Composite implements the interface instead;
        // anything else extends its super type.
        if (superFq == null) {
            clazz.implementing(COMPOSITE);
        } else {
            clazz.extending(superFq);
        }
        JavaSource out = clazz.open();

        if (type.getShortFormPart() != null) {
            ShortForm.writeIdentity(out, ShortForm.of(area, service, type.getShortFormPart()));
        }

        for (Field field : own) {
            JavaFieldBuilder.named(field.getName()).scope("private")
                    .ofType(JavaTypeName.of(model, field.getType()))
                    .comment(isBlank(field.getComment())
                            ? "The " + field.getName() + " field." : field.getComment())
                    .write(out);
        }

        JavaMethodBuilder.constructor(name).comment("Default constructor for " + name + ".")
                .write(out);

        if (!all.isEmpty()) {
            writeConstructor(out, model, name, all, inherited, own,
                    "Constructor that initialises the values of the structure.");
            // The second constructor is suppressed in two cases: with no non-nullable
            // fields it would take no arguments and collide with the default constructor,
            // and with nothing but non-nullable fields it would repeat the first one.
            List<Field> required = requiredOf(all);
            if (!required.isEmpty() && required.size() != all.size()) {
                writeConstructor(out, model, name, required,
                        retain(inherited, required), own,
                        "Constructor that initialises the non-nullable values of the structure.",
                        omitted(own, required));
            }
        }

        if (!isAbstract) {
            JavaMethodBuilder.named("createElement").asOverride()
                    .returns(JavaNaming.MAL_STRUCTURES + "Element", null)
                    .line("return new " + fq + "();")
                    .write(out);
        }

        for (Field field : own) {
            JavaMethodBuilder.named("get" + capitalise(field.getName()))
                    .comment("Returns the field " + field.getName() + ".")
                    .returns(JavaTypeName.of(model, field.getType()),
                            "The field " + field.getName())
                    .line("return " + field.getName() + ";")
                    .write(out);
        }

        writeEquals(out, name, own, superFq != null);
        writeHashCode(out, own, superFq != null);
        writeToString(out, name, own, superFq != null);
        writeEncode(out, model, own, superFq != null);
        writeDecode(out, model, own, superFq != null);

        if (type.getShortFormPart() != null) {
            JavaMethodBuilder.named("getTypeId").asOverride()
                    .returns(JavaNaming.MAL + "TypeId", null)
                    .line("return TYPE_ID;")
                    .write(out);
        }

        return clazz.close();
    }

    // --------------------------------------------------------- constructors

    private static void writeConstructor(JavaSource out, MOModel model, String name,
            List<Field> parameters, List<Field> superArgs, List<Field> ownArgs, String title) {
        writeConstructor(out, model, name, parameters, superArgs, ownArgs, title,
                new ArrayList<Field>());
    }

    private static void writeConstructor(JavaSource out, MOModel model, String name,
            List<Field> parameters, List<Field> superArgs, List<Field> ownArgs, String title,
            List<Field> nulled) {
        JavaMethodBuilder constructor = JavaMethodBuilder.constructor(name).comment(title);

        for (Field field : parameters) {
            // A field with no comment still gets a parameter description.
            constructor.argument(JavaTypeName.of(model, field.getType()), field.getName(),
                    field.getComment());
        }
        if (!superArgs.isEmpty()) {
            StringBuilder call = new StringBuilder("super(");
            for (int i = 0; i < superArgs.size(); i++) {
                if (i > 0) {
                    call.append(",\n            ");
                }
                call.append(superArgs.get(i).getName());
            }
            call.append(");");
            constructor.line(call.toString());
        }
        // Assignments follow declaration order; a constructor that omits the nullable
        // fields still sets them, in place, to null.
        for (Field field : ownArgs) {
            boolean omitted = nulled.contains(field);
            constructor.line("this." + field.getName() + " = "
                    + (omitted ? "null" : field.getName()) + ";");
        }
        constructor.write(out);
    }

    // ------------------------------------------------------------- equality

    private static void writeEquals(JavaSource out, String name, List<Field> fields,
            boolean hasSuper) {
        JavaMethodBuilder method = JavaMethodBuilder.named("equals").asOverride()
                .returns("boolean", null)
                .argument("Object", "obj", null);

        method.line("if (obj instanceof " + name + ") {");
        if (hasSuper) {
            // A subclass compares its own fields only after the inherited ones agree.
            method.line("    if (! super.equals(obj)) {");
            method.line("        return false;");
            method.line("    }");
        }
        if (!fields.isEmpty()) {
            method.line("    " + name + " other = (" + name + ") obj;");
        }
        for (Field field : fields) {
            String f = field.getName();
            method.line("    if (" + f + " == null) {");
            method.line("        if (other." + f + " != null) {");
            method.line("            return false;");
            method.line("        }");
            method.line("    } else {");
            method.line("        if (! " + f + ".equals(other." + f + ")) {");
            method.line("            return false;");
            method.line("        }");
            method.line("    }");
        }
        method.line("    return true;");
        method.line("}");
        method.line("return false;");
        method.write(out);
    }

    private static void writeHashCode(JavaSource out, List<Field> fields,
            boolean hasSuper) {
        JavaMethodBuilder method = JavaMethodBuilder.named("hashCode").asOverride()
                .returns("int", null);

        method.line("int hash = " + (hasSuper ? "super.hashCode()" : "7") + ";");
        for (Field field : fields) {
            method.line("hash = 83 * hash + (" + field.getName() + " != null ? "
                    + field.getName() + ".hashCode() : 0);");
        }
        method.line("return hash;");
        method.write(out);
    }

    private static void writeToString(JavaSource out, String name, List<Field> fields,
            boolean hasSuper) {
        JavaMethodBuilder method = JavaMethodBuilder.named("toString").asOverride()
                .returns("String", null);

        method.line("StringBuilder buf = new StringBuilder();");
        method.line("buf.append(\"(" + name + ": \");");
        if (hasSuper) {
            method.line("buf.append(super.toString());");
        }
        for (int i = 0; i < fields.size(); i++) {
            String f = fields.get(i).getName();
            String separator = (i == 0 && !hasSuper) ? "" : ", ";
            method.line("buf.append(\"" + separator + f + "=\").append(" + f + ");");
        }
        method.line("buf.append(')');");
        method.line("return buf.toString();");
        method.write(out);
    }

    // ------------------------------------------------------ encode / decode

    private static void writeEncode(JavaSource out, MOModel model, List<Field> fields,
            boolean hasSuper) {
        JavaMethodBuilder method = JavaMethodBuilder.named("encode").asOverride()
                .argument(JavaNaming.MAL + "MALEncoder", "encoder", null)
                .throwing(JavaNaming.MAL + "MALException", null);

        if (hasSuper) {
            // Inherited fields are written by the super type, before this one's.
            method.line("super.encode(encoder);");
        }
        for (Field field : fields) {
            if (!field.isCanBeNull()) {
                method.line("if (" + field.getName() + " == null) {");
                method.line("    throw new " + JavaNaming.MAL + "MALException(\"The field '"
                        + field.getName() + "' cannot be null!\");");
                method.line("}");
            }
        }
        for (Field field : fields) {
            String nullable = field.isCanBeNull() ? "Nullable" : "";
            String attribute = JavaTypeName.attributeName(model, field.getType());
            String encoded = attribute == null
                    ? (JavaTypeName.isAbstractElement(model, field.getType())
                            ? "AbstractElement" : "Element")
                    : attribute;
            method.line("encoder.encode" + nullable + encoded + "(" + field.getName() + ");");
        }
        method.write(out);
    }

    private static void writeDecode(JavaSource out, MOModel model, List<Field> fields,
            boolean hasSuper) {
        JavaMethodBuilder method = JavaMethodBuilder.named("decode").asOverride()
                .returns(JavaNaming.MAL_STRUCTURES + "Element", null)
                .argument(JavaNaming.MAL + "MALDecoder", "decoder", null)
                .throwing(JavaNaming.MAL + "MALException", null);

        if (hasSuper) {
            method.line("super.decode(decoder);");
        }
        for (Field field : fields) {
            String nullable = field.isCanBeNull() ? "Nullable" : "";
            String attribute = JavaTypeName.attributeName(model, field.getType());
            if (attribute != null) {
                // The abstract Attribute decodes to a general type and is cast back.
                String cast = "Attribute".equals(attribute)
                        ? "(" + JavaNaming.MAL_STRUCTURES + "Attribute) " : "";
                method.line(field.getName() + " = " + cast + "decoder.decode"
                        + nullable + attribute + "();");
            } else if (JavaTypeName.isAbstractElement(model, field.getType())) {
                // An abstract element carries its own type on the wire, so nothing needs
                // to be handed to the decoder.
                String java = JavaTypeName.of(model, field.getType());
                method.line(field.getName() + " = (" + java + ") decoder.decode"
                        + nullable + "AbstractElement();");
            } else {
                String java = JavaTypeName.of(model, field.getType());
                method.line(field.getName() + " = (" + java + ") decoder.decode"
                        + nullable + "Element("
                        + JavaTypeName.newInstance(model, field.getType()) + ");");
            }
        }
        method.line("return this;");
        method.write(out);
    }

    // ---------------------------------------------------------------- utils

    /**
     * A composite that extends the base Composite implements the interface; anything else
     * extends its super type and inherits its fields.
     */
    /**
     * A comment that was written empty says as little as one that was not written at all,
     * so both are replaced by a plain description of what is being declared.
     */
    private static boolean isBlank(String comment) {
        return comment == null || comment.isEmpty();
    }

    private static String superClassOf(CompositeType type) {
        TypeRef superType = type.getSuperType();
        if (superType == null
                || ("MAL".equals(superType.getArea()) && "Composite".equals(superType.getName()))) {
            return null;
        }
        return JavaTypeName.qualified(superType);
    }

    private static List<Field> requiredOf(List<Field> fields) {
        List<Field> required = new ArrayList<Field>();
        for (Field field : fields) {
            if (!field.isCanBeNull()) {
                required.add(field);
            }
        }
        return required;
    }

    private static List<Field> omitted(List<Field> source, List<Field> keep) {
        List<Field> result = new ArrayList<Field>();
        for (Field field : source) {
            if (!keep.contains(field)) {
                result.add(field);
            }
        }
        return result;
    }

    private static List<Field> retain(List<Field> source, List<Field> keep) {
        List<Field> result = new ArrayList<Field>();
        for (Field field : source) {
            if (keep.contains(field)) {
                result.add(field);
            }
        }
        return result;
    }

    private static String capitalise(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
