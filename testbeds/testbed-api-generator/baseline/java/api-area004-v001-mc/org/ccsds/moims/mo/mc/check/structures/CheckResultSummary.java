package org.ccsds.moims.mo.mc.check.structures;

/**
 * The CheckResultSummary structure holds details about a specific check link
 * and its evaluated result.
 */
public final class CheckResultSummary implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125917103489028L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489028L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the check link.
     */
    private Long linkId;

    /**
     * The current enabled state of the check link.
     */
    private Boolean checkEnabled;

    /**
     * The object instance key of the ParameterIdentity being checked. NULL only
     * for Compound checks.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey parameterId;

    /**
     * The timestamp of the check result. If as a result of max reporting interval
     * expiring then it shall contain the expiration timestamp.
     */
    private org.ccsds.moims.mo.mal.structures.Time evaluationTime;

    /**
     * The check result value.
     */
    private org.ccsds.moims.mo.mc.check.structures.CheckResult result;

    /**
     * Default constructor for CheckResultSummary.
     * 
     */
    public CheckResultSummary() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param linkId The object instance identifier of the check link.
     * @param checkEnabled The current enabled state of the check link.
     * @param parameterId The object instance key of the ParameterIdentity being checked. NULL only for Compound checks.
     * @param evaluationTime The timestamp of the check result. If as a result of max reporting interval expiring then it shall contain the expiration timestamp.
     * @param result The check result value.
     */
    public CheckResultSummary(Long linkId,
            Boolean checkEnabled,
            org.ccsds.moims.mo.com.structures.ObjectKey parameterId,
            org.ccsds.moims.mo.mal.structures.Time evaluationTime,
            org.ccsds.moims.mo.mc.check.structures.CheckResult result) {
        this.linkId = linkId;
        this.checkEnabled = checkEnabled;
        this.parameterId = parameterId;
        this.evaluationTime = evaluationTime;
        this.result = result;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param linkId The object instance identifier of the check link.
     * @param checkEnabled The current enabled state of the check link.
     * @param evaluationTime The timestamp of the check result. If as a result of max reporting interval expiring then it shall contain the expiration timestamp.
     * @param result The check result value.
     */
    public CheckResultSummary(Long linkId,
            Boolean checkEnabled,
            org.ccsds.moims.mo.mal.structures.Time evaluationTime,
            org.ccsds.moims.mo.mc.check.structures.CheckResult result) {
        this.linkId = linkId;
        this.checkEnabled = checkEnabled;
        this.parameterId = null;
        this.evaluationTime = evaluationTime;
        this.result = result;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.CheckResultSummary();
    }

    /**
     * Returns the field linkId.
     * 
     * @return The field linkId
     */
    public Long getLinkId() {
        return linkId;
    }

    /**
     * Returns the field checkEnabled.
     * 
     * @return The field checkEnabled
     */
    public Boolean getCheckEnabled() {
        return checkEnabled;
    }

    /**
     * Returns the field parameterId.
     * 
     * @return The field parameterId
     */
    public org.ccsds.moims.mo.com.structures.ObjectKey getParameterId() {
        return parameterId;
    }

    /**
     * Returns the field evaluationTime.
     * 
     * @return The field evaluationTime
     */
    public org.ccsds.moims.mo.mal.structures.Time getEvaluationTime() {
        return evaluationTime;
    }

    /**
     * Returns the field result.
     * 
     * @return The field result
     */
    public org.ccsds.moims.mo.mc.check.structures.CheckResult getResult() {
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckResultSummary) {
            CheckResultSummary other = (CheckResultSummary) obj;
            if (linkId == null) {
                if (other.linkId != null) {
                    return false;
                }
            } else {
                if (! linkId.equals(other.linkId)) {
                    return false;
                }
            }
            if (checkEnabled == null) {
                if (other.checkEnabled != null) {
                    return false;
                }
            } else {
                if (! checkEnabled.equals(other.checkEnabled)) {
                    return false;
                }
            }
            if (parameterId == null) {
                if (other.parameterId != null) {
                    return false;
                }
            } else {
                if (! parameterId.equals(other.parameterId)) {
                    return false;
                }
            }
            if (evaluationTime == null) {
                if (other.evaluationTime != null) {
                    return false;
                }
            } else {
                if (! evaluationTime.equals(other.evaluationTime)) {
                    return false;
                }
            }
            if (result == null) {
                if (other.result != null) {
                    return false;
                }
            } else {
                if (! result.equals(other.result)) {
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
        hash = 83 * hash + (linkId != null ? linkId.hashCode() : 0);
        hash = 83 * hash + (checkEnabled != null ? checkEnabled.hashCode() : 0);
        hash = 83 * hash + (parameterId != null ? parameterId.hashCode() : 0);
        hash = 83 * hash + (evaluationTime != null ? evaluationTime.hashCode() : 0);
        hash = 83 * hash + (result != null ? result.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CheckResultSummary: ");
        buf.append("linkId=").append(linkId);
        buf.append(", checkEnabled=").append(checkEnabled);
        buf.append(", parameterId=").append(parameterId);
        buf.append(", evaluationTime=").append(evaluationTime);
        buf.append(", result=").append(result);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (linkId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'linkId' cannot be null!");
        }
        if (checkEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkEnabled' cannot be null!");
        }
        if (evaluationTime == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'evaluationTime' cannot be null!");
        }
        if (result == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'result' cannot be null!");
        }
        encoder.encodeLong(linkId);
        encoder.encodeBoolean(checkEnabled);
        encoder.encodeNullableElement(parameterId);
        encoder.encodeTime(evaluationTime);
        encoder.encodeElement(result);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        linkId = decoder.decodeLong();
        checkEnabled = decoder.decodeBoolean();
        parameterId = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeNullableElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        evaluationTime = decoder.decodeTime();
        result = (org.ccsds.moims.mo.mc.check.structures.CheckResult) decoder.decodeElement(new org.ccsds.moims.mo.mc.check.structures.CheckResult());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
