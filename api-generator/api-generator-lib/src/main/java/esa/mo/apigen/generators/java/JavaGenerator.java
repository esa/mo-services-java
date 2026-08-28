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

import esa.mo.apigen.generators.Generator;
import esa.mo.apigen.generators.java.writers.CompositeWriter;
import esa.mo.apigen.generators.java.writers.ConsumerAdapterWriter;
import esa.mo.apigen.generators.java.writers.ConsumerStubWriter;
import esa.mo.apigen.generators.java.writers.ElementFactoryWriter;
import esa.mo.apigen.generators.java.writers.EnumerationWriter;
import esa.mo.apigen.generators.java.writers.ExceptionWriter;
import esa.mo.apigen.generators.java.writers.HelperWriter;
import esa.mo.apigen.generators.java.writers.ListWriter;
import esa.mo.apigen.generators.java.writers.MultiReturnBodyWriter;
import esa.mo.apigen.generators.java.writers.ProviderHandlerWriter;
import esa.mo.apigen.generators.java.writers.ProviderInheritanceSkeletonWriter;
import esa.mo.apigen.generators.java.writers.ProviderInteractionWriter;
import esa.mo.apigen.generators.java.writers.ProviderPublisherWriter;
import esa.mo.apigen.generators.java.writers.ProviderSkeletonWriter;
import esa.mo.apigen.generators.java.writers.ServiceInfoWriter;
import esa.mo.apigen.generators.java.writers.SubscriptionKeysWriter;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.TypeDefinition;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Generates the Java API.
 * <p>
 * Written against the model directly. The old generator is the reference for what comes
 * out - the generated API is compiled against by every api module and by user code, so
 * its shape is a compatibility surface - but not for how it is produced.
 * <p>
 * Incomplete: it writes the structures, the exceptions, the helpers, the element factories
 * and the package documentation - verified byte for byte against the reference output. The
 * provider skeletons, the consumer stubs, the {@code ServiceInfo} classes and the
 * multiple-return bodies are still to come; the last of those also brings the two
 * {@code package-info.java} files that the old generator writes as a side effect of it.
 */
public final class JavaGenerator implements Generator {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public String getShortName() {
        return "Java";
    }

    @Override
    public String getDescription() {
        return "Generates the Java API for a set of MO service specifications";
    }

    @Override
    public void generate(MOModel model, List<Area> targets, Path outputDir) throws IOException {
        for (Area area : targets) {
            generateArea(model, area, outputDir);
        }
    }

    private void generateArea(MOModel model, Area area, Path outputDir) throws IOException {
        PackageInfoWriter packages = new PackageInfoWriter(outputDir);
        // An area without a comment still gets one: the package documentation would
        // otherwise be an empty javadoc block.
        String areaComment = area.getComment() != null && !area.getComment().isEmpty()
                ? area.getComment() : "The " + area.getName() + " area";
        packages.write(JavaNaming.packageOf(area), areaComment);
        if (!area.getDataTypes().isEmpty()) {
            packages.write(JavaNaming.packageOf(area, JavaNaming.STRUCTURES),
                    "Package containing types defined in the " + area.getName() + " area");
        }
        writeTypes(model, area, null, area.getDataTypes(), outputDir);
        writeExceptions(area, outputDir);
        writeHelpers(area, outputDir);
        writeElementFactory(area, outputDir);
        for (Service service : area.getServices()) {
            writeTypes(model, area, service, service.getDataTypes(), outputDir);
            writeServiceInfo(model, area, service, outputDir);
            writeSubscriptionKeys(model, service, outputDir);
            writeConsumer(model, area, service, outputDir);
            writeProvider(model, service, outputDir);
            writeMultiReturnBodies(model, service, packages, outputDir);
            packages.write(JavaNaming.packageOf(service), service.getComment());
            packages.write(JavaNaming.packageOf(service, JavaNaming.CONSUMER),
                    "Package containing the consumer stubs for the "
                    + service.getName() + " service");
            packages.write(JavaNaming.packageOf(service, JavaNaming.PROVIDER),
                    "Package containing the provider skeletons for the "
                    + service.getName() + " service");
            if (!service.getDataTypes().isEmpty()) {
                packages.write(JavaNaming.packageOf(service, JavaNaming.STRUCTURES),
                        "Package containing types defined in the "
                        + service.getName() + " service");
            }
        }
    }

    /**
     * Every error the area declares becomes an exception in the area's own package,
     * whether it was declared by the area or by one of its services.
     */
    private void writeExceptions(Area area, Path outputDir) throws IOException {
        java.util.List<esa.mo.apigen.model.ErrorDefinition> errors
                = new java.util.ArrayList<esa.mo.apigen.model.ErrorDefinition>(area.getErrors());
        for (Service service : area.getServices()) {
            errors.addAll(service.getErrors());
        }
        if (errors.isEmpty()) {
            return;
        }
        Path dir = outputDir.resolve(JavaNaming.directoryOf(JavaNaming.packageOf(area)));
        Files.createDirectories(dir);
        for (esa.mo.apigen.model.ErrorDefinition error : errors) {
            Files.write(dir.resolve(ExceptionWriter.classNameOf(error.getName()) + ".java"),
                    ExceptionWriter.write(area, error).getBytes(UTF8));
        }
    }

    /**
     * The area's helper, and one for each of its services.
     */
    private void writeHelpers(Area area, Path outputDir) throws IOException {
        Path areaDir = outputDir.resolve(JavaNaming.directoryOf(JavaNaming.packageOf(area)));
        Files.createDirectories(areaDir);
        Files.write(areaDir.resolve(area.getName() + "Helper.java"),
                HelperWriter.writeArea(area).getBytes(UTF8));
        for (Service service : area.getServices()) {
            Path dir = outputDir.resolve(JavaNaming.directoryOf(JavaNaming.packageOf(service)));
            Files.createDirectories(dir);
            Files.write(dir.resolve(service.getName() + "Helper.java"),
                    HelperWriter.writeService(service).getBytes(UTF8));
        }
    }

    /**
     * The factory that creates the Elements of the area, one per area.
     */
    private void writeElementFactory(Area area, Path outputDir) throws IOException {
        Path dir = outputDir.resolve(JavaNaming.directoryOf(JavaNaming.packageOf(area)));
        Files.createDirectories(dir);
        Files.write(dir.resolve(area.getName() + "ElementFactory.java"),
                ElementFactoryWriter.write(area).getBytes(UTF8));
    }

    /**
     * The ServiceInfo of a service, which is what its stubs and skeletons name their
     * operations through.
     */
    private void writeServiceInfo(MOModel model, Area area, Service service, Path outputDir)
            throws IOException {
        Path dir = outputDir.resolve(JavaNaming.directoryOf(JavaNaming.packageOf(service)));
        Files.createDirectories(dir);
        Files.write(dir.resolve(service.getName() + "ServiceInfo.java"),
                ServiceInfoWriter.write(model, area, service).getBytes(UTF8));
    }

    /**
     * One class per publish-subscribe operation, for reading the keys of a notify message.
     */
    private void writeSubscriptionKeys(MOModel model, Service service, Path outputDir)
            throws IOException {
        Path dir = outputDir.resolve(
                JavaNaming.directoryOf(JavaNaming.packageOf(service, JavaNaming.CONSUMER)));
        for (esa.mo.apigen.model.Operation operation : service.getOperations()) {
            if (operation.getPattern() != esa.mo.apigen.model.InteractionPattern.PUBSUB) {
                continue;
            }
            Files.createDirectories(dir);
            Files.write(dir.resolve(SubscriptionKeysWriter.classNameOf(operation) + ".java"),
                    SubscriptionKeysWriter.write(model, service, operation).getBytes(UTF8));
        }
    }

    /**
     * The consumer stub of a service, and the adapter its answers arrive through.
     */
    private void writeConsumer(MOModel model, Area area, Service service, Path outputDir)
            throws IOException {
        Path dir = outputDir.resolve(
                JavaNaming.directoryOf(JavaNaming.packageOf(service, JavaNaming.CONSUMER)));
        Files.createDirectories(dir);
        Files.write(dir.resolve(service.getName() + "Stub.java"),
                ConsumerStubWriter.write(model, service).getBytes(UTF8));
        Files.write(dir.resolve(service.getName() + "Adapter.java"),
                ConsumerAdapterWriter.write(model, area, service).getBytes(UTF8));
    }

    /**
     * The provider side of a service: what a provider implements, and how it reaches its
     * publishers.
     */
    private void writeProvider(MOModel model, Service service, Path outputDir)
            throws IOException {
        Path dir = outputDir.resolve(
                JavaNaming.directoryOf(JavaNaming.packageOf(service, JavaNaming.PROVIDER)));
        Files.createDirectories(dir);
        Files.write(dir.resolve(service.getName() + "Handler.java"),
                ProviderHandlerWriter.write(model, service).getBytes(UTF8));
        Files.write(dir.resolve(service.getName() + "Skeleton.java"),
                ProviderSkeletonWriter.write(service).getBytes(UTF8));
        Files.write(dir.resolve(service.getName() + "InheritanceSkeleton.java"),
                ProviderInheritanceSkeletonWriter.write(model, service).getBytes(UTF8));
        // An operation that reports back over time is answered through an object of its
        // own, so that what it can send is named and typed.
        for (esa.mo.apigen.model.Operation operation : service.getOperations()) {
            if (operation.getPattern() == esa.mo.apigen.model.InteractionPattern.INVOKE
                    || operation.getPattern() == esa.mo.apigen.model.InteractionPattern.PROGRESS) {
                Files.write(dir.resolve(ProviderInteractionWriter.classNameOf(operation) + ".java"),
                        ProviderInteractionWriter.write(model, service, operation).getBytes(UTF8));
            }
            if (operation.getPattern() == esa.mo.apigen.model.InteractionPattern.PUBSUB) {
                Files.write(dir.resolve(ProviderPublisherWriter.classNameOf(operation) + ".java"),
                        ProviderPublisherWriter.write(model, service, operation).getBytes(UTF8));
            }
        }
    }

    /**
     * An operation that answers with more than one field needs a class to hold the answer,
     * since a Java method can only return one thing.
     */
    private void writeMultiReturnBodies(MOModel model, Service service,
            PackageInfoWriter packages, Path outputDir) throws IOException {
        java.util.List<esa.mo.apigen.model.Operation> operations
                = MultiReturnBodyWriter.operationsOf(service);
        if (operations.isEmpty()) {
            return;
        }
        String pkg = JavaNaming.packageOf(service, MultiReturnBodyWriter.BODY);
        packages.write(pkg, "Package containing the types for holding compound messages");
        Path dir = outputDir.resolve(JavaNaming.directoryOf(pkg));
        Files.createDirectories(dir);
        for (esa.mo.apigen.model.Operation operation : operations) {
            Files.write(dir.resolve(MultiReturnBodyWriter.classNameOf(operation) + ".java"),
                    MultiReturnBodyWriter.write(model, service, operation).getBytes(UTF8));
        }
    }

    private void writeTypes(MOModel model, Area area, Service service,
            java.util.List<TypeDefinition> types, Path outputDir) throws IOException {
        String pkg = service == null
                ? JavaNaming.packageOf(area, JavaNaming.STRUCTURES)
                : JavaNaming.packageOf(service, JavaNaming.STRUCTURES);
        Path dir = outputDir.resolve(JavaNaming.directoryOf(pkg));
        for (TypeDefinition type : types) {
            if (type instanceof EnumerationType) {
                Files.createDirectories(dir);
                Files.write(dir.resolve(type.getName() + ".java"),
                        EnumerationWriter.write(area, service, (EnumerationType) type)
                                .getBytes(UTF8));
            }
            if (type instanceof CompositeType) {
                Files.createDirectories(dir);
                Files.write(dir.resolve(type.getName() + ".java"),
                        CompositeWriter.write(model, area, service, (CompositeType) type)
                                .getBytes(UTF8));
            }
            String list = ListWriter.write(area, service, type);
            if (list != null) {
                Files.createDirectories(dir);
                Files.write(dir.resolve(type.getName() + "List.java"), list.getBytes(UTF8));
            }
        }
    }

    /**
     * Writes the package-info.java files.
     */
    private static final class PackageInfoWriter {

        private final Path root;

        private PackageInfoWriter(Path root) {
            this.root = root;
        }

        private void write(String packageName, String comment) throws IOException {
            Path dir = root.resolve(JavaNaming.directoryOf(packageName));
            Files.createDirectories(dir);
            StringBuilder buf = new StringBuilder();
            buf.append("/**\n");
            for (String line : JavaComment.normalise(comment)) {
                buf.append(" * ").append(line).append('\n');
            }
            buf.append("*/\n");
            buf.append("package ").append(packageName).append(";\n");
            Files.write(dir.resolve("package-info.java"), buf.toString().getBytes(UTF8));
        }
    }
}
