package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.File;

/**
 * E6: An OrbitFilePosition represents a Position that is defined with respect
 * to some Orbit Data Message (ODM) file (reference [D10]).
 */
public final class OrbitFilePosition extends Position {

    private static final long serialVersionUID = 1407374900330506L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330506L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Name of or reference to a file containing an ODM.
     */
    private File orbitFile;

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
    public OrbitFilePosition(File orbitFile) {
        this.orbitFile = orbitFile;
    }

    @Override
    public Element createElement() {
        return new OrbitFilePosition();
    }

    /**
     * Returns the field orbitFile.
     * 
     * @return The field orbitFile
     */
    public File getOrbitFile() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (orbitFile == null) {
            throw new MALException("The field 'orbitFile' cannot be null!");
        }
        encoder.encodeElement(orbitFile);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        orbitFile = (File) decoder.decodeElement(new File());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
