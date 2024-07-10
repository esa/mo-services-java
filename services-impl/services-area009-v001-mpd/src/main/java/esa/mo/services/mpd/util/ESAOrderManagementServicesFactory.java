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

import org.ccsds.moims.mo.mpd.OrderManagementServicesFactory;
import esa.mo.services.mpd.consumer.OrderManagementConsumerServiceImpl;
import esa.mo.services.mpd.provider.OrderManagementProviderServiceImpl;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.misc.ConsumerServiceImpl;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;

/**
 *
 */
public class ESAOrderManagementServicesFactory extends OrderManagementServicesFactory {

    private OrderManagementProviderServiceImpl providerService = null;

    public ESAOrderManagementServicesFactory() {
    }

    @Override
    public OrderManagementInheritanceSkeleton createProvider() throws MALException {
        providerService = new OrderManagementProviderServiceImpl();
        providerService.init();
        return providerService;
    }

    @Override
    public OrderManagementStub createConsumerStub(SingleConnectionDetails details) throws MALException, MALInteractionException {
        if (details == null) {
            throw new MALException("The provider details are null!");
        }

        ConsumerServiceImpl consumerService = new OrderManagementConsumerServiceImpl(details);
        return (OrderManagementStub) consumerService.getStub();
    }
}
