package org.ccsds.moims.mo.malprototype.iptest.body;

/**
 * Multi body return class for InvokeMultiAck.
 */
public final class InvokeMultiAck {

    /**
     * ack1: .
     */
    private String ack1;

    /**
     * ack2: .
     */
    private org.ccsds.moims.mo.mal.structures.Element ack2;

    /**
     * Default constructor for InvokeMultiAck.
     * 
     */
    public InvokeMultiAck() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param ack1 The ack1 field.
     * @param ack2 The ack2 field.
     */
    public InvokeMultiAck(String ack1,
            org.ccsds.moims.mo.mal.structures.Element ack2) {
        this.ack1 = ack1;
        this.ack2 = ack2;
    }

    /**
     * Returns the field ack1.
     * 
     * @return The field ack1
     */
    public String getAck1() {
        return ack1;
    }

    /**
     * Returns the field ack2.
     * 
     * @return The field ack2
     */
    public org.ccsds.moims.mo.mal.structures.Element getAck2() {
        return ack2;
    }

}
