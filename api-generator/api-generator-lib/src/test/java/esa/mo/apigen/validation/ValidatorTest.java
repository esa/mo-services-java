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

import esa.mo.apigen.model.*;
import static esa.mo.apigen.model.ModelFixtures.*;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectLink;
import esa.mo.apigen.model.types.CompositeType;
import static org.junit.Assert.*;
import org.junit.Test;

public class ValidatorTest {

    private static MOModel modelWith(Specification... specs) {
        MOModel model = new MOModel();
        for (Specification spec : specs) {
            model.add(spec);
        }
        return model;
    }

    private static boolean has(ValidationResult result, String rule) {
        for (ValidationIssue issue : result.getIssues()) {
            if (issue.getRule().equals(rule)) {
                return true;
            }
        }
        return false;
    }

    private static Specification mal() {
        Specification mal = spec("mal.xml", SchemaVersion.V003);
        composite(area(mal, "MAL", 1, 3), "Composite", 1);
        return mal;
    }

    /**
     * COM objects and events share one numbering space within a service - the schema's
     * uniqueness constraint selects both lists on a single number field. In Software
     * Management, AppsLauncher numbers its object 1 and its events 2, 3 and 4.
     */
    @Test
    public void comObjectsAndEventsShareOneNumberingSpace() {
        Specification spec = spec("sm.xml", SchemaVersion.V001);
        Service service = service(area(spec, "SM", 7, 1), "AppsLauncher", 5);
        COMFeatures com = new COMFeatures();
        COMObject app = new COMObject();
        app.setName("App");
        app.setNumber(1);
        com.getObjects().add(app);
        COMObject clash = new COMObject();
        clash.setName("StopApp");
        clash.setNumber(1);
        com.getEvents().add(clash);
        service.setCom(com);

        assertTrue(has(new Validator().validate(modelWith(spec)), "com.number.duplicate"));
    }

    @Test
    public void aCOMLinkWithNeitherTargetNorCommentIsPointedOut() {
        Specification spec = spec("sm.xml", SchemaVersion.V001);
        Service service = service(area(spec, "SM", 7, 1), "PackageManagement", 1);
        COMFeatures com = new COMFeatures();
        COMObject pkg = new COMObject();
        pkg.setName("Package");
        pkg.setNumber(1);
        pkg.setRelated(new ObjectLink());
        com.getObjects().add(pkg);
        service.setCom(com);

        ValidationResult result = new Validator().validate(modelWith(spec));
        assertTrue(has(result, "com.link.empty"));
        assertFalse("an empty link is noise, not a build failure", result.hasErrors());
    }

    @Test
    public void supportInReplayIsRejectedOnAV003Specification() {
        Specification spec = spec("mc.xml", SchemaVersion.V003);
        Operation op = operation(capability(service(area(spec, "MC", 4, 2), "Action", 1), 1),
                "submitAction", 1, InteractionPattern.SUBMIT);
        op.setSupportInReplay(true);

        assertTrue(has(new Validator().validate(modelWith(spec)), "version.supportInReplay"));
    }

    @Test
    public void serviceLevelDataTypesAreRejectedOnAV003Specification() {
        Specification spec = spec("mpd.xml", SchemaVersion.V003);
        Service service = service(area(spec, "MPD", 9, 1), "ProductRetrieval", 1);
        CompositeType type = new CompositeType();
        type.setName("Stray");
        service.getDataTypes().add(type);

        assertTrue(has(new Validator().validate(modelWith(spec)), "version.serviceDataTypes"));
    }

    @Test
    public void aMessageAtAStageThePatternDoesNotHaveIsRejected() {
        Specification spec = spec("mc.xml", SchemaVersion.V003);
        Operation op = operation(capability(service(area(spec, "MC", 4, 2), "Action", 1), 1),
                "listAction", 1, InteractionPattern.REQUEST);
        op.getMessages().put(InteractionStage.SUBSCRIPTION_KEYS, new MessageBody());

        assertTrue(has(new Validator().validate(modelWith(spec)), "operation.stage.unexpected"));
    }

    @Test
    public void duplicateOperationNumbersWithinAServiceAreRejected() {
        Specification spec = spec("mc.xml", SchemaVersion.V003);
        CapabilitySet set = capability(service(area(spec, "MC", 4, 2), "Action", 1), 1);
        operation(set, "one", 1, InteractionPattern.SUBMIT);
        operation(set, "two", 1, InteractionPattern.SUBMIT);

        assertTrue(has(new Validator().validate(modelWith(spec)), "operation.number.duplicate"));
    }

    @Test
    public void anUnresolvableTypeIsReported() {
        Specification spec = spec("mc.xml", SchemaVersion.V003);
        Area mc = area(spec, "MC", 4, 2);
        CompositeType type = composite(mc, "ActionDefinition", 1);
        Field field = new Field();
        field.setName("missing");
        field.setType(ref("MAL", 3, "NoSuchType"));
        type.getFields().add(field);

        assertTrue(has(new Validator().validate(modelWith(spec, mal())), "type.unresolved"));
    }

    @Test
    public void anUnlinkedTypeReferenceIsReported() {
        Specification spec = spec("mc.xml", SchemaVersion.V003);
        Area mc = area(spec, "MC", 4, 2);
        CompositeType type = composite(mc, "ActionDefinition", 1);
        Field field = new Field();
        field.setName("unlinked");
        field.setType(new esa.mo.apigen.model.types.TypeRef("MAL", 0, null, "Blob", false, false));
        type.getFields().add(field);

        assertTrue(has(new Validator().validate(modelWith(spec, mal())), "type.unlinked"));
    }

    @Test
    public void aDuplicateAreaIdentityIsAWarningNotAnError() {
        // Nothing in the build loads a colliding pair today, so this guards a future
        // pairing rather than describing a current failure.
        Specification com = spec("area002-v001-COM.xml", SchemaVersion.V001);
        area(com, "COM", 2, 1);
        Specification basics = spec("area002-v002-Basics.xml", SchemaVersion.V001);
        area(basics, "COM", 2, 1);

        ValidationResult result = new Validator().validate(modelWith(com, basics));
        assertTrue(has(result, "area.identity.duplicate"));
        assertFalse(result.hasErrors());
    }

    @Test
    public void aCleanModelReportsNothing() {
        Specification spec = spec("mc.xml", SchemaVersion.V003);
        Area mc = area(spec, "MC", 4, 2);
        CompositeType details = composite(mc, "ActionDefinition", 1);
        Field field = new Field();
        field.setName("name");
        field.setType(ref("MAL", 3, "Composite"));
        details.getFields().add(field);
        operation(capability(service(mc, "Action", 1), 1), "submitAction", 1,
                InteractionPattern.SUBMIT);

        ValidationResult result = new Validator().validate(modelWith(spec, mal()));
        assertFalse(result.toString(), result.hasIssues());
    }
}
