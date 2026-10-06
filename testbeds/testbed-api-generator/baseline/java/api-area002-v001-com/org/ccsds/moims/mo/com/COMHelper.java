package org.ccsds.moims.mo.com;

import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingHelper;
import org.ccsds.moims.mo.com.archive.ArchiveHelper;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for COM area.
 */
public class COMHelper {

    /**
     * Area number literal.
     */
    public static final int _COM_AREA_NUMBER = 2;

    /**
     * Area number instance.
     */
    public static final UShort COM_AREA_NUMBER = new UShort(_COM_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier COM_AREA_NAME = new Identifier("COM");

    /**
     * Area version literal.
     */
    public static final short _COM_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet COM_AREA_VERSION = new UOctet(_COM_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] COM_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] COM_AREA_SERVICES = {
        EventHelper.EVENT_SERVICE,
        ArchiveHelper.ARCHIVE_SERVICE,
        ActivityTrackingHelper.ACTIVITYTRACKING_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea COM_AREA = new MALArea(COM_AREA_NUMBER, COM_AREA_NAME, COM_AREA_VERSION, COM_AREA_ELEMENTS, COM_AREA_SERVICES, new COMElementFactory());

    /**
     * Error literal for error INVALID.
     */
    public static final long _INVALID_ERROR_NUMBER = 70000;

    /**
     * Error instance for error INVALID.
     */
    public static final UInteger INVALID_ERROR_NUMBER = new UInteger(_INVALID_ERROR_NUMBER);

    /**
     * Error literal for error DUPLICATE.
     */
    public static final long _DUPLICATE_ERROR_NUMBER = 70001;

    /**
     * Error instance for error DUPLICATE.
     */
    public static final UInteger DUPLICATE_ERROR_NUMBER = new UInteger(_DUPLICATE_ERROR_NUMBER);

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
            case 70000:
                return new InvalidException(extraInfo);
            case 70001:
                return new DuplicateException(extraInfo);
        }
        return null;
    }

    private COMHelper() {
        // Utility class; not meant to be instantiated.
    }

}
