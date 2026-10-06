package org.ccsds.moims.mo.mc.group;

import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.group.structures.GroupDetails;

/**
 * Helper class for Group service.
 */
public class GroupServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _GROUP_SERVICE_NUMBER = 8;

    /**
     * Service number instance.
     */
    public static final UShort GROUP_SERVICE_NUMBER = new UShort(_GROUP_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier GROUP_SERVICE_NAME = new Identifier("Group");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 1, GROUP_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final Element[] GROUP_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{};

    /**
     * Literal for object GROUPIDENTITY.
     */
    @Deprecated
    public static final int _GROUPIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object GROUPIDENTITY.
     */
    @Deprecated
    public static final UShort GROUPIDENTITY_OBJECT_NUMBER = new UShort(_GROUPIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier GROUPIDENTITY_OBJECT_NAME = new Identifier("GroupIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType GROUPIDENTITY_OBJECT_TYPE = new ObjectType(new UShort(4), GROUP_SERVICE_NUMBER, new UOctet(1), GROUPIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject GROUPIDENTITY_OBJECT = new COMObject(GROUPIDENTITY_OBJECT_TYPE, GROUPIDENTITY_OBJECT_NAME, Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object GROUPDEFINITION.
     */
    @Deprecated
    public static final int _GROUPDEFINITION_OBJECT_NUMBER = 2;

    /**
     * Instance for object GROUPDEFINITION.
     */
    @Deprecated
    public static final UShort GROUPDEFINITION_OBJECT_NUMBER = new UShort(_GROUPDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier GROUPDEFINITION_OBJECT_NAME = new Identifier("GroupDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType GROUPDEFINITION_OBJECT_TYPE = new ObjectType(new UShort(4), GROUP_SERVICE_NUMBER, new UOctet(1), GROUPDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject GROUPDEFINITION_OBJECT = new COMObject(GROUPDEFINITION_OBJECT_TYPE, GROUPDEFINITION_OBJECT_NAME, GroupDetails.SHORT_FORM, true, GroupServiceInfo.GROUPIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
        GROUPIDENTITY_OBJECT,
        GROUPDEFINITION_OBJECT,};

    /**
     * Creates an instance of the Group ServiceInfo.
     * 
     */
    public GroupServiceInfo() {
        super(SERVICE_KEY, GROUP_SERVICE_NAME, GROUP_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public MALArea getArea() {
        return MCHelper.MC_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        MOErrorException areaError = MCHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
