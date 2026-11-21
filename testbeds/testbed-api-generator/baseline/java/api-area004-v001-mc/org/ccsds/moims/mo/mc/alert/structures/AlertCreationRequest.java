package org.ccsds.moims.mo.mc.alert.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The AlertCreationRequest contains all the fields required when creating
 * a new alert in a provider.
 */
public final class AlertCreationRequest implements Composite {

    private static final long serialVersionUID = 1125912808521731L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125912808521731L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Alert name. Must not be empty or wildcard value.
     */
    private Identifier name;

    /**
     * The alert definition details.
     */
    private AlertDefinitionDetails alertDefDetails;

    /**
     * Default constructor for AlertCreationRequest.
     * 
     */
    public AlertCreationRequest() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name Alert name. Must not be empty or wildcard value.
     * @param alertDefDetails The alert definition details.
     */
    public AlertCreationRequest(Identifier name,
            AlertDefinitionDetails alertDefDetails) {
        this.name = name;
        this.alertDefDetails = alertDefDetails;
    }

    @Override
    public Element createElement() {
        return new AlertCreationRequest();
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
     * Returns the field alertDefDetails.
     * 
     * @return The field alertDefDetails
     */
    public AlertDefinitionDetails getAlertDefDetails() {
        return alertDefDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlertCreationRequest) {
            AlertCreationRequest other = (AlertCreationRequest) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (alertDefDetails == null) {
                if (other.alertDefDetails != null) {
                    return false;
                }
            } else {
                if (! alertDefDetails.equals(other.alertDefDetails)) {
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
        hash = 83 * hash + (alertDefDetails != null ? alertDefDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AlertCreationRequest: ");
        buf.append("name=").append(name);
        buf.append(", alertDefDetails=").append(alertDefDetails);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        if (alertDefDetails == null) {
            throw new MALException("The field 'alertDefDetails' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(alertDefDetails);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        alertDefDetails = (AlertDefinitionDetails) decoder.decodeElement(new AlertDefinitionDetails());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
