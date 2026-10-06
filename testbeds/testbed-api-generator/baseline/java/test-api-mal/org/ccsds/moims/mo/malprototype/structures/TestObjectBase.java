package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;

/**
 * An abstract MO Object structure.
 */
public abstract class TestObjectBase extends MOObject {

    /**
     * Example String item.
     */
    private String firstItem;

    /**
     * Example Integer item.
     */
    private Integer secondItem;

    /**
     * Default constructor for TestObjectBase.
     * 
     */
    public TestObjectBase() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param firstItem Example String item.
     * @param secondItem Example Integer item.
     */
    public TestObjectBase(ObjectIdentity objectIdentity,
            String firstItem,
            Integer secondItem) {
        super(objectIdentity);
        this.firstItem = firstItem;
        this.secondItem = secondItem;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     */
    public TestObjectBase(ObjectIdentity objectIdentity) {
        super(objectIdentity);
        this.firstItem = null;
        this.secondItem = null;
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
        if (obj instanceof TestObjectBase) {
            if (! super.equals(obj)) {
                return false;
            }
            TestObjectBase other = (TestObjectBase) obj;
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
        int hash = super.hashCode();
        hash = 83 * hash + (firstItem != null ? firstItem.hashCode() : 0);
        hash = 83 * hash + (secondItem != null ? secondItem.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestObjectBase: ");
        buf.append(super.toString());
        buf.append(", firstItem=").append(firstItem);
        buf.append(", secondItem=").append(secondItem);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableString(firstItem);
        encoder.encodeNullableInteger(secondItem);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        firstItem = decoder.decodeNullableString();
        secondItem = decoder.decodeNullableInteger();
        return this;
    }

}
