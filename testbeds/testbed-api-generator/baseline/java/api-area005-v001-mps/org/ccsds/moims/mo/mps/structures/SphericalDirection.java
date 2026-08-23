package org.ccsds.moims.mo.mps.structures;

/**
 * E6: Typically used to define a direction in a secondary frame.  When used
 * to specify a surface coordinate, this actually represents a {longitude,
 * latitude} pair.
 */
public final class SphericalDirection extends org.ccsds.moims.mo.mps.structures.Direction {

    private static final long serialVersionUID = 1407374900330511L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330511L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Angular coordinate.  May also represent longitude.
     */
    private org.ccsds.moims.mo.mps.structures.Angle azimuth;

    /**
     * Angular coordinate.  May also represent latitude.
     */
    private org.ccsds.moims.mo.mps.structures.Angle elevation;

    /**
     * Reference frame within which the direction is expressed.  Must be a celestial
     * body or spacecraft reference frame (see 4.4.2).
     */
    private org.ccsds.moims.mo.mal.structures.Identifier frame;

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
    public SphericalDirection(org.ccsds.moims.mo.mps.structures.Angle azimuth,
            org.ccsds.moims.mo.mps.structures.Angle elevation,
            org.ccsds.moims.mo.mal.structures.Identifier frame) {
        this.azimuth = azimuth;
        this.elevation = elevation;
        this.frame = frame;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.SphericalDirection();
    }

    /**
     * Returns the field azimuth.
     * 
     * @return The field azimuth
     */
    public org.ccsds.moims.mo.mps.structures.Angle getAzimuth() {
        return azimuth;
    }

    /**
     * Returns the field elevation.
     * 
     * @return The field elevation
     */
    public org.ccsds.moims.mo.mps.structures.Angle getElevation() {
        return elevation;
    }

    /**
     * Returns the field frame.
     * 
     * @return The field frame
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getFrame() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (azimuth == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'azimuth' cannot be null!");
        }
        if (elevation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'elevation' cannot be null!");
        }
        if (frame == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'frame' cannot be null!");
        }
        encoder.encodeElement(azimuth);
        encoder.encodeElement(elevation);
        encoder.encodeIdentifier(frame);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        azimuth = (org.ccsds.moims.mo.mps.structures.Angle) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.Angle());
        elevation = (org.ccsds.moims.mo.mps.structures.Angle) decoder.decodeElement(new org.ccsds.moims.mo.mps.structures.Angle());
        frame = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
