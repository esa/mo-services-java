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
package esa.mo.mal.impl.consumer;

import esa.mo.mal.impl.MALContextImpl;
import esa.mo.mal.impl.util.MALCloseable;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Implements the MALConsumerManager interface.
 */
public class MALConsumerManagerImpl implements MALConsumerManager, MALCloseable {

    private final MALContextImpl impl;

    /**
     * Constructor.
     *
     * @param impl MAL implementation.
     */
    public MALConsumerManagerImpl(final MALContextImpl impl) {
        this.impl = impl;
    }

    @Override
    public MALConsumer createConsumer(final String localName,
            final URI uriTo,
            final URI brokerUri,
            final MALService service,
            final Blob authenticationId,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel qosLevel,
            final Map qosProps,
            final UInteger priority,
            final NamedValueList supplements) throws MALException {
        // Load the elements here:
        MALContextFactory.getElementsRegistry().loadServiceAndAreaElements(service);

        return new MALConsumerImpl(impl,
                localName,
                uriTo,
                brokerUri,
                service,
                authenticationId,
                domain,
                networkZone,
                sessionType,
                sessionName,
                qosLevel,
                qosProps,
                priority,
                supplements);
    }

    @Override
    public MALConsumer createConsumer(final MALEndpoint endPoint,
            final URI uriTo,
            final URI brokerUri,
            final MALService service,
            final Blob authenticationId,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel qosLevel,
            final Map qosProps,
            final UInteger priority,
            final NamedValueList supplements)
            throws IllegalArgumentException, MALException {
        // Load the elements here:
        MALContextFactory.getElementsRegistry().loadServiceAndAreaElements(service);

        return new MALConsumerImpl(impl,
                endPoint,
                uriTo,
                brokerUri,
                service,
                authenticationId,
                domain,
                networkZone,
                sessionType,
                sessionName,
                qosLevel,
                qosProps,
                priority);
    }

    @Override
    public void close() throws MALException {
        impl.close();
    }

}
