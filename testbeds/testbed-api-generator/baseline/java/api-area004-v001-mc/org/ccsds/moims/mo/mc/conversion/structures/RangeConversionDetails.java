package org.ccsds.moims.mo.mc.conversion.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.PairList;

/**
 * The RangeConversionDetails structure holds a range for a one-way conversion
 * to convert between a continuous range to a discrete value. A range is defined
 * as from this point up to, but not including, the next point.
 */
public final class RangeConversionDetails implements Composite {

    private static final long serialVersionUID = 1125929988390916L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125929988390916L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The first attribute in each pair is the raw range, and the second attribute
     * is the converted value.
     */
    private PairList points;

    /**
     * Default constructor for RangeConversionDetails.
     * 
     */
    public RangeConversionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param points The first attribute in each pair is the raw range, and the second attribute is the converted value.
     */
    public RangeConversionDetails(PairList points) {
        this.points = points;
    }

    @Override
    public Element createElement() {
        return new RangeConversionDetails();
    }

    /**
     * Returns the field points.
     * 
     * @return The field points
     */
    public PairList getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RangeConversionDetails) {
            RangeConversionDetails other = (RangeConversionDetails) obj;
            if (points == null) {
                if (other.points != null) {
                    return false;
                }
            } else {
                if (! points.equals(other.points)) {
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
        hash = 83 * hash + (points != null ? points.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RangeConversionDetails: ");
        buf.append("points=").append(points);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (points == null) {
            throw new MALException("The field 'points' cannot be null!");
        }
        encoder.encodeElement(points);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        points = (PairList) decoder.decodeElement(new PairList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
