package org.ccsds.moims.mo.mc.alert.structures;

/**
 * The AlertEventDetails structure holds the details of an instance of an
 * alert.
 */
public final class AlertEventDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125912808521730L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125912808521730L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * List containing the values of the arguments. The ordering of the list matches
     * that of the definition. If a value for a particular entry is not being
     * supplied, then its position is filled with a NULL value. If no arguments
     * are defined, then the complete list is replaced with a NULL.
     */
    private org.ccsds.moims.mo.mc.structures.AttributeValueList argumentValues;

    /**
     * Optional list of argument definition identifiers. Allows the consumer to
     * verify that the correct arguments are being supplied. The ordering of the
     * list matches that of the argument list of the alert definition.
     */
    private org.ccsds.moims.mo.mal.structures.IdentifierList argumentIds;

    /**
     * Default constructor for AlertEventDetails.
     * 
     */
    public AlertEventDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param argumentValues List containing the values of the arguments. The ordering of the list matches that of the definition. If a value for a particular entry is not being supplied, then its position is filled with a NULL value. If no arguments are defined, then the complete list is replaced with a NULL.
     * @param argumentIds Optional list of argument definition identifiers. Allows the consumer to verify that the correct arguments are being supplied. The ordering of the list matches that of the argument list of the alert definition.
     */
    public AlertEventDetails(org.ccsds.moims.mo.mc.structures.AttributeValueList argumentValues,
            org.ccsds.moims.mo.mal.structures.IdentifierList argumentIds) {
        this.argumentValues = argumentValues;
        this.argumentIds = argumentIds;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.alert.structures.AlertEventDetails();
    }

    /**
     * Returns the field argumentValues.
     * 
     * @return The field argumentValues
     */
    public org.ccsds.moims.mo.mc.structures.AttributeValueList getArgumentValues() {
        return argumentValues;
    }

    /**
     * Returns the field argumentIds.
     * 
     * @return The field argumentIds
     */
    public org.ccsds.moims.mo.mal.structures.IdentifierList getArgumentIds() {
        return argumentIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlertEventDetails) {
            AlertEventDetails other = (AlertEventDetails) obj;
            if (argumentValues == null) {
                if (other.argumentValues != null) {
                    return false;
                }
            } else {
                if (! argumentValues.equals(other.argumentValues)) {
                    return false;
                }
            }
            if (argumentIds == null) {
                if (other.argumentIds != null) {
                    return false;
                }
            } else {
                if (! argumentIds.equals(other.argumentIds)) {
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
        hash = 83 * hash + (argumentValues != null ? argumentValues.hashCode() : 0);
        hash = 83 * hash + (argumentIds != null ? argumentIds.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AlertEventDetails: ");
        buf.append("argumentValues=").append(argumentValues);
        buf.append(", argumentIds=").append(argumentIds);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableElement(argumentValues);
        encoder.encodeNullableElement(argumentIds);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        argumentValues = (org.ccsds.moims.mo.mc.structures.AttributeValueList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.structures.AttributeValueList());
        argumentIds = (org.ccsds.moims.mo.mal.structures.IdentifierList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IdentifierList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
