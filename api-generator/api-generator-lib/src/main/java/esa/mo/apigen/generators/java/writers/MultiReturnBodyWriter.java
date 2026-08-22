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
import esa.mo.apigen.model.InteractionPattern;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the class that holds the answer of an operation that answers with more than one
 * field, since a Java method can only return one thing.
 */
public final class MultiReturnBodyWriter {

    /**
     * The package the classes live in, which is neither the consumer's nor the provider's:
     * both sides of the interaction name them.
     */
    public static final String BODY = "body";

    private MultiReturnBodyWriter() {
    }

    /**
     * @return the operations of a service that answer with more than one field, and so need
     * a class to hold the answer.
     */
    public static List<Operation> operationsOf(Service service) {
        List<Operation> found = new ArrayList<Operation>();
        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() == InteractionPattern.REQUEST
                    && fieldsOf(operation.getMessage(InteractionStage.RESPONSE)).size() > 1) {
                found.add(operation);
            }
        }
        return found;
    }

    /**
     * @return the name of the class that holds the answer of this operation.
     */
    public static String classNameOf(Operation operation) {
        String name = operation.getName();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1) + "Response";
    }

    /**
     * @return the source of the class holding the answer.
     */
    public static String write(MOModel model, Service service, Operation operation) {
        String className = classNameOf(operation);
        List<Field> fields = fieldsOf(operation.getMessage(InteractionStage.RESPONSE));

        JavaClassBuilder clazz = JavaClassBuilder.named(className).asFinal()
                .inPackage(JavaNaming.packageOf(service, BODY))
                .comment("Multi body return class for " + className + ".");
        JavaSource out = clazz.open();

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            // The field is documented by its name and then by what it holds, because a
            // reader of this class has only the names to go by.
            JavaFieldBuilder.named(field.getName()).scope("private")
                    .ofType(JavaTypeName.of(model, field.getType()))
                    .comment(field.getName() + ": " + describe(field, i))
                    .write(out);
        }

        JavaMethodBuilder.constructor(className)
                .comment("Default constructor for " + className + ".")
                .write(out);

        JavaMethodBuilder constructor = JavaMethodBuilder.constructor(className)
                .comment("Constructs an instance of this type using provided values.");
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            constructor.argument(JavaTypeName.of(model, field.getType()), field.getName(),
                    describe(field, i));
        }
        for (Field field : fields) {
            constructor.line("this." + field.getName() + " = " + field.getName() + ";");
        }
        constructor.write(out);

        for (Field field : fields) {
            JavaMethodBuilder.named("get" + capitalise(field.getName()))
                    .comment("Returns the field " + field.getName() + ".")
                    .returns(JavaTypeName.of(model, field.getType()),
                            "The field " + field.getName())
                    .line("return " + field.getName() + ";")
                    .write(out);
        }

        return clazz.close();
    }

    /**
     * @return what the field holds, or its place in the message where nothing was said.
     */
    private static String describe(Field field, int index) {
        return field.getComment() != null ? field.getComment()
                : field.getName() + " Argument number " + index
                + " as defined by the service operation";
    }

    private static List<Field> fieldsOf(MessageBody body) {
        return body == null ? new ArrayList<Field>() : body.getFields();
    }

    private static String capitalise(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
