/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
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
package esa.mo.services.mpd.util;

import esa.mo.services.mpd.provider.OrderManagementProviderServiceImpl;
import org.ccsds.moims.mo.mal.MALException;

/**
 * The MPD services consumer class that contains all the provider services.
 */
public class MPDServicesProvider {

    private final OrderManagementProviderServiceImpl orderManagementService = new OrderManagementProviderServiceImpl();

    public void init() throws MALException {
        orderManagementService.init();
    }

    public OrderManagementProviderServiceImpl getOrderManagementService() {
        return this.orderManagementService;
    }
}
