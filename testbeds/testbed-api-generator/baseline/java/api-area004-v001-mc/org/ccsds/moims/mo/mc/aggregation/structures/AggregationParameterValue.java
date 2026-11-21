package org.ccsds.moims.mo.mc.aggregation.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mc.parameter.structures.ParameterValue;

/**
 * The structure holds a single parameter value with its definition instance
 * identifier.
 */
public final class AggregationParameterValue implements Composite {

    private static final long serialVersionUID = 1125925693423621L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423621L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The parameter value.
     */
    private ParameterValue value;

    /**
     * The object instance identifier of the ParameterDefinition. NULL if sendDefinitions
     * in the AggregationDefinitionDetails is FALSE.
     */
    private Long paramDefInstId;

    /**
     * Default constructor for AggregationParameterValue.
     * 
     */
    public AggregationParameterValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param value The parameter value.
     * @param paramDefInstId The object instance identifier of the ParameterDefinition. NULL if sendDefinitions in the AggregationDefinitionDetails is FALSE.
     */
    public AggregationParameterValue(ParameterValue value,
            Long paramDefInstId) {
        this.value = value;
        this.paramDefInstId = paramDefInstId;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param value The parameter value.
     */
    public AggregationParameterValue(ParameterValue value) {
        this.value = value;
        this.paramDefInstId = null;
    }

    @Override
    public Element createElement() {
        return new AggregationParameterValue();
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public ParameterValue getValue() {
        return value;
    }

    /**
     * Returns the field paramDefInstId.
     * 
     * @return The field paramDefInstId
     */
    public Long getParamDefInstId() {
        return paramDefInstId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationParameterValue) {
            AggregationParameterValue other = (AggregationParameterValue) obj;
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
                    return false;
                }
            }
            if (paramDefInstId == null) {
                if (other.paramDefInstId != null) {
                    return false;
                }
            } else {
                if (! paramDefInstId.equals(other.paramDefInstId)) {
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
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        hash = 83 * hash + (paramDefInstId != null ? paramDefInstId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationParameterValue: ");
        buf.append("value=").append(value);
        buf.append(", paramDefInstId=").append(paramDefInstId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeElement(value);
        encoder.encodeNullableLong(paramDefInstId);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        value = (ParameterValue) decoder.decodeElement(new ParameterValue());
        paramDefInstId = decoder.decodeNullableLong();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
