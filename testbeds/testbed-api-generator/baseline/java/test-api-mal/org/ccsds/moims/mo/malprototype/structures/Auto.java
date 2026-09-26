package org.ccsds.moims.mo.malprototype.structures;

/**
 * An abstract object type for cars. This type and all derived types are notably
 * used in the Polymorphic types test procedure related to ObjectRef.
 */
public abstract class Auto extends org.ccsds.moims.mo.mal.structures.MOObject {

    /**
     * The engine of the car.
     */
    private String engine;

    /**
     * The chassis of the car.
     */
    private String chassis;

    /**
     * The windows of the car.
     */
    private org.ccsds.moims.mo.mal.structures.StringList windows;

    /**
     * Default constructor for Auto.
     * 
     */
    public Auto() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param engine The engine of the car.
     * @param chassis The chassis of the car.
     * @param windows The windows of the car.
     */
    public Auto(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            String engine,
            String chassis,
            org.ccsds.moims.mo.mal.structures.StringList windows) {
        super(objectIdentity);
        this.engine = engine;
        this.chassis = chassis;
        this.windows = windows;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     */
    public Auto(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity) {
        super(objectIdentity);
        this.engine = null;
        this.chassis = null;
        this.windows = null;
    }

    /**
     * Returns the field engine.
     * 
     * @return The field engine
     */
    public String getEngine() {
        return engine;
    }

    /**
     * Returns the field chassis.
     * 
     * @return The field chassis
     */
    public String getChassis() {
        return chassis;
    }

    /**
     * Returns the field windows.
     * 
     * @return The field windows
     */
    public org.ccsds.moims.mo.mal.structures.StringList getWindows() {
        return windows;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Auto) {
            if (! super.equals(obj)) {
                return false;
            }
            Auto other = (Auto) obj;
            if (engine == null) {
                if (other.engine != null) {
                    return false;
                }
            } else {
                if (! engine.equals(other.engine)) {
                    return false;
                }
            }
            if (chassis == null) {
                if (other.chassis != null) {
                    return false;
                }
            } else {
                if (! chassis.equals(other.chassis)) {
                    return false;
                }
            }
            if (windows == null) {
                if (other.windows != null) {
                    return false;
                }
            } else {
                if (! windows.equals(other.windows)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (engine != null ? engine.hashCode() : 0);
        hash = 83 * hash + (chassis != null ? chassis.hashCode() : 0);
        hash = 83 * hash + (windows != null ? windows.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Auto: ");
        buf.append(super.toString());
        buf.append(", engine=").append(engine);
        buf.append(", chassis=").append(chassis);
        buf.append(", windows=").append(windows);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableString(engine);
        encoder.encodeNullableString(chassis);
        encoder.encodeNullableElement(windows);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        engine = decoder.decodeNullableString();
        chassis = decoder.decodeNullableString();
        windows = (org.ccsds.moims.mo.mal.structures.StringList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.StringList());
        return this;
    }

}
