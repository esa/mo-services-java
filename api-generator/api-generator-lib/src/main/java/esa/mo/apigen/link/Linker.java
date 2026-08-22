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
package esa.mo.apigen.link;

import esa.mo.apigen.model.*;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.com.ObjectReference;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.FundamentalType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.model.types.TypeRef;
import esa.mo.apigen.validation.Severity;
import esa.mo.apigen.validation.ValidationIssue;
import esa.mo.apigen.validation.ValidationResult;
import java.util.List;

/**
 * Fills in what the XML leaves out, between import and validation.
 * <p>
 * Two things are missing from the file and have to be recovered from the model as a
 * whole:
 * <ul>
 * <li>A type reference carries no version - {@code <mal:type area="MAL" name="Blob"/>}
 * does not say which MAL - so the version of the area actually loaded is written into the
 * reference. Doing it here, once, is what lets resolution need no lookup context and lets
 * the same name in two versions of an area compare unequal.</li>
 * <li>A composite may omit {@code <extends>}, which the schema defines as implying the
 * base Composite. Resolving that here means no later reader has to know the rule.</li>
 * </ul>
 * After linking the model does not change again, which is what lets generation run in
 * parallel over it.
 */
public final class Linker {

    private static final String MAL = "MAL";
    private static final String COMPOSITE = "Composite";

    /**
     * Links every reference in the model.
     *
     * @param model The model to link.
     * @return anything that could not be linked.
     */
    public ValidationResult link(MOModel model) {
        ValidationResult result = new ValidationResult();
        Area mal = model.findUniqueArea(MAL);
        if (mal == null) {
            result.add(new ValidationIssue(Severity.ERROR, "link.mal.missing",
                    model.findAreas(MAL).isEmpty()
                            ? "The MAL area is not loaded, so no type can be resolved"
                            : "More than one version of the MAL area is loaded, so type "
                            + "references to it are ambiguous", null));
        }
        int malVersion = mal == null ? 0 : mal.getVersion();

        for (Specification spec : model.getSpecifications()) {
            for (Area area : spec.getAreas()) {
                linkArea(model, area, malVersion, result);
            }
        }
        return result;
    }

    private void linkArea(MOModel model, Area area, int malVersion, ValidationResult result) {
        linkTypes(model, area, area.getDataTypes(), malVersion, result);
        for (ErrorDefinition error : area.getErrors()) {
            linkField(model, area, error.getExtraInformation(), result);
        }
        for (Service service : area.getServices()) {
            linkTypes(model, area, service.getDataTypes(), malVersion, result);
            for (ErrorDefinition error : service.getErrors()) {
                linkField(model, area, error.getExtraInformation(), result);
            }
            for (Operation op : service.getOperations()) {
                linkOperation(model, area, op, result);
            }
            if (service.getCom() != null) {
                linkCom(model, area, service.getCom(), result);
            }
        }
    }

    private void linkTypes(MOModel model, Area area, List<TypeDefinition> types,
            int malVersion, ValidationResult result) {
        for (TypeDefinition type : types) {
            type.setArea(area);
            if (type instanceof CompositeType) {
                CompositeType composite = (CompositeType) type;
                if (composite.getSuperType() == null) {
                    // The schema: "if this is not present then the base Composite is implied".
                    composite.setSuperType(new TypeRef(MAL, malVersion, null, COMPOSITE, false, false));
                } else {
                    composite.setSuperType(linkRef(model, area, composite.getSuperType(),
                            composite.getLocation(), result));
                }
                for (Field field : composite.getFields()) {
                    linkField(model, area, field, result);
                }
            } else if (type instanceof FundamentalType) {
                FundamentalType fundamental = (FundamentalType) type;
                if (fundamental.getSuperType() != null) {
                    fundamental.setSuperType(linkRef(model, area, fundamental.getSuperType(),
                            fundamental.getLocation(), result));
                }
            }
        }
    }

    private void linkOperation(MOModel model, Area area, Operation op, ValidationResult result) {
        for (MessageBody body : op.getMessages().values()) {
            for (Field field : body.getFields()) {
                linkField(model, area, field, result);
            }
        }
        for (ErrorReference ref : op.getErrors()) {
            if (ref.getError() != null) {
                ref.setError(linkRef(model, area, ref.getError(), ref.getLocation(), result));
            }
            linkField(model, area, ref.getExtraInformation(), result);
        }
    }

    private void linkCom(MOModel model, Area area, COMFeatures com, ValidationResult result) {
        linkComObjects(model, area, com.getObjects(), result);
        linkComObjects(model, area, com.getEvents(), result);
    }

    private void linkComObjects(MOModel model, Area area, List<COMObject> objects,
            ValidationResult result) {
        for (COMObject object : objects) {
            if (object.getBodyType() != null) {
                object.setBodyType(linkRef(model, area, object.getBodyType(),
                        object.getLocation(), result));
            }
            linkObjectLink(model, area, object, object.getRelated(), result);
            linkObjectLink(model, area, object, object.getSource(), result);
        }
    }

    private void linkObjectLink(MOModel model, Area area, COMObject object, ObjectLink link,
            ValidationResult result) {
        if (link == null || link.getTarget() == null) {
            return;
        }
        ObjectReference ref = link.getTarget();
        int version = versionOf(model, area, ref.getArea(), object.getLocation(), result);
        if (version > 0) {
            link.setTarget(ref.withAreaVersion(version));
        }
    }

    private void linkField(MOModel model, Area area, Field field, ValidationResult result) {
        if (field == null || field.getType() == null) {
            return;
        }
        field.setType(linkRef(model, area, field.getType(), field.getLocation(), result));
    }

    private TypeRef linkRef(MOModel model, Area area, TypeRef ref, SourceLocation location,
            ValidationResult result) {
        int version = versionOf(model, area, ref.getArea(), location, result);
        return version > 0 ? ref.withAreaVersion(version) : ref;
    }

    /**
     * Works out which version of an area a reference means.
     * <p>
     * A reference to the referring area's own name means that area. A reference to the
     * MAL means the MAL of the referring specification's own generation, which its
     * namespace declares (see {@link SchemaVersion#getMalVersion()}); pairing a
     * specification with the MAL of its generation resolves every reference it makes.
     * Any other area is taken to be whichever version of it is loaded, there being
     * nothing in a reference to say otherwise.
     */
    private int versionOf(MOModel model, Area context, String areaName,
            SourceLocation location, ValidationResult result) {
        if (areaName == null) {
            return 0;
        }
        if (context != null && areaName.equals(context.getName())) {
            return context.getVersion();
        }
        if (MAL.equals(areaName) && context != null && context.getSpecification() != null
                && context.getSpecification().getSchemaVersion() != null) {
            int wanted = context.getSpecification().getSchemaVersion().getMalVersion();
            if (model.findArea(MAL, wanted) != null) {
                return wanted;
            }
            // The MAL of this specification's generation is not loaded. Whatever is
            // loaded is used instead, so that the rest of the model still links, and the
            // validator says so once for the specification rather than once for every
            // name that does not match.
        }
        Area target = model.findUniqueArea(areaName);
        if (target != null) {
            return target.getVersion();
        }
        if (model.findAreas(areaName).isEmpty()) {
            result.add(new ValidationIssue(Severity.ERROR, "link.area.missing",
                    "Area '" + areaName + "' is referenced but not loaded", location));
        } else {
            result.add(new ValidationIssue(Severity.ERROR, "link.area.ambiguous",
                    "Area '" + areaName + "' is loaded in more than one version, so the "
                    + "reference cannot say which is meant", location));
        }
        return 0;
    }
}
