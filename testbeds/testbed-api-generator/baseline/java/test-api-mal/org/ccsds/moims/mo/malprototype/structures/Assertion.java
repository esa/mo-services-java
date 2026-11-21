package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The Assertion structure.
 */
public final class Assertion implements Composite {

    private static final long serialVersionUID = 28147497687842817L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842817L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

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
    public Element createElement() {
        return new Assertion();
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableString(procedureName);
        encoder.encodeNullableString(Info);
        encoder.encodeNullableBoolean(Result);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        procedureName = decoder.decodeNullableString();
        Info = decoder.decodeNullableString();
        Result = decoder.decodeNullableBoolean();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
