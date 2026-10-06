package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E6: A NamedTargetDirection is a Direction that points to an existing object.
 * The manner in which the planning system derives the value of this Direction
 * from the name of the referenced object is implementation-defined.
 */
public final class NamedTargetDirection extends Direction {

    private static final long serialVersionUID = 1407374900330513L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330513L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Name or identifier of a catalogued celestial object or a mission specific
     * object (see 4.4.3).
     */
    private Identifier namedTarget;

    /**
     * Default constructor for NamedTargetDirection.
     * 
     */
    public NamedTargetDirection() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param namedTarget Name or identifier of a catalogued celestial object or a mission specific object (see 4.4.3).
     */
    public NamedTargetDirection(Identifier namedTarget) {
        this.namedTarget = namedTarget;
    }

    @Override
    public Element createElement() {
        return new NamedTargetDirection();
    }

    /**
     * Returns the field namedTarget.
     * 
     * @return The field namedTarget
     */
    public Identifier getNamedTarget() {
        return namedTarget;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamedTargetDirection) {
            if (! super.equals(obj)) {
                return false;
            }
            NamedTargetDirection other = (NamedTargetDirection) obj;
            if (namedTarget == null) {
                if (other.namedTarget != null) {
                    return false;
                }
            } else {
                if (! namedTarget.equals(other.namedTarget)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (namedTarget != null ? namedTarget.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(NamedTargetDirection: ");
        buf.append(super.toString());
        buf.append(", namedTarget=").append(namedTarget);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (namedTarget == null) {
            throw new MALException("The field 'namedTarget' cannot be null!");
        }
        encoder.encodeIdentifier(namedTarget);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        namedTarget = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
