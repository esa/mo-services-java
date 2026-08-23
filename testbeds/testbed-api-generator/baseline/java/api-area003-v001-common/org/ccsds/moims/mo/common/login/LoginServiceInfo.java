package org.ccsds.moims.mo.common.login;

/**
 * Helper class for Login service.
 */
public class LoginServiceInfo extends org.ccsds.moims.mo.com.COMService {

    /**
     * Service number literal.
     */
    public static final int _LOGIN_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final org.ccsds.moims.mo.mal.structures.UShort LOGIN_SERVICE_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOGIN_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final org.ccsds.moims.mo.mal.structures.Identifier LOGIN_SERVICE_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("Login");

    /**
     * The service key of this service.
     */
    private static final org.ccsds.moims.mo.mal.ServiceKey SERVICE_KEY = new org.ccsds.moims.mo.mal.ServiceKey(
            3, 1, LOGIN_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LOGIN.
     */
    public static final int _LOGIN_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LOGIN.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LOGIN_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOGIN_OP_NUMBER);

    /**
     * Operation instance for operation LOGIN.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LOGIN_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LOGIN_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("login"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("userDetails", true, org.ccsds.moims.mo.common.login.structures.Profile.SHORT_FORM, "The authenticationId field of the REQUEST message must be NULL otherwise an INVALID error shall be returned.\nThe authenticationId field shall be checked before applying all other tests here.\nThe userDetails field shall contain the details of the new user and role combination.\nIf the username field of the supplied Profile structure is either the wildcard '*' or empty an INVALID error shall be returned.\nIf roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.\nIf roles are not used by the system the role field of the supplied Profile structure shall be ignored and may be set to NULL.\nAn UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.\nA DUPLICATE error shall be returned if the username and role combination is currently in use.\nA TOO_MANY error shall be returned if the username or role are already used and exceed (deployment dependent) maximum number of concurrent logins/roles.\nIf the login is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.\nThe related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.\nA LoginEvent COM event shall be generated at this point."),
                new org.ccsds.moims.mo.mal.OperationField("password", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("authId", true, org.ccsds.moims.mo.mal.structures.Attribute.BLOB_SHORT_FORM, "The returned authId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the user and role in use."),
                new org.ccsds.moims.mo.mal.OperationField("objInstId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The returned objInstId field shall contain the LoginInstance COM object instance identifier that was created by the login operation.")}, 
            "The login operation allows a user to log in to the system. A user can log in more than once by using a different role; however, a specific deployment may place limits on the number of users that may use a specific role, and in that case will fail the login operation with the TOO_MANY error.");

    /**
     * Operation number literal for operation LOGOUT.
     */
    public static final int _LOGOUT_OP_NUMBER = 2;

    /**
     * Operation number instance for operation LOGOUT.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LOGOUT_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOGOUT_OP_NUMBER);

    /**
     * Operation instance for operation LOGOUT.
     */
    public static final org.ccsds.moims.mo.mal.MALSubmitOperation LOGOUT_OP = new org.ccsds.moims.mo.mal.MALSubmitOperation(SERVICE_KEY, 
            LOGOUT_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("logout"), 
            new org.ccsds.moims.mo.mal.structures.UShort(1), 
            new org.ccsds.moims.mo.mal.OperationField[] {}, 
            "The logout operation allows a user to log out from the system. No information is passed in the message as the MAL authentication Id is enough to identify the login.");

    /**
     * Operation number literal for operation LISTROLES.
     */
    public static final int _LISTROLES_OP_NUMBER = 3;

    /**
     * Operation number instance for operation LISTROLES.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort LISTROLES_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LISTROLES_OP_NUMBER);

    /**
     * Operation instance for operation LISTROLES.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation LISTROLES_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            LISTROLES_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("listRoles"), 
            new org.ccsds.moims.mo.mal.structures.UShort(2), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("username", true, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, "The username field shall hold the details of the user.\nIf the username field is either the wildcard '*', NULL or empty an INVALID error shall be returned."),
                new org.ccsds.moims.mo.mal.OperationField("password", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, "An UNKNOWN error shall be returned if the username and password combination are not correct for the system i.e. unknown user or incorrect password.")}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("permittedRoles", true, org.ccsds.moims.mo.mal.structures.LongList.SHORT_FORM, "The operation shall return a list of LoginRole object instance identifiers that are permitted for the user or NULL if roles are not used by the system.")}, 
            "The listRoles operation returns the list of available roles for a specific user. This operation is expected to be called before a user logs in so that the software can provide a list of possible roles.\nIt should be noted that this operation requires both a username and password field before returning any information, this is to ensure that it does not provide a security attack vector by allowing the discovery of valid usernames without first knowing the correct password.");

    /**
     * Operation number literal for operation HANDOVER.
     */
    public static final int _HANDOVER_OP_NUMBER = 4;

    /**
     * Operation number instance for operation HANDOVER.
     */
    private static final org.ccsds.moims.mo.mal.structures.UShort HANDOVER_OP_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_HANDOVER_OP_NUMBER);

    /**
     * Operation instance for operation HANDOVER.
     */
    public static final org.ccsds.moims.mo.mal.MALRequestOperation HANDOVER_OP = new org.ccsds.moims.mo.mal.MALRequestOperation(SERVICE_KEY, 
            HANDOVER_OP_NUMBER, 
            new org.ccsds.moims.mo.mal.structures.Identifier("handover"), 
            new org.ccsds.moims.mo.mal.structures.UShort(3), 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newUserDetails", true, org.ccsds.moims.mo.common.login.structures.Profile.SHORT_FORM, "The newUserDetails field shall contain the details of the new user and role combination.\nIf the username field of the supplied Profile structure is either NULL, the wildcard '*', or empty an INVALID error shall be returned.\nIf roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.\nThe role field of the supplied Profile structure may be NULL if roles are not used by the system.\nAn UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.\nA DUPLICATE error shall be returned if the username and role combination is currently in use.\nA TOO_MANY error shall be returned if the username or role are already used and exceed the permitted maximum usage value (deployment dependent).\nThe DUPLICATE and TOO_MANY checks shall take into account the fact that current operator/role combination will be logged out after the handover operation completes.\nIf the handover is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.\nThe related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.\nIf an error is raised then the handover operation shall fail and the original login remain active.\nThe source link of the new LoginInstance COM object shall be set to the LoginInstance COM object that represents the previous login.\nIf the handover operation is successful a LogoutEvent COM event shall be generated for the previous login and a LoginEvent COM event shall be generated for the new login."),
                new org.ccsds.moims.mo.mal.OperationField("newUserPassword", true, org.ccsds.moims.mo.mal.structures.Attribute.STRING_SHORT_FORM, null)}, 
            new org.ccsds.moims.mo.mal.OperationField[] {
                new org.ccsds.moims.mo.mal.OperationField("newAuthId", true, org.ccsds.moims.mo.mal.structures.Attribute.BLOB_SHORT_FORM, "The returned newAuthId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the new user and role in use."),
                new org.ccsds.moims.mo.mal.OperationField("newLoginInstId", true, org.ccsds.moims.mo.mal.structures.Attribute.LONG_SHORT_FORM, "The returned newLoginInstId field shall contain the new LoginInstance COM object instance identifier that was created by the operation.")}, 
            "The handover operation allows an existing login to be transferred to a new user. Two cases are expected here, the first is where the operation is used to change the user's current role, and the second is where an operations context is handed over to another user.");

    /**
     * Area elements.
     */
    public static final org.ccsds.moims.mo.mal.structures.Element[] LOGIN_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final org.ccsds.moims.mo.mal.MALOperation[] OPERATIONS = new org.ccsds.moims.mo.mal.MALOperation[]{LOGIN_OP,
        LOGOUT_OP,
        LISTROLES_OP,
        HANDOVER_OP};

    /**
     * Literal for object LOGINROLE.
     */
    @Deprecated
    public static final int _LOGINROLE_OBJECT_NUMBER = 1;

    /**
     * Instance for object LOGINROLE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort LOGINROLE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOGINROLE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier LOGINROLE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("LoginRole");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType LOGINROLE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), LOGIN_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), LOGINROLE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject LOGINROLE_OBJECT = new org.ccsds.moims.mo.com.COMObject(LOGINROLE_OBJECT_TYPE, LOGINROLE_OBJECT_NAME, org.ccsds.moims.mo.mal.structures.Attribute.IDENTIFIER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object LOGININSTANCE.
     */
    @Deprecated
    public static final int _LOGININSTANCE_OBJECT_NUMBER = 2;

    /**
     * Instance for object LOGININSTANCE.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort LOGININSTANCE_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOGININSTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier LOGININSTANCE_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("LoginInstance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType LOGININSTANCE_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), LOGIN_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), LOGININSTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject LOGININSTANCE_OBJECT = new org.ccsds.moims.mo.com.COMObject(LOGININSTANCE_OBJECT_TYPE, LOGININSTANCE_OBJECT_NAME, org.ccsds.moims.mo.common.login.structures.Profile.SHORT_FORM, true, org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGINROLE_OBJECT_TYPE, true, org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGININSTANCE_OBJECT_TYPE, false);

    /**
     * Literal for object LOGINEVENT.
     */
    @Deprecated
    public static final int _LOGINEVENT_OBJECT_NUMBER = 3;

    /**
     * Instance for object LOGINEVENT.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort LOGINEVENT_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOGINEVENT_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier LOGINEVENT_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("LoginEvent");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType LOGINEVENT_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), LOGIN_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), LOGINEVENT_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject LOGINEVENT_OBJECT = new org.ccsds.moims.mo.com.COMObject(LOGINEVENT_OBJECT_TYPE, LOGINEVENT_OBJECT_NAME, null, true, org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGININSTANCE_OBJECT_TYPE, false, null, true);

    /**
     * Literal for object LOGOUTEVENT.
     */
    @Deprecated
    public static final int _LOGOUTEVENT_OBJECT_NUMBER = 4;

    /**
     * Instance for object LOGOUTEVENT.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.UShort LOGOUTEVENT_OBJECT_NUMBER = new org.ccsds.moims.mo.mal.structures.UShort(_LOGOUTEVENT_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.mal.structures.Identifier LOGOUTEVENT_OBJECT_NAME = new org.ccsds.moims.mo.mal.structures.Identifier("LogoutEvent");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final org.ccsds.moims.mo.com.structures.ObjectType LOGOUTEVENT_OBJECT_TYPE = new org.ccsds.moims.mo.com.structures.ObjectType(new org.ccsds.moims.mo.mal.structures.UShort(3), LOGIN_SERVICE_NUMBER, new org.ccsds.moims.mo.mal.structures.UOctet(1), LOGOUTEVENT_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static org.ccsds.moims.mo.com.COMObject LOGOUTEVENT_OBJECT = new org.ccsds.moims.mo.com.COMObject(LOGOUTEVENT_OBJECT_TYPE, LOGOUTEVENT_OBJECT_NAME, null, true, org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGININSTANCE_OBJECT_TYPE, true, org.ccsds.moims.mo.common.login.LoginServiceInfo.LOGINEVENT_OBJECT_TYPE, true);

    /**
     * Object instance.
     */
    public static final org.ccsds.moims.mo.com.COMObject[] COM_OBJECTS = {
        LOGINROLE_OBJECT,
        LOGININSTANCE_OBJECT,
        LOGINEVENT_OBJECT,
        LOGOUTEVENT_OBJECT,};

    /**
     * Creates an instance of the Login ServiceInfo.
     * 
     */
    public LoginServiceInfo() {
        super(SERVICE_KEY, LOGIN_SERVICE_NAME, LOGIN_SERVICE_ELEMENTS, OPERATIONS, COM_OBJECTS);
    }

    @Override
    public org.ccsds.moims.mo.mal.MALArea getArea() {
        return org.ccsds.moims.mo.common.CommonHelper.COMMON_AREA;
    }

    @Override
    public org.ccsds.moims.mo.mal.MOErrorException generateMOError(int errorNumber,
            Object extraInfo) {
        switch (errorNumber) {
        }
        return null;
    }

}
