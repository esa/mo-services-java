package org.ccsds.moims.mo.mc.alert.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mc.structures.AttributeValueList;

/**
 * The AlertEventDetails structure holds the details of an instance of an
 * alert.
 */
public final class AlertEventDetails implements Composite {

    private static final long serialVersionUID = 1125912808521730L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125912808521730L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * List containing the values of the arguments. The ordering of the list matches
     * that of the definition. If a value for a particular entry is not being
     * supplied, then its position is filled with a NULL value. If no arguments
     * are defined, then the complete list is replaced with a NULL.
     */
    private AttributeValueList argumentValues;

    /**
     * Optional list of argument definition identifiers. Allows the consumer to
     * verify that the correct arguments are being supplied. The ordering of the
     * list matches that of the argument list of the alert definition.
     */
    private IdentifierList argumentIds;

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
    public AlertEventDetails(AttributeValueList argumentValues,
            IdentifierList argumentIds) {
        this.argumentValues = argumentValues;
        this.argumentIds = argumentIds;
    }

    @Override
    public Element createElement() {
        return new AlertEventDetails();
    }

    /**
     * Returns the field argumentValues.
     * 
     * @return The field argumentValues
     */
    public AttributeValueList getArgumentValues() {
        return argumentValues;
    }

    /**
     * Returns the field argumentIds.
     * 
     * @return The field argumentIds
     */
    public IdentifierList getArgumentIds() {
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableElement(argumentValues);
        encoder.encodeNullableElement(argumentIds);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        argumentValues = (AttributeValueList) decoder.decodeNullableElement(new AttributeValueList());
        argumentIds = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
