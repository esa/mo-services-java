package org.ccsds.moims.mo.common.login.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The Profile structure contains details of the user who is logging on to
 * take a specified role.
 */
public final class Profile implements Composite {

    private static final long serialVersionUID = 844433536843777L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844433536843777L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The name of the user.
     */
    private Identifier username;

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
    public Profile(Identifier username,
            Long role) {
        this.username = username;
        this.role = role;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param username The name of the user.
     */
    public Profile(Identifier username) {
        this.username = username;
        this.role = null;
    }

    @Override
    public Element createElement() {
        return new Profile();
    }

    /**
     * Returns the field username.
     * 
     * @return The field username
     */
    public Identifier getUsername() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (username == null) {
            throw new MALException("The field 'username' cannot be null!");
        }
        encoder.encodeIdentifier(username);
        encoder.encodeNullableLong(role);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        username = decoder.decodeIdentifier();
        role = decoder.decodeNullableLong();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
