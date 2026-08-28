package org.ccsds.moims.mo.mal.structures;

/**
 * List class for Long.
 */
public final class LongList extends java.util.ArrayList<Long> implements org.ccsds.moims.mo.mal.structures.HomogeneousList<Long> {

    private static final long serialVersionUID = 281475043819507L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043819507L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for LongList.
     * 
     */
    public LongList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public LongList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public LongList(java.util.ArrayList<Long> elementList) {
        for(Long element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(Long element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new LongList();
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
