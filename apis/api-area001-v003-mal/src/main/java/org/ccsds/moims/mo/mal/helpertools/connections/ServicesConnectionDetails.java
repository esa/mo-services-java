/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.helpertools.connections;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperConnections;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperDomain;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;

/**
 * Holds the details of the service connections.
 */
public class ServicesConnectionDetails {

    private HashMap<String, SingleConnectionDetails> services = new HashMap<>();

    public HashMap<String, SingleConnectionDetails> getServices() {
        return this.services;
    }

    public void setServices(HashMap<String, SingleConnectionDetails> services) {
        this.services = services;
    }

    public SingleConnectionDetails get(String string) {
        return services.get(string);
    }

    public SingleConnectionDetails get(Identifier id) {
        return services.get(id.toString());
    }

    public void add(String key, SingleConnectionDetails value) {
        services.put(key, value);
    }

    public void reset() {
        services.clear();
    }

    /**
     * Loads the URIs from the default properties file
     *
     * @return The connection details object generated from the file
     * @throws java.net.MalformedURLException when the MALconsumer is not
     * initialized correctly
     * @throws java.io.FileNotFoundException if the URI file has not been found
     */
    public ServicesConnectionDetails loadURIFromFiles() throws MalformedURLException, FileNotFoundException {
        return this.loadURIFromFiles(null);
    }

    /**
     * Loads the URIs from a selected properties file
     *
     * @param filename The name of the file
     * @return The connection details object generated from the file
     * @throws java.net.MalformedURLException when the MALconsumer is not
     * initialized correctly
     * @throws java.io.FileNotFoundException if the URI file has not been found
     */
    public ServicesConnectionDetails loadURIFromFiles(String filename) throws MalformedURLException, FileNotFoundException {
        if (filename == null) {
            filename = System.getProperty("providerURI.properties",
                    HelperMisc.PROVIDER_URIS_PROPERTIES_FILENAME);
        }

        //File file = getProviderURIsDirectory(filename);
        File configFile = ConnectionProvider.getProviderURIsDirectory(filename);
        if (!configFile.exists()) {
            throw new FileNotFoundException(filename + " not found.");
        }

        Properties uriProps = HelperMisc.loadProperties(configFile.toURI().toURL(), "providerURI.properties");
        return loadURIFromProperties(uriProps);
    }

    /**
     * Loads the URIs from a selected Java properties
     *
     * @param uriProps properties set containing the provider URIs
     * @return The connection details object generated from the file
     * @throws java.net.MalformedURLException when the MALconsumer is not
     * initialized correctly
     */
    private ServicesConnectionDetails loadURIFromProperties(Properties uriProps) throws MalformedURLException {
        // Reading the values out of the properties file
        Set propKeys = uriProps.keySet();
        Object[] array = propKeys.toArray();

        for (int i = 0; i < array.length; i++) {
            String propString = array[i].toString();

            // Is it a URI property of some service?
            if (propString.endsWith(HelperConnections.SUFFIX_URI)) {
                // Remove the URI part of it
                String serviceName = propString.substring(0, propString.length() - HelperConnections.SUFFIX_URI.length());

                // Get the URI + Broker + Domain from the Properties
                String providerURI = uriProps.getProperty(serviceName + HelperConnections.SUFFIX_URI);

                String brokerURI = uriProps.getProperty(serviceName + HelperConnections.SUFFIX_BROKER);

                if ("null".equals(brokerURI)) {
                    brokerURI = null;
                }

                String domainId = uriProps.getProperty(serviceName + HelperConnections.SUFFIX_DOMAIN);
                IdentifierList domain = HelperDomain.domainId2domain(domainId);
                String serviceKeyRaw = uriProps.getProperty(serviceName + HelperConnections.SUFFIX_SERVICE_KEY);
                IntegerList serviceKey = null;

                if (serviceKeyRaw != null) {
                    // 1 in order to remove the '['
                    String[] a = serviceKeyRaw.substring(1, serviceKeyRaw.length() - 1).split(", ");
                    serviceKey = new IntegerList();
                    serviceKey.add(Integer.parseInt(a[0]));
                    serviceKey.add(Integer.parseInt(a[1]));
                    serviceKey.add(Integer.parseInt(a[2]));
                }

                SingleConnectionDetails details = new SingleConnectionDetails(
                        providerURI, brokerURI, domain, serviceKey);

                // Put it in the Hash Map
                services.put(serviceName, details);
            }
        }

        return this;
    }

}
