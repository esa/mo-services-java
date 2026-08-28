package org.ccsds.moims.mo.mpd.structures;

/**
 * A StringPattern is a concrete subtype of AttributeFilter that allows the
 * specification of a regular expression (or match pattern) to be searched
 * for in the value of a text type metadata attribute.
 */
public final class StringPattern extends org.ccsds.moims.mo.mpd.structures.AttributeFilter {

    private static final long serialVersionUID = 2533274807173131L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 2533274807173131L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The regular expression - a sequence of characters that specifies a match
     * pattern to be searched for in a text type metadata attribute (String, Identifier
     * or URI).
     */
    private String regex;

    /**
     * Default constructor for StringPattern.
     * 
     */
    public StringPattern() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the metadata attribute to filter. If the product metadata being evaluated does not contain an attribute with this name, then the evaluation of the filter shall be false.
     * @param include Indicates whether the filter is to include [TRUE] or exclude [FALSE] attribute values that match the filter.
     * @param regex The regular expression - a sequence of characters that specifies a match pattern to be searched for in a text type metadata attribute (String, Identifier or URI).
     */
    public StringPattern(org.ccsds.moims.mo.mal.structures.Identifier name,
            Boolean include,
            String regex) {
        super(name,
            include);
        this.regex = regex;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mpd.structures.StringPattern();
    }

    /**
     * Returns the field regex.
     * 
     * @return The field regex
     */
    public String getRegex() {
        return regex;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringPattern) {
            if (! super.equals(obj)) {
                return false;
            }
            StringPattern other = (StringPattern) obj;
            if (regex == null) {
                if (other.regex != null) {
                    return false;
                }
            } else {
                if (! regex.equals(other.regex)) {
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
        hash = 83 * hash + (regex != null ? regex.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StringPattern: ");
        buf.append(super.toString());
        buf.append(", regex=").append(regex);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        if (regex == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'regex' cannot be null!");
        }
        encoder.encodeString(regex);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        regex = decoder.decodeString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
