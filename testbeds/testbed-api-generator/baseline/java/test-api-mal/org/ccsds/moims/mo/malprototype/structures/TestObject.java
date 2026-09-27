package org.ccsds.moims.mo.malprototype.structures;

/**
 * A concrete MO Object structure which does not extend directly the MAL Object
 * type.
 */
public final class TestObject extends org.ccsds.moims.mo.malprototype.structures.TestObjectBase {

    private static final long serialVersionUID = 28147497687842836L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842836L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Another example String item.
     */
    private String thirdItem;

    /**
     * Example Boolean item.
     */
    private Boolean fourthItem;

    /**
     * Default constructor for TestObject.
     * 
     */
    public TestObject() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param firstItem Example String item.
     * @param secondItem Example Integer item.
     * @param thirdItem Another example String item.
     * @param fourthItem Example Boolean item.
     */
    public TestObject(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String firstItem,
            Integer secondItem,
            String thirdItem,
            Boolean fourthItem) {
        super(objectIdentity,
            firstItem,
            secondItem);
        this.thirdItem = thirdItem;
        this.fourthItem = fourthItem;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     */
    public TestObject(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity) {
        super(objectIdentity);
        this.thirdItem = null;
        this.fourthItem = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.TestObject();
    }

    /**
     * Returns the field thirdItem.
     * 
     * @return The field thirdItem
     */
    public String getThirdItem() {
        return thirdItem;
    }

    /**
     * Returns the field fourthItem.
     * 
     * @return The field fourthItem
     */
    public Boolean getFourthItem() {
        return fourthItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestObject) {
            if (! super.equals(obj)) {
                return false;
            }
            TestObject other = (TestObject) obj;
            if (thirdItem == null) {
                if (other.thirdItem != null) {
                    return false;
                }
            } else {
                if (! thirdItem.equals(other.thirdItem)) {
                    return false;
                }
            }
            if (fourthItem == null) {
                if (other.fourthItem != null) {
                    return false;
                }
            } else {
                if (! fourthItem.equals(other.fourthItem)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (thirdItem != null ? thirdItem.hashCode() : 0);
        hash = 83 * hash + (fourthItem != null ? fourthItem.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestObject: ");
        buf.append(super.toString());
        buf.append(", thirdItem=").append(thirdItem);
        buf.append(", fourthItem=").append(fourthItem);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableString(thirdItem);
        encoder.encodeNullableBoolean(fourthItem);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        thirdItem = decoder.decodeNullableString();
        fourthItem = decoder.decodeNullableBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
