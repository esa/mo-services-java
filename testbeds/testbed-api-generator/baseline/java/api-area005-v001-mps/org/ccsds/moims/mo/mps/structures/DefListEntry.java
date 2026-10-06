package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ObjectRef;

/**
 * E1: Used in the context of the MPS Plan Information Management service,
 * this holds a list of definitions for a specified type of MPS service object,
 * together with their definitions.
 */
public final class DefListEntry implements Composite {

    private static final long serialVersionUID = 1407374900330503L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330503L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Object Type: ActivityDefinition | EventDefinition | Resource | RequestDefinition.
     * Reference to an Item.
     */
    private ObjectRef<Element> definitionID;

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
    public DefListEntry(ObjectRef<Element> definitionID,
            String description) {
        this.definitionID = definitionID;
        this.description = description;
    }

    @Override
    public Element createElement() {
        return new DefListEntry();
    }

    /**
     * Returns the field definitionID.
     * 
     * @return The field definitionID
     */
    public ObjectRef<Element> getDefinitionID() {
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
    public void encode(MALEncoder encoder) throws MALException {
        if (definitionID == null) {
            throw new MALException("The field 'definitionID' cannot be null!");
        }
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        encoder.encodeAbstractElement(definitionID);
        encoder.encodeString(description);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        definitionID = (ObjectRef<Element>) decoder.decodeAbstractElement();
        description = decoder.decodeString();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
