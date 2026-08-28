package org.ccsds.moims.mo.mps.structures;

/**
 * List class for EllipsoidalPositionConstraint.
 */
public final class EllipsoidalPositionConstraintList extends java.util.ArrayList<org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraint> implements org.ccsds.moims.mo.mal.structures.HomogeneousList<org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraint> {

    private static final long serialVersionUID = 1407374917107672L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374917107672L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for EllipsoidalPositionConstraintList.
     * 
     */
    public EllipsoidalPositionConstraintList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public EllipsoidalPositionConstraintList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public EllipsoidalPositionConstraintList(java.util.ArrayList<org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraint> elementList) {
        for(org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraint element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraint element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new EllipsoidalPositionConstraintList();
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createTypedElement() {
        return new org.ccsds.moims.mo.mps.structures.EllipsoidalPositionConstraint();
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
