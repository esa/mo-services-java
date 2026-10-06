package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E8: Contains the information required to invoke a defined function, including
 * the specification of argument values.
 */
public final class FunctionDetails implements Composite {

    private static final long serialVersionUID = 1407374900331198L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900331198L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * ID of a specific FunctionDefinition.
     */
    private Identifier functionID;

    /**
     * Set of argument specifications for each argument definition contained in
     * the referenced function definition.  These supply a value for each argument,
     * or an expression to enable the value to be derived.
     */
    private ArgSpecList argSpecs;

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
    public FunctionDetails(Identifier functionID,
            ArgSpecList argSpecs) {
        this.functionID = functionID;
        this.argSpecs = argSpecs;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param functionID ID of a specific FunctionDefinition.
     */
    public FunctionDetails(Identifier functionID) {
        this.functionID = functionID;
        this.argSpecs = null;
    }

    @Override
    public Element createElement() {
        return new FunctionDetails();
    }

    /**
     * Returns the field functionID.
     * 
     * @return The field functionID
     */
    public Identifier getFunctionID() {
        return functionID;
    }

    /**
     * Returns the field argSpecs.
     * 
     * @return The field argSpecs
     */
    public ArgSpecList getArgSpecs() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (functionID == null) {
            throw new MALException("The field 'functionID' cannot be null!");
        }
        encoder.encodeIdentifier(functionID);
        encoder.encodeNullableElement(argSpecs);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        functionID = decoder.decodeIdentifier();
        argSpecs = (ArgSpecList) decoder.decodeNullableElement(new ArgSpecList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
