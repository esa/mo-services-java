package org.ccsds.moims.mo.mps.structures;

/**
 * E1: When the MPS data types are not sufficiently expressive, it is possible
 * to provide an external expression that evaluates into a given data type,
 * using the ExternalExpression data type.  These external expressions are
 * themselves text strings in some external language. The manner in which
 * this expression is evaluated is implementation specific.
 */
public final class ExternalExpression implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1407374900330518L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1407374900330518L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration specifying the data type of the result of the expression.
     */
    private org.ccsds.moims.mo.mps.structures.ArgTypeEnum type;

    /**
     * Defines the expression language used to specify the expression.
     */
    private String expressionLanguage;

    /**
     * The text of the expression.
     */
    private String expression;

    /**
     * Default constructor for ExternalExpression.
     * 
     */
    public ExternalExpression() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param type Enumeration specifying the data type of the result of the expression.
     * @param expressionLanguage Defines the expression language used to specify the expression.
     * @param expression The text of the expression.
     */
    public ExternalExpression(org.ccsds.moims.mo.mps.structures.ArgTypeEnum type,
            String expressionLanguage,
            String expression) {
        this.type = type;
        this.expressionLanguage = expressionLanguage;
        this.expression = expression;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mps.structures.ExternalExpression();
    }

    /**
     * Returns the field type.
     * 
     * @return The field type
     */
    public org.ccsds.moims.mo.mps.structures.ArgTypeEnum getType() {
        return type;
    }

    /**
     * Returns the field expressionLanguage.
     * 
     * @return The field expressionLanguage
     */
    public String getExpressionLanguage() {
        return expressionLanguage;
    }

    /**
     * Returns the field expression.
     * 
     * @return The field expression
     */
    public String getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExternalExpression) {
            ExternalExpression other = (ExternalExpression) obj;
            if (type == null) {
                if (other.type != null) {
                    return false;
                }
            } else {
                if (! type.equals(other.type)) {
                    return false;
                }
            }
            if (expressionLanguage == null) {
                if (other.expressionLanguage != null) {
                    return false;
                }
            } else {
                if (! expressionLanguage.equals(other.expressionLanguage)) {
                    return false;
                }
            }
            if (expression == null) {
                if (other.expression != null) {
                    return false;
                }
            } else {
                if (! expression.equals(other.expression)) {
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
        hash = 83 * hash + (type != null ? type.hashCode() : 0);
        hash = 83 * hash + (expressionLanguage != null ? expressionLanguage.hashCode() : 0);
        hash = 83 * hash + (expression != null ? expression.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ExternalExpression: ");
        buf.append("type=").append(type);
        buf.append(", expressionLanguage=").append(expressionLanguage);
        buf.append(", expression=").append(expression);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (type == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'type' cannot be null!");
        }
        if (expressionLanguage == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'expressionLanguage' cannot be null!");
        }
        if (expression == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'expression' cannot be null!");
        }
        encoder.encodeElement(type);
        encoder.encodeString(expressionLanguage);
        encoder.encodeString(expression);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        type = (org.ccsds.moims.mo.mps.structures.ArgTypeEnum) decoder.decodeElement(org.ccsds.moims.mo.mps.structures.ArgTypeEnum.BLOB);
        expressionLanguage = decoder.decodeString();
        expression = decoder.decodeString();
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
