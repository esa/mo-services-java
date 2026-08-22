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
 * Writes the publisher of a publish-subscribe operation: how a provider registers with the
 * brokers it publishes through, and what one update looks like.
 */
public final class ProviderPublisherWriter {

    private static final String PUBLISHER_SET = JavaNaming.MAL + "provider.MALPublisherSet";

    private static final String LISTENER = JavaNaming.MAL + "provider.MALPublishInteractionListener";

    private static final String INTERACTION_EXCEPTION = JavaNaming.MAL + "MALInteractionException";

    private static final String MAL_EXCEPTION = JavaNaming.MAL + "MALException";

    private static final String INTERACTION_THROWN =
            "if there is a problem during the interaction as defined by the MAL specification.";

    private static final String EXCEPTION_THROWN = "if there is an implementation exception";

    private static final String ARGUMENT_THROWN = "If any supplied argument is invalid";

    private static final String LISTENER_COMMENT =
            "The listener object to use for callback from the publisher";

    private ProviderPublisherWriter() {
    }

    /**
     * @return the name of the class, which is the operation's with the kind appended.
     */
    public static String classNameOf(Operation operation) {
        String name = operation.getName();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1) + "Publisher";
    }

    /**
     * @return the source of the operation's publisher.
     */
    public static String write(MOModel model, Service service, Operation operation) {
        String className = classNameOf(operation);
        JavaClassBuilder clazz = JavaClassBuilder.named(className).asFinal()
                .inPackage(JavaNaming.packageOf(service, JavaNaming.PROVIDER))
                .comment("Publisher class for the " + operation.getName() + " operation.");
        JavaSource out = clazz.open();

        JavaFieldBuilder.named("publisherSet").scope("private").ofType(PUBLISHER_SET)
                .comment("The publisherSet field.").write(out);

        JavaMethodBuilder.constructor(className)
                .comment("Creates an instance of this class using the supplied publisher set.")
                .argument(PUBLISHER_SET, "publisherSet",
                        "The set of broker connections to use when registering and publishing.")
                .line("this.publisherSet = publisherSet;")
                .write(out);

        register(out, "register", "Registers this provider implementation to the set of broker"
                + " connections", "publisherSet.register(keyNames, keyTypes, listener);");

        writeRegisterWithDefaultKeys(out, model, operation);

        register(out, "asyncRegister", "Asynchronously registers this provider implementation to"
                + " the set of broker connections",
                "publisherSet.asyncRegister(keyNames, keyTypes, listener);");

        writePublish(out, model, operation);

        JavaMethodBuilder.named("deregister")
                .comment("Deregisters this provider implementation from the set of broker"
                        + " connections")
                .throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN)
                .line("publisherSet.deregister();")
                .write(out);

        JavaMethodBuilder.named("asyncDeregister")
                .comment("Asynchronously deregisters this provider implementation from the set"
                        + " of broker connections")
                .argument(LISTENER, "listener", LISTENER_COMMENT)
                .throwing("java.lang.IllegalArgumentException", ARGUMENT_THROWN)
                .throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN)
                .line("publisherSet.asyncDeregister(listener);")
                .write(out);

        JavaMethodBuilder.named("close").comment("Closes this publisher")
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN)
                .line("publisherSet.close();")
                .write(out);

        return clazz.close();
    }

    /**
     * Registering says which keys the updates of this provider will be identified by.
     */
    private static void register(JavaSource out, String name, String comment, String body) {
        JavaMethodBuilder.named(name).comment(comment)
                .argument(JavaNaming.MAL_STRUCTURES + "IdentifierList", "keyNames",
                        "The key names to use in the method")
                .argument(JavaNaming.MAL_STRUCTURES + "AttributeTypeList", "keyTypes",
                        "The key types to use in the method")
                .argument(LISTENER, "listener", LISTENER_COMMENT)
                .throwing("java.lang.IllegalArgumentException", ARGUMENT_THROWN)
                .throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN)
                .line(body)
                .write(out);
    }

    /**
     * Registering with the keys the operation itself declared, which is what a provider that
     * has nothing of its own to add does.
     */
    private static void writeRegisterWithDefaultKeys(JavaSource out, MOModel model,
            Operation operation) {
        JavaMethodBuilder method = JavaMethodBuilder.named("registerWithDefaultKeys")
                .comment("Registers this provider implementation to the set of broker"
                        + " connections with the default subscription keys")
                .argument(LISTENER, "listener", LISTENER_COMMENT)
                .throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN);

        method.line(JavaNaming.MAL_STRUCTURES + "IdentifierList keyNames = new "
                + JavaNaming.MAL_STRUCTURES + "IdentifierList();");
        method.line(JavaNaming.MAL_STRUCTURES + "AttributeTypeList keyTypes = new "
                + JavaNaming.MAL_STRUCTURES + "AttributeTypeList();");
        for (Field key : fieldsOf(operation.getMessage(InteractionStage.SUBSCRIPTION_KEYS))) {
            method.line("keyNames.add(new " + JavaNaming.MAL_STRUCTURES + "Identifier(\""
                    + key.getName() + "\"));");
            method.line("keyTypes.add(" + JavaNaming.MAL_STRUCTURES + "AttributeType."
                    + attributeTypeOf(model, key) + ");");
        }
        method.line("publisherSet.register(keyNames, keyTypes, listener);");
        method.write(out);
    }

    /**
     * One update: the header that identifies it, then the fields the operation publishes.
     */
    private static void writePublish(JavaSource out, MOModel model, Operation operation) {
        List<Field> published = fieldsOf(operation.getMessage(InteractionStage.PUBLISH_NOTIFY));
        JavaMethodBuilder method = JavaMethodBuilder.named("publish")
                .comment("Publishes updates to the set of registered broker connections")
                .argument(JavaNaming.MAL_STRUCTURES + "UpdateHeader", "updateHeader",
                        "The headers of the updates being added");

        StringBuilder arguments = new StringBuilder("updateHeader");
        for (int i = 0; i < published.size(); i++) {
            Field field = published.get(i);
            String comment = field.getComment() != null ? field.getComment()
                    : field.getName() + " Argument number " + i
                    + " as defined by the service operation";
            method.argument(JavaTypeName.of(model, field.getType()), field.getName(), comment);
            arguments.append(", ").append(field.getName());
        }

        method.throwing("java.lang.IllegalArgumentException", ARGUMENT_THROWN)
                .throwing(INTERACTION_EXCEPTION, INTERACTION_THROWN)
                .throwing(MAL_EXCEPTION, EXCEPTION_THROWN)
                .line("publisherSet.publish(" + arguments + ");")
                .write(out);
    }

    /**
     * @return the attribute type a key is carried as. An enumeration travels as the number
     * that stands for it, so it is keyed as a UShort.
     */
    private static String attributeTypeOf(MOModel model, Field key) {
        TypeDefinition definition = model.resolve(key.getType());
        return definition instanceof EnumerationType
                ? "USHORT" : key.getType().getName().toUpperCase();
    }

    private static List<Field> fieldsOf(MessageBody body) {
        return body == null ? new ArrayList<Field>() : body.getFields();
    }
}
