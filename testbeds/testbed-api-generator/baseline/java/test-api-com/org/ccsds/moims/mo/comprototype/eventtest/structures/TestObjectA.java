package org.ccsds.moims.mo.comprototype.eventtest.structures;

/**
 * Instances of this type created in event test.
.
 */
public final class TestObjectA implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 56295003948843018L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 56295003948843018L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The description field of the created object.
.
     */
    private String description;

    /**
     * Default constructor for TestObjectA.
     * 
     */
    public TestObjectA() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description The description field of the created object.

     */
    public TestObjectA(String description) {
        this.description = description;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectA();
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
        if (obj instanceof TestObjectA) {
            TestObjectA other = (TestObjectA) obj;
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestObjectA: ");
        buf.append("description=").append(description);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        encoder.encodeString(description);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        description = decoder.decodeString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
