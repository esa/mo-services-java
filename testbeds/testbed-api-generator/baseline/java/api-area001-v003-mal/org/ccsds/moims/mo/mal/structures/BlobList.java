package org.ccsds.moims.mo.mal.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * List class for Blob.
 */
public final class BlobList extends ArrayList<Blob> implements HomogeneousList<Blob> {

    private static final long serialVersionUID = 281475043819519L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043819519L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for BlobList.
     * 
     */
    public BlobList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public BlobList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public BlobList(ArrayList<Blob> elementList) {
        for(Blob element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(Blob element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new BlobList();
    }

    @Override
    public Element createTypedElement() {
        return new Blob();
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
