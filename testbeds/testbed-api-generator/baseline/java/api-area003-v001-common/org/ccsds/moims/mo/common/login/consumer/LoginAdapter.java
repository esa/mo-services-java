package org.ccsds.moims.mo.common.login.consumer;

/**
 * Consumer adapter for Login service.
 */
public abstract class LoginAdapter extends org.ccsds.moims.mo.mal.consumer.MALInteractionAdapter {

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation login.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param authId The returned authId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the user and role in use.
     * @param objInstId The returned objInstId field shall contain the LoginInstance COM object instance identifier that was created by the login operation.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void loginResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Blob authId,
            Long objInstId,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation login.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void loginErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement is received from a provider
     * for the operation logout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void logoutAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a SUBMIT acknowledgement error is received from
     * a provider for the operation logout.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void logoutErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response is received from a provider for
     * the operation listRoles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param permittedRoles The operation shall return a list of LoginRole object instance identifiers that are permitted for the user or NULL if roles are not used by the system.
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRolesResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.LongList permittedRoles,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation listRoles.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void listRolesErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
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
    public void handoverResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.structures.Blob newAuthId,
            Long newLoginInstId,
            java.util.Map qosProperties) {
    }

    /**
     * Called by the MAL when a REQUEST response error is received from a provider
     * for the operation handover.
     * 
     * @param msgHeader msgHeader The header of the received message
     * @param error error The received error message
     * @param qosProperties qosProperties The QoS properties associated with the message
     */
    public void handoverErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.MOErrorException error,
            java.util.Map qosProperties) {
    }

    @Override
    public final void submitAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._LOGOUT_OP_NUMBER:
            logoutAckReceived(msgHeader, qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void submitErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._LOGOUT_OP_NUMBER:
            logoutErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._LOGIN_OP_NUMBER:
            loginResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Blob) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Blob()),
                (body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._LISTROLES_OP_NUMBER:
            listRolesResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.LongList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList()), qosProperties);
            break;
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._HANDOVER_OP_NUMBER:
            handoverResponseReceived(msgHeader,
                (org.ccsds.moims.mo.mal.structures.Blob) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Blob()),
                (body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

    @Override
    public final void requestErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader,
            org.ccsds.moims.mo.mal.transport.MALErrorBody body,
            java.util.Map qosProperties) throws org.ccsds.moims.mo.mal.MALException {
        switch (msgHeader.getOperation().getValue()) {
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._LOGIN_OP_NUMBER:
            loginErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._LISTROLES_OP_NUMBER:
            listRolesErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          case org.ccsds.moims.mo.common.login.LoginServiceInfo._HANDOVER_OP_NUMBER:
            handoverErrorReceived(msgHeader, body.getError(), qosProperties);
            break;
          default:
            throw new org.ccsds.moims.mo.mal.MALException("Consumer adapter was not expecting operation number " + msgHeader.getOperation().getValue());
        }
    }

}
