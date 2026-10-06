package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E6: A DirectionReference is a Direction that may be computed following
 * some mission specific definition.
 */
public final class DirectionReference extends Direction {

    private static final long serialVersionUID = 1407374900330514L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330514L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Name of a mission specific direction definition.
     */
    private Identifier reference;

    /**
     * Default constructor for DirectionReference.
     * 
     */
    public DirectionReference() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param reference Name of a mission specific direction definition.
     */
    public DirectionReference(Identifier reference) {
        this.reference = reference;
    }

    @Override
    public Element createElement() {
        return new DirectionReference();
    }

    /**
     * Returns the field reference.
     * 
     * @return The field reference
     */
    public Identifier getReference() {
        return reference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DirectionReference) {
            if (! super.equals(obj)) {
                return false;
            }
            DirectionReference other = (DirectionReference) obj;
            if (reference == null) {
                if (other.reference != null) {
                    return false;
                }
            } else {
                if (! reference.equals(other.reference)) {
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
        hash = 83 * hash + (reference != null ? reference.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DirectionReference: ");
        buf.append(super.toString());
        buf.append(", reference=").append(reference);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (reference == null) {
            throw new MALException("The field 'reference' cannot be null!");
        }
        encoder.encodeIdentifier(reference);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        reference = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
