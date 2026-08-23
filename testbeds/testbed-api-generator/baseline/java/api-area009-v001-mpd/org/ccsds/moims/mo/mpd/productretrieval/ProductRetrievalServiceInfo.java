package org.ccsds.moims.mo.mpd.productretrieval;

/**
 * Helper class for ProductRetrieval service.
 */
public class ProductRetrievalServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PRODUCTRETRIEVAL_SERVICE_NUMBER = 1;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PRODUCTRETRIEVAL_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PRODUCTRETRIEVAL_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PRODUCTRETRIEVAL_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ProductRetrieval");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            9, 1, PRODUCTRETRIEVAL_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LISTPRODUCTS.
     */
    public static final int _LISTPRODUCTS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LISTPRODUCTS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTPRODUCTS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTPRODUCTS_OP_NUMBER);

    /**
     * Operation instance for operation LISTPRODUCTS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LISTPRODUCTS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LISTPRODUCTS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listProducts"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("productFilter", false, org.ccsds.moims.mo.mpd.structures.ProductFilter.SHORT_FORM, "The product filter used to refine the selection of products."),
                new org.ccsds.moims.mo.mal.OperationField("creationDate", true, org.ccsds.moims.mo.mpd.structures.TimeWindow.SHORT_FORM, "The time window used to filter products based on their creation date."),
                new org.ccsds.moims.mo.mal.OperationField("contentDate", true, org.ccsds.moims.mo.mpd.structures.TimeWindow.SHORT_FORM, "The time window used to filter products based on their content creation period.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("metadatas", false, org.ccsds.moims.mo.mpd.structures.ProductMetadataList.SHORT_FORM, "The list of metadata entries that match the selected filters.")}, 
            "The listProducts operation lists the available products for a selected product filter and optionally also for a selected creation date and for a selected content date time window.");

    /**
     * Operation number literal for operation GETPRODUCTS.
     */
    public static final int _GETPRODUCTS_OP_NUMBER = 2;

    /**
     * Operation number instance for operation GETPRODUCTS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort GETPRODUCTS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GETPRODUCTS_OP_NUMBER);

    /**
     * Operation instance for operation GETPRODUCTS.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation GETPRODUCTS_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            GETPRODUCTS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("getProducts"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("productRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "The references to the products to be retrieved.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("product", false, org.ccsds.moims.mo.mpd.structures.Product.SHORT_FORM, "The selected mission data product(s).")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The getProducts operation retrieves the selected mission data products from the provider.");

    /**
     * Operation number literal for operation DELIVERPRODUCTFILES.
     */
    public static final int _DELIVERPRODUCTFILES_OP_NUMBER = 3;

    /**
     * Operation number instance for operation DELIVERPRODUCTFILES.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DELIVERPRODUCTFILES_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELIVERPRODUCTFILES_OP_NUMBER);

    /**
     * Operation instance for operation DELIVERPRODUCTFILES.
     */
    public static final org.ccsds.moims.mo.mal.MALProgressOperation DELIVERPRODUCTFILES_OP = new org.ccsds.moims.mo.mal.MALProgressOperation(SERVICE_KEY, 
            DELIVERPRODUCTFILES_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deliverProductFiles"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("productRefs", false, org.ccsds.moims.mo.mal.structures.ObjectRefList.SHORT_FORM, "The references to the products to be delivered."),
                new org.ccsds.moims.mo.mal.OperationField("deliverTo", false, org.ccsds.moims.mo.mal.structures.Attribute.URI_SHORT_FORM, "The location's URI where the mission data product must be delivered.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("metadata", false, org.ccsds.moims.mo.mpd.structures.ProductMetadata.SHORT_FORM, "The metadata of the transferred mission data product(s)."),
                new org.ccsds.moims.mo.mal.OperationField("filename", false, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "The filename of the transferred mission data product(s)."),
                new org.ccsds.moims.mo.mal.OperationField("success", false, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The completion status of the remote file transfer.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The deliverProductFiles operation allows consumers to instruct the provider to initiate a remote file transfer delivery of the selected mission data products to a specified target.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PRODUCTRETRIEVAL_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{LISTPRODUCTS_OP,
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
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mpd.MPDHelper.MPD_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.mpd.InvalidException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.mpd.DeliveryFailedException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.mpd.OrderFailedException(extraInfo);
            case 4:
                return new org.ccsds.moims.mo.mpd.UnknownException(extraInfo);
            case 5:
                return new org.ccsds.moims.mo.mpd.TooManyException(extraInfo);
        }
        return null;
    }

}
