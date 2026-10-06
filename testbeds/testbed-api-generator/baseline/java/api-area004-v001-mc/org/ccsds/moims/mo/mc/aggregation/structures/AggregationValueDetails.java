package org.ccsds.moims.mo.mc.aggregation.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Time;

/**
 * This structure holds a specific time stamped value of the aggregation.
 * .
 */
public final class AggregationValueDetails implements Composite {

    private static final long serialVersionUID = 1125925693423627L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423627L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The AggregationIdentity object instance identifier.
     */
    private Long aggId;

    /**
     * The AggregationDefinition object instance identifier.
     */
    private Long defId;

    /**
     * The timestamp of the value. Use for the calculation of the individual parameter
     * value timestamps.
     */
    private Time timestamp;

    /**
     * The aggregation value.
     */
    private AggregationValue value;

    /**
     * Default constructor for AggregationValueDetails.
     * 
     */
    public AggregationValueDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param aggId The AggregationIdentity object instance identifier.
     * @param defId The AggregationDefinition object instance identifier.
     * @param timestamp The timestamp of the value. Use for the calculation of the individual parameter value timestamps.
     * @param value The aggregation value.
     */
    public AggregationValueDetails(Long aggId,
            Long defId,
            Time timestamp,
            AggregationValue value) {
        this.aggId = aggId;
        this.defId = defId;
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new AggregationValueDetails();
    }

    /**
     * Returns the field aggId.
     * 
     * @return The field aggId
     */
    public Long getAggId() {
        return aggId;
    }

    /**
     * Returns the field defId.
     * 
     * @return The field defId
     */
    public Long getDefId() {
        return defId;
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
     * Returns the field value.
     * 
     * @return The field value
     */
    public AggregationValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationValueDetails) {
            AggregationValueDetails other = (AggregationValueDetails) obj;
            if (aggId == null) {
                if (other.aggId != null) {
                    return false;
                }
            } else {
                if (! aggId.equals(other.aggId)) {
                    return false;
                }
            }
            if (defId == null) {
                if (other.defId != null) {
                    return false;
                }
            } else {
                if (! defId.equals(other.defId)) {
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
        hash = 83 * hash + (aggId != null ? aggId.hashCode() : 0);
        hash = 83 * hash + (defId != null ? defId.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationValueDetails: ");
        buf.append("aggId=").append(aggId);
        buf.append(", defId=").append(defId);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (aggId == null) {
            throw new MALException("The field 'aggId' cannot be null!");
        }
        if (defId == null) {
            throw new MALException("The field 'defId' cannot be null!");
        }
        if (timestamp == null) {
            throw new MALException("The field 'timestamp' cannot be null!");
        }
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeLong(aggId);
        encoder.encodeLong(defId);
        encoder.encodeTime(timestamp);
        encoder.encodeElement(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        aggId = decoder.decodeLong();
        defId = decoder.decodeLong();
        timestamp = decoder.decodeTime();
        value = (AggregationValue) decoder.decodeElement(new AggregationValue());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
