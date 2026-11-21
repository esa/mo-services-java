package org.ccsds.moims.mo.mc.parameter.structures;

import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mc.structures.ParameterExpression;

/**
 * The ParameterDefinitionDetails structure holds a parameter definition.
 * The conversion field defines the conditions where the relevant conversion
 * is applied. For onboard parameters, the report interval should be a multiple
 * of the minimum sampling interval of that parameter.
 */
public final class ParameterDefinitionDetails implements Composite {

    private static final long serialVersionUID = 1125908513554433L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125908513554433L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * The description of the parameter. May be empty.
     */
    private String description;

    /**
     * Holds the attribute short form part of the raw type of the parameter, e.g.,
     * for a MAL::String parameter it shall hold 15.
     */
    private Byte rawType;

    /**
     * The unit for the raw value. If NULL then raw type has no unit.
     */
    private String rawUnit;

    /**
     * Controls whether reports for this parameter are to be generated.
     */
    private Boolean generationEnabled;

    /**
     * Periodic report interval. No periodic reports to be generated if this is
     * set to &quot;0&quot;.
     */
    private Duration reportInterval;

    /**
     * Expression that determines this parameter&quot;s validity state. Can be
     * NULL if no validity check is required or validity is calculated by implementation-specific
     * mechanisms.
     */
    private ParameterExpression validityExpression;

    /**
     * If present then parameter has a converted type.
     */
    private ParameterConversion conversion;

    /**
     * Default constructor for ParameterDefinitionDetails.
     * 
     */
    public ParameterDefinitionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description The description of the parameter. May be empty.
     * @param rawType Holds the attribute short form part of the raw type of the parameter, e.g., for a MAL::String parameter it shall hold 15.
     * @param rawUnit The unit for the raw value. If NULL then raw type has no unit.
     * @param generationEnabled Controls whether reports for this parameter are to be generated.
     * @param reportInterval Periodic report interval. No periodic reports to be generated if this is set to '0'.
     * @param validityExpression Expression that determines this parameter's validity state. Can be NULL if no validity check is required or validity is calculated by implementation-specific mechanisms.
     * @param conversion If present then parameter has a converted type.
     */
    public ParameterDefinitionDetails(String description,
            Byte rawType,
            String rawUnit,
            Boolean generationEnabled,
            Duration reportInterval,
            ParameterExpression validityExpression,
            ParameterConversion conversion) {
        this.description = description;
        this.rawType = rawType;
        this.rawUnit = rawUnit;
        this.generationEnabled = generationEnabled;
        this.reportInterval = reportInterval;
        this.validityExpression = validityExpression;
        this.conversion = conversion;
    }

    /**
     * Constructor that initialises the non-nullable values of the structure.
     * 
     * @param description The description of the parameter. May be empty.
     * @param rawType Holds the attribute short form part of the raw type of the parameter, e.g., for a MAL::String parameter it shall hold 15.
     * @param generationEnabled Controls whether reports for this parameter are to be generated.
     * @param reportInterval Periodic report interval. No periodic reports to be generated if this is set to '0'.
     */
    public ParameterDefinitionDetails(String description,
            Byte rawType,
            Boolean generationEnabled,
            Duration reportInterval) {
        this.description = description;
        this.rawType = rawType;
        this.rawUnit = null;
        this.generationEnabled = generationEnabled;
        this.reportInterval = reportInterval;
        this.validityExpression = null;
        this.conversion = null;
    }

    @Override
    public Element createElement() {
        return new ParameterDefinitionDetails();
    }

    /**
     * Returns the field description.
     * 
     * @return The field description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the field rawType.
     * 
     * @return The field rawType
     */
    public Byte getRawType() {
        return rawType;
    }

    /**
     * Returns the field rawUnit.
     * 
     * @return The field rawUnit
     */
    public String getRawUnit() {
        return rawUnit;
    }

    /**
     * Returns the field generationEnabled.
     * 
     * @return The field generationEnabled
     */
    public Boolean getGenerationEnabled() {
        return generationEnabled;
    }

    /**
     * Returns the field reportInterval.
     * 
     * @return The field reportInterval
     */
    public Duration getReportInterval() {
        return reportInterval;
    }

    /**
     * Returns the field validityExpression.
     * 
     * @return The field validityExpression
     */
    public ParameterExpression getValidityExpression() {
        return validityExpression;
    }

    /**
     * Returns the field conversion.
     * 
     * @return The field conversion
     */
    public ParameterConversion getConversion() {
        return conversion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParameterDefinitionDetails) {
            ParameterDefinitionDetails other = (ParameterDefinitionDetails) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (rawType == null) {
                if (other.rawType != null) {
                    return false;
                }
            } else {
                if (! rawType.equals(other.rawType)) {
                    return false;
                }
            }
            if (rawUnit == null) {
                if (other.rawUnit != null) {
                    return false;
                }
            } else {
                if (! rawUnit.equals(other.rawUnit)) {
                    return false;
                }
            }
            if (generationEnabled == null) {
                if (other.generationEnabled != null) {
                    return false;
                }
            } else {
                if (! generationEnabled.equals(other.generationEnabled)) {
                    return false;
                }
            }
            if (reportInterval == null) {
                if (other.reportInterval != null) {
                    return false;
                }
            } else {
                if (! reportInterval.equals(other.reportInterval)) {
                    return false;
                }
            }
            if (validityExpression == null) {
                if (other.validityExpression != null) {
                    return false;
                }
            } else {
                if (! validityExpression.equals(other.validityExpression)) {
                    return false;
                }
            }
            if (conversion == null) {
                if (other.conversion != null) {
                    return false;
                }
            } else {
                if (! conversion.equals(other.conversion)) {
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
        hash = 83 * hash + (description != null ? description.hashCode() : 0);
        hash = 83 * hash + (rawType != null ? rawType.hashCode() : 0);
        hash = 83 * hash + (rawUnit != null ? rawUnit.hashCode() : 0);
        hash = 83 * hash + (generationEnabled != null ? generationEnabled.hashCode() : 0);
        hash = 83 * hash + (reportInterval != null ? reportInterval.hashCode() : 0);
        hash = 83 * hash + (validityExpression != null ? validityExpression.hashCode() : 0);
        hash = 83 * hash + (conversion != null ? conversion.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(ParameterDefinitionDetails: ");
        buf.append("description=").append(description);
        buf.append(", rawType=").append(rawType);
        buf.append(", rawUnit=").append(rawUnit);
        buf.append(", generationEnabled=").append(generationEnabled);
        buf.append(", reportInterval=").append(reportInterval);
        buf.append(", validityExpression=").append(validityExpression);
        buf.append(", conversion=").append(conversion);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(MALEncoder encoder) throws MALException {
        if (description == null) {
            throw new MALException("The field 'description' cannot be null!");
        }
        if (rawType == null) {
            throw new MALException("The field 'rawType' cannot be null!");
        }
        if (generationEnabled == null) {
            throw new MALException("The field 'generationEnabled' cannot be null!");
        }
        if (reportInterval == null) {
            throw new MALException("The field 'reportInterval' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeOctet(rawType);
        encoder.encodeNullableString(rawUnit);
        encoder.encodeBoolean(generationEnabled);
        encoder.encodeDuration(reportInterval);
        encoder.encodeNullableElement(validityExpression);
        encoder.encodeNullableElement(conversion);
    }

    @Override
    public Element decode(MALDecoder decoder) throws MALException {
        description = decoder.decodeString();
        rawType = decoder.decodeOctet();
        rawUnit = decoder.decodeNullableString();
        generationEnabled = decoder.decodeBoolean();
        reportInterval = decoder.decodeDuration();
        validityExpression = (ParameterExpression) decoder.decodeNullableElement(new ParameterExpression());
        conversion = (ParameterConversion) decoder.decodeNullableElement(new ParameterConversion());
        return this;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
