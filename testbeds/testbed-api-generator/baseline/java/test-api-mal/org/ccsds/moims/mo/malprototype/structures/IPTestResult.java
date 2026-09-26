package org.ccsds.moims.mo.malprototype.structures;

/**
 * The IPTestResult structure.
 */
public final class IPTestResult implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842828L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842828L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The transaction identifier assigned to the last interaction.
     */
    private org.ccsds.moims.mo.mal.structures.Identifier transactionId;

    /**
     * The list of assertions checked by the provider.
     */
    private org.ccsds.moims.mo.malprototype.structures.AssertionList assertions;

    /**
     * Default constructor for IPTestResult.
     * 
     */
    public IPTestResult() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param transactionId The transaction identifier assigned to the last interaction
     * @param assertions The list of assertions checked by the provider.
     */
    public IPTestResult(org.ccsds.moims.mo.mal.structures.Identifier transactionId,
            org.ccsds.moims.mo.malprototype.structures.AssertionList assertions) {
        this.transactionId = transactionId;
        this.assertions = assertions;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.IPTestResult();
    }

    /**
     * Returns the field transactionId.
     * 
     * @return The field transactionId
     */
    public org.ccsds.moims.mo.mal.structures.Identifier getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the field assertions.
     * 
     * @return The field assertions
     */
    public org.ccsds.moims.mo.malprototype.structures.AssertionList getAssertions() {
        return assertions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IPTestResult) {
            IPTestResult other = (IPTestResult) obj;
            if (transactionId == null) {
                if (other.transactionId != null) {
                    return false;
                }
            } else {
                if (! transactionId.equals(other.transactionId)) {
                    return false;
                }
            }
            if (assertions == null) {
                if (other.assertions != null) {
                    return false;
                }
            } else {
                if (! assertions.equals(other.assertions)) {
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
        hash = 83 * hash + (transactionId != null ? transactionId.hashCode() : 0);
        hash = 83 * hash + (assertions != null ? assertions.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(IPTestResult: ");
        buf.append("transactionId=").append(transactionId);
        buf.append(", assertions=").append(assertions);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableIdentifier(transactionId);
        encoder.encodeNullableElement(assertions);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        transactionId = decoder.decodeNullableIdentifier();
        assertions = (org.ccsds.moims.mo.malprototype.structures.AssertionList) decoder.decodeNullableElement(new org.ccsds.moims.mo.malprototype.structures.AssertionList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
