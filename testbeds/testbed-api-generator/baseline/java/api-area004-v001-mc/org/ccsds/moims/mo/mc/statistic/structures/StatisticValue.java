package org.ccsds.moims.mo.mc.statistic.structures;

/**
 * The StatisticValue structure holds the statistical result for a parameter.
 */
public final class StatisticValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125921398456323L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125921398456323L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the ParameterDefinition object used for
     * the parameter.
     */
    private Long paramDefInstId;

    /**
     * Time the statistic calculations started. This value can be NULL if the
     * start time can be derived by other means, e.g., other start times in a
     * set of StatisticValue structures.
     */
    private org.ccsds.moims.mo.mal.structures.Time startTime;

    /**
     * Time the statistic calculations ended. This value can be NULL if the time
     * can be derived by other means, e.g., other times in a set of StatisticValue
     * structures.
     */
    private org.ccsds.moims.mo.mal.structures.Time endTime;

    /**
     * Time the statistic value was reached. The time is only applicable for particular
     * statistic values such as min or max. Shall be NULL if not applicable for
     * cases such as &quot;mean average&quot;.
     */
    private org.ccsds.moims.mo.mal.structures.Time valueTime;

    /**
     * Value of the statistic.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute value;

    /**
     * Holds the number of samples that contributed to the statistic value. For
     * calculated values such as &quot;mean average&quot; this holds the number
     * of samples that were used to calculate the value, for non-calculated values
     * such as &quot;min&quot; then it is the number of samples that were in the
     * set evaluated.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger sampleCount;

    /**
     * Default constructor for StatisticValue.
     * 
     */
    public StatisticValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param paramDefInstId The object instance identifier of the ParameterDefinition object used for the parameter.
     * @param startTime Time the statistic calculations started. This value can be NULL if the start time can be derived by other means, e.g., other start times in a set of StatisticValue structures.
     * @param endTime Time the statistic calculations ended. This value can be NULL if the time can be derived by other means, e.g., other times in a set of StatisticValue structures.
     * @param valueTime Time the statistic value was reached. The time is only applicable for particular statistic values such as min or max. Shall be NULL if not applicable for cases such as 'mean average'.
     * @param value Value of the statistic.
     * @param sampleCount Holds the number of samples that contributed to the statistic value. For calculated values such as 'mean average' this holds the number of samples that were used to calculate the value, for non-calculated values such as 'min' then it is the number of samples that were in the set evaluated.
     */
    public StatisticValue(Long paramDefInstId,
            org.ccsds.moims.mo.mal.structures.Time startTime,
            org.ccsds.moims.mo.mal.structures.Time endTime,
            org.ccsds.moims.mo.mal.structures.Time valueTime,
            org.ccsds.moims.mo.mal.structures.Attribute value,
            org.ccsds.moims.mo.mal.structures.UInteger sampleCount) {
        this.paramDefInstId = paramDefInstId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.valueTime = valueTime;
        this.value = value;
        this.sampleCount = sampleCount;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param paramDefInstId The object instance identifier of the ParameterDefinition object used for the parameter.
     * @param sampleCount Holds the number of samples that contributed to the statistic value. For calculated values such as 'mean average' this holds the number of samples that were used to calculate the value, for non-calculated values such as 'min' then it is the number of samples that were in the set evaluated.
     */
    public StatisticValue(Long paramDefInstId,
            org.ccsds.moims.mo.mal.structures.UInteger sampleCount) {
        this.paramDefInstId = paramDefInstId;
        this.startTime = null;
        this.endTime = null;
        this.valueTime = null;
        this.value = null;
        this.sampleCount = sampleCount;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.statistic.structures.StatisticValue();
    }

    /**
     * Returns the field paramDefInstId.
     * 
     * @return The field paramDefInstId
     */
    public Long getParamDefInstId() {
        return paramDefInstId;
    }

    /**
     * Returns the field startTime.
     * 
     * @return The field startTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getStartTime() {
        return startTime;
    }

    /**
     * Returns the field endTime.
     * 
     * @return The field endTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getEndTime() {
        return endTime;
    }

    /**
     * Returns the field valueTime.
     * 
     * @return The field valueTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getValueTime() {
        return valueTime;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getValue() {
        return value;
    }

    /**
     * Returns the field sampleCount.
     * 
     * @return The field sampleCount
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getSampleCount() {
        return sampleCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatisticValue) {
            StatisticValue other = (StatisticValue) obj;
            if (paramDefInstId == null) {
                if (other.paramDefInstId != null) {
                    return false;
                }
            } else {
                if (! paramDefInstId.equals(other.paramDefInstId)) {
                    return false;
                }
            }
            if (startTime == null) {
                if (other.startTime != null) {
                    return false;
                }
            } else {
                if (! startTime.equals(other.startTime)) {
                    return false;
                }
            }
            if (endTime == null) {
                if (other.endTime != null) {
                    return false;
                }
            } else {
                if (! endTime.equals(other.endTime)) {
                    return false;
                }
            }
            if (valueTime == null) {
                if (other.valueTime != null) {
                    return false;
                }
            } else {
                if (! valueTime.equals(other.valueTime)) {
                    return false;
                }
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
                    return false;
                }
            }
            if (sampleCount == null) {
                if (other.sampleCount != null) {
                    return false;
                }
            } else {
                if (! sampleCount.equals(other.sampleCount)) {
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
        hash = 83 * hash + (paramDefInstId != null ? paramDefInstId.hashCode() : 0);
        hash = 83 * hash + (startTime != null ? startTime.hashCode() : 0);
        hash = 83 * hash + (endTime != null ? endTime.hashCode() : 0);
        hash = 83 * hash + (valueTime != null ? valueTime.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        hash = 83 * hash + (sampleCount != null ? sampleCount.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StatisticValue: ");
        buf.append("paramDefInstId=").append(paramDefInstId);
        buf.append(", startTime=").append(startTime);
        buf.append(", endTime=").append(endTime);
        buf.append(", valueTime=").append(valueTime);
        buf.append(", value=").append(value);
        buf.append(", sampleCount=").append(sampleCount);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (paramDefInstId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'paramDefInstId' cannot be null!");
        }
        if (sampleCount == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'sampleCount' cannot be null!");
        }
        encoder.encodeLong(paramDefInstId);
        encoder.encodeNullableTime(startTime);
        encoder.encodeNullableTime(endTime);
        encoder.encodeNullableTime(valueTime);
        encoder.encodeNullableAttribute(value);
        encoder.encodeUInteger(sampleCount);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        paramDefInstId = decoder.decodeLong();
        startTime = decoder.decodeNullableTime();
        endTime = decoder.decodeNullableTime();
        valueTime = decoder.decodeNullableTime();
        value = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        sampleCount = decoder.decodeUInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
