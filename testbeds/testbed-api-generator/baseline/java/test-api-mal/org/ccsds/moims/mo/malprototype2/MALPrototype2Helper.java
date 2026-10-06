package org.ccsds.moims.mo.malprototype2;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper;

/**
 * Helper class for MALPrototype2 area.
 */
public class MALPrototype2Helper {

    /**
     * Area number literal.
     */
    public static final int _MALPROTOTYPE2_AREA_NUMBER = 101;

    /**
     * Area number instance.
     */
    public static final UShort MALPROTOTYPE2_AREA_NUMBER = new UShort(_MALPROTOTYPE2_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier MALPROTOTYPE2_AREA_NAME = new Identifier("MALPrototype2");

    /**
     * Area version literal.
     */
    public static final short _MALPROTOTYPE2_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet MALPROTOTYPE2_AREA_VERSION = new UOctet(_MALPROTOTYPE2_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] MALPROTOTYPE2_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] MALPROTOTYPE2_AREA_SERVICES = {
        IPTestHelper.IPTEST_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea MALPROTOTYPE2_AREA = new MALArea(MALPROTOTYPE2_AREA_NUMBER, MALPROTOTYPE2_AREA_NAME, MALPROTOTYPE2_AREA_VERSION, MALPROTOTYPE2_AREA_ELEMENTS, MALPROTOTYPE2_AREA_SERVICES, new MALPrototype2ElementFactory());

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

    private MALPrototype2Helper() {
        // Utility class; not meant to be instantiated.
    }

}
