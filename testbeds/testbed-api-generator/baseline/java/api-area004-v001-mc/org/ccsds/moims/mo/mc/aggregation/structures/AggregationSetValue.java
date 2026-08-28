package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * The AggregationSetValue structure holds the values for one set of parameter
 * values. If the definition sendUnchanged field is set to FALSE parameter
 * values that are unchanged since the previous report are replaced by a NULL
 * in this list. The parameter values must be held in the same order as that
 * defined in the matching AggregationDefinitionDetails.
 */
public final class AggregationSetValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125925693423620L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423620L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Optional delta time, from the timestamp of the aggregation for the first
     * parameter set of the aggregation or the last value of the previous parameter
     * set otherwise, for the first parameter sample of this set. If NULL, then
     * the first sample time is the same as the aggregation timestamp for the
     * first parameter set of the aggregation or the last value of the previous
     * parameter set otherwise.
     */
    private org.ccsds.moims.mo.mal.structures.Duration deltaTime;

    /**
     * Optional delta time between samples in this set. If NULL, then all samples
     * in this set are given the same time. This is usually driven by the sampleInterval
     * in the aggregation set definition.
     */
    private org.ccsds.moims.mo.mal.structures.Duration intervalTime;

    /**
     * List containing values of the parameters which are part of the aggregation.
     * The ordering of the list entries shall match that of the definition of
     * the aggregation. If there are more values than contained in the definition
     * then it is assumed that the parameters cycle as a complete parameter set.
     */
    private org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList values;

    /**
     * Default constructor for AggregationSetValue.
     * 
     */
    public AggregationSetValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param deltaTime Optional delta time, from the timestamp of the aggregation for the first parameter set of the aggregation or the last value of the previous parameter set otherwise, for the first parameter sample of this set. If NULL, then the first sample time is the same as the aggregation timestamp for the first parameter set of the aggregation or the last value of the previous parameter set otherwise.
     * @param intervalTime Optional delta time between samples in this set. If NULL, then all samples in this set are given the same time. This is usually driven by the sampleInterval in the aggregation set definition.
     * @param values List containing values of the parameters which are part of the aggregation. The ordering of the list entries shall match that of the definition of the aggregation. If there are more values than contained in the definition then it is assumed that the parameters cycle as a complete parameter set.
     */
    public AggregationSetValue(org.ccsds.moims.mo.mal.structures.Duration deltaTime,
            org.ccsds.moims.mo.mal.structures.Duration intervalTime,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList values) {
        this.deltaTime = deltaTime;
        this.intervalTime = intervalTime;
        this.values = values;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param values List containing values of the parameters which are part of the aggregation. The ordering of the list entries shall match that of the definition of the aggregation. If there are more values than contained in the definition then it is assumed that the parameters cycle as a complete parameter set.
     */
    public AggregationSetValue(org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList values) {
        this.deltaTime = null;
        this.intervalTime = null;
        this.values = values;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValue();
    }

    /**
     * Returns the field deltaTime.
     * 
     * @return The field deltaTime
     */
    public org.ccsds.moims.mo.mal.structures.Duration getDeltaTime() {
        return deltaTime;
    }

    /**
     * Returns the field intervalTime.
     * 
     * @return The field intervalTime
     */
    public org.ccsds.moims.mo.mal.structures.Duration getIntervalTime() {
        return intervalTime;
    }

    /**
     * Returns the field values.
     * 
     * @return The field values
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList getValues() {
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationSetValue) {
            AggregationSetValue other = (AggregationSetValue) obj;
            if (deltaTime == null) {
                if (other.deltaTime != null) {
                    return false;
                }
            } else {
                if (! deltaTime.equals(other.deltaTime)) {
                    return false;
                }
            }
            if (intervalTime == null) {
                if (other.intervalTime != null) {
                    return false;
                }
            } else {
                if (! intervalTime.equals(other.intervalTime)) {
                    return false;
                }
            }
            if (values == null) {
                if (other.values != null) {
                    return false;
                }
            } else {
                if (! values.equals(other.values)) {
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
        hash = 83 * hash + (deltaTime != null ? deltaTime.hashCode() : 0);
        hash = 83 * hash + (intervalTime != null ? intervalTime.hashCode() : 0);
        hash = 83 * hash + (values != null ? values.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationSetValue: ");
        buf.append("deltaTime=").append(deltaTime);
        buf.append(", intervalTime=").append(intervalTime);
        buf.append(", values=").append(values);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (values == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'values' cannot be null!");
        }
        encoder.encodeNullableDuration(deltaTime);
        encoder.encodeNullableDuration(intervalTime);
        encoder.encodeElement(values);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        deltaTime = decoder.decodeNullableDuration();
        intervalTime = decoder.decodeNullableDuration();
        values = (org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList) decoder.decodeElement(new org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterValueList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
