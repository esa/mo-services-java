package org.ccsds.moims.mo.mal.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * List class for URI.
 */
public final class URIList extends ArrayList<URI> implements HomogeneousList<URI> {

    private static final long serialVersionUID = 281475043819502L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043819502L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for URIList.
     * 
     */
    public URIList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public URIList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public URIList(ArrayList<URI> elementList) {
        for(URI element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(URI element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new URIList();
    }

    @Override
    public Element createTypedElement() {
        return new URI();
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
