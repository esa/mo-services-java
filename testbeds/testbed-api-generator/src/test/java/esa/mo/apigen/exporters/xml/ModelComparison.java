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

import esa.mo.apigen.model.*;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.docs.Diagram;
import esa.mo.apigen.model.docs.DocSection;
import esa.mo.apigen.model.docs.Documentation;
import esa.mo.apigen.model.types.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Compares two models field by field, describing every difference.
 * <p>
 * The round-trip test needs to know not merely that two models differ but where, or a
 * failure says nothing useful about which importer or exporter is at fault.
 */
public final class ModelComparison {

    private final List<String> differences = new ArrayList<String>();

    public List<String> getDifferences() {
        return differences;
    }

    public boolean isEqual() {
        return differences.isEmpty();
    }

    private void diff(String where, Object a, Object b) {
        if (a == null ? b != null : !a.equals(b)) {
            differences.add(where + ": " + a + " != " + b);
        }
    }

    /**
     * Compares two types by what they denote rather than by how they were written.
     * <p>
     * An object reference has two spellings in the corpus - a type named
     * {@code ObjectRef(Product)}, and {@code Product} with the objectRef flag - and they
     * mean the same thing. A format that writes one of them is not losing anything by not
     * writing the other, so the comparison unwraps both before looking at them. The XML
     * round trip compares the XML text as well, so a change of spelling there is still
     * caught.
     */
    private void diff(String where, TypeRef a, TypeRef b) {
        TypeRef x = a == null ? null : a.unwrapped();
        TypeRef y = b == null ? null : b.unwrapped();
        if (x == null ? y != null : !x.equals(y)) {
            differences.add(where + ": " + a + " != " + b);
        }
    }

    private boolean sizes(String where, List<?> a, List<?> b) {
        if (a.size() != b.size()) {
            differences.add(where + ": " + a.size() + " entries != " + b.size());
            return false;
        }
        return true;
    }

    public void compare(Specification a, Specification b) {
        diff("schemaVersion", a.getSchemaVersion(), b.getSchemaVersion());
        diff("comment", a.getComment(), b.getComment());
        if (sizes("areas", a.getAreas(), b.getAreas())) {
            for (int i = 0; i < a.getAreas().size(); i++) {
                compareArea(a.getAreas().get(i), b.getAreas().get(i));
            }
        }
    }

    private void compareArea(Area a, Area b) {
        String at = "area " + a.getName();
        diff(at + ".name", a.getName(), b.getName());
        diff(at + ".number", a.getNumber(), b.getNumber());
        diff(at + ".version", a.getVersion(), b.getVersion());
        diff(at + ".comment", a.getComment(), b.getComment());
        compareDocs(at, a.getDocumentation(), b.getDocumentation());
        compareTypes(at, a.getDataTypes(), b.getDataTypes());
        compareErrorDefs(at, a.getErrors(), b.getErrors());
        if (sizes(at + ".services", a.getServices(), b.getServices())) {
            for (int i = 0; i < a.getServices().size(); i++) {
                compareService(at, a.getServices().get(i), b.getServices().get(i));
            }
        }
    }

    private void compareService(String parent, Service a, Service b) {
        String at = parent + "/service " + a.getName();
        diff(at + ".name", a.getName(), b.getName());
        diff(at + ".number", a.getNumber(), b.getNumber());
        diff(at + ".comment", a.getComment(), b.getComment());
        compareDocs(at, a.getDocumentation(), b.getDocumentation());
        compareTypes(at, a.getDataTypes(), b.getDataTypes());
        compareErrorDefs(at, a.getErrors(), b.getErrors());
        compareCom(at, a.getCom(), b.getCom());
        if (sizes(at + ".capabilitySets", a.getCapabilitySets(), b.getCapabilitySets())) {
            for (int i = 0; i < a.getCapabilitySets().size(); i++) {
                CapabilitySet x = a.getCapabilitySets().get(i);
                CapabilitySet y = b.getCapabilitySets().get(i);
                diff(at + ".capability.number", x.getNumber(), y.getNumber());
                diff(at + ".capability.comment", x.getComment(), y.getComment());
                if (sizes(at + ".operations", x.getOperations(), y.getOperations())) {
                    for (int j = 0; j < x.getOperations().size(); j++) {
                        compareOperation(at, x.getOperations().get(j), y.getOperations().get(j));
                    }
                }
            }
        }
    }

    private void compareOperation(String parent, Operation a, Operation b) {
        String at = parent + "/op " + a.getName();
        diff(at + ".name", a.getName(), b.getName());
        diff(at + ".number", a.getNumber(), b.getNumber());
        diff(at + ".pattern", a.getPattern(), b.getPattern());
        diff(at + ".comment", a.getComment(), b.getComment());
        diff(at + ".supportInReplay", a.isSupportInReplay(), b.isSupportInReplay());
        compareDocs(at, a.getDocumentation(), b.getDocumentation());
        diff(at + ".stages", a.getMessages().keySet(), b.getMessages().keySet());
        for (InteractionStage stage : a.getMessages().keySet()) {
            MessageBody x = a.getMessage(stage);
            MessageBody y = b.getMessage(stage);
            if (y == null) {
                differences.add(at + "." + stage + ": missing");
                continue;
            }
            diff(at + "." + stage + ".comment", x.getComment(), y.getComment());
            compareFields(at + "." + stage, x.getFields(), y.getFields());
        }
        if (sizes(at + ".errors", a.getErrors(), b.getErrors())) {
            for (int i = 0; i < a.getErrors().size(); i++) {
                ErrorReference x = a.getErrors().get(i);
                ErrorReference y = b.getErrors().get(i);
                diff(at + ".error.type", x.getError(), y.getError());
                diff(at + ".error.comment", x.getComment(), y.getComment());
                compareField(at + ".error.extra", x.getExtraInformation(), y.getExtraInformation());
            }
        }
    }

    private void compareFields(String at, List<Field> a, List<Field> b) {
        if (sizes(at + ".fields", a, b)) {
            for (int i = 0; i < a.size(); i++) {
                compareField(at + ".field[" + i + "]", a.get(i), b.get(i));
            }
        }
    }

    private void compareField(String at, Field a, Field b) {
        if (a == null || b == null) {
            if (a != b) {
                differences.add(at + ": " + (a == null ? "absent" : "present") + " != "
                        + (b == null ? "absent" : "present"));
            }
            return;
        }
        diff(at + ".name", a.getName(), b.getName());
        diff(at + ".comment", a.getComment(), b.getComment());
        diff(at + ".canBeNull", a.isCanBeNull(), b.isCanBeNull());
        diff(at + ".type", a.getType(), b.getType());
    }

    private void compareTypes(String at, List<TypeDefinition> a, List<TypeDefinition> b) {
        if (!sizes(at + ".dataTypes", a, b)) {
            return;
        }
        for (int i = 0; i < a.size(); i++) {
            TypeDefinition x = a.get(i);
            TypeDefinition y = b.get(i);
            String where = at + "/type " + x.getName();
            diff(where + ".class", x.getClass().getName(), y.getClass().getName());
            diff(where + ".name", x.getName(), y.getName());
            diff(where + ".comment", x.getComment(), y.getComment());
            if (x instanceof CompositeType && y instanceof CompositeType) {
                CompositeType cx = (CompositeType) x;
                CompositeType cy = (CompositeType) y;
                diff(where + ".shortFormPart", cx.getShortFormPart(), cy.getShortFormPart());
                diff(where + ".superType", cx.getSuperType(), cy.getSuperType());
                compareFields(where, cx.getFields(), cy.getFields());
            } else if (x instanceof EnumerationType && y instanceof EnumerationType) {
                EnumerationType ex = (EnumerationType) x;
                EnumerationType ey = (EnumerationType) y;
                diff(where + ".shortFormPart", ex.getShortFormPart(), ey.getShortFormPart());
                if (sizes(where + ".items", ex.getItems(), ey.getItems())) {
                    for (int j = 0; j < ex.getItems().size(); j++) {
                        EnumerationItem ix = ex.getItems().get(j);
                        EnumerationItem iy = ey.getItems().get(j);
                        diff(where + ".item.value", ix.getValue(), iy.getValue());
                        diff(where + ".item.nvalue", ix.getNumericValue(), iy.getNumericValue());
                        diff(where + ".item.comment", ix.getComment(), iy.getComment());
                    }
                }
            } else if (x instanceof AttributeType && y instanceof AttributeType) {
                diff(where + ".shortFormPart", ((AttributeType) x).getShortFormPart(),
                        ((AttributeType) y).getShortFormPart());
            } else if (x instanceof FundamentalType && y instanceof FundamentalType) {
                diff(where + ".superType", ((FundamentalType) x).getSuperType(),
                        ((FundamentalType) y).getSuperType());
            }
        }
    }

    private void compareErrorDefs(String at, List<ErrorDefinition> a, List<ErrorDefinition> b) {
        if (!sizes(at + ".errors", a, b)) {
            return;
        }
        for (int i = 0; i < a.size(); i++) {
            ErrorDefinition x = a.get(i);
            ErrorDefinition y = b.get(i);
            diff(at + ".error.name", x.getName(), y.getName());
            diff(at + ".error.number", x.getNumber(), y.getNumber());
            diff(at + ".error.comment", x.getComment(), y.getComment());
            compareField(at + ".error.extra", x.getExtraInformation(), y.getExtraInformation());
        }
    }

    private void compareCom(String at, COMFeatures a, COMFeatures b) {
        if (a == null || b == null) {
            if (a != b) {
                differences.add(at + ".com: " + (a == null ? "absent" : "present") + " != "
                        + (b == null ? "absent" : "present"));
            }
            return;
        }
        diff(at + ".com.objectsComment", a.getObjectsComment(), b.getObjectsComment());
        diff(at + ".com.eventsComment", a.getEventsComment(), b.getEventsComment());
        diff(at + ".com.archiveUsage", a.getArchiveUsage(), b.getArchiveUsage());
        diff(at + ".com.activityUsage", a.getActivityUsage(), b.getActivityUsage());
        compareDocs(at + ".com", a.getDocumentation(), b.getDocumentation());
        compareComObjects(at + ".com.objects", a.getObjects(), b.getObjects());
        compareComObjects(at + ".com.events", a.getEvents(), b.getEvents());
    }

    private void compareComObjects(String at, List<COMObject> a, List<COMObject> b) {
        if (!sizes(at, a, b)) {
            return;
        }
        for (int i = 0; i < a.size(); i++) {
            COMObject x = a.get(i);
            COMObject y = b.get(i);
            String where = at + "/" + x.getName();
            diff(where + ".name", x.getName(), y.getName());
            diff(where + ".number", x.getNumber(), y.getNumber());
            diff(where + ".comment", x.getComment(), y.getComment());
            diff(where + ".bodyType", x.getBodyType(), y.getBodyType());
            compareLink(where + ".related", x.getRelated(), y.getRelated());
            compareLink(where + ".source", x.getSource(), y.getSource());
        }
    }

    private void compareLink(String at, ObjectLink a, ObjectLink b) {
        if (a == null || b == null) {
            if (a != b) {
                differences.add(at + ": " + (a == null ? "absent" : "present") + " != "
                        + (b == null ? "absent" : "present"));
            }
            return;
        }
        diff(at + ".comment", a.getComment(), b.getComment());
        diff(at + ".target", a.getTarget(), b.getTarget());
    }

    private void compareDocs(String at, Documentation a, Documentation b) {
        if (a == null || b == null) {
            if (a != b) {
                differences.add(at + ".documentation: presence differs");
            }
            return;
        }
        if (sizes(at + ".docSections", a.getSections(), b.getSections())) {
            for (int i = 0; i < a.getSections().size(); i++) {
                DocSection x = a.getSections().get(i);
                DocSection y = b.getSections().get(i);
                diff(at + ".doc.name", x.getName(), y.getName());
                diff(at + ".doc.order", x.getOrder(), y.getOrder());
                diff(at + ".doc.content", x.getContent(), y.getContent());
            }
        }
        if (sizes(at + ".diagrams", a.getDiagrams(), b.getDiagrams())) {
            for (int i = 0; i < a.getDiagrams().size(); i++) {
                Diagram x = a.getDiagrams().get(i);
                Diagram y = b.getDiagrams().get(i);
                diff(at + ".diagram.name", x.getName(), y.getName());
                diff(at + ".diagram.svg", x.getSvg(), y.getSvg());
            }
        }
    }
}
