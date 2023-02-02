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
     * Service number.
     */
    private final UShort serviceNumber;

    /**
     * Service version.
     */
    private final UOctet serviceVersion;

    /**
     * Initializes the ServiceKey class.
     *
     * @param areaNumber The area number of the service.
     * @param serviceNumber The service number of the service.
     * @param serviceVersion The service version of the service.
     * @throws java.lang.IllegalArgumentException If any argument is null.
     */
    public ServiceKey(final UShort areaNumber, final UShort serviceNumber,
            final UOctet serviceVersion) throws IllegalArgumentException {
        if (areaNumber == null) {
            throw new IllegalArgumentException("The areaNumber argument cannot be null!");
        }
        if (serviceNumber == null) {
            throw new IllegalArgumentException("The serviceNumber argument cannot be null!");
        }
        if (serviceVersion == null) {
            throw new IllegalArgumentException("The serviceVersion argument cannot be null!");
        }

        this.areaNumber = areaNumber;
        this.serviceNumber = serviceNumber;
        this.serviceVersion = serviceVersion;
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
     * Returns the service number of the service.
     *
     * @return The service number of the service.
     */
    public UShort getServiceNumber() {
        return serviceNumber;
    }

    /**
     * Returns the service version of the service.
     *
     * @return The service version of the service.
     */
    public UOctet getServiceVersion() {
        return serviceVersion;
    }

}
