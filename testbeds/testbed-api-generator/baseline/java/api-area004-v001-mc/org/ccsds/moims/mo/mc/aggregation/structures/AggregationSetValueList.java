package org.ccsds.moims.mo.mc.aggregation.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.HomogeneousList;

/**
 * List class for AggregationSetValue.
 */
public final class AggregationSetValueList extends ArrayList<AggregationSetValue> implements HomogeneousList<AggregationSetValue> {

    private static final long serialVersionUID = 1125925710200828L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925710200828L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for AggregationSetValueList.
     * 
     */
    public AggregationSetValueList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public AggregationSetValueList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public AggregationSetValueList(ArrayList<AggregationSetValue> elementList) {
        for(AggregationSetValue element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(AggregationSetValue element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new AggregationSetValueList();
    }

    @Override
    public Element createTypedElement() {
        return new AggregationSetValue();
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
