package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E6: Dimensionless unit vector.  Either a direction in the base frame or
 * in a secondary frame may be defined.
 */
public final class CartesianDirection extends Direction {

    private static final long serialVersionUID = 1407374900330510L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330510L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Cartesian x coordinate defined in the given frame.
     */
    private Double x;

    /**
     * Cartesian y coordinate defined in the given frame.
     */
    private Double y;

    /**
     * Cartesian z coordinate defined in the given frame.
     */
    private Double z;

    /**
     * Reference frame within which the direction is expressed (see 4.4.2).
     */
    private Identifier frame;

    /**
     * Default constructor for CartesianDirection.
     * 
     */
    public CartesianDirection() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param x Cartesian x coordinate defined in the given frame.
     * @param y Cartesian y coordinate defined in the given frame.
     * @param z Cartesian z coordinate defined in the given frame.
     * @param frame Reference frame within which the direction is expressed (see 4.4.2).
     */
    public CartesianDirection(Double x,
            Double y,
            Double z,
            Identifier frame) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.frame = frame;
    }

    @Override
    public Element createElement() {
        return new CartesianDirection();
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
    public Identifier getFrame() {
        return frame;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CartesianDirection) {
            if (! super.equals(obj)) {
                return false;
            }
            CartesianDirection other = (CartesianDirection) obj;
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
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CartesianDirection: ");
        buf.append(super.toString());
        buf.append(", x=").append(x);
        buf.append(", y=").append(y);
        buf.append(", z=").append(z);
        buf.append(", frame=").append(frame);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (x == null) {
            throw new MALException("The field 'x' cannot be null!");
        }
        if (y == null) {
            throw new MALException("The field 'y' cannot be null!");
        }
        if (z == null) {
            throw new MALException("The field 'z' cannot be null!");
        }
        if (frame == null) {
            throw new MALException("The field 'frame' cannot be null!");
        }
        encoder.encodeDouble(x);
        encoder.encodeDouble(y);
        encoder.encodeDouble(z);
        encoder.encodeIdentifier(frame);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        x = decoder.decodeDouble();
        y = decoder.decodeDouble();
        z = decoder.decodeDouble();
        frame = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
