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

import esa.mo.apigen.link.Linker;
import esa.mo.apigen.model.*;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.types.CompositeType;
import esa.mo.apigen.model.types.TypeDefinition;
import esa.mo.apigen.validation.ValidationResult;
import java.io.File;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * The importer read against the specifications in this repository.
 * <p>
 * The counts below were measured from the XML itself, so a change in either the importer
 * or the specifications shows up here rather than three phases later.
 */
public class XmlImporterCorpusTest {

    private List<File> files;

    @Before
    public void findCorpus() {
        files = Corpus.all();
        Assume.assumeFalse("specifications not found from " + System.getProperty("user.dir"),
                files.isEmpty());
    }

    @Test
    public void everySpecificationParses() throws Exception {
        assertEquals("expected 12 prototypes and 6 standards", 18, files.size());
        for (File file : files) {
            Specification spec = Corpus.read(file);
            assertNotNull(file.getName(), spec.getSchemaVersion());
            assertFalse(file.getName() + " has no areas", spec.getAreas().isEmpty());
        }
    }

    @Test
    public void everySpecificationHasExactlyOneArea() throws Exception {
        for (File file : files) {
            assertEquals(file.getName(), 1, Corpus.read(file).getAreas().size());
        }
    }

    @Test
    public void schemaVersionComesFromTheRootNamespace() throws Exception {
        assertEquals(SchemaVersion.V001,
                Corpus.read(Corpus.file("area001-v001-MAL.xml")).getSchemaVersion());
        assertEquals(SchemaVersion.V003,
                Corpus.read(Corpus.file("area001-v003-MAL.xml")).getSchemaVersion());
        assertEquals(SchemaVersion.V003,
                Corpus.read(Corpus.file("area009-v001-Mission-Product-Distribution.xml"))
                        .getSchemaVersion());
    }

    @Test
    public void readsSoftwareManagementCompletely() throws Exception {
        Specification spec = Corpus.read(Corpus.file("area007-v001-Software-Management.xml"));
        Area area = spec.getAreas().get(0);

        assertEquals("SoftwareManagement", area.getName());
        assertEquals(7, area.getNumber());
        assertEquals(1, area.getVersion());
        assertEquals(4, area.getServices().size());

        int operations = 0;
        int composites = 0;
        int comObjects = 0;
        int comEvents = 0;
        int docSections = 0;
        int diagrams = 0;
        for (Service service : area.getServices()) {
            operations += service.getOperations().size();
            for (TypeDefinition type : service.getDataTypes()) {
                if (type instanceof CompositeType) {
                    composites++;
                }
            }
            docSections += service.getDocumentation().getSections().size();
            if (service.getCom() != null) {
                comObjects += service.getCom().getObjects().size();
                comEvents += service.getCom().getEvents().size();
                diagrams += service.getCom().getDocumentation().getDiagrams().size();
            }
        }
        assertEquals("operations", 13, operations);
        assertEquals("composites", 5, composites);
        assertEquals("COM objects", 4, comObjects);
        assertEquals("COM events", 6, comEvents);
        assertEquals("documentation sections", 6, docSections);
        assertEquals("diagrams", 2, diagrams);
    }

    @Test
    public void readsOperationLevelDocumentation() throws Exception {
        // MPD is the only specification that documents its operations: 36 sections.
        Specification spec = Corpus.read(
                Corpus.file("area009-v001-Mission-Product-Distribution.xml"));
        int sections = 0;
        for (Service service : spec.getAreas().get(0).getServices()) {
            for (Operation op : service.getOperations()) {
                sections += op.getDocumentation().getSections().size();
            }
        }
        assertEquals(36, sections);
    }

    @Test
    public void readsSubscriptionKeysWhichExistOnlyInV003() throws Exception {
        int found = 0;
        for (File file : Corpus.files("prototypes")) {
            Specification spec = Corpus.read(file);
            for (Area area : spec.getAreas()) {
                for (Service service : area.getServices()) {
                    for (Operation op : service.getOperations()) {
                        if (op.getMessages().containsKey(InteractionStage.SUBSCRIPTION_KEYS)) {
                            found++;
                            assertEquals(file.getName(), SchemaVersion.V003, spec.getSchemaVersion());
                        }
                    }
                }
            }
        }
        assertEquals(13, found);
    }

    @Test
    public void readsObjectReferencesWhichExistOnlyInV003() throws Exception {
        int found = 0;
        for (File file : Corpus.files("prototypes")) {
            for (Area area : Corpus.read(file).getAreas()) {
                for (TypeDefinition type : area.getDataTypes()) {
                    if (type instanceof CompositeType) {
                        for (Field field : ((CompositeType) type).getFields()) {
                            if (field.getType() != null && field.getType().isObjectRef()) {
                                found++;
                            }
                        }
                    }
                }
            }
        }
        assertTrue("expected object references in MPS and MC v2, found " + found, found > 50);
    }

    @Test
    public void readsDiagramsAsSvgText() throws Exception {
        Specification spec = Corpus.read(Corpus.file("area007-v001-Software-Management.xml"));
        int diagrams = 0;
        for (Service service : spec.getAreas().get(0).getServices()) {
            if (service.getCom() == null) {
                continue;
            }
            for (esa.mo.apigen.model.docs.Diagram d : service.getCom().getDocumentation().getDiagrams()) {
                diagrams++;
                assertNotNull(d.getName(), d.getSvg());
                assertTrue(d.getName() + " should carry SVG", d.getSvg().contains("<svg"));
            }
        }
        assertEquals(2, diagrams);
    }

    @Test
    public void anEmptyCommentIsKeptApartFromAnAbsentOne() throws Exception {
        // The specifications are full of comment="", which says nothing but is not the
        // same as saying nothing: a message body records which of the two a field carried,
        // so the import has to keep them apart. Everywhere a comment is shown they read
        // alike, and the generators substitute a plain description for either.
        Specification spec = Corpus.read(Corpus.file("area007-v001-Software-Management.xml"));
        int empty = 0;
        for (Service service : spec.getAreas().get(0).getServices()) {
            for (CapabilitySet set : service.getCapabilitySets()) {
                assertEquals("capability comments in this file are all empty",
                        "", set.getComment());
                empty++;
            }
        }
        assertTrue("the file should declare capability sets", empty > 0);
        // An attribute that is there and says something is kept as it is, so the three
        // states - absent, empty, and written - stay apart.
        assertNotNull(spec.getAreas().get(0).getComment());
    }

    @Test
    public void aCapturedDiagramIsSelfContained() throws Exception {
        // A diagram is written inside a specification, in a namespace the specification
        // declares once at the top. It is read here and rendered somewhere else entirely,
        // so what is captured has to carry that namespace itself - a drawing whose
        // namespace was dropped renders as an empty picture.
        Specification spec = Corpus.read(Corpus.file("area007-v001-Software-Management.xml"));
        int diagrams = 0;
        for (Service service : spec.getAreas().get(0).getServices()) {
            if (service.getCom() == null) {
                continue;
            }
            for (esa.mo.apigen.model.docs.Diagram diagram
                    : service.getCom().getDocumentation().getDiagrams()) {
                diagrams++;
                assertTrue(diagram.getName() + " should stand on its own",
                        diagram.getSvg().startsWith("<svg xmlns=\"http://www.w3.org/2000/svg\""));
            }
        }
        assertEquals(2, diagrams);
    }

    @Test
    public void anExtendedServiceIsMarkedWhetherOrNotItDeclaresFeatures() throws Exception {
        // A service says it is a COM extended service on the element itself. Most of them
        // go on to declare objects or events, but not all: this file has two that declare
        // none, and the generated ServiceInfo still has to extend COMService.
        Specification spec = Corpus.read(Corpus.file("area051-v001-Mission-Data-Product.xml"));
        int extended = 0;
        for (Service service : spec.getAreas().get(0).getServices()) {
            if (service.isExtended()) {
                extended++;
                assertNull("this file declares no features at all", service.getCom());
            }
        }
        assertEquals(2, extended);
    }

    @Test
    public void theSameSpecificationLoadedTwiceStillResolves() throws Exception {
        // A module commonly names its own specification among its references as well as
        // its target. The two copies say the same thing, so the second is left out of the
        // index rather than making every lookup in that area ambiguous.
        MOModel model = new MOModel();
        model.add(Corpus.read(Corpus.file("area001-v003-MAL.xml")));
        model.add(Corpus.read(Corpus.file("area002-v001-COM.xml")));
        model.add(Corpus.read(Corpus.file("area002-v001-COM.xml")));
        new Linker().link(model);

        assertTrue("the same file twice is not a conflict",
                model.getConflictingAreas().isEmpty());
        Area com = model.findArea("COM", 1);
        assertNotNull("the area has to stay reachable by name and version", com);
        assertFalse("and it has to still hold its types", com.getDataTypes().isEmpty());
    }

    @Test
    public void linkingResolvesTypesForATypicalModuleLoad() throws Exception {
        // What apis/api-area003-v001-common actually loads: Common, plus MAL v3 and COM
        // as references. Note the v001 specification resolving against MAL v3.
        MOModel model = new MOModel();
        model.add(Corpus.read(Corpus.file("area001-v003-MAL.xml")));
        model.add(Corpus.read(Corpus.file("area002-v001-COM.xml")));
        Specification common = Corpus.read(Corpus.file("area003-v001-Common.xml"));
        model.add(common);

        ValidationResult linked = new Linker().link(model);
        assertFalse(linked.toString(), linked.hasErrors());

        Area area = common.getAreas().get(0);
        for (Service service : area.getServices()) {
            for (TypeDefinition type : service.getDataTypes()) {
                if (type instanceof CompositeType) {
                    CompositeType composite = (CompositeType) type;
                    assertNotNull(composite.getName() + " should have a super type after linking",
                            composite.getSuperType());
                    assertTrue(composite.getName() + " super type should be linked",
                            composite.getSuperType().getAreaVersion() > 0);
                }
            }
        }
    }

    @Test
    public void impliedBaseCompositeIsResolvedAtImport() throws Exception {
        MOModel model = new MOModel();
        model.add(Corpus.read(Corpus.file("area001-v003-MAL.xml")));
        Specification mps = Corpus.read(
                Corpus.file("area005-v001-Mission-Planning-and-Scheduling.xml"));
        model.add(mps);
        new Linker().link(model);

        for (Area area : mps.getAreas()) {
            for (TypeDefinition type : area.getDataTypes()) {
                if (type instanceof CompositeType) {
                    assertNotNull(type.getName(), ((CompositeType) type).getSuperType());
                }
            }
        }
    }

    @Test
    public void comObjectsAndEventsAreReadWithTheirLinks() throws Exception {
        Specification spec = Corpus.read(Corpus.file("area007-v001-Software-Management.xml"));
        Service apps = spec.getAreas().get(0).getService("AppsLauncher");
        assertNotNull(apps);
        assertNotNull(apps.getCom());

        assertEquals(1, apps.getCom().getObjects().size());
        assertEquals(3, apps.getCom().getEvents().size());

        COMObject app = apps.getCom().getObjects().get(0);
        assertEquals("App", app.getName());
        assertEquals(1, app.getNumber());
        assertNotNull("App has a body type", app.getBodyType());
        assertNotNull("App has a source link", app.getSource());
        assertNotNull("the source link names PackageManagement::Installation",
                app.getSource().getTarget());
        assertEquals("PackageManagement", app.getSource().getTarget().getService());
        assertEquals(2, app.getSource().getTarget().getNumber());

        // Stopping and Stopped have no body at all.
        for (COMObject event : apps.getCom().getEvents()) {
            if ("Stopping".equals(event.getName()) || "Stopped".equals(event.getName())) {
                assertNull(event.getName() + " has no body", event.getBodyType());
            }
        }
    }
}
