/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
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
package esa.mo.services.mpd.provider;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalHelper;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo;
import org.ccsds.moims.mo.mpd.productretrieval.provider.GetProductFilesInteraction;
import org.ccsds.moims.mo.mpd.productretrieval.provider.GetProductsInteraction;
import org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductSummaryList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * The Product Retrieval service implementation, provider side.
 */
public class ProductRetrievalProviderServiceImpl extends ProductRetrievalInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(ProductRetrievalProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();

    private boolean running = false;
    private MALProvider productRetrievalServiceProvider;

    /**
     * Initializes the service.
     *
     * @throws MALException On initialisation error.
     */
    public synchronized void init() throws MALException {
        // shut down old service transport
        if (null != productRetrievalServiceProvider) {
            connection.closeAll();
        }

        productRetrievalServiceProvider = connection.startService(
                ProductRetrievalServiceInfo.PRODUCTRETRIEVAL_SERVICE_NAME.toString(),
                ProductRetrievalHelper.PRODUCTRETRIEVAL_SERVICE, false, this);

        running = true;
        LOGGER.info("Product Retrieval service READY");
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            if (null != productRetrievalServiceProvider) {
                productRetrievalServiceProvider.close();
            }

            connection.closeAll();
            running = false;
        } catch (MALException ex) {
            LOGGER.log(Level.WARNING, "Exception during close down of the provider {0}", ex);
        }
    }

    public ConnectionProvider getConnection() {
        return this.connection;
    }

    @Override
    public ProductSummaryList listProducts(ProductFilter productFilter, TimeWindow creationDate, TimeWindow timeWindow, MALInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void getProducts(ObjectRefList productRefs, GetProductsInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void getProductFiles(ObjectRefList productRefs, URI deliverTo, GetProductFilesInteraction interaction) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
