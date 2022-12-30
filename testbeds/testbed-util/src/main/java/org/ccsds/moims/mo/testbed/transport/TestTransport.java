/** *****************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 ****************************************************************************** */
package org.ccsds.moims.mo.testbed.transport;

import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

public class TestTransport implements MALTransport {

    private MALTransport delegate;

    public TestTransport(MALTransport delegate) throws MALException {
        this.delegate = delegate;
    }

    public void close() throws MALException {
        delegate.close();
    }

    public MALEndpoint createEndpoint(String localName, Map qosProperties) throws MALException {
        TestEndPoint ep = new TestEndPoint(delegate.createEndpoint(localName, qosProperties));
        TransportInterceptor.instance().addEndPoint(ep);
        return ep;
    }

    public void deleteEndpoint(String name) throws MALException {
        delegate.deleteEndpoint(name);
    }

    public boolean isSupportedInteractionType(InteractionType type) {
        TransportInterceptor.instance().incrementSupportedIpRequestCount(type);
        boolean isSupported = delegate.isSupportedInteractionType(type);
        TransportInterceptor.instance().incrementSupportedIpResponseCount(type);
        return isSupported;
    }

    public boolean isSupportedQoSLevel(QoSLevel qos) {
        TransportInterceptor.instance().incrementSupportedQoSRequestCount(qos);
        boolean isSupported = delegate.isSupportedQoSLevel(qos);
        TransportInterceptor.instance().incrementSupportedQoSResponseCount(qos);
        return isSupported;
    }

    public MALBrokerBinding createBroker(String localName, Blob authenticationId,
            QoSLevel[] expectedQos, UInteger priorityLevelNumber, Map qosProperties)
            throws MALException {
        return delegate.createBroker(localName, authenticationId, expectedQos,
                priorityLevelNumber, qosProperties);
    }

    public MALBrokerBinding createBroker(MALEndpoint endPoint, Blob authenticationId,
            QoSLevel[] expectedQos, UInteger priorityLevelNumber, Map qosProperties) throws MALException {
        return delegate.createBroker(endPoint, authenticationId, expectedQos,
                priorityLevelNumber, qosProperties);
    }

    public MALEndpoint getEndpoint(URI uri) throws java.lang.IllegalArgumentException, MALException {
        return delegate.getEndpoint(uri);
    }

    public MALEndpoint getEndpoint(String localName) throws java.lang.IllegalArgumentException, MALException {
        return delegate.getEndpoint(localName);
    }
}
