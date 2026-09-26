package org.ccsds.moims.mo.malprototype.errortest;

/**
 * Helper class for ErrorTest service.
 */
public class ErrorTestServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ERRORTEST_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ERRORTEST_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ERRORTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ERRORTEST_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("ErrorTest");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            100, 1, ERRORTEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation TESTDELIVERYFAILED.
     */
    public static final int _TESTDELIVERYFAILED_OP_NUMBER = 100;

    /**
     * Operation number instance for operation TESTDELIVERYFAILED.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTDELIVERYFAILED_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTDELIVERYFAILED_OP_NUMBER);

    /**
     * Operation instance for operation TESTDELIVERYFAILED.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTDELIVERYFAILED_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTDELIVERYFAILED_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testDeliveryFailed"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDELIVERYTIMEDOUT.
     */
    public static final int _TESTDELIVERYTIMEDOUT_OP_NUMBER = 101;

    /**
     * Operation number instance for operation TESTDELIVERYTIMEDOUT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTDELIVERYTIMEDOUT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTDELIVERYTIMEDOUT_OP_NUMBER);

    /**
     * Operation instance for operation TESTDELIVERYTIMEDOUT.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTDELIVERYTIMEDOUT_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTDELIVERYTIMEDOUT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testDeliveryTimedout"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDELIVERYDELAYED.
     */
    public static final int _TESTDELIVERYDELAYED_OP_NUMBER = 102;

    /**
     * Operation number instance for operation TESTDELIVERYDELAYED.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTDELIVERYDELAYED_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTDELIVERYDELAYED_OP_NUMBER);

    /**
     * Operation instance for operation TESTDELIVERYDELAYED.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTDELIVERYDELAYED_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTDELIVERYDELAYED_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testDeliveryDelayed"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDESTINATIONUNKNOWN.
     */
    public static final int _TESTDESTINATIONUNKNOWN_OP_NUMBER = 103;

    /**
     * Operation number instance for operation TESTDESTINATIONUNKNOWN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTDESTINATIONUNKNOWN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTDESTINATIONUNKNOWN_OP_NUMBER);

    /**
     * Operation instance for operation TESTDESTINATIONUNKNOWN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTDESTINATIONUNKNOWN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTDESTINATIONUNKNOWN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testDestinationUnknown"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDESTINATIONTRANSIENT.
     */
    public static final int _TESTDESTINATIONTRANSIENT_OP_NUMBER = 104;

    /**
     * Operation number instance for operation TESTDESTINATIONTRANSIENT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTDESTINATIONTRANSIENT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTDESTINATIONTRANSIENT_OP_NUMBER);

    /**
     * Operation instance for operation TESTDESTINATIONTRANSIENT.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTDESTINATIONTRANSIENT_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTDESTINATIONTRANSIENT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testDestinationTransient"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDESTINATIONLOST.
     */
    public static final int _TESTDESTINATIONLOST_OP_NUMBER = 105;

    /**
     * Operation number instance for operation TESTDESTINATIONLOST.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTDESTINATIONLOST_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTDESTINATIONLOST_OP_NUMBER);

    /**
     * Operation instance for operation TESTDESTINATIONLOST.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTDESTINATIONLOST_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTDESTINATIONLOST_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testDestinationLost"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTENCRYPTIONFAIL.
     */
    public static final int _TESTENCRYPTIONFAIL_OP_NUMBER = 106;

    /**
     * Operation number instance for operation TESTENCRYPTIONFAIL.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTENCRYPTIONFAIL_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTENCRYPTIONFAIL_OP_NUMBER);

    /**
     * Operation instance for operation TESTENCRYPTIONFAIL.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTENCRYPTIONFAIL_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTENCRYPTIONFAIL_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testEncryptionFail"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDAREA.
     */
    public static final int _TESTUNSUPPORTEDAREA_OP_NUMBER = 107;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDAREA.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTUNSUPPORTEDAREA_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTUNSUPPORTEDAREA_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDAREA.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTUNSUPPORTEDAREA_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDAREA_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testUnsupportedArea"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDOPERATION.
     */
    public static final int _TESTUNSUPPORTEDOPERATION_OP_NUMBER = 108;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDOPERATION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTUNSUPPORTEDOPERATION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTUNSUPPORTEDOPERATION_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDOPERATION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTUNSUPPORTEDOPERATION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDOPERATION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testUnsupportedOperation"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDAREAVERSION.
     */
    public static final int _TESTUNSUPPORTEDAREAVERSION_OP_NUMBER = 109;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDAREAVERSION.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTUNSUPPORTEDAREAVERSION_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTUNSUPPORTEDAREAVERSION_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDAREAVERSION.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTUNSUPPORTEDAREAVERSION_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDAREAVERSION_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testUnsupportedAreaVersion"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTBADENCODING.
     */
    public static final int _TESTBADENCODING_OP_NUMBER = 110;

    /**
     * Operation number instance for operation TESTBADENCODING.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTBADENCODING_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTBADENCODING_OP_NUMBER);

    /**
     * Operation instance for operation TESTBADENCODING.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTBADENCODING_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTBADENCODING_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testBadEncoding"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNKNOWN.
     */
    public static final int _TESTUNKNOWN_OP_NUMBER = 111;

    /**
     * Operation number instance for operation TESTUNKNOWN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTUNKNOWN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTUNKNOWN_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNKNOWN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTUNKNOWN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTUNKNOWN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testUnknown"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTAUTHENTICATIONFAILURE.
     */
    public static final int _TESTAUTHENTICATIONFAILURE_OP_NUMBER = 112;

    /**
     * Operation number instance for operation TESTAUTHENTICATIONFAILURE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTAUTHENTICATIONFAILURE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTAUTHENTICATIONFAILURE_OP_NUMBER);

    /**
     * Operation instance for operation TESTAUTHENTICATIONFAILURE.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTAUTHENTICATIONFAILURE_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTAUTHENTICATIONFAILURE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testAuthenticationFailure"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the MAL layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTAUTHORIZATIONFAILURE.
     */
    public static final int _TESTAUTHORIZATIONFAILURE_OP_NUMBER = 113;

    /**
     * Operation number instance for operation TESTAUTHORIZATIONFAILURE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTAUTHORIZATIONFAILURE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTAUTHORIZATIONFAILURE_OP_NUMBER);

    /**
     * Operation instance for operation TESTAUTHORIZATIONFAILURE.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTAUTHORIZATIONFAILURE_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTAUTHORIZATIONFAILURE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testAuthorizationFailure"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the MAL layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDSERVICE.
     */
    public static final int _TESTUNSUPPORTEDSERVICE_OP_NUMBER = 114;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDSERVICE.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort TESTUNSUPPORTEDSERVICE_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_TESTUNSUPPORTEDSERVICE_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDSERVICE.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation TESTUNSUPPORTEDSERVICE_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDSERVICE_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("testUnsupportedService"), 
            new org.ccsds.moims.mo.mal.structures.UShort(100), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("input", true, null, "")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ERRORTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{TESTDELIVERYFAILED_OP,
        TESTDELIVERYTIMEDOUT_OP,
        TESTDELIVERYDELAYED_OP,
        TESTDESTINATIONUNKNOWN_OP,
        TESTDESTINATIONTRANSIENT_OP,
        TESTDESTINATIONLOST_OP,
        TESTENCRYPTIONFAIL_OP,
        TESTUNSUPPORTEDAREA_OP,
        TESTUNSUPPORTEDOPERATION_OP,
        TESTUNSUPPORTEDAREAVERSION_OP,
        TESTBADENCODING_OP,
        TESTUNKNOWN_OP,
        TESTAUTHENTICATIONFAILURE_OP,
        TESTAUTHORIZATIONFAILURE_OP,
        TESTUNSUPPORTEDSERVICE_OP};

    /**
     * Creates an instance of the ErrorTest ServiceInfo.
     * 
     */
    public ErrorTestServiceInfo() {
        super(SERVICE_KEY, ERRORTEST_SERVICE_NAME, ERRORTEST_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.malprototype.MALPrototypeHelper.MALPROTOTYPE_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.malprototype.DataErrorException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.malprototype.TestObjectExistsException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.malprototype.TestErrorException(extraInfo);
        }
        return null;
    }

}
