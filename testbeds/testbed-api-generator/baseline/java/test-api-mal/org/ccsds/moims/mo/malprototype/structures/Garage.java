package org.ccsds.moims.mo.malprototype.structures;

/**
 * The object representing a Garage with multiple references and lists of
 * references to cars.
 */
public final class Garage extends org.ccsds.moims.mo.mal.structures.MOObject {

    private static final long serialVersionUID = 28147497687842938L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842938L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The Porsche courtesy car offered by the garage.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Porsche> courtesyCarAsPorsche;

    /**
     * The courtesy car offered by the garage.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> courtesyCarAsAuto;

    /**
     * The courtesy car offered by the garage.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> courtesyCarAsObject;

    /**
     * The list of Porsche cars parked in the garage.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList carsAsPorsches;

    /**
     * The list of cars parked in the garage.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList carsAsAutos;

    /**
     * The list of cars parked in the garage.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList carsAsObjects;

    /**
     * Default constructor for Garage.
     * 
     */
    public Garage() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     * @param courtesyCarAsPorsche The Porsche courtesy car offered by the garage.
     * @param courtesyCarAsAuto The courtesy car offered by the garage.
     * @param courtesyCarAsObject The courtesy car offered by the garage.
     * @param carsAsPorsches The list of Porsche cars parked in the garage.
     * @param carsAsAutos The list of cars parked in the garage.
     * @param carsAsObjects The list of cars parked in the garage.
     */
    public Garage(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Porsche> courtesyCarAsPorsche,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> courtesyCarAsAuto,
            org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> courtesyCarAsObject,
            org.ccsds.moims.mo.mal.structures.ObjectRefList carsAsPorsches,
            org.ccsds.moims.mo.mal.structures.ObjectRefList carsAsAutos,
            org.ccsds.moims.mo.mal.structures.ObjectRefList carsAsObjects) {
        super(objectIdentity);
        this.courtesyCarAsPorsche = courtesyCarAsPorsche;
        this.courtesyCarAsAuto = courtesyCarAsAuto;
        this.courtesyCarAsObject = courtesyCarAsObject;
        this.carsAsPorsches = carsAsPorsches;
        this.carsAsAutos = carsAsAutos;
        this.carsAsObjects = carsAsObjects;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param objectIdentity The identity of the MO Object.
     */
    public Garage(org.ccsds.moims.mo.mal.structures.ObjectIdentity objectIdentity) {
        super(objectIdentity);
        this.courtesyCarAsPorsche = null;
        this.courtesyCarAsAuto = null;
        this.courtesyCarAsObject = null;
        this.carsAsPorsches = null;
        this.carsAsAutos = null;
        this.carsAsObjects = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.Garage();
    }

    /**
     * Returns the field courtesyCarAsPorsche.
     * 
     * @return The field courtesyCarAsPorsche
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Porsche> getCourtesyCarAsPorsche() {
        return courtesyCarAsPorsche;
    }

    /**
     * Returns the field courtesyCarAsAuto.
     * 
     * @return The field courtesyCarAsAuto
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto> getCourtesyCarAsAuto() {
        return courtesyCarAsAuto;
    }

    /**
     * Returns the field courtesyCarAsObject.
     * 
     * @return The field courtesyCarAsObject
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> getCourtesyCarAsObject() {
        return courtesyCarAsObject;
    }

    /**
     * Returns the field carsAsPorsches.
     * 
     * @return The field carsAsPorsches
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getCarsAsPorsches() {
        return carsAsPorsches;
    }

    /**
     * Returns the field carsAsAutos.
     * 
     * @return The field carsAsAutos
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getCarsAsAutos() {
        return carsAsAutos;
    }

    /**
     * Returns the field carsAsObjects.
     * 
     * @return The field carsAsObjects
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getCarsAsObjects() {
        return carsAsObjects;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Garage) {
            if (! super.equals(obj)) {
                return false;
            }
            Garage other = (Garage) obj;
            if (courtesyCarAsPorsche == null) {
                if (other.courtesyCarAsPorsche != null) {
                    return false;
                }
            } else {
                if (! courtesyCarAsPorsche.equals(other.courtesyCarAsPorsche)) {
                    return false;
                }
            }
            if (courtesyCarAsAuto == null) {
                if (other.courtesyCarAsAuto != null) {
                    return false;
                }
            } else {
                if (! courtesyCarAsAuto.equals(other.courtesyCarAsAuto)) {
                    return false;
                }
            }
            if (courtesyCarAsObject == null) {
                if (other.courtesyCarAsObject != null) {
                    return false;
                }
            } else {
                if (! courtesyCarAsObject.equals(other.courtesyCarAsObject)) {
                    return false;
                }
            }
            if (carsAsPorsches == null) {
                if (other.carsAsPorsches != null) {
                    return false;
                }
            } else {
                if (! carsAsPorsches.equals(other.carsAsPorsches)) {
                    return false;
                }
            }
            if (carsAsAutos == null) {
                if (other.carsAsAutos != null) {
                    return false;
                }
            } else {
                if (! carsAsAutos.equals(other.carsAsAutos)) {
                    return false;
                }
            }
            if (carsAsObjects == null) {
                if (other.carsAsObjects != null) {
                    return false;
                }
            } else {
                if (! carsAsObjects.equals(other.carsAsObjects)) {
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
        hash = 83 * hash + (courtesyCarAsPorsche != null ? courtesyCarAsPorsche.hashCode() : 0);
        hash = 83 * hash + (courtesyCarAsAuto != null ? courtesyCarAsAuto.hashCode() : 0);
        hash = 83 * hash + (courtesyCarAsObject != null ? courtesyCarAsObject.hashCode() : 0);
        hash = 83 * hash + (carsAsPorsches != null ? carsAsPorsches.hashCode() : 0);
        hash = 83 * hash + (carsAsAutos != null ? carsAsAutos.hashCode() : 0);
        hash = 83 * hash + (carsAsObjects != null ? carsAsObjects.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Garage: ");
        buf.append(super.toString());
        buf.append(", courtesyCarAsPorsche=").append(courtesyCarAsPorsche);
        buf.append(", courtesyCarAsAuto=").append(courtesyCarAsAuto);
        buf.append(", courtesyCarAsObject=").append(courtesyCarAsObject);
        buf.append(", carsAsPorsches=").append(carsAsPorsches);
        buf.append(", carsAsAutos=").append(carsAsAutos);
        buf.append(", carsAsObjects=").append(carsAsObjects);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableElement(courtesyCarAsPorsche);
        encoder.encodeNullableElement(courtesyCarAsAuto);
        encoder.encodeNullableElement(courtesyCarAsObject);
        encoder.encodeNullableElement(carsAsPorsches);
        encoder.encodeNullableElement(carsAsAutos);
        encoder.encodeNullableElement(carsAsObjects);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        courtesyCarAsPorsche = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Porsche>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Porsche>());
        courtesyCarAsAuto = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>());
        courtesyCarAsObject = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element>) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element>());
        carsAsPorsches = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        carsAsAutos = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        carsAsObjects = (org.ccsds.moims.mo.mal.structures.ObjectRefList) decoder.decodeNullableElement(new org.ccsds.moims.mo.mal.structures.ObjectRefList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
