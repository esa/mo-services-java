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
import esa.mo.mal.impl.util.MALClose;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Implementation of the MALProviderManager interface.
 */
public class MALProviderManagerImpl extends MALClose implements MALProviderManager {

    private final MALContextImpl impl;

    /**
     * Creates a provider manager.
     *
     * @param impl The MAL implementation.
     */
    public MALProviderManagerImpl(final MALContextImpl impl) {
        super(impl);
        this.impl = impl;
    }

    @Override
    public MALProvider createProvider(
            final String localName,
            final String protocol,
            final MALService service,
            final Blob authenticationId,
            final MALInteractionHandler handler,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final Map defaultQoSProperties,
            final Boolean isPublisher,
            final URI sharedBrokerUri,
            final NamedValueList supplements) throws MALException {
        return new MALProviderImpl(this,
                impl,
                localName,
                protocol,
                service,
                authenticationId,
                handler,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties,
                isPublisher,
                sharedBrokerUri);
    }

    @Override
    public MALProvider createProvider(
            final MALEndpoint endPoint,
            final MALService service,
            final Blob authenticationId,
            final MALInteractionHandler handler,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final Map defaultQoSProperties,
            final Boolean isPublisher,
            final URI sharedBrokerUri,
            final NamedValueList supplements) throws MALException {
        return new MALProviderImpl(this,
                impl,
                endPoint,
                service,
                authenticationId,
                handler,
                expectedQos,
                priorityLevelNumber,
                defaultQoSProperties,
                isPublisher,
                sharedBrokerUri);
    }

    @Override
    public void close() throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void thisObjectClose() throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
