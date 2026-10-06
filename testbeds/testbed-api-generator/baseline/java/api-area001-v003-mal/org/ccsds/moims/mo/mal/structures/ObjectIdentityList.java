package org.ccsds.moims.mo.mal.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * List class for ObjectIdentity.
 */
public final class ObjectIdentityList extends ArrayList<ObjectIdentity> implements HomogeneousList<ObjectIdentity> {

    private static final long serialVersionUID = 281475043818512L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043818512L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for ObjectIdentityList.
     * 
     */
    public ObjectIdentityList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public ObjectIdentityList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public ObjectIdentityList(ArrayList<ObjectIdentity> elementList) {
        for(ObjectIdentity element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(ObjectIdentity element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new ObjectIdentityList();
    }

    @Override
    public Element createTypedElement() {
        return new ObjectIdentity();
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
