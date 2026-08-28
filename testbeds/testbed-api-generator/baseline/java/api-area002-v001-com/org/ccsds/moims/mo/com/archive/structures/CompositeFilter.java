package org.ccsds.moims.mo.com.archive.structures;

/**
 * The CompositeFilter allows an archive query to specify a filter based on
 * the content of the body of an object if that body is specified using the
 * MAL data type specification.
 */
public final class CompositeFilter implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 562958560133123L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 562958560133123L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The name of the field in the object body (MAL::Composite) to match against.
     * It is specified by referring to the name of the field. If the field does
     * not exist in the Composite then the filter shall evaluate to false. If
     * the field is nested inside another Composite, it can be referenced by separating
     * the field names by a &quot;.&quot; character; for example the field &quot;instId&quot;
     * or the source of an ArchiveDetails composite would be referenced using
     * "details.source.key.instId". If the body is not a Composite but an Attribute
     * or Enumeration then it can be referred to by using a blank ("") string
     * value here. Accessing values from lists is not supported by this method.
     */
    private String fieldName;

    /**
     * The type of the filter to apply.
     */
    private org.ccsds.moims.mo.com.archive.structures.ExpressionOperator type;

    /**
     * The value to compare with. The type of the suppied value and the matched
     * field mst be the same. Must not contain NULL for expression operators CONTAINS,
     * ICONTAINS, GREATER, GREATER_OR_EQUAL, LESS, or LESS_OR_EQUAL otherwise
     * an INVALID error should be returned. Must contain a UInteger ordinal value
     * if the field being matched is an Enumeration. Blob fields can only be used
     * with EQUAL/DIFFER. Must contain a String value if operator is CONTAINS
     * or ICONTAINS otherwise an INVALID error should be returned.
     */
    private org.ccsds.moims.mo.mal.structures.Attribute fieldValue;

    /**
     * Default constructor for CompositeFilter.
     * 
     */
    public CompositeFilter() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param fieldName The name of the field in the object body (MAL::Composite) to match against. It is specified by referring to the name of the field. If the field does not exist in the Composite then the filter shall evaluate to false. If the field is nested inside another Composite, it can be referenced by separating the field names by a '.' character; for example the field 'instId' or the source of an ArchiveDetails composite would be referenced using "details.source.key.instId". If the body is not a Composite but an Attribute or Enumeration then it can be referred to by using a blank ("") string value here. Accessing values from lists is not supported by this method.
     * @param type The type of the filter to apply.
     * @param fieldValue The value to compare with. The type of the suppied value and the matched field mst be the same. Must not contain NULL for expression operators CONTAINS, ICONTAINS, GREATER, GREATER_OR_EQUAL, LESS, or LESS_OR_EQUAL otherwise an INVALID error should be returned. Must contain a UInteger ordinal value if the field being matched is an Enumeration. Blob fields can only be used with EQUAL/DIFFER. Must contain a String value if operator is CONTAINS or ICONTAINS otherwise an INVALID error should be returned.
     */
    public CompositeFilter(String fieldName,
            org.ccsds.moims.mo.com.archive.structures.ExpressionOperator type,
            org.ccsds.moims.mo.mal.structures.Attribute fieldValue) {
        this.fieldName = fieldName;
        this.type = type;
        this.fieldValue = fieldValue;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param fieldName The name of the field in the object body (MAL::Composite) to match against. It is specified by referring to the name of the field. If the field does not exist in the Composite then the filter shall evaluate to false. If the field is nested inside another Composite, it can be referenced by separating the field names by a '.' character; for example the field 'instId' or the source of an ArchiveDetails composite would be referenced using "details.source.key.instId". If the body is not a Composite but an Attribute or Enumeration then it can be referred to by using a blank ("") string value here. Accessing values from lists is not supported by this method.
     * @param type The type of the filter to apply.
     */
    public CompositeFilter(String fieldName,
            org.ccsds.moims.mo.com.archive.structures.ExpressionOperator type) {
        this.fieldName = fieldName;
        this.type = type;
        this.fieldValue = null;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.com.archive.structures.CompositeFilter();
    }

    /**
     * Returns the field fieldName.
     * 
     * @return The field fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the field type.
     * 
     * @return The field type
     */
    public org.ccsds.moims.mo.com.archive.structures.ExpressionOperator getType() {
        return type;
    }

    /**
     * Returns the field fieldValue.
     * 
     * @return The field fieldValue
     */
    public org.ccsds.moims.mo.mal.structures.Attribute getFieldValue() {
        return fieldValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompositeFilter) {
            CompositeFilter other = (CompositeFilter) obj;
            if (fieldName == null) {
                if (other.fieldName != null) {
                    return false;
                }
            } else {
                if (! fieldName.equals(other.fieldName)) {
                    return false;
                }
            }
            if (type == null) {
                if (other.type != null) {
                    return false;
                }
            } else {
                if (! type.equals(other.type)) {
                    return false;
                }
            }
            if (fieldValue == null) {
                if (other.fieldValue != null) {
                    return false;
                }
            } else {
                if (! fieldValue.equals(other.fieldValue)) {
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
        hash = 83 * hash + (fieldName != null ? fieldName.hashCode() : 0);
        hash = 83 * hash + (type != null ? type.hashCode() : 0);
        hash = 83 * hash + (fieldValue != null ? fieldValue.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CompositeFilter: ");
        buf.append("fieldName=").append(fieldName);
        buf.append(", type=").append(type);
        buf.append(", fieldValue=").append(fieldValue);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (fieldName == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'fieldName' cannot be null!");
        }
        if (type == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'type' cannot be null!");
        }
        encoder.encodeString(fieldName);
        encoder.encodeElement(type);
        encoder.encodeNullableAttribute(fieldValue);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        fieldName = decoder.decodeString();
        type = (org.ccsds.moims.mo.com.archive.structures.ExpressionOperator) decoder.decodeElement(org.ccsds.moims.mo.com.archive.structures.ExpressionOperator.EQUAL);
        fieldValue = (org.ccsds.moims.mo.mal.structures.Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
