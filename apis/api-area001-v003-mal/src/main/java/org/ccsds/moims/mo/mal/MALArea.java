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
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Represents a MAL Area and holds information relating to the area.
 */
public class MALArea {

    private final Map<Integer, MALService> serviceNumbers = new HashMap<>();
    private final UShort number;
    private final Identifier name;
    private final UOctet version;
    private final Element[] elements;
    private final MALService[] services;

    /**
     * MALArea constructor.
     *
     * @param number The number of the Area.
     * @param name The name of the Area.
     * @param version The Area version.
     * @param elements The elements in this Area.
     * @param services The services in this Area.
     * @throws IllegalArgumentException If either argument is null.
     */
    public MALArea(UShort number, Identifier name, UOctet version,
            Element[] elements, MALService[] services) {
        if (number == null) {
            throw new IllegalArgumentException("Number argument must not be NULL");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name argument must not be NULL");
        }
        if (version == null) {
            throw new IllegalArgumentException("Version argument must not be NULL");
        }

        this.number = number;
        this.name = name;
        this.version = version;
        this.elements = elements;
        this.services = services;
    }

    /**
     * Returns the number of this Area.
     *
     * @return The MAL Area number.
     */
    public final UShort getNumber() {
        return number;
    }

    /**
     * Returns the name of this Area.
     *
     * @return The MAL Area name.
     */
    public final Identifier getName() {
        return name;
    }

    /**
     * Returns the version of the area.
     *
     * @return The service version.
     */
    public UOctet getVersion() {
        return version;
    }

    /**
     * Returns the services in this MAL Area.
     *
     * @return The services in this MAL Area.
     */
    public final MALService[] getServices() {
        return services;
    }

    /**
     * Returns a contained service identified by its number.
     *
     * @param serviceNumber The number of the service to find.
     * @return The found service or null if not found.
     */
    public synchronized MALService getServiceByNumber(final UShort serviceNumber) {
        if (serviceNumbers.isEmpty()) {
            for (MALService service : services) {
                serviceNumbers.put(service.getServiceNumber().getValue(), service);
            }
        }

        return serviceNumbers.get(serviceNumber.getValue());
    }

    /**
     * Returns the elements in this Area.
     *
     * @return The elements in this Area.
     */
    public Element[] getElements() {
        return elements;
    }
}
