package org.ccsds.moims.mo.mpd.productretrieval.provider;

import java.io.IOException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnsupportedOperationException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderSet;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalHelper;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalServiceInfo;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * Provider Inheritance skeleton for ProductRetrievalInheritanceSkeleton service.
 */
public abstract class ProductRetrievalInheritanceSkeleton implements MALInteractionHandler, ProductRetrievalSkeleton, ProductRetrievalHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(ProductRetrievalHelper.PRODUCTRETRIEVAL_SERVICE);

    /**
     * Returns the connection object for this provider.
     * 
     * @return the connection object for this provider
     * @throws IOException if the method was not implemented yet.
     */
    public ConnectionProvider getConnection() throws IOException {
        throw new IOException("This method needs to be overridden!");
    }

    @Override
    public void setSkeleton(ProductRetrievalSkeleton skeleton) {
        // Not used in the inheritance pattern (the skeleton is 'this');
    }

    @Override
    public void malInitialize(MALProvider provider) throws MALException {
        providerSet.addProvider(provider);
    }

    @Override
    public void malFinalize(MALProvider provider) throws MALException {
        providerSet.removeProvider(provider);
    }

    @Override
    public void handleSend(MALInteraction interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleSubmit(MALSubmit interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleRequest(MALRequest interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case ProductRetrievalServiceInfo._LISTPRODUCTS_OP_NUMBER:
            interaction.sendResponse(listProducts((ProductFilter) body.getBodyElement(0, new ProductFilter()),
                (TimeWindow) body.getBodyElement(1, new TimeWindow()),
                (TimeWindow) body.getBodyElement(2, new TimeWindow()),
                interaction));
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

    @Override
    public void handleInvoke(MALInvoke interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleProgress(MALProgress interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case ProductRetrievalServiceInfo._GETPRODUCTS_OP_NUMBER:
            getProducts((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                new GetProductsInteraction(interaction));
            break;
          case ProductRetrievalServiceInfo._DELIVERPRODUCTFILES_OP_NUMBER:
            deliverProductFiles((ObjectRefList) body.getBodyElement(0, new ObjectRefList()),
                (URI) body.getBodyElement(1, new URI()),
                new DeliverProductFilesInteraction(interaction));
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

}
