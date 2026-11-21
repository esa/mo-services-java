package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.MOObject;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;

/**
 * The object representing a Garage with multiple references and lists of
 * references to cars.
 */
public final class Garage extends MOObject {

    private static final long serialVersionUID = 28147497687842938L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842938L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The Porsche courtesy car offered by the garage.
     */
    private ObjectRef<Porsche> courtesyCarAsPorsche;

    /**
     * The courtesy car offered by the garage.
     */
    private ObjectRef<Auto> courtesyCarAsAuto;

    /**
     * The courtesy car offered by the garage.
     */
    private ObjectRef<Element> courtesyCarAsObject;

    /**
     * The list of Porsche cars parked in the garage.
     */
    private ObjectRefList carsAsPorsches;

    /**
     * The list of cars parked in the garage.
     */
    private ObjectRefList carsAsAutos;

    /**
     * The list of cars parked in the garage.
     */
    private ObjectRefList carsAsObjects;

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
    public Garage(ObjectIdentity objectIdentity,
            ObjectRef<Porsche> courtesyCarAsPorsche,
            ObjectRef<Auto> courtesyCarAsAuto,
            ObjectRef<Element> courtesyCarAsObject,
            ObjectRefList carsAsPorsches,
            ObjectRefList carsAsAutos,
            ObjectRefList carsAsObjects) {
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
    public Garage(ObjectIdentity objectIdentity) {
        super(objectIdentity);
        this.courtesyCarAsPorsche = null;
        this.courtesyCarAsAuto = null;
        this.courtesyCarAsObject = null;
        this.carsAsPorsches = null;
        this.carsAsAutos = null;
        this.carsAsObjects = null;
    }

    @Override
    public Element createElement() {
        return new Garage();
    }

    /**
     * Returns the field courtesyCarAsPorsche.
     * 
     * @return The field courtesyCarAsPorsche
     */
    public ObjectRef<Porsche> getCourtesyCarAsPorsche() {
        return courtesyCarAsPorsche;
    }

    /**
     * Returns the field courtesyCarAsAuto.
     * 
     * @return The field courtesyCarAsAuto
     */
    public ObjectRef<Auto> getCourtesyCarAsAuto() {
        return courtesyCarAsAuto;
    }

    /**
     * Returns the field courtesyCarAsObject.
     * 
     * @return The field courtesyCarAsObject
     */
    public ObjectRef<Element> getCourtesyCarAsObject() {
        return courtesyCarAsObject;
    }

    /**
     * Returns the field carsAsPorsches.
     * 
     * @return The field carsAsPorsches
     */
    public ObjectRefList getCarsAsPorsches() {
        return carsAsPorsches;
    }

    /**
     * Returns the field carsAsAutos.
     * 
     * @return The field carsAsAutos
     */
    public ObjectRefList getCarsAsAutos() {
        return carsAsAutos;
    }

    /**
     * Returns the field carsAsObjects.
     * 
     * @return The field carsAsObjects
     */
    public ObjectRefList getCarsAsObjects() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableElement(courtesyCarAsPorsche);
        encoder.encodeNullableElement(courtesyCarAsAuto);
        encoder.encodeNullableElement(courtesyCarAsObject);
        encoder.encodeNullableElement(carsAsPorsches);
        encoder.encodeNullableElement(carsAsAutos);
        encoder.encodeNullableElement(carsAsObjects);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        courtesyCarAsPorsche = (ObjectRef<Porsche>) decoder.decodeNullableElement(new ObjectRef<Porsche>());
        courtesyCarAsAuto = (ObjectRef<Auto>) decoder.decodeNullableElement(new ObjectRef<Auto>());
        courtesyCarAsObject = (ObjectRef<Element>) decoder.decodeNullableElement(new ObjectRef<Element>());
        carsAsPorsches = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        carsAsAutos = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        carsAsObjects = (ObjectRefList) decoder.decodeNullableElement(new ObjectRefList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
