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

/**
 * The related or source link of a COM object.
 * <p>
 * Either part may be absent, and in the specifications the commonest case by far is a
 * comment with no target: the link is described in prose but not named.
 */
public final class ObjectLink {

    private String comment;
    private ObjectReference target;

    /**
     * @return prose describing the link, or null.
     */
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the object pointed at, or null if the link is unspecified.
     */
    public ObjectReference getTarget() {
        return target;
    }

    public void setTarget(ObjectReference target) {
        this.target = target;
    }

    /**
     * @return true if the link carries neither a target nor a comment, and so says nothing.
     */
    public boolean isEmpty() {
        return target == null && (comment == null || comment.isEmpty());
    }
}
