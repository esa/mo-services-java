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

import esa.mo.services.mpd.util.FileTransferManager;
import esa.mo.services.mpd.util.HelperMPD;
import esa.mo.services.mpd.util.HelperProductFilters;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mpd.DeliveryFailedException;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.TooManyException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.backends.ProductRetrievalBackend;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalHelper;
import org.ccsds.moims.mo.mpd.productretrieval.provider.GetProductFilesInteraction;
import org.ccsds.moims.mo.mpd.productretrieval.provider.GetProductsInteraction;
import org.ccsds.moims.mo.mpd.productretrieval.provider.ProductRetrievalInheritanceSkeleton;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductList;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * The Product Retrieval service implementation, provider side.
 */
public class ProductRetrievalProviderServiceImpl extends ProductRetrievalInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(ProductRetrievalProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();
    private ProductRetrievalBackend backend;
    private MALProvider service;
    private boolean running = false;

    /**
     * Initializes the service.
     *
     * @param backend The backend of this service.
     * @throws MALException On initialisation error.
     */
    public synchronized void init(ProductRetrievalBackend backend) throws MALException {
        if (backend == null) {
            throw new IllegalArgumentException("The backend cannot be null!");
        }

        // shut down old service transport
        if (service != null) {
            connection.closeAll();
        }

        this.backend = backend;
        service = connection.startService(ProductRetrievalHelper.PRODUCTRETRIEVAL_SERVICE, false, this);
        running = true;
        LOGGER.info("Product Retrieval service READY");
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            if (service != null) {
                service.close();
            }

            connection.closeAll();
            running = false;
        } catch (MALException ex) {
            LOGGER.log(Level.WARNING, "Exception during close down of the provider {0}", ex);
        }
    }

    @Override
    public ConnectionProvider getConnection() {
        return this.connection;
    }

    @Override
    public ProductMetadataList listProducts(ProductFilter productFilter, TimeWindow creationDate,
            TimeWindow contentDate, MALInteraction interaction) throws MALInteractionException, MALException {
        // Validate the inputs
        if (productFilter == null) {
            throw new MALException("The productFilter cannot be null!");
        }

        // Validate the creationDate
        if (!HelperMPD.isTimeWindowValid(creationDate)) {
            String text = "The end date of the creationDate period is less than the start date!";
            throw new MALInteractionException(new InvalidException(text));
        }

        // Validate the contentDate
        if (!HelperMPD.isTimeWindowValid(contentDate)) {
            String text = "The end date of the contentDate period is less than the start date!";
            throw new MALInteractionException(new InvalidException(text));
        }

        ProductMetadataList allProducts = backend.getMetadataFromAllProducts();
        ProductMetadataList out = new ProductMetadataList();

        for (ProductMetadata productMetadata : allProducts) {
            try {
                // If the filters do not match, then skip it!
                if (!HelperProductFilters.productMetadataMatchesFilter(productMetadata, productFilter)) {
                    continue;
                }
            } catch (IOException ex) {
                // Jump over if the something goes wrong while attempting to match
                Logger.getLogger(ProductRetrievalProviderServiceImpl.class.getName()).log(Level.SEVERE,
                        "The filter could not be applied to the product metadata!", ex);
                continue;
            }

            if (creationDate != null) {
                long productCreationDate = productMetadata.getCreationDate().getValue();

                // If it is outside the range, then skip it!
                if (productCreationDate < creationDate.getStart().getValue()
                        || productCreationDate > creationDate.getEnd().getValue()) {
                    continue;
                }
            }

            if (contentDate != null) {
                if (productMetadata.getContentDate().getStart().getValue() > contentDate.getEnd().getValue()) {
                    continue;
                }
                if (productMetadata.getContentDate().getEnd().getValue() < contentDate.getStart().getValue()) {
                    continue;
                }
            }

            out.add(productMetadata);
        }

        if (out.size() > backend.getMaximumNumberOfResults()) {
            String text = "There are too many entries (" + out.size() + "). ";
            text += "The mamimux number allows is " + backend.getMaximumNumberOfResults() + ". ";
            text += "Please refine your search criteria, in order to decrease the amount of entries.";
            throw new MALInteractionException(new TooManyException(text));
        }

        return out;
    }

    @Override
    public void getProducts(ObjectRefList productRefs, GetProductsInteraction interaction)
            throws MALInteractionException, MALException {
        if (productRefs == null) {
            throw new MALException("The productRefs cannot be null!");
        }

        ProductList matchedProducts = new ProductList();
        IntegerList productsNotFoundIndex = new IntegerList();

        for (int i = 0; i < productRefs.size(); i++) {
            ObjectRef productRef = productRefs.get(i);
            ProductMetadata metadata = backend.getMetadata(productRef);

            if (metadata == null) {
                productsNotFoundIndex.add(i);
            }
        }

        // MO Errors
        if (!productsNotFoundIndex.isEmpty()) {
            throw new MALInteractionException(new UnknownException(productsNotFoundIndex));
        }

        for (int i = 0; i < productRefs.size(); i++) {
            ObjectRef productRef = productRefs.get(i);
            Product product = backend.getProduct(productRef, true);

            if (product != null) {
                matchedProducts.add(product);
            } else {
                // This should never happen because the code above already check if it exists!
                throw new MALInteractionException(new UnknownException(productsNotFoundIndex));
            }
        }

        interaction.sendAcknowledgement();

        for (Product product : matchedProducts) {
            interaction.sendUpdate(product);
        }

        interaction.sendResponse();
    }

    @Override
    public void getProductFiles(ObjectRefList productRefs, URI deliverTo,
            GetProductFilesInteraction interaction) throws MALInteractionException, MALException {
        if (productRefs == null) {
            throw new MALException("The productRefs cannot be null!");
        }

        if (deliverTo == null) {
            throw new MALException("The deliverTo cannot be null!");
        }

        IntegerList productsNotFoundIndex = new IntegerList();

        for (int i = 0; i < productRefs.size(); i++) {
            ObjectRef productRef = productRefs.get(i);
            ProductMetadata metadata = backend.getMetadata(productRef);

            if (metadata == null) {
                productsNotFoundIndex.add(i);
            }
        }

        // MO Errors
        if (!productsNotFoundIndex.isEmpty()) {
            throw new MALInteractionException(new UnknownException(productsNotFoundIndex));
        }

        FileTransferManager fileTransfer = new FileTransferManager(deliverTo);
        try {
            fileTransfer.connect();

            interaction.sendAcknowledgement();
            ProductList products = new ProductList();
            ProductMetadataList metadatas = new ProductMetadataList();

            for (ObjectRef productRef : productRefs) {
                ProductMetadata metadata = backend.getMetadata(productRef);

                if (metadata != null) {
                    Product product = backend.getProduct(productRef, true);
                    products.add(product);
                    metadatas.add(backend.getMetadata(productRef));
                }
            }

            // Execute delivery...
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                String filename = product.getObjectRef().getKey().getValue();

                boolean success = fileTransfer.executeTransfer(product, filename);
                ProductMetadata productSummary = metadatas.get(i);
                interaction.sendUpdate(productSummary, filename, success);
            }
        } catch (IOException ex) {
            String message = "The provider could not perform the file delivery!";
            throw new MALInteractionException(new DeliveryFailedException(message));
        }

        interaction.sendResponse();
    }

}
