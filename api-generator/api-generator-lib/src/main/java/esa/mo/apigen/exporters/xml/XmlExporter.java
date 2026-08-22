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
package esa.mo.apigen.exporters.xml;

import esa.mo.apigen.exporters.ExportException;
import esa.mo.apigen.exporters.Exporter;
import esa.mo.apigen.importers.xml.XmlNames;
import esa.mo.apigen.model.*;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.docs.Diagram;
import esa.mo.apigen.model.docs.DocSection;
import esa.mo.apigen.model.docs.Documentation;
import esa.mo.apigen.model.types.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Writes a specification back to the CCSDS service schema XML, in the version the
 * specification declares.
 * <p>
 * Constructs the target version does not have are refused rather than dropped: writing a
 * v003 model into a v001 file would silently lose its subscription keys, and a silent
 * loss is worse than a failed export.
 */
public final class XmlExporter implements Exporter {

    @Override
    public void write(Specification spec, Path outputDir) throws IOException, ExportException {
        Files.createDirectories(outputDir);
        String name = spec.getSource() == null ? "specification.xml" : spec.getSource().getName();
        Writer out = new OutputStreamWriter(
                Files.newOutputStream(outputDir.resolve(name)), Charset.forName("UTF-8"));
        try {
            writeTo(spec, out);
        } finally {
            out.close();
        }
    }

    /**
     * Writes a specification to any writer. The directory form above is a wrapper over
     * this, so that in-memory use and file use are the same code.
     *
     * @param spec The specification to write.
     * @param out Where to write it.
     * @throws IOException if writing fails.
     * @throws ExportException if the model cannot be represented in its schema version.
     */
    public void writeTo(Specification spec, Writer out) throws IOException, ExportException {
        SchemaVersion version = spec.getSchemaVersion();
        if (version == null) {
            throw new ExportException("The specification does not say which schema it uses");
        }
        StringWriter buffered = new StringWriter();
        XmlWriter xml = new XmlWriter(buffered);
        xml.start("mal:specification");
        xml.attr("xmlns:mal", version.getNamespace());
        xml.attr("xmlns:com", XmlNames.COM);
        xml.attr("xmlns:svg", XmlNames.SVG);
        xml.attr("xmlns:xsi", XmlNames.XSI);
        xml.attr("comment", spec.getComment());
        for (Area area : spec.getAreas()) {
            writeArea(xml, version, area);
        }
        xml.end();
        xml.flush();

        // Validated before it is written, so a failed export cannot leave an invalid
        // document behind (design section 6.1).
        String document = buffered.toString();
        String problem = SchemaValidation.check(document, version);
        if (problem != null) {
            throw new ExportException("The exported document does not satisfy the "
                    + version + " schema - " + problem);
        }
        out.write(document);
        out.flush();
    }

    /**
     * Returns the specification as a string.
     *
     * @param spec The specification to write.
     * @return the XML document.
     * @throws ExportException if the model cannot be represented in its schema version.
     */
    public String toXml(Specification spec) throws ExportException {
        StringWriter out = new StringWriter();
        try {
            writeTo(spec, out);
        } catch (IOException ex) {
            throw new IllegalStateException("a StringWriter cannot fail", ex);
        }
        return out.toString();
    }

    // ----------------------------------------------------------------- area

    private void writeArea(XmlWriter xml, SchemaVersion version, Area area)
            throws IOException, ExportException {
        xml.start("mal:area");
        xml.attr("name", area.getName());
        xml.attr("number", area.getNumber());
        xml.attr("version", area.getVersion());
        xml.attr("comment", area.getComment());
        writeDocumentation(xml, area.getDocumentation());
        for (Service service : area.getServices()) {
            writeService(xml, version, service);
        }
        writeDataTypes(xml, version, area.getDataTypes(), true);
        writeErrorDefinitions(xml, area.getErrors());
        xml.end();
    }

    // -------------------------------------------------------------- service

    private void writeService(XmlWriter xml, SchemaVersion version, Service service)
            throws IOException, ExportException {
        if (version == SchemaVersion.V003) {
            if (!service.getDataTypes().isEmpty()) {
                throw new ExportException("Service '" + service.getName() + "' declares data "
                        + "types, which the v003 schema does not have");
            }
            if (!service.getErrors().isEmpty()) {
                throw new ExportException("Service '" + service.getName() + "' declares errors, "
                        + "which the v003 schema does not have");
            }
        }
        xml.start("mal:service");
        xml.attr("name", service.getName());
        xml.attr("number", service.getNumber());
        xml.attr("comment", service.getComment());
        if (service.isExtended()) {
            xml.attr("xsi:type", "com:ExtendedServiceType");
        }
        writeDocumentation(xml, service.getDocumentation());
        for (CapabilitySet set : service.getCapabilitySets()) {
            writeCapabilitySet(xml, version, set);
        }
        writeDataTypes(xml, version, service.getDataTypes(), false);
        writeErrorDefinitions(xml, service.getErrors());
        if (service.getCom() != null) {
            writeCom(xml, service.getCom());
        }
        xml.end();
    }

    private void writeCapabilitySet(XmlWriter xml, SchemaVersion version, CapabilitySet set)
            throws IOException, ExportException {
        xml.start("mal:capabilitySet");
        xml.attr("number", set.getNumber());
        xml.attr("comment", set.getComment());
        for (Operation op : set.getOperations()) {
            writeOperation(xml, version, op);
        }
        xml.end();
    }

    // ------------------------------------------------------------ operation

    private void writeOperation(XmlWriter xml, SchemaVersion version, Operation op)
            throws IOException, ExportException {
        if (op.getPattern() == null) {
            throw new ExportException("Operation '" + op.getName() + "' has no interaction pattern");
        }
        if (version == SchemaVersion.V003 && op.isSupportInReplay()) {
            throw new ExportException("Operation '" + op.getName() + "' sets supportInReplay, "
                    + "which the v003 schema does not have");
        }
        if (version == SchemaVersion.V001 && !op.getDocumentation().isEmpty()) {
            throw new ExportException("Operation '" + op.getName() + "' carries documentation, "
                    + "which the v001 schema does not have");
        }
        String element = "mal:" + op.getPattern().name().toLowerCase() + "IP";
        xml.start(element);
        xml.attr("name", op.getName());
        xml.attr("number", op.getNumber());
        if (version == SchemaVersion.V001) {
            xml.attr("supportInReplay", op.isSupportInReplay());
        }
        xml.attr("comment", op.getComment());
        writeDocumentation(xml, op.getDocumentation());

        xml.start("mal:messages");
        for (InteractionStage stage : op.getPattern().getStages()) {
            MessageBody body = op.getMessage(stage);
            if (body == null) {
                continue;
            }
            if (stage == InteractionStage.SUBSCRIPTION_KEYS && version == SchemaVersion.V001) {
                throw new ExportException("Operation '" + op.getName() + "' declares subscription "
                        + "keys, which the v001 schema does not have");
            }
            xml.start("mal:" + stage.getXmlName());
            xml.attr("comment", body.getComment());
            for (Field field : body.getFields()) {
                writeField(xml, field);
            }
            xml.end();
        }
        xml.end();

        if (!op.getErrors().isEmpty()) {
            xml.start("mal:errors");
            for (ErrorReference ref : op.getErrors()) {
                xml.start("mal:errorRef");
                xml.attr("comment", ref.getComment());
                writeTypeRef(xml, ref.getError());
                writeExtraInformation(xml, ref.getExtraInformation());
                xml.end();
            }
            xml.end();
        }
        xml.end();
    }

    private void writeField(XmlWriter xml, Field field) throws IOException {
        xml.start("mal:field");
        xml.attr("name", field.getName());
        xml.attr("canBeNull", field.isCanBeNull());
        xml.attr("comment", field.getComment());
        writeTypeRef(xml, field.getType());
        xml.end();
    }

    private void writeTypeRef(XmlWriter xml, TypeRef ref) throws IOException {
        if (ref == null) {
            return;
        }
        xml.start("mal:type");
        xml.attr("area", ref.getArea());
        if (ref.getService() != null) {
            xml.attr("service", ref.getService());
        }
        xml.attr("name", ref.getName());
        xml.attr("list", ref.isList());
        if (ref.isObjectRef()) {
            xml.attr("objectRef", true);
        }
        xml.end();
    }

    private void writeExtraInformation(XmlWriter xml, Field extra) throws IOException {
        if (extra == null) {
            return;
        }
        xml.start("mal:extraInformation");
        xml.attr("comment", extra.getComment());
        writeTypeRef(xml, extra.getType());
        xml.end();
    }

    // ---------------------------------------------------------------- types

    private void writeDataTypes(XmlWriter xml, SchemaVersion version,
            List<TypeDefinition> types, boolean areaLevel) throws IOException, ExportException {
        if (types.isEmpty()) {
            return;
        }
        xml.start("mal:dataTypes");
        for (TypeDefinition type : types) {
            if (type instanceof FundamentalType) {
                FundamentalType fundamental = (FundamentalType) type;
                xml.start("mal:fundamental");
                xml.attr("name", type.getName());
                xml.attr("comment", type.getComment());
                writeExtends(xml, fundamental.getSuperType());
                xml.end();
            } else if (type instanceof AttributeType) {
                xml.start("mal:attribute");
                xml.attr("name", type.getName());
                xml.attr("shortFormPart", ((AttributeType) type).getShortFormPart());
                xml.attr("comment", type.getComment());
                xml.end();
            } else if (type instanceof CompositeType) {
                CompositeType composite = (CompositeType) type;
                xml.start("mal:composite");
                xml.attr("name", type.getName());
                if (composite.getShortFormPart() != null) {
                    xml.attr("shortFormPart", composite.getShortFormPart().intValue());
                }
                xml.attr("comment", type.getComment());
                writeExtends(xml, composite.getSuperType());
                for (Field field : composite.getFields()) {
                    writeField(xml, field);
                }
                xml.end();
            } else if (type instanceof EnumerationType) {
                EnumerationType enumeration = (EnumerationType) type;
                xml.start("mal:enumeration");
                xml.attr("name", type.getName());
                xml.attr("shortFormPart", enumeration.getShortFormPart());
                xml.attr("comment", type.getComment());
                for (EnumerationItem item : enumeration.getItems()) {
                    xml.start("mal:item");
                    xml.attr("value", item.getValue());
                    xml.attr("nvalue", item.getNumericValue());
                    xml.attr("comment", item.getComment());
                    xml.end();
                }
                xml.end();
            }
        }
        xml.end();
    }

    private void writeExtends(XmlWriter xml, TypeRef superType) throws IOException {
        if (superType == null) {
            return;
        }
        xml.start("mal:extends");
        writeTypeRef(xml, superType);
        xml.end();
    }

    private void writeErrorDefinitions(XmlWriter xml, List<ErrorDefinition> errors)
            throws IOException {
        if (errors.isEmpty()) {
            return;
        }
        xml.start("mal:errors");
        for (ErrorDefinition error : errors) {
            xml.start("mal:error");
            xml.attr("name", error.getName());
            xml.attr("number", error.getNumber());
            xml.attr("comment", error.getComment());
            writeExtraInformation(xml, error.getExtraInformation());
            xml.end();
        }
        xml.end();
    }

    // ------------------------------------------------------------------ COM

    private void writeCom(XmlWriter xml, COMFeatures com) throws IOException {
        xml.start("com:features");
        writeDocumentation(xml, com.getDocumentation());
        if (!com.getObjects().isEmpty() || com.getObjectsComment() != null) {
            xml.start("com:objects");
            xml.attr("comment", com.getObjectsComment());
            writeComObjects(xml, "object", com.getObjects());
            xml.end();
        }
        if (!com.getEvents().isEmpty() || com.getEventsComment() != null) {
            xml.start("com:events");
            xml.attr("comment", com.getEventsComment());
            writeComObjects(xml, "event", com.getEvents());
            xml.end();
        }
        if (com.getArchiveUsage() != null) {
            xml.start("com:archiveUsage");
            xml.attr("comment", com.getArchiveUsage());
            xml.end();
        }
        if (com.getActivityUsage() != null) {
            xml.start("com:activityUsage");
            xml.attr("comment", com.getActivityUsage());
            xml.end();
        }
        xml.end();
    }

    private void writeComObjects(XmlWriter xml, String element, List<COMObject> objects)
            throws IOException {
        for (COMObject object : objects) {
            xml.start("com:" + element);
            xml.attr("name", object.getName());
            xml.attr("number", object.getNumber());
            xml.attr("comment", object.getComment());
            if (object.getBodyType() != null) {
                xml.start("com:objectType");
                writeTypeRef(xml, object.getBodyType());
                xml.end();
            }
            writeObjectLink(xml, "relatedObject", object.getRelated());
            writeObjectLink(xml, "sourceObject", object.getSource());
            xml.end();
        }
    }

    private void writeObjectLink(XmlWriter xml, String element, ObjectLink link)
            throws IOException {
        if (link == null) {
            return;
        }
        xml.start("com:" + element);
        xml.attr("comment", link.getComment());
        if (link.getTarget() != null) {
            xml.start("com:objectType");
            xml.attr("area", link.getTarget().getArea());
            xml.attr("service", link.getTarget().getService());
            xml.attr("number", link.getTarget().getNumber());
            xml.end();
        }
        xml.end();
    }

    // -------------------------------------------------------- documentation

    private void writeDocumentation(XmlWriter xml, Documentation docs) throws IOException {
        if (docs == null) {
            return;
        }
        for (DocSection section : docs.getSections()) {
            xml.start("mal:documentation");
            xml.attr("name", section.getName());
            xml.attr("order", section.getOrder());
            xml.text(section.getContent());
            xml.end();
        }
        for (Diagram diagram : docs.getDiagrams()) {
            xml.start("mal:diagram");
            xml.attr("name", diagram.getName());
            xml.raw(diagram.getSvg());
            xml.end();
        }
    }
}
