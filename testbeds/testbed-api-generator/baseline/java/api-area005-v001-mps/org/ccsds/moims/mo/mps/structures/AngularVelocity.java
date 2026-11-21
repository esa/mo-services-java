package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: Physical value with units of type AngularVelocity.
 */
public final class AngularVelocity extends PhysicalValue {

    private static final long serialVersionUID = 1407374900330516L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330516L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for AngularVelocity.
     * 
     */
    public AngularVelocity() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param value Physical value.
     * @param units Optional units.  The units for a single quantity.  The unit type depends on the specific value type.
     */
    public AngularVelocity(Double value,
            String units) {
        super(value,
            units);
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param value Physical value.
     */
    public AngularVelocity(Double value) {
        super(value);
    }

    @Override
    public Element createElement() {
        return new AngularVelocity();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AngularVelocity) {
            if (! super.equals(obj)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AngularVelocity: ");
        buf.append(super.toString());
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
