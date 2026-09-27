package org.ccsds.moims.mo.malprototype.iptest.body;

/**
 * Multi body return class for RequestMultiResponse.
 */
public final class RequestMultiResponse {

    /**
     * output1: .
     */
    private String output1;

    /**
     * output2: .
     */
    private org.ccsds.moims.mo.mal.structures.Element output2;

    /**
     * Default constructor for RequestMultiResponse.
     * 
     */
    public RequestMultiResponse() {
    }

    /**
     * Constructs an instance of this type using provided values.
     * 
     * @param output1 The output1 field.
     * @param output2 The output2 field.
     */
    public RequestMultiResponse(String output1,
            org.ccsds.moims.mo.mal.structures.Element output2) {
        this.output1 = output1;
        this.output2 = output2;
    }

    /**
     * Returns the field output1.
     * 
     * @return The field output1
     */
    public String getOutput1() {
        return output1;
    }

    /**
     * Returns the field output2.
     * 
     * @return The field output2
     */
    public org.ccsds.moims.mo.mal.structures.Element getOutput2() {
        return output2;
    }

}
