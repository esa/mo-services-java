package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ConditionalConversion structure holds a condition expression to be
 * evaluated to determine if a specific Conversion should be used. In the
 * case that no test is required, i.e., the conversion should always be used,
 * then the condition field should be set to NULL.
 */
public final class ConditionalConversion implements Composite {

    private static final long serialVersionUID = 1125899923619843L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899923619843L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The expression indicates which entities are applicable for this check.
     * If NULL, then the condition shall evaluate to TRUE.
     */
    private ParameterExpression condition;

    /**
     * The object instance identifier of the ConversionIdentity object to be used
     * if the condition evaluates to TRUE or is NULL.
     */
    private ObjectKey conversionId;

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
    public ConditionalConversion(ParameterExpression condition,
            ObjectKey conversionId) {
        this.condition = condition;
        this.conversionId = conversionId;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param conversionId The object instance identifier of the ConversionIdentity object to be used if the condition evaluates to TRUE or is NULL.
     */
    public ConditionalConversion(ObjectKey conversionId) {
        this.condition = null;
        this.conversionId = conversionId;
    }

    @Override
    public Element createElement() {
        return new ConditionalConversion();
    }

    /**
     * Returns the field condition.
     * 
     * @return The field condition
     */
    public ParameterExpression getCondition() {
        return condition;
    }

    /**
     * Returns the field conversionId.
     * 
     * @return The field conversionId
     */
    public ObjectKey getConversionId() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (conversionId == null) {
            throw new MALException("The field 'conversionId' cannot be null!");
        }
        encoder.encodeNullableElement(condition);
        encoder.encodeElement(conversionId);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        condition = (ParameterExpression) decoder.decodeNullableElement(new ParameterExpression());
        conversionId = (ObjectKey) decoder.decodeElement(new ObjectKey());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
