package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;

/**
 * The AlertDefinition structure shall be used to provide the definition of
 * an alert including any argument definitions.
 */
public final class AlertDefinition extends MOObject {

    private static final long serialVersionUID = 1125899940397086L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397086L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The description field.
     */
    private String description;

    /**
     * The severity field.
     */
    private Severity severity;

    /**
     * The arguments field.
     */
    private ArgumentDefinitionList arguments;

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
    public AlertDefinition(ObjectIdentity objectIdentity,
            String description,
            Severity severity,
            ArgumentDefinitionList arguments) {
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
    public AlertDefinition(ObjectIdentity objectIdentity,
            String description,
            Severity severity) {
        super(objectIdentity);
        this.description = description;
        this.severity = severity;
        this.arguments = null;
    }

    @Override
    public Element createElement() {
        return new AlertDefinition();
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
    public Severity getSeverity() {
        return severity;
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (severity == null) {
            throw new MALException("The field 'severity' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeElement(severity);
        encoder.encodeNullableElement(arguments);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        description = decoder.decodeString();
        severity = (Severity) decoder.decodeElement(Severity.INFORMATIONAL);
        arguments = (ArgumentDefinitionList) decoder.decodeNullableElement(new ArgumentDefinitionList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
