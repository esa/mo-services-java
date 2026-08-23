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
package esa.mo.apigen.exporters.mospec;

import esa.mo.apigen.exporters.ExportException;
import esa.mo.apigen.exporters.Exporter;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.CapabilitySet;
import esa.mo.apigen.model.ErrorDefinition;
import esa.mo.apigen.model.ErrorReference;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionPattern;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.Specification;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.com.ObjectReference;
import esa.mo.apigen.model.docs.Diagram;
import esa.mo.apigen.model.docs.DocSection;
import esa.mo.apigen.model.types.AttributeType;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.EnumerationItem;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.FundamentalType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes a specification as MOSpec.
 * <p>
 * A MOSpec specification is a directory: one {@code .mospec} file holding the specification,
 * and one {@code .svg} beside it per diagram. Inlining a diagram would put a hundred lines of
 * drawing in the middle of a readable file, and dropping it would make the text format the
 * one place the round trip leaks.
 * <p>
 * The exporter defines the dialect: whatever it writes, the parser must read back. That is
 * what the round-trip test holds it to.
 */
public final class MOSpecExporter implements Exporter {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final DocMode mode;

    /**
     * An exporter writing in the default mode, with each operation's documentation gathered
     * above its signature.
     */
    public MOSpecExporter() {
        this(DocMode.BULK);
    }

    /**
     * @param mode Where to put the documentation of fields and errors.
     */
    public MOSpecExporter(DocMode mode) {
        this.mode = mode;
    }

    @Override
    public void write(Specification spec, Path outputDir) throws IOException, ExportException {
        Files.createDirectories(outputDir);
        SourceFormatter out = new SourceFormatter();
        writeSpecification(out, spec);

        String name = spec.getAreas().isEmpty() ? "specification"
                : spec.getAreas().get(0).getName();
        write(outputDir.resolve(name + ".mospec"), out.toText());

        for (Area area : spec.getAreas()) {
            writeDiagrams(outputDir, area.getDocumentation().getDiagrams());
            for (Service service : area.getServices()) {
                writeDiagrams(outputDir, service.getDocumentation().getDiagrams());
                if (service.getCom() != null) {
                    writeDiagrams(outputDir, service.getCom().getDocumentation().getDiagrams());
                }
            }
        }
    }

    /**
     * @return the text of a specification, for a caller that wants it without the sidecars.
     */
    public String toText(Specification spec) {
        SourceFormatter out = new SourceFormatter();
        writeSpecification(out, spec);
        return out.toText();
    }

    private void writeSpecification(SourceFormatter out, Specification spec) {
        if (mode == DocMode.SUPPRESS) {
            out.suppressDocumentation();
        }
        out.doc(spec.getComment());
        out.line("specification [" + spec.getSchemaVersion().name().toLowerCase() + "]");
        for (Area area : spec.getAreas()) {
            out.blank();
            writeArea(out, area);
        }
    }

    private void writeArea(SourceFormatter out, Area area) {
        out.doc(area.getComment());
        out.line("area " + MOSpecTypes.identifier(area.getName())
                + " [" + area.getNumber() + "." + area.getVersion() + "]");
        writeDocSections(out, area.getDocumentation().getSections());
        writeDiagramReferences(out, area.getDocumentation().getDiagrams());
        writeDataTypes(out, area, null, area.getDataTypes());
        writeErrorDefinitions(out, area.getErrors());

        for (Service service : area.getServices()) {
            out.blank();
            writeService(out, area, service);
        }
    }

    private void writeService(SourceFormatter out, Area area, Service service) {
        out.doc(service.getComment());
        // A service can be a COM extended service and declare nothing at all under com, so
        // being one is said on the service rather than inferred from what follows.
        out.line((service.isExtended() ? "extended service " : "service ")
                + MOSpecTypes.identifier(service.getName())
                + " [" + service.getNumber() + "] {");
        out.in();

        writeDocSections(out, service.getDocumentation().getSections());
        writeDiagramReferences(out, service.getDocumentation().getDiagrams());

        for (CapabilitySet set : service.getCapabilitySets()) {
            out.blank();
            writeCapabilitySet(out, area, service, set);
        }

        writeDataTypes(out, area, service, service.getDataTypes());
        writeErrorDefinitions(out, service.getErrors());

        if (service.getCom() != null) {
            out.blank();
            writeCom(out, area, service, service.getCom());
        }

        out.out();
        out.line("}");
    }

    private void writeCapabilitySet(SourceFormatter out, Area area, Service service,
            CapabilitySet set) {
        out.doc(set.getComment());
        out.line("capability [" + set.getNumber() + "] {");
        out.in();
        for (Operation operation : set.getOperations()) {
            out.blank();
            writeOperation(out, area, service, operation);
        }
        out.out();
        out.line("}");
    }

    /**
     * One operation: its documentation, then the pattern, the name, the number, and the
     * messages it exchanges in the order they travel.
     */
    private void writeOperation(SourceFormatter out, Area area, Service service,
            Operation operation) {
        if (mode == DocMode.BULK) {
            out.doc(operation.getComment(), bulkTags(area, operation));
        } else if (mode == DocMode.INLINE) {
            // A message's own comment stays in the block even inline, tagged by its stage.
            // Written inside the parentheses it could not be told from the documentation of
            // the first field, which sits in the same place and looks the same.
            out.doc(operation.getComment(), messageTags(operation));
        }
        writeDocSections(out, operation.getDocumentation().getSections());

        StringBuilder head = new StringBuilder();
        if (operation.isSupportInReplay()) {
            head.append("replayable ");
        }
        head.append(patternOf(operation)).append(' ')
                .append(MOSpecTypes.identifier(operation.getName()))
                .append(" [").append(operation.getNumber()).append(']');
        // The opening message is left out where the operation declares none: a
        // publish-subscribe operation that keys its subscription on nothing is not the same
        // as one that keys it on an empty list.
        InteractionStage first = firstStageOf(operation);
        boolean opened = operation.getMessage(first) != null;

        if (mode == DocMode.INLINE) {
            if (opened) {
                writeInlineMessage(out, area, service, operation, first, head + " ");
            } else {
                out.line(head.toString());
            }
            out.in();
            for (InteractionStage stage : replyStagesOf(operation)) {
                writeInlineMessage(out, area, service, operation, stage, "-> ");
                if (repeats(operation, stage)) {
                    out.line("*");
                }
            }
            writeThrows(out, area, service, operation);
            out.out();
            return;
        }

        if (opened) {
            head.append(' ').append(message(area, service, operation, first));
        }
        out.line(head.toString());

        out.in();
        for (InteractionStage stage : replyStagesOf(operation)) {
            out.line("-> " + message(area, service, operation, stage)
                    + (repeats(operation, stage) ? "*" : ""));
        }
        writeThrows(out, area, service, operation);
        out.out();
    }

    /**
     * The errors an operation may answer with, each with the type of whatever extra
     * information it carries.
     */
    private void writeThrows(SourceFormatter out, Area area, Service service,
            Operation operation) {
        if (operation.getErrors().isEmpty()) {
            return;
        }
        if (mode == DocMode.INLINE) {
            out.line("throws");
            out.in();
            List<ErrorReference> errors = operation.getErrors();
            for (int i = 0; i < errors.size(); i++) {
                ErrorReference error = errors.get(i);
                out.doc(error.getComment());
                String written = errorName(area, error) + (i == errors.size() - 1 ? "" : ",");
                if (error.getExtraInformation() == null) {
                    out.line(written);
                    continue;
                }
                out.line(errorName(area, error) + ":");
                out.in();
                out.doc(error.getExtraInformation().getComment());
                out.line(MOSpecTypes.of(area, service, error.getExtraInformation().getType())
                        + (i == errors.size() - 1 ? "" : ","));
                out.out();
            }
            out.out();
            return;
        }

        List<String> raised = new ArrayList<String>();
        for (ErrorReference error : operation.getErrors()) {
            String written = errorName(area, error);
            if (error.getExtraInformation() != null) {
                written += ": " + MOSpecTypes.of(area, service,
                        error.getExtraInformation().getType());
            }
            raised.add(written);
        }
        out.line("throws " + join(raised, ", "));
    }

    /**
     * @return the fields of one message, in parentheses.
     */
    private String message(Area area, Service service, Operation operation,
            InteractionStage stage) {
        if (stage == null) {
            return "()";
        }
        List<Field> fields = fieldsOf(operation, stage);
        List<String> written = new ArrayList<String>();
        for (Field field : fields) {
            written.add(MOSpecTypes.field(area, service, field));
        }
        return "(" + join(written, ", ") + ")";
    }

    /**
     * Writes a message with the documentation of each field beside it, which is what inline
     * mode is for: the signature is longer, and nothing about a field is anywhere else.
     *
     * @return true if anything was written, false where the message is empty and the
     * caller should write it on the line it is already on.
     */
    private boolean writeInlineMessage(SourceFormatter out, Area area, Service service,
            Operation operation, InteractionStage stage, String opening) {
        List<Field> fields = stage == null ? new ArrayList<Field>()
                : fieldsOf(operation, stage);

        if (fields.isEmpty()) {
            out.line(opening + "()");
            return true;
        }
        out.line(opening + "(");
        out.in();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            out.doc(field.getComment());
            out.line(MOSpecTypes.field(area, service, field)
                    + (i == fields.size() - 1 ? "" : ","));
        }
        out.out();
        out.line(")");
        return true;
    }

    /**
     * The tagged lines of a bulk documentation block: one per documented field, then one
     * per error and its extra information.
     */
    /**
     * @return one tag per message that documents itself, and nothing else.
     */
    private List<String> messageTags(Operation operation) {
        List<String> tags = new ArrayList<String>();
        for (InteractionStage stage : allStagesOf(operation)) {
            MessageBody body = operation.getMessage(stage);
            if (body != null && body.getComment() != null) {
                tags.add("@" + tagOf(stage) + ": " + body.getComment());
            }
        }
        return tags;
    }

    private List<String> bulkTags(Area area, Operation operation) {
        List<String> tags = new ArrayList<String>();
        for (InteractionStage stage : allStagesOf(operation)) {
            MessageBody body = operation.getMessage(stage);
            if (body != null && body.getComment() != null) {
                tags.add("@" + tagOf(stage) + ": " + body.getComment());
            }
            for (Field field : fieldsOf(operation, stage)) {
                // Written whenever there is a comment at all, empty or not: the empty one
                // is a thing the specification says, and it has to come back.
                if (field.getComment() != null) {
                    tags.add("@" + tagOf(stage) + "param " + field.getName() + ": "
                            + field.getComment());
                }
            }
        }
        for (ErrorReference error : operation.getErrors()) {
            String name = errorName(area, error);
            if (error.getComment() != null) {
                tags.add("@error " + name + ": " + error.getComment());
            }
            Field extra = error.getExtraInformation();
            if (extra != null && extra.getComment() != null) {
                tags.add("@errorinfo " + name + ": " + extra.getComment());
            }
        }
        return tags;
    }

    private void writeDataTypes(SourceFormatter out, Area area, Service service,
            List<TypeDefinition> types) {
        for (TypeDefinition type : types) {
            out.blank();
            if (type instanceof FundamentalType) {
                writeFundamental(out, area, service, (FundamentalType) type);
            } else if (type instanceof AttributeType) {
                writeAttribute(out, (AttributeType) type);
            } else if (type instanceof EnumerationType) {
                writeEnumeration(out, (EnumerationType) type);
            } else if (type instanceof CompositeType) {
                writeComposite(out, area, service, (CompositeType) type);
            }
        }
    }

    private void writeFundamental(SourceFormatter out, Area area, Service service,
            FundamentalType type) {
        out.doc(type.getComment());
        String head = "fundamental " + MOSpecTypes.identifier(type.getName());
        if (type.getSuperType() != null) {
            head += " extends " + MOSpecTypes.of(area, service, type.getSuperType());
        }
        out.line(head);
    }

    private void writeAttribute(SourceFormatter out, AttributeType type) {
        out.doc(type.getComment());
        out.line("attribute " + MOSpecTypes.identifier(type.getName())
                + " [" + type.getShortFormPart() + "]");
    }

    private void writeEnumeration(SourceFormatter out, EnumerationType type) {
        out.doc(type.getComment());
        out.line("enumeration " + MOSpecTypes.identifier(type.getName())
                + " [" + type.getShortFormPart() + "] {");
        out.in();
        for (EnumerationItem item : type.getItems()) {
            out.doc(item.getComment());
            out.line(MOSpecTypes.identifier(item.getValue())
                    + " [" + item.getNumericValue() + "]");
        }
        out.out();
        out.line("}");
    }

    private void writeComposite(SourceFormatter out, Area area, Service service,
            CompositeType type) {
        out.doc(type.getComment());
        StringBuilder head = new StringBuilder();
        if (type.isAbstract()) {
            head.append("abstract ");
        }
        head.append("composite ").append(MOSpecTypes.identifier(type.getName()));
        if (type.getShortFormPart() != null) {
            head.append(" [").append(type.getShortFormPart()).append(']');
        }
        if (type.getSuperType() != null) {
            head.append(" extends ").append(MOSpecTypes.of(area, service, type.getSuperType()));
        }
        out.line(head.append(" {").toString());
        out.in();
        for (Field field : type.getFields()) {
            out.doc(field.getComment());
            out.line(MOSpecTypes.field(area, service, field));
        }
        out.out();
        out.line("}");
    }

    private void writeErrorDefinitions(SourceFormatter out, List<ErrorDefinition> errors) {
        for (ErrorDefinition error : errors) {
            out.blank();
            out.doc(error.getComment());
            out.line("error " + MOSpecTypes.identifier(error.getName())
                    + " [" + error.getNumber() + "]");
        }
    }

    /**
     * What the service adds to the COM: the objects it stores, the events it raises, and
     * how it uses the archive and the activity tracking.
     */
    private void writeCom(SourceFormatter out, Area area, Service service, COMFeatures com) {
        out.line("com {");
        out.in();
        writeDiagramReferences(out, com.getDocumentation().getDiagrams());
        writeDocSections(out, com.getDocumentation().getSections());

        if (com.declaresObjects()) {
            out.blank();
            out.doc(com.getObjectsComment());
            out.line("objects {");
            out.in();
            for (COMObject object : com.getObjects()) {
                out.blank();
                writeComObject(out, area, service, "object", object);
            }
            out.out();
            out.line("}");
        }
        if (com.declaresEvents()) {
            out.blank();
            out.doc(com.getEventsComment());
            out.line("events {");
            out.in();
            for (COMObject event : com.getEvents()) {
                out.blank();
                writeComObject(out, area, service, "event", event);
            }
            out.out();
            out.line("}");
        }
        if (com.declaresArchiveUsage()) {
            out.blank();
            out.doc(com.getArchiveUsage());
            out.line("archiveUsage");
        }
        if (com.declaresActivityUsage()) {
            out.blank();
            out.doc(com.getActivityUsage());
            out.line("activityUsage");
        }
        out.out();
        out.line("}");
    }

    /**
     * One COM object or event. The braces are left off where it links to nothing, which is
     * most of them.
     */
    private void writeComObject(SourceFormatter out, Area area, Service service,
            String keyword, COMObject object) {
        out.doc(object.getComment());
        StringBuilder head = new StringBuilder(keyword);
        head.append(' ').append(MOSpecTypes.identifier(object.getName()))
                .append(" [").append(object.getNumber()).append(']');
        if (object.getBodyType() != null) {
            head.append(" (").append(MOSpecTypes.of(area, service, object.getBodyType()))
                    .append(')');
        }

        boolean hasLinks = object.getRelated() != null || object.getSource() != null;
        if (!hasLinks) {
            out.line(head.toString());
            return;
        }
        out.line(head.append(" {").toString());
        out.in();
        writeLink(out, area, "related", object.getRelated());
        writeLink(out, area, "source", object.getSource());
        out.out();
        out.line("}");
    }

    /**
     * A link to another COM object, by name where the target is known and by number where
     * it is not - a specification whose target area is absent still has to round-trip.
     */
    private void writeLink(SourceFormatter out, Area area, String keyword, ObjectLink link) {
        if (link == null) {
            return;
        }
        out.doc(link.getComment());
        ObjectReference target = link.getTarget();
        if (target == null) {
            out.line(keyword + " -");
            return;
        }
        StringBuilder written = new StringBuilder(keyword).append(' ');
        if (!area.getName().equals(target.getArea())) {
            written.append(target.getArea()).append("::");
        }
        if (target.getService() != null && !target.getService().isEmpty()) {
            written.append(target.getService());
        }
        out.line(written.append('#').append(target.getNumber()).toString());
    }

    private void writeDocSections(SourceFormatter out, List<DocSection> sections) {
        if (mode == DocMode.SUPPRESS) {
            return;
        }
        for (DocSection section : sections) {
            out.blank();
            String head = "doc \"" + section.getName() + "\" [" + section.getOrder() + "]";
            if (section.getContent() == null || section.getContent().isEmpty()) {
                out.line(head);
                continue;
            }
            out.line(head + " \"\"\"");
            out.in();
            for (String written : section.getContent().split("\n", -1)) {
                out.line(written);
            }
            out.out();
            out.line("\"\"\"");
        }
    }

    private void writeDiagramReferences(SourceFormatter out, List<Diagram> diagrams) {
        for (Diagram diagram : diagrams) {
            out.blank();
            out.line("diagram " + MOSpecTypes.identifier(diagram.getName())
                    + " \"" + diagram.getName() + ".svg\"");
        }
    }

    private void writeDiagrams(Path outputDir, List<Diagram> diagrams) throws IOException {
        for (Diagram diagram : diagrams) {
            write(outputDir.resolve(diagram.getName() + ".svg"), diagram.getSvg());
        }
    }

    private static void write(Path file, String content) throws IOException {
        Writer out = new OutputStreamWriter(Files.newOutputStream(file), UTF8);
        try {
            out.write(content == null ? "" : content);
        } finally {
            out.close();
        }
    }

    // ------------------------------------------------------------- the stages

    private static String patternOf(Operation operation) {
        return operation.getPattern().name().toLowerCase();
    }

    /**
     * @return the stage the consumer starts the interaction with.
     */
    private static InteractionStage firstStageOf(Operation operation) {
        switch (operation.getPattern()) {
            case SEND:
                return InteractionStage.SEND;
            case SUBMIT:
                return InteractionStage.SUBMIT;
            case REQUEST:
                return InteractionStage.REQUEST;
            case INVOKE:
                return InteractionStage.INVOKE;
            case PROGRESS:
                return InteractionStage.PROGRESS;
            case PUBSUB:
                return InteractionStage.SUBSCRIPTION_KEYS;
            default:
                return null;
        }
    }

    /**
     * @return the stages the provider answers with, in the order they travel.
     */
    private static List<InteractionStage> replyStagesOf(Operation operation) {
        List<InteractionStage> stages = new ArrayList<InteractionStage>();
        switch (operation.getPattern()) {
            case REQUEST:
                stages.add(InteractionStage.RESPONSE);
                break;
            case INVOKE:
                stages.add(InteractionStage.ACK);
                stages.add(InteractionStage.RESPONSE);
                break;
            case PROGRESS:
                stages.add(InteractionStage.ACK);
                stages.add(InteractionStage.UPDATE);
                stages.add(InteractionStage.RESPONSE);
                break;
            case PUBSUB:
                stages.add(InteractionStage.PUBLISH_NOTIFY);
                break;
            default:
                break;
        }
        return stages;
    }

    private static List<InteractionStage> allStagesOf(Operation operation) {
        List<InteractionStage> stages = new ArrayList<InteractionStage>();
        InteractionStage first = firstStageOf(operation);
        if (first != null) {
            stages.add(first);
        }
        stages.addAll(replyStagesOf(operation));
        return stages;
    }

    /**
     * @return true where the stage may be sent more than once, which only the update of a
     * progress is.
     */
    private static boolean repeats(Operation operation, InteractionStage stage) {
        return operation.getPattern() == InteractionPattern.PROGRESS
                && stage == InteractionStage.UPDATE;
    }

    /**
     * @return the tag that documents a field of this stage, which is the stage's own name.
     */
    private static String tagOf(InteractionStage stage) {
        if (stage == InteractionStage.PUBLISH_NOTIFY) {
            return "publish";
        }
        if (stage == InteractionStage.SUBSCRIPTION_KEYS) {
            return "subscriptionkeys";
        }
        return stage.name().toLowerCase();
    }

    private static String errorName(Area area, ErrorReference error) {
        TypeRef ref = error.getError();
        StringBuilder buf = new StringBuilder();
        if (ref.getArea() != null && !ref.getArea().equals(area.getName())) {
            buf.append(ref.getArea()).append("::");
        }
        if (ref.getService() != null && !ref.getService().isEmpty()) {
            buf.append(ref.getService()).append('.');
        }
        return buf.append(MOSpecTypes.identifier(ref.getName())).toString();
    }

    private static List<Field> fieldsOf(Operation operation, InteractionStage stage) {
        MessageBody body = operation.getMessage(stage);
        return body == null ? new ArrayList<Field>() : body.getFields();
    }

    private static String join(List<String> parts, String separator) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < parts.size(); i++) {
            buf.append(i == 0 ? "" : separator).append(parts.get(i));
        }
        return buf.toString();
    }
}
