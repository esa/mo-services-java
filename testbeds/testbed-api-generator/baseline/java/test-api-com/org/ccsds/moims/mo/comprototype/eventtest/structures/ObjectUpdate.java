package org.ccsds.moims.mo.comprototype.eventtest.structures;

/**
 * Holds object update details.
.
 */
public final class ObjectUpdate implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 56295003948843011L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003948843011L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Optional field - holds enum value.
.
     */
    private org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum EnumField;

    /**
     * Optional field - holds duration value.
.
     */
    private org.ccsds.moims.mo.mal.structures.Duration DurationField;

    /**
     * Optional field - holds one or more numeric (short values).
.
     */
    private org.ccsds.moims.mo.mal.structures.ShortList NumericListField;

    /**
     * Optional field - holds a composite containing a number of discrete value.
     * .
     */
    private org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite CompositeField;

    /**
     * Default constructor for ObjectUpdate.
     * 
     */
    public ObjectUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param EnumField Optional field - holds enum value.

     * @param DurationField Optional field - holds duration value.

     * @param NumericListField Optional field - holds one or more numeric (short values).

     * @param CompositeField Optional field - holds a composite containing a number of discrete value.

     */
    public ObjectUpdate(org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum EnumField,
            org.ccsds.moims.mo.mal.structures.Duration DurationField,
            org.ccsds.moims.mo.mal.structures.ShortList NumericListField,
            org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite CompositeField) {
        this.EnumField = EnumField;
        this.DurationField = DurationField;
        this.NumericListField = NumericListField;
        this.CompositeField = CompositeField;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate();
    }

    /**
     * Returns the field EnumField.
     * 
     * @return The field EnumField
     */
    public org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum getEnumField() {
        return EnumField;
    }

    /**
     * Returns the field DurationField.
     * 
     * @return The field DurationField
     */
    public org.ccsds.moims.mo.mal.structures.Duration getDurationField() {
        return DurationField;
    }

    /**
     * Returns the field NumericListField.
     * 
     * @return The field NumericListField
     */
    public org.ccsds.moims.mo.mal.structures.ShortList getNumericListField() {
        return NumericListField;
    }

    /**
     * Returns the field CompositeField.
     * 
     * @return The field CompositeField
     */
    public org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite getCompositeField() {
        return CompositeField;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectUpdate) {
            ObjectUpdate other = (ObjectUpdate) obj;
            if (EnumField == null) {
                if (other.EnumField != null) {
                    return false;
                }
            } else {
                if (! EnumField.equals(other.EnumField)) {
                    return false;
                }
            }
            if (DurationField == null) {
                if (other.DurationField != null) {
                    return false;
                }
            } else {
                if (! DurationField.equals(other.DurationField)) {
                    return false;
                }
            }
            if (NumericListField == null) {
                if (other.NumericListField != null) {
                    return false;
                }
            } else {
                if (! NumericListField.equals(other.NumericListField)) {
                    return false;
                }
            }
            if (CompositeField == null) {
                if (other.CompositeField != null) {
                    return false;
                }
            } else {
                if (! CompositeField.equals(other.CompositeField)) {
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
        hash = 83 * hash + (EnumField != null ? EnumField.hashCode() : 0);
        hash = 83 * hash + (DurationField != null ? DurationField.hashCode() : 0);
        hash = 83 * hash + (NumericListField != null ? NumericListField.hashCode() : 0);
        hash = 83 * hash + (CompositeField != null ? CompositeField.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectUpdate: ");
        buf.append("EnumField=").append(EnumField);
        buf.append(", DurationField=").append(DurationField);
        buf.append(", NumericListField=").append(NumericListField);
        buf.append(", CompositeField=").append(CompositeField);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableElement(EnumField);
        encoder.encodeNullableDuration(DurationField);
        encoder.encodeNullableElement(NumericListField);
        encoder.encodeNullableElement(CompositeField);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        EnumField = (org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum) decoder.decodeNullableElement(org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum.FIRST);
        DurationField = decoder.decodeNullableDuration();
        NumericListField = (org.ccsds.moims.mo.mal.structures.ShortList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ShortList());
        CompositeField = (org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite) decoder.decodeNullableElement(new org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
