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
import org.ccsds.moims.mo.mc.aggregation.AggregationHelper;
import org.ccsds.moims.mo.mc.aggregation.consumer.AggregationStub;
import org.ccsds.moims.mo.mc.alert.AlertHelper;
import org.ccsds.moims.mo.mc.alert.consumer.AlertStub;

/**
 * The Alert service implementation, consumer side.
 */
public class AlertConsumerServiceImpl extends ConsumerServiceImpl {

    private AlertStub alertService = null;

    /**
     * Constructor.
     *
     * @param connectionDetails The connection URIs to the provider.
     * @throws MALException If the service could not be started.
     */
    public AlertConsumerServiceImpl(final SingleConnectionDetails connectionDetails) throws MALException {
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
    public AlertConsumerServiceImpl(final SingleConnectionDetails connectionDetails,
            final Blob authenticationId, final String localNamePrefix) throws MALException {
        this.connectionDetails = connectionDetails;

        // Close previous connection
        if (tmConsumer != null) {
            try {
                tmConsumer.close();
            } catch (MALException ex) {
                Logger.getLogger(AlertConsumerServiceImpl.class.getName()).log(
                        Level.SEVERE, "The previous connection could not be closed!", ex);
            }
        }

        tmConsumer = connection.startService(connectionDetails,
                AlertHelper.ALERT_SERVICE, authenticationId, localNamePrefix);

        this.alertService = new AlertStub(tmConsumer);
    }

    @Override
    public Object generateServiceStub(MALConsumer tmConsumer) {
        return new AlertStub(tmConsumer);
    }

    @Override
    public Object getStub() {
        return this.getAlertStub();
    }

    /**
     * Returns the service stub.
     *
     * @return The service stub.
     */
    public AlertStub getAlertStub() {
        return this.alertService;
    }
}
