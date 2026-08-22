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
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.TypeDefinition;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the class that reads the subscription keys of one publish-subscribe operation.
 * <p>
 * A notify message carries its key values in the UpdateHeader, in the order the operation
 * declared them - but a subscription may have asked for only some of them, in which case
 * the values that arrive are fewer and the names that go with them come from the
 * subscription rather than from the operation. This class holds both and pairs them up by
 * name, so a consumer asks for a key rather than for an index. The UpdateHeader itself is
 * never modified.
 */
public final class SubscriptionKeysWriter {

    private static final String ATTRIBUTE = JavaNaming.MAL_STRUCTURES + "Attribute";

    private SubscriptionKeysWriter() {
    }

    /**
     * @return the name of the class, which is the operation's with the kind appended.
     */
    public static String classNameOf(Operation operation) {
        String name = operation.getName();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1) + "SubscriptionKeys";
    }

    /**
     * @return the source of the subscription key accessors.
     */
    public static String write(MOModel model, Service service, Operation operation) {
        String className = classNameOf(operation);
        List<Field> keys = keysOf(operation);

        JavaClassBuilder clazz = JavaClassBuilder.named(className).asFinal()
                .inPackage(JavaNaming.packageOf(service, JavaNaming.CONSUMER))
                .comment("Typed accessors for the Subscription Keys of the "
                        + operation.getName() + " PubSub operation.");
        JavaSource out = clazz.open();

        JavaFieldBuilder.named("keyValues").scope("private")
                .ofType(JavaNaming.MAL_STRUCTURES + "NullableAttributeList")
                .comment("The key values as received in the UpdateHeader").write(out);
        JavaFieldBuilder.named("keyNames").scope("private")
                .ofType(JavaNaming.MAL_STRUCTURES + "IdentifierList")
                .comment("The effective key names for the received key values").write(out);

        StringBuilder canonical = new StringBuilder("new " + JavaNaming.MAL_STRUCTURES
                + "IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(");
        for (int i = 0; i < keys.size(); i++) {
            canonical.append(i == 0 ? "" : ", ").append("new ").append(JavaNaming.MAL_STRUCTURES)
                    .append("Identifier(\"").append(keys.get(i).getName()).append("\")");
        }
        JavaFieldBuilder.named("CANONICAL_KEY_NAMES").scope("private").asStatic().asFinal()
                .ofType(JavaNaming.MAL_STRUCTURES + "IdentifierList")
                .value(canonical.append(")))").toString())
                .comment("The Subscription Key names defined by the operation, in order")
                .write(out);

        JavaMethodBuilder.constructor(className)
                .comment("Creates an instance from the received UpdateHeader and the"
                        + " subscription selectedKeys.")
                .argument(JavaNaming.MAL_STRUCTURES + "UpdateHeader", "updateHeader",
                        "The UpdateHeader received in the NOTIFY message")
                .argument(JavaNaming.MAL_STRUCTURES + "IdentifierList", "selectedKeys",
                        "The selectedKeys of the subscription, or null if trimming was not enabled")
                .line("this.keyValues = (updateHeader == null) ? null : updateHeader.getKeyValues();")
                .line("this.keyNames = (selectedKeys != null) ? selectedKeys : CANONICAL_KEY_NAMES;")
                .write(out);

        for (Field key : keys) {
            writeGetter(out, model, key);
        }

        JavaMethodBuilder.named("getByName").returns(ATTRIBUTE, "The key value, or null if not present")
                .comment("Returns the Subscription Key value with the given name, or null if it"
                        + " is not present (for example when it was trimmed away or is a custom"
                        + " key that is not part of this subscription).")
                .argument("String", "name", "The Subscription Key name")
                .line("return valueByName(name);")
                .write(out);

        JavaMethodBuilder.named("valueByName").scope("private").returns(ATTRIBUTE, null)
                .argument("String", "name", "The Subscription Key name")
                .line("if (keyNames == null || keyValues == null) {")
                .line("    return null;")
                .line("}")
                .line("for (int i = 0; i < keyNames.size(); i++) {")
                .line("    if (name.equals(keyNames.get(i).getValue())) {")
                .line("        if (i >= keyValues.size()) {")
                .line("            return null;")
                .line("        }")
                .line("        " + JavaNaming.MAL_STRUCTURES + "NullableAttribute na = keyValues.get(i);")
                .line("        return (na == null) ? null : na.getValue();")
                .line("    }")
                .line("}")
                .line("return null;")
                .write(out);

        return clazz.close();
    }

    /**
     * Writes the accessor of one key, which answers in the type the key was declared as
     * rather than in the Attribute it arrived as.
     */
    private static void writeGetter(JavaSource out, MOModel model, Field key) {
        String name = key.getName();
        String getter = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        String comment = "Returns the value of the \"" + name
                + "\" Subscription Key, or null if not present.";
        String type = JavaTypeName.of(model, key.getType());
        JavaMethodBuilder method = JavaMethodBuilder.named(getter)
                .returns(type, "The key value, or null if not present");

        TypeDefinition definition = model.resolve(key.getType());
        if (definition instanceof EnumerationType) {
            // An enumeration travels as the number that stands for it, so it is rebuilt
            // rather than cast.
            method.comment(comment + " Enumeration keys are transmitted as their UShort"
                    + " numeric value.")
                    .line(JavaNaming.MAL_STRUCTURES + "UShort v = ("
                            + JavaNaming.MAL_STRUCTURES + "UShort) valueByName(\"" + name + "\");")
                    .line("return (v == null) ? null : new " + type + "(v.getValue());");
        } else if (JavaTypeName.isNativeAttribute(model, key.getType())) {
            // A native attribute is a plain Java type, so it is unwrapped rather than cast.
            method.comment(comment)
                    .line(ATTRIBUTE + " v = valueByName(\"" + name + "\");")
                    .line("return (v == null) ? null : (" + type + ") " + ATTRIBUTE
                            + ".attribute2JavaType(v);");
        } else {
            method.comment(comment).line("return (" + type + ") valueByName(\"" + name + "\");");
        }
        method.write(out);
    }

    /**
     * @return the keys the operation declares, in order, empty if it declares none.
     */
    private static List<Field> keysOf(Operation operation) {
        MessageBody keys = operation.getMessage(InteractionStage.SUBSCRIPTION_KEYS);
        return keys == null ? new ArrayList<Field>() : keys.getFields();
    }
}
