package org.ccsds.moims.mo.mpd.ordermanagement.consumer;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mpd.ordermanagement.OrderManagementServiceInfo;
import org.ccsds.moims.mo.mpd.structures.StandingOrderList;

/**
 * Consumer adapter for OrderManagement service.
 */
public abstract class OrderManagementAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listStandingOrders.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param standingOrders The standing orders that match the selected criteria.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listStandingOrdersResponseReceived(MALMessageHeader msgHeader,
            StandingOrderList standingOrders,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listStandingOrders.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listStandingOrdersErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation submitStandingOrder.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param orderID The unique id of the standing order.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitStandingOrderResponseReceived(MALMessageHeader msgHeader,
            Long orderID,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation submitStandingOrder.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void submitStandingOrderErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation cancelStandingOrder.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void cancelStandingOrderAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation cancelStandingOrder.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void cancelStandingOrderErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case OrderManagementServiceInfo._CANCELSTANDINGORDER_OP_NUMBER:
            cancelStandingOrderAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case OrderManagementServiceInfo._CANCELSTANDINGORDER_OP_NUMBER:
            cancelStandingOrderErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(MALMessageHeader msgHeader,
            MALMessageBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case OrderManagementServiceInfo._LISTSTANDINGORDERS_OP_NUMBER:
            listStandingOrdersResponseReceived(msgHeader,
                (StandingOrderList) body.getBodyElement(0, new StandingOrderList()), qosProperties);
            break;
          case OrderManagementServiceInfo._SUBMITSTANDINGORDER_OP_NUMBER:
            submitStandingOrderResponseReceived(msgHeader,
                (body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(MALMessageHeader msgHeader,
            MALErrorBody body,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case OrderManagementServiceInfo._LISTSTANDINGORDERS_OP_NUMBER:
            listStandingOrdersErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case OrderManagementServiceInfo._SUBMITSTANDINGORDER_OP_NUMBER:
            submitStandingOrderErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
