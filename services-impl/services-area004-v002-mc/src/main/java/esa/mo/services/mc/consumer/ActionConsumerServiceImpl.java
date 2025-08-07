/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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
package esa.mo.services.mc.consumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.misc.ConsumerServiceImpl;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mc.action.ActionHelper;
import org.ccsds.moims.mo.mc.action.consumer.ActionStub;

/**
 * The Action service implementation, consumer side.
 */
public class ActionConsumerServiceImpl extends ConsumerServiceImpl {

    private ActionStub actionService = null;

    /**
     * Constructor.
     *
     * @param connectionDetails The connection URIs to the provider.
     * @throws MALException If the service could not be started.
     */
    public ActionConsumerServiceImpl(final SingleConnectionDetails connectionDetails) throws MALException {
        this(connectionDetails, null, null);
    }

    /**
     * Constructor.
     *
     * @param connectionDetails The connection URIs to the provider.
     * @param authenticationId The authenticationId token.
     * @param localNamePrefix The local name prefix.
     * @throws MALException If the service could not be started.
     */
    public ActionConsumerServiceImpl(final SingleConnectionDetails connectionDetails,
            final Blob authenticationId, final String localNamePrefix) throws MALException {
        this.connectionDetails = connectionDetails;

        // Close previous connection
        if (tmConsumer != null) {
            try {
                tmConsumer.close();
            } catch (MALException ex) {
                Logger.getLogger(ActionConsumerServiceImpl.class.getName()).log(
                        Level.SEVERE, "The previous connection could not be closed!", ex);
            }
        }

        tmConsumer = connection.startService(connectionDetails,
                ActionHelper.ACTION_SERVICE, authenticationId, localNamePrefix);

        this.actionService = new ActionStub(tmConsumer);
    }

    @Override
    public Object generateServiceStub(MALConsumer tmConsumer) {
        return new ActionStub(tmConsumer);
    }

    @Override
    public Object getStub() {
        return this.getActionStub();
    }

    /**
     * Returns the service stub.
     *
     * @return The service stub.
     */
    public ActionStub getActionStub() {
        return this.actionService;
    }
}
