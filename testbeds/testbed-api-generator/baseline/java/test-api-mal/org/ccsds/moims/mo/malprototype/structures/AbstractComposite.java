package org.ccsds.moims.mo.malprototype.structures;

/**
 * An abstract composite example. This type and all derived types are notably
 * used in the Polymorphic types test procedure.
 */
public abstract class AbstractComposite implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * Example String item.
     */
    private String firstItem;

    /**
     * Example Integer item.
     */
    private Integer secondItem;

    /**
     * Default constructor for AbstractComposite.
     * 
     */
    public AbstractComposite() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param firstItem Example String item.
     * @param secondItem Example Integer item.
     */
    public AbstractComposite(String firstItem,
            Integer secondItem) {
        this.firstItem = firstItem;
        this.secondItem = secondItem;
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
        if (obj instanceof AbstractComposite) {
            AbstractComposite other = (AbstractComposite) obj;
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
        buf.append("(AbstractComposite: ");
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

}
