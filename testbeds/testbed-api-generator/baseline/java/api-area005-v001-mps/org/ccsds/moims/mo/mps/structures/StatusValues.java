package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E1: Concrete sub-type of ValidationDetails that provides additional fields
 * to support data validation and interpretation for integer type arguments
 * that are effectively enumerated Statuses.
 */
public final class StatusValues extends ValidationDetails {

    private static final long serialVersionUID = 1407374900330524L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330524L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Set of allowed State definitions (see 4.6.2.4.2), comprising the enumerated
     * value and an associated text label.
     */
    private StateDefList allowedValues;

    /**
     * Default constructor for StatusValues.
     * 
     */
    public StatusValues() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param allowedValues Set of allowed State definitions (see 4.6.2.4.2), comprising the enumerated value and an associated text label.
     */
    public StatusValues(StateDefList allowedValues) {
        this.allowedValues = allowedValues;
    }

    @Override
    public Element createElement() {
        return new StatusValues();
    }

    /**
     * Returns the field allowedValues.
     * 
     * @return The field allowedValues
     */
    public StateDefList getAllowedValues() {
        return allowedValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatusValues) {
            if (! super.equals(obj)) {
                return false;
            }
            StatusValues other = (StatusValues) obj;
            if (allowedValues == null) {
                if (other.allowedValues != null) {
                    return false;
                }
            } else {
                if (! allowedValues.equals(other.allowedValues)) {
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
        hash = 83 * hash + (allowedValues != null ? allowedValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatusValues: ");
        buf.append(super.toString());
        buf.append(", allowedValues=").append(allowedValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (allowedValues == null) {
            throw new MALException("The field 'allowedValues' cannot be null!");
        }
        encoder.encodeElement(allowedValues);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        allowedValues = (StateDefList) decoder.decodeElement(new StateDefList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
