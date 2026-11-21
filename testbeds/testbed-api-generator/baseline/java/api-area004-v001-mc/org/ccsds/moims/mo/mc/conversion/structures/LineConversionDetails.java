package org.ccsds.moims.mo.mc.conversion.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.PairList;

/**
 * The LineConversionDetails structure is a bi-directional conversion between
 * raw and converted values. It is defined by a series of points between which
 * values are to be interpolated. The extrapolate attribute indicates if values
 * can also be linearly extrapolated beyond the initial and final points.
 */
public final class LineConversionDetails implements Composite {

    private static final long serialVersionUID = 1125929988390914L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125929988390914L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Indicates whether or not values can be extrapolated beyond the start and
     * the end of the points.
     */
    private Boolean extrapolate;

    /**
     * Defines the bi-directional conversion. The first attribute of the point
     * is a raw value, and the second attribute is the converted value.
     */
    private PairList points;

    /**
     * Default constructor for LineConversionDetails.
     * 
     */
    public LineConversionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param extrapolate Indicates whether or not values can be extrapolated beyond the start and the end of the points.
     * @param points Defines the bi-directional conversion. The first attribute of the point is a raw value, and the second attribute is the converted value.
     */
    public LineConversionDetails(Boolean extrapolate,
            PairList points) {
        this.extrapolate = extrapolate;
        this.points = points;
    }

    @Override
    public Element createElement() {
        return new LineConversionDetails();
    }

    /**
     * Returns the field extrapolate.
     * 
     * @return The field extrapolate
     */
    public Boolean getExtrapolate() {
        return extrapolate;
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
        if (obj instanceof LineConversionDetails) {
            LineConversionDetails other = (LineConversionDetails) obj;
            if (extrapolate == null) {
                if (other.extrapolate != null) {
                    return false;
                }
            } else {
                if (! extrapolate.equals(other.extrapolate)) {
                    return false;
                }
            }
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
        hash = 83 * hash + (extrapolate != null ? extrapolate.hashCode() : 0);
        hash = 83 * hash + (points != null ? points.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(LineConversionDetails: ");
        buf.append("extrapolate=").append(extrapolate);
        buf.append(", points=").append(points);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (extrapolate == null) {
            throw new MALException("The field 'extrapolate' cannot be null!");
        }
        if (points == null) {
            throw new MALException("The field 'points' cannot be null!");
        }
        encoder.encodeBoolean(extrapolate);
        encoder.encodeElement(points);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        extrapolate = decoder.decodeBoolean();
        points = (PairList) decoder.decodeElement(new PairList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
