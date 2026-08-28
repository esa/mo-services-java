package org.ccsds.moims.mo.mpd.ordermanagement;

/**
 * Helper class for OrderManagement service.
 */
public class OrderManagementServiceInfo extends org.ccsds.moims.mo.mal.ServiceInfo {

    /**
     * Service number literal.
     */
    public static final int _ORDERMANAGEMENT_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort ORDERMANAGEMENT_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_ORDERMANAGEMENT_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier ORDERMANAGEMENT_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("OrderManagement");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            9, 1, ORDERMANAGEMENT_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LISTSTANDINGORDERS.
     */
    public static final int _LISTSTANDINGORDERS_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LISTSTANDINGORDERS.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTSTANDINGORDERS_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTSTANDINGORDERS_OP_NUMBER);

    /**
     * Operation instance for operation LISTSTANDINGORDERS.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LISTSTANDINGORDERS_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LISTSTANDINGORDERS_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listStandingOrders"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("user", true, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, "The user of the standing order(s) to be listed."),
                new org.ccsds.moims.mo.mal.OperationField("domain", true, org.ccsds.moims.mo.mal.structures.IdentifierList.SHORT_FORM, "The domain of the standing order(s) to be listed.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("standingOrders", false, org.ccsds.moims.mo.mpd.structures.StandingOrderList.SHORT_FORM, "The standing orders that match the selected criteria.")}, 
            "The listStandingOrders operation lists the existing standing orders on the service provider for a given user and domain.");

    /**
     * Operation number literal for operation SUBMITSTANDINGORDER.
     */
    public static final int _SUBMITSTANDINGORDER_OP_NUMBER = 2;

    /**
     * Operation number instance for operation SUBMITSTANDINGORDER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort SUBMITSTANDINGORDER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_SUBMITSTANDINGORDER_OP_NUMBER);

    /**
     * Operation instance for operation SUBMITSTANDINGORDER.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation SUBMITSTANDINGORDER_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            SUBMITSTANDINGORDER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("submitStandingOrder"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("orderDetails", false, org.ccsds.moims.mo.mpd.structures.StandingOrder.SHORT_FORM, "The details of the order to be submitted for processing.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("orderID", false, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The unique id of the standing order.")}, 
            "The submitStandingOrder operation creates a new standing order in the provider for delivery of mission data products.");

    /**
     * Operation number literal for operation CANCELSTANDINGORDER.
     */
    public static final int _CANCELSTANDINGORDER_OP_NUMBER = 3;

    /**
     * Operation number instance for operation CANCELSTANDINGORDER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort CANCELSTANDINGORDER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_CANCELSTANDINGORDER_OP_NUMBER);

    /**
     * Operation instance for operation CANCELSTANDINGORDER.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation CANCELSTANDINGORDER_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            CANCELSTANDINGORDER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("cancelStandingOrder"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("orderID", false, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The unique id of the standing order to be cancelled.")}, 
            "The cancelStandingOrder operation cancels an existing standing order.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] ORDERMANAGEMENT_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{LISTSTANDINGORDERS_OP,
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
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.mpd.MPDHelper.MPD_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
            case 1:
                return new org.ccsds.moims.mo.mpd.InvalidException(extraInfo);
            case 2:
                return new org.ccsds.moims.mo.mpd.DeliveryFailedException(extraInfo);
            case 3:
                return new org.ccsds.moims.mo.mpd.OrderFailedException(extraInfo);
            case 4:
                return new org.ccsds.moims.mo.mpd.UnknownException(extraInfo);
            case 5:
                return new org.ccsds.moims.mo.mpd.TooManyException(extraInfo);
        }
        return null;
    }

}
