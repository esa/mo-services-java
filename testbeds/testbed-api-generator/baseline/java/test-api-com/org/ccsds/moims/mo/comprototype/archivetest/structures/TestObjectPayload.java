package org.ccsds.moims.mo.comprototype.archivetest.structures;

/**
 * Object body to test the Archive service.
 */
public final class TestObjectPayload implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 56295021128712193L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295021128712193L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The booleanField field.
     */
    private Boolean booleanField;

    /**
     * The integerField field.
     */
    private Integer integerField;

    /**
     * The stringField field.
     */
    private String stringField;

    /**
     * The compositeField field.
     */
    private org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite compositeField;

    /**
     * The enumeratedField field.
     */
    private org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject enumeratedField;

    /**
     * The listField field.
     */
    private org.ccsds.moims.mo.mal.structures.IntegerList listField;

    /**
     * Default constructor for TestObjectPayload.
     * 
     */
    public TestObjectPayload() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param booleanField The booleanField field.
     * @param integerField The integerField field.
     * @param stringField The stringField field.
     * @param compositeField The compositeField field.
     * @param enumeratedField The enumeratedField field.
     * @param listField The listField field.
     */
    public TestObjectPayload(Boolean booleanField,
            Integer integerField,
            String stringField,
            org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite compositeField,
            org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject enumeratedField,
            org.ccsds.moims.mo.mal.structures.IntegerList listField) {
        this.booleanField = booleanField;
        this.integerField = integerField;
        this.stringField = stringField;
        this.compositeField = compositeField;
        this.enumeratedField = enumeratedField;
        this.listField = listField;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload();
    }

    /**
     * Returns the field booleanField.
     * 
     * @return The field booleanField
     */
    public Boolean getBooleanField() {
        return booleanField;
    }

    /**
     * Returns the field integerField.
     * 
     * @return The field integerField
     */
    public Integer getIntegerField() {
        return integerField;
    }

    /**
     * Returns the field stringField.
     * 
     * @return The field stringField
     */
    public String getStringField() {
        return stringField;
    }

    /**
     * Returns the field compositeField.
     * 
     * @return The field compositeField
     */
    public org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite getCompositeField() {
        return compositeField;
    }

    /**
     * Returns the field enumeratedField.
     * 
     * @return The field enumeratedField
     */
    public org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject getEnumeratedField() {
        return enumeratedField;
    }

    /**
     * Returns the field listField.
     * 
     * @return The field listField
     */
    public org.ccsds.moims.mo.mal.structures.IntegerList getListField() {
        return listField;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestObjectPayload) {
            TestObjectPayload other = (TestObjectPayload) obj;
            if (booleanField == null) {
                if (other.booleanField != null) {
                    return false;
                }
            } else {
                if (! booleanField.equals(other.booleanField)) {
                    return false;
                }
            }
            if (integerField == null) {
                if (other.integerField != null) {
                    return false;
                }
            } else {
                if (! integerField.equals(other.integerField)) {
                    return false;
                }
            }
            if (stringField == null) {
                if (other.stringField != null) {
                    return false;
                }
            } else {
                if (! stringField.equals(other.stringField)) {
                    return false;
                }
            }
            if (compositeField == null) {
                if (other.compositeField != null) {
                    return false;
                }
            } else {
                if (! compositeField.equals(other.compositeField)) {
                    return false;
                }
            }
            if (enumeratedField == null) {
                if (other.enumeratedField != null) {
                    return false;
                }
            } else {
                if (! enumeratedField.equals(other.enumeratedField)) {
                    return false;
                }
            }
            if (listField == null) {
                if (other.listField != null) {
                    return false;
                }
            } else {
                if (! listField.equals(other.listField)) {
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
        hash = 83 * hash + (booleanField != null ? booleanField.hashCode() : 0);
        hash = 83 * hash + (integerField != null ? integerField.hashCode() : 0);
        hash = 83 * hash + (stringField != null ? stringField.hashCode() : 0);
        hash = 83 * hash + (compositeField != null ? compositeField.hashCode() : 0);
        hash = 83 * hash + (enumeratedField != null ? enumeratedField.hashCode() : 0);
        hash = 83 * hash + (listField != null ? listField.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestObjectPayload: ");
        buf.append("booleanField=").append(booleanField);
        buf.append(", integerField=").append(integerField);
        buf.append(", stringField=").append(stringField);
        buf.append(", compositeField=").append(compositeField);
        buf.append(", enumeratedField=").append(enumeratedField);
        buf.append(", listField=").append(listField);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableBoolean(booleanField);
        encoder.encodeNullableInteger(integerField);
        encoder.encodeNullableString(stringField);
        encoder.encodeNullableElement(compositeField);
        encoder.encodeNullableElement(enumeratedField);
        encoder.encodeNullableElement(listField);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        booleanField = decoder.decodeNullableBoolean();
        integerField = decoder.decodeNullableInteger();
        stringField = decoder.decodeNullableString();
        compositeField = (org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite) decoder.decodeNullableElement(new org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite());
        enumeratedField = (org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject) decoder.decodeNullableElement(org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject.OBJECT1);
        listField = (org.ccsds.moims.mo.mal.structures.IntegerList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.IntegerList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
