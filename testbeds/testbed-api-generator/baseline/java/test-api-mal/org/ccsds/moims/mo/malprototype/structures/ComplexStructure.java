package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * A concrete composite example extending an Abstract composite with some
 * common fields.
 */
public final class ComplexStructure extends AbstractComposite {

    private static final long serialVersionUID = 28147497687843161L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687843161L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Extra Boolean item.
     */
    private Boolean third_item;

    /**
     * Example Integer item.
     */
    private Integer fourth_item;

    /**
     * Contained structure.
     */
    private TestBody last_item;

    /**
     * Default constructor for ComplexStructure.
     * 
     */
    public ComplexStructure() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param firstItem Example String item.
     * @param secondItem Example Integer item.
     * @param third_item Extra Boolean item.
     * @param fourth_item Example Integer item.
     * @param last_item Contained structure.
     */
    public ComplexStructure(String firstItem,
            Integer secondItem,
            Boolean third_item,
            Integer fourth_item,
            TestBody last_item) {
        super(firstItem,
            secondItem);
        this.third_item = third_item;
        this.fourth_item = fourth_item;
        this.last_item = last_item;
    }

    @Override
    public Element createElement() {
        return new ComplexStructure();
    }

    /**
     * Returns the field third_item.
     * 
     * @return The field third_item
     */
    public Boolean getThird_item() {
        return third_item;
    }

    /**
     * Returns the field fourth_item.
     * 
     * @return The field fourth_item
     */
    public Integer getFourth_item() {
        return fourth_item;
    }

    /**
     * Returns the field last_item.
     * 
     * @return The field last_item
     */
    public TestBody getLast_item() {
        return last_item;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComplexStructure) {
            if (! super.equals(obj)) {
                return false;
            }
            ComplexStructure other = (ComplexStructure) obj;
            if (third_item == null) {
                if (other.third_item != null) {
                    return false;
                }
            } else {
                if (! third_item.equals(other.third_item)) {
                    return false;
                }
            }
            if (fourth_item == null) {
                if (other.fourth_item != null) {
                    return false;
                }
            } else {
                if (! fourth_item.equals(other.fourth_item)) {
                    return false;
                }
            }
            if (last_item == null) {
                if (other.last_item != null) {
                    return false;
                }
            } else {
                if (! last_item.equals(other.last_item)) {
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
        hash = 83 * hash + (third_item != null ? third_item.hashCode() : 0);
        hash = 83 * hash + (fourth_item != null ? fourth_item.hashCode() : 0);
        hash = 83 * hash + (last_item != null ? last_item.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ComplexStructure: ");
        buf.append(super.toString());
        buf.append(", third_item=").append(third_item);
        buf.append(", fourth_item=").append(fourth_item);
        buf.append(", last_item=").append(last_item);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableBoolean(third_item);
        encoder.encodeNullableInteger(fourth_item);
        encoder.encodeNullableElement(last_item);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        third_item = decoder.decodeNullableBoolean();
        fourth_item = decoder.decodeNullableInteger();
        last_item = (TestBody) decoder.decodeNullableElement(new TestBody());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
