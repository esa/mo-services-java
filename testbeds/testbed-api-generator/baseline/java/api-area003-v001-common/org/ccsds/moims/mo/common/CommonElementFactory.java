package org.ccsds.moims.mo.common;

/**
 * Creates the Elements of the Common area, without holding an instance of
 * each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class CommonElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
            int typeNumber) {
        switch (serviceNumber) {
            case 0: return createAreaElement(typeNumber);
            case 1: return createDirectoryElement(typeNumber);
            case 2: return createLoginElement(typeNumber);
            case 5: return createConfigurationElement(typeNumber);
            default: return null;
        }
    }

    @Override
    public int getAreaNumber() {
        return 3;
    }

    @Override
    public int getAreaVersion() {
        return 1;
    }

    /**
     * Creates an Element declared by the area itself.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createAreaElement(int typeNumber) {
        switch (typeNumber) {
            case -1: return new org.ccsds.moims.mo.common.structures.ServiceKeyList();
            case 1: return new org.ccsds.moims.mo.common.structures.ServiceKey();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Directory service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createDirectoryElement(int typeNumber) {
        switch (typeNumber) {
            case -7: return new org.ccsds.moims.mo.common.directory.structures.ServiceFilterList();
            case -6: return new org.ccsds.moims.mo.common.directory.structures.PublishDetailsList();
            case -5: return new org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList();
            case -4: return new org.ccsds.moims.mo.common.directory.structures.AddressDetailsList();
            case -2: return new org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList();
            case -1: return new org.ccsds.moims.mo.common.directory.structures.ProviderDetailsList();
            case 1: return new org.ccsds.moims.mo.common.directory.structures.ProviderDetails();
            case 2: return new org.ccsds.moims.mo.common.directory.structures.ServiceCapability();
            case 4: return new org.ccsds.moims.mo.common.directory.structures.AddressDetails();
            case 5: return new org.ccsds.moims.mo.common.directory.structures.ProviderSummary();
            case 6: return new org.ccsds.moims.mo.common.directory.structures.PublishDetails();
            case 7: return new org.ccsds.moims.mo.common.directory.structures.ServiceFilter();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Login service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createLoginElement(int typeNumber) {
        switch (typeNumber) {
            case -1: return new org.ccsds.moims.mo.common.login.structures.ProfileList();
            case 1: return new org.ccsds.moims.mo.common.login.structures.Profile();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Configuration service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createConfigurationElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationTypeList();
            case -3: return new org.ccsds.moims.mo.common.configuration.structures.ServiceConfigurationIdentifierList();
            case -2: return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetailsList();
            case -1: return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList();
            case 1: return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSet();
            case 2: return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetails();
            case 3: return new org.ccsds.moims.mo.common.configuration.structures.ServiceConfigurationIdentifier();
            case 4: return new org.ccsds.moims.mo.common.configuration.structures.ConfigurationType();
            default: return null;
        }
    }

}
