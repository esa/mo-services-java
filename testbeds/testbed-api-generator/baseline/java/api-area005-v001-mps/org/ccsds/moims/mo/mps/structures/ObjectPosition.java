package org.ccsds.moims.mo.mps.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * E6: An ObjectPosition is a Position that coincides with the position of
 * an existing object.  The manner in which the planning system derives the
 * value of this Position from the name of the referenced object is implementation-defined.
 */
public final class ObjectPosition extends Position {

    private static final long serialVersionUID = 1407374900330508L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330508L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Name or identifier of a catalogued celestial object or a mission specific
     * object.
     */
    private Identifier object;

    /**
     * Default constructor for ObjectPosition.
     * 
     */
    public ObjectPosition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param object Name or identifier of a catalogued celestial object or a mission specific object.
     */
    public ObjectPosition(Identifier object) {
        this.object = object;
    }

    @Override
    public Element createElement() {
        return new ObjectPosition();
    }

    /**
     * Returns the field object.
     * 
     * @return The field object
     */
    public Identifier getObject() {
        return object;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectPosition) {
            if (! super.equals(obj)) {
                return false;
            }
            ObjectPosition other = (ObjectPosition) obj;
            if (object == null) {
                if (other.object != null) {
                    return false;
                }
            } else {
                if (! object.equals(other.object)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (object != null ? object.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ObjectPosition: ");
        buf.append(super.toString());
        buf.append(", object=").append(object);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (object == null) {
            throw new MALException("The field 'object' cannot be null!");
        }
        encoder.encodeIdentifier(object);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        object = decoder.decodeIdentifier();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
