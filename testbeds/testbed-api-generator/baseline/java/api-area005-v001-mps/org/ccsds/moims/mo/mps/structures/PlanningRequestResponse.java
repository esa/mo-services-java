package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E1: PlanningRequestResponse is a data structure used in the context of
 * the MPS Planning Request service submitRequest and updateRequest operations,
 * in response to the submitted PlanningRequestDetails defined above.  It
 * contains a reference to the created RequestInstance and the supplied userReference
 * to allow the user to correlate the two.
 */
public final class PlanningRequestResponse implements Composite {

    private static final long serialVersionUID = 1407374900330902L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330902L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Reference to the RequestInstance created in response to a submitRequest
     * operation, or the updated version of the RequestInstance following an updateRequest
     * operation.
     */
    private ObjectRef<RequestInstance> instance;

    /**
     * User supplied reference for the planning request.  This is distinct from
     * the identity of the RequestInstance that is assigned by the planning function.
     */
    private Identifier userReference;

    /**
     * Default constructor for PlanningRequestResponse.
     * 
     */
    public PlanningRequestResponse() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param instance Reference to the RequestInstance created in response to a submitRequest operation, or the updated version of the RequestInstance following an updateRequest operation.
     * @param userReference User supplied reference for the planning request.  This is distinct from the identity of the RequestInstance that is assigned by the planning function.
     */
    public PlanningRequestResponse(ObjectRef<RequestInstance> instance,
            Identifier userReference) {
        this.instance = instance;
        this.userReference = userReference;
    }

    @Override
    public Element createElement() {
        return new PlanningRequestResponse();
    }

    /**
     * Returns the field instance.
     * 
     * @return The field instance
     */
    public ObjectRef<RequestInstance> getInstance() {
        return instance;
    }

    /**
     * Returns the field userReference.
     * 
     * @return The field userReference
     */
    public Identifier getUserReference() {
        return userReference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanningRequestResponse) {
            PlanningRequestResponse other = (PlanningRequestResponse) obj;
            if (instance == null) {
                if (other.instance != null) {
                    return false;
                }
            } else {
                if (! instance.equals(other.instance)) {
                    return false;
                }
            }
            if (userReference == null) {
                if (other.userReference != null) {
                    return false;
                }
            } else {
                if (! userReference.equals(other.userReference)) {
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
        hash = 83 * hash + (instance != null ? instance.hashCode() : 0);
        hash = 83 * hash + (userReference != null ? userReference.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(PlanningRequestResponse: ");
        buf.append("instance=").append(instance);
        buf.append(", userReference=").append(userReference);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (instance == null) {
            throw new MALException("The field 'instance' cannot be null!");
        }
        if (userReference == null) {
            throw new MALException("The field 'userReference' cannot be null!");
        }
        encoder.encodeElement(instance);
        encoder.encodeIdentifier(userReference);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        instance = (ObjectRef<RequestInstance>) decoder.decodeElement(new ObjectRef<RequestInstance>());
        userReference = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
