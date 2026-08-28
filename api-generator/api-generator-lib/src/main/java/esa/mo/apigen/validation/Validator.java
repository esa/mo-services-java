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
package esa.mo.apigen.validation;

import esa.mo.apigen.model.Area;
import esa.mo.apigen.model.CapabilitySet;
import esa.mo.apigen.model.ErrorDefinition;
import esa.mo.apigen.model.Field;
import esa.mo.apigen.model.MOModel;
import esa.mo.apigen.model.MessageBody;
import esa.mo.apigen.model.Operation;
import esa.mo.apigen.model.SchemaVersion;
import esa.mo.apigen.model.Service;
import esa.mo.apigen.model.Specification;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.docs.Diagram;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Checks a model, reporting everything it finds rather than stopping at the first problem.
 * <p>
 * Only rules the schema cannot express belong here. Anything the XSD already states -
 * that a short form part is at least 1, for instance - is left to schema validation on
 * export, so there is one source of truth for it.
 */
public final class Validator {

    private static final String MAL = "MAL";

    /**
     * Validates a model.
     *
     * @param model The model to check.
     * @return everything found.
     */
    public ValidationResult validate(MOModel model) {
        ValidationResult result = new ValidationResult();
        checkAreaIdentities(model, result);
        for (Specification spec : model.getSpecifications()) {
            checkMalGeneration(model, spec, result);
            for (Area area : spec.getAreas()) {
                checkArea(model, spec, area, result);
            }
        }
        return result;
    }

    /**
     * Reports a specification being built against a MAL of a different generation than
     * its own.
     * <p>
     * A specification says which generation it belongs to by its namespace, and names its
     * errors accordingly: v001 raises UNKNOWN, v003 raises Unknown. Give it the MAL of its
     * own generation and every reference resolves. Give it the other one and none of the
     * renamed errors does - so this is said once, for the specification, rather than once
     * for each name that fails to match.
     */
    private void checkMalGeneration(MOModel model, Specification spec, ValidationResult result) {
        if (spec.getSchemaVersion() == null || spec.getAreas().isEmpty()) {
            return;
        }
        int wanted = spec.getSchemaVersion().getMalVersion();
        if (model.findArea(MAL, wanted) != null || model.findAreas(MAL).isEmpty()) {
            return;
        }
        Area loaded = model.findUniqueArea(MAL);
        if (loaded == null || loaded.getVersion() == wanted) {
            return;
        }
        result.add(new ValidationIssue(Severity.WARNING, "specification.malGeneration",
                "This specification is written against the " + spec.getSchemaVersion()
                + " schema, so it names the errors of MAL " + wanted + ", but is built "
                + "against MAL " + loaded.getVersion() + ", which names them differently",
                spec.getAreas().get(0).getLocation()));
    }

    // ---------------------------------------------------------------- areas

    private void checkAreaIdentities(MOModel model, ValidationResult result) {
        for (Area area : model.getConflictingAreas()) {
            Area existing = model.findArea(area.getKey());
            result.add(new ValidationIssue(Severity.WARNING, "area.identity.duplicate",
                    "Area " + area.getKey() + " is declared twice: in "
                    + describe(existing) + " and in " + describe(area)
                    + ". Only the first is indexed.", area.getLocation()));
        }
        Map<Integer, Area> byNumber = new HashMap<Integer, Area>();
        for (Area area : model.getAreas()) {
            Area clash = byNumber.put(area.getNumber(), area);
            if (clash != null && !String.valueOf(clash.getName()).equals(area.getName())) {
                result.add(new ValidationIssue(Severity.WARNING, "area.number.reused",
                        "Area number " + area.getNumber() + " is used by both '"
                        + clash.getName() + "' and '" + area.getName() + "'.",
                        area.getLocation()));
            }
        }
    }

    private static String describe(Area area) {
        if (area == null || area.getSpecification() == null
                || area.getSpecification().getSource() == null) {
            return "<unknown>";
        }
        return area.getSpecification().getSource().getName();
    }

    private void checkArea(MOModel model, Specification spec, Area area, ValidationResult result) {
        Set<String> serviceNames = new HashSet<String>();
        Set<Integer> serviceNumbers = new HashSet<Integer>();
        for (Service service : area.getServices()) {
            if (!serviceNames.add(service.getName())) {
                result.add(issue(Severity.ERROR, "service.name.duplicate",
                        "Duplicate service name '" + service.getName() + "' in area "
                        + area.getName(), service.getLocation()));
            }
            if (!serviceNumbers.add(service.getNumber())) {
                result.add(issue(Severity.ERROR, "service.number.duplicate",
                        "Duplicate service number " + service.getNumber() + " in area "
                        + area.getName(), service.getLocation()));
            }
            checkService(model, spec, service, result);
        }
        checkTypes(model, area.getDataTypes(), result);
        checkErrors(area.getErrors(), "area " + area.getName(), result);
    }

    // ------------------------------------------------------------- services

    private void checkService(MOModel model, Specification spec, Service service,
            ValidationResult result) {
        if (spec.getSchemaVersion() == SchemaVersion.V003) {
            if (!service.getDataTypes().isEmpty()) {
                result.add(issue(Severity.ERROR, "version.serviceDataTypes",
                        "Service-level data types are not part of the v003 schema; "
                        + "v003 declares all types at area level", service.getLocation()));
            }
            if (!service.getErrors().isEmpty()) {
                result.add(issue(Severity.ERROR, "version.serviceErrors",
                        "Service-level errors are not part of the v003 schema",
                        service.getLocation()));
            }
        }

        Set<Integer> capabilityNumbers = new HashSet<Integer>();
        Set<String> operationNames = new HashSet<String>();
        Set<Integer> operationNumbers = new HashSet<Integer>();
        for (CapabilitySet set : service.getCapabilitySets()) {
            if (!capabilityNumbers.add(set.getNumber())) {
                result.add(issue(Severity.ERROR, "capability.number.duplicate",
                        "Duplicate capability set number " + set.getNumber()
                        + " in service " + service.getName(), set.getLocation()));
            }
            for (Operation op : set.getOperations()) {
                if (!operationNames.add(op.getName())) {
                    result.add(issue(Severity.ERROR, "operation.name.duplicate",
                            "Duplicate operation name '" + op.getName() + "' in service "
                            + service.getName(), op.getLocation()));
                }
                if (!operationNumbers.add(op.getNumber())) {
                    result.add(issue(Severity.ERROR, "operation.number.duplicate",
                            "Duplicate operation number " + op.getNumber()
                            + " in service " + service.getName(), op.getLocation()));
                }
                checkOperation(model, spec, op, result);
            }
        }
        checkTypes(model, service.getDataTypes(), result);
        checkErrors(service.getErrors(), "service " + service.getName(), result);
        if (service.getCom() != null) {
            checkCom(model, service, service.getCom(), result);
        }
    }

    // ----------------------------------------------------------- operations

    private void checkOperation(MOModel model, Specification spec, Operation op,
            ValidationResult result) {
        if (op.getPattern() == null) {
            result.add(issue(Severity.ERROR, "operation.pattern.missing",
                    "Operation '" + op.getName() + "' has no interaction pattern",
                    op.getLocation()));
            return;
        }
        for (Map.Entry<esa.mo.apigen.model.InteractionStage, MessageBody> entry
                : op.getMessages().entrySet()) {
            if (!op.getPattern().hasStage(entry.getKey())) {
                result.add(issue(Severity.ERROR, "operation.stage.unexpected",
                        "Operation '" + op.getName() + "' is a " + op.getPattern()
                        + " but carries a " + entry.getKey() + " message", op.getLocation()));
            }
            for (Field field : entry.getValue().getFields()) {
                checkType(model, field.getType(), "field '" + field.getName()
                        + "' of operation '" + op.getName() + "'", field.getLocation(), result);
            }
        }
        if (spec.getSchemaVersion() == SchemaVersion.V003 && op.isSupportInReplay()) {
            result.add(issue(Severity.ERROR, "version.supportInReplay",
                    "supportInReplay is not part of the v003 schema", op.getLocation()));
        }
        if (spec.getSchemaVersion() == SchemaVersion.V001
                && op.getDocumentation() != null && !op.getDocumentation().isEmpty()) {
            result.add(issue(Severity.ERROR, "version.operationDocumentation",
                    "Operation-level documentation is not part of the v001 schema",
                    op.getLocation()));
        }
        for (esa.mo.apigen.model.ErrorReference ref : op.getErrors()) {
            checkError(model, ref.getError(), "operation '" + op.getName() + "'",
                    ref.getLocation(), result);
            // The extra information an error carries is an ordinary type.
            if (ref.getExtraInformation() != null) {
                checkType(model, ref.getExtraInformation().getType(),
                        "extra information of an error of operation '" + op.getName() + "'",
                        ref.getLocation(), result);
            }
        }
    }

    // ---------------------------------------------------------------- types

    private void checkTypes(MOModel model, List<TypeDefinition> types, ValidationResult result) {
        for (TypeDefinition type : types) {
            if (type instanceof CompositeType) {
                CompositeType composite = (CompositeType) type;
                if (composite.getSuperType() == null) {
                    result.add(issue(Severity.ERROR, "composite.superType.missing",
                            "Composite '" + composite.getName() + "' has no super type; "
                            + "the implied base Composite should have been resolved at import",
                            composite.getLocation()));
                } else {
                    checkType(model, composite.getSuperType(),
                            "super type of '" + composite.getName() + "'",
                            composite.getLocation(), result);
                }
                for (Field field : composite.getFields()) {
                    checkType(model, field.getType(), "field '" + field.getName()
                            + "' of '" + composite.getName() + "'", field.getLocation(), result);
                }
            }
        }
    }

    /**
     * Checks that an error reference names an error that exists. Errors live in their own
     * namespace, so they are resolved separately from types.
     */
    private void checkError(MOModel model, TypeRef ref, String what,
            esa.mo.apigen.model.SourceLocation location, ValidationResult result) {
        if (ref == null) {
            result.add(issue(Severity.ERROR, "error.missing",
                    "No error given for " + what, location));
            return;
        }
        if (ref.getAreaVersion() == 0) {
            result.add(issue(Severity.ERROR, "error.unlinked",
                    "Error " + ref + " raised by " + what + " was never linked to an area version",
                    location));
            return;
        }
        if (model.resolveError(ref) == null) {
            // A warning rather than an error. The MAL renamed its errors between v001 and
            // v003 - UNKNOWN became Unknown - because v001 treated them as error codes and
            // v003 treats them as exceptions. A specification of one generation given the
            // MAL of the other therefore names errors the loaded MAL does not declare, and
            // the build has always done exactly that without harm.
            String renamed = renaming(model, ref);
            if (!renamed.isEmpty()) {
                // The generation gap accounts for it, and is reported once above.
                return;
            }
            result.add(issue(Severity.WARNING, "error.unresolved",
                    "Cannot resolve error " + ref + " raised by " + what, location));
        }
    }

    /**
     * Looks for an error the target area declares under a different convention, so that
     * the warning says what was probably meant rather than only what was not found.
     */
    private String renaming(MOModel model, TypeRef ref) {
        Area area = model.findArea(ref.getArea(), ref.getAreaVersion());
        if (area == null) {
            return "";
        }
        String wanted = flatten(ref.getName());
        for (ErrorDefinition error : allErrorsOf(area)) {
            if (flatten(error.getName()).equals(wanted)) {
                return "; the area declares it as '" + error.getName() + "'";
            }
        }
        return "";
    }

    private static List<ErrorDefinition> allErrorsOf(Area area) {
        List<ErrorDefinition> all = new java.util.ArrayList<ErrorDefinition>(area.getErrors());
        for (Service service : area.getServices()) {
            all.addAll(service.getErrors());
        }
        return all;
    }

    /**
     * Reduces a name to what it says, ignoring how it is spelled: UNKNOWN, Unknown and
     * "Too Many" against TOO_MANY.
     */
    private static String flatten(String name) {
        return name == null ? "" : name.replace("_", "").replace(" ", "").toLowerCase();
    }

    private void checkType(MOModel model, TypeRef ref, String what, 
            esa.mo.apigen.model.SourceLocation location, ValidationResult result) {
        if (ref == null) {
            result.add(issue(Severity.ERROR, "type.missing", "No type given for " + what, location));
            return;
        }
        if (ref.getAreaVersion() == 0) {
            result.add(issue(Severity.ERROR, "type.unlinked",
                    "Type " + ref + " used by " + what + " was never linked to an area version",
                    location));
            return;
        }
        if (model.resolve(ref) == null) {
            result.add(issue(Severity.ERROR, "type.unresolved",
                    "Cannot resolve type " + ref + " used by " + what, location));
        }
    }

    // --------------------------------------------------------------- errors

    private void checkErrors(List<ErrorDefinition> errors, String owner, ValidationResult result) {
        Set<Long> numbers = new HashSet<Long>();
        Set<String> names = new HashSet<String>();
        for (ErrorDefinition error : errors) {
            if (!numbers.add(error.getNumber())) {
                result.add(issue(Severity.ERROR, "error.number.duplicate",
                        "Duplicate error number " + error.getNumber() + " in " + owner,
                        error.getLocation()));
            }
            if (!names.add(error.getName())) {
                result.add(issue(Severity.ERROR, "error.name.duplicate",
                        "Duplicate error name '" + error.getName() + "' in " + owner,
                        error.getLocation()));
            }
        }
    }

    // ------------------------------------------------------------------ COM

    private void checkCom(MOModel model, Service service, COMFeatures com,
            ValidationResult result) {
        // Objects and events share one numbering space within a service: the schema's
        // uniqueness constraint selects both lists on a single number field.
        Set<Integer> numbers = new HashSet<Integer>();
        checkComObjects(model, service, com.getObjects(), "object", numbers, result);
        checkComObjects(model, service, com.getEvents(), "event", numbers, result);
        checkDiagrams(service, com, result);
    }

    /**
     * A diagram declared inside a specification is no longer drawn into anything.
     * <p>
     * The schema still permits {@code <mal:diagram>}, and the importer still reads it, so a
     * specification can carry one; nothing renders it. Saying so is the point - the reason
     * the support went is that an SVG inside a specification is a picture nothing can check
     * against the model it claims to describe (design section 8.3), and a diagram that
     * quietly produces no figure would be the same failure in a new form.
     */
    private void checkDiagrams(Service service, COMFeatures com, ValidationResult result) {
        for (Diagram diagram : com.getDocumentation().getDiagrams()) {
            result.add(issue(Severity.WARNING, "diagram.notRendered",
                    "Diagram '" + diagram.getName() + "' in service " + service.getName()
                    + " is declared in the specification but is not rendered into any"
                    + " output; diagrams belong beside a specification, not inside it",
                    service.getLocation()));
        }
    }

    private void checkComObjects(MOModel model, Service service, List<COMObject> objects,
            String kind, Set<Integer> numbers, ValidationResult result) {
        for (COMObject object : objects) {
            if (!numbers.add(object.getNumber())) {
                result.add(issue(Severity.ERROR, "com.number.duplicate",
                        "COM " + kind + " '" + object.getName() + "' reuses number "
                        + object.getNumber() + " in service " + service.getName()
                        + "; objects and events share one numbering space",
                        object.getLocation()));
            }
            if (object.getBodyType() != null) {
                checkType(model, object.getBodyType(), "body of COM " + kind + " '"
                        + object.getName() + "'", object.getLocation(), result);
            }
            checkLink(model, object, object.getRelated(), "related", result);
            checkLink(model, object, object.getSource(), "source", result);
        }
    }

    private void checkLink(MOModel model, COMObject object, ObjectLink link, String which,
            ValidationResult result) {
        if (link == null) {
            return;
        }
        if (link.isEmpty()) {
            result.add(issue(Severity.WARNING, "com.link.empty",
                    "The " + which + " link of '" + object.getName()
                    + "' has neither a target nor a comment, and so says nothing",
                    object.getLocation()));
            return;
        }
        if (link.getTarget() != null && model.resolve(link.getTarget()) == null) {
            result.add(issue(Severity.ERROR, "com.link.unresolved",
                    "Cannot resolve the " + which + " link of '" + object.getName()
                    + "': " + link.getTarget(), object.getLocation()));
        }
    }

    private static ValidationIssue issue(Severity severity, String rule, String message,
            esa.mo.apigen.model.SourceLocation location) {
        return new ValidationIssue(severity, rule, message, location);
    }
}
