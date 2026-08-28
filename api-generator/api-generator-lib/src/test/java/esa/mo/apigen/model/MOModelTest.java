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
package esa.mo.apigen.model;

import static esa.mo.apigen.model.ModelFixtures.*;
import esa.mo.apigen.model.com.COMFeatures;
import esa.mo.apigen.model.com.COMObject;
import esa.mo.apigen.model.com.ObjectReference;
import esa.mo.apigen.model.types.CompositeType;
import static org.junit.Assert.*;
import org.junit.Test;

public class MOModelTest {

    @Test
    public void resolvesAnAreaLevelType() {
        MOModel model = new MOModel();
        Specification spec = spec("mal.xml", SchemaVersion.V003);
        Area mal = area(spec, "MAL", 1, 3);
        CompositeType blob = composite(mal, "Blob", 1);
        model.add(spec);

        assertSame(blob, model.resolve(ref("MAL", 3, "Blob")));
    }

    @Test
    public void doesNotResolveAcrossAreaVersions() {
        MOModel model = new MOModel();
        Specification spec = spec("mal.xml", SchemaVersion.V003);
        Area mal = area(spec, "MAL", 1, 3);
        composite(mal, "Blob", 1);
        model.add(spec);

        // The v1 reference must not silently pick up the v3 type.
        assertNull(model.resolve(ref("MAL", 1, "Blob")));
    }

    @Test
    public void bothVersionsOfAnAreaCanBeLoadedAndResolveSeparately() {
        MOModel model = new MOModel();
        Specification v1 = spec("mal-v1.xml", SchemaVersion.V001);
        CompositeType oldBlob = composite(area(v1, "MAL", 1, 1), "Blob", 1);
        Specification v3 = spec("mal-v3.xml", SchemaVersion.V003);
        CompositeType newBlob = composite(area(v3, "MAL", 1, 3), "Blob", 1);
        model.add(v1);
        model.add(v3);

        assertSame(oldBlob, model.resolve(ref("MAL", 1, "Blob")));
        assertSame(newBlob, model.resolve(ref("MAL", 3, "Blob")));
        assertTrue(model.getConflictingAreas().isEmpty());
    }

    @Test
    public void aDuplicateAreaIdentityIsRecordedRatherThanOverwriting() {
        // area002-v001-COM.xml and area002-v002-Basics.xml both declare COM 2 v1.
        MOModel model = new MOModel();
        Specification com = spec("area002-v001-COM.xml", SchemaVersion.V001);
        Area first = area(com, "COM", 2, 1);
        Specification basics = spec("area002-v002-Basics.xml", SchemaVersion.V001);
        Area second = area(basics, "COM", 2, 1);
        model.add(com);
        model.add(basics);

        assertSame(first, model.findArea(new AreaKey("COM", 2, 1)));
        assertEquals(1, model.getConflictingAreas().size());
        assertSame(second, model.getConflictingAreas().get(0));
    }

    @Test
    public void anAmbiguousNameAndVersionResolvesToNothing() {
        // MC is area 4; Edge Monitor and Control is also MC, area 104. Same name and
        // version, so a reference naming only 'MC' cannot be resolved.
        MOModel model = new MOModel();
        Specification a = spec("area004.xml", SchemaVersion.V001);
        area(a, "MC", 4, 1);
        Specification b = spec("area104.xml", SchemaVersion.V001);
        area(b, "MC", 104, 1);
        model.add(a);
        model.add(b);

        assertNull(model.findArea("MC", 1));
        assertNull(model.findUniqueArea("MC"));
        assertEquals(2, model.findAreas("MC").size());
    }

    @Test
    public void resolvesACOMObjectByNumberAcrossObjectsAndEvents() {
        MOModel model = new MOModel();
        Specification spec = spec("sm.xml", SchemaVersion.V001);
        Area sm = area(spec, "SoftwareManagement", 7, 1);
        Service apps = service(sm, "AppsLauncher", 5);
        COMFeatures com = new COMFeatures();
        COMObject app = new COMObject();
        app.setName("App");
        app.setNumber(1);
        com.getObjects().add(app);
        COMObject stopApp = new COMObject();
        stopApp.setName("StopApp");
        stopApp.setNumber(2);
        com.getEvents().add(stopApp);
        apps.setCom(com);
        model.add(spec);

        assertSame(app, model.resolve(new ObjectReference("SoftwareManagement", 1, "AppsLauncher", 1)));
        assertSame(stopApp, model.resolve(new ObjectReference("SoftwareManagement", 1, "AppsLauncher", 2)));
        assertNull(model.resolve(new ObjectReference("SoftwareManagement", 1, "AppsLauncher", 9)));
    }
}
