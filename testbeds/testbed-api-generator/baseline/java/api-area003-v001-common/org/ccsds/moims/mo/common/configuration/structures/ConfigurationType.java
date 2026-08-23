package org.ccsds.moims.mo.common.configuration.structures;

/**
 * Enumeration class for ConfigurationType.
 */
public final class ConfigurationType extends org.ccsds.moims.mo.mal.structures.Enumeration {

    private static final long serialVersionUID = 844446421745668L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745668L;
    /**
     * The TypeId of this Element.
     */
    public static final org.ccsds.moims.mo.mal.TypeId TYPE_ID = new org.ccsds.moims.mo.mal.TypeId(SHORT_FORM);

    /**
     * Enumeration value for PROVIDER.
     */
    public static final int PROVIDER_VALUE = 1;

    /**
     * Enumeration singleton for value PROVIDER.
     */
    public static final org.ccsds.moims.mo.common.configuration.structures.ConfigurationType PROVIDER = new org.ccsds.moims.mo.common.configuration.structures.ConfigurationType(org.ccsds.moims.mo.common.configuration.structures.ConfigurationType.PROVIDER_VALUE);

    /**
     * Enumeration value for SERVICE.
     */
    public static final int SERVICE_VALUE = 2;

    /**
     * Enumeration singleton for value SERVICE.
     */
    public static final org.ccsds.moims.mo.common.configuration.structures.ConfigurationType SERVICE = new org.ccsds.moims.mo.common.configuration.structures.ConfigurationType(org.ccsds.moims.mo.common.configuration.structures.ConfigurationType.SERVICE_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final org.ccsds.moims.mo.common.configuration.structures.ConfigurationType[] _ENUMERATIONS = {
        PROVIDER, SERVICE};

    /**
     * The configuration type enumeration holds the possible types of a configuration.
     */
    public ConfigurationType() {
        super(-1);
    }

    /**
     * The configuration type enumeration holds the possible types of a configuration.
     * 
     * @param value The value of the Enumeration.
     */
    public ConfigurationType(int value) {
        super(value);
    }

    @Override
    public String toString() {
        switch (getValue()) {
            case PROVIDER_VALUE:
                return "PROVIDER";
            case SERVICE_VALUE:
                return "SERVICE";
            default:
                throw new RuntimeException("Unknown ordinal!");
        }
    }

    /**
     * Returns the enumeration element represented by the supplied string, or
     * null if not matched.
     * 
     * @param s s The string to search for.
     * @return The matched enumeration element, or null if not matched.
     */
    public static org.ccsds.moims.mo.common.configuration.structures.ConfigurationType fromString(String s) {
        switch (s) {
            case "PROVIDER":
                return ConfigurationType.PROVIDER;
            case "SERVICE":
                return ConfigurationType.SERVICE;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided string: " + s);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Enumeration fromValue(Integer value) {
        switch (value) {
            case PROVIDER_VALUE:
                return ConfigurationType.PROVIDER;
            case SERVICE_VALUE:
                return ConfigurationType.SERVICE;
            default:
                throw new RuntimeException("Unknown Enumeration for the provided value: " + value);
        }
    }

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 2;
    }

    @Override
    public org.ccsds.moims.mo.mal.TypeId getTypeId() {
        return TYPE_ID;
    }

}
