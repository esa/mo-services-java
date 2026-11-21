package org.ccsds.moims.mo.malprototype.datatest.body;

import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;

/**
 * Multi body return class for TestInnerAbstractMultiReturnResponse.
 */
public final class TestInnerAbstractMultiReturnResponse {

    /**
     * out1: .
     */
    private UOctet out1;

    /**
     * out2: .
     */
    private Element out2;

    /**
     * out3: .
     */
    private Element out3;

    /**
     * out4: .
     */
    private UInteger out4;

    /**
     * Default constructor for TestInnerAbstractMultiReturnResponse.
     * 
     */
    public TestInnerAbstractMultiReturnResponse() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param out1 The out1 field.
     * @param out2 The out2 field.
     * @param out3 The out3 field.
     * @param out4 The out4 field.
     */
    public TestInnerAbstractMultiReturnResponse(UOctet out1,
            Element out2,
            Element out3,
            UInteger out4) {
        this.out1 = out1;
        this.out2 = out2;
        this.out3 = out3;
        this.out4 = out4;
    }

    /**
     * Returns the field out1.
     * 
     * @return The field out1
     */
    public UOctet getOut1() {
        return out1;
    }

    /**
     * Returns the field out2.
     * 
     * @return The field out2
     */
    public Element getOut2() {
        return out2;
    }

    /**
     * Returns the field out3.
     * 
     * @return The field out3
     */
    public Element getOut3() {
        return out3;
    }

    /**
     * Returns the field out4.
     * 
     * @return The field out4
     */
    public UInteger getOut4() {
        return out4;
    }

}
