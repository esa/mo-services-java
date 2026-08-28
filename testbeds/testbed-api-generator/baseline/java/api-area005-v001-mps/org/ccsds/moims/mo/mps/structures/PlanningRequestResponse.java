package org.ccsds.moims.mo.mps.structures;

/**
 * E1: PlanningRequestResponse is a data structure used in the context of
 * the MPS Planning Request service submitRequest and updateRequest operations,
 * in response to the submitted PlanningRequestDetails defined above.  It
 * contains a reference to the created RequestInstance and the supplied userReference
 * to allow the user to correlate the two.
 */
public final class PlanningRequestResponse implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330902L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330902L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Reference to the RequestInstance created in response to a submitRequest
     * operation, or the updated version of the RequestInstance following an updateRequest
     * operation.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> instance;

    /**
     * User supplied reference for the planning request.  This is distinct from
     * the identity of the RequestInstance that is assigned by the planning function.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier userReference;

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
    public PlanningRequestResponse(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> instance,
            org.ccsds.moims.mo.mal.structures.Identifier userReference) {
        this.instance = instance;
        this.userReference = userReference;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.PlanningRequestResponse();
    }

    /**
     * Returns the field instance.
     * 
     * @return The field instance
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance> getInstance() {
        return instance;
    }

    /**
     * Returns the field userReference.
     * 
     * @return The field userReference
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getUserReference() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (instance == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'instance' cannot be null!");
        }
        if (userReference == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'userReference' cannot be null!");
        }
        encoder.encodeElement(instance);
        encoder.encodeIdentifier(userReference);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        instance = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance>) decoder.decodeElement(new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mps.structures.RequestInstance>());
        userReference = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
