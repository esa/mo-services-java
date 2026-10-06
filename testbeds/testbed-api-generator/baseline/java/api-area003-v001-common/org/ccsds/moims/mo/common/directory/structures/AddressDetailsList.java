package org.ccsds.moims.mo.common.directory.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;

/**
 * List class for AddressDetails.
 */
public final class AddressDetailsList extends ArrayList<AddressDetails> implements HomogeneousList<AddressDetails> {

    private static final long serialVersionUID = 844429258653692L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844429258653692L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for AddressDetailsList.
     * 
     */
    public AddressDetailsList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public AddressDetailsList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public AddressDetailsList(ArrayList<AddressDetails> elementList) {
        for(AddressDetails element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(AddressDetails element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new AddressDetailsList();
    }

    @Override
    public Element createTypedElement() {
        return new AddressDetails();
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
