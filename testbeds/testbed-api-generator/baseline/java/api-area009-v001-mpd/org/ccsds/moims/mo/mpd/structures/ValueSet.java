package org.ccsds.moims.mo.mpd.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * A ValueSet is a concrete subtype of AttributeFilter that allows the specification
 * of a set of allowed (or disallowed) values for a metadata attribute.
 */
public final class ValueSet extends AttributeFilter {

    private static final long serialVersionUID = 2533274807173130L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173130L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The set of allowed (or disallowed) values for the metadata attribute.
     */
    private AttributeList values;

    /**
     * Default constructor for ValueSet.
     * 
     */
    public ValueSet() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the metadata attribute to filter. If the product metadata being evaluated does not contain an attribute with this name, then the evaluation of the filter shall be false.
     * @param include Indicates whether the filter is to include [TRUE] or exclude [FALSE] attribute values that match the filter.
     * @param values The set of allowed (or disallowed) values for the metadata attribute.
     */
    public ValueSet(Identifier name,
            Boolean include,
            AttributeList values) {
        super(name,
            include);
        this.values = values;
    }

    @Override
    public Element createElement() {
        return new ValueSet();
    }

    /**
     * Returns the field values.
     * 
     * @return The field values
     */
    public AttributeList getValues() {
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValueSet) {
            if (! super.equals(obj)) {
                return false;
            }
            ValueSet other = (ValueSet) obj;
            if (values == null) {
                if (other.values != null) {
                    return false;
                }
            } else {
                if (! values.equals(other.values)) {
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
        hash = 83 * hash + (values != null ? values.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ValueSet: ");
        buf.append(super.toString());
        buf.append(", values=").append(values);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (values == null) {
            throw new MALException("The field 'values' cannot be null!");
        }
        encoder.encodeElement(values);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        values = (AttributeList) decoder.decodeElement(new AttributeList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
