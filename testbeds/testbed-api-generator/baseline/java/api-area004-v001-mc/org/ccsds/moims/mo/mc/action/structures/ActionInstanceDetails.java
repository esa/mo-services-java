package org.ccsds.moims.mo.mc.action.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.BooleanList;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mc.structures.AttributeValueList;

/**
 * The ActionInstanceDetails structure holds the information required for
 * an instance of an Action such as the argument values to use.
 */
public final class ActionInstanceDetails implements Composite {

    private static final long serialVersionUID = 1125904218587138L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125904218587138L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The object instance identifier of the ActionDefinition to be used.
     */
    private Long defInstId;

    /**
     * If TRUE, then an activity event of type Execution is required for the STARTED
     * stage.
     */
    private Boolean stageStartedRequired;

    /**
     * If TRUE, then activity events of type Execution are required for the PROGRESS
     * stages.
     */
    private Boolean stageProgressRequired;

    /**
     * If TRUE, then an activity event of type Execution is required for the COMPLETION
     * stage.
     */
    private Boolean stageCompletedRequired;

    /**
     * List containing the values of the arguments. The ordering of the list matches
     * that of the definition. If a value for a particular entry is not being
     * supplied, then its position is filled with a NULL value. If no arguments
     * are defined, then the complete list is replaced with a NULL.
     */
    private AttributeValueList argumentValues;

    /**
     * Optional list of argument definition identifiers. Allows the provider to
     * verify that the correct arguments are being supplied. The ordering of the
     * list matches that of the argument list of the action definition.
     */
    private IdentifierList argumentIds;

    /**
     * Optional list of Booleans that determine whether the supplied argument
     * values are raw or converted. If the Boolean for a particular value is TRUE
     * or NULL then that value is assumed to be raw. If the complete isRawValue
     * list is NULL then all arguments are assumed to be raw values.
     * The ordering of the list matches that of the argument list of the action
     * definition.
     */
    private BooleanList isRawValue;

    /**
     * Default constructor for ActionInstanceDetails.
     * 
     */
    public ActionInstanceDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param defInstId The object instance identifier of the ActionDefinition to be used.
     * @param stageStartedRequired If TRUE, then an activity event of type Execution is required for the STARTED stage.
     * @param stageProgressRequired If TRUE, then activity events of type Execution are required for the PROGRESS stages.
     * @param stageCompletedRequired If TRUE, then an activity event of type Execution is required for the COMPLETION stage.
     * @param argumentValues List containing the values of the arguments. The ordering of the list matches that of the definition. If a value for a particular entry is not being supplied, then its position is filled with a NULL value. If no arguments are defined, then the complete list is replaced with a NULL.
     * @param argumentIds Optional list of argument definition identifiers. Allows the provider to verify that the correct arguments are being supplied. The ordering of the list matches that of the argument list of the action definition.
     * @param isRawValue Optional list of Booleans that determine whether the supplied argument values are raw or converted. If the Boolean for a particular value is TRUE or NULL then that value is assumed to be raw. If the complete isRawValue list is NULL then all arguments are assumed to be raw values.
The ordering of the list matches that of the argument list of the action definition.
     */
    public ActionInstanceDetails(Long defInstId,
            Boolean stageStartedRequired,
            Boolean stageProgressRequired,
            Boolean stageCompletedRequired,
            AttributeValueList argumentValues,
            IdentifierList argumentIds,
            BooleanList isRawValue) {
        this.defInstId = defInstId;
        this.stageStartedRequired = stageStartedRequired;
        this.stageProgressRequired = stageProgressRequired;
        this.stageCompletedRequired = stageCompletedRequired;
        this.argumentValues = argumentValues;
        this.argumentIds = argumentIds;
        this.isRawValue = isRawValue;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param defInstId The object instance identifier of the ActionDefinition to be used.
     * @param stageStartedRequired If TRUE, then an activity event of type Execution is required for the STARTED stage.
     * @param stageProgressRequired If TRUE, then activity events of type Execution are required for the PROGRESS stages.
     * @param stageCompletedRequired If TRUE, then an activity event of type Execution is required for the COMPLETION stage.
     */
    public ActionInstanceDetails(Long defInstId,
            Boolean stageStartedRequired,
            Boolean stageProgressRequired,
            Boolean stageCompletedRequired) {
        this.defInstId = defInstId;
        this.stageStartedRequired = stageStartedRequired;
        this.stageProgressRequired = stageProgressRequired;
        this.stageCompletedRequired = stageCompletedRequired;
        this.argumentValues = null;
        this.argumentIds = null;
        this.isRawValue = null;
    }

    @Override
    public Element createElement() {
        return new ActionInstanceDetails();
    }

    /**
     * Returns the field defInstId.
     * 
     * @return The field defInstId
     */
    public Long getDefInstId() {
        return defInstId;
    }

    /**
     * Returns the field stageStartedRequired.
     * 
     * @return The field stageStartedRequired
     */
    public Boolean getStageStartedRequired() {
        return stageStartedRequired;
    }

    /**
     * Returns the field stageProgressRequired.
     * 
     * @return The field stageProgressRequired
     */
    public Boolean getStageProgressRequired() {
        return stageProgressRequired;
    }

    /**
     * Returns the field stageCompletedRequired.
     * 
     * @return The field stageCompletedRequired
     */
    public Boolean getStageCompletedRequired() {
        return stageCompletedRequired;
    }

    /**
     * Returns the field argumentValues.
     * 
     * @return The field argumentValues
     */
    public AttributeValueList getArgumentValues() {
        return argumentValues;
    }

    /**
     * Returns the field argumentIds.
     * 
     * @return The field argumentIds
     */
    public IdentifierList getArgumentIds() {
        return argumentIds;
    }

    /**
     * Returns the field isRawValue.
     * 
     * @return The field isRawValue
     */
    public BooleanList getIsRawValue() {
        return isRawValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionInstanceDetails) {
            ActionInstanceDetails other = (ActionInstanceDetails) obj;
            if (defInstId == null) {
                if (other.defInstId != null) {
                    return false;
                }
            } else {
                if (! defInstId.equals(other.defInstId)) {
                    return false;
                }
            }
            if (stageStartedRequired == null) {
                if (other.stageStartedRequired != null) {
                    return false;
                }
            } else {
                if (! stageStartedRequired.equals(other.stageStartedRequired)) {
                    return false;
                }
            }
            if (stageProgressRequired == null) {
                if (other.stageProgressRequired != null) {
                    return false;
                }
            } else {
                if (! stageProgressRequired.equals(other.stageProgressRequired)) {
                    return false;
                }
            }
            if (stageCompletedRequired == null) {
                if (other.stageCompletedRequired != null) {
                    return false;
                }
            } else {
                if (! stageCompletedRequired.equals(other.stageCompletedRequired)) {
                    return false;
                }
            }
            if (argumentValues == null) {
                if (other.argumentValues != null) {
                    return false;
                }
            } else {
                if (! argumentValues.equals(other.argumentValues)) {
                    return false;
                }
            }
            if (argumentIds == null) {
                if (other.argumentIds != null) {
                    return false;
                }
            } else {
                if (! argumentIds.equals(other.argumentIds)) {
                    return false;
                }
            }
            if (isRawValue == null) {
                if (other.isRawValue != null) {
                    return false;
                }
            } else {
                if (! isRawValue.equals(other.isRawValue)) {
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
        hash = 83 * hash + (defInstId != null ? defInstId.hashCode() : 0);
        hash = 83 * hash + (stageStartedRequired != null ? stageStartedRequired.hashCode() : 0);
        hash = 83 * hash + (stageProgressRequired != null ? stageProgressRequired.hashCode() : 0);
        hash = 83 * hash + (stageCompletedRequired != null ? stageCompletedRequired.hashCode() : 0);
        hash = 83 * hash + (argumentValues != null ? argumentValues.hashCode() : 0);
        hash = 83 * hash + (argumentIds != null ? argumentIds.hashCode() : 0);
        hash = 83 * hash + (isRawValue != null ? isRawValue.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ActionInstanceDetails: ");
        buf.append("defInstId=").append(defInstId);
        buf.append(", stageStartedRequired=").append(stageStartedRequired);
        buf.append(", stageProgressRequired=").append(stageProgressRequired);
        buf.append(", stageCompletedRequired=").append(stageCompletedRequired);
        buf.append(", argumentValues=").append(argumentValues);
        buf.append(", argumentIds=").append(argumentIds);
        buf.append(", isRawValue=").append(isRawValue);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (defInstId == null) {
            throw new MALException("The field 'defInstId' cannot be null!");
        }
        if (stageStartedRequired == null) {
            throw new MALException("The field 'stageStartedRequired' cannot be null!");
        }
        if (stageProgressRequired == null) {
            throw new MALException("The field 'stageProgressRequired' cannot be null!");
        }
        if (stageCompletedRequired == null) {
            throw new MALException("The field 'stageCompletedRequired' cannot be null!");
        }
        encoder.encodeLong(defInstId);
        encoder.encodeBoolean(stageStartedRequired);
        encoder.encodeBoolean(stageProgressRequired);
        encoder.encodeBoolean(stageCompletedRequired);
        encoder.encodeNullableElement(argumentValues);
        encoder.encodeNullableElement(argumentIds);
        encoder.encodeNullableElement(isRawValue);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        defInstId = decoder.decodeLong();
        stageStartedRequired = decoder.decodeBoolean();
        stageProgressRequired = decoder.decodeBoolean();
        stageCompletedRequired = decoder.decodeBoolean();
        argumentValues = (AttributeValueList) decoder.decodeNullableElement(new AttributeValueList());
        argumentIds = (IdentifierList) decoder.decodeNullableElement(new IdentifierList());
        isRawValue = (BooleanList) decoder.decodeNullableElement(new BooleanList());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
