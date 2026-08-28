package org.ccsds.moims.mo.mc.structures;

/**
 * The AlertDefinition structure shall be used to provide the definition of
 * an alert including any argument definitions.
 */
public final class AlertDefinition extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 1125899940397086L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397086L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The description field.
     */
    private String description;

    /**
     * The severity field.
     */
    private org.ccsds.moims.mo.mc.structures.Severity severity;

    /**
     * The arguments field.
     */
    private org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList arguments;

    /**
     * Default constructor for AlertDefinition.
     * 
     */
    public AlertDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param severity The severity field.
     * @param arguments The arguments field.
     */
    public AlertDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mc.structures.Severity severity,
            org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList arguments) {
        super(objectIdentity);
        this.description = description;
        this.severity = severity;
        this.arguments = arguments;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param description The description field.
     * @param severity The severity field.
     */
    public AlertDefinition(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String description,
            org.ccsds.moims.mo.mc.structures.Severity severity) {
        super(objectIdentity);
        this.description = description;
        this.severity = severity;
        this.arguments = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.AlertDefinition();
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
     * Returns the field arguments.
     * 
     * @return The field arguments
     */
    public org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlertDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            AlertDefinition other = (AlertDefinition) obj;
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
        hash = 83 * hash + (severity != null ? severity.hashCode() : 0);
        hash = 83 * hash + (arguments != null ? arguments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AlertDefinition: ");
        buf.append(super.toString());
        buf.append(", description=").append(description);
        buf.append(", severity=").append(severity);
        buf.append(", arguments=").append(arguments);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (severity == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'severity' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(severity);
        encoder.encodeNullableElement(arguments);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        severity = (org.ccsds.moims.mo.mc.structures.Severity) decoder.decodeElement(org.ccsds.moims.mo.mc.structures.Severity.INFORMATIONAL);
        arguments = (org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.structures.ArgumentDefinitionList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
