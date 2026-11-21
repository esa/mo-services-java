package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E4: An additional concrete sub-type of ValidationDetails applicable only
 * to Resources of any numeric type, including Duration, that provides additional
 * fields for the specification of numeric data validation.
 */
public final class NumericResource extends ValidationDetails {

    private static final long serialVersionUID = 1407374900330798L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330798L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Defines the permitted minimum value over time.
     */
    private ResourceProfile minimum;

    /**
     * Defines the permitted maximum value over time.
     */
    private ResourceProfile maximum;

    /**
     * Default constructor for NumericResource.
     * 
     */
    public NumericResource() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param minimum Defines the permitted minimum value over time.
     * @param maximum Defines the permitted maximum value over time.
     */
    public NumericResource(ResourceProfile minimum,
            ResourceProfile maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public Element createElement() {
        return new NumericResource();
    }

    /**
     * Returns the field minimum.
     * 
     * @return The field minimum
     */
    public ResourceProfile getMinimum() {
        return minimum;
    }

    /**
     * Returns the field maximum.
     * 
     * @return The field maximum
     */
    public ResourceProfile getMaximum() {
        return maximum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumericResource) {
            if (! super.equals(obj)) {
                return false;
            }
            NumericResource other = (NumericResource) obj;
            if (minimum == null) {
                if (other.minimum != null) {
                    return false;
                }
            } else {
                if (! minimum.equals(other.minimum)) {
                    return false;
                }
            }
            if (maximum == null) {
                if (other.maximum != null) {
                    return false;
                }
            } else {
                if (! maximum.equals(other.maximum)) {
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
        hash = 83 * hash + (minimum != null ? minimum.hashCode() : 0);
        hash = 83 * hash + (maximum != null ? maximum.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(NumericResource: ");
        buf.append(super.toString());
        buf.append(", minimum=").append(minimum);
        buf.append(", maximum=").append(maximum);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (minimum == null) {
            throw new MALException("The field 'minimum' cannot be null!");
        }
        if (maximum == null) {
            throw new MALException("The field 'maximum' cannot be null!");
        }
        encoder.encodeElement(minimum);
        encoder.encodeElement(maximum);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        minimum = (ResourceProfile) decoder.decodeElement(new ResourceProfile());
        maximum = (ResourceProfile) decoder.decodeElement(new ResourceProfile());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
