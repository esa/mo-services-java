package org.ccsds.moims.mo.mal.structures;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * List class for File.
 */
public final class FileList extends ArrayList<File> implements HomogeneousList<File> {

    private static final long serialVersionUID = 281475043818513L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 281475043818513L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for FileList.
     * 
     */
    public FileList() {
    }

    /**
     * Constructor that initialises the capacity of the list.
     * 
     * @param initialCapacity The required initial capacity.
     */
    public FileList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructor that uses an ArrayList for initialization.
     * 
     * @param elementList The ArrayList that is used for initialization.
     */
    public FileList(ArrayList<File> elementList) {
        for(File element : elementList) {
            this.add(element);
        }
    }

    @Override
    public boolean add(File element) {
        if (element == null) {
            throw new IllegalArgumentException("The added argument cannot be null!");
        }
        return super.add(element);
    }

    @Override
    public Element createElement() {
        return new FileList();
    }

    @Override
    public Element createTypedElement() {
        return new File();
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
