package org.ccsds.moims.mo.comprototype;

import org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementHelper;
import org.ccsds.moims.mo.comprototype.activitytest.ActivityTestHelper;
import org.ccsds.moims.mo.comprototype.archivetest.ArchiveTestHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestHelper;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for COMPrototype area.
 */
public class COMPrototypeHelper {

    /**
     * Area number literal.
     */
    public static final int _COMPROTOTYPE_AREA_NUMBER = 200;

    /**
     * Area number instance.
     */
    public static final UShort COMPROTOTYPE_AREA_NUMBER = new UShort(_COMPROTOTYPE_AREA_NUMBER);

    /**
     * Area name constant.
     */
    public static final Identifier COMPROTOTYPE_AREA_NAME = new Identifier("COMPrototype");

    /**
     * Area version literal.
     */
    public static final short _COMPROTOTYPE_AREA_VERSION = 1;

    /**
     * Area version instance.
     */
    public static final UOctet COMPROTOTYPE_AREA_VERSION = new UOctet(_COMPROTOTYPE_AREA_VERSION);

    /**
     * Area Elements.
     */
    public static final Element[] COMPROTOTYPE_AREA_ELEMENTS = {};

    /**
     * Services in this Area.
     */
    public static final ServiceInfo[] COMPROTOTYPE_AREA_SERVICES = {
        EventTestHelper.EVENTTEST_SERVICE,
        ActivityTestHelper.ACTIVITYTEST_SERVICE,
        ActivityRelayManagementHelper.ACTIVITYRELAYMANAGEMENT_SERVICE,
        ArchiveTestHelper.ARCHIVETEST_SERVICE,};

    /**
     * Area singleton instance.
     */
    public static final MALArea COMPROTOTYPE_AREA = new MALArea(COMPROTOTYPE_AREA_NUMBER, COMPROTOTYPE_AREA_NAME, COMPROTOTYPE_AREA_VERSION, COMPROTOTYPE_AREA_ELEMENTS, COMPROTOTYPE_AREA_SERVICES, new COMPrototypeElementFactory());

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

    private COMPrototypeHelper() {
        // Utility class; not meant to be instantiated.
    }

}
