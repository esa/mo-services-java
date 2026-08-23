package org.ccsds.moims.mo.mpd.productorderdelivery;

/**
 * Helper class for ProductOrderDelivery service.
 */
public class ProductOrderDeliveryServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PRODUCTORDERDELIVERY_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort PRODUCTORDERDELIVERY_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_PRODUCTORDERDELIVERY_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier PRODUCTORDERDELIVERY_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ProductOrderDelivery");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            9, 1, PRODUCTORDERDELIVERY_SERVICE_NUMBER);

    /**
     * Operation number literal for operation NOTIFYPRODUCTDELIVERY.
     */
    public static final int _NOTIFYPRODUCTDELIVERY_OP_NUMBER = 1;

    /**
     * Operation number instance for operation NOTIFYPRODUCTDELIVERY.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort NOTIFYPRODUCTDELIVERY_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_NOTIFYPRODUCTDELIVERY_OP_NUMBER);

    /**
     * Operation instance for operation NOTIFYPRODUCTDELIVERY.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation NOTIFYPRODUCTDELIVERY_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            NOTIFYPRODUCTDELIVERY_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("notifyProductDelivery"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("metadata", false, org.ccsds.moims.mo.mpd.structures.ProductMetadata.SHORT_FORM, "The metadata of the mission data product."),
                new org.ccsds.moims.mo.mal.OperationField("filename", false, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "The filename of the mission data product."),
                new org.ccsds.moims.mo.mal.OperationField("deliveredTo", false, org.ccsds.moims.mo.mal.structures.Attribute.URI_SHORT_FORM, "The location's URI where the mission data product was delivered."),
                new org.ccsds.moims.mo.mal.OperationField("success", false, org.ccsds.moims.mo.mal.structures.Attribute.BOOLEAN_SHORT_FORM, "The status indicating the successful delivery of the mission data product.")}, 
            "The notifyProductDelivery operation publishes a notification whenever a product has been delivered by file transfer in accordance with an existing standing order.");

    /**
     * Key names instance for NOTIFYPRODUCTDELIVERY operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _NOTIFYPRODUCTDELIVERY_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("user"),
            new org.ccsds.moims.mo.mal.structures.Identifier("orderID")};

    /**
     * Key names instance for NOTIFYPRODUCTDELIVERY operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList NOTIFYPRODUCTDELIVERY_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_NOTIFYPRODUCTDELIVERY_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation DELIVERPRODUCTS.
     */
    public static final int _DELIVERPRODUCTS_OP_NUMBER = 2;

    /**
     * Operation number instance for operation DELIVERPRODUCTS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort DELIVERPRODUCTS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DELIVERPRODUCTS_OP_NUMBER);

    /**
     * Operation instance for operation DELIVERPRODUCTS.
     */
    public static final org.ccsds.moims.mo.mal.MALPubSubOperation DELIVERPRODUCTS_OP = new org.ccsds.moims.mo.mal.MALPubSubOperation(SERVICE_KEY, 
            DELIVERPRODUCTS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("deliverProducts"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("product", false, org.ccsds.moims.mo.mpd.structures.Product.SHORT_FORM, "The mission data product.")}, 
            "The deliverProducts operation publishes mission data products directly via the service interface for an existing standing order.");

    /**
     * Key names instance for DELIVERPRODUCTS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.Identifier [] _DELIVERPRODUCTS_OP_KEY_NAMES = {new org.ccsds.moims.mo.mal.structures.Identifier("user"),
            new org.ccsds.moims.mo.mal.structures.Identifier("orderID")};

    /**
     * Key names instance for DELIVERPRODUCTS operation of pubsub interaction
     * pattern.
     */
    private static final org.ccsds.moims.mo.mal.structures.IdentifierList DELIVERPRODUCTS_OP_KEY_NAMES = new org.ccsds.moims.mo.mal.structures.IdentifierList(new java.util.ArrayList<>(java.util.Arrays.asList(_DELIVERPRODUCTS_OP_KEY_NAMES)));

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] PRODUCTORDERDELIVERY_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{NOTIFYPRODUCTDELIVERY_OP,
        DELIVERPRODUCTS_OP};

    /**
     * Creates an instance of the ProductOrderDelivery ServiceInfo.
     * 
     */
    public ProductOrderDeliveryServiceInfo() {
        super(SERVICE_KEY, PRODUCTORDERDELIVERY_SERVICE_NAME, PRODUCTORDERDELIVERY_SERVICE_ELEMENTS, OPERATIONS);
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
