package org.ccsds.moims.mo.comprototype.archivetest.structures;

/**
 * The SubComposite structure.
 */
public final class SubComposite implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 56295021128712194L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295021128712194L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The integerField field.
     */
    private Integer integerField;

    /**
     * Default constructor for SubComposite.
     * 
     */
    public SubComposite() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param integerField The integerField field.
     */
    public SubComposite(Integer integerField) {
        this.integerField = integerField;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite();
    }

    /**
     * Returns the field integerField.
     * 
     * @return The field integerField
     */
    public Integer getIntegerField() {
        return integerField;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubComposite) {
            SubComposite other = (SubComposite) obj;
            if (integerField == null) {
                if (other.integerField != null) {
                    return false;
                }
            } else {
                if (! integerField.equals(other.integerField)) {
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
        hash = 83 * hash + (integerField != null ? integerField.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(SubComposite: ");
        buf.append("integerField=").append(integerField);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableInteger(integerField);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        integerField = decoder.decodeNullableInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
