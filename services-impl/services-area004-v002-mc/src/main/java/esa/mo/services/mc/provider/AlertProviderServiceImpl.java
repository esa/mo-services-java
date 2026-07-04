/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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

package esa.mo.services.mc.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConfigurationProviderSingleton;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mc.AmbiguousException;
import org.ccsds.moims.mo.mc.alert.AlertHelper;
import org.ccsds.moims.mo.mc.alert.provider.AlertInheritanceSkeleton;
import org.ccsds.moims.mo.mc.alert.provider.MonitorAlertPublisher;
import org.ccsds.moims.mo.mc.backends.AlertBackend;
import org.ccsds.moims.mo.mc.structures.AlertConfiguration;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;
import org.ccsds.moims.mo.mc.structures.AlertDefinition;
import org.ccsds.moims.mo.mc.structures.AlertDefinitionList;

/**
 * The Alert service implementation, provider side.
 *
 */
public class AlertProviderServiceImpl extends AlertInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(AlertProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();
    private AlertBackend backend;
    private MALProvider service;
    private MonitorAlertPublisher monitorAlertPublisher;
    private boolean running = false;
    private boolean isRegistered = false;

    private AlertDefinitionList definitions;
    private boolean[] generationEnabled;
    private boolean[] lastConditionStatus;
    private NullableAttributeList[] lastConditionArgs;

    /**
     * Initializes the service.
     *
     * @param backend The backend of this service.
     * @throws MALException On initialization error.
     */
    public synchronized void init(AlertBackend backend) throws MALException {
        if (backend == null) {
            throw new IllegalArgumentException("The backend cannot be null!");
        }

        this.backend = backend;

        // Load all alert definitions from the backend
        definitions = backend.getAllAlertDefinitions();
        int count = definitions.size();

        // All alerts start with generation disabled
        generationEnabled = new boolean[count];
        lastConditionStatus = new boolean[count];
        lastConditionArgs = new NullableAttributeList[count];

        // Shut down old service transport
        if (service != null) {
            connection.closeAll();
        }

        // Start the service with pub-sub enabled
        service = connection.startService(AlertHelper.ALERT_SERVICE, true, this);

        // Create the monitorAlert publisher
        IdentifierList domain = connection.getConnectionDetails().getDomain();
        Identifier network = ConfigurationProviderSingleton.getNetwork();
        if (network == null) {
            network = new Identifier("");
        }

        monitorAlertPublisher = super.createMonitorAlertPublisher(
                domain,
                network,
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.BESTEFFORT,
                null,
                new UInteger(0));

        // Register with the 3 standard subscription keys: alertKey, alertVersion, alertSeverity
        try {
            monitorAlertPublisher.registerWithDefaultKeys(new PublishInteractionListener());
            isRegistered = true;
        } catch (MALInteractionException ex) {
            LOGGER.log(Level.SEVERE, "Failed to register monitorAlert publisher", ex);
            throw new MALException("Failed to register monitorAlert publisher", ex);
        }

        // Start listening for backend condition notifications
        backend.register(new AlertBackendListener());

        running = true;
        LOGGER.info("Alert service READY");
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            if (isRegistered && monitorAlertPublisher != null) {
                try {
                    monitorAlertPublisher.deregister();
                } catch (MALInteractionException | MALException ex) {
                    LOGGER.log(Level.WARNING, "Exception during publisher deregistration {0}", ex);
                }
                isRegistered = false;
            }

            if (monitorAlertPublisher != null) {
                try {
                    monitorAlertPublisher.close();
                } catch (MALException ex) {
                    LOGGER.log(Level.WARNING, "Exception during publisher close {0}", ex);
                }
            }

            if (service != null) {
                service.close();
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



    /**
     * Returns the generation configuration for the requested alerts.
     */
    @Override
    public AlertConfigurationList getAlertConfiguration(
            IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws AmbiguousException, MALInteractionException, MALException {

        List<Integer> indices = resolveAlertDefinitions(domain, keys);

        AlertConfigurationList result = new AlertConfigurationList();
        for (int idx : indices) {
            result.add(new AlertConfiguration(generationEnabled[idx]));
        }
        return result;
    }

    /**
     * Enables alert generation for the specified alerts.
     * If keys is null, enables all matching the domain.
     * If any key is unknown/ambiguous, throws error and makes no changes (atomic).
     * When enabling, publishes any retained active condition immediately.
     */
    @Override
    public void enableGeneration(
            IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws AmbiguousException, MALInteractionException, MALException {

        if (keys == null) {
            
            for (int i = 0; i < definitions.size(); i++) {
                if (domainMatches(definitions.get(i), domain)) {
                    boolean wasDisabled = !generationEnabled[i];
                    generationEnabled[i] = true;
                    if (wasDisabled && lastConditionStatus[i]) {
                        publishAlert(i, lastConditionArgs[i]);
                        lastConditionStatus[i] = false;
                        lastConditionArgs[i] = null;
                    }
                }
            }
        } else {

            List<Integer> indices = resolveAlertDefinitions(domain, keys);


            for (int idx : indices) {
                boolean wasDisabled = !generationEnabled[idx];
                generationEnabled[idx] = true;
                if (wasDisabled && lastConditionStatus[idx]) {
                    publishAlert(idx, lastConditionArgs[idx]);
                    lastConditionStatus[idx] = false;
                    lastConditionArgs[idx] = null;
                }
            }
        }
    }

    /**
     * Disables alert generation for the specified alerts.
     * If keys is null, disables all matching the domain.
     * If any key is unknown/ambiguous, throws error and makes no changes (atomic).
     */
    @Override
    public void disableGeneration(
            IdentifierList domain,
            IdentifierList keys,
            MALInteraction interaction) throws AmbiguousException, MALInteractionException, MALException {

        if (keys == null) {

            for (int i = 0; i < definitions.size(); i++) {
                if (domainMatches(definitions.get(i), domain)) {
                    generationEnabled[i] = false;
                }
            }
        } else {

            List<Integer> indices = resolveAlertDefinitions(domain, keys);

            for (int idx : indices) {
                generationEnabled[idx] = false;
            }
        }
    }


    /**
     * Resolves (domain, keys) into a list of definition indices.
     */
    private List<Integer> resolveAlertDefinitions(IdentifierList domain, IdentifierList keys)
            throws AmbiguousException, MALInteractionException {

        List<Integer> matchedIndices = new ArrayList<>();
        List<Integer> ambiguousIndices = new ArrayList<>();
        List<Integer> unknownIndices = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {
            Identifier key = keys.get(i);
            List<Integer> matches = new ArrayList<>();

            // Search through all definitions for matching key and domain
            for (int j = 0; j < definitions.size(); j++) {
                AlertDefinition def = definitions.get(j);
                if (def.getObjectIdentity().getKey().equals(key)) {
                    if (domain == null || def.getObjectIdentity().getDomain().equals(domain)) {
                        matches.add(j);
                    }
                }
            }

            if (domain == null && matches.size() > 1) {
                ambiguousIndices.add(i);
            } else if (matches.isEmpty()) {
                unknownIndices.add(i);
            } else {
                matchedIndices.add(matches.get(0));
            }
        }

        // Ambiguous takes priority
        if (!ambiguousIndices.isEmpty()) {
            UIntegerList extraInfo = toUIntegerList(ambiguousIndices);
            throw new AmbiguousException(extraInfo);
        }

        if (!unknownIndices.isEmpty()) {
            UIntegerList extraInfo = toUIntegerList(unknownIndices);
            throw new MALInteractionException(
                    new MOErrorException("Unknown", MALHelper.UNKNOWN_ERROR_NUMBER, extraInfo));
        }

        return matchedIndices;
    }

    /**
     * Checks whether a definition's domain matches the given domain filter.
     * If the filter is null, everything matches.
     */
    private boolean domainMatches(AlertDefinition def, IdentifierList domain) {
        if (domain == null) {
            return true;
        }
        return def.getObjectIdentity().getDomain().equals(domain);
    }

    /**
     * Converts a list of int indices into a UIntegerList for error extraInfo.
     */
    private UIntegerList toUIntegerList(List<Integer> indices) {
        UIntegerList list = new UIntegerList();
        for (int idx : indices) {
            list.add(new UInteger(idx));
        }
        return list;
    }

    /**
     * Publishes an alert notification via the monitorAlert pub-sub operation.
     * Builds the UpdateHeader with the 3 subscription keys (alertKey, alertVersion, alertSeverity)
     * and the domain from the AlertDefinition identity, then publishes.
     */
    private void publishAlert(int alertID, NullableAttributeList arguments) {
        try {
            AlertDefinition def = definitions.get(alertID);

            // Subscription key values
            NullableAttributeList keyValues = new NullableAttributeList();
            keyValues.add(new NullableAttribute(def.getObjectIdentity().getKey()));       
            keyValues.add(new NullableAttribute(def.getObjectIdentity().getVersion()));  
            keyValues.add(new NullableAttribute(
                    new UOctet(def.getSeverity().getValue())));                        

            // UpdateHeader: source URI, domain from the definition, key values
            Identifier source = new Identifier(
                    connection.getConnectionDetails().getProviderURI().getValue());
            IdentifierList alertDomain = def.getObjectIdentity().getDomain();
            UpdateHeader updateHeader = new UpdateHeader(source, alertDomain, keyValues);

            // Timestamp = time of detection
            Time timestamp = new Time(System.currentTimeMillis());

            // Publish
            monitorAlertPublisher.publish(updateHeader, timestamp, arguments);

        } catch (IllegalArgumentException | MALInteractionException | MALException ex) {
            LOGGER.log(Level.WARNING, "Exception during monitorAlert publish: " + ex.getMessage(), ex);
        }
    }

    /**
     * Receives condition notifications from the backend.
     * Stores the latest condition state (for retained-condition publish on enable),
     * and publishes if both the condition is active and generation is enabled.
     */
    private class AlertBackendListener implements AlertBackend.AlertListener {

        @Override
        public void notifyAlertCondition(
                int alertID,
                boolean status,
                NullableAttributeList arguments) {

            // Always remember the latest condition for retained-condition publishing
            lastConditionStatus[alertID] = status;
            lastConditionArgs[alertID] = arguments;

            // Only publish when the condition is active AND generation is enabled
            if (!status || !generationEnabled[alertID]) {
                return;
            }

            publishAlert(alertID, arguments);
        }
    }
}