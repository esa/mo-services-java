package org.ccsds.moims.mo.comprototype2;

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
    public static final org.ccsds.moims.mo.mal.structures.UShort COMPROTOTYPE2_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_COMPROTOTYPE2_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier COMPROTOTYPE2_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("COMPrototype2");

    /**
     * Area version literal.
     */
    public static final short _COMPROTOTYPE2_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet COMPROTOTYPE2_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_COMPROTOTYPE2_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] COMPROTOTYPE2_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] COMPROTOTYPE2_AREA_SERVICES = {
        org.ccsds.moims.mo.comprototype2.test1.Test1Helper.TEST1_SERVICE,
        org.ccsds.moims.mo.comprototype2.test2.Test2Helper.TEST2_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea COMPROTOTYPE2_AREA = new org.ccsds.moims.mo.mal.MALArea(COMPROTOTYPE2_AREA_NUMBER, COMPROTOTYPE2_AREA_NAME, COMPROTOTYPE2_AREA_VERSION, COMPROTOTYPE2_AREA_ELEMENTS, COMPROTOTYPE2_AREA_SERVICES, new COMPrototype2ElementFactory());

    /**
     * Returns the exception of the error of this area with the given number.
     * 
     * @param errorNumber The number of the error.
     * @param extraInfo The extra information of the error.
     * @return the exception, or null if the area declares no error with that number
     */
    public static org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
        }
        return null;
    }

    private COMPrototype2Helper() {
        // Utility class; not meant to be instantiated.
    }

}
