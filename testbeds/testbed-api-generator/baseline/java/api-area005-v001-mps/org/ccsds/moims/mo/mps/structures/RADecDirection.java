package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E6: Represents a Direction based on celestial angular coordinates of right
 * ascension and declination.
 */
public final class RADecDirection extends Direction {

    private static final long serialVersionUID = 1407374900330512L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330512L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Right Ascension: Celestial angular coordinate, measured eastward along
     * the celestial equator.
     */
    private Angle ra;

    /**
     * Declination: Celestial angular coordinate, north or south of the celestial
     * equator.
     */
    private Angle dec;

    /**
     * Reference frame within which the direction is expressed.  Must be a quasi-inertial
     * celestial body or orbit-related frame (see 4.4.2).
     */
    private Identifier frame;

    /**
     * Default constructor for RADecDirection.
     * 
     */
    public RADecDirection() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param ra Right Ascension: Celestial angular coordinate, measured eastward along the celestial equator.
     * @param dec Declination: Celestial angular coordinate, north or south of the celestial equator.
     * @param frame Reference frame within which the direction is expressed.  Must be a quasi-inertial celestial body or orbit-related frame (see 4.4.2).
     */
    public RADecDirection(Angle ra,
            Angle dec,
            Identifier frame) {
        this.ra = ra;
        this.dec = dec;
        this.frame = frame;
    }

    @Override
    public Element createElement() {
        return new RADecDirection();
    }

    /**
     * Returns the field ra.
     * 
     * @return The field ra
     */
    public Angle getRa() {
        return ra;
    }

    /**
     * Returns the field dec.
     * 
     * @return The field dec
     */
    public Angle getDec() {
        return dec;
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
        if (obj instanceof RADecDirection) {
            if (! super.equals(obj)) {
                return false;
            }
            RADecDirection other = (RADecDirection) obj;
            if (ra == null) {
                if (other.ra != null) {
                    return false;
                }
            } else {
                if (! ra.equals(other.ra)) {
                    return false;
                }
            }
            if (dec == null) {
                if (other.dec != null) {
                    return false;
                }
            } else {
                if (! dec.equals(other.dec)) {
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
        hash = 83 * hash + (ra != null ? ra.hashCode() : 0);
        hash = 83 * hash + (dec != null ? dec.hashCode() : 0);
        hash = 83 * hash + (frame != null ? frame.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(RADecDirection: ");
        buf.append(super.toString());
        buf.append(", ra=").append(ra);
        buf.append(", dec=").append(dec);
        buf.append(", frame=").append(frame);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (ra == null) {
            throw new MALException("The field 'ra' cannot be null!");
        }
        if (dec == null) {
            throw new MALException("The field 'dec' cannot be null!");
        }
        if (frame == null) {
            throw new MALException("The field 'frame' cannot be null!");
        }
        encoder.encodeElement(ra);
        encoder.encodeElement(dec);
        encoder.encodeIdentifier(frame);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        ra = (Angle) decoder.decodeElement(new Angle());
        dec = (Angle) decoder.decodeElement(new Angle());
        frame = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
