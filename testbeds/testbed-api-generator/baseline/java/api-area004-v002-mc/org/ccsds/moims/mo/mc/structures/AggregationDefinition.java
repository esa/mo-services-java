package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;

/**
 * The AggregationDefinition structure shall be used to hold definition details
 * of an aggregation.
 */
public final class AggregationDefinition extends MOObject {

    private static final long serialVersionUID = 1125899940397116L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397116L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The description field.
     */
    private String description;

    /**
     * The category field.
     */
    private Identifier category;

    /**
     * The parameters field.
     */
    private ObjectRefList parameters;

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
    public AggregationDefinition(ObjectIdentity objectIdentity,
            String description,
            Identifier category,
            ObjectRefList parameters) {
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
    public AggregationDefinition(ObjectIdentity objectIdentity,
            String description,
            ObjectRefList parameters) {
        super(objectIdentity);
        this.description = description;
        this.category = null;
        this.parameters = parameters;
    }

    @Override
    public Element createElement() {
        return new AggregationDefinition();
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
    public Identifier getCategory() {
        return category;
    }

    /**
     * Returns the field parameters.
     * 
     * @return The field parameters
     */
    public ObjectRefList getParameters() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (parameters == null) {
            throw new MALException("The field 'parameters' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeNullableIdentifier(category);
        encoder.encodeElement(parameters);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        category = decoder.decodeNullableIdentifier();
        parameters = (ObjectRefList) decoder.decodeElement(new ObjectRefList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
