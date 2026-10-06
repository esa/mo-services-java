package org.ccsds.moims.mo.mal;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

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
    public static final UShort MAL_AREA_NUMBER = new UShort(_MAL_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier MAL_AREA_NAME = new Identifier("MAL");

    /**
     * Area version literal.
     */
    public static final short _MAL_AREA_VERSION = 3;

    /**
     * Area version instance.
     */
    public static final UOctet MAL_AREA_VERSION = new UOctet(_MAL_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] MAL_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] MAL_AREA_SERVICES = {};

    /**
     * Area singleton instance.
     */
    public static final MALArea MAL_AREA = new MALArea(MAL_AREA_NUMBER, MAL_AREA_NAME, MAL_AREA_VERSION, MAL_AREA_ELEMENTS, MAL_AREA_SERVICES, new MALElementFactory());

    /**
     * Error literal for error DELIVERY_FAILED.
     */
    public static final long _DELIVERY_FAILED_ERROR_NUMBER = 65536;

    /**
     * Error instance for error DELIVERY_FAILED.
     */
    public static final UInteger DELIVERY_FAILED_ERROR_NUMBER = new UInteger(_DELIVERY_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error DELIVERY_TIMEDOUT.
     */
    public static final long _DELIVERY_TIMEDOUT_ERROR_NUMBER = 65537;

    /**
     * Error instance for error DELIVERY_TIMEDOUT.
     */
    public static final UInteger DELIVERY_TIMEDOUT_ERROR_NUMBER = new UInteger(_DELIVERY_TIMEDOUT_ERROR_NUMBER);

    /**
     * Error literal for error DELIVERY_DELAYED.
     */
    public static final long _DELIVERY_DELAYED_ERROR_NUMBER = 65538;

    /**
     * Error instance for error DELIVERY_DELAYED.
     */
    public static final UInteger DELIVERY_DELAYED_ERROR_NUMBER = new UInteger(_DELIVERY_DELAYED_ERROR_NUMBER);

    /**
     * Error literal for error DESTINATION_UNKNOWN.
     */
    public static final long _DESTINATION_UNKNOWN_ERROR_NUMBER = 65539;

    /**
     * Error instance for error DESTINATION_UNKNOWN.
     */
    public static final UInteger DESTINATION_UNKNOWN_ERROR_NUMBER = new UInteger(_DESTINATION_UNKNOWN_ERROR_NUMBER);

    /**
     * Error literal for error DESTINATION_TRANSIENT.
     */
    public static final long _DESTINATION_TRANSIENT_ERROR_NUMBER = 65540;

    /**
     * Error instance for error DESTINATION_TRANSIENT.
     */
    public static final UInteger DESTINATION_TRANSIENT_ERROR_NUMBER = new UInteger(_DESTINATION_TRANSIENT_ERROR_NUMBER);

    /**
     * Error literal for error DESTINATION_LOST.
     */
    public static final long _DESTINATION_LOST_ERROR_NUMBER = 65541;

    /**
     * Error instance for error DESTINATION_LOST.
     */
    public static final UInteger DESTINATION_LOST_ERROR_NUMBER = new UInteger(_DESTINATION_LOST_ERROR_NUMBER);

    /**
     * Error literal for error AUTHENTICATION_FAILED.
     */
    public static final long _AUTHENTICATION_FAILED_ERROR_NUMBER = 65542;

    /**
     * Error instance for error AUTHENTICATION_FAILED.
     */
    public static final UInteger AUTHENTICATION_FAILED_ERROR_NUMBER = new UInteger(_AUTHENTICATION_FAILED_ERROR_NUMBER);

    /**
     * Error literal for error AUTHORISATION_FAIL.
     */
    public static final long _AUTHORISATION_FAIL_ERROR_NUMBER = 65543;

    /**
     * Error instance for error AUTHORISATION_FAIL.
     */
    public static final UInteger AUTHORISATION_FAIL_ERROR_NUMBER = new UInteger(_AUTHORISATION_FAIL_ERROR_NUMBER);

    /**
     * Error literal for error ENCRYPTION_FAIL.
     */
    public static final long _ENCRYPTION_FAIL_ERROR_NUMBER = 65544;

    /**
     * Error instance for error ENCRYPTION_FAIL.
     */
    public static final UInteger ENCRYPTION_FAIL_ERROR_NUMBER = new UInteger(_ENCRYPTION_FAIL_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_AREA.
     */
    public static final long _UNSUPPORTED_AREA_ERROR_NUMBER = 65545;

    /**
     * Error instance for error UNSUPPORTED_AREA.
     */
    public static final UInteger UNSUPPORTED_AREA_ERROR_NUMBER = new UInteger(_UNSUPPORTED_AREA_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_AREA_VERSION.
     */
    public static final long _UNSUPPORTED_AREA_VERSION_ERROR_NUMBER = 65546;

    /**
     * Error instance for error UNSUPPORTED_AREA_VERSION.
     */
    public static final UInteger UNSUPPORTED_AREA_VERSION_ERROR_NUMBER = new UInteger(_UNSUPPORTED_AREA_VERSION_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_SERVICE.
     */
    public static final long _UNSUPPORTED_SERVICE_ERROR_NUMBER = 65547;

    /**
     * Error instance for error UNSUPPORTED_SERVICE.
     */
    public static final UInteger UNSUPPORTED_SERVICE_ERROR_NUMBER = new UInteger(_UNSUPPORTED_SERVICE_ERROR_NUMBER);

    /**
     * Error literal for error UNSUPPORTED_OPERATION.
     */
    public static final long _UNSUPPORTED_OPERATION_ERROR_NUMBER = 65548;

    /**
     * Error instance for error UNSUPPORTED_OPERATION.
     */
    public static final UInteger UNSUPPORTED_OPERATION_ERROR_NUMBER = new UInteger(_UNSUPPORTED_OPERATION_ERROR_NUMBER);

    /**
     * Error literal for error BAD_ENCODING.
     */
    public static final long _BAD_ENCODING_ERROR_NUMBER = 65549;

    /**
     * Error instance for error BAD_ENCODING.
     */
    public static final UInteger BAD_ENCODING_ERROR_NUMBER = new UInteger(_BAD_ENCODING_ERROR_NUMBER);

    /**
     * Error literal for error INTERNAL.
     */
    public static final long _INTERNAL_ERROR_NUMBER = 65550;

    /**
     * Error instance for error INTERNAL.
     */
    public static final UInteger INTERNAL_ERROR_NUMBER = new UInteger(_INTERNAL_ERROR_NUMBER);

    /**
     * Error literal for error UNKNOWN.
     */
    public static final long _UNKNOWN_ERROR_NUMBER = 65551;

    /**
     * Error instance for error UNKNOWN.
     */
    public static final UInteger UNKNOWN_ERROR_NUMBER = new UInteger(_UNKNOWN_ERROR_NUMBER);

    /**
     * Error literal for error INCORRECT_STATE.
     */
    public static final long _INCORRECT_STATE_ERROR_NUMBER = 65552;

    /**
     * Error instance for error INCORRECT_STATE.
     */
    public static final UInteger INCORRECT_STATE_ERROR_NUMBER = new UInteger(_INCORRECT_STATE_ERROR_NUMBER);

    /**
     * Error literal for error TOO_MANY.
     */
    public static final long _TOO_MANY_ERROR_NUMBER = 65553;

    /**
     * Error instance for error TOO_MANY.
     */
    public static final UInteger TOO_MANY_ERROR_NUMBER = new UInteger(_TOO_MANY_ERROR_NUMBER);

    /**
     * Error literal for error SHUTDOWN.
     */
    public static final long _SHUTDOWN_ERROR_NUMBER = 65554;

    /**
     * Error instance for error SHUTDOWN.
     */
    public static final UInteger SHUTDOWN_ERROR_NUMBER = new UInteger(_SHUTDOWN_ERROR_NUMBER);

    /**
     * Error literal for error TRANSACTION_TIMEOUT.
     */
    public static final long _TRANSACTION_TIMEOUT_ERROR_NUMBER = 65555;

    /**
     * Error instance for error TRANSACTION_TIMEOUT.
     */
    public static final UInteger TRANSACTION_TIMEOUT_ERROR_NUMBER = new UInteger(_TRANSACTION_TIMEOUT_ERROR_NUMBER);

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
            case 65536:
                return new DeliveryFailedException(extraInfo);
            case 65537:
                return new DeliveryTimedoutException(extraInfo);
            case 65538:
                return new DeliveryDelayedException(extraInfo);
            case 65539:
                return new DestinationUnknownException(extraInfo);
            case 65540:
                return new DestinationTransientException(extraInfo);
            case 65541:
                return new DestinationLostException(extraInfo);
            case 65542:
                return new AuthenticationFailedException(extraInfo);
            case 65543:
                return new AuthorisationFailException(extraInfo);
            case 65544:
                return new EncryptionFailException(extraInfo);
            case 65545:
                return new UnsupportedAreaException(extraInfo);
            case 65546:
                return new UnsupportedAreaVersionException(extraInfo);
            case 65547:
                return new UnsupportedServiceException(extraInfo);
            case 65548:
                return new UnsupportedOperationException(extraInfo);
            case 65549:
                return new BadEncodingException(extraInfo);
            case 65550:
                return new InternalException(extraInfo);
            case 65551:
                return new UnknownException(extraInfo);
            case 65552:
                return new IncorrectStateException(extraInfo);
            case 65553:
                return new TooManyException(extraInfo);
            case 65554:
                return new ShutdownException(extraInfo);
            case 65555:
                return new TransactionTimeoutException(extraInfo);
        }
        return null;
    }

    private MALHelper() {
        // Utility class; not meant to be instantiated.
    }

}
