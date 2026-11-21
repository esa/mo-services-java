package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 * The IPTestResult structure.
 */
public final class IPTestResult implements Composite {

    private static final long serialVersionUID = 28147497687842828L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842828L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The transaction identifier assigned to the last interaction.
     */
    private Identifier transactionId;

    /**
     * The list of assertions checked by the provider.
     */
    private AssertionList assertions;

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
    public IPTestResult(Identifier transactionId,
            AssertionList assertions) {
        this.transactionId = transactionId;
        this.assertions = assertions;
    }

    @Override
    public Element createElement() {
        return new IPTestResult();
    }

    /**
     * Returns the field transactionId.
     * 
     * @return The field transactionId
     */
    public Identifier getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the field assertions.
     * 
     * @return The field assertions
     */
    public AssertionList getAssertions() {
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
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableIdentifier(transactionId);
        encoder.encodeNullableElement(assertions);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        transactionId = decoder.decodeNullableIdentifier();
        assertions = (AssertionList) decoder.decodeNullableElement(new AssertionList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
