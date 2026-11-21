package org.ccsds.moims.mo.malprototype.errortest;

import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;

/**
 * Helper class for ErrorTest service.
 */
public class ErrorTestServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ERRORTEST_SERVICE_NUMBER = 3;

    /**
     * Service number instance.
     */
    public static final UShort ERRORTEST_SERVICE_NUMBER = new UShort(_ERRORTEST_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ERRORTEST_SERVICE_NAME = new Identifier("ErrorTest");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            100, 1, ERRORTEST_SERVICE_NUMBER);

    /**
     * Operation number literal for operation TESTDELIVERYFAILED.
     */
    public static final int _TESTDELIVERYFAILED_OP_NUMBER = 100;

    /**
     * Operation number instance for operation TESTDELIVERYFAILED.
     */
    private static final UShort TESTDELIVERYFAILED_OP_NUMBER = new UShort(_TESTDELIVERYFAILED_OP_NUMBER);

    /**
     * Operation instance for operation TESTDELIVERYFAILED.
     */
    public static final MALRequestOperation TESTDELIVERYFAILED_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDELIVERYFAILED_OP_NUMBER, 
            new Identifier("testDeliveryFailed"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDELIVERYTIMEDOUT.
     */
    public static final int _TESTDELIVERYTIMEDOUT_OP_NUMBER = 101;

    /**
     * Operation number instance for operation TESTDELIVERYTIMEDOUT.
     */
    private static final UShort TESTDELIVERYTIMEDOUT_OP_NUMBER = new UShort(_TESTDELIVERYTIMEDOUT_OP_NUMBER);

    /**
     * Operation instance for operation TESTDELIVERYTIMEDOUT.
     */
    public static final MALRequestOperation TESTDELIVERYTIMEDOUT_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDELIVERYTIMEDOUT_OP_NUMBER, 
            new Identifier("testDeliveryTimedout"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDELIVERYDELAYED.
     */
    public static final int _TESTDELIVERYDELAYED_OP_NUMBER = 102;

    /**
     * Operation number instance for operation TESTDELIVERYDELAYED.
     */
    private static final UShort TESTDELIVERYDELAYED_OP_NUMBER = new UShort(_TESTDELIVERYDELAYED_OP_NUMBER);

    /**
     * Operation instance for operation TESTDELIVERYDELAYED.
     */
    public static final MALRequestOperation TESTDELIVERYDELAYED_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDELIVERYDELAYED_OP_NUMBER, 
            new Identifier("testDeliveryDelayed"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDESTINATIONUNKNOWN.
     */
    public static final int _TESTDESTINATIONUNKNOWN_OP_NUMBER = 103;

    /**
     * Operation number instance for operation TESTDESTINATIONUNKNOWN.
     */
    private static final UShort TESTDESTINATIONUNKNOWN_OP_NUMBER = new UShort(_TESTDESTINATIONUNKNOWN_OP_NUMBER);

    /**
     * Operation instance for operation TESTDESTINATIONUNKNOWN.
     */
    public static final MALRequestOperation TESTDESTINATIONUNKNOWN_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDESTINATIONUNKNOWN_OP_NUMBER, 
            new Identifier("testDestinationUnknown"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDESTINATIONTRANSIENT.
     */
    public static final int _TESTDESTINATIONTRANSIENT_OP_NUMBER = 104;

    /**
     * Operation number instance for operation TESTDESTINATIONTRANSIENT.
     */
    private static final UShort TESTDESTINATIONTRANSIENT_OP_NUMBER = new UShort(_TESTDESTINATIONTRANSIENT_OP_NUMBER);

    /**
     * Operation instance for operation TESTDESTINATIONTRANSIENT.
     */
    public static final MALRequestOperation TESTDESTINATIONTRANSIENT_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDESTINATIONTRANSIENT_OP_NUMBER, 
            new Identifier("testDestinationTransient"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTDESTINATIONLOST.
     */
    public static final int _TESTDESTINATIONLOST_OP_NUMBER = 105;

    /**
     * Operation number instance for operation TESTDESTINATIONLOST.
     */
    private static final UShort TESTDESTINATIONLOST_OP_NUMBER = new UShort(_TESTDESTINATIONLOST_OP_NUMBER);

    /**
     * Operation instance for operation TESTDESTINATIONLOST.
     */
    public static final MALRequestOperation TESTDESTINATIONLOST_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTDESTINATIONLOST_OP_NUMBER, 
            new Identifier("testDestinationLost"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTENCRYPTIONFAIL.
     */
    public static final int _TESTENCRYPTIONFAIL_OP_NUMBER = 106;

    /**
     * Operation number instance for operation TESTENCRYPTIONFAIL.
     */
    private static final UShort TESTENCRYPTIONFAIL_OP_NUMBER = new UShort(_TESTENCRYPTIONFAIL_OP_NUMBER);

    /**
     * Operation instance for operation TESTENCRYPTIONFAIL.
     */
    public static final MALRequestOperation TESTENCRYPTIONFAIL_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTENCRYPTIONFAIL_OP_NUMBER, 
            new Identifier("testEncryptionFail"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDAREA.
     */
    public static final int _TESTUNSUPPORTEDAREA_OP_NUMBER = 107;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDAREA.
     */
    private static final UShort TESTUNSUPPORTEDAREA_OP_NUMBER = new UShort(_TESTUNSUPPORTEDAREA_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDAREA.
     */
    public static final MALRequestOperation TESTUNSUPPORTEDAREA_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDAREA_OP_NUMBER, 
            new Identifier("testUnsupportedArea"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDOPERATION.
     */
    public static final int _TESTUNSUPPORTEDOPERATION_OP_NUMBER = 108;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDOPERATION.
     */
    private static final UShort TESTUNSUPPORTEDOPERATION_OP_NUMBER = new UShort(_TESTUNSUPPORTEDOPERATION_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDOPERATION.
     */
    public static final MALRequestOperation TESTUNSUPPORTEDOPERATION_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDOPERATION_OP_NUMBER, 
            new Identifier("testUnsupportedOperation"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDAREAVERSION.
     */
    public static final int _TESTUNSUPPORTEDAREAVERSION_OP_NUMBER = 109;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDAREAVERSION.
     */
    private static final UShort TESTUNSUPPORTEDAREAVERSION_OP_NUMBER = new UShort(_TESTUNSUPPORTEDAREAVERSION_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDAREAVERSION.
     */
    public static final MALRequestOperation TESTUNSUPPORTEDAREAVERSION_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDAREAVERSION_OP_NUMBER, 
            new Identifier("testUnsupportedAreaVersion"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTBADENCODING.
     */
    public static final int _TESTBADENCODING_OP_NUMBER = 110;

    /**
     * Operation number instance for operation TESTBADENCODING.
     */
    private static final UShort TESTBADENCODING_OP_NUMBER = new UShort(_TESTBADENCODING_OP_NUMBER);

    /**
     * Operation instance for operation TESTBADENCODING.
     */
    public static final MALRequestOperation TESTBADENCODING_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTBADENCODING_OP_NUMBER, 
            new Identifier("testBadEncoding"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNKNOWN.
     */
    public static final int _TESTUNKNOWN_OP_NUMBER = 111;

    /**
     * Operation number instance for operation TESTUNKNOWN.
     */
    private static final UShort TESTUNKNOWN_OP_NUMBER = new UShort(_TESTUNKNOWN_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNKNOWN.
     */
    public static final MALRequestOperation TESTUNKNOWN_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTUNKNOWN_OP_NUMBER, 
            new Identifier("testUnknown"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTAUTHENTICATIONFAILURE.
     */
    public static final int _TESTAUTHENTICATIONFAILURE_OP_NUMBER = 112;

    /**
     * Operation number instance for operation TESTAUTHENTICATIONFAILURE.
     */
    private static final UShort TESTAUTHENTICATIONFAILURE_OP_NUMBER = new UShort(_TESTAUTHENTICATIONFAILURE_OP_NUMBER);

    /**
     * Operation instance for operation TESTAUTHENTICATIONFAILURE.
     */
    public static final MALRequestOperation TESTAUTHENTICATIONFAILURE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTAUTHENTICATIONFAILURE_OP_NUMBER, 
            new Identifier("testAuthenticationFailure"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the MAL layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTAUTHORIZATIONFAILURE.
     */
    public static final int _TESTAUTHORIZATIONFAILURE_OP_NUMBER = 113;

    /**
     * Operation number instance for operation TESTAUTHORIZATIONFAILURE.
     */
    private static final UShort TESTAUTHORIZATIONFAILURE_OP_NUMBER = new UShort(_TESTAUTHORIZATIONFAILURE_OP_NUMBER);

    /**
     * Operation instance for operation TESTAUTHORIZATIONFAILURE.
     */
    public static final MALRequestOperation TESTAUTHORIZATIONFAILURE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTAUTHORIZATIONFAILURE_OP_NUMBER, 
            new Identifier("testAuthorizationFailure"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the MAL layer before the provider is invoked.");

    /**
     * Operation number literal for operation TESTUNSUPPORTEDSERVICE.
     */
    public static final int _TESTUNSUPPORTEDSERVICE_OP_NUMBER = 114;

    /**
     * Operation number instance for operation TESTUNSUPPORTEDSERVICE.
     */
    private static final UShort TESTUNSUPPORTEDSERVICE_OP_NUMBER = new UShort(_TESTUNSUPPORTEDSERVICE_OP_NUMBER);

    /**
     * Operation instance for operation TESTUNSUPPORTEDSERVICE.
     */
    public static final MALRequestOperation TESTUNSUPPORTEDSERVICE_OP = new MALRequestOperation(SERVICE_KEY, 
            TESTUNSUPPORTEDSERVICE_OP_NUMBER, 
            new Identifier("testUnsupportedService"), 
            new UShort(100), 
            new OperationField[] {
                new OperationField("input", true, null, "")}, 
            new OperationField[] {
                new OperationField("output", true, null, "")}, 
            "This operation does nothing. Actually the error is raised by the transport layer before the provider is invoked.");

    /**
     * Area elements.
     */
    public static final Element[] ERRORTEST_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{TESTDELIVERYFAILED_OP,
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
    public MALArea getArea() {
        return MALPrototypeHelper.MALPROTOTYPE_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        MOErrorException areaError = MALPrototypeHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
