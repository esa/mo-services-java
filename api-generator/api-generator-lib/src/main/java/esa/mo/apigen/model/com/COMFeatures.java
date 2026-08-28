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
package esa.mo.apigen.model.com;

import esa.mo.apigen.model.docs.Documentation;
import java.util.ArrayList;
import java.util.List;

/**
 * The COM features a service declares: its objects, its events, and how it expects the
 * archive and activity tracking services to be used.
 * <p>
 * The comments on the object and event lists are not decoration - in the specifications
 * they run to many sentences and carry the normative rules for the links.
 */
public final class COMFeatures {

    private String objectsComment;
    private String eventsComment;
    private final List<COMObject> objects = new ArrayList<COMObject>();

    private boolean declaresObjects = false;

    private boolean declaresEvents = false;

    private boolean declaresArchiveUsage = false;

    private boolean declaresActivityUsage = false;
    private final List<COMObject> events = new ArrayList<COMObject>();
    private String archiveUsage;
    private String activityUsage;
    private Documentation documentation = new Documentation();

    public String getObjectsComment() {
        return objectsComment;
    }

    public void setObjectsComment(String objectsComment) {
        this.objectsComment = objectsComment;
    }

    public String getEventsComment() {
        return eventsComment;
    }

    public void setEventsComment(String eventsComment) {
        this.eventsComment = eventsComment;
    }

    /**
     * Returns true if the service wrote an objects section, whether or not it listed any.
     * <p>
     * Kept apart from the list itself: a service that declares the section and nothing in
     * it is still saying something about how it uses the COM, and the documents say so.
     *
     * @return true if the section was declared.
     */
    public boolean declaresObjects() {
        return declaresObjects;
    }

    public void setDeclaresObjects(boolean declaresObjects) {
        this.declaresObjects = declaresObjects;
    }

    /**
     * @return true if the service wrote an events section, whether or not it listed any.
     */
    public boolean declaresEvents() {
        return declaresEvents;
    }

    public void setDeclaresEvents(boolean declaresEvents) {
        this.declaresEvents = declaresEvents;
    }

    /**
     * Returns true if the service said how it uses the COM archive, whatever it said.
     * <p>
     * Kept apart from the text, which is the whole content of that statement: a format that
     * leaves documentation out still has to record that the statement was made.
     *
     * @return true if the section was declared.
     */
    public boolean declaresArchiveUsage() {
        return declaresArchiveUsage;
    }

    public void setDeclaresArchiveUsage(boolean declaresArchiveUsage) {
        this.declaresArchiveUsage = declaresArchiveUsage;
    }

    /**
     * @return true if the service said how it uses the COM activity tracking.
     */
    public boolean declaresActivityUsage() {
        return declaresActivityUsage;
    }

    public void setDeclaresActivityUsage(boolean declaresActivityUsage) {
        this.declaresActivityUsage = declaresActivityUsage;
    }

    public List<COMObject> getObjects() {
        return objects;
    }

    public List<COMObject> getEvents() {
        return events;
    }

    public String getArchiveUsage() {
        return archiveUsage;
    }

    public void setArchiveUsage(String archiveUsage) {
        this.archiveUsage = archiveUsage;
    }

    public String getActivityUsage() {
        return activityUsage;
    }

    public void setActivityUsage(String activityUsage) {
        this.activityUsage = activityUsage;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    /**
     * @return true if nothing at all is declared.
     */
    public boolean isEmpty() {
        return objects.isEmpty() && events.isEmpty()
                && archiveUsage == null && activityUsage == null
                && objectsComment == null && eventsComment == null
                && (documentation == null || documentation.isEmpty());
    }
}
