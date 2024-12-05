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
package org.ccsds.moims.mo.mpd;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.ordermanagement.consumer.OrderManagementStub;
import org.ccsds.moims.mo.mpd.ordermanagement.provider.OrderManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productorderdelivery.consumer.ProductOrderDeliveryStub;
import org.ccsds.moims.mo.mpd.productorderdelivery.provider.ProductOrderDeliveryInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.productretrieval.consumer.ProductRetrievalStub;
import org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalInheritanceSkeleton;

/**
 * The interface for the Order Management service Factory.
 */
public abstract class MPDServicesFactory {

    public abstract OrderManagementInheritanceSkeleton createProviderOrderManagement() throws MALException;

    public abstract ProductOrderDeliveryInheritanceSkeleton createProviderProductOrderDelivery(ProductRetrievalBackend backend) throws MALException;

    public abstract ProductRetrievalInheritanceSkeleton createProviderProductRetrieval(ProductRetrievalBackend backend) throws MALException;

    public abstract OrderManagementStub createConsumerStubOrderManagement(SingleConnectionDetails details) throws MALException;

    public abstract ProductOrderDeliveryStub createConsumerStubProductOrderDelivery(SingleConnectionDetails details) throws MALException;

    public abstract ProductRetrievalStub createConsumerStubProductRetrieval(SingleConnectionDetails details) throws MALException;
}
