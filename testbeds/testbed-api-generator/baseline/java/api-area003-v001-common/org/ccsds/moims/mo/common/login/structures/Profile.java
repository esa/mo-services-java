package org.ccsds.moims.mo.common.login.structures;

/**
 * The Profile structure contains details of the user who is logging on to
 * take a specified role.
 */
public final class Profile implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 844433536843777L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844433536843777L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The name of the user.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier username;

    /**
     * The optional object instance identifier of the role required by the user.
     */
    private Long role;

    /**
     * Default constructor for Profile.
     * 
     */
    public Profile() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param username The name of the user.
     * @param role The optional object instance identifier of the role required by the user.
     */
    public Profile(org.ccsds.moims.mo.mal.structures.Identifier username,
            Long role) {
        this.username = username;
        this.role = role;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param username The name of the user.
     */
    public Profile(org.ccsds.moims.mo.mal.structures.Identifier username) {
        this.username = username;
        this.role = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.common.login.structures.Profile();
    }

    /**
     * Returns the field username.
     * 
     * @return The field username
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getUsername() {
        return username;
    }

    /**
     * Returns the field role.
     * 
     * @return The field role
     */
    public Long getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile other = (Profile) obj;
            if (username == null) {
                if (other.username != null) {
                    return false;
                }
            } else {
                if (! username.equals(other.username)) {
                    return false;
                }
            }
            if (role == null) {
                if (other.role != null) {
                    return false;
                }
            } else {
                if (! role.equals(other.role)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (username != null ? username.hashCode() : 0);
        hash = 83 * hash + (role != null ? role.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Profile: ");
        buf.append("username=").append(username);
        buf.append(", role=").append(role);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (username == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'username' cannot be null!");
        }
        encoder.encodeIdentifier(username);
        encoder.encodeNullableLong(role);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        username = decoder.decodeIdentifier();
        role = decoder.decodeNullableLong();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
