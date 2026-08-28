package org.ccsds.moims.mo.mc.structures;

/**
 * The ParameterValue structure is a contextual object associated to the ParameterDefinition.
 * It is uniquely identified by the timestamp field, relative to the ParameterDefinition
 * object. It represents a specific time stamped value of the parameter.
 */
public final class ParameterValue implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899940397079L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397079L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The paramRef field.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ParameterDefinition> paramRef;

    /**
     * The timestamp field.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * The samplingTime field.
     */
    private org.ccsds.moims.mo.mal.structures.Time samplingTime;

    /**
     * The value field.
     */
    private org.ccsds.moims.mo.mc.structures.ParameterValueData value;

    /**
     * Default constructor for ParameterValue.
     * 
     */
    public ParameterValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param paramRef The paramRef field.
     * @param timestamp The timestamp field.
     * @param samplingTime The samplingTime field.
     * @param value The value field.
     */
    public ParameterValue(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ParameterDefinition> paramRef,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mal.structures.Time samplingTime,
            org.ccsds.moims.mo.mc.structures.ParameterValueData value) {
        this.paramRef = paramRef;
        this.timestamp = timestamp;
        this.samplingTime = samplingTime;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param paramRef The paramRef field.
     * @param timestamp The timestamp field.
     * @param value The value field.
     */
    public ParameterValue(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ParameterDefinition> paramRef,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mc.structures.ParameterValueData value) {
        this.paramRef = paramRef;
        this.timestamp = timestamp;
        this.samplingTime = null;
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.ParameterValue();
    }

    /**
     * Returns the field paramRef.
     * 
     * @return The field paramRef
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ParameterDefinition> getParamRef() {
        return paramRef;
    }

    /**
     * Returns the field timestamp.
     * 
     * @return The field timestamp
     */
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field samplingTime.
     * 
     * @return The field samplingTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getSamplingTime() {
        return samplingTime;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mc.structures.ParameterValueData getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterValue) {
            ParameterValue other = (ParameterValue) obj;
            if (paramRef == null) {
                if (other.paramRef != null) {
                    return false;
                }
            } else {
                if (! paramRef.equals(other.paramRef)) {
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
            if (samplingTime == null) {
                if (other.samplingTime != null) {
                    return false;
                }
            } else {
                if (! samplingTime.equals(other.samplingTime)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (paramRef != null ? paramRef.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (samplingTime != null ? samplingTime.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterValue: ");
        buf.append("paramRef=").append(paramRef);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", samplingTime=").append(samplingTime);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (paramRef == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'paramRef' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeElement(paramRef);
        encoder.encodeTime(timestamp);
        encoder.encodeNullableTime(samplingTime);
        encoder.encodeElement(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        paramRef = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ParameterDefinition>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mc.structures.ParameterDefinition>());
        timestamp = decoder.decodeTime();
        samplingTime = decoder.decodeNullableTime();
        value = (org.ccsds.moims.mo.mc.structures.ParameterValueData) decoder.decodeElement(new org.ccsds.moims.mo.mc.structures.ParameterValueData());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
