package org.ccsds.moims.mo.malprototype.datatest.body;

/**
 * Multi body return class for TestPolymorphicObjectRefTypesResponse.
 */
public final class TestPolymorphicObjectRefTypesResponse {

    /**
     * output1: .
     */
    private org.ccsds.moims.mo.malprototype.structures.Garage output1;

    /**
     * output2: .
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList output2;

    /**
     * output3: .
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList output3;

    /**
     * output4: .
     */
    private org.ccsds.moims.mo.mal.structures.ObjectRefList output4;

    /**
     * Default constructor for TestPolymorphicObjectRefTypesResponse.
     * 
     */
    public TestPolymorphicObjectRefTypesResponse() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     * @param output3 The output3 field.
     * @param output4 The output4 field.
     */
    public TestPolymorphicObjectRefTypesResponse(org.ccsds.moims.mo.malprototype.structures.Garage output1,
            org.ccsds.moims.mo.mal.structures.ObjectRefList output2,
            org.ccsds.moims.mo.mal.structures.ObjectRefList output3,
            org.ccsds.moims.mo.mal.structures.ObjectRefList output4) {
        this.output1 = output1;
        this.output2 = output2;
        this.output3 = output3;
        this.output4 = output4;
    }

    /**
     * Returns the field output1.
     * 
     * @return The field output1
     */
    public org.ccsds.moims.mo.malprototype.structures.Garage getOutput1() {
        return output1;
    }

    /**
     * Returns the field output2.
     * 
     * @return The field output2
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getOutput2() {
        return output2;
    }

    /**
     * Returns the field output3.
     * 
     * @return The field output3
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getOutput3() {
        return output3;
    }

    /**
     * Returns the field output4.
     * 
     * @return The field output4
     */
    public org.ccsds.moims.mo.mal.structures.ObjectRefList getOutput4() {
        return output4;
    }

}
