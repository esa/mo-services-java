package org.ccsds.moims.mo.mc.structures;

/**
 * The AggregationDefinition structure shall be used to hold definition details
 * of an aggregation.
 */
public final class AggregationDefinition extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 1125899940397116L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397116L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The description field.
     */
    private String description;

    /**
     * The category field.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier category;

    /**
     * The parameters field.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList parameters;

    /**
     * Default constructor for AggregationDefinition.
     * 
     */
    public AggregationDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param category The category field.
     * @param parameters The parameters field.
     */
    public AggregationDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mal.structures.Identifier category,
            org.ccsds.moims.mo.mal.structures.ObjectRefList parameters) {
        super(objectIdentity);
        this.description = description;
        this.category = category;
        this.parameters = parameters;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param parameters The parameters field.
     */
    public AggregationDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mal.structures.ObjectRefList parameters) {
        super(objectIdentity);
        this.description = description;
        this.category = null;
        this.parameters = parameters;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.AggregationDefinition();
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
     * Returns the field category.
     * 
     * @return The field category
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getCategory() {
        return category;
    }

    /**
     * Returns the field parameters.
     * 
     * @return The field parameters
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            AggregationDefinition other = (AggregationDefinition) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (category == null) {
                if (other.category != null) {
                    return false;
                }
            } else {
                if (! category.equals(other.category)) {
                    return false;
                }
            }
            if (parameters == null) {
                if (other.parameters != null) {
                    return false;
                }
            } else {
                if (! parameters.equals(other.parameters)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (category != null ? category.hashCode() : 0);
        hash = 83 * hash + (parameters != null ? parameters.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationDefinition: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", category=").append(category);
        buf.append(", parameters=").append(parameters);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (parameters == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameters' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeNullableIdentifier(category);
        encoder.encodeElement(parameters);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        category = decoder.decodeNullableIdentifier();
        parameters = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
