package org.ccsds.moims.mo.mc.action.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The ActionCreationRequest contains all the fields required when creating
 * a new action in a provider.
 */
public final class ActionCreationRequest implements Composite {

    private static final long serialVersionUID = 1125904218587139L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125904218587139L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The name of the action. Must not be empty or the wildcard value.
     */
    private Identifier name;

    /**
     * The action definition details.
     */
    private ActionDefinitionDetails actionDefDetails;

    /**
     * Default constructor for ActionCreationRequest.
     * 
     */
    public ActionCreationRequest() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param name The name of the action. Must not be empty or the wildcard value.
     * @param actionDefDetails The action definition details.
     */
    public ActionCreationRequest(Identifier name,
            ActionDefinitionDetails actionDefDetails) {
        this.name = name;
        this.actionDefDetails = actionDefDetails;
    }

    @Override
    public Element createElement() {
        return new ActionCreationRequest();
    }

    /**
     * Returns the field name.
     * 
     * @return The field name
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the field actionDefDetails.
     * 
     * @return The field actionDefDetails
     */
    public ActionDefinitionDetails getActionDefDetails() {
        return actionDefDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionCreationRequest) {
            ActionCreationRequest other = (ActionCreationRequest) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else {
                if (! name.equals(other.name)) {
                    return false;
                }
            }
            if (actionDefDetails == null) {
                if (other.actionDefDetails != null) {
                    return false;
                }
            } else {
                if (! actionDefDetails.equals(other.actionDefDetails)) {
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
        hash = 83 * hash + (name != null ? name.hashCode() : 0);
        hash = 83 * hash + (actionDefDetails != null ? actionDefDetails.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionCreationRequest: ");
        buf.append("name=").append(name);
        buf.append(", actionDefDetails=").append(actionDefDetails);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (name == null) {
            throw new MALException("The field 'name' cannot be null!");
        }
        if (actionDefDetails == null) {
            throw new MALException("The field 'actionDefDetails' cannot be null!");
        }
        encoder.encodeIdentifier(name);
        encoder.encodeElement(actionDefDetails);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        name = decoder.decodeIdentifier();
        actionDefDetails = (ActionDefinitionDetails) decoder.decodeElement(new ActionDefinitionDetails());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
