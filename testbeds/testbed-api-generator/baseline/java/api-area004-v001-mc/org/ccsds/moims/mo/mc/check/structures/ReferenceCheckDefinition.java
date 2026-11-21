package org.ccsds.moims.mo.mc.check.structures;

import org.ccsds.moims.mo.com.archive.structures.ExpressionOperator;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mc.structures.Severity;

/**
 * The ReferenceCheckDefinition structure holds the key to another entity
 * to compare against for a consistency check.
 */
public final class ReferenceCheckDefinition extends CheckDefinitionDetails {

    private static final long serialVersionUID = 1125917103489033L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489033L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The operator to be used to perform the check.
     */
    private ExpressionOperator operator;

    /**
     * The value to check against.
     */
    private ReferenceValue checkReference;

    /**
     * Default constructor for ReferenceCheckDefinition.
     * 
     */
    public ReferenceCheckDefinition() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description The description of the check. May be empty.
     * @param checkSeverity Indicates the seriousness of the violation based on its possible negative consequences.
     * @param maxReportingInterval Maximum interval that can elapse between generations of CheckResult reports. If this value expires, then a CheckResult is generated with the same state for the previous and current state. If set to '0', then no maximum reporting interval shall be applied.
     * @param nominalCount Number of consecutive valid samples passing the check for the check to be OK.
     * @param nominalTime If nominalCount is zero then this is duration that a parameter is continuously passing the check for the check to be OK. If nominalCount is not zero then this is the period over which samples will be used in the nominalCount calculation, i.e. samples further in the past than nominalTime are not considered.
     * @param violationCount Number of consecutive valid samples violating the check for the check to be in violation.
     * @param violationTime If violationCount is zero then this is duration that a parameter is continuously violating the check for the check to be in violation. If violationCount not zero then this is the period over which samples will be used in the violationCount calculation, i.e. samples further in the past than violationTime are not considered.
     * @param operator The operator to be used to perform the check.
     * @param checkReference The value to check against.
     */
    public ReferenceCheckDefinition(String description,
            Severity checkSeverity,
            Duration maxReportingInterval,
            UInteger nominalCount,
            Duration nominalTime,
            UInteger violationCount,
            Duration violationTime,
            ExpressionOperator operator,
            ReferenceValue checkReference) {
        super(description,
            checkSeverity,
            maxReportingInterval,
            nominalCount,
            nominalTime,
            violationCount,
            violationTime);
        this.operator = operator;
        this.checkReference = checkReference;
    }

    @Override
    public Element createElement() {
        return new ReferenceCheckDefinition();
    }

    /**
     * Returns the field operator.
     * 
     * @return The field operator
     */
    public ExpressionOperator getOperator() {
        return operator;
    }

    /**
     * Returns the field checkReference.
     * 
     * @return The field checkReference
     */
    public ReferenceValue getCheckReference() {
        return checkReference;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceCheckDefinition) {
            if (! super.equals(obj)) {
                return false;
            }
            ReferenceCheckDefinition other = (ReferenceCheckDefinition) obj;
            if (operator == null) {
                if (other.operator != null) {
                    return false;
                }
            } else {
                if (! operator.equals(other.operator)) {
                    return false;
                }
            }
            if (checkReference == null) {
                if (other.checkReference != null) {
                    return false;
                }
            } else {
                if (! checkReference.equals(other.checkReference)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + (operator != null ? operator.hashCode() : 0);
        hash = 83 * hash + (checkReference != null ? checkReference.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ReferenceCheckDefinition: ");
        buf.append(super.toString());
        buf.append(", operator=").append(operator);
        buf.append(", checkReference=").append(checkReference);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        super.encode(encoder);
        if (operator == null) {
            throw new MALException("The field 'operator' cannot be null!");
        }
        if (checkReference == null) {
            throw new MALException("The field 'checkReference' cannot be null!");
        }
        encoder.encodeElement(operator);
        encoder.encodeElement(checkReference);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        super.decode(decoder);
        operator = (ExpressionOperator) decoder.decodeElement(ExpressionOperator.EQUAL);
        checkReference = (ReferenceValue) decoder.decodeElement(new ReferenceValue());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
