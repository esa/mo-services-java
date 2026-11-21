package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The ActionDefinition structure holds the definition information of an action.
 */
public final class ActionDefinition extends MOObject {

    private static final long serialVersionUID = 1125899940397067L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397067L;
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
    private ActionCategory category;

    /**
     * The progressStepCount field.
     */
    private UShort progressStepCount;

    /**
     * The arguments field.
     */
    private ArgumentDefinitionList arguments;

    /**
     * Default constructor for ActionDefinition.
     * 
     */
    public ActionDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param category The category field.
     * @param progressStepCount The progressStepCount field.
     * @param arguments The arguments field.
     */
    public ActionDefinition(ObjectIdentity objectIdentity,
            String description,
            ActionCategory category,
            UShort progressStepCount,
            ArgumentDefinitionList arguments) {
        super(objectIdentity);
        this.description = description;
        this.category = category;
        this.progressStepCount = progressStepCount;
        this.arguments = arguments;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param category The category field.
     * @param progressStepCount The progressStepCount field.
     */
    public ActionDefinition(ObjectIdentity objectIdentity,
            String description,
            ActionCategory category,
            UShort progressStepCount) {
        super(objectIdentity);
        this.description = description;
        this.category = category;
        this.progressStepCount = progressStepCount;
        this.arguments = null;
    }

    @Override
    public Element createElement() {
        return new ActionDefinition();
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
    public ActionCategory getCategory() {
        return category;
    }

    /**
     * Returns the field progressStepCount.
     * 
     * @return The field progressStepCount
     */
    public UShort getProgressStepCount() {
        return progressStepCount;
    }

    /**
     * Returns the field arguments.
     * 
     * @return The field arguments
     */
    public ArgumentDefinitionList getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            ActionDefinition other = (ActionDefinition) obj;
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
            if (progressStepCount == null) {
                if (other.progressStepCount != null) {
                    return false;
                }
            } else {
                if (! progressStepCount.equals(other.progressStepCount)) {
                    return false;
                }
            }
            if (arguments == null) {
                if (other.arguments != null) {
                    return false;
                }
            } else {
                if (! arguments.equals(other.arguments)) {
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
        hash = 83 * hash + (progressStepCount != null ? progressStepCount.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionDefinition: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", category=").append(category);
        buf.append(", progressStepCount=").append(progressStepCount);
        buf.append(", arguments=").append(arguments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (category == null) {
            throw new MALException("The field 'category' cannot be null!");
        }
        if (progressStepCount == null) {
            throw new MALException("The field 'progressStepCount' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(category);
        encoder.encodeUShort(progressStepCount);
        encoder.encodeNullableElement(arguments);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        category = (ActionCategory) decoder.decodeElement(ActionCategory.DEFAULT);
        progressStepCount = decoder.decodeUShort();
        arguments = (ArgumentDefinitionList) decoder.decodeNullableElement(new ArgumentDefinitionList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
