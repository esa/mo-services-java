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
import esa.mo.apigen.model.InteractionPattern;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;

/**
 * Writes the skeleton interface of a service: how a provider reaches the publishers of its
 * publish-subscribe operations, and nothing else. A service that publishes nothing has an
 * empty one.
 */
public final class ProviderSkeletonWriter {

    private ProviderSkeletonWriter() {
    }

    /**
     * @return the source of the service's skeleton interface.
     */
    public static String write(Service service) {
        String name = service.getName();
        JavaClassBuilder clazz = JavaClassBuilder.named(name + "Skeleton").asInterface()
                .inPackage(JavaNaming.packageOf(service, JavaNaming.PROVIDER))
                .comment("The skeleton interface for the " + name + " service.");
        JavaSource out = clazz.open();

        for (Operation operation : service.getOperations()) {
            if (operation.getPattern() != InteractionPattern.PUBSUB) {
                continue;
            }
            String publisher = JavaNaming.packageOf(service, JavaNaming.PROVIDER) + "."
                    + capitalise(operation.getName()) + "Publisher";
            JavaMethodBuilder.named("create" + capitalise(operation.getName()) + "Publisher")
                    .asDeclaration()
                    .comment("Creates a publisher object using the current registered provider"
                            + " set for the PubSub operation " + operation.getName())
                    .returns(publisher, "The new publisher object.")
                    .argument(JavaNaming.MAL_STRUCTURES + "IdentifierList", "domain",
                            "The domain used for publishing")
                    .argument(JavaNaming.MAL_STRUCTURES + "Identifier", "networkZone",
                            "~The network zone used for publishing")
                    .argument(JavaNaming.MAL_STRUCTURES + "SessionType", "sessionType",
                            "The session used for publishing")
                    .argument(JavaNaming.MAL_STRUCTURES + "Identifier", "sessionName",
                            "The session name used for publishing")
                    .argument(JavaNaming.MAL_STRUCTURES + "QoSLevel", "qos",
                            "The QoS used for publishing")
                    .argument("java.util.Map", "qosProps",
                            "The QoS properties used for publishing")
                    .argument(JavaNaming.MAL_STRUCTURES + "UInteger", "priority",
                            "The priority used for publishing")
                    .throwing(JavaNaming.MAL + "MALException",
                            "if a problem is detected during creation of the publisher")
                    .write(out);
        }

        return clazz.close();
    }

    private static String capitalise(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}
