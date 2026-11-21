package org.ccsds.moims.mo.common.login;

import org.ccsds.moims.mo.com.COMObject;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.DuplicateException;
import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.common.CommonHelper;
import org.ccsds.moims.mo.common.login.structures.Profile;
import org.ccsds.moims.mo.mal.MALArea;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.MALRequestOperation;
import org.ccsds.moims.mo.mal.MALSubmitOperation;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.ServiceKey;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * Helper class for Login service.
 */
public class LoginServiceInfo extends COMService {

    /**
     * Service number literal.
     */
    public static final int _LOGIN_SERVICE_NUMBER = 2;

    /**
     * Service number instance.
     */
    public static final UShort LOGIN_SERVICE_NUMBER = new UShort(_LOGIN_SERVICE_NUMBER);

    /**
     * Service name constant.
     */
    public static final Identifier LOGIN_SERVICE_NAME = new Identifier("Login");

    /**
     * The service key of this service.
     */
    private static final ServiceKey SERVICE_KEY = new ServiceKey(
            3, 1, LOGIN_SERVICE_NUMBER);

    /**
     * Operation number literal for operation LOGIN.
     */
    public static final int _LOGIN_OP_NUMBER = 1;

    /**
     * Operation number instance for operation LOGIN.
     */
    private static final UShort LOGIN_OP_NUMBER = new UShort(_LOGIN_OP_NUMBER);

    /**
     * Operation instance for operation LOGIN.
     */
    public static final MALRequestOperation LOGIN_OP = new MALRequestOperation(SERVICE_KEY, 
            LOGIN_OP_NUMBER, 
            new Identifier("login"), 
            new UShort(1), 
            new OperationField[] {
                new OperationField("userDetails", true, Profile.SHORT_FORM, "The authenticationId field of the REQUEST message must be NULL otherwise an INVALID error shall be returned.\nThe authenticationId field shall be checked before applying all other tests here.\nThe userDetails field shall contain the details of the new user and role combination.\nIf the username field of the supplied Profile structure is either the wildcard '*' or empty an INVALID error shall be returned.\nIf roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.\nIf roles are not used by the system the role field of the supplied Profile structure shall be ignored and may be set to NULL.\nAn UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.\nA DUPLICATE error shall be returned if the username and role combination is currently in use.\nA TOO_MANY error shall be returned if the username or role are already used and exceed (deployment dependent) maximum number of concurrent logins/roles.\nIf the login is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.\nThe related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.\nA LoginEvent COM event shall be generated at this point."),
                new OperationField("password", true, Attribute.STRING_SHORT_FORM, null)}, 
            new OperationField[] {
                new OperationField("authId", true, Attribute.BLOB_SHORT_FORM, "The returned authId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the user and role in use."),
                new OperationField("objInstId", true, Attribute.LONG_SHORT_FORM, "The returned objInstId field shall contain the LoginInstance COM object instance identifier that was created by the login operation.")}, 
            "The login operation allows a user to log in to the system. A user can log in more than once by using a different role; however, a specific deployment may place limits on the number of users that may use a specific role, and in that case will fail the login operation with the TOO_MANY error.");

    /**
     * Operation number literal for operation LOGOUT.
     */
    public static final int _LOGOUT_OP_NUMBER = 2;

    /**
     * Operation number instance for operation LOGOUT.
     */
    private static final UShort LOGOUT_OP_NUMBER = new UShort(_LOGOUT_OP_NUMBER);

    /**
     * Operation instance for operation LOGOUT.
     */
    public static final MALSubmitOperation LOGOUT_OP = new MALSubmitOperation(SERVICE_KEY, 
            LOGOUT_OP_NUMBER, 
            new Identifier("logout"), 
            new UShort(1), 
            new OperationField[] {}, 
            "The logout operation allows a user to log out from the system. No information is passed in the message as the MAL authentication Id is enough to identify the login.");

    /**
     * Operation number literal for operation LISTROLES.
     */
    public static final int _LISTROLES_OP_NUMBER = 3;

    /**
     * Operation number instance for operation LISTROLES.
     */
    private static final UShort LISTROLES_OP_NUMBER = new UShort(_LISTROLES_OP_NUMBER);

    /**
     * Operation instance for operation LISTROLES.
     */
    public static final MALRequestOperation LISTROLES_OP = new MALRequestOperation(SERVICE_KEY, 
            LISTROLES_OP_NUMBER, 
            new Identifier("listRoles"), 
            new UShort(2), 
            new OperationField[] {
                new OperationField("username", true, Attribute.IDENTIFIER_SHORT_FORM, "The username field shall hold the details of the user.\nIf the username field is either the wildcard '*', NULL or empty an INVALID error shall be returned."),
                new OperationField("password", true, Attribute.STRING_SHORT_FORM, "An UNKNOWN error shall be returned if the username and password combination are not correct for the system i.e. unknown user or incorrect password.")}, 
            new OperationField[] {
                new OperationField("permittedRoles", true, LongList.SHORT_FORM, "The operation shall return a list of LoginRole object instance identifiers that are permitted for the user or NULL if roles are not used by the system.")}, 
            "The listRoles operation returns the list of available roles for a specific user. This operation is expected to be called before a user logs in so that the software can provide a list of possible roles.\nIt should be noted that this operation requires both a username and password field before returning any information, this is to ensure that it does not provide a security attack vector by allowing the discovery of valid usernames without first knowing the correct password.");

    /**
     * Operation number literal for operation HANDOVER.
     */
    public static final int _HANDOVER_OP_NUMBER = 4;

    /**
     * Operation number instance for operation HANDOVER.
     */
    private static final UShort HANDOVER_OP_NUMBER = new UShort(_HANDOVER_OP_NUMBER);

    /**
     * Operation instance for operation HANDOVER.
     */
    public static final MALRequestOperation HANDOVER_OP = new MALRequestOperation(SERVICE_KEY, 
            HANDOVER_OP_NUMBER, 
            new Identifier("handover"), 
            new UShort(3), 
            new OperationField[] {
                new OperationField("newUserDetails", true, Profile.SHORT_FORM, "The newUserDetails field shall contain the details of the new user and role combination.\nIf the username field of the supplied Profile structure is either NULL, the wildcard '*', or empty an INVALID error shall be returned.\nIf roles are required by the system and the role field of the supplied Profile structure is NULL then an INVALID error shall be returned.\nThe role field of the supplied Profile structure may be NULL if roles are not used by the system.\nAn UNKNOWN error shall be returned if the username, password and role combination are not correct for the system i.e. unknown user/role or incorrect password.\nA DUPLICATE error shall be returned if the username and role combination is currently in use.\nA TOO_MANY error shall be returned if the username or role are already used and exceed the permitted maximum usage value (deployment dependent).\nThe DUPLICATE and TOO_MANY checks shall take into account the fact that current operator/role combination will be logged out after the handover operation completes.\nIf the handover is successful the provider shall create a new LoginInstance COM object and store it in the COM archive.\nThe related link of the new LoginInstance COM object shall be set to the requested LoginRole COM object.\nIf an error is raised then the handover operation shall fail and the original login remain active.\nThe source link of the new LoginInstance COM object shall be set to the LoginInstance COM object that represents the previous login.\nIf the handover operation is successful a LogoutEvent COM event shall be generated for the previous login and a LoginEvent COM event shall be generated for the new login."),
                new OperationField("newUserPassword", true, Attribute.STRING_SHORT_FORM, null)}, 
            new OperationField[] {
                new OperationField("newAuthId", true, Attribute.BLOB_SHORT_FORM, "The returned newAuthId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the new user and role in use."),
                new OperationField("newLoginInstId", true, Attribute.LONG_SHORT_FORM, "The returned newLoginInstId field shall contain the new LoginInstance COM object instance identifier that was created by the operation.")}, 
            "The handover operation allows an existing login to be transferred to a new user. Two cases are expected here, the first is where the operation is used to change the user's current role, and the second is where an operations context is handed over to another user.");

    /**
     * Area elements.
     */
    public static final Element[] LOGIN_SERVICE_ELEMENTS = {};

    /**
     * The set of operations for this service.
     */
    public static final MALOperation[] OPERATIONS = new MALOperation[]{LOGIN_OP,
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
    public static final UShort LOGINROLE_OBJECT_NUMBER = new UShort(_LOGINROLE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier LOGINROLE_OBJECT_NAME = new Identifier("LoginRole");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType LOGINROLE_OBJECT_TYPE = new ObjectType(new UShort(3), LOGIN_SERVICE_NUMBER, new UOctet(1), LOGINROLE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject LOGINROLE_OBJECT = new COMObject(LOGINROLE_OBJECT_TYPE, LOGINROLE_OBJECT_NAME, Attribute.IDENTIFIER_SHORT_FORM, false, null, false, null, false);

    /**
     * Literal for object LOGININSTANCE.
     */
    @Deprecated
    public static final int _LOGININSTANCE_OBJECT_NUMBER = 2;

    /**
     * Instance for object LOGININSTANCE.
     */
    @Deprecated
    public static final UShort LOGININSTANCE_OBJECT_NUMBER = new UShort(_LOGININSTANCE_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier LOGININSTANCE_OBJECT_NAME = new Identifier("LoginInstance");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType LOGININSTANCE_OBJECT_TYPE = new ObjectType(new UShort(3), LOGIN_SERVICE_NUMBER, new UOctet(1), LOGININSTANCE_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject LOGININSTANCE_OBJECT = new COMObject(LOGININSTANCE_OBJECT_TYPE, LOGININSTANCE_OBJECT_NAME, Profile.SHORT_FORM, true, LoginServiceInfo.LOGINROLE_OBJECT_TYPE, true, LoginServiceInfo.LOGININSTANCE_OBJECT_TYPE, false);

    /**
     * Literal for object LOGINEVENT.
     */
    @Deprecated
    public static final int _LOGINEVENT_OBJECT_NUMBER = 3;

    /**
     * Instance for object LOGINEVENT.
     */
    @Deprecated
    public static final UShort LOGINEVENT_OBJECT_NUMBER = new UShort(_LOGINEVENT_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier LOGINEVENT_OBJECT_NAME = new Identifier("LoginEvent");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType LOGINEVENT_OBJECT_TYPE = new ObjectType(new UShort(3), LOGIN_SERVICE_NUMBER, new UOctet(1), LOGINEVENT_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject LOGINEVENT_OBJECT = new COMObject(LOGINEVENT_OBJECT_TYPE, LOGINEVENT_OBJECT_NAME, null, true, LoginServiceInfo.LOGININSTANCE_OBJECT_TYPE, false, null, true);

    /**
     * Literal for object LOGOUTEVENT.
     */
    @Deprecated
    public static final int _LOGOUTEVENT_OBJECT_NUMBER = 4;

    /**
     * Instance for object LOGOUTEVENT.
     */
    @Deprecated
    public static final UShort LOGOUTEVENT_OBJECT_NUMBER = new UShort(_LOGOUTEVENT_OBJECT_NUMBER);

    /**
     * Object name constant.
     */
    @Deprecated
    public static final Identifier LOGOUTEVENT_OBJECT_NAME = new Identifier("LogoutEvent");

    /**
     * Object type constant.
     */
    @Deprecated
    public static final ObjectType LOGOUTEVENT_OBJECT_TYPE = new ObjectType(new UShort(3), LOGIN_SERVICE_NUMBER, new UOctet(1), LOGOUTEVENT_OBJECT_NUMBER);

    /**
     * Object instance.
     */
    @Deprecated
    public static COMObject LOGOUTEVENT_OBJECT = new COMObject(LOGOUTEVENT_OBJECT_TYPE, LOGOUTEVENT_OBJECT_NAME, null, true, LoginServiceInfo.LOGININSTANCE_OBJECT_TYPE, true, LoginServiceInfo.LOGINEVENT_OBJECT_TYPE, true);

    /**
     * Object instance.
     */
    public static final COMObject[] COM_OBJECTS = {
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
    public MALArea getArea() {
        return CommonHelper.COMMON_AREA;
    }

    @Override
    public MOErrorException generateMOError(int operationNumber,
            int errorNumber,
            Object extraInfo) {
        switch (operationNumber) {
            case 1:
                switch (errorNumber) {
                    case 70001:
                        return new DuplicateException(extraInfo);
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 3:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                }
                break;
            case 4:
                switch (errorNumber) {
                    case 70000:
                        return new InvalidException(extraInfo);
                    case 70001:
                        return new DuplicateException(extraInfo);
                }
                break;
        }
        MOErrorException areaError = CommonHelper.generateMOError(errorNumber, extraInfo);
        return (areaError != null) ? areaError : MALHelper.generateMOError(errorNumber, extraInfo);
    }

}
