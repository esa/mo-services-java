package org.ccsds.moims.mo.comprototype.eventtest.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ShortList;

/**
 * Holds object update details.
.
 */
public final class ObjectUpdate implements Composite {

    private static final long serialVersionUID = 56295003948843011L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003948843011L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Optional field - holds enum value.
.
     */
    private BasicEnum EnumField;

    /**
     * Optional field - holds duration value.
.
     */
    private Duration DurationField;

    /**
     * Optional field - holds one or more numeric (short values).
.
     */
    private ShortList NumericListField;

    /**
     * Optional field - holds a composite containing a number of discrete value.
     * .
     */
    private UpdateComposite CompositeField;

    /**
     * Default constructor for ObjectUpdate.
     * 
     */
    public ObjectUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param EnumField Optional field - holds enum value.

     * @param DurationField Optional field - holds duration value.

     * @param NumericListField Optional field - holds one or more numeric (short values).

     * @param CompositeField Optional field - holds a composite containing a number of discrete value.

     */
    public ObjectUpdate(BasicEnum EnumField,
            Duration DurationField,
            ShortList NumericListField,
            UpdateComposite CompositeField) {
        this.EnumField = EnumField;
        this.DurationField = DurationField;
        this.NumericListField = NumericListField;
        this.CompositeField = CompositeField;
    }

    @Override
    public Element createElement() {
        return new ObjectUpdate();
    }

    /**
     * Returns the field EnumField.
     * 
     * @return The field EnumField
     */
    public BasicEnum getEnumField() {
        return EnumField;
    }

    /**
     * Returns the field DurationField.
     * 
     * @return The field DurationField
     */
    public Duration getDurationField() {
        return DurationField;
    }

    /**
     * Returns the field NumericListField.
     * 
     * @return The field NumericListField
     */
    public ShortList getNumericListField() {
        return NumericListField;
    }

    /**
     * Returns the field CompositeField.
     * 
     * @return The field CompositeField
     */
    public UpdateComposite getCompositeField() {
        return CompositeField;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectUpdate) {
            ObjectUpdate other = (ObjectUpdate) obj;
            if (EnumField == null) {
                if (other.EnumField != null) {
                    return false;
                }
            } else {
                if (! EnumField.equals(other.EnumField)) {
                    return false;
                }
            }
            if (DurationField == null) {
                if (other.DurationField != null) {
                    return false;
                }
            } else {
                if (! DurationField.equals(other.DurationField)) {
                    return false;
                }
            }
            if (NumericListField == null) {
                if (other.NumericListField != null) {
                    return false;
                }
            } else {
                if (! NumericListField.equals(other.NumericListField)) {
                    return false;
                }
            }
            if (CompositeField == null) {
                if (other.CompositeField != null) {
                    return false;
                }
            } else {
                if (! CompositeField.equals(other.CompositeField)) {
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
        hash = 83 * hash + (EnumField != null ? EnumField.hashCode() : 0);
        hash = 83 * hash + (DurationField != null ? DurationField.hashCode() : 0);
        hash = 83 * hash + (NumericListField != null ? NumericListField.hashCode() : 0);
        hash = 83 * hash + (CompositeField != null ? CompositeField.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectUpdate: ");
        buf.append("EnumField=").append(EnumField);
        buf.append(", DurationField=").append(DurationField);
        buf.append(", NumericListField=").append(NumericListField);
        buf.append(", CompositeField=").append(CompositeField);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableElement(EnumField);
        encoder.encodeNullableDuration(DurationField);
        encoder.encodeNullableElement(NumericListField);
        encoder.encodeNullableElement(CompositeField);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        EnumField = (BasicEnum) decoder.decodeNullableElement(BasicEnum.FIRST);
        DurationField = decoder.decodeNullableDuration();
        NumericListField = (ShortList) decoder.decodeNullableElement(new ShortList());
        CompositeField = (UpdateComposite) decoder.decodeNullableElement(new UpdateComposite());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
