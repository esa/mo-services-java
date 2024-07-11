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
package esa.mo.mal.impl.broker;

import esa.mo.mal.impl.MALContextImpl;
import esa.mo.mal.impl.transport.TransportSingleton;
import esa.mo.mal.impl.util.MALCloseable;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBroker;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.broker.MALBrokerHandler;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * Implements the MALBrokerManager interface.
 */
public class MALBrokerManagerImpl implements MALBrokerManager, MALCloseable {

    private final MALContextImpl impl;
    private final Map<String, MALBrokerBindingImpl> brokerBindingMap;

    /**
     * Constructor.
     *
     * @param impl MAL implementation.
     * @param brokerBindingMap Broker binding map.
     */
    public MALBrokerManagerImpl(final MALContextImpl impl,
            final Map<String, MALBrokerBindingImpl> brokerBindingMap) {
        this.impl = impl;
        this.brokerBindingMap = brokerBindingMap;
    }

    @Override
    public synchronized MALBroker createBroker() throws MALException {
        return new MALBrokerImpl();
    }

    @Override
    public synchronized MALBrokerBinding createBrokerBinding(
            final MALBroker optionalMALBroker,
            final String localName,
            final String protocol,
            final Blob authenticationId,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final Map qosProperties,
            final NamedValueList supplements) throws MALException {
        MALBrokerBinding retVal = null;

        MALBrokerImpl tparent = (MALBrokerImpl) optionalMALBroker;
        if (optionalMALBroker == null) {
            tparent = (MALBrokerImpl) createBroker();

            final MALTransport transport = TransportSingleton.instance(protocol, impl.getInitialProperties());
            retVal = transport.createBroker(localName,
                    authenticationId,
                    expectedQos,
                    priorityLevelNumber,
                    qosProperties);

            if (retVal != null) {
                return retVal;
            }
        }

        if (retVal == null) {
            retVal = new MALBrokerBindingImpl(tparent,
                    impl,
                    localName,
                    protocol,
                    authenticationId,
                    expectedQos,
                    priorityLevelNumber,
                    supplements,
                    qosProperties);
            ((MALBrokerBindingImpl) retVal).init();
            brokerBindingMap.put(retVal.getURI().getValue(), (MALBrokerBindingImpl) retVal);
        }

        return retVal;
    }

    @Override
    public MALBrokerBinding createBrokerBinding(
            final MALBroker optionalMALBroker,
            final MALEndpoint endPoint,
            final Blob authenticationId,
            final QoSLevel[] expectedQos,
            final UInteger priorityLevelNumber,
            final Map qosProperties,
            final NamedValueList supplements)
            throws IllegalArgumentException, MALException {
        MALBrokerBinding retVal = null;

        MALBrokerImpl tparent = (MALBrokerImpl) optionalMALBroker;
        if (optionalMALBroker == null) {
            tparent = (MALBrokerImpl) createBroker();

            final MALTransport transport = TransportSingleton.instance(endPoint.getURI(), impl.getInitialProperties());
            retVal = transport.createBroker(endPoint,
                    authenticationId,
                    expectedQos,
                    priorityLevelNumber,
                    qosProperties);

            if (retVal != null) {
                return retVal;
            }
        }

        if (retVal == null) {
            retVal = new MALBrokerBindingImpl(tparent,
                    impl,
                    endPoint,
                    authenticationId,
                    expectedQos,
                    priorityLevelNumber,
                    supplements,
                    qosProperties);
            ((MALBrokerBindingImpl) retVal).init();
            brokerBindingMap.put(retVal.getURI().getValue(), (MALBrokerBindingImpl) retVal);
        }

        return retVal;
    }

    @Override
    public void close() throws MALException {
        for (MALBrokerBindingImpl broker : brokerBindingMap.values()) {
            broker.close();
        }

        brokerBindingMap.clear();
    }
}
