package org.ccsds.moims.mo.mps.structures;

/**
 * E1: The instance of an argument is an Argument, a set of which may be contained
 * within the instance MO object of a planning event or planning activity
 * or within a planning request.  This comprises the name and value of the
 * argument, corresponding to the set of arguments defined in the ArgDef.
 * Argument values are represented as a MAL Element of appropriate data type.
 */
public final class Argument implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330520L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330520L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name of the argument.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier argName;

    /**
     * Argument value (or values if it is an array).  The MAL Element subtype(s)
     * must match the argument type supplied in the corresponding ArgDef.
     */
    private org.ccsds.moims.mo.mal.structures.HeterogeneousList argValues;

    /**
     * Default constructor for Argument.
     * 
     */
    public Argument() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param argName Name of the argument.
     * @param argValues Argument value (or values if it is an array).  The MAL Element subtype(s) must match the argument type supplied in the corresponding ArgDef.
     */
    public Argument(org.ccsds.moims.mo.mal.structures.Identifier argName,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList argValues) {
        this.argName = argName;
        this.argValues = argValues;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.Argument();
    }

    /**
     * Returns the field argName.
     * 
     * @return The field argName
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getArgName() {
        return argName;
    }

    /**
     * Returns the field argValues.
     * 
     * @return The field argValues
     */
    public org.ccsds.moims.mo.mal.structures.HeterogeneousList getArgValues() {
        return argValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Argument) {
            Argument other = (Argument) obj;
            if (argName == null) {
                if (other.argName != null) {
                    return false;
                }
            } else {
                if (! argName.equals(other.argName)) {
                    return false;
                }
            }
            if (argValues == null) {
                if (other.argValues != null) {
                    return false;
                }
            } else {
                if (! argValues.equals(other.argValues)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (argName != null ? argName.hashCode() : 0);
        hash = 83 * hash + (argValues != null ? argValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Argument: ");
        buf.append("argName=").append(argName);
        buf.append(", argValues=").append(argValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (argName == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argName' cannot be null!");
        }
        if (argValues == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argValues' cannot be null!");
        }
        encoder.encodeIdentifier(argName);
        encoder.encodeElement(argValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        argName = decoder.decodeIdentifier();
        argValues = (org.ccsds.moims.mo.mal.structures.HeterogeneousList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.HeterogeneousList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
