package org.ccsds.moims.mo.mps.structures;

/**
 * E6: A sub-type of Repetition based on the orbital cycle.
 */
public final class OrbitRepetition extends org.ccsds.moims.mo.mps.structures.Repetition {

    private static final long serialVersionUID = 1407374900330554L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330554L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Orbit number for the first occurrence.  Depending on the relativeOrbit
     * flag, the orbit number may be absolute (since start of mission) or relative
     * (to the orbital repeat cycle). The datum with respect to which the orbit
     * number is counted is mission specific.
     */
    private org.ccsds.moims.mo.mal.structures.Element orbitNumber;

    /**
     * Flag indicating if the orbit number is absolute or relative to the orbital
     * repeat cycle.
     */
    private Boolean relativeOrbit;

    /**
     * The required number of orbits separation between occurrences.  If orbitNumber
     * is Relative and the required repetition is once per repeat cycle, this
     * is the number of orbits in the repeat cycle, but the value 0 may also be
     * used.
     */
    private org.ccsds.moims.mo.mal.structures.Element orbitSeparation;

    /**
     * The required angular separation between occurrences.  This allows for multiple
     * repetitions within an orbit.  The value 0 indicates only one occurrence
     * within the orbit.
     */
    private org.ccsds.moims.mo.mal.structures.Element angularSeparation;

    /**
     * The required position of the first occurrence within the orbit expressed
     * as an angle.
     */
    private org.ccsds.moims.mo.mal.structures.Element orbitAngle;

    /**
     * The allowed tolerance (+/-) in the required orbital angle.
     */
    private org.ccsds.moims.mo.mal.structures.Element tolerance;

    /**
     * Default constructor for OrbitRepetition.
     * 
     */
    public OrbitRepetition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param count Maximum number of repeat cycles/instances. If not specified there is no limit to the number of repetitions.
     * @param timeWindow Time period over which the repetition is applicable. If not specified repetition continues indefinitely.
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param orbitNumber Orbit number for the first occurrence.  Depending on the relativeOrbit flag, the orbit number may be absolute (since start of mission) or relative (to the orbital repeat cycle). The datum with respect to which the orbit number is counted is mission specific.
     * @param relativeOrbit Flag indicating if the orbit number is absolute or relative to the orbital repeat cycle.
     * @param orbitSeparation The required number of orbits separation between occurrences.  If orbitNumber is Relative and the required repetition is once per repeat cycle, this is the number of orbits in the repeat cycle, but the value 0 may also be used.
     * @param angularSeparation The required angular separation between occurrences.  This allows for multiple repetitions within an orbit.  The value 0 indicates only one occurrence within the orbit.
     * @param orbitAngle The required position of the first occurrence within the orbit expressed as an angle.
     * @param tolerance The allowed tolerance (+/-) in the required orbital angle.
     */
    public OrbitRepetition(Integer count,
            org.ccsds.moims.mo.mps.structures.TimeWindow timeWindow,
            org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element orbitNumber,
            Boolean relativeOrbit,
            org.ccsds.moims.mo.mal.structures.Element orbitSeparation,
            org.ccsds.moims.mo.mal.structures.Element angularSeparation,
            org.ccsds.moims.mo.mal.structures.Element orbitAngle,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(count,
            timeWindow,
            separationType);
        this.orbitNumber = orbitNumber;
        this.relativeOrbit = relativeOrbit;
        this.orbitSeparation = orbitSeparation;
        this.angularSeparation = angularSeparation;
        this.orbitAngle = orbitAngle;
        this.tolerance = tolerance;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param separationType Specifies whether the repetition interval is Relative to the previous occurrence, or Absolute for all occurrences.
     * @param orbitNumber Orbit number for the first occurrence.  Depending on the relativeOrbit flag, the orbit number may be absolute (since start of mission) or relative (to the orbital repeat cycle). The datum with respect to which the orbit number is counted is mission specific.
     * @param relativeOrbit Flag indicating if the orbit number is absolute or relative to the orbital repeat cycle.
     * @param orbitSeparation The required number of orbits separation between occurrences.  If orbitNumber is Relative and the required repetition is once per repeat cycle, this is the number of orbits in the repeat cycle, but the value 0 may also be used.
     * @param angularSeparation The required angular separation between occurrences.  This allows for multiple repetitions within an orbit.  The value 0 indicates only one occurrence within the orbit.
     * @param orbitAngle The required position of the first occurrence within the orbit expressed as an angle.
     * @param tolerance The allowed tolerance (+/-) in the required orbital angle.
     */
    public OrbitRepetition(org.ccsds.moims.mo.mps.structures.SeparationTypeEnum separationType,
            org.ccsds.moims.mo.mal.structures.Element orbitNumber,
            Boolean relativeOrbit,
            org.ccsds.moims.mo.mal.structures.Element orbitSeparation,
            org.ccsds.moims.mo.mal.structures.Element angularSeparation,
            org.ccsds.moims.mo.mal.structures.Element orbitAngle,
            org.ccsds.moims.mo.mal.structures.Element tolerance) {
        super(separationType);
        this.orbitNumber = orbitNumber;
        this.relativeOrbit = relativeOrbit;
        this.orbitSeparation = orbitSeparation;
        this.angularSeparation = angularSeparation;
        this.orbitAngle = orbitAngle;
        this.tolerance = tolerance;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.OrbitRepetition();
    }

    /**
     * Returns the field orbitNumber.
     * 
     * @return The field orbitNumber
     */
    public org.ccsds.moims.mo.mal.structures.Element getOrbitNumber() {
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
     * Returns the field orbitSeparation.
     * 
     * @return The field orbitSeparation
     */
    public org.ccsds.moims.mo.mal.structures.Element getOrbitSeparation() {
        return orbitSeparation;
    }

    /**
     * Returns the field angularSeparation.
     * 
     * @return The field angularSeparation
     */
    public org.ccsds.moims.mo.mal.structures.Element getAngularSeparation() {
        return angularSeparation;
    }

    /**
     * Returns the field orbitAngle.
     * 
     * @return The field orbitAngle
     */
    public org.ccsds.moims.mo.mal.structures.Element getOrbitAngle() {
        return orbitAngle;
    }

    /**
     * Returns the field tolerance.
     * 
     * @return The field tolerance
     */
    public org.ccsds.moims.mo.mal.structures.Element getTolerance() {
        return tolerance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrbitRepetition) {
            if (! super.equals(obj)) {
                return false;
            }
            OrbitRepetition other = (OrbitRepetition) obj;
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
            if (orbitSeparation == null) {
                if (other.orbitSeparation != null) {
                    return false;
                }
            } else {
                if (! orbitSeparation.equals(other.orbitSeparation)) {
                    return false;
                }
            }
            if (angularSeparation == null) {
                if (other.angularSeparation != null) {
                    return false;
                }
            } else {
                if (! angularSeparation.equals(other.angularSeparation)) {
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
            if (tolerance == null) {
                if (other.tolerance != null) {
                    return false;
                }
            } else {
                if (! tolerance.equals(other.tolerance)) {
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
        hash = 83 * hash + (orbitSeparation != null ? orbitSeparation.hashCode() : 0);
        hash = 83 * hash + (angularSeparation != null ? angularSeparation.hashCode() : 0);
        hash = 83 * hash + (orbitAngle != null ? orbitAngle.hashCode() : 0);
        hash = 83 * hash + (tolerance != null ? tolerance.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(OrbitRepetition: ");
        buf.append(super.toString());
        buf.append(", orbitNumber=").append(orbitNumber);
        buf.append(", relativeOrbit=").append(relativeOrbit);
        buf.append(", orbitSeparation=").append(orbitSeparation);
        buf.append(", angularSeparation=").append(angularSeparation);
        buf.append(", orbitAngle=").append(orbitAngle);
        buf.append(", tolerance=").append(tolerance);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (orbitNumber == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'orbitNumber' cannot be null!");
        }
        if (relativeOrbit == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'relativeOrbit' cannot be null!");
        }
        if (orbitSeparation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'orbitSeparation' cannot be null!");
        }
        if (angularSeparation == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'angularSeparation' cannot be null!");
        }
        if (orbitAngle == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'orbitAngle' cannot be null!");
        }
        if (tolerance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'tolerance' cannot be null!");
        }
        encoder.encodeAbstractElement(orbitNumber);
        encoder.encodeBoolean(relativeOrbit);
        encoder.encodeAbstractElement(orbitSeparation);
        encoder.encodeAbstractElement(angularSeparation);
        encoder.encodeAbstractElement(orbitAngle);
        encoder.encodeAbstractElement(tolerance);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        orbitNumber = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        relativeOrbit = decoder.decodeBoolean();
        orbitSeparation = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        angularSeparation = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        orbitAngle = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        tolerance = (org.ccsds.moims.mo.mal.structures.Element) decoder.decodeAbstractElement();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
