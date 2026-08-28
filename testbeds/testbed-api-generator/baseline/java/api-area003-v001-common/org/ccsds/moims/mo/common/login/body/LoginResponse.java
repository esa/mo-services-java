package org.ccsds.moims.mo.common.login.body;

/**
 * Multi body return class for LoginResponse.
 */
public final class LoginResponse {

    /**
     * authId: The returned authId field shall be used as the authenticationId
     * field in future MAL messages by the consumer MAL for authentication. The
     * token is specific to the user and role in use.
     */
    private org.ccsds.moims.mo.mal.structures.Blob authId;

    /**
     * objInstId: The returned objInstId field shall contain the LoginInstance
     * COM object instance identifier that was created by the login operation.
     */
    private Long objInstId;

    /**
     * Default constructor for LoginResponse.
     * 
     */
    public LoginResponse() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param authId The returned authId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the user and role in use.
     * @param objInstId The returned objInstId field shall contain the LoginInstance COM object instance identifier that was created by the login operation.
     */
    public LoginResponse(org.ccsds.moims.mo.mal.structures.Blob authId,
            Long objInstId) {
        this.authId = authId;
        this.objInstId = objInstId;
    }

    /**
     * Returns the field authId.
     * 
     * @return The field authId
     */
    public org.ccsds.moims.mo.mal.structures.Blob getAuthId() {
        return authId;
    }

    /**
     * Returns the field objInstId.
     * 
     * @return The field objInstId
     */
    public Long getObjInstId() {
        return objInstId;
    }

}
