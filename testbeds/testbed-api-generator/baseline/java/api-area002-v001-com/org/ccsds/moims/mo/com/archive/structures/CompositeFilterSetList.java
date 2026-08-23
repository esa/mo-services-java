package org.ccsds.moims.mo.com.archive.structures;

/**
 * List class for CompositeFilterSet.
 */
public final class CompositeFilterSetList extends java.util.ArrayList<org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet> implements org.ccsds.moims.mo.mal.structures.HomogeneousList<org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet> {

    private static final long serialVersionUID = 562958576910332L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958576910332L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for CompositeFilterSetList.
     * 
     */
    public CompositeFilterSetList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public CompositeFilterSetList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public CompositeFilterSetList(java.util.ArrayList<org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet> elementList) {
        for(org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new CompositeFilterSetList();
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createTypedElement() {
        return new org.ccsds.moims.mo.com.archive.structures.CompositeFilterSet();
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
