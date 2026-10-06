package org.ccsds.moims.mo.common.login.consumer;

import org.ccsds.moims.mo.com.DuplicateException;
import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.common.login.LoginServiceInfo;
import org.ccsds.moims.mo.common.login.body.HandoverResponse;
import org.ccsds.moims.mo.common.login.body.LoginResponse;
import org.ccsds.moims.mo.common.login.structures.Profile;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.TooManyException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Consumer stub for Login service.
 */
public class LoginStub {

    /**
     * The consumer field.
     */
    private final MALConsumer consumer;

    /**
     * Wraps a MALconsumer connection with service specific methods that map from
     * the high level service API to the generic MAL API.
     * 
     * @param consumer consumer The MALConsumer to use in this stub.
     */
    public LoginStub(MALConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Returns the internal MAL consumer object used for sending of messages from
     * this interface.
     * 
     * @return The MAL consumer object.
     */
    public MALConsumer getConsumer() {
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
     * @throws DuplicateException Username/role combination currently in use.
     * @throws InvalidException Submitted profile contains invalid values. No further information is provided as it may compromise security.
     * @throws TooManyException Role concurrent session limit count exceeded.
     * @throws UnknownException Unknown username/role/password combination.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LoginResponse login(Profile userDetails,
            String password) throws DuplicateException, InvalidException, TooManyException, UnknownException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(LoginServiceInfo.LOGIN_OP, userDetails, (password == null) ? null : new Union(password));
            Object body0 = (Object) body.getBodyElement(0, new Blob());
            Object body1 = (Object) body.getBodyElement(1, new Union(Long.MAX_VALUE));
            return new LoginResponse((Blob) body0, (body1 == null) ? null : ((Union) body1).getLongValue());
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof DuplicateException) {
                throw (DuplicateException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof TooManyException) {
                throw (TooManyException) error;
            }
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncLogin(Profile userDetails,
            String password,
            LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(LoginServiceInfo.LOGIN_OP, adapter, userDetails, (password == null) ? null : new Union(password));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueLogin(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(LoginServiceInfo.LOGIN_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * The logout operation allows a user to log out from the system. No information
     * is passed in the message as the MAL authentication Id is enough to identify
     * the login.
     * 
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void logout() throws MALStandardError, MALException {
        try {
            consumer.submit(LoginServiceInfo.LOGOUT_OP, (Object[]) null);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Asynchronous version of method logout.
     * 
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @return the MAL message sent to initiate the interaction
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncLogout(LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncSubmit(LoginServiceInfo.LOGOUT_OP, adapter, (Object[]) null);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueLogout(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(LoginServiceInfo.LOGOUT_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws UnknownException Unknown username/password combination.
     * @throws InvalidException Submitted profile contains invalid values.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public LongList listRoles(Identifier username,
            String password) throws UnknownException, InvalidException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(LoginServiceInfo.LISTROLES_OP, username, (password == null) ? null : new Union(password));
            Object body0 = (Object) body.getBodyElement(0, new LongList());
            return (LongList) body0;
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncListRoles(Identifier username,
            String password,
            LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(LoginServiceInfo.LISTROLES_OP, adapter, username, (password == null) ? null : new Union(password));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueListRoles(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(LoginServiceInfo.LISTROLES_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws UnknownException Unknown username/role/password combination.
     * @throws InvalidException Submitted profile contains invalid values.
     * @throws TooManyException Role concurrent session limit count exceeded.
     * @throws DuplicateException Username/role combination currently in use.
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public HandoverResponse handover(Profile newUserDetails,
            String newUserPassword) throws UnknownException, InvalidException, TooManyException, DuplicateException, MALStandardError, MALException {
        try {
            MALMessageBody body = consumer.request(LoginServiceInfo.HANDOVER_OP, newUserDetails, (newUserPassword == null) ? null : new Union(newUserPassword));
            Object body0 = (Object) body.getBodyElement(0, new Blob());
            Object body1 = (Object) body.getBodyElement(1, new Union(Long.MAX_VALUE));
            return new HandoverResponse((Blob) body0, (body1 == null) ? null : ((Union) body1).getLongValue());
        } catch (MALInteractionException ex) {
            MOErrorException error = ex.getStandardError();
            if (error instanceof UnknownException) {
                throw (UnknownException) error;
            }
            if (error instanceof InvalidException) {
                throw (InvalidException) error;
            }
            if (error instanceof TooManyException) {
                throw (TooManyException) error;
            }
            if (error instanceof DuplicateException) {
                throw (DuplicateException) error;
            }
            throw MALStandardError.relayOrWrap(ex);
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
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public MALMessage asyncHandover(Profile newUserDetails,
            String newUserPassword,
            LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            return consumer.asyncRequest(LoginServiceInfo.HANDOVER_OP, adapter, newUserDetails, (newUserPassword == null) ? null : new Union(newUserPassword));
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

    /**
     * Continues a previously started interaction.
     * 
     * @param lastInteractionStage lastInteractionStage The last stage of the interaction to continue
     * @param initiationTimestamp initiationTimestamp Timestamp of the interaction initiation message
     * @param transactionId transactionId Transaction identifier of the interaction to continue
     * @param adapter adapter Listener in charge of receiving the messages from the service provider
     * @throws MALStandardError if the MAL, the transport or the provider returned a MAL standard error
     * @throws MALException if there is an implementation exception, or the provider returned an error the operation does not declare
     */
    public void continueHandover(UOctet lastInteractionStage,
            Time initiationTimestamp,
            Long transactionId,
            LoginAdapter adapter) throws MALStandardError, MALException {
        try {
            consumer.continueInteraction(LoginServiceInfo.HANDOVER_OP, lastInteractionStage, initiationTimestamp, transactionId, adapter);
        } catch (MALInteractionException ex) {
            throw MALStandardError.relayOrWrap(ex);
        }
    }

}
