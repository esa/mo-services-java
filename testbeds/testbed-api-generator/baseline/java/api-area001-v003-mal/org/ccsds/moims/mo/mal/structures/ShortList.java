package org.ccsds.moims.mo.mal.structures;

/**
 * List class for Short.
 */
public final class ShortList extends java.util.ArrayList<Short> implements org.ccsds.moims.mo.mal.structures.HomogeneousList<Short> {

    private static final long serialVersionUID = 281475043819511L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043819511L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for ShortList.
     * 
     */
    public ShortList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public ShortList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public ShortList(java.util.ArrayList<Short> elementList) {
        for(Short element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(Short element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new ShortList();
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createTypedElement() {
        org.ccsds.moims.mo.mal.TypeId typeId = this.getTypeId();
        return new Union(typeId.generateTypeIdPositive());
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeHomogeneousList(this);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        decoder.decodeHomogeneousList(this);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
