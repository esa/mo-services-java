package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * The AggregationValue structure holds the values for one or more sets of
 * parameter values. The value sets must be held in the same order as that
 * defined in the matching AggregationDefinitionDetails.
 */
public final class AggregationValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125925693423619L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423619L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reason for the aggregation being generated.
     */
    private org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode generationMode;

    /**
     * If a filter is enabled when the aggregation value is generated then this
     * value shall be set to TRUE, else FALSE.
     */
    private Boolean filtered;

    /**
     * The parameterSetValues list holds the sets of values of the aggregation.
     * The sets must be held in the same order as that defined in the aggregation
     * definition.
     */
    private org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValueList parameterSetValues;

    /**
     * Default constructor for AggregationValue.
     * 
     */
    public AggregationValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param generationMode Reason for the aggregation being generated.
     * @param filtered If a filter is enabled when the aggregation value is generated then this value shall be set to TRUE, else FALSE.
     * @param parameterSetValues The parameterSetValues list holds the sets of values of the aggregation. The sets must be held in the same order as that defined in the aggregation definition.
     */
    public AggregationValue(org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode generationMode,
            Boolean filtered,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValueList parameterSetValues) {
        this.generationMode = generationMode;
        this.filtered = filtered;
        this.parameterSetValues = parameterSetValues;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationValue();
    }

    /**
     * Returns the field generationMode.
     * 
     * @return The field generationMode
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode getGenerationMode() {
        return generationMode;
    }

    /**
     * Returns the field filtered.
     * 
     * @return The field filtered
     */
    public Boolean getFiltered() {
        return filtered;
    }

    /**
     * Returns the field parameterSetValues.
     * 
     * @return The field parameterSetValues
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValueList getParameterSetValues() {
        return parameterSetValues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationValue) {
            AggregationValue other = (AggregationValue) obj;
            if (generationMode == null) {
                if (other.generationMode != null) {
                    return false;
                }
            } else {
                if (! generationMode.equals(other.generationMode)) {
                    return false;
                }
            }
            if (filtered == null) {
                if (other.filtered != null) {
                    return false;
                }
            } else {
                if (! filtered.equals(other.filtered)) {
                    return false;
                }
            }
            if (parameterSetValues == null) {
                if (other.parameterSetValues != null) {
                    return false;
                }
            } else {
                if (! parameterSetValues.equals(other.parameterSetValues)) {
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
        hash = 83 * hash + (generationMode != null ? generationMode.hashCode() : 0);
        hash = 83 * hash + (filtered != null ? filtered.hashCode() : 0);
        hash = 83 * hash + (parameterSetValues != null ? parameterSetValues.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationValue: ");
        buf.append("generationMode=").append(generationMode);
        buf.append(", filtered=").append(filtered);
        buf.append(", parameterSetValues=").append(parameterSetValues);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (generationMode == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'generationMode' cannot be null!");
        }
        if (filtered == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'filtered' cannot be null!");
        }
        if (parameterSetValues == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameterSetValues' cannot be null!");
        }
        encoder.encodeElement(generationMode);
        encoder.encodeBoolean(filtered);
        encoder.encodeElement(parameterSetValues);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        generationMode = (org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode) decoder.decodeElement(org.ccsds.moims.mo.mc.aggregation.structures.GenerationMode.ADHOC);
        filtered = decoder.decodeBoolean();
        parameterSetValues = (org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValueList) decoder.decodeElement(new org.ccsds.moims.mo.mc.aggregation.structures.AggregationSetValueList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
