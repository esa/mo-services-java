package org.ccsds.moims.mo.mps.structures;

/**
 * E6: PhysicalValue is an abstract base type for the specific value types
 * defined below.  Only specific value types are used in the pointing constraint
 * definitions below.
 */
public abstract class PhysicalValue implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * Physical value.
     */
    private Double value;

    /**
     * Optional units.  The units for a single quantity.  The unit type depends
     * on the specific value type.
     */
    private String units;

    /**
     * Default constructor for PhysicalValue.
     * 
     */
    public PhysicalValue() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param value Physical value.
     * @param units Optional units.  The units for a single quantity.  The unit type depends on the specific value type.
     */
    public PhysicalValue(Double value,
            String units) {
        this.value = value;
        this.units = units;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param value Physical value.
     */
    public PhysicalValue(Double value) {
        this.value = value;
        this.units = null;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Double getValue() {
        return value;
    }

    /**
     * Returns the field units.
     * 
     * @return The field units
     */
    public String getUnits() {
        return units;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PhysicalValue) {
            PhysicalValue other = (PhysicalValue) obj;
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
                    return false;
                }
            }
            if (units == null) {
                if (other.units != null) {
                    return false;
                }
            } else {
                if (! units.equals(other.units)) {
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
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        hash = 83 * hash + (units != null ? units.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PhysicalValue: ");
        buf.append("value=").append(value);
        buf.append(", units=").append(units);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (value == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'value' cannot be null!");
        }
        encoder.encodeDouble(value);
        encoder.encodeNullableString(units);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        value = decoder.decodeDouble();
        units = decoder.decodeNullableString();
        return this;
    }

}
