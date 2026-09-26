package org.ccsds.moims.mo.malprototype.structures;

/**
 * The object representing a Lamborghini car.
 */
public final class Lamborghini extends org.ccsds.moims.mo.malprototype.structures.Auto {

    private static final long serialVersionUID = 28147497687842936L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842936L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for Lamborghini.
     * 
     */
    public Lamborghini() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param engine The engine of the car.
     * @param chassis The chassis of the car.
     * @param windows The windows of the car.
     */
    public Lamborghini(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String engine,
            String chassis,
            org.ccsds.moims.mo.mal.structures.StringList windows) {
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
    public Lamborghini(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity) {
        super(objectIdentity);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.Lamborghini();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Lamborghini) {
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
        buf.append("(Lamborghini: ");
        buf.append(super.toString());
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
