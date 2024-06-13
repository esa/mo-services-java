/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.provider;

import esa.mo.mal.impl.MALContextImpl;
import esa.mo.mal.impl.util.MALCloseable;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Implementation of the MALProviderManager interface.
 */
public class MALProviderManagerImpl implements MALProviderManager, MALCloseable {

    private final MALContextImpl contextImpl;

    /**
     * Creates a provider manager.
     *
     * @param contextImpl The MAL implementation.
     */
    public MALProviderManagerImpl(final MALContextImpl contextImpl) {
        this.contextImpl = contextImpl;
    }

    @Override
    public MALProvider createProvider(
            final String localName,
            final String protocol,
            final ServiceInfo service,
            final Blob authenticationId,
            final MALInteractionHandler handler,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final Map defaultQoSProperties,
            final Boolean isPublisher,
            final URI sharedBrokerUri,
            final NamedValueList supplements) throws MALException {
        // Load the elements here:
        MALContextFactory.getElementsRegistry().loadServiceAndAreaElements(service);

        return new MALProviderImpl(this,
                contextImpl,
                localName,
                protocol,
                service,
                authenticationId,
                handler,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties,
                isPublisher,
                sharedBrokerUri,
                supplements);
    }

    @Override
    public MALProvider createProvider(
            final MALEndpoint endPoint,
            final ServiceInfo service,
            final Blob authenticationId,
            final MALInteractionHandler handler,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final Map defaultQoSProperties,
            final Boolean isPublisher,
            final URI sharedBrokerUri,
            final NamedValueList supplements) throws MALException {
        // Load the elements here:
        MALContextFactory.getElementsRegistry().loadServiceAndAreaElements(service);

        return new MALProviderImpl(this,
                contextImpl,
                endPoint,
                service,
                authenticationId,
                handler,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties,
                isPublisher,
                sharedBrokerUri,
                supplements);
    }

    @Override
    public void close() throws MALException {
    }
}
