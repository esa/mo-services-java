package org.ccsds.moims.mo.mc.conversion.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.PairList;

/**
 * The PolyConversionDetails structure holds only forward (raw to converted)
 * polynomial conversions. They are defined by a series of points for the
 * polynomial coefficients.
 */
public final class PolyConversionDetails implements Composite {

    private static final long serialVersionUID = 1125929988390915L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125929988390915L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The first attribute of a point is a MAL::Integer, being the degree of the
     * polynomial; the second attribute is either a MAL::Float or a MAL::Double,
     * being the coefficient of the term.
     */
    private PairList points;

    /**
     * Default constructor for PolyConversionDetails.
     * 
     */
    public PolyConversionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param points The first attribute of a point is a MAL::Integer, being the degree of the polynomial; the second attribute is either a MAL::Float or a MAL::Double, being the coefficient of the term.
     */
    public PolyConversionDetails(PairList points) {
        this.points = points;
    }

    @Override
    public Element createElement() {
        return new PolyConversionDetails();
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
        if (obj instanceof PolyConversionDetails) {
            PolyConversionDetails other = (PolyConversionDetails) obj;
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
        buf.append("(PolyConversionDetails: ");
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
