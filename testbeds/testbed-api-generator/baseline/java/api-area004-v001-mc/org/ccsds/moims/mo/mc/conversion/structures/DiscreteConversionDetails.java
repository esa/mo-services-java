package org.ccsds.moims.mo.mc.conversion.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.PairList;

/**
 * The DiscreteConversionDetails structure holds a bidirectional conversion
 * between raw and converted values. The first element of the pair is the
 * raw value and the second is the converted value. Both sets of values must
 * be unique.
 */
public final class DiscreteConversionDetails implements Composite {

    private static final long serialVersionUID = 1125929988390913L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125929988390913L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Defines a mapping between raw and converted values as a discrete set of
     * points. The first entry in the pair is the raw value, and the second entry
     * is the converted value.
     */
    private PairList mapping;

    /**
     * Default constructor for DiscreteConversionDetails.
     * 
     */
    public DiscreteConversionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param mapping Defines a mapping between raw and converted values as a discrete set of points. The first entry in the pair is the raw value, and the second entry is the converted value.
     */
    public DiscreteConversionDetails(PairList mapping) {
        this.mapping = mapping;
    }

    @Override
    public Element createElement() {
        return new DiscreteConversionDetails();
    }

    /**
     * Returns the field mapping.
     * 
     * @return The field mapping
     */
    public PairList getMapping() {
        return mapping;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DiscreteConversionDetails) {
            DiscreteConversionDetails other = (DiscreteConversionDetails) obj;
            if (mapping == null) {
                if (other.mapping != null) {
                    return false;
                }
            } else {
                if (! mapping.equals(other.mapping)) {
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
        hash = 83 * hash + (mapping != null ? mapping.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DiscreteConversionDetails: ");
        buf.append("mapping=").append(mapping);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (mapping == null) {
            throw new MALException("The field 'mapping' cannot be null!");
        }
        encoder.encodeElement(mapping);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        mapping = (PairList) decoder.decodeElement(new PairList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
