package org.ccsds.moims.mo.mc.conversion.structures;

/**
 * The PolyConversionDetails structure holds only forward (raw to converted)
 * polynomial conversions. They are defined by a series of points for the
 * polynomial coefficients.
 */
public final class PolyConversionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125929988390915L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125929988390915L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The first attribute of a point is a MAL::Integer, being the degree of the
     * polynomial; the second attribute is either a MAL::Float or a MAL::Double,
     * being the coefficient of the term.
     */
    private org.ccsds.moims.mo.mal.structures.PairList points;

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
    public PolyConversionDetails(org.ccsds.moims.mo.mal.structures.PairList points) {
        this.points = points;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.conversion.structures.PolyConversionDetails();
    }

    /**
     * Returns the field points.
     * 
     * @return The field points
     */
    public org.ccsds.moims.mo.mal.structures.PairList getPoints() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (points == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'points' cannot be null!");
        }
        encoder.encodeElement(points);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        points = (org.ccsds.moims.mo.mal.structures.PairList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.PairList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
