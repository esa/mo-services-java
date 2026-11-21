package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * This structure is used to define an expected transition from an IP test.
 * It asserts what transition is expected and what result is expected from
 * the transition: successful or failure.
 */
public final class IPTestTransition implements Composite {

    private static final long serialVersionUID = 28147497687842821L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842821L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The type of the transition to do.
     */
    private IPTestTransitionType Type;

    /**
     * The code of the error expected to be raised when doing the transition (failed
     * transition).-1 if no error is expected (successful transition).
     */
    private UInteger errorCode;

    /**
     * Default constructor for IPTestTransition.
     * 
     */
    public IPTestTransition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param Type The type of the transition to do
     * @param errorCode The code of the error expected to be raised when doing the transition (failed transition).-1 if no error is expected (successful transition).
     */
    public IPTestTransition(IPTestTransitionType Type,
            UInteger errorCode) {
        this.Type = Type;
        this.errorCode = errorCode;
    }

    @Override
    public Element createElement() {
        return new IPTestTransition();
    }

    /**
     * Returns the field Type.
     * 
     * @return The field Type
     */
    public IPTestTransitionType getType() {
        return Type;
    }

    /**
     * Returns the field errorCode.
     * 
     * @return The field errorCode
     */
    public UInteger getErrorCode() {
        return errorCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IPTestTransition) {
            IPTestTransition other = (IPTestTransition) obj;
            if (Type == null) {
                if (other.Type != null) {
                    return false;
                }
            } else {
                if (! Type.equals(other.Type)) {
                    return false;
                }
            }
            if (errorCode == null) {
                if (other.errorCode != null) {
                    return false;
                }
            } else {
                if (! errorCode.equals(other.errorCode)) {
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
        hash = 83 * hash + (Type != null ? Type.hashCode() : 0);
        hash = 83 * hash + (errorCode != null ? errorCode.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(IPTestTransition: ");
        buf.append("Type=").append(Type);
        buf.append(", errorCode=").append(errorCode);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableElement(Type);
        encoder.encodeNullableUInteger(errorCode);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        Type = (IPTestTransitionType) decoder.decodeNullableElement(IPTestTransitionType.ACK);
        errorCode = decoder.decodeNullableUInteger();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
