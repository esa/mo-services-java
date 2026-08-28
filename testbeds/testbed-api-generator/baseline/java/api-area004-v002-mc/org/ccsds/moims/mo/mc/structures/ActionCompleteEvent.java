package org.ccsds.moims.mo.mc.structures;

/**
 * The ActionCompleteEvent type is used for publishing an action execution
 * reaching the completion stage.
 */
public final class ActionCompleteEvent extends org.ccsds.moims.mo.mc.structures.ActionEvent {

    private static final long serialVersionUID = 1125899940397071L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397071L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Default constructor for ActionCompleteEvent.
     * 
     */
    public ActionCompleteEvent() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param success The success field.
     * @param comment The comment field.
     */
    public ActionCompleteEvent(Boolean success,
            String comment) {
        super(success,
            comment);
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param success The success field.
     */
    public ActionCompleteEvent(Boolean success) {
        super(success);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.structures.ActionCompleteEvent();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionCompleteEvent) {
            if (! super.equals(obj)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionCompleteEvent: ");
        buf.append(super.toString());
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        super.encode(encoder);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
