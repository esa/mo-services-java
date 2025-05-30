/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Common services
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.common.impl.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.common.directory.DirectoryHelper;
import org.ccsds.moims.mo.common.directory.DirectoryServiceInfo;
import org.ccsds.moims.mo.common.directory.body.PublishProviderResponse;
import org.ccsds.moims.mo.common.directory.provider.DirectoryInheritanceSkeleton;
import org.ccsds.moims.mo.common.directory.structures.AddressDetails;
import org.ccsds.moims.mo.common.directory.structures.AddressDetailsList;
import org.ccsds.moims.mo.common.directory.structures.ProviderDetails;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummary;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList;
import org.ccsds.moims.mo.common.directory.structures.PublishDetails;
import org.ccsds.moims.mo.common.directory.structures.ServiceCapability;
import org.ccsds.moims.mo.common.directory.structures.ServiceCapabilityList;
import org.ccsds.moims.mo.common.directory.structures.ServiceFilter;
import org.ccsds.moims.mo.common.structures.ServiceKey;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConfigurationProviderSingleton;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.helpertools.connections.ServicesConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperDomain;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.FileList;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.QoSLevelList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * The Directory service implementation, provider side.
 */
public class DirectoryProviderServiceImpl extends DirectoryInheritanceSkeleton {

    public static final String CHAR_S2G = "s2g";
    private static final Logger LOGGER = Logger.getLogger(DirectoryProviderServiceImpl.class.getName());

    private final Map<Long, PublishDetails> providersAvailable = new ConcurrentHashMap<>();
    private final ConnectionProvider connection = new ConnectionProvider();
    private final AtomicLong counter = new AtomicLong(0);
    private final Object MUTEX = new Object();

    private boolean running = false;
    private MALProvider directoryServiceProvider;

    /**
     * creates the MAL objects, the publisher used to create updates and starts
     * the publishing thread
     *
     * @throws MALException On initialisation error.
     */
    public synchronized void init() throws MALException {
        // shut down old service transport
        if (null != directoryServiceProvider) {
            connection.closeAll();
        }

        directoryServiceProvider = connection.startService(
                DirectoryServiceInfo.DIRECTORY_SERVICE_NAME.toString(),
                DirectoryHelper.DIRECTORY_SERVICE, false, this);

        running = true;
        LOGGER.info("Directory service READY");
    }

    private static AddressDetails getServiceAddressDetails(final SingleConnectionDetails conn) {
        QoSLevelList qos = new QoSLevelList();
        qos.add(QoSLevel.ASSURED);
        NamedValueList qosProperties = new NamedValueList();  // Nothing here for now...

        return new AddressDetails(qos, qosProperties, new UInteger(1),
                conn.getProviderURI(), conn.getBrokerURI(), null);
    }

    private static AddressDetailsList findAddressDetailsListOfService(
            final ServiceKey key, final ServiceCapabilityList capabilities) {
        if (key == null) {
            return null;
        }

        // Iterate all capabilities until you find the serviceName
        for (ServiceCapability capability : capabilities) {
            if (capability != null) {
                if (key.equals(capability.getServiceKey())) {
                    return capability.getServiceAddresses();
                }
            }
        }

        return null; // Not found!
    }

    /**
     * Generates a ServiceKey object from the supplied keys.
     *
     * @param keys The keys to use to generate the ServiceKey.
     * @return The ServiceKey object.
     */
    public static ServiceKey generateServiceKey(final IntegerList keys) {
        return new ServiceKey(new UShort(keys.get(0)), new UShort(keys.get(1)),
                new UOctet(keys.get(2).shortValue()));
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            if (null != directoryServiceProvider) {
                directoryServiceProvider.close();
            }

            connection.closeAll();
            running = false;
        } catch (MALException ex) {
            LOGGER.log(Level.WARNING, "Exception during close down of the provider {0}", ex);
        }
    }

    @Override
    public ConnectionProvider getConnection() {
        return this.connection;
    }

    @Override
    public ProviderSummaryList lookupProvider(final ServiceFilter filter,
            final MALInteraction interaction) throws MALInteractionException, MALException {
        if (filter == null) { // Is the input null?
            throw new IllegalArgumentException("filter argument must not be null");
        }

        final IdentifierList inputDomain = filter.getDomain();

        // Check if the domain contains any wildcard that is not in the end, if so, throw error
        for (int i = 0; i < inputDomain.size(); i++) {
            Identifier domainPart = inputDomain.get(i);

            if (domainPart.toString().equals("*") && i != (inputDomain.size() - 1)) {
                throw new MALInteractionException(new InvalidException(null));
            }
        }

        final HashMap<Long, PublishDetails> list;

        synchronized (MUTEX) {
            list = new HashMap<>(providersAvailable);
        }

        LongList keys = new LongList();
        keys.addAll(list.keySet());

        // Initialize the final Provider Summary List
        ProviderSummaryList outputList = new ProviderSummaryList();

        // Filter...
        for (int i = 0; i < keys.size(); i++) { // Filter through all providers
            PublishDetails provider = list.get(keys.get(i));

            //Check service provider name
            if (!filter.getServiceProviderId().toString().equals("*")) { // If not a wildcard...
                if (!provider.getProviderId().toString().equals(filter.getServiceProviderId().toString())) {
                    continue;
                }
            }

            // Does it contain a wildcard in the filter?
            if (HelperDomain.domainContainsWildcard(filter.getDomain())) {
                // Compare each object one by one...
                if (!HelperDomain.domainMatchesWildcardDomain(provider.getDomain(), inputDomain)) {
                    continue;
                }

            } else if (!inputDomain.equals(provider.getDomain())) {
                continue;
            }

            // Check session type
            if (filter.getSessionType() != null) {
                if (!provider.getSessionType().equals(filter.getSessionType())) {
                    continue;
                }
            }

            // Check session name
            if (!filter.getSessionName().toString().equals("*")) {
                if (!CHAR_S2G.equals(filter.getSessionName().toString())) {
                    if (provider.getSourceSessionName() != null
                            && !provider.getSourceSessionName().toString().equals(filter.getSessionName().toString())) {
                        continue;
                    }
                }
            }

            // Set the Provider Details structure
            ServiceCapabilityList outCap = new ServiceCapabilityList();

            // Check each service
            for (int j = 0; j < provider.getProviderDetails().getServiceCapabilities().size(); j++) { // Go through all the services
                ServiceCapability serviceCapability = provider.getProviderDetails().getServiceCapabilities().get(j);
                ServiceKey fKey = filter.getServiceKey();
                ServiceKey sKey = serviceCapability.getServiceKey();

                // Check service key - area field
                if (fKey.getKeyArea().getValue() != 0) {
                    if (!sKey.getKeyArea().equals(fKey.getKeyArea())) {
                        continue;
                    }
                }

                // Check service key - service field
                if (fKey.getKeyService().getValue() != 0) {
                    if (!sKey.getKeyService().equals(fKey.getKeyService())) {
                        continue;
                    }
                }

                // Check service key - version field
                if (fKey.getKeyAreaVersion().getValue() != 0) {
                    if (!sKey.getKeyAreaVersion().equals(fKey.getKeyAreaVersion())) {
                        continue;
                    }
                }

                // Check service capabilities
                if (!filter.getRequiredCapabilitySets().isEmpty()) { // Not empty...
                    boolean capExists = false;

                    for (UShort cap : filter.getRequiredCapabilitySets()) {
                        // cycle all the ones available in the provider
                        for (UShort proCap : filter.getRequiredCapabilitySets()) {
                            if (cap.equals(proCap)) {
                                capExists = true;
                                break;
                            }
                        }
                    }

                    if (!capExists) { // If the capability we want does not exist, then get out...
                        continue;
                    }
                }

                ServiceCapability newServiceCapability = new ServiceCapability(
                        serviceCapability.getServiceKey(),
                        serviceCapability.getSupportedCapabilitySets(),
                        serviceCapability.getServiceProperties(),
                        new AddressDetailsList());

                // This is a workaround to save bandwidth on the downlink! It is not part of the standard
                if (CHAR_S2G.equals(filter.getSessionName().toString())) {
                    // We assume that we use malspp on the downlink
                    for (int k = 0; k < serviceCapability.getServiceAddresses().size(); k++) {
                        AddressDetails address = serviceCapability.getServiceAddresses().get(k);

                        if (address.getServiceURI().toString().startsWith("malspp")) {
                            newServiceCapability.getServiceAddresses().add(address);
                        }
                    }
                } else {
                    newServiceCapability.getServiceAddresses().addAll(serviceCapability.getServiceAddresses());
                }

                // Add the service to the list of matching services
                outCap.add(newServiceCapability);
            }

            // It passed all the tests!
            final ObjectKey objKey = new ObjectKey(provider.getDomain(), keys.get(i));
            ProviderDetails outProvDetails = new ProviderDetails(outCap, provider.getProviderDetails().getProviderAddresses());
            outputList.add(new ProviderSummary(objKey, provider.getProviderId(), outProvDetails));
        }

        // Errors
        // The operation does not return any errors.
        return outputList;  // requirement: 3.4.9.2.d
    }

    @Override
    public PublishProviderResponse publishProvider(final PublishDetails newProviderDetails,
            final MALInteraction interaction) throws MALInteractionException, MALException {
        Identifier serviceProviderName = newProviderDetails.getProviderId();
        HeterogeneousList objBodies = new HeterogeneousList();
        objBodies.add(serviceProviderName);

        synchronized (MUTEX) {
            final HashMap<Long, PublishDetails> list = new HashMap<>(providersAvailable);

            // Do we already have this provider in the Directory service?
            for (Long key : list.keySet()) {
                PublishDetails provider = this.providersAvailable.get(key);

                if (serviceProviderName.getValue().equals(provider.getProviderId().getValue())) {
                    // It is repeated!!
                    LOGGER.warning("There was already a provider with the same name in the Directory service. "
                            + "Removing the old one and adding the new one...");
                    withdrawProvider(key, null);
                }
            }

            Long servProvObjId = counter.incrementAndGet();
            this.providersAvailable.put(servProvObjId, newProviderDetails);
            return new PublishProviderResponse(servProvObjId, null);
        }
    }

    @Override
    public void withdrawProvider(Long providerObjectKey, MALInteraction interaction) throws MALInteractionException {
        synchronized (MUTEX) {
            if (!this.providersAvailable.containsKey(providerObjectKey)) { // The requested provider does not exist
                throw new MALInteractionException(new UnknownException(null));
            }

            this.providersAvailable.remove(providerObjectKey); // Remove the provider...
        }
    }

    /**
     * Withdraws all providers from the Directory service.
     *
     * @throws MALInteractionException if the providers could not be removed.
     */
    public void withdrawAllProviders() throws MALInteractionException {
        synchronized (MUTEX) {
            for (Long key : providersAvailable.keySet()) {
                withdrawProvider(key, null);
            }
        }
    }

    /**
     * Loads the URIs from the provider and populates the Directory service.
     *
     * @param providerName The name of this provider.
     * @return The details of the URIs for this provider.
     */
    public PublishDetails loadURIs(final String providerName) {
        ServicesConnectionDetails primaryConnectionDetails = ConnectionProvider.getGlobalProvidersDetailsPrimary();
        ServicesConnectionDetails secondaryAddresses = ConnectionProvider.getGlobalProvidersDetailsSecondary();

        // Services' connections
        HashMap<String, SingleConnectionDetails> connsMap = primaryConnectionDetails.getServices();
        Object[] serviceNames = connsMap.keySet().toArray();

        final ServiceCapabilityList capabilities = new ServiceCapabilityList();

        // Iterate all the services and make them available...
        for (Object serviceName : serviceNames) {
            SingleConnectionDetails conn = connsMap.get((String) serviceName);
            AddressDetails serviceAddress = DirectoryProviderServiceImpl.getServiceAddressDetails(conn);
            AddressDetailsList serviceAddresses = new AddressDetailsList();
            serviceAddresses.add(serviceAddress);
            ServiceKey key = DirectoryProviderServiceImpl.generateServiceKey(conn.getServiceKey());
            // "If NULL then all capabilities supported."
            ServiceCapability capability = new ServiceCapability(key, null, new NamedValueList(), serviceAddresses);
            capabilities.add(capability);
        }

        // Second iteration needed here for the secondaryAddresses
        if (secondaryAddresses != null) {
            connsMap = secondaryAddresses.getServices();
            serviceNames = connsMap.keySet().toArray();

            for (Object serviceName : serviceNames) {
                SingleConnectionDetails conn2 = connsMap.get((String) serviceName);
                AddressDetails serviceAddress = DirectoryProviderServiceImpl.getServiceAddressDetails(conn2);
                ServiceKey key2 = DirectoryProviderServiceImpl.generateServiceKey(conn2.getServiceKey());
                AddressDetailsList serviceAddresses = DirectoryProviderServiceImpl.findAddressDetailsListOfService(key2,
                        capabilities);
                ServiceCapability capability;

                if (serviceAddresses == null) { // If not found
                    serviceAddresses = new AddressDetailsList();

                    // Then create a new capability object
                    // "If NULL then all capabilities supported."
                    capability = new ServiceCapability(key2, null, new NamedValueList(), serviceAddresses);
                    capabilities.add(capability);
                }
                serviceAddresses.add(serviceAddress);
            }
        }

        ProviderDetails serviceDetails = new ProviderDetails(capabilities, new AddressDetailsList());

        PublishDetails newProviderDetails = new PublishDetails(new Identifier(providerName),
                ConfigurationProviderSingleton.getDomain(), ConfigurationProviderSingleton.getSession(),
                null, ConfigurationProviderSingleton.getNetwork(), serviceDetails, null);

        try {
            this.publishProvider(newProviderDetails, null);
            return newProviderDetails;
        } catch (MALInteractionException | MALException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public FileList getServiceXML(Long l, MALInteraction mali) throws MALInteractionException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
