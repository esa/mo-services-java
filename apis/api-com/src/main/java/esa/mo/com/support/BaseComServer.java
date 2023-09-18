/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Support library
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
package esa.mo.com.support;

import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;

/**
 * Extension of the base MAL service provide class for COM based service
 * providers.
 */
@Deprecated
public abstract class BaseComServer extends BaseMalServer {

    protected EventServiceProvider eventService;
    protected ActivityTrackingPublisher activityService;

    /**
     * Constructor.
     *
     * @param domain the domain of the service provider.
     * @param network The network of the service provider.
     */
    public BaseComServer(IdentifierList domain, Identifier network) {
        super(domain, network);
    }

    /**
     * Constructor.
     *
     * @param malFactory The MAL factory to use.
     * @param mal The MAL context to use.
     * @param consumerMgr The consumer manager to use.
     * @param providerMgr The provider manager to use.
     * @param domain the domain of the service provider.
     * @param network The network of the service provider.
     */
    public BaseComServer(MALContextFactory malFactory, MALContext mal,
            MALConsumerManager consumerMgr, MALProviderManager providerMgr,
            IdentifierList domain, Identifier network) {
        super(malFactory, mal, consumerMgr, providerMgr, domain, network);
    }

    @Override
    protected void subInitHelpers(MALElementsRegistry bodyElementFactory) throws MALException {
        COMHelper.deepInit(bodyElementFactory);
    }

    @Override
    protected void subInit() throws MALException, MALInteractionException {
        eventService = new EventServiceProvider(createEventHandlerPublishListener());
        activityService = new ActivityTrackingPublisher(eventService);

        createProvider(EventHelper.EVENT_SERVICE, eventService, true);

        eventService.init(domain, network);
    }

    protected MALPublishInteractionListener createEventHandlerPublishListener() {
        return new EventServiceProvider.EventPublisher();
    }
}
