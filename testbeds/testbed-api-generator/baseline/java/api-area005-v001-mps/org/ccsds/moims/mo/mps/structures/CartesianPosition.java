package org.ccsds.moims.mo.mps.structures;

/**
 * E6: Concrete type representing a Position in Cartesian coordinates.
 */
public final class CartesianPosition extends org.ccsds.moims.mo.mps.structures.Position {

    private static final long serialVersionUID = 1407374900330504L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330504L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Cartesian x coordinate defined in the given frame and with value of the
     * given unit.
     */
    private Double x;

    /**
     * Cartesian y coordinate defined in the given frame and with value of the
     * given unit.
     */
    private Double y;

    /**
     * Cartesian z coordinate defined in the given frame and with value of the
     * given unit.
     */
    private Double z;

    /**
     * Reference frame within which the position is expressed (see 4.4.2).
     */
    private org.ccsds.moims.mo.mal.structures.Identifier frame;

    /**
     * The units for the quantity of distance. Default = ‘km’.
     */
    private String units;

    /**
     * Default constructor for CartesianPosition.
     * 
     */
    public CartesianPosition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param x Cartesian x coordinate defined in the given frame and with value of the given unit.
     * @param y Cartesian y coordinate defined in the given frame and with value of the given unit.
     * @param z Cartesian z coordinate defined in the given frame and with value of the given unit.
     * @param frame Reference frame within which the position is expressed (see 4.4.2).
     * @param units The units for the quantity of distance. Default = ‘km’.
     */
    public CartesianPosition(Double x,
            Double y,
            Double z,
            org.ccsds.moims.mo.mal.structures.Identifier frame,
            String units) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.frame = frame;
        this.units = units;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param x Cartesian x coordinate defined in the given frame and with value of the given unit.
     * @param y Cartesian y coordinate defined in the given frame and with value of the given unit.
     * @param z Cartesian z coordinate defined in the given frame and with value of the given unit.
     * @param frame Reference frame within which the position is expressed (see 4.4.2).
     */
    public CartesianPosition(Double x,
            Double y,
            Double z,
            org.ccsds.moims.mo.mal.structures.Identifier frame) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.frame = frame;
        this.units = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.CartesianPosition();
    }

    /**
     * Returns the field x.
     * 
     * @return The field x
     */
    public Double getX() {
        return x;
    }

    /**
     * Returns the field y.
     * 
     * @return The field y
     */
    public Double getY() {
        return y;
    }

    /**
     * Returns the field z.
     * 
     * @return The field z
     */
    public Double getZ() {
        return z;
    }

    /**
     * Returns the field frame.
     * 
     * @return The field frame
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getFrame() {
        return frame;
    }

    /**
     * Returns the field units.
     * 
     * @return The field units
     */
    public String getUnits() {
        return units;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CartesianPosition) {
            if (! super.equals(obj)) {
                return false;
            }
            CartesianPosition other = (CartesianPosition) obj;
            if (x == null) {
                if (other.x != null) {
                    return false;
                }
            } else {
                if (! x.equals(other.x)) {
                    return false;
                }
            }
            if (y == null) {
                if (other.y != null) {
                    return false;
                }
            } else {
                if (! y.equals(other.y)) {
                    return false;
                }
            }
            if (z == null) {
                if (other.z != null) {
                    return false;
                }
            } else {
                if (! z.equals(other.z)) {
                    return false;
                }
            }
            if (frame == null) {
                if (other.frame != null) {
                    return false;
                }
            } else {
                if (! frame.equals(other.frame)) {
                    return false;
                }
            }
            if (units == null) {
                if (other.units != null) {
                    return false;
                }
            } else {
                if (! units.equals(other.units)) {
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
        hash = 83 * hash + (x != null ? x.hashCode() : 0);
        hash = 83 * hash + (y != null ? y.hashCode() : 0);
        hash = 83 * hash + (z != null ? z.hashCode() : 0);
        hash = 83 * hash + (frame != null ? frame.hashCode() : 0);
        hash = 83 * hash + (units != null ? units.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CartesianPosition: ");
        buf.append(super.toString());
        buf.append(", x=").append(x);
        buf.append(", y=").append(y);
        buf.append(", z=").append(z);
        buf.append(", frame=").append(frame);
        buf.append(", units=").append(units);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (x == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'x' cannot be null!");
        }
        if (y == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'y' cannot be null!");
        }
        if (z == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'z' cannot be null!");
        }
        if (frame == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'frame' cannot be null!");
        }
        encoder.encodeDouble(x);
        encoder.encodeDouble(y);
        encoder.encodeDouble(z);
        encoder.encodeIdentifier(frame);
        encoder.encodeNullableString(units);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        x = decoder.decodeDouble();
        y = decoder.decodeDouble();
        z = decoder.decodeDouble();
        frame = decoder.decodeIdentifier();
        units = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
