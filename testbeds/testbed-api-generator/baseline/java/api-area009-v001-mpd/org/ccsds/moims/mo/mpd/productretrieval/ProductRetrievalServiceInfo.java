package org.ccsds.moims.mo.mpd.productretrieval;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALProgressOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mpd.DeliveryFailedException;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.MPDHelper;
import org.ccsds.moims.mo.mpd.TooManyException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.ProductMetadataList;
import org.ccsds.moims.mo.mpd.structures.TimeWindow;

/**
 * Helper class for ProductRetrieval service.
 */
public class ProductRetrievalServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PRODUCTRETRIEVAL_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final UShort PRODUCTRETRIEVAL_SERVICE_NUMBER = new UShort(_PRODUCTRETRIEVAL_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PRODUCTRETRIEVAL_SERVICE_NAME = new Identifier("ProductRetrieval");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            9, 1, PRODUCTRETRIEVAL_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LISTPRODUCTS.
     */
    public static final int _LISTPRODUCTS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LISTPRODUCTS.
     */
    private static final UShort LISTPRODUCTS_OP_NUMBER = new UShort(_LISTPRODUCTS_OP_NUMBER);

    /**
     * Operation instance for operation LISTPRODUCTS.
     */
    public static final MALRequestOperation LISTPRODUCTS_OP = new MALRequestOperation(SERVICE_KEY, 
            LISTPRODUCTS_OP_NUMBER, 
            new Identifier("listProducts"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("productFilter", false, ProductFilter.SHORT_FORM, "The product filter used to refine the selection of products."),
                new OperationField("creationDate", true, TimeWindow.SHORT_FORM, "The time window used to filter products based on their creation date."),
                new OperationField("contentDate", true, TimeWindow.SHORT_FORM, "The time window used to filter products based on their content creation period.")}, 
            new OperationField[] {
                new OperationField("metadatas", false, ProductMetadataList.SHORT_FORM, "The list of metadata entries that match the selected filters.")}, 
            "The listProducts operation lists the available products for a selected product filter and optionally also for a selected creation date and for a selected content date time window.");

    /**
     * Operation number literal for operation GETPRODUCTS.
     */
    public static final int _GETPRODUCTS_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETPRODUCTS.
     */
    private static final UShort GETPRODUCTS_OP_NUMBER = new UShort(_GETPRODUCTS_OP_NUMBER);

    /**
     * Operation instance for operation GETPRODUCTS.
     */
    public static final MALProgressOperation GETPRODUCTS_OP = new MALProgressOperation(SERVICE_KEY, 
            GETPRODUCTS_OP_NUMBER, 
            new Identifier("getProducts"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("productRefs", false, ObjectRefList.SHORT_FORM, "The references to the products to be retrieved.")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("product", false, Product.SHORT_FORM, "The selected mission data product(s).")}, 
            new OperationField[] {}, 
            "The getProducts operation retrieves the selected mission data products from the provider.");

    /**
     * Operation number literal for operation DELIVERPRODUCTFILES.
     */
    public static final int _DELIVERPRODUCTFILES_OP_NUMBER = 3;

    /**
     * Operation number instance for operation DELIVERPRODUCTFILES.
     */
    private static final UShort DELIVERPRODUCTFILES_OP_NUMBER = new UShort(_DELIVERPRODUCTFILES_OP_NUMBER);

    /**
     * Operation instance for operation DELIVERPRODUCTFILES.
     */
    public static final MALProgressOperation DELIVERPRODUCTFILES_OP = new MALProgressOperation(SERVICE_KEY, 
            DELIVERPRODUCTFILES_OP_NUMBER, 
            new Identifier("deliverProductFiles"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("productRefs", false, ObjectRefList.SHORT_FORM, "The references to the products to be delivered."),
                new OperationField("deliverTo", false, Attribute.URI_SHORT_FORM, "The location's URI where the mission data product must be delivered.")}, 
            new OperationField[] {}, 
            new OperationField[] {
                new OperationField("metadata", false, ProductMetadata.SHORT_FORM, "The metadata of the transferred mission data product(s)."),
                new OperationField("filename", false, Attribute.STRING_SHORT_FORM, "The filename of the transferred mission data product(s)."),
                new OperationField("success", false, Attribute.BOOLEAN_SHORT_FORM, "The completion status of the remote file transfer.")}, 
            new OperationField[] {}, 
            "The deliverProductFiles operation allows consumers to instruct the provider to initiate a remote file transfer delivery of the selected mission data products to a specified target.");

    /**
     * Area elements.
     */
    public static final Element[] PRODUCTRETRIEVAL_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{LISTPRODUCTS_OP,
        GETPRODUCTS_OP,
        DELIVERPRODUCTFILES_OP};

    /**
     * Creates an instance of the ProductRetrieval ServiceInfo.
     * 
     */
    public ProductRetrievalServiceInfo() {
        super(SERVICE_KEY, PRODUCTRETRIEVAL_SERVICE_NAME, PRODUCTRETRIEVAL_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MPDHelper.MPD_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 1:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 5:
                        return new TooManyException(extraInfo);
                }
                break;
            case 2:
                switch (errorNumber) {
                    case 4:
                        return new UnknownException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 4:
                        return new UnknownException(extraInfo);
                    case 2:
                        return new DeliveryFailedException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MPDHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
