/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA MO Navigator
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
package esa.mo.navigator.parsers;

import esa.mo.navigator.FileSupport;
import de.dlr.gsoc.mcds.mosdl.InteractionStage;
import de.dlr.gsoc.mcds.mosdl.InteractionType;
import de.dlr.gsoc.mcds.mosdl.generators.Generator;
import de.dlr.gsoc.mcds.mosdl.generators.GeneratorException;
import de.dlr.gsoc.mcds.mosdl.generators.MosdlGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import javax.xml.bind.JAXBElement;
import org.ccsds.schema.comschema.ExtendedServiceType;
import org.ccsds.schema.comschema.ModelEventTypeList;
import org.ccsds.schema.comschema.ModelObjectType;
import org.ccsds.schema.comschema.ModelObjectTypeList;
import org.ccsds.schema.comschema.ObjectReference;
import org.ccsds.schema.comschema.SupportedFeatures;
import org.ccsds.schema.serviceschema.AnyTypeReferenceWithId;
import org.ccsds.schema.serviceschema.AreaType;
import org.ccsds.schema.serviceschema.AttributeType;
import org.ccsds.schema.serviceschema.CapabilitySetType;
import org.ccsds.schema.serviceschema.CompositeType;
import org.ccsds.schema.serviceschema.ElementReferenceWithCommentType;
import org.ccsds.schema.serviceschema.EnumerationType;
import org.ccsds.schema.serviceschema.ErrorDefinitionType;
import org.ccsds.schema.serviceschema.ErrorReferenceType;
import org.ccsds.schema.serviceschema.FundamentalType;
import org.ccsds.schema.serviceschema.InvokeOperationType;
import org.ccsds.schema.serviceschema.NamedElementReferenceWithCommentType;
import org.ccsds.schema.serviceschema.OperationType;
import org.ccsds.schema.serviceschema.ProgressOperationType;
import org.ccsds.schema.serviceschema.PubSubOperationType;
import org.ccsds.schema.serviceschema.RequestOperationType;
import org.ccsds.schema.serviceschema.ServiceType;
import org.ccsds.schema.serviceschema.SpecificationType;
import org.ccsds.schema.serviceschema.SubmitOperationType;
import org.ccsds.schema.serviceschema.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The XMLParser class parses the XML from a text field.
 */
public class GeneratorMOSDL extends Generator {

    private static final Logger logger = LoggerFactory.getLogger(MosdlGenerator.class);
    private static final String MOSDL_SPEC_FILE_ENDING = ".mosdl";
    private static final String MAL_AREA = "MAL";
    private static final Set<String> MAL_FUNDAMENTALS = new HashSet<>(
            Arrays.asList(
                    "Blob", "Boolean", "Double", "Duration", "FineTime",
                    "Float", "Identifier", "Integer", "Long", "Octet", "Short",
                    "String", "Time", "UInteger", "ULong", "UOctet", "URI",
                    "UShort", "Attribute", "Composite", "Element"
            ));
    private static final Set<String> KEYWORDS = new HashSet<>(
            Arrays.asList(
                    "area", "service", "composite", "enum", "attribute",
                    "fundamental", "error", "extends", "import", "throws",
                    "abstract", "capability", "send", "submit", "request",
                    "invoke", "progress", "pubsub"
            ));
    private static final Map<InteractionStage, String> stageToTagMap = new HashMap<>();

    private final DocType docType;
    private final boolean javaComments;
    private Writer currentWriter = null;
    private int currentIndent = 0;
    private AreaType currentArea;
    private ServiceType currentService;

    static {
        for (InteractionStage stage : InteractionStage.values()) {
            String tagName = Arrays.stream(stage.name().toLowerCase().split("_"))
                    .map(s -> s.toLowerCase())
                    .reduce("", (s1, s2) -> s2);
            stageToTagMap.put(stage, tagName);
        }
    }

    private void writeLinks(ModelObjectType obj, AreaType area, ServiceType service) throws IOException {
        indent();

        if (obj.getRelatedObject() != null) {
            String preText = "Related: ";
            if (null != obj.getRelatedObject().getObjectType()) {
                String relatedLink = createFQTypeName(area, service, obj.getRelatedObject().getObjectType());
                relatedLink = (relatedLink == null) ? "" : relatedLink;
                String comment = obj.getRelatedObject().getComment();
                if (comment != null) {
                    writeLine("/// " + comment);
                }
                writeLine(preText + relatedLink);
            } else {
                if (obj.getRelatedObject().getComment() != null) {
                    String comment = obj.getRelatedObject().getComment();
                    writeLine("/// " + comment);
                    writeLine(preText + "-");
                }
            }
        }

        if (obj.getSourceObject() != null) {
            String preText = "Source: ";
            if (null != obj.getSourceObject().getObjectType()) {
                String sourceLink = createFQTypeName(area, service, obj.getSourceObject().getObjectType());
                sourceLink = (sourceLink == null) ? "" : sourceLink;
                String comment = obj.getSourceObject().getComment();
                if (comment != null) {
                    writeLine("/// " + comment);
                }
                writeLine(preText + sourceLink);
            } else {
                if (obj.getSourceObject().getComment() != null) {
                    String comment = obj.getSourceObject().getComment();
                    writeLine("/// " + comment);
                    writeLine(preText + "-");
                }
            }
        }

        outdent();
    }

    private String extractObjectType(ModelObjectType obj) {
        String objBodyType = "";
        List<Object> aaa = obj.getObjectType().getAny();

        for (int i = 0; i < aaa.size(); i++) {
            JAXBElement bbb = (JAXBElement) aaa.get(i);
            TypeReference ref = (TypeReference) bbb.getValue();
            objBodyType += ref.getArea() + "::" + ref.getName();
        }

        return objBodyType;
    }

    /**
     * The possible types of documentation this generator can output.
     */
    public static enum DocType {
        /**
         * Documentation for operations and all elements belonging to them is
         * put in bulk at the head of the operation. This is the recommended
         * documentation type.
         */
        BULK,
        /**
         * Documentation for all elements of operations is put right in front of
         * each element. This will be difficult to read for more than a tiny
         * amount of documentation.
         */
        INLINE,
        /**
         * Documentation will be stripped completely.
         */
        SUPPRESS;
    }

    /**
     * Creates a new MOSDL generator.
     *
     * @param docType the type of documentation to generate
     */
    public GeneratorMOSDL(final DocType docType) {
        this.docType = docType;
        this.javaComments = true;
    }

    /**
     * The generateMOSDL method generates XML text from a specification.
     *
     * @param spec The specification
     * @return The parsed Data
     */
    public String generateMOSDL(SpecificationType spec) {
        StringWriter strWriter = new StringWriter();
        for (AreaType area : spec.getArea()) {
            currentArea = area;
            // File targetFile = new File(targetDirectory, area.getName() + MOSDL_SPEC_FILE_ENDING);
            // logger.debug("Generating MOSDL file '{}'.", targetFile);
            try {
                // currentWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), StandardCharsets.UTF_8));
                currentWriter = strWriter;
                writeDoc(area.getComment());
                writeLine("area ", getId(area.getName()), " [", area.getNumber(), area.getVersion() == 1 ? "" : "." + area.getVersion(), "]");
                writeLine();
                // TODO: imports
                for (ServiceType service : area.getService()) {
                    currentService = service;
                    writeDoc(service.getComment());
                    writeLine("service ", getId(service.getName()), " [", service.getNumber(), "] {");
                    indent();
                    for (CapabilitySetType cs : service.getCapabilitySet()) {
                        writeDoc(cs.getComment());
                        writeLine("capability [", cs.getNumber(), "] {");
                        indent();
                        for (OperationType op : cs.getSendIPOrSubmitIPOrRequestIP()) {
                            InteractionType interaction = getInteractionType(op);
                            List<MessageDetails> messages = MessageDetails.fromOp(op);
                            List<Object> errors = getErrorsFromOp(op, interaction);
                            boolean hasErrorDoc = errors.stream().anyMatch(e -> hasErrorDoc(e));
                            writeOpDoc(op, messages, errors, hasErrorDoc);
                            writeIndent();
                            write(interaction.name().toLowerCase(), " ");
                            if (op.isSupportInReplay()) {
                                write("*");
                            }
                            write(getId(op.getName()), " [", op.getNumber(), "] ");
                            indent();
                            switch (interaction) {
                                case SEND:
                                    writeMessage(null, messages.get(0));
                                    break;
                                case SUBMIT:
                                    writeMessage(null, messages.get(0));
                                    break;
                                case REQUEST:
                                    writeMessage(null, messages.get(0));
                                    writeMessage("-> ", messages.get(1));
                                    break;
                                case INVOKE:
                                    writeMessage(null, messages.get(0));
                                    writeMessage("-> ", messages.get(1));
                                    writeMessage("-> ", messages.get(2));
                                    break;
                                case PROGRESS:
                                    writeMessage(null, messages.get(0));
                                    writeMessage("-> ", messages.get(1));
                                    writeMessage("-> ", messages.get(2));
                                    write("*");
                                    writeMessage("-> ", messages.get(3));
                                    break;
                                case PUBSUB:
                                    write(" <- ");
                                    writeMessage(null, messages.get(0));
                                    break;
                            }
                            if (!errors.isEmpty()) {
                                writeLine();
                                writeIndent();
                                write("throws");
                                boolean isMultiline = hasErrorDoc && docType == DocType.INLINE;
                                if (isMultiline) {
                                    indent();
                                    writeLine();
                                    Iterator<Object> errorIter = errors.iterator();
                                    while (errorIter.hasNext()) {
                                        Object error = errorIter.next();
                                        if (error instanceof ErrorReferenceType) {
                                            writeDoc(((ErrorReferenceType) error).getComment());
                                        } else if (error instanceof ErrorDefinitionType) {
                                            writeDoc(((ErrorDefinitionType) error).getComment());
                                        }
                                        writeIndent();
                                        writeOpError(error);
                                        if (errorIter.hasNext()) {
                                            write(",");
                                            writeLine();
                                        }
                                    }
                                    outdent();
                                } else {
                                    write(" ");
                                    Iterator<Object> errorIter = errors.iterator();
                                    while (errorIter.hasNext()) {
                                        Object error = errorIter.next();
                                        writeOpError(error);
                                        if (errorIter.hasNext()) {
                                            write(", ");
                                        }
                                    }
                                }
                            }
                            outdent();
                            writeLine();
                            writeLine();
                        }
                        outdent();
                        writeLine("}");
                        writeLine();
                    }

                    // writeCOMFeatures(service, area);

                    if (null != service.getDataTypes()) {
                        for (Object dataType : service.getDataTypes().getCompositeOrEnumeration()) {
                            writeDataType(dataType);
                            writeLine();
                        }
                    }
                    writeErrors(service.getErrors());
                    outdent();
                    writeLine("}");
                    writeLine();
                    currentService = null;
                }

                if (null != area.getDataTypes()) {
                    for (Object dataType : area.getDataTypes().getFundamentalOrAttributeOrComposite()) {
                        writeDataType(dataType);
                        writeLine();
                    }
                }

                writeErrors(area.getErrors());
                currentArea = null;
                currentWriter.close();
                return currentWriter.toString();
                //logger.debug("Generated MOSDL file '{}", targetFile);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FileSupport.class.getName()).log(Level.WARNING,
                        "Error! ");
                // throw new IOException("Something went wrong!");
            }
        }

        return null;
    }

    public void writeCOMFeatures(ServiceType service, AreaType area) throws IOException {
        // We need to iterate the COM Features here
        ExtendedServiceType comService = (ExtendedServiceType) service;
        SupportedFeatures features = comService.getFeatures();
        boolean hasCOMObjects = false;
        boolean hasCOMEvents = false;

        if (features != null) {
            ModelObjectTypeList comObjects = features.getObjects();
            ModelEventTypeList comEvents = features.getEvents();
            hasCOMObjects = comObjects != null && !comObjects.getObject().isEmpty();
            hasCOMEvents = comEvents != null && !comEvents.getEvent().isEmpty();

            if (hasCOMObjects || hasCOMEvents) {
                writeLine("COM features {");
                indent();
                if (hasCOMObjects) {
                    writeLine("Objects {");
                    indent();

                    for (ModelObjectType obj : comObjects.getObject()) {
                        if (null != obj.getObjectType() && (null != obj.getObjectType().getAny())) {
                            String comment = (obj.getComment() == null) ? "" : obj.getComment();
                            String objNumber = String.valueOf(obj.getNumber());
                            String objBodyType = "No body";

                            if (null != obj.getObjectType() && (null != obj.getObjectType().getAny())) {
                                objBodyType = this.extractObjectType(obj);
                            }

                            writeLine("/// " + comment);
                            writeLine("Object " + obj.getName() + " [" + objNumber + "] (" + objBodyType + ")");
                            writeLinks(obj, area, service);
                        } else {
                            //writeLine("---------------> Nothing to be done here...");
                        }

                    }

                    outdent();
                    writeLine("}");
                }

                if (hasCOMEvents) {
                    writeLine("Events {");
                    indent();

                    for (ModelObjectType obj : comEvents.getEvent()) {
                        String comment = (obj.getComment() == null) ? "" : obj.getComment();
                        String objNumber = String.valueOf(obj.getNumber());
                        String objBodyType = "No body";

                        if (null != obj.getObjectType() && (null != obj.getObjectType().getAny())) {
                            objBodyType = this.extractObjectType(obj);
                        }

                        writeLine("/// " + comment);
                        writeLine("Event " + obj.getName() + " [" + objNumber + "] (" + objBodyType + ")");
                        writeLinks(obj, area, service);
                    }
                    outdent();
                    writeLine("}");
                }
                outdent();
                writeLine("}");
                writeLine();
            }
        }

        // We need to iterate the diagrams here
        for (AnyTypeReferenceWithId diagram : service.getDiagram()) {
            writeLine("This is a test 3 --> We need to code the diagrams part!");
            writeLine(diagram.toString());
            writeLine();
        }
    }

    @Override
    public void generate(SpecificationType spec, File targetDirectory) throws GeneratorException {
        logger.debug("Generating MOSDL file(s) into directory '{}'.", targetDirectory);
        for (AreaType area : spec.getArea()) {
            currentArea = area;
            File targetFile = new File(targetDirectory, area.getName() + MOSDL_SPEC_FILE_ENDING);
            logger.debug("Generating MOSDL file '{}'.", targetFile);
            try {
                currentWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), StandardCharsets.UTF_8));
                writeDoc(area.getComment());
                writeLine("area ", getId(area.getName()), " [", area.getNumber(), area.getVersion() == 1 ? "" : "." + area.getVersion(), "]");
                writeLine();
                // TODO: imports
                for (ServiceType service : area.getService()) {
                    currentService = service;
                    writeDoc(service.getComment());
                    writeLine("service ", getId(service.getName()), " [", service.getNumber(), "] {");
                    indent();
                    for (CapabilitySetType cs : service.getCapabilitySet()) {
                        writeDoc(cs.getComment());
                        writeLine("capability [", cs.getNumber(), "] {");
                        indent();
                        for (OperationType op : cs.getSendIPOrSubmitIPOrRequestIP()) {
                            InteractionType interaction = getInteractionType(op);
                            List<MessageDetails> messages = MessageDetails.fromOp(op);
                            List<Object> errors = getErrorsFromOp(op, interaction);
                            boolean hasErrorDoc = errors.stream().anyMatch(e -> hasErrorDoc(e));
                            writeOpDoc(op, messages, errors, hasErrorDoc);
                            writeIndent();
                            write(interaction.name().toLowerCase(), " ");
                            if (op.isSupportInReplay()) {
                                write("*");
                            }
                            write(getId(op.getName()), " [", op.getNumber(), "] ");
                            indent();
                            switch (interaction) {
                                case SEND:
                                    writeMessage(null, messages.get(0));
                                    break;
                                case SUBMIT:
                                    writeMessage(null, messages.get(0));
                                    break;
                                case REQUEST:
                                    writeMessage(null, messages.get(0));
                                    writeMessage("-> ", messages.get(1));
                                    break;
                                case INVOKE:
                                    writeMessage(null, messages.get(0));
                                    writeMessage("-> ", messages.get(1));
                                    writeMessage("-> ", messages.get(2));
                                    break;
                                case PROGRESS:
                                    writeMessage(null, messages.get(0));
                                    writeMessage("-> ", messages.get(1));
                                    writeMessage("-> ", messages.get(2));
                                    write("*");
                                    writeMessage("-> ", messages.get(3));
                                    break;
                                case PUBSUB:
                                    write(" <- ");
                                    writeMessage(null, messages.get(0));
                                    break;
                            }
                            if (!errors.isEmpty()) {
                                writeLine();
                                writeIndent();
                                write("throws");
                                boolean isMultiline = hasErrorDoc && docType == DocType.INLINE;
                                if (isMultiline) {
                                    indent();
                                    writeLine();
                                    Iterator<Object> errorIter = errors.iterator();
                                    while (errorIter.hasNext()) {
                                        Object error = errorIter.next();
                                        if (error instanceof ErrorReferenceType) {
                                            writeDoc(((ErrorReferenceType) error).getComment());
                                        } else if (error instanceof ErrorDefinitionType) {
                                            writeDoc(((ErrorDefinitionType) error).getComment());
                                        }
                                        writeIndent();
                                        writeOpError(error);
                                        if (errorIter.hasNext()) {
                                            write(",");
                                            writeLine();
                                        }
                                    }
                                    outdent();
                                } else {
                                    write(" ");
                                    Iterator<Object> errorIter = errors.iterator();
                                    while (errorIter.hasNext()) {
                                        Object error = errorIter.next();
                                        writeOpError(error);
                                        if (errorIter.hasNext()) {
                                            write(", ");
                                        }
                                    }
                                }
                            }
                            outdent();
                            writeLine();
                            writeLine();
                        }
                        outdent();
                        writeLine("}");
                        writeLine();
                    }
                    if (null != service.getDataTypes()) {
                        for (Object dataType : service.getDataTypes().getCompositeOrEnumeration()) {
                            writeDataType(dataType);
                            writeLine();
                        }
                    }
                    writeErrors(service.getErrors());
                    outdent();
                    writeLine("}");
                    writeLine();
                    currentService = null;
                }

                if (null != area.getDataTypes()) {
                    for (Object dataType : area.getDataTypes().getFundamentalOrAttributeOrComposite()) {
                        writeDataType(dataType);
                        writeLine();
                    }
                }

                writeErrors(area.getErrors());
                currentArea = null;
                currentWriter.close();
                logger.debug("Generated MOSDL file '{}", targetFile);
            } catch (IOException ex) {
                throw new GeneratorException(ex);
            }
        }
        logger.debug("Generated all MOSDL files for the supplied specification into directory '{}'.", targetDirectory);
    }

    private void writeMessage(String prefix, MessageDetails msg) throws IOException {
        if (null != prefix || (docType == DocType.INLINE && null != msg.getComment())) {
            writeLine();
        }
        if (docType == DocType.INLINE) {
            writeDoc(msg.getComment());
        }
        if (null != prefix) {
            writeIndent();
        }
        write(prefix, "(");
        boolean isMultiline = docType == DocType.INLINE && msg.getFields().stream().anyMatch(field -> null != field.getComment());
        if (isMultiline) {
            writeLine();
            indent();
        }
        Iterator<NamedElementReferenceWithCommentType> fieldIter = msg.getFields().iterator();
        while (fieldIter.hasNext()) {
            NamedElementReferenceWithCommentType field = fieldIter.next();
            if (docType == DocType.INLINE) {
                writeDoc(field.getComment());
            }
            if (isMultiline) {
                writeIndent();
            }
            write(getId(field.getName()), ": ", resolveType(field.getType(), field.isCanBeNull()));
            if (fieldIter.hasNext()) {
                write(", ");
            }
            if (isMultiline) {
                writeLine();
            }
        }
        if (isMultiline) {
            outdent();
            writeIndent();
        }
        write(")");
    }

    private void writeDataType(Object dataType) throws IOException {
        if (dataType instanceof CompositeType) {
            CompositeType composite = (CompositeType) dataType;
            writeDoc(composite.getComment());
            boolean isAbstract = null == composite.getShortFormPart() || 0 == composite.getShortFormPart();
            writeIndent();
            if (isAbstract) {
                write("abstract ");
            }
            write("composite ", getId(composite.getName()));
            if (!isAbstract) {
                write(" [", composite.getShortFormPart(), "]");
            }
            if (!isAbstract && null != composite.getExtends()) {
                write(" extends ", resolveType(composite.getExtends().getType()));
            }
            write(" {");
            writeLine();
            indent();
            for (NamedElementReferenceWithCommentType field : composite.getField()) {
                writeDoc(field.getComment());
                writeLine(getId(field.getName()), ": ", resolveType(field.getType(), field.isCanBeNull()));
            }
            outdent();
            writeLine("}");
        } else if (dataType instanceof EnumerationType) {
            EnumerationType enumeration = (EnumerationType) dataType;
            writeDoc(enumeration.getComment());
            writeLine("enum ", getId(enumeration.getName()), " [", enumeration.getShortFormPart(), "] {");
            indent();
            for (EnumerationType.Item item : enumeration.getItem()) {
                writeDoc(item.getComment());
                writeLine(getId(item.getValue()), " [", item.getNvalue(), "]");
            }
            outdent();
            writeLine("}");
        } else if (dataType instanceof AttributeType) {
            AttributeType attribute = (AttributeType) dataType;
            writeDoc(attribute.getComment());
            writeLine("attribute ", getId(attribute.getName()), " [", attribute.getShortFormPart(), "]");
        } else if (dataType instanceof FundamentalType) {
            FundamentalType fundamental = (FundamentalType) dataType;
            String extension = "";
            if (null != fundamental.getExtends()) {
                extension = " extends " + resolveType(fundamental.getExtends().getType());
            }
            writeDoc(fundamental.getComment());
            writeLine("fundamental ", getId(fundamental.getName()), extension);
        }
    }

    private void writeErrors(List<ErrorDefinitionType> errorList) throws IOException {
        if (null == errorList) {
            return;
        }
        for (ErrorDefinitionType error : errorList) {
            writeDoc(error.getComment());
            writeIndent();
            writeError(error, true);
            writeLine();
            writeLine();
        }
    }

    private String resolveType(TypeReference typeRef) {
        return resolveType(typeRef, false);
    }

    private String resolveType(TypeReference typeRef, boolean isCanBeNull) {
        boolean isFundamental = MAL_AREA.equals(typeRef.getArea())
                && null == typeRef.getService()
                && MAL_FUNDAMENTALS.contains(typeRef.getName());
        boolean isSameArea = null != currentArea && currentArea.getName().equals(typeRef.getArea());
        boolean isSameService = null != currentService && currentService.getName().equals(typeRef.getService());

        StringBuilder sb = new StringBuilder();
        if (typeRef.isList()) {
            sb.append("List");
            if (isCanBeNull) {
                sb.append("?");
            }
            sb.append("<");
        }
        // Simple check for same area alone can lead to wrong type references 
        // if a type with same name is present in the service and the area.
        if (!isFundamental && !(isSameArea && isSameService)) {
            sb.append(getId(typeRef.getArea()));
            sb.append("::");
        }
        if (null != typeRef.getService() && !isSameService) {
            sb.append(getId(typeRef.getService()));
            sb.append(".");
        }
        sb.append(getId(typeRef.getName()));
        if (!typeRef.isList() && isCanBeNull) {
            sb.append("?");
        }
        if (typeRef.isList()) {
            sb.append(">");
        }
        return sb.toString();
    }

    private void writeOpError(Object error) throws IOException {
        if (error instanceof ErrorReferenceType) {
            writeError((ErrorReferenceType) error);
        } else if (error instanceof ErrorDefinitionType) {
            writeError((ErrorDefinitionType) error, false);
        }
    }

    private void writeError(ErrorReferenceType errorRef) throws IOException {
        write(resolveType(errorRef.getType()));
        writeErrorExtraInfo(errorRef.getExtraInformation(), false);
    }

    private void writeError(ErrorDefinitionType error, boolean isAreaOrServiceLevel) throws IOException {
        write("error ", getId(error.getName()), " [", error.getNumber(), "]");
        writeErrorExtraInfo(error.getExtraInformation(), isAreaOrServiceLevel);
    }

    private void writeErrorExtraInfo(ElementReferenceWithCommentType extraInfo, boolean isAreaOrServiceLevel) throws IOException {
        if (null != extraInfo) {
            if ((isAreaOrServiceLevel || docType == DocType.INLINE) && null != extraInfo.getComment()) {
                write(":");
                writeLine();
                indent();
                writeDoc(extraInfo.getComment());
                writeIndent();
                write(resolveType(extraInfo.getType()));
                outdent();
            } else {
                write(": ", resolveType(extraInfo.getType()));
            }
        }
    }

    private void writeOpDoc(OperationType op, List<MessageDetails> messages, List<Object> errors, boolean hasErrorDoc) throws IOException {
        switch (docType) {
            case SUPPRESS:
                return;
            case INLINE:
                writeDoc(op.getComment());
                return;
            case BULK:
            // continue
            default:
            // continue
        }

        StringBuilder sb = new StringBuilder(null == op.getComment() ? "" : op.getComment());
        for (MessageDetails msg : messages) {
            if (null != msg.getComment() || !msg.getFields().isEmpty()) {
                sb.append(System.lineSeparator());
            }

            if ("notify".equals(stageToTagMap.get(msg.getStage()))) {
                continue;
            }

            // write message doc
            if (null != msg.getComment()) {
                sb.append(System.lineSeparator());
                sb.append("@");
                sb.append(stageToTagMap.get(msg.getStage()));
                sb.append(": ");
                sb.append(msg.getComment());
            }
            for (NamedElementReferenceWithCommentType field : msg.getFields()) {
                // write message field doc
                if (null != field.getComment()) {
                    sb.append(System.lineSeparator());
                    sb.append("@");
                    sb.append(stageToTagMap.get(msg.getStage()));
                    sb.append("param ");
                    sb.append(field.getName());
                    sb.append(": ");
                    sb.append(field.getComment());
                }
            }
        }

        if (hasErrorDoc) {
            sb.append(System.lineSeparator());
        }
        for (Object error : errors) {
            // write error doc
            String errorName;
            String errorComment;
            String extraComment;
            if (error instanceof ErrorReferenceType) {
                ErrorReferenceType errRef = (ErrorReferenceType) error;
                errorName = resolveType(errRef.getType());
                errorComment = errRef.getComment();
                extraComment = null == errRef.getExtraInformation() ? null : errRef.getExtraInformation().getComment();
            } else if (error instanceof ErrorDefinitionType) {
                ErrorDefinitionType errDef = (ErrorDefinitionType) error;
                errorName = errDef.getName();
                errorComment = errDef.getComment();
                extraComment = null == errDef.getExtraInformation() ? null : errDef.getExtraInformation().getComment();
            } else {
                continue;
            }
            if (null != errorComment) {
                sb.append(System.lineSeparator());
                sb.append("@error ");
                sb.append(errorName);
                sb.append(": ");
                sb.append(errorComment);
            }
            if (null != extraComment) {
                sb.append(System.lineSeparator());
                sb.append("@errorinfo ");
                sb.append(errorName);
                sb.append(": ");
                sb.append(extraComment);
            }
        }

        String doc = sb.toString();
        if (doc.trim().isEmpty()) {
            return;
        }
        writeDoc(doc);
    }

    private List<Object> getErrorsFromOp(OperationType op, InteractionType interaction) {
        List<Object> errors;
        switch (interaction) {
            case SEND:
                return Collections.emptyList();
            case SUBMIT:
                errors = ((SubmitOperationType) op).getErrors();
                break;
            case REQUEST:
                errors = ((RequestOperationType) op).getErrors();
                break;
            case INVOKE:
                errors = ((InvokeOperationType) op).getErrors();
                break;
            case PROGRESS:
                errors = ((ProgressOperationType) op).getErrors();
                break;
            case PUBSUB:
                errors = ((PubSubOperationType) op).getErrors();
                break;
            default:
                return Collections.emptyList();
        }
        if (null == errors) {
            return Collections.emptyList();
        }
        return errors;
    }

    private boolean hasErrorDoc(Object error) {
        if (error instanceof ErrorDefinitionType) {
            ErrorDefinitionType e = (ErrorDefinitionType) error;
            return null != e.getComment() || (null != e.getExtraInformation() && null != e.getExtraInformation().getComment());
        } else if (error instanceof ErrorReferenceType) {
            ErrorReferenceType e = (ErrorReferenceType) error;
            return null != e.getComment() || (null != e.getExtraInformation() && null != e.getExtraInformation().getComment());
        }
        return false;
    }

    private void writeDoc(CharSequence doc) throws IOException {
        if (docType == DocType.SUPPRESS || null == doc) {
            return;
        }
        if (doc.toString().contains("\n")) {
            String txtStart = (javaComments) ? "/**" : "\"\"\"";
            writeLine(txtStart);
            String[] docLines = doc.toString().trim().split("\\r\\n|\\n");
            String txtMiddle = (javaComments) ? " * " : "";
            for (String line : docLines) {
                writeLine(txtMiddle + line);
            }
            String txtEnd = (javaComments) ? " **/" : "\"\"\"";
            writeLine(txtEnd);
        } else {
            writeLine("/// ", doc);
        }
    }

    private void writeLine(Object... text) throws IOException {
        if (null != text && text.length != 0) {
            writeIndent();
            write(text);
        }
        write(System.lineSeparator());
    }

    private void writeIndent() throws IOException {
        write(String.join("", Collections.nCopies(currentIndent, "\t")));
    }

    private void indent() {
        currentIndent++;
    }

    private void outdent() {
        currentIndent--;
        if (currentIndent < 0) {
            currentIndent = 0;
        }
    }

    private void write(Object... text) throws IOException {
        if (null == text) {
            return;
        }
        for (Object cs : text) {
            currentWriter.append(Objects.toString(cs, ""));
        }
    }

    private String getId(String id) {
        if (KEYWORDS.contains(id)) {
            return "\"" + id + "\"";
        }
        return id;
    }

    private static String createFQTypeName(AreaType area, ServiceType service, TypeReference type) {
        return createFQTypeName(area, service, type, type.isList());
    }

    private static String createFQTypeName(AreaType area, ServiceType service, TypeReference type, boolean isList) {
        String servicename = (null == service) ? "" : service.getName();
        return createFQTypeName(area.getName(), servicename, type.getArea(), type.getService(), type.getName(), isList);
    }

    private static String createFQTypeName(AreaType area, ServiceType service, ObjectReference type) {
        String servicename = (null == service) ? "" : service.getName();
        return createFQTypeName(area.getName(), servicename, type.getArea(), type.getService(), String.valueOf(type.getNumber()), false);
    }

    private static String createFQTypeName(String myArea, String myService, String typeArea, String typeService, String typeName, boolean isList) {
        StringBuilder buf = new StringBuilder();

        if (!myArea.equalsIgnoreCase(typeArea)) {
            buf.append(typeArea);
            buf.append("::");
        }

        if ((null != typeService) && (0 < typeService.length() && !typeService.equalsIgnoreCase(myService))) {
            buf.append(typeService);
            buf.append("::");
        }

        buf.append(typeName);

        if (isList) {
            buf.insert(0, "List<");
            buf.append(">");
        }

        return buf.toString();
    }

}
