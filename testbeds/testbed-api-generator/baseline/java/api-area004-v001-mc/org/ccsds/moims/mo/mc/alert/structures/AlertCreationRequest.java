package org.ccsds.moims.mo.mc.alert.structures;

/**
 * The AlertCreationRequest contains all the fields required when creating
 * a new alert in a provider.
 */
public final class AlertCreationRequest implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125912808521731L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125912808521731L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Alert name. Must not be empty or wildcard value.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier name;

    /**
     * The alert definition details.
     */
    private org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails alertDefDetails;

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
    public AlertCreationRequest(org.ccsds.moims.mo.mal.structures.Identifier name,
            org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails alertDefDetails) {
        this.name = name;
        this.alertDefDetails = alertDefDetails;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.alert.structures.AlertCreationRequest();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getName() {
        return name;
    }

    /**
     * Returns the field alertDefDetails.
     * 
     * @return The field alertDefDetails
     */
    public org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails getAlertDefDetails() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (name == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'name' cannot be null!");
        }
        if (alertDefDetails == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'alertDefDetails' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(alertDefDetails);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        name = decoder.decodeIdentifier();
        alertDefDetails = (org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails) decoder.decodeElement(new org.ccsds.moims.mo.mc.alert.structures.AlertDefinitionDetails());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
