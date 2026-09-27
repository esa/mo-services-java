package org.ccsds.moims.mo.malprototype.structures;

/**
 * This data structure defines an Update published by the IPTest.
 */
public final class TestUpdate implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842826L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842826L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * A counter used to distinguish the test updates and to check the ordering.
     */
    private Integer Counter;

    /**
     * Default constructor for TestUpdate.
     * 
     */
    public TestUpdate() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param Counter A counter used to distinguish the test updates and to check the ordering.
     */
    public TestUpdate(Integer Counter) {
        this.Counter = Counter;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.TestUpdate();
    }

    /**
     * Returns the field Counter.
     * 
     * @return The field Counter
     */
    public Integer getCounter() {
        return Counter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestUpdate) {
            TestUpdate other = (TestUpdate) obj;
            if (Counter == null) {
                if (other.Counter != null) {
                    return false;
                }
            } else {
                if (! Counter.equals(other.Counter)) {
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
        hash = 83 * hash + (Counter != null ? Counter.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(TestUpdate: ");
        buf.append("Counter=").append(Counter);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableInteger(Counter);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        Counter = decoder.decodeNullableInteger();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
