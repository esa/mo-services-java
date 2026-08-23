package org.ccsds.moims.mo.mc.structures;

/**
 * The AlertConfiguration structure is used to retrieve the configuration
 * of the generation of an alert.
 */
public final class AlertConfiguration implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125899940397088L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397088L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The generationEnabled field.
     */
    private Boolean generationEnabled;

    /**
     * Default constructor for AlertConfiguration.
     * 
     */
    public AlertConfiguration() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param generationEnabled The generationEnabled field.
     */
    public AlertConfiguration(Boolean generationEnabled) {
        this.generationEnabled = generationEnabled;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.AlertConfiguration();
    }

    /**
     * Returns the field generationEnabled.
     * 
     * @return The field generationEnabled
     */
    public Boolean getGenerationEnabled() {
        return generationEnabled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlertConfiguration) {
            AlertConfiguration other = (AlertConfiguration) obj;
            if (generationEnabled == null) {
                if (other.generationEnabled != null) {
                    return false;
                }
            } else {
                if (! generationEnabled.equals(other.generationEnabled)) {
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
        hash = 83 * hash + (generationEnabled != null ? generationEnabled.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AlertConfiguration: ");
        buf.append("generationEnabled=").append(generationEnabled);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (generationEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'generationEnabled' cannot be null!");
        }
        encoder.encodeBoolean(generationEnabled);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        generationEnabled = decoder.decodeBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
