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
package esa.mo.apigen.importers.mospec;

import esa.mo.apigen.importers.ImportException;
import esa.mo.apigen.importers.Importer;
import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.CapabilitySet;
import esa.mo.apigen.model.ErrorDefinition;
import esa.mo.apigen.model.ErrorReference;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.InteractionPattern;
import esa.mo.apigen.model.InteractionStage;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.SchemaVersion;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.SourceRef;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Reads a specification from MOSpec.
 * <p>
 * Recursive descent over a hand-written lexer. The language is LL(1) throughout: every
 * declaration is led by a keyword, every block is brace-delimited, and there is no
 * expression grammar to speak of - so each production can decide what it is looking at from
 * the next token alone.
 * <p>
 * The documentation of whatever comes next is carried in {@code pending}: the lexer hands
 * back one DOC token for a comment however it was spelled, and each production takes it as
 * it starts. That is what lets a comment sit above the thing it describes without every
 * production having to look backwards.
 */
public final class MOSpecImporter implements Importer {

    /**
     * The area being read, so that an unqualified name can be resolved against it.
     */
    private Area area;

    /**
     * The service being read, or null at area level.
     */
    private Service service;

    /**
     * Every type the file declares, as "area" or "area.service" against the names declared
     * there.
     * <p>
     * Gathered before parsing, because a name is used before it is declared all the time: an
     * operation names a type its own service declares further down the file. Without this an
     * unqualified name would resolve to the MAL's type of that name, silently.
     */
    private final Map<String, Set<String>> declared = new HashMap<String, Set<String>>();

    @Override
    public Specification read(Reader in, SourceRef source) throws ImportException {
        try {
            Specification spec = read(readAll(in), source);
            readSidecars(spec, source);
            return spec;
        } catch (IOException ex) {
            throw new ImportException("Could not read " + source + ": " + ex.getMessage(), ex);
        }
    }

    /**
     * Reads the drawing of each diagram from the file beside the specification.
     * <p>
     * A MOSpec specification is a directory: the text names its diagrams and the drawings
     * live next to it, because a hundred lines of SVG in the middle of a readable file
     * would defeat the point of having one. Where the file cannot be found the diagram
     * keeps its name and loses its drawing, which is what reading a half-copied directory
     * should do rather than failing outright.
     */
    private void readSidecars(Specification spec, SourceRef source) throws IOException {
        if (source.getLocation() == null) {
            return;
        }
        File beside = new File(source.getLocation()).getAbsoluteFile().getParentFile();
        if (beside == null) {
            return;
        }
        for (Area read : spec.getAreas()) {
            readSidecars(beside, read.getDocumentation().getDiagrams());
            for (Service svc : read.getServices()) {
                readSidecars(beside, svc.getDocumentation().getDiagrams());
                if (svc.getCom() != null) {
                    readSidecars(beside, svc.getCom().getDocumentation().getDiagrams());
                }
            }
        }
    }

    private void readSidecars(File beside, List<Diagram> diagrams) throws IOException {
        for (Diagram diagram : diagrams) {
            File file = new File(beside, diagram.getName() + ".svg");
            if (file.isFile()) {
                diagram.setSvg(readAll(new InputStreamReader(
                        new FileInputStream(file), Charset.forName("UTF-8"))));
            }
        }
    }

    /**
     * Reads a specification from text.
     *
     * @param text The MOSpec source.
     * @param source Where it came from.
     * @return the specification.
     * @throws ImportException if the text cannot be parsed.
     */
    public Specification read(String text, SourceRef source) throws ImportException {
        List<Token> tokens = new Lexer(text, source.getName()).tokens();
        scanDeclarations(tokens);
        Cursor c = new Cursor(tokens, source);
        Specification spec = new Specification();
        spec.setSource(source);

        String comment = doc(c);
        c.expect("specification");
        c.expect("[");
        spec.setSchemaVersion(schemaVersion(c));
        c.expect("]");
        spec.setComment(comment);

        while (!c.atEnd()) {
            spec.addArea(area(c));
        }
        return spec;
    }

    /**
     * Walks the tokens once to find out what each area and service declares, so that an
     * unqualified name can be resolved wherever it appears.
     */
    private void scanDeclarations(List<Token> tokens) {
        declared.clear();
        String currentArea = null;
        String currentService = null;
        int depth = 0;
        int serviceDepth = -1;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.is("{")) {
                depth++;
                continue;
            }
            if (token.is("}")) {
                depth--;
                if (currentService != null && depth <= serviceDepth) {
                    currentService = null;
                    serviceDepth = -1;
                }
                continue;
            }
            if (token.getKind() != Token.Kind.WORD) {
                continue;
            }
            if (token.is("area") && i + 1 < tokens.size()) {
                currentArea = nameAt(tokens, i + 1);
                currentService = null;
            } else if (token.is("service") && i + 1 < tokens.size()) {
                currentService = nameAt(tokens, i + 1);
                serviceDepth = depth;
            } else if (token.is("composite") || token.is("enumeration")
                    || token.is("attribute") || token.is("fundamental")) {
                String name = nameAt(tokens, i + 1);
                if (name != null && currentArea != null) {
                    String key = currentService == null ? currentArea
                            : currentArea + "." + currentService;
                    Set<String> names = declared.get(key);
                    if (names == null) {
                        names = new HashSet<String>();
                        declared.put(key, names);
                    }
                    names.add(name);
                }
            }
        }
    }

    private static String nameAt(List<Token> tokens, int index) {
        if (index >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(index);
        return token.getKind() == Token.Kind.WORD || token.getKind() == Token.Kind.STRING
                ? token.getText() : null;
    }

    private SchemaVersion schemaVersion(Cursor c) throws ImportException {
        String written = c.expect(Token.Kind.WORD, "a schema version such as v003");
        for (SchemaVersion version : SchemaVersion.values()) {
            if (version.name().equalsIgnoreCase(written)) {
                return version;
            }
        }
        throw c.error("unknown schema version '" + written + "'");
    }

    // ----------------------------------------------------------------- area

    private Area area(Cursor c) throws ImportException {
        String comment = doc(c);
        Area read = new Area();
        read.setLocation(c.location());
        c.expect("area");
        read.setComment(comment);
        read.setName(identifier(c));
        c.expect("[");
        read.setNumber(number(c));
        c.expect(".");
        read.setVersion(number(c));
        c.expect("]");

        this.area = read;
        this.service = null;

        while (!c.atEnd() && !startsArea(c)) {
            String memberComment = doc(c);
            if (c.peek().is("service") || c.peek().is("extended")) {
                read.addService(service(c, memberComment));
            } else if (c.peek().is("doc")) {
                read.getDocumentation().getSections().add(docSection(c, memberComment));
            } else if (c.peek().is("diagram")) {
                read.getDocumentation().getDiagrams().add(diagram(c));
            } else if (c.peek().is("error")) {
                read.getErrors().add(errorDefinition(c, memberComment));
            } else {
                read.getDataTypes().add(dataType(c, memberComment));
            }
        }
        this.area = null;
        return read;
    }

    /**
     * @return true if what follows begins another area, which is where this one ends.
     */
    private boolean startsArea(Cursor c) {
        if (c.peek().is("area")) {
            return true;
        }
        // An area may be preceded by its documentation, which has already been taken.
        return c.peek().getKind() == Token.Kind.DOC && c.peek(1).is("area");
    }

    // -------------------------------------------------------------- service

    private Service service(Cursor c, String comment) throws ImportException {
        Service read = new Service();
        read.setLocation(c.location());
        if (c.accept("extended")) {
            read.setExtended(true);
        }
        c.expect("service");
        read.setComment(comment);
        read.setName(identifier(c));
        c.expect("[");
        read.setNumber(number(c));
        c.expect("]");
        c.expect("{");

        this.service = read;
        while (!c.accept("}")) {
            String memberComment = doc(c);
            if (c.peek().is("capability")) {
                read.addCapabilitySet(capabilitySet(c, memberComment));
            } else if (c.peek().is("doc")) {
                read.getDocumentation().getSections().add(docSection(c, memberComment));
            } else if (c.peek().is("diagram")) {
                read.getDocumentation().getDiagrams().add(diagram(c));
            } else if (c.peek().is("error")) {
                read.getErrors().add(errorDefinition(c, memberComment));
            } else if (c.peek().is("com")) {
                read.setExtended(true);
                read.setCom(com(c));
            } else {
                read.getDataTypes().add(dataType(c, memberComment));
            }
        }
        this.service = null;
        return read;
    }

    private CapabilitySet capabilitySet(Cursor c, String comment) throws ImportException {
        CapabilitySet set = new CapabilitySet();
        set.setLocation(c.location());
        c.expect("capability");
        set.setComment(comment);
        c.expect("[");
        set.setNumber(number(c));
        c.expect("]");
        c.expect("{");
        while (!c.accept("}")) {
            set.addOperation(operation(c, doc(c)));
        }
        return set;
    }

    // ------------------------------------------------------------ operation

    private Operation operation(Cursor c, String comment) throws ImportException {
        Operation read = new Operation();
        read.setLocation(c.location());
        // Documentation of the operation's own sections may sit between the comment and the
        // signature, so it is taken before anything else is decided.
        while (c.peek().is("doc")) {
            read.getDocumentation().getSections().add(docSection(c, null));
        }

        if (c.accept("replayable")) {
            read.setSupportInReplay(true);
        }
        InteractionPattern pattern = pattern(c);
        read.setPattern(pattern);
        read.setName(identifier(c));
        c.expect("[");
        read.setNumber(number(c));
        c.expect("]");

        List<InteractionStage> stages = stagesOf(pattern);
        if (c.peek().is("(")) {
            read.getMessages().put(stages.get(0), messageBody(c));
        }

        for (int i = 1; i < stages.size(); i++) {
            c.expect("->");
            MessageBody body = messageBody(c);
            // The repeating update of a progress is marked where it is declared.
            c.accept("*");
            read.getMessages().put(stages.get(i), body);
        }

        if (c.accept("throws")) {
            do {
                read.getErrors().add(errorReference(c));
            } while (c.accept(","));
        }

        applyBulkDocumentation(read, comment);
        return read;
    }

    private InteractionPattern pattern(Cursor c) throws ImportException {
        String written = c.expect(Token.Kind.WORD, "an interaction pattern");
        for (InteractionPattern pattern : InteractionPattern.values()) {
            if (pattern.name().equalsIgnoreCase(written)) {
                return pattern;
            }
        }
        throw c.error("unknown interaction pattern '" + written + "'");
    }

    private MessageBody messageBody(Cursor c) throws ImportException {
        MessageBody body = new MessageBody();
        c.expect("(");
        if (c.accept(")")) {
            return body;
        }
        do {
            String comment = doc(c);
            Field field = field(c);
            field.setComment(comment);
            body.getFields().add(field);
        } while (c.accept(","));
        c.expect(")");
        return body;
    }

    private ErrorReference errorReference(Cursor c) throws ImportException {
        ErrorReference reference = new ErrorReference();
        reference.setLocation(c.location());
        reference.setComment(doc(c));
        reference.setError(errorReference2(c));
        if (c.accept(":")) {
            String extraComment = doc(c);
            Field extra = new Field();
            extra.setCanBeNull(false);
            extra.setComment(extraComment);
            extra.setType(typeWithWrappers(c));
            reference.setExtraInformation(extra);
        }
        return reference;
    }

    /**
     * Reads the name of an error.
     * <p>
     * Not a type reference: there is no implicit import for errors, so a name that says no
     * area means an error of the area being read. The MAL's errors are named in full
     * wherever they are used, exactly as the specifications write them.
     */
    private TypeRef errorReference2(Cursor c) throws ImportException {
        String first = identifier(c);
        String areaName = area == null ? null : area.getName();
        String serviceName = null;
        String name = first;

        if (c.accept("::")) {
            areaName = first;
            name = identifier(c);
        }
        if (c.accept(".")) {
            serviceName = name;
            name = identifier(c);
        }
        return new TypeRef(areaName, 0, serviceName, name, false, false);
    }

    /**
     * Hands the tagged lines of a bulk documentation block to the things they describe.
     * <p>
     * Everything before the first tag is the operation's own comment; a line that does not
     * begin with a tag continues the one above it, which is what lets a field's
     * documentation run to several lines.
     */
    private void applyBulkDocumentation(Operation operation, String comment) {
        if (comment == null || comment.isEmpty()) {
            return;
        }
        List<String> own = new ArrayList<String>();
        List<String[]> tagged = new ArrayList<String[]>();
        String[] current = null;

        for (String line : comment.split("\n", -1)) {
            if (line.startsWith("@")) {
                current = tag(line);
                tagged.add(current);
            } else if (current != null) {
                // A line under a tag continues it, and continues it as a new line: the text
                // it came from had a break there, and the round trip has to give it back.
                current[2] = current[2] + "\n" + line;
            } else {
                own.add(line);
            }
        }
        operation.setComment(trimTrailingBlanks(own));

        for (String[] parts : tagged) {
            applyTag(operation, parts[0], parts[1], parts[2]);
        }
    }

    /**
     * Takes a tag line apart into what it is, what it is about, and what it says.
     * <p>
     * A tag naming a message is written {@code @submit: what it says}; one naming something
     * inside a message is {@code @submitparam name: what it says}. So the tag ends at the
     * first colon or space, and where a space came first the subject runs to the colon
     * after it - which may itself be preceded by a doubled colon, as in
     * {@code @error MAL::UNKNOWN: ...}.
     *
     * @param line The line, beginning with its tag.
     * @return the tag, the subject, and the text.
     */
    private static String[] tag(String line) {
        int end = 1;
        while (end < line.length() && line.charAt(end) != ':' && line.charAt(end) != ' ') {
            end++;
        }
        String name = line.substring(1, end);
        String rest = end < line.length() ? line.substring(end) : "";

        if (rest.startsWith(":")) {
            return new String[]{name, "", after(rest.substring(1))};
        }
        rest = rest.startsWith(" ") ? rest.substring(1) : rest;
        int colon = separator(rest);
        if (colon < 0) {
            return new String[]{name, subject(rest), ""};
        }
        return new String[]{name, subject(rest.substring(0, colon)),
            after(rest.substring(colon + 1))};
    }

    /**
     * A tag names what it is about the way the rest of the file does, so a name that had to
     * be quoted is quoted here too - the MAL calls one of its errors "Read Only". The quotes
     * are the writing, not the name, so they come off before anything is matched.
     *
     * @param text The subject as written.
     * @return the subject as named.
     */
    private static String subject(String text) {
        return text.trim().replace("\"", "");
    }

    /**
     * @param text What follows the colon of a tag.
     * @return the text with the one space the tag is written with removed, and nothing else:
     * some comments in the specifications end in spaces, and a round trip that tidied them
     * up would not be a round trip.
     */
    private static String after(String text) {
        return text.startsWith(" ") ? text.substring(1) : text;
    }

    /**
     * Gives one tagged line to whatever it describes: a message, a field of one, an error,
     * or an error's extra information.
     */
    private void applyTag(Operation operation, String tag, String subject, String text) {
        if ("error".equals(tag) || "errorinfo".equals(tag)) {
            for (ErrorReference reference : operation.getErrors()) {
                if (!subject.endsWith(reference.getError().getName())) {
                    continue;
                }
                if ("error".equals(tag)) {
                    reference.setComment(text);
                } else if (reference.getExtraInformation() != null) {
                    reference.getExtraInformation().setComment(text);
                }
            }
            return;
        }

        boolean namesAField = tag.endsWith("param");
        String stageName = namesAField
                ? tag.substring(0, tag.length() - "param".length()) : tag;

        for (InteractionStage stage : stagesOf(operation.getPattern())) {
            if (!tagOf(stage).equals(stageName)) {
                continue;
            }
            MessageBody body = operation.getMessage(stage);
            if (body == null) {
                continue;
            }
            if (!namesAField) {
                body.setComment(text);
                continue;
            }
            for (Field field : body.getFields()) {
                if (subject.equals(field.getName())) {
                    field.setComment(text);
                }
            }
        }
    }

    /**
     * Finds the colon that separates what a tag is about from what it says. The subject may
     * itself contain a doubled colon - {@code @error MAL::UNKNOWN: ...} - so the first
     * single colon is the one that matters.
     *
     * @param text The part of a tag line after the tag itself.
     * @return where the separator is, or -1 if there is none.
     */
    private static int separator(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ':') {
                continue;
            }
            if (i + 1 < text.length() && text.charAt(i + 1) == ':') {
                i++;
                continue;
            }
            return i;
        }
        return -1;
    }

    /**
     * Gives one tagged line to whatever it describes: a field of a stage, an error, or an
     * error's extra information.
     */
    private void applyTag(Operation operation, String tag, String rest) {
        int colon = separator(rest);
        if (colon < 0) {
            return;
        }
        String subject = rest.substring(0, colon).trim();
        // Only the one space the tag is written with is removed. What follows is the text
        // as it was, trailing spaces included: some comments in the specifications end in
        // them, and a round trip that tidied them up would not be a round trip.
        String text = rest.substring(colon + 1);
        if (text.startsWith(" ")) {
            text = text.substring(1);
        }

        if ("error".equals(tag) || "errorinfo".equals(tag)) {
            for (ErrorReference reference : operation.getErrors()) {
                if (!subject.endsWith(reference.getError().getName())) {
                    continue;
                }
                if ("error".equals(tag)) {
                    reference.setComment(text);
                } else if (reference.getExtraInformation() != null) {
                    reference.getExtraInformation().setComment(text);
                }
            }
            return;
        }
        if (!tag.endsWith("param")) {
            for (InteractionStage stage : stagesOf(operation.getPattern())) {
                MessageBody body = operation.getMessage(stage);
                if (tagOf(stage).equals(tag) && body != null) {
                    // The tag names the stage and nothing within it, so what it says is
                    // what the message itself says.
                    body.setComment(subject.isEmpty() ? text : subject + ":" + text);
                }
            }
            return;
        }
        String stageName = tag.substring(0, tag.length() - "param".length());
        for (InteractionStage stage : stagesOf(operation.getPattern())) {
            if (!tagOf(stage).equals(stageName)) {
                continue;
            }
            MessageBody body = operation.getMessage(stage);
            if (body == null) {
                continue;
            }
            for (Field field : body.getFields()) {
                if (subject.equals(field.getName())) {
                    field.setComment(text);
                }
            }
        }
    }

    // ------------------------------------------------------------ datatypes

    private TypeDefinition dataType(Cursor c, String comment) throws ImportException {
        if (c.peek().is("fundamental")) {
            return fundamental(c, comment);
        }
        if (c.peek().is("attribute")) {
            return attribute(c, comment);
        }
        if (c.peek().is("enumeration")) {
            return enumeration(c, comment);
        }
        if (c.peek().is("composite") || c.peek().is("abstract")) {
            return composite(c, comment);
        }
        throw c.error("expected a declaration");
    }

    private FundamentalType fundamental(Cursor c, String comment) throws ImportException {
        FundamentalType type = new FundamentalType();
        type.setLocation(c.location());
        c.expect("fundamental");
        type.setComment(comment);
        type.setName(identifier(c));
        if (c.accept("extends")) {
            type.setSuperType(typeReference(c));
        }
        return type;
    }

    private AttributeType attribute(Cursor c, String comment) throws ImportException {
        AttributeType type = new AttributeType();
        type.setLocation(c.location());
        c.expect("attribute");
        type.setComment(comment);
        type.setName(identifier(c));
        c.expect("[");
        type.setShortFormPart(number(c));
        c.expect("]");
        return type;
    }

    private EnumerationType enumeration(Cursor c, String comment) throws ImportException {
        EnumerationType type = new EnumerationType();
        type.setLocation(c.location());
        c.expect("enumeration");
        type.setComment(comment);
        type.setName(identifier(c));
        c.expect("[");
        type.setShortFormPart(number(c));
        c.expect("]");
        c.expect("{");
        while (!c.accept("}")) {
            String itemComment = doc(c);
            EnumerationItem item = new EnumerationItem();
            item.setLocation(c.location());
            item.setComment(itemComment);
            item.setValue(identifier(c));
            c.expect("[");
            item.setNumericValue(number(c));
            c.expect("]");
            type.getItems().add(item);
        }
        return type;
    }

    private CompositeType composite(Cursor c, String comment) throws ImportException {
        CompositeType type = new CompositeType();
        type.setLocation(c.location());
        // Abstractness is not stored: a composite is abstract exactly when it has no short
        // form, so the keyword says again what the missing [n] already says. It is accepted
        // because it reads better, and checked against the number below.
        boolean saidAbstract = c.accept("abstract");
        c.expect("composite");
        type.setComment(comment);
        type.setName(identifier(c));
        if (c.accept("[")) {
            type.setShortFormPart(number(c));
            c.expect("]");
        }
        if (saidAbstract != type.isAbstract()) {
            throw c.error(saidAbstract
                    ? "'" + type.getName() + "' is declared abstract but has a short form"
                    : "'" + type.getName() + "' has no short form, so it is abstract and"
                    + " has to say so");
        }
        if (c.accept("extends")) {
            type.setSuperType(typeReference(c));
        }
        c.expect("{");
        while (!c.accept("}")) {
            String fieldComment = doc(c);
            Field field = field(c);
            field.setComment(fieldComment);
            type.getFields().add(field);
        }
        return type;
    }

    private ErrorDefinition errorDefinition(Cursor c, String comment) throws ImportException {
        ErrorDefinition error = new ErrorDefinition();
        error.setLocation(c.location());
        c.expect("error");
        error.setComment(comment);
        error.setName(identifier(c));
        c.expect("[");
        error.setNumber(number(c));
        c.expect("]");
        return error;
    }

    // ------------------------------------------------------------------ com

    private COMFeatures com(Cursor c) throws ImportException {
        COMFeatures com = new COMFeatures();
        c.expect("com");
        c.expect("{");
        while (!c.accept("}")) {
            String comment = doc(c);
            if (c.peek().is("diagram")) {
                com.getDocumentation().getDiagrams().add(diagram(c));
            } else if (c.peek().is("doc")) {
                com.getDocumentation().getSections().add(docSection(c, comment));
            } else if (c.accept("objects")) {
                com.setDeclaresObjects(true);
                com.setObjectsComment(comment);
                c.expect("{");
                while (!c.accept("}")) {
                    com.getObjects().add(comObject(c, "object"));
                }
            } else if (c.accept("events")) {
                com.setDeclaresEvents(true);
                com.setEventsComment(comment);
                c.expect("{");
                while (!c.accept("}")) {
                    com.getEvents().add(comObject(c, "event"));
                }
            } else if (c.accept("archiveUsage")) {
                com.setDeclaresArchiveUsage(true);
                com.setArchiveUsage(comment);
            } else if (c.accept("activityUsage")) {
                com.setDeclaresActivityUsage(true);
                com.setActivityUsage(comment);
            } else {
                throw c.error("expected a COM declaration");
            }
        }
        return com;
    }

    private COMObject comObject(Cursor c, String keyword) throws ImportException {
        String comment = doc(c);
        COMObject object = new COMObject();
        object.setLocation(c.location());
        c.expect(keyword);
        object.setComment(comment);
        object.setName(identifier(c));
        c.expect("[");
        object.setNumber(number(c));
        c.expect("]");
        if (c.accept("(")) {
            object.setBodyType(typeWithWrappers(c));
            c.expect(")");
        }
        if (!c.accept("{")) {
            return object;
        }
        while (!c.accept("}")) {
            String linkComment = doc(c);
            if (c.accept("related")) {
                object.setRelated(objectLink(c, linkComment));
            } else if (c.accept("source")) {
                object.setSource(objectLink(c, linkComment));
            } else {
                throw c.error("expected 'related' or 'source'");
            }
        }
        return object;
    }

    /**
     * A link to another COM object, named by number so that it round-trips whether or not
     * the area it points into is loaded.
     */
    private ObjectLink objectLink(Cursor c, String comment) throws ImportException {
        ObjectLink link = new ObjectLink();
        link.setComment(comment);
        if (c.accept("-")) {
            return link;
        }
        String targetArea = area.getName();
        String targetService = null;
        if (c.peek(1).is("::")) {
            targetArea = identifier(c);
            c.expect("::");
        }
        if (!c.peek().is("#")) {
            targetService = identifier(c);
        }
        c.expect("#");
        // No version: the XML does not carry one on an object reference either, and the
        // linker is what decides which generation a reference means.
        link.setTarget(new ObjectReference(targetArea, 0, targetService, number(c)));
        return link;
    }

    // ------------------------------------------------------------ the small

    private DocSection docSection(Cursor c, String comment) throws ImportException {
        DocSection section = new DocSection();
        section.setLocation(c.location());
        c.expect("doc");
        section.setName(c.expect(Token.Kind.STRING, "the name of the section"));
        c.expect("[");
        section.setOrder(number(c));
        c.expect("]");
        if (c.peek().getKind() == Token.Kind.TEXT) {
            section.setContent(c.take().getText());
        }
        return section;
    }

    private Diagram diagram(Cursor c) throws ImportException {
        Diagram diagram = new Diagram();
        diagram.setLocation(c.location());
        c.expect("diagram");
        diagram.setName(identifier(c));
        // The sidecar is named after the diagram, so the file name says nothing the name
        // does not; it is read and discarded so that the two cannot disagree.
        c.expect(Token.Kind.STRING, "the name of the sidecar file");
        return diagram;
    }

    private Field field(Cursor c) throws ImportException {
        Field field = new Field();
        field.setLocation(c.location());
        field.setType(typeWithWrappers(c));
        field.setCanBeNull(c.accept("?"));
        field.setName(identifier(c));
        return field;
    }

    /**
     * Reads a type with whatever wrappers are around it: {@code List<ObjectRef<Product>>}.
     */
    private TypeRef typeWithWrappers(Cursor c) throws ImportException {
        boolean list = false;
        boolean objectRef = false;
        if (c.peek().is("List") && c.peek(1).is("<")) {
            c.take();
            c.take();
            list = true;
        }
        if (c.peek().is("ObjectRef") && c.peek(1).is("<")) {
            c.take();
            c.take();
            objectRef = true;
        }
        TypeRef named = typeReference(c);
        if (objectRef) {
            c.expect(">");
        }
        if (list) {
            c.expect(">");
        }
        return new TypeRef(named.getArea(), named.getAreaVersion(), named.getService(),
                named.getName(), list, objectRef);
    }

    /**
     * Reads a name, with whatever of the area and service it says. What it leaves out is
     * taken from where it is written, and a name that says nothing at all and is not
     * declared here means the MAL's - the language imports those implicitly.
     */
    private TypeRef typeReference(Cursor c) throws ImportException {
        String first = identifier(c);
        String areaName = null;
        String serviceName = null;
        String name = first;

        if (c.accept("::")) {
            areaName = first;
            name = identifier(c);
            if (c.accept(".")) {
                serviceName = name;
                name = identifier(c);
            }
        } else if (c.accept(".")) {
            serviceName = first;
            name = identifier(c);
        }

        if (areaName == null && serviceName != null) {
            // A name that says a service says this area: another area's service is always
            // written with its area in front of it.
            areaName = area.getName();
        } else if (areaName == null) {
            areaName = declaredHere(name) ? area.getName() : "MAL";
            if (serviceName == null && declaredHere(name) && service != null
                    && declaredByService(name)) {
                serviceName = service.getName();
            }
        }
        return new TypeRef(areaName, 0, serviceName, name, false, false);
    }

    /**
     * @return true if the area or the service being read declares a type of this name, in
     * which case an unqualified name means that one rather than the MAL's.
     */
    private boolean declaredHere(String name) {
        return declaredByArea(name) || declaredByService(name);
    }

    private boolean declaredByArea(String name) {
        return area != null && declaredIn(area.getName(), name);
    }

    private boolean declaredByService(String name) {
        return area != null && service != null
                && declaredIn(area.getName() + "." + service.getName(), name);
    }

    private boolean declaredIn(String scope, String name) {
        Set<String> names = declared.get(scope);
        return names != null && names.contains(name);
    }

    /**
     * @return the documentation attached to whatever comes next, or null if there is none.
     */
    private String doc(Cursor c) {
        return c.peek().getKind() == Token.Kind.DOC ? c.take().getText() : null;
    }

    /**
     * @return a name, which may be quoted where it would otherwise read as a keyword.
     */
    private String identifier(Cursor c) throws ImportException {
        if (c.peek().getKind() == Token.Kind.STRING) {
            return c.take().getText();
        }
        return c.expect(Token.Kind.WORD, "a name");
    }

    private int number(Cursor c) throws ImportException {
        return Integer.parseInt(c.expect(Token.Kind.NUMBER, "a number"));
    }

    private static String trimTrailingBlanks(List<String> lines) {
        while (!lines.isEmpty() && lines.get(lines.size() - 1).trim().isEmpty()) {
            lines.remove(lines.size() - 1);
        }
        if (lines.isEmpty()) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            buf.append(i == 0 ? "" : "\n").append(lines.get(i));
        }
        return buf.toString();
    }

    /**
     * The stages an interaction exchanges, in the order they are written.
     */
    private static List<InteractionStage> stagesOf(InteractionPattern pattern) {
        List<InteractionStage> stages = new ArrayList<InteractionStage>();
        switch (pattern) {
            case SEND:
                stages.add(InteractionStage.SEND);
                break;
            case SUBMIT:
                stages.add(InteractionStage.SUBMIT);
                break;
            case REQUEST:
                stages.add(InteractionStage.REQUEST);
                stages.add(InteractionStage.RESPONSE);
                break;
            case INVOKE:
                stages.add(InteractionStage.INVOKE);
                stages.add(InteractionStage.ACK);
                stages.add(InteractionStage.RESPONSE);
                break;
            case PROGRESS:
                stages.add(InteractionStage.PROGRESS);
                stages.add(InteractionStage.ACK);
                stages.add(InteractionStage.UPDATE);
                stages.add(InteractionStage.RESPONSE);
                break;
            case PUBSUB:
                stages.add(InteractionStage.SUBSCRIPTION_KEYS);
                stages.add(InteractionStage.PUBLISH_NOTIFY);
                break;
            default:
                break;
        }
        return stages;
    }

    private static String tagOf(InteractionStage stage) {
        if (stage == InteractionStage.PUBLISH_NOTIFY) {
            return "publish";
        }
        if (stage == InteractionStage.SUBSCRIPTION_KEYS) {
            return "subscriptionkeys";
        }
        return stage.name().toLowerCase();
    }

    private static String readAll(Reader in) throws IOException {
        StringBuilder buf = new StringBuilder();
        char[] chunk = new char[8192];
        int read;
        while ((read = in.read(chunk)) != -1) {
            buf.append(chunk, 0, read);
        }
        return buf.toString();
    }
}
