package org.ccsds.moims.mo.mal;

/**
 * Helper class for MAL area.
 */
public class MALHelper {

    /**
     * Area number literal.
     */
    public static final int _MAL_AREA_NUMBER = 1;

    /**
     * Area number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort MAL_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_MAL_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier MAL_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("MAL");

    /**
     * Area version literal.
     */
    public static final short _MAL_AREA_VERSION = 3;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet MAL_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_MAL_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] MAL_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] MAL_AREA_SERVICES = {};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea MAL_AREA = new org.ccsds.moims.mo.mal.MALArea(MAL_AREA_NUMBER, MAL_AREA_NAME, MAL_AREA_VERSION, MAL_AREA_ELEMENTS, MAL_AREA_SERVICES, new MALElementFactory());

    /**
     * Error literal for error DELIVERY_FAILED.
     */
    public static final long _DELIVERY_FAILED_ERROR_NUMBER = 65536;

    /**
     * Error instance for error DELIVERY_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DELIVERY_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DELIVERY_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DELIVERY_TIMEDOUT.
     */
    public static final long _DELIVERY_TIMEDOUT_ERROR_NUMBER = 65537;

    /**
     * Error instance for error DELIVERY_TIMEDOUT.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DELIVERY_TIMEDOUT_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DELIVERY_TIMEDOUT_ERROR_NUMBER);

    /**
     * Error literal for error DELIVERY_DELAYED.
     */
    public static final long _DELIVERY_DELAYED_ERROR_NUMBER = 65538;

    /**
     * Error instance for error DELIVERY_DELAYED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DELIVERY_DELAYED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DELIVERY_DELAYED_ERROR_NUMBER);

    /**
     * Error literal for error DESTINATION_UNKNOWN.
     */
    public static final long _DESTINATION_UNKNOWN_ERROR_NUMBER = 65539;

    /**
     * Error instance for error DESTINATION_UNKNOWN.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DESTINATION_UNKNOWN_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DESTINATION_UNKNOWN_ERROR_NUMBER);

    /**
     * Error literal for error DESTINATION_TRANSIENT.
     */
    public static final long _DESTINATION_TRANSIENT_ERROR_NUMBER = 65540;

    /**
     * Error instance for error DESTINATION_TRANSIENT.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DESTINATION_TRANSIENT_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DESTINATION_TRANSIENT_ERROR_NUMBER);

    /**
     * Error literal for error DESTINATION_LOST.
     */
    public static final long _DESTINATION_LOST_ERROR_NUMBER = 65541;

    /**
     * Error instance for error DESTINATION_LOST.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger DESTINATION_LOST_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_DESTINATION_LOST_ERROR_NUMBER);

    /**
     * Error literal for error AUTHENTICATION_FAILED.
     */
    public static final long _AUTHENTICATION_FAILED_ERROR_NUMBER = 65542;

    /**
     * Error instance for error AUTHENTICATION_FAILED.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger AUTHENTICATION_FAILED_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_AUTHENTICATION_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error AUTHORISATION_FAIL.
     */
    public static final long _AUTHORISATION_FAIL_ERROR_NUMBER = 65543;

    /**
     * Error instance for error AUTHORISATION_FAIL.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger AUTHORISATION_FAIL_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_AUTHORISATION_FAIL_ERROR_NUMBER);

    /**
     * Error literal for error ENCRYPTION_FAIL.
     */
    public static final long _ENCRYPTION_FAIL_ERROR_NUMBER = 65544;

    /**
     * Error instance for error ENCRYPTION_FAIL.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger ENCRYPTION_FAIL_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_ENCRYPTION_FAIL_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_AREA.
     */
    public static final long _UNSUPPORTED_AREA_ERROR_NUMBER = 65545;

    /**
     * Error instance for error UNSUPPORTED_AREA.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UNSUPPORTED_AREA_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UNSUPPORTED_AREA_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_AREA_VERSION.
     */
    public static final long _UNSUPPORTED_AREA_VERSION_ERROR_NUMBER = 65546;

    /**
     * Error instance for error UNSUPPORTED_AREA_VERSION.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UNSUPPORTED_AREA_VERSION_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UNSUPPORTED_AREA_VERSION_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_SERVICE.
     */
    public static final long _UNSUPPORTED_SERVICE_ERROR_NUMBER = 65547;

    /**
     * Error instance for error UNSUPPORTED_SERVICE.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UNSUPPORTED_SERVICE_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UNSUPPORTED_SERVICE_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_OPERATION.
     */
    public static final long _UNSUPPORTED_OPERATION_ERROR_NUMBER = 65548;

    /**
     * Error instance for error UNSUPPORTED_OPERATION.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UNSUPPORTED_OPERATION_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UNSUPPORTED_OPERATION_ERROR_NUMBER);

    /**
     * Error literal for error BAD_ENCODING.
     */
    public static final long _BAD_ENCODING_ERROR_NUMBER = 65549;

    /**
     * Error instance for error BAD_ENCODING.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger BAD_ENCODING_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_BAD_ENCODING_ERROR_NUMBER);

    /**
     * Error literal for error INTERNAL.
     */
    public static final long _INTERNAL_ERROR_NUMBER = 65550;

    /**
     * Error instance for error INTERNAL.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger INTERNAL_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_INTERNAL_ERROR_NUMBER);

    /**
     * Error literal for error UNKNOWN.
     */
    public static final long _UNKNOWN_ERROR_NUMBER = 65551;

    /**
     * Error instance for error UNKNOWN.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger UNKNOWN_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_UNKNOWN_ERROR_NUMBER);

    /**
     * Error literal for error INCORRECT_STATE.
     */
    public static final long _INCORRECT_STATE_ERROR_NUMBER = 65552;

    /**
     * Error instance for error INCORRECT_STATE.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger INCORRECT_STATE_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_INCORRECT_STATE_ERROR_NUMBER);

    /**
     * Error literal for error TOO_MANY.
     */
    public static final long _TOO_MANY_ERROR_NUMBER = 65553;

    /**
     * Error instance for error TOO_MANY.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger TOO_MANY_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_TOO_MANY_ERROR_NUMBER);

    /**
     * Error literal for error SHUTDOWN.
     */
    public static final long _SHUTDOWN_ERROR_NUMBER = 65554;

    /**
     * Error instance for error SHUTDOWN.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger SHUTDOWN_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_SHUTDOWN_ERROR_NUMBER);

    /**
     * Error literal for error TRANSACTION_TIMEOUT.
     */
    public static final long _TRANSACTION_TIMEOUT_ERROR_NUMBER = 65555;

    /**
     * Error instance for error TRANSACTION_TIMEOUT.
     */
    public static final org.ccsds.moims.mo.mal.structures.UInteger TRANSACTION_TIMEOUT_ERROR_NUMBER = new org.ccsds.moims.mo.mal.structures.UInteger(_TRANSACTION_TIMEOUT_ERROR_NUMBER);

    private MALHelper() {
        // Utility class; not meant to be instantiated.
    }

}
