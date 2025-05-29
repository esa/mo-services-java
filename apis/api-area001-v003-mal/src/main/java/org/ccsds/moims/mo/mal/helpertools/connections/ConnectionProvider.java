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

import org.ccsds.moims.mo.mal.helpertools.helpers.HelperConnections;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperDomain;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Holds the connection details of a service provider.
 */
public class ConnectionProvider {

    private static final ServicesConnectionDetails GLOBAL_PROVIDERS_DETAILS_PRIMARY = new ServicesConnectionDetails();
    private static final ServicesConnectionDetails GLOBAL_PROVIDERS_DETAILS_SECONDARY = new ServicesConnectionDetails();

    private MALContextFactory malFactory;
    private MALContext mal;
    private MALProviderManager providerMgr;
    private MALProvider primaryMALServiceProvider = null;
    private MALProvider secondaryMALServiceProvider = null;
    private final SingleConnectionDetails primaryConnectionDetails = new SingleConnectionDetails();
    private SingleConnectionDetails secondaryConnectionDetails = null;

    /**
     * Getter for the primaryConnectionDetails object.
     *
     * @return The primary connection details.
     */
    public SingleConnectionDetails getConnectionDetails() {
        return primaryConnectionDetails;
    }

    /**
     * Returns the primary connection details.
     *
     * @return The primary connection details.
     */
    public SingleConnectionDetails getPrimaryConnectionDetails() {
        return primaryConnectionDetails;
    }

    /**
     * Returns the secondary connection details.
     *
     * @return The secondary connection details.
     */
    public SingleConnectionDetails getSecondaryConnectionDetails() {
        return secondaryConnectionDetails;
    }

    /**
     * Get primary connection interface details of all providers in the
     * application.
     *
     * @return Primary connection details of all providers in the application.
     */
    public static ServicesConnectionDetails getGlobalProvidersDetailsPrimary() {
        return GLOBAL_PROVIDERS_DETAILS_PRIMARY;
    }

    /**
     * Get secondary connection interface details of all providers in the
     * application.
     *
     * @return Secondary connection details of all providers in the application.
     */
    public static ServicesConnectionDetails getGlobalProvidersDetailsSecondary() {
        return GLOBAL_PROVIDERS_DETAILS_SECONDARY;
    }

    /**
     * Returns the connection details of the inter-process communication (ipc).
     * This is usually the "secondary" connection but if we only have a
     * "primary" connection then this is the one to be returned.
     *
     * @return The ipc connection details
     */
    public SingleConnectionDetails getIPCConnectionDetails() {
        return (secondaryConnectionDetails != null) ? secondaryConnectionDetails : primaryConnectionDetails;
    }

    /**
     * Closes any existing service providers and recreates them. Used to
     * initialize services.
     *
     * @param serviceName Name of the service
     * @param malService MAL service
     * @param handler The handler of the interaction
     * @return The MAL provider
     * @throws MALException On error.
     */
    public MALProvider startService(String serviceName, ServiceInfo malService,
            MALInteractionHandler handler) throws MALException {
        return startService(serviceName, malService, true, handler);
    }

    /**
     * Closes any existing service providers and recreates them. Used to
     * initialize services.
     *
     * @param malService MAL service
     * @param isPublisher Boolean flag to define if the service has PUB-SUB
     * @param handler The handler of the interaction
     * @return The MAL provider
     * @throws MALException On error.
     */
    public MALProvider startService(ServiceInfo malService,
            boolean isPublisher, MALInteractionHandler handler) throws MALException {
        return startService(malService.getName().getValue(), malService, isPublisher, handler);
    }

    /**
     * Closes any existing service providers and recreates them. Used to
     * initialize services.
     *
     * @param serviceName Name of the service
     * @param malService MAL service
     * @param isPublisher Boolean flag to define if the service has PUB-SUB
     * @param handler The handler of the interaction
     * @return The MAL provider
     * @throws MALException On error.
     */
    public MALProvider startService(String serviceName, ServiceInfo malService,
            boolean isPublisher, MALInteractionHandler handler) throws MALException {
        try {
            malFactory = MALContextFactory.newFactory();
        } catch (MALException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.SEVERE,
                    "Check if the MAL implementation is included in your project!! "
                    + "This error usually happens when the MAL layer is missing.", ex);
        }

        mal = malFactory.createMALContext(System.getProperties());
        providerMgr = mal.createProviderManager();

        URI sharedBrokerURI = null;

        if ((null != System.getProperty(HelperMisc.PROPERTY_SHARED_BROKER_URI))) {
            sharedBrokerURI = new URI(System.getProperty(HelperMisc.PROPERTY_SHARED_BROKER_URI));
        }

        final String moAppName = System.getProperty(HelperMisc.PROP_MO_APP_NAME);
        final String uriName = (moAppName != null) ? moAppName + "-" + serviceName : serviceName;  // Create the uri string name

        Properties props = new Properties();
        props.putAll(System.getProperties());

        MALProvider serviceProvider = providerMgr.createProvider(uriName,
                null,
                malService,
                new Blob("".getBytes()),
                handler,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1),
                props,
                isPublisher,
                sharedBrokerURI,
                null);

        IntegerList serviceKey = new IntegerList();
        serviceKey.add((int) malService.getAreaNumber().getValue()); // Area
        serviceKey.add(malService.getServiceNumber().getValue()); // Service
        serviceKey.add((int) malService.getServiceVersion().getValue()); // Version

        primaryConnectionDetails.setProviderURI(serviceProvider.getURI());
        primaryConnectionDetails.setBrokerURI(serviceProvider.getBrokerURI());
        primaryConnectionDetails.setDomain(ConfigurationProviderSingleton.getDomain());
        primaryConnectionDetails.setServiceKey(serviceKey);

        Logger.getLogger(ConnectionProvider.class.getName()).log(Level.FINE,
                "\n" + serviceName + " Service URI        : {0}"
                + "\n" + serviceName + " Service broker URI : {1}"
                + "\n" + serviceName + " Service domain     : {2}"
                + "\n" + serviceName + " Service key        : {3}",
                new Object[]{
                    primaryConnectionDetails.getProviderURI(),
                    primaryConnectionDetails.getBrokerURI(),
                    primaryConnectionDetails.getDomain(),
                    serviceKey
                });

        this.writeURIsOnFile(primaryConnectionDetails,
                serviceName,
                HelperMisc.PROVIDER_URIS_PROPERTIES_FILENAME);

        GLOBAL_PROVIDERS_DETAILS_PRIMARY.add(serviceName, primaryConnectionDetails);
        primaryMALServiceProvider = serviceProvider;

        final String secondaryProtocol = System.getProperty(HelperMisc.SECONDARY_PROTOCOL);

        // Check if the secondary Transport is enabled
        if (secondaryProtocol != null) {
            secondaryConnectionDetails = new SingleConnectionDetails();

            MALProvider serviceProvider2 = providerMgr.createProvider(uriName,
                    secondaryProtocol,
                    malService,
                    new Blob("".getBytes()),
                    handler,
                    new QoSLevel[]{
                        QoSLevel.ASSURED
                    },
                    new UInteger(1),
                    props,
                    isPublisher,
                    sharedBrokerURI,
                    null);

            secondaryConnectionDetails.setProviderURI(serviceProvider2.getURI());
            secondaryConnectionDetails.setBrokerURI(serviceProvider2.getBrokerURI());
            secondaryConnectionDetails.setDomain(ConfigurationProviderSingleton.getDomain());
            secondaryConnectionDetails.setServiceKey(serviceKey);

            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.FINE,
                    "\n" + serviceName + " Service URI        : {0}"
                    + "\n" + serviceName + " Service broker URI : {1}"
                    + "\n" + serviceName + " Service domain     : {2}"
                    + "\n" + serviceName + " Service key        : {3}",
                    new Object[]{
                        secondaryConnectionDetails.getProviderURI(),
                        secondaryConnectionDetails.getBrokerURI(),
                        secondaryConnectionDetails.getDomain(),
                        serviceKey
                    });

            this.writeURIsOnFile(secondaryConnectionDetails,
                    serviceName,
                    HelperMisc.PROVIDER_URIS_SECONDARY_PROPERTIES_FILENAME);

            GLOBAL_PROVIDERS_DETAILS_SECONDARY.add(serviceName, secondaryConnectionDetails);
            secondaryMALServiceProvider = serviceProvider2;
        }

        return serviceProvider;
    }

    /**
     * Closes all running threads and releases the MAL resources. The method has
     * been deprecated and closeAll should be used instead.
     */
    @Deprecated
    public void close() {
        try {
            if (null != providerMgr) {
                providerMgr.close();
            }

            if (null != mal) {
                mal.close();
            }
        } catch (MALException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.WARNING,
                    "Exception during close down of the provider!", ex);
        }
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void closeAll() {
        try {
            if (null != primaryMALServiceProvider) {
                primaryMALServiceProvider.getClass();
                primaryMALServiceProvider.close();
            }

            if (null != secondaryMALServiceProvider) {
                secondaryMALServiceProvider.close();
            }
        } catch (MALException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.WARNING,
                    "Exception during close down of the provider!", ex);
        }

        try {
            if (null != providerMgr) {
                providerMgr.close();
            }

            if (null != mal) {
                mal.close();
            }
        } catch (MALException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.WARNING,
                    "Exception during close down of the provider!", ex);
        }
    }

    /**
     * Indicates whether the URI Files should be initialised. Defaults to false.
     *
     * @return true if URI Files should be initialised
     */
    public static boolean shouldInitUriFiles() {
        String key = HelperMisc.PROP_INIT_URI_FILES;
        return Boolean.parseBoolean(System.getProperty(key, "false"));
    }

    /**
     * Clears the URI links file of the provider
     */
    public static void resetURILinksFile() {
        String filename = HelperMisc.PROVIDER_URIS_PROPERTIES_FILENAME;
        File fileMain = getProviderURIsDirectory(filename);
        BufferedWriter wrt = null;
        try {
            wrt = new BufferedWriter(new FileWriter(fileMain, false));
        } catch (IOException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.WARNING,
                    "Unable to reset URI information from properties file: " + filename, ex);
        } finally {
            if (wrt != null) {
                try {
                    wrt.close();
                } catch (IOException ex) {
                }
            }
        }

        if (System.getProperty(HelperMisc.SECONDARY_PROTOCOL) != null) {
            String filenameSec = HelperMisc.PROVIDER_URIS_SECONDARY_PROPERTIES_FILENAME;
            File fileSec = getProviderURIsDirectory(filenameSec);
            BufferedWriter wrt2 = null;
            try {
                wrt2 = new BufferedWriter(new FileWriter(fileSec, false));
            } catch (IOException ex) {
                Logger.getLogger(ConnectionProvider.class.getName()).log(Level.WARNING,
                        "Unable to reset URI information from properties file: " + filenameSec, ex);
            } finally {
                if (wrt2 != null) {
                    try {
                        wrt2.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }

    private static File getProviderURIsDirectory(String filename) {
        File file = new File(filename);
        if (file.canWrite()) {
            return new File(filename);
        }

        StringBuilder path = new StringBuilder();
        path.append(System.getProperty("user.home"));
        path.append(File.separator);
        path.append(".mo-services");
        File fileInUserDir = new File(path.toString(), filename);
        mkDirAndSetPermissions(fileInUserDir.getParentFile());
        return fileInUserDir;
    }

    private static void mkDirAndSetPermissions(File directory) {
        if (!directory.exists()) {
            // If it does not exist, please check if the parent dir exists
            // because if not, then we also want to create that directory
            // and set the correct permissions
            mkDirAndSetPermissions(directory.getParentFile());

            // We want to give access to both the App itself and the nmf-admin group
            Set<PosixFilePermission> posix = PosixFilePermissions.fromString("rwxrwx---");
            FileAttribute<?> permissions = PosixFilePermissions.asFileAttribute(posix);
            try {
                Files.createDirectory(directory.toPath(), permissions);
            } catch (UnsupportedOperationException ex1) {
                // Probably we are on Windows... Let's create it with:
                directory.mkdirs();
                directory.setExecutable(false, false);
                directory.setExecutable(true, true);
                directory.setReadable(false, false);
                directory.setReadable(true, true);
                directory.setWritable(false, false);
                directory.setWritable(true, true);
            } catch (IOException ex2) {
                Logger.getLogger(ConnectionProvider.class.getName()).log(
                        Level.SEVERE, "Something went wrong...", ex2);
            }
        }
    }

    /**
     * Writes the URIs on a text file
     */
    private void writeURIsOnFile(SingleConnectionDetails connectionDetails, String serviceName, String filename) {
        File file = getProviderURIsDirectory(filename);
        BufferedWriter wrt = null;
        try {
            wrt = new BufferedWriter(new FileWriter(file, true));
            wrt.append(serviceName + HelperConnections.SUFFIX_URI);
            wrt.append("=" + connectionDetails.getProviderURI());
            wrt.newLine();
            wrt.append(serviceName + HelperConnections.SUFFIX_BROKER);
            wrt.append("=" + connectionDetails.getBrokerURI());
            wrt.newLine();
            wrt.append(serviceName + HelperConnections.SUFFIX_DOMAIN);
            wrt.append("=" + HelperDomain.domain2domainId(connectionDetails.getDomain()));
            wrt.newLine();
            wrt.append(serviceName + HelperConnections.SUFFIX_SERVICE_KEY);
            wrt.append("=" + connectionDetails.getServiceKey());
            wrt.newLine();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionProvider.class.getName()).log(Level.WARNING,
                    "Unable to write URI information to properties file: " + file.getAbsolutePath(), ex);
        } finally {
            if (wrt != null) {
                try {
                    wrt.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
