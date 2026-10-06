package org.ccsds.moims.mo.malprototype.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;

/**
 * List class for TestBody.
 */
public final class TestBodyList extends ArrayList<TestBody> implements HomogeneousList<TestBody> {

    private static final long serialVersionUID = 28147497704619909L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497704619909L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for TestBodyList.
     * 
     */
    public TestBodyList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public TestBodyList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public TestBodyList(ArrayList<TestBody> elementList) {
        for(TestBody element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(TestBody element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new TestBodyList();
    }

    @Override
    public Element createTypedElement() {
        return new TestBody();
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
