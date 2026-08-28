package org.ccsds.moims.mo.common.login.body;

/**
 * Multi body return class for HandoverResponse.
 */
public final class HandoverResponse {

    /**
     * newAuthId: The returned newAuthId field shall be used as the authenticationId
     * field in future MAL messages by the consumer MAL for authentication. The
     * token is specific to the new user and role in use.
     */
    private org.ccsds.moims.mo.mal.structures.Blob newAuthId;

    /**
     * newLoginInstId: The returned newLoginInstId field shall contain the new
     * LoginInstance COM object instance identifier that was created by the operation.
     */
    private Long newLoginInstId;

    /**
     * Default constructor for HandoverResponse.
     * 
     */
    public HandoverResponse() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param newAuthId The returned newAuthId field shall be used as the authenticationId field in future MAL messages by the consumer MAL for authentication. The token is specific to the new user and role in use.
     * @param newLoginInstId The returned newLoginInstId field shall contain the new LoginInstance COM object instance identifier that was created by the operation.
     */
    public HandoverResponse(org.ccsds.moims.mo.mal.structures.Blob newAuthId,
            Long newLoginInstId) {
        this.newAuthId = newAuthId;
        this.newLoginInstId = newLoginInstId;
    }

    /**
     * Returns the field newAuthId.
     * 
     * @return The field newAuthId
     */
    public org.ccsds.moims.mo.mal.structures.Blob getNewAuthId() {
        return newAuthId;
    }

    /**
     * Returns the field newLoginInstId.
     * 
     * @return The field newLoginInstId
     */
    public Long getNewLoginInstId() {
        return newLoginInstId;
    }

}
