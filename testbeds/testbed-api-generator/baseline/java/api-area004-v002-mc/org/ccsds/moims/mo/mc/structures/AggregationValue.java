package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * The AggregationValue structure shall be used to hold the values of the
 * aggregation parameters.
 */
public final class AggregationValue implements Composite {

    private static final long serialVersionUID = 1125899940397117L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397117L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The aggregationRef field.
     */
    private ObjectRef<AggregationDefinition> aggregationRef;

    /**
     * The timestamp field.
     */
    private Time timestamp;

    /**
     * The parameterValues field.
     */
    private ParameterValueDataList parameterValues;

    /**
     * Default constructor for AggregationValue.
     * 
     */
    public AggregationValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param aggregationRef The aggregationRef field.
     * @param timestamp The timestamp field.
     * @param parameterValues The parameterValues field.
     */
    public AggregationValue(ObjectRef<AggregationDefinition> aggregationRef,
            Time timestamp,
            ParameterValueDataList parameterValues) {
        this.aggregationRef = aggregationRef;
        this.timestamp = timestamp;
        this.parameterValues = parameterValues;
    }

    @Override
    public Element createElement() {
        return new AggregationValue();
    }

    /**
     * Returns the field aggregationRef.
     * 
     * @return The field aggregationRef
     */
    public ObjectRef<AggregationDefinition> getAggregationRef() {
        return aggregationRef;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field parameterValues.
     * 
     * @return The field parameterValues
     */
    public ParameterValueDataList getParameterValues() {
        return parameterValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationValue) {
            AggregationValue other = (AggregationValue) obj;
            if (aggregationRef == null) {
                if (other.aggregationRef != null) {
                    return false;
                }
            } else {
                if (! aggregationRef.equals(other.aggregationRef)) {
                    return false;
                }
            }
            if (timestamp == null) {
                if (other.timestamp != null) {
                    return false;
                }
            } else {
                if (! timestamp.equals(other.timestamp)) {
                    return false;
                }
            }
            if (parameterValues == null) {
                if (other.parameterValues != null) {
                    return false;
                }
            } else {
                if (! parameterValues.equals(other.parameterValues)) {
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
        hash = 83 * hash + (aggregationRef != null ? aggregationRef.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (parameterValues != null ? parameterValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationValue: ");
        buf.append("aggregationRef=").append(aggregationRef);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", parameterValues=").append(parameterValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (aggregationRef == null) {
            throw new MALException("The field 'aggregationRef' cannot be null!");
        }
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        if (parameterValues == null) {
            throw new MALException("The field 'parameterValues' cannot be null!");
        }
        encoder.encodeElement(aggregationRef);
        encoder.encodeTime(timestamp);
        encoder.encodeElement(parameterValues);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        aggregationRef = (ObjectRef<AggregationDefinition>) decoder.decodeElement(new ObjectRef<AggregationDefinition>());
        timestamp = decoder.decodeTime();
        parameterValues = (ParameterValueDataList) decoder.decodeElement(new ParameterValueDataList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
