package org.ccsds.moims.mo.malprototype.structures;

/**
 * A concrete composite example extending an Abstract composite with no field.
 */
public final class BasicAbstractComposite extends org.ccsds.moims.mo.malprototype.structures.AbstractComposite {

    private static final long serialVersionUID = 28147497687843162L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687843162L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for BasicAbstractComposite.
     * 
     */
    public BasicAbstractComposite() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param firstItem Example String item.
     * @param secondItem Example Integer item.
     */
    public BasicAbstractComposite(String firstItem,
            Integer secondItem) {
        super(firstItem,
            secondItem);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.BasicAbstractComposite();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BasicAbstractComposite) {
            if (! super.equals(obj)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(BasicAbstractComposite: ");
        buf.append(super.toString());
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
