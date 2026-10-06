package org.ccsds.moims.mo.malprototype.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * This data structure is an error report produced after having found a faulty
 * header.
 */
public final class BadHeaderReport implements Composite {

    private static final long serialVersionUID = 28147497687842822L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842822L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The expected header.
     */
    private MessageHeader expectedHeader;

    /**
     * The header that is not compliant with the MAL rules.
     */
    private MessageHeader faultyHeader;

    /**
     * Default constructor for BadHeaderReport.
     * 
     */
    public BadHeaderReport() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param expectedHeader The expected header
     * @param faultyHeader The header that is not compliant with the MAL rules
     */
    public BadHeaderReport(MessageHeader expectedHeader,
            MessageHeader faultyHeader) {
        this.expectedHeader = expectedHeader;
        this.faultyHeader = faultyHeader;
    }

    @Override
    public Element createElement() {
        return new BadHeaderReport();
    }

    /**
     * Returns the field expectedHeader.
     * 
     * @return The field expectedHeader
     */
    public MessageHeader getExpectedHeader() {
        return expectedHeader;
    }

    /**
     * Returns the field faultyHeader.
     * 
     * @return The field faultyHeader
     */
    public MessageHeader getFaultyHeader() {
        return faultyHeader;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BadHeaderReport) {
            BadHeaderReport other = (BadHeaderReport) obj;
            if (expectedHeader == null) {
                if (other.expectedHeader != null) {
                    return false;
                }
            } else {
                if (! expectedHeader.equals(other.expectedHeader)) {
                    return false;
                }
            }
            if (faultyHeader == null) {
                if (other.faultyHeader != null) {
                    return false;
                }
            } else {
                if (! faultyHeader.equals(other.faultyHeader)) {
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
        hash = 83 * hash + (expectedHeader != null ? expectedHeader.hashCode() : 0);
        hash = 83 * hash + (faultyHeader != null ? faultyHeader.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(BadHeaderReport: ");
        buf.append("expectedHeader=").append(expectedHeader);
        buf.append(", faultyHeader=").append(faultyHeader);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        encoder.encodeNullableElement(expectedHeader);
        encoder.encodeNullableElement(faultyHeader);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        expectedHeader = (MessageHeader) decoder.decodeNullableElement(new MessageHeader());
        faultyHeader = (MessageHeader) decoder.decodeNullableElement(new MessageHeader());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
