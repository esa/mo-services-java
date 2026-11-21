package org.ccsds.moims.mo.com.archive.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;

/**
 * List class for ExpressionOperator.
 */
public final class ExpressionOperatorList extends ArrayList<ExpressionOperator> implements HomogeneousList<ExpressionOperator> {

    private static final long serialVersionUID = 562958576910331L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958576910331L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for ExpressionOperatorList.
     * 
     */
    public ExpressionOperatorList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public ExpressionOperatorList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public ExpressionOperatorList(ArrayList<ExpressionOperator> elementList) {
        for(ExpressionOperator element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(ExpressionOperator element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new ExpressionOperatorList();
    }

    @Override
    public Element createTypedElement() {
        return ExpressionOperator.EQUAL;
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeHomogeneousList(this);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        decoder.decodeHomogeneousList(this);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
