package org.ccsds.moims.mo.mps.structures;

/**
 * E1: In the case of the planning activity, there is also an ArgSpec, a set
 * of which may be contained within the ActivityDetails structure embedded
 * within a planning request or parent planning activity definition.  The
 * ArgSpec defines how to derive the value of an Argument when instantiating
 * it at run-time.  .
 */
public final class ArgSpec implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330521L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330521L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name of the argument.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier argName;

    /**
     * Expression that can be evaluated at run-time to provide argument value(s)
     * of appropriate.  The MAL Element subtype(s) must match the argument type
     * supplied in the corresponding ArgDef.
     */
    private org.ccsds.moims.mo.mal.structures.HeterogeneousList argSpecValues;

    /**
     * Default constructor for ArgSpec.
     * 
     */
    public ArgSpec() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param argName Name of the argument.
     * @param argSpecValues Expression that can be evaluated at run-time to provide argument value(s) of appropriate.  The MAL Element subtype(s) must match the argument type supplied in the corresponding ArgDef.
     */
    public ArgSpec(org.ccsds.moims.mo.mal.structures.Identifier argName,
            org.ccsds.moims.mo.mal.structures.HeterogeneousList argSpecValues) {
        this.argName = argName;
        this.argSpecValues = argSpecValues;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ArgSpec();
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
     * Returns the field argSpecValues.
     * 
     * @return The field argSpecValues
     */
    public org.ccsds.moims.mo.mal.structures.HeterogeneousList getArgSpecValues() {
        return argSpecValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArgSpec) {
            ArgSpec other = (ArgSpec) obj;
            if (argName == null) {
                if (other.argName != null) {
                    return false;
                }
            } else {
                if (! argName.equals(other.argName)) {
                    return false;
                }
            }
            if (argSpecValues == null) {
                if (other.argSpecValues != null) {
                    return false;
                }
            } else {
                if (! argSpecValues.equals(other.argSpecValues)) {
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
        hash = 83 * hash + (argSpecValues != null ? argSpecValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ArgSpec: ");
        buf.append("argName=").append(argName);
        buf.append(", argSpecValues=").append(argSpecValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (argName == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argName' cannot be null!");
        }
        if (argSpecValues == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'argSpecValues' cannot be null!");
        }
        encoder.encodeIdentifier(argName);
        encoder.encodeElement(argSpecValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        argName = decoder.decodeIdentifier();
        argSpecValues = (org.ccsds.moims.mo.mal.structures.HeterogeneousList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.HeterogeneousList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
