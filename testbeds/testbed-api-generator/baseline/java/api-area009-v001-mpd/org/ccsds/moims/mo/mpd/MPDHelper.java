package org.ccsds.moims.mo.mpd;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementHelper;
import org.ccsds.moims.mo.mpd.productorderdelivery.ProductOrderDeliveryHelper;
import org.ccsds.moims.mo.mpd.productretrieval.ProductRetrievalHelper;

/**
 * Helper class for MPD area.
 */
public class MPDHelper {

    /**
     * Area number literal.
     */
    public static final int _MPD_AREA_NUMBER = 9;

    /**
     * Area number instance.
     */
    public static final UShort MPD_AREA_NUMBER = new UShort(_MPD_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier MPD_AREA_NAME = new Identifier("MPD");

    /**
     * Area version literal.
     */
    public static final short _MPD_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet MPD_AREA_VERSION = new UOctet(_MPD_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] MPD_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] MPD_AREA_SERVICES = {
        ProductRetrievalHelper.PRODUCTRETRIEVAL_SERVICE,
        OrderManagementHelper.ORDERMANAGEMENT_SERVICE,
        ProductOrderDeliveryHelper.PRODUCTORDERDELIVERY_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea MPD_AREA = new MALArea(MPD_AREA_NUMBER, MPD_AREA_NAME, MPD_AREA_VERSION, MPD_AREA_ELEMENTS, MPD_AREA_SERVICES, new MPDElementFactory());

    /**
     * Error literal for error INVALID.
     */
    public static final long _INVALID_ERROR_NUMBER = 1;

    /**
     * Error instance for error INVALID.
     */
    public static final UInteger INVALID_ERROR_NUMBER = new UInteger(_INVALID_ERROR_NUMBER);

    /**
     * Error literal for error DELIVERY_FAILED.
     */
    public static final long _DELIVERY_FAILED_ERROR_NUMBER = 2;

    /**
     * Error instance for error DELIVERY_FAILED.
     */
    public static final UInteger DELIVERY_FAILED_ERROR_NUMBER = new UInteger(_DELIVERY_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error ORDER_FAILED.
     */
    public static final long _ORDER_FAILED_ERROR_NUMBER = 3;

    /**
     * Error instance for error ORDER_FAILED.
     */
    public static final UInteger ORDER_FAILED_ERROR_NUMBER = new UInteger(_ORDER_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error UNKNOWN.
     */
    public static final long _UNKNOWN_ERROR_NUMBER = 4;

    /**
     * Error instance for error UNKNOWN.
     */
    public static final UInteger UNKNOWN_ERROR_NUMBER = new UInteger(_UNKNOWN_ERROR_NUMBER);

    /**
     * Error literal for error TOO_MANY.
     */
    public static final long _TOO_MANY_ERROR_NUMBER = 5;

    /**
     * Error instance for error TOO_MANY.
     */
    public static final UInteger TOO_MANY_ERROR_NUMBER = new UInteger(_TOO_MANY_ERROR_NUMBER);

    /**
     * Returns the exception of the error of this area with the given number.
     * 
     * @param errorNumber The number of the error.
     * @param extraInfo The extra information of the error.
     * @return the exception, or null if the area declares no error with that number
     */
    public static MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new InvalidException(extraInfo);
            case 2:
                return new DeliveryFailedException(extraInfo);
            case 3:
                return new OrderFailedException(extraInfo);
            case 4:
                return new UnknownException(extraInfo);
            case 5:
                return new TooManyException(extraInfo);
        }
        return null;
    }

    private MPDHelper() {
        // Utility class; not meant to be instantiated.
    }

}
