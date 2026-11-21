package org.ccsds.moims.mo.malprototype.iptest.body;

import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Multi body return class for ProgressMultiAck.
 */
public final class ProgressMultiAck {

    /**
     * ack1: .
     */
    private String ack1;

    /**
     * ack2: .
     */
    private Element ack2;

    /**
     * Default constructor for ProgressMultiAck.
     * 
     */
    public ProgressMultiAck() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param ack1 The ack1 field.
     * @param ack2 The ack2 field.
     */
    public ProgressMultiAck(String ack1,
            Element ack2) {
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
    public Element getAck2() {
        return ack2;
    }

}
