package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E6: Typically used to define a direction in a secondary frame.  When used
 * to specify a surface coordinate, this actually represents a {longitude,
 * latitude} pair.
 */
public final class SphericalDirection extends Direction {

    private static final long serialVersionUID = 1407374900330511L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330511L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Angular coordinate.  May also represent longitude.
     */
    private Angle azimuth;

    /**
     * Angular coordinate.  May also represent latitude.
     */
    private Angle elevation;

    /**
     * Reference frame within which the direction is expressed.  Must be a celestial
     * body or spacecraft reference frame (see 4.4.2).
     */
    private Identifier frame;

    /**
     * Default constructor for SphericalDirection.
     * 
     */
    public SphericalDirection() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param azimuth Angular coordinate.  May also represent longitude.
     * @param elevation Angular coordinate.  May also represent latitude.
     * @param frame Reference frame within which the direction is expressed.  Must be a celestial body or spacecraft reference frame (see 4.4.2).
     */
    public SphericalDirection(Angle azimuth,
            Angle elevation,
            Identifier frame) {
        this.azimuth = azimuth;
        this.elevation = elevation;
        this.frame = frame;
    }

    @Override
    public Element createElement() {
        return new SphericalDirection();
    }

    /**
     * Returns the field azimuth.
     * 
     * @return The field azimuth
     */
    public Angle getAzimuth() {
        return azimuth;
    }

    /**
     * Returns the field elevation.
     * 
     * @return The field elevation
     */
    public Angle getElevation() {
        return elevation;
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
        if (obj instanceof SphericalDirection) {
            if (! super.equals(obj)) {
                return false;
            }
            SphericalDirection other = (SphericalDirection) obj;
            if (azimuth == null) {
                if (other.azimuth != null) {
                    return false;
                }
            } else {
                if (! azimuth.equals(other.azimuth)) {
                    return false;
                }
            }
            if (elevation == null) {
                if (other.elevation != null) {
                    return false;
                }
            } else {
                if (! elevation.equals(other.elevation)) {
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
        hash = 83 * hash + (azimuth != null ? azimuth.hashCode() : 0);
        hash = 83 * hash + (elevation != null ? elevation.hashCode() : 0);
        hash = 83 * hash + (frame != null ? frame.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SphericalDirection: ");
        buf.append(super.toString());
        buf.append(", azimuth=").append(azimuth);
        buf.append(", elevation=").append(elevation);
        buf.append(", frame=").append(frame);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (azimuth == null) {
            throw new MALException("The field 'azimuth' cannot be null!");
        }
        if (elevation == null) {
            throw new MALException("The field 'elevation' cannot be null!");
        }
        if (frame == null) {
            throw new MALException("The field 'frame' cannot be null!");
        }
        encoder.encodeElement(azimuth);
        encoder.encodeElement(elevation);
        encoder.encodeIdentifier(frame);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        azimuth = (Angle) decoder.decodeElement(new Angle());
        elevation = (Angle) decoder.decodeElement(new Angle());
        frame = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
