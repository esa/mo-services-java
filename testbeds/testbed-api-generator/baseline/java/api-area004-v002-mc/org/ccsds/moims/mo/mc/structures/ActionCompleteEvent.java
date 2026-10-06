package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ActionCompleteEvent type is used for publishing an action execution
 * reaching the completion stage.
 */
public final class ActionCompleteEvent extends ActionEvent {

    private static final long serialVersionUID = 1125899940397071L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899940397071L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

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
    public Element createElement() {
        return new ActionCompleteEvent();
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
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
