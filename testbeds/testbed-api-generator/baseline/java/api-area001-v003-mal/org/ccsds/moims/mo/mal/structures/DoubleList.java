package org.ccsds.moims.mo.mal.structures;

/**
 * List class for Double.
 */
public final class DoubleList extends java.util.ArrayList<Double> implements org.ccsds.moims.mo.mal.structures.HomogeneousList<Double> {

    private static final long serialVersionUID = 281475043819515L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043819515L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for DoubleList.
     * 
     */
    public DoubleList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public DoubleList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public DoubleList(java.util.ArrayList<Double> elementList) {
        for(Double element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(Double element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new DoubleList();
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
