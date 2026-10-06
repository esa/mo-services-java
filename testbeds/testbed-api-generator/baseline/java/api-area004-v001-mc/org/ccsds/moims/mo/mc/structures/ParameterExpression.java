package org.ccsds.moims.mo.mc.structures;

import org.ccsds.moims.mo.com.archive.structures.ExpressionOperator;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * The ParameterExpression structure represents a simple expression between
 * a parameter and a value for that parameter.
 */
public final class ParameterExpression implements Composite {

    private static final long serialVersionUID = 1125899923619844L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125899923619844L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Holds the object instance identifier of the ParameterIdentity object to
     * be used in the expression.
     */
    private ObjectKey parameterId;

    /**
     * The expression operator.
     */
    private ExpressionOperator operator;

    /**
     * If set to TRUE the converted value field of the parameter value should
     * be used, otherwise the raw value field should be used.
     */
    private Boolean useConverted;

    /**
     * The value to be used in the expression.
     */
    private Attribute value;

    /**
     * Default constructor for ParameterExpression.
     * 
     */
    public ParameterExpression() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param parameterId Holds the object instance identifier of the ParameterIdentity object to be used in the expression.
     * @param operator The expression operator.
     * @param useConverted If set to TRUE the converted value field of the parameter value should be used, otherwise the raw value field should be used.
     * @param value The value to be used in the expression.
     */
    public ParameterExpression(ObjectKey parameterId,
            ExpressionOperator operator,
            Boolean useConverted,
            Attribute value) {
        this.parameterId = parameterId;
        this.operator = operator;
        this.useConverted = useConverted;
        this.value = value;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param parameterId Holds the object instance identifier of the ParameterIdentity object to be used in the expression.
     * @param operator The expression operator.
     * @param useConverted If set to TRUE the converted value field of the parameter value should be used, otherwise the raw value field should be used.
     */
    public ParameterExpression(ObjectKey parameterId,
            ExpressionOperator operator,
            Boolean useConverted) {
        this.parameterId = parameterId;
        this.operator = operator;
        this.useConverted = useConverted;
        this.value = null;
    }

    @Override
    public Element createElement() {
        return new ParameterExpression();
    }

    /**
     * Returns the field parameterId.
     * 
     * @return The field parameterId
     */
    public ObjectKey getParameterId() {
        return parameterId;
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
     * Returns the field useConverted.
     * 
     * @return The field useConverted
     */
    public Boolean getUseConverted() {
        return useConverted;
    }

    /**
     * Returns the field value.
     * 
     * @return The field value
     */
    public Attribute getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterExpression) {
            ParameterExpression other = (ParameterExpression) obj;
            if (parameterId == null) {
                if (other.parameterId != null) {
                    return false;
                }
            } else {
                if (! parameterId.equals(other.parameterId)) {
                    return false;
                }
            }
            if (operator == null) {
                if (other.operator != null) {
                    return false;
                }
            } else {
                if (! operator.equals(other.operator)) {
                    return false;
                }
            }
            if (useConverted == null) {
                if (other.useConverted != null) {
                    return false;
                }
            } else {
                if (! useConverted.equals(other.useConverted)) {
                    return false;
                }
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            } else {
                if (! value.equals(other.value)) {
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
        hash = 83 * hash + (parameterId != null ? parameterId.hashCode() : 0);
        hash = 83 * hash + (operator != null ? operator.hashCode() : 0);
        hash = 83 * hash + (useConverted != null ? useConverted.hashCode() : 0);
        hash = 83 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterExpression: ");
        buf.append("parameterId=").append(parameterId);
        buf.append(", operator=").append(operator);
        buf.append(", useConverted=").append(useConverted);
        buf.append(", value=").append(value);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (parameterId == null) {
            throw new MALException("The field 'parameterId' cannot be null!");
        }
        if (operator == null) {
            throw new MALException("The field 'operator' cannot be null!");
        }
        if (useConverted == null) {
            throw new MALException("The field 'useConverted' cannot be null!");
        }
        encoder.encodeElement(parameterId);
        encoder.encodeElement(operator);
        encoder.encodeBoolean(useConverted);
        encoder.encodeNullableAttribute(value);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        parameterId = (ObjectKey) decoder.decodeElement(new ObjectKey());
        operator = (ExpressionOperator) decoder.decodeElement(ExpressionOperator.EQUAL);
        useConverted = decoder.decodeBoolean();
        value = (Attribute) decoder.decodeNullableAttribute();
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
