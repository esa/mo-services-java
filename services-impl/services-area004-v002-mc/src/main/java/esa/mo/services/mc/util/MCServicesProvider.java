/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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
package esa.mo.services.mc.util;

import esa.mo.services.mc.provider.ParameterProviderServiceImpl;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mc.backends.ParameterBackend;

/**
 * The MC services consumer class that contains all the provider services.
 */
public class MCServicesProvider {

    private final ParameterProviderServiceImpl parameterService = new ParameterProviderServiceImpl();

    /**
     * Initializes the service providers.
     *
     * @param parameterBackend The backend to the Parameter service.
     * @throws MALException if the services could not be initialized.
     */
    public void init(ParameterBackend parameterBackend) throws MALException {
        parameterService.init(parameterBackend);
    }

    /**
     * Returns the Parameter service provider.
     *
     * @return The Parameter service provider.
     */
    public ParameterProviderServiceImpl getParameterService() {
        return this.parameterService;
    }

}
