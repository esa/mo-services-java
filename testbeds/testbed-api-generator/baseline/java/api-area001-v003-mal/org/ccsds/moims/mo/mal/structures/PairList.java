package org.ccsds.moims.mo.mal.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * List class for Pair.
 */
public final class PairList extends ArrayList<Pair> implements HomogeneousList<Pair> {

    private static final long serialVersionUID = 281475043818515L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043818515L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for PairList.
     * 
     */
    public PairList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public PairList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public PairList(ArrayList<Pair> elementList) {
        for(Pair element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(Pair element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new PairList();
    }

    @Override
    public Element createTypedElement() {
        return new Pair();
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
