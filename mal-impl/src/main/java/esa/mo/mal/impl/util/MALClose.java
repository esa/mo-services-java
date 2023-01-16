/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.util;

import java.util.LinkedList;
import java.util.List;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Base class that tracks children classes and its parent class and supports the
 * concept of being closed. Closing this class informs the parent we are being
 * closed and also all children that they should close also.
 *
 */
public class MALClose {

    private final List<MALClose> children = new LinkedList<>();
    private final MALClose parent;

    /**
     * Initialises the parent field of the closing class.
     *
     * @param parent The parent object of this class, may be null if no parent.
     */
    public MALClose(final MALClose parent) {
        this.parent = parent;
    }

    /**
     * Closes this class, informs all children that we are being closed and they
     * should also, and then informs our parent (if we have one) that we are
     * closing.
     *
     * @throws MALException If there is a problem with being closed.
     */
    public void close() throws MALException {
        synchronized (children) {
            parentClose();

            if (null != parent) {
                parent.childClose(this);
            }
        }
    }

    /**
     * Called by parent to notify its children that we are closing.
     *
     * @throws MALException If an error occurs.
     */
    protected void parentClose() throws MALException {
        synchronized (children) {
            for (MALClose obj : children) {
                obj.parentClose();
            }

            children.clear();
            thisObjectClose();
        }
    }

    /**
     * Removes a child from the list of children.
     *
     * @param child The child to remove.
     */
    protected void childClose(final MALClose child) {
        synchronized (children) {
            children.remove(child);
        }
    }

    /**
     * Add a child to the list of children.
     *
     * @param child The child to add.
     * @return Returns the passed in child.
     */
    protected final MALClose addChild(final MALClose child) {
        synchronized (children) {
            children.add(child);
        }

        return child;
    }

    /**
     * This should be overridden by any implementing class to provide the actual
     * close functionality of this class.
     *
     * @throws MALException If an error occurs, implementations should throw a
     * MALException.
     */
    protected void thisObjectClose() throws MALException {
        // do nothing by default
    }
}
