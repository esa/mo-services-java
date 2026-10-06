package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * A concrete composite example extending an Abstract composite with no field.
 */
public final class BasicAbstractComposite extends AbstractComposite {

    private static final long serialVersionUID = 28147497687843162L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687843162L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

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
    public Element createElement() {
        return new BasicAbstractComposite();
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
