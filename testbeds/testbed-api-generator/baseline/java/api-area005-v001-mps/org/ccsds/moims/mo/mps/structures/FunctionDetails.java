package org.ccsds.moims.mo.mps.structures;

/**
 * E8: Contains the information required to invoke a defined function, including
 * the specification of argument values.
 */
public final class FunctionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331198L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331198L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * ID of a specific FunctionDefinition.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier functionID;

    /**
     * Set of argument specifications for each argument definition contained in
     * the referenced function definition.  These supply a value for each argument,
     * or an expression to enable the value to be derived.
     */
    private org.ccsds.moims.mo.mps.structures.ArgSpecList argSpecs;

    /**
     * Default constructor for FunctionDetails.
     * 
     */
    public FunctionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param functionID ID of a specific FunctionDefinition.
     * @param argSpecs Set of argument specifications for each argument definition contained in the referenced function definition.  These supply a value for each argument, or an expression to enable the value to be derived.
     */
    public FunctionDetails(org.ccsds.moims.mo.mal.structures.Identifier functionID,
            org.ccsds.moims.mo.mps.structures.ArgSpecList argSpecs) {
        this.functionID = functionID;
        this.argSpecs = argSpecs;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param functionID ID of a specific FunctionDefinition.
     */
    public FunctionDetails(org.ccsds.moims.mo.mal.structures.Identifier functionID) {
        this.functionID = functionID;
        this.argSpecs = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.FunctionDetails();
    }

    /**
     * Returns the field functionID.
     * 
     * @return The field functionID
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getFunctionID() {
        return functionID;
    }

    /**
     * Returns the field argSpecs.
     * 
     * @return The field argSpecs
     */
    public org.ccsds.moims.mo.mps.structures.ArgSpecList getArgSpecs() {
        return argSpecs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FunctionDetails) {
            FunctionDetails other = (FunctionDetails) obj;
            if (functionID == null) {
                if (other.functionID != null) {
                    return false;
                }
            } else {
                if (! functionID.equals(other.functionID)) {
                    return false;
                }
            }
            if (argSpecs == null) {
                if (other.argSpecs != null) {
                    return false;
                }
            } else {
                if (! argSpecs.equals(other.argSpecs)) {
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
        hash = 83 * hash + (functionID != null ? functionID.hashCode() : 0);
        hash = 83 * hash + (argSpecs != null ? argSpecs.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(FunctionDetails: ");
        buf.append("functionID=").append(functionID);
        buf.append(", argSpecs=").append(argSpecs);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (functionID == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'functionID' cannot be null!");
        }
        encoder.encodeIdentifier(functionID);
        encoder.encodeNullableElement(argSpecs);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        functionID = decoder.decodeIdentifier();
        argSpecs = (org.ccsds.moims.mo.mps.structures.ArgSpecList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ArgSpecList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
