package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.StringList;

/**
 * The object representing a Porsche car.
 */
public final class Porsche extends Auto {

    private static final long serialVersionUID = 28147497687842937L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842937L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Default constructor for Porsche.
     * 
     */
    public Porsche() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param engine The engine of the car.
     * @param chassis The chassis of the car.
     * @param windows The windows of the car.
     */
    public Porsche(ObjectIdentity objectIdentity,
            String engine,
            String chassis,
            StringList windows) {
        super(objectIdentity,
            engine,
            chassis,
            windows);
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     */
    public Porsche(ObjectIdentity objectIdentity) {
        super(objectIdentity);
    }

    @Override
    public Element createElement() {
        return new Porsche();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Porsche) {
            if (! super.equals(obj)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Porsche: ");
        buf.append(super.toString());
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
