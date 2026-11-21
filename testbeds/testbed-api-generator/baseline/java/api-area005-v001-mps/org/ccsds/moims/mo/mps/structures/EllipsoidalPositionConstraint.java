package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E7: Geometric constraint that specifies a Position with a tolerance given
 * in terms of a frame-aligned ellipsoid.  The frame within which this tolerance
 * ellipsoid is expressed may be different from the frame in which the required
 * position is expressed.  .
 */
public final class EllipsoidalPositionConstraint extends GeometricConstraint {

    private static final long serialVersionUID = 1407374900330536L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330536L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Specifies the required position expressed using any concrete position type.
     */
    private Element position;

    /**
     * Length of the ellipsoid axis that is aligned with the x axis of the specified
     * frame.
     */
    private Element x;

    /**
     * Length of the ellipsoid axis that is aligned with the y axis of the specified
     * frame.
     */
    private Element y;

    /**
     * Length of the ellipsoid axis that is aligned with the z axis of the specified
     * frame.
     */
    private Element z;

    /**
     * Reference frame with which the axes of the tolerance ellipsoid are aligned
     * (see 4.4.2).
     */
    private Identifier frame;

    /**
     * Optional.  The tolerance units name, either for the quantity of distance
     * or the quantity of angle. Default = ‘km’, but ‘deg’ is more relevant for
     * an OrbitalPosition.
     */
    private String units;

    /**
     * Default constructor for EllipsoidalPositionConstraint.
     * 
     */
    public EllipsoidalPositionConstraint() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param negate Specifies whether the result of combining the Constraints is to be inverted (NOT function). Default = False.
     * @param startRef Identifies the point in the duration of the applicable planning activity to which the start of the constraint period relates. Default is the start of the planning activity.
     * @param endRef Identifies the point in the duration of the applicable planning activity to which the end of the constraint period relates. Default is the end of the planning activity.
     * @param startOffset Offset from startRef that specifies the start of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param endOffset Offset from endRef that specifies the end of the constraint period.  A positive offset implies a shift later in time. Default is no offset.
     * @param position Specifies the required position expressed using any concrete position type.
     * @param x Length of the ellipsoid axis that is aligned with the x axis of the specified frame.
     * @param y Length of the ellipsoid axis that is aligned with the y axis of the specified frame.
     * @param z Length of the ellipsoid axis that is aligned with the z axis of the specified frame.
     * @param frame Reference frame with which the axes of the tolerance ellipsoid are aligned (see 4.4.2).
     * @param units Optional.  The tolerance units name, either for the quantity of distance or the quantity of angle. Default = ‘km’, but ‘deg’ is more relevant for an OrbitalPosition.
     */
    public EllipsoidalPositionConstraint(Boolean negate,
            Slider startRef,
            Slider endRef,
            Element startOffset,
            Element endOffset,
            Element position,
            Element x,
            Element y,
            Element z,
            Identifier frame,
            String units) {
        super(negate,
            startRef,
            endRef,
            startOffset,
            endOffset);
        this.position = position;
        this.x = x;
        this.y = y;
        this.z = z;
        this.frame = frame;
        this.units = units;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param position Specifies the required position expressed using any concrete position type.
     * @param x Length of the ellipsoid axis that is aligned with the x axis of the specified frame.
     * @param y Length of the ellipsoid axis that is aligned with the y axis of the specified frame.
     * @param z Length of the ellipsoid axis that is aligned with the z axis of the specified frame.
     * @param frame Reference frame with which the axes of the tolerance ellipsoid are aligned (see 4.4.2).
     */
    public EllipsoidalPositionConstraint(Element position,
            Element x,
            Element y,
            Element z,
            Identifier frame) {
        this.position = position;
        this.x = x;
        this.y = y;
        this.z = z;
        this.frame = frame;
        this.units = null;
    }

    @Override
    public Element createElement() {
        return new EllipsoidalPositionConstraint();
    }

    /**
     * Returns the field position.
     * 
     * @return The field position
     */
    public Element getPosition() {
        return position;
    }

    /**
     * Returns the field x.
     * 
     * @return The field x
     */
    public Element getX() {
        return x;
    }

    /**
     * Returns the field y.
     * 
     * @return The field y
     */
    public Element getY() {
        return y;
    }

    /**
     * Returns the field z.
     * 
     * @return The field z
     */
    public Element getZ() {
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
        if (obj instanceof EllipsoidalPositionConstraint) {
            if (! super.equals(obj)) {
                return false;
            }
            EllipsoidalPositionConstraint other = (EllipsoidalPositionConstraint) obj;
            if (position == null) {
                if (other.position != null) {
                    return false;
                }
            } else {
                if (! position.equals(other.position)) {
                    return false;
                }
            }
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
        hash = 83 * hash + (position != null ? position.hashCode() : 0);
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
        buf.append("(EllipsoidalPositionConstraint: ");
        buf.append(super.toString());
        buf.append(", position=").append(position);
        buf.append(", x=").append(x);
        buf.append(", y=").append(y);
        buf.append(", z=").append(z);
        buf.append(", frame=").append(frame);
        buf.append(", units=").append(units);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (position == null) {
            throw new MALException("The field 'position' cannot be null!");
        }
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
        encoder.encodeAbstractElement(position);
        encoder.encodeAbstractElement(x);
        encoder.encodeAbstractElement(y);
        encoder.encodeAbstractElement(z);
        encoder.encodeIdentifier(frame);
        encoder.encodeNullableString(units);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        position = (Element) decoder.decodeAbstractElement();
        x = (Element) decoder.decodeAbstractElement();
        y = (Element) decoder.decodeAbstractElement();
        z = (Element) decoder.decodeAbstractElement();
        frame = decoder.decodeIdentifier();
        units = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
