package org.ccsds.moims.mo.mc.parameter.structures;

/**
 * This structure holds a specific time stamped value of the parameter. The
 * type of the value shall match that specified in the parameter definition.
 */
public final class ParameterValueDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125908513554439L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125908513554439L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The ParameterIdentity object instance identifier.
     */
    private Long paramId;

    /**
     * The ParameterDefinition object instance identifier.
     */
    private Long defId;

    /**
     * The timestamp of the value.
     */
    private org.ccsds.moims.mo.mal.structures.Time timestamp;

    /**
     * The parameter value.
     */
    private org.ccsds.moims.mo.mc.parameter.structures.ParameterValue value;

    /**
     * Default constructor for ParameterValueDetails.
     * 
     */
    public ParameterValueDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param paramId The ParameterIdentity object instance identifier.
     * @param defId The ParameterDefinition object instance identifier.
     * @param timestamp The timestamp of the value.
     * @param value The parameter value.
     */
    public ParameterValueDetails(Long paramId,
            Long defId,
            org.ccsds.moims.mo.mal.structures.Time timestamp,
            org.ccsds.moims.mo.mc.parameter.structures.ParameterValue value) {
        this.paramId = paramId;
        this.defId = defId;
        this.timestamp = timestamp;
        this.value = value;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.parameter.structures.ParameterValueDetails();
    }

    /**
     * Returns the field paramId.
     * 
     * @return The field paramId
     */
    public Long getParamId() {
        return paramId;
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
    public org.ccsds.moims.mo.mal.structures.Time getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public org.ccsds.moims.mo.mc.parameter.structures.ParameterValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterValueDetails) {
            ParameterValueDetails other = (ParameterValueDetails) obj;
            if (paramId == null) {
                if (other.paramId != null) {
                    return false;
                }
            } else {
                if (! paramId.equals(other.paramId)) {
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
        hash = 83 * hash + (paramId != null ? paramId.hashCode() : 0);
        hash = 83 * hash + (defId != null ? defId.hashCode() : 0);
        hash = 83 * hash + (timestamp != null ? timestamp.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterValueDetails: ");
        buf.append("paramId=").append(paramId);
        buf.append(", defId=").append(defId);
        buf.append(", timestamp=").append(timestamp);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (paramId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'paramId' cannot be null!");
        }
        if (defId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'defId' cannot be null!");
        }
        if (timestamp == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'timestamp' cannot be null!");
        }
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeLong(paramId);
        encoder.encodeLong(defId);
        encoder.encodeTime(timestamp);
        encoder.encodeElement(value);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        paramId = decoder.decodeLong();
        defId = decoder.decodeLong();
        timestamp = decoder.decodeTime();
        value = (org.ccsds.moims.mo.mc.parameter.structures.ParameterValue) decoder.decodeElement(new org.ccsds.moims.mo.mc.parameter.structures.ParameterValue());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
