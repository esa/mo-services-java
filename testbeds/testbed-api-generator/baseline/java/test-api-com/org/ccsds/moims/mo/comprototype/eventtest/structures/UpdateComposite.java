package org.ccsds.moims.mo.comprototype.eventtest.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UOctet;

/**
 * Encapsulates a set of fields that can be updated on a test object.
.
 */
public final class UpdateComposite implements Composite {

    private static final long serialVersionUID = 56295003948843020L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003948843020L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Unsigned octet value.
.
     */
    private UOctet UOctetField;

    /**
     * octet value.
.
     */
    private Byte OctetField;

    /**
     * double value.
.
     */
    private Double DoubleField;

    /**
     * Default constructor for UpdateComposite.
     * 
     */
    public UpdateComposite() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param UOctetField Unsigned octet value.

     * @param OctetField octet value.

     * @param DoubleField double value.

     */
    public UpdateComposite(UOctet UOctetField,
            Byte OctetField,
            Double DoubleField) {
        this.UOctetField = UOctetField;
        this.OctetField = OctetField;
        this.DoubleField = DoubleField;
    }

    @Override
    public Element createElement() {
        return new UpdateComposite();
    }

    /**
     * Returns the field UOctetField.
     * 
     * @return The field UOctetField
     */
    public UOctet getUOctetField() {
        return UOctetField;
    }

    /**
     * Returns the field OctetField.
     * 
     * @return The field OctetField
     */
    public Byte getOctetField() {
        return OctetField;
    }

    /**
     * Returns the field DoubleField.
     * 
     * @return The field DoubleField
     */
    public Double getDoubleField() {
        return DoubleField;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UpdateComposite) {
            UpdateComposite other = (UpdateComposite) obj;
            if (UOctetField == null) {
                if (other.UOctetField != null) {
                    return false;
                }
            } else {
                if (! UOctetField.equals(other.UOctetField)) {
                    return false;
                }
            }
            if (OctetField == null) {
                if (other.OctetField != null) {
                    return false;
                }
            } else {
                if (! OctetField.equals(other.OctetField)) {
                    return false;
                }
            }
            if (DoubleField == null) {
                if (other.DoubleField != null) {
                    return false;
                }
            } else {
                if (! DoubleField.equals(other.DoubleField)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (UOctetField != null ? UOctetField.hashCode() : 0);
        hash = 83 * hash + (OctetField != null ? OctetField.hashCode() : 0);
        hash = 83 * hash + (DoubleField != null ? DoubleField.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(UpdateComposite: ");
        buf.append("UOctetField=").append(UOctetField);
        buf.append(", OctetField=").append(OctetField);
        buf.append(", DoubleField=").append(DoubleField);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (UOctetField == null) {
            throw new MALException("The field 'UOctetField' cannot be null!");
        }
        if (OctetField == null) {
            throw new MALException("The field 'OctetField' cannot be null!");
        }
        if (DoubleField == null) {
            throw new MALException("The field 'DoubleField' cannot be null!");
        }
        encoder.encodeUOctet(UOctetField);
        encoder.encodeOctet(OctetField);
        encoder.encodeDouble(DoubleField);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        UOctetField = decoder.decodeUOctet();
        OctetField = decoder.decodeOctet();
        DoubleField = decoder.decodeDouble();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
