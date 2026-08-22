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
package esa.mo.apigen.importers.xml;

import esa.mo.apigen.importers.ImportException;
import esa.mo.apigen.importers.Importer;
import esa.mo.apigen.model.*;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.com.ObjectReference;
import esa.mo.apigen.model.docs.Diagram;
import esa.mo.apigen.model.docs.DocSection;
import esa.mo.apigen.model.docs.Documentation;
import esa.mo.apigen.model.types.*;
import java.io.Reader;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Reads a specification from the CCSDS service schema XML, in either version.
 * <p>
 * Which version a file uses is taken from the root element's namespace, and recorded on
 * the specification: it decides which constructs are legal, and which the exporter may
 * write back. Nothing here resolves references - that is the linker's job.
 * <p>
 * The reader is lenient. An element it does not understand is skipped rather than
 * rejected, so that a half-written file still produces a model to look at; whether the
 * result makes sense is the validator's question.
 */
public final class XmlImporter implements Importer {

    @Override
    public Specification read(Reader in, SourceRef source) throws ImportException {
        XMLStreamReader reader = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
            factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
            reader = factory.createXMLStreamReader(in);
            return readSpecification(new StaxCursor(reader, source), source);
        } catch (XMLStreamException ex) {
            throw new ImportException("Could not read " + source + ": " + ex.getMessage(), ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException ignored) {
                    // nothing useful to do while unwinding
                }
            }
        }
    }

    private Specification readSpecification(StaxCursor c, SourceRef source)
            throws XMLStreamException, ImportException {
        while (c.reader().hasNext()) {
            if (c.reader().next() == XMLStreamConstants.START_ELEMENT) {
                break;
            }
        }
        if (!"specification".equals(c.localName())) {
            throw new ImportException("Not a service specification: root element is <"
                    + c.localName() + ">");
        }
        SchemaVersion version = SchemaVersion.fromNamespace(c.namespace());
        if (version == null) {
            throw new ImportException("Unknown service schema namespace: " + c.namespace());
        }

        Specification spec = new Specification();
        spec.setSource(source);
        spec.setSchemaVersion(version);
        spec.setComment(c.comment());

        while (c.nextChild()) {
            if (XmlNames.isMal(c.namespace()) && "area".equals(c.localName())) {
                spec.addArea(readArea(c));
            } else {
                c.skip();
            }
        }
        return spec;
    }

    // ----------------------------------------------------------------- area

    private Area readArea(StaxCursor c) throws XMLStreamException {
        Area area = new Area();
        area.setLocation(c.location());
        area.setName(c.attr("name"));
        area.setNumber(c.intAttr("number", 0));
        area.setVersion(c.intAttr("version", 1));
        area.setComment(c.comment());

        while (c.nextChild()) {
            String name = c.localName();
            if ("service".equals(name)) {
                area.addService(readService(c));
            } else if ("dataTypes".equals(name)) {
                readDataTypes(c, area.getDataTypes());
            } else if ("errors".equals(name)) {
                readErrorDefinitions(c, area.getErrors());
            } else if ("documentation".equals(name)) {
                area.getDocumentation().getSections().add(readDocSection(c));
            } else if ("diagram".equals(name)) {
                area.getDocumentation().getDiagrams().add(readDiagram(c));
            } else {
                c.skip();
            }
        }
        return area;
    }

    // -------------------------------------------------------------- service

    private Service readService(StaxCursor c) throws XMLStreamException {
        Service service = new Service();
        service.setLocation(c.location());
        service.setName(c.attr("name"));
        service.setNumber(c.intAttr("number", 0));
        service.setComment(c.comment());
        // A COM extended service says so on the element itself. It is recorded here rather
        // than inferred from the features it declares, because a service can be extended
        // and declare none.
        String type = c.attr("type");
        service.setExtended(type != null && type.endsWith("ExtendedServiceType"));

        while (c.nextChild()) {
            String name = c.localName();
            if (XmlNames.isCom(c.namespace()) && "features".equals(name)) {
                service.setExtended(true);
                service.setCom(readComFeatures(c));
            } else if ("capabilitySet".equals(name)) {
                service.addCapabilitySet(readCapabilitySet(c));
            } else if ("dataTypes".equals(name)) {
                readDataTypes(c, service.getDataTypes());
            } else if ("errors".equals(name)) {
                readErrorDefinitions(c, service.getErrors());
            } else if ("documentation".equals(name)) {
                service.getDocumentation().getSections().add(readDocSection(c));
            } else if ("diagram".equals(name)) {
                service.getDocumentation().getDiagrams().add(readDiagram(c));
            } else {
                c.skip();
            }
        }
        return service;
    }

    private CapabilitySet readCapabilitySet(StaxCursor c) throws XMLStreamException {
        CapabilitySet set = new CapabilitySet();
        set.setLocation(c.location());
        set.setNumber(c.intAttr("number", 0));
        set.setComment(c.comment());

        while (c.nextChild()) {
            InteractionPattern pattern = patternOf(c.localName());
            if (pattern != null) {
                set.addOperation(readOperation(c, pattern));
            } else {
                c.skip();
            }
        }
        return set;
    }

    private static InteractionPattern patternOf(String elementName) {
        if (elementName.endsWith("IP")) {
            String base = elementName.substring(0, elementName.length() - 2);
            for (InteractionPattern pattern : InteractionPattern.values()) {
                if (pattern.name().equalsIgnoreCase(base)) {
                    return pattern;
                }
            }
        }
        return null;
    }

    // ------------------------------------------------------------ operation

    private Operation readOperation(StaxCursor c, InteractionPattern pattern)
            throws XMLStreamException {
        Operation op = new Operation();
        op.setLocation(c.location());
        op.setPattern(pattern);
        op.setName(c.attr("name"));
        op.setNumber(c.intAttr("number", 0));
        op.setComment(c.comment());
        op.setSupportInReplay(c.boolAttr("supportInReplay", false));

        while (c.nextChild()) {
            String name = c.localName();
            if ("messages".equals(name)) {
                readMessages(c, op);
            } else if ("errors".equals(name)) {
                readErrorReferences(c, op.getErrors());
            } else if ("documentation".equals(name)) {
                op.getDocumentation().getSections().add(readDocSection(c));
            } else if ("diagram".equals(name)) {
                op.getDocumentation().getDiagrams().add(readDiagram(c));
            } else {
                c.skip();
            }
        }
        return op;
    }

    private void readMessages(StaxCursor c, Operation op) throws XMLStreamException {
        while (c.nextChild()) {
            InteractionStage stage = InteractionStage.fromXmlName(c.localName());
            if (stage != null) {
                op.getMessages().put(stage, readMessageBody(c));
            } else {
                c.skip();
            }
        }
    }

    private MessageBody readMessageBody(StaxCursor c) throws XMLStreamException {
        MessageBody body = new MessageBody();
        body.setComment(c.comment());
        while (c.nextChild()) {
            if ("field".equals(c.localName())) {
                body.getFields().add(readField(c));
            } else {
                c.skip();
            }
        }
        return body;
    }

    private Field readField(StaxCursor c) throws XMLStreamException {
        Field field = new Field();
        field.setLocation(c.location());
        field.setName(c.attr("name"));
        field.setComment(c.comment());
        field.setCanBeNull(c.boolAttr("canBeNull", true));
        while (c.nextChild()) {
            if ("type".equals(c.localName())) {
                field.setType(readTypeRef(c));
                c.skip();
            } else {
                c.skip();
            }
        }
        return field;
    }

    /**
     * Reads a type reference. The area version is left at 0: the XML does not carry it,
     * and filling it in is the linker's job.
     */
    private TypeRef readTypeRef(StaxCursor c) {
        return new TypeRef(c.attr("area"), 0, c.attr("service"), c.attr("name"),
                c.boolAttr("list", false), c.boolAttr("objectRef", false));
    }

    // ---------------------------------------------------------------- types

    private void readDataTypes(StaxCursor c, List<TypeDefinition> into)
            throws XMLStreamException {
        while (c.nextChild()) {
            String name = c.localName();
            if ("fundamental".equals(name)) {
                into.add(readFundamental(c));
            } else if ("attribute".equals(name)) {
                into.add(readAttribute(c));
            } else if ("composite".equals(name)) {
                into.add(readComposite(c));
            } else if ("enumeration".equals(name)) {
                into.add(readEnumeration(c));
            } else {
                c.skip();
            }
        }
    }

    private FundamentalType readFundamental(StaxCursor c) throws XMLStreamException {
        FundamentalType type = new FundamentalType();
        type.setLocation(c.location());
        type.setName(c.attr("name"));
        type.setComment(c.comment());
        while (c.nextChild()) {
            if ("extends".equals(c.localName())) {
                type.setSuperType(readExtends(c));
            } else {
                c.skip();
            }
        }
        return type;
    }

    private AttributeType readAttribute(StaxCursor c) throws XMLStreamException {
        AttributeType type = new AttributeType();
        type.setLocation(c.location());
        type.setName(c.attr("name"));
        type.setComment(c.comment());
        type.setShortFormPart(c.intAttr("shortFormPart", 0));
        c.skip();
        return type;
    }

    private CompositeType readComposite(StaxCursor c) throws XMLStreamException {
        CompositeType type = new CompositeType();
        type.setLocation(c.location());
        type.setName(c.attr("name"));
        type.setComment(c.comment());
        type.setShortFormPart(c.integerAttr("shortFormPart"));
        while (c.nextChild()) {
            String name = c.localName();
            if ("extends".equals(name)) {
                type.setSuperType(readExtends(c));
            } else if ("field".equals(name)) {
                type.getFields().add(readField(c));
            } else {
                c.skip();
            }
        }
        return type;
    }

    private EnumerationType readEnumeration(StaxCursor c) throws XMLStreamException {
        EnumerationType type = new EnumerationType();
        type.setLocation(c.location());
        type.setName(c.attr("name"));
        type.setComment(c.comment());
        type.setShortFormPart(c.intAttr("shortFormPart", 0));
        while (c.nextChild()) {
            if ("item".equals(c.localName())) {
                EnumerationItem item = new EnumerationItem();
                item.setLocation(c.location());
                item.setValue(c.attr("value"));
                item.setNumericValue(c.longAttr("nvalue", 0));
                item.setComment(c.comment());
                type.getItems().add(item);
                c.skip();
            } else {
                c.skip();
            }
        }
        return type;
    }

    private TypeRef readExtends(StaxCursor c) throws XMLStreamException {
        TypeRef ref = null;
        while (c.nextChild()) {
            if ("type".equals(c.localName())) {
                ref = readTypeRef(c);
            }
            c.skip();
        }
        return ref;
    }

    // --------------------------------------------------------------- errors

    private void readErrorDefinitions(StaxCursor c, List<ErrorDefinition> into)
            throws XMLStreamException {
        while (c.nextChild()) {
            if ("error".equals(c.localName())) {
                ErrorDefinition error = new ErrorDefinition();
                error.setLocation(c.location());
                error.setName(c.attr("name"));
                error.setNumber(c.longAttr("number", 0));
                error.setComment(c.comment());
                while (c.nextChild()) {
                    if ("extraInformation".equals(c.localName())) {
                        error.setExtraInformation(readExtraInformation(c));
                    } else {
                        c.skip();
                    }
                }
                into.add(error);
            } else {
                c.skip();
            }
        }
    }

    private void readErrorReferences(StaxCursor c, List<ErrorReference> into)
            throws XMLStreamException {
        while (c.nextChild()) {
            if ("errorRef".equals(c.localName())) {
                ErrorReference ref = new ErrorReference();
                ref.setLocation(c.location());
                ref.setComment(c.comment());
                while (c.nextChild()) {
                    String name = c.localName();
                    if ("type".equals(name)) {
                        ref.setError(readTypeRef(c));
                        c.skip();
                    } else if ("extraInformation".equals(name)) {
                        ref.setExtraInformation(readExtraInformation(c));
                    } else {
                        c.skip();
                    }
                }
                into.add(ref);
            } else {
                c.skip();
            }
        }
    }

    private Field readExtraInformation(StaxCursor c) throws XMLStreamException {
        Field extra = new Field();
        extra.setLocation(c.location());
        extra.setComment(c.comment());
        extra.setCanBeNull(false);
        while (c.nextChild()) {
            if ("type".equals(c.localName())) {
                extra.setType(readTypeRef(c));
            }
            c.skip();
        }
        return extra;
    }

    // ------------------------------------------------------------------ COM

    private COMFeatures readComFeatures(StaxCursor c) throws XMLStreamException {
        COMFeatures com = new COMFeatures();
        while (c.nextChild()) {
            String name = c.localName();
            if ("objects".equals(name)) {
                com.setObjectsComment(c.comment());
                readComObjects(c, com.getObjects());
            } else if ("events".equals(name)) {
                com.setEventsComment(c.comment());
                readComObjects(c, com.getEvents());
            } else if ("archiveUsage".equals(name)) {
                com.setArchiveUsage(c.comment());
                c.skip();
            } else if ("activityUsage".equals(name)) {
                com.setActivityUsage(c.comment());
                c.skip();
            } else if ("documentation".equals(name)) {
                com.getDocumentation().getSections().add(readDocSection(c));
            } else if ("diagram".equals(name)) {
                com.getDocumentation().getDiagrams().add(readDiagram(c));
            } else {
                c.skip();
            }
        }
        return com;
    }

    private void readComObjects(StaxCursor c, List<COMObject> into) throws XMLStreamException {
        while (c.nextChild()) {
            String name = c.localName();
            if ("object".equals(name) || "event".equals(name)) {
                COMObject object = new COMObject();
                object.setLocation(c.location());
                object.setName(c.attr("name"));
                object.setNumber(c.intAttr("number", 0));
                object.setComment(c.comment());
                while (c.nextChild()) {
                    String child = c.localName();
                    if ("objectType".equals(child)) {
                        object.setBodyType(readBodyType(c));
                    } else if ("relatedObject".equals(child)) {
                        object.setRelated(readObjectLink(c));
                    } else if ("sourceObject".equals(child)) {
                        object.setSource(readObjectLink(c));
                    } else {
                        c.skip();
                    }
                }
                into.add(object);
            } else {
                c.skip();
            }
        }
    }

    /**
     * The body of a COM object is a {@code <com:objectType>} wrapping a {@code <mal:type>};
     * an empty wrapper means the object has no body.
     */
    private TypeRef readBodyType(StaxCursor c) throws XMLStreamException {
        TypeRef ref = null;
        while (c.nextChild()) {
            if ("type".equals(c.localName())) {
                ref = readTypeRef(c);
            }
            c.skip();
        }
        return ref;
    }

    private ObjectLink readObjectLink(StaxCursor c) throws XMLStreamException {
        ObjectLink link = new ObjectLink();
        link.setComment(c.comment());
        while (c.nextChild()) {
            if ("objectType".equals(c.localName())) {
                link.setTarget(new ObjectReference(c.attr("area"), 0, c.attr("service"),
                        c.intAttr("number", 0)));
            }
            c.skip();
        }
        return link;
    }

    // -------------------------------------------------------- documentation

    private DocSection readDocSection(StaxCursor c) throws XMLStreamException {
        DocSection section = new DocSection();
        section.setLocation(c.location());
        section.setName(c.attr("name"));
        section.setOrder(c.intAttr("order", 0));
        section.setContent(c.text());
        return section;
    }

    private Diagram readDiagram(StaxCursor c) throws XMLStreamException {
        Diagram diagram = new Diagram();
        diagram.setLocation(c.location());
        diagram.setName(c.attr("name"));
        diagram.setSvg(c.captureChildren());
        return diagram;
    }
}
