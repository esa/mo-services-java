package org.ccsds.moims.mo.mc.parameter.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The ParameterCreationRequest contains all the fields required when creating
 * a new parameter in a provider.
 */
public final class ParameterCreationRequest implements Composite {

    private static final long serialVersionUID = 1125908513554437L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125908513554437L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The name of the parameter. Must not be empty or the wildcard value.
     */
    private Identifier name;

    /**
     * The parameter definition details.
     */
    private ParameterDefinitionDetails paramDefDetails;

    /**
     * Default constructor for ParameterCreationRequest.
     * 
     */
    public ParameterCreationRequest() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the parameter. Must not be empty or the wildcard value.
     * @param paramDefDetails The parameter definition details.
     */
    public ParameterCreationRequest(Identifier name,
            ParameterDefinitionDetails paramDefDetails) {
        this.name = name;
        this.paramDefDetails = paramDefDetails;
    }

    @Override
    public Element createElement() {
        return new ParameterCreationRequest();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the field paramDefDetails.
     * 
     * @return The field paramDefDetails
     */
    public ParameterDefinitionDetails getParamDefDetails() {
        return paramDefDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterCreationRequest) {
            ParameterCreationRequest other = (ParameterCreationRequest) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (paramDefDetails == null) {
                if (other.paramDefDetails != null) {
                    return false;
                }
            } else {
                if (! paramDefDetails.equals(other.paramDefDetails)) {
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
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (paramDefDetails != null ? paramDefDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterCreationRequest: ");
        buf.append("name=").append(name);
        buf.append(", paramDefDetails=").append(paramDefDetails);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        if (paramDefDetails == null) {
            throw new MALException("The field 'paramDefDetails' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(paramDefDetails);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        paramDefDetails = (ParameterDefinitionDetails) decoder.decodeElement(new ParameterDefinitionDetails());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
