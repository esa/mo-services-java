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
package esa.mo.apigen.generators.docx;

import esa.mo.apigen.generators.Generator;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.CapabilitySet;
import esa.mo.apigen.model.ErrorDefinition;
import esa.mo.apigen.model.ErrorReference;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionPattern;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.com.ObjectReference;
import esa.mo.apigen.model.docs.DocSection;
import esa.mo.apigen.model.types.AttributeType;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.EnumerationItem;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.FundamentalType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes the Word document that describes a specification.
 * <p>
 * One document per area: what the area is, then a chapter per service and a section per
 * operation, then the data types and the errors. The data types are built up in a second
 * body as the services are walked and appended at the end, which is why the document is
 * assembled rather than written straight through.
 */
public final class DocxGenerator implements Generator {

    /**
     * The blue every table header is filled with.
     */
    static final String HEADER_COLOUR = "00CCFF";

    /**
     * The grey a fixed part of a message is filled with, as against a field the
     * specification chose.
     */
    static final String FIXED_COLOUR = "E0E0E0";

    private static final int[] SERVICE_OVERVIEW_WIDTHS = {2250, 2801, 1382, 1185, 1382};

    private static final int[] ERROR_TABLE_WIDTHS = {2302, 1430, 5268};

    private static final int[] OPERATION_OVERVIEW_WIDTHS = {2200, 1700, 800, 4300};

    private static final int[] OPERATION_ERROR_WIDTHS = {1500, 1000, 2500, 4000};

    /**
     * What is shown where an error carries no extra information.
     */
    private static final String NOT_USED = "Not Used";

    private static final int[] LIST_WIDTHS = {2302, 6698};

    private static final int[] ENUM_WIDTHS = {2302, 2430, 4268};

    private static final int[] COMPOSITE_WIDTHS = {2302, 1830, 1100, 3768};

    private static final int[] COM_TYPES_WIDTHS = {1010, 2250, 2500, 1150, 2196};

    @Override
    public String getShortName() {
        return "docx";
    }

    @Override
    public String getDescription() {
        return "Generates the Word document of a set of MO service specifications";
    }

    @Override
    public void generate(MOModel model, List<Area> targets, Path outputDir) throws IOException {
        for (Area area : targets) {
            generateArea(model, area, outputDir);
        }
    }

    /**
     * @return the name of the document of an area, which says what it documents.
     */
    public static String documentNameOf(Area area) {
        return String.format("Service_Specification_area%03d-v%03d-%s",
                area.getNumber(), area.getVersion(), area.getName());
    }

    private void generateArea(MOModel model, Area area, Path outputDir) throws IOException {
        File folder = new File(outputDir.toFile(), documentNameOf(area));
        DocxDocument document = new DocxDocument(folder);
        DocxBody body = document.getBody();
        // The data types are described after everything else, but which of them matter is
        // only known once the services have been walked, so they are gathered as we go.
        DocxBody types = new DocxBody();

        body.title(1, "Specification: " + area.getName());
        body.title(2, "General");
        body.comment("This section contains the specifications for the "
                + area.getName() + " services.");
        body.comment(area.getComment());
        body.comment("This area contains the following services:");

        List<String> names = new ArrayList<String>();
        for (Service service : area.getServices()) {
            names.add(service.getName());
        }
        body.numberedComment(names);

        for (DocSection section : area.getDocumentation().getSections()) {
            body.title(2, section.getName());
            body.numberedComment(DocxText.split(section.getContent()));
        }

        for (Service service : area.getServices()) {
            writeService(body, document, model, area, service);
        }

        writeDataTypes(types, model, area);
        writeErrors(types, area);

        body.append(types);
        document.write();
    }

    /**
     * A chapter per service: what it is, what it must do, and a section per operation.
     */
    private void writeService(DocxBody body, DocxDocument document, MOModel model, Area area,
            Service service) throws IOException {
        body.pageBreak();
        body.title(2, "Service: ", service.getName(), "SERVICE");
        body.title(3, "Overview");
        body.comment(service.getComment());
        writeServiceTable(body, area, service);
        body.title(3, "Service-level Requirements");
        writeRequirements(body, service.getDocumentation().getSections());
        writeComUsage(body, document, area, service);

        for (CapabilitySet set : service.getCapabilitySets()) {
            for (Operation operation : set.getOperations()) {
                body.pageBreak();
                // The bookmark is named after the service number rather than its name: a
                // long bookmark name is cut short and the link stops working.
                body.title(3, "Operation: ", operation.getName(),
                        "OPERATION_" + service.getNumber());
                body.title(4, "Overview");
                body.comment(operation.getComment());
                writeOperationTable(body, area, service, operation);
                writeSignatureDetails(body, operation);
                body.title(4, "Requirements");
                writeRequirements(body, operation.getDocumentation().getSections());
                writeOperationErrors(body, model, area, service, operation);
            }
        }
    }

    /**
     * What the service is numbered, and every operation it offers.
     */
    private void writeServiceTable(DocxBody body, Area area, Service service) {
        DocxTable table = body.table(SERVICE_OVERVIEW_WIDTHS,
                service.getName() + " Service Operations");
        table.row()
                .cell("Area Identifier").shaded(HEADER_COLOUR).centered().next()
                .cell("Service Identifier").shaded(HEADER_COLOUR).centered().next()
                .cell("Area Number").shaded(HEADER_COLOUR).centered().next()
                .cell("Service Number").shaded(HEADER_COLOUR).centered().next()
                .cell("Area Version").shaded(HEADER_COLOUR).centered().endRow();
        table.row()
                .cell(area.getName()).centered().next()
                .cell(service.getName()).centered().next()
                .cell(String.valueOf(area.getNumber())).centered().next()
                .cell(String.valueOf(service.getNumber())).centered().next()
                .cell(String.valueOf(area.getVersion())).centered().endRow();
        table.row()
                .cell("Interaction Pattern").shaded(HEADER_COLOUR).centered().next()
                .cell("Operation Identifier").shaded(HEADER_COLOUR).centered().spanning(2).next()
                .cell("Operation Number").shaded(HEADER_COLOUR).centered().next()
                .cell("Capability Set").shaded(HEADER_COLOUR).centered().endRow();

        for (CapabilitySet set : service.getCapabilitySets()) {
            // The capability set number is written once for the run of operations that
            // belong to it, so the cell of the first operation is merged over the rest.
            boolean first = true;
            for (Operation operation : set.getOperations()) {
                String linkTo = "OPERATION_" + service.getNumber() + "_" + operation.getName();
                DocxRow row = table.row();
                row.cell(shortPatternNameOf(operation)).centered().next()
                        .markedUp(DocxTypeLink.hyperlink("", operation.getName(), "", linkTo, true))
                        .centered().spanning(2).next()
                        .cell(String.valueOf(operation.getNumber())).centered().next();
                DocxCell capability = row.cell(String.valueOf(set.getNumber())).centered();
                if (first) {
                    capability.mergeStart();
                } else {
                    capability.mergeContinue();
                }
                capability.endRow();
                first = false;
            }
        }
        table.end();
    }

    /**
     * What the service adds to the COM: the objects it stores, the events it raises, and
     * how it uses the archive and the activity tracking.
     */
    private void writeComUsage(DocxBody body, DocxDocument document, Area area, Service service)
            throws IOException {
        COMFeatures com = service.getCom();
        if (com == null) {
            return;
        }

        // An objects section counts as saying something only when it lists something; an
        // events section counts as soon as it is written at all. The difference is in the
        // existing generator and decides which relationships the figure is said to show.
        boolean hasObjects = !com.getObjects().isEmpty();
        boolean hasEvents = com.declaresEvents();

        if (com.declaresObjects()) {
            body.title(3, "COM usage");
            body.numberedComment(DocxText.split(com.getObjectsComment()));
            if (hasObjects) {
                writeComObjects(body, area, service, com.getObjects(),
                        service.getName() + " Service Object Types", "Object Name");
            }
        }
        if (hasEvents) {
            body.title(3, "COM Event Service usage");
            body.numberedComment(DocxText.split(com.getEventsComment()));
            writeComObjects(body, area, service, com.getEvents(),
                    service.getName() + " Service Events", "Event Name");
        }

        if (hasObjects || hasEvents) {
            String what = "COM" + (hasObjects ? " object" : "")
                    + (hasEvents ? (hasObjects ? " and event" : " event") : "") + " relationships";
            body.title(3, "COM Object Relationships");
            body.comment("The Figure below shows the " + what + " for this service:");
            body.figureCaption(service.getName() + " Service " + what);
        }

        if (com.getArchiveUsage() != null) {
            body.title(3, "COM Archive Service usage");
            body.numberedComment(DocxText.split(com.getArchiveUsage()));
        }
        if (com.getActivityUsage() != null) {
            body.title(3, "COM Activity Service usage");
            body.numberedComment(DocxText.split(com.getActivityUsage()));
        }
    }

    /**
     * The objects or the events of a service, which are described the same way.
     */
    private void writeComObjects(DocxBody body, Area area, Service service,
            List<COMObject> objects, String caption, String nameColumn) {
        DocxTable table = body.table(COM_TYPES_WIDTHS, caption);
        table.row()
                .cell("Object Number").shaded(HEADER_COLOUR).centered().next()
                .cell(nameColumn).shaded(HEADER_COLOUR).centered().next()
                .cell("Object Body Type").shaded(HEADER_COLOUR).centered().next()
                .cell("Related link").shaded(HEADER_COLOUR).centered().next()
                .cell("Source link").shaded(HEADER_COLOUR).centered().endRow();

        for (COMObject object : objects) {
            DocxRow row = table.row();
            row.cell(String.valueOf(object.getNumber())).centered().next()
                    .cell(object.getName()).centered().next();
            if (object.getBodyType() == null) {
                row.cell("No body").centered().next();
            } else {
                row.markedUp(DocxTypeLink.forType(area, service, object.getBodyType()))
                        .centered().next();
            }
            row.cell(linkText(area, service, object.getRelated())).centered().next()
                    .cell(linkText(area, service, object.getSource())).centered().endRow();
        }
        table.end();
    }

    /**
     * What a link to another object says: the object it points at, whatever the
     * specification said instead, or that there is nothing there.
     */
    private static String linkText(Area area, Service service, ObjectLink link) {
        if (link == null) {
            return "Set to NULL";
        }
        if (link.getTarget() != null) {
            ObjectReference target = link.getTarget();
            StringBuilder buf = new StringBuilder();
            if (!area.getName().equalsIgnoreCase(target.getArea())) {
                buf.append(target.getArea()).append("::");
            }
            String owning = service == null ? "" : service.getName();
            if (target.getService() != null && !target.getService().isEmpty()
                    && !target.getService().equalsIgnoreCase(owning)) {
                buf.append(target.getService()).append("::");
            }
            return buf.append(target.getNumber()).toString();
        }
        return link.getComment() != null ? link.getComment() : "Not specified";
    }

    /**
     * What one operation exchanges: the pattern it follows, and a row per message with the
     * fields it carries.
     */
    private void writeOperationTable(DocxBody body, Area area, Service service,
            Operation operation) {
        DocxTable table = body.table(OPERATION_OVERVIEW_WIDTHS);
        table.row()
                .cell("Operation Identifier").shaded(HEADER_COLOUR).centered().next()
                .cell(operation.getName()).centered().spanning(3).endRow();

        table.row()
                .cell("Interaction Pattern").shaded(HEADER_COLOUR).centered().next()
                .cell(patternNameOf(operation)).shaded(FIXED_COLOUR).centered().spanning(3)
                .endRow();

        // Only where the operation says something about its keys: an operation that
        // declares none at all is not the same as one that declares an empty set.
        if (operation.getPattern() == InteractionPattern.PUBSUB
                && operation.getMessage(InteractionStage.SUBSCRIPTION_KEYS) != null) {
            writeSubscriptionKeys(table, area, service, operation);
        }

        table.row()
                .cell("Pattern Sequence").shaded(HEADER_COLOUR).centered().next()
                .cell("Message").shaded(HEADER_COLOUR).centered().next()
                .cell("Nullable").shaded(HEADER_COLOUR).centered().next()
                .cell("Type Signature").shaded(HEADER_COLOUR).centered().endRow();

        for (Message message : messagesOf(operation)) {
            writeMessage(table, area, service, message);
        }
        table.end();
    }

    /**
     * The names a subscription is keyed by, which a publish-subscribe operation states
     * before the messages themselves.
     */
    private void writeSubscriptionKeys(DocxTable table, Area area, Service service,
            Operation operation) {
        List<Field> keys = fieldsOf(operation, InteractionStage.SUBSCRIPTION_KEYS);
        DocxRow row = table.row();
        row.cell("Subscription Keys").shaded(HEADER_COLOUR).centered().next();
        if (keys.isEmpty()) {
            row.cell("Empty").shaded(FIXED_COLOUR).centered().spanning(3).endRow();
        } else {
            row.markedUp(signature(area, service, keys)).centered().spanning(3).endRow();
        }
    }

    /**
     * One message of an interaction: which way it goes, what it is called, and what it
     * carries.
     */
    private void writeMessage(DocxTable table, Area area, Service service, Message message) {
        DocxRow row = table.row();
        row.cell(message.incoming ? "IN" : "OUT").shaded(FIXED_COLOUR).centered().next()
                .cell(message.name).shaded(FIXED_COLOUR).centered().next();
        if (message.fields.isEmpty()) {
            row.cell("-").shaded(FIXED_COLOUR).centered().next()
                    .cell("-").shaded(FIXED_COLOUR).centered().endRow();
        } else {
            row.markedUp(DocxTypeLink.nullability(message.fields)).centered().next()
                    .markedUp(signature(area, service, message.fields)).centered().endRow();
        }
    }

    /**
     * @return the fields of a message, each named and linked to its type.
     */
    private static String signature(Area area, Service service, List<Field> fields) {
        List<String> parts = new ArrayList<String>();
        for (Field field : fields) {
            parts.add(DocxTypeLink.forField(area, service, field));
        }
        return DocxTypeLink.join(parts);
    }

    /**
     * The messages of an interaction, in the order they are exchanged, each knowing which
     * way it travels and what the document calls it.
     */
    private static List<Message> messagesOf(Operation operation) {
        List<Message> messages = new ArrayList<Message>();
        switch (operation.getPattern()) {
            case SEND:
                messages.add(new Message(true, "SEND", InteractionStage.SEND,
                        fieldsOf(operation, InteractionStage.SEND)));
                break;
            case SUBMIT:
                messages.add(new Message(true, "SUBMIT", InteractionStage.SUBMIT,
                        fieldsOf(operation, InteractionStage.SUBMIT)));
                break;
            case REQUEST:
                messages.add(new Message(true, "REQUEST", InteractionStage.REQUEST,
                        fieldsOf(operation, InteractionStage.REQUEST)));
                messages.add(new Message(false, "RESPONSE", InteractionStage.RESPONSE,
                        fieldsOf(operation, InteractionStage.RESPONSE)));
                break;
            case INVOKE:
                messages.add(new Message(true, "INVOKE", InteractionStage.INVOKE,
                        fieldsOf(operation, InteractionStage.INVOKE)));
                messages.add(new Message(false, "ACK", InteractionStage.ACK,
                        fieldsOf(operation, InteractionStage.ACK)));
                messages.add(new Message(false, "RESPONSE", InteractionStage.RESPONSE,
                        fieldsOf(operation, InteractionStage.RESPONSE)));
                break;
            case PROGRESS:
                messages.add(new Message(true, "PROGRESS", InteractionStage.PROGRESS,
                        fieldsOf(operation, InteractionStage.PROGRESS)));
                messages.add(new Message(false, "ACK", InteractionStage.ACK,
                        fieldsOf(operation, InteractionStage.ACK)));
                messages.add(new Message(false, "UPDATE", InteractionStage.UPDATE,
                        fieldsOf(operation, InteractionStage.UPDATE)));
                messages.add(new Message(false, "RESPONSE", InteractionStage.RESPONSE,
                        fieldsOf(operation, InteractionStage.RESPONSE)));
                break;
            case PUBSUB:
                messages.add(new Message(false, "PUBLISH", InteractionStage.PUBLISH_NOTIFY,
                        fieldsOf(operation, InteractionStage.PUBLISH_NOTIFY)));
                break;
            default:
                break;
        }
        return messages;
    }

    /**
     * @return what the operation's own table calls this interaction pattern.
     */
    private static String patternNameOf(Operation operation) {
        return operation.getPattern() == InteractionPattern.PUBSUB
                ? "PUBLISH-SUBSCRIBE" : operation.getPattern().name();
    }

    /**
     * @return what the service overview table calls this interaction pattern, which is the
     * same as above except that the column is narrow enough to want it shortened.
     */
    private static String shortPatternNameOf(Operation operation) {
        return operation.getPattern() == InteractionPattern.PUBSUB
                ? "PUB-SUB" : operation.getPattern().name();
    }

    private static List<Field> fieldsOf(Operation operation, InteractionStage stage) {
        MessageBody body = operation.getMessage(stage);
        return body == null ? new ArrayList<Field>() : body.getFields();
    }

    /**
     * One error an operation may answer with, gathered from the reference that names it.
     * <p>
     * The specifications only ever reference an error from an operation, never define one
     * there, so the definition is looked up to find its number - and an error of another
     * area is named rather than numbered, since its number is that area's business.
     */
    private static final class OperationError implements Comparable<OperationError> {

        private final String name;
        private final String number;

        /**
         * What the error sorts on. Usually its number, but an error of another area sorts
         * as zero and one whose number could not be found sorts under the text that says
         * so - which is how the reference output orders them.
         */
        private final String order;
        private final List<String> comments;
        private final String extraType;
        private final String extraDescription;

        private OperationError(String name, String number, String order, List<String> comments,
                String extraType, String extraDescription) {
            this.name = name;
            this.number = number;
            this.order = order;
            this.comments = comments;
            this.extraType = extraType;
            this.extraDescription = extraDescription;
        }

        private static OperationError of(MOModel model, Area area, Service service,
                ErrorReference reference) {
            String name = reference.getError().getName();
            String number = "UNKNOWN ERROR NUMBER!";
            String order;

            String declaredIn = reference.getError().getArea();
            if (declaredIn == null || declaredIn.equals(area.getName())) {
                ErrorDefinition definition = model.resolveError(reference.getError());
                if (definition != null) {
                    number = String.valueOf(definition.getNumber());
                }
                order = number;
            } else {
                number = "Defined in " + declaredIn;
                order = "0";
            }

            String extraType = NOT_USED;
            String extraDescription = "-";
            Field extra = reference.getExtraInformation();
            if (extra != null) {
                extraType = qualify(area, service, extra.getType());
                if (extra.getComment() != null) {
                    extraDescription = extra.getComment();
                }
            }
            return new OperationError(name, number, order,
                    DocxText.split(reference.getComment()), extraType, extraDescription);
        }

        /**
         * @return the error named and described, a line per thing said about it.
         */
        private List<String> describe() {
            List<String> lines = new ArrayList<String>();
            if (comments.isEmpty()) {
                lines.add(name + ": Not described");
                return lines;
            }
            for (String comment : comments) {
                lines.add(name + ": " + comment);
            }
            return lines;
        }

        @Override
        public int compareTo(OperationError other) {
            try {
                return Long.valueOf(order).compareTo(Long.valueOf(other.order));
            } catch (NumberFormatException ex) {
                // One of them is not a number at all, so they are ordered as text.
                return order.compareTo(other.order);
            }
        }
    }

    /**
     * Names a type from where it is mentioned, leaving off the area and service that are
     * being written about.
     */
    private static String qualify(Area area, Service service, esa.mo.apigen.model.types.TypeRef type) {
        StringBuilder buf = new StringBuilder();
        if (!area.getName().equalsIgnoreCase(type.getArea())) {
            buf.append(type.getArea()).append("::");
        }
        String owning = service == null ? "" : service.getName();
        if (type.getService() != null && !type.getService().isEmpty()
                && !type.getService().equalsIgnoreCase(owning)) {
            buf.append(type.getService()).append("::");
        }
        buf.append(type.getName());
        // The extra information of an error is named in full, list wrapper and all: unlike
        // a message field, there is no separate column to put it in.
        return type.isList() ? "List<" + buf + ">" : buf.toString();
    }

    /**
     * One message of an interaction, as the table describes it.
     */
    private static final class Message {

        private final boolean incoming;
        private final String name;
        private final InteractionStage stage;
        private final List<Field> fields;

        private Message(boolean incoming, String name, InteractionStage stage,
                List<Field> fields) {
            this.incoming = incoming;
            this.name = name;
            this.stage = stage;
            this.fields = fields;
        }
    }

    /**
     * The errors an operation may answer with: named and described, then tabulated with
     * whatever extra information each one carries.
     */
    private void writeOperationErrors(DocxBody body, MOModel model, Area area, Service service,
            Operation operation) throws IOException {
        body.title(4, "MO Errors");

        if (operation.getPattern() == InteractionPattern.SEND) {
            // A send is not answered at all, so there is nowhere to put an error.
            body.comment("The operation cannot return any errors.");
            return;
        }
        if (operation.getErrors().isEmpty()) {
            body.comment("The operation does not return any errors.");
            return;
        }

        body.comment(operation.getErrors().size() == 1
                ? "The operation may return the following error:"
                : "The operation may return one of the following errors:");

        List<OperationError> errors = new ArrayList<OperationError>();
        for (ErrorReference reference : operation.getErrors()) {
            errors.add(OperationError.of(model, area, service, reference));
        }
        java.util.Collections.sort(errors);

        List<String> described = new ArrayList<String>();
        for (OperationError error : errors) {
            described.addAll(error.describe());
        }
        body.numberedComment(described);

        DocxTable table = body.table(OPERATION_ERROR_WIDTHS);
        table.row()
                .cell("Error").shaded(HEADER_COLOUR).centered().next()
                .cell("Error #").shaded(HEADER_COLOUR).centered().next()
                .cell("ExtraInfo Type").shaded(HEADER_COLOUR).centered().next()
                .cell("ExtraInfo description").shaded(HEADER_COLOUR).centered().endRow();

        for (OperationError error : errors) {
            DocxRow row = table.row();
            row.cell(error.name).centered().next()
                    .cell(error.number).centered().next();
            // A type of this area is linked to where it is defined; one that names its own
            // area, or none at all, is only shown.
            if (error.extraType.contains("::") || NOT_USED.equals(error.extraType)) {
                row.cell(error.extraType.replace("MAL::", "")).centered().next();
            } else {
                row.markedUp(DocxTypeLink.hyperlink("", error.extraType, "",
                        "DATATYPE_" + error.extraType, true)).centered().next();
            }
            row.cell(error.extraDescription).centered().endRow();
        }
        table.end();
    }

    /**
     * Describes every field of every message, so that the table above can stay to the
     * types and the names.
     */
    private void writeSignatureDetails(DocxBody body, Operation operation) {
        body.title(4, "Type Signature Details");
        for (InteractionStage stage : detailedStagesOf(operation)) {
            for (Field field : fieldsOf(operation, stage)) {
                body.singleTypeSignature(field.getName(), field.getComment());
            }
        }
    }

    /**
     * The stages whose fields are described one by one. A publish-subscribe operation
     * describes its subscription keys here as well, which the table above only lists.
     */
    private static List<InteractionStage> detailedStagesOf(Operation operation) {
        List<InteractionStage> stages = new ArrayList<InteractionStage>();
        if (operation.getPattern() == InteractionPattern.PUBSUB) {
            stages.add(InteractionStage.SUBSCRIPTION_KEYS);
            stages.add(InteractionStage.PUBLISH_NOTIFY);
            return stages;
        }
        for (Message message : messagesOf(operation)) {
            stages.add(message.stage);
        }
        return stages;
    }

    /**
     * The requirements a service states, numbered as one list however many sections they
     * are written in: they are the requirements of the service, not of its sections.
     */
    private void writeRequirements(DocxBody body, List<DocSection> sections) throws IOException {
        List<String> requirements = new ArrayList<String>();
        for (DocSection section : sections) {
            requirements.addAll(DocxText.split(section.getContent()));
        }
        body.numberedComment(requirements);
    }

    /**
     * Every type the specification declares, area level first and then service by service.
     */
    private void writeDataTypes(DocxBody types, MOModel model, Area area) throws IOException {
        types.pageBreak();
        types.title(1, "Data types");
        boolean any = !area.getDataTypes().isEmpty();

        if (any) {
            types.title(2, "Area data types: " + area.getName());
            for (TypeDefinition type : area.getDataTypes()) {
                writeDataType(types, model, area, null, type);
            }
        }
        for (Service service : area.getServices()) {
            if (!service.getDataTypes().isEmpty()) {
                any = true;
                types.title(2, "Service data types: " + service.getName());
                for (TypeDefinition type : service.getDataTypes()) {
                    writeDataType(types, model, area, service, type);
                }
            }
        }
        if (!any) {
            types.comment("No data types are defined in this specification.");
        }
    }

    /**
     * One declared type, described according to what kind of thing it is.
     */
    private void writeDataType(DocxBody types, MOModel model, Area area, Service service,
            TypeDefinition type) {
        if (type instanceof FundamentalType) {
            types.title(3, "Fundamental: ", type.getName(), "DATATYPE");
            if (type.getComment() != null && !type.getComment().isEmpty()) {
            types.comment(type.getComment());
        }
        } else if (type instanceof AttributeType) {
            writeAttribute(types, (AttributeType) type);
        } else if (type instanceof EnumerationType) {
            writeEnumeration(types, (EnumerationType) type);
        } else if (type instanceof CompositeType) {
            writeComposite(types, model, area, service, (CompositeType) type);
        }
    }

    /**
     * An attribute is one of the types everything else is built from, so there is little to
     * say beyond what it is called and numbered.
     */
    private void writeAttribute(DocxBody types, AttributeType attribute) {
        types.title(3, "Attribute: ", attribute.getName(), "DATATYPE");
        if (attribute.getComment() != null && !attribute.getComment().isEmpty()) {
            types.comment(attribute.getComment());
        }

        DocxTable table = types.table(LIST_WIDTHS);
        table.row().cell("Name").shaded(HEADER_COLOUR).centered().next()
                .cell(attribute.getName()).centered().endRow();
        table.row().cell("Extends").shaded(HEADER_COLOUR).centered().next()
                .cell("Attribute").centered().endRow();
        table.row().cell("Short Form Part").shaded(HEADER_COLOUR).centered().next()
                .cell(String.valueOf(attribute.getShortFormPart())).centered().endRow();
        table.end();
        types.sectionBreak();
    }

    /**
     * An enumeration is its values, each with the number it travels as.
     */
    private void writeEnumeration(DocxBody types, EnumerationType enumeration) {
        types.title(3, "Enumeration: ", enumeration.getName(), "DATATYPE");
        if (enumeration.getComment() != null && !enumeration.getComment().isEmpty()) {
            types.comment(enumeration.getComment());
        }

        DocxTable table = types.table(ENUM_WIDTHS);
        table.row().cell("Name").shaded(HEADER_COLOUR).centered().next()
                .cell(enumeration.getName()).centered().spanning(2).endRow();
        table.row().cell("Short Form Part").shaded(HEADER_COLOUR).centered().next()
                .cell(String.valueOf(enumeration.getShortFormPart())).centered().spanning(2)
                .endRow();
        table.row()
                .cell("Enumeration Value").shaded(HEADER_COLOUR).centered().next()
                .cell("Numerical Value").shaded(HEADER_COLOUR).centered().next()
                .cell("Comment").shaded(HEADER_COLOUR).centered().endRow();

        for (EnumerationItem item : enumeration.getItems()) {
            table.row()
                    .cell(item.getValue()).centered().next()
                    .cell(String.valueOf(item.getNumericValue())).centered().next()
                    .cell(item.getComment()).endRow();
        }
        table.end();
        types.sectionBreak();
    }

    /**
     * A composite is its fields, and the fields of whatever it extends before them.
     */
    private void writeComposite(DocxBody types, MOModel model, Area area, Service service,
            CompositeType composite) {
        TypeRef parent = composite.getSuperType() != null ? composite.getSuperType()
                : new TypeRef("MAL", area.getVersion(), null, "Composite", false, false);
        // A composite extending the MAL Object is an object in the COM sense: something
        // that is stored and referred to, rather than only carried in a message.
        boolean isObject = "Object".equals(parent.getName());

        types.title(3, isObject ? "MO Object: " : "Composite: ", composite.getName(), "DATATYPE");
        if (composite.getComment() != null && !composite.getComment().isEmpty()) {
            types.comment(composite.getComment());
        }

        DocxTable table = types.table(COMPOSITE_WIDTHS);
        table.row().cell("Name").shaded(HEADER_COLOUR).centered().next()
                .cell(composite.getName()).centered().spanning(3).endRow();
        table.row().cell("Extends").shaded(HEADER_COLOUR).centered().next()
                .markedUp(DocxTypeLink.forType(area, service, parent)).centered().spanning(3)
                .endRow();

        if (composite.getShortFormPart() == null) {
            table.row().cell("Abstract").shaded(HEADER_COLOUR).centered().spanning(4).endRow();
        } else {
            table.row().cell("Short Form Part").shaded(HEADER_COLOUR).centered().next()
                    .cell(String.valueOf(composite.getShortFormPart())).centered().spanning(3)
                    .endRow();
        }

        List<Field> inherited = model.inheritedFields(composite);
        List<Field> own = composite.getFields();

        if (!own.isEmpty()) {
            table.row()
                    .cell("Field").shaded(HEADER_COLOUR).centered().next()
                    .cell("Type").shaded(HEADER_COLOUR).centered().next()
                    .cell("Nullable").shaded(HEADER_COLOUR).centered().next()
                    .cell("Comment").shaded(HEADER_COLOUR).centered().endRow();

            for (Field field : inherited) {
                writeCompositeField(table, area, service, field, FIXED_COLOUR);
            }
            for (Field field : own) {
                writeCompositeField(table, area, service, field, null);
            }
        }
        table.end();
        types.sectionBreak();
    }

    /**
     * One field of a composite. A field it inherits is filled in, to set it apart from the
     * ones the composite declares itself.
     */
    private void writeCompositeField(DocxTable table, Area area, Service service, Field field,
            String shade) {
        DocxRow row = table.row();
        row.cell(field.getName()).shaded(shade).centered().next()
                .markedUp(DocxTypeLink.forType(area, service, field.getType())).shaded(shade)
                .centered().next()
                .cell(field.isCanBeNull() ? "Yes" : "No").shaded(shade).centered().next()
                .cell(field.getComment()).shaded(shade).endRow();
    }

    /**
     * Every error the specification declares, wherever it declares it.
     */
    private void writeErrors(DocxBody types, Area area) throws IOException {
        types.title(1, "MO Errors");
        List<ErrorDefinition> errors = new ArrayList<ErrorDefinition>(area.getErrors());
        for (Service service : area.getServices()) {
            errors.addAll(service.getErrors());
        }

        if (errors.isEmpty()) {
            types.comment("No errors are defined in this specification.");
            return;
        }

        types.comment("The following table lists the errors defined in this specification:");
        DocxTable table = types.table(ERROR_TABLE_WIDTHS, area.getName() + " MO Errors");
        table.row()
                .cell("Error").shaded(HEADER_COLOUR).centered().next()
                .cell("Error #").shaded(HEADER_COLOUR).centered().next()
                .cell("Comment").shaded(HEADER_COLOUR).centered().endRow();
        for (ErrorDefinition error : errors) {
            table.row()
                    .cell(error.getName()).centered().next()
                    .cell(String.valueOf(error.getNumber())).centered().next()
                    .cell(error.getComment()).endRow();
        }
        table.end();
    }
}
