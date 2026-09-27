package org.ccsds.moims.mo.comprototype.eventtest.structures;

/**
 * List class for BasicEnum.
 */
public final class BasicEnumList extends java.util.ArrayList<org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum> implements org.ccsds.moims.mo.mal.structures.HomogeneousList<org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum> {

    private static final long serialVersionUID = 56295003965620211L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003965620211L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for BasicEnumList.
     * 
     */
    public BasicEnumList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public BasicEnumList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public BasicEnumList(java.util.ArrayList<org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum> elementList) {
        for(org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new BasicEnumList();
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createTypedElement() {
        return org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum.FIRST;
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
