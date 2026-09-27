package org.ccsds.moims.mo.common.login.consumer;

/**
 * Consumer stub for Login service.
 */
public class LoginStub {

    /**
     * The consumer field.
     */
    private final org.ccsds.moims.mo.mal.consumer.MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public LoginStub(org.ccsds.moims.mo.mal.consumer.MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public org.ccsds.moims.mo.mal.consumer.MALConsumer getConsumer() {
        return consumer;
    }

    /**
     * The login operation allows a user to log in to the system. A user can log
     * in more than once by using a different role; however, a specific deployment
     * may place limits on the number of users that may use a specific role, and
     * in that case will fail the login operation with the TOO_MANY error.
     * 
     * @param userDetails The authenticationId field of the REQUEST message must be NULL otherwise an INVALID error shall be returned.
The authenticationId field shall be checked before applying all other tests here.
The userDetails field shall contain the details of the new user and role combination.
If the username field of the supplied Profile structure is either the wildcard '*' or empty an INVALID error shall be returned.
If roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.
If roles are not used by the system the role field of the supplied Profile structure shall be ignored and may be set to NULL.
An UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.
A DUPLICATE error shall be returned if the username and role combination is currently in use.
A TOO_MANY error shall be returned if the username or role are already used and exceed (deployment dependent) maximum number of concurrent logins/roles.
If the login is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.
The related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.
A LoginEvent COM event shall be generated at this point.
     * @param password password Argument number 1 as defined by the service operation
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.com.DuplicateException Username/role combination currently in use.
     * @throws org.ccsds.moims.mo.com.InvalidException Submitted profile contains invalid values. No further information is provided as it may compromise security.
     * @throws org.ccsds.moims.mo.mal.TooManyException Role concurrent session limit count exceeded.
     * @throws org.ccsds.moims.mo.mal.UnknownException Unknown username/role/password combination.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.common.login.body.LoginResponse login(org.ccsds.moims.mo.common.login.structures.Profile userDetails,
            String password) throws org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.TooManyException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGIN_OP, userDetails, (password == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(password));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Blob());
            Object body1 = (Object) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE));
            return new org.ccsds.moims.mo.common.login.body.LoginResponse((org.ccsds.moims.mo.mal.structures.Blob) body0, (body1 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body1).getLongValue());
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.com.DuplicateException) {
                throw (org.ccsds.moims.mo.com.DuplicateException) error;
            }
            if (error instanceof org.ccsds.moims.mo.com.InvalidException) {
                throw (org.ccsds.moims.mo.com.InvalidException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mal.TooManyException) {
                throw (org.ccsds.moims.mo.mal.TooManyException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method login.
     * 
     * @param userDetails The authenticationId field of the REQUEST message must be NULL otherwise an INVALID error shall be returned.
The authenticationId field shall be checked before applying all other tests here.
The userDetails field shall contain the details of the new user and role combination.
If the username field of the supplied Profile structure is either the wildcard '*' or empty an INVALID error shall be returned.
If roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.
If roles are not used by the system the role field of the supplied Profile structure shall be ignored and may be set to NULL.
An UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.
A DUPLICATE error shall be returned if the username and role combination is currently in use.
A TOO_MANY error shall be returned if the username or role are already used and exceed (deployment dependent) maximum number of concurrent logins/roles.
If the login is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.
The related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.
A LoginEvent COM event shall be generated at this point.
     * @param password password Argument number 1 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncLogin(org.ccsds.moims.mo.common.login.structures.Profile userDetails,
            String password,
            org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGIN_OP, adapter, userDetails, (password == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(password));
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueLogin(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGIN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The logout operation allows a user to log out from the system. No information
     * is passed in the message as the MAL authentication Id is enough to identify
     * the login.
     * 
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void logout() throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.submit(org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGOUT_OP, (Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method logout.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncLogout(org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncSubmit(org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGOUT_OP, adapter, (Object[]) null);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueLogout(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGOUT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The listRoles operation returns the list of available roles for a specific
     * user. This operation is expected to be called before a user logs in so
     * that the software can provide a list of possible roles.
     * It should be noted that this operation requires both a username and password
     * field before returning any information, this is to ensure that it does
     * not provide a security attack vector by allowing the discovery of valid
     * usernames without first knowing the correct password.
     * 
     * @param username The username field shall hold the details of the user.
If the username field is either the wildcard '*', NULL or empty an INVALID error shall be returned.
     * @param password An UNKNOWN error shall be returned if the username and password combination are not correct for the system i.e. unknown user or incorrect password.
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.UnknownException Unknown username/password combination.
     * @throws org.ccsds.moims.mo.com.InvalidException Submitted profile contains invalid values.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.structures.LongList listRoles(org.ccsds.moims.mo.mal.structures.Identifier username,
            String password) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.common.login.LoginServiceInfo.LISTROLES_OP, username, (password == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(password));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.LongList());
            return (org.ccsds.moims.mo.mal.structures.LongList) body0;
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            if (error instanceof org.ccsds.moims.mo.com.InvalidException) {
                throw (org.ccsds.moims.mo.com.InvalidException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method listRoles.
     * 
     * @param username The username field shall hold the details of the user.
If the username field is either the wildcard '*', NULL or empty an INVALID error shall be returned.
     * @param password An UNKNOWN error shall be returned if the username and password combination are not correct for the system i.e. unknown user or incorrect password.
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncListRoles(org.ccsds.moims.mo.mal.structures.Identifier username,
            String password,
            org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.common.login.LoginServiceInfo.LISTROLES_OP, adapter, username, (password == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(password));
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueListRoles(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.common.login.LoginServiceInfo.LISTROLES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The handover operation allows an existing login to be transferred to a
     * new user. Two cases are expected here, the first is where the operation
     * is used to change the user&quot;s current role, and the second is where
     * an operations context is handed over to another user.
     * 
     * @param newUserDetails The newUserDetails field shall contain the details of the new user and role combination.
If the username field of the supplied Profile structure is either NULL, the wildcard '*', or empty an INVALID error shall be returned.
If roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.
The role field of the supplied Profile structure may be NULL if roles are not used by the system.
An UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.
A DUPLICATE error shall be returned if the username and role combination is currently in use.
A TOO_MANY error shall be returned if the username or role are already used and exceed the permitted maximum usage value (deployment dependent).
The DUPLICATE and TOO_MANY checks shall take into account the fact that current operator/role combination will be logged out after the handover operation completes.
If the handover is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.
The related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.
If an error is raised then the handover operation shall fail and the original login remain active.
The source link of the new LoginInstance COM object shall be set to the LoginInstance COM object that represents the previous login.
If the handover operation is successful a LogoutEvent COM event shall be generated for the previous login and a LoginEvent COM event shall be generated for the new login.
     * @param newUserPassword newUserPassword Argument number 1 as defined by the service operation
     * @return The return value of the interaction
     * @throws org.ccsds.moims.mo.mal.UnknownException Unknown username/role/password combination.
     * @throws org.ccsds.moims.mo.com.InvalidException Submitted profile contains invalid values.
     * @throws org.ccsds.moims.mo.mal.TooManyException Role concurrent session limit count exceeded.
     * @throws org.ccsds.moims.mo.com.DuplicateException Username/role combination currently in use.
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.common.login.body.HandoverResponse handover(org.ccsds.moims.mo.common.login.structures.Profile newUserDetails,
            String newUserPassword) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.TooManyException, org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            org.ccsds.moims.mo.mal.transport.MALMessageBody body = consumer.request(org.ccsds.moims.mo.common.login.LoginServiceInfo.HANDOVER_OP, newUserDetails, (newUserPassword == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(newUserPassword));
            Object body0 = (Object) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Blob());
            Object body1 = (Object) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE));
            return new org.ccsds.moims.mo.common.login.body.HandoverResponse((org.ccsds.moims.mo.mal.structures.Blob) body0, (body1 == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body1).getLongValue());
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            org.ccsds.moims.mo.mal.MOErrorException error = ex.getStandardError();
            if (error instanceof org.ccsds.moims.mo.mal.UnknownException) {
                throw (org.ccsds.moims.mo.mal.UnknownException) error;
            }
            if (error instanceof org.ccsds.moims.mo.com.InvalidException) {
                throw (org.ccsds.moims.mo.com.InvalidException) error;
            }
            if (error instanceof org.ccsds.moims.mo.mal.TooManyException) {
                throw (org.ccsds.moims.mo.mal.TooManyException) error;
            }
            if (error instanceof org.ccsds.moims.mo.com.DuplicateException) {
                throw (org.ccsds.moims.mo.com.DuplicateException) error;
            }
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method handover.
     * 
     * @param newUserDetails The newUserDetails field shall contain the details of the new user and role combination.
If the username field of the supplied Profile structure is either NULL, the wildcard '*', or empty an INVALID error shall be returned.
If roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.
The role field of the supplied Profile structure may be NULL if roles are not used by the system.
An UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.
A DUPLICATE error shall be returned if the username and role combination is currently in use.
A TOO_MANY error shall be returned if the username or role are already used and exceed the permitted maximum usage value (deployment dependent).
The DUPLICATE and TOO_MANY checks shall take into account the fact that current operator/role combination will be logged out after the handover operation completes.
If the handover is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.
The related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.
If an error is raised then the handover operation shall fail and the original login remain active.
The source link of the new LoginInstance COM object shall be set to the LoginInstance COM object that represents the previous login.
If the handover operation is successful a LogoutEvent COM event shall be generated for the previous login and a LoginEvent COM event shall be generated for the new login.
     * @param newUserPassword newUserPassword Argument number 1 as defined by the service operation
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public org.ccsds.moims.mo.mal.transport.MALMessage asyncHandover(org.ccsds.moims.mo.common.login.structures.Profile newUserDetails,
            String newUserPassword,
            org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            return consumer.asyncRequest(org.ccsds.moims.mo.common.login.LoginServiceInfo.HANDOVER_OP, adapter, newUserDetails, (newUserPassword == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(newUserPassword));
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws org.ccsds.moims.mo.mal.MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueHandover(org.ccsds.moims.mo.mal.structures.UOctet lastInteractionStage,
            org.ccsds.moims.mo.mal.structures.Time initiationTimestamp,
            Long transactionId,
            org.ccsds.moims.mo.common.login.consumer.LoginAdapter adapter) throws org.ccsds.moims.mo.mal.MALStandardError, org.ccsds.moims.mo.mal.MALException {
        try {
            consumer.continueInteraction(org.ccsds.moims.mo.common.login.LoginServiceInfo.HANDOVER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (org.ccsds.moims.mo.mal.MALInteractionException ex) {
            throw org.ccsds.moims.mo.mal.MALStandardError.relayOrWrap(ex);
        }
    }

}
