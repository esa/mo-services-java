package org.ccsds.moims.mo.mpd.productorderdelivery;

import java.util.ArrayList;
import java.util.Arrays;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mpd.MPDHelper;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;

/**
 * Helper class for ProductOrderDelivery service.
 */
public class ProductOrderDeliveryServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _PRODUCTORDERDELIVERY_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final UShort PRODUCTORDERDELIVERY_SERVICE_NUMBER = new UShort(_PRODUCTORDERDELIVERY_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier PRODUCTORDERDELIVERY_SERVICE_NAME = new Identifier("ProductOrderDelivery");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            9, 1, PRODUCTORDERDELIVERY_SERVICE_NUMBER);

    /**
     * Operation number literal for operation NOTIFYPRODUCTDELIVERY.
     */
    public static final int _NOTIFYPRODUCTDELIVERY_OP_NUMBER = 1;

    /**
     * Operation number instance for operation NOTIFYPRODUCTDELIVERY.
     */
    private static final UShort NOTIFYPRODUCTDELIVERY_OP_NUMBER = new UShort(_NOTIFYPRODUCTDELIVERY_OP_NUMBER);

    /**
     * Operation instance for operation NOTIFYPRODUCTDELIVERY.
     */
    public static final MALPubSubOperation NOTIFYPRODUCTDELIVERY_OP = new MALPubSubOperation(SERVICE_KEY, 
            NOTIFYPRODUCTDELIVERY_OP_NUMBER, 
            new Identifier("notifyProductDelivery"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("metadata", false, ProductMetadata.SHORT_FORM, "The metadata of the mission data product."),
                new OperationField("filename", false, Attribute.STRING_SHORT_FORM, "The filename of the mission data product."),
                new OperationField("deliveredTo", false, Attribute.URI_SHORT_FORM, "The location's URI where the mission data product was delivered."),
                new OperationField("success", false, Attribute.BOOLEAN_SHORT_FORM, "The status indicating the successful delivery of the mission data product.")}, 
            "The notifyProductDelivery operation publishes a notification whenever a product has been delivered by file transfer in accordance with an existing standing order.");

    /**
     * Key names instance for NOTIFYPRODUCTDELIVERY operation of pubsub interaction
     * pattern.
     */
    private static final Identifier [] _NOTIFYPRODUCTDELIVERY_OP_KEY_NAMES = {new Identifier("user"),
            new Identifier("orderID")};

    /**
     * Key names instance for NOTIFYPRODUCTDELIVERY operation of pubsub interaction
     * pattern.
     */
    private static final IdentifierList NOTIFYPRODUCTDELIVERY_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_NOTIFYPRODUCTDELIVERY_OP_KEY_NAMES)));

    /**
     * Operation number literal for operation DELIVERPRODUCTS.
     */
    public static final int _DELIVERPRODUCTS_OP_NUMBER = 2;

    /**
     * Operation number instance for operation DELIVERPRODUCTS.
     */
    private static final UShort DELIVERPRODUCTS_OP_NUMBER = new UShort(_DELIVERPRODUCTS_OP_NUMBER);

    /**
     * Operation instance for operation DELIVERPRODUCTS.
     */
    public static final MALPubSubOperation DELIVERPRODUCTS_OP = new MALPubSubOperation(SERVICE_KEY, 
            DELIVERPRODUCTS_OP_NUMBER, 
            new Identifier("deliverProducts"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("product", false, Product.SHORT_FORM, "The mission data product.")}, 
            "The deliverProducts operation publishes mission data products directly via the service interface for an existing standing order.");

    /**
     * Key names instance for DELIVERPRODUCTS operation of pubsub interaction
     * pattern.
     */
    private static final Identifier [] _DELIVERPRODUCTS_OP_KEY_NAMES = {new Identifier("user"),
            new Identifier("orderID")};

    /**
     * Key names instance for DELIVERPRODUCTS operation of pubsub interaction
     * pattern.
     */
    private static final IdentifierList DELIVERPRODUCTS_OP_KEY_NAMES = new IdentifierList(new ArrayList<>(Arrays.asList(_DELIVERPRODUCTS_OP_KEY_NAMES)));

    /**
     * Area elements.
     */
    public static final Element[] PRODUCTORDERDELIVERY_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{NOTIFYPRODUCTDELIVERY_OP,
        DELIVERPRODUCTS_OP};

    /**
     * Creates an instance of the ProductOrderDelivery ServiceInfo.
     * 
     */
    public ProductOrderDeliveryServiceInfo() {
        super(SERVICE_KEY, PRODUCTORDERDELIVERY_SERVICE_NAME, PRODUCTORDERDELIVERY_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MPDHelper.MPD_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        MOErrorException areaError = MPDHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
