package org.ccsds.moims.mo.malprototype.datatest.body;

/**
 * Multi body return class for TestAbstractMultiReturnResponse.
 */
public final class TestAbstractMultiReturnResponse {

    /**
     * out1: .
     */
    private org.ccsds.moims.mo.mal.structures.UOctet out1;

    /**
     * out2: .
     */
    private org.ccsds.moims.mo.mal.structures.UShort out2;

    /**
     * out3: .
     */
    private org.ccsds.moims.mo.mal.structures.UInteger out3;

    /**
     * out4: .
     */
    private org.ccsds.moims.mo.mal.structures.Element out4;

    /**
     * Default constructor for TestAbstractMultiReturnResponse.
     * 
     */
    public TestAbstractMultiReturnResponse() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param out1 The out1 field.
     * @param out2 The out2 field.
     * @param out3 The out3 field.
     * @param out4 The out4 field.
     */
    public TestAbstractMultiReturnResponse(org.ccsds.moims.mo.mal.structures.UOctet out1,
            org.ccsds.moims.mo.mal.structures.UShort out2,
            org.ccsds.moims.mo.mal.structures.UInteger out3,
            org.ccsds.moims.mo.mal.structures.Element out4) {
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
    public org.ccsds.moims.mo.mal.structures.UOctet getOut1() {
        return out1;
    }

    /**
     * Returns the field out2.
     * 
     * @return The field out2
     */
    public org.ccsds.moims.mo.mal.structures.UShort getOut2() {
        return out2;
    }

    /**
     * Returns the field out3.
     * 
     * @return The field out3
     */
    public org.ccsds.moims.mo.mal.structures.UInteger getOut3() {
        return out3;
    }

    /**
     * Returns the field out4.
     * 
     * @return The field out4
     */
    public org.ccsds.moims.mo.mal.structures.Element getOut4() {
        return out4;
    }

}
