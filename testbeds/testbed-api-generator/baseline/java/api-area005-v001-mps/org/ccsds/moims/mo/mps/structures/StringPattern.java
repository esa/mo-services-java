package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Concrete sub-type of ValidationDetails that provides additional fields
 * to support data validation for the string data type.
 */
public final class StringPattern extends org.ccsds.moims.mo.mps.structures.ValidationDetails {

    private static final long serialVersionUID = 1407374900330523L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330523L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Maximum length of the string (characters).  If omitted, no maximum length
     * is enforced.
     */
    private org.ccsds.moims.mo.mal.structures.UInteger maxLength;

    /**
     * A ‘regular expression’ or sequence of characters defining a character pattern
     * that the string value must match.  If omitted, all character sequences
     * are permitted. The choice of ‘regular expression’ specification to follow
     * is implementation-specific.
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
     * @param maxLength Maximum length of the string (characters).  If omitted, no maximum length is enforced.
     * @param regex A ‘regular expression’ or sequence of characters defining a character pattern that the string value must match.  If omitted, all character sequences are permitted. The choice of ‘regular expression’ specification to follow is implementation-specific.
     */
    public StringPattern(org.ccsds.moims.mo.mal.structures.UInteger maxLength,
            String regex) {
        this.maxLength = maxLength;
        this.regex = regex;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.StringPattern();
    }

    /**
     * Returns the field maxLength.
     * 
     * @return The field maxLength
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getMaxLength() {
        return maxLength;
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
            if (maxLength == null) {
                if (other.maxLength != null) {
                    return false;
                }
            } else {
                if (! maxLength.equals(other.maxLength)) {
                    return false;
                }
            }
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
        hash = 83 * hash + (maxLength != null ? maxLength.hashCode() : 0);
        hash = 83 * hash + (regex != null ? regex.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(StringPattern: ");
        buf.append(super.toString());
        buf.append(", maxLength=").append(maxLength);
        buf.append(", regex=").append(regex);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
        encoder.encodeNullableUInteger(maxLength);
        encoder.encodeNullableString(regex);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        maxLength = decoder.decodeNullableUInteger();
        regex = decoder.decodeNullableString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
