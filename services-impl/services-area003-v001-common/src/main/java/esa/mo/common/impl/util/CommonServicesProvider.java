/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Common services
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
package esa.mo.common.impl.util;

import esa.mo.common.impl.provider.DirectoryProviderServiceImpl;
import org.ccsds.moims.mo.mal.MALException;

/**
 * The Common services consumer class that contains all the provider services.
 */
public class CommonServicesProvider {

    private final DirectoryProviderServiceImpl directoryService = new DirectoryProviderServiceImpl();

    public void init() throws MALException {
        directoryService.init();
    }

    public DirectoryProviderServiceImpl getDirectoryService() {
        return this.directoryService;
    }
}
