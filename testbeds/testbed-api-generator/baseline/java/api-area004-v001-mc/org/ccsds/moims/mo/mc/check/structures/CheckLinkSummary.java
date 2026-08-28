package org.ccsds.moims.mo.mc.check.structures;

/**
 * The CheckLinkSummary structure holds the ids of a specific check link and
 * the check and parameter it links to.
 */
public final class CheckLinkSummary implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125917103489027L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489027L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the CheckIdentity object.
     */
    private Long checkId;

    /**
     * The object instance identifier of the CheckLink object.
     */
    private Long linkId;

    /**
     * Contains the object instance identifier of the CheckLinkDefinition object.
     */
    private Long linkDefinitionId;

    /**
     * TRUE if the check instance is enabled.
     */
    private Boolean checkEnabled;

    /**
     * The object instance identifier of the ParameterIdentity object for the
     * check link. NULL for Compound checks.
     */
    private org.ccsds.moims.mo.com.structures.ObjectKey parameterId;

    /**
     * Default constructor for CheckLinkSummary.
     * 
     */
    public CheckLinkSummary() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param checkId The object instance identifier of the CheckIdentity object.
     * @param linkId The object instance identifier of the CheckLink object.
     * @param linkDefinitionId Contains the object instance identifier of the CheckLinkDefinition object.
     * @param checkEnabled TRUE if the check instance is enabled.
     * @param parameterId The object instance identifier of the ParameterIdentity object for the check link. NULL for Compound checks.
     */
    public CheckLinkSummary(Long checkId,
            Long linkId,
            Long linkDefinitionId,
            Boolean checkEnabled,
            org.ccsds.moims.mo.com.structures.ObjectKey parameterId) {
        this.checkId = checkId;
        this.linkId = linkId;
        this.linkDefinitionId = linkDefinitionId;
        this.checkEnabled = checkEnabled;
        this.parameterId = parameterId;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param checkId The object instance identifier of the CheckIdentity object.
     * @param linkId The object instance identifier of the CheckLink object.
     * @param linkDefinitionId Contains the object instance identifier of the CheckLinkDefinition object.
     * @param checkEnabled TRUE if the check instance is enabled.
     */
    public CheckLinkSummary(Long checkId,
            Long linkId,
            Long linkDefinitionId,
            Boolean checkEnabled) {
        this.checkId = checkId;
        this.linkId = linkId;
        this.linkDefinitionId = linkDefinitionId;
        this.checkEnabled = checkEnabled;
        this.parameterId = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.check.structures.CheckLinkSummary();
    }

    /**
     * Returns the field checkId.
     * 
     * @return The field checkId
     */
    public Long getCheckId() {
        return checkId;
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
     * Returns the field linkDefinitionId.
     * 
     * @return The field linkDefinitionId
     */
    public Long getLinkDefinitionId() {
        return linkDefinitionId;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckLinkSummary) {
            CheckLinkSummary other = (CheckLinkSummary) obj;
            if (checkId == null) {
                if (other.checkId != null) {
                    return false;
                }
            } else {
                if (! checkId.equals(other.checkId)) {
                    return false;
                }
            }
            if (linkId == null) {
                if (other.linkId != null) {
                    return false;
                }
            } else {
                if (! linkId.equals(other.linkId)) {
                    return false;
                }
            }
            if (linkDefinitionId == null) {
                if (other.linkDefinitionId != null) {
                    return false;
                }
            } else {
                if (! linkDefinitionId.equals(other.linkDefinitionId)) {
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (checkId != null ? checkId.hashCode() : 0);
        hash = 83 * hash + (linkId != null ? linkId.hashCode() : 0);
        hash = 83 * hash + (linkDefinitionId != null ? linkDefinitionId.hashCode() : 0);
        hash = 83 * hash + (checkEnabled != null ? checkEnabled.hashCode() : 0);
        hash = 83 * hash + (parameterId != null ? parameterId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CheckLinkSummary: ");
        buf.append("checkId=").append(checkId);
        buf.append(", linkId=").append(linkId);
        buf.append(", linkDefinitionId=").append(linkDefinitionId);
        buf.append(", checkEnabled=").append(checkEnabled);
        buf.append(", parameterId=").append(parameterId);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (checkId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkId' cannot be null!");
        }
        if (linkId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'linkId' cannot be null!");
        }
        if (linkDefinitionId == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'linkDefinitionId' cannot be null!");
        }
        if (checkEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'checkEnabled' cannot be null!");
        }
        encoder.encodeLong(checkId);
        encoder.encodeLong(linkId);
        encoder.encodeLong(linkDefinitionId);
        encoder.encodeBoolean(checkEnabled);
        encoder.encodeNullableElement(parameterId);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        checkId = decoder.decodeLong();
        linkId = decoder.decodeLong();
        linkDefinitionId = decoder.decodeLong();
        checkEnabled = decoder.decodeBoolean();
        parameterId = (org.ccsds.moims.mo.com.structures.ObjectKey) decoder.decodeNullableElement(new org.ccsds.moims.mo.com.structures.ObjectKey());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
