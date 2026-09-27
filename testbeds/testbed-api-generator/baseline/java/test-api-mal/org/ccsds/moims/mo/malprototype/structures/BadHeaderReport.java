package org.ccsds.moims.mo.malprototype.structures;

/**
 * This data structure is an error report produced after having found a faulty
 * header.
 */
public final class BadHeaderReport implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 28147497687842822L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 28147497687842822L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The expected header.
     */
    private org.ccsds.moims.mo.malprototype.structures.MessageHeader expectedHeader;

    /**
     * The header that is not compliant with the MAL rules.
     */
    private org.ccsds.moims.mo.malprototype.structures.MessageHeader faultyHeader;

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
    public BadHeaderReport(org.ccsds.moims.mo.malprototype.structures.MessageHeader expectedHeader,
            org.ccsds.moims.mo.malprototype.structures.MessageHeader faultyHeader) {
        this.expectedHeader = expectedHeader;
        this.faultyHeader = faultyHeader;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.malprototype.structures.BadHeaderReport();
    }

    /**
     * Returns the field expectedHeader.
     * 
     * @return The field expectedHeader
     */
    public org.ccsds.moims.mo.malprototype.structures.MessageHeader getExpectedHeader() {
        return expectedHeader;
    }

    /**
     * Returns the field faultyHeader.
     * 
     * @return The field faultyHeader
     */
    public org.ccsds.moims.mo.malprototype.structures.MessageHeader getFaultyHeader() {
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
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeNullableElement(expectedHeader);
        encoder.encodeNullableElement(faultyHeader);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        expectedHeader = (org.ccsds.moims.mo.malprototype.structures.MessageHeader) decoder.decodeNullableElement(new org.ccsds.moims.mo.malprototype.structures.MessageHeader());
        faultyHeader = (org.ccsds.moims.mo.malprototype.structures.MessageHeader) decoder.decodeNullableElement(new org.ccsds.moims.mo.malprototype.structures.MessageHeader());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
