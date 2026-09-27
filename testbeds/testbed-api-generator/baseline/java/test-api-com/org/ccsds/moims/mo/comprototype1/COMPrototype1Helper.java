package org.ccsds.moims.mo.comprototype1;

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
    public static final org.ccsds.moims.mo.mal.structures.UShort COMPROTOTYPE1_AREA_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_COMPROTOTYPE1_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier COMPROTOTYPE1_AREA_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("COMPrototype1");

    /**
     * Area version literal.
     */
    public static final short _COMPROTOTYPE1_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UOctet COMPROTOTYPE1_AREA_VERSION = new org.ccsds.moims.mo.mal.structures.UOctet(_COMPROTOTYPE1_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] COMPROTOTYPE1_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final org.ccsds.moims.mo.mal.ServiceInfo[] COMPROTOTYPE1_AREA_SERVICES = {
        org.ccsds.moims.mo.comprototype1.test1.Test1Helper.TEST1_SERVICE,
        org.ccsds.moims.mo.comprototype1.test2.Test2Helper.TEST2_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final org.ccsds.moims.mo.mal.MALArea COMPROTOTYPE1_AREA = new org.ccsds.moims.mo.mal.MALArea(COMPROTOTYPE1_AREA_NUMBER, COMPROTOTYPE1_AREA_NAME, COMPROTOTYPE1_AREA_VERSION, COMPROTOTYPE1_AREA_ELEMENTS, COMPROTOTYPE1_AREA_SERVICES, new COMPrototype1ElementFactory());

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

    private COMPrototype1Helper() {
        // Utility class; not meant to be instantiated.
    }

}
