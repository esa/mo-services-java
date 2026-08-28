package org.ccsds.moims.mo.mc.group;

/**
 * Helper class for Group service.
 */
public class GroupServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _GROUP_SERVICE_NUMBER = 8;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort GROUP_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GROUP_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier GROUP_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Group");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 1, GROUP_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] GROUP_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{};

    /**
     * Literal for object GROUPIDENTITY.
     */
    @Deprecated
    public static final int _GROUPIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object GROUPIDENTITY.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort GROUPIDENTITY_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GROUPIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier GROUPIDENTITY_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("GroupIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType GROUPIDENTITY_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), GROUP_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), GROUPIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject GROUPIDENTITY_OBJECT = new org.ccsds.moims.mo.com.COMObject(GROUPIDENTITY_OBJECT_TYPE, GROUPIDENTITY_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object GROUPDEFINITION.
     */
    @Deprecated
    public static final int _GROUPDEFINITION_OBJECT_NUMBER = 2;

    /**
     * Instance for object GROUPDEFINITION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort GROUPDEFINITION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_GROUPDEFINITION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier GROUPDEFINITION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("GroupDefinition");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType GROUPDEFINITION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), GROUP_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), GROUPDEFINITION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject GROUPDEFINITION_OBJECT = new org.ccsds.moims.mo.com.COMObject(GROUPDEFINITION_OBJECT_TYPE, GROUPDEFINITION_OBJECT_NAME, org.ccsds.moims.mo.mc.group.structures.GroupDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.group.GroupServiceInfo.GROUPIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
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
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mc.MCHelper.MC_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 70020:
                return new org.ccsds.moims.mo.mc.ReadonlyException(extraInfo);
            case 70021:
                return new org.ccsds.moims.mo.mc.ReferencedException(extraInfo);
        }
        return null;
    }

}
