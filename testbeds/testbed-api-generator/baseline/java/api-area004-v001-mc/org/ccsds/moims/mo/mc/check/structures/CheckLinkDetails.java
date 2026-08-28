package org.ccsds.moims.mo.mc.check.structures;

/**
 * The CheckLinkDetails structure represents the link from a check definition
 * to a check result for a specific parameter.
 */
public final class CheckLinkDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125917103489025L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489025L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * TRUE if the check instance is enabled.
     */
    private Boolean checkEnabled;

    /**
     * If TRUE then any change to state or value of the parameter, or the check
     * condition will trigger a check evaluation. Ignored for Compound checks.
     */
    private Boolean checkOnChange;

    /**
     * If set to TRUE the converted value field of the parameter value should
     * be used, otherwise the raw value field should be used. Ignored for Compound
     * checks.
     */
    private Boolean useConverted;

    /**
     * The interval that a check should be applied. Only applicable if checkOnChange
     * is FALSE. If &quot;0&quot;, then no periodic checking shall be performed,
     * and a check will be triggered by another mechanism. Ignored for Compound
     * checks.
     */
    private org.ccsds.moims.mo.mal.structures.Duration checkInterval;

    /**
     * Should this check be applied, if NULL then always applied.
     */
    private org.ccsds.moims.mo.mc.structures.ParameterExpression condition;

    /**
     * Default constructor for CheckLinkDetails.
     * 
     */
    public CheckLinkDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param checkEnabled TRUE if the check instance is enabled.
     * @param checkOnChange If TRUE then any change to state or value of the parameter, or the check condition will trigger a check evaluation. Ignored for Compound checks.
     * @param useConverted If set to TRUE the converted value field of the parameter value should be used, otherwise the raw value field should be used. Ignored for Compound checks.
     * @param checkInterval The interval that a check should be applied. Only applicable if checkOnChange is FALSE. If '0', then no periodic checking shall be performed, and a check will be triggered by another mechanism. Ignored for Compound checks.
     * @param condition Should this check be applied, if NULL then always applied.
     */
    public CheckLinkDetails(Boolean checkEnabled,
            Boolean checkOnChange,
            Boolean useConverted,
            org.ccsds.moims.mo.mal.structures.Duration checkInterval,
            org.ccsds.moims.mo.mc.structures.ParameterExpression condition) {
        this.checkEnabled = checkEnabled;
        this.checkOnChange = checkOnChange;
        this.useConverted = useConverted;
        this.checkInterval = checkInterval;
        this.condition = condition;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param checkEnabled TRUE if the check instance is enabled.
     * @param checkOnChange If TRUE then any change to state or value of the parameter, or the check condition will trigger a check evaluation. Ignored for Compound checks.
     * @param useConverted If set to TRUE the converted value field of the parameter value should be used, otherwise the raw value field should be used. Ignored for Compound checks.
     * @param checkInterval The interval that a check should be applied. Only applicable if checkOnChange is FALSE. If '0', then no periodic checking shall be performed, and a check will be triggered by another mechanism. Ignored for Compound checks.
     */
    public CheckLinkDetails(Boolean checkEnabled,
            Boolean checkOnChange,
            Boolean useConverted,
            org.ccsds.moims.mo.mal.structures.Duration checkInterval) {
        this.checkEnabled = checkEnabled;
        this.checkOnChange = checkOnChange;
        this.useConverted = useConverted;
        this.checkInterval = checkInterval;
        this.condition = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.CheckLinkDetails();
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
     * Returns the field checkOnChange.
     * 
     * @return The field checkOnChange
     */
    public Boolean getCheckOnChange() {
        return checkOnChange;
    }

    /**
     * Returns the field useConverted.
     * 
     * @return The field useConverted
     */
    public Boolean getUseConverted() {
        return useConverted;
    }

    /**
     * Returns the field checkInterval.
     * 
     * @return The field checkInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getCheckInterval() {
        return checkInterval;
    }

    /**
     * Returns the field condition.
     * 
     * @return The field condition
     */
    public org.ccsds.moims.mo.mc.structures.ParameterExpression getCondition() {
        return condition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckLinkDetails) {
            CheckLinkDetails other = (CheckLinkDetails) obj;
            if (checkEnabled == null) {
                if (other.checkEnabled != null) {
                    return false;
                }
            } else {
                if (! checkEnabled.equals(other.checkEnabled)) {
                    return false;
                }
            }
            if (checkOnChange == null) {
                if (other.checkOnChange != null) {
                    return false;
                }
            } else {
                if (! checkOnChange.equals(other.checkOnChange)) {
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
            if (checkInterval == null) {
                if (other.checkInterval != null) {
                    return false;
                }
            } else {
                if (! checkInterval.equals(other.checkInterval)) {
                    return false;
                }
            }
            if (condition == null) {
                if (other.condition != null) {
                    return false;
                }
            } else {
                if (! condition.equals(other.condition)) {
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
        hash = 83 * hash + (checkEnabled != null ? checkEnabled.hashCode() : 0);
        hash = 83 * hash + (checkOnChange != null ? checkOnChange.hashCode() : 0);
        hash = 83 * hash + (useConverted != null ? useConverted.hashCode() : 0);
        hash = 83 * hash + (checkInterval != null ? checkInterval.hashCode() : 0);
        hash = 83 * hash + (condition != null ? condition.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CheckLinkDetails: ");
        buf.append("checkEnabled=").append(checkEnabled);
        buf.append(", checkOnChange=").append(checkOnChange);
        buf.append(", useConverted=").append(useConverted);
        buf.append(", checkInterval=").append(checkInterval);
        buf.append(", condition=").append(condition);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (checkEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkEnabled' cannot be null!");
        }
        if (checkOnChange == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkOnChange' cannot be null!");
        }
        if (useConverted == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'useConverted' cannot be null!");
        }
        if (checkInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkInterval' cannot be null!");
        }
        encoder.encodeBoolean(checkEnabled);
        encoder.encodeBoolean(checkOnChange);
        encoder.encodeBoolean(useConverted);
        encoder.encodeDuration(checkInterval);
        encoder.encodeNullableElement(condition);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        checkEnabled = decoder.decodeBoolean();
        checkOnChange = decoder.decodeBoolean();
        useConverted = decoder.decodeBoolean();
        checkInterval = decoder.decodeDuration();
        condition = (org.ccsds.moims.mo.mc.structures.ParameterExpression) decoder.decodeNullableElement(new org.ccsds.moims.mo.mc.structures.ParameterExpression());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
