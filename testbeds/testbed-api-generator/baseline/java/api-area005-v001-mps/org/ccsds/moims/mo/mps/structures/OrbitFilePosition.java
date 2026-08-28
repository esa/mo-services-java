package org.ccsds.moims.mo.mps.structures;

/**
 * E6: An OrbitFilePosition represents a Position that is defined with respect
 * to some Orbit Data Message (ODM) file (reference [D10]).
 */
public final class OrbitFilePosition extends org.ccsds.moims.mo.mps.structures.Position {

    private static final long serialVersionUID = 1407374900330506L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330506L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name of or reference to a file containing an ODM.
     */
    private org.ccsds.moims.mo.mal.structures.File orbitFile;

    /**
     * Default constructor for OrbitFilePosition.
     * 
     */
    public OrbitFilePosition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param orbitFile Name of or reference to a file containing an ODM.
     */
    public OrbitFilePosition(org.ccsds.moims.mo.mal.structures.File orbitFile) {
        this.orbitFile = orbitFile;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.OrbitFilePosition();
    }

    /**
     * Returns the field orbitFile.
     * 
     * @return The field orbitFile
     */
    public org.ccsds.moims.mo.mal.structures.File getOrbitFile() {
        return orbitFile;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OrbitFilePosition) {
            if (! super.equals(obj)) {
                return false;
            }
            OrbitFilePosition other = (OrbitFilePosition) obj;
            if (orbitFile == null) {
                if (other.orbitFile != null) {
                    return false;
                }
            } else {
                if (! orbitFile.equals(other.orbitFile)) {
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
        hash = 83 * hash + (orbitFile != null ? orbitFile.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(OrbitFilePosition: ");
        buf.append(super.toString());
        buf.append(", orbitFile=").append(orbitFile);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (orbitFile == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'orbitFile' cannot be null!");
        }
        encoder.encodeElement(orbitFile);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        orbitFile = (org.ccsds.moims.mo.mal.structures.File) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.File());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
