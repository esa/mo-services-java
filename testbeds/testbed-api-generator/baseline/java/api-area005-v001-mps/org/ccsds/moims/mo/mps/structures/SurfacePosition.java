package org.ccsds.moims.mo.mps.structures;

/**
 * E6: A SurfacePosition is typically used to specify a coordinate on the
 * surface of a celestial body.  Optionally, an altitude above the surface
 * may also be specified.  The reference ellipsoid used to define the surface
 * may be mission specific.
 */
public final class SurfacePosition extends org.ccsds.moims.mo.mps.structures.Position {

    private static final long serialVersionUID = 1407374900330505L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330505L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Angular coordinate.  May also represent azimuth.
     */
    private Double longitude;

    /**
     * Angular coordinate.  May also represent elevation.
     */
    private Double latitude;

    /**
     * Reference frame used to determine the origin and orientation of the reference
     * ellipsoid.  Must be a celestial body reference frame (see 4.4.2).
     */
    private org.ccsds.moims.mo.mal.structures.Identifier frame;

    /**
     * The units for the quantity of angle, in which to express the longitude
     * and latitude. Default = ‘deg’.
     */
    private String units;

    /**
     * Altitude above a reference ellipsoid (negative values allowed). Default
     * = 0.
     */
    private Double altitude;

    /**
     * The units for the quantity of altitude. Default = ‘m’.
     */
    private String altitudeUnits;

    /**
     * Default constructor for SurfacePosition.
     * 
     */
    public SurfacePosition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param longitude Angular coordinate.  May also represent azimuth.
     * @param latitude Angular coordinate.  May also represent elevation.
     * @param frame Reference frame used to determine the origin and orientation of the reference ellipsoid.  Must be a celestial body reference frame (see 4.4.2).
     * @param units The units for the quantity of angle, in which to express the longitude and latitude. Default = ‘deg’.
     * @param altitude Altitude above a reference ellipsoid (negative values allowed). Default = 0.
     * @param altitudeUnits The units for the quantity of altitude. Default = ‘m’.
     */
    public SurfacePosition(Double longitude,
            Double latitude,
            org.ccsds.moims.mo.mal.structures.Identifier frame,
            String units,
            Double altitude,
            String altitudeUnits) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.frame = frame;
        this.units = units;
        this.altitude = altitude;
        this.altitudeUnits = altitudeUnits;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param longitude Angular coordinate.  May also represent azimuth.
     * @param latitude Angular coordinate.  May also represent elevation.
     * @param frame Reference frame used to determine the origin and orientation of the reference ellipsoid.  Must be a celestial body reference frame (see 4.4.2).
     */
    public SurfacePosition(Double longitude,
            Double latitude,
            org.ccsds.moims.mo.mal.structures.Identifier frame) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.frame = frame;
        this.units = null;
        this.altitude = null;
        this.altitudeUnits = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.SurfacePosition();
    }

    /**
     * Returns the field longitude.
     * 
     * @return The field longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Returns the field latitude.
     * 
     * @return The field latitude
     */
    public Double getLatitude() {
        return latitude;
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

    /**
     * Returns the field altitude.
     * 
     * @return The field altitude
     */
    public Double getAltitude() {
        return altitude;
    }

    /**
     * Returns the field altitudeUnits.
     * 
     * @return The field altitudeUnits
     */
    public String getAltitudeUnits() {
        return altitudeUnits;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SurfacePosition) {
            if (! super.equals(obj)) {
                return false;
            }
            SurfacePosition other = (SurfacePosition) obj;
            if (longitude == null) {
                if (other.longitude != null) {
                    return false;
                }
            } else {
                if (! longitude.equals(other.longitude)) {
                    return false;
                }
            }
            if (latitude == null) {
                if (other.latitude != null) {
                    return false;
                }
            } else {
                if (! latitude.equals(other.latitude)) {
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
            if (altitude == null) {
                if (other.altitude != null) {
                    return false;
                }
            } else {
                if (! altitude.equals(other.altitude)) {
                    return false;
                }
            }
            if (altitudeUnits == null) {
                if (other.altitudeUnits != null) {
                    return false;
                }
            } else {
                if (! altitudeUnits.equals(other.altitudeUnits)) {
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
        hash = 83 * hash + (longitude != null ? longitude.hashCode() : 0);
        hash = 83 * hash + (latitude != null ? latitude.hashCode() : 0);
        hash = 83 * hash + (frame != null ? frame.hashCode() : 0);
        hash = 83 * hash + (units != null ? units.hashCode() : 0);
        hash = 83 * hash + (altitude != null ? altitude.hashCode() : 0);
        hash = 83 * hash + (altitudeUnits != null ? altitudeUnits.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SurfacePosition: ");
        buf.append(super.toString());
        buf.append(", longitude=").append(longitude);
        buf.append(", latitude=").append(latitude);
        buf.append(", frame=").append(frame);
        buf.append(", units=").append(units);
        buf.append(", altitude=").append(altitude);
        buf.append(", altitudeUnits=").append(altitudeUnits);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (longitude == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'longitude' cannot be null!");
        }
        if (latitude == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'latitude' cannot be null!");
        }
        if (frame == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'frame' cannot be null!");
        }
        encoder.encodeDouble(longitude);
        encoder.encodeDouble(latitude);
        encoder.encodeIdentifier(frame);
        encoder.encodeNullableString(units);
        encoder.encodeNullableDouble(altitude);
        encoder.encodeNullableString(altitudeUnits);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        longitude = decoder.decodeDouble();
        latitude = decoder.decodeDouble();
        frame = decoder.decodeIdentifier();
        units = decoder.decodeNullableString();
        altitude = decoder.decodeNullableDouble();
        altitudeUnits = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
