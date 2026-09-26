package org.ccsds.moims.mo.malprototype.structures;

/**
 * A concrete composite example extending an Abstract composite with a field
 * which is itself an Abstract composite, allowing for defining complex instances.
 */
public final class StructureWithAbstractField extends org.ccsds.moims.mo.malprototype.structures.AbstractComposite {

    private static final long serialVersionUID = 28147497687843163L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687843163L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Contained abstract structure.
     */
    private org.ccsds.moims.mo.malprototype.structures.AbstractComposite abstract_item;

    /**
     * Extra Boolean item.
     */
    private Boolean third_item;

    /**
     * Example Integer item.
     */
    private Integer fourth_item;

    /**
     * Default constructor for StructureWithAbstractField.
     * 
     */
    public StructureWithAbstractField() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param firstItem Example String item.
     * @param secondItem Example Integer item.
     * @param abstract_item Contained abstract structure.
     * @param third_item Extra Boolean item.
     * @param fourth_item Example Integer item.
     */
    public StructureWithAbstractField(String firstItem,
            Integer secondItem,
            org.ccsds.moims.mo.malprototype.structures.AbstractComposite abstract_item,
            Boolean third_item,
            Integer fourth_item) {
        super(firstItem,
            secondItem);
        this.abstract_item = abstract_item;
        this.third_item = third_item;
        this.fourth_item = fourth_item;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractField();
    }

    /**
     * Returns the field abstract_item.
     * 
     * @return The field abstract_item
     */
    public org.ccsds.moims.mo.malprototype.structures.AbstractComposite getAbstract_item() {
        return abstract_item;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StructureWithAbstractField) {
            if (! super.equals(obj)) {
                return false;
            }
            StructureWithAbstractField other = (StructureWithAbstractField) obj;
            if (abstract_item == null) {
                if (other.abstract_item != null) {
                    return false;
                }
            } else {
                if (! abstract_item.equals(other.abstract_item)) {
                    return false;
                }
            }
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
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (abstract_item != null ? abstract_item.hashCode() : 0);
        hash = 83 * hash + (third_item != null ? third_item.hashCode() : 0);
        hash = 83 * hash + (fourth_item != null ? fourth_item.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StructureWithAbstractField: ");
        buf.append(super.toString());
        buf.append(", abstract_item=").append(abstract_item);
        buf.append(", third_item=").append(third_item);
        buf.append(", fourth_item=").append(fourth_item);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableAbstractElement(abstract_item);
        encoder.encodeNullableBoolean(third_item);
        encoder.encodeNullableInteger(fourth_item);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        abstract_item = (org.ccsds.moims.mo.malprototype.structures.AbstractComposite) decoder.decodeNullableAbstractElement();
        third_item = decoder.decodeNullableBoolean();
        fourth_item = decoder.decodeNullableInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
