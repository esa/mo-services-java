package org.ccsds.moims.mo.malprototype.structures;

/**
 * An concrete composite example used to build complex composite structures.
 */
public final class TestBody implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842939L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842939L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Example String item.
     */
    private String firstItem;

    /**
     * Example Integer item.
     */
    private Integer secondItem;

    /**
     * Default constructor for TestBody.
     * 
     */
    public TestBody() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param firstItem Example String item.
     * @param secondItem Example Integer item.
     */
    public TestBody(String firstItem,
            Integer secondItem) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.TestBody();
    }

    /**
     * Returns the field firstItem.
     * 
     * @return The field firstItem
     */
    public String getFirstItem() {
        return firstItem;
    }

    /**
     * Returns the field secondItem.
     * 
     * @return The field secondItem
     */
    public Integer getSecondItem() {
        return secondItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestBody) {
            TestBody other = (TestBody) obj;
            if (firstItem == null) {
                if (other.firstItem != null) {
                    return false;
                }
            } else {
                if (! firstItem.equals(other.firstItem)) {
                    return false;
                }
            }
            if (secondItem == null) {
                if (other.secondItem != null) {
                    return false;
                }
            } else {
                if (! secondItem.equals(other.secondItem)) {
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
        hash = 83 * hash + (firstItem != null ? firstItem.hashCode() : 0);
        hash = 83 * hash + (secondItem != null ? secondItem.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestBody: ");
        buf.append("firstItem=").append(firstItem);
        buf.append(", secondItem=").append(secondItem);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableString(firstItem);
        encoder.encodeNullableInteger(secondItem);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        firstItem = decoder.decodeNullableString();
        secondItem = decoder.decodeNullableInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
