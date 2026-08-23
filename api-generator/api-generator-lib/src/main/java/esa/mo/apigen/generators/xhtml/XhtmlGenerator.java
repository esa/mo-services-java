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
package esa.mo.apigen.generators.xhtml;

import esa.mo.apigen.generators.Generator;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionPattern;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.EnumerationItem;
import esa.mo.apigen.model.types.EnumerationType;
import esa.mo.apigen.model.types.FundamentalType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import java.io.IOException;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Writes a browsable page describing a specification, in an ECSS PUS style.
 * <p>
 * One page per area: what the area is, then a section per service and per operation, then
 * every data type it declares, with an index of the lot at the end. The messages and the
 * types are drawn rather than tabulated - each message once as it goes on the wire and once
 * opened out into everything it is made of - and every type named anywhere is a link to
 * where that type is described, whether that is this page or another.
 * <p>
 * This is not the generator that draws the diagrams in the Word documents. Those are SVG
 * the specification itself declares, and the document generator rasterises them; these are
 * drawn here from the model. The two share no code and no output.
 */
public final class XhtmlGenerator implements Generator {

    /**
     * What each message of an interaction is called on the page.
     */
    private static final String ARGUMENT_TITLE = "Telecommand application data:";

    private static final String ACK_TITLE = "Acknowledgement telemetry report application data:";

    private static final String UPDATE_TITLE = "Progress telemetry report application data:";

    private static final String RESPONSE_TITLE = "Response telemetry report application data:";

    private static final String NOTIFY_TITLE = "Notify telemetry report application data:";

    private static final String EVENT_TITLE = "Event additional application data:";

    @Override
    public String getShortName() {
        return "xhtml";
    }

    @Override
    public String getDescription() {
        return "Generates a navigable XHTML page of a set of MO service specifications";
    }

    @Override
    public void generate(MOModel model, List<Area> targets, Path outputDir)
            throws IOException {
        for (Area area : targets) {
            generateArea(model, area, outputDir);
        }
    }

    private void generateArea(MOModel model, Area area, Path outputDir) throws IOException {
        XhtmlBody page = new XhtmlBody();
        page.title(1, "Specification: ", XhtmlLink.anchorOf(null, null), area.getName(), false);
        page.comment(area.getComment());

        // Every type described anywhere on the page, gathered as the page is written and
        // shown in one alphabetical list at the end. The generator this replaces gathered
        // them into a hash map and wrote them out in whatever order it iterated in, which
        // is an index only in the sense that everything was in it.
        Map<String, String> index = new TreeMap<String, String>();

        List<Map.Entry<String, String>> serviceIndex = XhtmlBody.newIndex();
        XhtmlBody services = new XhtmlBody();
        for (Service service : area.getServices()) {
            writeService(services, serviceIndex, model, area, service);
        }
        page.index("Services", 2, serviceIndex);
        page.append(services);

        page.title(1, "Data types", null, "", false);
        writeDataTypes(page, index, model, area);

        List<Map.Entry<String, String>> entries = XhtmlBody.newIndex();
        for (Map.Entry<String, String> entry : index.entrySet()) {
            entries.add(entry);
        }
        page.index("Index", 1, entries);

        XhtmlPage.write(outputDir.resolve(XhtmlLink.pageOf(area)), area.getName(), page);
    }

    private void writeService(XhtmlBody body, List<Map.Entry<String, String>> serviceIndex,
            MOModel model, Area area, Service service) {
        body.title(2, "Service: ", XhtmlLink.anchorOf(service, null), service.getName(), false);
        body.comment(service.getComment());
        serviceIndex.add(entry(service.getName(), XhtmlLink.hrefWithin(service, null)));

        List<Map.Entry<String, String>> operationIndex = XhtmlBody.newIndex();
        XhtmlBody operations = new XhtmlBody();
        for (Operation operation : service.getOperations()) {
            operations.title(3, "Operation: ",
                    XhtmlLink.anchorOf(service, operation.getName()), operation.getName(), false);
            operations.comment(operation.getComment());
            operationIndex.add(entry(operation.getName(),
                    XhtmlLink.hrefWithin(service, operation.getName())));
            writeMessages(operations, model, area, operation);
        }
        body.index("Operations", 3, operationIndex);
        body.append(operations);

        writeEvents(body, model, area, service);
    }

    /**
     * Describes the events a service declares, each carrying whatever its object type says.
     */
    private void writeEvents(XhtmlBody body, MOModel model, Area area, Service service) {
        COMFeatures com = service.getCom();
        if (com == null || com.getEvents().isEmpty()) {
            return;
        }
        List<Map.Entry<String, String>> eventIndex = XhtmlBody.newIndex();
        XhtmlBody events = new XhtmlBody();
        for (COMObject event : com.getEvents()) {
            events.title(3, "Event: ", XhtmlLink.anchorOf(service, event.getName()),
                    event.getName(), false);
            eventIndex.add(entry(event.getName(),
                    XhtmlLink.hrefWithin(service, event.getName())));

            List<Field> carried = new ArrayList<Field>();
            if (event.getBodyType() != null) {
                carried.add(field(null, event.getBodyType(), false));
            }
            writeMessage(events, model, area, EVENT_TITLE, carried, event.getComment());
        }
        body.index("Events", 3, eventIndex);
        body.append(events);
    }

    /**
     * Describes each message of an operation, in the order the interaction sends them.
     */
    private void writeMessages(XhtmlBody body, MOModel model, Area area, Operation operation) {
        switch (operation.getPattern()) {
            case SEND:
                message(body, model, area, operation, ARGUMENT_TITLE, InteractionStage.SEND);
                break;
            case SUBMIT:
                message(body, model, area, operation, ARGUMENT_TITLE, InteractionStage.SUBMIT);
                break;
            case REQUEST:
                message(body, model, area, operation, ARGUMENT_TITLE, InteractionStage.REQUEST);
                message(body, model, area, operation, RESPONSE_TITLE, InteractionStage.RESPONSE);
                break;
            case INVOKE:
                message(body, model, area, operation, ARGUMENT_TITLE, InteractionStage.INVOKE);
                message(body, model, area, operation, ACK_TITLE, InteractionStage.ACK);
                message(body, model, area, operation, RESPONSE_TITLE, InteractionStage.RESPONSE);
                break;
            case PROGRESS:
                message(body, model, area, operation, ARGUMENT_TITLE, InteractionStage.PROGRESS);
                message(body, model, area, operation, ACK_TITLE, InteractionStage.ACK);
                message(body, model, area, operation, UPDATE_TITLE, InteractionStage.UPDATE);
                message(body, model, area, operation, RESPONSE_TITLE, InteractionStage.RESPONSE);
                break;
            case PUBSUB:
                MessageBody notify = operation.getMessage(InteractionStage.PUBLISH_NOTIFY);
                writeMessage(body, model, area, NOTIFY_TITLE, notifyFields(model, area, notify),
                        notify == null ? null : notify.getComment());
                break;
            default:
                break;
        }
    }

    private void message(XhtmlBody body, MOModel model, Area area, Operation operation,
            String title, InteractionStage stage) {
        MessageBody message = operation.getMessage(stage);
        writeMessage(body, model, area, title,
                message == null ? new ArrayList<Field>() : message.getFields(),
                message == null ? null : message.getComment());
    }

    /**
     * What a NOTIFY carries, which is not what the specification writes.
     * <p>
     * A specification says what a publish-subscribe operation publishes; the message that
     * arrives holds the identifier of the subscription it answers and a header for each
     * update, and carries a list of every published value rather than one of each. The
     * diagram shows the message, so it is assembled here.
     */
    private List<Field> notifyFields(MOModel model, Area area, MessageBody published) {
        List<Field> fields = new ArrayList<Field>();
        int mal = malVersionOf(area);
        // Every part of a NOTIFY is there: the message says which subscription it answers
        // and carries a header for each update, whatever the values themselves are.
        fields.add(field("subscriptionId", new TypeRef("MAL", mal, null,
                "Identifier", false, false), false));
        fields.add(field("updateHeaders", new TypeRef("MAL", mal, null,
                "UpdateHeader", true, false), false));
        if (published != null) {
            for (Field declared : published.getFields()) {
                TypeRef type = declared.getType().unwrapped();
                fields.add(field(declared.getName(), new TypeRef(type.getArea(),
                        type.getAreaVersion(), type.getService(), type.getName(), true, false),
                        false));
            }
        }
        return fields;
    }

    /**
     * Describes one message: what it carries, drawn as it goes on the wire and drawn again
     * opened out, then what each field of it is.
     */
    private void writeMessage(XhtmlBody body, MOModel model, Area area, String title,
            List<Field> fields, String comment) {
        body.title(4, title, null, fields.isEmpty() ? " None" : "", false);
        if (fields.isEmpty()) {
            body.comment(comment);
            return;
        }

        SvgDiagram flat = new SvgDiagram();
        for (Field field : fields) {
            drawField(flat, model, area, field);
        }
        body.diagram(flat);

        SvgDiagram expanded = new SvgDiagram();
        TypeNode root = TypeNode.ofMessage(fields, model,
                new TypeRef("MAL", malVersionOf(area), null, "Integer", false, false));
        root.draw(expanded, area, model, 1, 0, root.depth(0));
        body.diagram(expanded);

        body.comment(comment);
        for (Field field : fields) {
            if (field.getComment() != null && !field.getComment().isEmpty()) {
                body.fieldComment(nameOf(field), field.getComment());
            }
        }
    }

    /**
     * Draws one field as it goes on the wire: a list is a count followed by that many of
     * the type in it.
     */
    private void drawField(SvgDiagram diagram, MOModel model, Area area, Field field) {
        TypeRef type = field.getType().unwrapped();
        if (type.isList()) {
            diagram.field("N", "Integer", XhtmlLink.hrefTo(model, area,
                    new TypeRef("MAL", malVersionOf(area), null, "Integer", false, false)),
                    false, false);
            diagram.span(1, 1, "Repeated N times");
        }
        diagram.field(nameOf(field), type.getName(), XhtmlLink.hrefTo(model, area, type),
                isAbstract(model, type), isEnumeration(model, type));
    }

    /**
     * Describes every type the specification declares, area level first and then service by
     * service.
     */
    private void writeDataTypes(XhtmlBody body, Map<String, String> index,
            MOModel model, Area area) {
        if (!area.getDataTypes().isEmpty()) {
            body.title(2, "Area data types: ", null, area.getName(), false);
            for (TypeDefinition type : area.getDataTypes()) {
                writeDataType(body, index, model, area, null, type);
            }
        }
        for (Service service : area.getServices()) {
            if (service.getDataTypes().isEmpty()) {
                continue;
            }
            body.title(2, "Service data types: ", null, service.getName(), false);
            for (TypeDefinition type : service.getDataTypes()) {
                writeDataType(body, index, model, area, service, type);
            }
        }
    }

    private void writeDataType(XhtmlBody body, Map<String, String> index, MOModel model,
            Area area, Service service, TypeDefinition type) {
        if (type instanceof FundamentalType) {
            body.title(3, "Fundamental: ", XhtmlLink.anchorOf(service, type.getName()),
                    type.getName(), true);
            body.comment(type.getComment());
        } else if (type instanceof esa.mo.apigen.model.types.AttributeType) {
            body.title(3, "Attribute: ", XhtmlLink.anchorOf(service, type.getName()),
                    type.getName(), true);
            body.comment(type.getComment());
        } else if (type instanceof EnumerationType) {
            writeEnumeration(body, (EnumerationType) type, service);
        } else if (type instanceof CompositeType) {
            writeComposite(body, model, area, service, (CompositeType) type);
        } else {
            throw new IllegalArgumentException("A data type of "
                    + area.getName() + " is a " + type.getClass().getName()
                    + ", which is not something a page can describe");
        }
        index.put(type.getName(), XhtmlLink.hrefWithin(service, type.getName()));
    }

    private void writeEnumeration(XhtmlBody body, EnumerationType type, Service service) {
        body.title(3, "Enum: ", XhtmlLink.anchorOf(service, type.getName()),
                type.getName(), false);
        body.comment(type.getComment());
        for (EnumerationItem item : type.getItems()) {
            body.fieldComment(item.getValue(), item.getComment());
        }
    }

    private void writeComposite(XhtmlBody body, MOModel model, Area area, Service service,
            CompositeType type) {
        body.title(3, "Composite: ", XhtmlLink.anchorOf(service, type.getName()),
                type.getName(), type.isAbstract());
        body.comment(type.getComment());

        SvgDiagram diagram = new SvgDiagram();
        TypeRef parent = type.getSuperType();
        if (parent != null && !"Composite".equals(parent.getName())) {
            diagram.parent(parent.getName(), XhtmlLink.hrefTo(model, area, parent));
        }
        for (Field field : type.getFields()) {
            TypeRef fieldType = field.getType().unwrapped();
            if (field.isCanBeNull()) {
                int covered = fieldType.isList() ? 2 : 1;
                diagram.span(covered, covered, "Nullable");
            }
            if (fieldType.isList()) {
                diagram.field("N", "Integer", XhtmlLink.hrefTo(model, area,
                        new TypeRef("MAL", malVersionOf(area), null, "Integer", false, false)),
                        false, false);
                diagram.span(1, 1, "Repeated N times");
            }
            diagram.field(field.getName(), fieldType.getName(),
                    XhtmlLink.hrefTo(model, area, fieldType),
                    isAbstract(model, fieldType), isEnumeration(model, fieldType));
        }
        body.diagram(diagram);

        for (Field field : type.getFields()) {
            if (field.getComment() != null && !field.getComment().isEmpty()) {
                body.fieldComment(field.getName(), field.getComment());
            }
        }
    }

    /**
     * The MAL generation a specification is written against, which decides which MAL its
     * Integer and its UpdateHeader are.
     */
    private static int malVersionOf(Area area) {
        if (area.getSpecification() == null || area.getSpecification().getSchemaVersion() == null) {
            return 1;
        }
        return area.getSpecification().getSchemaVersion().getMalVersion();
    }

    private static boolean isAbstract(MOModel model, TypeRef type) {
        TypeDefinition definition = model.resolve(type);
        return definition != null && definition.isAbstract();
    }

    private static boolean isEnumeration(MOModel model, TypeRef type) {
        return model.resolve(type) instanceof EnumerationType;
    }

    /**
     * A message field need not be named, and a box has to say something.
     */
    private static String nameOf(Field field) {
        return field.getName() == null || field.getName().isEmpty() ? "Part" : field.getName();
    }

    private static Field field(String name, TypeRef type, boolean canBeNull) {
        Field field = new Field();
        field.setName(name);
        field.setType(type);
        field.setCanBeNull(canBeNull);
        return field;
    }

    private static Map.Entry<String, String> entry(String name, String href) {
        return new AbstractMap.SimpleEntry<String, String>(name, href);
    }
}
