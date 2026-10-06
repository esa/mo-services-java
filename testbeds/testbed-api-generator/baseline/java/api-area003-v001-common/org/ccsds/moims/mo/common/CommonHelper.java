package org.ccsds.moims.mo.common;

import org.ccsds.moims.mo.common.configuration.ConfigurationHelper;
import org.ccsds.moims.mo.common.directory.DirectoryHelper;
import org.ccsds.moims.mo.common.login.LoginHelper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for Common area.
 */
public class CommonHelper {

    /**
     * Area number literal.
     */
    public static final int _COMMON_AREA_NUMBER = 3;

    /**
     * Area number instance.
     */
    public static final UShort COMMON_AREA_NUMBER = new UShort(_COMMON_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier COMMON_AREA_NAME = new Identifier("Common");

    /**
     * Area version literal.
     */
    public static final short _COMMON_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet COMMON_AREA_VERSION = new UOctet(_COMMON_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] COMMON_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] COMMON_AREA_SERVICES = {
        DirectoryHelper.DIRECTORY_SERVICE,
        LoginHelper.LOGIN_SERVICE,
        ConfigurationHelper.CONFIGURATION_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea COMMON_AREA = new MALArea(COMMON_AREA_NUMBER, COMMON_AREA_NAME, COMMON_AREA_VERSION, COMMON_AREA_ELEMENTS, COMMON_AREA_SERVICES, new CommonElementFactory());

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
        }
        return null;
    }

    private CommonHelper() {
        // Utility class; not meant to be instantiated.
    }

}
