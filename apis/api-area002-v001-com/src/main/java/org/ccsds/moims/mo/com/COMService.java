/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Java API
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
package org.ccsds.moims.mo.com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * This class is deprecated. It is only here for backward compatibility with the
 * old MAL.
 */
@Deprecated
public abstract class COMService extends MALService {

    private final Map<Integer, COMObject> objectsByNumber = new HashMap<>();
    private final COMObject[] comObjects;

    public COMService(final ServiceKey serviceKey, final Identifier serviceName,
            final Element[] elements, final MALOperation[] operations) {
        this(serviceKey, serviceName, elements, operations, new COMObject[0]);
    }

    public COMService(final ServiceKey serviceKey, final Identifier serviceName,
            final Element[] elements, final MALOperation[] operations, final COMObject[] comObjects) {
        super(serviceKey, serviceName, elements, operations);
        this.comObjects = comObjects;
    }

    /**
     * Return an object identified by its number.
     *
     * @param opNumber The number of the object.
     * @return The found operation or null.
     */
    public COMObject getObjectByNumber(final UShort opNumber) {
        if (objectsByNumber.isEmpty()) {
            for (COMObject comObject : comObjects) {
                objectsByNumber.put(comObject.getObjectType().getNumber().getValue(), comObject);
            }
        }
        return objectsByNumber.get(opNumber.getValue());
    }

    /**
     * Returns the set of objects.
     *
     * @return The set of objects or an empty array if none defined.
     */
    public COMObject[] getObjects() {
        if (objectsByNumber.isEmpty()) {
            for (COMObject comObject : comObjects) {
                objectsByNumber.put(comObject.getObjectType().getNumber().getValue(), comObject);
            }
        }
        return (COMObject[]) Arrays.asList(objectsByNumber.values()).toArray();
    }
}
