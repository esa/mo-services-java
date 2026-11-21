package org.ccsds.moims.mo.mpd.ordermanagement;

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
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mpd.InvalidException;
import org.ccsds.moims.mo.mpd.MPDHelper;
import org.ccsds.moims.mo.mpd.OrderFailedException;
import org.ccsds.moims.mo.mpd.UnknownException;
import org.ccsds.moims.mo.mpd.structures.StandingOrder;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;

/**
 * Helper class for OrderManagement service.
 */
public class OrderManagementServiceInfo extends ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ORDERMANAGEMENT_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final UShort ORDERMANAGEMENT_SERVICE_NUMBER = new UShort(_ORDERMANAGEMENT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier ORDERMANAGEMENT_SERVICE_NAME = new Identifier("OrderManagement");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            9, 1, ORDERMANAGEMENT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LISTSTANDINGORDERS.
     */
    public static final int _LISTSTANDINGORDERS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LISTSTANDINGORDERS.
     */
    private static final UShort LISTSTANDINGORDERS_OP_NUMBER = new UShort(_LISTSTANDINGORDERS_OP_NUMBER);

    /**
     * Operation instance for operation LISTSTANDINGORDERS.
     */
    public static final MALRequestOperation LISTSTANDINGORDERS_OP = new MALRequestOperation(SERVICE_KEY, 
            LISTSTANDINGORDERS_OP_NUMBER, 
            new Identifier("listStandingOrders"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("user", true, Attribute.IDENTIFIER_SHORT_FORM, "The user of the standing order(s) to be listed."),
                new OperationField("domain", true, IdentifierList.SHORT_FORM, "The domain of the standing order(s) to be listed.")}, 
            new OperationField[] {
                new OperationField("standingOrders", false, StandingOrderList.SHORT_FORM, "The standing orders that match the selected criteria.")}, 
            "The listStandingOrders operation lists the existing standing orders on the service provider for a given user and domain.");

    /**
     * Operation number literal for operation SUBMITSTANDINGORDER.
     */
    public static final int _SUBMITSTANDINGORDER_OP_NUMBER = 2;

    /**
     * Operation number instance for operation SUBMITSTANDINGORDER.
     */
    private static final UShort SUBMITSTANDINGORDER_OP_NUMBER = new UShort(_SUBMITSTANDINGORDER_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITSTANDINGORDER.
     */
    public static final MALRequestOperation SUBMITSTANDINGORDER_OP = new MALRequestOperation(SERVICE_KEY, 
            SUBMITSTANDINGORDER_OP_NUMBER, 
            new Identifier("submitStandingOrder"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("orderDetails", false, StandingOrder.SHORT_FORM, "The details of the order to be submitted for processing.")}, 
            new OperationField[] {
                new OperationField("orderID", false, Attribute.LONG_SHORT_FORM, "The unique id of the standing order.")}, 
            "The submitStandingOrder operation creates a new standing order in the provider for delivery of mission data products.");

    /**
     * Operation number literal for operation CANCELSTANDINGORDER.
     */
    public static final int _CANCELSTANDINGORDER_OP_NUMBER = 3;

    /**
     * Operation number instance for operation CANCELSTANDINGORDER.
     */
    private static final UShort CANCELSTANDINGORDER_OP_NUMBER = new UShort(_CANCELSTANDINGORDER_OP_NUMBER);

    /**
     * Operation instance for operation CANCELSTANDINGORDER.
     */
    public static final MALSubmitOperation CANCELSTANDINGORDER_OP = new MALSubmitOperation(SERVICE_KEY, 
            CANCELSTANDINGORDER_OP_NUMBER, 
            new Identifier("cancelStandingOrder"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("orderID", false, Attribute.LONG_SHORT_FORM, "The unique id of the standing order to be cancelled.")}, 
            "The cancelStandingOrder operation cancels an existing standing order.");

    /**
     * Area elements.
     */
    public static final Element[] ORDERMANAGEMENT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{LISTSTANDINGORDERS_OP,
        SUBMITSTANDINGORDER_OP,
        CANCELSTANDINGORDER_OP};

    /**
     * Creates an instance of the OrderManagement ServiceInfo.
     * 
     */
    public OrderManagementServiceInfo() {
        super(SERVICE_KEY, ORDERMANAGEMENT_SERVICE_NAME, ORDERMANAGEMENT_SERVICE_ELEMENTS, OPERATIONS);
    }

    @Override
    public MALArea getArea() {
        return MPDHelper.MPD_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 2:
                switch (errorNumber) {
                    case 1:
                        return new InvalidException(extraInfo);
                    case 3:
                        return new OrderFailedException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 4:
                        return new UnknownException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = MPDHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
