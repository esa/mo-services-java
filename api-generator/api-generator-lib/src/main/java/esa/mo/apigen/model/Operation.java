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

import esa.mo.apigen.model.docs.Documentation;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * An operation, carrying its own messages.
 * <p>
 * Holding the bodies here rather than deriving them is what removes the second, summary
 * model the old generators needed.
 * <p>
 * {@code supportInReplay} exists only in v001 specifications, where it is a required
 * attribute; {@code documentation} only in v003.
 */
public final class Operation {

    private String name;
    private int number;
    private String comment;
    private boolean supportInReplay;
    private InteractionPattern pattern;
    private final Map<InteractionStage, MessageBody> messages
            = new EnumMap<InteractionStage, MessageBody>(InteractionStage.class);
    private final List<ErrorReference> errors = new ArrayList<ErrorReference>();
    private Documentation documentation = new Documentation();
    private CapabilitySet parent;
    private SourceLocation location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSupportInReplay() {
        return supportInReplay;
    }

    public void setSupportInReplay(boolean supportInReplay) {
        this.supportInReplay = supportInReplay;
    }

    public InteractionPattern getPattern() {
        return pattern;
    }

    public void setPattern(InteractionPattern pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the messages, keyed by the stage each occupies.
     */
    public Map<InteractionStage, MessageBody> getMessages() {
        return messages;
    }

    /**
     * Returns the message at a stage.
     *
     * @param stage The stage to look for.
     * @return the message, or null if the operation has none at that stage.
     */
    public MessageBody getMessage(InteractionStage stage) {
        return messages.get(stage);
    }

    public List<ErrorReference> getErrors() {
        return errors;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    public CapabilitySet getParent() {
        return parent;
    }

    public void setParent(CapabilitySet parent) {
        this.parent = parent;
    }

    /**
     * @return the service that declares this operation, or null if it is detached.
     */
    public Service getService() {
        return parent == null ? null : parent.getService();
    }

    public SourceLocation getLocation() {
        return location;
    }

    public void setLocation(SourceLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return pattern + " " + name + " [" + number + "]";
    }
}
