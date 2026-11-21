package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UInteger;

/**
 * E1: Concrete sub-type of ValidationDetails that provides additional fields
 * to support data validation for the string data type.
 */
public final class StringPattern extends ValidationDetails {

    private static final long serialVersionUID = 1407374900330523L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330523L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Maximum length of the string (characters).  If omitted, no maximum length
     * is enforced.
     */
    private UInteger maxLength;

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
    public StringPattern(UInteger maxLength,
            String regex) {
        this.maxLength = maxLength;
        this.regex = regex;
    }

    @Override
    public Element createElement() {
        return new StringPattern();
    }

    /**
     * Returns the field maxLength.
     * 
     * @return The field maxLength
     */
    public UInteger getMaxLength() {
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        encoder.encodeNullableUInteger(maxLength);
        encoder.encodeNullableString(regex);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        maxLength = decoder.decodeNullableUInteger();
        regex = decoder.decodeNullableString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
