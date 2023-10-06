// Copyright 2019 DLR - GSOC
// SPDX-License-Identifier: Apache-2.0
package esa.mo.navigator.parsers;

import de.dlr.gsoc.mcds.mosdl.InteractionStage;
import de.dlr.gsoc.mcds.mosdl.MOSDLBaseListener;
import de.dlr.gsoc.mcds.mosdl.MOSDLLexer;
import de.dlr.gsoc.mcds.mosdl.MOSDLParser;
import de.dlr.gsoc.mcds.mosdl.loaders.LoaderException;
import de.dlr.gsoc.mcds.mosdl.loaders.SpecLoader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBElement;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.ccsds.schema.serviceschema.AnyTypeReference;
import org.ccsds.schema.serviceschema.AreaDataTypeList;
import org.ccsds.schema.serviceschema.AreaType;
import org.ccsds.schema.serviceschema.AttributeType;
import org.ccsds.schema.serviceschema.CapabilitySetType;
import org.ccsds.schema.serviceschema.CompositeType;
import org.ccsds.schema.serviceschema.DataTypeList;
import org.ccsds.schema.serviceschema.ElementReferenceType;
import org.ccsds.schema.serviceschema.ElementReferenceWithCommentType;
import org.ccsds.schema.serviceschema.EnumerationType;
import org.ccsds.schema.serviceschema.ErrorDefinitionType;
import org.ccsds.schema.serviceschema.ErrorReferenceType;
import org.ccsds.schema.serviceschema.FundamentalType;
import org.ccsds.schema.serviceschema.InvokeOperationType;
import org.ccsds.schema.serviceschema.NamedElementReferenceWithCommentType;
import org.ccsds.schema.serviceschema.ObjectFactory;
import org.ccsds.schema.serviceschema.OperationType;
import org.ccsds.schema.serviceschema.ProgressOperationType;
import org.ccsds.schema.serviceschema.PubSubOperationType;
import org.ccsds.schema.serviceschema.RequestOperationType;
import org.ccsds.schema.serviceschema.SendOperationType;
import org.ccsds.schema.serviceschema.ServiceType;
import org.ccsds.schema.serviceschema.SpecificationType;
import org.ccsds.schema.serviceschema.SubmitOperationType;
import org.ccsds.schema.serviceschema.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specification loader to load an MO service specification from a set of MOSDL
 * files.
 * <p>
 * All MOSDL files comprising a single specification need to be supplied in the
 * call to {@link #setInput(File...)}. MOSDL files need to have '.mosdl' file
 * ending and have to conform to the MO service description language.
 * Alternatively, directories containing MOSDL files may be supplied.
 */
public class MosdlSpecLoader implements SpecLoader {

    private static final Logger logger = LoggerFactory.getLogger(MosdlSpecLoader.class);

    private static final String MOSDL_SPEC_FILE_ENDING = ".mosdl";
    private final boolean isLaxMode;
    private File[] files = {};

    /**
     * Create a new MOSDL loader instance.
     *
     * @param isLaxMode {@code true} to try to recover from errors,
     * {@code false} to always fail on errors
     */
    public MosdlSpecLoader(final boolean isLaxMode) {
        this.isLaxMode = isLaxMode;
    }

    /**
     * Sets the input files or directories that comprise a single MO service
     * specification in MOSDL format.
     * <p>
     * @param input a set of MOSDL files that comprise an MO service
     * specification. Alternatively, directories containing MOSDL files.
     */
    @Override
    public void setInput(File... input) {
        this.files = input;
    }

    @Override
    public File[] getInput() {
        return files;
    }

    @Override
    public boolean isLoadable() {
        boolean isLoadable;
        for (File file : files) {
            if (file.isDirectory()) {
                isLoadable = Arrays.stream(file.listFiles()).anyMatch(MosdlSpecLoader::isLoadable);
            } else {
                isLoadable = isLoadable(file);
            }
            if (isLoadable) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLoadable(final File singleFile) {
        return singleFile.isFile() && singleFile.getPath().toLowerCase().endsWith(MOSDL_SPEC_FILE_ENDING);
    }

    @Override
    public SpecificationType load() throws LoaderException {
        List<File> inputFiles = Arrays.stream(files)
                .flatMap(f -> f.isDirectory() ? Arrays.stream(f.listFiles()) : Stream.of(f))
                .filter(MosdlSpecLoader::isLoadable)
                .collect(Collectors.toList());

        logger.debug("Loading specification from {} MOSDL file(s): {}", inputFiles.size(), inputFiles);
        SpecificationType spec = new SpecificationType();
        ParseListener parseListener = new ParseListener(spec, isLaxMode);
        ErrorListener errorListener = new ErrorListener(isLaxMode);
        for (File file : inputFiles) {
            logger.debug("Loading MOSDL spec file '{}'. Lax mode: {}", file, isLaxMode);
            try {
                CharStream input = CharStreams.fromFileName(file.getPath());
                MOSDLLexer lexer = new MOSDLLexer(input);
                lexer.removeErrorListeners(); // remove default listener
                lexer.addErrorListener(errorListener);
                MOSDLParser parser = new MOSDLParser(new CommonTokenStream(lexer));
                parser.removeErrorListeners(); // remove default listener
                parser.addErrorListener(errorListener);
                ParseTree parseTree = parser.area();
                ParseTreeWalker.DEFAULT.walk(parseListener, parseTree);
                logger.debug("Loaded MOSDL spec file '{}'.", file);
            } catch (ParseCancellationException | IOException ex) {
                throw new LoaderException(ex);
            }
        }
        logger.debug("Loaded complete specification from {} MOSDL file(s).", inputFiles.size());
        return spec;
    }

    public static class ErrorListener extends BaseErrorListener {

        private final boolean isLaxMode;

        public ErrorListener(boolean isLaxMode) {
            this.isLaxMode = isLaxMode;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, 
                int line, int charPositionInLine, String msg, RecognitionException e) {
            String errorMsg = String.format("Syntax error in %s:%d:%d - %s", 
                    recognizer.getInputStream().getSourceName(), line, charPositionInLine, msg);
            
            if (isLaxMode) {
                errorMsg += ". Trying to recover.";
            }
            logger.error(errorMsg);
            if (!isLaxMode) {
                throw new ParseCancellationException(errorMsg);
            }
        }

    }

    /**
     * Class to listen for parser events from which the service specification is
     * built successively.
     */
    public static class ParseListener extends MOSDLBaseListener {

        private static final String LIST_GENERIC = "List";
        private static final String MAL_AREA = "MAL";
        private static final String[] MAL_FUNDAMENTALS = {
            "Blob", "Boolean", "Double", "Duration", "FineTime", "Float",
            "Identifier", "Integer", "Long", "Octet", "Short", "String",
            "Time", "UInteger", "ULong", "UOctet", "URI", "UShort",
            "Attribute", "Composite", "Element", "Object"
        };
        private static final String DOC_TOKEN = "\"\"\"";
        private static final String LINE_DOC_TOKEN = "///";
        private static final int START_USER_AREA = 256; // 1: MAL, 2: COM, 3: Common, 4: MC
        private static final int START_USER_SERVICE = 1;
        private static final int START_USER_AREATYPE = 1;
        private static final int START_USER_SERVICETYPE = 1;
        private static final int START_USER_CAPABILITYSET = 1;
        private static final int START_USER_OPERATION = 1;
        private static final int START_USER_ERROR = 1;
        private static final int START_ENUM_VALUE = 1;
        private static final short DEFAULT_VERSION = 1;
        private final boolean isLaxMode;
        private final SpecificationType spec;
        private AreaType currentArea;
        private ServiceType currentService;
        private CapabilitySetType currentCapabilitySet;
        private OperationType currentOperation;
        private OperationDoc currentOpDoc;
        private InteractionStage currentStage;
        private EnumerationType currentEnum;
        private CompositeType currentComposite;
        private final Deque<AnyTypeReference> currentOpMessages = new ArrayDeque<>();
        private List<Object> currentErrors;
        private final AtomicInteger areaCounter = new AtomicInteger(START_USER_AREA);
        private final AtomicInteger serviceCounter = new AtomicInteger(START_USER_SERVICE);
        private final AtomicInteger areaTypeCounter = new AtomicInteger(START_USER_AREATYPE);
        private final AtomicInteger serviceTypeCounter = new AtomicInteger(START_USER_SERVICETYPE);
        private final AtomicInteger capabilitySetCounter = new AtomicInteger(START_USER_CAPABILITYSET);
        private final AtomicInteger operationCounter = new AtomicInteger(START_USER_OPERATION);
        private final AtomicInteger enumItemCounter = new AtomicInteger(START_ENUM_VALUE);
        private final AtomicInteger errorCounter = new AtomicInteger(START_USER_ERROR);
        private final Map<String, TypeReference> importedTypes = new HashMap<>();
        private final Map<String, Set<TypeReference>> postponedTypes = new HashMap<>();
        private final ObjectFactory of = new ObjectFactory();

        /**
         * Create a new listener instance.
         *
         * @param isLaxMode {@code true} to try to recover from errors,
         * {@code false} to always fail on errors
         * @param spec the specification to add the new specification created
         * from the parser events to
         */
        public ParseListener(final SpecificationType spec, final boolean isLaxMode) {
            this.spec = spec;
            this.isLaxMode = isLaxMode;
            initImportedTypes();
            initCounters();
        }

        /**
         * Automatically import the fundamental MAL data types so they can be
         * referred to by unqualified name.
         */
        private void initImportedTypes() {
            importedTypes.clear();
            // add fundamental MAL types to imported types by default
            for (String malDataType : MAL_FUNDAMENTALS) {
                TypeReference typeRef = new TypeReference();
                typeRef.setArea(MAL_AREA);
                typeRef.setName(malDataType);
                importedTypes.put(malDataType, typeRef);
            }
        }

        /**
         * Initialize counters for numbers that act as identifiers.
         * <p>
         * This mechanism allows the user to omit identification numbers and
         * have them auto-generated. Counters are initialized to the highest
         * numbers already present in the spec so that any user-set numbers are
         * not overwritten.
         */
        private void initCounters() {
            // initialize appropriate counters to highest ones already present in spec
            int maxArea = START_USER_AREA - 1; // subtract one because this number was not yet assigned, but here we try to find the highest assigned number
            int maxService = START_USER_SERVICE - 1;
            int maxAreaType = START_USER_AREATYPE - 1;
            int maxError = START_USER_ERROR - 1;
            for (AreaType area : spec.getArea()) {
                maxArea = Math.max(maxArea, area.getNumber());
                for (ServiceType service : area.getService()) {
                    maxService = Math.max(maxService, service.getNumber());
                    for (ErrorDefinitionType error : service.getErrors()) {
                        maxError = Math.max(maxError, (int) error.getNumber());
                    }
                    for (CapabilitySetType cs : service.getCapabilitySet()) {
                        for (OperationType op : cs.getSendIPOrSubmitIPOrRequestIP()) {
                            if (op instanceof SubmitOperationType) {
                                for (Object error : ((SubmitOperationType) op).getErrors()) {
                                    if (error instanceof ErrorDefinitionType) {
                                        maxError = Math.max(maxError, (int) ((ErrorDefinitionType) error).getNumber());
                                    }
                                }
                            } else if (op instanceof RequestOperationType) {
                                for (Object error : ((RequestOperationType) op).getErrors()) {
                                    if (error instanceof ErrorDefinitionType) {
                                        maxError = Math.max(maxError, (int) ((ErrorDefinitionType) error).getNumber());
                                    }
                                }
                            } else if (op instanceof InvokeOperationType) {
                                for (Object error : ((InvokeOperationType) op).getErrors()) {
                                    if (error instanceof ErrorDefinitionType) {
                                        maxError = Math.max(maxError, (int) ((ErrorDefinitionType) error).getNumber());
                                    }
                                }
                            } else if (op instanceof ProgressOperationType) {
                                for (Object error : ((ProgressOperationType) op).getErrors()) {
                                    if (error instanceof ErrorDefinitionType) {
                                        maxError = Math.max(maxError, (int) ((ErrorDefinitionType) error).getNumber());
                                    }
                                }
                            } else if (op instanceof PubSubOperationType) {
                                for (Object error : ((PubSubOperationType) op).getErrors()) {
                                    if (error instanceof ErrorDefinitionType) {
                                        maxError = Math.max(maxError, (int) ((ErrorDefinitionType) error).getNumber());
                                    }
                                }
                            }
                        }
                    }
                }
                if (null != area.getDataTypes()) {
                    for (Object dataType : area.getDataTypes().getFundamentalOrAttributeOrComposite()) {
                        int typeNumber = 0;
                        if (dataType instanceof EnumerationType) {
                            typeNumber = (int) ((EnumerationType) dataType).getShortFormPart();
                        } else if (dataType instanceof CompositeType) {
                            Long tempTypeNumber = ((CompositeType) dataType).getShortFormPart();
                            if (null != tempTypeNumber) {
                                typeNumber = tempTypeNumber.intValue();
                            }
                        } else if (dataType instanceof AttributeType) {
                            typeNumber = (int) ((AttributeType) dataType).getShortFormPart();
                        }
                        maxAreaType = Math.max(maxAreaType, typeNumber);
                    }
                }
                for (ErrorDefinitionType error : area.getErrors()) {
                    maxError = Math.max(maxError, (int) error.getNumber());
                }
            }
            areaCounter.set(maxArea + 1);
            serviceCounter.set(maxService + 1);
            areaTypeCounter.set(maxAreaType + 1);
            errorCounter.set(maxError + 1);
        }

        @Override
        public void enterArea(MOSDLParser.AreaContext ctx) {
            String areaName = getName(ctx.nvidentifier());
            currentArea = spec.getArea().stream()
                    .filter(a -> a.getName().equals(areaName))
                    .findAny().orElse(null);
            boolean isNewArea = null == currentArea;
            if (isNewArea) {
                currentArea = new AreaType();
                spec.getArea().add(currentArea);
                currentArea.setName(areaName);
                currentArea.setVersion(getVersion(ctx.nvidentifier()));
                currentArea.setNumber(claimNumber(ctx.nvidentifier(), areaCounter));
            } else if (null != getNumber(ctx.nvidentifier())) {
                // TODO: Rethink how to handle existing areas with differing numbers and/or versions.
                // Currently: Only change number of already existing area if a new number has been specified.
                currentArea.setNumber(claimNumber(ctx.nvidentifier(), areaCounter));
            }
            currentArea.setComment(getDoc(ctx.doc()));
        }

        @Override
        public void exitArea(MOSDLParser.AreaContext ctx) {
            initImportedTypes();
            initCounters();
            currentArea = null;
        }

        @Override
        public void enterTypeImport(MOSDLParser.TypeImportContext ctx) {
            MOSDLParser.FullyQualifiedTypeContext fqt = ctx.fullyQualifiedType();
            String typeName;
            TypeReference typeRef = new TypeReference();
            typeRef.setArea(getId(fqt.ID(0)));
            if (fqt.ID().size() == 2) {
                typeName = getId(fqt.ID(1));
            } else {
                typeRef.setService(getId(fqt.ID(1)));
                typeName = getId(fqt.ID(2));
            }
            typeRef.setName(typeName);
            importedTypes.put(typeName, typeRef);
        }

        @Override
        public void enterError(MOSDLParser.ErrorContext ctx) {
            ErrorDefinitionType error = new ErrorDefinitionType();
            String errorName = getName(ctx.nidentifier());
            error.setName(errorName);
            error.setNumber(claimNumber(ctx.nidentifier(), errorCounter));
            String doc = getDoc(ctx.doc());
            if (null != ctx.extraInfo()) {
                ElementReferenceWithCommentType extraInfo = new ElementReferenceWithCommentType();
                extraInfo.setType(getType(ctx.extraInfo().nonNullableType()));
                String extraInfoDoc = getDoc(ctx.extraInfo().doc());
                if (null != currentErrors) {
                    // if active operation merge potential extra info doc of operation
                    extraInfo.setComment(currentOpDoc.getErrorInfoDoc(errorName, extraInfoDoc));
                } else {
                    extraInfo.setComment(extraInfoDoc);
                }
                error.setExtraInformation(extraInfo);
            }
            if (null != currentErrors) {
                // if active operation add error to that (and merge potential error doc of operation)
                error.setComment(currentOpDoc.getErrorDoc(errorName, doc));
                currentErrors.add(error);
            } else if (null != currentService) {
                error.setComment(doc);
                currentService.getErrors().add(error);
            } else {
                error.setComment(doc);
                currentArea.getErrors().add(error);
            }
        }

        @Override
        public void enterErrorRef(MOSDLParser.ErrorRefContext ctx) {
            ErrorReferenceType errorRef = new ErrorReferenceType();
            errorRef.setType(getType(ctx.nonNullableType()));
            String errorName = ctx.nonNullableType().getText();
            errorRef.setComment(currentOpDoc.getErrorDoc(errorName, getDoc(ctx.doc())));
            if (null != ctx.extraInfo()) {
                ElementReferenceWithCommentType extraInfo = new ElementReferenceWithCommentType();
                extraInfo.setType(getType(ctx.extraInfo().nonNullableType()));
                extraInfo.setComment(currentOpDoc.getErrorInfoDoc(errorName, getDoc(ctx.extraInfo().doc())));
                errorRef.setExtraInformation(extraInfo);
            }
            currentErrors.add(errorRef);
        }

        @Override
        public void enterService(MOSDLParser.ServiceContext ctx) {
            currentService = new ServiceType();
            currentService.setName(getName(ctx.nidentifier()));
            currentService.setNumber(claimNumber(ctx.nidentifier(), serviceCounter));
            String comment = getDoc(ctx.doc()).replace("\n", "&#10;");
            currentService.setComment(comment);
            currentArea.getService().add(currentService);
        }

        @Override
        public void exitService(MOSDLParser.ServiceContext ctx) {
            // get all service-level data types that are also contained in the postponed types list
            // and set the service name for the stored type reference
            if (null != currentService.getDataTypes()) {
                currentService.getDataTypes().getCompositeOrEnumeration().stream()
                        .map(dt -> {
                            if (dt instanceof CompositeType) {
                                return ((CompositeType) dt).getName();
                            } else {
                                return ((EnumerationType) dt).getName();
                            }
                        })
                        .filter(postponedTypes::containsKey)
                        .forEach(typeName -> postponedTypes.get(typeName).forEach(typeRef -> typeRef.setService(currentService.getName())));
            }
            // do the same for service-level errors
            currentService.getErrors().stream()
                    .map(e -> e.getName())
                    .filter(postponedTypes::containsKey)
                    .forEach(errorName -> postponedTypes.get(errorName).forEach(typeRef -> typeRef.setService(currentService.getName())));

            // clear also all area-level type references because they have been declared area-level by default
            postponedTypes.clear();
            serviceTypeCounter.set(START_USER_SERVICETYPE);
            capabilitySetCounter.set(START_USER_CAPABILITYSET);
            operationCounter.set(START_USER_OPERATION);
            currentService = null;
        }

        @Override
        public void enterComposite(MOSDLParser.CompositeContext ctx) {
            boolean isAbstract = null != ctx.ABSTRACT();
            CompositeType compType = new CompositeType();
            if (isAbstract) {
                compType.setName(getId(ctx.ID()));
            } else {
                compType.setName(getName(ctx.nidentifier()));
            }
            compType.setComment(getDoc(ctx.doc()));
            if (null != ctx.EXTENDS()) {
                ElementReferenceType refType = new ElementReferenceType();
                refType.setType(getType(ctx.type()));
                compType.setExtends(refType);
            }
            currentComposite = compType;

            // add composite to service or area
            if (null != currentService) {
                DataTypeList dataTypeList = currentService.getDataTypes();
                if (null == dataTypeList) {
                    dataTypeList = new DataTypeList();
                    currentService.setDataTypes(dataTypeList);
                }
                if (!isAbstract) {
                    compType.setShortFormPart(claimNumber(ctx.nidentifier(), serviceTypeCounter).longValue());
                }
                dataTypeList.getCompositeOrEnumeration().add(compType);
            } else {
                AreaDataTypeList dataTypeList = currentArea.getDataTypes();
                if (null == dataTypeList) {
                    dataTypeList = new AreaDataTypeList();
                    currentArea.setDataTypes(dataTypeList);
                }
                if (!isAbstract) {
                    compType.setShortFormPart(claimNumber(ctx.nidentifier(), areaTypeCounter).longValue());
                }
                dataTypeList.getFundamentalOrAttributeOrComposite().add(compType);
            }
        }

        @Override
        public void exitComposite(MOSDLParser.CompositeContext ctx) {
            currentComposite = null;
        }

        @Override
        public void enterField(MOSDLParser.FieldContext ctx) {
            NamedElementReferenceWithCommentType field = new NamedElementReferenceWithCommentType();
            field.setName(getId(ctx.ID()));
            String doc = getDoc(ctx.doc());
            if (null == ctx.nullableType().QUEST()) {
                field.setCanBeNull(Boolean.FALSE);
            }
            field.setType(getType(ctx.nullableType()));

            // fields can be fields of composite or fields of message parameter list
            if (null != currentComposite) {
                // String comment = doc.replace("\n", "&#10;");
                field.setComment(doc);
                currentComposite.getField().add(field);
            } else {
                //String comment = currentOpDoc.getParamDoc(currentStage, field.getName(), doc);
                field.setComment(currentOpDoc.getParamDoc(currentStage, field.getName(), doc));
                // add field to parameter list of current message
                JAXBElement<NamedElementReferenceWithCommentType> wrappedField = of.createField(field);
                AnyTypeReference currentMsg = currentOpMessages.getLast();
                currentMsg.getAny().add(wrappedField);
            }
        }

        @Override
        public void enterEnumeration(MOSDLParser.EnumerationContext ctx) {
            EnumerationType enumType = new EnumerationType();
            enumType.setName(getName(ctx.nidentifier()));
            enumType.setComment(getDoc(ctx.doc()));
            currentEnum = enumType;

            // add enum to service or area
            if (null != currentService) {
                DataTypeList dataTypeList = currentService.getDataTypes();
                if (null == dataTypeList) {
                    dataTypeList = new DataTypeList();
                    currentService.setDataTypes(dataTypeList);
                }
                enumType.setShortFormPart(claimNumber(ctx.nidentifier(), serviceTypeCounter));
                dataTypeList.getCompositeOrEnumeration().add(enumType);
            } else {
                AreaDataTypeList dataTypeList = currentArea.getDataTypes();
                if (null == dataTypeList) {
                    dataTypeList = new AreaDataTypeList();
                    currentArea.setDataTypes(dataTypeList);
                }
                enumType.setShortFormPart(claimNumber(ctx.nidentifier(), areaTypeCounter));
                dataTypeList.getFundamentalOrAttributeOrComposite().add(enumType);
            }
        }

        @Override
        public void exitEnumeration(MOSDLParser.EnumerationContext ctx) {
            enumItemCounter.set(START_ENUM_VALUE);
            currentEnum = null;
        }

        @Override
        public void enterEnumItem(MOSDLParser.EnumItemContext ctx) {
            EnumerationType.Item enumItem = new EnumerationType.Item();
            enumItem.setValue(getName(ctx.nidentifier()));
            enumItem.setNvalue(claimNumber(ctx.nidentifier(), enumItemCounter));
            enumItem.setComment(getDoc(ctx.doc()));
            currentEnum.getItem().add(enumItem);
        }

        @Override
        public void enterFundamental(MOSDLParser.FundamentalContext ctx) {
            FundamentalType fundamentalType = new FundamentalType();
            fundamentalType.setName(getId(ctx.ID()));
            fundamentalType.setComment(getDoc(ctx.doc()));
            if (null != ctx.EXTENDS()) {
                ElementReferenceType refType = new ElementReferenceType();
                refType.setType(getType(ctx.type()));
                fundamentalType.setExtends(refType);
            }
            AreaDataTypeList dataTypeList = currentArea.getDataTypes();
            if (null == dataTypeList) {
                dataTypeList = new AreaDataTypeList();
                currentArea.setDataTypes(dataTypeList);
            }
            dataTypeList.getFundamentalOrAttributeOrComposite().add(fundamentalType);
        }

        @Override
        public void enterAttribute(MOSDLParser.AttributeContext ctx) {
            AttributeType attrType = new AttributeType();
            attrType.setName(getName(ctx.nidentifier()));
            attrType.setComment(getDoc(ctx.doc()));
            attrType.setShortFormPart(claimNumber(ctx.nidentifier(), areaTypeCounter).longValue());
            AreaDataTypeList dataTypeList = currentArea.getDataTypes();
            if (null == dataTypeList) {
                dataTypeList = new AreaDataTypeList();
                currentArea.setDataTypes(dataTypeList);
            }
            dataTypeList.getFundamentalOrAttributeOrComposite().add(attrType);
        }

        @Override
        public void enterCapability(MOSDLParser.CapabilityContext ctx) {
            currentCapabilitySet = new CapabilitySetType();
            currentCapabilitySet.setNumber(claimNumber(ctx.NUMBER(), capabilitySetCounter));
            currentCapabilitySet.setComment(getDoc(ctx.doc()));
            currentService.getCapabilitySet().add(currentCapabilitySet);
        }

        @Override
        public void exitCapability(MOSDLParser.CapabilityContext ctx) {
            currentCapabilitySet = null;
        }

        @Override
        public void enterOperation(MOSDLParser.OperationContext ctx) {
            currentOpDoc = new OperationDoc(getDoc(ctx.doc()));
        }

        @Override
        public void enterSendOp(MOSDLParser.SendOpContext ctx) {
            SendOperationType op = new SendOperationType();
            op.setName(getName(ctx.nidentifier()));
            op.setNumber(claimNumber(ctx.nidentifier(), operationCounter));
            currentOperation = op;
        }

        @Override
        public void enterSubmitOp(MOSDLParser.SubmitOpContext ctx) {
            SubmitOperationType op = new SubmitOperationType();
            op.setName(getName(ctx.nidentifier()));
            op.setNumber(claimNumber(ctx.nidentifier(), operationCounter));
            currentErrors = op.getErrors();
            currentOperation = op;
        }

        @Override
        public void enterRequestOp(MOSDLParser.RequestOpContext ctx) {
            RequestOperationType op = new RequestOperationType();
            op.setName(getName(ctx.nidentifier()));
            op.setNumber(claimNumber(ctx.nidentifier(), operationCounter));
            currentErrors = op.getErrors();
            currentOperation = op;
        }

        @Override
        public void enterInvokeOp(MOSDLParser.InvokeOpContext ctx) {
            InvokeOperationType op = new InvokeOperationType();
            op.setName(getName(ctx.nidentifier()));
            op.setNumber(claimNumber(ctx.nidentifier(), operationCounter));
            currentErrors = op.getErrors();
            currentOperation = op;
        }

        @Override
        public void enterProgressOp(MOSDLParser.ProgressOpContext ctx) {
            ProgressOperationType op = new ProgressOperationType();
            op.setName(getName(ctx.nidentifier()));
            op.setNumber(claimNumber(ctx.nidentifier(), operationCounter));
            currentErrors = op.getErrors();
            currentOperation = op;
        }

        @Override
        public void enterPubsubOp(MOSDLParser.PubsubOpContext ctx) {
            PubSubOperationType op = new PubSubOperationType();
            op.setName(getName(ctx.nidentifier()));
            op.setNumber(claimNumber(ctx.nidentifier(), operationCounter));
            currentErrors = op.getErrors();
            currentOperation = op;
        }

        @Override
        public void exitSendOp(MOSDLParser.SendOpContext ctx) {
            SendOperationType op = (SendOperationType) currentOperation;
            SendOperationType.Messages msgs = new SendOperationType.Messages();
            msgs.setSend(currentOpMessages.removeFirst());
            op.setMessages(msgs);
            op.setSupportInReplay(null != ctx.STAR());
        }

        @Override
        public void exitSubmitOp(MOSDLParser.SubmitOpContext ctx) {
            SubmitOperationType op = (SubmitOperationType) currentOperation;
            SubmitOperationType.Messages msgs = new SubmitOperationType.Messages();
            msgs.setSubmit(currentOpMessages.removeFirst());
            op.setMessages(msgs);
            op.setSupportInReplay(null != ctx.STAR());
        }

        @Override
        public void exitRequestOp(MOSDLParser.RequestOpContext ctx) {
            RequestOperationType op = (RequestOperationType) currentOperation;
            RequestOperationType.Messages msgs = new RequestOperationType.Messages();
            msgs.setRequest(currentOpMessages.removeFirst());
            msgs.setResponse(currentOpMessages.removeFirst());
            op.setMessages(msgs);
            op.setSupportInReplay(null != ctx.STAR());
        }

        @Override
        public void exitInvokeOp(MOSDLParser.InvokeOpContext ctx) {
            InvokeOperationType op = (InvokeOperationType) currentOperation;
            InvokeOperationType.Messages msgs = new InvokeOperationType.Messages();
            msgs.setInvoke(currentOpMessages.removeFirst());
            msgs.setAcknowledgement(currentOpMessages.removeFirst());
            msgs.setResponse(currentOpMessages.removeFirst());
            op.setMessages(msgs);
            op.setSupportInReplay(null != ctx.STAR());
        }

        @Override
        public void exitProgressOp(MOSDLParser.ProgressOpContext ctx) {
            ProgressOperationType op = (ProgressOperationType) currentOperation;
            ProgressOperationType.Messages msgs = new ProgressOperationType.Messages();
            msgs.setProgress(currentOpMessages.removeFirst());
            msgs.setAcknowledgement(currentOpMessages.removeFirst());
            msgs.setUpdate(currentOpMessages.removeFirst());
            msgs.setResponse(currentOpMessages.removeFirst());
            op.setMessages(msgs);
            op.setSupportInReplay(null != ctx.STAR());
        }

        @Override
        public void exitPubsubOp(MOSDLParser.PubsubOpContext ctx) {
            PubSubOperationType op = (PubSubOperationType) currentOperation;
            PubSubOperationType.Messages msgs = new PubSubOperationType.Messages();
            msgs.setPublishNotify(currentOpMessages.removeFirst());
            op.setMessages(msgs);
            op.setSupportInReplay(null != ctx.STAR());
        }

        @Override
        public void exitOperation(MOSDLParser.OperationContext ctx) {
            if (currentOperation == null) {
                int lineNumber = ctx.getStart().getLine();
                throw new NullPointerException(String.valueOf(lineNumber));
            }
            String comment = currentOpDoc.getOperationDoc();
            currentOperation.setComment(comment);
            CapabilitySetType cs = currentCapabilitySet;
            if (null == cs) {
                // if operation defined outside of capability set, put it in its own cap set
                cs = new CapabilitySetType();
                cs.setNumber(claimNumber((TerminalNode) null, capabilitySetCounter));
                currentService.getCapabilitySet().add(cs);
            }
            currentOpMessages.clear();
            currentErrors = null;
            cs.getSendIPOrSubmitIPOrRequestIP().add(currentOperation);
            currentOperation = null;
            currentOpDoc = null;
            currentStage = null;
        }

        @Override
        public void enterSendMsg(MOSDLParser.SendMsgContext ctx) {
            enterMsg(InteractionStage.SEND, ctx.cmsg().doc());
        }

        @Override
        public void enterSubmitMsg(MOSDLParser.SubmitMsgContext ctx) {
            enterMsg(InteractionStage.SUBMIT, ctx.cmsg().doc());
        }

        @Override
        public void enterRequestMsg(MOSDLParser.RequestMsgContext ctx) {
            enterMsg(InteractionStage.REQUEST, ctx.cmsg().doc());
        }

        @Override
        public void enterRequestResponseMsg(MOSDLParser.RequestResponseMsgContext ctx) {
            enterMsg(InteractionStage.REQUEST_RESPONSE, ctx.pmsg().doc());
        }

        @Override
        public void enterInvokeMsg(MOSDLParser.InvokeMsgContext ctx) {
            enterMsg(InteractionStage.INVOKE, ctx.cmsg().doc());
        }

        @Override
        public void enterInvokeAckMsg(MOSDLParser.InvokeAckMsgContext ctx) {
            enterMsg(InteractionStage.INVOKE_ACK, ctx.pmsg().doc());
        }

        @Override
        public void enterInvokeResponseMsg(MOSDLParser.InvokeResponseMsgContext ctx) {
            enterMsg(InteractionStage.INVOKE_RESPONSE, ctx.pmsg().doc());
        }

        @Override
        public void enterProgressMsg(MOSDLParser.ProgressMsgContext ctx) {
            enterMsg(InteractionStage.PROGRESS, ctx.cmsg().doc());
        }

        @Override
        public void enterProgressAckMsg(MOSDLParser.ProgressAckMsgContext ctx) {
            enterMsg(InteractionStage.PROGRESS_ACK, ctx.pmsg().doc());
        }

        @Override
        public void enterProgressUpdateMsg(MOSDLParser.ProgressUpdateMsgContext ctx) {
            enterMsg(InteractionStage.PROGRESS_UPDATE, ctx.pmsg().doc());
        }

        @Override
        public void enterProgressResponseMsg(MOSDLParser.ProgressResponseMsgContext ctx) {
            enterMsg(InteractionStage.PROGRESS_RESPONSE, ctx.pmsg().doc());
        }

        @Override
        public void enterPubsubMsg(MOSDLParser.PubsubMsgContext ctx) {
            enterMsg(InteractionStage.PUBSUB_PUBLISH, ctx.bmsg().doc());
        }

        private void enterMsg(final InteractionStage stage, final MOSDLParser.DocContext doc) {
            currentStage = stage;
            AnyTypeReference msg = new AnyTypeReference();
            String completeDoc = currentOpDoc.getMessageDoc(stage, getDoc(doc));
            msg.setComment(completeDoc);
            currentOpMessages.addLast(msg);
        }

        private static String getDoc(final MOSDLParser.DocContext doc) {
            if (null == doc) {
                return null;
            }
            String text;
            if (null != doc.DOC()) {
                text = doc.DOC().getText().replace(DOC_TOKEN, "");
            } else {
                text = doc.LINE_DOC().getText().substring(LINE_DOC_TOKEN.length());
            }
            return text.trim();
        }

        private static Integer claimNumber(final MOSDLParser.NidentifierContext nidentifier, final AtomicInteger counter) {
            return claimNumber(nidentifier.NUMBER(), counter);
        }

        private static Integer claimNumber(final MOSDLParser.NvidentifierContext nvidentifier, final AtomicInteger counter) {
            boolean hasNumber = nvidentifier.NUMBER().size() == 2 || nvidentifier.NUMBER().size() == 1 && !hasVersion(nvidentifier);
            if (hasNumber) {
                return claimNumber(nvidentifier.NUMBER(0), counter);
            }
            return claimNumber((TerminalNode) null, counter);
        }

        /**
         * Gets the number identifier from a terminal node or create new one in
         * case no number has been specified.
         * <p>
         * In any case, the current counter is incremented so that it can supply
         * a new valid number identifier when called again.
         *
         * @param number the terminal node containing the user-specified number
         * or {@code null} if the user did not specify a number
         * @param counter the counter to use for determining the next number
         * used as identifier
         * @return a number usable as identifier
         */
        private static Integer claimNumber(final TerminalNode number, final AtomicInteger counter) {
            if (null == number) {
                return counter.getAndIncrement();
            }
            int n = Integer.decode(number.getText());
            counter.set(n + 1);
            return n;
        }

        /**
         * Gets the number of an identifier.
         * <p>
         * If no number has been specified, {@code null} is returned. This
         * method does not have side effects.
         *
         * @param nvidentifier the 'nvidentifier' parse context
         * @return the contained number or {@code null} if no number has been
         * given
         */
        private static Integer getNumber(final MOSDLParser.NvidentifierContext nvidentifier) {
            boolean hasNumber = nvidentifier.NUMBER().size() == 2 || nvidentifier.NUMBER().size() == 1 && !hasVersion(nvidentifier);
            if (hasNumber) {
                return Integer.decode(nvidentifier.NUMBER(0).getText());
            }
            return null;
        }

        private static boolean hasVersion(final MOSDLParser.NvidentifierContext nvidentifier) {
            return null != nvidentifier.DOT();
        }

        /**
         * Gets the version specified in the 'nvidentifier' parse context or the
         * default version if none was specified.
         *
         * @param nvidentifier the 'nvidentifier' parse context
         * @return the specified version number or the default version number
         */
        private static short getVersion(final MOSDLParser.NvidentifierContext nvidentifier) {
            if (!hasVersion(nvidentifier)) {
                return DEFAULT_VERSION;
            }
            if (nvidentifier.NUMBER().size() == 2) {
                return Short.decode(nvidentifier.NUMBER(1).getText());
            }
            return Short.decode(nvidentifier.NUMBER(0).getText());
        }

        private static String getName(final MOSDLParser.NidentifierContext nidentifier) {
            return getId(nidentifier.ID());
        }

        private static String getName(final MOSDLParser.NvidentifierContext nvidentifier) {
            return getId(nvidentifier.ID());
        }

        private TypeReference getType(final MOSDLParser.NonNullableTypeContext type) {
            MOSDLParser.NonNullableTypeContext t = type;
            boolean isList = false;
            if (null != type.nonNullableType()) {
                // type is the generic type, nonNullableType the contained type - only LIST_GENERIC as generic is supported
                if (null != type.type().simpleType() && LIST_GENERIC.equals(getId(type.type().simpleType().ID()))) {
                    isList = true;
                    t = type.nonNullableType();
                } else {
                    conditionallyFailOnError("Only lists are supported as generic types.", type);
                    // recovery: treat as if user had specified a list
                    isList = true;
                    t = type.nonNullableType();
                }
            }
            if (null != t.nonNullableType()) {
                conditionallyFailOnError("Generics of generics are not allowed.", type);
            }

            // resolve type
            TypeReference typeRef = getType(t.type());
            if (isList) {
                typeRef.setList(Boolean.TRUE);
            }
            return typeRef;
        }

        private TypeReference getType(final MOSDLParser.NullableTypeContext type) {
            MOSDLParser.NullableTypeContext t = type;
            boolean isList = false;
            if (null != type.nullableType()) {
                // type is the generic type, nonNullableType the contained type - only LIST_GENERIC as generic is supported
                if (null != type.type().simpleType() && LIST_GENERIC.equals(getId(type.type().simpleType().ID()))) {
                    isList = true;
                    t = type.nullableType();
                } else {
                    conditionallyFailOnError("Only lists are supported as generic types.", type);
                    // recovery: treat as if user had specified a list
                    isList = true;
                    t = type.nullableType();
                }
            }
            if (null != t.nullableType()) {
                conditionallyFailOnError("Generics of generics are not allowed.", type);
            }

            // resolve type
            TypeReference typeRef = getType(t.type());
            if (isList) {
                typeRef.setList(Boolean.TRUE);
            }
            return typeRef;
        }

        private TypeReference getType(final MOSDLParser.TypeContext type) {
            TypeReference typeRef = new TypeReference();
            if (null != type.fullyQualifiedType()) {
                MOSDLParser.FullyQualifiedTypeContext fqt = type.fullyQualifiedType();
                typeRef.setArea(getId(fqt.ID(0)));
                if (fqt.ID().size() == 2) {
                    typeRef.setName(getId(fqt.ID(1)));
                } else {
                    typeRef.setService(getId(fqt.ID(1)));
                    typeRef.setName(getId(fqt.ID(2)));
                }
            } else if (null != type.qualifiedType()) {
                typeRef.setArea(currentArea.getName());
                typeRef.setService(getId(type.qualifiedType().ID(0)));
                typeRef.setName(getId(type.qualifiedType().ID(1)));
            } else {
                // simple type can refer to imported type, type of current service or type of current area.
                // Imported types can be resolved directly.
                // If we have a service in scope, we need to postpone type resolution until the service goes
                // out of scope because we need to have a list of all types defined by the service in order
                // to decide whether an area-level or a service-level type was meant.
                String typeName = getId(type.simpleType().ID());
                typeRef.setName(typeName);
                TypeReference importedType = importedTypes.get(typeName);
                if (null != importedType) {
                    typeRef.setArea(importedType.getArea());
                    typeRef.setService(importedType.getService());
                } else if (null == currentService) {
                    typeRef.setArea(currentArea.getName());
                } else {
                    // postpone final type resolution until we can decide whether a current service or current area type is meant
                    typeRef.setArea(currentArea.getName());
                    postponedTypes.computeIfAbsent(typeName, t -> new HashSet<>()).add(typeRef);
                }
            }
            return typeRef;
        }

        private void conditionallyFailOnError(String msg, ParserRuleContext context) {
            String errorMsg = String.format("Error in %s:%d:%d - %s", 
                    context.getStart().getInputStream().getSourceName(), 
                    context.getStart().getLine(), 
                    context.getStart().getCharPositionInLine(), msg);
            if (isLaxMode) {
                errorMsg += " Trying to recover.";
            }
            logger.error(errorMsg);
            if (!isLaxMode) {
                throw new ParseCancellationException(errorMsg);
            }

        }
    }

    private static String getId(TerminalNode idNode) {
        return getId(idNode.getText());
    }

    private static String getId(String id) {
        if (id.startsWith("\"")) {
            return id.substring(1, id.length() - 1);
        }
        return id;

    }

    /**
     * Class for holding and processing the bulk documentation of an operation.
     * <p>
     * Documentation for messages and message body parts can occur in two
     * places: In 'bulk' using special tags for the operation documentation
     * (similar to Javadoc) or right before each message and message body part
     * (referred to as 'local doc' in this class). This class is responsible for
     * parsing 'bulk' documentation and providing the final documentation by
     * concatenating 'bulk' and 'local' documentation.
     */
    private static class OperationDoc {

        private final Map<TagKey, String> tags = new HashMap<>();
        private final String opDoc;

        public OperationDoc(final String doc) {
            if (null == doc) {
                opDoc = null;
                return;
            }
            // split at line breaks (or beginning of string) followed by 
            // arbitrary white space followed by @
            String[] lines = doc.split("(^|\\r?\\n)\\s*@");
            if (lines.length == 0) {
                opDoc = null;
                return;
            }
            int i = 0;
            if (lines[0].startsWith("@")) {
                opDoc = null;
            } else {
                String line = lines[0].replace("\n", "&#10;").replace("\t", "");
                opDoc = line;
                i = 1;
            }
            for (; i < lines.length; i++) {
                String line = lines[i];
                line = line.replace("\n", "&#10;").replace("\t", "");
                // a tag consists of a name, an optional parameter and a colon 
                // followed by whitespace, the tag value comes next
                String[] tagSplit = line.split(":\\s", 2);
                String tag = tagSplit[0].trim();
                String tagValue = "";
                if (tagSplit.length == 2) {
                    tagValue = tagSplit[1].trim();
                }
                if (tagValue.isEmpty()) {
                    tagValue = null;
                }
                createTagEntry(tag, tagValue);
            }
        }

        private void createTagEntry(String tag, String tagValue) {
            String[] tagDetails = tag.split("\\s", 2);
            String tagName = tagDetails[0];
            String tagParam = null;
            if (tagDetails.length == 2) {
                tagParam = getId(tagDetails[1].trim());
            }
            TagKey tagKey = new TagKey(tagName, tagParam);
            tags.put(tagKey, tagValue);
        }

        /**
         * Gets the documentation for the operation.
         *
         * @return the operation documentation.
         */
        public String getOperationDoc() {
            return opDoc;
        }

        /**
         * Gets the documentation for a single message.
         *
         * @param stage the stage denoting one of the messages of the operation
         * @param localDoc any local documentation for the message, may be
         * {@code null}
         * @return the complete documentation for the message
         */
        public String getMessageDoc(InteractionStage stage, String localDoc) {
            String tagName = getTagFromStage(stage);
            return getDoc(new TagKey(tagName, null), localDoc);
        }

        /**
         * Gets the documentation for a message body part.
         *
         * @param stage the stage denoting one of the messages of the operation
         * @param paramName the name of the message body part
         * @param localDoc any local documentation for the message body part,
         * may be {@code null}
         * @return the complete documentation for the message body part
         */
        public String getParamDoc(InteractionStage stage, String paramName, String localDoc) {
            String tagName = getTagFromStage(stage) + "param";
            return getDoc(new TagKey(tagName, paramName), localDoc);
        }

        /**
         * Gets the documentation for an error raised by the operation.
         *
         * @param errorName name of the error
         * @param localDoc any local documentation for the error, may be
         * {@code null}
         * @return the complete documentation for the error
         */
        public String getErrorDoc(String errorName, String localDoc) {
            return getDoc(new TagKey("error", errorName), localDoc);
        }

        /**
         * Gets the documentation for the extra information of an error raised
         * by the operation.
         *
         * @param errorName name of the error
         * @param localDoc any local documentation for the extra information of
         * the error
         * @return the complete documentation for the extra information of the
         * error
         */
        public String getErrorInfoDoc(String errorName, String localDoc) {
            return getDoc(new TagKey("errorinfo", errorName), localDoc);
        }

        private String getDoc(TagKey tagKey, String localDoc) {
            String tag = tags.get(tagKey);
            if (null == tag && null == localDoc) {
                return null;
            }
            if (null == tag) {
                return localDoc;
            }
            if (null == localDoc) {
                return tag;
            }
            return tag + System.lineSeparator() + localDoc;
        }

        private String getTagFromStage(InteractionStage stage) {
            String[] nameParts = stage.name().toLowerCase().split("_", 2);
            return nameParts.length == 2 ? nameParts[1] : nameParts[0];
        }

        private static class TagKey {

            private final String name;
            private final String param;

            public TagKey(String name, String param) {
                this.name = name;
                this.param = param;
            }

            @Override
            public int hashCode() {
                int hash = 7;
                hash = 89 * hash + Objects.hashCode(this.name);
                hash = 89 * hash + Objects.hashCode(this.param);
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final TagKey other = (TagKey) obj;
                if (!Objects.equals(this.name, other.name)) {
                    return false;
                }
                return Objects.equals(this.param, other.param);
            }

        }

    }
}
