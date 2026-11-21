package org.ccsds.moims.mo.mc.aggregation.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ThresholdFilter structure holds the filter for a parameter.
 */
public final class ThresholdFilter implements Composite {

    private static final long serialVersionUID = 1125925693423622L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423622L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The type of filter to apply for filtered periodic reports when filters
     * are applied.
     */
    private ThresholdType thresholdType;

    /**
     * Threshold value to apply.
     */
    private Attribute thresholdValue;

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
    public ThresholdFilter(ThresholdType thresholdType,
            Attribute thresholdValue,
            Boolean useConverted) {
        this.thresholdType = thresholdType;
        this.thresholdValue = thresholdValue;
        this.useConverted = useConverted;
    }

    @Override
    public Element createElement() {
        return new ThresholdFilter();
    }

    /**
     * Returns the field thresholdType.
     * 
     * @return The field thresholdType
     */
    public ThresholdType getThresholdType() {
        return thresholdType;
    }

    /**
     * Returns the field thresholdValue.
     * 
     * @return The field thresholdValue
     */
    public Attribute getThresholdValue() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (thresholdType == null) {
            throw new MALException("The field 'thresholdType' cannot be null!");
        }
        if (thresholdValue == null) {
            throw new MALException("The field 'thresholdValue' cannot be null!");
        }
        if (useConverted == null) {
            throw new MALException("The field 'useConverted' cannot be null!");
        }
        encoder.encodeElement(thresholdType);
        encoder.encodeAttribute(thresholdValue);
        encoder.encodeBoolean(useConverted);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        thresholdType = (ThresholdType) decoder.decodeElement(ThresholdType.PERCENTAGE);
        thresholdValue = (Attribute) decoder.decodeAttribute();
        useConverted = decoder.decodeBoolean();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
