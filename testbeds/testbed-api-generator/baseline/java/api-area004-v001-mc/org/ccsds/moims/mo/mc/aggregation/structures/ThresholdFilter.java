package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * The ThresholdFilter structure holds the filter for a parameter.
 */
public final class ThresholdFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125925693423622L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423622L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The type of filter to apply for filtered periodic reports when filters
     * are applied.
     */
    private org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType thresholdType;

    /**
     * Threshold value to apply.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute thresholdValue;

    /**
     * If true, and the relevant Parameter has a conversion, then use the converted
     * value for the threshold comparison, otherwise use the raw value.
     */
    private Boolean useConverted;

    /**
     * Default constructor for ThresholdFilter.
     * 
     */
    public ThresholdFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param thresholdType The type of filter to apply for filtered periodic reports when filters are applied.
     * @param thresholdValue Threshold value to apply.
     * @param useConverted If true, and the relevant Parameter has a conversion, then use the converted value for the threshold comparison, otherwise use the raw value.
     */
    public ThresholdFilter(org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType thresholdType,
            org.ccsds.moims.mo.mal.structures.Attribute thresholdValue,
            Boolean useConverted) {
        this.thresholdType = thresholdType;
        this.thresholdValue = thresholdValue;
        this.useConverted = useConverted;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.aggregation.structures.ThresholdFilter();
    }

    /**
     * Returns the field thresholdType.
     * 
     * @return The field thresholdType
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType getThresholdType() {
        return thresholdType;
    }

    /**
     * Returns the field thresholdValue.
     * 
     * @return The field thresholdValue
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getThresholdValue() {
        return thresholdValue;
    }

    /**
     * Returns the field useConverted.
     * 
     * @return The field useConverted
     */
    public Boolean getUseConverted() {
        return useConverted;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ThresholdFilter) {
            ThresholdFilter other = (ThresholdFilter) obj;
            if (thresholdType == null) {
                if (other.thresholdType != null) {
                    return false;
                }
            } else {
                if (! thresholdType.equals(other.thresholdType)) {
                    return false;
                }
            }
            if (thresholdValue == null) {
                if (other.thresholdValue != null) {
                    return false;
                }
            } else {
                if (! thresholdValue.equals(other.thresholdValue)) {
                    return false;
                }
            }
            if (useConverted == null) {
                if (other.useConverted != null) {
                    return false;
                }
            } else {
                if (! useConverted.equals(other.useConverted)) {
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
        hash = 83 * hash + (thresholdType != null ? thresholdType.hashCode() : 0);
        hash = 83 * hash + (thresholdValue != null ? thresholdValue.hashCode() : 0);
        hash = 83 * hash + (useConverted != null ? useConverted.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ThresholdFilter: ");
        buf.append("thresholdType=").append(thresholdType);
        buf.append(", thresholdValue=").append(thresholdValue);
        buf.append(", useConverted=").append(useConverted);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (thresholdType == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'thresholdType' cannot be null!");
        }
        if (thresholdValue == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'thresholdValue' cannot be null!");
        }
        if (useConverted == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'useConverted' cannot be null!");
        }
        encoder.encodeElement(thresholdType);
        encoder.encodeAttribute(thresholdValue);
        encoder.encodeBoolean(useConverted);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        thresholdType = (org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType) decoder.decodeElement(org.ccsds.moims.mo.mc.aggregation.structures.ThresholdType.PERCENTAGE);
        thresholdValue = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeAttribute();
        useConverted = decoder.decodeBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
