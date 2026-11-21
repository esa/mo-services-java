package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E6: An OrbitalPosition represents a Position that is defined with respect
 * to some mission specific orbit.  The conventions used to derive the orbitNumber
 * and orbitAngle follow from a mission specific definition.
 */
public final class OrbitalPosition extends Position {

    private static final long serialVersionUID = 1407374900330507L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330507L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Orbit number.  Depending on the relativeOrbit flag, the orbit number may
     * be absolute (since start of mission) or relative (to the orbital repeat
     * cycle).
     */
    private Integer orbitNumber;

    /**
     * Flag indicating if the orbit number is absolute or relative to the orbital
     * repeat cycle.
     */
    private Boolean relativeOrbit;

    /**
     * Angle within orbit.  Whether this angle is the mean or true anomaly and
     * from which datum it is measured are mission specific.
     */
    private Angle orbitAngle;

    /**
     * Default constructor for OrbitalPosition.
     * 
     */
    public OrbitalPosition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param orbitNumber Orbit number.  Depending on the relativeOrbit flag, the orbit number may be absolute (since start of mission) or relative (to the orbital repeat cycle).
     * @param relativeOrbit Flag indicating if the orbit number is absolute or relative to the orbital repeat cycle.
     * @param orbitAngle Angle within orbit.  Whether this angle is the mean or true anomaly and from which datum it is measured are mission specific.
     */
    public OrbitalPosition(Integer orbitNumber,
            Boolean relativeOrbit,
            Angle orbitAngle) {
        this.orbitNumber = orbitNumber;
        this.relativeOrbit = relativeOrbit;
        this.orbitAngle = orbitAngle;
    }

    @Override
    public Element createElement() {
        return new OrbitalPosition();
    }

    /**
     * Returns the field orbitNumber.
     * 
     * @return The field orbitNumber
     */
    public Integer getOrbitNumber() {
        return orbitNumber;
    }

    /**
     * Returns the field relativeOrbit.
     * 
     * @return The field relativeOrbit
     */
    public Boolean getRelativeOrbit() {
        return relativeOrbit;
    }

    /**
     * Returns the field orbitAngle.
     * 
     * @return The field orbitAngle
     */
    public Angle getOrbitAngle() {
        return orbitAngle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrbitalPosition) {
            if (! super.equals(obj)) {
                return false;
            }
            OrbitalPosition other = (OrbitalPosition) obj;
            if (orbitNumber == null) {
                if (other.orbitNumber != null) {
                    return false;
                }
            } else {
                if (! orbitNumber.equals(other.orbitNumber)) {
                    return false;
                }
            }
            if (relativeOrbit == null) {
                if (other.relativeOrbit != null) {
                    return false;
                }
            } else {
                if (! relativeOrbit.equals(other.relativeOrbit)) {
                    return false;
                }
            }
            if (orbitAngle == null) {
                if (other.orbitAngle != null) {
                    return false;
                }
            } else {
                if (! orbitAngle.equals(other.orbitAngle)) {
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
        hash = 83 * hash + (orbitNumber != null ? orbitNumber.hashCode() : 0);
        hash = 83 * hash + (relativeOrbit != null ? relativeOrbit.hashCode() : 0);
        hash = 83 * hash + (orbitAngle != null ? orbitAngle.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(OrbitalPosition: ");
        buf.append(super.toString());
        buf.append(", orbitNumber=").append(orbitNumber);
        buf.append(", relativeOrbit=").append(relativeOrbit);
        buf.append(", orbitAngle=").append(orbitAngle);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (orbitNumber == null) {
            throw new MALException("The field 'orbitNumber' cannot be null!");
        }
        if (relativeOrbit == null) {
            throw new MALException("The field 'relativeOrbit' cannot be null!");
        }
        if (orbitAngle == null) {
            throw new MALException("The field 'orbitAngle' cannot be null!");
        }
        encoder.encodeInteger(orbitNumber);
        encoder.encodeBoolean(relativeOrbit);
        encoder.encodeElement(orbitAngle);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        orbitNumber = decoder.decodeInteger();
        relativeOrbit = decoder.decodeBoolean();
        orbitAngle = (Angle) decoder.decodeElement(new Angle());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
