package org.ccsds.moims.mo.common.login.provider;

/**
 * Interface that providers of the Login service must implement to handle
 * the operations of that service.
 */
public interface LoginHandler {

    /**
     * Implements the operation login.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.com.DuplicateException Username/role combination currently in use.
     * @throws org.ccsds.moims.mo.com.InvalidException Submitted profile contains invalid values. No further information is provided as it may compromise security.
     * @throws org.ccsds.moims.mo.mal.TooManyException Role concurrent session limit count exceeded.
     * @throws org.ccsds.moims.mo.mal.UnknownException Unknown username/role/password combination.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.common.login.body.LoginResponse login(org.ccsds.moims.mo.common.login.structures.Profile userDetails,
            String password,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.TooManyException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation logout.
     * 
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void logout(org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation listRoles.
     * 
     * @param username The username field shall hold the details of the user.
If the username field is either the wildcard '*', NULL or empty an INVALID error shall be returned.
     * @param password An UNKNOWN error shall be returned if the username and password combination are not correct for the system i.e. unknown user or incorrect password.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException Unknown username/password combination.
     * @throws org.ccsds.moims.mo.com.InvalidException Submitted profile contains invalid values.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.mal.structures.LongList listRoles(org.ccsds.moims.mo.mal.structures.Identifier username,
            String password,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Implements the operation handover.
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
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws org.ccsds.moims.mo.mal.UnknownException Unknown username/role/password combination.
     * @throws org.ccsds.moims.mo.com.InvalidException Submitted profile contains invalid values.
     * @throws org.ccsds.moims.mo.mal.TooManyException Role concurrent session limit count exceeded.
     * @throws org.ccsds.moims.mo.com.DuplicateException Username/role combination currently in use.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    org.ccsds.moims.mo.common.login.body.HandoverResponse handover(org.ccsds.moims.mo.common.login.structures.Profile newUserDetails,
            String newUserPassword,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.com.InvalidException, org.ccsds.moims.mo.mal.TooManyException, org.ccsds.moims.mo.com.DuplicateException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.common.login.provider.LoginSkeleton skeleton);
}
