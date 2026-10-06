package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The ArgumentDefinition structure shall be used to hold the details of an
 * argument definition.
 */
public final class ArgumentDefinition implements Composite {

    private static final long serialVersionUID = 1125899940397057L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397057L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The argId field.
     */
    private Identifier argId;

    /**
     * The description field.
     */
    private String description;

    /**
     * The type field.
     */
    private AttributeType type;

    /**
     * The unit field.
     */
    private String unit;

    /**
     * Default constructor for ArgumentDefinition.
     * 
     */
    public ArgumentDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param argId The argId field.
     * @param description The description field.
     * @param type The type field.
     * @param unit The unit field.
     */
    public ArgumentDefinition(Identifier argId,
            String description,
            AttributeType type,
            String unit) {
        this.argId = argId;
        this.description = description;
        this.type = type;
        this.unit = unit;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param argId The argId field.
     * @param type The type field.
     */
    public ArgumentDefinition(Identifier argId,
            AttributeType type) {
        this.argId = argId;
        this.description = null;
        this.type = type;
        this.unit = null;
    }

    @Override
    public Element createElement() {
        return new ArgumentDefinition();
    }

    /**
     * Returns the field argId.
     * 
     * @return The field argId
     */
    public Identifier getArgId() {
        return argId;
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
     * Returns the field type.
     * 
     * @return The field type
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * Returns the field unit.
     * 
     * @return The field unit
     */
    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArgumentDefinition) {
            ArgumentDefinition other = (ArgumentDefinition) obj;
            if (argId == null) {
                if (other.argId != null) {
                    return false;
                }
            } else {
                if (! argId.equals(other.argId)) {
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
            if (type == null) {
                if (other.type != null) {
                    return false;
                }
            } else {
                if (! type.equals(other.type)) {
                    return false;
                }
            }
            if (unit == null) {
                if (other.unit != null) {
                    return false;
                }
            } else {
                if (! unit.equals(other.unit)) {
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
        hash = 83 * hash + (argId != null ? argId.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (type != null ? type.hashCode() : 0);
        hash = 83 * hash + (unit != null ? unit.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ArgumentDefinition: ");
        buf.append("argId=").append(argId);
        buf.append(", description=").append(description);
        buf.append(", type=").append(type);
        buf.append(", unit=").append(unit);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (argId == null) {
            throw new MALException("The field 'argId' cannot be null!");
        }
        if (type == null) {
            throw new MALException("The field 'type' cannot be null!");
        }
        encoder.encodeIdentifier(argId);
        encoder.encodeNullableString(description);
        encoder.encodeElement(type);
        encoder.encodeNullableString(unit);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        argId = decoder.decodeIdentifier();
        description = decoder.decodeNullableString();
        type = (AttributeType) decoder.decodeElement(AttributeType.BLOB);
        unit = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
