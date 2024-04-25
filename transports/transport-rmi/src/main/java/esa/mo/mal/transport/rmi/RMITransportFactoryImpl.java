/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO RMI Transport
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
package esa.mo.mal.transport.rmi;

import esa.mo.mal.transport.gen.Transport;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.transport.MALTransport;
import org.ccsds.moims.mo.mal.transport.MALTransportFactory;

/**
 * Instance of the transport factory for the RMI protocol.
 */
public class RMITransportFactoryImpl extends MALTransportFactory {

    private static final Object MUTEX = new Object();
    private Transport transport = null;

    /**
     * Constructor.
     *
     * @param protocol The protocol string.
     */
    public RMITransportFactoryImpl(final String protocol) {
        super(protocol);
    }

    @Override
    public MALTransport createTransport(final MALContext malContext,
            final Map properties) throws MALException {
        synchronized (MUTEX) {
            if (null == transport) {
                transport = new RMITransport(getProtocol(), this, properties);
                transport.init();
            }

            return transport;
        }
    }
}
