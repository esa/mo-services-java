package org.ccsds.moims.mo.mps.structures;

/**
 * E8: FunctionDefinitionDetails is a data structure that contains static
 * configuration data relating to custom functions: built-in Boolean functions
 * of an MPS system, each of which has a specified Identifier and optional
 * set of argument definitions.  This may change over time, each comprising
 * a separate version of the definition.  FunctionDefinitions form part of
 * the planning configuration data.
 */
public final class FunctionDefinitionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900331197L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331197L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * ID of the custom function.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier functionID;

    /**
     * Version of the FunctionDefinition.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger version;

    /**
     * Description of the custom function.
     */
    private String description;

    /**
     * List of argument definitions.
     */
    private org.ccsds.moims.mo.mps.structures.ArgDefList argDefs;

    /**
     * Default constructor for FunctionDefinitionDetails.
     * 
     */
    public FunctionDefinitionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param functionID ID of the custom function.
     * @param version Version of the FunctionDefinition.
     * @param description Description of the custom function.
     * @param argDefs List of argument definitions.
     */
    public FunctionDefinitionDetails(org.ccsds.moims.mo.mal.structures.Identifier functionID,
            org.ccsds.moims.mo.mal.structures.UInteger version,
            String description,
            org.ccsds.moims.mo.mps.structures.ArgDefList argDefs) {
        this.functionID = functionID;
        this.version = version;
        this.description = description;
        this.argDefs = argDefs;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param functionID ID of the custom function.
     * @param version Version of the FunctionDefinition.
     * @param description Description of the custom function.
     */
    public FunctionDefinitionDetails(org.ccsds.moims.mo.mal.structures.Identifier functionID,
            org.ccsds.moims.mo.mal.structures.UInteger version,
            String description) {
        this.functionID = functionID;
        this.version = version;
        this.description = description;
        this.argDefs = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.FunctionDefinitionDetails();
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
     * Returns the field version.
     * 
     * @return The field version
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getVersion() {
        return version;
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field argDefs.
     * 
     * @return The field argDefs
     */
    public org.ccsds.moims.mo.mps.structures.ArgDefList getArgDefs() {
        return argDefs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FunctionDefinitionDetails) {
            FunctionDefinitionDetails other = (FunctionDefinitionDetails) obj;
            if (functionID == null) {
                if (other.functionID != null) {
                    return false;
                }
            } else {
                if (! functionID.equals(other.functionID)) {
                    return false;
                }
            }
            if (version == null) {
                if (other.version != null) {
                    return false;
                }
            } else {
                if (! version.equals(other.version)) {
                    return false;
                }
            }
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (argDefs == null) {
                if (other.argDefs != null) {
                    return false;
                }
            } else {
                if (! argDefs.equals(other.argDefs)) {
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
        hash = 83 * hash + (version != null ? version.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (argDefs != null ? argDefs.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(FunctionDefinitionDetails: ");
        buf.append("functionID=").append(functionID);
        buf.append(", version=").append(version);
        buf.append(", description=").append(description);
        buf.append(", argDefs=").append(argDefs);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (functionID == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'functionID' cannot be null!");
        }
        if (version == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'version' cannot be null!");
        }
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        encoder.encodeIdentifier(functionID);
        encoder.encodeUInteger(version);
        encoder.encodeString(description);
        encoder.encodeNullableElement(argDefs);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        functionID = decoder.decodeIdentifier();
        version = decoder.decodeUInteger();
        description = decoder.decodeString();
        argDefs = (org.ccsds.moims.mo.mps.structures.ArgDefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mps.structures.ArgDefList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
