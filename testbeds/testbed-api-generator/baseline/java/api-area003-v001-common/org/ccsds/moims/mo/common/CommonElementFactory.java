package org.ccsds.moims.mo.common;

import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetails;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetailsList;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSet;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationType;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationTypeList;
import org.ccsds.moims.mo.common.configuration.structures.ServiceConfigurationIdentifier;
import org.ccsds.moims.mo.common.configuration.structures.ServiceConfigurationIdentifierList;
import org.ccsds.moims.mo.common.directory.structures.AddressDetails;
import org.ccsds.moims.mo.common.directory.structures.AddressDetailsList;
import org.ccsds.moims.mo.common.directory.structures.ProviderDetails;
import org.ccsds.moims.mo.common.directory.structures.ProviderDetailsList;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummary;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList;
import org.ccsds.moims.mo.common.directory.structures.PublishDetails;
import org.ccsds.moims.mo.common.directory.structures.PublishDetailsList;
import org.ccsds.moims.mo.common.directory.structures.ServiceCapability;
import org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList;
import org.ccsds.moims.mo.common.directory.structures.ServiceFilter;
import org.ccsds.moims.mo.common.directory.structures.ServiceFilterList;
import org.ccsds.moims.mo.common.login.structures.Profile;
import org.ccsds.moims.mo.common.login.structures.ProfileList;
import org.ccsds.moims.mo.common.structures.ServiceKey;
import org.ccsds.moims.mo.common.structures.ServiceKeyList;
import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Creates the Elements of the Common area, without holding an instance of
 * each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class CommonElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
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
    private static Element createAreaElement(int typeNumber) {
        switch (typeNumber) {
            case -1: return new ServiceKeyList();
            case 1: return new ServiceKey();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Directory service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createDirectoryElement(int typeNumber) {
        switch (typeNumber) {
            case -7: return new ServiceFilterList();
            case -6: return new PublishDetailsList();
            case -5: return new ProviderSummaryList();
            case -4: return new AddressDetailsList();
            case -2: return new ServiceCapabilityList();
            case -1: return new ProviderDetailsList();
            case 1: return new ProviderDetails();
            case 2: return new ServiceCapability();
            case 4: return new AddressDetails();
            case 5: return new ProviderSummary();
            case 6: return new PublishDetails();
            case 7: return new ServiceFilter();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Login service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createLoginElement(int typeNumber) {
        switch (typeNumber) {
            case -1: return new ProfileList();
            case 1: return new Profile();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the Configuration service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createConfigurationElement(int typeNumber) {
        switch (typeNumber) {
            case -4: return new ConfigurationTypeList();
            case -3: return new ServiceConfigurationIdentifierList();
            case -2: return new ConfigurationObjectDetailsList();
            case -1: return new ConfigurationObjectSetList();
            case 1: return new ConfigurationObjectSet();
            case 2: return new ConfigurationObjectDetails();
            case 3: return new ServiceConfigurationIdentifier();
            case 4: return new ConfigurationType();
            default: return null;
        }
    }

}
