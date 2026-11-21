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
 * The ParameterValue structure is a contextual object associated to the ParameterDefinition.
 * It is uniquely identified by the timestamp field, relative to the ParameterDefinition
 * object. It represents a specific time stamped value of the parameter.
 */
public final class ParameterValue implements Composite {

    private static final long serialVersionUID = 1125899940397079L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397079L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The paramRef field.
     */
    private ObjectRef<ParameterDefinition> paramRef;

    /**
     * The timestamp field.
     */
    private Time timestamp;

    /**
     * The samplingTime field.
     */
    private Time samplingTime;

    /**
     * The value field.
     */
    private ParameterValueData value;

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
    public ParameterValue(ObjectRef<ParameterDefinition> paramRef,
            Time timestamp,
            Time samplingTime,
            ParameterValueData value) {
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
    public ParameterValue(ObjectRef<ParameterDefinition> paramRef,
            Time timestamp,
            ParameterValueData value) {
        this.paramRef = paramRef;
        this.timestamp = timestamp;
        this.samplingTime = null;
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new ParameterValue();
    }

    /**
     * Returns the field paramRef.
     * 
     * @return The field paramRef
     */
    public ObjectRef<ParameterDefinition> getParamRef() {
        return paramRef;
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
     * Returns the field samplingTime.
     * 
     * @return The field samplingTime
     */
    public Time getSamplingTime() {
        return samplingTime;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public ParameterValueData getValue() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (paramRef == null) {
            throw new MALException("The field 'paramRef' cannot be null!");
        }
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeElement(paramRef);
        encoder.encodeTime(timestamp);
        encoder.encodeNullableTime(samplingTime);
        encoder.encodeElement(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        paramRef = (ObjectRef<ParameterDefinition>) decoder.decodeElement(new ObjectRef<ParameterDefinition>());
        timestamp = decoder.decodeTime();
        samplingTime = decoder.decodeNullableTime();
        value = (ParameterValueData) decoder.decodeElement(new ParameterValueData());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
