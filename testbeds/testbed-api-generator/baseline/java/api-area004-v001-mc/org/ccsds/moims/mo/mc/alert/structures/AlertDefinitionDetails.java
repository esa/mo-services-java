package org.ccsds.moims.mo.mc.alert.structures;

/**
 * The AlertDefinitionDetails provides the definition of an alert including
 * any argument definitions.
 */
public final class AlertDefinitionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125912808521729L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125912808521729L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The description of the alert.
     */
    private String description;

    /**
     * Severity of the alert.
     */
    private org.ccsds.moims.mo.mc.structures.Severity severity;

    /**
     * Controls whether instances of this alert are to be generated.
     */
    private Boolean generationEnabled;

    /**
     * The list of argument definitions.
     */
    private org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList arguments;

    /**
     * Default constructor for AlertDefinitionDetails.
     * 
     */
    public AlertDefinitionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description The description of the alert.
     * @param severity Severity of the alert.
     * @param generationEnabled Controls whether instances of this alert are to be generated.
     * @param arguments The list of argument definitions.
     */
    public AlertDefinitionDetails(String description,
            org.ccsds.moims.mo.mc.structures.Severity severity,
            Boolean generationEnabled,
            org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList arguments) {
        this.description = description;
        this.severity = severity;
        this.generationEnabled = generationEnabled;
        this.arguments = arguments;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails();
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
     * Returns the field severity.
     * 
     * @return The field severity
     */
    public org.ccsds.moims.mo.mc.structures.Severity getSeverity() {
        return severity;
    }

    /**
     * Returns the field generationEnabled.
     * 
     * @return The field generationEnabled
     */
    public Boolean getGenerationEnabled() {
        return generationEnabled;
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
        if (obj instanceof AlertDefinitionDetails) {
            AlertDefinitionDetails other = (AlertDefinitionDetails) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (severity == null) {
                if (other.severity != null) {
                    return false;
                }
            } else {
                if (! severity.equals(other.severity)) {
                    return false;
                }
            }
            if (generationEnabled == null) {
                if (other.generationEnabled != null) {
                    return false;
                }
            } else {
                if (! generationEnabled.equals(other.generationEnabled)) {
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
        hash = 83 * hash + (severity != null ? severity.hashCode() : 0);
        hash = 83 * hash + (generationEnabled != null ? generationEnabled.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AlertDefinitionDetails: ");
        buf.append("description=").append(description);
        buf.append(", severity=").append(severity);
        buf.append(", generationEnabled=").append(generationEnabled);
        buf.append(", arguments=").append(arguments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (severity == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'severity' cannot be null!");
        }
        if (generationEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'generationEnabled' cannot be null!");
        }
        if (arguments == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'arguments' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(severity);
        encoder.encodeBoolean(generationEnabled);
        encoder.encodeElement(arguments);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        description = decoder.decodeString();
        severity = (org.ccsds.moims.mo.mc.structures.Severity) decoder.decodeElement(org.ccsds.moims.mo.mc.structures.Severity.INFORMATIONAL);
        generationEnabled = decoder.decodeBoolean();
        arguments = (org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList) decoder.decodeElement(new org.ccsds.moims.mo.mc.structures.ArgumentDefinitionDetailsList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
