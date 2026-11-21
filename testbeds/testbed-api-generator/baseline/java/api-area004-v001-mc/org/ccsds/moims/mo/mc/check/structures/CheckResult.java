package org.ccsds.moims.mo.mc.check.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The CheckResult structure holds basic information about the check state
 * and the value of the parameter at the time of the check. The timestamp
 * of the event is the transition time of the check.
 */
public final class CheckResult implements Composite {

    private static final long serialVersionUID = 1125917103489026L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125917103489026L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The previous evaluation state of the check. Initially UNCHECKED for the
     * first transition of a check. For check evaluations that do not detect a
     * check transition, this value will be the same as the currentCheckState.
     */
    private CheckState previousCheckState;

    /**
     * The current evaluation state of the check.
     */
    private CheckState currentCheckState;

    /**
     * The object instance identifier of the ParameterDefinition used for the
     * check evaluation. NULL if compound check.
     */
    private Long paramDefInstId;

    /**
     * This is the value of the parameter or for a compound check the number of
     * checks in violation at the time of a check state transition, or if it is
     * a report due to the CheckDefinitionDetails maxReportingInterval expiring,
     * then it is the value or the number when the interval expired.
     */
    private Attribute checkedValue;

    /**
     * Default constructor for CheckResult.
     * 
     */
    public CheckResult() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param previousCheckState The previous evaluation state of the check. Initially UNCHECKED for the first transition of a check. For check evaluations that do not detect a check transition, this value will be the same as the currentCheckState.
     * @param currentCheckState The current evaluation state of the check
     * @param paramDefInstId The object instance identifier of the ParameterDefinition used for the check evaluation. NULL if compound check.
     * @param checkedValue This is the value of the parameter or for a compound check the number of checks in violation at the time of a check state transition, or if it is a report due to the CheckDefinitionDetails maxReportingInterval expiring, then it is the value or the number when the interval expired.
     */
    public CheckResult(CheckState previousCheckState,
            CheckState currentCheckState,
            Long paramDefInstId,
            Attribute checkedValue) {
        this.previousCheckState = previousCheckState;
        this.currentCheckState = currentCheckState;
        this.paramDefInstId = paramDefInstId;
        this.checkedValue = checkedValue;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param previousCheckState The previous evaluation state of the check. Initially UNCHECKED for the first transition of a check. For check evaluations that do not detect a check transition, this value will be the same as the currentCheckState.
     * @param currentCheckState The current evaluation state of the check
     */
    public CheckResult(CheckState previousCheckState,
            CheckState currentCheckState) {
        this.previousCheckState = previousCheckState;
        this.currentCheckState = currentCheckState;
        this.paramDefInstId = null;
        this.checkedValue = null;
    }

    @Override
    public Element createElement() {
        return new CheckResult();
    }

    /**
     * Returns the field previousCheckState.
     * 
     * @return The field previousCheckState
     */
    public CheckState getPreviousCheckState() {
        return previousCheckState;
    }

    /**
     * Returns the field currentCheckState.
     * 
     * @return The field currentCheckState
     */
    public CheckState getCurrentCheckState() {
        return currentCheckState;
    }

    /**
     * Returns the field paramDefInstId.
     * 
     * @return The field paramDefInstId
     */
    public Long getParamDefInstId() {
        return paramDefInstId;
    }

    /**
     * Returns the field checkedValue.
     * 
     * @return The field checkedValue
     */
    public Attribute getCheckedValue() {
        return checkedValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CheckResult) {
            CheckResult other = (CheckResult) obj;
            if (previousCheckState == null) {
                if (other.previousCheckState != null) {
                    return false;
                }
            } else {
                if (! previousCheckState.equals(other.previousCheckState)) {
                    return false;
                }
            }
            if (currentCheckState == null) {
                if (other.currentCheckState != null) {
                    return false;
                }
            } else {
                if (! currentCheckState.equals(other.currentCheckState)) {
                    return false;
                }
            }
            if (paramDefInstId == null) {
                if (other.paramDefInstId != null) {
                    return false;
                }
            } else {
                if (! paramDefInstId.equals(other.paramDefInstId)) {
                    return false;
                }
            }
            if (checkedValue == null) {
                if (other.checkedValue != null) {
                    return false;
                }
            } else {
                if (! checkedValue.equals(other.checkedValue)) {
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
        hash = 83 * hash + (previousCheckState != null ? previousCheckState.hashCode() : 0);
        hash = 83 * hash + (currentCheckState != null ? currentCheckState.hashCode() : 0);
        hash = 83 * hash + (paramDefInstId != null ? paramDefInstId.hashCode() : 0);
        hash = 83 * hash + (checkedValue != null ? checkedValue.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(CheckResult: ");
        buf.append("previousCheckState=").append(previousCheckState);
        buf.append(", currentCheckState=").append(currentCheckState);
        buf.append(", paramDefInstId=").append(paramDefInstId);
        buf.append(", checkedValue=").append(checkedValue);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (previousCheckState == null) {
            throw new MALException("The field 'previousCheckState' cannot be null!");
        }
        if (currentCheckState == null) {
            throw new MALException("The field 'currentCheckState' cannot be null!");
        }
        encoder.encodeElement(previousCheckState);
        encoder.encodeElement(currentCheckState);
        encoder.encodeNullableLong(paramDefInstId);
        encoder.encodeNullableAttribute(checkedValue);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        previousCheckState = (CheckState) decoder.decodeElement(CheckState.DISABLED);
        currentCheckState = (CheckState) decoder.decodeElement(CheckState.DISABLED);
        paramDefInstId = decoder.decodeNullableLong();
        checkedValue = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
