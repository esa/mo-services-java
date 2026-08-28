package org.ccsds.moims.mo.mc.action.structures;

/**
 * The ActionDefinitionDetails structure holds the definition information
 * of an action.
 */
public final class ActionDefinitionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125904218587137L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125904218587137L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The description of the action.
     */
    private String description;

    /**
     * Category of the action. Value taken from ActionCategory enumeration, although
     * the use of a UOctet allows deployment specific extension. Extensions must
     * use values greater than 127.
     */
    private org.ccsds.moims.mo.mal.structures.UOctet category;

    /**
     * Total number of steps that will be reported if PROGRESS reporting is selected
     * in the sent Action. 0 if PROGRESS reporting is not used.
     */
    private org.ccsds.moims.mo.mal.structures.UShort progressStepCount;

    /**
     * The list of argument definitions. If no arguments are defined, then the
     * complete list is replaced with a NULL.
     */
    private org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList arguments;

    /**
     * Default constructor for ActionDefinitionDetails.
     * 
     */
    public ActionDefinitionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description The description of the action.
     * @param category Category of the action. Value taken from ActionCategory enumeration, although the use of a UOctet allows deployment specific extension. Extensions must use values greater than 127.
     * @param progressStepCount Total number of steps that will be reported if PROGRESS reporting is selected in the sent Action. 0 if PROGRESS reporting is not used.
     * @param arguments The list of argument definitions. If no arguments are defined, then the complete list is replaced with a NULL.
     */
    public ActionDefinitionDetails(String description,
            org.ccsds.moims.mo.mal.structures.UOctet category,
            org.ccsds.moims.mo.mal.structures.UShort progressStepCount,
            org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList arguments) {
        this.description = description;
        this.category = category;
        this.progressStepCount = progressStepCount;
        this.arguments = arguments;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param description The description of the action.
     * @param category Category of the action. Value taken from ActionCategory enumeration, although the use of a UOctet allows deployment specific extension. Extensions must use values greater than 127.
     * @param progressStepCount Total number of steps that will be reported if PROGRESS reporting is selected in the sent Action. 0 if PROGRESS reporting is not used.
     */
    public ActionDefinitionDetails(String description,
            org.ccsds.moims.mo.mal.structures.UOctet category,
            org.ccsds.moims.mo.mal.structures.UShort progressStepCount) {
        this.description = description;
        this.category = category;
        this.progressStepCount = progressStepCount;
        this.arguments = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.action.structures.ActionDefinitionDetails();
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
    public org.ccsds.moims.mo.mal.structures.UOctet getCategory() {
        return category;
    }

    /**
     * Returns the field progressStepCount.
     * 
     * @return The field progressStepCount
     */
    public org.ccsds.moims.mo.mal.structures.UShort getProgressStepCount() {
        return progressStepCount;
    }

    /**
     * Returns the field arguments.
     * 
     * @return The field arguments
     */
    public org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionDefinitionDetails) {
            ActionDefinitionDetails other = (ActionDefinitionDetails) obj;
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
        int hash = 7;
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (category != null ? category.hashCode() : 0);
        hash = 83 * hash + (progressStepCount != null ? progressStepCount.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionDefinitionDetails: ");
        buf.append("description=").append(description);
        buf.append(", category=").append(category);
        buf.append(", progressStepCount=").append(progressStepCount);
        buf.append(", arguments=").append(arguments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (category == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'category' cannot be null!");
        }
        if (progressStepCount == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'progressStepCount' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeUOctet(category);
        encoder.encodeUShort(progressStepCount);
        encoder.encodeNullableElement(arguments);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        description = decoder.decodeString();
        category = decoder.decodeUOctet();
        progressStepCount = decoder.decodeUShort();
        arguments = (org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
