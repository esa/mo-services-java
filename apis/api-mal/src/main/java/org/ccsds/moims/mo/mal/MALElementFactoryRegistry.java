/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds a map of MALElementFactorys indexed on type short forms. Used to lookup
 * the correct element factory for a supplied type short form.
 */
public class MALElementFactoryRegistry {

    private final Map<Object, MALElementFactory> factoryMap = new HashMap<Object, MALElementFactory>();

    /**
     * Registers a element factory in the map using the supplied short form
     * object as the key.
     *
     * @param shortForm The short form object used for lookup.
     * @param elementFactory The element factory.
     * @throws IllegalArgumentException If either supplied argument is null.
     */
    public void registerElementFactory(final Object shortForm,
            final MALElementFactory elementFactory)
            throws IllegalArgumentException {
        if ((null == shortForm) || (null == elementFactory)) {
            throw new IllegalArgumentException("NULL argument");
        }

        factoryMap.put(shortForm, elementFactory);
    }

    /**
     * Returns a MALElementFactory for the supplied short form, or null if not
     * found.
     *
     * @param shortForm The short form to search for.
     * @return The MALELementFactory or null if not found.
     * @throws IllegalArgumentException If supplied argument is null.
     */
    public MALElementFactory lookupElementFactory(final Object shortForm)
            throws IllegalArgumentException {
        if (null == shortForm) {
            throw new IllegalArgumentException("NULL argument");
        }

        return factoryMap.get(shortForm);
    }

    /**
     * Removes a previously registered short form from the map.
     *
     * @param shortForm The short form to deregister.
     * @return True if factory was registered.
     * @throws IllegalArgumentException If supplied argument is null.
     */
    public boolean deregisterElementFactory(final Object shortForm) throws IllegalArgumentException {
        if (null == shortForm) {
            throw new IllegalArgumentException("NULL argument");
        }

        if (factoryMap.containsKey(shortForm)) {
            factoryMap.remove(shortForm);
            return true;
        }

        return false;
    }
}
