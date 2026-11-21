package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * E4: Defines the value (or minimum/maximum value) of a resource at a particular
 * point in time.
 */
public final class ProfileEntry implements Composite {

    private static final long serialVersionUID = 1407374900330802L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330802L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Time of resource data point.
     */
    private Element time;

    /**
     * Value of resource data point.  MAL Attribute type must match the dataType
     * of the Resource definition.
     */
    private Attribute value;

    /**
     * Default constructor for ProfileEntry.
     * 
     */
    public ProfileEntry() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param time Time of resource data point.
     * @param value Value of resource data point.  MAL Attribute type must match the dataType of the Resource definition.
     */
    public ProfileEntry(Element time,
            Attribute value) {
        this.time = time;
        this.value = value;
    }

    @Override
    public Element createElement() {
        return new ProfileEntry();
    }

    /**
     * Returns the field time.
     * 
     * @return The field time
     */
    public Element getTime() {
        return time;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Attribute getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProfileEntry) {
            ProfileEntry other = (ProfileEntry) obj;
            if (time == null) {
                if (other.time != null) {
                    return false;
                }
            } else {
                if (! time.equals(other.time)) {
                    return false;
                }
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
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
        hash = 83 * hash + (time != null ? time.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ProfileEntry: ");
        buf.append("time=").append(time);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (time == null) {
            throw new MALException("The field 'time' cannot be null!");
        }
        if (value == null) {
            throw new MALException("The field 'value' cannot be null!");
        }
        encoder.encodeAbstractElement(time);
        encoder.encodeAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        time = (Element) decoder.decodeAbstractElement();
        value = (Attribute) decoder.decodeAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
