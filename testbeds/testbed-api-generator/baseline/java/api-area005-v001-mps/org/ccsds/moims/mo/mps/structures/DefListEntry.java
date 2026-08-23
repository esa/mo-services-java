package org.ccsds.moims.mo.mps.structures;

/**
 * E1: Used in the context of the MPS Plan Information Management service,
 * this holds a list of definitions for a specified type of MPS service object,
 * together with their definitions.
 */
public final class DefListEntry implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330503L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330503L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Object Type: ActivityDefinition | EventDefinition | Resource | RequestDefinition.
     * Reference to an Item.
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> definitionID;

    /**
     * Description of the item.
     */
    private String description;

    /**
     * Default constructor for DefListEntry.
     * 
     */
    public DefListEntry() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param definitionID Object Type: ActivityDefinition | EventDefinition | Resource | RequestDefinition. Reference to an Item.
     * @param description Description of the item.
     */
    public DefListEntry(org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> definitionID,
            String description) {
        this.definitionID = definitionID;
        this.description = description;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.DefListEntry();
    }

    /**
     * Returns the field definitionID.
     * 
     * @return The field definitionID
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element> getDefinitionID() {
        return definitionID;
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DefListEntry) {
            DefListEntry other = (DefListEntry) obj;
            if (definitionID == null) {
                if (other.definitionID != null) {
                    return false;
                }
            } else {
                if (! definitionID.equals(other.definitionID)) {
                    return false;
                }
            }
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
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
        hash = 83 * hash + (definitionID != null ? definitionID.hashCode() : 0);
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(DefListEntry: ");
        buf.append("definitionID=").append(definitionID);
        buf.append(", description=").append(description);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (definitionID == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'definitionID' cannot be null!");
        }
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        encoder.encodeAbstractElement(definitionID);
        encoder.encodeString(description);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        definitionID = (org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.mal.structures.Element>) decoder.decodeAbstractElement();
        description = decoder.decodeString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
