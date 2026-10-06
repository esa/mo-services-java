package org.ccsds.moims.mo.comprototype.archivetest.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IntegerList;

/**
 * Object body to test the Archive service.
 */
public final class TestObjectPayload implements Composite {

    private static final long serialVersionUID = 56295021128712193L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295021128712193L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

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
    private SubComposite compositeField;

    /**
     * The enumeratedField field.
     */
    private EnumeratedObject enumeratedField;

    /**
     * The listField field.
     */
    private IntegerList listField;

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
            SubComposite compositeField,
            EnumeratedObject enumeratedField,
            IntegerList listField) {
        this.booleanField = booleanField;
        this.integerField = integerField;
        this.stringField = stringField;
        this.compositeField = compositeField;
        this.enumeratedField = enumeratedField;
        this.listField = listField;
    }

    @Override
    public Element createElement() {
        return new TestObjectPayload();
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
    public SubComposite getCompositeField() {
        return compositeField;
    }

    /**
     * Returns the field enumeratedField.
     * 
     * @return The field enumeratedField
     */
    public EnumeratedObject getEnumeratedField() {
        return enumeratedField;
    }

    /**
     * Returns the field listField.
     * 
     * @return The field listField
     */
    public IntegerList getListField() {
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableBoolean(booleanField);
        encoder.encodeNullableInteger(integerField);
        encoder.encodeNullableString(stringField);
        encoder.encodeNullableElement(compositeField);
        encoder.encodeNullableElement(enumeratedField);
        encoder.encodeNullableElement(listField);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        booleanField = decoder.decodeNullableBoolean();
        integerField = decoder.decodeNullableInteger();
        stringField = decoder.decodeNullableString();
        compositeField = (SubComposite) decoder.decodeNullableElement(new SubComposite());
        enumeratedField = (EnumeratedObject) decoder.decodeNullableElement(EnumeratedObject.OBJECT1);
        listField = (IntegerList) decoder.decodeNullableElement(new IntegerList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
