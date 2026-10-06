package org.ccsds.moims.mo.mc.conversion;

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
import org.ccsds.moims.mo.mc.conversion.structures.DiscreteConversionDetails;
import org.ccsds.moims.mo.mc.conversion.structures.LineConversionDetails;
import org.ccsds.moims.mo.mc.conversion.structures.PolyConversionDetails;
import org.ccsds.moims.mo.mc.conversion.structures.RangeConversionDetails;

/**
 * Helper class for Conversion service.
 */
public class ConversionServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _CONVERSION_SERVICE_NUMBER = 7;

    /**
     * Service number instance.
     */
    public static final UShort CONVERSION_SERVICE_NUMBER = new UShort(_CONVERSION_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier CONVERSION_SERVICE_NAME = new Identifier("Conversion");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            4, 1, CONVERSION_SERVICE_NUMBER);

    /**
     * Area elements.
     */
    public static final Element[] CONVERSION_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{};

    /**
     * Literal for object CONVERSIONIDENTITY.
     */
    @Deprecated
    public static final int _CONVERSIONIDENTITY_OBJECT_NUMBER = 1;

    /**
     * Instance for object CONVERSIONIDENTITY.
     */
    @Deprecated
    public static final UShort CONVERSIONIDENTITY_OBJECT_NUMBER = new UShort(_CONVERSIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier CONVERSIONIDENTITY_OBJECT_NAME = new Identifier("ConversionIdentity");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType CONVERSIONIDENTITY_OBJECT_TYPE = new ObjectType(new UShort(4), CONVERSION_SERVICE_NUMBER, new UOctet(1), CONVERSIONIDENTITY_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject CONVERSIONIDENTITY_OBJECT = new COMObject(CONVERSIONIDENTITY_OBJECT_TYPE, CONVERSIONIDENTITY_OBJECT_NAME, Attribute.IDENTIFIER_SHORT_FORM, false, null, true, null, false);

    /**
     * Literal for object DISCRETECONVERSION.
     */
    @Deprecated
    public static final int _DISCRETECONVERSION_OBJECT_NUMBER = 2;

    /**
     * Instance for object DISCRETECONVERSION.
     */
    @Deprecated
    public static final UShort DISCRETECONVERSION_OBJECT_NUMBER = new UShort(_DISCRETECONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier DISCRETECONVERSION_OBJECT_NAME = new Identifier("DiscreteConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType DISCRETECONVERSION_OBJECT_TYPE = new ObjectType(new UShort(4), CONVERSION_SERVICE_NUMBER, new UOctet(1), DISCRETECONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject DISCRETECONVERSION_OBJECT = new COMObject(DISCRETECONVERSION_OBJECT_TYPE, DISCRETECONVERSION_OBJECT_NAME, DiscreteConversionDetails.SHORT_FORM, true, ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object LINECONVERSION.
     */
    @Deprecated
    public static final int _LINECONVERSION_OBJECT_NUMBER = 3;

    /**
     * Instance for object LINECONVERSION.
     */
    @Deprecated
    public static final UShort LINECONVERSION_OBJECT_NUMBER = new UShort(_LINECONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier LINECONVERSION_OBJECT_NAME = new Identifier("LineConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType LINECONVERSION_OBJECT_TYPE = new ObjectType(new UShort(4), CONVERSION_SERVICE_NUMBER, new UOctet(1), LINECONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject LINECONVERSION_OBJECT = new COMObject(LINECONVERSION_OBJECT_TYPE, LINECONVERSION_OBJECT_NAME, LineConversionDetails.SHORT_FORM, true, ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object POLYCONVERSION.
     */
    @Deprecated
    public static final int _POLYCONVERSION_OBJECT_NUMBER = 4;

    /**
     * Instance for object POLYCONVERSION.
     */
    @Deprecated
    public static final UShort POLYCONVERSION_OBJECT_NUMBER = new UShort(_POLYCONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier POLYCONVERSION_OBJECT_NAME = new Identifier("PolyConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType POLYCONVERSION_OBJECT_TYPE = new ObjectType(new UShort(4), CONVERSION_SERVICE_NUMBER, new UOctet(1), POLYCONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject POLYCONVERSION_OBJECT = new COMObject(POLYCONVERSION_OBJECT_TYPE, POLYCONVERSION_OBJECT_NAME, PolyConversionDetails.SHORT_FORM, true, ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Literal for object RANGECONVERSION.
     */
    @Deprecated
    public static final int _RANGECONVERSION_OBJECT_NUMBER = 5;

    /**
     * Instance for object RANGECONVERSION.
     */
    @Deprecated
    public static final UShort RANGECONVERSION_OBJECT_NUMBER = new UShort(_RANGECONVERSION_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier RANGECONVERSION_OBJECT_NAME = new Identifier("RangeConversion");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType RANGECONVERSION_OBJECT_TYPE = new ObjectType(new UShort(4), CONVERSION_SERVICE_NUMBER, new UOctet(1), RANGECONVERSION_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject RANGECONVERSION_OBJECT = new COMObject(RANGECONVERSION_OBJECT_TYPE, RANGECONVERSION_OBJECT_NAME, RangeConversionDetails.SHORT_FORM, true, ConversionServiceInfo.CONVERSIONIDENTITY_OBJECT_TYPE, true, null, false);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
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
