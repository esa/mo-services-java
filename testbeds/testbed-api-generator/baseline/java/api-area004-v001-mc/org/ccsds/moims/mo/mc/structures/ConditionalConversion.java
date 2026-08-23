package org.ccsds.moims.mo.mc.structures;

/**
 * The ConditionalConversion structure holds a condition expression to be
 * evaluated to determine if a specific Conversion should be used. In the
 * case that no test is required, i.e., the conversion should always be used,
 * then the condition field should be set to NULL.
 */
public final class ConditionalConversion implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899923619843L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899923619843L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The expression indicates which entities are applicable for this check.
     * If NULL, then the condition shall evaluate to TRUE.
     */
    private org.ccsds.moims.mo.mc.structures.ParameterExpression condition;

    /**
     * The object instance identifier of the ConversionIdentity object to be used
     * if the condition evaluates to TRUE or is NULL.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey conversionId;

    /**
     * Default constructor for ConditionalConversion.
     * 
     */
    public ConditionalConversion() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param condition The expression indicates which entities are applicable for this check. If NULL, then the condition shall evaluate to TRUE.
     * @param conversionId The object instance identifier of the ConversionIdentity object to be used if the condition evaluates to TRUE or is NULL.
     */
    public ConditionalConversion(org.ccsds.moims.mo.mc.structures.ParameterExpression condition,
            org.ccsds.moims.mo.com.structures.ObjectKey conversionId) {
        this.condition = condition;
        this.conversionId = conversionId;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param conversionId The object instance identifier of the ConversionIdentity object to be used if the condition evaluates to TRUE or is NULL.
     */
    public ConditionalConversion(org.ccsds.moims.mo.com.structures.ObjectKey conversionId) {
        this.condition = null;
        this.conversionId = conversionId;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.ConditionalConversion();
    }

    /**
     * Returns the field condition.
     * 
     * @return The field condition
     */
    public org.ccsds.moims.mo.mc.structures.ParameterExpression getCondition() {
        return condition;
    }

    /**
     * Returns the field conversionId.
     * 
     * @return The field conversionId
     */
    public org.ccsds.moims.mo.com.structures.ObjectKey getConversionId() {
        return conversionId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConditionalConversion) {
            ConditionalConversion other = (ConditionalConversion) obj;
            if (condition == null) {
                if (other.condition != null) {
                    return false;
                }
            } else {
                if (! condition.equals(other.condition)) {
                    return false;
                }
            }
            if (conversionId == null) {
                if (other.conversionId != null) {
                    return false;
                }
            } else {
                if (! conversionId.equals(other.conversionId)) {
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
        hash = 83 * hash + (condition != null ? condition.hashCode() : 0);
        hash = 83 * hash + (conversionId != null ? conversionId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ConditionalConversion: ");
        buf.append("condition=").append(condition);
        buf.append(", conversionId=").append(conversionId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (conversionId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'conversionId' cannot be null!");
        }
        encoder.encodeNullableElement(condition);
        encoder.encodeElement(conversionId);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        condition = (org.ccsds.moims.mo.mc.structures.ParameterExpression) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.structures.ParameterExpression());
        conversionId = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
