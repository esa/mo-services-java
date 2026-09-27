package org.ccsds.moims.mo.malprototype.structures;

/**
 * The Assertion structure.
 */
public final class Assertion implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842817L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842817L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Name of the test procedure that evaluated the assertion.
     */
    private String procedureName;

    /**
     * Message explaining what the assertion checks.
     */
    private String Info;

    /**
     * Boolean indicating whether the assertion succeeded (true) or not (false).
     */
    private Boolean Result;

    /**
     * Default constructor for Assertion.
     * 
     */
    public Assertion() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param procedureName Name of the test procedure that evaluated the assertion.
     * @param Info Message explaining what the assertion checks.
     * @param Result Boolean indicating whether the assertion succeeded (true) or not (false).
     */
    public Assertion(String procedureName,
            String Info,
            Boolean Result) {
        this.procedureName = procedureName;
        this.Info = Info;
        this.Result = Result;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.Assertion();
    }

    /**
     * Returns the field procedureName.
     * 
     * @return The field procedureName
     */
    public String getProcedureName() {
        return procedureName;
    }

    /**
     * Returns the field Info.
     * 
     * @return The field Info
     */
    public String getInfo() {
        return Info;
    }

    /**
     * Returns the field Result.
     * 
     * @return The field Result
     */
    public Boolean getResult() {
        return Result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Assertion) {
            Assertion other = (Assertion) obj;
            if (procedureName == null) {
                if (other.procedureName != null) {
                    return false;
                }
            } else {
                if (! procedureName.equals(other.procedureName)) {
                    return false;
                }
            }
            if (Info == null) {
                if (other.Info != null) {
                    return false;
                }
            } else {
                if (! Info.equals(other.Info)) {
                    return false;
                }
            }
            if (Result == null) {
                if (other.Result != null) {
                    return false;
                }
            } else {
                if (! Result.equals(other.Result)) {
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
        hash = 83 * hash + (procedureName != null ? procedureName.hashCode() : 0);
        hash = 83 * hash + (Info != null ? Info.hashCode() : 0);
        hash = 83 * hash + (Result != null ? Result.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(Assertion: ");
        buf.append("procedureName=").append(procedureName);
        buf.append(", Info=").append(Info);
        buf.append(", Result=").append(Result);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableString(procedureName);
        encoder.encodeNullableString(Info);
        encoder.encodeNullableBoolean(Result);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        procedureName = decoder.decodeNullableString();
        Info = decoder.decodeNullableString();
        Result = decoder.decodeNullableBoolean();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
