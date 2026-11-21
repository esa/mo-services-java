package org.ccsds.moims.mo.common.login.consumer;

import java.util.Map;
import org.ccsds.moims.mo.common.login.LoginServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Consumer adapter for Login service.
 */
public abstract class LoginAdapter extends MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation login.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param authId The returned authId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the user and role in use.
     * @param objInstId The returned objInstId field shall contain the LoginInstance COM object instance identifier that was created by the login operation.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void loginResponseReceived(MALMessageHeader msgHeader,
            Blob authId,
            Long objInstId,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation login.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void loginErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation logout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void logoutAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation logout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void logoutErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listRoles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param permittedRoles The operation shall return a list of LoginRole object instance identifiers that are permitted for the user or NULL if roles are not used by the system.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRolesResponseReceived(MALMessageHeader msgHeader,
            LongList permittedRoles,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listRoles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRolesErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation handover.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param newAuthId The returned newAuthId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the new user and role in use.
     * @param newLoginInstId The returned newLoginInstId field shall contain the new LoginInstance COM object instance identifier that was created by the operation.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void handoverResponseReceived(MALMessageHeader msgHeader,
            Blob newAuthId,
            Long newLoginInstId,
            Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation handover.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void handoverErrorReceived(MALMessageHeader msgHeader,
            MOErrorException error,
            Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(MALMessageHeader msgHeader,
            Map qosProperties) throws MALException {
        switch (msgHeader.getOperation().getValue()) {
          case LoginServiceInfo._LOGOUT_OP_NUMBER:
            logoutAckReceived(msgHeader, qosProperties);
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
          case LoginServiceInfo._LOGOUT_OP_NUMBER:
            logoutErrorReceived(msgHeader, body.getError(), qosProperties);
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
          case LoginServiceInfo._LOGIN_OP_NUMBER:
            loginResponseReceived(msgHeader,
                (Blob) body.getBodyElement(0, new Blob()),
                (body.getBodyElement(1, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(1, new Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
            break;
          case LoginServiceInfo._LISTROLES_OP_NUMBER:
            listRolesResponseReceived(msgHeader,
                (LongList) body.getBodyElement(0, new LongList()), qosProperties);
            break;
          case LoginServiceInfo._HANDOVER_OP_NUMBER:
            handoverResponseReceived(msgHeader,
                (Blob) body.getBodyElement(0, new Blob()),
                (body.getBodyElement(1, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(1, new Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
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
          case LoginServiceInfo._LOGIN_OP_NUMBER:
            loginErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case LoginServiceInfo._LISTROLES_OP_NUMBER:
            listRolesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case LoginServiceInfo._HANDOVER_OP_NUMBER:
            handoverErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
