package org.ccsds.moims.mo.mc.check.structures;

/**
 * The CheckResultFilter structure holds a filter for the current check result
 * transition information.
 */
public final class CheckResultFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125917103489029L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489029L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * If TRUE then the checkFilter field contains GroupIdentity object instance
     * identifiers that link to CheckIdentity objects otherwise it contains CheckIdentity
     * object instance identifiers directly.
     */
    private Boolean checkFilterViaGroups;

    /**
     * The list of GroupIdentity object instance identifiers if checkFilterViaGroups
     * is TRUE otherwise the CheckIdentity object instance identifiers to filter
     * on. A value of &quot;0&quot; means match all.
     */
    private org.ccsds.moims.mo.mal.structures.LongList checkFilter;

    /**
     * If TRUE then the parameterFilter field contains GroupIdentity object instance
     * identifiers that link to ParameterIdentity objects otherwise it contains
     * ParameterIdentity object instance identifiers directly.
     */
    private Boolean parameterFilterViaGroups;

    /**
     * The list of GroupIdentity object instance identifiers if parameterFilterViaGroups
     * is TRUE otherwise the ParameterIdentity object instance identifiers to
     * filter on. A value of &quot;0&quot; means match all.
     */
    private org.ccsds.moims.mo.mal.structures.LongList parameterFilter;

    /**
     * The list of required check states to filter on. Empty list means match
     * all.
     */
    private org.ccsds.moims.mo.mc.check.structures.CheckStateList stateFilter;

    /**
     * Default constructor for CheckResultFilter.
     * 
     */
    public CheckResultFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param checkFilterViaGroups If TRUE then the checkFilter field contains GroupIdentity object instance identifiers that link to CheckIdentity objects otherwise it contains CheckIdentity object instance identifiers directly.
     * @param checkFilter The list of GroupIdentity object instance identifiers if checkFilterViaGroups is TRUE otherwise the CheckIdentity object instance identifiers to filter on. A value of '0' means match all.
     * @param parameterFilterViaGroups If TRUE then the parameterFilter field contains GroupIdentity object instance identifiers that link to ParameterIdentity objects otherwise it contains ParameterIdentity object instance identifiers directly.
     * @param parameterFilter The list of GroupIdentity object instance identifiers if parameterFilterViaGroups is TRUE otherwise the ParameterIdentity object instance identifiers to filter on. A value of '0' means match all.
     * @param stateFilter The list of required check states to filter on. Empty list means match all.
     */
    public CheckResultFilter(Boolean checkFilterViaGroups,
            org.ccsds.moims.mo.mal.structures.LongList checkFilter,
            Boolean parameterFilterViaGroups,
            org.ccsds.moims.mo.mal.structures.LongList parameterFilter,
            org.ccsds.moims.mo.mc.check.structures.CheckStateList stateFilter) {
        this.checkFilterViaGroups = checkFilterViaGroups;
        this.checkFilter = checkFilter;
        this.parameterFilterViaGroups = parameterFilterViaGroups;
        this.parameterFilter = parameterFilter;
        this.stateFilter = stateFilter;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.CheckResultFilter();
    }

    /**
     * Returns the field checkFilterViaGroups.
     * 
     * @return The field checkFilterViaGroups
     */
    public Boolean getCheckFilterViaGroups() {
        return checkFilterViaGroups;
    }

    /**
     * Returns the field checkFilter.
     * 
     * @return The field checkFilter
     */
    public org.ccsds.moims.mo.mal.structures.LongList getCheckFilter() {
        return checkFilter;
    }

    /**
     * Returns the field parameterFilterViaGroups.
     * 
     * @return The field parameterFilterViaGroups
     */
    public Boolean getParameterFilterViaGroups() {
        return parameterFilterViaGroups;
    }

    /**
     * Returns the field parameterFilter.
     * 
     * @return The field parameterFilter
     */
    public org.ccsds.moims.mo.mal.structures.LongList getParameterFilter() {
        return parameterFilter;
    }

    /**
     * Returns the field stateFilter.
     * 
     * @return The field stateFilter
     */
    public org.ccsds.moims.mo.mc.check.structures.CheckStateList getStateFilter() {
        return stateFilter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckResultFilter) {
            CheckResultFilter other = (CheckResultFilter) obj;
            if (checkFilterViaGroups == null) {
                if (other.checkFilterViaGroups != null) {
                    return false;
                }
            } else {
                if (! checkFilterViaGroups.equals(other.checkFilterViaGroups)) {
                    return false;
                }
            }
            if (checkFilter == null) {
                if (other.checkFilter != null) {
                    return false;
                }
            } else {
                if (! checkFilter.equals(other.checkFilter)) {
                    return false;
                }
            }
            if (parameterFilterViaGroups == null) {
                if (other.parameterFilterViaGroups != null) {
                    return false;
                }
            } else {
                if (! parameterFilterViaGroups.equals(other.parameterFilterViaGroups)) {
                    return false;
                }
            }
            if (parameterFilter == null) {
                if (other.parameterFilter != null) {
                    return false;
                }
            } else {
                if (! parameterFilter.equals(other.parameterFilter)) {
                    return false;
                }
            }
            if (stateFilter == null) {
                if (other.stateFilter != null) {
                    return false;
                }
            } else {
                if (! stateFilter.equals(other.stateFilter)) {
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
        hash = 83 * hash + (checkFilterViaGroups != null ? checkFilterViaGroups.hashCode() : 0);
        hash = 83 * hash + (checkFilter != null ? checkFilter.hashCode() : 0);
        hash = 83 * hash + (parameterFilterViaGroups != null ? parameterFilterViaGroups.hashCode() : 0);
        hash = 83 * hash + (parameterFilter != null ? parameterFilter.hashCode() : 0);
        hash = 83 * hash + (stateFilter != null ? stateFilter.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CheckResultFilter: ");
        buf.append("checkFilterViaGroups=").append(checkFilterViaGroups);
        buf.append(", checkFilter=").append(checkFilter);
        buf.append(", parameterFilterViaGroups=").append(parameterFilterViaGroups);
        buf.append(", parameterFilter=").append(parameterFilter);
        buf.append(", stateFilter=").append(stateFilter);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (checkFilterViaGroups == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkFilterViaGroups' cannot be null!");
        }
        if (checkFilter == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkFilter' cannot be null!");
        }
        if (parameterFilterViaGroups == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameterFilterViaGroups' cannot be null!");
        }
        if (parameterFilter == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameterFilter' cannot be null!");
        }
        if (stateFilter == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'stateFilter' cannot be null!");
        }
        encoder.encodeBoolean(checkFilterViaGroups);
        encoder.encodeElement(checkFilter);
        encoder.encodeBoolean(parameterFilterViaGroups);
        encoder.encodeElement(parameterFilter);
        encoder.encodeElement(stateFilter);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        checkFilterViaGroups = decoder.decodeBoolean();
        checkFilter = (org.ccsds.moims.mo.mal.structures.LongList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.LongList());
        parameterFilterViaGroups = decoder.decodeBoolean();
        parameterFilter = (org.ccsds.moims.mo.mal.structures.LongList) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.LongList());
        stateFilter = (org.ccsds.moims.mo.mc.check.structures.CheckStateList) decoder.decodeElement(new org.ccsds.moims.mo.mc.check.structures.CheckStateList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
