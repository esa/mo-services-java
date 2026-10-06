package org.ccsds.moims.mo.comprototype.eventtest.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;

/**
 * List class for ObjectDeletion.
 */
public final class ObjectDeletionList extends ArrayList<ObjectDeletion> implements HomogeneousList<ObjectDeletion> {

    private static final long serialVersionUID = 56295003965620222L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003965620222L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for ObjectDeletionList.
     * 
     */
    public ObjectDeletionList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public ObjectDeletionList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public ObjectDeletionList(ArrayList<ObjectDeletion> elementList) {
        for(ObjectDeletion element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(ObjectDeletion element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new ObjectDeletionList();
    }

    @Override
    public Element createTypedElement() {
        return new ObjectDeletion();
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
