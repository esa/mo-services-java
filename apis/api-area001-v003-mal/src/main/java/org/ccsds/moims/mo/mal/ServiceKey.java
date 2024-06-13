/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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

import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Class representing a key to a MAL Service.
 */
public class ServiceKey {

    /**
     * Area number.
     */
    private final UShort areaNumber;

    /**
     * Area version.
     */
    private final UOctet areaVersion;

    /**
     * Service number.
     */
    private final UShort serviceNumber;

    /**
     * Initializes the ServiceKey class.
     *
     * @param areaNumber The area number of the service.
     * @param areaVersion The area version of the service.
     * @param serviceNumber The service number of the service.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public ServiceKey(final UShort areaNumber, final UOctet areaVersion,
            final UShort serviceNumber) throws IllegalArgumentException {
        if (areaNumber == null) {
            throw new IllegalArgumentException("The areaNumber argument cannot be null!");
        }
        if (areaVersion == null) {
            throw new IllegalArgumentException("The areaVersion argument cannot be null!");
        }
        if (serviceNumber == null) {
            throw new IllegalArgumentException("The serviceNumber argument cannot be null!");
        }

        this.areaNumber = areaNumber;
        this.areaVersion = areaVersion;
        this.serviceNumber = serviceNumber;
    }

    /**
     * Initializes the ServiceKey class.
     *
     * @param areaNumber The area number of the service.
     * @param areaVersion The area version of the service.
     * @param serviceNumber The service number of the service.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public ServiceKey(final int areaNumber, final int areaVersion,
            final UShort serviceNumber) throws IllegalArgumentException {
        this(new UShort(areaNumber), new UOctet(areaVersion), serviceNumber);
    }

    /**
     * Returns the area number of the service.
     *
     * @return The area number of the service.
     */
    public UShort getAreaNumber() {
        return areaNumber;
    }

    /**
     * Returns the area version of the service.
     *
     * @return The area version of the service.
     */
    public UOctet getAreaVersion() {
        return areaVersion;
    }

    /**
     * Returns the service number of the service.
     *
     * @return The service number of the service.
     */
    public UShort getServiceNumber() {
        return serviceNumber;
    }
}
