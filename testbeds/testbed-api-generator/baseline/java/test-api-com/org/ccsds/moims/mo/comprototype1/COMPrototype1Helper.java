package org.ccsds.moims.mo.comprototype1;

import org.ccsds.moims.mo.comprototype1.test1.Test1Helper;
import org.ccsds.moims.mo.comprototype1.test2.Test2Helper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for COMPrototype1 area.
 */
public class COMPrototype1Helper {

    /**
     * Area number literal.
     */
    public static final int _COMPROTOTYPE1_AREA_NUMBER = 201;

    /**
     * Area number instance.
     */
    public static final UShort COMPROTOTYPE1_AREA_NUMBER = new UShort(_COMPROTOTYPE1_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier COMPROTOTYPE1_AREA_NAME = new Identifier("COMPrototype1");

    /**
     * Area version literal.
     */
    public static final short _COMPROTOTYPE1_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet COMPROTOTYPE1_AREA_VERSION = new UOctet(_COMPROTOTYPE1_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] COMPROTOTYPE1_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] COMPROTOTYPE1_AREA_SERVICES = {
        Test1Helper.TEST1_SERVICE,
        Test2Helper.TEST2_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea COMPROTOTYPE1_AREA = new MALArea(COMPROTOTYPE1_AREA_NUMBER, COMPROTOTYPE1_AREA_NAME, COMPROTOTYPE1_AREA_VERSION, COMPROTOTYPE1_AREA_ELEMENTS, COMPROTOTYPE1_AREA_SERVICES, new COMPrototype1ElementFactory());

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

    private COMPrototype1Helper() {
        // Utility class; not meant to be instantiated.
    }

}
