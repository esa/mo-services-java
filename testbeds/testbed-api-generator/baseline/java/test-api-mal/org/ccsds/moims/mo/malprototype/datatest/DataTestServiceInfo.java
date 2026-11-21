package org.ccsds.moims.mo.malprototype.datatest;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malprototype.DataErrorException;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.TestObjectExistsException;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.Garage;

/**
 * Helper class for DataTest service.
 */
public class DataTestServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _DATATEST_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final UShort DATATEST_SERVICE_NUMBER = new UShort(_DATATEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier DATATEST_SERVICE_NAME = new Identifier("DataTest");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            100, 1, DATATEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation SETTESTDATAOFFSET.
     */
    public static final int _SETTESTDATAOFFSET_OP_NUMBER = 99;

    /**
     * Operation number instance for operation SETTESTDATAOFFSET.
     */
    private static final UShort SETTESTDATAOFFSET_OP_NUMBER = new UShort(_SETTESTDATAOFFSET_OP_NUMBER);

    /**
     * Operation instance for operation SETTESTDATAOFFSET.
     */
    public static final MALSubmitOperation SETTESTDATAOFFSET_OP = new MALSubmitOperation(SERVICE_KEY, 
            SETTESTDATAOFFSET_OP_NUMBER, 
            new Identifier("setTestDataOffset"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.INTEGER_SHORT_FORM, "")}, 
            "This operation sets the index into the test list for the testData operation. Passing non positive values resets it to the start of the list.");

    /**
     * Operation number literal for operation TESTDATA.
     */
    public static final int _TESTDATA_OP_NUMBER = 100;

    /**
     * Operation number instance for operation TESTDATA.
     */
    private static final UShort TESTDATA_OP_NUMBER = new UShort(_TESTDATA_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATA.
     */
    public static final MALRequestOperation TESTDATA_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATA_OP_NUMBER, 
            new Identifier("testData"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            "The 'testData' operation allows a consumer to check that a data is correctly decoded on the provider side, then that the same data sent back by the provider is correctly decoded on the consumer side. The provider needs to statically know the list of data that the consumer is going to send. The consumer selects the data in the same order as the list and calls the operation 'testData'. The provider keeps the index of the currently selected data from the static list. When the operation 'testData' is called, the provider checks that the received data is equal to the selected data from the list. If the equality test fails, then the error DATA_ERROR is raised, otherwise the provider returns the decoded data. When the consumer receives the returned data, it checks that this data is equal to the original data it sent.");

    /**
     * Operation number literal for operation TESTDATABLOB.
     */
    public static final int _TESTDATABLOB_OP_NUMBER = 101;

    /**
     * Operation number instance for operation TESTDATABLOB.
     */
    private static final UShort TESTDATABLOB_OP_NUMBER = new UShort(_TESTDATABLOB_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATABLOB.
     */
    public static final MALRequestOperation TESTDATABLOB_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATABLOB_OP_NUMBER, 
            new Identifier("testDataBlob"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.BLOB_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.BLOB_SHORT_FORM, "")}, 
            "This operation checks that a basic Blob type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATABOOLEAN.
     */
    public static final int _TESTDATABOOLEAN_OP_NUMBER = 102;

    /**
     * Operation number instance for operation TESTDATABOOLEAN.
     */
    private static final UShort TESTDATABOOLEAN_OP_NUMBER = new UShort(_TESTDATABOOLEAN_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATABOOLEAN.
     */
    public static final MALRequestOperation TESTDATABOOLEAN_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATABOOLEAN_OP_NUMBER, 
            new Identifier("testDataBoolean"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.BOOLEAN_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.BOOLEAN_SHORT_FORM, "")}, 
            "This operation checks that a basic Boolean type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATADOUBLE.
     */
    public static final int _TESTDATADOUBLE_OP_NUMBER = 103;

    /**
     * Operation number instance for operation TESTDATADOUBLE.
     */
    private static final UShort TESTDATADOUBLE_OP_NUMBER = new UShort(_TESTDATADOUBLE_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATADOUBLE.
     */
    public static final MALRequestOperation TESTDATADOUBLE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATADOUBLE_OP_NUMBER, 
            new Identifier("testDataDouble"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.DOUBLE_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.DOUBLE_SHORT_FORM, "")}, 
            "This operation checks that a basic Double type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATADURATION.
     */
    public static final int _TESTDATADURATION_OP_NUMBER = 104;

    /**
     * Operation number instance for operation TESTDATADURATION.
     */
    private static final UShort TESTDATADURATION_OP_NUMBER = new UShort(_TESTDATADURATION_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATADURATION.
     */
    public static final MALRequestOperation TESTDATADURATION_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATADURATION_OP_NUMBER, 
            new Identifier("testDataDuration"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.DURATION_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.DURATION_SHORT_FORM, "")}, 
            "This operation checks that a basic Duration type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAFINETIME.
     */
    public static final int _TESTDATAFINETIME_OP_NUMBER = 105;

    /**
     * Operation number instance for operation TESTDATAFINETIME.
     */
    private static final UShort TESTDATAFINETIME_OP_NUMBER = new UShort(_TESTDATAFINETIME_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAFINETIME.
     */
    public static final MALRequestOperation TESTDATAFINETIME_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAFINETIME_OP_NUMBER, 
            new Identifier("testDataFineTime"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.FINETIME_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.FINETIME_SHORT_FORM, "")}, 
            "This operation checks that a basic FineTime type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAFLOAT.
     */
    public static final int _TESTDATAFLOAT_OP_NUMBER = 106;

    /**
     * Operation number instance for operation TESTDATAFLOAT.
     */
    private static final UShort TESTDATAFLOAT_OP_NUMBER = new UShort(_TESTDATAFLOAT_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAFLOAT.
     */
    public static final MALRequestOperation TESTDATAFLOAT_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAFLOAT_OP_NUMBER, 
            new Identifier("testDataFloat"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.FLOAT_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.FLOAT_SHORT_FORM, "")}, 
            "This operation checks that a basic Float type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAIDENTIFIER.
     */
    public static final int _TESTDATAIDENTIFIER_OP_NUMBER = 107;

    /**
     * Operation number instance for operation TESTDATAIDENTIFIER.
     */
    private static final UShort TESTDATAIDENTIFIER_OP_NUMBER = new UShort(_TESTDATAIDENTIFIER_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAIDENTIFIER.
     */
    public static final MALRequestOperation TESTDATAIDENTIFIER_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAIDENTIFIER_OP_NUMBER, 
            new Identifier("testDataIdentifier"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.IDENTIFIER_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.IDENTIFIER_SHORT_FORM, "")}, 
            "This operation checks that a basic Identifier type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAINTEGER.
     */
    public static final int _TESTDATAINTEGER_OP_NUMBER = 108;

    /**
     * Operation number instance for operation TESTDATAINTEGER.
     */
    private static final UShort TESTDATAINTEGER_OP_NUMBER = new UShort(_TESTDATAINTEGER_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAINTEGER.
     */
    public static final MALRequestOperation TESTDATAINTEGER_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAINTEGER_OP_NUMBER, 
            new Identifier("testDataInteger"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.INTEGER_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.INTEGER_SHORT_FORM, "")}, 
            "This operation checks that a basic Integer type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATALONG.
     */
    public static final int _TESTDATALONG_OP_NUMBER = 109;

    /**
     * Operation number instance for operation TESTDATALONG.
     */
    private static final UShort TESTDATALONG_OP_NUMBER = new UShort(_TESTDATALONG_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATALONG.
     */
    public static final MALRequestOperation TESTDATALONG_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATALONG_OP_NUMBER, 
            new Identifier("testDataLong"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.LONG_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.LONG_SHORT_FORM, "")}, 
            "This operation checks that a basic Long type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAOCTET.
     */
    public static final int _TESTDATAOCTET_OP_NUMBER = 110;

    /**
     * Operation number instance for operation TESTDATAOCTET.
     */
    private static final UShort TESTDATAOCTET_OP_NUMBER = new UShort(_TESTDATAOCTET_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAOCTET.
     */
    public static final MALRequestOperation TESTDATAOCTET_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAOCTET_OP_NUMBER, 
            new Identifier("testDataOctet"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.OCTET_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.OCTET_SHORT_FORM, "")}, 
            "This operation checks that a basic Octet type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATASHORT.
     */
    public static final int _TESTDATASHORT_OP_NUMBER = 111;

    /**
     * Operation number instance for operation TESTDATASHORT.
     */
    private static final UShort TESTDATASHORT_OP_NUMBER = new UShort(_TESTDATASHORT_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATASHORT.
     */
    public static final MALRequestOperation TESTDATASHORT_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATASHORT_OP_NUMBER, 
            new Identifier("testDataShort"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.SHORT_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.SHORT_SHORT_FORM, "")}, 
            "This operation checks that a basic Short type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATASTRING.
     */
    public static final int _TESTDATASTRING_OP_NUMBER = 112;

    /**
     * Operation number instance for operation TESTDATASTRING.
     */
    private static final UShort TESTDATASTRING_OP_NUMBER = new UShort(_TESTDATASTRING_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATASTRING.
     */
    public static final MALRequestOperation TESTDATASTRING_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATASTRING_OP_NUMBER, 
            new Identifier("testDataString"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.STRING_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.STRING_SHORT_FORM, "")}, 
            "This operation checks that a basic String type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATATIME.
     */
    public static final int _TESTDATATIME_OP_NUMBER = 113;

    /**
     * Operation number instance for operation TESTDATATIME.
     */
    private static final UShort TESTDATATIME_OP_NUMBER = new UShort(_TESTDATATIME_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATATIME.
     */
    public static final MALRequestOperation TESTDATATIME_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATATIME_OP_NUMBER, 
            new Identifier("testDataTime"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.TIME_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.TIME_SHORT_FORM, "")}, 
            "This operation checks that a basic Time type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAURI.
     */
    public static final int _TESTDATAURI_OP_NUMBER = 114;

    /**
     * Operation number instance for operation TESTDATAURI.
     */
    private static final UShort TESTDATAURI_OP_NUMBER = new UShort(_TESTDATAURI_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAURI.
     */
    public static final MALRequestOperation TESTDATAURI_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAURI_OP_NUMBER, 
            new Identifier("testDataURI"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.URI_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.URI_SHORT_FORM, "")}, 
            "This operation checks that a basic URI type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATACOMPOSITE.
     */
    public static final int _TESTDATACOMPOSITE_OP_NUMBER = 115;

    /**
     * Operation number instance for operation TESTDATACOMPOSITE.
     */
    private static final UShort TESTDATACOMPOSITE_OP_NUMBER = new UShort(_TESTDATACOMPOSITE_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATACOMPOSITE.
     */
    public static final MALRequestOperation TESTDATACOMPOSITE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATACOMPOSITE_OP_NUMBER, 
            new Identifier("testDataComposite"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Assertion.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Assertion.SHORT_FORM, "")}, 
            "This operation checks that a composite type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAENUMERATION.
     */
    public static final int _TESTDATAENUMERATION_OP_NUMBER = 116;

    /**
     * Operation number instance for operation TESTDATAENUMERATION.
     */
    private static final UShort TESTDATAENUMERATION_OP_NUMBER = new UShort(_TESTDATAENUMERATION_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAENUMERATION.
     */
    public static final MALRequestOperation TESTDATAENUMERATION_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAENUMERATION_OP_NUMBER, 
            new Identifier("testDataEnumeration"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, SessionType.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, SessionType.SHORT_FORM, "")}, 
            "This operation checks that a enumeration type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATALIST.
     */
    public static final int _TESTDATALIST_OP_NUMBER = 117;

    /**
     * Operation number instance for operation TESTDATALIST.
     */
    private static final UShort TESTDATALIST_OP_NUMBER = new UShort(_TESTDATALIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATALIST.
     */
    public static final MALRequestOperation TESTDATALIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATALIST_OP_NUMBER, 
            new Identifier("testDataList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, AssertionList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, AssertionList.SHORT_FORM, "")}, 
            "This operation checks that a list type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAUINTEGER.
     */
    public static final int _TESTDATAUINTEGER_OP_NUMBER = 118;

    /**
     * Operation number instance for operation TESTDATAUINTEGER.
     */
    private static final UShort TESTDATAUINTEGER_OP_NUMBER = new UShort(_TESTDATAUINTEGER_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAUINTEGER.
     */
    public static final MALRequestOperation TESTDATAUINTEGER_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAUINTEGER_OP_NUMBER, 
            new Identifier("testDataUInteger"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.UINTEGER_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.UINTEGER_SHORT_FORM, "")}, 
            "This operation checks that a basic UInteger type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAULONG.
     */
    public static final int _TESTDATAULONG_OP_NUMBER = 119;

    /**
     * Operation number instance for operation TESTDATAULONG.
     */
    private static final UShort TESTDATAULONG_OP_NUMBER = new UShort(_TESTDATAULONG_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAULONG.
     */
    public static final MALRequestOperation TESTDATAULONG_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAULONG_OP_NUMBER, 
            new Identifier("testDataULong"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.ULONG_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.ULONG_SHORT_FORM, "")}, 
            "This operation checks that a basic ULong type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAUOCTET.
     */
    public static final int _TESTDATAUOCTET_OP_NUMBER = 120;

    /**
     * Operation number instance for operation TESTDATAUOCTET.
     */
    private static final UShort TESTDATAUOCTET_OP_NUMBER = new UShort(_TESTDATAUOCTET_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAUOCTET.
     */
    public static final MALRequestOperation TESTDATAUOCTET_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAUOCTET_OP_NUMBER, 
            new Identifier("testDataUOctet"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.UOCTET_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.UOCTET_SHORT_FORM, "")}, 
            "This operation checks that a basic UOctet type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTDATAUSHORT.
     */
    public static final int _TESTDATAUSHORT_OP_NUMBER = 121;

    /**
     * Operation number instance for operation TESTDATAUSHORT.
     */
    private static final UShort TESTDATAUSHORT_OP_NUMBER = new UShort(_TESTDATAUSHORT_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAUSHORT.
     */
    public static final MALRequestOperation TESTDATAUSHORT_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAUSHORT_OP_NUMBER, 
            new Identifier("testDataUShort"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, Attribute.USHORT_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Attribute.USHORT_SHORT_FORM, "")}, 
            "This operation checks that a basic UShort type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTEXPLICITMULTIRETURN.
     */
    public static final int _TESTEXPLICITMULTIRETURN_OP_NUMBER = 122;

    /**
     * Operation number instance for operation TESTEXPLICITMULTIRETURN.
     */
    private static final UShort TESTEXPLICITMULTIRETURN_OP_NUMBER = new UShort(_TESTEXPLICITMULTIRETURN_OP_NUMBER);

    /**
     * Operation instance for operation TESTEXPLICITMULTIRETURN.
     */
    public static final MALRequestOperation TESTEXPLICITMULTIRETURN_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTEXPLICITMULTIRETURN_OP_NUMBER, 
            new Identifier("testExplicitMultiReturn"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", false, Attribute.UOCTET_SHORT_FORM, ""),
                new OperationField("in2", false, Attribute.USHORT_SHORT_FORM, ""),
                new OperationField("in3", false, Attribute.UINTEGER_SHORT_FORM, ""),
                new OperationField("in4", true, Attribute.ULONG_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("out1", false, Attribute.UOCTET_SHORT_FORM, ""),
                new OperationField("out2", false, Attribute.USHORT_SHORT_FORM, ""),
                new OperationField("out3", false, Attribute.UINTEGER_SHORT_FORM, ""),
                new OperationField("out4", true, Attribute.ULONG_SHORT_FORM, "")}, 
            "This operation checks that multiple types can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTABSTRACTMULTIRETURN.
     */
    public static final int _TESTABSTRACTMULTIRETURN_OP_NUMBER = 123;

    /**
     * Operation number instance for operation TESTABSTRACTMULTIRETURN.
     */
    private static final UShort TESTABSTRACTMULTIRETURN_OP_NUMBER = new UShort(_TESTABSTRACTMULTIRETURN_OP_NUMBER);

    /**
     * Operation instance for operation TESTABSTRACTMULTIRETURN.
     */
    public static final MALRequestOperation TESTABSTRACTMULTIRETURN_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTABSTRACTMULTIRETURN_OP_NUMBER, 
            new Identifier("testAbstractMultiReturn"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", false, Attribute.UOCTET_SHORT_FORM, ""),
                new OperationField("in2", false, Attribute.USHORT_SHORT_FORM, ""),
                new OperationField("in3", false, Attribute.UINTEGER_SHORT_FORM, ""),
                new OperationField("in4", true, null, "")}, 
            new OperationField[] {
                new OperationField("out1", false, Attribute.UOCTET_SHORT_FORM, ""),
                new OperationField("out2", false, Attribute.USHORT_SHORT_FORM, ""),
                new OperationField("out3", false, Attribute.UINTEGER_SHORT_FORM, ""),
                new OperationField("out4", true, null, "")}, 
            "This operation checks that multiple types with a final abstract type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTEMPTYBODY.
     */
    public static final int _TESTEMPTYBODY_OP_NUMBER = 124;

    /**
     * Operation number instance for operation TESTEMPTYBODY.
     */
    private static final UShort TESTEMPTYBODY_OP_NUMBER = new UShort(_TESTEMPTYBODY_OP_NUMBER);

    /**
     * Operation instance for operation TESTEMPTYBODY.
     */
    public static final MALRequestOperation TESTEMPTYBODY_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTEMPTYBODY_OP_NUMBER, 
            new Identifier("testEmptyBody"), 
            new UShort(100), 
            new OperationField[] {}, 
            new OperationField[] {}, 
            "This operation checks that an empty body can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTMALATTRIBUTE.
     */
    public static final int _TESTMALATTRIBUTE_OP_NUMBER = 125;

    /**
     * Operation number instance for operation TESTMALATTRIBUTE.
     */
    private static final UShort TESTMALATTRIBUTE_OP_NUMBER = new UShort(_TESTMALATTRIBUTE_OP_NUMBER);

    /**
     * Operation instance for operation TESTMALATTRIBUTE.
     */
    public static final MALRequestOperation TESTMALATTRIBUTE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTMALATTRIBUTE_OP_NUMBER, 
            new Identifier("testMalAttribute"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that a MAL::Attribute can be sent and received as an abstract Attribute");

    /**
     * Operation number literal for operation TESTMALCOMPOSITE.
     */
    public static final int _TESTMALCOMPOSITE_OP_NUMBER = 126;

    /**
     * Operation number instance for operation TESTMALCOMPOSITE.
     */
    private static final UShort TESTMALCOMPOSITE_OP_NUMBER = new UShort(_TESTMALCOMPOSITE_OP_NUMBER);

    /**
     * Operation instance for operation TESTMALCOMPOSITE.
     */
    public static final MALRequestOperation TESTMALCOMPOSITE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTMALCOMPOSITE_OP_NUMBER, 
            new Identifier("testMalComposite"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that a Composite can be sent and received as a MAL Composite. It is no longer used in the testbed.");

    /**
     * Operation number literal for operation TESTABSTRACTCOMPOSITE.
     */
    public static final int _TESTABSTRACTCOMPOSITE_OP_NUMBER = 127;

    /**
     * Operation number instance for operation TESTABSTRACTCOMPOSITE.
     */
    private static final UShort TESTABSTRACTCOMPOSITE_OP_NUMBER = new UShort(_TESTABSTRACTCOMPOSITE_OP_NUMBER);

    /**
     * Operation instance for operation TESTABSTRACTCOMPOSITE.
     */
    public static final MALRequestOperation TESTABSTRACTCOMPOSITE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTABSTRACTCOMPOSITE_OP_NUMBER, 
            new Identifier("testAbstractComposite"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that a Composite can be sent and received as an abstract Composite. It is no longer used in the testbed.");

    /**
     * Operation number literal for operation TESTMALATTRIBUTELIST.
     */
    public static final int _TESTMALATTRIBUTELIST_OP_NUMBER = 128;

    /**
     * Operation number instance for operation TESTMALATTRIBUTELIST.
     */
    private static final UShort TESTMALATTRIBUTELIST_OP_NUMBER = new UShort(_TESTMALATTRIBUTELIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTMALATTRIBUTELIST.
     */
    public static final MALRequestOperation TESTMALATTRIBUTELIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTMALATTRIBUTELIST_OP_NUMBER, 
            new Identifier("testMalAttributeList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that a list of MAL::Attribute can be sent and received explicitly. It is no longer used in the testbed.");

    /**
     * Operation number literal for operation TESTMALELEMENTLIST.
     */
    public static final int _TESTMALELEMENTLIST_OP_NUMBER = 129;

    /**
     * Operation number instance for operation TESTMALELEMENTLIST.
     */
    private static final UShort TESTMALELEMENTLIST_OP_NUMBER = new UShort(_TESTMALELEMENTLIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTMALELEMENTLIST.
     */
    public static final MALRequestOperation TESTMALELEMENTLIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTMALELEMENTLIST_OP_NUMBER, 
            new Identifier("testMalElementList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that a list of MAL::Element can be sent and received explicitly. It is no longer used in the testbed.");

    /**
     * Operation number literal for operation TESTMALCOMPOSITELIST.
     */
    public static final int _TESTMALCOMPOSITELIST_OP_NUMBER = 130;

    /**
     * Operation number instance for operation TESTMALCOMPOSITELIST.
     */
    private static final UShort TESTMALCOMPOSITELIST_OP_NUMBER = new UShort(_TESTMALCOMPOSITELIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTMALCOMPOSITELIST.
     */
    public static final MALRequestOperation TESTMALCOMPOSITELIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTMALCOMPOSITELIST_OP_NUMBER, 
            new Identifier("testMalCompositeList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that a list of MAL::Composite can be sent and received explicitly. It is no longer used in the testbed.");

    /**
     * Operation number literal for operation TESTABSTRACTCOMPOSITELIST.
     */
    public static final int _TESTABSTRACTCOMPOSITELIST_OP_NUMBER = 131;

    /**
     * Operation number instance for operation TESTABSTRACTCOMPOSITELIST.
     */
    private static final UShort TESTABSTRACTCOMPOSITELIST_OP_NUMBER = new UShort(_TESTABSTRACTCOMPOSITELIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTABSTRACTCOMPOSITELIST.
     */
    public static final MALRequestOperation TESTABSTRACTCOMPOSITELIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTABSTRACTCOMPOSITELIST_OP_NUMBER, 
            new Identifier("testAbstractCompositeList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that a list of abstract composite can be sent and received explicitly. It is no longer used in the testbed.");

    /**
     * Operation number literal for operation TESTDATAOBJECTREF.
     */
    public static final int _TESTDATAOBJECTREF_OP_NUMBER = 132;

    /**
     * Operation number instance for operation TESTDATAOBJECTREF.
     */
    private static final UShort TESTDATAOBJECTREF_OP_NUMBER = new UShort(_TESTDATAOBJECTREF_OP_NUMBER);

    /**
     * Operation instance for operation TESTDATAOBJECTREF.
     */
    public static final MALRequestOperation TESTDATAOBJECTREF_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDATAOBJECTREF_OP_NUMBER, 
            new Identifier("testDataObjectRef"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "This operation checks that a basic ObjectRef type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTINNERABSTRACTMULTIRETURN.
     */
    public static final int _TESTINNERABSTRACTMULTIRETURN_OP_NUMBER = 133;

    /**
     * Operation number instance for operation TESTINNERABSTRACTMULTIRETURN.
     */
    private static final UShort TESTINNERABSTRACTMULTIRETURN_OP_NUMBER = new UShort(_TESTINNERABSTRACTMULTIRETURN_OP_NUMBER);

    /**
     * Operation instance for operation TESTINNERABSTRACTMULTIRETURN.
     */
    public static final MALRequestOperation TESTINNERABSTRACTMULTIRETURN_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTINNERABSTRACTMULTIRETURN_OP_NUMBER, 
            new Identifier("testInnerAbstractMultiReturn"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("in1", true, Attribute.UOCTET_SHORT_FORM, ""),
                new OperationField("in2", true, null, ""),
                new OperationField("in3", true, null, ""),
                new OperationField("in4", true, Attribute.UINTEGER_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("out1", true, Attribute.UOCTET_SHORT_FORM, ""),
                new OperationField("out2", true, null, ""),
                new OperationField("out3", true, null, ""),
                new OperationField("out4", true, Attribute.UINTEGER_SHORT_FORM, "")}, 
            "This operation checks that multiple types with a not final abstract type can be sent and received explicitly");

    /**
     * Operation number literal for operation TESTPOLYMORPHICABSTRACTCOMPOSITELIST.
     */
    public static final int _TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER = 134;

    /**
     * Operation number instance for operation TESTPOLYMORPHICABSTRACTCOMPOSITELIST.
     */
    private static final UShort TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER = new UShort(_TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTPOLYMORPHICABSTRACTCOMPOSITELIST.
     */
    public static final MALRequestOperation TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER, 
            new Identifier("testPolymorphicAbstractCompositeList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that various concrete values can be sent and received explicitly as a list of abstract composite");

    /**
     * Operation number literal for operation TESTPOLYMORPHICMALCOMPOSITELIST.
     */
    public static final int _TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER = 135;

    /**
     * Operation number instance for operation TESTPOLYMORPHICMALCOMPOSITELIST.
     */
    private static final UShort TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER = new UShort(_TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTPOLYMORPHICMALCOMPOSITELIST.
     */
    public static final MALRequestOperation TESTPOLYMORPHICMALCOMPOSITELIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER, 
            new Identifier("testPolymorphicMalCompositeList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that various concrete values can be sent and received explicitly as a list of MAL Composite");

    /**
     * Operation number literal for operation TESTPOLYMORPHICMALELEMENTLIST.
     */
    public static final int _TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER = 136;

    /**
     * Operation number instance for operation TESTPOLYMORPHICMALELEMENTLIST.
     */
    private static final UShort TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER = new UShort(_TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER);

    /**
     * Operation instance for operation TESTPOLYMORPHICMALELEMENTLIST.
     */
    public static final MALRequestOperation TESTPOLYMORPHICMALELEMENTLIST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER, 
            new Identifier("testPolymorphicMalElementList"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input1", true, null, "")}, 
            new OperationField[] {
                new OperationField("output1", true, null, "")}, 
            "This operation checks that various concrete values can be sent and received explicitly as a list of MAL Element");

    /**
     * Operation number literal for operation TESTPOLYMORPHICOBJECTREFTYPES.
     */
    public static final int _TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER = 137;

    /**
     * Operation number instance for operation TESTPOLYMORPHICOBJECTREFTYPES.
     */
    private static final UShort TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER = new UShort(_TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER);

    /**
     * Operation instance for operation TESTPOLYMORPHICOBJECTREFTYPES.
     */
    public static final MALRequestOperation TESTPOLYMORPHICOBJECTREFTYPES_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER, 
            new Identifier("testPolymorphicObjectRefTypes"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("garage", true, Garage.SHORT_FORM, ""),
                new OperationField("porsches", true, ObjectRefList.SHORT_FORM, ""),
                new OperationField("autos", true, ObjectRefList.SHORT_FORM, ""),
                new OperationField("elements", true, ObjectRefList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output1", true, Garage.SHORT_FORM, ""),
                new OperationField("output2", true, ObjectRefList.SHORT_FORM, ""),
                new OperationField("output3", true, ObjectRefList.SHORT_FORM, ""),
                new OperationField("output4", true, ObjectRefList.SHORT_FORM, "")}, 
            "This operation checks the ObjectRef(T) polymorphism in operation type signature and in composite fields");

    /**
     * Operation number literal for operation CREATEOBJECT.
     */
    public static final int _CREATEOBJECT_OP_NUMBER = 201;

    /**
     * Operation number instance for operation CREATEOBJECT.
     */
    private static final UShort CREATEOBJECT_OP_NUMBER = new UShort(_CREATEOBJECT_OP_NUMBER);

    /**
     * Operation instance for operation CREATEOBJECT.
     */
    public static final MALRequestOperation CREATEOBJECT_OP = new MALRequestOperation(SERVICE_KEY, 
            CREATEOBJECT_OP_NUMBER, 
            new Identifier("createObject"), 
            new UShort(200), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "This operation creates a new MO Object from a full value");

    /**
     * Operation number literal for operation CREATEOBJECTFROMFIELDS.
     */
    public static final int _CREATEOBJECTFROMFIELDS_OP_NUMBER = 202;

    /**
     * Operation number instance for operation CREATEOBJECTFROMFIELDS.
     */
    private static final UShort CREATEOBJECTFROMFIELDS_OP_NUMBER = new UShort(_CREATEOBJECTFROMFIELDS_OP_NUMBER);

    /**
     * Operation instance for operation CREATEOBJECTFROMFIELDS.
     */
    public static final MALRequestOperation CREATEOBJECTFROMFIELDS_OP = new MALRequestOperation(SERVICE_KEY, 
            CREATEOBJECTFROMFIELDS_OP_NUMBER, 
            new Identifier("createObjectFromFields"), 
            new UShort(200), 
            new OperationField[] {
                new OperationField("autoType", true, Attribute.LONG_SHORT_FORM, ""),
                new OperationField("key", true, Attribute.IDENTIFIER_SHORT_FORM, ""),
                new OperationField("update", true, Attribute.BOOLEAN_SHORT_FORM, ""),
                new OperationField("engine", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("chassis", true, Attribute.STRING_SHORT_FORM, ""),
                new OperationField("windows", true, StringList.SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "This operation creates a new MO Object from fields values");

    /**
     * Operation number literal for operation DELETEOBJECT.
     */
    public static final int _DELETEOBJECT_OP_NUMBER = 203;

    /**
     * Operation number instance for operation DELETEOBJECT.
     */
    private static final UShort DELETEOBJECT_OP_NUMBER = new UShort(_DELETEOBJECT_OP_NUMBER);

    /**
     * Operation instance for operation DELETEOBJECT.
     */
    public static final MALSubmitOperation DELETEOBJECT_OP = new MALSubmitOperation(SERVICE_KEY, 
            DELETEOBJECT_OP_NUMBER, 
            new Identifier("deleteObject"), 
            new UShort(200), 
            new OperationField[] {
                new OperationField("input", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            "This operation deletes an MO Object");

    /**
     * Operation number literal for operation GETOBJECT.
     */
    public static final int _GETOBJECT_OP_NUMBER = 204;

    /**
     * Operation number instance for operation GETOBJECT.
     */
    private static final UShort GETOBJECT_OP_NUMBER = new UShort(_GETOBJECT_OP_NUMBER);

    /**
     * Operation instance for operation GETOBJECT.
     */
    public static final MALRequestOperation GETOBJECT_OP = new MALRequestOperation(SERVICE_KEY, 
            GETOBJECT_OP_NUMBER, 
            new Identifier("getObject"), 
            new UShort(200), 
            new OperationField[] {
                new OperationField("input", true, ObjectRef.OBJECTREF_SHORT_FORM, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation gets an MO Object value from its reference");

    /**
     * Area elements.
     */
    public static final Element[] DATATEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{SETTESTDATAOFFSET_OP,
        TESTDATA_OP,
        TESTDATABLOB_OP,
        TESTDATABOOLEAN_OP,
        TESTDATADOUBLE_OP,
        TESTDATADURATION_OP,
        TESTDATAFINETIME_OP,
        TESTDATAFLOAT_OP,
        TESTDATAIDENTIFIER_OP,
        TESTDATAINTEGER_OP,
        TESTDATALONG_OP,
        TESTDATAOCTET_OP,
        TESTDATASHORT_OP,
        TESTDATASTRING_OP,
        TESTDATATIME_OP,
        TESTDATAURI_OP,
        TESTDATACOMPOSITE_OP,
        TESTDATAENUMERATION_OP,
        TESTDATALIST_OP,
        TESTDATAUINTEGER_OP,
        TESTDATAULONG_OP,
        TESTDATAUOCTET_OP,
        TESTDATAUSHORT_OP,
        TESTEXPLICITMULTIRETURN_OP,
        TESTABSTRACTMULTIRETURN_OP,
        TESTEMPTYBODY_OP,
        TESTMALATTRIBUTE_OP,
        TESTMALCOMPOSITE_OP,
        TESTABSTRACTCOMPOSITE_OP,
        TESTMALATTRIBUTELIST_OP,
        TESTMALELEMENTLIST_OP,
        TESTMALCOMPOSITELIST_OP,
        TESTABSTRACTCOMPOSITELIST_OP,
        TESTDATAOBJECTREF_OP,
        TESTINNERABSTRACTMULTIRETURN_OP,
        TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP,
        TESTPOLYMORPHICMALCOMPOSITELIST_OP,
        TESTPOLYMORPHICMALELEMENTLIST_OP,
        TESTPOLYMORPHICOBJECTREFTYPES_OP,
        CREATEOBJECT_OP,
        CREATEOBJECTFROMFIELDS_OP,
        DELETEOBJECT_OP,
        GETOBJECT_OP};

    /**
     * Creates an instance of the DataTest ServiceInfo.
     * 
     */
    public DataTestServiceInfo() {
        super(SERVICE_KEY, DATATEST_SERVICE_NAME, DATATEST_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MALPrototypeHelper.MALPROTOTYPE_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 100:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 101:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 102:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 103:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 104:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 105:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 106:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 107:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 108:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 109:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 110:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 111:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 112:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 113:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 114:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 115:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 116:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 117:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 118:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 119:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 120:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 121:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 122:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 123:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 124:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 125:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 126:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 127:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 128:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 129:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 130:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 131:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 132:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 133:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 134:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 135:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 136:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 137:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 201:
                switch (errorNumber) {
                    case 2:
                        return new TestObjectExistsException(extraInfo);
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 202:
                switch (errorNumber) {
                    case 2:
                        return new TestObjectExistsException(extraInfo);
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 203:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
            case 204:
                switch (errorNumber) {
                    case 1:
                        return new DataErrorException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MALPrototypeHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
