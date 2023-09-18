/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Common Support library
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
package esa.mo.common.support;

import esa.mo.com.support.BaseComServer;
import java.util.Arrays;
import org.ccsds.moims.mo.common.CommonHelper;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Extension of the base COM service provide class for Common based service
 * providers.
 */
@Deprecated
public abstract class BaseCommonServer extends BaseComServer {

    private final Identifier providerName;
    protected final DirectoryServiceWrapper directoryService;

    /**
     * Constructor.
     *
     * @param providerName The name of this provider to be used in the directory
     * service.
     * @param domain the domain of the service provider.
     * @param network The network of the service provider.
     */
    public BaseCommonServer(final String providerName,
            final IdentifierList domain, final Identifier network) {
        super(domain, network);

        this.providerName = new Identifier(providerName);
        directoryService = new DirectoryServiceWrapper();
    }

    /**
     * Constructor.
     *
     * @param malFactory The MAL factory to use.
     * @param mal The MAL context to use.
     * @param consumerMgr The consumer manager to use.
     * @param providerMgr The provider manager to use.
     * @param providerName The name of this provider to be used in the directory
     * service.
     * @param domain the domain of the service provider.
     * @param network The network of the service provider.
     */
    public BaseCommonServer(final MALContextFactory malFactory,
            final MALContext mal,
            final MALConsumerManager consumerMgr,
            final MALProviderManager providerMgr,
            final String providerName,
            final IdentifierList domain,
            final Identifier network) {
        super(malFactory, mal, consumerMgr, providerMgr, domain, network);

        this.providerName = new Identifier(providerName);
        directoryService = new DirectoryServiceWrapper();
    }

    @Override
    protected void subInitHelpers(MALElementsRegistry bodyElementFactory) throws MALException {
        super.subInitHelpers(bodyElementFactory);

        CommonHelper.deepInit(bodyElementFactory);
    }

    @Override
    protected void subInit() throws MALException, MALInteractionException {
        super.subInit();

        String duri = System.getProperty("directory.uri",
                "rmi://localhost:1024/1024-DirectoryService");
        directoryService.init(consumerMgr, new URI(duri));
    }

    /**
     * Helper method that creates the MAL provider instance and registers it in
     * the Directory service.
     *
     * @param <T> The service provider type.
     * @param service The MAL service being provided.
     * @param supportCapabilities The supported capability numbers being
     * provided.
     * @param handler The service provider implementation.
     * @param isPublisher True if service provider is a publisher.
     * @return The supplied handler object.
     * @throws MALException On error.
     * @throws MALInteractionException On error.
     */
    protected <T extends MALInteractionHandler> T createAndPublishProvider(
            MALService service, Integer[] supportCapabilities, T handler,
            boolean isPublisher) throws MALException, MALInteractionException {
        IntegerList scl = new IntegerList();
        scl.addAll(Arrays.asList(supportCapabilities));

        createProvider(service, handler, isPublisher);
        directoryService.publishProvider(providerName, domain,
                network, service, scl, ep.getURI(), ep.getURI());

        return handler;
    }
}
