package org.ccsds.moims.mo.mc.conversion;

/**
 * Helper class for Conversion service.
 */
public class ConversionServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _CONVERSION_SERVICE_NUMBER = 7;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort CONVERSION_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONVERSION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONVERSION_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Conversion");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            4, 1, CONVERSION_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] CONVERSION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{};

    /**
     * Literal for object CONVERSIONIDENTITY.
     */
    @Deprecated
    public static final int _CONVERSIONIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object CONVERSIONIDENTITY.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort CONVERSIONIDENTITY_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CONVERSIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier CONVERSIONIDENTITY_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ConversionIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType CONVERSIONIDENTITY_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CONVERSION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), CONVERSIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject CONVERSIONIDENTITY_OBJECT = new org.ccsds.moims.mo.com.COMObject(CONVERSIONIDENTITY_OBJECT_TYPE, CONVERSIONIDENTITY_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object DISCRETECONVERSION.
     */
    @Deprecated
    public static final int _DISCRETECONVERSION_OBJECT_NUMBER = 2;

    /**
     * Instance for object DISCRETECONVERSION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort DISCRETECONVERSION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_DISCRETECONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier DISCRETECONVERSION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("DiscreteConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType DISCRETECONVERSION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CONVERSION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), DISCRETECONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject DISCRETECONVERSION_OBJECT = new org.ccsds.moims.mo.com.COMObject(DISCRETECONVERSION_OBJECT_TYPE, DISCRETECONVERSION_OBJECT_NAME, org.ccsds.moims.mo.mc.conversion.structures.DiscreteConversionDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.conversion.ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object LINECONVERSION.
     */
    @Deprecated
    public static final int _LINECONVERSION_OBJECT_NUMBER = 3;

    /**
     * Instance for object LINECONVERSION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort LINECONVERSION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LINECONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier LINECONVERSION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("LineConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType LINECONVERSION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CONVERSION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), LINECONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject LINECONVERSION_OBJECT = new org.ccsds.moims.mo.com.COMObject(LINECONVERSION_OBJECT_TYPE, LINECONVERSION_OBJECT_NAME, org.ccsds.moims.mo.mc.conversion.structures.LineConversionDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.conversion.ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object POLYCONVERSION.
     */
    @Deprecated
    public static final int _POLYCONVERSION_OBJECT_NUMBER = 4;

    /**
     * Instance for object POLYCONVERSION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort POLYCONVERSION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_POLYCONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier POLYCONVERSION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("PolyConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType POLYCONVERSION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CONVERSION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), POLYCONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject POLYCONVERSION_OBJECT = new org.ccsds.moims.mo.com.COMObject(POLYCONVERSION_OBJECT_TYPE, POLYCONVERSION_OBJECT_NAME, org.ccsds.moims.mo.mc.conversion.structures.PolyConversionDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.conversion.ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object RANGECONVERSION.
     */
    @Deprecated
    public static final int _RANGECONVERSION_OBJECT_NUMBER = 5;

    /**
     * Instance for object RANGECONVERSION.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort RANGECONVERSION_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_RANGECONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier RANGECONVERSION_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("RangeConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType RANGECONVERSION_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(4), CONVERSION_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), RANGECONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject RANGECONVERSION_OBJECT = new org.ccsds.moims.mo.com.COMObject(RANGECONVERSION_OBJECT_TYPE, RANGECONVERSION_OBJECT_NAME, org.ccsds.moims.mo.mc.conversion.structures.RangeConversionDetails.SHORT_FORM, true, org.ccsds.moims.mo.mc.conversion.ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        CONVERSIONIDENTITY_OBJECT,
        DISCRETECONVERSION_OBJECT,
        LINECONVERSION_OBJECT,
        POLYCONVERSION_OBJECT,
        RANGECONVERSION_OBJECT,};

    /**
     * Creates an instance of the Conversion ServiceInfo.
     * 
     */
    public ConversionServiceInfo() {
        super(SERVICE_KEY, CONVERSION_SERVICE_NAME, CONVERSION_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
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
