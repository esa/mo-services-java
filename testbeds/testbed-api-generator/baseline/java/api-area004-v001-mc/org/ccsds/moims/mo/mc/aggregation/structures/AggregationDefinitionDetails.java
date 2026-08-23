package org.ccsds.moims.mo.mc.aggregation.structures;

/**
 * The AggregationDefinitionDetails structure holds definition details of
 * an aggregation.
 */
public final class AggregationDefinitionDetails implements org.ccsds.moims.mo.mal.structures.Composite {

    private static final long serialVersionUID = 1125925693423617L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 1125925693423617L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * The description of the parameter. May be empty.
     */
    private String description;

    /**
     * Category of the aggregation. Value taken from AggregationCategory enumeration,
     * although the use of a UOctet allows deployment specific extension. Extensions
     * must use values greater than 127.
     */
    private org.ccsds.moims.mo.mal.structures.UOctet category;

    /**
     * The interval between periodic reports on this aggregation. If this aggregation
     * is not periodic, this field must be &quot;0&quot;.
     */
    private org.ccsds.moims.mo.mal.structures.Duration reportInterval;

    /**
     * If TRUE reports will include all values regardless of whether changed,
     * if FALSE values unchanged from previous report are replaced with a NULL.
     */
    private Boolean sendUnchanged;

    /**
     * If TRUE reports will include the ParameterDefinition object instance identifier
     * in the AggregationParameterValue, if FALSE it will be set to NULL.
     */
    private Boolean sendDefinitions;

    /**
     * Controls whether reports for this aggregation are to be filtered.
     */
    private Boolean filterEnabled;

    /**
     * The maximum duration between filtered reports. If this value is exceeded,
     * then a report is sent regardless of filtered thresholds. Ignored if not
     * filtered.
     */
    private org.ccsds.moims.mo.mal.structures.Duration filteredTimeout;

    /**
     * Controls whether reports for this aggregation are to be generated.
     */
    private Boolean generationEnabled;

    /**
     * List containing the parameter sets which define the aggregation.
     */
    private org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSetList parameterSets;

    /**
     * Default constructor for AggregationDefinitionDetails.
     * 
     */
    public AggregationDefinitionDetails() {
    }

    /**
     * Constructor that initialises the values of the structure.
     * 
     * @param description The description of the parameter. May be empty.
     * @param category Category of the aggregation. Value taken from AggregationCategory enumeration, although the use of a UOctet allows deployment specific extension. Extensions must use values greater than 127.
     * @param reportInterval The interval between periodic reports on this aggregation. If this aggregation is not periodic, this field must be '0'.
     * @param sendUnchanged If TRUE reports will include all values regardless of whether changed, if FALSE values unchanged from previous report are replaced with a NULL.
     * @param sendDefinitions If TRUE reports will include the ParameterDefinition object instance identifier in the AggregationParameterValue, if FALSE it will be set to NULL.
     * @param filterEnabled Controls whether reports for this aggregation are to be filtered.
     * @param filteredTimeout The maximum duration between filtered reports. If this value is exceeded, then a report is sent regardless of filtered thresholds. Ignored if not filtered.
     * @param generationEnabled Controls whether reports for this aggregation are to be generated.
     * @param parameterSets List containing the parameter sets which define the aggregation.
     */
    public AggregationDefinitionDetails(String description,
            org.ccsds.moims.mo.mal.structures.UOctet category,
            org.ccsds.moims.mo.mal.structures.Duration reportInterval,
            Boolean sendUnchanged,
            Boolean sendDefinitions,
            Boolean filterEnabled,
            org.ccsds.moims.mo.mal.structures.Duration filteredTimeout,
            Boolean generationEnabled,
            org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSetList parameterSets) {
        this.description = description;
        this.category = category;
        this.reportInterval = reportInterval;
        this.sendUnchanged = sendUnchanged;
        this.sendDefinitions = sendDefinitions;
        this.filterEnabled = filterEnabled;
        this.filteredTimeout = filteredTimeout;
        this.generationEnabled = generationEnabled;
        this.parameterSets = parameterSets;
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return new org.ccsds.moims.mo.mc.aggregation.structures.AggregationDefinitionDetails();
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
     * Returns the field category.
     * 
     * @return The field category
     */
    public org.ccsds.moims.mo.mal.structures.UOctet getCategory() {
        return category;
    }

    /**
     * Returns the field reportInterval.
     * 
     * @return The field reportInterval
     */
    public org.ccsds.moims.mo.mal.structures.Duration getReportInterval() {
        return reportInterval;
    }

    /**
     * Returns the field sendUnchanged.
     * 
     * @return The field sendUnchanged
     */
    public Boolean getSendUnchanged() {
        return sendUnchanged;
    }

    /**
     * Returns the field sendDefinitions.
     * 
     * @return The field sendDefinitions
     */
    public Boolean getSendDefinitions() {
        return sendDefinitions;
    }

    /**
     * Returns the field filterEnabled.
     * 
     * @return The field filterEnabled
     */
    public Boolean getFilterEnabled() {
        return filterEnabled;
    }

    /**
     * Returns the field filteredTimeout.
     * 
     * @return The field filteredTimeout
     */
    public org.ccsds.moims.mo.mal.structures.Duration getFilteredTimeout() {
        return filteredTimeout;
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
     * Returns the field parameterSets.
     * 
     * @return The field parameterSets
     */
    public org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSetList getParameterSets() {
        return parameterSets;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AggregationDefinitionDetails) {
            AggregationDefinitionDetails other = (AggregationDefinitionDetails) obj;
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            } else {
                if (! description.equals(other.description)) {
                    return false;
                }
            }
            if (category == null) {
                if (other.category != null) {
                    return false;
                }
            } else {
                if (! category.equals(other.category)) {
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
            if (sendUnchanged == null) {
                if (other.sendUnchanged != null) {
                    return false;
                }
            } else {
                if (! sendUnchanged.equals(other.sendUnchanged)) {
                    return false;
                }
            }
            if (sendDefinitions == null) {
                if (other.sendDefinitions != null) {
                    return false;
                }
            } else {
                if (! sendDefinitions.equals(other.sendDefinitions)) {
                    return false;
                }
            }
            if (filterEnabled == null) {
                if (other.filterEnabled != null) {
                    return false;
                }
            } else {
                if (! filterEnabled.equals(other.filterEnabled)) {
                    return false;
                }
            }
            if (filteredTimeout == null) {
                if (other.filteredTimeout != null) {
                    return false;
                }
            } else {
                if (! filteredTimeout.equals(other.filteredTimeout)) {
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
            if (parameterSets == null) {
                if (other.parameterSets != null) {
                    return false;
                }
            } else {
                if (! parameterSets.equals(other.parameterSets)) {
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
        hash = 83 * hash + (category != null ? category.hashCode() : 0);
        hash = 83 * hash + (reportInterval != null ? reportInterval.hashCode() : 0);
        hash = 83 * hash + (sendUnchanged != null ? sendUnchanged.hashCode() : 0);
        hash = 83 * hash + (sendDefinitions != null ? sendDefinitions.hashCode() : 0);
        hash = 83 * hash + (filterEnabled != null ? filterEnabled.hashCode() : 0);
        hash = 83 * hash + (filteredTimeout != null ? filteredTimeout.hashCode() : 0);
        hash = 83 * hash + (generationEnabled != null ? generationEnabled.hashCode() : 0);
        hash = 83 * hash + (parameterSets != null ? parameterSets.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(AggregationDefinitionDetails: ");
        buf.append("description=").append(description);
        buf.append(", category=").append(category);
        buf.append(", reportInterval=").append(reportInterval);
        buf.append(", sendUnchanged=").append(sendUnchanged);
        buf.append(", sendDefinitions=").append(sendDefinitions);
        buf.append(", filterEnabled=").append(filterEnabled);
        buf.append(", filteredTimeout=").append(filteredTimeout);
        buf.append(", generationEnabled=").append(generationEnabled);
        buf.append(", parameterSets=").append(parameterSets);
        buf.append(')');
        return buf.toString();
    }

    @Override
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        if (description == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'description' cannot be null!");
        }
        if (category == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'category' cannot be null!");
        }
        if (reportInterval == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'reportInterval' cannot be null!");
        }
        if (sendUnchanged == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'sendUnchanged' cannot be null!");
        }
        if (sendDefinitions == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'sendDefinitions' cannot be null!");
        }
        if (filterEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'filterEnabled' cannot be null!");
        }
        if (filteredTimeout == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'filteredTimeout' cannot be null!");
        }
        if (generationEnabled == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'generationEnabled' cannot be null!");
        }
        if (parameterSets == null) {
            throw new org.ccsds.moims.mo.mal.MALException("The field 'parameterSets' cannot be null!");
        }
        encoder.encodeString(description);
        encoder.encodeUOctet(category);
        encoder.encodeDuration(reportInterval);
        encoder.encodeBoolean(sendUnchanged);
        encoder.encodeBoolean(sendDefinitions);
        encoder.encodeBoolean(filterEnabled);
        encoder.encodeDuration(filteredTimeout);
        encoder.encodeBoolean(generationEnabled);
        encoder.encodeElement(parameterSets);
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        description = decoder.decodeString();
        category = decoder.decodeUOctet();
        reportInterval = decoder.decodeDuration();
        sendUnchanged = decoder.decodeBoolean();
        sendDefinitions = decoder.decodeBoolean();
        filterEnabled = decoder.decodeBoolean();
        filteredTimeout = decoder.decodeDuration();
        generationEnabled = decoder.decodeBoolean();
        parameterSets = (org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSetList) decoder.decodeElement(new org.ccsds.moims.mo.mc.aggregation.structures.AggregationParameterSetList());
        return this;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
