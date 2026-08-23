package org.ccsds.moims.mo.mc.structures;

/**
 * The ActionEvent is the base type of all events used by the monitorActionExec
 * operation to publish a new execution stage reached by the execution of
 * an action.
 */
public abstract class ActionEvent implements org.ccsds.moims.mo.mal.structures.Composite {

    /**
     * The success field.
     */
    private Boolean success;

    /**
     * The comment field.
     */
    private String comment;

    /**
     * Default constructor for ActionEvent.
     * 
     */
    public ActionEvent() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param success The success field.
     * @param comment The comment field.
     */
    public ActionEvent(Boolean success,
            String comment) {
        this.success = success;
        this.comment = comment;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param success The success field.
     */
    public ActionEvent(Boolean success) {
        this.success = success;
        this.comment = null;
    }

    /**
     * Returns the field success.
     * 
     * @return The field success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * Returns the field comment.
     * 
     * @return The field comment
     */
    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionEvent) {
            ActionEvent other = (ActionEvent) obj;
            if (success == null) {
                if (other.success != null) {
                    return false;
                }
            } else {
                if (! success.equals(other.success)) {
                    return false;
                }
            }
            if (comment == null) {
                if (other.comment != null) {
                    return false;
                }
            } else {
                if (! comment.equals(other.comment)) {
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
        hash = 83 * hash + (success != null ? success.hashCode() : 0);
        hash = 83 * hash + (comment != null ? comment.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionEvent: ");
        buf.append("success=").append(success);
        buf.append(", comment=").append(comment);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (success == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'success' cannot be null!");
        }
        encoder.encodeBoolean(success);
        encoder.encodeNullableString(comment);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        success = decoder.decodeBoolean();
        comment = decoder.decodeNullableString();
        return this;
    }

}
