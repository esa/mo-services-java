package org.ccsds.moims.mo.common.configuration.structures;

import org.ccsds.moims.mo.mal.TypeId;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Enumeration;

/**
 * Enumeration class for ConfigurationType.
 */
public final class ConfigurationType extends Enumeration {

    private static final long serialVersionUID = 844446421745668L;
    /**
     * The TypeId of this Element as a long.
     */
    public static final Long SHORT_FORM = 844446421745668L;
    /**
     * The TypeId of this Element.
     */
    public static final TypeId TYPE_ID = new TypeId(SHORT_FORM);

    /**
     * Enumeration value for PROVIDER.
     */
    public static final int PROVIDER_VALUE = 1;

    /**
     * Enumeration singleton for value PROVIDER.
     */
    public static final ConfigurationType PROVIDER = new ConfigurationType(ConfigurationType.PROVIDER_VALUE);

    /**
     * Enumeration value for SERVICE.
     */
    public static final int SERVICE_VALUE = 2;

    /**
     * Enumeration singleton for value SERVICE.
     */
    public static final ConfigurationType SERVICE = new ConfigurationType(ConfigurationType.SERVICE_VALUE);

    /**
     * Set of enumeration instances.
     */
    private static final ConfigurationType[] _ENUMERATIONS = {
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
    public static ConfigurationType fromString(String s) {
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
    public Enumeration fromValue(Integer value) {
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
    public Element createElement() {
        return _ENUMERATIONS[0];
    }

    @Override
    public int getEnumSize() {
        return 2;
    }

    @Override
    public TypeId getTypeId() {
        return TYPE_ID;
    }

}
