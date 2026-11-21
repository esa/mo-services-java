package org.ccsds.moims.mo.comprototype2;

import org.ccsds.moims.mo.comprototype2.test1.Test1Helper;
import org.ccsds.moims.mo.comprototype2.test2.Test2Helper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for COMPrototype2 area.
 */
public class COMPrototype2Helper {

    /**
     * Area number literal.
     */
    public static final int _COMPROTOTYPE2_AREA_NUMBER = 202;

    /**
     * Area number instance.
     */
    public static final UShort COMPROTOTYPE2_AREA_NUMBER = new UShort(_COMPROTOTYPE2_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier COMPROTOTYPE2_AREA_NAME = new Identifier("COMPrototype2");

    /**
     * Area version literal.
     */
    public static final short _COMPROTOTYPE2_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet COMPROTOTYPE2_AREA_VERSION = new UOctet(_COMPROTOTYPE2_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] COMPROTOTYPE2_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] COMPROTOTYPE2_AREA_SERVICES = {
        Test1Helper.TEST1_SERVICE,
        Test2Helper.TEST2_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea COMPROTOTYPE2_AREA = new MALArea(COMPROTOTYPE2_AREA_NUMBER, COMPROTOTYPE2_AREA_NAME, COMPROTOTYPE2_AREA_VERSION, COMPROTOTYPE2_AREA_ELEMENTS, COMPROTOTYPE2_AREA_SERVICES, new COMPrototype2ElementFactory());

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

    private COMPrototype2Helper() {
        // Utility class; not meant to be instantiated.
    }

}
