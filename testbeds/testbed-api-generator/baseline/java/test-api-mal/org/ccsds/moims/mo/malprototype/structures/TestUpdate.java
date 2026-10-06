package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * This data structure defines an Update published by the IPTest.
 */
public final class TestUpdate implements Composite {

    private static final long serialVersionUID = 28147497687842826L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842826L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

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
    public Element createElement() {
        return new TestUpdate();
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableInteger(Counter);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        Counter = decoder.decodeNullableInteger();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
